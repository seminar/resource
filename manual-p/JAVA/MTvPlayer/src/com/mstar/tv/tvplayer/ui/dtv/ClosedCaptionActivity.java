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

package com.mstar.tv.tvplayer.ui.dtv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tvapi.common.vo.CCSetting;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.MenuConstants;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;

public class ClosedCaptionActivity extends MstarBaseActivity {

    private static final String TAG = "ClosedCaptionActivity";

    private TvCcManager mTvCcManager = null;

    private ComboButton mComboBtnButtonCcMode = null;

    private ComboButton mComboBtnButtonBasicSelection = null;

    private ComboButton mComboBtnButtonAdvancedSelection = null;

    private MyButton mButtonCcAdvanceSetting = null;

    private CCSetting mSetting = null;

    public static final String FOCUS_CC_ADVANCED_SETTING = "focusCcAdvancedSetting";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc_setting);
        initItems();
        updateUI();
    }

    private void initItems() {
        mTvCcManager = TvCcManager.getInstance();
        mSetting = mTvCcManager.getCCSetting();

        mComboBtnButtonCcMode = new ComboButton(ClosedCaptionActivity.this, getResources().getStringArray(
                R.array.str_arr_setting_cc_mode_vals), R.id.linearlayout_setting_cc_mode, 1, 2,
                true) {
            @Override
            public void doUpdate() {
                setCCSetting();
            }
        };

        mComboBtnButtonBasicSelection = new ComboButton(ClosedCaptionActivity.this, getResources().getStringArray(
                R.array.str_arr_setting_cc_basic_selection_vals),
                R.id.linearlayout_setting_cc_basic_selection, 1, 2, true) {
            @Override
            public void doUpdate() {
                setCCSetting();
            }
        };

        mComboBtnButtonAdvancedSelection = new ComboButton(ClosedCaptionActivity.this, getResources().getStringArray(
                R.array.str_arr_setting_cc_advanced_selection_vals),
                R.id.linearlayout_setting_cc_advanced_selection, 1, 2, true) {
            @Override
            public void doUpdate() {
                setCCSetting();
                updateUI();
            }
        };

        mButtonCcAdvanceSetting = new MyButton(ClosedCaptionActivity.this, R.id.linearlayout_setting_cc_advanced_settings) {
            @Override
            public void doUpdate() {
                mTvCcManager.stopCc();
                Intent intent = new Intent(ClosedCaptionActivity.this, DigitalCCActivity.class);
                intent.putExtra(MenuConstants.INDEX_KEY, mSetting.advancedMode);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                setCCSetting();
                Intent intent = new Intent(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.OPTION_PAGE);
                startActivity(intent);
                finish();
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            if (getIntent().getBooleanExtra(FOCUS_CC_ADVANCED_SETTING, false) == true) {
                mButtonCcAdvanceSetting.setFocused();
            }
        }

        /* read CCSetting into variable mSetting and refresh UI */
        updateUI();

        if (TvCcManager.ATSC_CC_MODE_OFF != mSetting.ccMode) {
            mTvCcManager.startCc();
        }
        super.onResume();
    }

    private void updateUI() {
        mSetting = mTvCcManager.getCCSetting();
        mComboBtnButtonCcMode.setIdx(mSetting.ccMode);
        mComboBtnButtonBasicSelection.setIdx(mSetting.basicMode);
        mComboBtnButtonAdvancedSelection.setIdx(mSetting.advancedMode);

        /*
        * Disable the Button "CC Advance Setting" When
        * the ComboBtn "Advanced Seletion" set to be "Off"
        */
        if (mComboBtnButtonAdvancedSelection.getIdx() == 0) {
            mButtonCcAdvanceSetting.setEnable(false);
        } else {
            mButtonCcAdvanceSetting.setEnable(true);
        }
    }

    private void setCCSetting() {
        mSetting.ccMode = mComboBtnButtonCcMode.getIdx();
        mSetting.basicMode = mComboBtnButtonBasicSelection.getIdx();
        mSetting.advancedMode = mComboBtnButtonAdvancedSelection.getIdx();

        /* Need to reset CC to take effect */
        mTvCcManager.stopCc();
        mTvCcManager.setCCSetting(mSetting);

        /* Read ccmode in case of value not set successfully */
        if (TvCcManager.ATSC_CC_MODE_OFF != mTvCcManager.getCCSetting().ccMode) {
            mTvCcManager.startCc();
        }
    }
}
