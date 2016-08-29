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

package com.mstar.tv.tvplayer.ui.channel;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import org.xml.sax.InputSource;

import android.R.drawable;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.format.Time;

import com.mstar.android.MKeyEvent;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvEpgManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.PresentFollowingEventInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfoQueryCriteria;
import com.mstar.android.tvapi.common.vo.VideoInfo;
import com.mstar.android.tvapi.common.vo.VideoInfo.EnumScanType;
import com.mstar.android.tvapi.common.vo.HbbtvEventInfo;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.common.vo.CecSetting;
import com.mstar.android.tvapi.dtv.vo.DtvAudioInfo;
import com.mstar.android.tvapi.dtv.vo.DtvEitInfo;
import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;
import com.mstar.android.tvapi.atv.vo.AtvEventScan;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo;
import com.mstar.tv.tvplayer.ui.NoSignalActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.R.layout;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.pvr.PVRActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.TvEvent;

public class SourceInfoActivity extends MstarBaseActivity {

    private static final String TAG = "SourceInfoActivity";

    private static final int CMD_SET_CURRENT_TIME = 0x00;

    private static final int CMD_UPDATE_SOURCE_INFO = 0X01;

    private static final int CMD_SIGNAL_LOCK = 0x02;

    private int mTvSystem = 0;

    private final int MAX_TIMES = 10;

    private final int mCecStatusOn = 1;

    private int mCount = 0;

    private static final int MAX_VALUE_OF_ONE_DIGIT = 9;

    private static final int MAX_VALUE_OF_TWO_DIGIT = 99;

    private static final int MAX_VALUE_OF_THREE_DIGIT = 999;

    private TvChannelManager mTvChannelManager = null;

    private int mInputSource = TvCommonManager.INPUT_SOURCE_NONE;

    private int mPreviousInputSource = TvCommonManager.INPUT_SOURCE_NONE;

    private VideoInfo mVideoInfo = null;

    private String mStr_video_info = null;

    private int mCurChannelNumber = 1;

    private ImageView mTvImageView = null;

    private TextView mTitle = null;

    private TextView mInfo = null;

    private TvCommonManager mTvCommonManager = null;
    
    private ImageView mFirstTvNumberIcon = null;

    private ImageView mSecondTvNumberIcon = null;

    private ImageView mThirdTvNumberIcon = null;

    private ImageView mFourthTvNumberIcon = null;

    private ImageView mTvDotIcon = null;

    private ImageView mDigitMinorTvNumberIcon1 = null;

    private ImageView mDigitMinorTvNumberIcon2 = null;

    private ImageView mDigitMinorTvNumberIcon3 = null;

    private ImageView mDigitMinorTvNumberIcon4 = null;

    private TextView mSource_info_tvnumber;

    private TextView mSource_info_tvname;

    private TextView mSource_info_teletext;

    private TextView mSource_info_program_name;

    private TextView mSource_info_Subtitle;

    private TextView mSource_info_mheg5;

    private TextView mSource_info_video_format;

    private TextView mSource_info_audio_format;

    private TextView mSource_info_program_type;

    private TextView mSource_info_program_period;

    private TextView mSource_info_description;

    private TextView mSource_info_digital_TV;

    private TextView mSource_info_language;

    private TextView mSource_info_imageformat;

    private TextView mSource_info_soundformat;

    private TextView mSource_info_audioformat;

    private TextView mSource_info_age;

    private TextView mSource_info_genre;

    private TextView mSource_info_cc;

    private TextView mSource_epg_event_name;

    private TextView mSource_epg_event_rating;

    private String mStr;

    private TextView mCurrentTime = null;

    private TvEpgManager mTvEpgManager = null;

    private TvAtscChannelManager mTvAtscChannelManager = null;

    private TvCecManager mTvCecManager = null;

    private TvIsdbChannelManager mTvIsdbChannelManager = null;

    protected TimerTask mTimerTask;

    // protected ST_Time curDateTime;
    public static boolean isDTVChannelNameReady = true;
    
    // private static boolean isATVProgramInfoReady = true;
    protected Timer mTimer;

    private Intent mIntent = null;

    private DtvEitInfo mDtveitinfo;
    
    private int colour_system; //add by wxy

    private int[] mNumberResIds = {
            R.drawable.popup_img_number_0, R.drawable.popup_img_number_1,
            R.drawable.popup_img_number_2, R.drawable.popup_img_number_3,
            R.drawable.popup_img_number_4, R.drawable.popup_img_number_5,
            R.drawable.popup_img_number_6, R.drawable.popup_img_number_7,
            R.drawable.popup_img_number_8, R.drawable.popup_img_number_9
    };

    private String[] mAudioTypeDisplayString = {
            "MPEG", "Dolby D", "AAC", "AC3P"
    };

    private String[] mServiceTypeDisplayString = {
            "", "DTV", "RADIO", "DATA", "UNITED_TV", "INVALID"
    };

    private String[] mVideoTypeDisplayString = {
            "", "MPEG", "H.264", "AVS", "VC1"
    };

    private OnAtvPlayerEventListener mAtvPlayerEventListener = null;

    private OnDtvPlayerEventListener mDtvPlayerEventListener = null;

    private OnTvPlayerEventListener mTvPlayerEventListener = null;

