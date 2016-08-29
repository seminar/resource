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

package com.mstar.tv.tvplayer.ui.dtv.epg;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvEpgManager;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tvapi.common.vo.EpgEventTimerInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.dtv.vo.EpgEventInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tvframework.MstarBaseActivity;

public class RecordActivity extends MstarBaseActivity {
    private static final String TAG = "RecordActivity";

    private int mEventBaseTime = 0;

    private int mFocusIndex = 0;

    private int mEventChannelNum = 0;

    private int mServiceType = TvChannelManager.SERVICE_TYPE_DTV;

    private int mTvSystem = 0;

    // /EPG timer event type none
    private final int EPG_EVENT_NONE = 0;

    // /EPG timer event type remider
    private final int EPG_EVENT_REMIDER = 1;

    // /EPG timer event type recorder
    private final int EPG_EVENT_RECORDER = 2;

    // /EPG timer repeat mode auto
    // private final int EPG_REPEAT_AUTO = 0;
    // /EPG timer repeat mode once
    private final int EPG_REPEAT_ONCE = 0x81;

    // /EPG timer repeat mode daily
    private final int EPG_REPEAT_DAILY = 0x7F;

    // /EPG timer repeat mode weekly
    private final int EPG_REPEAT_WEEKLY = 0x82;

    private static EpgEventTimerInfo eventTimerInfo = new EpgEventTimerInfo();

    private static int userProgramIndex = 0;

    private TextView channelValue = null;

    private TextView startMinuteValue = null;

    private TextView startHourValue = null;

    private TextView startMonthValue = null;

    private TextView startDateValue = null;

    private TextView endMinuteValue = null;

    private TextView endHourValue = null;

    private TextView endMonthValue = null;

    private TextView endDateValue = null;

    private TextView modeValue = null;

    private TvTimerManager mTvTimerManager = null;

    private TvEpgManager mTvEpgManager = null;

    private TvChannelManager mTvChannelManager = null;

    private TvAtscChannelManager mTvAtscChannelManager = null;

    private String mMinute = null;

    private String mHour = null;

    private String mMonth = null;

    private String mDate = null;

    private final static int TIME_GAP_FROM_NOW_SEC = 60;

    private final static int TIME_BASE_DEFAULT_DURATION_SEC = 600;

    private Time mStartTime = new Time();

