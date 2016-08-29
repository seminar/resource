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

package com.mstar.tv.tvplayer.ui.tuning.dvb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnCancelListener;

import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MultiSelector;
import com.mstar.tv.tvplayer.ui.tuning.AutoTuneOptionActivity;
import com.mstar.tv.tvplayer.ui.tuning.ChannelTuning;
import com.mstar.tv.tvplayer.ui.tuning.DTVAutoTuneOptionActivity;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;

import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsScanParam;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.SatelliteInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsTransponderInfo;

public class DvbsDTVAutoTuneOptionActivity extends MstarBaseActivity {

    private static final String TAG = "DvbsDTVAutoTuneOptionActivity";

    private final int REQUEST_CODE_OF_SATELLITE = 1;

    private final int REQUEST_CODE_OF_TRANSPONDER = 2;

    private final String[] mScanMode = {
            "Free", "Free+Scramble", "Scramble"
    };

    private final String[] mServiceType = {
            "DTV", "DTV+Radio", "Radio"
    };

    private final String[] mSearchOption = {
            "BLIND SCAN", "FAST SCAN"
    };

    private final String[] mLNBType = {
            "C", "Ku"
    };

    private final String[] mPolarity = {
            "V", "H"
    };

    private final String[] mNetworkSearchOption = {
            "Off", "On"
    };

    private class TransponderData {
        private int mId = 0;

        private boolean selected = false;

        private String mFreq = "";

        private String mSymRate = "";

        private int mPola = 0;
    }

    private class SatelliteData {
        private int mId = 0;

        private boolean selected = false;

        private String mName = "";

        private String mBand = "";
    }

    private TvChannelManager mTvChannelManager = null;

    private TvDvbChannelManager mTvDvbChannelManager = null;

    private ComboButton mComboBtnSearch;

    private ComboButton mComboBtnScanMode;

    private ComboButton mComboBtnServiceType;

    private ComboButton mComboBtnNetworkSearch;

    private TextView mSatelliteTextView;

    private TextView mTransponderTextView;

    private String mSatelliteStr;

    private String mTransponderStr;

    private SatelliteData mCurrentSatellite;

    private TransponderData mCurrentTransponder;

    private Spinner mTransponderSpinner;

    private ArrayAdapter<String> mTransponderAdapter;

    private SatelliteData[] mSatelliteList;

    private TransponderData[] mTransponderList;

    private int[] mMultiSatelliteList;

    private int[] mMultiTransponderList;

    private String[] mSatelliteStrList;

    private String[] mTransponderStrList;

    private boolean[] mSatelliteSelectedList;

    private boolean[] mTransponderSelectedList;

    private MultiSelector mSatMultiSelector = null;

