//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2013 MStar Semiconductor, Inc. All rights reserved.
// All software, firmware and related documentation herein ("MStar Software") are
// intellectual property of MStar Semiconductor, Inc. ("MStar") and protected by
// law, including, but not limited to, copyright law and international treaties.
// Any use, modification, reproduction, retransmission, or republication of all
// or part of MStar Software is expressly prohibited, unless prior written
// permission has been granted by MStar.
//
// By accessing, browsing and/or using MStar Software, you acknowledge that you
// have read, understood, and agree, to be bound by below terms ("Terms") and to
// comply with all applicable laws and regulations:
//
// 1. MStar shall retain any and all right, ownership and interest to MStar
//    Software and any modification/derivatives thereof.
//    No right, ownership, or interest to MStar Software and any
//    modification/derivatives thereof is transferred to you under Terms.
//
// 2. You understand that MStar Software might include, incorporate or be
//    supplied together with third party's software and the use of MStar
//    Software may require additional licenses from third parties.
//    Therefore, you hereby agree it is your sole responsibility to separately
//    obtain any and all third party right and license necessary for your use of
//    such third party's software.
//
// 3. MStar Software and any modification/derivatives thereof shall be deemed as
//    MStar's confidential information and you agree to keep MStar's
//    confidential information in strictest confidence and not disclose to any
//    third party.
//
// 4. MStar Software is provided on an "AS IS" basis without warranties of any
//    kind. Any warranties are hereby expressly disclaimed by MStar, including
//    without limitation, any warranties of merchantability, non-infringement of
//    intellectual property rights, fitness for a particular purpose, error free
//    and in conformity with any international standard.  You agree to waive any
//    claim against MStar for any loss, damage, cost or expense that you may
//    incur related to your use of MStar Software.
//    In no event shall MStar be liable for any direct, indirect, incidental or
//    consequential damages, including without limitation, lost of profit or
//    revenues, lost or damage of data, and unauthorized system use.
//    You agree that this Section 4 shall still apply without being affected
//    even if MStar Software has been modified by MStar in accordance with your
//    request or instruction for your use, except otherwise agreed by both
//    parties in writing.
//
// 5. If requested, MStar may from time to time provide technical supports or
//    services in relation with MStar Software to you for your use of
//    MStar Software in conjunction with your or your customer's product
//    ("Services").
//    You understand and agree that, except otherwise agreed by both parties in
//    writing, Services are provided on an "AS IS" basis and the warranty
//    disclaimer set forth in Section 4 above shall apply.
//
// 6. Nothing contained herein shall be construed as by implication, estoppels
//    or otherwise:
//    (a) conferring any license or right to use MStar name, trademark, service
//        mark, symbol or any other identification;
//    (b) obligating MStar or any of its affiliates to furnish any person,
//        including without limitation, you and your customers, any assistance
//        of any kind whatsoever, or any information; or
//    (c) conferring any license or right under any intellectual property right.
//
// 7. These terms shall be governed by and construed in accordance with the laws
//    of Taiwan, R.O.C., excluding its conflict of law rules.
//    Any and all dispute arising out hereof or related hereto shall be finally
//    settled by arbitration referred to the Chinese Arbitration Association,
//    Taipei in accordance with the ROC Arbitration Law and the Arbitration
//    Rules of the Association by three (3) arbitrators appointed in accordance
//    with the said Rules.
//    The place of arbitration shall be in Taipei, Taiwan and the language shall
//    be English.
//    The arbitration award shall be final and binding to both parties.
//
//******************************************************************************
//<MStar Software>

package com.mstar.tvframework;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.HashMap;

//import com.mstar.babaoframework.AppWidgetInfo;
//import com.mstar.babaoframework.ItemInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;

public class MstarUtil {
    // file suffix, .PNG
    private static final String SUFIXX_PNG = ".png";
    // for storage
    private static final String PATH_SDCARD = "/sdcard/";
    // log tag
    private final static String TAG = "MstarUtil";
    // max quality
    private static final int MAX_QUALITY = 100;
    // singleton instance
    private static MstarUtil instance = new MstarUtil();
    private HashMap<String, String> mAppLocalValues;
    private Activity mActivity = null;

    private static final int FONT_BITMAP_WIDTH = 100;
    private static final int FONT_BITMAP_HEIGHT = 100;

    private MstarUtil() {
        mAppLocalValues = new HashMap<String, String>();
        mAppLocalValues.put("STORAGE_ROOT", Environment
                .getExternalStorageDirectory().getAbsolutePath());
    }

    /**
     * @param activity
     *            current focus UI.
     */
    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * @return singleton instance.
     */
    public static MstarUtil getInstance() {
        Log.v(TAG, "getInstance");
        return instance;
    }

