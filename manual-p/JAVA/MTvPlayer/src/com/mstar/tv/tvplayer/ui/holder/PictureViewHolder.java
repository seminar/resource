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

package com.mstar.tv.tvplayer.ui.holder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvManager;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tv.tvplayer.ui.settings.PCImageModeDialogActivity;

public class PictureViewHolder {
	//edit rollin 20130710 for new OSD
	protected String[] saturation = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
	    		"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
	    	    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
	    	    "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
	    	    "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
	    	    "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", 
	    	    "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", 
	    	    "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", 
	    	    "93", "94", "95", "96", "97", "98", "99", "100" };
    private static final int INVALID_PICTURE_MODE = 111;

    private static final int VALID_PICTURE_MODE = 222;

    private static final int INIT_ZOOM_MODE = 333;

    private static final int INIT_ZOOM_GAME_MODE = 444;

    private static final int INIT_ZOOM_PC_DotByDot_MODE = 555;

    private static final int INIT_ZOOM_PC_NOT_DotByDot_MODE = 666;

    private int mArcType = TvPictureManager.VIDEO_ARC_MAX;

    protected static final int STEP = 1;

    protected static final String TAG = "PictureViewHolder";

    private int mPictureMode = TvPictureManager.PICTURE_MODE_NORMAL;

    protected ProgressDialog progressDialog;

    protected ComboButton comboBtnPictureMode;

  /*protected SeekBarButton seekBtnContrast;

    protected SeekBarButton seekBtnBrightness;

    protected SeekBarButton seekBtnSharpness;

    protected SeekBarButton seekBtnSaturation;

    protected SeekBarButton seekBtnHue;

    protected SeekBarButton seekBtnBacklight;*/
  //edit skye 20141024 for new OSD
  	protected ComboButton myComboBtnContrast;
  	protected ComboButton myComboBtnBrightness;
	protected ComboButton myComboBtnHue;
	protected ComboButton myComboBtnSharpness;
	protected ComboButton myComboBtnSaturation;
	protected ComboButton myComboBtnBacklight;

    protected ComboButton comboBtnColorTemperature;

    protected ComboButton comboBtnZoomMode;

    protected ComboButton comboBtnImageNoiseReduction;

    protected ComboButton comboBtnMPEGNoiseReduction;

    protected ComboButton comboBtnxvYCC;

    protected ComboButton comboBtnITC;

    protected MyButton myButtonPcImageMode;

    protected LinearLayout layoutMenu;

    protected Activity activity;

    private MyHandler myHandler = null;

    private short OFF = 0;

    private short ON = 1;
 
    private boolean mIsSignalLock = true;

    public PictureViewHolder(Activity act) {
        this.activity = act;
    }

    public void findViews() {
        layoutMenu = (LinearLayout) activity.findViewById(R.id.linearlayout_pic_menu);
        myHandler = new MyHandler();
        myComboBtnContrast = new ComboButton(activity, saturation,R.id.linearlayout_pic_contrast, 1, 2, true, true){
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setVideoItem(
                            TvPictureManager.PICTURE_CONTRAST,
                            myComboBtnContrast.getIdx());
                }
            }
        };
        myComboBtnBrightness = new ComboButton(activity, saturation,R.id.linearlayout_pic_brightness, 1, 2, true, true){
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setVideoItem(
                            TvPictureManager.PICTURE_BRIGHTNESS,
                            myComboBtnBrightness.getIdx());
                }
            }
        };
        myComboBtnSharpness = new ComboButton(activity, saturation,R.id.linearlayout_pic_sharpness, 1, 2, true, true){
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setVideoItem(
                            TvPictureManager.PICTURE_SHARPNESS,
                            myComboBtnSharpness.getIdx());
                }
            }
        };
        myComboBtnHue = new ComboButton(activity, saturation,R.id.linearlayout_pic_hue, 1, 2, true, true){
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setVideoItem(TvPictureManager.PICTURE_HUE,
                    		myComboBtnHue.getIdx());
                }
            }
        };
        myComboBtnSaturation = new ComboButton(activity, saturation,R.id.linearlayout_pic_saturation, 1, 2, true, true){
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setVideoItem(
                            TvPictureManager.PICTURE_SATURATION,
                            myComboBtnSaturation.getIdx());
                }
            }
        };
        myComboBtnBacklight =new ComboButton(activity, saturation,R.id.linearlayout_pic_backlight, 1, 2, true, true){
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setBacklight(myComboBtnBacklight.getIdx());
                }
            }
        };
        /*
         * seekBtnBacklight = new SeekBarButton(activity, R.id.linearlayout_pic_backlight, STEP, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setBacklight(seekBtnBacklight.getProgress());
                }
            }
        };
         * */
        comboBtnPictureMode = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_pic_picturemode_vals), R.id.linearlayout_pic_picturemode, 1, 2,
                true) {
            @Override
            public void doUpdate() {
                int specifyPicMode = TvPictureManager.PICTURE_MODE_NORMAL;
                SetFocusableForUserMode();
                if (TvPictureManager.getInstance() != null) {
                    myHandler.sendEmptyMessage(INIT_ZOOM_MODE);
                    specifyPicMode = comboBtnPictureMode.getIdx();
                    TvPictureManager.getInstance().setPictureMode(comboBtnPictureMode.getIdx());
                    initZoomDataScope();
                    comboBtnZoomMode.setIdx(mArcType);
                    Log.i(TAG, "mPictureMode = " + mPictureMode);
                    Log.i(TAG, "specifyPicMode = " + specifyPicMode);
                    Log.i(TAG, "mArcType = " + mArcType);
                    if (mPictureMode == TvPictureManager.PICTURE_MODE_GAME
                            || mPictureMode == TvPictureManager.PICTURE_MODE_AUTO
                            || mPictureMode == TvPictureManager.PICTURE_MODE_PC
                            || specifyPicMode == TvPictureManager.PICTURE_MODE_GAME
                            || specifyPicMode == TvPictureManager.PICTURE_MODE_AUTO
                            || specifyPicMode == TvPictureManager.PICTURE_MODE_PC) {
                        TvPictureManager.getInstance().setVideoArcType(mArcType);
                    }
                    mPictureMode = specifyPicMode;
                }
                freshDataToUIWhenPicModChange();
            }
        };

        comboBtnColorTemperature = new ComboButton(activity, activity.getResources()
                .getStringArray(R.array.str_arr_pic_colortemperature_vals),
                R.id.linearlayout_pic_colortemperature, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setColorTemprature(comboBtnColorTemperature.getIdx());
                }
                freshDataToUIWhenColorTmpChange();
            }
        };
        comboBtnZoomMode = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_pic_zoommode_vals), R.id.linearlayout_pic_zoommode, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setVideoArcType(comboBtnZoomMode.getIdx());
                }
            }
        };
        comboBtnImageNoiseReduction = new ComboButton(activity, activity.getResources()
                .getStringArray(R.array.str_arr_pic_imgnoisereduction_vals),
                R.id.linearlayout_pic_imgnoisereduction, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setNoiseReduction(comboBtnImageNoiseReduction.getIdx());
                }
            }
        };
        comboBtnMPEGNoiseReduction = new ComboButton(activity, activity.getResources()
                .getStringArray(R.array.str_arr_pic_mpegnoisereduction_vals),
                R.id.linearlayout_pic_mpegnoisereduction, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    TvPictureManager.getInstance().setMpegNoiseReduction(comboBtnMPEGNoiseReduction.getIdx());
                }
            }
        };
        comboBtnxvYCC = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_xvycc_vals), R.id.linearlayout_pic_xvycc, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    if (comboBtnxvYCC.getIdx() == 0) {
                        TvPictureManager.getInstance().enableXvyccCompensation(false, 0);
                    } else {
                        TvPictureManager.getInstance().enableXvyccCompensation(true, 0);
                    }
                }
            }
        };

        comboBtnITC = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_ITC_vals), R.id.linearlayout_pic_ITC, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (TvPictureManager.getInstance() != null) {
                    if (comboBtnITC.getIdx() == 0) {
                        TvPictureManager.getInstance().setITC(OFF);
                    } else {
                        TvPictureManager.getInstance().setITC(ON);
                    }
                }
            }
        };

        myButtonPcImageMode = new MyButton(activity, R.id.linearlayout_pic_pcimagemode);
        myButtonPcImageMode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PCImageModeDialogActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

       // mIsSignalLock add for nosignal GRAY PCimagemode by li 20141012
    	try {
			mIsSignalLock = com.mstar.android.tvapi.common.TvManager.getInstance().getPlayerManager().isSignalStable();
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LinearLayout pccontainer = (LinearLayout) activity
                .findViewById(R.id.linearlayout_pic_pcimagemode);
        TextView pctextview = (TextView) (pccontainer.getChildAt(0));
        if (TvCommonManager.getInstance() != null) {
            if ((TvCommonManager.getInstance().getCurrentTvInputSource() != TvCommonManager.INPUT_SOURCE_VGA)||(!mIsSignalLock)) {
                pccontainer.setFocusable(false);
                pctextview.setTextColor(Color.GRAY);
            } else {
            	
                pccontainer.setFocusable(true);
                pctextview.setTextColor(Color.WHITE);
				
            }
        }
        initPictureMode();
        setOnFocusChangeListeners();
        setOnClickListeners();
        comboBtnPictureMode.setFocused();
    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == INVALID_PICTURE_MODE) {
                comboBtnPictureMode.setItemEnable(TvPictureManager.PICTURE_MODE_AUTO, false);
                comboBtnPictureMode.setItemEnable(TvPictureManager.PICTURE_MODE_PC, false);
            } else if (what == VALID_PICTURE_MODE) {
                comboBtnPictureMode.setItemEnable(TvPictureManager.PICTURE_MODE_AUTO, true);
                comboBtnPictureMode.setItemEnable(TvPictureManager.PICTURE_MODE_PC, true);
            } else if (what == INIT_ZOOM_MODE) {
                comboBtnZoomMode.setEnable(true);
                comboBtnZoomMode.setFocusable(true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_DEFAULT, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_4x3, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_AUTO, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_PANORAMA, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_JUSTSCAN, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_ZOOM1, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_ZOOM2, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_14x9, true);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_DOTBYDOT, true);
            } else if (what == INIT_ZOOM_GAME_MODE) {
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_DEFAULT, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_4x3, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_AUTO, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_PANORAMA, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_ZOOM1, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_ZOOM2, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_14x9, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_DOTBYDOT, false);
            } else if (what == INIT_ZOOM_PC_DotByDot_MODE) {
                comboBtnZoomMode.setEnable(false);
                comboBtnZoomMode.setFocusable(false);
            } else if (what == INIT_ZOOM_PC_NOT_DotByDot_MODE) {
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_DEFAULT, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_JUSTSCAN, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_AUTO, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_PANORAMA, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_ZOOM1, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_ZOOM2, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_14x9, false);
                comboBtnZoomMode.setItemEnable(TvPictureManager.VIDEO_ARC_DOTBYDOT, false);
            }
            super.handleMessage(msg);
        }
    }

    private ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(activity.getResources().getString(
                    R.string.str_pic_pcimagemode_waiting));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
        }
        return progressDialog;
    }

    private void setOnClickListeners() {
        OnClickListener comoBtnOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.isSelected()) {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.VISIBLE);
                    container.getChildAt(3).setVisibility(View.VISIBLE);
                } else {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.GONE);
                    container.getChildAt(3).setVisibility(View.GONE);
                }
            }
        };
        comboBtnPictureMode.setOnClickListener(comoBtnOnClickListener);
        comboBtnColorTemperature.setOnClickListener(comoBtnOnClickListener);
        comboBtnZoomMode.setOnClickListener(comoBtnOnClickListener);
        comboBtnImageNoiseReduction.setOnClickListener(comoBtnOnClickListener);
        comboBtnMPEGNoiseReduction.setOnClickListener(comoBtnOnClickListener);
        this.comboBtnxvYCC.setOnClickListener(comoBtnOnClickListener);
        comboBtnITC.setOnClickListener(comoBtnOnClickListener);
        OnClickListener seekBarBtnOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.VISIBLE);
                } else {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.GONE);
                }
            }
        };
        myComboBtnContrast.setOnClickListener(comoBtnOnClickListener);//skye 20141024
        myComboBtnBrightness.setOnClickListener(comoBtnOnClickListener);
        myComboBtnSharpness.setOnClickListener(comoBtnOnClickListener);
        myComboBtnSaturation.setOnClickListener(comoBtnOnClickListener);
        myComboBtnHue.setOnClickListener(comoBtnOnClickListener);
        myComboBtnBacklight.setOnClickListener(comoBtnOnClickListener);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener comboBtnFocusListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // if (hasFocus ) {
                // LinearLayout container = (LinearLayout) v;
                // container.getChildAt(0).setVisibility(View.VISIBLE);
                // container.getChildAt(3).setVisibility(View.VISIBLE);
                // } else {
                LinearLayout container = (LinearLayout) v;
                container.getChildAt(0).setVisibility(View.GONE);
                container.getChildAt(3).setVisibility(View.GONE);
                // }
            }
        };
        comboBtnPictureMode.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnColorTemperature.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnZoomMode.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnImageNoiseReduction.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnMPEGNoiseReduction.setOnFocusChangeListener(comboBtnFocusListener);
        this.comboBtnxvYCC.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnITC.setOnFocusChangeListener(comboBtnFocusListener);
        OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // if (hasFocus && v.isSelected()) {
                // LinearLayout container = (LinearLayout) v;
                // ((TextView) container.getChildAt(0))
                // .setTextColor(Color.WHITE);
                // ((TextView) container.getChildAt(1))
                // .setTextColor(Color.WHITE);
                // container.getChildAt(2).setVisibility(View.VISIBLE);
                // } else {
                LinearLayout container = (LinearLayout) v;
                // if (hasFocus) {
                // container.getChildAt(2).setVisibility(View.VISIBLE);
                // } else {
                container.getChildAt(2).setVisibility(View.GONE);
                // }
            }
        };
        myComboBtnContrast.setOnFocusChangeListener(comboBtnFocusListener);
        myComboBtnBrightness.setOnFocusChangeListener(comboBtnFocusListener);
        myComboBtnSharpness.setOnFocusChangeListener(comboBtnFocusListener);
        myComboBtnSaturation.setOnFocusChangeListener(comboBtnFocusListener);
        myComboBtnHue.setOnFocusChangeListener(comboBtnFocusListener);
        myComboBtnBacklight.setOnFocusChangeListener(comboBtnFocusListener);
    }

    public void LoadDataToUI() {
        if (TvPictureManager.getInstance() != null) {
        	
	
            mPictureMode = TvPictureManager.getInstance().getPictureMode();
            comboBtnPictureMode.setIdx(mPictureMode);
            int inputSrcType = TvCommonManager.getInstance().getCurrentTvInputSource();

            //Refine performance with query DB by content provider to reduce startup time in picture page.
            Cursor cursor = this.activity.getApplicationContext().getContentResolver().query(
                Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/" + inputSrcType
                        + "/picmode/" + mPictureMode), null, null, null, null);
            if (cursor.moveToFirst()) {
            	myComboBtnContrast.setIdx(
                        (short) cursor.getInt(cursor.getColumnIndex("u8Contrast")));
            	myComboBtnBrightness.setIdx(
                        (short) cursor.getInt(cursor.getColumnIndex("u8Brightness")));
                myComboBtnSharpness.setIdx(
                        (short) cursor.getInt(cursor.getColumnIndex("u8Sharpness")));
                myComboBtnHue.setIdx(
                        (short) cursor.getInt(cursor.getColumnIndex("u8Hue")));
                myComboBtnSaturation.setIdx(
                        (short) cursor.getInt(cursor.getColumnIndex("u8Saturation")));
                myComboBtnBacklight.setIdx((short) cursor.getInt(cursor.getColumnIndex("u8Backlight")));
            }
            cursor.close();

            short colorTempIdx = getColorTemperatureSetting();
            comboBtnColorTemperature.setIdx(colorTempIdx);

            cursor = this.activity.getApplicationContext().getContentResolver().query(
                Uri.parse("content://mstar.tv.usersetting/nrmode/nrmode/" + colorTempIdx
                        + "/inputsrc/" + inputSrcType), null, null, null, null);
            int eNr = 0;
            int eMpegNr = 0;
            if (cursor.moveToFirst()) {
                eNr = cursor.getInt(cursor.getColumnIndex("eNR"));
                eMpegNr = cursor.getInt(cursor.getColumnIndex("eMPEG_NR"));
            }
            cursor.close();
            comboBtnImageNoiseReduction.setIdx(eNr);
            comboBtnMPEGNoiseReduction.setIdx(eMpegNr);

            cursor = this.activity.getApplicationContext().getContentResolver().query(
                    Uri.parse("content://mstar.tv.usersetting/videosetting/inputsrc/" + inputSrcType),
                    null, null, null, null);
            int eArcMode = 0;
            if (cursor.moveToFirst()) {
                if (mPictureMode == TvPictureManager.PICTURE_MODE_GAME) {
                    eArcMode = cursor.getInt(cursor.getColumnIndex("enGameModeARCType"));
                } else if (mPictureMode == TvPictureManager.PICTURE_MODE_AUTO) {
                    eArcMode = cursor.getInt(cursor.getColumnIndex("enAutoModeARCType"));
                } else if (mPictureMode == TvPictureManager.PICTURE_MODE_PC) {
                    eArcMode = cursor.getInt(cursor.getColumnIndex("enPcModeARCType"));
                } else {
                    eArcMode = cursor.getInt(cursor.getColumnIndex("enARCType"));
                }
            }
            cursor.close();
            comboBtnZoomMode.setIdx(eArcMode);

            this.comboBtnxvYCC.setIdx(0);
            comboBtnITC.setIdx((short) TvPictureManager.getInstance().getITC());
            
        	
        }
        SetFocusableForUserMode();
    }

    private short getColorTemperatureSetting() {
        short colorTempIdx = 0;

        if (TvPictureManager.getInstance() != null) {
            int inputSrcType = TvCommonManager.getInstance().getCurrentTvInputSource();
            int pictureMode = TvPictureManager.getInstance().getPictureMode();
            Cursor cursor = this.activity.getApplicationContext().getContentResolver().query(
                Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/" + inputSrcType
                        + "/picmode/" + pictureMode), null, null, null, "PictureModeType");
            if (cursor.moveToFirst()) {
                colorTempIdx = (short) cursor.getInt(cursor.getColumnIndex("eColorTemp"));
            }
            cursor.close();
        }

        return colorTempIdx;
    }

    private void updateBtnColorTemperature() {
        comboBtnColorTemperature.setIdx(getColorTemperatureSetting());
        freshDataToUIWhenColorTmpChange();
    }

    private void freshDataToUIWhenPicModChange() {
        if (TvPictureManager.getInstance() != null) {
        	myComboBtnContrast.setIdx((short) TvPictureManager.getInstance().getVideoItem(
                    TvPictureManager.PICTURE_CONTRAST));
        	myComboBtnBrightness.setIdx((short) TvPictureManager.getInstance().getVideoItem(
                    TvPictureManager.PICTURE_BRIGHTNESS));
        	myComboBtnSharpness.setIdx((short) TvPictureManager.getInstance().getVideoItem(
                    TvPictureManager.PICTURE_SHARPNESS));
        	myComboBtnHue.setIdx((short) TvPictureManager.getInstance().getVideoItem(
                    TvPictureManager.PICTURE_HUE));
        	myComboBtnSaturation.setIdx((short) TvPictureManager.getInstance().getVideoItem(
                    TvPictureManager.PICTURE_SATURATION));
        	myComboBtnBacklight.setIdx((short) TvPictureManager.getInstance().getBacklight());
            comboBtnImageNoiseReduction.setIdx(TvPictureManager.getInstance().getNoiseReduction());
            comboBtnMPEGNoiseReduction.setIdx(TvPictureManager.getInstance().getMpegNoiseReduction());
            comboBtnZoomMode.setIdx(TvPictureManager.getInstance().getVideoArcType());
            this.comboBtnxvYCC.setIdx(0);

            updateBtnColorTemperature();
        }
    }

    private void initZoomDataScope() {
        if (TvPictureManager.getInstance() != null && TvCommonManager.getInstance() != null) {
            int inputSrc = TvCommonManager.getInstance().getCurrentTvInputSource();
            mArcType = TvPictureManager.VIDEO_ARC_MAX;
            mArcType = TvPictureManager.getInstance().getVideoArcType();
            if (mArcType == TvPictureManager.VIDEO_ARC_MAX) {
                return;
            }
            if (inputSrc == TvCommonManager.INPUT_SOURCE_VGA
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI2
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI3
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI4) {
                if ((comboBtnPictureMode.getIdx() == TvPictureManager.PICTURE_MODE_AUTO && TvPictureManager
                        .getInstance().getIsPcMode() == 1)
                        || comboBtnPictureMode.getIdx() == TvPictureManager.PICTURE_MODE_PC) {
                    if (mArcType == TvPictureManager.VIDEO_ARC_DOTBYDOT) {
                        myHandler.sendEmptyMessage(INIT_ZOOM_PC_DotByDot_MODE);
                    } else {
                        myHandler.sendEmptyMessage(INIT_ZOOM_PC_NOT_DotByDot_MODE);
                    }
                }
            }
            if (comboBtnPictureMode.getIdx() == TvPictureManager.PICTURE_MODE_GAME) {
                myHandler.sendEmptyMessage(INIT_ZOOM_GAME_MODE);
            }
        }
    }

    private void initPictureMode() {
        if (TvPictureManager.getInstance() != null && TvCommonManager.getInstance() != null) {
            int inputSrc = TvCommonManager.getInstance().getCurrentTvInputSource();
            int pictureMode = TvPictureManager.getInstance().getPictureMode();
            mArcType = TvPictureManager.VIDEO_ARC_MAX;
            mArcType = TvPictureManager.getInstance().getVideoArcType();
            if (mArcType == TvPictureManager.VIDEO_ARC_MAX) {
                return;
            }
            if (inputSrc == TvCommonManager.INPUT_SOURCE_VGA
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI2
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI3
                    || inputSrc == TvCommonManager.INPUT_SOURCE_HDMI4) {
                myHandler.sendEmptyMessage(VALID_PICTURE_MODE);
                if ((pictureMode == TvPictureManager.PICTURE_MODE_AUTO && TvPictureManager.getInstance()
                        .getIsPcMode() == 1) || pictureMode == TvPictureManager.PICTURE_MODE_PC) {
                    if (mArcType == TvPictureManager.VIDEO_ARC_DOTBYDOT) {
                        myHandler.sendEmptyMessage(INIT_ZOOM_PC_DotByDot_MODE);
                    } else {
                        myHandler.sendEmptyMessage(INIT_ZOOM_PC_NOT_DotByDot_MODE);
                    }
                }
            } else {
                myHandler.sendEmptyMessage(INVALID_PICTURE_MODE);
            }
            if (pictureMode == TvPictureManager.PICTURE_MODE_GAME) {
                myHandler.sendEmptyMessage(INIT_ZOOM_GAME_MODE);
            }
        }
    }

    private void freshDataToUIWhenColorTmpChange() {

        if (TvPictureManager.getInstance() != null) {
            comboBtnImageNoiseReduction.setIdx(TvPictureManager.getInstance().getNoiseReduction());
            comboBtnMPEGNoiseReduction.setIdx(TvPictureManager.getInstance().getMpegNoiseReduction());
        }
    }

    private void SetFocusableForUserMode() {
        if (comboBtnPictureMode.getIdx() != 3) {
        	myComboBtnContrast.setEnable(false);
        	myComboBtnBrightness.setEnable(false);
        	myComboBtnSharpness.setEnable(false);
        	myComboBtnHue.setEnable(false);
        	myComboBtnSaturation.setEnable(false);
        	myComboBtnBacklight.setEnable(false);
            myComboBtnContrast.setFocusable(false);
            myComboBtnBrightness.setFocusable(false);
            myComboBtnSharpness.setFocusable(false);
            myComboBtnHue.setFocusable(false);
            myComboBtnSaturation.setFocusable(false);
            myComboBtnBacklight.setFocusable(false);
        } else {
        	myComboBtnContrast.setEnable(true);
        	myComboBtnBrightness.setEnable(true);
        	myComboBtnSharpness.setEnable(true);
        	myComboBtnHue.setEnable(true);
        	myComboBtnSaturation.setEnable(true);
        	myComboBtnBacklight.setEnable(true);
            myComboBtnContrast.setFocusable(true);
            myComboBtnBrightness.setFocusable(true);
            myComboBtnSharpness.setFocusable(true);
            myComboBtnHue.setFocusable(true);
            myComboBtnSaturation.setFocusable(true);
            myComboBtnBacklight.setFocusable(true);
        }
    }

    private boolean isSourceDVI() {
        if (TvCommonManager.getInstance() != null) {
            int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
            if (curInputSource >= TvCommonManager.INPUT_SOURCE_DVI
                    && curInputSource < TvCommonManager.INPUT_SOURCE_DVI_MAX) {
                return true;
            } else if (curInputSource >= TvCommonManager.INPUT_SOURCE_HDMI
                    && curInputSource < TvCommonManager.INPUT_SOURCE_HDMI_MAX) {
                if (TvCommonManager.getInstance().isHdmiSignalMode()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
