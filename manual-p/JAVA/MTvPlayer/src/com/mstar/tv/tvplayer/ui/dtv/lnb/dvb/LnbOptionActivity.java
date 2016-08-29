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

import android.app.AlertDialog;
import android.util.Log;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.util.Constant;
import com.mstar.tv.tvplayer.ui.channel.LNBSettingActivity;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;

import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.SatelliteInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsTransponderInfo;

public class LnbOptionActivity extends MstarBaseActivity {

    private static final String TAG = "LnbOptionActivity";

    public static final String RESULT_INTENT_SAT_TAG = "Satellite Name";

    public static final String RESULT_INTENT_TP_TAG = "Transponder Result";

    public static final String RESULT_INTENT_FREQ_TAG = "Frequencies Result";

    public static final String RESULT_FREQ_LOW_TAG = "Freq Low";

    public static final String RESULT_FREQ_HIGH_TAG = "Freq High";

    public static final String RESULT_INTENT_MOTOR_TAG = "Motor Result";

    public static final String RESULT_INTENT_SINGLECABLE_TAG = "SingleCable Result";

    public static final String RESULT_INTENT_SINGLECABLE_SLOT = "SingleCable Slot";

    private ListView mLvOption = null;

    private TextView mTvOptionTitle = null;

    private LinearLayout mLayoutFuncBar = null;

    private LinearLayout mLayoutFreq = null;

    private LinearLayout mLayoutFreqLow = null;

    private LinearLayout mLayoutFreqHigh = null;

    private LinearLayout mLayoutSingleCableSlot = null;

    private LinearLayout mLayoutSingleCableFreq = null;

    private LinearLayout mLayoutSingleCablePin = null;

    private LinearLayout mLayoutSingleCableLnb = null;

    private ComboButton mCbLNBFreq = null;

    private SeekBarButton mSbLNBFreqLow = null;

    private SeekBarButton mSbLNBFreqHigh = null;

    private ComboButton mCbSingleCableSlot = null;

    private SeekBarButton mSbSingleCableFreq = null;

    private SeekBarButton mSbSingleCablePin = null;

    private ComboButton mCbSingleCableLnb = null;

    private TextView mTvSingleCableVerify = null;

    private TextView mTvMotorNone = null;

    private TextView mTvMotorDiSEqC_1_2 = null;

    private TextView mTvMotorDiSEqC_1_3 = null;

    private String[] mFreqLnbType = null;

    private String[] mFreqLowValue = null;

    private String[] mFreqHighValue = null;

    private String[] mLnbBandType = null;

    private String[] mTpPolarType = null;

    private ArrayList<LnbOptionListViewItemObject> mOptionListItem = new ArrayList<LnbOptionListViewItemObject>();

    private LnbOptionEditAdapter mLnbOptionEditAdapter = null;

    private int mOptionType = Constant.LNBOPTION_PAGETYPE_INVALID;

    private int mInputFreqLow = 0;

    private int mInputFreqHigh = 0;

    private int mInputSingleCableFreq = 0;

    private int mMotorPageSelection = Constant.LNBOPTION_MOTOR_NONE;

    private int mCurrentSatNum = 0;

    private String mStrFreqLow = new String("");

    private String mStrFreqHigh = new String("");

    private String mStrSingleCableFreq = new String("");

    private AlertDialog.Builder mDeleteDialog = null;

    private TvDvbChannelManager mTvDvbChannelManager = null;

