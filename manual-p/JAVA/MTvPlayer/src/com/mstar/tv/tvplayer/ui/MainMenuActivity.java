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

package com.mstar.tv.tvplayer.ui;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.mstar.android.tv.TvS3DManager;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvLanguage;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tvapi.common.vo.CecSetting;
import com.mstar.tv.tvplayer.ui.holder.ChannelViewHolder;
import com.mstar.tv.tvplayer.ui.holder.DemoViewHolder;
import com.mstar.tv.tvplayer.ui.holder.MenuOf3DViewHolder;
import com.mstar.tv.tvplayer.ui.holder.PictureViewHolder;
import com.mstar.tv.tvplayer.ui.holder.SettingViewHolder;
import com.mstar.tv.tvplayer.ui.holder.SoundViewHolder;
import com.mstar.tv.tvplayer.ui.holder.TimeViewHolder;
import com.mstar.tv.tvplayer.ui.holder.OptionViewHolder;
import com.mstar.tv.tvplayer.ui.holder.ParentalControlViewHolder;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;
import com.mstar.util.Tools;

public class MainMenuActivity extends MstarBaseActivity implements OnGestureListener {
    private static final String TAG = "MainMenuActivity";

    private int mTvSystem = 0;

    private GestureDetector detector;

    protected ViewFlipper viewFlipper = null;

    protected LayoutInflater lf;

    protected static boolean hasAdd;

    public static int selectedstatusforChannel = 0x00000000;

    public static int selectedsStatusForDemo = 0x00000000;

    protected int currentPage = PICTURE_PAGE;

    public final static int PICTURE_PAGE = 0;

    public final static int SOUND_PAGE = 1;

    public final static int CHANNEL_PAGE = 2;

    public final static int SETTING_PAGE = 3;

    public final static int TIME_PAGE = 4;

    public final static int DEMO_PAGE = 5;

    public final static int S3D_PAGE = 6;

    public final static int OPTION_PAGE = 7;

    public final static int LOCK_PAGE = 8;

    private int mMaxPageIdx = 7;

    protected PictureViewHolder pictureViewHolder;

    protected SoundViewHolder soundViewHolder;

    protected MenuOf3DViewHolder menuOf3DViewHolder;

    protected TimeViewHolder timeViewHolder;

    protected ChannelViewHolder menuOfChannelViewHolder;

    protected SettingViewHolder menuOfSettingViewHolder;

    protected DemoViewHolder menuOfDemoViewHolder;

    protected OptionViewHolder menuOfOptionViewHolder;

    protected ParentalControlViewHolder menuOfParentalControlViewHolder;

    protected LinearLayout MainMenu_Surface;

    private static boolean NeedSaveBitmap = true;

    public static Bitmap currentBitmapImg = null;

    public static KeyEvent currentKeyEvent = null;

    private static MainMenuActivity mainMenuActivity = null;

    // To remember focus
    private LinearLayout curLinearLayout;

    protected static int[] curFocusedViewIds;

    final static int LANGUAGE_CHANGE_MSG = 1080;

    private MainMenuPauseReceiver mainmenupausereceiver;

    private boolean needRestartMainMenu = false;

    private boolean onCreatFlag = false;

    protected static boolean bMainMenuFocused = false;

    private int mOptionSelectStatus = 0x00000000;

    private int mParentalControlSelectStatus = 0x00000000;

    private boolean mIsPwdCorrect = false;
  //li 20140913 add
    private ImageView mPictureIconView;
    private ImageView mSoundIconView;
    private ImageView mChannelIconView;
    private ImageView mTimeIconView;
    private ImageView mSettingIconView;
    private ImageView mThreedIconView;

    private int mSelectedStatusInSetting = 0x00000000;

    //zb20141008 add
    private final static boolean is3DFlag=false;
    //end
    
    private boolean mIsAnimationEnd = true;
 //li 20140913 add for aging mode
	private final static String GOODKEYCODES = String
	        .valueOf(KeyEvent.KEYCODE_0)
	        + String.valueOf(KeyEvent.KEYCODE_8)
	        + String.valueOf(KeyEvent.KEYCODE_5)
	        + String.valueOf(KeyEvent.KEYCODE_2);
	private ArrayList<Integer> keyQueue;
    public static MainMenuActivity getInstance() {
        return mainMenuActivity;
    }

