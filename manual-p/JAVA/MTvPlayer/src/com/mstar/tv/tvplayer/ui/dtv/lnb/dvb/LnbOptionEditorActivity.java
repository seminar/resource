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

package com.mstar.tv.tvplayer.ui.dtv.lnb.dvb;

import java.util.ArrayList;

import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.util.Constant;
import com.mstar.util.Tools;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;

import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.SatelliteInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsTransponderInfo;

public class LnbOptionEditorActivity extends MstarBaseActivity {

    private static final String TAG = "LnbOptionEditorActivity";

    // default angle value , 90.0, 90*10
    private static final int DEFAULT_LONGITUDE_ANGLE = 900;

    // default transponder frequency value
    private static final int DEFAULT_TP_FREQUENCY = 12537;

    // default transponder symbol rate value
    private static final int DEFAULT_TP_SYMBOL_RATE = 41248;

    // hard code of max low freq value : 4200+2150
    private static final int MAX_C_LOF_FREQ = 6350;

    private int mOptionType = Constant.LNBOPTION_PAGETYPE_INVALID;

    private int mActionType = Constant.LNBOPTION_EDITOR_ACTION_INVALID;

    private TextView mTvTitle = null;

    private TextView mTvNumber = null;

    private EditText mEtSatName = null;

    private LinearLayout mLayoutDirection = null;

    private LinearLayout mLayoutAngle = null;

    private LinearLayout mLayoutFreq = null;

    private LinearLayout mLayoutSym = null;

    private TextView mTvBandPolarText = null;

    private LinearLayout mLayoutFuncBar = null;

    private ComboButton mCbLongitudeDirection = null;

    private SeekBarButton mSbLongitudeAngle = null;

    private SeekBarButton mSbFrequency = null;

    private SeekBarButton mSbSymbolRate = null;

    private ComboButton mCbBandPolar = null;

    private String[] mFreqLowValue = null;

    private String[] mFreqHighValue = null;

    private int mInputFreq = 0;

    private int mInputSymbolRate = 0;

    private int mCurrentSatNumber = 0;

    private int mCurrentTpNumber = 0;

    private String mStrTpFreq = new String("");

    private String mStrTpSym = new String("");