    private Time mEndTime = new Time();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        ProgramInfo targetProgInfo = new ProgramInfo();
        EpgEventInfo epgEventInfo = new EpgEventInfo();
        eventTimerInfo = new EpgEventTimerInfo();
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvChannelManager = TvChannelManager.getInstance();
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mTvAtscChannelManager = TvAtscChannelManager.getInstance();
        }
        mTvTimerManager = TvTimerManager.getInstance();
        mTvEpgManager = TvEpgManager.getInstance();
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            mEventBaseTime = getIntent().getIntExtra("EventBaseTime", 0);
            mFocusIndex = getIntent().getIntExtra("FocusIndex", 0);
            mEventChannelNum = getIntent().getIntExtra("ChannelNum", 0);
            Log.d(TAG, "======>>>REC mEventBaseTime " + mEventBaseTime);
            Log.d(TAG, "======>>>REC mFocusIndex " + mFocusIndex);
            Log.d(TAG, "======>>>REC mEventChannelNum " + mEventChannelNum);
        }

        int programCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
        for (int i = 0; i < programCount; i++) {
            ProgramInfo progInfo = new ProgramInfo();
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                progInfo = mTvAtscChannelManager.getProgramInfo(i);
            } else {
                progInfo = mTvChannelManager.getProgramInfoByIndex(i);
            }
            if (progInfo.number == mEventChannelNum) {
                mServiceType = progInfo.serviceType;
                targetProgInfo = progInfo;
                userProgramIndex = i;
                break;
            }
        }
        Log.d(TAG, "targetProgInfo.number" + targetProgInfo.number);

        // textview id
        channelValue = (TextView) findViewById(R.id.record_service_name);
        startMinuteValue = (TextView) findViewById(R.id.record_start_time_minute);
        startHourValue = (TextView) findViewById(R.id.record_start_time_hour);
        startMonthValue = (TextView) findViewById(R.id.record_start_time_month);
        startDateValue = (TextView) findViewById(R.id.record_start_time_date);
        endMinuteValue = (TextView) findViewById(R.id.record_end_time_minute);
        endHourValue = (TextView) findViewById(R.id.record_end_time_hour);
        endMonthValue = (TextView) findViewById(R.id.record_end_time_month);
        endDateValue = (TextView) findViewById(R.id.record_end_time_date);
        modeValue = (TextView) findViewById(R.id.reminder_mode_value);
        mMinute = getString(R.string.str_record_minute);
        mHour = getString(R.string.str_record_hour);
        mMonth = getString(R.string.str_record_month);
        mDate = getString(R.string.str_record_date);
        // show service number and name
        String serviceNumberAndNameStr = null;
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            serviceNumberAndNameStr = "CH " + targetProgInfo.majorNum + "."
                    + targetProgInfo.minorNum + " " + targetProgInfo.serviceName;
        } else {
            serviceNumberAndNameStr = "CH " + mEventChannelNum + " " + targetProgInfo.serviceName;
        }
        channelValue.setText(serviceNumberAndNameStr);

        Time baseTime = new Time();
        // get event info
        baseTime.set((mEventBaseTime * 1000L + 1));
        List<EpgEventInfo> eventInfoList = new ArrayList<EpgEventInfo>();
        eventInfoList.clear();
        eventInfoList = mTvEpgManager.getEventInfo((short) mServiceType, mEventChannelNum,
                baseTime, 1);
        int eventStartTimeInS = 0;
        int eventDurationInS = 0;
        boolean isRecordTimeFromEvent = false;
        if (eventInfoList != null) {
            epgEventInfo = eventInfoList.get(0);
            if (epgEventInfo != null) {
                eventStartTimeInS = epgEventInfo.originalStartTime;
                eventDurationInS = epgEventInfo.durationTime;
                isRecordTimeFromEvent = true;
            }
        }

        /* record time should later current time */
        baseTime.setToNow();
        baseTime.second = 0;
        final int curTimeGapInS = (int) (baseTime.toMillis(true) / 1000) + TIME_GAP_FROM_NOW_SEC;

        if (isRecordTimeFromEvent == false) {
            Log.d(TAG, "Time base record");
            eventStartTimeInS = mEventBaseTime;
            eventDurationInS = TIME_BASE_DEFAULT_DURATION_SEC;
        }
        final int eventEndTimeInS = eventStartTimeInS + eventDurationInS;
        if ((curTimeGapInS > eventStartTimeInS) && (curTimeGapInS < eventEndTimeInS)) {
            eventDurationInS = eventEndTimeInS - curTimeGapInS;
            eventStartTimeInS = curTimeGapInS;
        }

        eventTimerInfo.enTimerType = EPG_EVENT_RECORDER;
        eventTimerInfo.enRepeatMode = EPG_REPEAT_ONCE;
        eventTimerInfo.startTime = eventStartTimeInS;
        eventTimerInfo.durationTime = eventDurationInS;
        eventTimerInfo.serviceType = mServiceType;
        eventTimerInfo.serviceNumber = mEventChannelNum;
        eventTimerInfo.eventID = mFocusIndex;
        Log.d(TAG, "======>>>eventTimerInfo.durationTime sec " + eventTimerInfo.durationTime);

        // show event start time
        mStartTime.set(eventStartTimeInS * 1000L);
        startMinuteValue.setText(mStartTime.minute + mMinute);
        startHourValue.setText(mStartTime.hour + mHour);
        startMonthValue.setText((mStartTime.month + 1) + mMonth);
        startDateValue.setText(mStartTime.monthDay + mDate);

        mEndTime.set(eventEndTimeInS * 1000L);
        endMinuteValue.setText(mEndTime.minute + mMinute);
        endHourValue.setText(mEndTime.hour + mHour);
        endMonthValue.setText((mEndTime.month + 1) + mMonth);
        endDateValue.setText(mEndTime.monthDay + mDate);

        // show repeat mode
        switch (eventTimerInfo.enRepeatMode) {
            case EPG_REPEAT_ONCE:
                modeValue.setText(R.string.str_reminder_once);
                break;
            case EPG_REPEAT_DAILY:
                modeValue.setText(R.string.str_reminder_daily);
                break;
            case EPG_REPEAT_WEEKLY:
                modeValue.setText(R.string.str_reminder_weekly);
                break;
            default:
                modeValue.setText(R.string.str_reminder_once);
                break;
        }

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addEpgEvent();
            }
        };

        channelValue.setOnClickListener(clickListener);
        startMinuteValue.setOnClickListener(clickListener);
        startHourValue.setOnClickListener(clickListener);
        endMinuteValue.setOnClickListener(clickListener);
        endHourValue.setOnClickListener(clickListener);
        modeValue.setOnClickListener(clickListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT: {
                LinearLayout modeLayout = (LinearLayout) findViewById(R.id.reminder_mode_value_layout);
                if (channelValue.isFocused()) {
                    changeRecordProgramNumber(true);
                } else if (startMinuteValue.isFocused()) {
                    changeRecordStartMin(true);
                } else if (startHourValue.isFocused()) {
                    changeRecordStartHour(true);
                } else if (endMinuteValue.isFocused()) {
                    changeRecordEndMin(true);
                } else if (endHourValue.isFocused()) {
                    changeRecordEndHour(true);
                } else if (modeLayout.isFocused()) {
                    changeRecordMode(true);
                }
            }
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT: {
                LinearLayout modeLayout = (LinearLayout) findViewById(R.id.reminder_mode_value_layout);
                if (channelValue.isFocused()) {
                    changeRecordProgramNumber(false);
                } else if (startMinuteValue.isFocused()) {
                    changeRecordStartMin(false);
                } else if (startHourValue.isFocused()) {
                    changeRecordStartHour(false);
                } else if (endMinuteValue.isFocused()) {
                    changeRecordEndMin(false);
                } else if (endHourValue.isFocused()) {
                    changeRecordEndHour(false);
                } else if (modeLayout.isFocused()) {
                    changeRecordMode(false);
                }
            }
                return true;
            case KeyEvent.KEYCODE_BACK:
                // case KeyEvent.KEYCODE_PROG_RED:
                intent = new Intent(this, EPGActivity.class);
                intent.setClass(RecordActivity.this, EPGActivity.class);
                intent.putExtra("FocusIndex", mFocusIndex);
                startActivity(intent);
                finish();
                return true;

            case KeyEvent.KEYCODE_ENTER:
                Log.d(TAG, "###addEpgEvent recorder KEYCODE_ENTER\n");
                addEpgEvent();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addEpgEvent() {
        Time curTime = new Time();
        curTime.setToNow();
        String[] dispInfo = getResources().getStringArray(
                R.array.str_arr_epg_time_check_display_info);

        if (mStartTime.after(curTime) && mEndTime.after(mStartTime)) {
            eventTimerInfo.startTime = (int) (mStartTime.toMillis(true) / 1000);
            eventTimerInfo.durationTime = (int) (mEndTime.toMillis(true) / 1000)
                    - eventTimerInfo.startTime;
            int ret = mTvTimerManager.addEpgNewEvent(eventTimerInfo);
            Toast toast = Toast.makeText(RecordActivity.this, dispInfo[ret], Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(RecordActivity.this,
                    dispInfo[TvTimerManager.EPG_TIMECHECK_PAST], Toast.LENGTH_SHORT);
            toast.show();
        }
        Intent intent = new Intent(RecordActivity.this, EPGActivity.class);
        intent.setClass(RecordActivity.this, EPGActivity.class);
        intent.putExtra("FocusIndex", mFocusIndex);
        startActivity(intent);
        finish();
    }

    private void changeRecordMode(boolean isLeftKey) {
        if (isLeftKey) {
            switch (eventTimerInfo.enRepeatMode) {
                case EPG_REPEAT_ONCE:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_DAILY;
                    break;

                case EPG_REPEAT_DAILY:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_WEEKLY;
                    break;

                case EPG_REPEAT_WEEKLY:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_ONCE;
                    break;

                default:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_ONCE;
                    break;
            }
        } else {
            switch (eventTimerInfo.enRepeatMode) {
                case EPG_REPEAT_ONCE:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_WEEKLY;
                    break;

                case EPG_REPEAT_WEEKLY:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_DAILY;
                    break;

                case EPG_REPEAT_DAILY:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_ONCE;
                    break;

                default:
                    eventTimerInfo.enRepeatMode = EPG_REPEAT_ONCE;
                    break;
            }
        }

        // show repeat mode
        switch (eventTimerInfo.enRepeatMode) {
            case EPG_REPEAT_ONCE:
                modeValue.setText(R.string.str_reminder_once);
                break;
            case EPG_REPEAT_DAILY:
                modeValue.setText(R.string.str_reminder_daily);
                break;
            case EPG_REPEAT_WEEKLY:
                modeValue.setText(R.string.str_reminder_weekly);
                break;
            default:
                modeValue.setText(R.string.str_reminder_once);
                break;
        }

        modeValue.invalidate();
    }

    private void changeRecordProgramNumber(boolean isLeftKey) {
        ProgramInfo progInfo = new ProgramInfo();
        String tmpStr = null;

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

        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            progInfo = mTvAtscChannelManager.getProgramInfo(userProgramIndex);
        } else {
            progInfo = mTvChannelManager.getProgramInfoByIndex(userProgramIndex);
        }
        eventTimerInfo.serviceNumber = progInfo.number;
        // show service number and name
        tmpStr = "CH " + progInfo.number + " " + progInfo.serviceName;
        channelValue.setText(tmpStr);
        channelValue.invalidate();
    }

    private void changeRecordStartMin(boolean isLeftKey) {
        int userTime = mStartTime.minute;

        if (isLeftKey) {
            if (userTime > 0) {
                userTime--;
            } else {
                userTime = 59;
            }
        } else {
            if (userTime < 59) {
                userTime++;
            } else {
                userTime = 0;
            }
        }

        mStartTime.minute = userTime;
        startMinuteValue.setText(userTime + mMinute);
        startMinuteValue.invalidate();
    }

    private void changeRecordStartHour(boolean isLeftKey) {
        int userTime = mStartTime.hour;

        if (isLeftKey) {
            if (userTime > 0) {
                userTime--;
            } else {
                userTime = 23;
            }
        } else {
            if (userTime < 23) {
                userTime++;
            } else {
                userTime = 0;
            }
        }

        mStartTime.hour = userTime;
        startHourValue.setText(userTime + mHour);
        startHourValue.invalidate();
    }

    private void changeRecordEndMin(boolean isLeftKey) {
        int userTime = mEndTime.minute;

        if (isLeftKey) {
            if (userTime > 0) {
                userTime--;
            } else {
                userTime = 59;
            }
        } else {
            if (userTime < 59) {
                userTime++;
            } else {
                userTime = 0;
            }
        }

        mEndTime.minute = userTime;
        endMinuteValue.setText(userTime + mMinute);
        endMinuteValue.invalidate();
    }

    private void changeRecordEndHour(boolean isLeftKey) {
        int userTime = mEndTime.hour;

        if (isLeftKey) {
            if (userTime > 0) {
                userTime--;
            } else {
                userTime = 23;
            }
        } else {
            if (userTime < 23) {
                userTime++;
            } else {
                userTime = 0;
            }
        }

        mEndTime.hour = userTime;
        endHourValue.setText(userTime + mHour);
        endHourValue.invalidate();
    }
}
