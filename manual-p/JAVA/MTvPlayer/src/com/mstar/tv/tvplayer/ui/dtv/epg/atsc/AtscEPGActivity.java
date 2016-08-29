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

package com.mstar.tv.tvplayer.ui.dtv.epg.atsc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import com.mstar.android.tv.TvEpgManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.HbbtvEventInfo;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvType.EnumEpgDescriptionType;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;
import com.mstar.tv.tvplayer.ui.holder.EPGViewHolder;
import com.mstar.tvframework.MstarBaseActivity;

public class AtscEPGActivity extends MstarBaseActivity {

    private static final String TAG = "AtscEPGActivity";

    private int mTvSystem = 0;

    private final static short EPG_UPDATE_LIST = 0x01;

    private final static short EPG_UPDATE_LIST_NOTIFY = 0x08;

    private final static short EPG_ITEM_COUNT_PER_PAGE = 9999;

    private final static int TIME_OUT_MSG = 51;

    private ArrayList<EPGViewHolder> mEpgEventViewHolderList = new ArrayList<EPGViewHolder>();

    private ArrayList<EPGViewHolder> mEpgProgramViewHolderList = new ArrayList<EPGViewHolder>();

    private List<AtscEpgEventInfo> mEventInfoList = new ArrayList<AtscEpgEventInfo>();

    private ArrayList<ProgramInfo> mProgInfoList = new ArrayList<ProgramInfo>();

    private boolean mIsEpgChannel = true;

    private ListView mEpgListView;

    private AtscEPGAdapter mEventAdapter;

    private AtscEPGAdapter mProgramAdapter;

    private TextView mComDateNameTextView = null;

    private TvChannelManager mTvChannelManager = null;

    private TvAtscChannelManager mTvAtscChannelManager= null;

    private TextView mTimeInfo = null;

    private TextView mContext = null;

    private TextView mTitle = null;

    private LinearLayout mInfoLay = null;

    private TextView mRatingTxt = null;

    private static int mUserProgramIndex = 0;

    private static int mUserEventIndex = 0;

    private static long mOffsetTimeInMs = 0;

    private boolean mInfoDisplay = false;

    private Time mNextEventBaseTime = null;

    private Time mNextProgramBaseTime = null;

    private int mTotalEventCount = 0;

    private int mTotalProgramCount = 0;

    private int mPrevConstructEventCount = 0;

    private int mPrevConstructProgramCount = 0;

    private TimeOutHelper mTimeOutHelper;

    private TvEpgManager mTvEpgManager = null;

    private MyHandler mMyTvHandler = null;

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
            AtscEPGActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    refreshProgrameGuideListChannel();
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

    private void setEventData(String[] str_name, String[] str_info, boolean b_cc) {
        for (int i = 0; i < str_name.length; i++) {
            EPGViewHolder vh = new EPGViewHolder();
            vh.setChannelInfo(str_info[i]);
            vh.setChannelName(str_name[i]);
            vh.setCcInfo(b_cc);
            // vh.setInfo(info[i]);
            mEpgEventViewHolderList.add(vh);
        }
    }

