package com.cs389team4.needtofeed.ui.home;

import static com.cs389team4.needtofeed.utils.Constants.SALES_TAX_NY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Order;
import com.cs389team4.needtofeed.databinding.ActivityOrderCartBinding;
import com.cs389team4.needtofeed.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderCartActivity extends AppCompatActivity {

    private ActivityOrderCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        JsonObject queryResult = null;

        float subtotal = 0;
        double deliveryFee = 0;
        double tax = 0;
        double total = 0;
        double tip = 0;

        try {
            Future<JsonObject> jsonQueryOrderAsync = getJsonAsync();

            queryResult = jsonQueryOrderAsync.get();
            String[] arrOrderKeyset = queryResult.keySet().toArray(new String[0]);

            RecyclerView recyclerView = binding.orderCartItemList;

            OrderCartAdapter itemAdapter = new OrderCartAdapter(arrOrderKeyset, queryResult);
            recyclerView.setAdapter(itemAdapter);

            for (String key : arrOrderKeyset) {
                float price = queryResult.getAsJsonObject(key).get("price").getAsFloat();
                subtotal += price;
            }

            deliveryFee = Utils.getDeliveryFee(subtotal);
            tax = subtotal * SALES_TAX_NY;
            tip = subtotal * .15;
            total = subtotal + deliveryFee + tax + tip;

            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setCurrency(Currency.getInstance("USD"));

            binding.orderCartSubtotalValue.setText(format.format(subtotal));
            binding.orderCartDeliveryFeeValue.setText(format.format(deliveryFee));
            binding.orderCartTaxValue.setText(format.format(tax));
            binding.orderCartTipValue.setText(format.format(tip));
            binding.orderCartTotalValue.setText(format.format(total));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button btnCheckout = binding.continueCheckout;

        JsonObject finalQueryResult = queryResult;

        final float finalSubtotal = subtotal;
        final double finalTotal = total;
        final double finalDeliveryFee = deliveryFee;
        final double finalTip = tip;
        final double finalTax = tax;

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra("order", String.valueOf(finalQueryResult));
            intent.putExtra("priceTotal", finalTotal);
            intent.putExtra("subtotal", finalSubtotal);
            intent.putExtra("deliveryFee", finalDeliveryFee);
            intent.putExtra("tax", finalTax);
            intent.putExtra("tip", finalTip);

            startActivity(intent);
        });
    }

    public Future<JsonObject> getJsonAsync() throws InterruptedException {
        CompletableFuture<JsonObject> asyncTask = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Amplify.API.query(
                    ModelQuery.list(Order.class, Order.IS_EDITABLE.eq(true)),
                    response -> {
                        final Order[] order = new Order[1];
                        order[0] = response.getData().getItems().iterator().next();

                        binding.orderCartRestaurantName.setText(order[0].getOrderRestaurant());
                        binding.orderCartOrderType.setText(order[0].getOrderType());
                        binding.orderCartEstimatedTime.setText(order[0].getEstimatedTimeComplete().toDate().toString().substring(12,16));

                        JsonObject orderDetailsJson = JsonParser.parseString(order[0].getOrderItems()).getAsJsonObject();

                        asyncTask.complete(orderDetailsJson);
                    },
                    error -> Utils.showMessage(getApplicationContext(), "Error loading order cart, please try again")
            );
            return null;
        });
        return asyncTask;
    }
}