    private class TvPlayerEventListener implements OnTvPlayerEventListener {
        @Override
        public boolean onScreenSaverMode(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onHbbtvUiEvent(int what, HbbtvEventInfo eventInfo) {
            return false;
        }

        @Override
        public boolean onPopupDialog(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onPvrNotifyPlaybackTime(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyPlaybackSpeedChange(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyRecordTime(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyRecordSize(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyRecordStop(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyPlaybackStop(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyPlaybackBegin(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyTimeShiftOverwritesBefore(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyTimeShiftOverwritesAfter(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyOverRun(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyUsbRemoved(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyCiPlusProtection(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyParentalControl(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onPvrNotifyAlwaysTimeShiftProgramReady(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int what) {
            return false;
        }

        @Override
        public boolean onPvrNotifyCiPlusRetentionLimitUpdate(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onTvProgramInfoReady(int what) {
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
        public boolean onEpgUpdateList(int what, int arg1) {
            Log.i(TAG, "onEpgUpdateList()");
            SourceInfoActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                        if (TvCommonManager.getInstance()
                            .getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
                            updateSourceInFo();
                        }
                    }
            });
            return true;
        }

        @Override
        public boolean on4k2kHDMIDisablePip(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean on4k2kHDMIDisablePop(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean on4k2kHDMIDisableDualView(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean on4k2kHDMIDisableTravelingMode(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onDtvPsipTsUpdate(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onEmerencyAlert(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onDtvChannelInfoUpdate(int what, int info, int arg2) {
            return false;
        }
    }

    private class AtvPlayerEventListener implements OnAtvPlayerEventListener {

        @Override
        public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
            return false;
        }

        @Override
        public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
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
        public boolean onAtvProgramInfoReady(int what) {
            Bundle b = new Bundle();
            Message msg = myHandler.obtainMessage();
            msg.what = CMD_UPDATE_SOURCE_INFO;
            b.putInt("CmdIndex", TvEvent.ATV_PROGRAM_INFO_READY);
            myHandler.sendMessage(msg);
            return false;
        }
    }

    private class DtvPlayerEventListener implements OnDtvPlayerEventListener {

        @Override
        public boolean onDtvChannelNameReady(int what) {
            SourceInfoActivity.isDTVChannelNameReady=true;

            Bundle b = new Bundle();
            Message msg = myHandler.obtainMessage();
            msg.what = CMD_UPDATE_SOURCE_INFO;
            b.putInt("CmdIndex", TvEvent.DTV_CHANNELNAME_READY);
            myHandler.sendMessage(msg);
            return true;
        }

        @Override
        public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
            return false;
        }

        @Override
        public boolean onDtvProgramInfoReady(int what) {
            Bundle b = new Bundle();
            Message msg = myHandler.obtainMessage();
            msg.what = CMD_UPDATE_SOURCE_INFO;
            b.putInt("CmdIndex", (int) (TvEvent.DTV_PROGRAM_INFO_READY));
            msg.setData(b);
            myHandler.sendMessage(msg);
            return true;
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

    protected Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case CMD_SET_CURRENT_TIME:
                    String time = new SimpleDateFormat("HH:mm yyyy/MM/dd E").format(new Date());
                    mCurrentTime.setText(getString(R.string.str_time_time) + " : " + time);
                    break;

                case CMD_UPDATE_SOURCE_INFO:
                    int CmdIndex = bundle.getInt("CmdIndex");
                    onMSrvMsgCmd(CmdIndex);
                    break;

                case CMD_SIGNAL_LOCK:
                    updateSourceInFo();
                    break;

                default:
                    break;
            }
        }
    };

    private void onMSrvMsgCmd(int index) {
        if (index == TvEvent.DTV_CHANNELNAME_READY) {
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                isDTVChannelNameReady = true;
            }
            updateChannelInfo(true);
        } else if (index == TvEvent.DTV_PROGRAM_INFO_READY) {
            updateSourceInFo();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvEpgManager = TvEpgManager.getInstance();
        mTvCecManager = TvCecManager.getInstance();
        mTvCommonManager = TvCommonManager.getInstance();
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mTvAtscChannelManager = TvAtscChannelManager.getInstance();
            isDTVChannelNameReady = true;
        } else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            mTvIsdbChannelManager = TvIsdbChannelManager.getInstance();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(TvIntent.ACTION_SOURCE_INFO_DISAPPEAR);
        filter.addAction(TvIntent.ACTION_SIGNAL_LOCK);
        registerReceiver(mReceiver, filter);
        mTvChannelManager = TvChannelManager.getInstance();
        checkInputSourceAndInitView();
        setInvisible();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        // receive dtvlistener call back
        mCount = 0;

        mAtvPlayerEventListener = new AtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnAtvPlayerEventListener(mAtvPlayerEventListener);
        mDtvPlayerEventListener = new DtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
        mTvPlayerEventListener = new TvPlayerEventListener();
        TvChannelManager.getInstance().registerOnTvPlayerEventListener(mTvPlayerEventListener);

        if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV
                || mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            try {
                if (mTimer == null)
                    mTimer = new Timer();
                if (mTimerTask == null)
                    mTimerTask = getTimerTask();
                if (mTimer != null && mTimerTask != null)
                    mTimer.schedule(mTimerTask, 10, 1000);
            } catch (Exception e) {
            }
        }
        if (mInputSource == TvCommonManager.INPUT_SOURCE_HDMI
                || mInputSource == TvCommonManager.INPUT_SOURCE_HDMI2
                || mInputSource == TvCommonManager.INPUT_SOURCE_HDMI3
                || mInputSource == TvCommonManager.INPUT_SOURCE_HDMI4) {
            // this will call updateSourceInFo() delay.
            myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 1000);
        } else {
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                // this will call updateSourceInFo() delay.
                myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 1000);
            } else {
                // this will call updateSourceInFo() delay.
                myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 300);
            }
        }
        if (mInputSource == TvCommonManager.INPUT_SOURCE_YPBPR) {
            try {
                if (mTimer == null)
                    mTimer = new Timer();
                if (mTimerTask == null)
                    mTimerTask = getTimerTask();
                if (mTimer != null && mTimerTask != null)
                    mTimer.schedule(mTimerTask, 10, 1000);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");

        TvChannelManager.getInstance().unregisterOnAtvPlayerEventListener(mAtvPlayerEventListener);
        mAtvPlayerEventListener = null;
        TvChannelManager.getInstance().unregisterOnDtvPlayerEventListener(mDtvPlayerEventListener);
        mDtvPlayerEventListener = null;
        TvChannelManager.getInstance().unregisterOnTvPlayerEventListener(mTvPlayerEventListener);
        mTvPlayerEventListener = null;

        try {
            if (mTimerTask != null) {
                mTimerTask.cancel();
                mTimerTask = null;
            }
            mTimer.cancel();
            mTimer = null;
        } catch (Exception e) {
        }

        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void setInvisible() {
        if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV
            || mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            mFirstTvNumberIcon.setVisibility(View.INVISIBLE);
            mSecondTvNumberIcon.setVisibility(View.INVISIBLE);
            mThirdTvNumberIcon.setVisibility(View.INVISIBLE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.INVISIBLE);
                mTvDotIcon.setVisibility(View.INVISIBLE);
                mDigitMinorTvNumberIcon1.setVisibility(View.INVISIBLE);
                mDigitMinorTvNumberIcon2.setVisibility(View.INVISIBLE);
                mDigitMinorTvNumberIcon3.setVisibility(View.INVISIBLE);
                mDigitMinorTvNumberIcon4.setVisibility(View.INVISIBLE);
            }
            mCurrentTime.setVisibility(View.INVISIBLE);
            mSource_info_tvnumber.setVisibility(View.INVISIBLE);
            mSource_info_tvname.setVisibility(View.INVISIBLE);
        }
    }

    private void checkInputSourceAndInitView() {
        mPreviousInputSource = mInputSource;
        mInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (mInputSource == mPreviousInputSource) {
            return;
        }

        if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
            setContentView(R.layout.source_info_atv);
            mTvImageView = (ImageView) findViewById(R.id.source_info_imageView);
            mTitle = (TextView) findViewById(R.id.source_info_title);
            mFirstTvNumberIcon = (ImageView) findViewById(R.id.source_info_image1);
            mSecondTvNumberIcon = (ImageView) findViewById(R.id.source_info_image2);
            mThirdTvNumberIcon = (ImageView) findViewById(R.id.source_info_image3);
            mCurrentTime = (TextView) findViewById(R.id.source_info_tvtime);
            mSource_info_tvnumber = (TextView) findViewById(R.id.source_info_tvnumber);
            mSource_info_tvname = (TextView) findViewById(R.id.source_info_tvname);
            mSource_info_imageformat = (TextView) findViewById(R.id.source_info_imageformat);
            mSource_info_soundformat = (TextView) findViewById(R.id.source_info_soundformat);
            mSource_info_audioformat = (TextView) findViewById(R.id.source_info_audioformat);
            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                mSource_epg_event_rating = (TextView) findViewById(R.id.source_info_epg_rating_atv);
                mSource_epg_event_rating.setVisibility(View.VISIBLE);
            }
            mTitle.setText("ATV");
        } else if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            setContentView(R.layout.source_info_dtv);
            ((TextView) findViewById(R.id.source_info_description_title))
                    .setText(getString(R.string.str_dtv_source_info_description) + " : ");
            
            
            mTvImageView = (ImageView) findViewById(R.id.source_info_imageView);
            mTitle = (TextView) findViewById(R.id.source_info_title);
            mFirstTvNumberIcon = (ImageView) findViewById(R.id.source_info_image1);
            mSecondTvNumberIcon = (ImageView) findViewById(R.id.source_info_image2);
            mThirdTvNumberIcon = (ImageView) findViewById(R.id.source_info_image3);
            mFourthTvNumberIcon = (ImageView) findViewById(R.id.source_info_image4);
            mTvDotIcon = (ImageView) findViewById(R.id.source_info_image_dot);
            mDigitMinorTvNumberIcon1 = (ImageView) findViewById(R.id.source_info_image_minor_1);
            mDigitMinorTvNumberIcon2 = (ImageView) findViewById(R.id.source_info_image_minor_2);
            mDigitMinorTvNumberIcon3 = (ImageView) findViewById(R.id.source_info_image_minor_3);
            mDigitMinorTvNumberIcon4 = (ImageView) findViewById(R.id.source_info_image_minor_4);
            mCurrentTime = (TextView) findViewById(R.id.source_info_tvtime);
            mSource_info_tvnumber = (TextView) findViewById(R.id.source_info_tvnumber);
            mSource_info_tvname = (TextView) findViewById(R.id.source_info_tvname);
            mSource_info_teletext = (TextView) findViewById(R.id.source_info_teletext);
            mSource_info_program_name = (TextView) findViewById(R.id.source_info_program_name);
            mSource_info_Subtitle = (TextView) findViewById(R.id.source_info_Subtitle);
            mSource_info_mheg5 = (TextView) findViewById(R.id.source_info_mheg5);
            mSource_info_video_format = (TextView) findViewById(R.id.source_info_video_format);
            mSource_info_audio_format = (TextView) findViewById(R.id.source_info_audio_format);
            mSource_info_program_type = (TextView) findViewById(R.id.source_info_program_type);
            mSource_info_program_period = (TextView) findViewById(R.id.source_info_program_period);
            mSource_info_description = (TextView) findViewById(R.id.source_info_description);
            mSource_info_digital_TV = (TextView) findViewById(R.id.source_info_digital_TV);
            mSource_info_language = (TextView) findViewById(R.id.source_info_language);
            mSource_info_age = (TextView) findViewById(R.id.source_info_age);
            mSource_info_genre = (TextView) findViewById(R.id.source_info_genre);
            mSource_info_cc = (TextView) findViewById(R.id.source_info_cc);
            mSource_epg_event_name = (TextView) findViewById(R.id.source_info_epg_name);
            mSource_epg_event_rating = (TextView) findViewById(R.id.source_info_epg_rating_dtv);
            mSource_info_genre.setVisibility(View.GONE);
            if (TvCommonManager.TV_SYSTEM_ATSC != mTvSystem) {
                mSource_epg_event_name.setVisibility(View.GONE);
                mSource_epg_event_rating.setVisibility(View.GONE);
            } else {
                mSource_epg_event_rating.setVisibility(View.VISIBLE);
            }
            mTitle.setText("DTV");
        } else {
            setContentView(R.layout.source_info);
            mInfo = (TextView) findViewById(R.id.source_info_textview);
            mTvImageView = (ImageView) findViewById(R.id.source_info_imageView);
            mTitle = (TextView) findViewById(R.id.source_info_title);
        }
    }

    private void clearTvPartialSourceInfo() {
        if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
            mSource_info_imageformat.setText("");
            mSource_info_soundformat.setText("");
            mSource_info_audioformat.setText("");
            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                mSource_epg_event_rating.setText("");
            }
        } else if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            mSource_info_teletext.setText("");
            mSource_info_program_name.setText("");
            mSource_info_Subtitle.setText("");
            mSource_info_mheg5.setText("");
            mSource_info_video_format.setText("");
            mSource_info_audio_format.setText("");
            mSource_info_program_type.setText("");
            mSource_info_program_period.setText("");
            mSource_info_description.setText("");
            mSource_info_digital_TV.setText("");
            mSource_info_language.setText("");
            mSource_info_age.setText("");
            mSource_info_genre.setText("");
            mSource_info_cc.setText("");
            mSource_epg_event_name.setText("");
            mSource_epg_event_rating.setText("");
            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                mSource_epg_event_rating.setText("");
            }
        }
    }

    private String getCurProgrameName() {
        int pgNum = mTvChannelManager.getCurrentChannelNumber();
        int st = TvChannelManager.SERVICE_TYPE_ATV;
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                st = TvChannelManager.SERVICE_TYPE_DTV;
            }
        } else {
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                st = TvChannelManager.SERVICE_TYPE_DTV;
            }
        }
        String pgName = mTvChannelManager.getProgramName(pgNum, st, 0x00);
        return pgName;
    }

    private String getCurProgrameName(short num) {
        int pgNum = mTvChannelManager.getCurrentChannelNumber();
        int st = -1;
        switch ((int) num) {
            case 0:
                st = TvChannelManager.SERVICE_TYPE_ATV;
                break;
            case 3:
                st = TvChannelManager.SERVICE_TYPE_DATA;
                break;
            case 1:
                st = TvChannelManager.SERVICE_TYPE_DTV;
                break;
            case 5:
                st = TvChannelManager.SERVICE_TYPE_INVALID;
                break;
            case 2:
                st = TvChannelManager.SERVICE_TYPE_RADIO;
                break;
            case 4:
                st = TvChannelManager.SERVICE_TYPE_UNITED_TV;
                break;
        }
        String pgName = mTvChannelManager.getProgramName(pgNum, st, 0x00);
        return pgName;
    }

    private void updateChannelInfo(boolean bPresent) {
        int videostandard = TvChannelManager.AVD_VIDEO_STANDARD_PAL_BGHI;
        checkInputSourceAndInitView();
        clearTvPartialSourceInfo();
        mCurChannelNumber = mTvChannelManager.getCurrentChannelNumber();

        if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            ProgramInfo CurrProg_Info = new ProgramInfo();
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                CurrProg_Info = mTvAtscChannelManager.getCurrentProgramInfo();
                majorMinorToIcon(CurrProg_Info.majorNum, CurrProg_Info.minorNum);
                String channum = mTvAtscChannelManager.getDispChannelNum(CurrProg_Info); 
                int index;
                index = Integer.valueOf(channum).intValue();
                String name = mTvAtscChannelManager.getDispChannelName(CurrProg_Info);
                mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                        + " : " + name);
                //mSource_info_tvname.setText(mData.get(mCurChannelNumber).getTvNumber());
                if (CurrProg_Info.favorite==1) //add by wxy
                {
          
                	mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                            + " : " + channum+"    ");
                	//add by wxy 
                    Resources res = getResources();
            		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_favorite_focus);
                    favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                    mSource_info_tvnumber.setCompoundDrawables(null, null, favo_img, null);
                    //add end
                } 
                if (CurrProg_Info.isSkip) //add by wxy
                {
          
                	mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                            + " : " + channum+"    ");
                	//add by wxy 
                    Resources res = getResources();
            		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_skip_focus);
                    favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                    mSource_info_tvnumber.setCompoundDrawables(null, null, favo_img, null);
                    //add end
                } 
                if((CurrProg_Info.isSkip==false)&&(CurrProg_Info.favorite!=1)){
               
                mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                        + " : " + channum+"    ");
                mSource_info_tvnumber.setCompoundDrawables(null, null, null, null);
                }
                
                
                AtscEpgEventInfo pfEvtInfo = null;

                mCurChannelNumber = CurrProg_Info.number;
                mSource_info_Subtitle.setVisibility(View.GONE);
                mSource_info_teletext.setVisibility(View.GONE);
                mSource_info_mheg5.setVisibility(View.GONE);
                mSource_info_video_format.setVisibility(View.GONE);

                updateAudioInfo(false);

                updateServiceType(CurrProg_Info);

                mSource_info_digital_TV.setText(getString(R.string.str_dtv_source_info_resolution)
                        + " : " + mStr_video_info);

                Time currTime;
                currTime = new Time();
                currTime.setToNow();
                currTime.set(currTime.toMillis(true));
                pfEvtInfo = mTvEpgManager.getAtscEventInfoByTime(CurrProg_Info.majorNum,
                    CurrProg_Info.minorNum,  CurrProg_Info.number, CurrProg_Info.progId, currTime);
                AtscEpgEventInfo epgEvInfoExted = mTvEpgManager.getEventExtendInfoByTime(
                    CurrProg_Info.majorNum, CurrProg_Info.minorNum, (int)CurrProg_Info.serviceType,
                    CurrProg_Info.progId, currTime);

                if (pfEvtInfo != null) {
                    mSource_epg_event_name.setText(getString(R.string.str_epg_event_name)
                        + " : " + pfEvtInfo.sName);
                }
                mSource_epg_event_rating.setText(getString(R.string.str_epg_event_rating)
                    + " : " + mTvAtscChannelManager.getCurrentRatingInformation());
                mSource_info_program_name.setVisibility(View.GONE);
                mSource_info_age.setVisibility(View.GONE);
                mSource_info_cc.setVisibility(View.GONE);
                if (epgEvInfoExted == null) {
                    mSource_info_description.setText("");
                } else {
                    mSource_info_description.setText(epgEvInfoExted.sExtendedText);
                }
                mSource_info_description.requestFocus();
            } else {
                ProgramInfoQueryCriteria cr = new ProgramInfoQueryCriteria();
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                    CurrProg_Info = mTvIsdbChannelManager.getCurrentProgramInfo();
                } else {
                    CurrProg_Info = TvDvbChannelManager.getInstance().getProgramInfo(cr,
                            TvDvbChannelManager.PROGRAM_INFO_TYPE_CURRENT);
                }
                PresentFollowingEventInfo pfEvtInfo = new PresentFollowingEventInfo();

                if (CurrProg_Info != null) {
                	
                    
                    
                    mCurChannelNumber = CurrProg_Info.number;
                    //zb20141013 add
                    try {
						if(mCurChannelNumber==0 && TvManager.getInstance().getPlayerManager().isSignalStable()==false)
						{
							mCurChannelNumber=1;
						}
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    	
                    //end
                    if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                    	
                    	
                        if (CurrProg_Info.favorite==1)   //add by wxy
                        {
                        	mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                    + " : " + CurrProg_Info.serviceName);
                            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                    + " : " + CurrProg_Info.majorNum + "." + CurrProg_Info.minorNum+"    ");
                          //add by wxy 
                            Resources res = getResources();
                    		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_favorite_focus);
                            favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                            mSource_info_tvnumber.setCompoundDrawables(null, null, favo_img, null);
                            //add end
                            majorMinorToIcon(CurrProg_Info.majorNum,CurrProg_Info.minorNum);
                        }
                        if (CurrProg_Info.isSkip)   //add by wxy
                        {
                        	mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                    + " : " + CurrProg_Info.serviceName);
                            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                    + " : " + CurrProg_Info.majorNum + "." + CurrProg_Info.minorNum+"    ");
                          //add by wxy 
                            Resources res = getResources();
                    		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_skip_focus);
                            favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                            mSource_info_tvnumber.setCompoundDrawables(null, null, favo_img, null);
                            //add end
                            majorMinorToIcon(CurrProg_Info.majorNum,CurrProg_Info.minorNum);
                        } 
                        if((CurrProg_Info.isSkip==false)&&(CurrProg_Info.favorite!=1))
                        {
                        	mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                    + " : " + CurrProg_Info.serviceName);
                            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                    + " : " + CurrProg_Info.majorNum + "." + CurrProg_Info.minorNum+"    ");
                            mSource_info_tvnumber.setCompoundDrawables(null, null, null, null);
                            majorMinorToIcon(CurrProg_Info.majorNum,CurrProg_Info.minorNum);
                        }
                    	/*
                        mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                + " : " + CurrProg_Info.serviceName);
                        mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                + " : " + CurrProg_Info.majorNum + "." + CurrProg_Info.minorNum);
                        majorMinorToIcon(CurrProg_Info.majorNum,CurrProg_Info.minorNum);*/
                    } else {
                    	if (CurrProg_Info.favorite ==1)      //add by wxy
                    	{
                    		
                    		mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                    + " : " + getCurProgrameName((CurrProg_Info.serviceType)));
                            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                    + " : " + mCurChannelNumber+"    ");
                            //add by wxy 
                            Resources res = getResources();
                    		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_favorite_focus);
                            favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                            mSource_info_tvnumber.setCompoundDrawables(null, null, favo_img, null);
                            //add end
                            numberToIcon(mCurChannelNumber);
                    	}
                    	if (CurrProg_Info.isSkip)      //add by wxy
                    	{
                    		
                    		mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                    + " : " + getCurProgrameName((CurrProg_Info.serviceType)));
                            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                    + " : " + mCurChannelNumber+"    ");
                            //add by wxy 
                            Resources res = getResources();
                    		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_skip_focus);
                            favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                            mSource_info_tvnumber.setCompoundDrawables(null, null, favo_img, null);
                            //add end
                            numberToIcon(mCurChannelNumber);
                    	}
                    	if((CurrProg_Info.isSkip==false)&&(CurrProg_Info.favorite!=1))
                    	{
                    		
                    		mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                    + " : " + getCurProgrameName((CurrProg_Info.serviceType)));
                            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                    + " : " + mCurChannelNumber+"    ");
                            mSource_info_tvnumber.setCompoundDrawables(null, null, null, null);
                            numberToIcon(mCurChannelNumber);
                    		
                    	}
                    	/*
                        mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                                + " : " + getCurProgrameName((CurrProg_Info.serviceType)));
                        mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                                + " : " + mCurChannelNumber);
                        numberToIcon(mCurChannelNumber);
                        */
                    }
                } else {
                    Log.v(TAG, "CurrProg_Info is NULL!!");
                    return;
                }


                try {
                    pfEvtInfo = mTvEpgManager.getEpgPresentFollowingEventInfo(CurrProg_Info.serviceType,
                            mCurChannelNumber/*zb20141013 modify CurrProg_Info.number*/, bPresent, TvEpgManager.EPG_DETAIL_DESCRIPTION);
                    if (pfEvtInfo == null) {
                        return;
                    }
                    mDtveitinfo = new DtvEitInfo();
                    mDtveitinfo = mTvEpgManager.getEitInfo(bPresent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mDtveitinfo != null) {
                    mSource_info_program_name.setText(mDtveitinfo.eitCurrentEventPf.eventName);

                    mSource_info_age.setText(getString(R.string.str_dtv_source_info_age) + ":"
                            + mDtveitinfo.eitCurrentEventPf.parentalControl);
                }
                DtvSubtitleInfo subtitleInfo = mTvChannelManager.getSubtitleInfo();
                mSource_info_Subtitle.setText(getString(R.string.str_dtv_source_info_Subtitle) + " : "
                        + subtitleInfo.subtitleServiceNumber);

                if (mTvChannelManager.isTtxChannel()) {
                    mSource_info_teletext.setText(getString(R.string.str_dtv_source_info_teletext));
                } else {
                    mStr = "";
                    mSource_info_teletext.setText(mStr);
                }
                if (pfEvtInfo.componentInfo.mheg5Service) {
                    if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                        mStr = getString(R.string.str_dtv_source_info_ginga);
                    } else {
                        mStr = getString(R.string.str_dtv_source_info_mheg5);
                    }
                } else {
                    mStr = "";
                }
                mSource_info_mheg5.setText(mStr);

                updateVideoType(pfEvtInfo.componentInfo.getVideoType().ordinal());

                updateAudioInfo(pfEvtInfo.componentInfo.isAd);

                updateServiceType(CurrProg_Info);

                mSource_info_program_period
                        .setText(getString(R.string.str_dtv_source_info_program_period) + " : "
                                + (pfEvtInfo.eventInfo.durationTime / 60) + "Min");

                updateIsCcExist();

                if (mDtveitinfo != null) {
                    // show short description
                    if (mDtveitinfo.eitCurrentEventPf.shortEventText.length() > 0) {
                        mStr = mDtveitinfo.eitCurrentEventPf.shortEventText;
                    } else if (mDtveitinfo.eitCurrentEventPf.extendedEventText.length() > 0) {
                        mStr = mDtveitinfo.eitCurrentEventPf.extendedEventText;
                    } else if (mDtveitinfo.eitCurrentEventPf.extendedEventItem.length() > 0) {
                        mStr = mDtveitinfo.eitCurrentEventPf.extendedEventItem;
                    } else {
                        mStr = "";
                    }
                    mSource_info_description.setText(mStr);

                    mSource_info_digital_TV.setText(getString(R.string.str_dtv_source_info_resolution)
                            + " : " + mStr_video_info);

                    mSource_info_description.requestFocus();
                }
            }
        } else if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
            ProgramInfo pinfo = mTvChannelManager.getCurrentProgramInfo();
            int dispCh = mCurChannelNumber;
            if (pinfo == null) {
                return;
            }
            mCurChannelNumber = pinfo.number;
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {		
                String channum = mTvAtscChannelManager.getDispChannelNum(pinfo);
                String name = mTvAtscChannelManager.getDispChannelName(pinfo);
                if(pinfo.favorite==1)   //add by wxy
                {
                mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                        + " : " + name+"    ");
              //add by wxy 
                Resources res = getResources();
        		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_favorite_focus);
                favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                mSource_info_tvname.setCompoundDrawables(null, null, favo_img, null);
                //add end
                }
                if(pinfo.isSkip)   //add by wxy
                {
                mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                        + " : " + name+"    ");
              //add by wxy 
                Resources res = getResources();
        		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_skip_focus);
                favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                mSource_info_tvname.setCompoundDrawables(null, null, favo_img, null);
                //add end
                }
                if((pinfo.isSkip==false)&&(pinfo.favorite!=1))
                {
                	mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                            + " : " + name+"    ");
                	mSource_info_tvnumber.setCompoundDrawables(null, null, null, null);
                }
                mSource_epg_event_rating.setText(getString(R.string.str_epg_event_rating)
                        + " : " + mTvAtscChannelManager.getCurrentRatingInformation());
            } else {
            	if(pinfo.favorite==1)
            	{
                mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                        + " : " + getCurProgrameName()+"    ");
              //add by wxy 
                Resources res = getResources();
        		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_favorite_focus);
                favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                mSource_info_tvname.setCompoundDrawables(null, null, favo_img, null);
                //add end
            	}
            	if(pinfo.isSkip)
            	{
                    mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                            + " : " + getCurProgrameName()+"    ");
                  //add by wxy 
                    Resources res = getResources();
            		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_skip_focus);
                    favo_img.setBounds(0, 0, favo_img.getMinimumWidth(), favo_img.getMinimumHeight());
                    mSource_info_tvname.setCompoundDrawables(null, null, favo_img, null);
                    //add end
                	}
            	if((pinfo.isSkip==false)&&(pinfo.favorite!=1))
            	{
            		mSource_info_tvname.setText(getString(R.string.str_textview_record_chaneel_name)
                            + " : " + getCurProgrameName()+"    ");
            		mSource_info_tvname.setCompoundDrawables(null, null, null, null);
            	}
            }
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                dispCh = mCurChannelNumber;
            } else {
                dispCh = mCurChannelNumber + 1;
            }
            mSource_info_tvnumber.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
                    + " : " + dispCh);
            numberToIcon(dispCh);

            videostandard = mTvChannelManager.getAvdVideoStandard();

            // get video standard
            switch (videostandard) {
                case TvChannelManager.AVD_VIDEO_STANDARD_PAL_BGHI:
                case TvChannelManager.AVD_VIDEO_STANDARD_PAL_M:
                case TvChannelManager.AVD_VIDEO_STANDARD_PAL_N:
                case TvChannelManager.AVD_VIDEO_STANDARD_PAL_60:
                    mStr = "PAL";
                    break;
                case TvChannelManager.AVD_VIDEO_STANDARD_NTSC_44:
                case TvChannelManager.AVD_VIDEO_STANDARD_NTSC_M:
                    mStr = "NTSC";
                    break;
                case TvChannelManager.AVD_VIDEO_STANDARD_SECAM:
                    mStr = "SECAM";
                    break;
                default:
                    mStr = "";
            }
            mSource_info_imageformat.setText(getString(R.string.str_atv_source_imageformat) + " : "
                    + mStr);
            mStr = getATVSoundFormat();
            mSource_info_soundformat.setText(getString(R.string.str_atv_source_soundformat) + " : "
                    + mStr);
            switch (mTvChannelManager.getAtvAudioStandard()) {
                case TvChannelManager.ATV_AUDIO_STANDARD_BG:
                    mStr = "BG";
                    break;
                case TvChannelManager.ATV_AUDIO_STANDARD_DK:
                    mStr = "DK";
                    break;
                case TvChannelManager.ATV_AUDIO_STANDARD_I:
                    mStr = " I";
                    break;
                case TvChannelManager.ATV_AUDIO_STANDARD_L:
                    mStr = " L";
                    break;
                case TvChannelManager.ATV_AUDIO_STANDARD_M:
                    mStr = " MN ";
                    break;
                default:
                    mStr = "BG";
            }
     
            mSource_info_audioformat.setText(getString(R.string.str_atv_source_audioformat) + " : "
                    + mStr);
           
          

        }
    }

    private void updateAudioInfo(boolean isAD) {
        DtvAudioInfo audioInfo = new DtvAudioInfo();
        audioInfo = mTvChannelManager.getAudioInfo();
        if (audioInfo.audioInfos.length > 0) {
            if (audioInfo.audioInfos[audioInfo.currentAudioIndex] != null) {
                mStr = "";
                if (audioInfo.audioInfos[audioInfo.currentAudioIndex].audioType < mAudioTypeDisplayString.length) {
                    mStr = mAudioTypeDisplayString[audioInfo.audioInfos[audioInfo.currentAudioIndex].audioType];
                }

                if (isAD) {
                    mStr += "  AD";
                }

                mSource_info_audio_format.setText(getString(R.string.str_dtv_source_info_audio_format)
                        + " : " + mStr);
            }
            if (mTvSystem != TvCommonManager.TV_SYSTEM_ATSC) {
                mSource_info_language.setText(getString(R.string.str_dtv_source_info_language) + " : "
                    + audioInfo.audioLangNum);
            }
        }
    }

    private void updateIsCcExist() {
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            if (mTvIsdbChannelManager.doesCcExist()) {
                mSource_info_cc.setText(getString(R.string.str_dtv_source_info_cc_exist));
            } else {
                mSource_info_cc.setText(getString(R.string.str_dtv_source_info_cc_noexist));
            }
        }
    }

    private void updateServiceType(ProgramInfo pi) {
        mStr = "";
        if (pi.serviceType < mServiceTypeDisplayString.length) {
             mStr = mServiceTypeDisplayString[pi.serviceType];
        }

        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)mSource_info_program_type.getLayoutParams();
            mlp.setMargins(0, 0, 0, 0);
        }
        mSource_info_program_type.setText(getString(R.string.str_dtv_source_info_program_type)
                + " : " + mStr);
    }

    private void updateVideoType(int type) {
        mStr = "";
        if (type < mVideoTypeDisplayString.length) {
            mStr = mVideoTypeDisplayString[type];
        }
        mSource_info_video_format
                .setText(getString(R.string.str_dtv_source_info_program_format) + " : " + mStr);
    }

    private String getATVSoundFormat() {
        String mStr = "";
        switch (TvCommonManager.getInstance().getATVMtsMode()) {
            case TvCommonManager.ATV_AUDIOMODE_MONO:
                mStr = "MONO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_DUAL_A:
                mStr = "DUAL A";
                break;
            case TvCommonManager.ATV_AUDIOMODE_DUAL_AB:
                mStr = "DUAL AB";
                break;
            case TvCommonManager.ATV_AUDIOMODE_DUAL_B:
                mStr = "DUAL B";
                break;
            case TvCommonManager.ATV_AUDIOMODE_FORCED_MONO:
                mStr = "MONO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_G_STEREO:
                mStr = "STEREO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_HIDEV_MONO:
                mStr = "MONO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_K_STEREO:
                mStr = "STEREO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_LEFT_LEFT:
                mStr = "LEFT LEFT";
                break;
            case TvCommonManager.ATV_AUDIOMODE_LEFT_RIGHT:
                mStr = "LEFT RIGHT";
                break;
            case TvCommonManager.ATV_AUDIOMODE_MONO_SAP:
                mStr = "MONO SAP";
                break;
            case TvCommonManager.ATV_AUDIOMODE_NICAM_DUAL_A:
                mStr = "NICAM DUAL A";
                break;
            case TvCommonManager.ATV_AUDIOMODE_NICAM_DUAL_AB:
                mStr = "NICAM DUAL AB";
                break;
            case TvCommonManager.ATV_AUDIOMODE_NICAM_DUAL_B:
                mStr = "NICAM DUAL B";
                break;
            case TvCommonManager.ATV_AUDIOMODE_NICAM_MONO:
                mStr = "NICAM MONO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_NICAM_STEREO:
                mStr = "NICAM STEREO";
                break;
            case TvCommonManager.ATV_AUDIOMODE_RIGHT_RIGHT:
                mStr = "RIGHT RIGHT";
                break;
            case TvCommonManager.ATV_AUDIOMODE_STEREO_SAP:
                mStr = "STEREO SAP";
                break;
            case TvCommonManager.ATV_AUDIOMODE_INVALID:
            default:
                mStr = "UNKNOWN";
                break;
        }
        return mStr;
    }

    /**
     * Select source icon according to the input source
     */
    private void selectIconByInputSource() {
        selectVideoInfo();
        switch (mInputSource) {
            case TvCommonManager.INPUT_SOURCE_VGA:
                setSourceInfo(R.drawable.grid_menu_pc, "VGA");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS:
                setSourceInfo(R.drawable.grid_menu_avi, "AV");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS2:
                setSourceInfo(R.drawable.grid_menu_avi, "AV2");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS3:
                setSourceInfo(R.drawable.grid_menu_avi, "CVBS3");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS4:
                setSourceInfo(R.drawable.grid_menu_avi, "CVBS4");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS5:
                setSourceInfo(R.drawable.grid_menu_avi, "CVBS5");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS6:
                setSourceInfo(R.drawable.grid_menu_avi, "CVBS6");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS7:
                setSourceInfo(R.drawable.grid_menu_avi, "CVBS7");
                break;
            case TvCommonManager.INPUT_SOURCE_CVBS8:
                setSourceInfo(R.drawable.grid_menu_avi, "CVBS8");
                break;
            case TvCommonManager.INPUT_SOURCE_SVIDEO:
                setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO");
                break;
            case TvCommonManager.INPUT_SOURCE_SVIDEO2:
                setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO2");
                break;
            case TvCommonManager.INPUT_SOURCE_SVIDEO3:
                setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO3");
                break;
            case TvCommonManager.INPUT_SOURCE_SVIDEO4:
                setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO4");
                break;
            case TvCommonManager.INPUT_SOURCE_YPBPR:
                setSourceInfo(R.drawable.grid_menu_yuv, "YPBPR");
                break;
            case TvCommonManager.INPUT_SOURCE_YPBPR2:
                setSourceInfo(R.drawable.grid_menu_yuv, "YPBPR2");
                break;
            case TvCommonManager.INPUT_SOURCE_YPBPR3:
                setSourceInfo(R.drawable.grid_menu_yuv, "YPBPR3");
                break;
            case TvCommonManager.INPUT_SOURCE_SCART:
                setSourceInfo(R.drawable.grid_menu_scart, "SCART");
                break;
            case TvCommonManager.INPUT_SOURCE_SCART2:
                setSourceInfo(R.drawable.grid_menu_scart, "SCART2");
                break;
            case TvCommonManager.INPUT_SOURCE_HDMI:
                if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                    setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI1");
                } else {
                    setSourceInfo(R.drawable.grid_menu_pc, "DVI");
                }
                break;
            case TvCommonManager.INPUT_SOURCE_HDMI2:
                if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                    setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI2");
                } else {
                    setSourceInfo(R.drawable.grid_menu_pc, "DVI2");
                }
                break;
            case TvCommonManager.INPUT_SOURCE_HDMI3:
                if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                    setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI3");
                } else {
                    setSourceInfo(R.drawable.grid_menu_pc, "DVI3");
                }
                break;
            case TvCommonManager.INPUT_SOURCE_HDMI4:
                if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                    setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI4");
                } else {
                    setSourceInfo(R.drawable.grid_menu_pc, "DVI4");
                }
                break;
            case TvCommonManager.INPUT_SOURCE_ATV:
            case TvCommonManager.INPUT_SOURCE_DTV:
                updateChannelInfo(true);
                break;
            case TvCommonManager.INPUT_SOURCE_DVI:
                setSourceInfo(R.drawable.grid_menu_pc, "DVI");
                break;
            case TvCommonManager.INPUT_SOURCE_DVI2:
                setSourceInfo(R.drawable.grid_menu_pc, "DVI2");
                break;
            case TvCommonManager.INPUT_SOURCE_DVI3:
                setSourceInfo(R.drawable.grid_menu_pc, "DVI3");
                break;
            case TvCommonManager.INPUT_SOURCE_DVI4:
                setSourceInfo(R.drawable.grid_menu_pc, "DVI4");
                break;
            default:
                break;
        }
    }

    private void setSourceInfo(int resId, String strtitle) {
    	mTvImageView.setImageResource(resId);
        mTitle.setText(strtitle);
        //add by wxy 
        if(mInputSource==TvCommonManager.INPUT_SOURCE_CVBS
        	||mInputSource==TvCommonManager.INPUT_SOURCE_CVBS2
        	||mInputSource==TvCommonManager.INPUT_SOURCE_CVBS3
        	||mInputSource==TvCommonManager.INPUT_SOURCE_CVBS4)
        {
        	try {
				colour_system=TvManager.getInstance().getPlayerManager().getVideoStandard().ordinal();
				//Log.i("wxy","----colour_system="+colour_system+"------");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.i("SourceInfoActivity","Get colour system fail");
				e.printStackTrace();
			}
        	switch(colour_system)
        	{
        	case 0:
        		mInfo.setText("PAL");
        		break;
        	case 1:
        		mInfo.setText("NTSC_M");
        		break;
        	case 2:
        		mInfo.setText("SECAM");
        		break;
        	case 3:
        		mInfo.setText("NTSC_443");
        		break;
        	case 4:
        		mInfo.setText("PAL_M");
        		break;
        	case 5:
        		mInfo.setText("PAL_N");
        		break;
        	case 6:
        		mInfo.setText("PAL_60");
        		break;
        	default:
        		if(mTvCommonManager.isSignalStable(mInputSource))
        		mInfo.setText("PAL");
        		else
        		mInfo.setText(" ");	
        		break;
        	}
        }
        else
        mInfo.setText(mStr_video_info);
    }

    private void selectVideoInfo() {
        if (mVideoInfo.vResolution != 0) {
            int s16FrameRateShow = (mVideoInfo.frameRate + 5) / 10;
            EnumScanType scanType = EnumScanType.E_PROGRESSIVE;
            try {
                scanType = mVideoInfo.getScanType();
            } catch (TvCommonException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            switch (mInputSource) {
                case TvCommonManager.INPUT_SOURCE_ATV:
                case TvCommonManager.INPUT_SOURCE_DTV:
                    if (scanType == EnumScanType.E_PROGRESSIVE) {
                        mStr_video_info = mVideoInfo.vResolution + "P";
                    } else {
                        mStr_video_info = mVideoInfo.vResolution + "I";
                    }
                    break;
                case TvCommonManager.INPUT_SOURCE_VGA:
                case TvCommonManager.INPUT_SOURCE_DVI:
                case TvCommonManager.INPUT_SOURCE_DVI2:
                case TvCommonManager.INPUT_SOURCE_DVI3:
                case TvCommonManager.INPUT_SOURCE_DVI4:
                    mStr_video_info = mVideoInfo.hResolution + "X" + mVideoInfo.vResolution + "@"
                            + s16FrameRateShow + "Hz";
                    break;
                case TvCommonManager.INPUT_SOURCE_HDMI:
                case TvCommonManager.INPUT_SOURCE_HDMI2:
                case TvCommonManager.INPUT_SOURCE_HDMI3:
                case TvCommonManager.INPUT_SOURCE_HDMI4:
                    if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                        if (scanType == EnumScanType.E_PROGRESSIVE) {
                            mStr_video_info = mVideoInfo.vResolution + "P";
                        } else {
                            mStr_video_info = mVideoInfo.vResolution + "I";
                        }
                        mStr_video_info += "@" + s16FrameRateShow + "Hz";
                    } else {
                        mStr_video_info = mVideoInfo.hResolution + "X" + mVideoInfo.vResolution
                                + "@" + s16FrameRateShow + "Hz";
                    }
                    break;
                case TvCommonManager.INPUT_SOURCE_YPBPR:
                case TvCommonManager.INPUT_SOURCE_YPBPR2:
                case TvCommonManager.INPUT_SOURCE_YPBPR3:
                    if (scanType == EnumScanType.E_PROGRESSIVE) {
                        mStr_video_info = mVideoInfo.vResolution + "P";
                    } else {
                        mStr_video_info = mVideoInfo.vResolution + "I";
                    }
                    mStr_video_info += "@" + s16FrameRateShow + "Hz";
                    break;
                default:
                    if (scanType == EnumScanType.E_PROGRESSIVE) {
                        mStr_video_info = mVideoInfo.vResolution + "P";
                    } else {
                        mStr_video_info = mVideoInfo.vResolution + "I";
                    }
                    break;
            }
        }
        if (TvChannelManager.getInstance().isSignalStabled() == false)
            mStr_video_info = "";
        if (mStr_video_info == "X") {
            mStr_video_info = "";
        }
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            if (mStr_video_info == null) {
                mStr_video_info = "";
            }
        }
    }

    public ArrayList<String> numberToPicture(int num) {
        ArrayList<String> strArray = new ArrayList<String>();
        String mStr = num + "";
        for (int i = 0; i < mStr.length(); i++) {
            char ch = mStr.charAt(i);
            strArray.add("" + ch);
        }
        return strArray;
    }

    public ArrayList<Integer> getResoulseID(ArrayList<String> mStr) {
        ArrayList<Integer> n = new ArrayList<Integer>();
        for (String string : mStr) {
            Integer resId = mNumberResIds[Integer.parseInt(string)];
            n.add(resId);
        }
        return n;
    }

    public void numberToIcon(int number) {
        ArrayList<Integer> n = getResoulseID(numberToPicture(number));
        if (number <= MAX_VALUE_OF_ONE_DIGIT) {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setVisibility(View.GONE);
            mThirdTvNumberIcon.setVisibility(View.GONE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.GONE);
            }
        } else if ((MAX_VALUE_OF_ONE_DIGIT < number) && (number <= MAX_VALUE_OF_TWO_DIGIT)) {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setImageResource(n.get(1));
            mFirstTvNumberIcon.setVisibility(View.VISIBLE);
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.GONE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.GONE);
            }
        } else if ((MAX_VALUE_OF_TWO_DIGIT < number) && (number <= MAX_VALUE_OF_THREE_DIGIT)) {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setImageResource(n.get(1));
            mThirdTvNumberIcon.setImageResource(n.get(2));
            mFirstTvNumberIcon.setVisibility(View.VISIBLE);
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.VISIBLE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.GONE);
            }
        } else {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setImageResource(n.get(1));
            mThirdTvNumberIcon.setImageResource(n.get(2));
            mFourthTvNumberIcon.setImageResource(n.get(3));
            mFirstTvNumberIcon.setVisibility(View.VISIBLE);
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.VISIBLE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.VISIBLE);
            }
        }
        if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            mTvDotIcon.setVisibility(View.GONE);
            mDigitMinorTvNumberIcon1.setVisibility(View.GONE);
            mDigitMinorTvNumberIcon2.setVisibility(View.GONE);
            mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
            mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
        }
    }

    public void majorMinorToIcon(int majorNum, int minorNum) {
        ArrayList<Integer> n = getResoulseID(numberToPicture(majorNum));
        if (majorNum <= MAX_VALUE_OF_ONE_DIGIT) {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setVisibility(View.GONE);
            mThirdTvNumberIcon.setVisibility(View.GONE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.GONE);
            }
        } else if ((MAX_VALUE_OF_ONE_DIGIT < majorNum) && (majorNum <= MAX_VALUE_OF_TWO_DIGIT)) {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setImageResource(n.get(1));
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.GONE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.GONE);
            }
        } else if ((MAX_VALUE_OF_TWO_DIGIT < majorNum) && (majorNum <= MAX_VALUE_OF_THREE_DIGIT)) {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setImageResource(n.get(1));
            mThirdTvNumberIcon.setImageResource(n.get(2));
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.VISIBLE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.GONE);
            }
        } else {
            mFirstTvNumberIcon.setImageResource(n.get(0));
            mSecondTvNumberIcon.setImageResource(n.get(1));
            mThirdTvNumberIcon.setImageResource(n.get(2));
            mFourthTvNumberIcon.setImageResource(n.get(3));
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.VISIBLE);
            if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mFourthTvNumberIcon.setVisibility(View.VISIBLE);
            }
        }

        if (minorNum == TvAtscChannelManager.ONEPARTCHANNEL_MINOR_NUM) {
            /*
             * For ONE-PART channel, the minor number is defined as 0xFFFF.
             * In a case: MajorNum = 15, MinorNum = 65535(0xFFFF), channel
             * number will be displayed as "15.0" in channellist menu.
             * User inputs "15.0" to tune to the channel. It needs to
             * convert "0" to "0xFFFF" before doing channel switching.
             */
            minorNum = 0;
        }
        n = getResoulseID(numberToPicture(minorNum));
        if ((mTvDotIcon != null) && (mDigitMinorTvNumberIcon1 != null) &&
           (mDigitMinorTvNumberIcon2 != null) && (mDigitMinorTvNumberIcon3 != null) &&
           (mDigitMinorTvNumberIcon4 != null)) {
            if (minorNum <= MAX_VALUE_OF_ONE_DIGIT) {
                mTvDotIcon.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
                mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon2.setVisibility(View.GONE);
                mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
                mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
            } else if ((MAX_VALUE_OF_ONE_DIGIT < minorNum) && (minorNum <= MAX_VALUE_OF_TWO_DIGIT)) {
                mTvDotIcon.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
                mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon2.setImageResource(n.get(1));
                mDigitMinorTvNumberIcon2.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
                mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
            } else if ((MAX_VALUE_OF_TWO_DIGIT < minorNum) && (minorNum <= MAX_VALUE_OF_THREE_DIGIT)) {
                mTvDotIcon.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
                mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon2.setImageResource(n.get(1));
                mDigitMinorTvNumberIcon2.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon3.setImageResource(n.get(2));
                mDigitMinorTvNumberIcon3.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
            } else {
                mTvDotIcon.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
                mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon2.setImageResource(n.get(1));
                mDigitMinorTvNumberIcon2.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon3.setImageResource(n.get(2));
                mDigitMinorTvNumberIcon3.setVisibility(View.VISIBLE);
                mDigitMinorTvNumberIcon4.setImageResource(n.get(3));
                mDigitMinorTvNumberIcon4.setVisibility(View.VISIBLE);
            }
        }
    }

    private TimerTask getTimerTask() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV
                        || (mInputSource == TvCommonManager.INPUT_SOURCE_DTV)) {
                    myHandler.sendEmptyMessage(CMD_SET_CURRENT_TIME);
                }
                mCount++;
                if (mCount < MAX_TIMES && mCount % 2 == 0// the period is 2s
                        && mInputSource == TvCommonManager.INPUT_SOURCE_YPBPR) {
                    myHandler.sendEmptyMessage(CMD_SIGNAL_LOCK);
                }
            }
        };
        return mTimerTask;
    }

    public void updateSourceInFo() {
        checkInputSourceAndInitView();
        clearTvPartialSourceInfo();
        if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV
                || mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            mFirstTvNumberIcon.setVisibility(View.VISIBLE);
            mSecondTvNumberIcon.setVisibility(View.VISIBLE);
            mThirdTvNumberIcon.setVisibility(View.VISIBLE);
            mCurrentTime.setVisibility(View.VISIBLE);
            mSource_info_tvnumber.setVisibility(View.VISIBLE);
            mSource_info_tvname.setVisibility(View.VISIBLE);
        }
        mVideoInfo = TvPictureManager.getInstance().getVideoInfo();
        selectIconByInputSource();
    }

    public ArrayList<ProgramInfo> getAllProgramList() {
        ProgramInfo pgi = null;
        int indexBase = 0;
        ArrayList<ProgramInfo> progInfoList = new ArrayList<ProgramInfo>();
        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        int m_nServiceNum = 0;

        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            currInputSource = mInputSource;
        }
        if (currInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
            indexBase = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            if (indexBase == 0xFFFFFFFF) {
                indexBase = 0;
            }
            m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);

        } else {
            indexBase = 0;
            m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
        }

        for (int k = indexBase; k < m_nServiceNum; k++) {
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                pgi = mTvAtscChannelManager.getProgramInfo(k);
            } else {
                pgi = mTvChannelManager.getProgramInfoByIndex(k);
            }
            if (pgi != null) {
                if ((pgi.isDelete == true) || (pgi.isVisible == false)) {
                    continue;
                } else {
                    progInfoList.add(pgi);
                }
            }

        }
        return progInfoList;
    }

    public ProgramInfo getNextProgramm() {

        ArrayList<ProgramInfo> progInfoList = getAllProgramList();
        if (progInfoList == null || progInfoList.size() == 0) {
            return null;
        }
        ProgramInfo currentProgInfo = mTvChannelManager.getCurrentProgramInfo();
        if (currentProgInfo == null) {
            return null;
        }
        ProgramInfo next = null;
        for (int i = 0; i < progInfoList.size(); i++) {
            ProgramInfo pi = progInfoList.get(i);
            if (currentProgInfo.frequency == pi.frequency
                    && currentProgInfo.serviceId == pi.serviceId
                    && currentProgInfo.number == pi.number && currentProgInfo.progId == pi.progId) {
                if (i < progInfoList.size() - 1) {
                    next = progInfoList.get(i + 1);
                    break;
                }
                if (i == progInfoList.size() - 1) {
                    next = progInfoList.get(0);
                }

            }
        }
        return next;

    }

    public ProgramInfo getPreviousProgram() {
        ArrayList<ProgramInfo> progInfoList = getAllProgramList();
        ProgramInfo currentProgInfo = mTvChannelManager.getCurrentProgramInfo();
        if (currentProgInfo == null) {
            return null;
        }
        ProgramInfo previous = null;
        for (int i = 0; i < progInfoList.size(); i++) {
            ProgramInfo pi = progInfoList.get(i);
            if (currentProgInfo.number == pi.number
                    && currentProgInfo.serviceType == pi.serviceType) {
                if (i == 0) {
                    previous = progInfoList.get(progInfoList.size() - 1);
                    break;
                }
                if (i > 0) {
                    previous = progInfoList.get(i - 1);
                }
            }
        }
        return previous;
    }

    private boolean isNeedToCheckExitRecord(ProgramInfo pi) {
        if (pi == null) {
            return false;
        }
        /* Always time shift recording will auto stop by tvsystem. */
        final TvPvrManager pvr = TvPvrManager.getInstance();
        if (PVRActivity.currentRecordingProgrammFrency != pi.frequency
                && pvr.isAlwaysTimeShiftRecording() == false
                && pvr.isRecording()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mCount = 0;
        CecSetting setting = mTvCecManager.getCecConfiguration();
        switch (mInputSource) {
            case TvCommonManager.INPUT_SOURCE_HDMI:
            case TvCommonManager.INPUT_SOURCE_HDMI2:
            case TvCommonManager.INPUT_SOURCE_HDMI3:
            case TvCommonManager.INPUT_SOURCE_HDMI4: {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    break;
                }
                if ((setting.cecStatus == mCecStatusOn) && (TvCommonManager.getInstance().isHdmiSignalMode() == true)) {
                     if (mTvCecManager.sendCecKey(keyCode)) {
                        Log.d(TAG, "onKeyDown return TRUE");
                        return true;
                     }
                }
                break;
            }
            case TvCommonManager.INPUT_SOURCE_CVBS:
            case TvCommonManager.INPUT_SOURCE_CVBS2:
            case TvCommonManager.INPUT_SOURCE_CVBS3:
            case TvCommonManager.INPUT_SOURCE_CVBS4:
            case TvCommonManager.INPUT_SOURCE_YPBPR:
            case TvCommonManager.INPUT_SOURCE_YPBPR2:
            case TvCommonManager.INPUT_SOURCE_YPBPR3:
            case TvCommonManager.INPUT_SOURCE_DTV:
            case TvCommonManager.INPUT_SOURCE_ATV: {
                if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
                        || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                        || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
                    if (setting.cecStatus == mCecStatusOn) {
                        if (mTvCecManager.sendCecKey(keyCode)) {
                            Log.d(TAG, "onKeyDown return TRUE");
                            return true;
                        }
                    }
                }
                break;
            }
            default:
                break;
        }

        if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
            finish();
            return true;
        } else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
            finish();
            return true;
        } else if (SwitchPageHelper.goToPvrPage(this, keyCode) == true) {
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
                || keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN) {
            if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    if (isDTVChannelNameReady == true) {
                        isDTVChannelNameReady = false;
                        mTvChannelManager.programDown();
                        return true;
                    }
                } else {
                    mTvChannelManager.programDown();
                    updateChannelInfo(true);
                }

            } else if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                if (isDTVChannelNameReady == true) {
                    isDTVChannelNameReady = false;
                    if (isNeedToCheckExitRecord(getPreviousProgram())) {
                        // Toast.makeText
                        AlertDialog.Builder build = new AlertDialog.Builder(this);
                        build.setMessage(R.string.str_pvr_tip2);
                        build.setPositiveButton(R.string.str_stop_record_dialog_stop, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                TvPvrManager.getInstance().stopRecord();
                                PVRActivity.currentRecordingProgrammFrency = -1;
                                mTvChannelManager.programDown();
                                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                                    return;
                                }
                                startSourceInfo();
                            }
                        });
                        build.setNegativeButton(R.string.str_stop_record_dialog_cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        build.create().show();
                        return true;
                    }
                    mTvChannelManager.programDown();
                    if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                        return true;
                    }
                    startSourceInfo();
                    return true;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_CHANNEL_UP) {

            if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    if (isDTVChannelNameReady == true) {
                        isDTVChannelNameReady = false;
                        mTvChannelManager.programUp();
                        return true;
                    }
                } else {
                    mTvChannelManager.programUp();
                    updateChannelInfo(true);
                }
            } else if (mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                if (isDTVChannelNameReady == true) {
                    isDTVChannelNameReady = false;
                    if (isNeedToCheckExitRecord(getNextProgramm())) {
                        // Toast.makeText
                        AlertDialog.Builder build = new AlertDialog.Builder(SourceInfoActivity.this);
                        build.setMessage(R.string.str_pvr_tip2);
                        build.setPositiveButton(R.string.str_stop_record_dialog_stop, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                TvPvrManager.getInstance().stopRecord();
                                PVRActivity.currentRecordingProgrammFrency = -1;
                                mTvChannelManager.programUp();
                                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                                    return;
                                }
                                startSourceInfo();
                            }
                        });
                        build.setNegativeButton(R.string.str_stop_record_dialog_cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        build.create().show();
                        return true;

                    }

                    mTvChannelManager.programUp();
                    if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                        return true;
                    }
                    startSourceInfo();
                    return true;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else if (keyCode == MKeyEvent.KEYCODE_CHANNEL_RETURN) {
            if (mInputSource == TvCommonManager.INPUT_SOURCE_ATV
                    || mInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                mTvChannelManager.returnToPreviousProgram();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateChannelInfo(true);
            } else {
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            finish();
            return true;
        } else if (keyCode >= KeyEvent.KEYCODE_1 && keyCode <= KeyEvent.KEYCODE_9) {
            Intent intentChannelControl = new Intent(this, ChannelControlActivity.class);
            intentChannelControl.putExtra("KeyCode", keyCode);
            this.startActivity(intentChannelControl);
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
            if (deviceName.equals("MStar Smart TV IR Receiver")
                    || deviceName.equals("MStar Smart TV Keypad")) {
                updateChannelInfo(false);
            } else {
                Log.d(TAG, "deviceName is:" + deviceName);
            }
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
            if (deviceName.equals("MStar Smart TV IR Receiver")
                    || deviceName.equals("MStar Smart TV Keypad")) {
                updateChannelInfo(true);
            } else {
                Log.d(TAG, "deviceName is:" + deviceName);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Invoked by MstarBaseActivity in which send the 10s delay.
     */
    @Override
    public void onTimeOut() {
        super.onTimeOut();
        mIntent = new Intent(this, RootActivity.class);
        startActivity(mIntent);
        finish();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TvIntent.ACTION_SIGNAL_LOCK)) {
                myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 1000);// this
            } else if (intent.getAction().equals(TvIntent.ACTION_SOURCE_INFO_DISAPPEAR)) {
                finish();
            }
        }
    };

    /**
     * Open SourceInfoActivity Added by gerard.jiang for "0396073" in 2013/05/18
     */
    private void startSourceInfo() {
        boolean isSystemLocked = TvParentalControlManager.getInstance().isSystemLock();
        boolean isCurrentProgramLocked = mTvChannelManager.getCurrentProgramInfo().isLock;
        if (isSystemLocked && isCurrentProgramLocked) {
            Intent intent = new Intent(this, SourceInfoActivity.class);
            intent.putExtra("info_key", true);
            startActivity(intent);
        }
    }
}
