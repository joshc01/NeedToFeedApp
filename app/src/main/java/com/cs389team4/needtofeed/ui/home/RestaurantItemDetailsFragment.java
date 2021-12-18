package com.cs389team4.needtofeed.ui.home;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Order;
import com.bumptech.glide.Glide;

import com.cs389team4.needtofeed.MainActivity;

import com.cs389team4.needtofeed.databinding.FragmentRestaurantItemDetailsBinding;
import com.cs389team4.needtofeed.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Currency;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantItemDetailsFragment extends Fragment {
    private FragmentRestaurantItemDetailsBinding binding = null;
    private final String TAG = "RestaurantItemDetailsFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantItemDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewItem = binding.itemDetailsImage;
        TextView textViewItemName = binding.itemDetailsName;
        TextView textViewItemPrice = binding.itemDetailsPrice;

        RestaurantItemDetailsFragmentArgs args = RestaurantItemDetailsFragmentArgs.fromBundle(getArguments());

        String itemImage = args.getItemImage();
        Glide.with(view).load(itemImage).centerCrop().into(imageViewItem);

        String itemName = args.getItemName();
        textViewItemName.setText(itemName);

        String restaurantName = args.getRestaurantName();

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("USD"));
        String itemPrice = format.format(args.getItemPrice());
        textViewItemPrice.setText(itemPrice);

        Button btnAddToCart = binding.itemDetailsAddButton;

        ImageButton btnQuantityAdd = binding.btnQuantityIncrease;
        ImageButton btnQuantityRemove = binding.btnQuantityDecrease;
        TextView textViewQuantity = binding.lblQuantity;

        AtomicInteger quantity = new AtomicInteger(1);

        btnQuantityAdd.setOnClickListener(v -> {
            if (quantity.get() < 99) {
                quantity.getAndIncrement();
                textViewQuantity.setText(quantity.toString());
            }
        });
        btnQuantityRemove.setOnClickListener(v -> {
            if (quantity.get() > 1) {
                quantity.getAndDecrement();
                textViewQuantity.setText(quantity.toString());
            }
        });

        AlertDialog dialogLoadCart = Utils.createLoadingDialog(getContext());

        btnAddToCart.setOnClickListener(v -> {
            dialogLoadCart.show();

            Amplify.API.query(
                    ModelQuery.list(Order.class, Order.IS_EDITABLE.eq(true)),
                    response -> {
                        Order existingOrder = response.getData().iterator().next();
                        // If no active order exists
                        if (!MainActivity.getOrderCartExists()) {

                            JsonObject orderContent = new JsonObject();
                            orderContent.add("quantity", new Gson().toJsonTree(quantity));
                            orderContent.add("price", new Gson().toJsonTree(args.getItemPrice()));

                            JsonObject orderDetailsJson = new JsonObject();
                            orderDetailsJson.add(itemName, orderContent);

                            String dateTime = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

                            Order todo = Order.builder()
                                    .orderType("Delivery")
                                    .estimatedTimeComplete(new Temporal.Time(String.valueOf(LocalTime.now())))
                                    .orderTotal(0.99)
                                    .orderItems(orderDetailsJson.toString())
                                    .isEditable(true)
                                    .isActive(false)
                                    .orderRestaurantId("restaurant_id")
                                    .orderDateTime(new Temporal.DateTime(dateTime))
                                    .orderRestaurant(restaurantName)
                                    .build();

                            Amplify.API.mutate(ModelMutation.create(todo),
                                    resp -> {
                                        Log.i(TAG, "Todo with id: " + resp);
                                        runOnUiThread(() -> {
                                            Utils.showMessage(getContext(), "Item added to cart");
                                            Navigation.findNavController(view).popBackStack();
                                        });
                                    },
                                    error -> Log.e(TAG, "Create failed", error)
                            );

                            MainActivity.setOrderCartExists(true);
                        } else if (!existingOrder.getOrderRestaurant().equals(restaurantName)) {

                            AlertDialog.Builder dialogCartExistsBuilder = new AlertDialog.Builder(getContext());
                            dialogCartExistsBuilder.setTitle("Cart not empty")
                                    .setMessage("Your cart contains items from another restaurant. Empty your cart to start a new order.")
                                    .setNeutralButton("Cancel", null)
                                    .setNegativeButton("Empty cart", (dialog, which) -> {

                                        AlertDialog loadingDialog = Utils.createLoadingDialog(getContext());
                                        loadingDialog.show();

                                        Amplify.API.mutate(ModelMutation.delete(existingOrder),
                                                resp -> {
                                                    Log.i(TAG, "Order deleted successfully: " + resp);
                                                    runOnUiThread(() -> Navigation.findNavController(view).popBackStack());
                                                    loadingDialog.dismiss();
                                                },
                                                error -> {
                                                    Log.e(TAG, "Delete failed", error);
                                                    loadingDialog.dismiss();
                                                }
                                        );
                                    });

                            requireActivity().runOnUiThread(dialogCartExistsBuilder::show);
                        } else {
                            JsonObject orderContent = new JsonObject();

                            orderContent.add("price", new Gson().toJsonTree(args.getItemPrice()));
                            orderContent.add("quantity", new Gson().toJsonTree(quantity));

                            JsonObject orderDetailsJson = JsonParser.parseString(existingOrder.getOrderItems()).getAsJsonObject();

                            if (orderDetailsJson.keySet().contains(itemName)) {
                                int orderItemQty = orderDetailsJson.getAsJsonObject(itemName).get("quantity").getAsInt();
                                orderItemQty += quantity.get();
                                orderContent.addProperty("quantity", orderItemQty);
                                orderDetailsJson.remove(itemName);
                            }

                            orderDetailsJson.add(itemName, orderContent);

                            Order orderUpdate = Order.builder()
                                    .orderType(existingOrder.getOrderType())
                                    .estimatedTimeComplete(existingOrder.getEstimatedTimeComplete())
                                    .orderTotal(existingOrder.getOrderTotal())
                                    .orderItems(orderDetailsJson.toString())
                                    .isEditable(existingOrder.getIsEditable())
                                    .isActive(existingOrder.getIsActive())
                                    .orderRestaurantId(existingOrder.getOrderRestaurantId())
                                    .orderDateTime(existingOrder.getOrderDateTime())
                                    .orderRestaurant(restaurantName)
                                    .id(existingOrder.getId())
                                    .build();

                            Amplify.API.mutate(ModelMutation.update(orderUpdate),
                                    resp -> {
                                        Log.i(TAG, "Todo with id: " + resp);
                                        runOnUiThread(() -> {
                                            Utils.showMessage(getContext(), "Item added to cart");
                                            Navigation.findNavController(view).popBackStack();
                                        });
                                    },
                                    error -> Log.e(TAG, "Create failed", error)
                            );
                        }
                    },
                    error -> Log.e("Failure updating order: ", error.getMessage())
            );
        });
    }
}
