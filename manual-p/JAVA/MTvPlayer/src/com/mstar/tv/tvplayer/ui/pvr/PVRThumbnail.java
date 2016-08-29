//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2014 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tv.tvplayer.ui.pvr;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.mstar.android.tv.TvPvrManager;

public abstract class PVRThumbnail extends Gallery {

    private Context mContext = null;
    private int totalNumber = 0;
    private boolean isShown = false;
    private ThumbnailAdapter adapter = null;
    private ArrayList<ImageView> images = null;
    private TvPvrManager mPvrManager = null;
    private static final String TAG = "PVRThumbnail";

    public PVRThumbnail(Context context) {
        super(context);
        mContext = context;
        mPvrManager = TvPvrManager.getInstance();
        constructImages();
        adapter = new ThumbnailAdapter();
        this.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                onItemClicked(position);
            }
        });
        this.setAdapter(adapter);
    }
    public class ThumbnailAdapter extends BaseAdapter {

        public ThumbnailAdapter() {
        }

        @Override
        public int getCount() {
            return totalNumber;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return images.get(position);
        }
    }

    private void constructImages() {
        images = new ArrayList<ImageView>();
        images.clear();
        int total = totalNumber = 0; // Currently we don't support the PVR Thumbnail function.
        for (int i = 0; i < total; i++) {
            ImageView image = createThumbnailImage(mPvrManager.getThumbnailPath(i),
                    mPvrManager.getThumbnailDisplay(i));
            if (image == null) {
                totalNumber--;
            } else {
                images.add(createThumbnailImage(mPvrManager.getThumbnailPath(i), mPvrManager.getThumbnailDisplay(i)));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private ImageView createThumbnailImage(String path, String info) {
        if (path == null || info == null) {
            return null;
        }
        Button image = new Button(mContext);
        Bitmap bitmap = getBitmapfromPath(path);
        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = dip2px(300);
        int newHeight = dip2px(150);
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        image.setBackgroundDrawable(bmd);
        image.setText(info);
        image.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        image.setShadowLayer(2, 0, 0, Color.rgb(00, 00, 00));
        image.setDrawingCacheEnabled(true);
        image.measure(MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY));
        image.layout(0, 0, image.getMeasuredWidth(), image.getMeasuredHeight());
        image.buildDrawingCache();

        ImageView imageView = new ImageButton(mContext);
        imageView.setImageBitmap(image.getDrawingCache());
        return imageView;
    }

    private Bitmap getBitmapfromPath(String path) {
        byte[] data = null;
        Log.e(TAG, "==============>>>>> getBitmapfromPath = " + path);
        try {
            FileInputStream fin = new FileInputStream(path);
            int length = fin.available();
            Log.e(TAG, "==============>>>>> data length = " + length);
            data = new byte[length];
            fin.read(data);
            fin.close();
        }
        catch (Exception e) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);
        Bitmap bitmap = Bitmap.createBitmap(192, 108, Bitmap.Config.RGB_565);
        bitmap.copyPixelsFromBuffer(buffer);
        buffer.clear();
        return bitmap;
    }

    public boolean addThumbnail(int index) {
        totalNumber = mPvrManager.getThumbnailNumber();
        images.add(index, createThumbnailImage(mPvrManager.getThumbnailPath(index),
                mPvrManager.getThumbnailDisplay(index)));
        adapter.notifyDataSetChanged();
        return true;
    }

    public boolean updateThumbnail() {
        constructImages();
        adapter.notifyDataSetChanged();
        return true;
    }

    public boolean isShown() {
        return isShown;
    }

    public void Show(boolean show) {
        if (show) {
            this.setVisibility(View.VISIBLE);
            requestFocus();
        } else {
            this.setVisibility(View.INVISIBLE);
        }
        isShown = show;
    }

    private Bitmap createTxtImage(String txt, int txtSize) {
        Bitmap mbmpTest = Bitmap.createBitmap(txt.length() * txtSize + 4, txtSize + 4,
                Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(mbmpTest);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        p.setTextSize(txtSize);
        canvasTemp.drawText(txt, 2, txtSize - 2, p);
        return mbmpTest;
    }

    private Bitmap createReflectedImage(Bitmap originalImage) {
        // The gap we want between the reflection and the original image
        final int reflectionGap = 0;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        // This will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        // Create a Bitmap with the flip matrix applied to it.
        // We only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width,
                height / 2, matrix, false);
        // Create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2),
                Config.ARGB_8888);
        // Create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(bitmapWithReflection);
        // Draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        // Draw in the gap
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        // Draw in the reflection
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        // Create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.CLAMP);
        // Set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    private int dip2px(float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    abstract void onItemClicked(int position);
}
