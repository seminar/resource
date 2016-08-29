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

package com.mstar.tv.tvplayer.ui.tuning;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;


import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvMhlManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvChannelManager.DvbcScanParam;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.android.tvapi.atv.AtvManager;
import com.mstar.android.tvapi.atv.vo.AtvEventScan;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;
import com.mstar.android.tvapi.dtv.dvb.vo.DvbMuxInfo;
import com.mstar.android.tvapi.dtv.vo.EnumRfChannelBandwidth;
import com.mstar.android.tvapi.dtv.vo.EnumDtvScanStatus;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tvframework.MstarBaseActivity;

public class ChannelTuning extends MstarBaseActivity {
    /** Called when the activity is first created. */

    private static final String TAG = "ChannelTuning";

    private int mTvSystem = 0;

    private static int ATV_MIN_FREQ = 48250;    //del by wxy 
    //private static int ATV_MIN_FREQ = 100250;

    private static int ATV_MAX_FREQ = 877250;

    private static int ATV_EVENTINTERVAL = 500 * 1000;// every 500ms to show

    private static int dtvServiceCount = 0;

    private boolean isDtvAutoUpdateScan = false;

    private ViewHolder viewholder_channeltune;

    private TvCommonManager mTvCommonManager = null;

    private Time scanStartTime = new Time();

    private boolean isMhlOpen = false;

    private int scanPercent = -1;

    private int mCurrentRoute = TvChannelManager.TV_ROUTE_NONE;

    private TvChannelManager mTvChannelManager = null;

    private OnAtvPlayerEventListener mAtvPlayerEventListener = null;

    private OnDtvPlayerEventListener mDtvPlayerEventListener = null;

    private Handler mAtvUiEventHandler = null;

    private Handler mDtvUiEventHandler = null;

    private BroadcastReceiver mReceiver = null;
    
    private int Currentcountry;
    
    private TextView ch_num;
    

    private class AtvPlayerEventListener implements OnAtvPlayerEventListener {

        @Override
        public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
            Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
            AtvEventScan AtvEventScan_msg;
        	AtvEventScan_msg = (AtvEventScan) msg.obj;
			mAtvUiEventHandler.sendMessage(msg);
            return true;
        }

