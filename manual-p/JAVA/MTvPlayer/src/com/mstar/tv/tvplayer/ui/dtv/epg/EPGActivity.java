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

package com.mstar.tv.tvplayer.ui.dtv.epg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.dtv.vo.EpgEventInfo;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvEpgManager;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.tv.tvplayer.ui.holder.EPGViewHolder;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;
import com.mstar.tv.tvplayer.ui.TVRootApp;

public class EPGActivity extends MstarBaseActivity {
    private static final String TAG = "EPGActivity";

    private final static short EPG_UPDATE_LIST = 0x01;

    private final static short EPG_ITEM_COUNT_PER_PAGE = 14;

    private final static int TIME_OUT_MSG = 51;

    private final static int REFRESH_TIME_MS = 1000;

    private int mTvSystem = 0;

    private ArrayList<EPGViewHolder> epgEventViewHolderList = new ArrayList<EPGViewHolder>();

    private ArrayList<EPGViewHolder> epgProgramViewHolderList = new ArrayList<EPGViewHolder>();

    private List<EpgEventInfo> eventInfoList = new ArrayList<EpgEventInfo>();

    private boolean isEpgChannel = true;

    private Intent intent = null;

    private ListView epgListView;

    private EPGAdapter eventAdapter;

    private EPGAdapter programAdapter;

    private TextView comDateNameTextView = null;

    private TextView timeinfo = null;

    private TextView context = null;

    private TextView title = null;

    private LinearLayout info_lay = null;

    private static int userProgramIndex = 0;

    private static int userEventIndex = 0;

    private boolean infodisplay = false;

    private Time nextEventBaseTime = null;

    private Time nextProgramBaseTime = null;

    private int totalEventCount = 0;

    private int totalProgramCount = 0;

    private int prevConstructEventCount = 0;

    private int prevConstructProgramCount = 0;

    private Thread getEpgDataThread = null;

    private static boolean enableEpgDataThread = false;

    private TimeOutHelper timeOutHelper;

    private TvEpgManager mTvEpgManager = null;

    private TvChannelManager mTvChannelManager = null;

    private ProgramInfo mCurProgInfo = null;

    private boolean mIsPvrEnable = false;

    private ImageView mDot1 = null;

    private ImageView mDot2 = null;

    private void setEventData(int channelNumber, String[] str_name, String[] str_info) {
        for (int i = 0; i < str_name.length; i++) {
            EPGViewHolder vh = new EPGViewHolder();
            vh.setChannelInfo(str_info[i]);
            vh.setChannelName(str_name[i]);
            vh.setChannelNum(channelNumber);
            epgEventViewHolderList.add(vh);
        }
    }

    private void setProgramData(int channelNumber, String[] str_name, String[] str_info) {
        for (int i = 0; i < str_name.length; i++) {
            EPGViewHolder vh = new EPGViewHolder();
            vh.setChannelInfo(str_info[i]);
            vh.setChannelName(str_name[i]);
            vh.setChannelNum(channelNumber);
            epgProgramViewHolderList.add(vh);
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programme_epg);
        epgListView = (ListView) findViewById(R.id.programme_epg_list_view);

        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            int focusIdx = getIntent().getIntExtra("FocusIndex", 0);
        }

        eventAdapter = new EPGAdapter(this, epgEventViewHolderList);
        programAdapter = new EPGAdapter(this, epgProgramViewHolderList);
        if (isEpgChannel == true) {
            epgListView.setAdapter(eventAdapter);
        } else {
            epgListView.setAdapter(programAdapter);
        }
        epgListView.setDividerHeight(0);
        comDateNameTextView = (TextView) findViewById(R.id.programme_epg_context);
        title = (TextView) findViewById(R.id.programme_epg_title);
        timeinfo = (TextView) findViewById(R.id.programme_epg_info_time);
        context = (TextView) findViewById(R.id.programme_epg_info_context);
        info_lay = (LinearLayout) findViewById(R.id.programme_epg_info_layout);
        info_lay.setVisibility(View.INVISIBLE);
        TVRootApp app = (TVRootApp) getApplication();
        mIsPvrEnable = app.isPVREnable();
        if (!mIsPvrEnable) {
            ImageView redImage = (ImageView) findViewById(R.id.programme_epg_tips_red_image);
            redImage.setVisibility(View.GONE);
            TextView redText = (TextView) findViewById(R.id.programme_epg_tips_red_text);
            redText.setVisibility(View.GONE);
        }
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mCurProgInfo = new ProgramInfo();
        mTvChannelManager = TvChannelManager.getInstance();
        mTvEpgManager = TvEpgManager.getInstance();
        resetAllNextBaseTime(true);
        mTvEpgManager.enableEpgBarkerChannel();

