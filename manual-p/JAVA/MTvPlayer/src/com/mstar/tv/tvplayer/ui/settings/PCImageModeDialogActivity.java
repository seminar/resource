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

package com.mstar.tv.tvplayer.ui.settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mstar.android.tv.TvPictureManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tvframework.MstarBaseActivity;

public class PCImageModeDialogActivity extends MstarBaseActivity {
	// skye 20141024
	protected String[] sky = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
    		"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
    	    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
    	    "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
    	    "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
    	    "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", 
    	    "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", 
    	    "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", 
    	    "93", "94", "95", "96", "97", "98", "99", "100" };
   // private SeekBarButton seekBtnClock;

	private ComboButton seekBtnClock;
    private ComboButton seekBtnPhase;

    private ComboButton seekBtnHorizontalPos;

    private ComboButton seekBtnVerticalPos;

    private short clock = 0;

    private short phase = 0;

    private short horizontalPos = 0;

    private short verticalPos = 0;

    private MyButton myBtnBack;

    private static final int STEP = 1;

    private ProgressDialog progressDialog;

    private Activity activity = null;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 8888) {
                seekBtnClock = new ComboButton(activity,sky, R.id.linearlayout_pic_clock,1,2,true,
  						false) {
                    @Override
                    public void doUpdate() {
                        // TODO Auto-generated method stub
                        TvPictureManager.getInstance().setPCClock(seekBtnClock.getIdx());
                    }
                };
                seekBtnPhase = new ComboButton(activity,sky, R.id.linearlayout_pic_phase, 1,2,true,
  						false) {
                    @Override
                    public void doUpdate() {
                        // TODO Auto-generated method stub
                        TvPictureManager.getInstance().setPCPhase(seekBtnPhase.getIdx());
                    }
                };
                seekBtnHorizontalPos = new ComboButton(activity,sky,
                        R.id.linearlayout_pic_horizontalpos,1,2,true,
  						false) {
                    @Override
                    public void doUpdate() {
                        // TODO Auto-generated method stub
                        TvPictureManager.getInstance()
                                .setPCHPos(seekBtnHorizontalPos.getIdx());
                    }
                };
                seekBtnVerticalPos = new ComboButton(activity,sky, R.id.linearlayout_pic_verticalpos,
                		1,2,true,false) {
                    @Override
                    public void doUpdate() {
                        // TODO Auto-generated method stub
                        TvPictureManager.getInstance().setPCVPos(seekBtnVerticalPos.getIdx());
                    }
                };
                myBtnBack = new MyButton(activity, R.id.linearlayout_pic_pcimagemodeback);
                myBtnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TvPictureManager.getInstance().execAutoPc();
                    }
                });
                LoadDataToDialog();
                setOnFocusChangeListeners();
                setOnClickListeners();
            }
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                Intent intent = new Intent(PCImageModeDialogActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.pc_image_mode_dialog);

        clock = (short) TvPictureManager.getInstance().getPCClock();
        phase = (short) TvPictureManager.getInstance().getPCPhase();
        horizontalPos = (short) TvPictureManager.getInstance().getPCHPos();
        verticalPos = (short) TvPictureManager.getInstance().getPCVPos();
        handler.sendEmptyMessageDelayed(8888, 100);

        LittleDownTimer.setHandler(handler);
    }

    private ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(this.getResources().getString(
                    R.string.str_pic_pcimagemode_waiting));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
        }
        return progressDialog;
    }

    @Override
    protected void onResume() {
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    public void onUserInteraction() {
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "PcImageModeDialogActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TvIntent.MAINMENU);
        intent.putExtra("currentPage", MainMenuActivity.PICTURE_PAGE);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void LoadDataToDialog() {
        clock = (short) TvPictureManager.getInstance().getPCClock();
        phase = (short) TvPictureManager.getInstance().getPCPhase();
        horizontalPos = (short) TvPictureManager.getInstance().getPCHPos();
        verticalPos = (short) TvPictureManager.getInstance().getPCVPos();

        seekBtnClock.setIdx(clock);
        seekBtnPhase.setIdx(phase);
        seekBtnHorizontalPos.setIdx(horizontalPos);
        seekBtnVerticalPos.setIdx(verticalPos);
    }

    private void setOnFocusChangeListeners() {
    	 OnFocusChangeListener comboBtnFocusListener1 = new OnFocusChangeListener() {
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
        OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.VISIBLE);
                } else {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(2).setVisibility(View.GONE);
                }
            }
        };
        seekBtnClock.setOnFocusChangeListener(comboBtnFocusListener1);
        seekBtnPhase.setOnFocusChangeListener(comboBtnFocusListener1);
        seekBtnHorizontalPos.setOnFocusChangeListener(comboBtnFocusListener1);
        seekBtnVerticalPos.setOnFocusChangeListener(comboBtnFocusListener1);
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
    
        seekBtnClock.setOnClickListener(comoBtnOnClickListener);
        seekBtnPhase.setOnClickListener(comoBtnOnClickListener);
        seekBtnHorizontalPos.setOnClickListener(comoBtnOnClickListener);
        seekBtnVerticalPos.setOnClickListener(comoBtnOnClickListener);
    }
}