    private SatelliteInfo mCurrentSatInfo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.lnb_option);
        mOptionType = getIntent().getIntExtra(Constant.LNBOPTION_PAGETYPE,
                Constant.LNBOPTION_PAGETYPE_INVALID);
        mLayoutFuncBar = (LinearLayout) findViewById(R.id.lnboption_bottom_function_bar);
        mTvOptionTitle = (TextView) findViewById(R.id.lnb_option_edit_title);
        mLvOption = (ListView) findViewById(R.id.lnb_option_edit_list_view);
        mLayoutFreq = (LinearLayout) findViewById(R.id.lnb_option_freq_lnb_type);
        mLayoutFreqLow = (LinearLayout) findViewById(R.id.lnb_option_freq_low_value);
        mLayoutFreqHigh = (LinearLayout) findViewById(R.id.lnb_option_freq_high_value);
        mTvMotorNone = (TextView) findViewById(R.id.lnb_option_motor_none);
        mTvMotorDiSEqC_1_2 = (TextView) findViewById(R.id.lnb_option_motor_diseqc_1_2);
        mTvMotorDiSEqC_1_3 = (TextView) findViewById(R.id.lnb_option_motor_diseqc_1_3);
        mLayoutSingleCableSlot = (LinearLayout) findViewById(R.id.lnb_option_single_cable_slot);
        mLayoutSingleCableFreq = (LinearLayout) findViewById(R.id.lnb_option_single_cable_freq);
        mLayoutSingleCablePin = (LinearLayout) findViewById(R.id.lnb_option_single_cable_pin);
        mLayoutSingleCableLnb = (LinearLayout) findViewById(R.id.lnb_option_single_cable_lnb);
        mTvSingleCableVerify = (TextView) findViewById(R.id.lnb_option_single_cable_verify);

        mLnbBandType = getResources().getStringArray(R.array.str_arr_satellite_band_option);
        mTpPolarType = getResources().getStringArray(
                R.array.str_arr_transponder_polarization_option);

        mTvDvbChannelManager = TvDvbChannelManager.getInstance();

        if ((Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType)
                || (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType)) {
            setupOptionAdapter();
        } else if (Constant.LNBOPTION_PAGETYPE_MOTOR == mOptionType) {
            mMotorPageSelection = getIntent().getIntExtra(Constant.LNBOPTION_MOTOR_FOCUS,
                    Constant.LNBOPTION_MOTOR_NONE);
        }
        setupOptionPage();
        createDeleteDialog();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = -1;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                finish();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;
            case KeyEvent.KEYCODE_ENTER:
                break;
            case KeyEvent.KEYCODE_PROG_RED:
                if (Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType) {
                    Intent clickIntent = new Intent(TvIntent.ACTION_LNBOPTION_EDITOR);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_SATELLITE);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_ACTIONTYPE,
                            Constant.LNBOPTION_EDITOR_ACTION_ADD);
                    startActivity(clickIntent);
                } else if (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType) {
                    Intent clickIntent = new Intent(TvIntent.ACTION_LNBOPTION_EDITOR);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_TRANSPONDER);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_ACTIONTYPE,
                            Constant.LNBOPTION_EDITOR_ACTION_ADD);
                    startActivity(clickIntent);
                } else if (Constant.LNBOPTION_PAGETYPE_MOTOR == mOptionType) {
                    currentid = getCurrentFocus().getId();
                    if (R.id.lnb_option_motor_diseqc_1_2 == currentid) {
                        Intent clickIntent = new Intent(TvIntent.ACTION_LNBMOTOR_EDITOR);
                        clickIntent.putExtra(Constant.LNBOPTION_MOTOR_ACTIONTYPE,
                                Constant.LNBOPTION_MOTOR_ACTION_POSITION);
                        clickIntent.putExtra(Constant.LNBMOTOR_EDITOR_DISEQC_VERSION,
                                Constant.LNBMOTOR_EDITOR_DISEQC_1_2);
                        startActivity(clickIntent);
                    } else if (R.id.lnb_option_motor_diseqc_1_3 == currentid) {
                        Intent clickIntent = new Intent(TvIntent.ACTION_LNBMOTOR_EDITOR);
                        clickIntent.putExtra(Constant.LNBOPTION_MOTOR_ACTIONTYPE,
                                Constant.LNBOPTION_MOTOR_ACTION_POSITION);
                        clickIntent.putExtra(Constant.LNBMOTOR_EDITOR_DISEQC_VERSION,
                                Constant.LNBMOTOR_EDITOR_DISEQC_1_3);
                        startActivity(clickIntent);
                    }
                }
                break;
            case KeyEvent.KEYCODE_PROG_YELLOW:
                if (Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType) {
                    Intent clickIntent = new Intent(TvIntent.ACTION_LNBOPTION_EDITOR);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_SATELLITE);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_ACTIONTYPE,
                            Constant.LNBOPTION_EDITOR_ACTION_EDIT);
                    int focusNumber = mLvOption.getSelectedItemPosition();
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_INDEX, focusNumber);
                    startActivity(clickIntent);
                } else if (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType) {
                    Intent clickIntent = new Intent(TvIntent.ACTION_LNBOPTION_EDITOR);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_TRANSPONDER);
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_ACTIONTYPE,
                            Constant.LNBOPTION_EDITOR_ACTION_EDIT);
                    int focusNumber = mLvOption.getSelectedItemPosition();
                    clickIntent.putExtra(Constant.LNBOPTION_EDITOR_INDEX, focusNumber);
                    startActivity(clickIntent);
                } else if (Constant.LNBOPTION_PAGETYPE_MOTOR == mOptionType) {
                    Intent clickIntent = new Intent(TvIntent.ACTION_LNBMOTOR_EDITOR);
                    clickIntent.putExtra(Constant.LNBOPTION_MOTOR_ACTIONTYPE,
                            Constant.LNBOPTION_MOTOR_ACTION_LOCATION);
                    clickIntent.putExtra(Constant.LNBMOTOR_EDITOR_DISEQC_VERSION,
                            Constant.LNBMOTOR_EDITOR_DISEQC_1_3);
                    startActivity(clickIntent);
                }
                break;
            case KeyEvent.KEYCODE_PROG_GREEN:
                if ((Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType)
                        || (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType)) {
                    mDeleteDialog.show();
                } else if (Constant.LNBOPTION_PAGETYPE_MOTOR == mOptionType) {
                    Intent clickIntent = new Intent(TvIntent.ACTION_LNBMOTOR_EDITOR);
                    clickIntent.putExtra(Constant.LNBOPTION_MOTOR_ACTIONTYPE,
                            Constant.LNBOPTION_MOTOR_ACTION_LIMIT);
                    currentid = getCurrentFocus().getId();
                    if (R.id.lnb_option_motor_diseqc_1_2 == currentid) {
                        clickIntent.putExtra(Constant.LNBMOTOR_EDITOR_DISEQC_VERSION,
                                Constant.LNBMOTOR_EDITOR_DISEQC_1_2);
                    } else if (R.id.lnb_option_motor_diseqc_1_3 == currentid) {
                        clickIntent.putExtra(Constant.LNBMOTOR_EDITOR_DISEQC_VERSION,
                                Constant.LNBMOTOR_EDITOR_DISEQC_1_3);
                    }
                    startActivity(clickIntent);
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
                if (null != getCurrentFocus()) {
                    currentid = getCurrentFocus().getId();
                }
                switch (currentid) {
                    case R.id.lnb_option_freq_low_value:
                        mInputFreqLow = keyCode - KeyEvent.KEYCODE_0;
                        mStrFreqLow = checkInputNumber(mInputFreqLow, mStrFreqLow,
                                mSbLNBFreqLow.getMax());
                        mSbLNBFreqLow.setProgressInt(Integer.parseInt(mStrFreqLow));
                        break;
                    case R.id.lnb_option_freq_high_value:
                        mInputFreqHigh = keyCode - KeyEvent.KEYCODE_0;
                        mStrFreqHigh = checkInputNumber(mInputFreqHigh, mStrFreqHigh,
                                mSbLNBFreqHigh.getMax());
                        mSbLNBFreqHigh.setProgressInt(Integer.parseInt(mStrFreqHigh));
                        break;
                    case R.id.lnb_option_single_cable_freq:
                        mInputSingleCableFreq = keyCode - KeyEvent.KEYCODE_0;
                        mStrSingleCableFreq = checkInputNumber(mInputSingleCableFreq,
                                mStrSingleCableFreq, mSbSingleCableFreq.getMax());
                        mSbSingleCableFreq.setProgressInt(Integer.parseInt(mStrSingleCableFreq));
                        break;
                    default:
                        break;
                }
                break;
            default:
                Log.d(TAG, "default key code:" + keyCode);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupOptionPage() {
        switch (mOptionType) {
            case Constant.LNBOPTION_PAGETYPE_SATELLITE:
                mTvOptionTitle.setText(getResources().getString(R.string.str_lnbsetting_satellite));
                mLayoutFreq.setVisibility(View.GONE);
                mLayoutFreqLow.setVisibility(View.GONE);
                mLayoutFreqHigh.setVisibility(View.GONE);
                mTvMotorNone.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_2.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_3.setVisibility(View.GONE);
                mLayoutSingleCableSlot.setVisibility(View.GONE);
                mLayoutSingleCableFreq.setVisibility(View.GONE);
                mLayoutSingleCablePin.setVisibility(View.GONE);
                mLayoutSingleCableLnb.setVisibility(View.GONE);
                mTvSingleCableVerify.setVisibility(View.GONE);
                initFuncBar();
                break;
            case Constant.LNBOPTION_PAGETYPE_TRANSPONDER:
                mTvOptionTitle.setText(getResources()
                        .getString(R.string.str_lnbsetting_transponder));
                mLayoutFreq.setVisibility(View.GONE);
                mLayoutFreqLow.setVisibility(View.GONE);
                mLayoutFreqHigh.setVisibility(View.GONE);
                mTvMotorNone.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_2.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_3.setVisibility(View.GONE);
                mLayoutSingleCableSlot.setVisibility(View.GONE);
                mLayoutSingleCableFreq.setVisibility(View.GONE);
                mLayoutSingleCablePin.setVisibility(View.GONE);
                mLayoutSingleCableLnb.setVisibility(View.GONE);
                mTvSingleCableVerify.setVisibility(View.GONE);
                initFuncBar();
                break;
            case Constant.LNBOPTION_PAGETYPE_FREQUENCIES:
                mTvOptionTitle.setText("");
                mLvOption.setVisibility(View.GONE);
                mLayoutFuncBar.setVisibility(View.GONE);
                mTvMotorNone.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_2.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_3.setVisibility(View.GONE);
                mLayoutSingleCableSlot.setVisibility(View.GONE);
                mLayoutSingleCableFreq.setVisibility(View.GONE);
                mLayoutSingleCablePin.setVisibility(View.GONE);
                mLayoutSingleCableLnb.setVisibility(View.GONE);
                mTvSingleCableVerify.setVisibility(View.GONE);
                mFreqLnbType = getResources()
                        .getStringArray(R.array.str_arr_lnb_frequencies_option);
                mFreqLowValue = getResources().getStringArray(R.array.str_arr_lnb_frequencies_low);
                mFreqHighValue = getResources()
                        .getStringArray(R.array.str_arr_lnb_frequencies_high);
                mSbLNBFreqLow = new SeekBarButton(this, R.id.lnb_option_freq_low_value, 1, false) {
                };
                mSbLNBFreqHigh = new SeekBarButton(this, R.id.lnb_option_freq_high_value, 1, false) {
                };
                mCbLNBFreq = new ComboButton(this, mFreqLnbType, R.id.lnb_option_freq_lnb_type, 0,
                        1, false) {
                    @Override
                    public void doUpdate() {
                        setFreqOptionFocusable();
                        freshFreqWhenIdxChange();
                    }
                };
                mCbLNBFreq.setIdx(0);
                setFreqOptionFocusable();
                freshFreqWhenIdxChange();
                OnClickListener onClickFreqListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResultToLNBSettingPage(v);
                        finish();
                    }
                };
                mCbLNBFreq.setOnClickListener(onClickFreqListener);
                mSbLNBFreqLow.setOnClickListener(onClickFreqListener);
                mSbLNBFreqHigh.setOnClickListener(onClickFreqListener);
                break;
            case Constant.LNBOPTION_PAGETYPE_MOTOR:
                mTvOptionTitle.setText(getResources().getString(R.string.str_lnbsetting_motor));
                mLvOption.setVisibility(View.GONE);
                mLayoutFreq.setVisibility(View.GONE);
                mLayoutFreqLow.setVisibility(View.GONE);
                mLayoutFreqHigh.setVisibility(View.GONE);
                mLayoutSingleCableSlot.setVisibility(View.GONE);
                mLayoutSingleCableFreq.setVisibility(View.GONE);
                mLayoutSingleCablePin.setVisibility(View.GONE);
                mLayoutSingleCableLnb.setVisibility(View.GONE);
                mTvSingleCableVerify.setVisibility(View.GONE);
                initFuncBar();

                mCurrentSatNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
                mCurrentSatInfo = mTvDvbChannelManager.getSatelliteInfo(mCurrentSatNum);

                OnClickListener onClickMotorListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResultToLNBSettingPage(v);
                        finish();
                    }
                };
                mTvMotorNone.setOnClickListener(onClickMotorListener);
                mTvMotorDiSEqC_1_2.setOnClickListener(onClickMotorListener);
                mTvMotorDiSEqC_1_3.setOnClickListener(onClickMotorListener);

                OnFocusChangeListener onFocusChangeMotorListener = new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            TextView tv = null;
                            ImageView iv = null;
                            switch (v.getId()) {
                                case R.id.lnb_option_motor_none:
                                    mLayoutFuncBar.setVisibility(View.GONE);
                                    mCurrentSatInfo.position = 0;
                                    break;
                                case R.id.lnb_option_motor_diseqc_1_2:
                                    mLayoutFuncBar.setVisibility(View.VISIBLE);
                                    tv = (TextView) findViewById(R.id.lnboption_yellow_str);
                                    tv.setVisibility(View.GONE);
                                    iv = (ImageView) findViewById(R.id.lnboption_yellow_img);
                                    iv.setVisibility(View.GONE);
                                    if (0 == mCurrentSatInfo.getPositionContentBit()) {
                                        mCurrentSatInfo.position = Integer.valueOf(mTvDvbChannelManager.getUnusedMotorPosition()).shortValue();
                                    }
                                    break;
                                case R.id.lnb_option_motor_diseqc_1_3:
                                    mLayoutFuncBar.setVisibility(View.VISIBLE);
                                    tv = (TextView) findViewById(R.id.lnboption_yellow_str);
                                    tv.setVisibility(View.VISIBLE);
                                    iv = (ImageView) findViewById(R.id.lnboption_yellow_img);
                                    iv.setVisibility(View.VISIBLE);
                                    if (0 == mCurrentSatInfo.getPositionContentBit()) {
                                        mCurrentSatInfo.position = Integer.valueOf(mTvDvbChannelManager.getUnusedMotorPosition()).shortValue();
                                    }
                                    break;
                                default:
                                    Log.e(TAG, "this is a error id in page motor !!");
                                    break;
                            }
                            mTvDvbChannelManager.updateSatelliteInfo(mCurrentSatNum,  mCurrentSatInfo);
                        }
                    }
                };

                mTvMotorNone.setOnFocusChangeListener(onFocusChangeMotorListener);
                mTvMotorDiSEqC_1_2.setOnFocusChangeListener(onFocusChangeMotorListener);
                mTvMotorDiSEqC_1_3.setOnFocusChangeListener(onFocusChangeMotorListener);

                // set default focus item
                switch (mMotorPageSelection) {
                    case Constant.LNBOPTION_MOTOR_NONE:
                        mTvMotorNone.requestFocus();
                        break;
                    case Constant.LNBOPTION_MOTOR_1_2:
                        mTvMotorDiSEqC_1_2.requestFocus();
                        break;
                    case Constant.LNBOPTION_MOTOR_1_3:
                        mTvMotorDiSEqC_1_3.requestFocus();
                        break;
                    default:
                        mTvMotorNone.requestFocus();
                        break;
                }
                break;
            case Constant.LNBOPTION_PAGETYPE_SINGLECABLE:
                mTvOptionTitle.setText(getResources()
                        .getString(R.string.str_lnbsetting_singlecable));
                mLvOption.setVisibility(View.GONE);
                mLayoutFuncBar.setVisibility(View.GONE);
                mLayoutFreq.setVisibility(View.GONE);
                mLayoutFreqLow.setVisibility(View.GONE);
                mLayoutFreqHigh.setVisibility(View.GONE);
                mTvMotorNone.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_2.setVisibility(View.GONE);
                mTvMotorDiSEqC_1_3.setVisibility(View.GONE);
                mCbSingleCableSlot = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_single_cable_slot_id), R.id.lnb_option_single_cable_slot,
                        0, 1, false) {
                };
                mSbSingleCableFreq = new SeekBarButton(this, R.id.lnb_option_single_cable_freq, 1,
                        false) {
                };
                mSbSingleCablePin = new SeekBarButton(this, R.id.lnb_option_single_cable_pin, 1,
                        false) {
                };
                mCbSingleCableLnb = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_single_cable_lnb_option), R.id.lnb_option_single_cable_lnb,
                        0, 1, false) {
                };

                OnClickListener onClickSingleCableListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResultToLNBSettingPage(v);
                        finish();
                    }
                };
                mTvSingleCableVerify.setOnClickListener(onClickSingleCableListener);
                break;
            default:
                break;
        }
    }

    private void refreshOptionAdapter() {
        mOptionListItem.clear();
        getOptionList();
        mLnbOptionEditAdapter.notifyDataSetChanged();
        mLvOption.invalidate();
    }

    private void setupOptionAdapter() {
        if ((Constant.LNBOPTION_PAGETYPE_SATELLITE != mOptionType)
                && (Constant.LNBOPTION_PAGETYPE_TRANSPONDER != mOptionType)) {
            Log.e(TAG, "this function only used for satellite and transponder setting page");
            return;
        }
        mOptionListItem.clear();
        getOptionList();
        mLnbOptionEditAdapter = new LnbOptionEditAdapter(this, mOptionListItem);
        mLvOption.setAdapter(mLnbOptionEditAdapter);
        mLvOption.setDividerHeight(0);
        mLvOption.setSelection(0);
        mLvOption.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((KeyEvent.KEYCODE_ENTER == keyCode)
                        && (KeyEvent.ACTION_UP == keyEvent.getAction())) {
                    setResultToLNBSettingPage(view);
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void createDeleteDialog() {
        mDeleteDialog = new AlertDialog.Builder(this);
        mDeleteDialog.setTitle(R.string.str_lnboption_editor_del_dialog_title);
        mDeleteDialog.setMessage(R.string.str_lnboption_editor_del_dialog_msg);
        mDeleteDialog.setPositiveButton(R.string.str_lnboption_editor_del_dialog_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete selected item
                        int focusNumber = mLvOption.getSelectedItemPosition();
                        if (Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType) {
                            mTvDvbChannelManager.deleteSatelliteInfo(focusNumber);
                        } else if (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType) {
                            int satNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
                            mTvDvbChannelManager.deleteTransponderInfo(satNum, focusNumber);
                        }
                        Log.d(TAG, "delete item:" + focusNumber);
                        refreshOptionAdapter();
                        dialog.dismiss();
                    }
                });
        mDeleteDialog.setNegativeButton(R.string.str_lnboption_editor_del_dialog_no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel to delete and return to previous page
                        dialog.dismiss();
                    }
                });
        mDeleteDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        mDeleteDialog.create();
    }

    private void setResultToLNBSettingPage(View view) {
        Intent resultIntent = new Intent(LnbOptionActivity.this, LNBSettingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.LNBOPTION_PAGETYPE, mOptionType);
        if (Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType) {
            // send satellite id as result to LNBSettingActivity
            String resStr = Integer.toString(mLvOption.getSelectedItemPosition());
            Log.d(TAG, "set satellite id:" + resStr + " as result to LnbSetting");
            bundle.putString(RESULT_INTENT_SAT_TAG, resStr);
        } else if (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType) {
            // send satellite id as result to LNBSettingActivity
            String resStr = (mOptionListItem.get(mLvOption.getSelectedItemPosition())).getNumber();
            Log.d(TAG, "set transponder id:" + resStr + " as result to LnbSetting");
            bundle.putString(RESULT_INTENT_TP_TAG, resStr);
        } else if (Constant.LNBOPTION_PAGETYPE_FREQUENCIES == mOptionType) {
            // send frequencies option index and low/high frequencies value to
            // LNBSettingActivity
            Log.d(TAG, "freq idx:" + mCbLNBFreq.getIdx() + " low:" + mSbLNBFreqLow.getProgressInt()
                    + " high:" + mSbLNBFreqHigh.getProgressInt());
            bundle.putString(RESULT_INTENT_FREQ_TAG, Short.toString(mCbLNBFreq.getIdx()));
            bundle.putInt(RESULT_FREQ_LOW_TAG, mSbLNBFreqLow.getProgressInt());
            bundle.putInt(RESULT_FREQ_HIGH_TAG, mSbLNBFreqHigh.getProgressInt());
        } else if (Constant.LNBOPTION_PAGETYPE_MOTOR == mOptionType) {
            Log.d(TAG, "set motor result");
        } else if (Constant.LNBOPTION_PAGETYPE_SINGLECABLE == mOptionType) {
            Log.d(TAG, "set single cable result");
            int satNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
            SatelliteInfo satInfo = mTvDvbChannelManager.getSatelliteInfo(satNum);
            if (null != satInfo) {
                satInfo.channelId = mCbSingleCableSlot.getIdx();
                satInfo.frequency = mSbSingleCableFreq.getProgressInt();
                satInfo.lnbFreq = TvDvbChannelManager.LNB_FREQ_UNICABLE;
                satInfo.lnbIdx = mCbSingleCableLnb.getIdx();
                if (0 == mSbSingleCablePin.getProgressInt()) {
                    satInfo.mdu = false;
                } else {
                    satInfo.mdu = true;
                }
                satInfo.pin = mSbSingleCablePin.getProgress();
            }
            mTvDvbChannelManager.updateSatelliteInfo(satNum, satInfo);
            boolean verifyResult = mTvDvbChannelManager.verifySlotFrequency();
            bundle.putBoolean(RESULT_INTENT_SINGLECABLE_TAG, verifyResult);
            if (true == verifyResult) {
                String resStr = getResources().getString(R.string.str_lnbsetting_singlecable_slot);
                resStr += " ";
                resStr += mCbSingleCableSlot.getItemNameByIdx(mCbSingleCableSlot.getIdx());
                bundle.putString(RESULT_INTENT_SINGLECABLE_SLOT, resStr);
            }
        }
        resultIntent.putExtras(bundle);
        startActivity(resultIntent);
    }

    private void getOptionList() {
        if (Constant.LNBOPTION_PAGETYPE_SATELLITE == mOptionType) {
            addSatelliteItem();
        } else if (Constant.LNBOPTION_PAGETYPE_TRANSPONDER == mOptionType) {
            addTransponderItem();
        }
    }

    private void initFuncBar() {
        TextView tv = null;
        switch (mOptionType) {
            case Constant.LNBOPTION_PAGETYPE_SATELLITE:
            case Constant.LNBOPTION_PAGETYPE_TRANSPONDER:
                tv = (TextView) findViewById(R.id.lnboption_red_str);
                tv.setText(getResources().getString(R.string.str_lnboption_menu_hint_add));
                tv = (TextView) findViewById(R.id.lnboption_green_str);
                tv.setText(getResources().getString(R.string.str_lnboption_menu_hint_delete));
                tv = (TextView) findViewById(R.id.lnboption_yellow_str);
                tv.setText(getResources().getString(R.string.str_lnboption_menu_hint_edit));
                break;
            case Constant.LNBOPTION_PAGETYPE_MOTOR:
                tv = (TextView) findViewById(R.id.lnboption_red_str);
                tv.setText(getResources().getString(R.string.str_lnboption_menu_hint_position));
                tv = (TextView) findViewById(R.id.lnboption_green_str);
                tv.setText(getResources().getString(R.string.str_lnboption_menu_hint_limit));
                tv = (TextView) findViewById(R.id.lnboption_yellow_str);
                tv.setText(getResources().getString(R.string.str_lnboption_menu_hint_location));
                break;
            default:
                break;
        }
    }

    private void setFreqOptionFocusable() {
        // if type is not user mode, set enable to false
        if (TvDvbChannelManager.LNB_FREQ_USER_DEFINED != mCbLNBFreq.getIdx()) {
            mSbLNBFreqLow.setEnable(false);
            mSbLNBFreqHigh.setEnable(false);
            mSbLNBFreqLow.setFocusable(false);
            mSbLNBFreqHigh.setFocusable(false);
        } else {
            mSbLNBFreqLow.setEnable(true);
            mSbLNBFreqHigh.setEnable(true);
            mSbLNBFreqLow.setFocusable(true);
            mSbLNBFreqHigh.setFocusable(true);
        }
    }

    private void freshFreqWhenIdxChange() {
        int idx = mCbLNBFreq.getIdx();
        mSbLNBFreqLow.setProgressInt(Integer.valueOf(mFreqLowValue[idx]));
        mSbLNBFreqHigh.setProgressInt(Integer.valueOf(mFreqHighValue[idx]));
    }

    private String checkInputNumber(int inputNo, String checkNo, int maxNo) {
        int curNum = 0;
        int outputNumber = 0;
        checkNo = checkNo + Integer.toString(inputNo);
        curNum = Integer.parseInt(checkNo);
        if (curNum > maxNo) {
            checkNo = Integer.toString(inputNo);
            outputNumber = inputNo;
        } else if (checkNo.length() >= Integer.toString(maxNo).length()) {
            checkNo = Integer.toString(inputNo);
            outputNumber = inputNo;
        } else {
            outputNumber = curNum;
        }
        return Integer.toString(outputNumber);
    }

    private void addSatelliteItem() {
        int satCount = mTvDvbChannelManager.getCurrentSatelliteCount();
        for (int i = 0; i < satCount; i++) {
            SatelliteInfo info = mTvDvbChannelManager.getSatelliteInfo(i);
            LnbOptionListViewItemObject item = new LnbOptionListViewItemObject();
            item.setNumber(Integer.toString(i));
            item.setData1(info.satName);
            item.setData2("");
            if (TvDvbChannelManager.LNB_BAND_TYPE_C == info.lnbType) {
                item.setData3(mLnbBandType[TvDvbChannelManager.LNB_BAND_TYPE_C]);
            } else {
                item.setData3(mLnbBandType[TvDvbChannelManager.LNB_BAND_TYPE_KU]);
            }
            mOptionListItem.add(item);
        }
    }

    private void addTransponderItem() {
        int satNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
        int tpCount = mTvDvbChannelManager.getTransponderCount(satNum);
        for (int i = 0; i < tpCount; i++) {
            DvbsTransponderInfo info = mTvDvbChannelManager.getTransponderInfo(satNum, i);
            LnbOptionListViewItemObject item = new LnbOptionListViewItemObject();
            item.setNumber(String.valueOf(i));
            item.setData1(String.valueOf(info.frequency) + "(MHz)");
            item.setData2(String.valueOf(info.symbolRate) + "(kS/s)");
            if (TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL == info.polarity) {
                item.setData3(mTpPolarType[TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL]);
            } else {
                item.setData3(mTpPolarType[TvDvbChannelManager.TRANSPONDER_POLARITY_HORIZONTAL]);
            }
            mOptionListItem.add(item);
        }
    }
}
