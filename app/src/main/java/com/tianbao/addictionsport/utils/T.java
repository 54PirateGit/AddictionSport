package com.tianbao.addictionsport.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司
 * Created by edianzu on 2017/9/12.
 */

public class T {

    private static boolean isDebug = true;// 是否调试测试

    // 私有
    private T() {

    }

    /**
     * 显示 short
     */
    public static void showShort(Context context, String content) {
        if (isDebug) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示 long
     */
    public static void showLong(Context context, String content) {
        if (isDebug) {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 一直显示 short
     */
    public static void alwaysShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 一直显示 long
     */
    public static void alwaysLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
