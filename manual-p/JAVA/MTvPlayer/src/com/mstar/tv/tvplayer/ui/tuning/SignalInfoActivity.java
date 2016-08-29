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
//        of any kind whatsoever, or any information;or
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

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.android.tvapi.common.vo.DtvProgramSignalInfo;
import com.mstar.android.tvapi.common.vo.DtvProgramSignalInfo.EnumProgramDemodType;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.dtv.vo.OadCustomerInfo;
import com.mstar.android.tvapi.common.exception.TvOutOfBoundException;
import com.mstar.android.tvapi.dtv.vo.RfInfo;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvOadManager;
import com.mstar.tvframework.MstarBaseActivity;

public class SignalInfoActivity extends MstarBaseActivity {
    private static final String TAG = "SignalInfoActivity";

    private final static int DTV_REFRESH_UI = 0x01;

    private final static int REFRESH_UI_DELAY_TIME = 1000;

    private final static int PROGRESS_MIN = 0;

    private final static int PROGRESS_MAX = 9;

    private LinearLayout mLinearSoftwareVersion;

    private TextView mTextviewSoftwareVersionVal;

    private LinearLayout mLinearAntennatype;

    private TextView mTextviewAntennatypeVal;

    private TextView mTextviewChVal;

    private LinearLayout mLinearFrequencyIfno;

    private TextView mTextviewFrequencyVal;

    private LinearLayout mLinearModulation;

    private TextView mTextviewModulationVal;

    private LinearLayout mLinearNetwork;

    private TextView mTextviewNetwrokVal;

    private LinearLayout mLinearTransportStream;

    private TextView mTextviewTransportStreamVal;

    private LinearLayout mLinearService;

    private TextView mTextviewServiceVal;

    private LinearLayout mLinearSignalQualityVal;

    private LinearLayout mLinearSignalStrengthVal;

    private String[] mModulationStringTable;

    private int mTvSystem = 0;

    private TvChannelManager mTvChannelManager = null;

    private TvCommonManager mTvCommonManager = null;

    private TvAtscChannelManager mTvAtscChannelManager = null;

    private TvIsdbChannelManager mTvIsdbChannelManager = null;

    private DtvProgramSignalInfo mCurrSignalInfo = null;