    private void setProgramData(String[] str_name, String[] str_info, boolean b_cc) {
        for (int i = 0; i < str_name.length; i++) {
            EPGViewHolder vh = new EPGViewHolder();
            vh.setChannelInfo(str_info[i]);
            vh.setChannelName(str_name[i]);
            vh.setCcInfo(b_cc);
            // vh.setInfo(info[i]);
            mEpgProgramViewHolderList.add(vh);
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            super.handleMessage(msg);
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();

        setContentView(R.layout.atscprogramme_epg);
        mEpgListView = (ListView) findViewById(R.id.atscprogramme_epg_list_view);

        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            int focusIdx = getIntent().getIntExtra("FocusIndex", 0);
        }

        mEventAdapter = new AtscEPGAdapter(this, mEpgEventViewHolderList);
        mProgramAdapter = new AtscEPGAdapter(this, mEpgProgramViewHolderList);
        if (mIsEpgChannel == true) {
            mEpgListView.setAdapter(mEventAdapter);
        } else {
            mEpgListView.setAdapter(mProgramAdapter);
        }
        mEpgListView.setDividerHeight(0);
        mComDateNameTextView = (TextView) findViewById(R.id.atscprogramme_epg_context);
        mTitle = (TextView) findViewById(R.id.atscprogramme_epg_title);
        mTimeInfo = (TextView) findViewById(R.id.atscprogramme_epg_info_time);
        mContext = (TextView) findViewById(R.id.atscprogramme_epg_info_context);
        mInfoLay = (LinearLayout) findViewById(R.id.atscprogramme_epg_info_layout);
        mRatingTxt = (TextView) findViewById(R.id.atscprogramme_epg_rating);

        mInfoLay.setVisibility(View.INVISIBLE);

        ProgramInfo curProgInfo = new ProgramInfo();
        TVRootApp app = (TVRootApp) getApplication();
        mTvChannelManager = TvChannelManager.getInstance();
        mTvAtscChannelManager = TvAtscChannelManager.getInstance();
        mTvEpgManager = TvEpgManager.getInstance();
        resetAllNextBaseTime(true);
        mMyTvHandler = new MyHandler();

        // get cur time zone offset time
        Time CurTime = new Time();
        CurTime.setToNow();
        CurTime.toMillis(true);
        mOffsetTimeInMs = mTvEpgManager.getEpgEventOffsetTime(CurTime, true) * 1000;
        Log.d(TAG, "mOffsetTimeInMs:"  + mOffsetTimeInMs);

        curProgInfo = mTvAtscChannelManager.getCurrentProgramInfo();
        int programCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);
        mUserProgramIndex = 0;
        mProgInfoList.clear();
        for (int i = 0, j = 0; i < programCount; i++) {
            ProgramInfo tmpProgInfo = mTvAtscChannelManager.getProgramInfo(i);
            if (tmpProgInfo.serviceType == TvChannelManager.SERVICE_TYPE_DTV) {
                if ((curProgInfo.majorNum == tmpProgInfo.majorNum)
                    && (curProgInfo.minorNum == tmpProgInfo.minorNum)) {
                    mUserProgramIndex = j;
                }
                mProgInfoList.add(tmpProgInfo);
                j++;
            }
        }
        Log.d(TAG, "mProgInfoList.size(): " + mProgInfoList.size());
        Log.d(TAG, "mUserProgramIndex: " + mUserProgramIndex);
        Log.d(TAG, "mIsEpgChannel: " + mIsEpgChannel);

        registerListener();

        if (mIsEpgChannel) {
            mTitle.setText(R.string.str_epg_title_channel);
            updateChannelNumber();

        } else {
            mTitle.setText(R.string.str_epg_title_time);
            updateDateTime(0);
        }

