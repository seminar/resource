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

package com.mstar.tv.tvplayer.ui.channel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.dtv.lnb.dvb.LnbOptionActivity;
import com.mstar.util.Constant;
import com.mstar.tvframework.MstarBaseActivity;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.SatelliteInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsTransponderInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsScanParam;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;

public class LNBSettingActivity extends MstarBaseActivity {

    private static final String TAG = "LNBSettingActivity";

    private TextView mTvSatellite = null;

    private TextView mTvTransponder = null;

    private TextView mTvFrequencies = null;

    private TextView mTvMotor = null;

    private TextView mTvSingleCableSlot = null;

    private ComboButton mComboBtnLNBPower = null;

    private ComboButton mComboBtnCompensation = null;

    private ComboButton mComboBtn22kHzTone = null;

    private ComboButton mComboBtnToneburst = null;

    private ComboButton mComboBtnDiSEqC_1_0 = null;

    private ComboButton mComboBtnDiSEqC_1_1 = null;

    private ProgressBar mProgressBarQuality = null;

    private ProgressBar mProgressBarStrength = null;

    private String[] mStrTpPolarOption = null;

    private String[] mStrMotorDiSEqCVersion = null;

    private String[] mStrSingleCableSlotId = null;

    private int mCurrentSatNo = 0;

    private int mCurrentTpNo = 0;

    private int mCurrentFreqTypeIdx = 0;

    private int mCurrentFreqLow = 0;

    private int mCurrentFreqHigh = 0;

    private Handler mDtvUiEventHandler = null;

    private SatelliteInfo mCurrentSatInfo = new SatelliteInfo();

    private DvbsTransponderInfo mCurrentTpInfo = new DvbsTransponderInfo();

    private TvDvbChannelManager mTvDvbChannelManager = null;

    private DtvEventListener mDtvEventListener = null;

    private class DtvEventListener implements OnDtvPlayerEventListener {

        @Override
        public boolean onDtvChannelNameReady(int what) {
            return false;
        }

        @Override
        public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
            Message msg = mDtvUiEventHandler.obtainMessage(what, extra);
            mDtvUiEventHandler.sendMessage(msg);
            return true;
        }

        @Override
        public boolean onDtvProgramInfoReady(int what) {
            return false;
        }

        @Override
        public boolean onCiLoadCredentialFail(int what) {
            return false;
        }

