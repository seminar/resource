package com.mstar.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

public class Tools {

    public static void toastShow(int resId, Context context) {
        Toast toast = new Toast(context);
        TextView MsgShow = new TextView(context);
        toast.setDuration(Toast.LENGTH_LONG);
        MsgShow.setTextColor(Color.RED);
        MsgShow.setTextSize(25);
        MsgShow.setText(resId);
        toast.setView(MsgShow);
        toast.show();
    }

    private static final String MSTAR_PRODUCT_CHARACTERISTICS = "mstar.product.characteristics";
    private static final String MSTAR_PRODUCT_STB = "stb";
    private static String mProduct = null;
    public static boolean isBox() {
        if (mProduct == null) {
            Class<?> systemProperties = null;
            Method method = null;
            try {
                systemProperties = Class.forName("android.os.SystemProperties");
                method = systemProperties.getMethod("get", String.class, String.class);
                mProduct = (String) method.invoke(null, MSTAR_PRODUCT_CHARACTERISTICS, "");
            } catch (Exception e) {
                return false;
            }
        }
        // Log.d("Tools", "mstar.product.characteristics is " + mProduct);
        if (MSTAR_PRODUCT_STB.equals(mProduct)) {
            return true;
        } else {
            return false;
        }
    }

}
