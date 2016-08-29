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
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.mstar.android.tv.TvCaManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.util.Tools;

public class CaManagementActivity extends Activity {
    protected LinearLayout linearlayout_ca_mg_modify_pin;

    protected LinearLayout linearlayout_ca_mg_modify_time;

    protected LinearLayout linearlayout_ca_mg_watch_level;

    protected LinearLayout linearlayout_card_paired;

    protected LinearLayout linearlayout_child_parent_card_feed;

    protected Intent intent = new Intent();

    private TvCaManager tvCaManager = null;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaManagementActivity.this.finish();
                Intent intent = new Intent(CaManagementActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_card_management);
        LittleDownTimer.setHandler(handler);
        tvCaManager = TvCaManager.getInstance();
        findViews();
        setOnClickLisenters();
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaManagementActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaManagementActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();
    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaManagementActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void findViews() {
        linearlayout_ca_mg_modify_pin = (LinearLayout) findViewById(R.id.linearlayout_ca_mg_modify_pin);
        linearlayout_ca_mg_modify_time = (LinearLayout) findViewById(R.id.linearlayout_ca_mg_modify_time);
        linearlayout_ca_mg_watch_level = (LinearLayout) findViewById(R.id.linearlayout_ca_mg_watch_level);
        linearlayout_card_paired = (LinearLayout) findViewById(R.id.linearlayout_card_paired);
        linearlayout_child_parent_card_feed = (LinearLayout) findViewById(R.id.linearlayout_child_parent_card_feed);
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.linearlayout_ca_mg_modify_pin:
                        intent.setClass(CaManagementActivity.this, CaInfoModifyPinActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_mg_modify_time:
                        intent.setClass(CaManagementActivity.this, CaWorkTimeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_mg_watch_level:
                        intent.setClass(CaManagementActivity.this, CaWatchLevelActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_card_paired:
                        short result = -1;
                        result = tvCaManager.CaIsPaired((short) 1, tvCaManager.CaGetPlatformID()
                                + "");
                        if (result == 0x00) {
                            Tools.toastShow(R.string.cdca_rc_ok, CaManagementActivity.this);
                        } else if (result == 0x07) {
                            Tools.toastShow(R.string.cdca_rc_card_pairother,
                                    CaManagementActivity.this);

                        } else if (result == 0x0D) {
                            Tools.toastShow(R.string.cdca_rc_card_nopair, CaManagementActivity.this);

                        } else if (result == 0x03) {
                            Tools.toastShow(R.string.cdca_rc_invalid, CaManagementActivity.this);

                        }
                        break;
                    case R.id.linearlayout_child_parent_card_feed:
                        intent.setClass(CaManagementActivity.this, CaCardFeedActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        linearlayout_ca_mg_modify_pin.setOnClickListener(listener);
        linearlayout_ca_mg_modify_time.setOnClickListener(listener);
        linearlayout_ca_mg_watch_level.setOnClickListener(listener);
        linearlayout_card_paired.setOnClickListener(listener);
        linearlayout_child_parent_card_feed.setOnClickListener(listener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaManagementActivity.this, CaActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
