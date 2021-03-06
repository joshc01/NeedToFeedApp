package com.cs389team4.needtofeed.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;

import com.cs389team4.needtofeed.R;

public class Utils {

    private Utils() {
        // Empty private constructor to hide implicit public constructor
    }

    // Display toast message
    public static void showMessage(Context context, String message) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, message, Toast.LENGTH_LONG).show());
    }

    // Calculate delivery fee
    public static double getDeliveryFee(float orderSubtotal) {
        // Return 15 percent of order subtotal, at max of 5
        return Math.min(orderSubtotal * 0.15, 5.00);
    }

    public static AlertDialog createLoadingDialog(Context context) {
        AlertDialog.Builder dialogLoadingBuilder = new AlertDialog.Builder(context);
        dialogLoadingBuilder.setCancelable(false);
        dialogLoadingBuilder.setTitle("Loading..")
                .setView(R.layout.loading_dialog_overlay);

        AlertDialog dialog = dialogLoadingBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    public static Bitmap bitmapFromDrawableRes(Context context, @DrawableRes int resourceId) {
        return convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId));
    }

    private static @Nullable Bitmap convertDrawableToBitmap(@Nullable Drawable srcDrawable) {
        if (srcDrawable == null) {
            return null;
        }
        else if (srcDrawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) srcDrawable).getBitmap();
        }
        else {
            Drawable.ConstantState constantState = srcDrawable.getConstantState();
            Drawable drawable = constantState.newDrawable().mutate();
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }
    }
}
