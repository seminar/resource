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

package com.mstar.tv.tvplayer.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.Gravity;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.os.Looper;

import com.mstar.android.MKeyEvent;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tv.TvCecManager.OnCecCtrlEventListener;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tv.TvMhlManager;
import com.mstar.android.tv.TvS3DManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.android.tv.TvPipPopManager;
import com.mstar.android.tv.TvFactoryManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tv.TvCiManager;
import com.mstar.android.tv.TvCiManager.OnUiEventListener;
import com.mstar.android.tv.TvCiManager.OnCiStatusChangeEventListener;
import com.mstar.android.tv.TvGingaManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tv.TvTimerManager.OnEpgTimerEventListener;
import com.mstar.android.tv.TvTimerManager.OnPvrTimerEventListener;
import com.mstar.android.tv.TvTimerManager.OnOadTimerEventListener;
import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tv.TvHbbTVManager;
import com.mstar.android.tv.TvOadManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.android.tv.widget.TvView;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.listener.OnTvEventListener;
import com.mstar.android.tvapi.common.listener.OnMhlEventListener;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.CecSetting;
import com.mstar.android.tvapi.common.vo.HbbtvEventInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.EnumPipModes;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.dtv.common.CaManager;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;
import com.mstar.android.tvapi.dtv.vo.MwAtscEasInfo;
import com.mstar.android.tvapi.atv.vo.AtvEventScan;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.common.CaManager.OnCaEventListener;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.tv.tvplayer.ui.channel.ChannelControlActivity;
import com.mstar.tv.tvplayer.ui.dtv.CimmiActivity;
import com.mstar.tv.tvplayer.ui.dtv.eas.atsc.EmergencyAlertDialog;
import com.mstar.tv.tvplayer.ui.component.PasswordCheckDialog;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.CheckParentalPwdActivity;
import com.mstar.tv.tvplayer.ui.tuning.OadScan;
import com.mstar.tv.tvplayer.ui.dtv.oad.OadDownload;
import com.mstar.tv.tvplayer.ui.holder.CaViewHolder;
import com.mstar.tv.tvplayer.ui.MenuConstants;
import com.mstar.tv.tvplayer.ui.pvr.PVRActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.tuning.AutoTuneOptionActivity;
import com.mstar.tv.tvplayer.ui.tuning.dvb.DvbsDTVAutoTuneOptionActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tvframework.MstarUIActivity;
import com.mstar.util.Constant;
import com.mstar.util.Constant.SignalProgSyncStatus;
import com.mstar.util.Constant.ScreenSaverMode;
import com.mstar.util.Tools;
import com.mstar.util.TvEvent;
import android.os.SystemProperties;

public class RootActivity extends MstarUIActivity {
    private static final String TAG = "RootActivity";

    // 15min.
    //zb20141016 modify
    //time+1 minute
    private static final int NO_SIGNAL_SHUTDOWN_TIME = 9 * 60 * 1000;
    private static final int NO_SIGNAL_SHUTDOWN_TIME_VGA=1*60*1000;
    //end
    
    private static final int SCREENSAVER_DEFAULT_STATUS = -1;

    public boolean bCmd_TvApkExit = false;

    private TvView tvView = null;

    private boolean mIsSignalLock = true;

    private static boolean mIsActive = false;

    private static boolean mIsBackKeyPressed = false;

    private volatile int mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;

    private static boolean mIsScreenSaverShown = false;

    private static boolean mIsMsrvStarted = false;

    private static String TV_APK_START = "com.mstar.tv.ui.tvstart";

    private static String TV_APK_END = "com.mstar.tv.ui.tvend";

    private final static String PREFERENCES_INPUT_SOURCE = "INPUT_SOURCE";

    private final static String PREFERENCES_PREVIOUS_INPUT_SOURCE = "PREVIOUS_INPUT_SOURCE";

    private TvCommonManager mTvCommonManager = null;

    private TvPictureManager mTvPictureManager = null;

    private TvS3DManager mTvS3DManager = null;

    private TvChannelManager mTvChannelManager = null;
    
    private AudioManager maudiomanager = null;

    private TvMhlManager mTvMhlManager = null;

    private TvTimerManager mTvTimerManager = null;

    private TvCecManager mTvCecManager = null;

    private boolean mIsImageFreezed = false;

    private static boolean isFirstPowerOn = true;

    private static int systemAutoTime = 0;

    private boolean mIsExiting = false;

    private final int mCecStatusOn = 1;

    private Handler mAutoShutdownHandler = new Handler();

    private boolean mIsEasShow = false;

    private int mEasRemainTime = 0;

    private final int CH_NUM_INVALID = -1;

    private int mEasPreProgramMajor = CH_NUM_INVALID;

    private int mEasPreProgramMinor = CH_NUM_INVALID;

    private final int EAS_DISPLAY_TIME_DEFAULT = 10;

    private final int EAS_DISPLAY_TIME_CHECK = 15;

    private final int EAS_DISPLAY_TIME_EXTEND = 30;

    private int mPreviousInputSource = mTvCommonManager.INPUT_SOURCE_NONE;

    public int caCurNotifyIdx;

    public int caCurEvent;

    private int msgFromActivity = 1;

    protected static AlertDialog mExitDialog;

    // now close 3D function, when open, it
    private boolean _3Dflag = false;

    // shall be deleted
    private static RootActivity rootActivity = null;

    private CaViewHolder caViewHolder;

    private boolean startPvr = false;

    private static boolean isReboot = false;

    private boolean mIskeyLocked = false;

    private PowerManager mPowerManager = null;

    private AlertDialog mCiPlusOPRefreshDialog = null;

    private BroadcastReceiver mReceiver = null;

    private OnDtvPlayerEventListener mDtvPlayerEventListener = null;

    private OnAtvPlayerEventListener mAtvPlayerEventListener = null;

    private OnTvPlayerEventListener mTvPlayerEventListener = null;

    private OnTvEventListener mTvEventListener = null;

    private OnEpgTimerEventListener mEpgTimerEventListener = null;

    private OnPvrTimerEventListener mPvrTimerEventListener = null;

    private OnCaEventListener mCaEventListener = null;

    private OnUiEventListener mUiEventListener = null;

    private OnCiStatusChangeEventListener mCiStatusChangeEventListener = null;

    private OnCecCtrlEventListener mCecCtrlEventListener = null;

    private OnOadTimerEventListener mOadTimerEventListener = null;

    private Toast mCcKeyToast;

    private TextView mNoSignalView = null;

    private TextView mFreezeView = null;

    private boolean mIsPowerOn = false;

    private boolean mIsToPromptPassword = false;

    private int mTvSystem = 0;

    private PasswordCheckDialog mPasswordLock = null;