        // time out to kill this
        mTimeOutHelper = new TimeOutHelper(epgHandler, this);
    }

    private Handler epgHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d(TAG, "Handler: " + msg.what);

            switch (msg.what) {
                case EPG_UPDATE_LIST_NOTIFY:
                    resetAllNextBaseTime(true);
                    constructmEventInfoList();
                    mEventAdapter.notifyDataSetChanged();
                    break;

                case EPG_UPDATE_LIST:
                    if (mIsEpgChannel == true) {
                        constructmEventInfoList();
                        mEventAdapter.notifyDataSetChanged();
                    } else {
                        constructProgramInfoList(null);
                        mProgramAdapter.notifyDataSetChanged();
                    }

                    mEpgListView.invalidate();
                    break;
                case TIME_OUT_MSG:
                    finish();
                    break;

                default:
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        mTimeOutHelper.start();
        mTimeOutHelper.init();
        mTvPlayerEventListener = new TvPlayerEventListener();
        mTvChannelManager.registerOnTvPlayerEventListener(mTvPlayerEventListener);
        if (mIsEpgChannel) {
            refreshProgrameGuideListChannel();
        } else {
            refreshProgrameGuideListTime(0);
        }
    };

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        mTimeOutHelper.stop();
        mTvChannelManager.unregisterOnTvPlayerEventListener(mTvPlayerEventListener);
        mTvPlayerEventListener = null;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mTimeOutHelper.reset();
        Log.d(TAG, "onKeyDown: " + event);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mInfoLay.getVisibility() == View.VISIBLE) {
                    mInfoDisplay = false;
                    mContext.setText("");
                    mInfoLay.setVisibility(View.GONE);
                } else {
                    finish();
                }
                return true;

            case KeyEvent.KEYCODE_PROG_GREEN: // Info
                if (mEpgListView.isFocused()) {
                    mInfoDisplay = !mInfoDisplay;
                    if (!mInfoDisplay) {
                        if (mInfoLay.getVisibility() == View.VISIBLE) {
                            mContext.setText("");
                            mInfoLay.setVisibility(View.GONE);
                        }
                    } else {
                        updateEpgProgramInfo(keyCode);
                    }
                }
                return true;

            case KeyEvent.KEYCODE_PROG_YELLOW:
                return true;

            case KeyEvent.KEYCODE_PROG_RED:
                return true;

            case KeyEvent.KEYCODE_PROG_BLUE:
                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (!mComDateNameTextView.isFocused()) {
                    return true;
                }
                if (mInfoLay.getVisibility() == View.VISIBLE) {
                    mContext.setText("");
                    mInfoLay.setVisibility(View.GONE);
                }

                if (mComDateNameTextView.isFocused()) {

                    if (mIsEpgChannel) {
                        changeProgramNumber(true);
                    } else {
                        changeEventStartTime(true);
                    }
                } else {
                    mIsEpgChannel = !mIsEpgChannel;

                    if (mIsEpgChannel == true) {
                        mEpgListView.setAdapter(mEventAdapter);
                    } else {
                        mEpgListView.setAdapter(mProgramAdapter);
                    }
                    resetAllNextBaseTime(false);
                    if (mIsEpgChannel) {
                        mTitle.setText(R.string.str_epg_title_channel);
                        constructProgrameGuideListChannel();
                    } else {
                        mTitle.setText(R.string.str_epg_title_time);
                        constructProgrameGuideListTime(0);
                    }
                }
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (!mComDateNameTextView.isFocused()) {
                    return true;
                }
                if (mInfoLay.getVisibility() == View.VISIBLE) {
                    mContext.setText("");
                    mInfoLay.setVisibility(View.GONE);
                }

                if (mComDateNameTextView.isFocused()) {
                    if (mIsEpgChannel) {
                        changeProgramNumber(false);
                    } else {
                        changeEventStartTime(false);
                    }

                } else {
                    mIsEpgChannel = !mIsEpgChannel;

                    if (mIsEpgChannel == true) {
                        mEpgListView.setAdapter(mEventAdapter);
                    } else {
                        mEpgListView.setAdapter(mProgramAdapter);
                    }
                    resetAllNextBaseTime(false);
                    if (mIsEpgChannel) {
                        mTitle.setText(R.string.str_epg_title_channel);
                        constructProgrameGuideListChannel();
                    } else {
                        mTitle.setText(R.string.str_epg_title_time);
                        constructProgrameGuideListTime(0);
                    }
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerListener() {
        mEpgListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mTimeOutHelper.reset();
                // TODO Auto-generated method stub
                if (mIsEpgChannel == false) {
                    // go to the selected channel
                    ProgramInfo curProgInfo = new ProgramInfo();
                    TVRootApp app = (TVRootApp) getApplication();
                    if (0xffffffff == mEpgListView.getSelectedItemPosition()) {
                        return;
                    }

                    curProgInfo = mTvAtscChannelManager.getProgramInfo(mEpgListView.getSelectedItemPosition());

                    mTvAtscChannelManager.programSel(curProgInfo.majorNum, curProgInfo.minorNum);
                    resetAllNextBaseTime(true);
                }
            }
        });

        mEpgListView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mInfoDisplay) {
                        updateEpgProgramInfo(KeyEvent.KEYCODE_PROG_GREEN);
                    }
                } else {
                    if (mInfoLay.getVisibility() == View.VISIBLE) {
                        mContext.setText("");
                        mInfoLay.setVisibility(View.GONE);
                    }
                }
            }
        });
        mEpgListView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                mTimeOutHelper.reset();
                if (mInfoDisplay) {
                    int curFocusIdx = mEpgListView.getSelectedItemPosition();
                    if (((keyCode == KeyEvent.KEYCODE_DPAD_UP) || (keyCode == KeyEvent.KEYCODE_DPAD_DOWN))
                            && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (0xffffffff != curFocusIdx) {
                            updateEpgProgramInfo(keyCode);
                        }
                    }
                }
                return false;
            }
        });

        mTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mInfoLay.getVisibility() == View.VISIBLE) {
                        mContext.setText("");
                        mInfoLay.setVisibility(View.GONE);
                    }
                } else {
                    if (mEpgEventViewHolderList.isEmpty()) {
                        if (mInfoLay.getVisibility() == View.VISIBLE) {
                            mContext.setText("");
                            mInfoLay.setVisibility(View.GONE);
                        }
                    } else {
                        mContext.setText(mEpgEventViewHolderList.get(0).getInfo());
                    }
                }
            }
        });
    }

    private void resetAllNextBaseTime(boolean initAll) {
        Log.d(TAG, "resetAllNextBaseTime, initAll: " + initAll);
        Log.d(TAG, "resetAllNextBaseTime, mIsEpgChannel: " + mIsEpgChannel);
        if (initAll == true) {
            mNextEventBaseTime = null;
            mPrevConstructEventCount = 0;
            mTotalEventCount = 0;
            mNextProgramBaseTime = null;
            mPrevConstructProgramCount = 0;
            mTotalProgramCount = 0;
        } else {
            if (mIsEpgChannel == true) {
                mNextEventBaseTime = null;
                mPrevConstructEventCount = 0;
                mTotalEventCount = 0;
            } else {
                mNextProgramBaseTime = null;
                mPrevConstructProgramCount = 0;
                mTotalProgramCount = 0;
            }
        }
    }

    private boolean constructmEventInfoList() {
        Log.d(TAG, "constructmEventInfoList()");
        ProgramInfo curProgInfo = new ProgramInfo();
        AtscEpgEventInfo epgEventInfo = new AtscEpgEventInfo();
        List<AtscEpgEventInfo> mEventInfoListTemp = new ArrayList<AtscEpgEventInfo>();

        if ((mPrevConstructEventCount >= mTotalEventCount) && (mTotalEventCount > 0)) {
            // construct finish
            return false;
        }
        curProgInfo = mTvAtscChannelManager.getCurrentProgramInfo();

        // initial
        if (mNextEventBaseTime == null) {
            mNextEventBaseTime = new Time();
            mNextEventBaseTime.setToNow();
            mNextEventBaseTime.set(mNextEventBaseTime.toMillis(true));

            mEpgEventViewHolderList.clear();
            mEventInfoList.clear();

            Log.d(TAG, "mNextEventBaseTime :" + mNextEventBaseTime);
            mTotalEventCount = mTvEpgManager.getAtscEventCount(curProgInfo.majorNum, curProgInfo.minorNum,
                (int)curProgInfo.serviceType, curProgInfo.progId, mNextEventBaseTime);

            mPrevConstructEventCount = 0;
        } else if (mTotalEventCount == 0) {
            mTotalEventCount = mTvEpgManager.getAtscEventCount(curProgInfo.majorNum, curProgInfo.minorNum,
                (int)curProgInfo.serviceType, curProgInfo.progId, mNextEventBaseTime);
            mPrevConstructEventCount = 0;
        }
        if (mTotalEventCount == 0) {
            return false;
        }

        mEventInfoListTemp.clear();
        mEventInfoListTemp = getEpgEvents();
        if (mEventInfoListTemp != null) {
            for (int i = 0; i < mEventInfoListTemp.size(); i++) {
                mEventInfoList.add(mEventInfoListTemp.get(i));

                // push event start time -> end time & event name in list
                String[] eventNameStr = {
                    null
                };
                String[] mTimeInfoStr = {
                    null
                };

                epgEventInfo = mEventInfoListTemp.get(i);
                eventNameStr[0] = epgEventInfo.sName;

                Time startTime = new Time();
                startTime.set((long) epgEventInfo.startTime * 1000 - mOffsetTimeInMs);
                Time endTime = new Time();
                endTime.set((long) epgEventInfo.endTime * 1000 - mOffsetTimeInMs);
                mTimeInfoStr[0] = startTime.hour + ":" + startTime.minute + "-" + endTime.hour + ":"
                        + endTime.minute;
                setEventData(eventNameStr, mTimeInfoStr, epgEventInfo.bHasCCInfo);
            }
            if(mEventInfoListTemp.size() > 0)
            {
                // store current last event's end time
                epgEventInfo = mEventInfoListTemp.get(mEventInfoListTemp.size() - 1);
                mNextEventBaseTime.set((long) epgEventInfo.endTime * 1000 - mOffsetTimeInMs);
                mPrevConstructEventCount += EPG_ITEM_COUNT_PER_PAGE;
            }
        }
        return true;
    }

    private boolean constructProgramInfoList(Time baseTime) {
        Log.d(TAG, "constructProgramInfoList()");
        int availableProgramCount = 0;
        ProgramInfo specificProgInfo = new ProgramInfo();
        AtscEpgEventInfo epgEventInfo = new AtscEpgEventInfo();
        List<AtscEpgEventInfo> mEventInfoListTemp = new ArrayList<AtscEpgEventInfo>();

        if ((mPrevConstructProgramCount >= mTotalProgramCount) && (mTotalProgramCount > 0)) {
            // construct finish
            return false;
        }

        // initial
        if (baseTime != null) {
            if (mNextProgramBaseTime == null) {
                mNextProgramBaseTime = new Time();
            }
            mNextProgramBaseTime.set(baseTime.toMillis(true) - mOffsetTimeInMs);
            mEpgProgramViewHolderList.clear();
            mTotalProgramCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            mPrevConstructProgramCount = 0;
        } else {
            if (mNextProgramBaseTime == null) {
                mNextProgramBaseTime = new Time();
                mNextProgramBaseTime.setToNow();
                mNextProgramBaseTime.set(mNextProgramBaseTime.toMillis(true));
                mEpgProgramViewHolderList.clear();
                mTotalProgramCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
                mPrevConstructProgramCount = 0;
            } else if (mTotalProgramCount == 0) {
                mTotalProgramCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
                mPrevConstructProgramCount = 0;
            }
        }

        if (mTotalProgramCount == 0) {
            return false;
        }
        Log.d(TAG, "constructProgramInfoList(): mTotalProgramCount: " + mTotalProgramCount);

        for (int i = mPrevConstructProgramCount; i < mTotalProgramCount; i++) {
            specificProgInfo = mTvAtscChannelManager.getProgramInfo(i);

            if (i == (mTotalProgramCount - 1)) {
                mPrevConstructProgramCount = i + 1;
            }

            if (specificProgInfo == null) {
                continue;
            }

            if ((specificProgInfo.isDelete == true) || (specificProgInfo.isVisible == false)) {
                continue;
            }

            if (mEventInfoListTemp != null) {
                mEventInfoListTemp.clear();
                mEventInfoListTemp = getEpgEvents();

                epgEventInfo = mEventInfoListTemp.get(0);
            }
            if (epgEventInfo == null) {
                continue;
            }

            // push service name & event name in list,
            String[] eventNameStr = {
                null
            };
            String[] serviceInfoStr = {
                null
            };
            eventNameStr[0] = epgEventInfo.sName;
            serviceInfoStr[0] = specificProgInfo.number + "  " + specificProgInfo.serviceName;
            setProgramData(eventNameStr, serviceInfoStr, false);

            availableProgramCount++;
            if (availableProgramCount >= EPG_ITEM_COUNT_PER_PAGE) {
                mPrevConstructProgramCount = i + 1;
                break;
            }
        }

        return true;
    }

    private void updateChannelNumber() {
        ProgramInfo curProgInfo = new ProgramInfo();

        // get event info
        Time baseTime = new Time();
        baseTime.setToNow();
        baseTime.set(baseTime.toMillis(true));

        // show cur service number
        curProgInfo = mTvAtscChannelManager.getCurrentProgramInfo();

        String channum = mTvAtscChannelManager.getDispChannelNum(curProgInfo);

        mComDateNameTextView.setText(channum);
    }

    private void updateDateTime(long timeInSec) {
        // show date&time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Time stTime = getSTTime(timeInSec);
        Date curDate = new Date(stTime.toMillis(true));
        String dateStr = formatter.format(curDate);

        mComDateNameTextView.setText(dateStr);
    }

    private boolean constructProgrameGuideListChannel() {
        Log.d(TAG, "constructProgrameGuideListChannel()");

        updateChannelNumber();
        constructmEventInfoList();
        mEventAdapter.notifyDataSetChanged();
        mEpgListView.invalidate();
        return true;
    }

    private boolean constructProgrameGuideListTime(long timeInSec) {
        Log.d(TAG, "constructProgrameGuideListTime()");
        updateDateTime(timeInSec);
        constructProgramInfoList(getSTTime(timeInSec));
        mProgramAdapter.notifyDataSetChanged();
        mEpgListView.invalidate();
        return true;
    }

    public void changeProgramNumber(boolean isLeftKey) {
        TVRootApp app = (TVRootApp) getApplication();
        ProgramInfo progInfo = new ProgramInfo();
        // String tmpStr = null;

        int tmpindex = mUserProgramIndex;
        while (true) {
            if (isLeftKey) {
                if (mUserProgramIndex > 0) {
                    mUserProgramIndex--;
                } else {
                    mUserProgramIndex = mProgInfoList.size() - 1;
                }
                Log.d(TAG, "changeProgramNumber() mUserProgramIndex: " + mUserProgramIndex);

            } else {
                if (mUserProgramIndex < (mProgInfoList.size() - 1)) {
                    mUserProgramIndex++;
                } else {
                    mUserProgramIndex = 0;
                }
            }

            Log.d(TAG, "mUserProgramIndex:"  + mUserProgramIndex);

            progInfo = mProgInfoList.get(mUserProgramIndex);

            if (progInfo.isDelete || progInfo.isHide || progInfo.isSkip || !progInfo.isVisible) {
                if (tmpindex == mUserProgramIndex) {
                    break;
                } else {
                    continue;
                }

            } else {
                break;
            }
        }
        mTvAtscChannelManager.programSel(progInfo.majorNum, progInfo.minorNum);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        resetAllNextBaseTime(true);
        constructProgrameGuideListChannel();
    }

    public void changeEventStartTime(boolean isLeftKey) {
        AtscEpgEventInfo epgEventInfo = new AtscEpgEventInfo();

        if (mEventInfoList.size() == 0) {
            return;
        }
        if (isLeftKey) {
            if (mUserEventIndex > 0) {
                mUserEventIndex--;
            } else {
                mUserEventIndex = mEventInfoList.size() - 1;
            }
        } else {
            if (mUserEventIndex < (mEventInfoList.size() - 1)) {
                mUserEventIndex++;
            } else {
                mUserEventIndex = 0;
            }
        }

        epgEventInfo = mEventInfoList.get(mUserEventIndex);

        resetAllNextBaseTime(false);
        constructProgrameGuideListTime(epgEventInfo.startTime);
    }

    public boolean updateEpgProgramInfo(int keycode) {
        if (mIsEpgChannel) {
            ProgramInfo curProgInfo = new ProgramInfo();
            AtscEpgEventInfo epgEventInfo = new AtscEpgEventInfo();
            TVRootApp app = (TVRootApp) getApplication();
            int curFocusIdx = mEpgListView.getSelectedItemPosition();
            if (0xffffffff == curFocusIdx) {
                return true;
            }

            if (keycode == KeyEvent.KEYCODE_DPAD_UP) {
                if (0 != curFocusIdx) {
                    curFocusIdx--;
                } else {
                    return true;
                }
            } else if (keycode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if ((curFocusIdx + 1) < mEventInfoList.size()) {
                    curFocusIdx++;
                } else {
                    return true;
                }
            }
            curProgInfo = mTvAtscChannelManager.getCurrentProgramInfo();
            if (curFocusIdx >= mEventInfoList.size()) {
                mContext.setText("");
                return true;
            }
            epgEventInfo = mEventInfoList.get(curFocusIdx);
            // show event start time and end time
            Time startTime = new Time();
            // startTime.switchTimezone("Euro/London");
            startTime.set((long) epgEventInfo.startTime * 1000 - mOffsetTimeInMs); // s->ms

            Time endTime = new Time();
            // endTime.switchTimezone("Euro/London");
            endTime.set((long) epgEventInfo.endTime * 1000 - mOffsetTimeInMs); // s->ms
            String mTimeInfoStr = startTime.year + "/" + (startTime.month + 1) + "/"
                    + startTime.monthDay + " " + startTime.hour + ":" + startTime.minute + "-"
                    + endTime.hour + ":" + endTime.minute;
            mTimeInfo.setText(mTimeInfoStr);

            mRatingTxt.setText(mTvAtscChannelManager.getCurrentRatingInformation());

            // show event description
            Log.d(TAG, "startTime :" + startTime);
            AtscEpgEventInfo epgEvInfoExted = mTvEpgManager.getEventExtendInfoByTime(
                curProgInfo.majorNum, curProgInfo.minorNum, (int)curProgInfo.serviceType,
                curProgInfo.progId, startTime);
            if (epgEvInfoExted == null) {
                Log.e(TAG, "AtscEpgEventInfo: no data");
                mContext.setText(R.string.str_epg_event_extend_no_info);
            } else {
                mContext.setText(epgEvInfoExted.sExtendedText);
            }
            mInfoLay.setVisibility(View.VISIBLE);
        } else {
            ProgramInfo curProgInfo = new ProgramInfo();
            AtscEpgEventInfo epgEventInfo = new AtscEpgEventInfo();
            TVRootApp app = (TVRootApp) getApplication();
            int curFocusIdx = 0;
            /*
             * if (LoadCurFocusChannelEventList() == false) { return true; }
             */

            curProgInfo = mTvAtscChannelManager.getCurrentProgramInfo();
            if (curFocusIdx >= mEventInfoList.size()) {
                return true;
            }
            epgEventInfo = mEventInfoList.get(curFocusIdx);

            // show event start time and end time
            Time startTime = new Time();
            // startTime.switchTimezone("Euro/London");
            startTime.set((long) epgEventInfo.startTime * 1000 - mOffsetTimeInMs); // s->ms
            Time endTime = new Time();
            // endTime.switchTimezone("Euro/London");
            endTime.set((long) epgEventInfo.endTime * 1000 - mOffsetTimeInMs); // s->ms
            String mTimeInfoStr = startTime.year + "/" + (startTime.month + 1) + "/"
                    + startTime.monthDay + " " + startTime.hour + ":" + startTime.minute + "-"
                    + endTime.hour + ":" + endTime.minute;
            mTimeInfo.setText(mTimeInfoStr);

            // show event description

            if (startTime.toMillis(true) > mOffsetTimeInMs) {
                startTime.set(startTime.toMillis(true) - mOffsetTimeInMs);
            }

            Log.d(TAG, "startTime :" + startTime);
            AtscEpgEventInfo epgEvInfoExted = mTvEpgManager.getEventExtendInfoByTime(
                curProgInfo.majorNum, curProgInfo.minorNum, (int)curProgInfo.serviceType,
                curProgInfo.progId, startTime);
            if (epgEvInfoExted == null) {
                Log.e(TAG, "AtscEpgEventInfo: no data");
                mContext.setText(R.string.str_epg_event_extend_no_info);
            } else {
                mContext.setText(epgEvInfoExted.sExtendedText);
            }

            mInfoLay.setVisibility(View.VISIBLE);
        }
        return false;
    }

    private List<AtscEpgEventInfo> getEpgEvents() {
        List<AtscEpgEventInfo> eventInfos = new ArrayList<AtscEpgEventInfo>();
        Time currTime;
        currTime = new Time();
        currTime.setToNow();
        currTime.set(currTime.toMillis(true));
        ProgramInfo channelInfo = mTvAtscChannelManager.getCurrentProgramInfo();
        boolean canBegin = mTvEpgManager.beginToGetEventInformation(channelInfo.number,
                channelInfo.majorNum, channelInfo.minorNum, channelInfo.progId);
        Log.e(TAG, "the flag for begining to get epg information:" + canBegin + ",channelnum:"
                + channelInfo.majorNum + "-" + channelInfo.minorNum);
        if (canBegin) {
            int count = mTvEpgManager.getAtscEventCount(channelInfo.majorNum, channelInfo.minorNum,
                    channelInfo.number, channelInfo.progId, currTime);
            Log.e(TAG, "getEventCount:" + count);
            if (count > 0) {
                AtscEpgEventInfo info;
                // currTime.set(currTime.toMillis(true) + (long) offset * 1000);
                Log.e(TAG, "getFirstEventInformation, currTime: " + currTime);
                info = mTvEpgManager.getFirstEventInformation(currTime);
                Log.e(TAG, "adjust the first event information is null:" + (info == null)
                        + ",epg event count:" + count);
                do {
                    if (info == null) {
                        Log.e(TAG, "AtscEpgEventInfo: no data");
                        break;
                    } else {
                        if (eventInfos.size() == 0) {
                            info.endTime = info.startTime + info.durationTime;
                            Log.e(TAG, "epg event, info.startTime: " + info.startTime);
                            Log.e(TAG, "epg event, info.endTime: " + info.endTime);
                            Log.e(TAG, "epg event, info.durationTime: " + info.durationTime);
                            eventInfos.add(info);
                        } else {
                            AtscEpgEventInfo lastInfo = eventInfos.get(eventInfos.size() - 1);
                            // to filter the repeat EPG event information.
                            Log.i(TAG, info.sName);
                            if (lastInfo.startTime != info.startTime) {
                                info.endTime = info.startTime + info.durationTime;
                                Log.e(TAG, "epg event, info.startTime: " + info.startTime);
                                Log.e(TAG, "epg event, info.endTime: " + info.endTime);
                                Log.e(TAG, "epg event, info.durationTime: " + info.durationTime);
                                eventInfos.add(info);
                            }
                        }
                    }

                    count--;
                    if (count <= 0) {
                        break;
                    }

                    info = mTvEpgManager.getNextEventInformation();
                } while (true);
            }
        }
        mTvEpgManager.endToGetEventInformation();

        return eventInfos;
    }

    private void refreshProgrameGuideListChannel() {
        resetAllNextBaseTime(true);
        constructmEventInfoList();
        mEventAdapter.notifyDataSetChanged();
        mEpgListView.invalidate();
        if (mInfoDisplay) {
            updateEpgProgramInfo(KeyEvent.KEYCODE_PROG_GREEN);
        }
        mTimeOutHelper.reset();
    }

    private void refreshProgrameGuideListTime(long timeInSec) {
        resetAllNextBaseTime(true);
        constructProgramInfoList(getSTTime(timeInSec));
        mProgramAdapter.notifyDataSetChanged();
        mEpgListView.invalidate();
    }

    private Time getSTTime(long timeInSec) {
        // get st time
        Time stTime = new Time();
        if (timeInSec == 0) {
            // use default time
            stTime.setToNow();
        } else {
            // use specific time
            stTime.set(timeInSec * 1000);
        }
        return stTime;
    }
}
