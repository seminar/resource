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

package com.mstar.tv.tvplayer.ui.tuning;

import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.view.KeyEvent;
import android.util.Log;

import com.mstar.android.tv.TvOadManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tv.tvplayer.ui.dtv.oad.OadDownload;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;
import com.mstar.android.tvapi.dtv.vo.EnumDtvScanStatus;
import com.mstar.android.tvapi.dtv.vo.RfInfo;

public class OadScan extends MstarBaseActivity {
    private static final String TAG = "OadScan";

    // private int mScanPercent = -1;

    private ViewHolder mViewHolder;

    private int mCountry = TvCountry.OTHERS;

    private TvOadManager mTvOadManager = null;

    private TvChannelManager mTvChannelManager = null;

    private OnDtvPlayerEventListener mDtvPlayerEventListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oadscan);
        mViewHolder = new ViewHolder(OadScan.this);
        mViewHolder.findViewForOadScan();
        mTvOadManager = TvOadManager.getInstance();
        mTvChannelManager = TvChannelManager.getInstance();
        mCountry = TvChannelManager.getInstance().getSystemCountryId();
        if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
            Log.d(TAG, "startAutoOadScan");
            mTvOadManager.startAutoOadScan();
        }
    }

    @Override
    protected void onResume() {
        mDtvPlayerEventListener = new DtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
        super.onResume();
    }

    @Override
    protected void onPause() {
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
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mTvChannelManager.stopDtvScan();
                OadScan.this.finish();
                mTvChannelManager.playDtvCurrentProgram();
                mTvOadManager.resetOad();
                break;
            default:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateOadScanInfo(DtvEventScan extra) {
        String str;
        int percent = extra.scanPercentageNum;
        // int currRFCh = extra.currRFCh;
        int scanStatus = extra.scanStatus;
        // mScanPercent = percent;
        Log.d(TAG, "updateOadScanInfo: percent = " + percent + ", currRFCh = " + extra.currRFCh
                + ", scanStatus = " + scanStatus);
        RfInfo rfInfo = null;
        rfInfo = mTvChannelManager.getRfInfo(TvChannelManager.RF_INFO, (int) extra.currRFCh);
        Log.d(TAG, "rfInfo.rfName = " + rfInfo.rfName);

        str = "" + percent + '%';
        mViewHolder.text_oadscan_progress_percent.setText(str);
        str = "" + rfInfo.rfName;
        mViewHolder.text_oadscan_num.setText(str);
        mViewHolder.bar_oadscan_progress.setProgress(percent);

        if (scanStatus == EnumDtvScanStatus.E_STATUS_SCAN_END.ordinal()) {
            oadScanFinish();
        } else if (scanStatus == EnumDtvScanStatus.E_STATUS_EXIT_TO_DL.ordinal()) {
            Log.d(TAG, "updateOadScanInfo: scanStatus == EnumDtvScanStatus.E_STATUS_EXIT_TO_DL");
            oadExitToDownload();
        }
    }

    private void oadScanFinish() {
        AlertDialog.Builder build = new AlertDialog.Builder(OadScan.this);
        build.setMessage(R.string.str_oad_msg_software_not_available);
        build.setPositiveButton(R.string.str_oad_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OadScan.this.finish();
            }
        });
        build.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                OadScan.this.finish();
            }
        });
        build.create().show();
        mTvChannelManager.playDtvCurrentProgram();
    }

    private void oadExitToDownload() {
        OadScan.this.finish();
    }

    private class DtvPlayerEventListener implements OnDtvPlayerEventListener {
        @Override
        public boolean onDtvChannelNameReady(int what) {
            return false;
        }

        @Override
        public boolean onDtvAutoTuningScanInfo(int what, final DtvEventScan extra) {
            OadScan.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
                        updateOadScanInfo(extra);
                    }
                }
            });
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
}
