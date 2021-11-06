package com.cs389team4.needtofeed.utils;

import static spark.Spark.port;
import static spark.Spark.post;

import com.google.gson.Gson;

import com.stripe.model.EphemeralKey;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        port(4242);

        post("/payment-sheet", (request, response) -> {
            response.type("application/json");

            // Use an existing Customer ID if this is a returning customer.
            CustomerCreateParams customerParams = CustomerCreateParams.builder().build();

            Customer customer = null;
            try {
                customer = Customer.create(customerParams);
            } catch (StripeException e) {
                e.printStackTrace();
            }

            EphemeralKeyCreateParams ephemeralKeyParams =
                    EphemeralKeyCreateParams.builder()
                            .setCustomer(customer.getId())
                            .build();

            RequestOptions ephemeralKeyOptions =
                    new RequestOptions.RequestOptionsBuilder()
                    .setStripeVersionOverride("2020-08-27")
                    .build();

            EphemeralKey ephemeralKey = EphemeralKey.create(
                    ephemeralKeyParams,
                    ephemeralKeyOptions);

            List<String> paymentMethodTypes = new ArrayList<>();
            paymentMethodTypes.add("bancontact");
            paymentMethodTypes.add("card");
            paymentMethodTypes.add("sepa_debit");
            PaymentIntentCreateParams paymentIntentParams =
                    PaymentIntentCreateParams.builder()
                            .setAmount(1099L)
                            .setCurrency("usd")
                            .setCustomer(customer.getId())
                            .addAllPaymentMethodType(paymentMethodTypes)
                            .build();
            PaymentIntent paymentIntent = null;
            try {
                paymentIntent = PaymentIntent.create(paymentIntentParams);
            } catch (StripeException e) {
                e.printStackTrace();
            }

            Map<String, String> responseData = new HashMap<>();
            responseData.put("paymentIntent", paymentIntent.getClientSecret());
            responseData.put("ephemeralKey", ephemeralKey.getSecret());
            responseData.put("customer", customer.getId());
            responseData.put("publishableKey", "pk_test_51JSLHXBOGDvmFaAC9zb0sYlMY2yVUq53MfuJj2dfNGd9J2mQlEpCYrxRrqks0LU751yc9Lbemo7IYGVRwkGD04Jg00sxhwBVop");

            return gson.toJson(responseData);
        });
    }
}
