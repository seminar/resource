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

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tvframework.MstarBaseActivity;

import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tvapi.common.vo.CaptionOptionSetting;
import com.mstar.android.tvapi.common.vo.CCSetting;
import android.widget.Toast;
import android.util.Log;
import com.mstar.tv.tvplayer.ui.MenuConstants;

public class DigitalCCActivity extends MstarBaseActivity {
    private static final String TAG = "DigitalCCActivity";

    private TvCcManager mCCManager = null;

    private CaptionOptionSetting mSetting = null;

    private CaptionOptionSetting mDefaultSetting = null;

    private int mIndex = 0;

    private ComboButton mComboBtnButtonMode = null;

    private ComboButton mComboBtnButtonFontStyle = null;

    private ComboButton mComboBtnButtonFontSize = null;

    private ComboButton mComboBtnButtonFontEdgeStyle = null;

    private ComboButton mComboBtnButtonFontEdgeColor = null;

    private ComboButton mComboBtnButtonFGColor = null;

    private ComboButton mComboBtnButtonBGColor = null;

    private ComboButton mComboBtnButtonFGOpacity = null;

    private ComboButton mComboBtnButtonBGOpacity = null;

    private CCSetting mCcSetting = null;

    private short mCcMode = TvCcManager.CAPTION_OFF;

    private ComboButton mComboBtnButtonItalicAttr = null;

