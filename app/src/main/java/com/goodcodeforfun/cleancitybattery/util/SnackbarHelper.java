package com.goodcodeforfun.cleancitybattery.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by snigavig on 04.10.15.
 */
public class SnackbarHelper {
    public static void show(View mParentView, String message) {
        if (null != mParentView)
            Snackbar
                    .make(mParentView, message, Snackbar.LENGTH_LONG)
                    .setAction("", null)
                    .show(); // Don’t forget to show!
    }
}
