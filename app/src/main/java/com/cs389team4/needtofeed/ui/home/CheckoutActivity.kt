package com.cs389team4.needtofeed.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Order
import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.databinding.ActivityCheckoutBinding
import com.cs389team4.needtofeed.notification.Notification
import com.cs389team4.needtofeed.notification.OneSignalNotificationSender
import com.cs389team4.needtofeed.utils.PaymentUtil
import com.cs389team4.needtofeed.utils.Utils
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.properties.Delegates

class CheckoutActivity: AppCompatActivity() {
    private lateinit var paymentsClient: PaymentsClient

    private lateinit var orderDetails: JsonObject

    private var total by Delegates.notNull<Double>()
    private var subtotal by Delegates.notNull<Float>()
    private var deliveryFee by Delegates.notNull<Double>()
    private var tax by Delegates.notNull<Double>()
    private var tip by Delegates.notNull<Double>()

    private lateinit var btnGooglePay: RelativeLayout

    private lateinit var binding: ActivityCheckoutBinding

    companion object {
        private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val strOrderDetails = bundle!!.getString("order")
        orderDetails = Gson().fromJson(strOrderDetails, JsonObject::class.java)

        total = bundle.getDouble("priceTotal")
        subtotal = bundle.getFloat("subtotal")
        deliveryFee = bundle.getDouble("deliveryFee")
        tax = bundle.getDouble("tax")
        tip = bundle.getDouble("tip")

        total += tip

        val recyclerView = binding.checkoutListItems

        val arrItemKeys = orderDetails.keySet().toTypedArray()
        val itemAdapter = OrderCartAdapter(arrItemKeys, orderDetails)

        recyclerView.adapter = itemAdapter

        val name = MainActivity.userAttrs[2].value ?: "Name"

        binding.checkoutUserName.text = name

        val intent = Intent(this, CheckoutUpdateOrderDetailsActivity::class.java)

        binding.checkoutYourInfoContactContainer.setOnClickListener {
            intent.putExtra("fragment", "contact")
            startActivity(intent)
        }

        binding.checkoutYourInfoDeliveryAddressContainer.setOnClickListener {
            intent.putExtra("fragment", "address")
            startActivity(intent)
        }

        binding.checkoutDeliveryInstructionsCourierNotesContainer.setOnClickListener {
            intent.putExtra("fragment", "notes")
            startActivity(intent)
        }

        // Initialize Google Pay API client
        paymentsClient = PaymentUtil.createPaymentsClient(this)

        showGooglePayButtonIfReady()

        btnGooglePay = binding.checkoutBtnPlaceOrder.root
        btnGooglePay.setOnClickListener {
            // requestPayment()

            OneSignalNotificationSender.sendDeviceNotification(Notification.GENERAL)

            // Update order to mark as active and not editable
            MainActivity.orderCartExists = false
            MainActivity.activeOrderExists = true

            Amplify.API.query(
                ModelQuery.list(Order::class.java, Order.IS_EDITABLE.eq(true)),
                { response ->
                    run {
                        val order = response.data.items.iterator().next()

                        val updatedOrder = Order.builder()
                            .orderType(order.orderType)
                            .estimatedTimeComplete(order.estimatedTimeComplete)
                            .orderTotal(order.orderTotal)
                            .orderItems(order.orderItems)
                            .isEditable(false)
                            .isActive(true)
                            .orderRestaurantId(order.orderRestaurantId)
                            .orderDateTime(order.orderDateTime)
                            .orderRestaurant(order.orderRestaurant)
                            .id(order.id)
                            .build()

                        Amplify.API.mutate(ModelMutation.update(updatedOrder),
                            { Log.i("CheckoutActivity", "Order updated with id: ${it.data.id}") },
                            { Log.e("CheckoutActivity", "Update failed", it) }
                        )
                    }
                },
                { Log.e("CheckoutActivity", "Query failure", it) }
            )

            // Close activity
            finish()
        }

        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance("USD")

        binding.checkoutSubtotalValue.text = format.format(subtotal)
        binding.checkoutDeliveryFeeValue.text = format.format(deliveryFee)
        binding.checkoutTaxValue.text = format.format(tax)
        binding.checkoutTipValue.text = format.format(tip)
        binding.checkoutTotalValue.text = format.format(total)
    }

    // Determine support for payment methods
    private fun showGooglePayButtonIfReady() {
        val isReadyToPayJson = PaymentUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                Log.w("isReadyToPay failure", exception)
            }
        }
    }

    // Show payment button and hide text if available on device
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            btnGooglePay.visibility = View.VISIBLE
        } else {
            Utils.showMessage(this, "Unfortunately, Google Pay is not available on this device")
        }
    }

    private fun requestPayment() {
        btnGooglePay.isClickable = false

        val priceCents = (total * PaymentUtil.CENTS.toLong()).toLong()

        val paymentDataRequestJson = PaymentUtil.getPaymentDataRequest(priceCents)
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        if (request != null) {
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this,
                LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        }

                    RESULT_CANCELED -> {
                        // User cancelled payment
                    }

                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            handleError(it.statusCode)
                        }
                    }
                }
                btnGooglePay.isClickable = true
            }
        }
    }

    // TODO: Replace deprecated onActivityResult with below function
    // Handle resolved activity from Google Pay payment sheet
//    val paymentSheetActivityResult = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()) { result ->
//
//        val data: Intent? = result.data
//
//        when (result.resultCode) {
//            RESULT_OK -> {
//                data?.let { intent ->
//                    PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
//                }
//            }
//            RESULT_CANCELED -> {
//                // User cancelled payment attempt
//            }
//            AutoResolveHelper.RESULT_ERROR -> {
//                AutoResolveHelper.getStatusFromIntent(data)?.let {
//                    handleError(it.statusCode)
//                }
//            }
//        }
//        btnGooglePay.isClickable = true
//    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInfo = paymentData.toJson() ?: return

        try {
            val paymentMethodData = JSONObject(paymentInfo).getJSONObject("paymentMethodData")
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress")
                .getString("name")

            Log.d("billingName", billingName)

            Log.d("GooglePaymentToken", paymentMethodData
                .getJSONObject("tokenizationData")
                .getString("token"))
        } catch (e: JSONException) {
            Log.e("handlePaymentSuccess", "Error: $e")
        }
    }

    private fun handleError(statusCode: Int) {
        Log.w("loadPaymentData failed", "Error code: $statusCode")
    }
}