    private ComboButton mComboBtnButtonUnderlineAttr = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digital_cc_setting);

        initItems();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mCCManager.exitPreviewCc();

        if (mComboBtnButtonMode.getIdx() == TvCcManager.CC_MODE_DEFAULT) {
            mCCManager.setAdvancedSetting(mDefaultSetting, mIndex);
        } else {
            mCCManager.setAdvancedSetting(mSetting, mIndex);
        }
        if (TvCcManager.CAPTION_OFF != mCcMode) {
            mCCManager.startCc();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null == mCCManager) {
            mCCManager = TvCcManager.getInstance();
        }
        if (null == mDefaultSetting) {
            mDefaultSetting = new CaptionOptionSetting(
                    TvCcManager.CC_MODE_DEFAULT,
                    TvCcManager.CC_COLOR_WHITE,
                    TvCcManager.CC_COLOR_BLACK,
                    TvCcManager.CC_OPACITY_SOLID,
                    TvCcManager.CC_OPACITY_SOLID,
                    TvCcManager.CC_FONT_SIZE_NORMAL,
                    TvCcManager.CC_FONT_STYLE_STYLE1,
                    TvCcManager.CC_EDGE_STYLE_NONE,
                    TvCcManager.CC_COLOR_BLACK,
                    TvCcManager.CC_ITALIC_OFF,
                    TvCcManager.CC_UNDERLINE_OFF
                    );
        }
        mCCManager.stopCc();

        mSetting = mCCManager.getAdvancedSetting(mIndex);

        mComboBtnButtonMode.setIdx(mSetting.currProgInfoIsDefault);
        mComboBtnButtonFontStyle.setIdx(mSetting.currProgInfoFontStyle);
        mComboBtnButtonFontSize.setIdx(mSetting.currProgInfoFontSize);
        mComboBtnButtonFontEdgeStyle.setIdx(mSetting.currProgInfoEdgeStyle);
        mComboBtnButtonFontEdgeColor.setIdx(mSetting.currProgInfoEdgeColor);
        mComboBtnButtonFGColor.setIdx(mSetting.currProgInfoFGColor);
        mComboBtnButtonBGColor.setIdx(mSetting.currProgInfoBGColor);
        mComboBtnButtonFGOpacity.setIdx(mSetting.currProgInfoFGOpacity);
        mComboBtnButtonBGOpacity.setIdx(mSetting.currProgInfoBGOpacity);
        mComboBtnButtonItalicAttr.setIdx(mSetting.currProgInfoItalicsAttr);
        mComboBtnButtonUnderlineAttr.setIdx(mSetting.currProgInfoUnderlineAttr);

        updateUI();
        mCCManager.creatPreviewCcWindow();
        drawPreviewCc();
    }

    private void initItems() {

        mCCManager = TvCcManager.getInstance();
        mCcSetting = mCCManager.getCCSetting();
        mCcMode = mCcSetting.ccMode;

        mIndex = getIntent().getShortExtra(MenuConstants.INDEX_KEY, (short) TvCcManager.CAPTION_OFF);
        try {
            mSetting = mCCManager.getAdvancedSetting(mIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCCManager.setAdvancedSetting(mSetting, mIndex);
        if (null == mDefaultSetting) {
            mDefaultSetting = new CaptionOptionSetting(
                    TvCcManager.CC_MODE_DEFAULT,
                    TvCcManager.CC_COLOR_WHITE,
                    TvCcManager.CC_COLOR_BLACK,
                    TvCcManager.CC_OPACITY_SOLID,
                    TvCcManager.CC_OPACITY_SOLID,
                    TvCcManager.CC_FONT_SIZE_NORMAL,
                    TvCcManager.CC_FONT_STYLE_STYLE1,
                    TvCcManager.CC_EDGE_STYLE_NONE,
                    TvCcManager.CC_COLOR_BLACK,
                    TvCcManager.CC_ITALIC_OFF,
                    TvCcManager.CC_UNDERLINE_OFF
                    );
        }

        mComboBtnButtonMode = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_mode),
                R.id.linearlayout_setting_digital_cc_mode, 1, 2, true) {
            @Override
            public void doUpdate() {
                updateUI();
                drawPreviewCc();
            }
        };
        mComboBtnButtonFontStyle = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_font_style),
                R.id.linearlayout_setting_digital_cc_font_style, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonFontSize = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_font_size),
                R.id.linearlayout_setting_digital_cc_font_size, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonFontEdgeStyle = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_font_edge_style),
                R.id.linearlayout_setting_digital_cc_font_edge_style, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonFontEdgeColor = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_caption_color),
                R.id.linearlayout_setting_digital_cc_font_edge_color, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonFGColor = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_caption_color),
                R.id.linearlayout_setting_digital_cc_fg_color, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonBGColor = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_caption_color),
                R.id.linearlayout_setting_digital_cc_bg_color, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonFGOpacity = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_caption_opacity),
                R.id.linearlayout_setting_digital_cc_fg_opacity, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonBGOpacity = new ComboButton(this, getResources().getStringArray(
                R.array.setting_digital_cc_caption_opacity),
                R.id.linearlayout_setting_digital_cc_bg_opacity, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonItalicAttr = new ComboButton(this,
                getResources().getStringArray(R.array.setting_digital_cc_italic_attr),
                R.id.linearlayout_setting_digital_cc_italic_attr, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
        mComboBtnButtonUnderlineAttr = new ComboButton(this,
                getResources().getStringArray(R.array.setting_digital_cc_underline_attr),
                R.id.linearlayout_setting_digital_cc_underline_attr, 1, 2, true) {
            @Override
            public void doUpdate() {
                drawPreviewCc();
            }
        };
    }

    private void drawPreviewCc() {
        Log.d(TAG, "drawPreviewCc()");

        if (mComboBtnButtonMode.getIdx() == TvCcManager.CC_MODE_DEFAULT) {
            mCCManager.drawPreviewCc(mDefaultSetting);
        } else {
            mSetting.currProgInfoIsDefault = mComboBtnButtonMode.getIdx();
            mSetting.currProgInfoFontStyle = mComboBtnButtonFontStyle.getIdx();
            mSetting.currProgInfoFontSize = mComboBtnButtonFontSize.getIdx();
            mSetting.currProgInfoEdgeStyle = mComboBtnButtonFontEdgeStyle.getIdx();
            mSetting.currProgInfoEdgeColor = mComboBtnButtonFontEdgeColor.getIdx();
            mSetting.currProgInfoFGColor = mComboBtnButtonFGColor.getIdx();
            mSetting.currProgInfoBGColor = mComboBtnButtonBGColor.getIdx();
            mSetting.currProgInfoFGOpacity = mComboBtnButtonFGOpacity.getIdx();
            mSetting.currProgInfoBGOpacity = mComboBtnButtonBGOpacity.getIdx();
            mSetting.currProgInfoItalicsAttr = mComboBtnButtonItalicAttr.getIdx();
            mSetting.currProgInfoUnderlineAttr = mComboBtnButtonUnderlineAttr.getIdx();

            if(TvCcManager.CC_FONT_STYLE_DEFAULT == mSetting.currProgInfoFontStyle)
                mSetting.currProgInfoFontStyle = TvCcManager.CC_FONT_STYLE_STYLE1;
            if(TvCcManager.CC_FONT_SIZE_DEFAULT == mSetting.currProgInfoFontSize)
                mSetting.currProgInfoFontSize = TvCcManager.CC_FONT_SIZE_NORMAL;
            if(TvCcManager.CC_EDGE_STYLE_DEFAULT == mSetting.currProgInfoEdgeStyle)
                mSetting.currProgInfoEdgeStyle = TvCcManager.CC_EDGE_STYLE_NONE;
            if(TvCcManager.CC_COLOR_DEFAULT == mSetting.currProgInfoEdgeColor)
                mSetting.currProgInfoEdgeColor = TvCcManager.CC_COLOR_BLACK;
            if(TvCcManager.CC_COLOR_DEFAULT == mSetting.currProgInfoFGColor)
                mSetting.currProgInfoFGColor = TvCcManager.CC_COLOR_WHITE;
            if(TvCcManager.CC_COLOR_DEFAULT == mSetting.currProgInfoBGColor)
                mSetting.currProgInfoBGColor = TvCcManager.CC_COLOR_BLACK;
            if(TvCcManager.CC_OPACITY_DEFAULT == mSetting.currProgInfoFGOpacity)
                mSetting.currProgInfoFGOpacity = TvCcManager.CC_OPACITY_SOLID;
            if(TvCcManager.CC_OPACITY_DEFAULT == mSetting.currProgInfoBGOpacity)
                mSetting.currProgInfoBGOpacity = TvCcManager.CC_OPACITY_SOLID;

            mCCManager.drawPreviewCc(mSetting);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                try {
                    mCCManager.exitPreviewCc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(DigitalCCActivity.this, ClosedCaptionActivity.class);
                intent.putExtra(ClosedCaptionActivity.FOCUS_CC_ADVANCED_SETTING, true);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateUI() {
        boolean enable = true;

        if (mComboBtnButtonMode.getIdx() == TvCcManager.CC_MODE_DEFAULT) {
            enable = false;
        } else {
            enable = true;
        }
        mComboBtnButtonFontStyle.setEnable(enable);
        mComboBtnButtonFontSize.setEnable(enable);
        mComboBtnButtonFontEdgeStyle.setEnable(enable);
        mComboBtnButtonFontEdgeColor.setEnable(enable);
        mComboBtnButtonFGColor.setEnable(enable);
        mComboBtnButtonBGColor.setEnable(enable);
        mComboBtnButtonFGOpacity.setEnable(enable);
        mComboBtnButtonBGOpacity.setEnable(enable);
        mComboBtnButtonItalicAttr.setEnable(enable);
        mComboBtnButtonUnderlineAttr.setEnable(enable);
    }
}
