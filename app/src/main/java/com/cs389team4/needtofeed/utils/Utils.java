package com.cs389team4.needtofeed.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class Utils {

    // Display toast message
    public void showMessage(Context context, String message) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, message, Toast.LENGTH_LONG).show());
    }

    // Calculate delivery fee
    public double getDeliveryFee(double orderSubtotal) {
        // Return 15 percent of order subtotal, at max of 5
        return Math.min(orderSubtotal * 0.15, 5.00);
    }
}
