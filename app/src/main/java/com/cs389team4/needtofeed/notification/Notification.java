package com.cs389team4.needtofeed.notification;

public enum Notification {

    GENERAL("General",
            NotificationData.GENERAL_DATA,
            "ic_bell_white_24dp",
            "",
            "[]",
            true,
            0),

    GREETING("Greetings",
            NotificationData.GREETING_DATA,
            "ic",
            "",
            "[]",
            true,
            0),

    PROMOTIONS("Promotions",
            NotificationData.PROMOTION_DATA,
            "ic",
            "",
            "[]",
            true,
            0),

    ABANDONED_CART("Abandoned Cart",
            NotificationData.ABANDONED_CART_DATA,
            "ic",
            "",
            "[]",
            true,
            0),

    RE_ENGAGEMENT("Re-Engagement",
            NotificationData.RE_ENGAGEMENT_DATA,
            "ic",
            "",
            "[]",
            true,
            0),

    RATING("Rating",
            NotificationData.RATING_DATA,
            "ic",
            "",
            "[]",
            true,
            0);

    private final String title;
    private final String[][] data;
    private final String smallIconRes;
    private final String iconUrl;
    private final String buttons;
    private final boolean shouldShow;
    private int pos;

    Notification(String title, String[][] data, String smallIconRes, String iconUrl, String buttons, boolean shouldShow, int pos) {
        this.title = title;
        this.data = data;
        this.smallIconRes = smallIconRes;
        this.iconUrl = iconUrl;
        this.buttons = buttons;
        this.shouldShow = shouldShow;
        this.pos = pos;
    }

    public String getGroup() {
        return title;
    }

    public String getTitle(int pos) {
        return data[pos][0];
    }

    public String getMessage(int pos) {
        return data[pos][1];
    }

    public String getSmallIconRes() {
        return smallIconRes;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getLargeIconUrl(int pos) {
        return data[pos][2];
    }

    public String getBigPictureUrl(int pos) {
        return data[pos][3];
    }

    public String getButtons() {
        return buttons;
    }

    public boolean shouldShow() {
        return shouldShow;
    }

    public int getTemplatePos() {
        if (pos > 2) pos = 0;
        return pos++;
    }
}