    private Runnable mEasDisplayTextRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "mEasDisplayTextRunnable Runnable :" + mEasRemainTime);
            if (mEasRemainTime > 0) {
                mEasRemainTime--;
                handler.postDelayed(this, 1000);
            } else {
                Log.i(TAG, "mEasPreProgramMajor:" + mEasPreProgramMajor + " mEasPreProgramMinor:" + mEasPreProgramMinor);
                if ((mEasPreProgramMajor + mEasPreProgramMinor) > 0) {
                    Log.i(TAG, "switch to per channel:" + mEasPreProgramMajor + "." + mEasPreProgramMinor);
                    TvAtscChannelManager.getInstance().programSel(mEasPreProgramMajor, mEasPreProgramMinor);
                }
                EmergencyAlertDialog.create(getApplicationContext()).dismiss();
                mIsEasShow = false;
            }
        }
    };

    private class DtvPlayerEventListener implements OnDtvPlayerEventListener {

        @Override
        public boolean onDtvChannelNameReady(int what) {
            return false;
        }

        @Override
        public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
            return false;
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
            if (arg1 == TvOadManager.OAD_UI_TYPE_DOWNLOAD_CONFIRM) {
                RootActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d(TAG, "call oadDownloadConfirm");
                        oadDownloadConfirm();
                    }
                });
            }
            return true;
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
            Log.d(TAG, "SIGNAL Lock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_LOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onSignalUnLock(int what) {
            Log.d(TAG, "SIGNAL UnLock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_UNLOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onUiOPRefreshQuery(int what) {
            Log.i(TAG, "get CI+ OP event EV_CI_OP_REFRESH_QUERY");
            TvCiManager.getInstance().ciClearOPSearchSuspended();
            displayOpRefreshconfirmation();
            return true;
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
            Log.d(TAG, "SIGNAL Lock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_LOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onSignalUnLock(int what) {
            Log.d(TAG, "SIGNAL UnLock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_UNLOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onAtvProgramInfoReady(int what) {
            return false;
        }
    }

    private class TvPlayerEventListener implements OnTvPlayerEventListener {

        @Override
        public boolean onScreenSaverMode(int what, int arg1) {
            mScreenSaverStatus = arg1;
            /*Log.d(TAG, "EV_SCREEN_SAVER_MODE***");
            Log.d(TAG, "onScreenSaverMode receive status:" + mScreenSaverStatus);*/
            // getCurrentInputSource takes much time so leave it to subthread.
            new Thread() {
                public void run() {
                    int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
                    Message m = Message.obtain();
                    m.arg1 = mScreenSaverStatus;
                    m.arg2 = curInputSource;
                    screenSaverHandler.sendMessage(m);
                };
            }.start();

            return true;
        }

        @Override
        public boolean onHbbtvUiEvent(int what, HbbtvEventInfo eventInfo) {
            return false;
        }

        @Override
        public boolean onPopupDialog(int what, int arg1, int arg2) {
            Log.d(TAG, "onPopupDialog(" + arg1 + "," + arg2 + ")");
            if (TvCommonManager.POPUP_DIALOG_SHOW == arg1) {
                mIsToPromptPassword = true;
                if (true == mIsActive) {
                    showPasswordLock(true);
                }
            } else if (TvCommonManager.POPUP_DIALOG_HIDE == arg1) {
                mIsToPromptPassword = false;
                showPasswordLock(false);
            }
            return true;
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
            RootActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast toast = Toast.makeText(RootActivity.this,  "pvr playback is stopped", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            if (PVRActivity.isPVRActivityActive) {
                Intent intent = new Intent(RootActivity.this, PVRActivity.class);
                intent.putExtra("PVR_PLAYBACK_STOP", 11);
                RootActivity.this.startActivity(intent);
            }
            return true;
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
            RootActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast toast = Toast.makeText(RootActivity.this,  R.string.str_disk_too_slow, Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            return true;
        }

        @Override
        public boolean onPvrNotifyUsbRemoved(int what, int arg1) {
            RootActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast toast = Toast.makeText(RootActivity.this,  "The disk is full, please check the disk capacity", Toast.LENGTH_LONG);
                    toast.show();
                }
            });

            if (PVRActivity.isPVRActivityActive) {
                Intent intent = new Intent(RootActivity.this, PVRActivity.class);
                intent.putExtra("PVR_ONE_TOUCH_MODE", 4);
                RootActivity.this.startActivity(intent);
            } else {
                final TvPvrManager pvr = TvPvrManager.getInstance();
                if (pvr != null) {
                    pvr.stopPvr();
                    if (pvr.getIsBootByRecord()) {
                        pvr.setIsBootByRecord(false);
                        mTvCommonManager.standbySystem("pvr");
                    }
                }
                PVRActivity.currentRecordingProgrammFrency = -1;
            }
            return true;
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
            Log.d(TAG, "onPvrNotifyAlwaysTimeShiftProgramReady");

            final boolean result = ((TvPvrManager.getInstance().startAlwaysTimeShiftRecord() == TvPvrManager.PVR_STATUS_SUCCESS));
            RootActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (result){
                        RelativeLayout recordingView = (RelativeLayout) findViewById(R.id.pvralwaysrecording);
                        recordingView.setVisibility(View.VISIBLE);
                    } else {
                        Toast toast = Toast.makeText(RootActivity.this,
                                R.string.str_pvr_alwaystimeshift_record_fail, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });
            Log.d(TAG, "onPvrNotifyAlwaysTimeShiftProgramReady result:" + result);
            return result;
        }

        @Override
        public boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int what) {
            Log.d(TAG, "onPvrNotifyAlwaysTimeShiftProgramNotReady");

            if (TvPvrManager.getInstance().isAlwaysTimeShiftRecording()) {
                if (TvPvrManager.getInstance().isPlaybacking()) {
                    return false;
                } else if (TvPvrManager.getInstance().isAlwaysTimeShiftPlaybackPaused()) {
                    TvPvrManager.getInstance().pauseAlwaysTimeShiftPlayback(false);
                }
                TvPvrManager.getInstance().stopAlwaysTimeShiftRecord();
            }
            RootActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout recordingView = (RelativeLayout) findViewById(R.id.pvralwaysrecording);
                    recordingView.setVisibility(View.GONE);
                }
            });


            return true;
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
            Log.d(TAG, "SIGNAL Lock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_LOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onSignalUnLock(int what) {
            Log.d(TAG, "SIGNAL UnLock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_UNLOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onEpgUpdateList(int what, int arg1) {
            return false;
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

            if (TvCommonManager.getInstance().getCurrentTvSystem() != TvCommonManager.TV_SYSTEM_ATSC) {
                return false;
            }

            Log.e(TAG, "onEmerencyAlert() arg1:" + arg1 + ",  arg2:" + arg2);

            MwAtscEasInfo easInfo = null;
            String text = "";

            easInfo = TvAtscChannelManager.getInstance().getEASInProgress();
            Log.i(TAG, "easInfo:" + easInfo.u16DetailsMajorNum + "." + easInfo.u16DetailsMinorNum);

            if (easInfo == null) {
                return false;
            }

            int type = arg1;
            Log.i(TAG, "EAS currentType is" + type);

            switch (type) {
                case TvAtscChannelManager.EAS_TEXT_ONLY:
                    mEasRemainTime = EAS_DISPLAY_TIME_DEFAULT;
                    handler.removeCallbacks(mEasDisplayTextRunnable);
                    handler.post(mEasDisplayTextRunnable);
                    mIsEasShow = true;
                    StringBuffer sb = new StringBuffer();
                    for (int j = 0; j < easInfo.au8AlertText.length; j++) {
                        if (easInfo.au8AlertText[j] > 0) {
                            sb.append((char) easInfo.au8AlertText[j]);
                        }
                    }

                    if (easInfo.au8AlertText != null && easInfo.au8AlertText[0] != 0) {
                        text += sb.toString();
                    } else {
                        text = "";
                    }
                    EmergencyAlertDialog.create(getApplicationContext()).show(text);
                    if ((mEasPreProgramMajor + mEasPreProgramMinor) > 0) {
                        TvAtscChannelManager.getInstance().programSel(mEasPreProgramMajor, mEasPreProgramMinor);
                        mEasPreProgramMajor = CH_NUM_INVALID;
                        mEasPreProgramMinor = CH_NUM_INVALID;
                    }
                    break;
                case TvAtscChannelManager.EAS_DETAIL_CHANNEL:
                    mEasRemainTime = easInfo.u8AlertTimeRemain;
                    Log.i(TAG, "remainTime" + mEasRemainTime);
                    if (mEasRemainTime < EAS_DISPLAY_TIME_CHECK) {
                        mEasRemainTime += EAS_DISPLAY_TIME_EXTEND;
                    }
                    Log.i(TAG, "remainTime:" + mEasRemainTime);
                    handler.removeCallbacks(mEasDisplayTextRunnable);
                    handler.post(mEasDisplayTextRunnable);
                    mIsEasShow = true;
                    StringBuffer sb2 = new StringBuffer();
                    for (int j = 0; j < easInfo.au8AlertText.length; j++) {
                        if (easInfo.au8AlertText[j] > 0) {
                            sb2.append((char) easInfo.au8AlertText[j]);
                        }
                    }

                    if (easInfo.au8AlertText != null && easInfo.au8AlertText[0] != 0) {
                        text += sb2.toString();
                    } else {
                        text = "";
                    }
                    Log.i(TAG, "Text:" + text);

                    EmergencyAlertDialog.create(getApplicationContext()).show(text);
                    ProgramInfo pInfo = TvAtscChannelManager.getInstance().getCurrentProgramInfo();
                    if ((easInfo.u16DetailsMajorNum == pInfo.majorNum)
                        && (easInfo.u16DetailsMinorNum == pInfo.minorNum)) {
                        Log.i(TAG, "same channel ,needn't tune to channel");
                        return true;
                    }
                    Log.i(TAG, "switch channel to " + easInfo.u16DetailsMajorNum + "." + easInfo.u16DetailsMinorNum);
                    TvAtscChannelManager.getInstance().programSel(easInfo.u16DetailsMajorNum, easInfo.u16DetailsMinorNum);
                    mEasPreProgramMajor = pInfo.majorNum;
                    mEasPreProgramMinor = pInfo.minorNum;
                    break;
                case TvAtscChannelManager.EAS_STOP_TEXT_SCORLL:
                    EmergencyAlertDialog.create(getApplicationContext()).stopScroll();
                    break;
                case TvAtscChannelManager.EAS_UPDATE_TIME_REMAINING:
                    mEasRemainTime = easInfo.u8AlertTimeRemain;
                    Log.i(TAG, "remainTime:" + mEasRemainTime);
                    handler.removeCallbacks(mEasDisplayTextRunnable);
                    handler.post(mEasDisplayTextRunnable);
                    break;
            }
            return true;
        }

        @Override
        public boolean onDtvChannelInfoUpdate(int what, int info, int arg2) {
            return false;
        }
    }

    private class TvEventListener implements OnTvEventListener {

        @Override
        public boolean onDtvReadyPopupDialog(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onScartMuteOsdMode(int what) {
            return false;
        }

        @Override
        public boolean onSignalUnlock(int what) {
            Log.d(TAG, "SIGNAL UnLock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_UNLOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onSignalLock(int what) {
            Log.d(TAG, "SIGNAL Lock***");
            Message m = Message.obtain();
            m.arg1 = TvEvent.SIGNAL_LOCK;
            signalLockHandler.sendMessage(m);
            return true;
        }

        @Override
        public boolean onUnityEvent(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onScreenSaverMode(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onAtscPopupDialog(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onDeadthEvent(int what, int arg1, int arg2) {
            return false;
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
    }

    private class EpgTimerEventListener implements OnEpgTimerEventListener {
        @Override
        public boolean onEpgTimerEvent(int what, int arg1, int arg2) {
            Log.i(TAG, "onEpgTimerEvent(), what:" + what);
            switch (what) {
                case TvTimerManager.TVTIMER_EPG_TIME_UP: {
                        if (mIsActive == false) {
                            Intent intent = new Intent(TvIntent.ACTION_START_ROOTACTIVITY);
                            startActivity(intent);
                        }
                    }
                    break;
                case TvTimerManager.TVTIMER_EPG_TIMER_RECORD_START: {
                        Intent intent = new Intent(RootActivity.this, PVRActivity.class);
                        intent.putExtra("PVR_ONE_TOUCH_MODE", 1);
                        startActivity(intent);
                    }
                    break;
                default: {
                    }
                    break;
            }
            return true;
        }
    }

    private class PvrTimerEventListener implements OnPvrTimerEventListener {
        @Override
        public boolean onPvrTimerEvent(int what, int arg1, int arg2) {
            Log.i(TAG, "onPvrTimerEvent(), what:" + what);
            switch (what) {
                case TvTimerManager.TVTIMER_PVR_NOTIFY_RECORD_STOP: {
                    Intent intent = new Intent(RootActivity.this, PVRActivity.class);
                    intent.putExtra("PVR_ONE_TOUCH_MODE", 4);
                    startActivity(intent);
                }
                    break;
                default: {
                }
                    break;
            }
            return true;
        }
    }

    private class OadTimerEventListener implements OnOadTimerEventListener {
        @Override
        public boolean onOadTimerEvent(int what) {
            Log.i(TAG, "onOadTimerEvent(), what:" + what);
            switch (what) {
                case TvTimerManager.TVTIMER_OAD_TIME_SCAN: {
                    oadScanConfirm();
                }
                    break;
                default: {
                }
                    break;
            }
            return true;
        }
    }

    public static RootActivity getInstance() {
        return rootActivity;
    }

    /**
     * 1. update No signal activity 2. start TV apk
     */
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.ROOTACTIVITY_RESUME_MESSAGE) {
                /*
                 * Modified by gerard.jiang for "0386249" in 2013/04/28. Add
                 * reboot flag When system is suspending (isScreenOn == false),
                 * do not start No Signal Activity. by Desmond Pu 2013/12/18
                 */
                if (_3Dflag == false && !isReboot && (mPowerManager.isScreenOn()))
                /***** Ended by gerard.jiang 2013/04/28 *****/
                {
                    updateTvSourceSignal();
                }
                //Notfiy event queue to start sending pending event
                try {
                    mTvCommonManager.setTvosCommonCommand(Constant.TV_EVENT_LISTENER_READY);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (msg.what == 900) {
                executePreviousTask(getIntent());

                SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("_3Dflag", _3Dflag);
                editor.commit();
                checkSystemAutoTime();
            }
        };
    };

    public boolean isBackKeyPressed() {
        return mIsBackKeyPressed;
    }

    private void gotoPVRRecordingForStandBy() {
        Intent i = new Intent(this, PVRActivity.class);
        startActivity(i);
    }

    Handler standbyHandler = new Handler() {

        public void handleMessage(Message msg) {
            gotoPVRRecordingForStandBy();
        };
    };

    // ---------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.root);
        rootActivity = this;
        _3Dflag = false;
        super.onCreate(savedInstanceState);
        mTvCommonManager = TvCommonManager.getInstance();
        mTvPictureManager = TvPictureManager.getInstance();
        mTvS3DManager = TvS3DManager.getInstance();
        mTvChannelManager = TvChannelManager.getInstance();
        // tvAudioManager =TvAudioManager.getInstance();
        mTvCecManager = TvCecManager.getInstance();
        mTvSystem = mTvCommonManager.getCurrentTvSystem();
        mNoSignalView = (TextView) findViewById(R.id.NoSiganlText);
        mFreezeView = (TextView) findViewById(R.id.FreezeText);
        mNoSignalView.setText(R.string.str_no_signal);
        mFreezeView.setText("Freeze On");
        mFreezeView.setBackgroundColor(android.graphics.Color.BLACK);
        createSurfaceView();
        mIsMsrvStarted = true;
        caViewHolder = new CaViewHolder(this);
        mHandler.sendEmptyMessage(900);
        mTvMhlManager = TvMhlManager.getInstance();
        mTvTimerManager = TvTimerManager.getInstance();
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        maudiomanager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);	
        mIsPowerOn = getIntent().getBooleanExtra("isPowerOn", false);

        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            mPasswordLock = new PasswordCheckDialog(rootActivity) {
                @Override
                public String onCheckPassword() {
                    return MenuConstants.getSharedPreferencesValue(rootActivity, MenuConstants.VCHIPPASSWORD, MenuConstants.VCHIPPASSWORD_DEFAULTVALUE);
                }

                @Override
                public void onPassWordCorrect() {
                    mToast.cancel();
                    mToast = Toast.makeText(rootActivity, rootActivity.getResources().getString(R.string.str_check_password_pass) , Toast.LENGTH_SHORT);
                    mToast.show();

                    // FIXME: unblock both if the password if correct.
                    if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == rootActivity.mScreenSaverStatus) {
                        TvAtscChannelManager.getInstance().setBlockSysLockModeTemporarily(false);
                    } else if (ScreenSaverMode.DTV_SS_CH_BLOCK == rootActivity.mScreenSaverStatus) {
                        ProgramInfo ProgInf = TvAtscChannelManager.getInstance().getCurrentProgramInfo();
                        if (ProgInf.isLock) {
                            if (TvChannelManager.getInstance() != null) {
                                TvChannelManager.getInstance()
                                        .setProgramAttribute(TvChannelManager.PROGRAM_ATTRIBUTE_LOCK,
                                        ProgInf.majorNum, (short)ProgInf.minorNum, ProgInf.progId, false);
                            }
                        }
                    }
                    dismiss();
                }

                @Override
                public void onKeyDown(int keyCode, KeyEvent keyEvent) {
                    if (KeyEvent.KEYCODE_BACK != keyCode && KeyEvent.KEYCODE_ENTER != keyCode) {
                        RootActivity.this.onKeyDown(keyCode, keyEvent);
                    }
                }

                @Override
                public void onCancel() {
                    mToast.cancel();
                    mToast = Toast.makeText(rootActivity, rootActivity.getResources().getString(R.string.str_check_password_invalid) , Toast.LENGTH_SHORT);
                    mToast.show();
                }
            };
        }

        // updateTvSourceSignal();
        if (caViewHolder.isCaEnable()) {
            caViewHolder.emailNotify(CaManager._current_email_type);
            caViewHolder.detitleNotify(CaManager._current_detitle_type);
        }
        boolean isPVRStandby = getIntent().getBooleanExtra("isPVRStandby", false);
        Log.e(TAG, "onCreate isPVRStandby:" + isPVRStandby);
        if (isPVRStandby) {
            standbyHandler.sendEmptyMessageDelayed(100, 5000);
        }

        // for support input source change intent send from source hot key
        if (getIntent() != null && getIntent().getAction() != null) {
            if (getIntent().getAction().equals(TvIntent.ACTION_SOURCE_CHANGE)) {
                /*
                 * clear screen saver status after changing input source
                 * and hide NoSignalView before change input source.
                 */
                mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
                showNoSignalView(false);
                int inputsource = getIntent().getIntExtra("inputSrc", TvCommonManager.INPUT_SOURCE_ATV);
                Log.i(TAG, "onCreate:inputsource = " + inputsource);
                startSourceChange(inputsource);
            }
        }

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TvIntent.ACTION_EXIT_TV_APK)) {
                    tvApkExitHandler();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(TvIntent.ACTION_EXIT_TV_APK);
        registerReceiver(mReceiver, filter);

        LayoutInflater factory = LayoutInflater.from(this);
        final View layout = factory.inflate(R.layout.ciplus_oprefresh_confirmation_dialog, null);

        mCiPlusOPRefreshDialog = new AlertDialog.Builder(this)
            .setTitle(getString(R.string.str_ciplus_op_confirmation_title))
            .setView(layout)
            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setPositiveButton(getString(android.R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TvCiManager.getInstance().sendCiOpSearchStart(false);
                            updateScreenSaver();
                        }
                    })
            .setNegativeButton(getString(android.R.string.no),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateScreenSaver();
                        }
                    })
            .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                            updateScreenSaver();
                        }
                    })
            .create();

        mExitDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.str_root_alert_dialog_title)
                .setMessage(R.string.str_root_alert_dialog_message)
                .setPositiveButton(R.string.str_root_alert_dialog_confirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LittleDownTimer.destory();
                                mIsBackKeyPressed = false;
                                dialog.dismiss();
                                mIsExiting = true;
                                //zb20141031 add
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                        		intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        		intent.setComponent(new ComponentName("com.toptech.launcherkorea2",
                        				"com.toptech.launcherkorea2.Launcher"));
                        		startActivity(intent);
                                //end
                                finish();
                            }
                        })
                .setNegativeButton(R.string.str_root_alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mIsActive = true;
                                dialog.dismiss();
                                mIsBackKeyPressed = false;
                                bCmd_TvApkExit = false;
                                updateScreenSaver();
                                caViewHolder.sendCaNotifyMsg(caCurEvent, caCurNotifyIdx,
                                        msgFromActivity);
                                mIskeyLocked = false;
                            }
                        }).setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mIskeyLocked = false;
                        mIsActive = true;
                        dialog.dismiss();
                        mIsBackKeyPressed = false;
                        bCmd_TvApkExit = false;
                        updateScreenSaver();
                        caViewHolder.sendCaNotifyMsg(caCurEvent, caCurNotifyIdx, msgFromActivity);

                        // ***add by allen.sun 2013-5-27
                        // Adaptation different resolutions in STB
                        if (Tools.isBox()) {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent pipIntent = new Intent("com.mstar.pipservice");
                                    pipIntent.putExtra("cmd", "visible");
                                    RootActivity.this.startService(pipIntent);
                                }
                            }.start();
                        }
                        // ***and end
                    }

                }).create();

        TvManager.getInstance().getMhlManager().setOnMhlEventListener(new OnMhlEventListener() {
            Intent intent;
            @Override
            public boolean onKeyInfo(int arg0, int arg1, int arg2) {
                Log.d(TAG, "onKeyInfo");
                return false;
            }

            @Override
            public boolean onAutoSwitch(int arg0, final int arg1, int arg2) {
                Log.d(TAG, "onAutoSwitch");
                intent = new Intent(TvIntent.ACTION_START_ROOTACTIVITY);
                intent.putExtra("task_tag", "input_source_changed");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTvS3DManager
                                .setDisplayFormatForUI(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                        TvCommonManager.getInstance().setInputSource(arg1);
                        startActivity(intent);
                    }
                }).start();
                return false;
            }
        });

        mDtvPlayerEventListener = new DtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
        mTvPlayerEventListener = new TvPlayerEventListener();
        TvChannelManager.getInstance().registerOnTvPlayerEventListener(mTvPlayerEventListener);
        mAtvPlayerEventListener = new AtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnAtvPlayerEventListener(mAtvPlayerEventListener);
        mTvEventListener = new TvEventListener();
        TvCommonManager.getInstance().registerOnTvEventListener(mTvEventListener);
        mEpgTimerEventListener = new EpgTimerEventListener();
        TvTimerManager.getInstance().registerOnEpgTimerEventListener(mEpgTimerEventListener);
        mPvrTimerEventListener = new PvrTimerEventListener();
        TvTimerManager.getInstance().registerOnPvrTimerEventListener(mPvrTimerEventListener);
        mCaEventListener = caViewHolder.getCaEventListener();
        TvCaManager.getInstance().registerOnCaEventListener(mCaEventListener);
        mUiEventListener = new UiEventListener();
        TvCiManager.getInstance().registerOnUiEventListener(mUiEventListener);
        mCiStatusChangeEventListener = new CiStatusChangeEventListener();
        TvCiManager.getInstance().registerOnCiStatusChangeEventListener(mCiStatusChangeEventListener);
        mOadTimerEventListener = new OadTimerEventListener();
        TvTimerManager.getInstance().registerOnOadTimerEventListener(mOadTimerEventListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged(), newConfig:" + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        mCecCtrlEventListener = new CecCtrlEventListener();
        TvCecManager.getInstance().registerOnCecCtrlEventListener(mCecCtrlEventListener);

        // Start Autotuning if TvPlayer Never Launched
        SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
        if (false == settings.getBoolean(Constant.PREFERENCES_IS_AUTOSCAN_LAUNCHED, false)) {
            SharedPreferences.Editor editor = settings.edit();
            Log.d(TAG, "Launch TV First Time , Do AutoScan");
            if (TvCommonManager.TV_SYSTEM_ATSC != mTvSystem
                    && TvCommonManager.TV_SYSTEM_ISDB != mTvSystem) {
                if (TvCiManager.getInstance().isOpMode() == false) {
                    startAutoTuning();
                } else {
                    Log.d(TAG, getResources().getString(R.string.str_op_forbid_channel_tuning_content));
                }
            } else {
                startAutoTuning();
            }
        } else {
            Log.d(TAG, "Not Launch TV First Time, No Needed To Do AutoScan");
        }
    }

    //zb20141107 add
    private boolean isNoMenu=true;
    //end
    @Override
    protected void onResume() {
        Log.i(TAG, "onResume()");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTvPictureManager.is4K2KMode(true)) {
                    sendBroadcast(new Intent("enter.4k2k"));
                }
            }
        }, 1000);
        super.onResume();
      //zb20141029 add
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if((!TvCommonManager.getInstance().isSignalStable(TvCommonManager.getInstance().getCurrentTvInputSource()))
                		&&(isNoMenu==true))
                	mNoSignalView.setVisibility(View.VISIBLE);
            }
        }, 3000);
        isNoMenu=true;
        //end
        
        Log.d(TAG, "onResume() mScreenSaverStatus = " + mScreenSaverStatus);
        showPasswordLock(mIsToPromptPassword);

        sendBroadcast(new Intent(TV_APK_START));
	//zb20141022 add
        mIsSignalLock=mTvCommonManager.isSignalStable(mTvCommonManager.getCurrentTvInputSource());
	//end
        if (!mIsSignalLock) {
            mAutoShutdownHandler.removeCallbacks(mShutdownTask);
            //zb20141022 add
            showPasswordLock(false);
            mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
            mNoSignalView.setText(R.string.str_no_signal);
            mIsScreenSaverShown = true;
            showNoSignalView(true);
            //end
            //zb20141016 add
            if(mTvCommonManager.getCurrentTvInputSource()==TvCommonManager.INPUT_SOURCE_VGA)
            {
            	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME_VGA);
            }
            else 
            //end
            {
            	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME);
			}
        }

        // get previous inputsource from preferences
        SharedPreferences settings = getSharedPreferences(PREFERENCES_INPUT_SOURCE, Context.MODE_PRIVATE);
        mPreviousInputSource = settings.getInt(PREFERENCES_PREVIOUS_INPUT_SOURCE, TvCommonManager.INPUT_SOURCE_ATV);
        Log.d(TAG, "get previous input source from preference:" + mPreviousInputSource);

        mIskeyLocked = false;
        tvView.setBackgroundColor(Color.TRANSPARENT);
        mIsBackKeyPressed = false;
        mHandler.sendEmptyMessage(Constant.ROOTACTIVITY_RESUME_MESSAGE);

        if (TvPvrManager.getInstance().isAlwaysTimeShiftRecording() ==  true){
            RelativeLayout recordingView = (RelativeLayout) findViewById(R.id.pvralwaysrecording);
            recordingView.setVisibility(View.VISIBLE);
        }

        if (startPvr) {
            startPvr = false;
            Intent intent2 = new Intent(this, PVRActivity.class);
            intent2.putExtra("PVR_ONE_TOUCH_MODE", 1);
            startActivity(intent2);
        }
        if (TvCommonManager.INPUT_SOURCE_ATV == mTvCommonManager.getCurrentTvInputSource()) {
            SharedPreferences share = getSharedPreferences("atv_ttx", Context.MODE_PRIVATE);
            boolean atvttx = share.getBoolean("ATV_TTXOPEN", false);
            if (atvttx) {
                TvChannelManager.getInstance()
                        .openTeletext(TvChannelManager.TTX_MODE_SUBTITLE_NAVIGATION);
            }
        }
    }

    private void startAutoTuning() {
        Log.d(TAG, "startAutoTuning()");
        int currentRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
        TvTypeInfo tvInfo = TvCommonManager.getInstance().getTvInfo();
        int currentRoute = tvInfo.routePath[currentRouteIndex];
        Log.d(TAG, "currRoute:" + currentRouteIndex);
        Intent intent = new Intent();

        if (TvChannelManager.TV_ROUTE_DVBS == currentRoute
                || TvChannelManager.TV_ROUTE_DVBS2 == currentRoute) {
            Log.d(TAG, "create dvbs activity");
            intent.setAction(TvIntent.ACTION_DVBSDTV_AUTOTUNING_OPTION);
        } else {
            Log.d(TAG, "create normal tuning activity");
            intent.setAction(TvIntent.ACTION_AUTOTUNING_OPTION);
        }
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void checkSystemAutoTime() {
        try {
            systemAutoTime = Settings.System
                    .getInt(getContentResolver(), Settings.Global.AUTO_TIME);
        } catch (SettingNotFoundException e) {
            systemAutoTime = 0;
        }

        if (systemAutoTime > 0) {
            int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
            if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                Settings.System.putInt(getContentResolver(), Settings.Global.AUTO_TIME, 0);
            }
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        sendBroadcast(new Intent(TV_APK_END));
        if (mCiPlusOPRefreshDialog != null && mCiPlusOPRefreshDialog.isShowing()) {
            mCiPlusOPRefreshDialog.dismiss();
        }

        if (mExitDialog != null) {
            mExitDialog.dismiss();
        }
        mIsPowerOn = false;
        TvCecManager.getInstance().unregisterOnCecCtrlEventListener(mCecCtrlEventListener);
        mCecCtrlEventListener = null;

        //switch input source to storage for releasing TV relative resrouce
        if (TvCommonManager.getInstance().getCurrentTvInputSource() != TvCommonManager.INPUT_SOURCE_STORAGE) {
            Log.i(TAG, "onStop(): Switch input source to storage...");
//            TvCommonManager.getInstance().setInputSource(TvCommonManager.INPUT_SOURCE_STORAGE);
        }

        super.onStop();
    }

    private void updateTvSourceSignal() {
        int curInputSource = TvCommonManager.INPUT_SOURCE_NONE;
        curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        Log.i(TAG, "curInputSource is :" + curInputSource);
        boolean noChangeSource = getIntent().getBooleanExtra("no_change_source", false);
        getIntent().removeExtra("no_change_source");
        Log.d(TAG, "mIsMsrvStarted:" + mIsMsrvStarted);
        if (mIsMsrvStarted == true) {
            /**
            * If current inputsource is storage, it means apk resume from mm.
            * We need to change inputsource to previous tv inputsource.
            */
            if ((TvCommonManager.INPUT_SOURCE_STORAGE == curInputSource)
                    || (TvCommonManager.INPUT_SOURCE_NONE == curInputSource)) {
                if (!noChangeSource && !mIsPowerOn) {
                    new Thread(new Runnable() {
                        public void run() {
                            Log.d(TAG, "Change InputSource to previous :" + mPreviousInputSource);
                            TvCommonManager.getInstance().setInputSource(mPreviousInputSource);
                            TVRootApp.setSourceDirty(true);
                            if (mPreviousInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                                TvChannelManager.getInstance().changeToFirstService(
                                        TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                                        TvChannelManager.FIRST_SERVICE_DEFAULT);
                            } else if (mPreviousInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                                TvChannelManager.getInstance().changeToFirstService(
                                        TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV,
                                        TvChannelManager.FIRST_SERVICE_DEFAULT);
                            }
                        }
                    }).start();
                    TvPictureManager.getInstance().setDynamicBackLightThreadSleep(false);
                }
            } else {
                /* The flow add here is for the purpose of acquiring tv resource control from
                   reousrce manager. It will notify resource manager TvPlayer own the TV resource
                   as this activity startup.
                 */
                if (mIsPowerOn == true) {
                    new Thread(new Runnable() {
                            public void run() {
                            Log.d(TAG, "Change InputSource to previous :" + mPreviousInputSource);
                            // acquire resource control from resource manager
                            TvCommonManager.getInstance().setInputSource(mPreviousInputSource);
                            }
                            }).start();
                }
                if (isFirstPowerOn == false) {
                    if (curInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                        startThread(true);
                    } else if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                        startThread(false);
                    }
                }
                isFirstPowerOn = false; // move here for first power on
            }
            int swMode = mTvChannelManager.getAtvChannelSwitchMode();
            if (swMode == TvChannelManager.CHANNEL_SWITCH_MODE_FREEZESCREEN) {
                mTvChannelManager.setChannelChangeFreezeMode(true);
            } else {
                mTvChannelManager.setChannelChangeFreezeMode(false);
            }
            mIsMsrvStarted = false;
        }
        curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        mIsActive = true;
        updateScreenSaver();
        if (caViewHolder.isCaEnable()) {
            caViewHolder.sendCaNotifyMsg(caCurEvent, caCurNotifyIdx, msgFromActivity);
        } else {
            caViewHolder.cancelAllCANotify();
        }
        if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            mTvChannelManager.setUserScanType(TvChannelManager.TV_SCAN_DTV);
        } else {
            mTvChannelManager.setUserScanType(TvChannelManager.TV_SCAN_ATV);
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        //zb20141107 add
        isNoMenu=false;
        //end
        showPasswordLock(false);

        if (mCcKeyToast != null) {
            mCcKeyToast.cancel();
        }
        if (true == mIsExiting) {
            Log.i(TAG, "Exiting, prepare to change souce");
            mIsExiting = false;
        }
        mIsActive = false;
        TVRootApp.setSourceDirty(false);

        int curInputSource = TvCommonManager.INPUT_SOURCE_NONE;
        curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        /**
         * If user switch between tv and launch too fast, the currentInputSource had not changed
         * from storage to last input source. Don't need to update  mPreviousInputSource
         */
        if ((TvCommonManager.INPUT_SOURCE_STORAGE != curInputSource)
                && (TvCommonManager.INPUT_SOURCE_NONE != curInputSource)) {
            mPreviousInputSource = curInputSource;
            Log.d(TAG, "Save previous inputsource :" + mPreviousInputSource);
            SharedPreferences settings = getSharedPreferences (PREFERENCES_INPUT_SOURCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(PREFERENCES_PREVIOUS_INPUT_SOURCE, mPreviousInputSource);
            editor.commit();
        }

        if (mExitDialog != null) {
            mExitDialog.dismiss();
        }
        mAutoShutdownHandler.removeCallbacks(mShutdownTask);
        //zb20141029 add
        if(!TvCommonManager.getInstance().isSignalStable(TvCommonManager.getInstance().getCurrentTvInputSource()))
        	mNoSignalView.setVisibility(View.GONE);
        //end
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart()");
        isFirstPowerOn = true;
        mIsMsrvStarted = true;
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");

        if (mCiPlusOPRefreshDialog != null && mCiPlusOPRefreshDialog.isShowing()) {
            mCiPlusOPRefreshDialog.dismiss();
        }

        if (mExitDialog != null && mExitDialog.isShowing()) {
            mExitDialog.dismiss();
        }
        if (systemAutoTime > 0) {
            int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
            if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                Settings.System.putInt(getContentResolver(), Settings.Global.AUTO_TIME,
                        systemAutoTime);
            }
        }
        TvPictureManager.getInstance().setDynamicBackLightThreadSleep(true);
        TvChannelManager.getInstance().unregisterOnAtvPlayerEventListener(mAtvPlayerEventListener);
        mAtvPlayerEventListener = null;
        TvChannelManager.getInstance().unregisterOnDtvPlayerEventListener(mDtvPlayerEventListener);
        mDtvPlayerEventListener = null;
        TvChannelManager.getInstance().unregisterOnTvPlayerEventListener(mTvPlayerEventListener);
        mTvPlayerEventListener = null;
        TvCommonManager.getInstance().unregisterOnTvEventListener(mTvEventListener);
        mTvEventListener = null;
        TvTimerManager.getInstance().unregisterOnEpgTimerEventListener(mEpgTimerEventListener);
        mEpgTimerEventListener = null;
        TvTimerManager.getInstance().unregisterOnPvrTimerEventListener(mPvrTimerEventListener);
        mPvrTimerEventListener = null;
        TvCaManager.getInstance().unregisterOnCaEventListener(mCaEventListener);
        mCaEventListener = null;
        TvCiManager.getInstance().unregisterOnUiEventListener(mUiEventListener);
        mUiEventListener = null;
        TvCiManager.getInstance().unregisterOnCiStatusChangeEventListener(mCiStatusChangeEventListener);
        mCiStatusChangeEventListener = null;
        TvTimerManager.getInstance().unregisterOnOadTimerEventListener(mOadTimerEventListener);
        mOadTimerEventListener = null;
        unregisterReceiver(mReceiver);
        mIsActive = false;
        TvCaManager.getInstance().setCurrentEvent(caCurEvent);
        TvCaManager.getInstance().setCurrentMsgType(caCurNotifyIdx);
        TVRootApp.setSourceDirty(false);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        executePreviousTask(intent);
        setIntent(intent);
        boolean isPVRStandby = intent.getBooleanExtra("isPVRStandby", false);
        Log.e(TAG, "onNewIntent isPVRStandby=" + isPVRStandby);
        if (isPVRStandby) {
            standbyHandler.sendEmptyMessageDelayed(100, 5000);
        }

        // In case RootActivity under onStop() stage, we have to handle input source change intent here
        if (getIntent() != null && getIntent().getAction() != null) {
            if (getIntent().getAction().equals(TvIntent.ACTION_SOURCE_CHANGE)) {
                /*
                 * clear screen saver status after changing input source
                 * and hide NoSignalView before change input source.
                 */
                mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
                showNoSignalView(false);
                int inputsource = getIntent().getIntExtra("inputSrc", TvCommonManager.INPUT_SOURCE_ATV);
                Log.i(TAG, "onNewIntent:inputsource = " + inputsource);
                startSourceChange(inputsource);
            }
        }
    }

    private void executePreviousTask(final Intent paramIntent) {
        if (paramIntent != null) {
            String taskTag = paramIntent.getStringExtra("task_tag");
            if ("input_source_changed".equals(taskTag)) {
                TVRootApp.setSourceDirty(true);
            }
        }
    }

    public ArrayList<ProgramInfo> getAllProgramList() {
        ProgramInfo pgi = null;
        int indexBase = 0;
        ArrayList<ProgramInfo> progInfoList = new ArrayList<ProgramInfo>();
        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        int m_nServiceNum = 0;
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
        Log.d(TAG, "indexBase:" + indexBase);
        Log.d(TAG, "m_nServiceNum:" + m_nServiceNum);
        for (int k = indexBase; k < m_nServiceNum; k++) {
            if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                pgi = TvAtscChannelManager.getInstance().getProgramInfo(k);
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

    /**
     * handle the up, down, return and 0-9 key
     *
     * @param keyCode
     * @return
     */
    public boolean onChannelChange(int keyCode) {
        if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_ATV
                || TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
            if (keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (isNeedToCheckExitRecord(getPreviousProgram())) {
                    AlertDialog.Builder build = new AlertDialog.Builder(RootActivity.this);
                    build.setMessage(R.string.str_pvr_tip2);
                    build.setPositiveButton(R.string.str_stop_record_dialog_stop, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TvPvrManager.getInstance().stopRecord();
                            PVRActivity.currentRecordingProgrammFrency = -1;
                            mTvChannelManager.programDown();
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
                startSourceInfo();
                return true;

            } else if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP
                    || keyCode == KeyEvent.KEYCODE_DPAD_UP) {

                if (isNeedToCheckExitRecord(getNextProgramm())) {
                    AlertDialog.Builder build = new AlertDialog.Builder(RootActivity.this);
                    build.setMessage(R.string.str_pvr_tip2);
                    build.setPositiveButton(R.string.str_stop_record_dialog_stop, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TvPvrManager.getInstance().stopRecord();
                            PVRActivity.currentRecordingProgrammFrency = -1;
                            mTvChannelManager.programUp();
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
                startSourceInfo();
                return true;
            } else if (keyCode == MKeyEvent.KEYCODE_CHANNEL_RETURN) {
                mTvChannelManager.returnToPreviousProgram();
                Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
                intent.putExtra("info_key", true);
                this.startActivity(intent);
                return true;
            }
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
                case MKeyEvent.KEYCODE_SUBTITLE:
                    Intent intentChannelControl = new Intent(this, ChannelControlActivity.class);
                    intentChannelControl.putExtra("KeyCode", keyCode);
                    this.startActivity(intentChannelControl);
                    return true;
            }
        }
        return false;
    }

    static boolean canRepeatKey = true;

    static int preKeyCode = KeyEvent.KEYCODE_UNKNOWN;

    Handler mRepeatHandler = new Handler();

    Runnable mRepeatRun = new Runnable() {
        public void run() {
            canRepeatKey = true;
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (sendGingaKey(keyCode, event)) {
            Log.i(TAG, "onKeyUp:sendGingaKey success!");
            return true;
        }

        if (mTvMhlManager.CbusStatus() == true && mTvMhlManager.IsMhlPortInUse() == true) {
            if (mTvMhlManager.IRKeyProcess(keyCode, true) == true) {
                SystemClock.sleep(140);
                return true;
            }
        }
        
        Log.d(TAG, " onKeyUp  keyCode = " + keyCode+" return super.onKeyDown");
        return super.onKeyUp(keyCode, event);
    };

    /*
     * @Override public boolean onKeyUp(int keyCode, KeyEvent event) { if
     * (mTvMhlManager.CbusStatus()==true && mTvMhlManager.IsMhlPortInUse()==true)
     * { if (mTvMhlManager.IRKeyProcess(keyCode, true) == true) {
     * SystemClock.sleep(140); return true; } } return super.onKeyUp(keyCode,
     * event); }
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mAutoShutdownHandler.removeCallbacks(mShutdownTask);
      //zb20141016 add
        if(mTvCommonManager.getCurrentTvInputSource()==TvCommonManager.INPUT_SOURCE_VGA)
        {
        	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME_VGA);
        }
        else 
        //end
        {
        	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME);
		}

        //zb20141013 add
        if(SystemProperties.getInt("persist.sys.aging",0)==1)
        {
        	if(keyCode==KeyEvent.KEYCODE_MENU)
        	{
        		try{
        			TvManager.getInstance().setTvosCommonCommand("StopAging");
        			if(SystemProperties.getInt("persist.sys.ismute",0)==1)
        			{
        				SystemProperties.set("persist.sys.muteiconctl","1"); //0:narmal ctl 1:mute and show mute icon 2:mute but hide mute icon 
        				maudiomanager.setMasterMute(true);
        			}
        			
        		} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		if(TvCommonManager.getInstance().getCurrentTvInputSource() != TvCommonManager.INPUT_SOURCE_ATV)
            	{
        			TvCommonManager.getInstance().setInputSource(TvCommonManager.INPUT_SOURCE_ATV);
        			
            	}
        		TvChannelManager.getInstance().selectProgram(TvChannelManager.getInstance().getCurrentChannelNumber(),
        				TvChannelManager.SERVICE_TYPE_ATV);
        	}
        	return true;
        }
        //end
        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
            if (mIsEasShow == true) {
                Log.w(TAG, "onKeyDown: user input blocked in showing EAS inforations");
                return true;
            }
        }

        final boolean down = event.getAction() == KeyEvent.ACTION_DOWN;
        if (down && (keyCode == KeyEvent.KEYCODE_M || keyCode == KeyEvent.KEYCODE_MENU)) {

            if (!canRepeatKey && !(preKeyCode == keyCode)) {
                preKeyCode = keyCode;
            } else {
                preKeyCode = keyCode;
                mRepeatHandler.removeCallbacks(mRepeatRun);
                canRepeatKey = false;
                mRepeatHandler.postDelayed(mRepeatRun, 2000);
            }
        }

        if (sendHbbTVKey(keyCode)) {
            Log.i(TAG, "onKeyDown:sendHbbTVKey success!");
            return true;
        }

        // arrange CEC key
	 //for volume keys,move to PhoneWindow.java for responding under all conditions-lxk 20141110
	 if((keyCode != KeyEvent.KEYCODE_VOLUME_UP)
           &&(keyCode != KeyEvent.KEYCODE_VOLUME_DOWN)
           &&(keyCode != KeyEvent.KEYCODE_VOLUME_MUTE)){//end
        if (sendCecKey(keyCode)) {
            Log.i(TAG, "onKeyDown:sendCecKey success!");
            return true;
        }
	 }

        // arrange Mheg5 key
        if (sendMheg5Key(keyCode)) {
            Log.i(TAG, "onKeyDown:sendMhegKey success!");
            return true;
        }

        // arrange Ginga key
        if (sendGingaKey(keyCode, event)) {
            Log.i(TAG, "onKeyDown:sendGingaKey success!");
            return true;
        }

        if (mTvMhlManager.CbusStatus() == true && mTvMhlManager.IsMhlPortInUse() == true) {
            if (mTvMhlManager.IRKeyProcess(keyCode, false) == true) {
                SystemClock.sleep(140);
                return true;
            }
        }

        if (!Constant.lockKey && keyCode != KeyEvent.KEYCODE_BACK) {
            return true;
        }
        if (_3Dflag) {
        }
        Log.d(TAG, " onKeyDown  keyCode = " + keyCode);
        if (mIsImageFreezed == true) {
            mTvPictureManager.unFreezeImage();
            mFreezeView.setVisibility(TextView.INVISIBLE);
            mIsImageFreezed = !mIsImageFreezed;
            if (MKeyEvent.KEYCODE_FREEZE == keyCode) {
                return true;
            }
        }
        switch (keyCode) {
            case MKeyEvent.KEYCODE_CC:
                if (mTvCommonManager.isSupportModule(TvCommonManager.MODULE_CC)
                        || mTvCommonManager.isSupportModule(TvCommonManager.MODULE_BRAZIL_CC)) {
                    if (mCcKeyToast == null) {
                        mCcKeyToast = new Toast(this);
                        mCcKeyToast.setGravity(Gravity.CENTER, 0, 0);
                    }
                    TextView tv = new TextView(RootActivity.this);
                    tv.setTextSize(Constant.CCKEY_TEXTSIZE);
                    tv.setTextColor(Color.WHITE);
                    tv.setAlpha(Constant.CCKEY_ALPHA);
                    mCcKeyToast.setView(tv);
                    mCcKeyToast.setDuration(Toast.LENGTH_SHORT);

                    int closedCaptionMode = TvCcManager.getInstance().getNextClosedCaptionMode();
                    TvCcManager.getInstance().setClosedCaptionMode(closedCaptionMode);
                    TvCcManager.getInstance().stopCc();
                    if (TvCcManager.CLOSED_CAPTION_OFF != closedCaptionMode) {
                        TvCcManager.getInstance().startCc();
                    }
                    String[] closedCaptionStrings = getResources().getStringArray(R.array.str_arr_option_closed_caption);
                    if (0 <= closedCaptionMode && closedCaptionStrings.length > closedCaptionMode) {
                        tv.setText(getResources().getString(R.string.str_option_caption)
                                + " "
                                + closedCaptionStrings[closedCaptionMode]);
                    }
                    mCcKeyToast.show();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
                if (deviceName.equals("MStar Smart TV IR Receiver")
                        || deviceName.equals("MStar Smart TV Keypad")) {
                    AudioManager audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    /*
                     * Adjust the volume in on key down since it is more
                     * responsive to the user.
                     */
                    if (audiomanager != null) {
                        int flags = AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_VIBRATE;
                        audiomanager.adjustVolume(
                                keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ? AudioManager.ADJUST_RAISE
                                        : AudioManager.ADJUST_LOWER, flags);
                    }
                } else {
                    Log.d(TAG, "deviceName is:" + deviceName);
                }
                break;
            case KeyEvent.KEYCODE_PROG_YELLOW:
            case KeyEvent.KEYCODE_PROG_BLUE:
            case KeyEvent.KEYCODE_PROG_GREEN:
            case KeyEvent.KEYCODE_PROG_RED:
            case KeyEvent.KEYCODE_ENTER:
                if (caViewHolder.isCaEnable()) {
                    Intent intent = new Intent();
                    intent.setClass(this, EmailListActivity.class);
                    RootActivity.this.startActivity(intent);
                    break;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                if (mIsBackKeyPressed == false) {
                    mIsActive = false;
                    mIsBackKeyPressed = true;
                    if (!mIskeyLocked) {
                        mIskeyLocked = true;
                        showExitDialog();
                    }
                }
                return true;
            case MKeyEvent.KEYCODE_FREEZE:
                if (mIsImageFreezed == false) {
                    Log.i(TAG, "KEYCODE_FREEZE pressed, mIsImageFreezed = false, freeze window");
                    mIsImageFreezed = mTvPictureManager.freezeImage();
                    mFreezeView.setVisibility(TextView.VISIBLE);
                }
                return true;
            case KeyEvent.KEYCODE_M:
            case KeyEvent.KEYCODE_MENU:
                break;
        }
        if (!mIskeyLocked && SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
            mIskeyLocked = true;
            Log.d(TAG, "onKeyDown->goToMenuPage  keyCode = " + keyCode);
            return true;
        } else if (SwitchPageHelper.goToSourceInfo(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToPvrPage(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToPvrBrowserPage(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToSubtitleLangPage(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToAudioLangPage(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToProgrameListInfo(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToFavorateListInfo(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToSleepMode(this, keyCode) == true) {
            return true;
        } else if (SwitchPageHelper.goToTeletextPage(this, keyCode) == true) {
            return true;
        } else if (onChannelChange(keyCode) == true) {
            return true;
        }
        
        Log.d(TAG, " onKeyDown  keyCode = " + keyCode+" return super.onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    private void createSurfaceView() {
        tvView = (TvView) findViewById(R.id.tranplentview);
        Boolean isPowerOn = getIntent() != null ? getIntent().getBooleanExtra("isPowerOn", false)
                : false;
        Log.d(TAG, "isPowerOn = " + isPowerOn);
        String taskTag = getIntent().getStringExtra("task_tag");
        // true means don't set window size which will cause black screen
        tvView.openView(isPowerOn || "input_source_changed".equals(taskTag));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Constant.CHANNEL_LOCK_RESULT_CODE:
                if (data.getExtras().getBoolean("result")) {
                    mNoSignalView.setVisibility(TextView.INVISIBLE);
                }
                break;
            default:
                Log.w(TAG, "onActivityResult resultCode not match");
                break;
        }
    }

    /*
     * update NoSignalView Text String by Screen Saver Status
     * and input source.
     */
    private Handler signalLockHandler = new Handler() {
        public void handleMessage(Message msg) {
            int lockStatus = msg.arg1;
            if (TvEvent.SIGNAL_LOCK == lockStatus) {
                mIsSignalLock = true;
            } else if (TvEvent.SIGNAL_UNLOCK == lockStatus) {
                mIsSignalLock = false;
            }

            int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
            mIsToPromptPassword = false;
            if (mIsSignalLock) {
                /*
                 * send broadcast to those who need to know signal lock status.
                 * ex: SourceInfoActivity will get this intent when it is alive,
                 *     and update source info content.
                 */
                sendBroadcast(new Intent(TvIntent.ACTION_SIGNAL_LOCK));
                mAutoShutdownHandler.removeCallbacks(mShutdownTask);

                switch (curInputSource) {
                    case TvCommonManager.INPUT_SOURCE_DTV: {
                        /* show SourceInfo before updating NoSignalView */
                        startSourceInfo();
                        /* update NoSignalView string by screen saver status */
                        if (ScreenSaverMode.DTV_SS_INVALID_SERVICE == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_invalid_service);
                        } else if (ScreenSaverMode.DTV_SS_NO_CI_MODULE == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_no_ci_module);
                        } else if (ScreenSaverMode.DTV_SS_CI_PLUS_AUTHENTICATION == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_ci_plus_authentication);
                        } else if (ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_scrambled_program);
                        } else if (ScreenSaverMode.DTV_SS_UNSUPPORTED_FORMAT == mScreenSaverStatus) {
                            /*
                             *  FIXME: atsc screen saver status is separate by flag in supernova
                             *  enum of screen saver should be sync between each tv system.
                             */
                            if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                                mNoSignalView.setText(R.string.str_no_channel_banner);
                            } else {
                                mNoSignalView.setText(R.string.str_unsupported);
                            }
                        } else if (ScreenSaverMode.DTV_SS_CH_BLOCK == mScreenSaverStatus) {
                            mIsToPromptPassword = true;
                            mNoSignalView.setText(R.string.str_channel_block);
                        } else if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == mScreenSaverStatus) {
                            mIsToPromptPassword = true;
                            mNoSignalView.setText(R.string.str_parental_block);
                        } else if (ScreenSaverMode.DTV_SS_AUDIO_ONLY == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_audio_only);
                        } else if (ScreenSaverMode.DTV_SS_DATA_ONLY == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_data_only);
                        } else if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == mScreenSaverStatus) {
                            // Reset NoSignalView string to default string : No Signal
                            mNoSignalView.setText(R.string.str_no_signal);
                        } else if (ScreenSaverMode.DTV_SS_INVALID_PMT == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_invalid_pmt);
                        } else if (SCREENSAVER_DEFAULT_STATUS == mScreenSaverStatus){
                            /*
                             * Status fall into a default value case (SCREENSAVER_DEFAULT_STATUS)
                             * skip updating NoSignalView, it will be updated when ScreenSaverStatus
                             * be updated.
                             */
                            Log.i(TAG, "Default ScreenSaver status, wait screenSaverHandler updating NoSignalView.");
                            break;
                        } else {
                            Log.w(TAG, "Current Screen Saver Status is unrecognized");
                            Log.w(TAG, "status: " + mScreenSaverStatus);
                            break;
                        }
                        Log.d(TAG, "screen saver status is " + mScreenSaverStatus);
                        /* update NoSignalView Visibility by signal lock flag and screen saver status */
                        if (ScreenSaverMode.DTV_SS_COMMON_VIDEO != mScreenSaverStatus) {
                            mIsScreenSaverShown = true;
                            showNoSignalView(true);
                        } else {
                            mIsScreenSaverShown = false;
                            showNoSignalView(false);
                        }
                        break;
                    }
                    case TvCommonManager.INPUT_SOURCE_HDMI:
                    case TvCommonManager.INPUT_SOURCE_HDMI2:
                    case TvCommonManager.INPUT_SOURCE_HDMI3:
                    case TvCommonManager.INPUT_SOURCE_HDMI4:
                        if ((SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == mScreenSaverStatus)
                                || (SignalProgSyncStatus.UNSTABLE == mScreenSaverStatus)) {
                            mNoSignalView.setText(R.string.str_unsupported);
                            mIsScreenSaverShown = true;
                            showNoSignalView(true);
                        } else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == mScreenSaverStatus) {
                            mIsScreenSaverShown = false;
                            showNoSignalView(false);
                        }
                        startSourceInfo();
                        break;
                    case TvCommonManager.INPUT_SOURCE_VGA:
                        if ((SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == mScreenSaverStatus)
                                || (SignalProgSyncStatus.UNSTABLE == mScreenSaverStatus)) {
                            mNoSignalView.setText(R.string.str_unsupported);
                            mIsScreenSaverShown = true;
                            showNoSignalView(true);
                        } else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == mScreenSaverStatus) {
                            mIsScreenSaverShown = false;
                            showNoSignalView(false);
                            startSourceInfo();
                        } else if (SignalProgSyncStatus.AUTO_ADJUST == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_auto_adjust);
                            mIsScreenSaverShown = true;
                            showNoSignalView(true);
                            signalLockHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mIsScreenSaverShown = false;
                                    showNoSignalView(false);
                                    startSourceInfo();
                                }
                            }, 3000);
                        }
                        break;
                    case TvCommonManager.INPUT_SOURCE_CVBS:
                    case TvCommonManager.INPUT_SOURCE_CVBS2:
                    case TvCommonManager.INPUT_SOURCE_CVBS3:
                    case TvCommonManager.INPUT_SOURCE_CVBS4:
                        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                            boolean isSystemLocked = false;
                            isSystemLocked = TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus();
                            if (true == isSystemLocked) {
                                mNoSignalView.setText(R.string.str_parental_block);
                                mIsToPromptPassword = true;
                                mIsScreenSaverShown = true;
                                showNoSignalView(true);
                                startSourceInfo();
                            } else {
                                mIsToPromptPassword = false;
                                mIsScreenSaverShown = false;
                                showNoSignalView(false);
                                startSourceInfo();
                            }
                        } else {
                            mIsScreenSaverShown = false;
                            showNoSignalView(false);
                            startSourceInfo();
                        }
                        break;
                    case TvCommonManager.INPUT_SOURCE_YPBPR:
                    case TvCommonManager.INPUT_SOURCE_YPBPR2:
                    case TvCommonManager.INPUT_SOURCE_YPBPR3:
                        mIsScreenSaverShown = false;
                        showNoSignalView(false);
                        startSourceInfo();
                        break;
                    case TvCommonManager.INPUT_SOURCE_STORAGE:
                        mIsScreenSaverShown = false;
                        showNoSignalView(false);
                        break;
                    default :
                        mIsScreenSaverShown = false;
                        showNoSignalView(false);
                        startSourceInfo();
                        break;
                }
            } else {
            // signal unlock case
              /* zb20141016 del
              	switch (curInputSource) {
                    case TvCommonManager.INPUT_SOURCE_ATV:
                        // atv would not show nosignal text even signal unlock.
                        mIsScreenSaverShown = false;
                        showNoSignalView(false);
                        mIsSignalLock = true;
                        mAutoShutdownHandler.removeCallbacks(mShutdownTask);
                        return;
                    default:*/
                        showPasswordLock(false);
                        mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
                        mNoSignalView.setText(R.string.str_no_signal);
                        Log.d(TAG, "unlock show nosignal");
                        mIsScreenSaverShown = true;
                        showNoSignalView(true);
                        mAutoShutdownHandler.removeCallbacks(mShutdownTask);
                      //zb20141016 add
                        if(mTvCommonManager.getCurrentTvInputSource()==TvCommonManager.INPUT_SOURCE_VGA)
                        {
                        	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME_VGA);
                        }
                        else 
                        //end
                        {
                        	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME);
            			}
              //  }
            }
        };
    };

    /**
     * Used to handle scrren saver mode, decide if we need to show NoSignal Text.
     * Each inputsource will have itself situation.
     * Status can be referenced in two enum: EnumScreenMode, EnumSignalProgSyncStatus.
     */
    private Handler screenSaverHandler = new Handler() {
        public void handleMessage(Message msg) {
            int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
            int status = msg.arg1;

            // FIXME: check consistency between program lock status and activity visibility.
            // FIXME: activity should receive a event from tvservice to know program lock status changed.
            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                if (false == TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus()
                    && false == mTvChannelManager.getCurrentProgramInfo().isLock) {
                    mIsToPromptPassword = false;
                    showPasswordLock(false);
                }
            }

            switch (curInputSource) {
                case TvCommonManager.INPUT_SOURCE_ATV:
                case TvCommonManager.INPUT_SOURCE_DTV:
                    /* update NoSignalView string by screen saver status */
                    if (ScreenSaverMode.DTV_SS_INVALID_SERVICE == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_invalid_service);
                    } else if (ScreenSaverMode.DTV_SS_NO_CI_MODULE == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_no_ci_module);
                    } else if (ScreenSaverMode.DTV_SS_CI_PLUS_AUTHENTICATION == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_ci_plus_authentication);
                    } else if (ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_scrambled_program);
                    } else if (ScreenSaverMode.DTV_SS_UNSUPPORTED_FORMAT == status) {
                        /*
                         *  FIXME: atsc screen saver status is separate by flag in supernova
                         *  enum of screen saver should be sync between each tv system.
                         */
                        mIsScreenSaverShown = true;
                        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                            mNoSignalView.setText(R.string.str_no_channel_banner);
                        } else {
                            mNoSignalView.setText(R.string.str_unsupported);
                        }
                    } else if (ScreenSaverMode.DTV_SS_CH_BLOCK == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_channel_block);
                        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                            if (true == TvAtscChannelManager.getInstance().getCurrentProgramInfo().isLock) {
                                mIsToPromptPassword = true;
                                if (true == mIsActive) {
                                    showPasswordLock(true);
                                }
                            }
                        } else {
                            if (true == isTopActivity(RootActivity.this.getClass().getName())) {
                                if (null == CheckParentalPwdActivity.instance) {
                                    if (TvParentalControlManager.getInstance().isSystemLock()) {
                                        Intent intent = new Intent();
                                        intent.setClass(RootActivity.this, CheckParentalPwdActivity.class);
                                        intent.putExtra("list", 4);
                                        startActivityForResult(intent, 100);
                                    } else {
                                        TvParentalControlManager.getInstance().unlockChannel();
                                    }
                                }
                            }
                        }
                    } else if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_parental_block);
                        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                            mIsToPromptPassword = true;
                            if (true == mIsActive) {
                                showPasswordLock(true);
                            }
                        } else {
                            if (true == isTopActivity(RootActivity.this.getClass().getName())) {
                                if (null == CheckParentalPwdActivity.instance) {
                                    Log.i(TAG, "isSystemLock = " + TvParentalControlManager.getInstance().isSystemLock());
                                    if (TvParentalControlManager.getInstance().isSystemLock()) {
                                        Intent intent = new Intent();
                                        intent.setClass(RootActivity.this, CheckParentalPwdActivity.class);
                                        intent.putExtra("list", 4);
                                        startActivityForResult(intent, 100);
                                    } else {
                                        TvParentalControlManager.getInstance().unlockChannel();
                                    }
                                }
                            }
                        }
                    } else if (ScreenSaverMode.DTV_SS_AUDIO_ONLY == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_audio_only);
                    } else if (ScreenSaverMode.DTV_SS_DATA_ONLY == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_data_only);
                    } else if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == status) {
                        // Reset NoSignalView string to default string : No Signal
                        mIsScreenSaverShown = false;
                        mNoSignalView.setText(R.string.str_no_signal);
                    } else if (ScreenSaverMode.DTV_SS_INVALID_PMT == status) {
                        mIsScreenSaverShown = true;
                        mNoSignalView.setText(R.string.str_invalid_pmt);
                    }
                    break;
                case TvCommonManager.INPUT_SOURCE_VGA:
                    if (SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == status) {
                        mNoSignalView.setText(R.string.str_unsupported);
                        mIsScreenSaverShown = true;
                        showNoSignalView(true);
                    } else if (SignalProgSyncStatus.AUTO_ADJUST == status) {
                        mIsScreenSaverShown = true;
                        showNoSignalView(true);
                        screenSaverHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mIsScreenSaverShown = false;
                                showNoSignalView(false);
                                startSourceInfo();
                            }
                        }, 3000);
                    } else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == status) {
                        mIsScreenSaverShown = false;
                        showNoSignalView(false);
                        startSourceInfo();
                    }
                    break;
                case TvCommonManager.INPUT_SOURCE_CVBS:
                case TvCommonManager.INPUT_SOURCE_CVBS2:
                case TvCommonManager.INPUT_SOURCE_CVBS3:
                case TvCommonManager.INPUT_SOURCE_CVBS4:
                        if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_parental_block);
                            if (true == mIsActive) {
                                showPasswordLock(true);
                            }
                            mIsScreenSaverShown = true;
                            showNoSignalView(true);
                        } else if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == mScreenSaverStatus) {
                            mNoSignalView.setText(R.string.str_no_signal);
                            showPasswordLock(false);
                            mIsScreenSaverShown = false;
                            showNoSignalView(false);
                        }
                    break;
                case TvCommonManager.INPUT_SOURCE_YPBPR:
                case TvCommonManager.INPUT_SOURCE_YPBPR2:
                case TvCommonManager.INPUT_SOURCE_YPBPR3:
                    break;
                case TvCommonManager.INPUT_SOURCE_HDMI:
                case TvCommonManager.INPUT_SOURCE_HDMI2:
                case TvCommonManager.INPUT_SOURCE_HDMI3:
                case TvCommonManager.INPUT_SOURCE_HDMI4:
                    if ((SignalProgSyncStatus.UNSTABLE == status)
                        || (SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == status)) {
                        mIsScreenSaverShown = true;
                        showNoSignalView(true);
                    } else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == status) {
                        mIsScreenSaverShown = false;
                        showNoSignalView(false);
                    }
                    break;
                default:
                    break;
            }

            startNoSignal();
        };
    };

    private void tvApkExitHandler() {
        mIsActive = false;
        bCmd_TvApkExit = true;
        mIsBackKeyPressed = true;
        showExitDialog();
        return;
    }

    private class CecCtrlEventListener implements OnCecCtrlEventListener {
        @Override
        public boolean onCecCtrlEvent(int what) {
            switch (what) {
                case TvCecManager.TVCEC_STANDBY: {
                    Log.i(TAG, "&&&&&&&&&&______________EV_CEC_STANDBY");
                    TvCommonManager.getInstance().standbySystem("cec");
                }
                    break;
                default: {
                    Log.i(TAG, "Unknown message type " + what);
                }
                    break;
            }
            return true;
        }
    }

    protected class UiEventListener implements OnUiEventListener {
        @Override
        public boolean onUiEvent(int what) {
            switch (what) {
                case TvCiManager.TVCI_UI_DATA_READY: {
                    if (TvCommonManager.INPUT_SOURCE_STORAGE != mTvCommonManager.getCurrentTvInputSource()) {
                        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                        String foreGroundActivity = am.getRunningTasks(1).get(0).topActivity.getClassName();
                        if (foreGroundActivity.equals(CimmiActivity.class.getName()) == false) {
                            Intent intent = new Intent(RootActivity.this, CimmiActivity.class);
                            RootActivity.this.startActivity(intent);
                        }
                    }
                } break;
                case TvCiManager.TVCI_UI_CARD_INSERTED: {
                    Toast toast = Toast.makeText(RootActivity.this,
                            R.string.str_cimmi_hint_ci_inserted, 5);
                    toast.show();
                } break;
                case TvCiManager.TVCI_UI_CARD_REMOVED: {
                    Toast toast = Toast.makeText(RootActivity.this,
                            R.string.str_cimmi_hint_ci_removed,5);
                    toast.show();
                    if (mScreenSaverStatus == ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM) {
                        mScreenSaverStatus = ScreenSaverMode.DTV_SS_NO_CI_MODULE;
                        sendBroadcast(new Intent(TvIntent.ACTION_REDRAW_NOSIGNAL));
                    }
                } break;
                default: {
                } break;
            }
            return true;
        }
    }

    private class CiStatusChangeEventListener implements OnCiStatusChangeEventListener {
        @Override
        public boolean onCiStatusChanged(int what, int arg1, int arg2) {
            Log.i(TAG, "onCiStatusChanged(), what:" + what);
            switch (what) {
                case TvCiManager.TVCI_STATUS_CHANGE_TUNER_OCCUPIED: {
                    switch (arg1) {
                        case TvCiManager.CI_NOTIFY_CU_IS_PROGRESS: {
                            openNotifyMessage(getString(R.string.str_cam_upgrade_alarm));
                        } break;
                        case TvCiManager.CI_NOTIFY_OP_IS_TUNING: {
                            openNotifyMessage(getString(R.string.str_op_tuning_alarm));
                        } break;
                        default: {
                            Log.d(TAG, "Unknown CI occupied status, arg1 = " + arg1);
                        } break;
                    }
                    Log.i(TAG,"sendBroadcast CIPLUS_TUNER_UNAVAIABLE intent: status = " + arg1);
                    Intent intent = new Intent(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
                    intent.putExtra(Constant.TUNER_AVAIABLE, false);
                    sendBroadcast(intent);
                } break;
                default : {
                } break;

            }
            return true;
        }
    }

    Thread t_atv = null;

    Thread t_dtv = null;

    private void startThread(boolean type) {
        if (type) {
            // atv
            if (t_atv == null) {
                t_atv = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTvChannelManager.changeToFirstService(
                                TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                                TvChannelManager.FIRST_SERVICE_DEFAULT);
                        t_atv = null;
                    }
                });
                t_atv.start();
            }
        } else {
            if (t_dtv == null) {
                // dtv
                t_dtv = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTvChannelManager.changeToFirstService(
                                TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV,
                                TvChannelManager.FIRST_SERVICE_DEFAULT);
                        t_dtv = null;
                    }
                });
                t_dtv.start();
            }
        }
    }

    private void startNoSignal() {
/*        Log.d(TAG, "mIsSignalLock: " + mIsSignalLock
                + " mIsScreenSaverShown: " + mIsScreenSaverShown);*/

        if (mIsSignalLock) {
            mAutoShutdownHandler.removeCallbacks(mShutdownTask);

            if (mIsScreenSaverShown) {
                showNoSignalView(true);
            } else {
                showNoSignalView(false);
            }
        } else {
//            mAutoShutdownHandler.removeCallbacks(mShutdownTask);
//          //zb20141016 add
//            if(mTvCommonManager.getCurrentTvInputSource()==TvCommonManager.INPUT_SOURCE_VGA)
//            {
//            	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME_VGA);
//            }
//            else 
//            //end
//            {
//            	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME);
//			}
            showNoSignalView(true);
        }
    }

    private void startSourceChange(final int inputsource) {
        new Thread(new Runnable() {
        @Override
        public void run() {
                TvCommonManager.getInstance().setInputSource(inputsource);
                if (inputsource == TvCommonManager.INPUT_SOURCE_ATV) {
                    if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ISDB) {
                        TvIsdbChannelManager.getInstance().genMixProgList(false);
                    }
                    int curChannelNumber = TvChannelManager.getInstance().getCurrentChannelNumber();
                    if (curChannelNumber > 0xFF) {
                        curChannelNumber = 0;
                    }
                    TvChannelManager.getInstance().setAtvChannel(curChannelNumber);
                } else if (inputsource == TvCommonManager.INPUT_SOURCE_DTV) {
                    if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ISDB) {
                        TvIsdbChannelManager.getInstance().setAntennaType(TvIsdbChannelManager.DTV_ANTENNA_TYPE_AIR);
                    }
                    TvChannelManager.getInstance().playDtvCurrentProgram();
                }
            }
        }).start();
    }

    /*
     * FIXME:
     * By Android Api Guide, getRunningTasks should not be used in our code's core section.
     * It is only using for debugging.
     * But we do not have a better method to determinant whether RootActivity is in top or not,
     * So we used this method temporarily.
     */
    private boolean isTopActivity(String className) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = manager.getRunningTasks(1).get(0).topActivity;
        String topActivityName = cn.getClassName();
        return topActivityName.equals(className);
    }

    /**
     * get back key status in root activity
     * @return boolean , true is back key pressed.
     */
    public static boolean getBackKeyStatus() {
        return mIsBackKeyPressed;
    }

    /**
     * get root activity status
     * @return boolean , true means root activity is active now.
     */
    public static boolean getActiveStatus() {
        return mIsActive;
    }

    /**
     * Add API when system is reboot.
     *
     * @author gerard.jiang
     * @serialData 2013/04/28
     * @param flag
     */
    public static void setRebootFlag(boolean flag) {
        isReboot = flag;
    }

    /**
     * Show the exit dialog.
     *
     * @author gerard.jiang
     * @param aDialog
     */
    private void showExitDialog() {
        // ***add by allen.sun 2013-5-27
        // Adaptation different resolutions in STB
        if (Tools.isBox()) {
            Log.i(TAG, "start com.mstar.pipservice");
            Intent pipIntent = new Intent("com.mstar.pipservice");
            pipIntent.putExtra("cmd", "invisible");
            RootActivity.this.startService(pipIntent);
        }
        // ***and end
        mExitDialog.setOwnerActivity(rootActivity);
        mExitDialog.show();
    }

    /**
     * Open SourceInfoActivity after checking program lock.
     * if input source is not tv, this function works as starting source info activity.
     */
    private void startSourceInfo() {
        boolean isSystemLocked = false;
        boolean isCurrentProgramLocked = false;
        int curInput = TvCommonManager.getInstance().getCurrentTvInputSource();
        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
            isSystemLocked = TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus();
        } else {
            isSystemLocked = TvParentalControlManager.getInstance().isSystemLock();
        }
        if ((TvCommonManager.INPUT_SOURCE_ATV == curInput)
                || (TvCommonManager.INPUT_SOURCE_DTV == curInput)) {
            isCurrentProgramLocked = mTvChannelManager.getCurrentProgramInfo().isLock;
        }
        if (!(isSystemLocked && isCurrentProgramLocked)) {
            /*
             * when RootActivity is not running,
             * we don't start activity to interrupt other menu,
             * so we send SIGNAL_LOCK action to source info for updating content
             * if SourceInfoActivity is alive, its BoradcastReceiver will handle this event.
             */
            if (false == isTopActivity(RootActivity.class.getName())) {
                sendBroadcast(new Intent(TvIntent.ACTION_SIGNAL_LOCK));
            } else {
                Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
                startActivity(intent);
            }
        }
    }

    private void displayOpRefreshconfirmation() {
        this.runOnUiThread( new Runnable() {
            public void run() {
                mCiPlusOPRefreshDialog.show();
            }
        });
    }

    private void showNoSignalView(boolean isShown) {
       /* Log.d(TAG, "show nosignal view :" + isShown);*/
        if (isShown) {
            if (TextView.INVISIBLE == mNoSignalView.getVisibility()) {
                mNoSignalView.setVisibility(TextView.VISIBLE);
            }
        } else {
            if (TextView.VISIBLE == mNoSignalView.getVisibility()) {
                mNoSignalView.setVisibility(TextView.INVISIBLE);
            }
        }
    }

    private boolean sendCecKey(int keyCode) {
         CecSetting setting = mTvCecManager.getCecConfiguration();
         int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
         if (setting.cecStatus == mCecStatusOn) {
            if (curInputSource == TvCommonManager.INPUT_SOURCE_HDMI ||
                curInputSource == TvCommonManager.INPUT_SOURCE_HDMI2 ||
                curInputSource == TvCommonManager.INPUT_SOURCE_HDMI3 ||
                curInputSource == TvCommonManager.INPUT_SOURCE_HDMI4 ) {
                if (TvCommonManager.getInstance().isHdmiSignalMode() == true) {
                    if (mTvCecManager.sendCecKey(keyCode)) {
                        Log.d(TAG, "send Cec key,keyCode is " + keyCode + ", tv don't handl the key");
                        return true;
                    }
                }
             } 
		//for volume keys,move to PhoneWindow.java for responding under all conditions-lxk 20141110
		/*else if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV
                     || curInputSource == TvCommonManager.INPUT_SOURCE_ATV
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS2
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS3
                     || curInputSource == TvCommonManager.INPUT_SOURCE_CVBS4
                     || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR
                     || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR2
                     || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR3) {
                 if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
                         || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                         || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
                     if (mTvCecManager.sendCecKey(keyCode)) {
                         Log.d(TAG, "send Cec key,keyCode is " + keyCode
                                 + ", tv don't handl the key");
                         return true;
                     }
                 }
             }*///end 1110
         }
       return false;
    }


    private boolean sendGingaKey(int keyCode, KeyEvent event) {
        final boolean down = event.getAction() == KeyEvent.ACTION_DOWN;

        if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
            if (TvGingaManager.getInstance().isGingaRunning()) {
                if (down) {
                    if (TvGingaManager.getInstance().processKey(keyCode, true)) {
                        Log.i(TAG, "onKeyDown GingaStatusMode:processKey");
                        return true;
                    }
                } else {
                    if (TvGingaManager.getInstance().processKey(keyCode, false)) {
                        Log.i(TAG, "onKeyUp GingaStatusMode:processKey");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean sendHbbTVKey(int keyCode) {
        if (TvCommonManager.getInstance().getCurrentTvSystem() <= TvCommonManager.TV_SYSTEM_DTMB &&
                TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
            if (TvHbbTVManager.getInstance().isHbbTVEnabled()) {
                if (TvHbbTVManager.getInstance().hbbtvKeyHandler(keyCode)) {
                    Log.i(TAG, "onKeyDown HbbTV:sendHbbTVKey");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sendMheg5Key(int keyCode) {
        boolean ret = false;
        try {
            if (TvManager.getInstance().getPlayerManager().isMheg5Running()) {
                ret = TvManager.getInstance().getPlayerManager().sendMheg5Key(keyCode);
            } else {
                Log.i(TAG, "isMheg5Running return fali!");
            }

        } catch (TvCommonException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void updateScreenSaver() {

        /*
         * FIXME: This function is called only when tv first boot up.
         * FIXME: because unlock event maybe post before tvapk is ready.
         * FIXME: so we use this function to draw nosignal when first boot up.
         */
        Boolean isPowerOn = getIntent() != null ? getIntent().getBooleanExtra("isPowerOn", false)
                : false;
        if (!isPowerOn) {
            Log.d(TAG, "only use updateScreenSaver to update NoSignalTextView when first boot up");
            return;
        }
        /**
         * if apk is not exiting or need to update screen saver,
         * send screen saver status or signal lock status to handler for updating screen.
         */
        int curInputSource = mTvCommonManager.getCurrentTvInputSource();
        int curSubInputSource = mTvCommonManager.getCurrentSubInputSource().ordinal();
        mIsSignalLock = mTvCommonManager.isSignalStable(curInputSource);

        if (true == checkChannelBlockStatus(curInputSource)) {
            return;
        }

        if (!mIsSignalLock && !bCmd_TvApkExit) {
            boolean bSubPopSignalLock = false;
            if (TvPipPopManager.getInstance() != null) {
                if (TvPipPopManager.getInstance().isPipModeEnabled()) {
                    if (TvPipPopManager.getInstance().getPipMode() == EnumPipModes.E_PIP_MODE_POP) {
                        if (mTvCommonManager.isSignalStable(curSubInputSource)) {
                            bSubPopSignalLock = true;
                        }
                    }
                }
            }
            /**
             * Send Signal Unlock to signalLock Handler
             * if sub inputsource is signal stabled and inputsource is not changing now.
             */
            if (!bSubPopSignalLock && !TVRootApp.getSourceDirty()) {
                Message msgLock = Message.obtain();
                msgLock.arg1 = TvEvent.SIGNAL_UNLOCK;
                signalLockHandler.sendMessage(msgLock);
            }
        }
        /**
         * update screen saver status to screenSaverHandler
         * if screen saver need to show and apk is not exiting.
         */
        if (mIsScreenSaverShown && !bCmd_TvApkExit && !mIsBackKeyPressed) {
            Message msgSaver = Message.obtain();
            msgSaver.arg1 = mScreenSaverStatus;
            msgSaver.arg2 = curInputSource;
            Log.d(TAG, "update screen saver source :" + curInputSource + " status :" + msgSaver.arg1);
            screenSaverHandler.sendMessage(msgSaver);
        }
    }

    private boolean checkChannelBlockStatus(int curInputSource) {
        boolean bBlocking = false;
        Message msgSaver_lock = null;

        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            if (true == TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus()) {
                msgSaver_lock = Message.obtain();
                msgSaver_lock.arg2 = curInputSource;
                msgSaver_lock.arg1 = ScreenSaverMode.DTV_SS_PARENTAL_BLOCK;
                screenSaverHandler.sendMessage(msgSaver_lock);
                bBlocking = true;
            } else if (true == mTvChannelManager.getCurrentProgramInfo().isLock) {
                msgSaver_lock = Message.obtain();
                msgSaver_lock.arg2 = curInputSource;
                msgSaver_lock.arg1 = ScreenSaverMode.DTV_SS_CH_BLOCK;
                screenSaverHandler.sendMessage(msgSaver_lock);
                bBlocking = true;
            }
        }
        return bBlocking;
    }

    private Runnable mShutdownTask = new Runnable() {
        @Override
        public void run() {
            if (TvFactoryManager.getInstance().isNoSignalAutoShutdownEnable()&& SystemProperties.getInt("persist.sys.aging",0)==0) {
                Log.i(TAG, "=================NO SIGNAL STANDBY===============");
              //zb20141016 add
                Intent intent=new Intent("com.android.server.tv.TIME_EVENT_LAST_MINUTE_WARN");
                intent.putExtra("NoSignalSleep", true);
                RootActivity.this.sendBroadcast(intent);
                
//                int inputSource = mTvCommonManager.getCurrentTvInputSource();
//        		if (inputSource == TvCommonManager.INPUT_SOURCE_VGA
//              || inputSource == TvCommonManager.INPUT_SOURCE_HDMI
//              || inputSource == TvCommonManager.INPUT_SOURCE_HDMI2
//              || inputSource == TvCommonManager.INPUT_SOURCE_HDMI3
//              || inputSource == TvCommonManager.INPUT_SOURCE_HDMI4) {
//          		mTvCommonManager.setDpmsWakeUpEnable(true);
//      		}
//      		mTvCommonManager.standbySystem("standby");
                //end
            } else {
            	//zb20141016 add
                if(mTvCommonManager.getCurrentTvInputSource()==TvCommonManager.INPUT_SOURCE_VGA)
                {
                	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME_VGA);
                }
                else 
                //end
                {
                	mAutoShutdownHandler.postDelayed(mShutdownTask,NO_SIGNAL_SHUTDOWN_TIME);
    			}
            }
        }
    };

    private void oadDownloadConfirm() {
        AlertDialog.Builder build = new AlertDialog.Builder(RootActivity.this);
        int country = TvChannelManager.getInstance().getSystemCountryId();
        if (country == TvCountry.AUSTRALIA || country == TvCountry.NEWZEALAND) {
            build.setMessage(R.string.str_oad_msg_dowload_prompt_nz);
        } else {
            build.setMessage(this.getString(R.string.str_oad_msg_download_found) + "\n"
                    + this.getString(R.string.str_oad_msg_dowload_prompt));
        }
        build.setPositiveButton(R.string.str_oad_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               TvOadManager.getInstance().startOad();
                Intent intent = new Intent(RootActivity.this, OadDownload.class);
                RootActivity.this.startActivity(intent);
            }
        });
        build.setNegativeButton(R.string.str_oad_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               TvOadManager.getInstance().stopOad(true);
               TvOadManager.getInstance().resetOad();
            }
        });
        build.create().show();
    }

    private void oadScanConfirm() {
        AlertDialog.Builder build = new AlertDialog.Builder(RootActivity.this);
        build.setMessage(R.string.str_oad_msg_scan_confirm);
        build.setPositiveButton(R.string.str_oad_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TvOadManager.getInstance().resetOad();
                Intent intent = new Intent(RootActivity.this, OadScan.class);
                RootActivity.this.startActivity(intent);
            }
        });
        build.setNegativeButton(R.string.str_oad_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        build.create().show();
    }

    private void showPasswordLock(boolean bShow) {
        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            if (true == bShow) {
                mPasswordLock.show();
            } else {
                mPasswordLock.dismiss();
            }
        }
    }

    private void openNotifyMessage(final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run(){
                Toast.makeText(RootActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
