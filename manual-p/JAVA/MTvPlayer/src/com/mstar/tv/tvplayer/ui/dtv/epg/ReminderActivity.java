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

public class ReminderActivity extends MstarBaseActivity {

    private int eventBaseTime = 0;

    private int eventIdx = 0;

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

    private List<EpgEventInfo> eventInfoList = new ArrayList<EpgEventInfo>();

    private static int userProgramIndex = 0;

    private TextView channelValue = null;

    private TextView minuteValue = null;

    private TextView hourValue = null;

    private TextView monthValue = null;

    private TextView dateValue = null;

    private TextView modeValue = null;

    private LinearLayout channelLayout = null;

    private LinearLayout minuteLayout = null;

    private LinearLayout hourLayout = null;

    private LinearLayout monthLayout = null;

    private LinearLayout dateLayout = null;

    private LinearLayout modeLayout = null;

    private int offsetTimeInS = 0;

    private TvChannelManager mTvChannelManager = null;

    private TvAtscChannelManager mTvAtscChannelManager = null;

    private TvTimerManager mTvTimerManager = null;

    private TvEpgManager mTvEpgManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvChannelManager = TvChannelManager.getInstance();
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mTvAtscChannelManager = TvAtscChannelManager.getInstance();
        }
        mTvTimerManager = TvTimerManager.getInstance();
        mTvEpgManager = TvEpgManager.getInstance();
        ProgramInfo curProgInfo = new ProgramInfo();
        EpgEventInfo epgEventInfo = new EpgEventInfo();
        eventTimerInfo = new EpgEventTimerInfo();
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            eventBaseTime = getIntent().getIntExtra("EventBaseTime", 0);
            eventIdx = getIntent().getIntExtra("FocusIndex", 0);
        }

        curProgInfo = mTvChannelManager.getCurrentProgramInfo();
        int programCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
        for (int i = 0; i < programCount; i++) {
            ProgramInfo progInfo = new ProgramInfo();
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                progInfo = mTvAtscChannelManager.getProgramInfo(i);
            } else {
                progInfo = mTvChannelManager.getProgramInfoByIndex(i);
            }
            if ((progInfo.number == curProgInfo.number)
                    && (progInfo.serviceType == curProgInfo.serviceType)) {
                userProgramIndex = i;
                break;
            }
        }
        // textview id

        channelValue = (TextView) findViewById(R.id.reminder_channel_value);
        minuteValue = (TextView) findViewById(R.id.reminder_minute_value);
        hourValue = (TextView) findViewById(R.id.reminder_hour_value);
        monthValue = (TextView) findViewById(R.id.reminder_month_value);
        dateValue = (TextView) findViewById(R.id.reminder_date_value);
        modeValue = (TextView) findViewById(R.id.reminder_mode_value);
        channelLayout = (LinearLayout) findViewById(R.id.reminder_channel_layout);
        minuteLayout = (LinearLayout) findViewById(R.id.reminder_minute_layout);
        hourLayout = (LinearLayout) findViewById(R.id.reminder_hour_layout);
        monthLayout = (LinearLayout) findViewById(R.id.reminder_month_layout);
        dateLayout = (LinearLayout) findViewById(R.id.reminder_date_layout);
        modeLayout = (LinearLayout) findViewById(R.id.reminder_mode_layout);
        channelLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        // show service number and name
        String serviceNumberAndNameStr = curProgInfo.number + " " + curProgInfo.serviceName;
        channelValue.setText(serviceNumberAndNameStr);

        // get event info
        Time baseTime = new Time();
        baseTime.setToNow();
        baseTime.toMillis(true);

        offsetTimeInS = mTvEpgManager.getEpgEventOffsetTime(baseTime, true);
        /*Use long to prevent multiplication overflow*/
        baseTime.set(((eventBaseTime - offsetTimeInS) * 1000L + 1));
        eventInfoList.clear();
        eventInfoList = mTvEpgManager.getEventInfo(curProgInfo.serviceType,
                curProgInfo.number, baseTime, 1);
        epgEventInfo = eventInfoList.get(0);

        // TODO add reminder event
        eventTimerInfo.enTimerType = EPG_EVENT_REMIDER; // TODO, we should store
                                                        // it!!
        eventTimerInfo.enRepeatMode = EPG_REPEAT_ONCE; // TODO, we should store
                                                       // it!!
        eventTimerInfo.startTime = epgEventInfo.startTime - offsetTimeInS;
        eventTimerInfo.durationTime = epgEventInfo.durationTime;
        eventTimerInfo.serviceType = curProgInfo.serviceType;
        eventTimerInfo.serviceNumber = curProgInfo.number;
        eventTimerInfo.eventID = eventIdx;

        // show event start time
        Time startTime = new Time();
        startTime.set((long) eventTimerInfo.startTime * 1000); // s->ms
        String tmpStr = startTime.minute + getString(R.string.str_record_minute);
        minuteValue.setText(tmpStr);
        tmpStr = startTime.hour + getString(R.string.str_record_hour);
        hourValue.setText(tmpStr);
        tmpStr = (startTime.month + 1) + getString(R.string.str_record_month);
        monthValue.setText(tmpStr);
        tmpStr = startTime.monthDay + getString(R.string.str_record_date);
        dateValue.setText(tmpStr);

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
                String[] dispInfo = getResources().getStringArray(R.array.str_arr_epg_time_check_display_info);
                int ret = mTvTimerManager.addEpgNewEvent(eventTimerInfo);
                Toast toast = Toast.makeText(ReminderActivity.this,
                    dispInfo[ret], Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(ReminderActivity.this, EPGActivity.class);
                intent.setClass(ReminderActivity.this, EPGActivity.class);
                intent.putExtra("FocusIndex", eventIdx);
                startActivity(intent);
                finish();
            }
        };

        channelValue.setOnClickListener(clickListener);
        minuteValue.setOnClickListener(clickListener);
        hourValue.setOnClickListener(clickListener);
        modeValue.setOnClickListener(clickListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                changeFocusDown();
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                changeFocusUp();
                return true;
            case KeyEvent.KEYCODE_ENTER:
                String[] dispInfo = getResources().getStringArray(R.array.str_arr_epg_time_check_display_info);
                int ret = mTvTimerManager.addEpgNewEvent(eventTimerInfo);
                Toast toast = Toast.makeText(ReminderActivity.this,
                    dispInfo[ret], Toast.LENGTH_SHORT);
                toast.show();
                intent = new Intent(ReminderActivity.this, EPGActivity.class);
                intent.setClass(ReminderActivity.this, EPGActivity.class);
                intent.putExtra("FocusIndex", eventIdx);
                startActivity(intent);
                finish();
                return true;

            case KeyEvent.KEYCODE_BACK:
                // case KeyEvent.KEYCODE_PROG_BLUE:
                intent = new Intent(this, EPGActivity.class);
                intent.setClass(ReminderActivity.this, EPGActivity.class);
                intent.putExtra("FocusIndex", eventIdx);
                startActivity(intent);
                finish();
                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (channelValue.isFocused()) {
                    changeReminderProgramNumber(true);
                } else if (minuteValue.isFocused()) {
                    changeReminderMin(true);
                } else if (hourValue.isFocused()) {
                    changeReminderHour(true);
                } else if (monthValue.isFocused()) {
                    changeReminderMonth(true);
                } else if (dateValue.isFocused()) {
                    changeReminderDate(true);
                } else if (modeValue.isFocused()) {
                    changeReminderMode(true);
                }
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (channelValue.isFocused()) {
                    changeReminderProgramNumber(false);
                } else if (minuteValue.isFocused()) {
                    changeReminderMin(false);
                } else if (hourValue.isFocused()) {
                    changeReminderHour(false);
                } else if (monthValue.isFocused()) {
                    changeReminderMonth(false);
                } else if (dateValue.isFocused()) {
                    changeReminderDate(false);
                } else if (modeValue.isFocused()) {
                    changeReminderMode(false);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void clearFocus() {
        channelValue.clearFocus();
        minuteValue.clearFocus();
        hourValue.clearFocus();
        monthValue.clearFocus();
        dateValue.clearFocus();
        modeValue.clearFocus();
        channelLayout.setBackgroundResource(R.drawable.alpha_img);
        minuteLayout.setBackgroundResource(R.drawable.alpha_img);
        hourLayout.setBackgroundResource(R.drawable.alpha_img);
        monthLayout.setBackgroundResource(R.drawable.alpha_img);
        dateLayout.setBackgroundResource(R.drawable.alpha_img);
        modeLayout.setBackgroundResource(R.drawable.alpha_img);
    }

    public void changeFocusUp() {
        if (channelValue.isFocused()) {
            clearFocus();
            modeValue.requestFocus();
            modeLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        } else if (minuteValue.isFocused()) {
            clearFocus();
            channelValue.requestFocus();
            channelLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);
        } else if (hourValue.isFocused()) {
            clearFocus();
            minuteValue.requestFocus();
            minuteLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);
        } else if (monthValue.isFocused()) {
            clearFocus();
            hourValue.requestFocus();
            hourLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);
        } else if (dateValue.isFocused()) {
            clearFocus();
            monthValue.requestFocus();
            monthLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);
        } else if (modeValue.isFocused()) {
            clearFocus();
            dateValue.requestFocus();
            dateLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        }
    }

    public void changeFocusDown() {
        if (channelValue.isFocused()) {
            clearFocus();
            minuteValue.requestFocus();
            minuteLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        }

        else if (minuteValue.isFocused()) {
            clearFocus();
            hourValue.requestFocus();
            hourLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);
        }

        else if (hourValue.isFocused()) {
            clearFocus();
            monthValue.requestFocus();
            monthLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        } else if (monthValue.isFocused()) {
            clearFocus();
            dateValue.requestFocus();
            dateLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        } else if (dateValue.isFocused()) {
            clearFocus();
            modeValue.requestFocus();
            modeLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);
        }

        else if (modeValue.isFocused()) {
            clearFocus();
            channelValue.requestFocus();
            channelLayout.setBackgroundResource(R.drawable.programme_epg_img_focus);

        }

    }

    public void changeReminderMode(boolean isLeftKey) {

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

    public void changeReminderProgramNumber(boolean isLeftKey) {
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
        tmpStr = progInfo.number + " " + progInfo.serviceName;
        channelValue.setText(tmpStr);
        channelValue.invalidate();
    }

    public void changeReminderMin(boolean isLeftKey) {
        int userTime = eventTimerInfo.startTime;
        Time startTime = new Time();
        // startTime.switchTimezone("Euro/London");
        startTime.set((long) userTime * 1000); // s->ms
        userTime = startTime.minute;
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
        startTime.minute = userTime;
        eventTimerInfo.startTime = (int) (startTime.toMillis(false) / 1000);
        String tmpStr = userTime + getString(R.string.str_record_minute);
        minuteValue.setText(tmpStr);
        minuteValue.invalidate();
    }

    public void changeReminderHour(boolean isLeftKey) {
        int userTime = eventTimerInfo.startTime;
        Time startTime = new Time();
        // startTime.switchTimezone("Euro/London");
        startTime.set((long) userTime * 1000); // s->ms
        userTime = startTime.hour;
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

        startTime.hour = userTime;
        eventTimerInfo.startTime = (int) (startTime.toMillis(false) / 1000);
        String tmpStr = userTime + getString(R.string.str_record_hour);
        hourValue.setText(tmpStr);
        hourValue.invalidate();
    }

    public void changeReminderMonth(boolean isLeftKey) {
        int userTime = eventTimerInfo.startTime;
        Time startTime = new Time();
        // startTime.switchTimezone("Euro/London");
        startTime.set((long) userTime * 1000); // s->ms
        userTime = startTime.month;
        if (isLeftKey) {
            if (userTime > 0) {
                userTime--;
            } else {
                userTime = 11;
            }
        } else {
            if (userTime < 11) {
                userTime++;
            } else {
                userTime = 0;
            }
        }

        if (userTime == 1) {
            if (startTime.monthDay >= 29) {
                if (startTime.year % 4 == 0) {
                    startTime.monthDay = 29;
                } else {
                    startTime.monthDay = 28;
                }
            }
        } else if((userTime== 3) || (userTime == 5)
                    || (userTime == 8) || (userTime == 10)) {
            if (startTime.monthDay == 31) {
                startTime.monthDay = 30;
            }
        }
        startTime.month = userTime;
        eventTimerInfo.startTime = (int) (startTime.toMillis(false) / 1000);
        String tmpStr = (userTime+1) + getString(R.string.str_record_month);
        monthValue.setText(tmpStr);
        monthValue.invalidate();
        String tmpStrday = startTime.monthDay + getString(R.string.str_record_date);
        dateValue.setText(tmpStrday);
        dateValue.invalidate();
    }

    public void changeReminderDate(boolean isLeftKey) {
        int monthTotalDay = 0;
        int userTime = eventTimerInfo.startTime;
        Time startTime = new Time();
        // startTime.switchTimezone("Euro/London");
        startTime.set((long) userTime * 1000); // s->ms
        userTime = startTime.monthDay;
        if (startTime.month == 1) {
            if(startTime.year % 4 == 0) {
                monthTotalDay = 29;
            } else {
                monthTotalDay = 28;
            }
        } else if ((startTime.month == 3) || (startTime.month == 5)
                    || (startTime.month == 8) || (startTime.month == 10)) {
            monthTotalDay = 30;
        } else {
            monthTotalDay = 31;
        }

        if (isLeftKey) {
            if (userTime > 1) {
                userTime--;
            } else {
                userTime = monthTotalDay;
            }
        } else {
            if (userTime < monthTotalDay) {
                userTime++;
            } else {
                userTime = 1;
            }
        }

        startTime.monthDay = userTime;
        eventTimerInfo.startTime = (int) (startTime.toMillis(false) / 1000);
        String tmpStr = userTime + getString(R.string.str_record_date);
        dateValue.setText(tmpStr);
        dateValue.invalidate();
    }

}