        @Override
        public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
            Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
            mAtvUiEventHandler.sendMessage(msg);
            return true;
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
        public boolean onAtvProgramInfoReady(int what) {
            return false;
        }
    }

    private class DtvPlayerEventListener implements OnDtvPlayerEventListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channeltuning);
       Intent intent = this.getIntent(); 
		Currentcountry = intent.getIntExtra("CoutrySelected_Index",0);
        
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE)) {
                    Log.i(TAG, "Receive ACTION_CIPLUS_TUNER_UNAVAIABLE...");
                    finish();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
        registerReceiver(mReceiver, filter);

        mAtvUiEventHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            	AtvEventScan AtvEventScan_msg;
            	AtvEventScan_msg = (AtvEventScan) msg.obj;
                updateAtvTuningScanInfo((AtvEventScan)msg.obj);
            }
        };

        mDtvUiEventHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateDtvTuningScanInfo((DtvEventScan)msg.obj);
            }
        };

        viewholder_channeltune = new ViewHolder(ChannelTuning.this);
        viewholder_channeltune.findViewForChannelTuning();
        mTvCommonManager = TvCommonManager.getInstance();
        mTvChannelManager = TvChannelManager.getInstance();
        ch_num = (TextView)findViewById(R.id.textview_cha_tuningprogress_ch);

        dtvServiceCount = 0;
        scanStartTime.setToNow();

        mTvSystem = mTvCommonManager.getCurrentTvSystem();

        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            viewholder_channeltune.linear_cha_radioprogram.setVisibility(View.INVISIBLE);
            viewholder_channeltune.linear_cha_dataprogram.setVisibility(View.INVISIBLE);
            viewholder_channeltune.text_cha_tuningprogress_rf
                .setText(getResources().getString(R.string.str_cha_tuningprogress_uhf));
        }
        else if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
            viewholder_channeltune.linear_cha_radioprogram.setVisibility(View.GONE);
            viewholder_channeltune.linear_cha_dataprogram.setVisibility(View.GONE);
            viewholder_channeltune.lineaR_cha_tvprogram.setVisibility(View.GONE);
            viewholder_channeltune.lineaR_cha_dtvprogram.setVisibility(View.GONE);
            viewholder_channeltune.linear_cha_airdtv.setVisibility(View.VISIBLE);
            viewholder_channeltune.linear_cha_airtv.setVisibility(View.VISIBLE);
            viewholder_channeltune.linear_cha_cabletv.setVisibility(View.VISIBLE);
        }

        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            isDtvAutoUpdateScan = getIntent().getBooleanExtra("DtvAutoUpdateScan", false);
        }

        if (isDtvAutoUpdateScan) {
            viewholder_channeltune.text_cha_tuningprogress_type.setText("DTV");
            Log.e(TAG, "switchMSrvDtvRouteCmd 1");
            int m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            TvChannelManager.DvbcScanParam sp = mTvChannelManager.new DvbcScanParam();
            int dvbcRouteIndex = mTvChannelManager.getSpecificDtvRouteIndex(TvChannelManager.TV_ROUTE_DVBC);
            if (dvbcRouteIndex < 0) {
                Log.e(TAG, "get route index error");
                return;
            }
            mTvChannelManager.switchMSrvDtvRouteCmd(dvbcRouteIndex);
            mTvChannelManager.dvbcgetScanParam(sp);

            if (m_nServiceNum > 0) {
                DvbMuxInfo dmi = mTvChannelManager.getCurrentMuxInfo();
                if (dmi != null) {
                    sp.u32NITFrequency = dmi.frequency;
                    sp.CAB_Type = dmi.modulationMode;
                    sp.u16SymbolRate = (short) dmi.symbRate;
                    Log.e(TAG, "dmi.u32NITFrequencye: " + sp.u32NITFrequency);
                    Log.e(TAG, "dmi.CAB_Type: " + sp.CAB_Type);
                    Log.e(TAG, "dmi.u16SymbolRate: " + sp.u16SymbolRate);
                    mTvChannelManager.setUserScanType(TvChannelManager.TV_SCAN_DTV);
                    mTvChannelManager.setDvbcScanParam(sp.u16SymbolRate, sp.CAB_Type,
                            sp.u32NITFrequency, 0, (short) 0x0000);
                    mTvChannelManager.startQuickScan();
                } else {
                    Log.e(TAG, "getCurrentMuxInfo error");
                    return;
                }
            } else {
                Log.e(TAG, "m_nServiceNum = 0");
                return;
            }
        } else if (mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_ALL
                || mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_DTV) {
            viewholder_channeltune.text_cha_tuningprogress_type.setText("DTV");
            TvTypeInfo tvinfo = mTvCommonManager.getTvInfo();
            int currentRouteIndex = mTvChannelManager.getCurrentDtvRouteIndex();
            mCurrentRoute = tvinfo.routePath[currentRouteIndex];
            int dtmbRouteIndex = mTvChannelManager.getSpecificDtvRouteIndex(TvChannelManager.TV_ROUTE_DTMB);
            if (TvChannelManager.TV_ROUTE_DVBC == mCurrentRoute) {
                TvChannelManager.DvbcScanParam sp = mTvChannelManager.new DvbcScanParam();
                mTvChannelManager.switchMSrvDtvRouteCmd(currentRouteIndex);
                mTvChannelManager.dvbcgetScanParam(sp);
                if ((getIntent() != null) && (getIntent().getIntArrayExtra("NitScanPara") != null)) {
                    // NIT SCAN
                    int[] data = getIntent().getIntArrayExtra("NitScanPara");
                    sp.u32NITFrequency = data[0] * 1000;
                    sp.CAB_Type = data[1];
                    sp.u16SymbolRate = (short) data[2];
                    mTvChannelManager.setDvbcScanParam(sp.u16SymbolRate, sp.CAB_Type,
                            sp.u32NITFrequency, 905000, (short) 0x0000);
                    mTvChannelManager.startDtvAutoScan();

                } else {
                    // FULL SCAN
                    mTvChannelManager.setDvbcScanParam(sp.u16SymbolRate, sp.CAB_Type,
                            sp.u32NITFrequency, 0, (short) 0x0000);
                    mTvChannelManager.startDtvFullScan();
                }
            } else if ((TvChannelManager.TV_ROUTE_DVBT == mCurrentRoute)
                    || TvChannelManager.TV_ROUTE_DVBT2 == mCurrentRoute) {
                Intent i = getIntent();
                boolean tmp = i.getBooleanExtra("isAustria", false);
                if (tmp) {
                    mTvChannelManager.setBandwidth(TvChannelManager.RF_CHANNEL_BANDWIDTH_7_MHZ);
                } else {
                    mTvChannelManager.setBandwidth(TvChannelManager.RF_CHANNEL_BANDWIDTH_8_MHZ);
                }
                mTvChannelManager.switchMSrvDtvRouteCmd(currentRouteIndex);
                mTvChannelManager.startDtvAutoScan();
            } else if ((TvChannelManager.TV_ROUTE_DVBS == mCurrentRoute)
                    || TvChannelManager.TV_ROUTE_DVBS2 == mCurrentRoute) {
                viewholder_channeltune.text_cha_tuningprogress_rf
                        .setText(getResources().getString(R.string.str_cha_dtvautotuning_frequency));
                viewholder_channeltune.text_cha_tuningprogress_ch.setVisibility(View.GONE);
                mTvChannelManager.switchMSrvDtvRouteCmd(currentRouteIndex);
                //mTvChannelManager.startDtvFullScan();
                mTvChannelManager.startDtvAutoScan();
            } else {
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    if (mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_ALL) {
                        TvAtscChannelManager.getInstance().deleteAllMainList();
                    } else {
                        TvAtscChannelManager.getInstance().deleteDtvMainList();
                    }
                } else if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
                    TvIsdbChannelManager.getInstance().setAntennaType(TvIsdbChannelManager.DTV_ANTENNA_TYPE_AIR);
                } else {
                    mTvChannelManager.switchMSrvDtvRouteCmd(dtmbRouteIndex);
                }
                mTvChannelManager.startDtvAutoScan();
            }
        } else {
            viewholder_channeltune.text_cha_tuningprogress_type.setText("ATV");
            //String str = "0%49.25";
            String str = "0%"+ATV_MIN_FREQ;
            if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
                TvIsdbChannelManager.getInstance().setAntennaType(TvIsdbChannelManager.DTV_ANTENNA_TYPE_AIR);
                viewholder_channeltune.text_cha_tuningprogress_type.setText("AIR");
            }
            else if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                TvAtscChannelManager.getInstance().deleteAtvMainList();
            }
            viewholder_channeltune.text_cha_tuningprogress_val.setText(str);
            mTvChannelManager.startAtvAutoTuning(ATV_EVENTINTERVAL, ATV_MIN_FREQ, ATV_MAX_FREQ);
        }
    }

    @Override
    protected void onResume() {
        isMhlOpen = TvMhlManager.getInstance().getAutoSwitch();
        if (isMhlOpen)
            TvMhlManager.getInstance().setAutoSwitch(false);
        viewholder_channeltune.linear_cha_mainlinear.setVisibility(View.VISIBLE);

        mAtvPlayerEventListener = new AtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnAtvPlayerEventListener(mAtvPlayerEventListener);

        mDtvPlayerEventListener = new DtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (isMhlOpen) {
            TvMhlManager.getInstance().setAutoSwitch(true);
        }
        if (mTvCommonManager.getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_STORAGE) {
            return;
        }

        if (scanPercent <= 100) {
            channetuningActivityLeave();
            pauseChannelTuning();
        }

        TvChannelManager.getInstance().unregisterOnAtvPlayerEventListener(mAtvPlayerEventListener);
        mAtvPlayerEventListener = null;

        TvChannelManager.getInstance().unregisterOnDtvPlayerEventListener(mDtvPlayerEventListener);
        mDtvPlayerEventListener = null;

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU: {
                Time curTime = new Time();
                curTime.setToNow();
                if (curTime.after(scanStartTime)) {
                    if ((curTime.toMillis(true) - scanStartTime.toMillis(true)) < 2000) {
                        Toast toast = Toast.makeText(ChannelTuning.this,
                                "Wait for a moment please!", 1);
                        toast.show();
                        return false;
                    }
                }
                channetuningActivityLeave();
                viewholder_channeltune.linear_cha_mainlinear.setVisibility(View.GONE);
                ExitTuningInfoDialog exitTuning = new ExitTuningInfoDialog(this, R.style.Dialog);
                exitTuning.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (mTvChannelManager.getTuningStatus() == TvChannelManager.TUNING_STATUS_NONE) {
                            finish();// if leave tuning this page should hide
                        } else {
                            viewholder_channeltune.linear_cha_mainlinear
                                    .setVisibility(View.VISIBLE);
                            if (mTvChannelManager.getTuningStatus() == TvChannelManager.TUNING_STATUS_ATV_AUTO_TUNING) {
                                    viewholder_channeltune.text_cha_tuningprogress_type.setText("ATV");
                                }
                        }
                    }
                });
                exitTuning.show();
            }
                break;
            case KeyEvent.KEYCODE_TV_INPUT:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void channetuningActivityLeave() {
        switch (mTvChannelManager.getTuningStatus()) {
            case TvChannelManager.TUNING_STATUS_ATV_AUTO_TUNING:
                mTvChannelManager.pauseAtvAutoTuning();
                break;
            case TvChannelManager.TUNING_STATUS_DTV_AUTO_TUNING:
            case TvChannelManager.TUNING_STATUS_DTV_FULL_TUNING:
                mTvChannelManager.pauseDtvScan();
                break;
            default:
                break;
        }
    }

    private void channetuningActivityExit() {
    	AutoTuneOptionActivity.isautotuning = false;
        Log.e(TAG, "channetuningActivityExit");
        Intent intent = new Intent(TvIntent.MAINMENU);
        intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
        startActivity(intent);
    }

    private void pauseChannelTuning() {
        switch (mTvChannelManager.getTuningStatus()) {
            case TvChannelManager.TUNING_STATUS_ATV_SCAN_PAUSING:
                mTvChannelManager.stopAtvAutoTuning();
                mTvChannelManager.changeToFirstService(
                        TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                        TvChannelManager.FIRST_SERVICE_DEFAULT);
                break;
            case TvChannelManager.TUNING_STATUS_DTV_SCAN_PAUSING:
                mTvChannelManager.stopDtvScan();
                if (mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_ALL) {
                    boolean res = mTvChannelManager.stopAtvAutoTuning();
                    if (res == false) {
                        Log.e(TAG, "atvSetAutoTuningStart Error!!!");
                    }
                } else {
                    mTvChannelManager.changeToFirstService(
                            TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV,
                            TvChannelManager.FIRST_SERVICE_DEFAULT);
                }
                break;
            default:
                break;
        }
    }

    private void updateAtvTuningScanInfo(AtvEventScan extra) {
    	
    	//add by wxy 20141011
    	switch(Currentcountry)
    	{
    	case TvCountry.CHINA:
    		ATV_MIN_FREQ=49750;
    		break;
    	case TvCountry.TAIWAN:
    		ATV_MIN_FREQ=55250;
    		break;
    	default:
    		ATV_MIN_FREQ=48250;
    		break;
    	}
    	String str = new String();
    	
        int percent = extra.percent;
        int frequencyKHz = extra.frequencyKHz;
        int scannedChannelNum = extra.scannedChannelNum;
        int curScannedChannel = extra.curScannedChannel;
        boolean bIsScaningEnable = extra.bIsScaningEnable;

        scanPercent = percent;
        
        ch_num.setVisibility(View.INVISIBLE);
        str = "" + scannedChannelNum;
        viewholder_channeltune.text_cha_tvprogram_val.setText(str);
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            if(TvIsdbChannelManager.getInstance().getAntennaType() == TvIsdbChannelManager.DTV_ANTENNA_TYPE_CABLE) {
                viewholder_channeltune.text_cha_cabletv_scanned_channels_val.setText(str);
            } else if (TvIsdbChannelManager.getInstance().getAntennaType() == TvIsdbChannelManager.DTV_ANTENNA_TYPE_AIR) {
                viewholder_channeltune.text_cha_airtv_scanned_channels_val.setText(str);
            } else {
                viewholder_channeltune.text_cha_airtv_scanned_channels_val.setText(str);
            }
        }

        str = "" + curScannedChannel;
        viewholder_channeltune.text_cha_tuningprogress_num.setText(str);

        String sFreq = " " + (frequencyKHz / 1000) + "." + (frequencyKHz % 1000) / 10
                    + "Mhz";
        str = "" + percent + '%' + sFreq;
        
        //Log.i("wxy","-----------sFreq="+sFreq+"----"+"str="+str+"----");
        viewholder_channeltune.text_cha_tuningprogress_val.setText(str);
        viewholder_channeltune.progressbar_cha_tuneprogress.setProgress(percent);

        if ((percent == 100 && bIsScaningEnable == false) || (percent > 100) || (frequencyKHz > ATV_MAX_FREQ)) {
            mTvChannelManager.stopAtvAutoTuning();

            if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB && TvIsdbChannelManager.getInstance().getAntennaType() == TvIsdbChannelManager.DTV_ANTENNA_TYPE_AIR) {
                TvIsdbChannelManager.getInstance().genMixProgList(false);
                TvIsdbChannelManager.getInstance().setAntennaType(TvIsdbChannelManager.DTV_ANTENNA_TYPE_CABLE);
                viewholder_channeltune.text_cha_tuningprogress_type.setText("CABLE");
                mTvChannelManager.startAtvAutoTuning(ATV_EVENTINTERVAL, ATV_MIN_FREQ, ATV_MAX_FREQ);
            } else {
                if (mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_ALL) {
                    if (dtvServiceCount > 0) {
                        if (mTvCommonManager.getCurrentTvInputSource()
                                != TvCommonManager.INPUT_SOURCE_DTV) {
                            mTvCommonManager.setInputSource(TvCommonManager.INPUT_SOURCE_DTV);
                        }
                        mTvChannelManager.changeToFirstService(
                                TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV,
                                TvChannelManager.FIRST_SERVICE_DEFAULT);
                    } else {
                        if (mTvCommonManager.getCurrentTvInputSource()
                                != TvCommonManager.INPUT_SOURCE_ATV) {
                            mTvCommonManager.setInputSource(TvCommonManager.INPUT_SOURCE_ATV);
                        }
                        mTvChannelManager.changeToFirstService(
                                TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                                TvChannelManager.FIRST_SERVICE_DEFAULT);
                    }
                } else {
                    if (mTvCommonManager.getCurrentTvInputSource()
                            != TvCommonManager.INPUT_SOURCE_ATV) {
                        mTvCommonManager.setInputSource(TvCommonManager.INPUT_SOURCE_ATV);
                    }
                    mTvChannelManager.changeToFirstService(
                            TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                            TvChannelManager.FIRST_SERVICE_DEFAULT);
                }
                channetuningActivityExit();
            }
        }
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            viewholder_channeltune.text_cha_tuningprogress_rf.setVisibility(View.INVISIBLE);
        }
    }

    private void updateDtvTuningScanInfo(DtvEventScan extra) {
        String str;
        int dtv = extra.dtvSrvCount;
        int radio = extra.radioSrvCount;
        int data = extra.dataSrvCount;
        int percent = extra.scanPercentageNum;
        int currRFCh = extra.currRFCh;
        int scan_status = extra.scanStatus;
        int currFrequency = extra.currFrequency;
        scanPercent = percent;

        str = "" + (dtv + radio + data);
        viewholder_channeltune.text_cha_airdtv_scanned_channels_val.setText(str);

        str = "" + dtv;
        viewholder_channeltune.text_cha_dtvprogram_val.setText(str);
        str = "" + radio;
        viewholder_channeltune.text_cha_radioprogram_val.setText(str);
        str = "" + data;
        viewholder_channeltune.text_cha_dataprogram_val.setText(str);
        str = "" + percent + '%';
        viewholder_channeltune.text_cha_tuningprogress_val.setText(str);
        ch_num.setText("CH"+currRFCh);
        
        

        if ((TvChannelManager.TV_ROUTE_DVBS == mCurrentRoute)
                || TvChannelManager.TV_ROUTE_DVBS2 == mCurrentRoute) {
            str = "" + currFrequency;
        } else {
            str = "" + currRFCh;
        }
        viewholder_channeltune.text_cha_tuningprogress_num.setText(str);
        viewholder_channeltune.progressbar_cha_tuneprogress.setProgress(percent);

        if (scan_status == EnumDtvScanStatus.E_STATUS_SCAN_END.ordinal()) {
            if (mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_ALL) {
                dtvServiceCount = dtv + radio + data;
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    TvAtscChannelManager.getInstance().deleteAtvMainList();
                }
                viewholder_channeltune.text_cha_tuningprogress_type.setText("ATV");

                if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                    TvIsdbChannelManager.getInstance().genMixProgList(false);
                    TvIsdbChannelManager.getInstance().setAntennaType(TvIsdbChannelManager.DTV_ANTENNA_TYPE_AIR);
                    viewholder_channeltune.text_cha_tuningprogress_type.setText("AIR");
                }

                mTvChannelManager.startAtvAutoTuning(ATV_EVENTINTERVAL, ATV_MIN_FREQ,
                        ATV_MAX_FREQ);
            } else if (mTvChannelManager.getUserScanType() == TvChannelManager.TV_SCAN_DTV) {
                if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
                    TvIsdbChannelManager.getInstance().genMixProgList(false);
                }
                mTvChannelManager.changeToFirstService(
                        TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV,
                        TvChannelManager.FIRST_SERVICE_DEFAULT);
                if (isDtvAutoUpdateScan) {
                    finish();
                } else {
                    channetuningActivityExit();
                }
            }
        }
    }
}
