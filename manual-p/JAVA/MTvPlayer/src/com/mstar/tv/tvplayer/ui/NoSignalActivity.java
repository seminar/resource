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

package com.mstar.tv.tvplayer.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.graphics.Color;

import com.mstar.android.MKeyEvent;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.CecSetting;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.channel.ChannelControlActivity;
import com.mstar.tv.tvplayer.ui.dtv.CimmiActivity;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.CheckParentalPwdActivity;
import com.mstar.tv.tvplayer.ui.pvr.PVRActivity;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant.SignalProgSyncStatus;
import com.mstar.util.Constant.ScreenSaverMode;
import com.mstar.util.Constant;

public class NoSignalActivity extends MstarBaseActivity {
    private static final String TAG = "NoSignalActivity";

    private static final int CMD_SIGNAL_LOCK = 0x00;

    private static final int CMD_SHOW_SOURCE_INFO = 0x01;

    private static final int CMD_REDRAW = 0x02;

    private MyHandler myHandler = null;

    private int m_bScreenSaverStatus = -1;

    TextView title;

    private int resId = -1;

    private int caEventType = -1;

    private int caMsgType = -1;

    private TvCommonManager tvCommonManager = null;

    private TvCecManager mTvCecManager = null;

    private TvChannelManager mTvChannelManager = null;

    private BroadcastReceiver mReceiver = null;

