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

package com.mstar.tv.tvplayer.ui.tuning;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tvframework.MstarBaseActivity;

public class DTVAutoTuneOptionActivity extends MstarBaseActivity {

    private static final String TAG = "DTVAutoTuneOptionActivity";

    private static final int FULLSCAN = 0;

    private static final int NITSCAN = 1;

    private static int freMax = 999;

    private static int lenMax = 100;

    private static int qualMax = 100;

    private short dvbcSymbol = 6875;

    private int dvbcFreq = 474;

    private int inputFreq = 0;

    private int inputSymbol = 0;

    private String strFreq = new String();

    private String strSymbol = new String();

    private ComboButton comboBtnTitle;

    private ComboButton comboBtnFrequency;

    private ComboButton comboBtnModulation;

    private ComboButton comboBtnSymbol;

    private ComboButton comboBtnSignalStrength;

    private ComboButton comboBtnSignalQuality;

    private String[] modulationStrs = {
            "16", "32", "64", "128", "256"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dtv_auto_tune_option);
        initUIComponents();
        loadDataToUI();
        comboBtnTitle.setOnClickListener(clickListener);
        comboBtnFrequency.setOnClickListener(clickListener);
        comboBtnModulation.setOnClickListener(clickListener);
        comboBtnSymbol.setOnClickListener(clickListener);
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            switch (view.getId()) {
                case R.id.linearlayout_cha_dtvautotuning_choose:
                case R.id.linearlayout_cha_dtvautotuning_fre:
                case R.id.linearlayout_cha_dtvautotuning_mod:
                case R.id.linearlayout_cha_dtvautotuning_sym:
                    Intent intent = new Intent();
                    intent.setClass(DTVAutoTuneOptionActivity.this, ChannelTuning.class);
                    if (comboBtnTitle.getIdx() == NITSCAN) {
                        int[] userScanSettings = {
                                0, 0, 0
                        };
                        userScanSettings[0] = comboBtnFrequency.getIdx();
                        userScanSettings[1] = comboBtnModulation.getIdx();
                        userScanSettings[2] = comboBtnSymbol.getIdx();
                        intent.putExtra("NitScanPara", userScanSettings);
                    }
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DTVAutoTuneOptionActivity.this, AutoTuneOptionActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void initUIComponents() {
        int currentDtvRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
        int dvbcDtvRouteIndex = TvChannelManager.getInstance()
                .getSpecificDtvRouteIndex(TvChannelManager.TV_ROUTE_DVBC);
        if (currentDtvRouteIndex == dvbcDtvRouteIndex) {
            comboBtnTitle = new ComboButton(this, getResources().getStringArray(
                    R.array.strs_cha_dtvautotuning_full_or_net),
                    R.id.linearlayout_cha_dtvautotuning_choose, 1, 2, false) {
                @Override
                public void doUpdate() {
                    if (comboBtnTitle.getIdx() == 1) {
                        setBelowTextEnable(true);
                    } else {
                        setBelowTextEnable(false);
                    }
                }
            };
        } else {
            comboBtnTitle = new ComboButton(this, getResources().getStringArray(
                    R.array.strs_cha_dtvautotuning_full),
                    R.id.linearlayout_cha_dtvautotuning_choose, 1, 2, false) {
                @Override
                public void doUpdate() {
                    if (comboBtnTitle.getIdx() == 1) {
                        setBelowTextEnable(true);
                    } else {
                        setBelowTextEnable(false);
                    }
                }
            };
        }

        comboBtnFrequency = new ComboButton(this, null, R.id.linearlayout_cha_dtvautotuning_fre, 1,
                2, false) {
            @Override
            public void doUpdate() {
                // limit the range
                if (comboBtnFrequency.getIdx() < 0) {
                    comboBtnFrequency.setIdx(freMax);
                }
                if (comboBtnFrequency.getIdx() > freMax) {
                    comboBtnFrequency.setIdx(0);
                }
            }
        };
        comboBtnModulation = new ComboButton(this, modulationStrs,
                R.id.linearlayout_cha_dtvautotuning_mod, 1, 2, false) {
            @Override
            public void doUpdate() {

            }
        };
        comboBtnSymbol = new ComboButton(this, null, R.id.linearlayout_cha_dtvautotuning_sym, 1, 2,
                false) {
            @Override
            public void doUpdate() {
                if (comboBtnSymbol.getIdx() < 0)
                    comboBtnSymbol.setIdx(0);
            }
        };
        comboBtnSignalStrength = new ComboButton(this, null,
                R.id.linearlayout_cha_dtvautotuning_len, 1, 2, false) {
            @Override
            public void doUpdate() {

                // limit the range
                if (comboBtnFrequency.getIdx() < 0) {
                    comboBtnFrequency.setIdx(lenMax);
                }
                if (comboBtnFrequency.getIdx() > lenMax) {
                    comboBtnFrequency.setIdx(0);
                }
            }
        };
        comboBtnSignalQuality = new ComboButton(this, null,
                R.id.linearlayout_cha_dtvautotuning_qual, 1, 2, false) {
            @Override
            public void doUpdate() {
                // limit the range
                if (comboBtnFrequency.getIdx() < 0) {
                    comboBtnFrequency.setIdx(qualMax);
                }
                if (comboBtnFrequency.getIdx() > qualMax) {
                    comboBtnFrequency.setIdx(0);
                }
            }
        };
        setOnFocusChangeListeners();
        setStrengthAndQualityTextEnable(false);

    }

    private void loadDataToUI() {
        // TODO, this data should be get from DB
        // just set default value now
        comboBtnTitle.setIdx(0);
        setBelowTextEnable(false);
        comboBtnFrequency.setIdx(474);
        comboBtnModulation.setIdx(2);
        comboBtnSignalQuality.setIdx(0);
        comboBtnSignalStrength.setIdx(0);
        comboBtnSymbol.setIdx(6875);
    }

    private void setBelowTextEnable(boolean b) {
        comboBtnFrequency.setEnable(b);
        comboBtnModulation.setEnable(b);
        comboBtnSymbol.setEnable(b);

        comboBtnFrequency.setFocusable(b);
        comboBtnModulation.setFocusable(b);
        comboBtnSymbol.setFocusable(b);

        int textColor = b ? getResources().getColor(R.color.enable_text_color) : getResources()
                .getColor(R.color.disable_text_color);
        ((TextView) comboBtnFrequency.getLayout().getChildAt(1)).setTextColor(textColor);
        ((TextView) comboBtnModulation.getLayout().getChildAt(1)).setTextColor(textColor);
        ((TextView) comboBtnSymbol.getLayout().getChildAt(1)).setTextColor(textColor);
    }

    private void setStrengthAndQualityTextEnable(boolean b) {
        comboBtnSignalQuality.setEnable(b);
        comboBtnSignalStrength.setEnable(b);

        comboBtnSignalQuality.setFocusable(b);
        comboBtnSignalStrength.setFocusable(b);

        int textColor = b ? getResources().getColor(R.color.enable_text_color) : getResources()
                .getColor(R.color.disable_text_color);
        ((TextView) comboBtnSignalQuality.getLayout().getChildAt(1)).setTextColor(textColor);
        ((TextView) comboBtnSignalStrength.getLayout().getChildAt(1)).setTextColor(textColor);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener comboBtnFocusListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
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
        comboBtnFrequency.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnModulation.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnSignalQuality.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnSignalStrength.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnSymbol.setOnFocusChangeListener(comboBtnFocusListener);
        comboBtnTitle.setOnFocusChangeListener(comboBtnFocusListener);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = getCurrentFocus().getId();
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                switch (currentid) {
                    case R.id.linearlayout_cha_dtvautotuning_fre:
                        inputFreq = keyCode - KeyEvent.KEYCODE_0;
                        inputFrequencyNumber(inputFreq);
                        break;
                    case R.id.linearlayout_cha_dtvautotuning_sym:
                        inputSymbol = keyCode - KeyEvent.KEYCODE_0;
                        inputSymbolNumber(inputSymbol);
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void inputFrequencyNumber(int inputNumber) {
        int freq = 0;
        strFreq = strFreq + Integer.toString(inputNumber);
        freq = Integer.parseInt(strFreq);
        if (freq > freMax) {
            strFreq = Integer.toString(inputNumber);
            dvbcFreq = inputNumber;
        } else if (strFreq.length() >= 4) {
            strFreq = Integer.toString(inputNumber);
            dvbcFreq = inputNumber;
        } else {
            dvbcFreq = freq;
        }
        comboBtnFrequency.setIdx(dvbcFreq);
        return;
    }

    private void inputSymbolNumber(int inputNumber) {
        short symbol = 0;
        strSymbol = strSymbol + Integer.toString(inputNumber);
        symbol = (short) Integer.parseInt(strSymbol);
        if (strSymbol.length() >= 5) {
            strSymbol = Integer.toString(inputNumber);
            dvbcSymbol = (short) inputNumber;
        } else {
            dvbcSymbol = symbol;
        }
        comboBtnSymbol.setIdx(dvbcSymbol);
        return;
    }
}