    private Handler mMessageHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d(TAG, "mMessageHandler, msg.what = " + msg.what);
            switch (msg.what) {
                case DTV_REFRESH_UI:
                    int quality = 0;
                    int strength = 0;

                    mCurrSignalInfo = mTvChannelManager.getCurrentSignalInformation();
                    if (null != mCurrSignalInfo) {
                        quality = mCurrSignalInfo.quality;
                        strength = mCurrSignalInfo.strength;
                    }
                    setProgressBarValue(quality, strength);

                    mMessageHandler.sendEmptyMessageDelayed(DTV_REFRESH_UI, REFRESH_UI_DELAY_TIME);

                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signalinfo);
        findViewId();
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvChannelManager = TvChannelManager.getInstance();
        mTvCommonManager = TvCommonManager.getInstance();
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mTvAtscChannelManager = TvAtscChannelManager.getInstance();
        } else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            mTvIsdbChannelManager = TvIsdbChannelManager.getInstance();
        }
        mCurrSignalInfo = mTvChannelManager.getCurrentSignalInformation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        mMessageHandler.sendEmptyMessage(DTV_REFRESH_UI);
    }

    @Override
    protected void onPause() {
        mMessageHandler.removeMessages(DTV_REFRESH_UI);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void findViewId() {
        mLinearSoftwareVersion = (LinearLayout) findViewById(
            R.id.linearlayout_cha_softwareversion);
        mTextviewSoftwareVersionVal = (TextView) findViewById(
            R.id.textview_cha_softwareversion_val);

        mLinearAntennatype = (LinearLayout) findViewById(
            R.id.linearlayout_cha_antennatype);
        mTextviewAntennatypeVal = (TextView) findViewById(
            R.id.textview_cha_antennatype_val);

        mTextviewChVal = (TextView) findViewById(
            R.id.textview_cha_channelinfo_ch_val);

        mLinearFrequencyIfno = (LinearLayout) findViewById(
            R.id.linearlayout_cha_frequencyinfo);
        mTextviewFrequencyVal = (TextView) findViewById(
            R.id.textview_cha_channelinfo_frequency_val);

        mModulationStringTable = getResources().getStringArray(
            R.array.str_arr_signalinfo_modulation);
        mLinearModulation = (LinearLayout) findViewById(
            R.id.linearlayout_cha_modulation);
        mTextviewModulationVal = (TextView) findViewById(
            R.id.textview_cha_modulation_val);

        mLinearNetwork = (LinearLayout) findViewById(
            R.id.linearlayout_cha_network);
        mTextviewNetwrokVal = (TextView) findViewById(
            R.id.textview_cha_network_val);

        mLinearTransportStream = (LinearLayout) findViewById(
            R.id.linearlayout_cha_transport_stream);
        mTextviewTransportStreamVal = (TextView) findViewById(
            R.id.textview_cha_transport_stream_val);

        mLinearService = (LinearLayout) findViewById(
            R.id.linearlayout_cha_service);
        mTextviewServiceVal = (TextView) findViewById(
            R.id.textview_cha_service_val);

        mLinearSignalQualityVal = (LinearLayout) findViewById(
            R.id.linearlayout_cha_signalquality_val);
        mLinearSignalStrengthVal = (LinearLayout) findViewById(
            R.id.linearlayout_cha_signalstrength_val);
    }

    private void initView() {
        setSoftwareVersionInfo();
        setAtannaType();
        setChannelAndFrequencyInfo();
        setModulationInfo();
        setNetwrokInfo();
        setTransportStreamInfo();
        setServiceInfo();
        // initial progress value (default)
        setProgressBarValue(PROGRESS_MIN, PROGRESS_MIN);
    }

    private void setSoftwareVersionInfo() {
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mLinearSoftwareVersion.setVisibility(View.GONE);
        } else {
            int swVersion = TvOadManager.getInstance().getOadVersion(TvOadManager.OAD_VERSION_TYPE_TV);
            Log.d(TAG, "swVersion = "+swVersion);
            String str = "" + swVersion;
            mTextviewSoftwareVersionVal.setText(str);
        }
    }

    private void setAtannaType() {
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            if (mTvAtscChannelManager.getDtvAntennaType() == TvChannelManager.DTV_ANTENNA_TYPE_AIR) {
                mTextviewAntennatypeVal.setText(R.string.str_cha_antannatype_air);
            } else {
                mTextviewAntennatypeVal.setText(R.string.str_cha_antannatype_cable);
            }
        } else {
            mLinearAntennatype.setVisibility(View.GONE);
        }
    }

    private void setChannelAndFrequencyInfo() {
        String str;

        str = "" + mCurrSignalInfo.rfNumber;
        mTextviewChVal.setText(str);

        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mLinearFrequencyIfno.setVisibility(View.GONE);
        } else {
            RfInfo rfInfo = null;

            rfInfo = mTvChannelManager.getRfInfo(TvChannelManager.RF_INFO, mCurrSignalInfo.rfNumber);
            str = "" + (rfInfo.frequency / 1000);
            mTextviewFrequencyVal.setText(str);
        }
    }

    private void setModulationInfo() {
        String str = "";
        int mode = 0;
        try {
            mode = mCurrSignalInfo.getModulationMode().getValue();

        } catch (TvOutOfBoundException e) {
            e.printStackTrace();
        }

        if (mode < mModulationStringTable.length) {
            str = mModulationStringTable[mode];
        }
        mTextviewModulationVal.setText(str);
    }

    private void setNetwrokInfo() {
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mLinearNetwork.setVisibility(View.GONE);
        } else {
            mTextviewNetwrokVal.setText(mCurrSignalInfo.networkName);
        }
    }

    private void setTransportStreamInfo() {
        if ((mTvSystem == TvCommonManager.TV_SYSTEM_ATSC)
            || (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB)) {
            mLinearTransportStream.setVisibility(View.GONE);
        } else {
            String str;
            ProgramInfo cpi = mTvChannelManager.getCurrentProgramInfo();

            str = "" + cpi.transportStreamId;
            mTextviewTransportStreamVal.setText(str);
        }
    }

    private void setServiceInfo() {
        if ((mTvSystem == TvCommonManager.TV_SYSTEM_ATSC)
            || (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB)) {
            mLinearService.setVisibility(View.GONE);
        } else {
            String str;
            ProgramInfo cpi;

            cpi = mTvChannelManager.getCurrentProgramInfo();
            str = "" + cpi.serviceId;
            mTextviewServiceVal.setText(str);
        }
    }

    private void setProgressBarValue(int qualityVal, int strengthVal) {
        ImageView iv;
        Drawable picSolid;
        Drawable picEmpty;

        Log.d(TAG, "setProgressBarValue(), qualityVal = " + qualityVal + ", strengthVal = " + strengthVal);

        if (qualityVal < PROGRESS_MIN) {
            qualityVal = PROGRESS_MIN;
        }
        if (strengthVal < PROGRESS_MIN) {
            strengthVal = PROGRESS_MIN;
        }
        // nominalize to PROGRESS_MIN ~ PROGRESS_MAX
        qualityVal = qualityVal / (PROGRESS_MAX + 1);
        // nominalize to PROGRESS_MIN ~ PROGRESS_MAX
        strengthVal = strengthVal / (PROGRESS_MAX + 1);

        picSolid = getResources().getDrawable(
                    R.drawable.picture_serchprogressbar_solid);
        picEmpty = getResources().getDrawable(
                    R.drawable.picture_serchprogressbar_empty);
        for (int i = PROGRESS_MIN; i <= PROGRESS_MAX; i++) {

            // for signal quality
            iv = (ImageView) (mLinearSignalQualityVal.getChildAt(i));
            if (i < qualityVal) {
                iv.setImageDrawable(picSolid);
            } else {
                iv.setImageDrawable(picEmpty);
            }

            // for signal strength
            iv = (ImageView) (mLinearSignalStrengthVal.getChildAt(i));
            if (i < strengthVal) {
                iv.setImageDrawable(picSolid);
            } else {
                iv.setImageDrawable(picEmpty);
            }
        }
    }
}