    /**
     * Checks whether a key is in the app local value list.
     *
     * @param key
     *            The key to test.
     * @return Whether or not the key is in the app local value list.
     */
    public boolean hasAppLocalValue(String key) {
        Log.v(TAG, "hasAppLocalValue");
        return mAppLocalValues.containsKey(key);
    }

    /**
     * Get the specified key value from the app local value list.
     *
     * @param key
     *            The key to get the value of.
     * @return The key's value.
     */
    public String getAppLocalValue(String key) {
        Log.v(TAG, "getAppLocalValue");
        return mAppLocalValues.get(key);
    }

    /**
     * Set the specified key value in the app local value list.
     *
     * @param key
     *            The key to set the value of
     * @param value
     *            The value
     */
    public void setAppLocalValue(String key, String value) {
        Log.v(TAG, "setAppLocalValue");
        mAppLocalValues.put(key, value);
    }

    /**
     * This function is used to get the parameters used to start the Activity
     * via, for example:
     *
     * <pre>
     * adb shell am start -a android.intent.action.MAIN -n com.nvidia.devtech.water/com.nvidia.devtech.water.Water -e param1 1 -e param2 2
     * </pre>
     *
     * Where "param1" and "param2" are the parameter names and "1" and "2" are
     * the parameter values.
     *
     * @param paramName
     *            The name of the parameter to get.
     * @return The parameter
     */
    public String getParameter(String paramName) {
        Log.v(TAG, "getParameter");
        return mActivity.getIntent().getStringExtra(paramName);
    }

    /**
     * @param keyCode
     *            button or keyboard code.
     * @param event
     *            Contains constants for key events, see {@link KeyEvent}.
     * @param keyStatus
     *            press down or up.
     */
    public void printKeyCode(int keyCode, KeyEvent event, int keyStatus) {
        Log.v(TAG, "printKeyCode");
        String status = "Key Down";
        if (keyStatus == KeyEvent.ACTION_UP) {
            status = "Key Up";
        }
        String strkey = String.format("%s keyCode: %d", status,
                event.getKeyCode());
        Log.v(TAG, strkey);
    }

    /**
     * @param bitmap
     *            bitmap that write to file.
     * @param fileName
     *            name of file.
     * @throws IOException
     *             file operation exception.
     */
    public void saveToBitmap(Bitmap bitmap, String fileName) throws IOException {

        Log.v(TAG, "saveToBitmap");
        File f = new File(PATH_SDCARD + fileName + SUFIXX_PNG);
        f.createNewFile();
        FileOutputStream fOut = null;

        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            Log.v(TAG, "FileNotFoundException in saveToBitmap");
            e.printStackTrace();
    }
        bitmap.compress(Bitmap.CompressFormat.PNG, MAX_QUALITY, fOut);

        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.v(TAG, "IOException in saveToBitmap");
            e.printStackTrace();
    }
    }

//    @Deprecated
//    private void SaveToFile(ArrayList<ItemInfo> list, String filePath) {
//        String str = "";
//        for (ItemInfo cell : list) {
//            str = str + cell.getClassName() + "\n";
//        }
//        WriteFile(filePath, str);
//    }
//
//    /**
//     *
//     * @param fileName
//     * @param content
//     */
//    @Deprecated
//    private static void WriteFile(String fileName, String content) {
//        try {
//            FileWriter writer = new FileWriter(fileName, false);
//            writer.write(content);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Bitmap getFontBmp(Context context, int srcId) {
        Bitmap mBitmap = Bitmap.createBitmap(FONT_BITMAP_WIDTH, FONT_BITMAP_HEIGHT, Config.ARGB_8888);

        Canvas canvas = new Canvas(mBitmap);
        canvas.drawARGB(0, 0, 0, 0);

        String familyName = "";
        Typeface font = Typeface.create(familyName,Typeface.ITALIC);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(font);

        canvas.drawText(context.getResources().getString(srcId), 12, 95, paint);

        return mBitmap;
    }

    public Bitmap getFontBmp(Context context, int srcId, int position) {
        Bitmap mBitmap = Bitmap.createBitmap(FONT_BITMAP_WIDTH, FONT_BITMAP_HEIGHT, Config.ARGB_8888);

        Canvas canvas = new Canvas(mBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        String familyName = "";
        Typeface font = Typeface.create(familyName,Typeface.ITALIC);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(font);

        canvas.drawText(context.getResources().getString(srcId), position, 95, paint);

        return mBitmap;
    }
    public void getFontImage(Bitmap bitmap, String fontName) {
        File captureFile = new File("/sdcard/" + fontName + ".png");
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(captureFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}