        // get cur time zone offset time
        Time CurTime = new Time();
        CurTime.setToNow();
        CurTime.toMillis(true);
        mCurProgInfo = mTvChannelManager.getCurrentProgramInfo();
        int dtvProgramCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
        ProgramInfo tmpProgInfo = new ProgramInfo();
        userProgramIndex = 0;

        for (int i = 0; i < dtvProgramCount; i++) {
            tmpProgInfo = mTvChannelManager.getProgramInfoByIndex(i);
            if ((mCurProgInfo.serviceType == tmpProgInfo.serviceType)
                    && (mCurProgInfo.number == tmpProgInfo.number)) {
                userProgramIndex = i;
                break;
            }
        }

        Log.i(TAG, "userProgramIndex:" + userProgramIndex);

        registerListener();

        if (isEpgChannel) {
            title.setText(R.string.str_epg_title_channel);
            constructProgrameGuideListChannel(mCurProgInfo);

        } else {
            title.setText(R.string.str_epg_title_time);
            constructProgrameGuideListTime(0);
        }

        enableEpgDataThread = true;
        getEpgDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (enableEpgDataThread) {
                    try {
                        Thread.sleep(REFRESH_TIME_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    epgHandler.sendEmptyMessageDelayed(EPG_UPDATE_LIST, 20);
                }
            }
        });

        if (getEpgDataThread != null) {
            getEpgDataThread.start();
        }

        // time out to kill this
        timeOutHelper = new TimeOutHelper(epgHandler, this);
    }

    private Handler epgHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case EPG_UPDATE_LIST:
                    if (isEpgChannel == true) {
                        constructEventInfoList(mCurProgInfo);
                        eventAdapter.notifyDataSetChanged();
                    } else {
                        constructProgramInfoList(null);
                        programAdapter.notifyDataSetChanged();
                    }

                    epgListView.invalidate();
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
        super.onResume();
        timeOutHelper.start();
        timeOutHelper.init();
    };

    @Override
    protected void onPause() {
        super.onPause();
        timeOutHelper.stop();
    }

    @Override
    protected void onDestroy() {
        mTvEpgManager.disableEpgBarkerChannel();
        enableEpgDataThread = false;
        getEpgDataThread.run();

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        timeOutHelper.reset();
        final int selectedItemPosition = epgListView.getSelectedItemPosition();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (info_lay.getVisibility() == View.VISIBLE) {
                    infodisplay = false;
                    context.setText("");
                    info_lay.setVisibility(View.GONE);
                } else {
                    finish();
                }
                return true;

            case KeyEvent.KEYCODE_PROG_GREEN:
                if (epgListView.isFocused()) {
                    infodisplay = !infodisplay;
                    if (!infodisplay) {
                        if (info_lay.getVisibility() == View.VISIBLE) {
                            context.setText("");
                            info_lay.setVisibility(View.GONE);
                        }
                    } else {
                        UpdateEpgProgramInfo(keyCode);
                    }
                }
                return true;

            case KeyEvent.KEYCODE_PROG_YELLOW:
                if (info_lay.getVisibility() == View.VISIBLE) {
                    context.setText("");
                    info_lay.setVisibility(View.GONE);
                }
                intent = new Intent(this, ScheduleListActivity.class);
                startActivity(intent);
                finish();
                return true;

            case KeyEvent.KEYCODE_PROG_RED:
                if (!mIsPvrEnable) {
                    Log.d(TAG, "PVR not enable");
                    break;
                }
                if (info_lay.getVisibility() == View.VISIBLE) {
                    context.setText("");
                    info_lay.setVisibility(View.GONE);
                }
                Log.d(TAG, "EpgChannel:" + isEpgChannel + "Position:" + selectedItemPosition);
                if (ListView.INVALID_POSITION == selectedItemPosition) {
                    return true;
                }
                if (isEpgChannel) {
                    intent = new Intent(this, RecordActivity.class);
                    intent.setClass(EPGActivity.this, RecordActivity.class);
                    if (epgListView.getSelectedItemPosition() >= eventInfoList.size()) {
                        return true;
                    }
                    EpgEventInfo epgEventInfo = eventInfoList.get(selectedItemPosition);
                    /* use UTC for Record Activity */
                    intent.putExtra("ChannelNum", mCurProgInfo.number);
                    intent.putExtra("EventBaseTime", epgEventInfo.originalStartTime);
                    intent.putExtra("FocusIndex", selectedItemPosition);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(this, RecordActivity.class);
                    intent.setClass(EPGActivity.this, RecordActivity.class);
                    Log.d(TAG, "eventInfoList.size()" + eventInfoList.size());
                    int channelNum = mCurProgInfo.number;
                    if ((epgProgramViewHolderList == null)
                            || epgProgramViewHolderList.size() <= selectedItemPosition) {
                        Log.d(TAG, "Get Channel Num error");
                        return true;
                    }
                    channelNum = epgProgramViewHolderList.get(selectedItemPosition).getChannelNum();
                    intent.putExtra("ChannelNum", channelNum);
                    /* use UTC for Record Activity */
                    if (eventInfoList.size() == 0) {
                        Time CurTime = new Time();
                        CurTime.setToNow();
                        intent.putExtra("EventBaseTime", (int) (CurTime.toMillis(true) / 1000));
                    } else {
                        EpgEventInfo epgEventInfo = new EpgEventInfo();
                        epgEventInfo = eventInfoList.get(userEventIndex);
                        intent.putExtra("EventBaseTime", epgEventInfo.originalStartTime);
                    }
                    intent.putExtra("FocusIndex", selectedItemPosition);
                    startActivity(intent);
                    finish();
                }
                return true;

            case KeyEvent.KEYCODE_PROG_BLUE:
                if (info_lay.getVisibility() == View.VISIBLE) {
                    context.setText("");
                    info_lay.setVisibility(View.GONE);
                }
                if (ListView.INVALID_POSITION == selectedItemPosition) {
                    return true;
                }
                if (isEpgChannel) {
                    intent = new Intent(this, ReminderActivity.class);
                    intent.setClass(EPGActivity.this, ReminderActivity.class);

                    if (selectedItemPosition >= eventInfoList.size()) {
                        return true;
                    }
                    EpgEventInfo epgEventInfo = eventInfoList.get(selectedItemPosition);
                    intent.putExtra("EventBaseTime", epgEventInfo.startTime);
                    intent.putExtra("FocusIndex", selectedItemPosition);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(this, ReminderActivity.class);
                    intent.setClass(EPGActivity.this, ReminderActivity.class);
                    if (eventInfoList.size() == 0) {
                        return true;
                    }
                    EpgEventInfo epgEventInfo = eventInfoList.get(0);
                    intent.putExtra("EventBaseTime", epgEventInfo.startTime);
                    intent.putExtra("FocusIndex", selectedItemPosition);
                    startActivity(intent);
                    finish();
                }
                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (info_lay.getVisibility() == View.VISIBLE) {
                    context.setText("");
                    info_lay.setVisibility(View.GONE);
                }

                if (comDateNameTextView.isFocused()) {

                    if (isEpgChannel) {
                        changeProgramNumber(true);
                    } else {
                        changeEventStartTime(true);
                    }
                } else {
                    changeEPGListProgramList();
                }
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (info_lay.getVisibility() == View.VISIBLE) {
                    context.setText("");
                    info_lay.setVisibility(View.GONE);
                }

                if (comDateNameTextView.isFocused()) {
                    if (isEpgChannel) {
                        changeProgramNumber(false);
                    } else {
                        changeEventStartTime(false);
                    }

                } else {
                    changeEPGListProgramList();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void changeEPGListProgramList() {
        if (mDot1 == null) {
            mDot1 = (ImageView) findViewById(R.id.programme_epg_bg_dot_1);
        }
        if (mDot2 == null) {
            mDot2 = (ImageView) findViewById(R.id.programme_epg_bg_dot_2);
        }

        isEpgChannel = !isEpgChannel;
        if (isEpgChannel == true) {
            epgListView.setAdapter(eventAdapter);
        } else {
            epgListView.setAdapter(programAdapter);
        }
        resetAllNextBaseTime(false);
        if (isEpgChannel) {
            mCurProgInfo = mTvChannelManager.getCurrentProgramInfo();
            title.setText(R.string.str_epg_title_channel);
            constructProgrameGuideListChannel(mCurProgInfo);
            if((mDot1 != null) && (mDot2 != null)) {
                mDot1.setImageResource(R.drawable.common_img_pagepoint_enable);
                mDot2.setImageResource(R.drawable.common_img_pagepoint_disable);
            }
        } else {
            title.setText(R.string.str_epg_title_time);
            constructProgrameGuideListTime(0);
            if((mDot1 != null) && (mDot2 != null)) {
                mDot1.setImageResource(R.drawable.common_img_pagepoint_disable);
                mDot2.setImageResource(R.drawable.common_img_pagepoint_enable);
            }
        }
    }

    private void registerListener() {
        epgListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                timeOutHelper.reset();

                if (isEpgChannel == false) {
                    // go to the selected channel
                    final int selectedItemPosition = epgListView.getSelectedItemPosition();
                    ProgramInfo curProgInfo = new ProgramInfo();
                    if (ListView.INVALID_POSITION == selectedItemPosition) {
                        return;
                    }
                    curProgInfo = mTvChannelManager.getProgramInfoByIndex(selectedItemPosition);
                    mTvChannelManager.selectProgram(curProgInfo.number, curProgInfo.serviceType);
                    resetAllNextBaseTime(true);
                }
            }
        });

        epgListView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (infodisplay) {
                        UpdateEpgProgramInfo(KeyEvent.KEYCODE_PROG_GREEN);
                    }
                } else {
                    if (info_lay.getVisibility() == View.VISIBLE) {
                        context.setText("");
                        info_lay.setVisibility(View.GONE);
                    }
                }
            }
        });
        epgListView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                timeOutHelper.reset();

                if (infodisplay) {
                    int curFocusIdx = epgListView.getSelectedItemPosition();
                    if (((keyCode == KeyEvent.KEYCODE_DPAD_UP) || (keyCode == KeyEvent.KEYCODE_DPAD_DOWN))
                            && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (ListView.INVALID_POSITION != curFocusIdx) {
                            UpdateEpgProgramInfo(keyCode);
                        }
                    }
                }
                return false;
            }
        });
        title.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                timeOutHelper.reset();
                TextView title = (TextView) view;
                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    changeEPGListProgramList();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    changeEPGListProgramList();
                    return true;
                }
                return false;
            }
        });

        title.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (info_lay.getVisibility() == View.VISIBLE) {
                        context.setText("");
                        info_lay.setVisibility(View.GONE);
                    }
                } else {
                    if (epgEventViewHolderList.isEmpty()) {
                        if (info_lay.getVisibility() == View.VISIBLE) {
                            context.setText("");
                            info_lay.setVisibility(View.GONE);
                        }
                    } else {
                        context.setText(epgEventViewHolderList.get(0).getInfo());
                    }
                }
            }
        });
    }

    private void resetAllNextBaseTime(boolean initAll) {
        if (initAll == true) {
            nextEventBaseTime = null;
            prevConstructEventCount = 0;
            totalEventCount = 0;
            nextProgramBaseTime = null;
            prevConstructProgramCount = 0;
            totalProgramCount = 0;
        } else {
            if (isEpgChannel == true) {
                nextEventBaseTime = null;
                prevConstructEventCount = 0;
                totalEventCount = 0;
            } else {
                nextProgramBaseTime = null;
                prevConstructProgramCount = 0;
                totalProgramCount = 0;
            }
        }
    }

    private boolean constructEventInfoList(ProgramInfo curProgInfo) {
        EpgEventInfo epgEventInfo = new EpgEventInfo();
        List<EpgEventInfo> eventInfoListTemp = null;

        if ((prevConstructEventCount >= totalEventCount) && (totalEventCount > 0)) {
            return false;
        }
        Log.v(TAG, "\33[1;36m constructEventInfoList CH :" + curProgInfo.number + "\33[0;0m");

        // initial
        if (nextEventBaseTime == null) {
            nextEventBaseTime = new Time();
            nextEventBaseTime.setToNow();
            nextEventBaseTime.set(nextEventBaseTime.toMillis(true));

            epgEventViewHolderList.clear();
            eventInfoList.clear();

            totalEventCount = mTvEpgManager.getDvbEventCount(curProgInfo.serviceType,
                    curProgInfo.number, nextEventBaseTime.toMillis(true));
            prevConstructEventCount = 0;
        } else if (totalEventCount == 0) {
            totalEventCount = mTvEpgManager.getDvbEventCount(curProgInfo.serviceType,
                    curProgInfo.number, nextEventBaseTime.toMillis(true));
            prevConstructEventCount = 0;
        }
        if (totalEventCount == 0) {
            return false;
        }

        eventInfoListTemp = mTvEpgManager.getEventInfo(curProgInfo.serviceType, curProgInfo.number,
                nextEventBaseTime, totalEventCount);
        if (eventInfoListTemp != null) {
            for (int i = 0; i < eventInfoListTemp.size(); i++) {
                epgEventInfo = eventInfoListTemp.get(i);
                if (!TextUtils.isEmpty(epgEventInfo.name)) {
                    eventInfoList.add(eventInfoListTemp.get(i));

                    // push event start time -> end time & event name in list
                    String[] eventNameStr = {
                        null
                    };
                    String[] timeInfoStr = {
                        null
                    };

                    epgEventInfo = eventInfoListTemp.get(i);
                    eventNameStr[0] = epgEventInfo.name;

                    Time startTime = new Time();
                    startTime.set((long) epgEventInfo.originalStartTime * 1000);
                    Time endTime = new Time();
                    endTime.set((long) (epgEventInfo.originalStartTime + epgEventInfo.durationTime) * 1000);
                    timeInfoStr[0] = String.format("%02d", startTime.hour) + ":"
                            + String.format("%02d", startTime.minute) + "-"
                            + String.format("%02d", endTime.hour) + ":"
                            + String.format("%02d", endTime.minute);
                    setEventData(curProgInfo.number, eventNameStr, timeInfoStr);
                }
            }

            // store current last event's end time
            epgEventInfo = eventInfoListTemp.get(eventInfoListTemp.size() - 1);
        }
        nextEventBaseTime
                .set((long) (epgEventInfo.originalStartTime + epgEventInfo.durationTime) * 1000);
        prevConstructEventCount += totalEventCount;
        return true;
    }

    private boolean constructProgramInfoList(Time baseTime) {
        int availableProgramCount = 0;
        ProgramInfo specificProgInfo = new ProgramInfo();
        EpgEventInfo epgEventInfo = new EpgEventInfo();
        List<EpgEventInfo> eventInfoListTemp = null;

        if ((prevConstructProgramCount >= totalProgramCount) && (totalProgramCount > 0)) {
            // construct finish
            return false;
        }
        // initial
        if (baseTime != null) {
            if (nextProgramBaseTime == null) {
                nextProgramBaseTime = new Time();
            }
            nextProgramBaseTime.set(baseTime.toMillis(true));
            epgProgramViewHolderList.clear();
            totalProgramCount = mTvChannelManager
                    .getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            prevConstructProgramCount = 0;
        } else {
            if (nextProgramBaseTime == null) {
                nextProgramBaseTime = new Time();
                nextProgramBaseTime.setToNow();
                nextProgramBaseTime.set(nextProgramBaseTime.toMillis(true));
                epgProgramViewHolderList.clear();
                totalProgramCount = mTvChannelManager
                        .getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
                prevConstructProgramCount = 0;
            } else if (totalProgramCount == 0) {
                totalProgramCount = mTvChannelManager
                        .getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
                prevConstructProgramCount = 0;
            }
        }

        if (totalProgramCount == 0) {
            return false;
        }

        for (int i = prevConstructProgramCount; i < totalProgramCount; i++) {
            specificProgInfo = mTvChannelManager.getProgramInfoByIndex(i);

            if (i == (totalProgramCount - 1)) {
                prevConstructProgramCount = i + 1;
            }

            if (specificProgInfo == null) {
                continue;
            }

            if ((specificProgInfo.isDelete == true) || (specificProgInfo.isVisible == false)) {
                continue;
            }

            eventInfoListTemp = TvEpgManager.getInstance().getEventInfo(
                    specificProgInfo.serviceType, specificProgInfo.number, nextProgramBaseTime, 1);

            // FIXME : check eventInfoListTemp is null case
            if (eventInfoListTemp != null) {
                epgEventInfo = eventInfoListTemp.get(0);
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
            eventNameStr[0] = epgEventInfo.name;
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                serviceInfoStr[0] = specificProgInfo.majorNum + "." + specificProgInfo.minorNum
                        + "  " + specificProgInfo.serviceName;
            } else {
                serviceInfoStr[0] = specificProgInfo.number + "  " + specificProgInfo.serviceName;
            }
            setProgramData(specificProgInfo.number, eventNameStr, serviceInfoStr);

            availableProgramCount++;
            if (availableProgramCount >= EPG_ITEM_COUNT_PER_PAGE) {
                prevConstructProgramCount = i + 1;
                break;
            }
        }

        return true;
    }

    private boolean constructProgrameGuideListChannel(ProgramInfo curProgInfo) {
        // get event info
        Time baseTime = new Time();
        baseTime.setToNow();
        baseTime.set(baseTime.toMillis(true));

        // show cur service number
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            comDateNameTextView.setText("CH " + curProgInfo.majorNum + "." + curProgInfo.minorNum);
        } else {
            comDateNameTextView.setText("CH " + curProgInfo.number);
        }
        Log.v(TAG, "constructProgrameGuideListChannel CH :" + curProgInfo.number);

        constructEventInfoList(curProgInfo);
        eventAdapter.notifyDataSetChanged();
        epgListView.invalidate();
        return true;
    }

    private boolean constructProgrameGuideListTime(long timeInSec) {
        // get st time
        Time stTime = new Time();
        if (timeInSec == 0) {
            // use default time
            stTime.setToNow();
        } else {
            // use specific time
            stTime.set(timeInSec * 1000);
        }

        // show date&time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date curDate = new Date(stTime.toMillis(true));
        String dateStr = formatter.format(curDate);

        comDateNameTextView.setText(dateStr);

        constructProgramInfoList(stTime);
        programAdapter.notifyDataSetChanged();
        epgListView.invalidate();
        return true;
    }

    public void changeProgramNumber(boolean isLeftKey) {
        ProgramInfo progInfo = new ProgramInfo();

        int tmpindex = userProgramIndex;
        while (true) {

            if (isLeftKey) {
                if (userProgramIndex > 0) {
                    userProgramIndex--;
                } else {
                    userProgramIndex = mTvChannelManager
                            .getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV) - 1;
                }
            } else {
                if (userProgramIndex < (mTvChannelManager
                        .getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV) - 1)) {
                    userProgramIndex++;
                } else {
                    userProgramIndex = 0;
                }
            }

            Log.i(TAG, "changeProgramNumber userProgramIndex : " + userProgramIndex);
            progInfo = mTvChannelManager.getProgramInfoByIndex(userProgramIndex);

            if (progInfo.isDelete || progInfo.isHide || progInfo.isSkip || !progInfo.isVisible) {
                if (tmpindex == userProgramIndex) {
                    break;
                } else {
                    continue;
                }

            } else {
                break;
            }
        }
        mTvChannelManager.selectProgram(progInfo.number, progInfo.serviceType);
        resetAllNextBaseTime(true);
        constructProgrameGuideListChannel(progInfo);
        mCurProgInfo = progInfo;
    }

    public void changeEventStartTime(boolean isLeftKey) {
        EpgEventInfo epgEventInfo = new EpgEventInfo();

        if (eventInfoList.size() == 0) {
            return;
        }
        if (isLeftKey) {
            if (userEventIndex > 0) {
                userEventIndex--;
            } else {
                userEventIndex = eventInfoList.size() - 1;
            }
        } else {
            if (userEventIndex < (eventInfoList.size() - 1)) {
                userEventIndex++;
            } else {
                userEventIndex = 0;
            }
        }

        epgEventInfo = eventInfoList.get(userEventIndex);

        resetAllNextBaseTime(false);
        constructProgrameGuideListTime(epgEventInfo.originalStartTime);
    }

    public boolean UpdateEpgProgramInfo(int keycode) {
        context.setText("");

        if (isEpgChannel) {
            ProgramInfo curProgInfo = new ProgramInfo();
            EpgEventInfo epgEventInfo = new EpgEventInfo();
            int curFocusIdx = epgListView.getSelectedItemPosition();
            if (0xffffffff == curFocusIdx) {
                return true;
            }

            if (keycode == KeyEvent.KEYCODE_DPAD_UP) {
                if (0 != curFocusIdx)
                    curFocusIdx--;
                else
                    return true;
            } else if (keycode == KeyEvent.KEYCODE_DPAD_DOWN) {
                curFocusIdx++;
            }
            curProgInfo = mTvChannelManager.getCurrentProgramInfo();
            if (curFocusIdx >= eventInfoList.size()) {
                return true;
            }
            epgEventInfo = eventInfoList.get(curFocusIdx);
            // show event start time and end time
            Time startTime = new Time();
            startTime.set((long) epgEventInfo.originalStartTime * 1000);

            Time endTime = new Time();
            endTime.set((long) (epgEventInfo.originalStartTime + epgEventInfo.durationTime) * 1000);
            String timeInfoStr = startTime.year + "/" + (startTime.month + 1) + "/"
                    + startTime.monthDay + " " + startTime.hour + ":" + startTime.minute + "-"
                    + endTime.hour + ":" + endTime.minute;
            timeinfo.setText(timeInfoStr);
            // show event description
            String eventDescription = mTvEpgManager.getEventDescriptor(curProgInfo.serviceType,
                    curProgInfo.number, startTime, TvEpgManager.EPG_DETAIL_DESCRIPTION);
            context.setText(eventDescription);

            info_lay.setVisibility(View.VISIBLE);
        } else {
            ProgramInfo curProgInfo = new ProgramInfo();
            EpgEventInfo epgEventInfo = new EpgEventInfo();
            int curFocusIdx = 0;

            curProgInfo = mTvChannelManager.getCurrentProgramInfo();
            if (curFocusIdx >= eventInfoList.size()) {
                return true;
            }
            epgEventInfo = eventInfoList.get(curFocusIdx);

            // show event start time and end time
            Time startTime = new Time();
            startTime.set((long) epgEventInfo.originalStartTime * 1000);
            Time endTime = new Time();
            endTime.set((long) (epgEventInfo.originalStartTime + epgEventInfo.durationTime) * 1000);
            String timeInfoStr = startTime.year + "/" + (startTime.month + 1) + "/"
                    + startTime.monthDay + " " + startTime.hour + ":" + startTime.minute + "-"
                    + endTime.hour + ":" + endTime.minute;
            timeinfo.setText(timeInfoStr);
            String eventDescription = mTvEpgManager.getEventDescriptor(curProgInfo.serviceType,
                    curProgInfo.number, startTime, TvEpgManager.EPG_SHORT_DESCRIPTION);
            context.setText(eventDescription);

            info_lay.setVisibility(View.VISIBLE);
        }
        return false;
    }
}
