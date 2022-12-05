package com.cookandroid.dakku;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;

public interface SendEventListener {
    public void sendMessage(String message);
    public void sendColor(int color);
    //public void sendFont(String font);
}