    private MultiSelector mTpMultiSelector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvChannelManager = TvChannelManager.getInstance();
        mTvDvbChannelManager = TvDvbChannelManager.getInstance();
        mCurrentSatellite = new SatelliteData();
        mCurrentTransponder = new TransponderData();
        mTransponderStr = getResources().getString(R.string.str_cha_dvbsdtvtuning_transponder);
        mSatelliteStr = getResources().getString(R.string.str_cha_dvbsdtvtuning_satellite);
        setContentView(R.layout.dvbs_dtv_auto_tune_option);
        createMainPageItem();
        mComboBtnScanMode.setIdx(0);
        mComboBtnServiceType.setIdx(0);
        mComboBtnNetworkSearch.setIdx(0);
        mComboBtnSearch.setIdx(0);
        mComboBtnSearch.setOnClickListener(clickListener);
        mSatelliteTextView.setOnClickListener(clickListener);
        mTransponderTextView.setOnClickListener(clickListener);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
        if (false == settings.getBoolean(Constant.PREFERENCES_IS_AUTOSCAN_LAUNCHED, false)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constant.PREFERENCES_IS_AUTOSCAN_LAUNCHED, true).commit();
        }
        // switch inputsource to dtv beforce setting options.
        final int curInputsource = TvCommonManager.getInstance().getCurrentTvInputSource();
        if (TvCommonManager.INPUT_SOURCE_DTV != curInputsource) {
            TvCommonManager.getInstance().setInputSource(TvCommonManager.INPUT_SOURCE_DTV);
        }
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

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linearlayout_cha_dvbsdtvautotuning_tuneoption: {
                    mTvChannelManager.setUserScanType(TvChannelManager.TV_SCAN_DTV);
                    DvbsScanParam param = new DvbsScanParam();
                    param.setSymbolRate(Integer.valueOf(mCurrentTransponder.mSymRate));
                    param.setNetworkSearch(mComboBtnNetworkSearch.getIdx());
                    param.setScanMode(mComboBtnScanMode.getIdx());
                    param.setServiceType(mComboBtnServiceType.getIdx());
                    if (0 == mCurrentTransponder.mPola) {
                        param.setPolarization(false);
                    } else {
                        param.setPolarization(true);
                    }
                    param.satelliteArray = new int[1];
                    param.satelliteArray[0] = mCurrentSatellite.mId;
                    mTvDvbChannelManager.setDvbsScanParam(param);
                    Intent intent = new Intent();
                    intent.setClass(DvbsDTVAutoTuneOptionActivity.this, ChannelTuning.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case R.id.textview_satellite: {
                    // create dialog to show satellite selections
                    showSatelliteSelector();
                    break;
                }
                case R.id.textview_transponder: {
                    // create dialog to show transponder selections
                    showTransponderSelector();
                    break;
                }
                default:
                    break;
            }
        }
    };

    private OnCancelListener satCancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // get selected list from MultiSelector
            int count = 0;
            mSatelliteSelectedList = mSatMultiSelector.getResults();
            for (int i = 0; i < mSatelliteSelectedList.length; i++) {
                if (true == mSatelliteSelectedList[i]) {
                    count++;
                }
            }
            // prepare satellite list for scanning
            mMultiSatelliteList = new int[count];
            count = 0;
            for (int j = 0; j < mSatelliteSelectedList.length; j++) {
                if (true == mSatelliteSelectedList[j]) {
                    mMultiSatelliteList[count] = mSatelliteList[j].mId;
                    count++;
                }
            }
            // set satellite to last selected one.
            int satNum = mMultiSatelliteList[mMultiSatelliteList.length - 1];
            mTvDvbChannelManager.setCurrentSatelliteNumber(satNum);
            updateCurrentSatellite(mSatelliteList[satNum]);
            updateSatelliteViewContent();
            mTransponderList = null;
            mMultiTransponderList = null;
            updateTransponderViewContent();
        }
    };

    private OnCancelListener tpCancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // get selected list from MultiSelector
            int count = 0;
            mTransponderSelectedList = mTpMultiSelector.getResults();
            for (int i = 0; i < mTransponderSelectedList.length; i++) {
                if (true == mTransponderSelectedList[i]) {
                    count++;
                }
            }
            // prepare transponder list for scanning
            mMultiTransponderList = new int[count];
            count = 0;
            for (int i = 0; i < mTransponderSelectedList.length; i++) {
                if (true == mTransponderSelectedList[i]) {
                    mMultiTransponderList[count] = mTransponderList[i].mId;
                    count++;
                }
            }
            // set transponder to last selected one.
            int tpNum = mMultiTransponderList[mMultiTransponderList.length - 1];
            mTvDvbChannelManager.setCurrentTransponderNumber(tpNum);
            updateCurrentTransponder(mTransponderList[tpNum]);
            updateTransponderViewContent();
        }
    };

    private void showSatelliteSelector() {
        mSatMultiSelector = new MultiSelector(this, AlertDialog.THEME_HOLO_DARK);
        if (null == mSatelliteList) {
            // update mSatelliteList
            int num = mTvDvbChannelManager.getCurrentSatelliteCount();
            mSatelliteList = new SatelliteData[num];
            mSatelliteStrList = new String[num];
            mSatelliteSelectedList = new boolean[num];
            for (int i = 0; i < num; i++) {
                SatelliteInfo info = mTvDvbChannelManager.getSatelliteInfo(i);
                mSatelliteList[i] = new SatelliteData();
                mSatelliteList[i].mId = i;
                mSatelliteList[i].mName = info.satName;
                mSatelliteList[i].mBand = mLNBType[info.lnbType];
                mSatelliteStrList[i] = mSatelliteList[i].mId + " "
                        + info.satName + " " + mSatelliteList[i].mBand;
                // let current satellite be default selected one.
                if (mCurrentSatellite.mId == mSatelliteList[i].mId) {
                    mSatelliteList[i].selected = true;
                    mSatelliteSelectedList[i] = true;
                } else {
                    mSatelliteList[i].selected = false;
                    mSatelliteSelectedList[i] = false;
                }
            }
        }
        mSatMultiSelector.setMultiChoiceItems(mSatelliteStrList, mSatelliteSelectedList);
        mSatMultiSelector.setOnCancelListener(satCancelListener);
        mSatMultiSelector.show();
    }

    private void showTransponderSelector() {
        mTpMultiSelector = new MultiSelector(this, AlertDialog.THEME_HOLO_DARK);
        if (null == mTransponderList) {
            // update mSatelliteList
            int num = mTvDvbChannelManager.getCurrentSatelliteNumber();
            int count = mTvDvbChannelManager.getTransponderCount(num);
            mTransponderList = new TransponderData[count];
            mTransponderStrList = new String[count];
            mTransponderSelectedList = new boolean[count];
            for (int i = 0; i < count; i++) {
                DvbsTransponderInfo info = mTvDvbChannelManager.getTransponderInfo(num, i);
                mTransponderList[i] = new TransponderData();
                mTransponderList[i].mId = i;
                mTransponderList[i].mFreq = String.valueOf(info.frequency);
                mTransponderList[i].mSymRate = String.valueOf(info.symbolRate);
                mTransponderList[i].mPola = info.polarity;
                mTransponderStrList[i] = mTransponderList[i].mFreq + "(MHz) "
                        + mPolarity[mTransponderList[i].mPola] + " "
                        + mTransponderList[i].mSymRate + "(kS/s)";
                if (mCurrentTransponder.mId == mTransponderList[i].mId) {
                    mTransponderList[i].selected = true;
                    mTransponderSelectedList[i] = true;
                } else {
                    mTransponderList[i].selected = false;
                    mTransponderSelectedList[i] = false;
                }
            }
        }
        mTpMultiSelector.setMultiChoiceItems(mTransponderStrList, mTransponderSelectedList);
        mTpMultiSelector.setOnCancelListener(tpCancelListener);
        mTpMultiSelector.show();
    }

    private void updateTransponderViewContent() {
        // multi transponder list is null means this is first time to show page.
        // get current transponder info by satellite number and update to
        // mCurrentTransponder
        if (null == mMultiTransponderList) {
            int satNum = mTvDvbChannelManager.getCurrentSatelliteNumber();
            int tpNum = mTvDvbChannelManager.getCurrentTransponderNumber();
            DvbsTransponderInfo tpInfo = mTvDvbChannelManager.getTransponderInfo(satNum, tpNum);
            TransponderData tpData = new TransponderData();
            tpData.mId = tpNum;
            tpData.selected = true;
            tpData.mFreq = String.valueOf(tpInfo.frequency);
            tpData.mSymRate = String.valueOf(tpInfo.symbolRate);
            tpData.mPola = tpInfo.polarity;
            updateCurrentTransponder(tpData);
        }
        // update text to text view.
        String tmpStr = mTransponderStr + " "
                + mCurrentTransponder.mFreq + " "
                + mPolarity[mCurrentTransponder.mPola] + " "
                + mCurrentTransponder.mSymRate;
        mTransponderTextView.setText(tmpStr);
    }

    private void updateSatelliteViewContent() {
        // multi satellite list is null means this is first time to show page.
        // get current satellite number for tvservice and update to
        // mCurrentSatellie
        if (null == mMultiSatelliteList) {
            int num = mTvDvbChannelManager.getCurrentSatelliteNumber();
            SatelliteInfo satInfo = mTvDvbChannelManager.getSatelliteInfo(num);
            SatelliteData satData = new SatelliteData();
            satData.mId = num;
            satData.selected = true;
            satData.mName = satInfo.satName;
            satData.mBand = mLNBType[satInfo.lnbType];
            updateCurrentSatellite(satData);
        }
        // update text to text view.
        mSatelliteTextView.setText(mSatelliteStr + " " + mCurrentSatellite.mName);
    }

    private void createMainPageItem() {
        mComboBtnScanMode = new ComboButton(this, mScanMode,
                R.id.linearlayout_cha_dvbsdtvautotuning_scanmode, 1, 2, false) {
        };
        mComboBtnServiceType = new ComboButton(this, mServiceType,
                R.id.linearlayout_cha_dvbsdtvautotuning_servicetype, 1, 2, false) {
        };
        mComboBtnNetworkSearch = new ComboButton(this, mNetworkSearchOption,
                R.id.linearlayout_cha_dvbsdtvautotuning_networksearch, 1, 2, false) {
        };
        mComboBtnSearch = new ComboButton(this, mSearchOption,
                R.id.linearlayout_cha_dvbsdtvautotuning_tuneoption, 1, 2, false) {
        };

        mSatelliteTextView = (TextView) findViewById(R.id.textview_satellite);
        mTransponderTextView = (TextView) findViewById(R.id.textview_transponder);
        updateSatelliteViewContent();
        updateTransponderViewContent();
    }

    private void updateCurrentTransponder(TransponderData data) {
        mCurrentTransponder.mId = data.mId;
        mCurrentTransponder.selected = data.selected;
        mCurrentTransponder.mFreq = data.mFreq;
        mCurrentTransponder.mSymRate = data.mSymRate;
        mCurrentTransponder.mPola = data.mPola;
    }

    private void updateCurrentSatellite(SatelliteData data) {
        mCurrentSatellite.mId = data.mId;
        mCurrentSatellite.selected = data.selected;
        mCurrentSatellite.mName = data.mName;
        mCurrentSatellite.mBand = data.mBand;
    }

    private void reNewMultiSatelliteList(int[] list) {
        mMultiSatelliteList = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            mMultiSatelliteList[i] = list[i];
        }
    }

    private void reNewMultiTransponderList(int[] list) {
        mMultiTransponderList = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            mMultiTransponderList[i] = list[i];
        }
    }
}
