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

package com.mstar.tv.tvplayer.ui.ca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CARatingInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.ca.CaErrorType.RETURN_CODE;

public class CaWatchLevelActivity extends Activity {
    protected int level_index = 0;

    protected Intent intent = new Intent();

    protected LinearLayout linearlayout_watch_level;

    protected TextView textview_watch_level = null;

    private EditText editText_ca_watch_level_pin;

    protected Button btnSubmit;

    protected TvCaManager tvCaManager = null;

    protected String[] levels = {
            "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
    };

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (editText_ca_watch_level_pin.hasFocus()) {
                LittleDownTimer.resetMenu();
                return;
            }
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaWatchLevelActivity.this.finish();
                Intent intent = new Intent(CaWatchLevelActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_info_watch_level);
        LittleDownTimer.setHandler(handler);

        tvCaManager = TvCaManager.getInstance();
        findViews();
        getInitData();
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaWatchLevelActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaWatchLevelActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();

    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaWatchLevelActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void findViews() {
        linearlayout_watch_level = (LinearLayout) findViewById(R.id.linearlayout_watch_level);
        editText_ca_watch_level_pin = (EditText) findViewById(R.id.editText_ca_watch_level_pin);
        textview_watch_level = (TextView) findViewById(R.id.textview_watch_level);
        textview_watch_level.setText(levels[level_index]);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        setOnClickLisenters();
        setOnFocusChangeListeners();
    }

    private void getInitData() {
        CARatingInfo ratinginfo = tvCaManager.CaGetRating();
        if (ratinginfo == null) {
            return;
        }
        if (ratinginfo.sRatingState == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            textview_watch_level.setText(Short.toString(ratinginfo.pbyRating));
            for (int i = 0; i < levels.length; i++) {
                if (ratinginfo.pbyRating == Short.parseShort(levels[i])) {
                    level_index = i;
                    break;
                }
            }
        } else if (ratinginfo.sRatingState == RETURN_CODE.ST_CA_RC_CARD_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_card_invalid);
        } else if (ratinginfo.sRatingState == RETURN_CODE.ST_CA_RC_POINTER_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pointer_invalid);
        } else if (ratinginfo.sRatingState == RETURN_CODE.ST_CA_RC_UNKNOWN.getRetCode()) {
            toastShow(R.string.st_ca_rc_unknown);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = CaWatchLevelActivity.this.getCurrentFocus().getId();

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_watch_level:
                        if (level_index == levels.length - 1) {
                            level_index = 0;
                        } else {
                            level_index++;
                        }
                        textview_watch_level.setText(levels[level_index]);
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_watch_level:
                        if (level_index == 0) {
                            level_index = levels.length - 1;
                        } else {
                            level_index--;
                        }
                        textview_watch_level.setText(levels[level_index]);
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaWatchLevelActivity.this, CaManagementActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.linearlayout_watch_level:
                        linearlayout_watch_level.getChildAt(0).setVisibility(View.VISIBLE);
                        linearlayout_watch_level.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.btnSubmit:
                        modifyWatchLevel();
                        break;
                    default:
                        break;
                }
            }
        };
        linearlayout_watch_level.setOnClickListener(listener);
        btnSubmit.setOnClickListener(listener);
    }

    private void modifyWatchLevel() {
        String pin = editText_ca_watch_level_pin.getText().toString();
        if (pin.length() < 6) {
            toastShow(R.string.st_ca_rc_pin_len);
            return;
        }
        short rating = Short.parseShort(textview_watch_level.getText().toString());
        short ret = tvCaManager.CaSetRating(pin, rating);
        if (ret == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            toastShow(R.string.st_ca_rc_ok);
        } else if (ret == RETURN_CODE.ST_CA_RC_CARD_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_card_invalid);
        } else if (ret == RETURN_CODE.ST_CA_RC_POINTER_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pointer_invalid);
        } else if (ret == RETURN_CODE.ST_CA_RC_PIN_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pin_invalid);
        } else if (ret == RETURN_CODE.ST_CA_RC_UNKNOWN.getRetCode()) {
            toastShow(R.string.st_ca_rc_unknown);
        } else if (ret == RETURN_CODE.ST_CA_RC_WATCHRATING_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_watchrating_invalid);
        }
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener FocuschangesListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout container = (LinearLayout) v;
                container.getChildAt(0).setVisibility(View.GONE);
                container.getChildAt(3).setVisibility(View.GONE);
            }
        };
        linearlayout_watch_level.setOnFocusChangeListener(FocuschangesListener);
    }

    private void toastShow(int resId) {
        Toast toast = new Toast(this);
        TextView MsgShow = new TextView(this);
        toast.setDuration(0);
        MsgShow.setTextColor(Color.RED);
        MsgShow.setTextSize(25);
        MsgShow.setText(resId);
        toast.setView(MsgShow);
        toast.show();
    }
}
