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
import com.mstar.android.tvapi.dtv.vo.CaWorkTimeInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.ca.CaErrorType.RETURN_CODE;

public class CaWorkTimeActivity extends Activity {
    protected int start_hour_idx = 0;

    protected int start_minute_idx = 0;

    protected int end_hour_idx = 0;

    protected int end_minute_idx = 0;

    protected Intent intent = new Intent();

    protected LinearLayout linearlayout_start_time_hour;

    protected LinearLayout linearlayout_start_time_minute;

    protected LinearLayout linearlayout_end_time_hour;

    protected LinearLayout linearlayout_end_time_minute;

    protected TextView textview_start_time_hour = null;

    protected TextView textview_start_time_minute = null;

    protected TextView textview_end_time_hour = null;

    protected TextView textview_end_time_minute = null;

    protected EditText editText_ca_work_time_pin = null;

    protected Button btnSubmit;

    protected TvCaManager tvCaManager = null;

    protected String[] hours = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23",
    };

    protected String[] minutes = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
            "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
            "58", "59"
    };

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (editText_ca_work_time_pin.hasFocus()) {
                LittleDownTimer.resetMenu();
                return;
            }
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaWorkTimeActivity.this.finish();
                Intent intent = new Intent(CaWorkTimeActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_info_work_time);
        LittleDownTimer.setHandler(handler);

        tvCaManager = TvCaManager.getInstance();
        findViews();
        getInitData();
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaWorkTimeActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaWorkTimeActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();

    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaWorkTimeActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void findViews() {
        linearlayout_start_time_hour = (LinearLayout) findViewById(R.id.linearlayout_start_time_hour);
        linearlayout_start_time_minute = (LinearLayout) findViewById(R.id.linearlayout_start_time_minute);
        linearlayout_end_time_hour = (LinearLayout) findViewById(R.id.linearlayout_end_time_hour);
        linearlayout_end_time_minute = (LinearLayout) findViewById(R.id.linearlayout_end_time_minute);
        textview_start_time_hour = (TextView) findViewById(R.id.textview_start_time_hour);
        textview_start_time_minute = (TextView) findViewById(R.id.textview_start_time_minute);
        textview_end_time_hour = (TextView) findViewById(R.id.textview_end_time_hour);
        textview_end_time_minute = (TextView) findViewById(R.id.textview_end_time_minute);
        editText_ca_work_time_pin = (EditText) findViewById(R.id.editText_ca_work_time_pin);
        textview_start_time_hour.setText(hours[start_hour_idx]);
        textview_start_time_minute.setText(minutes[start_minute_idx]);
        textview_end_time_hour.setText(hours[end_hour_idx]);
        textview_end_time_minute.setText(minutes[end_minute_idx]);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        setOnClickLisenters();
        setOnFocusChangeListeners();
    }

    private void getInitData() {
        CaWorkTimeInfo worktimeinfo = tvCaManager.CaGetWorkTime();
        if (worktimeinfo == null) {
            return;
        }
        if (worktimeinfo.sWorkTimeState == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            textview_start_time_hour.setText(Short.toString(worktimeinfo.sStartHour));
            for (int i = 0; i < hours.length; i++) {
                if (worktimeinfo.sStartHour == Short.parseShort(hours[i])) {
                    start_hour_idx = i;
                    break;
                }
            }

            textview_start_time_minute.setText(Short.toString(worktimeinfo.syStartMin));
            for (int i = 0; i < minutes.length; i++) {
                if (worktimeinfo.syStartMin == Short.parseShort(minutes[i])) {
                    start_minute_idx = i;
                    break;
                }
            }

            textview_end_time_hour.setText(Short.toString(worktimeinfo.syEndHour));
            for (int i = 0; i < hours.length; i++) {
                if (worktimeinfo.syEndHour == Short.parseShort(hours[i])) {
                    end_hour_idx = i;
                    break;
                }
            }

            textview_end_time_minute.setText(Short.toString(worktimeinfo.syEndMin));
            for (int i = 0; i < minutes.length; i++) {
                if (worktimeinfo.syEndMin == Short.parseShort(minutes[i])) {
                    end_minute_idx = i;
                    break;
                }
            }
        } else if (worktimeinfo.sWorkTimeState == RETURN_CODE.ST_CA_RC_CARD_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_card_invalid);
        } else if (worktimeinfo.sWorkTimeState == RETURN_CODE.ST_CA_RC_POINTER_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pointer_invalid);
        } else if (worktimeinfo.sWorkTimeState == RETURN_CODE.ST_CA_RC_UNKNOWN.getRetCode()) {
            toastShow(R.string.st_ca_rc_unknown);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = CaWorkTimeActivity.this.getCurrentFocus().getId();

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_start_time_hour:
                        if (start_hour_idx == hours.length - 1) {
                            start_hour_idx = 0;
                        } else {
                            start_hour_idx++;
                        }
                        textview_start_time_hour.setText(hours[start_hour_idx]);
                        break;
                    case R.id.linearlayout_start_time_minute:
                        if (start_minute_idx == minutes.length - 1) {
                            start_minute_idx = 0;
                        } else {
                            start_minute_idx++;
                        }
                        textview_start_time_minute.setText(minutes[start_minute_idx]);
                        break;
                    case R.id.linearlayout_end_time_hour:
                        if (end_hour_idx == hours.length - 1) {
                            end_hour_idx = 0;
                        } else {
                            end_hour_idx++;
                        }
                        textview_end_time_hour.setText(hours[end_hour_idx]);
                        break;
                    case R.id.linearlayout_end_time_minute:
                        if (end_minute_idx == minutes.length - 1) {
                            end_minute_idx = 0;
                        } else {
                            end_minute_idx++;
                        }
                        textview_end_time_minute.setText(minutes[end_minute_idx]);
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_start_time_hour:
                        if (start_hour_idx == 0) {
                            start_hour_idx = hours.length - 1;
                        } else {
                            start_hour_idx--;
                        }
                        textview_start_time_hour.setText(hours[start_hour_idx]);
                        break;
                    case R.id.linearlayout_start_time_minute:
                        if (start_minute_idx == 0) {
                            start_minute_idx = minutes.length - 1;
                        } else {
                            start_minute_idx--;
                        }
                        textview_start_time_minute.setText(minutes[start_minute_idx]);
                        break;
                    case R.id.linearlayout_end_time_hour:
                        if (end_hour_idx == 0) {
                            end_hour_idx = hours.length - 1;
                        } else {
                            end_hour_idx--;
                        }
                        textview_end_time_hour.setText(hours[end_hour_idx]);
                        break;
                    case R.id.linearlayout_end_time_minute:
                        if (end_minute_idx == 0) {
                            end_minute_idx = minutes.length - 1;
                        } else {
                            end_minute_idx--;
                        }
                        textview_end_time_minute.setText(minutes[end_minute_idx]);
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaWorkTimeActivity.this, CaManagementActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.linearlayout_start_time_hour:
                        linearlayout_start_time_hour.getChildAt(0).setVisibility(View.VISIBLE);
                        linearlayout_start_time_hour.getChildAt(3).setVisibility(View.VISIBLE);
                        System.out.println("----linearlayout_start_time_hour==-"
                                + linearlayout_start_time_hour);
                        break;
                    case R.id.linearlayout_start_time_minute:
                        linearlayout_start_time_minute.getChildAt(0).setVisibility(View.VISIBLE);
                        linearlayout_start_time_minute.getChildAt(3).setVisibility(View.VISIBLE);
                        System.out.println("----linearlayout_start_time_minute==-"
                                + linearlayout_start_time_minute);
                        break;
                    case R.id.linearlayout_end_time_hour:
                        linearlayout_end_time_hour.getChildAt(0).setVisibility(View.VISIBLE);
                        linearlayout_end_time_hour.getChildAt(3).setVisibility(View.VISIBLE);
                        System.out.println("----linearlayout_end_time_hour==-"
                                + linearlayout_end_time_hour);
                        break;
                    case R.id.linearlayout_end_time_minute:
                        linearlayout_end_time_minute.getChildAt(0).setVisibility(View.VISIBLE);
                        linearlayout_end_time_minute.getChildAt(3).setVisibility(View.VISIBLE);
                        System.out.println("----linearlayout_end_time_minute==-"
                                + linearlayout_end_time_minute);
                        break;
                    case R.id.btnSubmit:
                        modifyWorkTime();
                        break;
                    default:
                        break;
                }
            }
        };
        linearlayout_start_time_hour.setOnClickListener(listener);
        linearlayout_start_time_minute.setOnClickListener(listener);
        linearlayout_end_time_hour.setOnClickListener(listener);
        linearlayout_end_time_minute.setOnClickListener(listener);
        btnSubmit.setOnClickListener(listener);
    }

    private void modifyWorkTime() {
        String pin = editText_ca_work_time_pin.getText().toString();
        if (pin.length() < 6) {
            toastShow(R.string.st_ca_rc_pin_len);
            return;
        }
        CaWorkTimeInfo worktimeinfo = new CaWorkTimeInfo();
        worktimeinfo.sStartHour = Short.parseShort(textview_start_time_hour.getText().toString());
        worktimeinfo.syStartMin = Short.parseShort(textview_start_time_minute.getText().toString());
        worktimeinfo.syEndHour = Short.parseShort(textview_end_time_hour.getText().toString());
        worktimeinfo.syEndMin = Short.parseShort(textview_end_time_minute.getText().toString());

        short ret = tvCaManager.CaSetWorkTime(pin, worktimeinfo);
        if (ret == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            toastShow(R.string.st_ca_rc_ok);
        } else if (ret == RETURN_CODE.ST_CA_RC_CARD_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_card_invalid);
        } else if (ret == RETURN_CODE.ST_CA_RC_POINTER_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pointer_invalid);
        } else if (ret == RETURN_CODE.ST_CA_RC_UNKNOWN.getRetCode()) {
            toastShow(R.string.st_ca_rc_unknown);
        } else if (ret == RETURN_CODE.ST_CA_RC_PIN_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pin_invalid);
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
        linearlayout_start_time_hour.setOnFocusChangeListener(FocuschangesListener);
        linearlayout_start_time_minute.setOnFocusChangeListener(FocuschangesListener);
        linearlayout_end_time_hour.setOnFocusChangeListener(FocuschangesListener);
        linearlayout_end_time_minute.setOnFocusChangeListener(FocuschangesListener);
    }

    private void toastShow(int resId) {
        Toast toast = new Toast(this);
        TextView MsgShow = new TextView(this);
        toast.setDuration(Toast.LENGTH_LONG);
        MsgShow.setTextColor(Color.RED);
        MsgShow.setTextSize(25);
        MsgShow.setText(resId);
        toast.setView(MsgShow);
        toast.show();
    }
}
