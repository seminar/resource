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
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.mstar.tv.tvplayer.ui.R;

import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tv.tvplayer.ui.settings.EqualizerDialogActivity;
import com.mstar.tv.tvplayer.ui.settings.SeperateHearActivity;
import com.mstar.util.Tools;
import com.mstar.android.tv.TvAudioManager;
import com.mstar.android.tvapi.common.vo.EnumSoundMode;
import com.mstar.android.tvapi.common.vo.EnumSurroundMode;
import com.mstar.android.tvapi.common.vo.EnumSpdifType;
///lxk-add for SPDIF focusable conditionally 20141012
import com.mstar.android.tv.TvCommonManager;
////lxk-add end 

public class SoundViewHolder {
    private static final String TAG = "SoundViewHolder";
  //edit skye 20141024 for new OSD
  	protected String[] soundstring = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
      		"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
      	    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
      	    "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
      	    "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
      	    "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", 
      	    "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", 
      	    "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", 
      	    "93", "94", "95", "96", "97", "98", "99", "100" };

    protected static final int STEP = 1;

    protected ComboButton comboBtnSoundmode;  

//    protected SeekBarButton seekBarBtnBass;
//
//    protected SeekBarButton seekBarBtnTreble;

    protected MyButton btnEqualizer;

    //protected SeekBarButton seekBarBtnBalance;
    //SKYE 20141024
    protected ComboButton comboBtnBass;
    protected ComboButton comboBtnTreble;
    protected ComboButton comboBalance;

    protected ComboButton comboBtnAVC;

    protected ComboButton comboBtnSurround;   

    protected ComboButton comboBtnSrs;

    protected ComboButton comboBtnSpdifoutput;

    // protected ComboButton comboBtnSeparatehear;
    protected MyButton btnSeperateHear;

    protected Activity activity;

    protected TvAudioManager tvAudioManager = null;

    protected ComboButton comboBtnAd;

    protected ComboButton comboBtnHOH;

    protected ComboButton comboBtnHDByPass;

    public SoundViewHolder(Activity act) {
        this.activity = act;
        tvAudioManager = TvAudioManager.getInstance();
    }

    public void findViews() {
    	comboBtnBass = new ComboButton(activity, soundstring,R.id.linearlayout_sound_bass, 1, 2, true, true){
            @Override
            public void doUpdate() {
                // TODO Auto-generated method stub
                if (tvAudioManager != null) {
                    tvAudioManager.setBass(comboBtnBass.getIdx());
                }
            }
        };
        comboBtnTreble = new ComboButton(activity, soundstring, R.id.linearlayout_sound_treble, 1 , 2, true, true) {
            @Override
            public void doUpdate() {
                // TODO Auto-generated method stub
                if (tvAudioManager != null) {
                    tvAudioManager.setTreble(comboBtnTreble.getIdx());
                }
            }
        };
        comboBtnSoundmode = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_soundmode_vals), R.id.linearlayout_sound_soundmode, 1, 2,
                true) {
            @Override
            public void doUpdate() {
                SetFocusableOrNotForUserMode();
                if (tvAudioManager != null) {
                    tvAudioManager.setAudioSoundMode(comboBtnSoundmode.getIdx());
                }
                freshDataToUIWhenSoundModChange();
            }
        };
        SetFocusableOrNotForUserMode();
        btnEqualizer = new MyButton(activity, R.id.linearlayout_sound_equalizer);
        btnEqualizer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EqualizerDialogActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        comboBalance = new ComboButton(activity,soundstring, R.id.linearlayout_sound_balance,1 , 2, true, true) {
            @Override
            public void doUpdate() {
                // TODO Auto-generated method stub
                if (tvAudioManager != null) {
                    tvAudioManager.setBalance(comboBalance.getIdx());
                }
            }
        };
        comboBtnAVC = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_avc_vals), R.id.linearlayout_sound_avc, 1, 2, true) {
            @Override
            public void doUpdate() {
                // TODO Auto-generated method stub
                if (tvAudioManager != null) {
                    tvAudioManager.setAvcMode(comboBtnAVC.getIdx() == 0 ? false : true);
                }
            }
        };

        comboBtnAd = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_avc_vals), R.id.linearlayout_sound_ad, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvAudioManager != null) {
                    tvAudioManager.setADEnable(comboBtnAd.getIdx() == 0 ? false : true);
                    tvAudioManager.setADAbsoluteVolume(50);
                }
            }
        };
        comboBtnHOH = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_avc_vals), R.id.linearlayout_sound_hoh, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvAudioManager != null) {
                    tvAudioManager.setHOHStatus(comboBtnHOH.getIdx() == 0 ? false : true);
                }
            }
        };
        comboBtnHDByPass = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_avc_vals), R.id.linearlayout_sound_hdbypass, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvAudioManager != null) {
                    tvAudioManager
                            .setHDMITx_HDByPass(comboBtnHDByPass.getIdx() == 0 ? false : true);
                }
            }
        };

        if (Tools.isBox()) {
            comboBtnAd.setVisibility(false);
            comboBtnHOH.setVisibility(false);
        }
        comboBtnSurround = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_surround_vals), R.id.linearlayout_sound_surround, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvAudioManager != null) {
                    tvAudioManager.setAudioSurroundMode(comboBtnSurround.getIdx());
                }
            }
        };

        comboBtnSrs = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_surround_vals), R.id.linearlayout_sound_srs, 1, 2, true) {
            @Override
            public void doUpdate() {
                // TODO Auto-generated method stub
                if (tvAudioManager != null) {
                    tvAudioManager.enableSRS((comboBtnSrs.getIdx() != 0));
                }
            }
        };
        comboBtnSpdifoutput = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sound_spdifoutput_vals), R.id.linearlayout_sound_spdifoutput, 1, 2,
                true) {
            @Override
            public void doUpdate() {
                // TODO Auto-generated method stub
                tvAudioManager.setAudioSpdifOutMode(comboBtnSpdifoutput.getIdx());
            }
        };
    ///lxk-add for SPDIF focusable conditionally 20141012
	 SetFocusableOrNotAboutSPDIFOut();
	 //end
        btnSeperateHear = new MyButton(activity, R.id.linearlayout_sound_separatehear);
        btnSeperateHear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SeperateHearActivity.class);
                intent.putExtra("extra","SeperateHearActivity");
                activity.startActivity(intent);
                // activity.finish();
            }
        });
        SetFocusableOrNotForARCMode();
        setOnClickListeners();
        setOnFocusChangeListeners();
        comboBtnSoundmode.setFocused();
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
        comboBtnSoundmode.setOnClickListener(comoBtnOnClickListener);
        comboBtnAVC.setOnClickListener(comoBtnOnClickListener);
        comboBtnAd.setOnClickListener(comoBtnOnClickListener);
        comboBtnHOH.setOnClickListener(comoBtnOnClickListener);
        comboBtnHDByPass.setOnClickListener(comoBtnOnClickListener);
        comboBtnSurround.setOnClickListener(comoBtnOnClickListener);
        comboBtnSrs.setOnClickListener(comoBtnOnClickListener);
        comboBtnSpdifoutput.setOnClickListener(comoBtnOnClickListener);
        OnClickListener seekBarBtnOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (v.isSelected()) {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.VISIBLE);
                } else {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.GONE);
                }
            }
        };
        comboBtnBass.setOnClickListener(comoBtnOnClickListener);
        comboBtnTreble.setOnClickListener(comoBtnOnClickListener);
        comboBalance.setOnClickListener(comoBtnOnClickListener);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener comboBtnFocusListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                // if (hasFocus) {
                // LinearLayout container = (LinearLayout)v;
                // container.getChildAt(0).setVisibility(View.VISIBLE);
                // container.getChildAt(3).setVisibility(View.VISIBLE);
                // }
                // else
                {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.GONE);
                    container.getChildAt(3).setVisibility(View.GONE);
                }
            }
        };
        comboBtnSoundmode.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnAVC.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnAd.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnHOH.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnHDByPass.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnSurround.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnSrs.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnSpdifoutput.setOnFocusChangeListener(comboBtnFocusListener);
        OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                // if (hasFocus)
                // {
                // LinearLayout container = (LinearLayout)v;
                // container.getChildAt(2).setVisibility(View.VISIBLE);
                // }
                // else
                {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.GONE);
                }
            }
        };
        comboBtnBass.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnTreble.setOnFocusChangeListener(comboBtnFocusListener);
        comboBalance.setOnFocusChangeListener(comboBtnFocusListener);
    }

    private void freshDataToUIWhenSoundModChange() {
        if (tvAudioManager != null) {
        	comboBtnBass.setIdx((short) tvAudioManager.getBass());
        	comboBtnTreble.setIdx((short) tvAudioManager.getTreble());
        }
    }

    public void LoadDataToUI() {
        if (tvAudioManager != null) {
            // Refine performance with query DB by content provider to reduce
            // startup time in sound page.
            Cursor cursor = this.activity
                    .getApplicationContext()
                    .getContentResolver()
                    .query(Uri.parse("content://mstar.tv.usersetting/soundsetting"), null, null,
                            null, null);

            int soundMode = 0;
            if (cursor.moveToFirst()) {
                soundMode = cursor.getInt(cursor.getColumnIndex("SoundMode"));
                comboBtnSoundmode.setIdx(soundMode);
                comboBalance.setIdx((short) cursor.getInt(cursor
                        .getColumnIndex("Balance")));
                comboBtnAVC.setIdx(cursor.getInt(cursor.getColumnIndex("bEnableAVC")) == 1 ? 1 : 0);
                comboBtnAd.setIdx(cursor.getInt(cursor.getColumnIndex("bEnableAD")) == 1 ? 1 : 0);
                comboBtnSurround.setIdx(cursor.getInt(cursor.getColumnIndex("Surround")));
                comboBtnSrs
                        .setIdx(cursor.getInt(cursor.getColumnIndex("SurroundSoundMode")) != 0 ? 1
                                : 0);
            }
            cursor.close();

            cursor = this.activity
                    .getApplicationContext()
                    .getContentResolver()
                    .query(Uri
                            .parse("content://mstar.tv.usersetting/soundmodesetting/" + soundMode),
                            null, null, null, null);

            if (cursor.moveToFirst()) {
            	comboBtnBass.setIdx((short) cursor.getInt(cursor.getColumnIndex("Bass")));
            	comboBtnTreble
                        .setIdx((short) cursor.getInt(cursor.getColumnIndex("Treble")));
            }
            cursor.close();

            comboBtnHOH.setIdx(tvAudioManager.getHOHStatus() ? 1 : 0);
            Log.i(TAG,
                    "*****Spidif Mode is :" + tvAudioManager.getAudioSpdifOutMode() + "*****");
            comboBtnSpdifoutput.setIdx(tvAudioManager.getAudioSpdifOutMode());

            comboBtnHDByPass.setIdx(tvAudioManager.getHDMITx_HDByPass() ? 1 : 0);

            if (!tvAudioManager.isSupportHDMITx_HDByPassMode()) {
                comboBtnHDByPass.setVisibility(false);
            }

        }
        SetFocusableOrNotForUserMode();
        SetFocusableOrNotForARCMode();
    }

    private  void SetFocusableOrNotForUserMode() {
        if (comboBtnSoundmode.getIdx() != 4) {
        	comboBtnBass.setEnable(false);
        	comboBtnBass.setFocusable(false);
        	comboBtnTreble.setEnable(false);
        	comboBtnTreble.setFocusable(false);
        } else {
        	comboBtnBass.setEnable(true);
        	comboBtnBass.setFocusable(true);
        	comboBtnTreble.setEnable(true);
        	comboBtnTreble.setFocusable(true);
        }
    }
    //add by wxy
    public void SetFocusableOrNotForARCMode()
    {
        if((SettingViewHolder.getHdmiArcMode()==1)&&(SettingViewHolder.getHdmiCecMode()==1))
        {
        	comboBtnSoundmode.setEnable(false);
            comboBtnSoundmode.setFocusable(false);
            comboBtnSurround.setEnable(false);
            comboBtnSurround.setFocusable(false);
        }
        else
        {
        	comboBtnSoundmode.setEnable(true);
            comboBtnSoundmode.setFocusable(true);
            comboBtnSurround.setEnable(true);
            comboBtnSurround.setFocusable(true);
        }
    }
    //add end
    
    ///lxk-add for SPDIF focusable conditionally 20141012
    private void SetFocusableOrNotAboutSPDIFOut() {
        int curis = TvCommonManager.getInstance().getCurrentTvInputSource();
        if (((curis >= TvCommonManager.INPUT_SOURCE_HDMI)&&(curis <= TvCommonManager.INPUT_SOURCE_HDMI4)) ||
		((curis >= TvCommonManager.INPUT_SOURCE_DTV)&&(curis <= TvCommonManager.INPUT_SOURCE_DTV2))||
		((curis >= TvCommonManager.INPUT_SOURCE_STORAGE)&&(curis <= TvCommonManager.INPUT_SOURCE_STORAGE2))) {
            comboBtnSpdifoutput.setFocusable(true);
        } else {
            comboBtnSpdifoutput.setFocusable(false);
        }
    }
    ////lxk-add end
}
