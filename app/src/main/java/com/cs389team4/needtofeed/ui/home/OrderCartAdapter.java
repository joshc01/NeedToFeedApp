package com.cs389team4.needtofeed.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs389team4.needtofeed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartAdapter.ViewHolder> {

    private String[] localData;

    public OrderCartAdapter(String[] data) {
        localData = data;
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

        holder.quantity.setText(localData[position]);

    }

    @Override
    public int getItemCount() {
        return localData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quantity;
        private final TextView name;
        private final TextView price;

        public ViewHolder(View view) {
            super(view);

            quantity = (TextView) view.findViewById(R.id.order_cart_list_quantity);
            name = (TextView) view.findViewById(R.id.order_cart_list_name);
            price = (TextView) view.findViewById(R.id.order_cart_list_price);
        }
    }
}
