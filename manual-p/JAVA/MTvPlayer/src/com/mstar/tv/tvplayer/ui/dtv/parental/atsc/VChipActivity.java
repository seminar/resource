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

package com.mstar.tv.tvplayer.ui.dtv.parental.atsc;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.MenuConstants;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tvframework.MstarBaseActivity;

import java.util.ArrayList;

import com.mstar.tv.tvplayer.ui.component.PasswordChangeDialog;

public class VChipActivity extends MstarBaseActivity {
    private static final String TAG = "VChipActivity";

    private ComboButton mComboBtnButtonLock;

    private ComboButton mComboBtnBlockUnratedTv;

    private MyButton mButtonUsaParentalControl;

    private MyButton mButtonCanadianParentalControl;

    private MyButton mButtonVchipRrt5Setting;

    private MyButton mButtonVchipResetRrt5;

    private MyButton mButtonVchipChangePassword;

    private int mCurSource = TvCommonManager.INPUT_SOURCE_ATV;

    private Boolean mFlag = true;

    private Boolean mFocus = true;

    private PasswordChangeDialog mPasswordChange = null;

    private TvAtscChannelManager manager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vchip);
        initItems();
    }

    private void initItems() {
        manager = TvAtscChannelManager.getInstance();
        int value = manager.getBlockSysLockMode();
        mFocus = (value != 0);

        mPasswordChange = new PasswordChangeDialog(VChipActivity.this) {
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_MENU == keyCode || KeyEvent.KEYCODE_BACK == keyCode) {
                    cancel();
                    return true;
                }
                return false;
            }

            @Override
            public String onCheckPassword() {
                return MenuConstants.getSharedPreferencesValue(VChipActivity.this, MenuConstants.VCHIPPASSWORD, MenuConstants.VCHIPPASSWORD_DEFAULTVALUE);
            }
            @Override
            public boolean setPassword(String newPassword) {
                MenuConstants.setSharedPreferencesValue(VChipActivity.this, MenuConstants.VCHIPPASSWORD, newPassword);
                return true;
            }

            @Override
            public void onShow() {
                View view = VChipActivity.this.findViewById(android.R.id.content);
                if (null != view) {
                    view.animate().alpha(0f)
                            .setDuration(VChipActivity.this.getResources().getInteger(android.R.integer.config_shortAnimTime));
                }
            }

            @Override
            public void onDismiss() {
                View view = VChipActivity.this.findViewById(android.R.id.content);
                if (null != view) {
                    view.animate().alpha(1f)
                            .setDuration(VChipActivity.this.getResources().getInteger(android.R.integer.config_shortAnimTime));
                }
            }
        };

        mComboBtnButtonLock = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_vchip_button_lock_vals), R.id.linearlayout_vchip_act_button_lock,
                1, 2, true) {
            @Override
            public void doUpdate() {
                super.doUpdate();
                int blockSysLockMode = mComboBtnButtonLock.getIdx();
                if (1 == blockSysLockMode){
                    manager.setBlockSysLockMode(true);
                } else {
                    manager.setBlockSysLockMode(false);
                }
                updateUI();
            }
        };

        mComboBtnBlockUnratedTv = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_vchip_block_unrated_tv_vals),
                R.id.linearlayout_vchip_act_block_unrated_tv, 1, 2, true) {
            @Override
            public void doUpdate() {
                super.doUpdate();
                int blockUnratedTvBtnIdx = mComboBtnBlockUnratedTv.getIdx();
                if (1 == blockUnratedTvBtnIdx){
                    manager.setBlockUnlockUnrated(true);
                } else {
                    manager.setBlockUnlockUnrated(false);
                }
            }
        };

        mButtonUsaParentalControl = new MyButton(this, R.id.linearlayout_vchip_act_usa_parental_control) {
            @Override
            public void doUpdate() {
                Intent intent_usa_par = new Intent(VChipActivity.this,
                        UsaParLocksActivity.class);
                intent_usa_par.putExtra(MenuConstants.TITLE_KEY,
                        R.string.str_vchip_usa_parental_control);
                startActivity(intent_usa_par);
            }
        };

        mButtonCanadianParentalControl = new MyButton(this, R.id.linearlayout_vchip_act_canadian_parental_control) {
            @Override
            public void doUpdate() {
                Intent intent_can_par = new Intent();
                intent_can_par.setClass(VChipActivity.this, CanParLocksActivity.class);
                intent_can_par.putExtra(MenuConstants.TITLE_KEY,
                        R.string.str_vchip_canadian_parental_control);
                startActivity(intent_can_par);
            }
        };

        mButtonVchipRrt5Setting = new MyButton(this, R.id.linearlayout_vchip_act_rrt5_setting) {
            @Override
            public void doUpdate() {
                Intent intent_rrt5_setting = new Intent();
                intent_rrt5_setting.setClass(VChipActivity.this, RRT5SettingActivity.class);
                startActivity(intent_rrt5_setting);
                setVisible(false);
            }
        };

        mButtonVchipResetRrt5 = new MyButton(this, R.id.linearlayout_vchip_act_reset_rrt5) {
            @Override
            public void doUpdate() {
                creatResetRRT5Dialog();
            }
        };

        mButtonVchipChangePassword = new MyButton(this, R.id.linearlayout_vchip_act_change_password) {
            @Override
            public void doUpdate() {
                mPasswordChange.show();
            }
        };

        mCurSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        if ((mCurSource == TvCommonManager.INPUT_SOURCE_ATV || manager.getRRT5NoDimension() <= 0 || !mFocus)) {
            mFlag = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        setVisible(true);
    }

    public void updateUI() {
        Boolean bEnable = false;
        int value = manager.getBlockSysLockMode();
        mCurSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (0 == value) {
            // Block Disabled
            mFocus = false;
        } else {
            // Block Enabled
            mFocus = true;
        }
        if (TvCommonManager.INPUT_SOURCE_ATV == mCurSource || manager.getRRT5NoDimension() <= 0 || false == mFocus) {
            mFlag = false;
        }
        if (manager.getRRT5Dimension().size() <= 0) {
            mFlag = false;
        }
        mComboBtnButtonLock.setIdx(value);

        boolean bBlockUnratedTvStatus = manager.getBlockUnlockUnrated();
        if (true == bBlockUnratedTvStatus) {
            mComboBtnBlockUnratedTv.setIdx(1);
        } else {
            mComboBtnBlockUnratedTv.setIdx(0);
        }

        if (1 == value) {
            bEnable = true;
        } else {
            bEnable = false;
        }

        mComboBtnBlockUnratedTv.setEnable(bEnable);
        mButtonUsaParentalControl.setEnable(bEnable);
        mButtonCanadianParentalControl.setEnable(bEnable);
        mButtonVchipRrt5Setting.setEnable(mFlag);
        mButtonVchipResetRrt5.setEnable(mFlag);
    }

    private void creatResetRRT5Dialog() {
        final Dialog resetRRT5Dialog = new Dialog(this, R.style.dialog);
        resetRRT5Dialog.setContentView(R.layout.resetrrt5);
        Button sureBtn = (Button) resetRRT5Dialog.findViewById(R.id.sure_reset_btn);
        Button cancelBtn = (Button) resetRRT5Dialog.findViewById(R.id.cancel_reset_btn);

        sureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.resetRRTSetting();
                mFlag = false;
                resetRRT5Dialog.dismiss();
                updateUI();
                ((LinearLayout) findViewById(R.id.linearlayout_vchip_act_button_lock))
                        .requestFocus();
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                resetRRT5Dialog.dismiss();
            }
        });

        resetRRT5Dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keycode, KeyEvent keyEvent) {
                switch (keycode) {
                    case KeyEvent.KEYCODE_BACK:
                        resetRRT5Dialog.dismiss();
                        break;
                }
                return false;
            }
        });
        resetRRT5Dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TvIntent.MAINMENU);
        intent.putExtra("currentPage", MainMenuActivity.OPTION_PAGE);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
