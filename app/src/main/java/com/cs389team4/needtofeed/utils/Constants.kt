package com.cs389team4.needtofeed.utils

import com.google.android.gms.wallet.WalletConstants

object Constants {
    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

    val SUPPORTED_NETWORKS = listOf(
        "AMEX",
        "DISCOVER",
        "JCB",
        "MASTERCARD",
        "VISA"
    )

    val SUPPORTED_METHODS = listOf(
        "PAN_ONLY",
        "CRYPTOGRAM_3DS"
    )

    const val COUNTRY_CODE = "US"

    const val CURRENCY_CODE = "USD"

    const val PAYMENT_GATEWAY_TOKENIZATION_NAME = "NeedToFeed"

    val PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS = mapOf(
        "gateway" to PAYMENT_GATEWAY_TOKENIZATION_NAME,
        "gatewayMerchantId" to "exampleGatewayMerchantId"
    )
}
