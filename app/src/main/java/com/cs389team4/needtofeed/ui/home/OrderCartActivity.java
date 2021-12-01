package com.cs389team4.needtofeed.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Order;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.ActivityOrderCartBinding;
import com.cs389team4.needtofeed.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Iterator;

public class OrderCartActivity extends AppCompatActivity {

    private ActivityOrderCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Amplify.API.query(
                ModelQuery.list(Order.class, Order.IS_ACTIVE.eq(true)),
                response -> {
                    Iterator<Order> resp = response.getData().getItems().iterator();
                    final Order[] order = new Order[1];
                    resp.forEachRemaining(element -> order[0] = element);

                    binding.orderCartRestaurantName.setText(order[0].getOrderRestaurant());
                    binding.orderCartOrderType.setText(order[0].getOrderType());
                    binding.orderCartEstimatedTime.setText(order[0].getEstimatedTimeComplete().toString());
                },
                error -> Utils.showMessage(getApplicationContext(), "NOT SHOWN!")
        );

        String[] str = new String[]{"Foo", "Bar", "Test"};

        RecyclerView recyclerView = binding.orderCartItemList;
        OrderCartAdapter itemAdapter = new OrderCartAdapter(str);
        recyclerView.setAdapter(itemAdapter);

        Button btnCheckout = binding.continueCheckout;
        btnCheckout.setOnClickListener(v ->
                startActivity(new Intent(this, CheckoutActivity.class)));
    }
}