    AnimationListener mAnimationListener = new Animation.AnimationListener() {
        public void onAnimationStart(Animation animation) {
            mIsAnimationEnd = false;
        }

        public void onAnimationRepeat(Animation animation) {
            mIsAnimationEnd = false;
        }

        public void onAnimationEnd(Animation animation) {
            mIsAnimationEnd = true;
        }
    };

    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (pictureViewHolder != null) {
                // pictureViewHolder.toHandleMsg(msg);
            }
            if (timeViewHolder != null) {
                timeViewHolder.toHandleMsg(msg);
            }
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                setExitTypeResult("TimeOut");
                Intent intent = new Intent(MainMenuActivity.this, RootActivity.class);
                startActivity(intent);
            }
            if (msg.what == LittleDownTimer.SELECT_RETURN_MSG) {
                if (!(getCurrentFocus() instanceof LinearLayout))
                    return;
                curLinearLayout = (LinearLayout) getCurrentFocus();
                if (curLinearLayout != null) {
                    if (selectedstatusforChannel != 0x00000000
                            && mSelectedStatusInSetting != 0x00000000
                            && selectedsStatusForDemo != 0x00000000
                            && mOptionSelectStatus != 0x00000000
                            && mParentalControlSelectStatus != 0x00000000) {
                        selectedstatusforChannel = 0x00000000;
                        mSelectedStatusInSetting = 0x00000000;
                        selectedsStatusForDemo = 0x00000000;
                        mOptionSelectStatus = 0x00000000;
                        mParentalControlSelectStatus = 0x00000000;
                    }
                    curLinearLayout.clearFocus();
                    curLinearLayout.requestFocus();
                    curLinearLayout.setSelected(false);
                }
            }
            if (msg.what == LANGUAGE_CHANGE_MSG) {
                restoreCurFocus();
                // recordCurFocusViewId();
                // saveFocusDataToSys();
                setContentView(R.layout.main_menu);
                viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper_main_menu);
                pictureViewHolder = null;
                soundViewHolder = null;
                menuOf3DViewHolder = null;
                timeViewHolder = null;
                menuOfChannelViewHolder = null;
                menuOfSettingViewHolder = null;
                menuOfDemoViewHolder = null;
                menuOfOptionViewHolder = null;
                menuOfParentalControlViewHolder = null;
                initUIComponent(currentPage);
                LittleDownTimer.resetItem();
                setLanguageItemSelected();
            }
            if (msg.what == 7758521) {
                {
                    if ((getIntent() != null) && (getIntent().getExtras() != null)) {
                        currentPage = getIntent().getIntExtra("currentPage", PICTURE_PAGE);
                        keyInjection(getIntent().getIntExtra("currentKeyCode", 0));
                    }
                    if (onCreatFlag) {
                        addCurrentView(currentPage);
                        initUIComponent(currentPage);
                        selectedstatusforChannel = 0x00000000;
                        mSelectedStatusInSetting = 0x00000000;
                        selectedsStatusForDemo = 0x00000000;
                        mOptionSelectStatus = 0x00000000;
                        mParentalControlSelectStatus = 0x00000000;
                    }
                    LittleDownTimer.resumeMenu();
                    LittleDownTimer.resumeItem();
                    currentBitmapImg = null;
                    currentKeyEvent = null;
                    if (currentPage == TIME_PAGE) {
                        timeViewHolder.loadDataToMyBtnOffTime();
                        timeViewHolder.loadDataToMyBtnScheduledTime();
                    }
                    // freshUI();
                }
                if (onCreatFlag) {
                    // new timer service
                    loadFocusDataFromSys();
                    LittleDownTimer.setHandler(handler);
                    mainmenupausereceiver = new MainMenuPauseReceiver();
                    IntentFilter filter1 = new IntentFilter();
                    filter1.addAction("mstar.tvsetting.ui.pausemainmenu");
                    registerReceiver(mainmenupausereceiver, filter1);
                    // if (getSharedPreferences("TvSetting",
                    // 0).getBoolean("_3Dflag", false)) { // get
                    // 3D
                    // open
                    // flag
                    // MainMenu_Surface.setBackgroundDrawable(getResources().getDrawable(
                    // R.drawable.picture_mode_img_3d_bg));
                    // RootActivity.my3DHandler.sendEmptyMessage(RootActivity._3DAction.justshow);
                    // }
                    onCreatFlag = false;
                }
            }
        };
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,
                "--------------> startActivity->MainMenuActivity begin "
                        + System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        //zb20141008 modify
        if(is3DFlag)
        	setContentView(R.layout.main_menu3d);
        else
        	setContentView(R.layout.main_menu);
        //end
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            mMaxPageIdx = 7; // Only 7 Pages for ATSC System
        } else {
            mMaxPageIdx = 8; // Additional Page for Parental Control
        }
        curFocusedViewIds = new int[mMaxPageIdx + 1];
        detector = new GestureDetector(this);
        mainMenuActivity = this;
        onCreatFlag = true;
        hasAdd = false;
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper_main_menu);
        MainMenu_Surface = (LinearLayout) findViewById(R.id.MainMenu);
        Log.i(TAG, "========== Display TV Menu ==========");
        Log.i(TAG, "========== Display TV Menu ========== " + System.currentTimeMillis());

        // box platform
        if (Tools.isBox()) {
            // make sure the default antenna type is DVB-C for box
            new ConfigAntennaTypeAsyncTask().execute();
        }
        if (TvParentalControlManager.getInstance().isSystemLock()) {
            SharedPreferences share2 = getSharedPreferences("menu_check_pwd", Activity.MODE_PRIVATE);
            Editor editor = share2.edit();
            editor.putBoolean("pwd_ok", true);
            editor.commit();
        }
    	// li 20140913 add
		mPictureIconView=(ImageView)findViewById(R.id.picture_icon);
		mSoundIconView=(ImageView)findViewById(R.id.sound_icon);
		mChannelIconView=(ImageView)findViewById(R.id.channel_icon);
		mSettingIconView=(ImageView)findViewById(R.id.setting_icon);
		mTimeIconView=(ImageView)findViewById(R.id.time_icon);
		mThreedIconView=(ImageView)findViewById(R.id.threed_icon);
		// li 20140913 add for touch screen
		mPictureIconView.setOnClickListener(titleIconClick);
		mSoundIconView.setOnClickListener(titleIconClick);
		mChannelIconView.setOnClickListener(titleIconClick);
		mSettingIconView.setOnClickListener(titleIconClick);
		mTimeIconView.setOnClickListener(titleIconClick);
		mThreedIconView.setOnClickListener(titleIconClick);
        keyQueue = new ArrayList<Integer>();
    }
    //li 20140913 add for touch screen
    public void LoadAnimation()
    {
    	this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.left_in));
        this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.left_out));
    }
    private OnClickListener titleIconClick= new OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				restoreCurFocus();
                if (!hasAdd) {
                    addOtherView();
                }
                LoadAnimation();
                recordCurFocusViewId();
				switch(arg0.getId())
				{
				case R.id.picture_icon:
					currentPage=0;
					break;
				case R.id.sound_icon:
					currentPage=1;
					break;
				case R.id.channel_icon:
					currentPage=2;
					break;
				case R.id.setting_icon:
					currentPage=3;
					break;
				case R.id.time_icon:
					currentPage=4;
					break;
				case R.id.threed_icon:
					currentPage=5;
					break;
				}
				initUIComponent(currentPage);
			}
		};
    @Override
    protected void onResume() {
        super.onResume();

        if (TvCecManager.getInstance().getCecConfiguration().cecStatus == Constant.CEC_STATUS_ON) {
            TvCecManager.getInstance().disableDeviceMenu();
        }
        // Charles
        if (RootActivity.mExitDialog != null) {
            if (RootActivity.mExitDialog.isShowing()) {
                RootActivity.mExitDialog.dismiss();
            }
        }
        bMainMenuFocused = true;

        lf = LayoutInflater.from(MainMenuActivity.this);
        SharedPreferences share = getSharedPreferences("menu_check_pwd", Activity.MODE_PRIVATE);
        mIsPwdCorrect = share.getBoolean("pwd_ok", false);
        handler.sendEmptyMessage(7758521);
    }

    protected void addCurrentView(int pageId) {
        if (viewFlipper.getChildCount() > mMaxPageIdx) {
            return;
        }
        viewFlipper.removeAllViews();
        for (int i = 0; i <= pageId; i++) {
            addView(i);
        }
    }

    protected void addView(int id) {
        switch (id) {
            case 0:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.picture, null), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.sound, null), 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.channel, null), 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    View viewSetting = lf.inflate(R.layout.setting, null);
                    TvCommonManager tvCommonManager = TvCommonManager.getInstance();
                    LinearLayout linearlayoutSetting = (LinearLayout) viewSetting
                            .findViewById(R.id.linearlayout_setting);
                    if (!tvCommonManager.isSupportModule(TvCommonManager.MODULE_PREVIEW_MODE)) {
                        LinearLayout linearlayoutSetSourcepreview = (LinearLayout) viewSetting
                                .findViewById(R.id.linearlayout_set_sourcepreview);
                        linearlayoutSetting.removeView(linearlayoutSetSourcepreview);
                    }
                    if (!tvCommonManager.isSupportModule(TvCommonManager.MODULE_OFFLINE_DETECT)) {
                        LinearLayout linearlayoutSetAutosourceident = (LinearLayout) viewSetting
                                .findViewById(R.id.linearlayout_set_autosourceident);
                        linearlayoutSetting.removeView(linearlayoutSetAutosourceident);
                        LinearLayout linearlayoutSetAutosourceswit = (LinearLayout) viewSetting
                                .findViewById(R.id.linearlayout_set_autosourceswit);
                        linearlayoutSetting.removeView(linearlayoutSetAutosourceswit);
                    }
                    viewFlipper.addView(viewSetting, 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.time, null), 4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.demo, null), 5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.menu_of_3d, null), 6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.option, null), 7);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                try {
                    viewFlipper.addView(lf.inflate(R.layout.menu_parentalcontrol, null), 8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    protected void addOtherView() {
        hasAdd = true;
        if (viewFlipper.getChildCount() >= mMaxPageIdx) {
            return;
        }

        int i = 0;
        if (viewFlipper.getChildCount() < (currentPage + 1)) {
            i = viewFlipper.getChildCount();
            for (; i < (currentPage + 1); i++) {
                addView(i);
            }
        }
        for (i = currentPage; i < mMaxPageIdx; i++) {
            addView(i + 1);
        }
    }

    private void freshUI() {
        if (pictureViewHolder != null) {
            pictureViewHolder.LoadDataToUI();
        }
        if (soundViewHolder != null) {
            soundViewHolder.LoadDataToUI();
        }
        if (menuOf3DViewHolder != null) {
            menuOf3DViewHolder.LoadDataToUI();
        }
        if (timeViewHolder != null) {
            timeViewHolder.loadDataToUI();
        }
        if (menuOfChannelViewHolder != null) {
            menuOfChannelViewHolder.findViews();
        }
        if (menuOfSettingViewHolder != null) {
            menuOfSettingViewHolder.findViews();
        }
        if (menuOfDemoViewHolder != null) {
            menuOfDemoViewHolder.findViews();
        }
        if (menuOfOptionViewHolder != null) {
            menuOfOptionViewHolder.findViews();
        }
        if (menuOfParentalControlViewHolder != null) {
            menuOfParentalControlViewHolder.findViews();
        }
    }

    @Override
    protected void onPause() {
        try {
            LittleDownTimer.pauseMenu();
            LittleDownTimer.pauseItem();
            bMainMenuFocused = false;
            SharedPreferences settings = this.getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
            boolean flag = settings.getBoolean("_3Dflag", false);
            if (flag) {
                boolean drawDone = false;
                Canvas myCanvas = getBitmapCanvas();
                if (myCanvas != null) {
                    MainMenu_Surface.draw(myCanvas);
                    drawDone = true;
                }
                if (NeedSaveBitmap && drawDone) {
                    try {
                        String picName = "";
                        int systemLanguage = TvCommonManager.getInstance().getOsdLanguage();
                        if (systemLanguage == TvLanguage.ENGLISH) {
                            picName = String.format("mainmenu_eng_pic_%d", currentPage);
                        } else if (systemLanguage == TvLanguage.CHINESE) {
                            picName = String.format("mainmenu_chn_pic_%d", currentPage);
                        } else if (systemLanguage == TvLanguage.ACHINESE) {
                            picName = String.format("mainmenu_tw_pic_%d", currentPage);
                        }
                        saveToBitmap(currentBitmapImg, picName);
                        Log.v(TAG, "drawBitmap saveToBitmap");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            recordCurFocusViewId();
            saveFocusDataToSys();
        } catch (Exception e) {
        }
        SharedPreferences share2 = getSharedPreferences("menu_check_pwd", Activity.MODE_PRIVATE);
        Editor editor = share2.edit();
        editor.putBoolean("pwd_ok", false);
        editor.commit();
        // selectedparental = 0x00000000;
        if (TvCecManager.getInstance().getCecConfiguration().cecStatus == Constant.CEC_STATUS_ON) {
            TvCecManager.getInstance().enableDeviceMenu();
        }
        super.onPause();
    }

    @Override
    public void onUserInteraction() {
        LittleDownTimer.resetMenu();
        LittleDownTimer.resetItem();
        super.onUserInteraction();
    }

    private void keyInjection(final int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP
                || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                ||keyCode == KeyEvent.KEYCODE_ENTER) {
            new Thread() {
                public void run() {
                    try {
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(keyCode);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }.start();
        }
    }

    private boolean isSourceInTv() {
        int curis = TvCommonManager.getInstance().getCurrentTvInputSource();
        if (curis == TvCommonManager.INPUT_SOURCE_ATV || curis == TvCommonManager.INPUT_SOURCE_DTV) {
            return true;
        } else {
            return false;
        }
    }
//li 20140913 add
	private String intArrayListToString(ArrayList<Integer> al)
	{
		String str = "";
		for (int i = 0; i < al.size(); ++i)
		{
			str += al.get(i).toString();
		}
		return str;
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag;
        SharedPreferences settings;

        // If MapKeyPadToIR returns true,the previous keycode has been changed
        // to responding
        // android d_pad keys,just return.
        if (MapKeyPadToIR(keyCode, event))
            return true;
        
        // lyp 2014.11.08 解决cec_key 和ad key 无法同时有效的问题
        sendCecKey(keyCode);

        if (selectedstatusforChannel == 0x00000000 && mSelectedStatusInSetting == 0x00000000
                && selectedsStatusForDemo == 0x00000000 && mOptionSelectStatus == 0x00000000
                && mParentalControlSelectStatus == 0x00000000) {
            Intent intent;
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                case KeyEvent.KEYCODE_MENU:// lyp 2014.10.10 add for exit Menu
                    setExitTypeResult("ManualExit");
                    Intent intentRoot = new Intent(this, RootActivity.class);
                    startActivity(intentRoot);
                    break;
                case KeyEvent.KEYCODE_TV_INPUT:
                    needRestartMainMenu = true;
                    Intent intentSource = new Intent(
                            "com.mstar.tvsetting.switchinputsource.intent.action.PictrueChangeActivity");
                    startActivity(intentSource);
                    return true;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (mIsAnimationEnd == false) {
                        break;
                    }
                    if (!hasAdd) {
                        addOtherView();
                    }
                    Animation leftAnimationFadeIn;
                    leftAnimationFadeIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
                    this.viewFlipper.setInAnimation(leftAnimationFadeIn);
                    leftAnimationFadeIn.setAnimationListener(mAnimationListener);
                    this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.right_out));
                    recordCurFocusViewId();
                    settings = this.getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                    flag = settings.getBoolean("_3Dflag", false);
                    if (flag) {
                        currentKeyEvent = event;
                        setExitTypeResult("PageSwitch");
                        intent = new Intent(this, RootActivity.class);
                        startActivity(intent);
                        // finish();
                        // overridePendingTransition(R.anim.zoomin,
                        // R.anim.zoomout);
                    } else {
                        if (currentPage == 0) {
                        	//zb20141008 modify
                        	if(is3DFlag)
                        		currentPage = mMaxPageIdx-2;
                        	else {
                        		currentPage = mMaxPageIdx-4;
							}
                        	//end
                        } else {
                            currentPage--;
                            if (currentPage == CHANNEL_PAGE) {
                                if (!isSourceInTv()) {
                                    currentPage--;
                                }
                            }
                         //li 20140913 add to del demo page
                            if(currentPage == DEMO_PAGE)
                            	currentPage--;
						    if(currentPage == OPTION_PAGE)
                            	currentPage--;
						    //zb20141008 modify
						    if(!is3DFlag)
						    {
						    	if(currentPage == S3D_PAGE)
	                            	currentPage--;
						    }
						    //end
//							if(currentPage == LOCK_PAGE)
//                            	currentPage--;
                        }
                        initUIComponent(currentPage);
                    }
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if (mIsAnimationEnd == false) {
                        break;
                    }

                    if (!hasAdd) {
                        addOtherView();
                    }
                    Animation rightAnimationFadeIn = AnimationUtils.loadAnimation(this,
                            R.anim.left_in);
                    this.viewFlipper.setInAnimation(rightAnimationFadeIn);
                    rightAnimationFadeIn.setAnimationListener(mAnimationListener);
                    this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.left_out));
                    recordCurFocusViewId();
                    settings = this.getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                    flag = settings.getBoolean("_3Dflag", false);
                    if (flag) {
                        currentKeyEvent = event;
                        setExitTypeResult("PageSwitch");
                        intent = new Intent(this, RootActivity.class);
                        startActivity(intent);
                        // finish();
                        // overridePendingTransition(R.anim.zoomin,
                        // R.anim.zoomout);
                    } else {
                    	//zb20141008 modify
                    	if(is3DFlag)
                    	{
	                        if (currentPage == mMaxPageIdx-2) {
	                            currentPage = 0;
	                        } else {
	                            currentPage++;
	                            if (currentPage == CHANNEL_PAGE) {
	                                if (!isSourceInTv()) {
	                                    currentPage++;
	                                }
	                            }
	                           //li 20140913 add to del demo page
	                            if(currentPage == DEMO_PAGE)
	                            	currentPage++;
							    if(currentPage == OPTION_PAGE)
	                            	currentPage++;
	//							if(currentPage == LOCK_PAGE)
	//                            	currentPage++;
	                        }
                    	}
                    	else {
                    		if (currentPage == mMaxPageIdx-4) {
	                            currentPage = 0;
	                        } else {
	                            currentPage++;
	                            if (currentPage == CHANNEL_PAGE) {
	                                if (!isSourceInTv()) {
	                                    currentPage++;
	                                }
	                            }
	                           //li 20140913 add to del demo page
	                            if(currentPage == DEMO_PAGE)
	                            	currentPage++;
							    if(currentPage == OPTION_PAGE)
	                            	currentPage++;
							    if(!is3DFlag)
							    {
							    	if(currentPage == S3D_PAGE)
		                            	currentPage++;
							    }
						  }
                    	}
                    	//end
                        initUIComponent(currentPage);
                    }
                    return true;
                case KeyEvent.KEYCODE_M:
                    settings = this.getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                    flag = settings.getBoolean("_3Dflag", false);
                    if (flag) {
                        intent = new Intent(this, MainMenu3DActivity.class);
                        startActivity(intent);
                        // finish();
                        // overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                    }
                    return true;
            }
        }
        //li 20140913 add
        keyQueue.add(keyCode);
		if (keyQueue.size() == 4)
		{
			String keystr = intArrayListToString(keyQueue);
			if (keystr.equals(GOODKEYCODES))
			{
				keyQueue.clear();
				Intent intent = new Intent(
				        "mstar.tvsetting.factory.AginModeActivity");
				startActivity(intent);
				finish();
				return true;
			}
			else
			{
				keyQueue.remove(0);
            }
        }
        if (currentPage == CHANNEL_PAGE) {
            menuOfChannelViewHolder.onKeyDown(keyCode, event);
        }
        if (currentPage == SETTING_PAGE) {
            menuOfSettingViewHolder.onKeyDown(keyCode, event);
        }
        if (currentPage == DEMO_PAGE) {
            menuOfDemoViewHolder.onKeyDown(keyCode, event);
        }
        if (currentPage == OPTION_PAGE) {
            menuOfOptionViewHolder.onKeyDown(keyCode, event);
        }
        if (currentPage == LOCK_PAGE) {
            menuOfParentalControlViewHolder.onKeyDown(keyCode, event);
        }

        // /focus loop
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            View view = getCurrentFocus();
            if (view != null) {
                if (view.getId() == R.id.linearlayout_time_offtime) {
                    if (findViewById(R.id.linearlayout_time_sleep) != null) {
                        findViewById(R.id.linearlayout_time_sleep).requestFocus();
                    }
                    return true;
                }
                if (findViewById(R.id.linearlayout_cha_antennatype) != null
                        && findViewById(R.id.linearlayout_cha_antennatype).isFocusable()) {
                    if (view.getId() == R.id.linearlayout_cha_antennatype) {
                        if (findViewById(R.id.linearlayout_cha_oad_view_prompt) != null) {
                            findViewById(R.id.linearlayout_cha_oad_view_prompt).requestFocus();
                        }
                        return true;
                    }
                } else if (view.getId() == R.id.linearlayout_cha_autotuning) {
                    if (findViewById(R.id.linearlayout_cha_programedit) != null) {
                        findViewById(R.id.linearlayout_cha_programedit).requestFocus();
                    }
                    return true;
                }
                if (view.getId() == R.id.linearlayout_demo_mwe) {
                    if (findViewById(R.id.linearlayout_demo_uclear) != null) {
                        findViewById(R.id.linearlayout_demo_uclear).requestFocus();
                    }
                    return true;
                }
                if (view.getId() == R.id.linearlayout_3d_self_adaptive_detecttriple) {
                    if (findViewById(R.id.linearlayout_3d_lrswitch) != null) {
                        findViewById(R.id.linearlayout_3d_lrswitch).requestFocus();
                    }
                    return true;
                }
                if (view.getId() == R.id.linearlayout_set_menutime) {
                    if (findViewById(R.id.linearlayout_set_restoretodefault) != null) {
                        findViewById(R.id.linearlayout_set_restoretodefault).requestFocus();
                    }
                    return true;

                }
            }
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            View view = getCurrentFocus();
            if (view != null) {
                if (view.getId() == R.id.linearlayout_time_sleep) {
                    if (findViewById(R.id.linearlayout_time_offtime) != null) {
                        findViewById(R.id.linearlayout_time_offtime).requestFocus();
                    }
                    return true;
                }
                if ((findViewById(R.id.linearlayout_cha_oad_view_prompt) != null)
                        && (findViewById(R.id.linearlayout_cha_oad_view_prompt).isFocusable())) {
                    if (view.getId() == R.id.linearlayout_cha_oad_view_prompt) {
                        if (findViewById(R.id.linearlayout_cha_antennatype) != null) {
                            findViewById(R.id.linearlayout_cha_antennatype).requestFocus();
                        }
                        return true;
                    }
                } else if (view.getId() == R.id.linearlayout_cha_programedit) {
                    if (findViewById(R.id.linearlayout_cha_autotuning) != null) {
                        findViewById(R.id.linearlayout_cha_autotuning).requestFocus();
                    }
                    return true;
                }
                if (view.getId() == R.id.linearlayout_demo_uclear) {
                    if (findViewById(R.id.linearlayout_demo_mwe) != null) {
                        findViewById(R.id.linearlayout_demo_mwe).requestFocus();
                    }
                    return true;
                }
                if (view.getId() == R.id.linearlayout_3d_lrswitch) {
                    if (findViewById(R.id.linearlayout_3d_self_adaptive_detecttriple) != null) {
                        findViewById(R.id.linearlayout_3d_self_adaptive_detecttriple)
                                .requestFocus();
                    }
                    return true;
                }
                if (view.getId() == R.id.linearlayout_set_restoretodefault) {
                    if (findViewById(R.id.linearlayout_set_menutime) != null) {
                        findViewById(R.id.linearlayout_set_menutime).requestFocus();
                    }
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // ignore any key up event from MStar Smart TV Keypad
        String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
        if (deviceName.equals("MStar Smart TV Keypad")) {
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    public boolean MapKeyPadToIR(int keyCode, KeyEvent event) {
        String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
        Log.d(TAG, "deviceName" + deviceName);
        
        // lyp 2014.10.11 add
        if(KeyEvent.KEYCODE_TV_INPUT == keyCode){
        	keyInjection(KeyEvent.KEYCODE_ENTER);
            return true;
        }
        
        if (!deviceName.equals("MStar Smart TV Keypad"))
            return false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_CHANNEL_UP:
                keyInjection(KeyEvent.KEYCODE_DPAD_UP);
                return true;
            case KeyEvent.KEYCODE_CHANNEL_DOWN:
                keyInjection(KeyEvent.KEYCODE_DPAD_DOWN);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                keyInjection(KeyEvent.KEYCODE_DPAD_RIGHT);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                keyInjection(KeyEvent.KEYCODE_DPAD_LEFT);
                return true;
            default:
                return false;
        }

    }

    private void setExitTypeResult(String result) {
        // RootActivity.MENU_EXIT_TYPE = result;
    }
    //li 20140913 add
    private void disableAllIcon()
    {
    	mPictureIconView.setBackgroundResource(R.drawable.picture_icon_disable);
    	mSoundIconView.setBackgroundResource(R.drawable.sound_icon_disable);
    	mChannelIconView.setBackgroundResource(R.drawable.channel_icon_disable);
    	mSettingIconView.setBackgroundResource(R.drawable.setting_icon_disable);
    	mTimeIconView.setBackgroundResource(R.drawable.time_icon_disable);
    	mThreedIconView.setBackgroundResource(R.drawable.threed_icon_disable);
    }
    protected void initUIComponent(int page) {
        viewFlipper.setDisplayedChild(page);
        switch (page) {
            case PICTURE_PAGE:
            	//li 20140913 add
            	disableAllIcon();
            	mPictureIconView.setBackgroundResource(R.drawable.picture_icon_focus);
                if (pictureViewHolder == null) {
                    pictureViewHolder = new PictureViewHolder(MainMenuActivity.this);
                    pictureViewHolder.findViews();
                    pictureViewHolder.LoadDataToUI();
                }
                break;
            case SOUND_PAGE:
            	//li 20140913 add
            	disableAllIcon();
            	mSoundIconView.setBackgroundResource(R.drawable.sound_icon_focus);
                if (soundViewHolder == null) {
                    soundViewHolder = new SoundViewHolder(MainMenuActivity.this);
                    soundViewHolder.findViews();
                    soundViewHolder.LoadDataToUI();
                }
              //add by wxy
                soundViewHolder.SetFocusableOrNotForARCMode();
                break;
            case S3D_PAGE:
            	disableAllIcon();
            	mThreedIconView.setBackgroundResource(R.drawable.threed_icon_focus);
                if (menuOf3DViewHolder == null) {
                    menuOf3DViewHolder = new MenuOf3DViewHolder(MainMenuActivity.this);
                    menuOf3DViewHolder.findViews();
                    menuOf3DViewHolder.LoadDataToUI();
                }
                menuOf3DViewHolder.updateUI();// for some cases support no 3d
                                              // function
                break;
            case TIME_PAGE:
            	//li 20140913 add
            	disableAllIcon();
            	mTimeIconView.setBackgroundResource(R.drawable.time_icon_focus);
                if (timeViewHolder == null) {
                    timeViewHolder = new TimeViewHolder(MainMenuActivity.this, handler);
                    timeViewHolder.findViews();
                    timeViewHolder.loadDataToUI();
                }
                timeViewHolder.loadDataToMyBtnOffTime();
                timeViewHolder.loadDataToMyBtnScheduledTime();
                break;
            case CHANNEL_PAGE:
            	//li 20140913 add
            	disableAllIcon();
            	mChannelIconView.setBackgroundResource(R.drawable.channel_icon_focus);
                menuOfChannelViewHolder = new ChannelViewHolder(MainMenuActivity.this);
                menuOfChannelViewHolder.findViews();
                break;
            case SETTING_PAGE:
            	//li 20140913 add
            	disableAllIcon();
            	mSettingIconView.setBackgroundResource(R.drawable.setting_icon_focus);
                if (menuOfSettingViewHolder == null) {
                    menuOfSettingViewHolder = new SettingViewHolder(MainMenuActivity.this, handler);
                    menuOfSettingViewHolder.findViews();
                }
                break;
            case DEMO_PAGE:
                if (menuOfDemoViewHolder == null) {
                    menuOfDemoViewHolder = new DemoViewHolder(MainMenuActivity.this);
                    menuOfDemoViewHolder.findViews();
                }
                break;
            case OPTION_PAGE:
                if (menuOfOptionViewHolder == null) {
                    menuOfOptionViewHolder = new OptionViewHolder(MainMenuActivity.this);
                    menuOfOptionViewHolder.findViews();
                    menuOfOptionViewHolder.loadDataToUi();
                }
                break;
            case LOCK_PAGE:
                if (menuOfParentalControlViewHolder == null) {
                    menuOfParentalControlViewHolder = new ParentalControlViewHolder(
                            MainMenuActivity.this);
                    menuOfParentalControlViewHolder.findViews();
                }
                break;
        }
        initCurFocus();
    }

    @Override
    public void onDestroy() {
        if (timeViewHolder != null) {
            timeViewHolder.endUIClock();
        }
        unregisterReceiver(mainmenupausereceiver);
        // if (this.getSharedPreferences("TvSetting", 0).getBoolean("_3Dflag",
        // false)
        // && RootActivity.MENU_EXIT_TYPE.equals(""))
        // RootActivity.my3DHandler.sendEmptyMessageDelayed(RootActivity._3DAction.hide,
        // 300);
        if (needRestartMainMenu) {
            setExitTypeResult("Restart");
            needRestartMainMenu = false;
        }
        super.onDestroy();
    }

    private void saveToBitmap(Bitmap bitmap, String bitName) throws IOException {
        FileOutputStream fOut = openFileOutput(bitName + ".png", Activity.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Canvas getBitmapCanvas() {
        final int cl = MainMenu_Surface.getLeft();
        final int ct = MainMenu_Surface.getTop();
        final int cr = MainMenu_Surface.getRight();
        final int cb = MainMenu_Surface.getBottom();
        if (currentBitmapImg == null) {
            final int width = cr - cl;
            final int height = cb - ct;
            if (width <= 0 || height <= 0) {
                return null;
            }
            try {
                currentBitmapImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError e) {
                return null;
            }
        }
        Canvas myCanvas = new Canvas(currentBitmapImg);
        return myCanvas;
    }

    private void recordCurFocusViewId() {
        View view = getCurrentFocus();
        if (view != null) {
            curFocusedViewIds[currentPage] = view.getId();
        }
    }

    private void saveFocusDataToSys() {
        SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE).edit();
        int i = 0;
        for (int id : curFocusedViewIds) {
            editor.putInt("page" + i, id);
            editor.commit();
            i++;
        }
    }

    private void initCurFocus() {
        if (curFocusedViewIds[currentPage] != 0) {
            View view = findViewById(curFocusedViewIds[currentPage]);
            if (view != null) {
                view.requestFocus();
            }
        }
    }

    private void restoreCurFocus() {
        curFocusedViewIds[PICTURE_PAGE] = R.id.linearlayout_pic_picturemode;
        curFocusedViewIds[SOUND_PAGE] = R.id.linearlayout_sound_soundmode;
        curFocusedViewIds[CHANNEL_PAGE] = R.id.linearlayout_cha_antennatype;
        curFocusedViewIds[SETTING_PAGE] = R.id.linearlayout_set_language;
        curFocusedViewIds[TIME_PAGE] = R.id.linearlayout_time_offtime;
        curFocusedViewIds[DEMO_PAGE] = R.id.linearlayout_demo_mwe;
        curFocusedViewIds[S3D_PAGE] = R.id.linearlayout_3d_3dto2d;
        curFocusedViewIds[OPTION_PAGE] = R.id.linearlayout_set_caption;
        if (TvCommonManager.TV_SYSTEM_ATSC != mTvSystem) {
            curFocusedViewIds[LOCK_PAGE] = R.id.linearlayout_lock_system;
        }
    }

    private void loadFocusDataFromSys() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
        for (int i = 0; i < curFocusedViewIds.length; i++) {
            curFocusedViewIds[i] = sharedPreferences.getInt("page" + i, 0);
        }
    }

    private void setLanguageItemSelected() {
        if (currentPage != SETTING_PAGE)
            return;
        Log.d(TAG, "setLanguageItemSelected");
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout_set_language);
        mSelectedStatusInSetting = 0x00000001;
        menuOfSettingViewHolder.setFocusId(R.id.linearlayout_set_language);
        ImageView iv1 = (ImageView) ll.getChildAt(0);
        ImageView iv2 = (ImageView) ll.getChildAt(3);
        iv1.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() < -100) {
        	//li 20140913 add for touchscreen
        	restoreCurFocus();
            if (!hasAdd) {
                addOtherView();
            }
            recordCurFocusViewId();
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
            // this.viewFlipper.showPrevious();
            if (currentPage == 0) {
            	if(is3DFlag)
            		currentPage = mMaxPageIdx-2;
            	else {
            		currentPage = mMaxPageIdx-4;
				}
            } else {
                currentPage--;
                if (currentPage == CHANNEL_PAGE) {
                    if (!isSourceInTv()) {
                        currentPage--;
                    }
                }
            }
          //li 20140913add for touchscreen
            if(currentPage == DEMO_PAGE ||currentPage == OPTION_PAGE )
            	currentPage--;
            //zb20141008 add
            if(!is3DFlag)
            {
            	if(currentPage == S3D_PAGE)
            		currentPage--;
            }
            //end
            initUIComponent(currentPage);
            return true;
        } else if (e1.getX() - e2.getX() > 100) {
        	//li 20140913 add for touchscreen
        	restoreCurFocus();
            if (!hasAdd) {
                addOtherView();
            }
            recordCurFocusViewId();
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
            // this.viewFlipper.showNext();
            //zb20141008 add
            if(is3DFlag)
            {
            	if (currentPage == mMaxPageIdx-2) {
                    currentPage = 0;
                } else {
                    currentPage++;
                    if (currentPage == CHANNEL_PAGE) {
                        if (!isSourceInTv()) {
                            currentPage++;
                        }
                    }
                }
            }
            else
            {
            	if (currentPage == mMaxPageIdx-4) {
                    currentPage = 0;
                } else {
                    currentPage++;
                    if (currentPage == CHANNEL_PAGE) {
                        if (!isSourceInTv()) {
                            currentPage++;
                        }
                    }
                }	
            }
            //end
  //li 20140913add for touchscreen
            if(currentPage == DEMO_PAGE ||currentPage == OPTION_PAGE )
            	currentPage++;
            //zb20141008 add
            if(!is3DFlag)
            {
            	if(currentPage == S3D_PAGE)
            		currentPage++;
            }
            //end
            initUIComponent(currentPage);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    public boolean isPwdCorrect() {
        return mIsPwdCorrect;
    }

    public int getOptionSelectStatus() {
        return mOptionSelectStatus;
    }

    public void setOptionSelectStatus(int status) {
        mOptionSelectStatus = status;
    }

    public int getParentalControlSelectStatus() {
        return mParentalControlSelectStatus;
    }

    public void setParentalControlSelectStatus(int status) {
        mParentalControlSelectStatus = status;
    }

    public int getSettingSelectStatus() {
        return mSelectedStatusInSetting;
    }

    public void setSettingSelectStatus(int status) {
        mSelectedStatusInSetting = status;
    }

    private class MainMenuPauseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // mute
            if (action.equals("mstar.tvsetting.ui.pausemainmenu")) {
                Log.i(TAG, "--------------recieved-------------");
                // finish();
                return;
                // from.finish();
            }
        }
    }

    // for box only
    private class ConfigAntennaTypeAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int currentRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
            int dvbcRouteIndex = TvChannelManager.getInstance().getSpecificDtvRouteIndex(
                    TvChannelManager.TV_ROUTE_DVBC);
            Log.d(TAG, "ConfigAntennaTypeAsyncTask, antenna " + currentRouteIndex);
            if (currentRouteIndex != dvbcRouteIndex) {
                TvS3DManager.getInstance().setDisplayFormatForUI(
                        TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                TvChannelManager.getInstance().switchMSrvDtvRouteCmd(dvbcRouteIndex);
            }

            return null;
        }
    }
    
    

    // lyp 2014.11.08
    private boolean sendCecKey(int keyCode) {
    	 int mCecStatusOn = 1;

         CecSetting setting = TvCecManager.getInstance().getCecConfiguration();
         int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
         if (setting.cecStatus == mCecStatusOn) {
            if (curInputSource == TvCommonManager.INPUT_SOURCE_HDMI ||
                curInputSource == TvCommonManager.INPUT_SOURCE_HDMI2 ||
                curInputSource == TvCommonManager.INPUT_SOURCE_HDMI3 ||
                curInputSource == TvCommonManager.INPUT_SOURCE_HDMI4 ) {
                if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                    if (TvCecManager.getInstance().sendCecKey(keyCode)) {
                        Log.d(TAG, "send Cec key,keyCode is " + keyCode + ", tv don't handl the key");
                        return true;
                    }
                }
             } else if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV
                     || curInputSource == TvCommonManager.INPUT_SOURCE_ATV
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS2
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS3
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS4
                     || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR
                     || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR2
                     || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR3) {
                 if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
                         || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                         || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
                     if (TvCecManager.getInstance().sendCecKey(keyCode)) {
                         Log.d(TAG, "send Cec key,keyCode is " + keyCode
                                 + ", tv don't handl the key");
                         return true;
                     }
                 }
             }
         }
       return false;
    }

}