    private Toast mCcKeyToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nosignal);
        myHandler = new MyHandler();
        m_bScreenSaverStatus = getIntent().getIntExtra("IntendId", 0);
        Log.i("NoSignalActivity", "&&&&&......m_bScreenSaverStatus is :" + m_bScreenSaverStatus);
        tvCommonManager = TvCommonManager.getInstance();
        mTvChannelManager = TvChannelManager.getInstance();
        mTvCecManager = TvCecManager.getInstance();

        myHandler.sendEmptyMessageDelayed(CMD_REDRAW, 1);
        sendBroadcast(new Intent(TvIntent.ACTION_SOURCE_INFO_DISAPPEAR));
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TvIntent.ACTION_SIGNAL_LOCK)) {
                    myHandler.sendEmptyMessage(CMD_SIGNAL_LOCK);
                } else if (intent.getAction().equals(TvIntent.ACTION_SIGNAL_UNLOCK)) {

                } else if (intent.getAction().equals(TvIntent.ACTION_COMMON_VIDEO)) {
                    myHandler.sendEmptyMessage(CMD_SHOW_SOURCE_INFO);
                } else if (intent.getAction().equals(TvIntent.ACTION_REDRAW_NOSIGNAL)) {
                    myHandler.sendEmptyMessage(CMD_REDRAW);
                } else if (intent.getAction().equals(TvIntent.ACTION_CIPLUS_OP_REFRESH)) {
                    finish();
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(TvIntent.ACTION_SIGNAL_LOCK);
        filter.addAction(TvIntent.ACTION_SIGNAL_UNLOCK);
        filter.addAction(TvIntent.ACTION_COMMON_VIDEO);
        filter.addAction(TvIntent.ACTION_CIPLUS_OP_REFRESH);
        registerReceiver(mReceiver, filter);
    }

    private void redrawComponents() {
        int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        TextView title = (TextView) findViewById(R.id.NoSiganlText);
        title.setVisibility(View.VISIBLE);
        // No-Signal case
        if (m_bScreenSaverStatus == Constant.TV_SCREENSAVER_NOSIGNAL) {
            // FIXME: why need delay?
            // delay to check signal because HDMI/YPBPR/DTV signal show late.
            if ((curInputSource >= TvCommonManager.INPUT_SOURCE_HDMI
                    && curInputSource <= TvCommonManager.INPUT_SOURCE_HDMI4)
                    || curInputSource == TvCommonManager.INPUT_SOURCE_YPBPR
                    || curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                delay2CheckSignal();
            }
            title.setText(R.string.str_no_signal);
            return;
        }
        // Deal with each screen saver status by inputsource
        if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_VGA) {
            if (m_bScreenSaverStatus == SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE) {
                title.setText(R.string.str_unsupported);
            } else if (m_bScreenSaverStatus == SignalProgSyncStatus.AUTO_ADJUST) {
                title.setText(R.string.str_auto_adjust);
                myHandler.sendEmptyMessageDelayed(CMD_SHOW_SOURCE_INFO, 3000);
            } else {
                finish();
            }
        } else if (curInputSource == TvCommonManager.INPUT_SOURCE_HDMI
                || curInputSource == TvCommonManager.INPUT_SOURCE_HDMI2
                || curInputSource == TvCommonManager.INPUT_SOURCE_HDMI3
                || curInputSource == TvCommonManager.INPUT_SOURCE_HDMI4) {
            if (m_bScreenSaverStatus == SignalProgSyncStatus.UNSTABLE) {
                title.setText(R.string.str_unsupported);
            }
        } else if (curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_INVALID_SERVICE) {
                title.setText(R.string.str_invalid_service);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_NO_CI_MODULE) {
                title.setText(R.string.str_no_ci_module);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_CI_PLUS_AUTHENTICATION) {
                title.setText(R.string.str_ci_plus_authentication);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM) {
                title.setText(R.string.str_scrambled_program);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_UNSUPPORTED_FORMAT) {
                /*
                 *  FIXME: atsc screen saver status is separate by flag in supernova
                 *  enum of screen saver should be sync between each tv system.
                 */
                if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                    title.setText(R.string.str_no_channel_banner);
                } else {
                    title.setText(R.string.str_unsupported);
                }
            } else if (ScreenSaverMode.DTV_SS_CH_BLOCK == m_bScreenSaverStatus) {
                if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                    ProgramInfo ProgInf = TvAtscChannelManager.getInstance()
                            .getCurrentProgramInfo();
                    if (ProgInf.isLock) {
                    } else {
                        try {
                            if (mTvChannelManager != null) {
                                mTvChannelManager.setProgramAttribute(TvChannelManager.PROGRAM_ATTRIBUTE_LOCK,
                                        ProgInf.majorNum, (short) ProgInf.minorNum, ProgInf.progId,
                                        false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                } else if (null == CheckParentalPwdActivity.instance) {
                    if (TvParentalControlManager.getInstance().isSystemLock()) {
                        Intent intent = new Intent();
                        intent.setClass(this, CheckParentalPwdActivity.class);
                        intent.putExtra("list", 4);
                        startActivityForResult(intent, 100);
                        isGoingToBeClosed(false);
                    } else {
                        TvParentalControlManager.getInstance().unlockChannel();
                        finish();
                    }
                }
            } else if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == m_bScreenSaverStatus) {
                if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                    if (TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus()) {
                    } else {
                        TvAtscChannelManager.getInstance().setBlockSysLockMode(false);
                        finish();
                    }
                } else {
                    if (TvParentalControlManager.getInstance().isSystemLock()) {
                        Intent intent = new Intent();
                        intent.setClass(this, CheckParentalPwdActivity.class);
                        intent.putExtra("list", 4);
                        startActivityForResult(intent, 100);
                        isGoingToBeClosed(false);
                    } else {
                        TvParentalControlManager.getInstance().unlockChannel();
                        finish();
                    }
                }
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_AUDIO_ONLY) {
                title.setText(R.string.str_audio_only);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_DATA_ONLY) {
                title.setText(R.string.str_data_only);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_COMMON_VIDEO) {
                finish();
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_INVALID_PMT) {
                title.setText(R.string.str_invalid_pmt);
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_CA_NOTIFY) {
                if ((getIntent() != null) && (getIntent().getExtras() != null)) {
                    caEventType = getIntent().getIntExtra("caEventType", 0);
                    caMsgType = getIntent().getIntExtra("caMsgType", 0);
                    resId = getIntent().getIntExtra("resId", 0);
                }
                if (caMsgType == 0) {
                    Intent intent = new Intent(NoSignalActivity.this, RootActivity.class);
                    NoSignalActivity.this.startActivity(intent);
                    finish();
                } else {
                    title.setText(resId);
                }
            }
        } else if (TvCommonManager.INPUT_SOURCE_ATV == curInputSource) {
            if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == m_bScreenSaverStatus) {
                finish();
            } else if (ScreenSaverMode.DTV_SS_CH_BLOCK == m_bScreenSaverStatus) {
                if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                }
            } else if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_PARENTAL_BLOCK) {
                if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                }
            }
        }
    }

    private void delay2CheckSignal() {
        findViewById(R.id.NoSiganlText).setVisibility(View.INVISIBLE);// not
                                                                      // sure
                                                                      // it's
                                                                      // signal,
                                                                      // so
                                                                      // don't
                                                                      // show it
                                                                      // at
                                                                      // first.
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (false == mTvChannelManager.isSignalStabled()) {
                    // show no signal, because we sure now.
                    findViewById(R.id.NoSiganlText).setVisibility(View.VISIBLE);
                }
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        myHandler.postDelayed(runnable, 15 * 60 * 1000);
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("NoSignalActivity", "NoSignalActivity onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);
        if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_CA_NOTIFY) {
            if ((getIntent() != null) && (getIntent().getExtras() != null)) {
                Bundle bundle = getIntent().getExtras();
                caEventType = bundle.getInt("caEventType", 0);
                caMsgType = bundle.getInt("caMsgType", 0);
                resId = bundle.getInt("resId", 0);
            }
            if (caMsgType == 0) {
                finish();
            } else {
                if (title == null) {
                    title = (TextView) findViewById(R.id.NoSiganlText);
                }
                title.setText(resId);
            }
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        if (mCcKeyToast != null) {
            mCcKeyToast.cancel();
        }
        myHandler.removeCallbacks(runnable);
        finish();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(mReceiver);
        super.onDestroy();
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
        System.out.println("\n--->indexBase: " + indexBase);
        System.out.println("\n--->m_nServiceNum: " + m_nServiceNum);
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

    public boolean isNeedToCheckExitRecord(ProgramInfo pi) {
        Log.e("qhc", "PVRActivity.currentRecordingProgrammFrency=");

        if (pi == null) {
            return false;
        }

        // check is recording
        final TvPvrManager pvr = TvPvrManager.getInstance();
        if (PVRActivity.currentRecordingProgrammFrency != pi.frequency
                && (pvr.isRecording() || pvr.isTimeShiftRecording())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPvrRecording() {
        final TvPvrManager pvr = TvPvrManager.getInstance();
        return pvr.isRecording() || pvr.isTimeShiftRecording();
    }

    /**
     * @param keyCode
     */
    public boolean onChannelChange(int keyCode) {
        if (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_ATV
                || TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV) {
            if (keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                // Log.e("qhc","ch- number:"+getPreviousProgram().number+" ch -servicetype :"+getPreviousProgram().serviceType);
                if (isNeedToCheckExitRecord(getPreviousProgram())) {
                    final TvPvrManager pvr = TvPvrManager.getInstance();
                    AlertDialog.Builder build = new AlertDialog.Builder(NoSignalActivity.this);
                    build.setMessage("Are you going to stop PVR recording?");
                    build.setPositiveButton("Stop", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (pvr.isTimeShiftRecording()) {
                                pvr.stopTimeShiftRecord();
                            } else if (pvr.isRecording()) {
                                pvr.stopRecord();
                            }
                            PVRActivity.currentRecordingProgrammFrency = -1;
                            mTvChannelManager.programDown();
                            startSourceInfo();
                        }
                    });
                    build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    build.create().show();

                }

                mTvChannelManager.programDown();
                startSourceInfo();
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP
                    || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                if (isNeedToCheckExitRecord(getNextProgramm())) {
                    final TvPvrManager pvr = TvPvrManager.getInstance();
                    AlertDialog.Builder build = new AlertDialog.Builder(NoSignalActivity.this);
                    build.setMessage("Are you going to stop PVR recording?");
                    build.setPositiveButton("Stop", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (pvr.isTimeShiftRecording()) {
                                pvr.stopTimeShiftRecord();
                            } else if (pvr.isRecording()) {
                                pvr.stopRecord();
                            }
                            PVRActivity.currentRecordingProgrammFrency = -1;
                            mTvChannelManager.programUp();
                            startSourceInfo();
                        }
                    });
                    build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                Log.i(TAG, "go to SourceInfoActivity and finish : " + TAG);
                finish();// add by zhihui.xing for bug 0402241 Destroy this
                         // activity when channelchange
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
                    Intent intentChannelControl = new Intent(this, ChannelControlActivity.class);
                    intentChannelControl.putExtra("KeyCode", keyCode);
                    this.startActivity(intentChannelControl);
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        CecSetting setting = mTvCecManager.getCecConfiguration();

        if (!Constant.lockKey && keyCode != KeyEvent.KEYCODE_BACK) {
            return true;
        }

        if ((setting.cecStatus == Constant.CEC_STATUS_ON) && (TvCommonManager.getInstance().isHdmiSignalMode() == true)) {
             if (mTvCecManager.sendCecKey(keyCode)) {
                Log.d(TAG, "onKeyDown return TRUE");
                return true;
             }
        }
        myHandler.removeCallbacks(runnable);
        myHandler.postDelayed(runnable, 15 * 60 * 1000);
        switch (keyCode) {
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
                    int ccResId = 0;
                    if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                        caption = TvCcManager.getInstance().getNextAtscCcMode();
                        ccResId = R.array.str_arr_setting_cc_mode_vals;
                    } else {
                        caption = TvCcManager.getInstance().getNextIsdbCcMode();
                        ccResId = R.array.str_arr_option_caption;
                    }
                    tv.setText(getResources().getString(R.string.str_option_caption)
                            + " "
                            + getResources().getStringArray(ccResId)[caption]);
                    mCcKeyToast.show();
                }
                break;
            case KeyEvent.KEYCODE_PROG_YELLOW:
                if (m_bScreenSaverStatus == ScreenSaverMode.DTV_SS_CA_NOTIFY) {

                    Intent intent = new Intent();
                    intent.setClass(this, EmailListActivity.class);
                    NoSignalActivity.this.startActivity(intent);
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
                    Log.d("RootActivity", "------------------------>deviceName is:" + deviceName);
                }
                break;

            case KeyEvent.KEYCODE_BACK: {
                if (RootActivity.getBackKeyStatus() == false) {
                    Log.d("TvApp", " NoSiganlActivity send Cmd_TvApkExit to RootActivity----------");
                    sendBroadcast(new Intent(TvIntent.ACTION_EXIT_TV_APK));
                }
                finish();
                return true;
            }

        }
        /* add by owen.qin begin to add response in nosignal activity */
        if (SwitchPageHelper.goToPvrBrowserPage(this, keyCode)) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToSourceInfo(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToPvrPage(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToSubtitleLangPage(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToAudioLangPage(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToProgrameListInfo(this, keyCode) == true) {
            finish();
            return true;
        }
        if (SwitchPageHelper.goToFavorateListInfo(this, keyCode) == true) {
            finish();
            return true;
        }
        if (onChannelChange(keyCode) == true) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // do stand by
            // myHandler.postDelayed(runnable, 15*60*1000);
            Log.i("NoSignalActivity", "=================NO SIGNAL STANDBY===============");
            tvCommonManager.standbySystem("standby");
        }
    };

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == CMD_SIGNAL_LOCK) {
                Log.d(TAG, "&&&&&------Cmd_Signal_Lock---------");
                Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
                startActivity(intent);
                finish();
            } else if (what == CMD_SHOW_SOURCE_INFO) {
                if (m_bScreenSaverStatus != ScreenSaverMode.DTV_SS_CA_NOTIFY) {
                    Log.d(TAG, "&&&&&------Cmd_CommonVedio---------");
                    Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
                    startActivity(intent);
                    finish();
                }
            } else if (what == CMD_REDRAW) {
                Log.d(TAG, "&&&&&------Cmd_Redraw---------");
                if (RootActivity.mExitDialog != null) {
                    if (RootActivity.mExitDialog.isShowing()) {
                        RootActivity.mExitDialog.dismiss();
                    }
                }
                if ((MainMenuActivity.getInstance() != null)
                        && MainMenuActivity.bMainMenuFocused == true) {
                    finish();
                }
                redrawComponents();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                isGoingToBeClosed(true);
                if (data.getExtras().getBoolean("result")) {
                    finish();
                } else {
                    title.setText(R.string.str_parental_block);
                }
        }
    }

    /**
     * Open SourceInfoActivity Added by gerard.jiang for "0396073" in 2013/05/18
     */
    private void startSourceInfo() {
        boolean isSystemLocked = true;
        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
            isSystemLocked = TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus();
        } else {
            isSystemLocked = TvParentalControlManager.getInstance().isSystemLock();
        }

        boolean isCurrentProgramLocked = mTvChannelManager.getCurrentProgramInfo().isLock;
        if (!(isSystemLocked && isCurrentProgramLocked)) {
            Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
            intent.putExtra("info_key", true);
            startActivity(intent);
        }
    }
}
