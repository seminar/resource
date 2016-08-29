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

package com.mstar.tv.tvplayer.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvLanguage;
import com.mstar.tvframework.MstarUIActivity;

public class MainMenu3DActivity extends MstarUIActivity {
    private static final String TAG = "MainMenu3DActivity";

    protected static int currentPageIdx = 0;

    protected static final int totalPageIdx = 7;

    protected boolean mIsKeyShouldReturn = false;

    private static boolean mIsImageLoaded = false;

    // private LinearLayout _3DView = null;
    public static String MENU_EXIT_TYPE = "";

    public static _3DHandler my3DHandler = null;

    final static int LANGUAGE_CHANGE_MSG = 1180;

    public static boolean isFirstCreated = true;

    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LANGUAGE_CHANGE_MSG) {
                Log.d(TAG, "reload picture after language changing!!!!!!!!!!!");
                for (int i = 0; i < totalPageIdx; i++) {
                    String objName = "pageTexture.00" + i;
                    setImage(getPageSkin(i), "", "Scene", objName, 0, 0, 0, 0);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "    _______________________onCreate");
        super.onCreate(savedInstanceState);
        if (Settings.System.getInt(getContentResolver(), "mstar.tv.isFirstCreated", 100) == 1) {
            isFirstCreated = true;
        } else {
            isFirstCreated = false;
        }
        // if(isFirstCreated)
        // return;
        setContentView(R.layout.root);
        if (!_3Dflag) {
            // glView = (GLsurfaceViewActivity) findViewById(R.id.gamekit_view);
            glView.setVisibility(View.GONE);
            return;
        }
        // _3DView = (LinearLayout) findViewById(R.id.gamekit_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        if (width == 1280 && height == 720) {
            configFile = getFilesDir() + "/tv_menu_720p.cfg";
        } else if (width == 1920 && height == 1080) {
            configFile = getFilesDir() + "/tv_menu_1080p.cfg";
        } else {// default set 720p config file
            configFile = getFilesDir() + "/tv_menu_720p.cfg";
        }

        blendFile = getFilesDir() + "/tv_menu.blend";
        if (!fileIsExists(blendFile) || !fileIsExists(configFile)) {
            Log.d(TAG, "loading blender file!!!!!");
            try {
                loadBlenderFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // glView = new GLsurfaceViewActivity(this);
        // glView = (GLsurfaceViewActivity) findViewById(R.id.gamekit_view);
        glView.setVisibility(View.VISIBLE);
        glView.setZOrderOnTop(true);
        // glView.getHolder().setFormat(PixelFormat.TRANSPARENT);//
        // PixelFormat.TRANSLUCENT
        // _3DView.addView(glView);
        glView.requestFocus();
        glView.gameEngine.registerAnimToListen("Scene", "Camera", "CameraAction");
        Isrender = true;
        mIsImageLoaded = false;

        my3DHandler = new _3DHandler();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "    #___________________________onResume");
        super.onResume();
        if (!_3Dflag) {
            return;
        }
        // overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        glView.onResume();
        do3DRecovery();
    }

    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event) {
        if (!_3Dflag) {
            return super.onKeyDown(keyCode, event);
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mIsKeyShouldReturn = true;
                passKeyEvent(KeyEvent.KEYCODE_X);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        _3DMenuHide();
                        mIsKeyShouldReturn = false;
                    }
                }, 800);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_ENTER:
                mIsKeyShouldReturn = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        switchto2D(keyCode);

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mIsKeyShouldReturn = false;
                    }
                }).start();
                passKeyEvent(KeyEvent.KEYCODE_ENTER);
                break;
            // case KeyEvent.KEYCODE_DPAD_LEFT:
            // currentPageIdx = (currentPageIdx - 1 + totalPageIdx) %
            // totalPageIdx;
            // break;
            // case KeyEvent.KEYCODE_DPAD_RIGHT:
            // currentPageIdx = (currentPageIdx + 1) % totalPageIdx;
            // break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // int screenHeight = this.getWindowManager().getDefaultDisplay()
        // .getHeight();
        // int viewHeight = surfaceView.getHeight();
        // Use the difference as the cursor offset
        // setOffsets(0, viewHeight - screenHeight);
        super.onWindowFocusChanged(hasFocus);
    }

    private Bitmap getPageSkin(int curPageIdx) {
        String picPath = "";
        String picName = "";
        Bitmap bitmap = null;
        int systemLanguage = TvCommonManager.getInstance().getOsdLanguage();
        if (systemLanguage == TvLanguage.ENGLISH) {
            picName = String.format("mainmenu_eng_pic_%d", curPageIdx);
        } else if (systemLanguage == TvLanguage.CHINESE) {
            picName = String.format("mainmenu_chn_pic_%d", curPageIdx);
        } else if (systemLanguage == TvLanguage.ACHINESE) {
            picName = String.format("mainmenu_tw_pic_%d", curPageIdx);
        }
        picPath = getFilesDir() + "/" + picName + ".png";
        if (fileIsExists(picPath)) {
            bitmap = BitmapFactory.decodeFile(picPath);
        } else
        // load picture from drawable
        {
            int id = getResources().getIdentifier(picName, "drawable", getPackageName());
            bitmap = drawableToBitmap(getResources().getDrawable(id));
        }
        if (bitmap == null) {
            Log.d(TAG, "~~~~~~~~~~~~~~~~~bitmap is null!!!!!!!!!!");
        }
        // bitmap.prepareToDraw();
        return bitmap;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    protected void _3DMenuShowbyReturn() {
        Log.e(TAG, "============>>>> now _3DMenuShowWithoutReset, mIsInMenuPage = " + mIsInMenuPage);
        if (mIsInMenuPage)
            return;
        MENU_EXIT_TYPE = "Restart";
        Intent pageIntent = new Intent();
        pageIntent
                .setClassName("com.mstar.tv.tvplayer.ui", "com.mstar.tv.tvplayer.ui.RootActivity");
        pageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        getApplicationContext().startActivity(pageIntent);
    }

    protected void _3DMenuShow() {
        Log.e(TAG, "============>>>> now _3DMenuShow");
        if (!mIsImageLoaded) {
            mIsImageLoaded = true;
            Bitmap whiteSide = BitmapFactory.decodeResource(getResources(), R.drawable.white);
            String sideName = "tuning_side.004";
            glView.gameEngine.setImage(whiteSide, "", "Scene", sideName, 0, 0, 0, 0);
            for (int i = 0; i < totalPageIdx; i++) {
                Bitmap bitmap = getPageSkin(i);
                if (bitmap != null) {
                    String objName = "pageTexture.00" + i;
                    glView.gameEngine.setImage(bitmap, "", "Scene", objName, 0, 0, 0, 0);
                }

            }
        }
        mIsKeyShouldReturn = true;
        mIsInMenuPage = true;
        Isrender = true;
        glView.setVisibility(View.VISIBLE);
        glView.requestFocus();
        passKeyEvent(KeyEvent.KEYCODE_SPACE); // do init animation
    }

    protected void _3DMenuHide() {
        Log.e(TAG, "============>>>> now _3DMenuHide");
        mIsInMenuPage = false;
        Isrender = false;
        glView.setVisibility(View.GONE);
        glView.clearFocus();
    }

    private void switchto2D(int keyCode) {
        Intent intent = new Intent(TvIntent.MAINMENU);
        // as the obj name is Page1 to Page7, so we need to do the following
        // function to get the page idx
        currentPageIdx = Integer.parseInt(this.queryCurrentFocusObj("Scene").substring(4)) - 1;
        intent.putExtra("currentPage", currentPageIdx);
        intent.putExtra("currentKeyCode", keyCode);
        Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~~~page index:" + currentPageIdx);
        startActivity(intent);
    }

    protected void do3DRecovery() {
        final String string = MENU_EXIT_TYPE;
        if (string == "")
            return;
        Log.e(TAG, "==============>>>> now do3DRecovery!!!");
        mIsKeyShouldReturn = true;
        if (MainMenuActivity.currentBitmapImg != null) {
            String objName = "pageTexture.00" + currentPageIdx;
            Log.d(TAG, "set Image ON object:" + objName);
            glView.gameEngine.setImage(MainMenuActivity.currentBitmapImg, "", "Scene", objName, 0,
                    0, 0, 0);
        }

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (string.equals("ManualExit")) {
                    Log.e(TAG, "==============>>>> now Manual Exit!!!");
                    passKeyEvent(KeyEvent.KEYCODE_X);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            _3DMenuHide();
                            mIsKeyShouldReturn = false;
                        }
                    }, 800);
                } else if (string.equals("TimeOut")) {
                    Log.e(TAG, "==============>>>> now Time Out!!!");
                    // mIsInMenuPage = true;
                    // passKeyEvent(KeyEvent.KEYCODE_TAB);
                    passKeyEvent(KeyEvent.KEYCODE_X);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            _3DMenuHide();
                            mIsKeyShouldReturn = false;
                        }
                    }, 800);
                } else if (string.equals("PageSwitch")) {
                    Log.e(TAG, "==============>>>> now Page Switch!!!");
                    if (MainMenuActivity.currentKeyEvent != null) {
                        switch (MainMenuActivity.currentKeyEvent.getKeyCode()) {
                            case KeyEvent.KEYCODE_DPAD_LEFT:
                                currentPageIdx = (currentPageIdx - 1 + totalPageIdx) % totalPageIdx;
                                break;
                            case KeyEvent.KEYCODE_DPAD_RIGHT:
                                currentPageIdx = (currentPageIdx + 1) % totalPageIdx;
                                break;
                        }
                        mIsInMenuPage = true;
                        passKeyEvent(MainMenuActivity.currentKeyEvent.getKeyCode());
                        MainMenuActivity.currentKeyEvent = null;
                    }
                    mIsKeyShouldReturn = false;
                } else if (string.equals("Restart")) {
                    Log.e(TAG, "==============>>>> now Restart!!!");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mIsInMenuPage = true;
                            Isrender = true;
                            glView.setVisibility(View.VISIBLE);
                            glView.requestFocus();
                            switchto2D(0);
                            mIsKeyShouldReturn = false;
                        }
                    });
                }
                MENU_EXIT_TYPE = "";
                MainMenuActivity.currentBitmapImg = null;
            }
        }).start();
    }

    private void passKeyEvent(int keycode) {
        KeyEvent ke = new KeyEvent(KeyEvent.ACTION_DOWN, keycode);
        super.onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN, ke);
    }

    private void loadBlenderFile() throws IOException {
        try {
            InputStream in = getResources().openRawResource(R.raw.tv_menu);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            FileOutputStream outputStream = openFileOutput("tv_menu.blend", Activity.MODE_PRIVATE);
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();

            InputStream in_cfg0 = getResources().openRawResource(R.raw.tv_menu_1080p_cfg);
            int lenght_cfg0 = in_cfg0.available();
            byte[] buffer_cfg0 = new byte[lenght_cfg0];
            in_cfg0.read(buffer_cfg0);
            FileOutputStream outputStream_cfg0 = openFileOutput("tv_menu_1080p.cfg",
                    Activity.MODE_PRIVATE);
            outputStream_cfg0.write(buffer_cfg0);
            outputStream_cfg0.flush();
            outputStream_cfg0.close();

            InputStream in_cfg1 = getResources().openRawResource(R.raw.tv_menu_720p_cfg);
            int lenght_cfg1 = in_cfg1.available();
            byte[] buffer_cfg1 = new byte[lenght_cfg1];
            in_cfg1.read(buffer_cfg1);
            FileOutputStream outputStream_cfg1 = openFileOutput("tv_menu_720p.cfg",
                    Activity.MODE_PRIVATE);
            outputStream_cfg1.write(buffer_cfg1);
            outputStream_cfg1.flush();
            outputStream_cfg1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bt = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bt);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bt;
    }

    protected boolean fileIsExists(String Path) {
        File f = new File(Path);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    public void TestQuery(int keyCode, KeyEvent event) {
        Log.v(TAG, "Test onKeyUp begin======");
        if (keyCode == KeyEvent.KEYCODE_1) {
            String temp = queryCurrentScene();
            Log.v(TAG, temp);
        }
        if (keyCode == KeyEvent.KEYCODE_2) {
            String scene = "sceneObj";
            String temp = queryCurrentFocusObj(scene);
            Log.v(TAG, temp);
        }
        if (keyCode == KeyEvent.KEYCODE_3) {
            String scene = "sceneObj";
            String obj = "Obj";
            String temp = queryCurrentFocusApp(scene, obj);
            Log.v(TAG, temp);
        }
    }

    @Override
    public void callBackError(int errorCode, String message) {
        Log.v(TAG, "callBackError " + errorCode + " error msg" + message);
    }

    @Override
    public void callBackSceneChange(String OldSceneName, String NewSceneName) {
        Log.v(TAG, "callBackSceneChange OldSceneName" + OldSceneName + " NewSceneName"
                + NewSceneName);
        handler.post(new Runnable() {
            public void run() {
                _3DMenuHide();
            }
        });
    }

    @Override
    public void callBackBlendFileChange(String OldFileName, String NewFileName) {
        Log.v(TAG, "callBackBlendFileChange OldFileName" + OldFileName + " NewFileName"
                + NewFileName);
    }

    @Override
    public void callBackAxisEvent(int coordType, int btnType, int action, float x, float y) {
        Log.v(TAG, "callBackAxisEvent ");
    }

    @Override
    public void callBackKeyEvent(String sceneName, String objName, int action, int unicodeChar,
            int keyCode) {
        Log.v(TAG, "callBackKeyEvent ");
    }

    @Override
    public void callBackFocusChange(String SceneName, String OldObjName, String NewObjName) {
        Log.v(TAG, "callBackFocusChange ");
    }

    protected class _3DHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case _3DAction.show:
                    _3DMenuShow();
                    break;
                case _3DAction.hide:
                    _3DMenuHide();
                    break;
                case _3DAction.justshow:
                    _3DMenuShowbyReturn();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class _3DAction {
        public final static int show = 0;

        public final static int hide = 1;

        public final static int justshow = 2; // without init animation
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (!_3Dflag) {
            return;
        }
        glView.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "%%%%%%%%%%____________onPause___________");
        super.onPause();
        if (!_3Dflag) {
            return;
        }
        glView.onPause();
        Isrender = false; // stop render
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        if (!_3Dflag) {
            return;
        }
        glView.onStart();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "###-------------------onDestroy");
        // _3DView.removeAllViews();
        super.onDestroy();
        if (!_3Dflag) {
            return;
        }
        glView = null;
    }

    @Override
    public void callBackAnimDone(String sceneName, String objName, String animName) {
        if (sceneName.equals("Scene") && objName.equals("Camera")
                && animName.equals("CameraAction")) {
            mIsKeyShouldReturn = false;
        }
    }
}
