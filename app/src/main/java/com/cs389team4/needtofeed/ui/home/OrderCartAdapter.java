package com.cs389team4.needtofeed.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs389team4.needtofeed.R;
import com.google.gson.JsonObject;

import java.text.NumberFormat;
import java.util.Currency;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartAdapter.ViewHolder> {

    private final String[] keys;
    private final JsonObject order;

    public OrderCartAdapter(String[] keys, JsonObject order) {
        this.keys = keys;
        this.order = order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_cart_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String quantity = order.getAsJsonObject(keys[position]).get("quantity").toString();
        float price = order.getAsJsonObject(keys[position]).get("price").getAsFloat();

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("USD"));

        holder.quantity.setText(quantity);
        holder.name.setText(keys[position]);
        holder.price.setText(format.format(price));
    }

    @Override
    public int getItemCount() {
        return keys.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quantity;
        private final TextView name;
        private final TextView price;

        public ViewHolder(View view) {
            super(view);

            quantity = view.findViewById(R.id.order_cart_list_quantity);
            name = view.findViewById(R.id.order_cart_list_name);
            price = view.findViewById(R.id.order_cart_list_price);
        }
    }
}
