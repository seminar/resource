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

package com.mstar.tv.tvplayer.ui.pvr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TVRootApp;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.graphics.Color;

import com.mstar.android.MKeyEvent;
import com.mstar.android.storage.MStorageManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvEpgManager;
import com.mstar.android.tvapi.common.PvrManager;
import com.mstar.android.tvapi.common.PvrManager.OnPvrEventListener;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tvapi.common.vo.CaptureThumbnailResult;
import com.mstar.android.tvapi.common.vo.EnumPvrStatus;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.PvrPlaybackSpeed.EnumPvrPlaybackSpeed;
import com.mstar.android.tvapi.dtv.vo.DtvAudioInfo;
import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;
import com.mstar.android.tvapi.dtv.vo.EpgEventInfo;
import com.mstar.tv.tvplayer.ui.dtv.AudioLanguageActivity;
import com.mstar.tv.tvplayer.ui.dtv.SubtitleLanguageActivity;
import com.mstar.tv.tvplayer.ui.pvr.USBDiskSelecter.usbListener;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;

public class PVRActivity extends MstarBaseActivity implements OnPvrEventListener {

    public static boolean isPVRActivityActive = false;

    public enum PVR_MODE {
        // /pvr mode none
        E_PVR_MODE_NONE,
        // /pvr mode record
        E_PVR_MODE_RECORD,
        // /pvr mode playback
        E_PVR_MODE_PLAYBACK,
        // /pvr mode time shift
        E_PVR_MODE_TIME_SHIFT,
        // /pvr mode always time shift
        E_PVR_MODE_ALWAYS_TIME_SHIFT,
        // /pvr mode file browser
        E_PVR_MODE_FILE_BROWSER,
        // /pvr mode short
        E_PVR_MODE_SHORT,
    }

    public enum PVR_AB_LOOP_STATUS {
        // /pvr ab loop not set
        E_PVR_AB_LOOP_STATUS_NONE,
        // /pvr ab loop set a position
        E_PVR_AB_LOOP_STATUS_A,
        // /pvr ab loop set b position
        E_PVR_AB_LOOP_STATUS_AB,
    }

    public static int currentRecordingProgrammFrency = -1;

    private static final int INVALID_TIME = 0xFFFFFFFF;

    private static final int SKIP_TIME_INSEC = 30;

    private PVR_AB_LOOP_STATUS setPvrABLoop = PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_NONE;

    private int pvrABLoopStartTime = INVALID_TIME;

    private int pvrABLoopEndTime = INVALID_TIME;

    private PVR_MODE curPvrMode = PVR_MODE.E_PVR_MODE_NONE;

    private final int savingProgress = 0;

    private final int timeChoose = 1;

    private Handler handler = new Handler();

    private boolean isMenuHide = false;

    private boolean isBrowserCalled = false;

    private boolean isOneTouchPlayMode = false;

    private boolean isOneTouchPauseMode = false;

    private boolean isWatchRcodFilInRcoding = false;

    private PVRImageFlag pvrImageFlag = null;

    private RelativeLayout rootView = null;

    private RelativeLayout recordingView = null;

    private ImageButton recorder = null;

    private ImageButton play = null;

    private ImageButton stop = null;

    private ImageButton pause = null;

    private ImageButton rev = null;

    private ImageButton ff = null;

    private ImageButton slow = null;

    private ImageButton time = null;

    private ImageButton backward = null;

    private ImageButton forward = null;

    // private ImageButton capture = null;
    private TextView serviceNameText = null;

    private TextView eventNameText = null;

    private TextView totalRecordTime = null;

    private TextView usbLabel = null;

    private TextView usbPercentage = null;

    private Activity activity = null;

    private String recordDiskPath = null;

    private String recordDiskLable = null;

    private USBDiskSelecter usbSelecter = null;

    private AnimatorSet menuShowAnimation;

    private AnimatorSet menuHideAnimation;

    private AnimatorSet recordIconAnimation;

    private AlertDialog timeChooser = null;

    private PVRThumbnail thumbnail = null;

    private TextProgressBar RPProgress = null;

    private ProgressBar usbFreeSpace = null;

    private Button resetJump2Timebtn = null;

    /* For prompt alert dialog to notify user */
    private static Dialog stopRecordDialog = null;

    private KeyEvent tPreviousEvent;

    private static final String TAG = "PVRActivity";

    // ham
    private TextView textViewPlay = null;

    // private ImageView imgViewLoopAB = null;
    private ProgressBar progress_loopab = null;

    private int A_progress = 0;

    private android.widget.RelativeLayout.LayoutParams lp4LoopAB;

    private int looptime;

    private int currentlooptime;

    private TextView playSpeed;

    private int subtitlePosLive = -1;

    private short audioLangPosLive = -1;

    private TvChannelManager mTvChannelManager = null;

    private TvEpgManager mTvEpgManager = null;

    private TvPvrManager mPvrManager = null;

    private Toast mCcKeyToast;

    private static final String NO_DISK = "NO_DISK";

    private static final String CHOOSE_DISK = "CHOOSE_DISK";

    private final String FAT = "FAT";

    private final String NTFS = "NTFS";