    private TvDvbChannelManager mTvDvbChannelManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.lnb_option_editor);
        mOptionType = getIntent().getIntExtra(Constant.LNBOPTION_EDITOR_PAGETYPE,
                Constant.LNBOPTION_PAGETYPE_INVALID);
        mActionType = getIntent().getIntExtra(Constant.LNBOPTION_EDITOR_ACTIONTYPE,
                Constant.LNBOPTION_EDITOR_ACTION_INVALID);

        mTvTitle = (TextView) findViewById(R.id.lnb_option_editor_title);
        mTvNumber = (TextView) findViewById(R.id.lnb_option_editor_number);
        mEtSatName = (EditText) findViewById(R.id.lnb_option_editor_satname);
        mLayoutDirection = (LinearLayout) findViewById(R.id.lnb_option_editor_longitude_direction);
        mLayoutAngle = (LinearLayout) findViewById(R.id.lnb_option_editor_longitude_angle);
        mLayoutFreq = (LinearLayout) findViewById(R.id.lnb_option_editor_frequency);
        mLayoutSym = (LinearLayout) findViewById(R.id.lnb_option_editor_symbolrate);
        mTvBandPolarText = (TextView) findViewById(R.id.lnb_option_editor_band_polar_text);
        mLayoutFuncBar = (LinearLayout) findViewById(R.id.lnboption_editor_bottom_function_bar);

        mTvDvbChannelManager = TvDvbChannelManager.getInstance();

        setupOptionEditorPage();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = getCurrentFocus().getId();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                backToLnbOptionPage();
                break;
            case KeyEvent.KEYCODE_PROG_RED:
                if (Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType) {
                    // open software keyboard for editing satName
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
                        mEtSatName.requestFocus();
                    }
                }
                break;
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
                    case R.id.lnb_option_editor_frequency:
                        mInputFreq = keyCode - KeyEvent.KEYCODE_0;
                        mStrTpFreq = checkInputNumber(mInputFreq, mStrTpFreq, mSbFrequency.getMax());
                        mSbFrequency.setProgressInt(Integer.parseInt(mStrTpFreq));
                        break;
                    case R.id.lnb_option_editor_symbolrate:
                        mInputSymbolRate = keyCode - KeyEvent.KEYCODE_0;
                        mStrTpSym = checkInputNumber(mInputSymbolRate, mStrTpSym,
                                mSbSymbolRate.getMax());
                        mSbSymbolRate.setProgressInt(Integer.parseInt(mStrTpSym));
                        break;
                }
                break;
            default:
                Log.d(TAG, "default key code:" + keyCode);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupOptionEditorPage() {
        String titleStr = null;
        String numberStr = getResources().getString(R.string.str_lnboption_editor_number);
        switch (mOptionType) {
            case Constant.LNBOPTION_PAGETYPE_SATELLITE:
                mLayoutFreq.setVisibility(View.GONE);
                mLayoutSym.setVisibility(View.GONE);
                titleStr = getResources().getString(R.string.str_lnbsetting_satellite);
                mCbLongitudeDirection = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_longitude_direction_option),
                        R.id.lnb_option_editor_longitude_direction, 0, 1, false) {
                };
                mSbLongitudeAngle = new SeekBarButton(this,
                        R.id.lnb_option_editor_longitude_angle, 1, false, 1) {
                };
                mCbBandPolar = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_satellite_band_option), R.id.lnb_option_editor_band_polar,
                        0, 1, false) {
                };
                mTvBandPolarText.setText(getResources().getString(
                        R.string.str_lnboption_editor_band));
                mFreqLowValue = getResources().getStringArray(R.array.str_arr_lnb_frequencies_low);
                mFreqHighValue = getResources()
                        .getStringArray(R.array.str_arr_lnb_frequencies_high);
                String satNameStr = getResources().getString(R.string.str_lnboption_editor_name);

                int angle = DEFAULT_LONGITUDE_ANGLE;
                int direction = TvDvbChannelManager.LONGITUDE_DIRECTION_EAST;
                int band = TvDvbChannelManager.LNB_BAND_TYPE_C;
                SatelliteInfo satInfo;
                if (Constant.LNBOPTION_EDITOR_ACTION_ADD == mActionType) {
                    titleStr += getResources().getString(R.string.str_lnboption_menu_hint_add);
                    // get next number from sn
                    mCurrentSatNumber = mTvDvbChannelManager.getCurrentSatelliteCount();
                } else if (Constant.LNBOPTION_EDITOR_ACTION_EDIT == mActionType) {
                    titleStr += getResources().getString(R.string.str_lnboption_menu_hint_edit);
                    // get current sat number from sn
                    mCurrentSatNumber = getIntent().getIntExtra(Constant.LNBOPTION_EDITOR_INDEX, 0);
                    // get current sat name from sn
                    satInfo = mTvDvbChannelManager.getSatelliteInfo(mCurrentSatNumber);
                    satNameStr = satInfo.satName;
                    // get angle and direction
                    direction = satInfo.direction;
                    angle = satInfo.angle;
                    // get band type
                    band = satInfo.lnbType;
                }
                mTvTitle.setText(titleStr);
                numberStr += mCurrentSatNumber;
                mTvNumber.setText(numberStr);
                mEtSatName.setText(satNameStr);
                mSbLongitudeAngle.setProgressInt(angle);
                mCbLongitudeDirection.setIdx(direction);
                mCbBandPolar.setIdx(band);
                break;
            case Constant.LNBOPTION_PAGETYPE_TRANSPONDER:
                mEtSatName.setVisibility(View.GONE);
                mLayoutDirection.setVisibility(View.GONE);
                mLayoutAngle.setVisibility(View.GONE);
                mLayoutFuncBar.setVisibility(View.GONE);
                titleStr = getResources().getString(R.string.str_lnbsetting_transponder);
                mSbFrequency = new SeekBarButton(this, R.id.lnb_option_editor_frequency, 1, false) {
                };
                mSbSymbolRate = new SeekBarButton(this, R.id.lnb_option_editor_symbolrate, 1, false) {
                };
                mCbBandPolar = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_transponder_polarization_option),
                        R.id.lnb_option_editor_band_polar, 0, 1, false) {
                };
                mTvBandPolarText.setText(getResources().getString(
                        R.string.str_lnboption_editor_polarization));

                int satNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
                DvbsTransponderInfo tpInfo;
                int freq = DEFAULT_TP_FREQUENCY;
                int symbol = DEFAULT_TP_SYMBOL_RATE;
                int polarity = TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL;
                if (Constant.LNBOPTION_EDITOR_ACTION_ADD == mActionType) {
                    titleStr += getResources().getString(R.string.str_lnboption_menu_hint_add);
                    // get next number from sn
                    mCurrentTpNumber = mTvDvbChannelManager.getTransponderCount(satNum);
                } else if (Constant.LNBOPTION_EDITOR_ACTION_EDIT == mActionType) {
                    titleStr += getResources().getString(R.string.str_lnboption_menu_hint_edit);
                    // get current transponder number from sn
                    mCurrentTpNumber = getIntent().getIntExtra(Constant.LNBOPTION_EDITOR_INDEX, 0);
                    tpInfo = mTvDvbChannelManager.getTransponderInfo(satNum, mCurrentTpNumber);
                    freq = tpInfo.frequency;
                    symbol = tpInfo.symbolRate;
                    polarity = tpInfo.polarity;
                }
                mTvTitle.setText(titleStr);
                numberStr += mCurrentTpNumber;
                mTvNumber.setText(numberStr);
                mSbFrequency.setProgressInt(freq);
                mSbSymbolRate.setProgressInt(symbol);
                if (TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL == polarity) {
                    mCbBandPolar.setIdx(TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL);
                } else {
                    mCbBandPolar.setIdx(TvDvbChannelManager.TRANSPONDER_POLARITY_HORIZONTAL);
                }
                break;
            default:
                break;
        }
    }

    private void backToLnbOptionPage() {
        Intent intent = new Intent(TvIntent.ACTION_LNBOPTION);
        switch (mOptionType) {
            case Constant.LNBOPTION_PAGETYPE_SATELLITE:
                SatelliteInfo curSatInfo = new SatelliteInfo();
                if (Constant.LNBOPTION_EDITOR_ACTION_EDIT == mActionType) {
                    curSatInfo = mTvDvbChannelManager.getSatelliteInfo(mCurrentSatNumber);
                }
                curSatInfo.satName = mEtSatName.getText().toString();
                curSatInfo.angle = mSbLongitudeAngle.getProgressInt();
                curSatInfo.lnbType = mCbBandPolar.getIdx();
                if (Constant.LNBOPTION_EDITOR_ACTION_ADD == mActionType) {
                    if (TvDvbChannelManager.LNB_BAND_TYPE_KU == curSatInfo.lnbType) {
                        curSatInfo.lowLOF = Integer
                                .parseInt(mFreqLowValue[TvDvbChannelManager.LNB_FREQ_UNIVERSAL]);
                        curSatInfo.hiLOF = Integer
                                .parseInt(mFreqHighValue[TvDvbChannelManager.LNB_FREQ_UNIVERSAL]);
                    } else if (TvDvbChannelManager.LNB_BAND_TYPE_C == curSatInfo.lnbType) {
                        curSatInfo.lowLOF = Integer
                                .parseInt(mFreqLowValue[TvDvbChannelManager.LNB_FREQ_5150]);
                        curSatInfo.hiLOF = Integer
                                .parseInt(mFreqHighValue[TvDvbChannelManager.LNB_FREQ_5150]);
                    }
                    curSatInfo.lnbPwrOnOff = TvDvbChannelManager.LNB_POWER_MODE_13OR18V;
                    curSatInfo.e22KOnOff = TvDvbChannelManager.DISH_22K_MODE_AUTO;
                    curSatInfo.toneburstType = TvDvbChannelManager.DISH_TONE_BURST_MODE_NONE;
                    curSatInfo.swt10Port = TvDvbChannelManager.DISEQC_V_1_0_PORT_NONE;
                    curSatInfo.swt11Port = TvDvbChannelManager.DISEQC_V_1_1_PORT_NONE;
                    curSatInfo.position = 0;
                    mTvDvbChannelManager.addSatelliteInfo(curSatInfo);
                } else if (Constant.LNBOPTION_EDITOR_ACTION_EDIT == mActionType) {
                    if ((TvDvbChannelManager.LNB_BAND_TYPE_KU == curSatInfo.lnbType)
                            && (MAX_C_LOF_FREQ > curSatInfo.lowLOF)) {
                        curSatInfo.lowLOF = Integer
                                .parseInt(mFreqLowValue[TvDvbChannelManager.LNB_FREQ_UNIVERSAL]);
                        curSatInfo.hiLOF = Integer
                                .parseInt(mFreqHighValue[TvDvbChannelManager.LNB_FREQ_UNIVERSAL]);
                    } else if ((TvDvbChannelManager.LNB_BAND_TYPE_C == curSatInfo.lnbType)
                            && (MAX_C_LOF_FREQ < curSatInfo.lowLOF)) {
                        curSatInfo.lowLOF = Integer
                                .parseInt(mFreqLowValue[TvDvbChannelManager.LNB_FREQ_5150]);
                        curSatInfo.hiLOF = Integer
                                .parseInt(mFreqHighValue[TvDvbChannelManager.LNB_FREQ_5150]);
                    }
                    mTvDvbChannelManager.updateSatelliteInfo(mCurrentSatNumber, curSatInfo);
                }
                // push intent to lnb option page for update add/edit result
                intent.putExtra(Constant.LNBOPTION_PAGETYPE, Constant.LNBOPTION_PAGETYPE_SATELLITE);
                break;
            case Constant.LNBOPTION_PAGETYPE_TRANSPONDER:
                DvbsTransponderInfo curTpInfo = new DvbsTransponderInfo();
                int satNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
                if (Constant.LNBOPTION_EDITOR_ACTION_EDIT == mActionType) {
                    curTpInfo = mTvDvbChannelManager.getTransponderInfo(satNum, mCurrentTpNumber);
                }
                curTpInfo.frequency = mSbFrequency.getProgressInt();
                curTpInfo.symbolRate = mSbSymbolRate.getProgressInt();
                curTpInfo.polarity =  Short.valueOf(mCbBandPolar.getIdx()).byteValue();

                if (Constant.LNBOPTION_EDITOR_ACTION_ADD == mActionType) {
                    curTpInfo.rf = Integer.valueOf(mCurrentTpNumber).shortValue();
                    mTvDvbChannelManager.addTransponderInfo(satNum, curTpInfo);
                } else if (Constant.LNBOPTION_EDITOR_ACTION_EDIT == mActionType) {
                    mTvDvbChannelManager.updateTransponderInfo(satNum, mCurrentTpNumber, curTpInfo);
                }
                // push intent to lnb option page for update add/edit result
                intent.putExtra(Constant.LNBOPTION_PAGETYPE, Constant.LNBOPTION_PAGETYPE_TRANSPONDER);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    private String checkInputNumber(int inputNo, String checkNo, int maxNo) {
        int curNum = 0;
        int outputNumber = 0;
        checkNo = checkNo + Integer.toString(inputNo);
        curNum = Integer.parseInt(checkNo);
        if (curNum >= maxNo) {
            checkNo = Integer.toString(inputNo);
            outputNumber = inputNo;
        } else if (checkNo.length() > Integer.toString(maxNo).length()) {
            checkNo = Integer.toString(inputNo);
            outputNumber = inputNo;
        } else {
            outputNumber = curNum;
        }
        return Integer.toString(outputNumber);
    }
}
