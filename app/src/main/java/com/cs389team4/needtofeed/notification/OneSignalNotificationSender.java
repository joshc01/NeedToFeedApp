package com.cs389team4.needtofeed.notification;

import android.util.Log;

import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class OneSignalNotificationSender {
    private static final String TAG = "OneSignalNotificationSender";

    public static void sendDeviceNotification(final Notification notification) {
        new Thread(() -> {
            OSDeviceState deviceState = OneSignal.getDeviceState();
            String userId = deviceState != null ? deviceState.getUserId() : null;
            boolean isSubscribed = deviceState != null && deviceState.isSubscribed();

            if (!isSubscribed) return;

            int pos = notification.getTemplatePos();
            try {
                JSONObject notificationContent = new JSONObject("{'include_player_ids': ['" + userId + "']," +
                        "'headings': {'en': '" + notification.getTitle(pos) + "'}," +
                        "'contents': {'en': '" + notification.getMessage(pos) + "'}," +
                        "'small_icon': '" + notification.getSmallIconRes() + "'," +
                        "'large_icon': '" + notification.getLargeIconUrl(pos) + "'," +
                        "'big_picture': '" + notification.getBigPictureUrl(pos) + "'," +
                        "'android_group': '" + notification.getGroup() + "'," +
                        "'buttons': " + notification.getButtons() + "," +
                        "'android_led_color': 'FFE9444E'," +
                        "'android_accent_color': 'FFE9444E'," +
                        "'android_sound': 'nil'}");

                OneSignal.postNotification(notificationContent, new OneSignal.PostNotificationResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.d(TAG, "Success sending notification: " + response.toString());
                    }

                    @Override
                    public void onFailure(JSONObject response) {
                        Log.d(TAG, "Failure sending notification: " + response.toString());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