        @Override
        public boolean onEpgTimerSimulcast(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onHbbtvStatusMode(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onMheg5StatusMode(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onMheg5ReturnKey(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onOadHandler(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onOadDownload(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onDtvAutoUpdateScan(int what) {
            return false;
        }

        @Override
        public boolean onTsChange(int what) {
            return false;
        }

        @Override
        public boolean onPopupScanDialogLossSignal(int what) {
            return false;
        }

        @Override
        public boolean onPopupScanDialogNewMultiplex(int what) {
            return false;
        }

        @Override
        public boolean onPopupScanDialogFrequencyChange(int what) {
            return false;
        }

        @Override
        public boolean onRctPresence(int what) {
            return false;
        }

        @Override
        public boolean onChangeTtxStatus(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onDtvPriComponentMissing(int what) {
            return false;
        }

        @Override
        public boolean onAudioModeChange(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onMheg5EventHandler(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onOadTimeout(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onGingaStatusMode(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onSignalLock(int what) {
            return false;
        }

        @Override
        public boolean onSignalUnLock(int what) {
            return false;
        }

        @Override
        public boolean onUiOPRefreshQuery(int what) {
            return false;
        }

        @Override
        public boolean onUiOPServiceList(int what) {
            return false;
        }

        @Override
        public boolean onUiOPExitServiceList(int what) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dvbs_lnb_setting);
        mTvSatellite = (TextView) findViewById(R.id.linearlayout_lnbsetting_satellite);
        mTvTransponder = (TextView) findViewById(R.id.linearlayout_lnbsetting_transponder);
        mTvFrequencies = (TextView) findViewById(R.id.linearlayout_lnbsetting_frequencies);
        mTvMotor = (TextView) findViewById(R.id.linearlayout_lnbsetting_motor);
        mTvSingleCableSlot = (TextView) findViewById(R.id.linearlayout_lnbsetting_singlecable);
        mProgressBarQuality = (ProgressBar) findViewById(R.id.progressbar_signalquality);
        mProgressBarStrength = (ProgressBar) findViewById(R.id.progressbar_strength);
        mComboBtnLNBPower = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_lnbpower_option), R.id.linearlayout_lnbsetting_lnbpower, 0, 1,
                false) {
            @Override
            public void doUpdate() {
                updateCurrentEditSatelliteInfo();
                mTvDvbChannelManager.setDishLNBPowerMode(mComboBtnLNBPower.getIdx());
                scanCurrentFreq();
            }
        };
        mComboBtnCompensation = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_compensation_option), R.id.linearlayout_lnbsetting_compensation, 0,
                1, false) {
        };
        mComboBtn22kHzTone = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_22knz_tone_option), R.id.linearlayout_lnbsetting_22knz_tone, 0, 1,
                false) {
            @Override
            public void doUpdate() {
                updateCurrentEditSatelliteInfo();
                mTvDvbChannelManager.setDish22KMode(mComboBtn22kHzTone.getIdx());
                scanCurrentFreq();
            }
        };
        mComboBtnToneburst = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_toneburst_option), R.id.linearlayout_lnbsetting_toneburst, 0, 1,
                false) {
            @Override
            public void doUpdate() {
                updateCurrentEditSatelliteInfo();
                mTvDvbChannelManager.setDishToneburstMode(mComboBtnToneburst.getIdx());
                scanCurrentFreq();
            }
        };
        mComboBtnDiSEqC_1_0 = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_diseqc_1_0_option), R.id.linearlayout_lnbsetting_diseqc_1_0, 0, 1,
                false) {
            @Override
            public void doUpdate() {
                updateCurrentEditSatelliteInfo();
                mTvDvbChannelManager.setDiSEqCSwitchPort(mComboBtnDiSEqC_1_0.getIdx(), 0);
                scanCurrentFreq();
            }
        };
        mComboBtnDiSEqC_1_1 = new ComboButton(this, getResources().getStringArray(
                R.array.str_arr_diseqc_1_1_option), R.id.linearlayout_lnbsetting_diseqc_1_1, 0, 1,
                false) {
            @Override
            public void doUpdate() {
                updateCurrentEditSatelliteInfo();
                mTvDvbChannelManager.setDiSEqCSwitchPort(mComboBtnDiSEqC_1_1.getIdx(), 1);
                scanCurrentFreq();
            }
        };
        mStrTpPolarOption = getResources().getStringArray(
                R.array.str_arr_transponder_polarization_option);
        mStrMotorDiSEqCVersion = getResources().getStringArray(R.array.str_arr_motor_diseqc_version);
        mStrSingleCableSlotId = getResources().getStringArray(R.array.str_arr_single_cable_slot_id);

        mTvDvbChannelManager = TvDvbChannelManager.getInstance();

        updateUiContent();
        setClickListener();

        mDtvUiEventHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                DtvEventScan scanEvent = (DtvEventScan) msg.obj;
                Log.d(TAG, "get scan info quality:" + scanEvent.signalQuality);
                Log.d(TAG, "get scan info strength:" + scanEvent.signalStrength);
                mProgressBarQuality.setProgress(scanEvent.signalQuality);
                mProgressBarStrength.setProgress(scanEvent.signalStrength);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDtvEventListener = new DtvEventListener();
        TvChannelManager.getInstance().registerOnDtvPlayerEventListener(mDtvEventListener);
        scanCurrentFreq();
        Log.d(TAG, "onResume()");
    };

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        TvChannelManager.getInstance().unregisterOnDtvPlayerEventListener(mDtvEventListener);
        mDtvEventListener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                Intent intent = new Intent(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String resStr = null;
        Bundle bundle = intent.getExtras();
        int backType = bundle.getInt(Constant.LNBOPTION_PAGETYPE);
        switch (backType) {
            case Constant.LNBOPTION_PAGETYPE_SATELLITE:
                // get selected satellite id from LnbOptionActivity
                resStr = bundle.getString(LnbOptionActivity.RESULT_INTENT_SAT_TAG);
                Log.d(TAG, "get satellite id:" + resStr);
                // set current satellite by result satellite id
                mTvDvbChannelManager.setCurrentSatelliteNumber(Integer.valueOf(resStr));
                // reset transponder id to first one
                mTvDvbChannelManager.setCurrentTransponderNumber(0);
                break;
            case Constant.LNBOPTION_PAGETYPE_TRANSPONDER:
                // get selected transponder id from LnbOptionActivity
                resStr = bundle.getString(LnbOptionActivity.RESULT_INTENT_TP_TAG);
                Log.d(TAG, "get transponder id:" + resStr);
                // set current transponder by result transponder id
                mTvDvbChannelManager.setCurrentTransponderNumber(Integer.valueOf(resStr));
                break;
            case Constant.LNBOPTION_PAGETYPE_FREQUENCIES:
                // get frequencies option index and low/high frequencies value
                mCurrentFreqTypeIdx = Integer.valueOf(bundle
                        .getString(LnbOptionActivity.RESULT_INTENT_FREQ_TAG));
                mCurrentFreqLow = bundle.getInt(LnbOptionActivity.RESULT_FREQ_LOW_TAG);
                mCurrentFreqHigh = bundle.getInt(LnbOptionActivity.RESULT_FREQ_HIGH_TAG);
                Log.d(TAG, "freq idx:" + mCurrentFreqTypeIdx + " low:" + mCurrentFreqLow + " high:"
                        + mCurrentFreqHigh);
                updateCurrentEditSatelliteInfo();
                break;
            case Constant.LNBOPTION_PAGETYPE_MOTOR:
                break;
            case Constant.LNBOPTION_PAGETYPE_SINGLECABLE:
                break;
            default:
                break;
        }
        // update ui content after getting result code
        updateUiContent();
    }

    private void updateUiContent() {
        String tmpStr = "";
        /*
         * Set Satellite Name, get Current Satellite Data Info from TvService.
         */
        mCurrentSatNo = mTvDvbChannelManager.getCurrentSatelliteNumber();
        mCurrentSatInfo = mTvDvbChannelManager.getSatelliteInfo(mCurrentSatNo);

        if (null != mTvSatellite) {
            if (null != mCurrentSatInfo) {
                mTvSatellite.setText(mCurrentSatInfo.satName);
            } else {
                mTvSatellite.setText("");
            }
        }

        /*
         * Set Transponder Info, Get Current Transponder Data Info by current
         * satellite number and current transponder number.
         */
        mCurrentTpNo = mTvDvbChannelManager.getCurrentTransponderNumber();
        mCurrentTpInfo = mTvDvbChannelManager.getTransponderInfo(mCurrentSatNo, mCurrentTpNo);
        if (null != mTvTransponder) {
            if (null != mCurrentTpInfo) {
                tmpStr = Integer.toString(mCurrentTpInfo.frequency);
                tmpStr += getResources().getString(R.string.str_lnbsetting_transponder_freq);
                tmpStr += " ";
                if (TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL == mCurrentTpInfo.polarity) {
                    // vertical position
                    tmpStr += mStrTpPolarOption[TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL];
                } else {
                    // horizontal position
                    tmpStr += mStrTpPolarOption[TvDvbChannelManager.TRANSPONDER_POLARITY_HORIZONTAL];
                }
                tmpStr += " ";
                tmpStr += Integer.toString(mCurrentTpInfo.symbolRate);
                tmpStr += getResources().getString(R.string.str_lnbsetting_transponder_symbol);
                mTvTransponder.setText(tmpStr);
            } else {
                mTvTransponder.setText("0 H 0");
            }
        }

        /*
         * Set Lnb Power by current satellite info.
         */
        if (null != mCurrentSatInfo) {
            switch (mCurrentSatInfo.lnbPwrOnOff) {
                case TvDvbChannelManager.LNB_POWER_MODE_13OR18V:
                case TvDvbChannelManager.LNB_POWER_MODE_14OR19V:
                    mComboBtnLNBPower.setIdx(TvDvbChannelManager.LNB_POWER_MODE_13OR18V);
                    break;
                case TvDvbChannelManager.LNB_POWER_MODE_13V:
                case TvDvbChannelManager.LNB_POWER_MODE_14V:
                    mComboBtnLNBPower.setIdx(TvDvbChannelManager.LNB_POWER_MODE_13V);
                    break;
                case TvDvbChannelManager.LNB_POWER_MODE_18V:
                case TvDvbChannelManager.LNB_POWER_MODE_19V:
                    mComboBtnLNBPower.setIdx(TvDvbChannelManager.LNB_POWER_MODE_18V);
                    break;
                default:
                    mComboBtnLNBPower.setIdx(TvDvbChannelManager.LNB_POWER_MODE_OFF);
                    break;
            }
        } else {
            mComboBtnLNBPower.setIdx(TvDvbChannelManager.LNB_POWER_MODE_OFF);
        }

        /*
         * Set Long cable compensation by current satellite info.
         */
        if (null != mCurrentSatInfo) {
            switch (mCurrentSatInfo.lnbPwrOnOff) {
                case TvDvbChannelManager.LNB_POWER_MODE_14OR19V:
                case TvDvbChannelManager.LNB_POWER_MODE_14V:
                case TvDvbChannelManager.LNB_POWER_MODE_19V:
                    // compensation on
                    mComboBtnCompensation.setIdx(1);
                    break;
                case TvDvbChannelManager.LNB_POWER_MODE_13OR18V:
                case TvDvbChannelManager.LNB_POWER_MODE_13V:
                case TvDvbChannelManager.LNB_POWER_MODE_18V:
                default:
                    // compensation off
                    mComboBtnCompensation.setIdx(0);
                    break;
            }
        } else {
            mComboBtnCompensation.setIdx(0);
        }

        /*
         * Set LNB Low/High Frequencies by current satellite info.
         */
        if (null != mTvFrequencies) {
            tmpStr = "";
            tmpStr = getResources().getString(R.string.str_lnbsetting_frequencies);
            tmpStr += " ";
            if (null != mCurrentSatInfo) {
                tmpStr += mCurrentSatInfo.lowLOF;
                tmpStr += "/";
                tmpStr += mCurrentSatInfo.hiLOF;
                mCurrentFreqLow = mCurrentSatInfo.lowLOF;
                mCurrentFreqHigh = mCurrentSatInfo.hiLOF;
            } else {
                tmpStr += "0/0";
                mCurrentFreqLow = 0;
                mCurrentFreqHigh = 0;
            }
            mTvFrequencies.setText(tmpStr);
        }

        /*
         * Set 22kHz Tone, Toneburst, DiSEqC 1.0/1.1 port by current satellite
         * info.
         */
        if (null != mCurrentSatInfo) {
            mComboBtn22kHzTone.setIdx(mCurrentSatInfo.e22KOnOff);
            mComboBtnToneburst.setIdx(mCurrentSatInfo.toneburstType);
            mComboBtnDiSEqC_1_0.setIdx(mCurrentSatInfo.swt10Port);
            mComboBtnDiSEqC_1_1.setIdx(mCurrentSatInfo.swt10Port);
        } else {
            mComboBtn22kHzTone.setIdx(TvDvbChannelManager.DISH_22K_MODE_AUTO);
            mComboBtnToneburst.setIdx(TvDvbChannelManager.DISH_TONE_BURST_MODE_NONE);
            mComboBtnDiSEqC_1_0.setIdx(TvDvbChannelManager.DISEQC_V_1_0_PORT_NONE);
            mComboBtnDiSEqC_1_1.setIdx(TvDvbChannelManager.DISEQC_V_1_1_PORT_NONE);
        }

        /*
         * Set Motor DiSEqc Version
         */
        if (null != mTvMotor) {
            tmpStr = "";
            tmpStr = getResources().getString(R.string.str_lnbsetting_motor);
            tmpStr += " ";
            if (null != mCurrentSatInfo) {
                tmpStr += mStrMotorDiSEqCVersion[mCurrentSatInfo.motorDiSEqCVer];
            } else {
                tmpStr += mStrMotorDiSEqCVersion[0];
            }
            mTvMotor.setText(tmpStr);
        }

        /*
         * Set Single Cable Slot port by current satellite info.
         */
        if (null != mTvSingleCableSlot) {
            tmpStr = "";
            tmpStr = getResources().getString(R.string.str_lnbsetting_singlecable);
            tmpStr += " ";
            tmpStr += getResources().getString(R.string.str_lnbsetting_singlecable_slot);
            tmpStr += " ";
            if (null != mCurrentSatInfo) {
                if (TvDvbChannelManager.LNB_FREQ_UNICABLE == mCurrentSatInfo.lnbFreq) {
                    tmpStr += mStrSingleCableSlotId[mCurrentSatInfo.channelId];
                } else {
                    tmpStr += mStrSingleCableSlotId[0];
                }
            }
            mTvSingleCableSlot.setText(tmpStr);
        }
    }

    private void scanCurrentFreq() {
        TvChannelManager.getInstance().stopDtvScan();
        DvbsScanParam param = new DvbsScanParam();
        param.setScanMode((Integer.valueOf(TvDvbChannelManager.SCAN_MODE_FREE)).shortValue());
        param.setServiceType((Integer.valueOf(TvDvbChannelManager.SERVICE_TYPE_TV_AUDIO)).shortValue());
        param.setNetworkSearch(TvDvbChannelManager.NETWORK_SEARCH_OFF);
        param.setSymbolRate(mCurrentTpInfo.symbolRate);
        if (TvDvbChannelManager.TRANSPONDER_POLARITY_VERTICAL == mCurrentTpInfo.polarity) {
            param.setPolarization(false);
        } else {
            param.setPolarization(true);
        }
        mTvDvbChannelManager.setDvbsScanParam(param);
        TvChannelManager.getInstance().setDtvManualScanByFreq(mCurrentTpInfo.frequency);
    }

    private void updateCurrentEditSatelliteInfo() {
        if ((mCurrentSatInfo.lowLOF != mCurrentFreqLow)
                || (mCurrentSatInfo.hiLOF != mCurrentFreqHigh)
                || (mCurrentSatInfo.lnbPwrOnOff != mComboBtnLNBPower.getIdx())
                || (mCurrentSatInfo.e22KOnOff != mComboBtn22kHzTone.getIdx())
                || (mCurrentSatInfo.toneburstType != mComboBtnToneburst.getIdx())
                || (mCurrentSatInfo.swt10Port != mComboBtnDiSEqC_1_0.getIdx())
                || (mCurrentSatInfo.swt11Port != mComboBtnDiSEqC_1_1.getIdx())) {
            // update satInfo content by current value
            mCurrentSatInfo.lowLOF = mCurrentFreqLow;
            mCurrentSatInfo.hiLOF = mCurrentFreqHigh;
            mCurrentSatInfo.lnbPwrOnOff = mComboBtnLNBPower.getIdx();
            mCurrentSatInfo.e22KOnOff = mComboBtn22kHzTone.getIdx();
            mCurrentSatInfo.toneburstType = mComboBtnToneburst.getIdx();
            mCurrentSatInfo.swt10Port = mComboBtnDiSEqC_1_0.getIdx();
            mCurrentSatInfo.swt11Port = mComboBtnDiSEqC_1_1.getIdx();

            // TODO: update motor position

            // update info
            mTvDvbChannelManager.updateSatelliteInfo(mCurrentSatNo, mCurrentSatInfo);
        }
    }

    private OnClickListener clickListener = new OnClickListener() {
        Intent clickIntent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linearlayout_lnbsetting_satellite:
                    isGoingToBeClosed(false);
                    clickIntent = new Intent(TvIntent.ACTION_LNBOPTION);
                    clickIntent.putExtra(Constant.LNBOPTION_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_SATELLITE);
                    startActivity(clickIntent);
                    break;
                case R.id.linearlayout_lnbsetting_transponder:
                    isGoingToBeClosed(false);
                    clickIntent = new Intent(TvIntent.ACTION_LNBOPTION);
                    clickIntent.putExtra(Constant.LNBOPTION_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_TRANSPONDER);
                    startActivity(clickIntent);
                    break;
                case R.id.linearlayout_lnbsetting_frequencies:
                    isGoingToBeClosed(false);
                    clickIntent = new Intent(TvIntent.ACTION_LNBOPTION);
                    clickIntent.putExtra(Constant.LNBOPTION_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_FREQUENCIES);
                    startActivity(clickIntent);
                    break;
                case R.id.linearlayout_lnbsetting_motor:
                    isGoingToBeClosed(false);
                    clickIntent = new Intent(TvIntent.ACTION_LNBOPTION);
                    clickIntent.putExtra(Constant.LNBOPTION_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_MOTOR);
                    startActivity(clickIntent);
                    break;
                case R.id.linearlayout_lnbsetting_singlecable:
                    isGoingToBeClosed(false);
                    clickIntent = new Intent(TvIntent.ACTION_LNBOPTION);
                    clickIntent.putExtra(Constant.LNBOPTION_PAGETYPE,
                            Constant.LNBOPTION_PAGETYPE_SINGLECABLE);
                    startActivity(clickIntent);
                    break;
                default:
                    break;
            }
        }
    };

    private void setClickListener() {
        mTvSatellite.setOnClickListener(clickListener);
        mTvTransponder.setOnClickListener(clickListener);
        mTvFrequencies.setOnClickListener(clickListener);
        mTvMotor.setOnClickListener(clickListener);
        mTvSingleCableSlot.setOnClickListener(clickListener);
    }
}