    private void findView() {
        rootView = (RelativeLayout) findViewById(R.id.pvrrootmenu);
        recordingView = (RelativeLayout) findViewById(R.id.pvrisrecording);
        recorder = (ImageButton) findViewById(R.id.player_recorder);
        play = (ImageButton) findViewById(R.id.player_play);
        stop = (ImageButton) findViewById(R.id.player_stop);
        pause = (ImageButton) findViewById(R.id.player_pause);
        rev = (ImageButton) findViewById(R.id.player_rev);
        ff = (ImageButton) findViewById(R.id.player_ff);
        slow = (ImageButton) findViewById(R.id.player_slow);
        time = (ImageButton) findViewById(R.id.player_time);
        backward = (ImageButton) findViewById(R.id.player_backward);
        forward = (ImageButton) findViewById(R.id.player_forward);
        serviceNameText = (TextView) findViewById(R.id.textView1);
        eventNameText = (TextView) findViewById(R.id.textView2);
        totalRecordTime = (TextView) findViewById(R.id.record_time);
        totalRecordTime.setText("00:00:00");
        playSpeed = (TextView) findViewById(R.id.play_speed);
        playSpeed.setVisibility(View.GONE);
        usbLabel = (TextView) findViewById(R.id.usbLabelName);
        usbPercentage = (TextView) findViewById(R.id.usbFreeSpacePercent);
        RPProgress = (TextProgressBar) findViewById(R.id.play_record_progress);
        usbFreeSpace = (ProgressBar) findViewById(R.id.usbFreeSpace);
        usbFreeSpace.setMax(100);
        textViewPlay = (TextView) findViewById(R.id.text_view_player_play);
        progress_loopab = (ProgressBar) findViewById(R.id.progressbar_loopab);
        lp4LoopAB = new android.widget.RelativeLayout.LayoutParams(0, dip2px(5));
        lp4LoopAB.topMargin = 5;

        recorder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                createAnimation();
                onKeyRecord();
            }
        });
        play.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyPlay();
            }
        });
        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setImageResource(R.drawable.player_stop_focus);
                pvrImageFlag.setStopFlag(true);
                onKeyStop();
            }
        });
        pause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyPause();
            }
        });
        rev.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyRev();
                rev.setImageResource(R.drawable.player_rev_focus);
                pvrImageFlag.setRevFlag(true);
            }
        });
        ff.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyFF();
                ff.setImageResource(R.drawable.player_ff_focus);
                pvrImageFlag.setFfFlag(true);
            }
        });
        slow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeySlowMotion();
                slow.setImageResource(R.drawable.player_slow_focus);
                pvrImageFlag.setSlowFlag(true);
            }
        });
        time.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyGoToTime();
                time.setImageResource(R.drawable.player_time_focus);
                pvrImageFlag.setTimeFlag(true);
            }
        });
        backward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyBackward();
                backward.setImageResource(R.drawable.player_backward_focus);
                pvrImageFlag.setBackwardFlag(true);
            }
        });
        forward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onKeyForward();
                forward.setImageResource(R.drawable.player_forward_focus);
                pvrImageFlag.setFowardFlag(true);
            }
        });

    }

    private void settingServiceEventName() {
        ProgramInfo curProgInfo = null;
        EpgEventInfo epgEventInfo = new EpgEventInfo();
        String serviceNumberAndNameStr = null;
        if(TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ISDB) {
            curProgInfo = TvIsdbChannelManager.getInstance().getCurrentProgramInfo();
            serviceNumberAndNameStr = "CH" + curProgInfo.majorNum + "." + curProgInfo.minorNum+ " " + curProgInfo.serviceName;
        } else {
            curProgInfo = mTvChannelManager.getCurrentProgramInfo();
            serviceNumberAndNameStr = "CH" + curProgInfo.number + " " + curProgInfo.serviceName;
        }
        serviceNameText.setText(serviceNumberAndNameStr);
        Time curTime = new Time();
        curTime.setToNow();
        curTime.toMillis(true);
        epgEventInfo = mTvEpgManager.getDvbEventInfoByTime(curProgInfo.serviceType,
                    curProgInfo.number, curTime);
        if ((epgEventInfo != null) && (epgEventInfo.name != null)) {
            eventNameText.setText(epgEventInfo.name);
        } else {
            eventNameText.setText("");
        }
    }


    private void settingUSB() {
        usbSelecter = new USBDiskSelecter(activity) {

            @Override
            public void onItemChosen(int position, String diskLabel, String diskPath) {
                boolean isRecordSuccess = false;
                recordDiskPath = diskPath;
                recordDiskLable = diskLabel;
                usbLabel.setText(diskLabel);
                Log.d(TAG, "=============>>>>> current Selected Disk = " + recordDiskPath);
                Log.e(TAG, "==============>>>>> current selected DiskLabel=" + recordDiskLable);
                if (isOneTouchPauseMode) {
                    isRecordSuccess = doPVRTimeShift(true,mPvrManager.isRecording(),mPvrManager.isRecordPaused());
                } else {
                    if (recordDiskLable.regionMatches(6, FAT, 0, 3)) {
                        isRecordSuccess = doPVRRecord(true,mPvrManager.isRecording(),mPvrManager.isRecordPaused());
                    } else {
                        Toast.makeText(activity, R.string.str_pvr_unsurpt_flsystem, Toast.LENGTH_SHORT).show();
                    }
                }
                if (!isRecordSuccess) {
                    finish();
                }
            }
        };
        usbSelecter.setUSBListener(new usbListener() {

            @Override
            public void onUSBUnmounted(String diskPath) {
            }

            @Override
            public void onUSBMounted(String diskPath) {
            }

            @Override
            public void onUSBEject(String diskPath) {
                Log.d(TAG, "=============>>>>> onUSBEject");
                if (recordDiskPath == null || !recordDiskPath.equals(diskPath))
                    return;
                stopPlaybacking();
                mPvrManager.stopRecord();
                finish();
                if (isBootedByRecord()) {
                    standbySystem();
                }
            }
        });
    }

    private void settingThumbnail() {
        thumbnail = new PVRThumbnail(this) {

            @Override
            void onItemClicked(int position) {
                mPvrManager.jumpToThumbnail(position);
            }
        };
        ((RelativeLayout) findViewById(R.id.thumbnailRoot)).addView(thumbnail);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        thumbnail.setLayoutParams(lp);
        thumbnail.Show(false);
        /*
         * capture.setOnClickListener(new OnClickListener() {
         * @Override public void onClick(View v) { try { onKeyCapture(); } catch
         * (TvCommonException e) { e.printStackTrace(); }
         * capture.setImageResource(R.drawable.player_capture_focus);
         * pvrImageFlag.setCaptureFlag(true); } });
         */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pvr_menu);
        alwaysTimeout = true;
        activity = this;
        isPVRActivityActive = true;
        mTvChannelManager = TvChannelManager.getInstance();
        mTvEpgManager = TvEpgManager.getInstance();
        mPvrManager = TvPvrManager.getInstance();
        mPvrManager.registerOnPvrEventListener(this);
        pvrImageFlag = new PVRImageFlag();
        pvrImageFlag.setPauseFlag(true);
        pvrABLoopStartTime = INVALID_TIME;
        pvrABLoopEndTime = INVALID_TIME;
        setPvrABLoop = PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_NONE;
        findView();
        settingThumbnail();
        settingUSB();

        if (getIntent().getExtras() != null) {
            isBrowserCalled = getIntent().getExtras().getBoolean("FullPageBrowserCall", false);
            isOneTouchPlayMode = (getIntent().getExtras().getInt("PVR_ONE_TOUCH_MODE") == 2);
            isOneTouchPauseMode = (getIntent().getExtras().getInt("PVR_ONE_TOUCH_MODE") == 3);
            if (isBrowserCalled || isOneTouchPlayMode) {
                findViewById(R.id.player_recorder_icon).setVisibility(View.GONE);
                findViewById(R.id.usbInfoLayout).setVisibility(View.GONE);
                pvrImageFlag.setPauseFlag(false);
                curPvrMode = PVR_MODE.E_PVR_MODE_PLAYBACK;
            } else if (isOneTouchPauseMode) {
                findViewById(R.id.player_recorder_icon).setVisibility(View.GONE);
                if(true == mPvrManager.isAlwaysTimeShiftRecording()) {
                    curPvrMode = PVR_MODE.E_PVR_MODE_ALWAYS_TIME_SHIFT;
                } else {
                    // Do PVR TimeShift!!!!!
                    curPvrMode = PVR_MODE.E_PVR_MODE_TIME_SHIFT;
                }
            } else {
                // set pvr bar status
                setBarStatusOfStartRecord();
            }
        } else {
            // set pvr bar status
            setBarStatusOfStartRecord();
        }

        Log.d(TAG, "onCreate curPvrMode:" + curPvrMode);

        if (isBrowserCalled) {
            play.performClick();
            final int total = mPvrManager.getRecordedFileDurationTime(mPvrManager.getCurPlaybackingFileName());
            RPProgress.setMax(total);
            totalRecordTime.setText(getTimeString(total));
            /*
             * to avoid request focus on stop or other image button , give
             * the request to invisible image button when the first focus is
             * on the stop button, when press the enter in pvr brower page,
             * it will perform button onclick
             */
            recorder.setEnabled(false);
            recorder.requestFocus();
            new BrowserCalledPlayBackProgress().start();
        } else if (isOneTouchPlayMode) {
            play.performClick();
            final String pvrFileName = getIntent().getExtras().getString("PVRRECORDEDFILE");
            if (pvrFileName == null) {
                Log.e(TAG, "===========>>>> pvrFileName is NULL !!!!!");
                finish();
                return;
            }

            final String pvrFileLcn = "CH " + mPvrManager.getFileLcn(0);
            final String pvrFileServiceName = mPvrManager.getFileServiceName(pvrFileName);
            final String pvrFileEventName = mPvrManager.getFileEventName(pvrFileName);
            final String pvrFileServiceNumberAndNameStr = pvrFileLcn + " " + pvrFileServiceName;
            serviceNameText.setText(pvrFileServiceNumberAndNameStr);
            eventNameText.setText(pvrFileEventName);
            Log.d(TAG, "PlayName    :" + pvrFileName);
            Log.d(TAG, "Lcn         :" + pvrFileLcn);
            Log.d(TAG, "ServiceName :" + pvrFileServiceName);
            Log.d(TAG, "EventName   :" + pvrFileEventName);
            //TODO: check this
            stopPlaybacking();
            EnumPvrStatus playbackStatus = mPvrManager.startPlayback(pvrFileName);
            Log.d(TAG, "==========>>> playbackStatus = " + playbackStatus);
            if (!playbackStatus.equals(EnumPvrStatus.E_SUCCESS)) {
                Toast.makeText(
                        this,
                        "Can't PlayBack Properly, the Reason is "
                                + playbackStatus.toString(), Toast.LENGTH_SHORT).show();
            }

            final int total = mPvrManager.getRecordedFileDurationTime(pvrFileName);
            RPProgress.setMax(total);
            totalRecordTime.setText(getTimeString(total));
            stop.requestFocus();
            new PlayBackProgress().start();
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_ALWAYS_TIME_SHIFT) {
            recordDiskPath = mPvrManager.getPvrMountPath();
            recordDiskLable = getUsbLabelByPath(new String(recordDiskPath));
            settingServiceEventName();
            createAnimation();
            onKeyPause();
            new PlayBackProgress().start();
        } else {
            settingServiceEventName();
            recorder.performClick();
        }
    }

    @Override
    public void onBackPressed() {
        final boolean isTimeShiftRecording = mPvrManager.isTimeShiftRecording();
        final boolean isAlwaysTimeShiftRecording = isTimeShiftRecording ? mPvrManager.isAlwaysTimeShiftRecording() : false;

        stopPlaybacking();
        if (isAlwaysTimeShiftRecording){
            finish();
        } else if (isTimeShiftRecording) {
            PVRActivity.currentRecordingProgrammFrency = -1;
            mPvrManager.stopTimeShiftRecord();
            finish();
        } else if (!isMenuHide && pvrImageFlag.isRecorderFlag()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.str_root_alert_dialog_title)
                    .setMessage(R.string.str_pvr_exit_confirm)
                    .setPositiveButton(R.string.str_root_alert_dialog_confirm,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                    PVRActivity.currentRecordingProgrammFrency = -1;
                                    saveAndExit();
                                }
                            })
                    .setNegativeButton(R.string.str_root_alert_dialog_cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                }
                            }).show();
        } else if (isMenuHide && pvrImageFlag.isRecorderFlag()) {
            menuShow();
        } else {
            finish();
        }
    }

    @Override
    public void onTimeOut() {
        super.onTimeOut();
        if (pvrImageFlag.isRecorderFlag()) {
            menuHide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Use for press "index" key in PVR recording state then back to PVR
        isGoingToBeClosed(false);
        isWatchRcodFilInRcoding = false;
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /* must store the new intent unless getIntent() will return the old one */
        setIntent(intent);
        if (getIntent().getExtras() != null) {
            final boolean isNotifyRecordStop = (getIntent().getExtras().getInt("PVR_ONE_TOUCH_MODE") == 4);
            final boolean isNotifyPlaybackStop = (getIntent().getExtras().getInt("PVR_PLAYBACK_STOP") == 11);
            if (isNotifyPlaybackStop && mPvrManager.isPlaybacking()) {
                stopPlaybacking();
                finish();
            }
            if (isNotifyRecordStop) {
                saveAndExit();
                boolean isBootedByRecord = mPvrManager.getIsBootByRecord();
                if (isBootedByRecord) {
                    // goto standby
                    mPvrManager.setIsBootByRecord(false);
                    TvCommonManager.getInstance().standbySystem("pvr");
                }
            }
        }
    }

    /* For prompt alert dialog to notify user */
    private class OnStopRecordCancelClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            Log.i(TAG, "OnStopRecordCancelClickListener onClick");
            dialog.dismiss();
            stopRecordDialog = null;
        }

    }

    private class OnStopRecordConfirmClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            stopRecordDialog = null;
            switchChannel(tPreviousEvent.getKeyCode());
        }
    }

    private boolean showStopRecordDialog() {
        boolean bRet = true;
        do {
            if (stopRecordDialog != null && stopRecordDialog.isShowing()) {
                Log.e(TAG, "StopRecordDialog allready exist");
                bRet = false;
                break;
            }
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder = dialogBuilder.setTitle(R.string.str_stop_record_dialog_title);
            dialogBuilder = dialogBuilder.setMessage(R.string.str_stop_record_dialog_message);
            dialogBuilder = dialogBuilder
                    .setPositiveButton(R.string.str_stop_record_dialog_confirm,
                            new OnStopRecordConfirmClickListener());
            dialogBuilder = dialogBuilder.setNegativeButton(R.string.str_stop_record_dialog_cancel,
                    new OnStopRecordCancelClickListener());
            if (dialogBuilder == null) {
                Log.e(TAG, "showStopRecordDialog -- AlertDialog.Builder init fail");
                bRet = false;
                break;
            }
            stopRecordDialog = dialogBuilder.create();
            if (stopRecordDialog == null) {
                Log.e(TAG, "showStopRecordDialog -- AlertDialog.Builder create dialog fail");
                bRet = false;
                break;
            }
            stopRecordDialog.show();
        } while (false);
        return bRet;
    }

    private ArrayList<ProgramInfo> getAllProgramList() {
        ProgramInfo pgi = null;
        int indexBase = 0;
        ArrayList<ProgramInfo> progInfoList = new ArrayList<ProgramInfo>();
        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        int serviceNum = 0;
        if (currInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
            indexBase = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            serviceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);
        } else {
            indexBase = 0;
            serviceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
        }
        if ((indexBase == 0xFFFFFFFF) || (serviceNum == 0xFFFFFFFF)) {
            return null;
        }
        for (int k = indexBase; k < serviceNum; k++) {
            pgi = mTvChannelManager.getProgramInfoByIndex(k);
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


    private ProgramInfo getNextProgramm() {
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

    private ProgramInfo getPreviousProgram() {
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
            return true;
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

    private boolean CheckNeedToStopRecord(KeyEvent tEvent) {
        boolean bRet = false;
        do {
            final int keyCode = tEvent.getKeyCode();
            if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP) {
                if (false == isNeedToCheckExitRecord(getNextProgramm())) {
                    mTvChannelManager.programUp();
                    break;
                }
            } else if (keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN) {
                if (false == isNeedToCheckExitRecord(getPreviousProgram())) {
                    mTvChannelManager.programDown();
                    break;
                }
            } else if (keyCode != MKeyEvent.KEYCODE_CHANNEL_RETURN) {
                break;
            }
            if (isMenuHide == true) {
                bRet = true;
                menuShow();
                break;
            }
            tPreviousEvent = tEvent;
            bRet = showStopRecordDialog();
        } while (false);
        return bRet;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_PROG_BLUE: {
                onKeyCapture();
                return true;
            }
            case KeyEvent.KEYCODE_PROG_GREEN: {
                boolean isTSRecording = false;
                isTSRecording = mPvrManager.isTimeShiftRecording();
                if (!isTSRecording) {
                    thumbnail.Show(!thumbnail.isShown());
                }
                return true;
            }
            case KeyEvent.KEYCODE_PROG_YELLOW: {
                boolean isTSRecording = false;
                isTSRecording = mPvrManager.isTimeShiftRecording();
                if (!isTSRecording) {
                    RemoveThumbnail();
                }
                return true;
            }
            case MKeyEvent.KEYCODE_MSTAR_INDEX: {
                isWatchRcodFilInRcoding = true;
                Intent intent = new Intent(this, PVRFullPageBrowserActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            case MKeyEvent.KEYCODE_MTS: {
                try {
                    DtvAudioInfo audioInfo = new DtvAudioInfo();
                    audioInfo = TvChannelManager.getInstance().getAudioInfo();
                    if (audioInfo != null)
                        audioLangPosLive = audioInfo.currentAudioIndex;
                    /*
                     * add by owen.qin to avoid finish by BaseActivity's onPause
                     * begin
                     */
                    isGoingToBeClosed(false);
                    /*
                     * add by owen.qin to avoid finish by BaseActivity's onPause
                     * end
                     */
                    Intent intent = new Intent(PVRActivity.this, AudioLanguageActivity.class);
                    PVRActivity.this.startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /* add by owen.qin begin */
                break;
                /* add by owen.qin end */
            }
            case MKeyEvent.KEYCODE_SUBTITLE: {
                try {
                    isGoingToBeClosed(false);
                    SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                    subtitlePosLive = settings.getInt("subtitlePos", -1);

                    Intent intent = new Intent(PVRActivity.this, SubtitleLanguageActivity.class);
                    PVRActivity.this.startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case KeyEvent.KEYCODE_DVR:
            case KeyEvent.KEYCODE_MEDIA_RECORD:
                recorder.performClick();
                return true;

            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                // Toast.makeText(this, "KEYCODE_MEDIA_PAUSE",
                // Toast.LENGTH_SHORT).show();
                pause.performClick();
                return true;
                // return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                // Toast.makeText(this, "KEYCODE_MEDIA_PLAY",
                // Toast.LENGTH_SHORT).show();
                play.performClick();
                return true;
                // return super.onKeyDown(keyCode, event);

            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                // Toast.makeText(this, "KEYCODE_MEDIA_PLAY",
                // Toast.LENGTH_SHORT).show();
                ff.performClick();
                return true;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                // Toast.makeText(this, "KEYCODE_MEDIA_PLAY",
                // Toast.LENGTH_SHORT).show();
                rev.performClick();
                return true;
            case KeyEvent.KEYCODE_MEDIA_STOP:
                // Toast.makeText(this, "KEYCODE_MEDIA_STOP",
                // Toast.LENGTH_SHORT).show();
                stop.performClick();
                return true;
                // return super.onKeyDown(keyCode, event);
            case MKeyEvent.KEYCODE_CC:
                if ((TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ISDB)
                        || (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC)) {
                    if (mCcKeyToast == null) {
                        mCcKeyToast = new Toast(this);
                        mCcKeyToast.setGravity(Gravity.CENTER, 0, 0);
                    }
                    TextView tv = new TextView(this);
                    tv.setTextSize(Constant.CCKEY_TEXTSIZE);
                    tv.setTextColor(Color.WHITE);
                    tv.setAlpha(Constant.CCKEY_ALPHA);
                    mCcKeyToast.setView(tv);
                    mCcKeyToast.setDuration(Toast.LENGTH_SHORT);
                    int caption = 0;
                    int resId = 0;
                    if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                        caption = TvCcManager.getInstance().getNextAtscCcMode();
                        resId = R.array.str_arr_setting_cc_mode_vals;
                    } else {
                        caption = TvCcManager.getInstance().getNextIsdbCcMode();
                        resId = R.array.str_arr_option_caption;
                    }
                    tv.setText(getResources().getString(R.string.str_option_caption)
                            + " "
                            + getResources().getStringArray(resId)[caption]);
                    mCcKeyToast.show();
                }
                return true;
        }

        /* For prompt alert dialog to notify user */
        if (CheckNeedToStopRecord(event) == true) {
            return true;
        }
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            menuShow();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case savingProgress: {
                ProgressDialog mpDialog = new ProgressDialog(this);
                mpDialog.setMessage(getResources().getString(R.string.str_pvr_program_saving));
                mpDialog.setIndeterminate(false);
                mpDialog.setCancelable(false);
                return mpDialog;
            }
            case timeChoose: {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.pvr_menu_dialog,
                        (ViewGroup) findViewById(R.id.pvr_dialog));

                resetJump2Timebtn = (Button) layout.findViewById(R.id.ResetJ2TBtn);
                resetJump2Timebtn.setOnClickListener(J2TButtonListener);

                timeChooser = new AlertDialog.Builder(activity)
                        .setTitle(R.string.str_player_time)
                        .setView(layout)
                        .setCancelable(false)
                        .setPositiveButton(R.string.str_root_alert_dialog_confirm,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                        int timeInSecond = ((TimeChooser) timeChooser
                                                .findViewById(R.id.pvr_menu_dialog_hours))
                                                .getValue()
                                                * 3600
                                                + ((TimeChooser) timeChooser
                                                        .findViewById(R.id.pvr_menu_dialog_minutes))
                                                        .getValue()
                                                * 60
                                                + ((TimeChooser) timeChooser
                                                        .findViewById(R.id.pvr_menu_dialog_seconds))
                                                        .getValue();
                                        Log.e(TAG, "=============>>> CURRENT JUMP TIME = "
                                                + timeInSecond);
                                        mPvrManager.jumpPlaybackTime(timeInSecond);
                                    }
                                })
                        .setNegativeButton(R.string.str_root_alert_dialog_cancel,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                        timeChooser = null;
                                    }
                                }).show();
                // WindowManager.LayoutParams params =
                // alertDialog.getWindow().getAttributes();
                // params.width = 300;
                // params.height = 300;
                // alertDialog.getWindow().setAttributes(params);
                // pvr bar status change
                setBarStatusOfPlayToOthers();
                return timeChooser;
            }
        }
        return null;
    }

    private OnClickListener J2TButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            EditText hours, minutes, seconds;

            hours = (TimeChooser) timeChooser.findViewById(R.id.pvr_menu_dialog_hours);
            minutes = (TimeChooser) timeChooser.findViewById(R.id.pvr_menu_dialog_minutes);
            seconds = (TimeChooser) timeChooser.findViewById(R.id.pvr_menu_dialog_seconds);

            hours.setText("");
            minutes.setText("");
            seconds.setText("");
        }
    };

    @Override
    protected void onDestroy() {
        isPVRActivityActive = false;
        super.onDestroy();
        usbSelecter.dismiss();
        stopPlaybacking();
        if (isBrowserCalled) {
            Intent intent = new Intent(this, PVRFullPageBrowserActivity.class);
            startActivity(intent);
        }
        mPvrManager.unregisterOnPvrEventListener(this);
    }

    public void setBarStatusOfStartRecord() {
        recorder.setEnabled(true);
        play.setEnabled(true);
        stop.setEnabled(true);
        pause.setEnabled(true);
        rev.setEnabled(false);
        ff.setEnabled(false);
        slow.setEnabled(false);
        time.setEnabled(false);
        backward.setEnabled(false);
        forward.setEnabled(false);
        // capture.setEnabled(false);
        recorder.setFocusable(true);
        play.setFocusable(true);
        stop.setFocusable(true);
        pause.setFocusable(true);
        rev.setFocusable(false);
        ff.setFocusable(false);
        slow.setFocusable(false);
        time.setFocusable(false);
        backward.setFocusable(false);
        forward.setFocusable(false);
        // capture.setFocusable(false);
    }

    public void setBarStatusOfRecordToPlay() {
        recorder.setEnabled(false);
        play.setEnabled(false);
        stop.setEnabled(true);
        pause.setEnabled(true);
        rev.setEnabled(true);
        ff.setEnabled(true);
        slow.setEnabled(true);
        time.setEnabled(true);
        backward.setEnabled(true);
        forward.setEnabled(true);
        // capture.setEnabled(true);
        recorder.setFocusable(false);
        play.setFocusable(false);
        stop.setFocusable(true);
        pause.setFocusable(true);
        rev.setFocusable(true);
        ff.setFocusable(true);
        slow.setFocusable(true);
        time.setFocusable(true);
        backward.setFocusable(true);
        forward.setFocusable(true);
        // capture.setFocusable(true);
    }

    public void setBarStatusOfRecordToPause() {
        recorder.setEnabled(true);
        play.setEnabled(true);
        stop.setEnabled(true);
        pause.setEnabled(true);
        rev.setEnabled(false);
        ff.setEnabled(false);
        slow.setEnabled(false);
        time.setEnabled(false);
        backward.setEnabled(false);
        forward.setEnabled(false);
        // capture.setEnabled(false);
        recorder.setFocusable(true);
        play.setFocusable(true);
        stop.setFocusable(true);
        pause.setFocusable(true);
        rev.setFocusable(false);
        ff.setFocusable(false);
        slow.setFocusable(false);
        time.setFocusable(false);
        backward.setFocusable(false);
        forward.setFocusable(false);
        // capture.setFocusable(false);
    }

    public void setBarStatusOfPlayToPause() {
        recorder.setEnabled(true);
        play.setEnabled(true);
        stop.setEnabled(false);
        pause.setEnabled(true);
        rev.setEnabled(true);
        ff.setEnabled(true);
        slow.setEnabled(true);
        time.setEnabled(true);
        backward.setEnabled(true);
        forward.setEnabled(true);
        // capture.setEnabled(true);
        recorder.setFocusable(true);
        play.setFocusable(true);
        stop.setFocusable(false);
        pause.setFocusable(true);
        rev.setFocusable(true);
        ff.setFocusable(true);
        slow.setFocusable(true);
        time.setFocusable(true);
        backward.setFocusable(true);
        forward.setFocusable(true);
        // capture.setFocusable(true);
    }

    public void setBarStatusOfPlayToOthers() {
        recorder.setEnabled(true);
        play.setEnabled(true);
        stop.setEnabled(true);
        pause.setEnabled(true);
        rev.setEnabled(true);
        ff.setEnabled(true);
        slow.setEnabled(true);
        time.setEnabled(true);
        backward.setEnabled(true);
        forward.setEnabled(true);
        // capture.setEnabled(true);
        recorder.setFocusable(true);
        play.setFocusable(true);
        stop.setFocusable(true);
        pause.setFocusable(true);
        rev.setFocusable(true);
        ff.setFocusable(true);
        slow.setFocusable(true);
        time.setFocusable(true);
        backward.setFocusable(true);
        forward.setFocusable(true);
        // capture.setFocusable(true);
    }

    /* add by owen.qin begin */
    //TODO: using USBDiskSelecter instead

    private boolean getChooseDiskSettings() {

        SharedPreferences sp = getSharedPreferences("save_setting_select", MODE_PRIVATE);
        return sp.getBoolean("IS_ALREADY_CHOOSE_DISK", false);

    }

    private String getChooseDiskLable() {

        SharedPreferences sp = getSharedPreferences("save_setting_select", MODE_PRIVATE);
        return sp.getString("DISK_LABEL", "unknown");
    }

    private String getChooseDiskPath() {

        SharedPreferences sp = getSharedPreferences("save_setting_select", MODE_PRIVATE);
        return sp.getString("DISK_PATH", "unknown");
    }

    private boolean isFileExisted(String path) {
        if (path == null || "".equals(path)) {
            return false;
        } else {
            File file = new File(path);
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        }

    }

    private static class DirectoryFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String filename) {

            System.out.println("dir:" + dir + " name:" + filename);
            if (new File(dir, filename).isDirectory()){
                return true;
            } else {
                return false;
            }
        }

    }

    private boolean isFATDisk(String diskPath) {
        Log.e(TAG, "isFATDisk diskPath:" + diskPath);
        String label = getUsbLabelByPath(new String(diskPath));
        Log.e(TAG, "isFATDisk label:" + label);
        if (label != null && label.contains(FAT)) {
            return true;
        }
        return false;
    }

    private String getFirstUseableDiskAtParentDir(String parent) {
        File file = new File(parent);
        if (file.isDirectory()) {
            FilenameFilter filter = new DirectoryFilter();
            File[] list = file.listFiles(filter);
            for (File tmp : list) {
                if ((isFileExisted(tmp.getAbsolutePath() + "/_MSTPVR/") || USBBroadcastReceiver
                        .isDiskExisted(new String(tmp.getAbsolutePath())))
                        && (isFATDisk(tmp.getAbsolutePath()))) {
                    return tmp.getAbsolutePath();
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private String getBestDiskPath() {
        if (getChooseDiskSettings()) {
            String path = getChooseDiskPath();
            if (isFileExisted(path + "/_MSTPVR") || USBBroadcastReceiver.isDiskExisted(new String(path))) {
                return path;
            } else {
                String parent = "/mnt/usb/";
                String firstDisk = getFirstUseableDiskAtParentDir(parent);
                if (firstDisk == null) {
                    return NO_DISK;
                } else {
                    return firstDisk;
                }
            }

        } else {
            return CHOOSE_DISK;
        }

    }

    /**
     * @param path like /mnt/usb/sda1
     */
    private String getUsbLabelByPath(String diskPath) {

        MStorageManager storageManager = MStorageManager.getInstance(this);
        String[] volumes = storageManager.getVolumePaths();
        int usbDriverCount = 0;
        ArrayList<String> usbDriverLabel = new ArrayList<String>();
        ArrayList<String> usbDriverPath = new ArrayList<String>();
        usbDriverLabel.clear();
        usbDriverPath.clear();
        if (volumes == null) {
            return null;
        }

        File file = new File("proc/mounts");
        if (!file.exists() || file.isDirectory()) {
            file = null;
        }

        for (int i = 0; i < volumes.length; ++i) {
            String state = storageManager.getVolumeState(volumes[i]);
            if (state == null || !state.equals(Environment.MEDIA_MOUNTED)) {
                continue;
            }
            String path = volumes[i];
            String[] pathPartition = path.split("/");
            String label = pathPartition[pathPartition.length - 1]; // the last
                                                                    // part

            String volumeLabel = storageManager.getVolumeLabel(path);
            if (volumeLabel != null) {
                // get rid of the long space in the Label word
                String[] tempVolumeLabel = volumeLabel.split(" ");
                volumeLabel = "";
                for (int j = 0; j < tempVolumeLabel.length; j++) {
                    if (j != tempVolumeLabel.length - 1) {
                        volumeLabel += tempVolumeLabel[j] + " ";
                        continue;
                    }
                    volumeLabel += tempVolumeLabel[j];
                }
            }
            label += ": " + getFileSystem(file, path) + "\n" + volumeLabel;
            usbDriverLabel.add(usbDriverCount, label);
            usbDriverPath.add(usbDriverCount, path);
            usbDriverCount++;
        }

        if (diskPath.startsWith("/")) {
            diskPath = diskPath.substring(1);
        }
        if (diskPath.endsWith("/")) {
            diskPath = diskPath.substring(0, diskPath.length() - 1);
        }
        for (int i = 0; i < usbDriverPath.size(); i++) {
            if (usbDriverPath.get(i).contains(diskPath)) {
                return usbDriverLabel.get(i);
            }
        }
        return null;
    }

    private String getFileSystem(File file, String path) {
        if (file == null) {
            return "";
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                String[] info = line.split(" ");
                if (info[1].equals(path)) {
                    if (info[2].equals("ntfs3g"))
                        return NTFS;
                    if (info[2].equals("vfat"))
                        return FAT;
                    else
                        return info[2];
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "";
    }

    private boolean isBootedByRecord() {

        boolean isBootedByRecord = mPvrManager.getIsBootByRecord();
        return isBootedByRecord;
    }

    private void standbySystem() {
        mPvrManager.setIsBootByRecord(false);
        TvCommonManager.getInstance().standbySystem("pvr");
    }

    private String getAvaliableDiskForStandBy() {
        String parent = "/mnt/usb/";
        String firstDisk = getFirstUseableDiskAtParentDir(parent);
        return firstDisk;

    }

    private void cancelValidEpgTimerEvent() {
        Time currTime = new Time();
        currTime.setToNow();
        currTime.set(currTime.toMillis(true));
        System.out.println("\n>>>cancelEpgTimerEvent");
        System.out.println("\ncancelEpgTimerEvent " + (int) (currTime.toMillis(true) / 1000));
        TvTimerManager timer = TvTimerManager.getInstance();
        if (timer != null) {
            timer.cancelEpgTimerEvent((int) ((currTime.toMillis(true) / 1000) + 10 + 3), false);
        }

    }

    /* add by owen.qin end */

    public boolean onKeyRecord() {
        boolean ret = false;
        final boolean isRecording = mPvrManager.isRecording();
        final boolean isRecorderFlag = pvrImageFlag.isRecorderFlag();
        final boolean isRecordPaused = mPvrManager.isRecordPaused();
        recordDiskPath = mPvrManager.getPvrMountPath();
        Log.d(TAG, "isRecording =" + isRecording);
        Log.d(TAG, "isRecorderFlag=" + isRecorderFlag);
        Log.d(TAG, "isRecordPaused=" + isRecordPaused);
        Log.d(TAG, "getMountPath=" + recordDiskPath);
        if (!isRecorderFlag) {
            if (isRecording) {
                recordDiskLable = getUsbLabelByPath(new String(recordDiskPath));
                usbLabel.setText(recordDiskLable);
                Log.d(TAG, "recordDiskLable=" + recordDiskLable);
                if (isOneTouchPauseMode) {
                    ret = doPVRTimeShift(true, isRecording,isRecordPaused);
                } else {
                    ret = doPVRRecord(true, isRecording, isRecordPaused);
                }
            } else {
                if (usbSelecter.getDriverCount() <= 0) {
                    Toast.makeText(activity, R.string.str_pvr_insert_usb, Toast.LENGTH_SHORT).show();
                    if (isBootedByRecord()) {
                        cancelValidEpgTimerEvent();
                        standbySystem();
                    }
                    return false;
                }
                String diskPath = getBestDiskPath();
                Log.e(TAG, "getBestDiskPath:" + diskPath);
                if (NO_DISK.equals(diskPath)) {
                    Toast.makeText(activity, R.string.str_pvr_insert_usb, Toast.LENGTH_SHORT).show();
                    if (isBootedByRecord()) {
                        cancelValidEpgTimerEvent();
                        standbySystem();
                    }
                    return false;
                }
                if (CHOOSE_DISK.equals(diskPath)) {
                    Log.e(TAG, "choose disk");
                    Log.e(TAG, "isBootedByRecord=" + isBootedByRecord());
                    if (isBootedByRecord()) {
                        diskPath = getAvaliableDiskForStandBy();
                        Log.e(TAG, "getAvaliableDiskForStandBy=" + diskPath);
                        if (diskPath == null) {
                            cancelValidEpgTimerEvent();
                            standbySystem();
                            return false;
                        }

                    } else {
                        usbSelecter.start();
                        return false;
                    }

                }
                String diskLabel = getUsbLabelByPath(new String(diskPath));
                recordDiskPath = diskPath;
                recordDiskLable = diskLabel;
                usbLabel.setText(diskLabel);
                if (isOneTouchPauseMode) {
                    ret = doPVRTimeShift(true, isRecording,isRecordPaused);
                } else {
                    if (recordDiskLable.regionMatches(6, FAT, 0, 3)) {
                        ret = doPVRRecord(true, isRecording, isRecordPaused);
                    } else {
                        Toast.makeText(activity, R.string.str_pvr_unsurpt_flsystem, Toast.LENGTH_SHORT).show();
                        if (isBootedByRecord()) {
                            standbySystem();
                        }
                        return false;
                    }
                }
            }
        } else {
            ret = doPVRRecord(isRecordPaused, isRecording, isRecordPaused);
        }
        return ret;
    }

    public void onKeyPlay() {
        if (curPvrMode == PVR_MODE.E_PVR_MODE_NONE)
            return;
        play.setImageResource(R.drawable.player_play_focus);
        pvrImageFlag.setPlayFlag(true);
        if (mPvrManager.isTimeShiftRecording()) {
            play.requestFocus();
            if (mPvrManager.getPlaybackSpeed() == EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_INVALID) {
                if (mPvrManager.isAlwaysTimeShiftPlaybackPaused()) {
                    if (mPvrManager.startAlwaysTimeShiftPlayback() != TvPvrManager.PVR_STATUS_SUCCESS) {
                        Log.e(TAG, "=========>>>> startAlwaysTimeShiftPlayback is not E_SUCCESS!");
                        return;
                    }
                } else {
                    if (mPvrManager.startTimeShiftPlayback() != EnumPvrStatus.E_SUCCESS) {
                        Log.e(TAG, "=========>>>> startTimeShiftPlayback is not E_SUCCESS!");
                        return;
                    }
                }
            } else {
                play.requestFocus();
                switch (mPvrManager.getPlaybackSpeed()) {
                    case E_PVR_PLAYBACK_SPEED_0X:
                        mPvrManager.resumePlayback();
                        break;
                    case E_PVR_PLAYBACK_SPEED_STEP_IN:
                        mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                        break;
                    case E_PVR_PLAYBACK_SPEED_1X:
                        OnClick_ABLoop();
                        break;
                    case E_PVR_PLAYBACK_SPEED_2XFF:
                    case E_PVR_PLAYBACK_SPEED_4XFF:
                    case E_PVR_PLAYBACK_SPEED_8XFF:
                    case E_PVR_PLAYBACK_SPEED_16XFF:
                    case E_PVR_PLAYBACK_SPEED_32XFF:
                    case E_PVR_PLAYBACK_SPEED_1XFB:
                    case E_PVR_PLAYBACK_SPEED_2XFB:
                    case E_PVR_PLAYBACK_SPEED_4XFB:
                    case E_PVR_PLAYBACK_SPEED_8XFB:
                    case E_PVR_PLAYBACK_SPEED_16XFB:
                    case E_PVR_PLAYBACK_SPEED_32XFB:
                    case E_PVR_PLAYBACK_SPEED_FF_1_2X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_4X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_8X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_16X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_32X:
                        mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                        break;
                    default:
                        break;
                }
            }
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_PLAYBACK) {
            play.requestFocus();
            switch (mPvrManager.getPlaybackSpeed()) {
                case E_PVR_PLAYBACK_SPEED_0X:
                    mPvrManager.resumePlayback();
                    break;
                case E_PVR_PLAYBACK_SPEED_STEP_IN:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                    break;
                case E_PVR_PLAYBACK_SPEED_1X:
                    OnClick_ABLoop();
                    break;
                case E_PVR_PLAYBACK_SPEED_2XFF:
                case E_PVR_PLAYBACK_SPEED_4XFF:
                case E_PVR_PLAYBACK_SPEED_8XFF:
                case E_PVR_PLAYBACK_SPEED_16XFF:
                case E_PVR_PLAYBACK_SPEED_32XFF:
                case E_PVR_PLAYBACK_SPEED_1XFB:
                case E_PVR_PLAYBACK_SPEED_2XFB:
                case E_PVR_PLAYBACK_SPEED_4XFB:
                case E_PVR_PLAYBACK_SPEED_8XFB:
                case E_PVR_PLAYBACK_SPEED_16XFB:
                case E_PVR_PLAYBACK_SPEED_32XFB:
                case E_PVR_PLAYBACK_SPEED_FF_1_2X:
                case E_PVR_PLAYBACK_SPEED_FF_1_4X:
                case E_PVR_PLAYBACK_SPEED_FF_1_8X:
                case E_PVR_PLAYBACK_SPEED_FF_1_16X:
                case E_PVR_PLAYBACK_SPEED_FF_1_32X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                    break;
                default:
                    break;
            }
            playSpeed.setVisibility(View.VISIBLE);
            playSpeed.setText("");
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_RECORD) {
            play.requestFocus();
            if (mPvrManager.isPlaybacking()) {
                switch (mPvrManager.getPlaybackSpeed()) {
                    case E_PVR_PLAYBACK_SPEED_0X:
                        mPvrManager.resumePlayback();
                        break;
                    case E_PVR_PLAYBACK_SPEED_STEP_IN:
                        mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                        break;
                    case E_PVR_PLAYBACK_SPEED_1X:
                        OnClick_ABLoop();
                        break;
                    case E_PVR_PLAYBACK_SPEED_2XFF:
                    case E_PVR_PLAYBACK_SPEED_4XFF:
                    case E_PVR_PLAYBACK_SPEED_8XFF:
                    case E_PVR_PLAYBACK_SPEED_16XFF:
                    case E_PVR_PLAYBACK_SPEED_32XFF:
                    case E_PVR_PLAYBACK_SPEED_1XFB:
                    case E_PVR_PLAYBACK_SPEED_2XFB:
                    case E_PVR_PLAYBACK_SPEED_4XFB:
                    case E_PVR_PLAYBACK_SPEED_8XFB:
                    case E_PVR_PLAYBACK_SPEED_16XFB:
                    case E_PVR_PLAYBACK_SPEED_32XFB:
                    case E_PVR_PLAYBACK_SPEED_FF_1_2X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_4X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_8X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_16X:
                    case E_PVR_PLAYBACK_SPEED_FF_1_32X:
                        mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                        break;
                    default:
                        break;
                }
            } else {// record only
                final String strFileName = mPvrManager.getCurRecordingFileName();
                if (EnumPvrStatus.E_SUCCESS != mPvrManager.startPlayback(strFileName)) {
                    Log.e(TAG, "=========>>>> startPlayback is not E_SUCCESS!");
                    return;
                }
                mPvrManager.assignThumbnailFileInfoHandler(strFileName);
                curPvrMode = PVR_MODE.E_PVR_MODE_PLAYBACK;
                // ConstructThumbnailList();
                // UpdateEIT();
                // UpdateChannelName();
                // DrawSkipBlocks();
            }
        }

        if (curPvrMode == PVR_MODE.E_PVR_MODE_PLAYBACK
                || curPvrMode == PVR_MODE.E_PVR_MODE_TIME_SHIFT) {
            setBarStatusOfPlayToOthers();
        } else {
            setBarStatusOfRecordToPlay();
        }
        return;
    }

    public void onKeyStop() {
        Log.e(TAG, "=========>>>> curPvrMode is :" + curPvrMode);
        if (curPvrMode == PVR_MODE.E_PVR_MODE_NONE)
            return;
        if (curPvrMode == PVR_MODE.E_PVR_MODE_PLAYBACK || curPvrMode == PVR_MODE.E_PVR_MODE_RECORD
                || curPvrMode == PVR_MODE.E_PVR_MODE_TIME_SHIFT) {
            if (mPvrManager.isPlaybacking()) {
                resumeLang();
                resumeSubtitle();
                if (pvrABLoopStartTime != INVALID_TIME) {
                    mPvrManager.stopPlaybackLoop();
                    pvrABLoopStartTime = pvrABLoopEndTime = INVALID_TIME;
                    progress_loopab.setVisibility(View.INVISIBLE);
                    textViewPlay.setText(getString(R.string.str_player_play));
                    if (isBrowserCalled) {
                        finish();
                    }
                    return;
                } else if (isOneTouchPlayMode) {
                    stopPlaybacking();
                    finish();
                }
            }
        }

        if (curPvrMode == PVR_MODE.E_PVR_MODE_RECORD) {
            if (mPvrManager.isPlaybacking()) {// record & play
                // Reset_Start_Time();
                mPvrManager.stopPlayback();
                setBarStatusOfRecordToPause();
                pvrABLoopStartTime = pvrABLoopEndTime = INVALID_TIME;
                // DoValueUpdate_PVR_Playback_Time(0);
                // ShowRecordPane();
                return;
            }
            if (mPvrManager.isRecording()) {// record only
                onBackPressed();
                return;
            }
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_TIME_SHIFT) {
            stopPlaybacking();
            mPvrManager.stopTimeShiftRecord(); // close time shift
            PVRActivity.currentRecordingProgrammFrency = -1;
            finish();
            return;
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_ALWAYS_TIME_SHIFT) {
            stopPlaybacking();
            progress_loopab.setVisibility(View.INVISIBLE);
            finish();
            return;
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_PLAYBACK) {
            stopPlaybacking();
            if (mPvrManager.isRecording()) {
                pvrABLoopStartTime = pvrABLoopEndTime = INVALID_TIME;
                setBarStatusOfRecordToPause();
                // SetPlaySpeedIcon();
                // DoValueUpdate_PVR_Playback_Time(0);
                if (curPvrMode == PVR_MODE.E_PVR_MODE_PLAYBACK) {
                    curPvrMode = PVR_MODE.E_PVR_MODE_RECORD;
                }
            } else {
                finish();
            }
            return;
        }
        return;
    }

    public void onKeyPause()  {
        Log.e(TAG, "=========>>>> curPvrMode is :" + curPvrMode);
        if (curPvrMode == PVR_MODE.E_PVR_MODE_NONE)
            return;
        if (mPvrManager.isTimeShiftRecording()) {
            if (!mPvrManager.isPlaybacking()) {
                // freeze img and setup start time
                mPvrManager.pauseAlwaysTimeShiftPlayback(true);
                // UpdateChannelName();
                // UpdateEIT();
                setBarStatusOfRecordToPause();
            } else {
                mPvrManager.stepInPlayback(); // stop or pause
                setBarStatusOfRecordToPause();
                // SetPlaySpeedIcon();
            }
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_PLAYBACK) {
            Log.e(TAG, "=========>>>> stepInPlayback !!!!");
            mPvrManager.stepInPlayback(); // stop or pause
            setBarStatusOfRecordToPause();
        } else if (curPvrMode == PVR_MODE.E_PVR_MODE_RECORD) {
            if (mPvrManager.isPlaybacking()) {// record & play
                mPvrManager.stepInPlayback(); // stop or pause
                setBarStatusOfRecordToPause();
            } else {
                mPvrManager.pauseRecord();
            }
        }
        pause.requestFocus();
        return;
    }

    private boolean isFastBackPlaying() {
        EnumPvrPlaybackSpeed speed = mPvrManager.getPlaybackSpeed();
        if (speed.ordinal() >= EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_32XFB.ordinal()
                && speed.ordinal() <= EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1XFB.ordinal()) {
            return true;
        }
        return false;
    }

    public void onKeyRev() {
        // rev
        if (mPvrManager.isPlaybacking()) {
            EnumPvrPlaybackSpeed curPlayBackSpeed = mPvrManager.getPlaybackSpeed();
            switch (curPlayBackSpeed) {
                case E_PVR_PLAYBACK_SPEED_1XFB:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_2XFB);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("2X");
                    break;
                case E_PVR_PLAYBACK_SPEED_2XFB:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_4XFB);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("4X");
                    break;
                case E_PVR_PLAYBACK_SPEED_4XFB:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_8XFB);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("8X");
                    break;
                case E_PVR_PLAYBACK_SPEED_8XFB:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_16XFB);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("16X");
                    break;
                case E_PVR_PLAYBACK_SPEED_16XFB:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_32XFB);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("32X");
                    break;
                case E_PVR_PLAYBACK_SPEED_32XFB:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1XFB);
                    playSpeed.setVisibility(View.GONE);
                    playSpeed.setText("");
                    break;
                default:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1XFB);
                    playSpeed.setVisibility(View.GONE);
                    playSpeed.setText("");
                    break;
            }
            // pvr bar status change
            setBarStatusOfPlayToOthers();
        }
    }

    public void onKeyFF()  {
        // FF
        if (mPvrManager.isPlaybacking()) {
            EnumPvrPlaybackSpeed curPlayBackSpeed = mPvrManager.getPlaybackSpeed();
            switch (curPlayBackSpeed) {
                case E_PVR_PLAYBACK_SPEED_1X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_2XFF);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("2X");
                    break;
                case E_PVR_PLAYBACK_SPEED_2XFF:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_4XFF);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("4X");
                    break;
                case E_PVR_PLAYBACK_SPEED_4XFF:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_8XFF);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("8X");
                    break;
                case E_PVR_PLAYBACK_SPEED_8XFF:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_16XFF);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("16X");
                    break;
                case E_PVR_PLAYBACK_SPEED_16XFF:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_32XFF);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("32X");
                    break;
                case E_PVR_PLAYBACK_SPEED_32XFF:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                    playSpeed.setVisibility(View.GONE);
                    playSpeed.setText("");
                    break;
                default:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                    playSpeed.setVisibility(View.GONE);
                    playSpeed.setText("");
                    break;
            }
            // pvr bar status change
            setBarStatusOfPlayToOthers();
        }
    }

    public void onKeySlowMotion()  {
        // slow motion
        if (mPvrManager.isPlaybacking()) {
            EnumPvrPlaybackSpeed curPlayBackSpeed = mPvrManager.getPlaybackSpeed();
            switch (curPlayBackSpeed) {
                case E_PVR_PLAYBACK_SPEED_STEP_IN:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_FF_1_2X);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("Slow/2");
                    break;
                case E_PVR_PLAYBACK_SPEED_FF_1_32X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
                    playSpeed.setVisibility(View.GONE);
                    playSpeed.setText("");
                    break;
                case E_PVR_PLAYBACK_SPEED_FF_1_16X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_FF_1_32X);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("Slow/32");
                    break;
                case E_PVR_PLAYBACK_SPEED_FF_1_8X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_FF_1_16X);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("Slow/16");
                    break;
                case E_PVR_PLAYBACK_SPEED_FF_1_4X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_FF_1_8X);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("Slow/8");
                    break;
                case E_PVR_PLAYBACK_SPEED_FF_1_2X:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_FF_1_4X);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("Slow/4");
                    break;
                default:
                    mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_FF_1_2X);
                    playSpeed.setVisibility(View.VISIBLE);
                    playSpeed.setText("Slow/2");
                    break;
            }
            // pvr bar status change
            setBarStatusOfPlayToOthers();
        }
    }

    public void onKeyGoToTime()  {
        if (mPvrManager.isPlaybacking()) {
            showDialog(timeChoose);
        }
    }

    public void onKeyBackward()  {
        int PVRCurPlaybackTime = 0;
        // for ATshift
        if (!mPvrManager.isPlaybacking() && mPvrManager.isRecording()
                && curPvrMode == PVR_MODE.E_PVR_MODE_ALWAYS_TIME_SHIFT) {
            if (mPvrManager.isAlwaysTimeShiftPlaybackPaused()) {
                if (mPvrManager.startAlwaysTimeShiftPlayback() != 0) {
                    return;
                }
            } else {
                mPvrManager.pauseAlwaysTimeShiftPlayback(true);
                if (mPvrManager.startAlwaysTimeShiftPlayback() != 0) {
                    return;
                }
                // UpdateChannelName();
                // UpdateEIT();
                setBarStatusOfRecordToPause();
                play.requestFocus();
            }
            PVRCurPlaybackTime = mPvrManager.getCurPlaybackTimeInSecond(); // update
                                                                   // time
            setBarStatusOfPlayToOthers();
            // m_u32PVR_Control_mode = PVR_CONTROL_MODE_PLAY;
            // SetPlaySpeedIcon();
        }
        // <
        if (mPvrManager.isPlaybacking() && !mPvrManager.isPlaybackPaused()
                && mPvrManager.getPlaybackSpeed() != EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_STEP_IN) {
            PVRCurPlaybackTime = mPvrManager.getCurPlaybackTimeInSecond();
            if (PVRCurPlaybackTime > SKIP_TIME_INSEC) {
                // Normal Jump Backward
                mPvrManager.jumpPlaybackTime(PVRCurPlaybackTime - SKIP_TIME_INSEC);
            } else {
                // Jump To Head
                mPvrManager.jumpPlaybackTime(0);
            }
        }
        return;
    }

    public void onKeyForward()  {
        if (mPvrManager.isPlaybacking()
                && !mPvrManager.isPlaybackPaused()
                && mPvrManager.getPlaybackSpeed() != EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_STEP_IN) {
            final int total = mPvrManager.getRecordedFileDurationTime(mPvrManager.getCurPlaybackingFileName());
            final int JumpTime = mPvrManager.getCurPlaybackTimeInSecond() + SKIP_TIME_INSEC;
            if (JumpTime < total) {
                mPvrManager.jumpPlaybackTime(JumpTime);
            } else { //according to PVR spec
                mPvrManager.jumpPlaybackTime(total);
            }
        }
        // pvr bar status change
        setBarStatusOfPlayToOthers();
    }

    public void onKeyCapture()  {
        // capture
        if (!mPvrManager.isTimeShiftRecording() && mPvrManager.isPlaybacking()) {
            CaptureThumbnail();
        }
        // pvr bar status change
        setBarStatusOfPlayToOthers();
    }

    private void OnClick_ABLoop()  {
        if (setPvrABLoop == PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_NONE) {
            pvrABLoopStartTime = mPvrManager.getCurPlaybackTimeInSecond();
            setPvrABLoop = PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_A;

            // ham
            // lp4LoopAB = new
            // android.widget.RelativeLayout.LayoutParams(0,dip2px(13));
            // imgViewLoopAB.setLayoutParams(lp4LoopAB);
            textViewPlay.setText(getString(R.string.str_player_play) + " A");
            A_progress = RPProgress.getProgress();
            int x = RPProgress.getWidth() * A_progress
                    / (RPProgress.getMax() == 0 ? 1 : RPProgress.getMax());
            lp4LoopAB.leftMargin = x;
            looptime = 0;
            progress_loopab.setMax(looptime);
            progress_loopab.setProgress(looptime);
            progress_loopab.setLayoutParams(lp4LoopAB);
            progress_loopab.setVisibility(View.VISIBLE);
            // imgViewLoopAB.setX(x);
            // imgViewLoopAB.setVisibility(View.VISIBLE);

        } else if (setPvrABLoop == PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_A) {
            pvrABLoopEndTime = mPvrManager.getCurPlaybackTimeInSecond();
            Log.e(TAG, "b-a=" + (pvrABLoopEndTime - pvrABLoopStartTime));
            if (pvrABLoopEndTime - pvrABLoopStartTime <= 2) {
                Toast.makeText(this,
                        R.string.str_pvr_abloop_too_short,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mPvrManager.startPlaybackLoop(pvrABLoopStartTime, pvrABLoopEndTime);
            setPvrABLoop = PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_AB;

            // ham
            textViewPlay.setText(getString(R.string.str_player_play) + " A-B");
            lp4LoopAB.width = (RPProgress.getProgress() - A_progress) * RPProgress.getWidth()
                    / (RPProgress.getMax() == 0 ? 1 : RPProgress.getMax());
            progress_loopab.setLayoutParams(lp4LoopAB);
            looptime++;
            progress_loopab.setMax(looptime);
            currentlooptime = 0;
        } else {
            mPvrManager.stopPlaybackLoop();
            setPvrABLoop = PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_NONE;
            textViewPlay.setText(getString(R.string.str_player_play));
            lp4LoopAB.width = 0;
            progress_loopab.setMax(0);
            progress_loopab.setVisibility(View.GONE);
        }

    }

    // ham
    private int dip2px(int dipValue) {

        float scale = getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);

    }

    private boolean doPVRRecord(boolean onOff, boolean isRecording, boolean isRecordPaused)  {
        if (onOff) {
            recorder.setImageResource(R.drawable.player_recorder_focus);
            recordingView.setVisibility(View.VISIBLE);
            pvrImageFlag.setRecorderFlag(true);
            recordIconAnimation.start();
            if (isRecordPaused) {
                mPvrManager.resumeRecord();
                return true;
            }
            if (!isRecording) {
                if (recordDiskPath == null) {
                    Log.e(TAG, "=============>>>>> USB Disk Path is NULL !!!");
                    return false;
                }
                Log.d(TAG, "=============>>>>> USB Disk Path = " + recordDiskPath);
                Log.d(TAG, "=============>>>>> USB Disk Label = " + recordDiskLable);
                if (recordDiskLable.regionMatches(6, FAT, 0, 3)) {
                    mPvrManager.setPvrParams(recordDiskPath, (short) 2);
                } else {
                    Toast.makeText(PVRActivity.this,
                            R.string.str_pvr_unsurpt_flsystem,
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                final EnumPvrStatus status = mPvrManager.startRecord();
                Log.d(TAG, "status=" + status);
                if (status != EnumPvrStatus.E_SUCCESS) {
                    if (isBootedByRecord()) {
                        cancelValidEpgTimerEvent();
                        standbySystem();
                        return false;
                    }
                    Toast.makeText(this, R.string.str_pvr_record_fail, Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            currentRecordingProgrammFrency = mTvChannelManager.getCurrentProgramInfo().frequency;
            curPvrMode = PVR_MODE.E_PVR_MODE_RECORD;
            final String strFileName = mPvrManager.getCurRecordingFileName();
            Log.d(TAG, "===========>>>> doPVRRecord: current recording fileName = "
                    + strFileName);
            SwitchPageHelper.sLastRecordedFileName = strFileName;
            new PlayBackProgress().start();
        } else {
            recordingView.setVisibility(View.GONE);
            recordIconAnimation.end();
            recorder.setImageResource(R.drawable.player_recorder);
            mPvrManager.pauseRecord();
        }
        return true;
    }

    private boolean doPVRTimeShift(boolean onOff, boolean isRecording, boolean isRecordPaused)  {
        mPvrManager.stepInPlayback(); // stop or pause
        setBarStatusOfStartRecord();
        recorder.setEnabled(false);
        recorder.setFocusable(false);

        if (onOff) {
            recorder.setImageResource(R.drawable.player_recorder_focus);
            recordingView.setVisibility(View.VISIBLE);
            pvrImageFlag.setRecorderFlag(false);
            recordIconAnimation.start();
            currentRecordingProgrammFrency = mTvChannelManager.getCurrentProgramInfo().frequency;

            if (isRecordPaused) {
                mPvrManager.resumeRecord();
                return true;
            }

            if (!isRecording) {
                if (recordDiskPath == null) {
                    Log.e(TAG, "=============>>>>> doPVRTimeShift USB Disk Path is NULL !!!");
                    return false;
                }

                mPvrManager.setPvrParams(recordDiskPath, (short) 2);
                final EnumPvrStatus status = mPvrManager.startTimeShiftRecord();
                Log.d(TAG, "status=" + status);
                if (status != EnumPvrStatus.E_SUCCESS) {
                    Toast.makeText(this, R.string.str_pvr_record_fail, Toast.LENGTH_SHORT).show();
                    return false;
                }
                curPvrMode = PVR_MODE.E_PVR_MODE_TIME_SHIFT;
            }
            new PlayBackProgress().start();
        } else {
            recordingView.setVisibility(View.GONE);
            recordIconAnimation.end();
            recorder.setImageResource(R.drawable.player_recorder);
            mPvrManager.stopTimeShiftRecord();
            PVRActivity.currentRecordingProgrammFrency = -1;
        }
        return true;
    }

    private void resumeSubtitle() {
        DtvSubtitleInfo subtitleInfo;

        subtitleInfo = mTvChannelManager.getSubtitleInfo();
        if (subtitleInfo != null) {
            if (subtitlePosLive != -1 && subtitlePosLive <= subtitleInfo.subtitleServiceNumber
                    && subtitlePosLive != subtitleInfo.currentSubtitleIndex + 1) {
                mTvChannelManager.closeSubtitle();
                mTvChannelManager.openSubtitle((subtitlePosLive - 1));
                SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                Editor editor = settings.edit();
                editor.putInt("subtitlePos", subtitlePosLive);
                editor.commit();
                subtitlePosLive = -1;
            }
        }
    }

    private void resumeLang() {
        DtvAudioInfo audioInfo = new DtvAudioInfo();
        audioInfo = mTvChannelManager.getAudioInfo();
        if (audioInfo != null) {
            if (audioLangPosLive != -1 && audioInfo.audioLangNum > audioLangPosLive
                    && audioInfo.currentAudioIndex != audioLangPosLive) {
                mTvChannelManager.switchAudioTrack(audioLangPosLive);
                audioLangPosLive = -1;
            }
        }
    }

    private void saveAndExit() {
        showDialog(savingProgress);
        new Thread(new Runnable() {

            @Override
            public void run() {
                mPvrManager.stopRecord();
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        recorder.setImageResource(R.drawable.player_recorder);
                        pvrImageFlag.setRecorderFlag(false);
                        dismissDialog(savingProgress);
                        finish();
                    }
                });
            }
        }).start();
    }


    private void stopPlaybacking()
    {
        if (curPvrMode == PVR_MODE.E_PVR_MODE_ALWAYS_TIME_SHIFT) {
            mPvrManager.stopAlwaysTimeShiftPlayback();
            mPvrManager.pauseAlwaysTimeShiftPlayback(false);
            return;
        }

        if (mPvrManager.isPlaybacking()) {
            resumeLang();
            resumeSubtitle();
            mPvrManager.stopPlayback();
        }
    }

    private void switchChannel(final int keyCode)
    {
        showDialog(savingProgress);
        new Thread(new Runnable() {

           @Override
           public void run() {
               stopPlaybacking();
               mPvrManager.stopRecord();
               handler.post(new Runnable() {

                   @Override
                   public void run() {
                       recorder.setImageResource(R.drawable.player_recorder);
                       pvrImageFlag.setRecorderFlag(false);
                       dismissDialog(savingProgress);
                   }
               });
               switch (keyCode) {
                   case KeyEvent.KEYCODE_CHANNEL_UP:
                       mTvChannelManager.programUp();
                       break;
                   case KeyEvent.KEYCODE_CHANNEL_DOWN:
                       mTvChannelManager.programDown();
                       break;
                   case MKeyEvent.KEYCODE_CHANNEL_RETURN:
                       mTvChannelManager.returnToPreviousProgram();
                       break;
               }
               finish();
           }
        }).start();

    }

    private void updateUSBInfo() {
        StatFs sf = new StatFs(recordDiskPath);
        final int percent = (int) (100 - ((sf.getFreeBlocksLong() * 100)/ sf.getBlockCountLong()));
        usbPercentage.setText(percent + "%");
        usbFreeSpace.setProgress(percent);
    }

    private void createAnimation() {
        if (menuShowAnimation != null && menuHideAnimation != null)
            return;
        int height = rootView.getHeight() + rootView.getPaddingBottom();
        ObjectAnimator fadeInAlphaAnim = ObjectAnimator.ofFloat(rootView, "alpha", 0f, 1f);
        fadeInAlphaAnim.setInterpolator(new DecelerateInterpolator());
        fadeInAlphaAnim.setDuration(300);
        ObjectAnimator fadeOutAlphaAnim = ObjectAnimator.ofFloat(rootView, "alpha", 1f, 0f);
        fadeOutAlphaAnim.setInterpolator(new DecelerateInterpolator());
        fadeOutAlphaAnim.setDuration(300);
        ObjectAnimator moveUpAnim = ObjectAnimator.ofFloat(rootView, "translationY", height, 0);
        moveUpAnim.setInterpolator(new DecelerateInterpolator());
        moveUpAnim.setDuration(300);
        ObjectAnimator moveDownAnim = ObjectAnimator.ofFloat(rootView, "translationY", 0, height);
        moveDownAnim.setInterpolator(new DecelerateInterpolator());
        moveDownAnim.setDuration(300);
        menuShowAnimation = new AnimatorSet();
        menuHideAnimation = new AnimatorSet();
        recordIconAnimation = new AnimatorSet();
        menuShowAnimation.play(moveUpAnim).with(fadeInAlphaAnim);
        menuHideAnimation.play(moveDownAnim).with(fadeOutAlphaAnim);
        menuShowAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                isMenuHide = false;
                rootView.setVisibility(View.VISIBLE);
                rootView.requestFocus();
            }
        });
        menuHideAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.setVisibility(View.GONE);
                isMenuHide = true;
            }
        });
        fadeOutAlphaAnim = ObjectAnimator.ofFloat(recordingView.findViewById(R.id.pvrrecordimage),
                "alpha", 1f, 0f);
        fadeOutAlphaAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOutAlphaAnim.setDuration(2000);
        fadeOutAlphaAnim.setRepeatCount(Animation.INFINITE);
        fadeOutAlphaAnim.setRepeatMode(Animation.RESTART);
        fadeOutAlphaAnim.addListener(new AnimatorListener() {

            private int count = 0;

            private TextView text = (TextView) recordingView.findViewById(R.id.pvrrecordtext);

            private String strName = getResources().getString(R.string.str_pvr_is_recording);

            @Override
            public void onAnimationStart(Animator animation) {
                text.setText(strName + ".");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                String str = strName;
                for (int i = 0; i < count + 1; i++) {
                    str += ".";
                }
                text.setText(str);
                count = (count + 1) % 8;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        recordIconAnimation.play(fadeOutAlphaAnim);
    }

    private void menuShow() {
        Log.d(TAG, "=============>>>> menuShow = " + isMenuHide);
        if (isMenuHide) {
            menuHideAnimation.end();
            menuShowAnimation.start();
        }
    }

    private void menuHide() {
        Log.d(TAG, "=============>>>> menuHide = " + !isMenuHide);
        if (!isMenuHide) {
            menuShowAnimation.end();
            menuHideAnimation.start();
        }
    }

    private boolean CaptureThumbnail()  {
        CaptureThumbnailResult result = mPvrManager.captureThumbnail();
        Log.d(TAG,
                "=============>>>> result.getPvrThumbnailStatus() = "
                        + result.getPvrThumbnailStatus());
        switch (result.getPvrThumbnailStatus()) {
            case E_OK:
                if (false == AddThumbnail(result.thumbnailIndex)) {
                    Log.d(TAG, "=============>>>> AddThumbnail fail !!! ");
                    return false;
                }
                break;
            case E_REPLACE:// clear cache image in MW_PVR
                break;
            default:
                Log.e(TAG, "capture fail !!!\n");
                return false;
        }
        if (thumbnail.isShown())
            thumbnail.setSelection(result.thumbnailIndex);
        return true;
    }

    private boolean AddThumbnail(int index) {
        return thumbnail.addThumbnail(index);
    }

    private boolean RemoveThumbnail() {
        if (!thumbnail.isShown())
            return false;
        int index = thumbnail.getSelectedItemPosition();
        boolean result = false;
        if (result) {
            thumbnail.removeViewAt(index);
            return true;
        } else
            return false;
    }

    private void updateProgress(final int currentTime, final int total) {
        final boolean isPlaybacking = mPvrManager.isPlaybacking();
        RPProgress.setMax(total);
        totalRecordTime.setText(getTimeString(total));

        if (setPvrABLoop == PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_AB) {
            currentlooptime++;
            RPProgress.setTextProgress(getTimeString(currentTime), 0);
            Log.i(TAG, "looptime:" + looptime);
            progress_loopab.setProgress(currentlooptime % looptime + 1);
            Log.i(TAG, "max:" + progress_loopab.getMax());
            Log.i(TAG, "current" + progress_loopab.getProgress());
        } else if (setPvrABLoop == PVR_AB_LOOP_STATUS.E_PVR_AB_LOOP_STATUS_A) {
            RPProgress.setTextProgress(getTimeString(currentTime), currentTime);
            lp4LoopAB.width = (RPProgress.getProgress() - A_progress) * RPProgress.getWidth()
                    / (RPProgress.getMax() == 0 ? 1 : RPProgress.getMax());
            looptime++;
            progress_loopab.setLayoutParams(lp4LoopAB);
        } else {
            RPProgress.setTextProgress(getTimeString(currentTime), currentTime);
        }

        if (isPlaybacking && isFastBackPlaying() && (currentTime <= 0))
        {
            // back to normal when FB to begin.
            playSpeed.setVisibility(View.GONE);
            playSpeed.setText("");
            pvrImageFlag.setFfFlag(false);
            mPvrManager.setPlaybackSpeed(EnumPvrPlaybackSpeed.E_PVR_PLAYBACK_SPEED_1X);
            return;
        } else if (isPlaybacking && (currentTime >= total)) {
            stopPlaybacking();
            if (mPvrManager.isTimeShiftRecording()) {
                mPvrManager.stopTimeShiftRecord();
                PVRActivity.currentRecordingProgrammFrency = -1;
            }
            finish();
        }
    }

    private class PlayBackProgress extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                // Only [Record] & [TimeShift] mode need to update USB info.
                if (mPvrManager.isRecording() || mPvrManager.isTimeShiftRecording()) {
                    new usbInfoUpdate().start();
                }
                while ((mPvrManager.isPlaybacking() || mPvrManager.isRecording()) && !isFinishing()) {
                    final int currentTime = mPvrManager.getCurPlaybackTimeInSecond();
                    final int total = (mPvrManager.isRecording() && !isWatchRcodFilInRcoding) ? mPvrManager
                            .getCurRecordTimeInSecond() : mPvrManager
                            .getRecordedFileDurationTime(mPvrManager.getCurPlaybackingFileName());
                    Log.d(TAG, "==========>>> current: " + currentTime+ "/  total: " +total);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            updateProgress(currentTime, total);
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class BrowserCalledPlayBackProgress extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                while ((mPvrManager.isPlaybacking() || mPvrManager.isRecording()) && !isFinishing()) {
                    final int currentTime = mPvrManager.getCurPlaybackTimeInSecond();
                    final int total = mPvrManager.getRecordedFileDurationTime(mPvrManager
                            .getCurPlaybackingFileName());
                    Log.e(TAG, "==========>>> current time = " + currentTime);
                    Log.e(TAG, "=======>>> tatal Time=" + total);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            updateProgress(currentTime, total);
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class usbInfoUpdate extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                while ((mPvrManager.isPlaybacking() || mPvrManager.isRecording()) && !isFinishing()) {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            updateUSBInfo();
                        }
                    });
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTimeString(int seconds) {
        String hour = "00";
        String minute = "00";
        String second = "00";
        if (seconds % 60 < 10)
            second = "0" + seconds % 60;
        else
            second = "" + seconds % 60;

        int offset = seconds / 60;
        if (offset % 60 < 10)
            minute = "0" + offset % 60;
        else
            minute = "" + offset % 60;

        offset = seconds / 3600;
        if (offset < 10)
            hour = "0" + offset;
        else
            hour = "" + offset;
        return hour + ":" + minute + ":" + second;
    }

    @Override
    public boolean onPvrNotifyUsbInserted(PvrManager mgr, int what, int arg1, int arg2) {
        Log.e(TAG, "==========>>> onPvrNotifyUsbInserted = " + what);
        return false;
    }

    @Override
    public boolean onPvrNotifyUsbRemoved(PvrManager mgr, int what, int arg1, int arg2) {
        Log.e(TAG, "==========>>> onPvrNotifyUsbRemoved = " + what);
        return false;
    }

    @Override
    public boolean onPvrNotifyFormatFinished(PvrManager mgr, int what, int arg1, int arg2) {
        Log.e(TAG, "==========>>> onPvrNotifyFormatFinished = " + what);
        return false;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public boolean onPvrNotifyPlaybackBegin(PvrManager what, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        Log.e(TAG, "==========>>> onPvrNotifyPlaybackBegin = " + what);
        return false;
    }

    @Override
    public boolean onPvrNotifyPlaybackStop(PvrManager arg0, int what, int arg2, int arg3) {
        // TODO Auto-generated method stub
        Log.e(TAG, "==========>>> onPvrNotifyPlaybackStop = " + what);
        return false;
    }
}
