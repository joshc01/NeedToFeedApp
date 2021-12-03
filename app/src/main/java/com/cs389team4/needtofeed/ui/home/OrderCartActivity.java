package com.cs389team4.needtofeed.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Order;
import com.cs389team4.needtofeed.databinding.ActivityOrderCartBinding;
import com.cs389team4.needtofeed.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Set;

public class OrderCartActivity extends AppCompatActivity {

    private ActivityOrderCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Set<String> orderContent = new ArraySet<>();

        Amplify.API.query(
                ModelQuery.list(Order.class, Order.IS_ACTIVE.eq(true)),
                response -> {
                    final Order[] order = new Order[1];
                    order[0] = response.getData().getItems().iterator().next();

                    binding.orderCartRestaurantName.setText(order[0].getOrderRestaurant());
                    binding.orderCartOrderType.setText(order[0].getOrderType());
                    binding.orderCartEstimatedTime.setText(order[0].getEstimatedTimeComplete().toString());

                    JsonObject orderDetailsJson = JsonParser.parseString(order[0].getOrderItems()).getAsJsonObject();

                    orderContent.addAll(orderDetailsJson.keySet());

                    Utils.showMessage(getApplicationContext(), orderContent + "");
                },
                error -> Utils.showMessage(getApplicationContext(), "NOT SHOWN!")
        );

        String[] str = new String[]{"Foo", "Bar", "Test"};
        String[] arrOrderContent = orderContent.toArray(new String[0]);

        RecyclerView recyclerView = binding.orderCartItemList;
        OrderCartAdapter itemAdapter = new OrderCartAdapter(arrOrderContent);
        recyclerView.setAdapter(itemAdapter);

        Button btnCheckout = binding.continueCheckout;
        btnCheckout.setOnClickListener(v ->
                startActivity(new Intent(this, CheckoutActivity.class)));
    }
}