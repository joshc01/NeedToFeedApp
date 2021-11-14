package com.cs389team4.needtofeed.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cs389team4.needtofeed.databinding.ActivityCheckoutBinding
import com.cs389team4.needtofeed.utils.PaymentUtil
import com.cs389team4.needtofeed.utils.Utils
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToLong

class CheckoutActivity: AppCompatActivity() {
    private lateinit var paymentsClient: PaymentsClient

    private lateinit var foodItemList: JSONArray
    private lateinit var selectedFoodItem: JSONObject

    private lateinit var btnGooglePay: RelativeLayout

    private lateinit var binding: ActivityCheckoutBinding

    companion object {
        private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Google Pay API client
        paymentsClient = PaymentUtil.createPaymentsClient(this)

        showGooglePayButtonIfReady()

        btnGooglePay = binding.checkoutBtnPlaceOrder.root
        btnGooglePay.setOnClickListener {
            requestPayment()
        }
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
            Utils().showMessage(this, "Unfortunately, Google Pay is not available on this device")
        }
    }

    private fun requestPayment() {
        btnGooglePay.isClickable = false

        val orderSubtotal = 13.99
        val deliveryFee = Utils().getDeliveryFee(orderSubtotal)
        val priceCents = (orderSubtotal * PaymentUtil.CENTS.toLong()).roundToLong() + (deliveryFee * PaymentUtil.CENTS.toLong())

        val paymentDataRequestJson = PaymentUtil.getPaymentDataRequest(priceCents.toLong())
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

    // Handle resolved activity from Google Pay payment sheet
    val paymentSheetActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->

        val data: Intent? = result.data

        when (result.resultCode) {
            RESULT_OK -> {
                data?.let { intent ->
                    PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                }
            }
            RESULT_CANCELED -> {
                // User cancelled payment attempt
            }
            AutoResolveHelper.RESULT_ERROR -> {
                AutoResolveHelper.getStatusFromIntent(data)?.let {
                    handleError(it.statusCode)
                }
            }
        }
        btnGooglePay.isClickable = true
    }

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
