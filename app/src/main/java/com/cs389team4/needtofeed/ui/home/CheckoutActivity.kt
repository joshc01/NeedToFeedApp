package com.cs389team4.needtofeed.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.cs389team4.needtofeed.databinding.ActivityCheckoutBinding
import com.cs389team4.needtofeed.utils.PaymentUtil
import com.cs389team4.needtofeed.utils.Utils
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentsClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CheckoutActivity: Activity() {
    private lateinit var paymentsClient: PaymentsClient

    private lateinit var foodItemList: JSONArray
    private lateinit var selectedFoodItem: JSONObject

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

        } else {
            Utils().showMessage(this, "Unfortunately, Google Pay is not available on this device")
        }
    }

    private fun requestPayment() {

    }

    // Handle resolved activity from Google Pay payment sheet
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
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

            }
        }
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
