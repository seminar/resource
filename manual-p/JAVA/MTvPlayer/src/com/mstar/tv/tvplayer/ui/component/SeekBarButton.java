//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2012 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tv.tvplayer.ui.component;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarButton implements IUpdateSysData {
    private static final int TEXT_VIEW_NAME_IDX = 0;

    private static final int PROGRESS_BAR_IDX = 2;

    private static final int TEXT_VIEW_PROGRESS_IDX = 1;

    private int step;

    private int decimalDigits = 0;

    private boolean isSelectedDifferent = false;

    private LinearLayout mLayout;

    TextView textViewName;

    SeekBar seekbar;

    TextView textViewProgress;

    public SeekBarButton(Activity context, int resId, int step, boolean isSelectedDiff) {
        this.isSelectedDifferent = isSelectedDiff;
        this.step = step;
        mLayout = (LinearLayout) context.findViewById(resId);
        if (mLayout != null) {
            textViewName = (TextView) mLayout.getChildAt(TEXT_VIEW_NAME_IDX);
            seekbar = (SeekBar) mLayout.getChildAt(PROGRESS_BAR_IDX);
            textViewProgress = (TextView) mLayout.getChildAt(TEXT_VIEW_PROGRESS_IDX);
            textViewProgress.setText(String.valueOf(seekbar.getProgress()));
            mLayout.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER
                            && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (mLayout.isSelected()) {
                            mLayout.setSelected(false);
                        } else {
                            mLayout.setSelected(true);
                        }
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
                            || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        mLayout.setSelected(false);
                    }
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                decreaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                increaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_VOLUME_DOWN:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                decreaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_VOLUME_UP:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                increaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                    }
                    return false;
                }
            });
            mLayout.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        SeekBarButton.this.setFocused();
                        seekbar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        SeekBarButton.this.setFocused();
                        seekbar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    return false;
                }
            });

            seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    textViewProgress.setText(String.valueOf(seekBar.getProgress()));
                    doUpdate();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }
            });
        }
    }
    public SeekBarButton(Activity context, int resId, int step, boolean isSelectedDiff, int decimalDigits) {
        this.isSelectedDifferent = isSelectedDiff;
        this.step = step;
        this.decimalDigits = decimalDigits;
        mLayout = (LinearLayout) context.findViewById(resId);
        if (mLayout != null) {
            textViewName = (TextView) mLayout.getChildAt(TEXT_VIEW_NAME_IDX);
            seekbar = (SeekBar) mLayout.getChildAt(PROGRESS_BAR_IDX);
            textViewProgress = (TextView) mLayout.getChildAt(TEXT_VIEW_PROGRESS_IDX);
            textViewProgress.setText(String.valueOf(seekbar.getProgress()));
            mLayout.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER
                            && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (mLayout.isSelected()) {
                            mLayout.setSelected(false);
                        } else {
                            mLayout.setSelected(true);
                        }
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
                            || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        mLayout.setSelected(false);
                    }
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                decreaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                increaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_VOLUME_DOWN:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                decreaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_VOLUME_UP:
                            if (event.getAction() == KeyEvent.ACTION_DOWN
                                    && (mLayout.isSelected() || !isSelectedDifferent)) {
                                increaseProgress();
                                doUpdate();
                                return true;
                            }
                            break;
                    }
                    return false;
                }
            });
            mLayout.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        SeekBarButton.this.setFocused();
                        seekbar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        SeekBarButton.this.setFocused();
                        seekbar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    return false;
                }
            });

            seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    textViewProgress.setText(String.valueOf(seekBar.getProgress()));
                    doUpdate();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }
            });
        }
    }

    public void setSeletedDifferent(boolean b) {
        isSelectedDifferent = b;
    }

    public SeekBarButton(Dialog dialog, int resId, int step) {
        this.step = step;
        mLayout = (LinearLayout) dialog.findViewById(resId);
        textViewName = (TextView) mLayout.getChildAt(TEXT_VIEW_NAME_IDX);
        seekbar = (SeekBar) mLayout.getChildAt(PROGRESS_BAR_IDX);
        textViewProgress = (TextView) mLayout.getChildAt(TEXT_VIEW_PROGRESS_IDX);
        textViewProgress.setText(String.valueOf(seekbar.getProgress()));
        mLayout.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            decreaseProgress();
                            doUpdate();
                            return true;
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            increaseProgress();
                            doUpdate();
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
    }

    protected void increaseProgress() {
        seekbar.incrementProgressBy(this.step);
        String strProgress = convertProgressToString(seekbar.getProgress());
        textViewProgress.setText(strProgress);
    }

    protected void decreaseProgress() {
        seekbar.incrementProgressBy(-this.step);
        String strProgress = convertProgressToString(seekbar.getProgress());
        textViewProgress.setText(strProgress);
    }

    public short getProgress() {
        return (short) seekbar.getProgress();
    }

    public void setProgress(short progress) {
        seekbar.setProgress(progress);
        String strProgress = convertProgressToString(seekbar.getProgress());
        textViewProgress.setText(strProgress);
    }

    public int getProgressInt() {
        return seekbar.getProgress();
    }

    public void setProgressInt(int progress) {
        seekbar.setProgress(progress);
        String strProgress = convertProgressToString(seekbar.getProgress());
        textViewProgress.setText(strProgress);
    }

    public int getMax() {
        return seekbar.getMax();
    }

    @Override
    public void doUpdate() {
        // TODO Auto-generated method stub
    }

    public void setFocused() {
        mLayout.setFocusable(true);
        mLayout.setFocusableInTouchMode(true);
        mLayout.requestFocus();
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mLayout.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mLayout.setOnClickListener(onClickListener);
    }

    public void setFocusable(boolean b) {
        if (b) {
            textViewName.setTextColor(Color.WHITE);
            textViewProgress.setTextColor(Color.WHITE);
        } else {
            textViewName.setTextColor(Color.GRAY);
            textViewProgress.setTextColor(Color.GRAY);
        }
        mLayout.setFocusable(b);
    }

    public void setEnable(boolean b) {
        mLayout.setEnabled(b);
    }

    public void setVisibility(boolean b) {
        if (b) {
            mLayout.setVisibility(View.VISIBLE);
        } else {
            mLayout.setVisibility(View.GONE);
        }
    }

    public LinearLayout getLayout() {
        return mLayout;
    }

    private String convertProgressToString(int progress) {
        String str = new String();
        if (0 == this.decimalDigits) {
            str = String.valueOf(progress);
        } else {
            int digit = Double.valueOf(Math.pow(10, this.decimalDigits)).intValue();
            str = String.valueOf(progress / digit);
            str += ".";
            str += String.valueOf(progress % digit);
        }
        return str;
    }
}
