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

package com.mstar.tv.tvplayer.ui.holder;

import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;


import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvS3DManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;

public class MenuOf3DViewHolder {
    protected static final int STEP = 1;

    protected ComboButton comboBtn3DSelfAdaptiveDetect;

    protected ComboButton comboBtn3DConversion;

    protected ComboButton comboBtn3DTo2D;

    protected ComboButton comboBtn3DDepth;

    protected ComboButton comboBtn3DOffset;

    protected ComboButton comboBtn3DOutputAspect;

    protected ComboButton comboBtnLRViewSwitch;

    protected Activity activity;

    protected TvS3DManager tvS3DManager = null;

    private boolean mThreeDDepthEnable;

    public MenuOf3DViewHolder(Activity act) {
        this.activity = act;
        tvS3DManager = TvS3DManager.getInstance();
        mThreeDDepthEnable = TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_TV_CONFIG_THREED_DEPTH);
    }

    public void findViews() {
        comboBtn3DSelfAdaptiveDetect = new ComboButton(activity, activity.getResources()
                .getStringArray(R.array.str_arr_3d_self_adaptive_detecttriple_vals),
                R.id.linearlayout_3d_self_adaptive_detecttriple, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    tvS3DManager
                            .setSelfAdaptiveDetectMode(comboBtn3DSelfAdaptiveDetect.getIdx());
                }
                super.doUpdate();
                SetFocusableFor3DConversion();
                SetFocusableFor3DDepthandOffset();
                comboBtn3DConversion.setIdx(tvS3DManager.get3dDisplayFormat());
                if (comboBtn3DSelfAdaptiveDetect.getIdx() == TvS3DManager.THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_OFF)
                    comboBtnLRViewSwitch.setFocusable(false);
                else
                    comboBtnLRViewSwitch.setFocusable(true);
            }
        };
        comboBtn3DSelfAdaptiveDetect.setItemEnable(TvS3DManager.THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_REALTIME, false);
        comboBtn3DConversion = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_3d_3dconversion_vals), R.id.linearlayout_3d_conversion, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    if ((comboBtn3DConversion.getIdx() != TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE)
                            && (TvPictureManager.getInstance().getMWEDemoMode() != TvPictureManager.MWE_DEMO_MODE_OFF)) {
                        TvPictureManager.getInstance().setMWEDemoMode(TvPictureManager.MWE_DEMO_MODE_OFF);
                        Toast toast = Toast.makeText(activity, R.string.str_3d_toast, 5);
                        toast.show();
                    }
                    tvS3DManager.set3dDisplayFormat(comboBtn3DConversion.getIdx());
                }
                super.doUpdate();
                SetFocusableFor3DDepthandOffset();
                if (comboBtn3DConversion.getIdx() == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE)
                    comboBtnLRViewSwitch.setFocusable(false);
                else
                    comboBtnLRViewSwitch.setFocusable(true);
            }
        };

        if (TvCommonManager.getInstance() != null) {
            if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_HDMI
                    || TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_HDMI2
                    || TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_HDMI3
                    || TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_HDMI4) {
                comboBtn3DConversion.setItemEnable(
                        TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_AUTO,
                        false);
            }
        }
        comboBtn3DTo2D = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_3d_3dto2d_vals), R.id.linearlayout_3d_3dto2d, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    tvS3DManager.set3DTo2DDisplayMode(comboBtn3DTo2D.getIdx());
                }
                if (comboBtn3DTo2D.getIdx() != TvS3DManager.THREE_DIMENSIONS_VIDEO_3DTO2D_NONE) {
                    comboBtn3DConversion.setIdx(0);// 0 for off
                }
                super.doUpdate();
                comboBtnLRViewSwitch.setFocusable(false);
            }
        };
        comboBtn3DDepth = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_3d_3ddepth_vals), R.id.linearlayout_3d_3ddepth, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    tvS3DManager.set3DDepthMode(comboBtn3DDepth.getIdx());
                }
                super.doUpdate();
            }
        };
        if (mThreeDDepthEnable == false) {
            comboBtn3DDepth.setVisibility(false);
        }
        comboBtn3DOffset = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_3d_3doffset_vals), R.id.linearlayout_3d_3doffset, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    tvS3DManager.set3DOffsetMode(comboBtn3DOffset.getIdx());
                }
                super.doUpdate();
            }
        };
        comboBtn3DOutputAspect = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_3d_3doutputaspect_vals), R.id.linearlayout_3d_3doutputaspect, 1, 2,
                true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    tvS3DManager
                            .setThreeDVideoOutputAspectMode(comboBtn3DOutputAspect.getIdx());
                }
                super.doUpdate();
            }
        };
        comboBtn3DOutputAspect.setVisibility(false);
        comboBtnLRViewSwitch = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_3d_lrswitch_vals), R.id.linearlayout_3d_lrswitch, 1, 2, true) {
            @Override
            public void doUpdate() {
                if (tvS3DManager != null) {
                    tvS3DManager
                            .setThreeDVideoLrViewSwitch(comboBtnLRViewSwitch.getIdx());
                }
                super.doUpdate();
            }
        };
        setOnFocusChangeListeners();
        setOnClickListeners();
        LoadDataToUI();
        comboBtn3DTo2D.setFocused();
        // comboBtnLRViewSwitch.setFocusable(false);
    }

    public void toHandleMsg(Message msg) {
    }

    public void LoadDataToUI() {
        //Refine performance with query DB by content provider to reduce startup time in 3D page.
        int inputSrcType = TvCommonManager.getInstance().getCurrentTvInputSource();

        Cursor cursorHdmi = this.activity.getApplicationContext().getContentResolver().query(
            Uri.parse("content://mstar.tv.usersetting/threedvideomode/inputsrc/"
                        + TvCommonManager.INPUT_SOURCE_HDMI), null, null, null, null);
        int threeDSelfdetectHdmi = 0;
        if (cursorHdmi.moveToFirst()) {
            threeDSelfdetectHdmi = cursorHdmi.getInt(cursorHdmi.getColumnIndex("eThreeDVideoSelfAdaptiveDetect"));
        }
        cursorHdmi.close();

        Cursor cursor = this.activity.getApplicationContext().getContentResolver().query(
            Uri.parse("content://mstar.tv.usersetting/threedvideomode/inputsrc/"
                        + inputSrcType), null, null, null, null);
        if (cursor.moveToFirst()) {
            int threeDSelfdectect = cursor.getInt(cursor.getColumnIndex("eThreeDVideoSelfAdaptiveDetect"));
            comboBtn3DSelfAdaptiveDetect.setIdx(threeDSelfdectect);
            if (threeDSelfdetectHdmi == TvS3DManager.THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_OFF) {
                comboBtn3DConversion.setIdx(cursor.getInt(cursor.getColumnIndex("eThreeDVideoDisplayFormat")));
            } else {
                comboBtn3DConversion.setIdx(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_AUTO);
            }
            comboBtn3DTo2D.setIdx(cursor.getInt(cursor.getColumnIndex("eThreeDVideo3DTo2D")));
            comboBtn3DDepth.setIdx(cursor.getInt(cursor.getColumnIndex("eThreeDVideo3DDepth")));
            comboBtn3DOffset.setIdx(cursor.getInt(cursor.getColumnIndex("eThreeDVideo3DOffset")));
            comboBtn3DOutputAspect.setIdx(cursor.getInt(cursor.getColumnIndex("eThreeDVideo3DOutputAspect")));
        }
        cursor.close();

        comboBtnLRViewSwitch.setIdx(tvS3DManager.getThreeDVideoLrViewSwitch());
        SetFocusableFor3DConversion();
        SetFocusableFor3DDepthandOffset();
    }

    public void updateUI() {
        // support no 3d function in MWE
        if (TvPictureManager.getInstance().getMWEDemoMode() == TvPictureManager.MWE_DEMO_MODE_OFF) {
            if (TvChannelManager.getInstance().isSignalStabled()) {
                comboBtn3DSelfAdaptiveDetect.setFocusable(true);
            } else {
                comboBtn3DSelfAdaptiveDetect.setFocusable(false);
            }
            comboBtn3DConversion.setFocusable(true);
            comboBtn3DTo2D.setFocusable(true);
            comboBtn3DDepth.setFocusable(true);
            comboBtn3DOffset.setFocusable(true);
            comboBtn3DOutputAspect.setFocusable(true);
            comboBtnLRViewSwitch.setFocusable(true);
        } else {
            comboBtn3DSelfAdaptiveDetect.setFocusable(false);
            comboBtn3DConversion.setFocusable(false);
            comboBtn3DTo2D.setFocusable(false);
            comboBtn3DDepth.setFocusable(false);
            comboBtn3DOffset.setFocusable(false);
            comboBtn3DOutputAspect.setFocusable(false);
            comboBtnLRViewSwitch.setFocusable(false);
        }
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
        comboBtn3DSelfAdaptiveDetect.setOnClickListener(comoBtnOnClickListener);
        comboBtn3DConversion.setOnClickListener(comoBtnOnClickListener);
        comboBtn3DTo2D.setOnClickListener(comoBtnOnClickListener);
        comboBtn3DDepth.setOnClickListener(comoBtnOnClickListener);
        comboBtn3DOffset.setOnClickListener(comoBtnOnClickListener);
        comboBtn3DOutputAspect.setOnClickListener(comoBtnOnClickListener);
        comboBtnLRViewSwitch.setOnClickListener(comoBtnOnClickListener);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener comboBtnFocusListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.GONE);
                    container.getChildAt(3).setVisibility(View.GONE);
                }
            }
        };
        comboBtn3DSelfAdaptiveDetect.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtn3DConversion.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtn3DTo2D.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtn3DDepth.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtn3DOffset.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtn3DOutputAspect.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnLRViewSwitch.setOnFocusChangeListener(comboBtnFocusListener);
    }

    private void SetFocusableFor3DConversion() {
        if (tvS3DManager.getSelfAdaptiveDetectMode() !=  TvS3DManager.THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_OFF) {
            comboBtn3DConversion.setFocusable(false);
        } else {
            comboBtn3DConversion.setFocusable(true);
        }
    }

    private void SetFocusableFor3DDepthandOffset() {
        if (tvS3DManager.get3dDisplayFormat() == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE) {
            comboBtn3DDepth.setFocusable(false);
            comboBtn3DOffset.setFocusable(false);
            comboBtnLRViewSwitch.setFocusable(false);
        } else {
            if (tvS3DManager.get3dDisplayFormat() == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_2DTO3D) {
                comboBtn3DDepth.setFocusable(true);
                comboBtn3DOffset.setFocusable(true);
                comboBtnLRViewSwitch.setFocusable(true);
            } else {
                comboBtn3DDepth.setFocusable(true);
                comboBtn3DOffset.setFocusable(false);
                comboBtnLRViewSwitch.setFocusable(true);
            }
        }
    }
}
