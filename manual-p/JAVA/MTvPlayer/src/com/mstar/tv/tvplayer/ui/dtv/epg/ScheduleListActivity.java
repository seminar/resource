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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvEpgManager;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tvapi.common.vo.EpgEventTimerInfo;
import com.mstar.android.tvapi.dtv.vo.EpgEventInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tvframework.MstarBaseActivity;

public class ScheduleListActivity extends MstarBaseActivity {
    // /EPG timer event type none
    private final int EPG_EVENT_NONE = 0;

    // /EPG timer event type remider
    private final int EPG_EVENT_REMIDER = 1;

    // /EPG timer event type recorder
    private final int EPG_EVENT_RECORDER = 2;

    // /EPG timer repeat mode auto
    private final int EPG_REPEAT_AUTO = 0;

    // /EPG timer repeat mode once
    private final int EPG_REPEAT_ONCE = 0x81;

    // /EPG timer repeat mode daily
    private final int EPG_REPEAT_DAILY = 0x7F;

    // /EPG timer repeat mode weekly
    private final int EPG_REPEAT_WEEKLY = 0x82;

    private final long MILLISECOND_PER_DAY = 24 * 60 * 60 * 1000;

    private final long MILLISECOND_PER_WEEK = MILLISECOND_PER_DAY * 7;

    private List<EpgEventInfo> eventInfoList = new ArrayList<EpgEventInfo>();

    private ArrayList<ScheduleListViewHolder> schvhs = new ArrayList<ScheduleListViewHolder>();

    private ListView listView = null;

    private TextView currentTimeTextView = null;

    private ScheduleListViewAdapter adapter = null;

    private ScheduleListViewHolder selectItem = null;

    private TvChannelManager mTvChannelManager = null;

    private TvTimerManager mTvTimerManager = null;

    private TvEpgManager mTvEpgManager = null;

    public class ScheduleListViewAdapter extends BaseAdapter {
        ArrayList<ScheduleListViewHolder> mData = null;

        private Context mContext;

        public ScheduleListViewAdapter(Context context, ArrayList<ScheduleListViewHolder> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /* Add by gerard.jiang for "0380505" 2013/04/17 */
            selectItem = mData.get(position);
            /***** End by gerard.jiang 2013/04/17 *****/
            convertView = LayoutInflater.from(mContext).inflate(R.layout.schedule_list_item, null);

            TextView tmpText = (TextView) convertView
                    .findViewById(R.id.schdeule_list_view_item_time);
            tmpText.setText(mData.get(position).getTime());

            tmpText = (TextView) convertView.findViewById(R.id.schdeule_list_view_item_date);
            tmpText.setText(mData.get(position).getData());

            tmpText = (TextView) convertView
                    .findViewById(R.id.schdeule_list_view_item_channel_name);
            tmpText.setText(mData.get(position).getChannelName());

            tmpText = (TextView) convertView
                    .findViewById(R.id.schdeule_list_view_item_programmer_title);
            tmpText.setText(mData.get(position).getProgrammeTitle());

            if (mData.get(position).isModeFlag()) {
                convertView.findViewById(R.id.schdeule_list_view_item_mode).setVisibility(
                        View.VISIBLE);
            } else {
                convertView.findViewById(R.id.schdeule_list_view_item_mode).setVisibility(
                        View.INVISIBLE);
            }
            if (mData.get(position).isReminderFlag()) {
                convertView.findViewById(R.id.schdeule_list_view_item_reminder).setVisibility(
                        View.VISIBLE);
            } else {
                convertView.findViewById(R.id.schdeule_list_view_item_reminder).setVisibility(
                        View.INVISIBLE);
            }
            if (mData.get(position).isVideoFlag()) {
                convertView.findViewById(R.id.schdeule_list_view_item_video).setVisibility(
                        View.VISIBLE);
            } else {
                convertView.findViewById(R.id.schdeule_list_view_item_video).setVisibility(
                        View.INVISIBLE);
            }
            return convertView;
        }
    }

    private class ScheduleListViewHolder {
        private String time = null;

        private String data = null;

        private String channelName = null;

        private String programmeTitle = null;

        private boolean videoFlag = false;

        private boolean reminderFlag = false;

        private boolean modeFlag = false;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getProgrammeTitle() {
            return programmeTitle;
        }

        public void setProgrammeTitle(String programmeTitle) {
            this.programmeTitle = programmeTitle;
        }

        public boolean isVideoFlag() {
            return videoFlag;
        }

        public void setVideoFlag(boolean videoFlag) {
            this.videoFlag = videoFlag;
        }

        public boolean isReminderFlag() {
            return reminderFlag;
        }

        public void setReminderFlag(boolean reminderFlag) {
            this.reminderFlag = reminderFlag;
        }

        public boolean isModeFlag() {
            return modeFlag;
        }

        public void setModeFlag(boolean modeFlag) {
            this.modeFlag = modeFlag;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_list);

        listView = (ListView) findViewById(R.id.schdeule_list_view);
        currentTimeTextView = (TextView) findViewById(R.id.schdeule_list_current_time);

        adapter = new ScheduleListViewAdapter(this, schvhs);

        listView.setAdapter(adapter);
        listView.setDividerHeight(0);
        mTvChannelManager = TvChannelManager.getInstance();
        mTvTimerManager = TvTimerManager.getInstance();
        mTvEpgManager = TvEpgManager.getInstance();
        /*
         * Add by gerard.jiang for "0380505" 2013/04/17 Set flag when an item in
         * the ListView is selected.
         */
        listView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                EpgEventTimerInfo epgEventTimerInfo = new EpgEventTimerInfo();
                epgEventTimerInfo = mTvTimerManager.getEpgTimerEventByIndex(position);

                if (epgEventTimerInfo.enTimerType == EPG_EVENT_RECORDER) {
                    selectItem.setReminderFlag(false);
                    selectItem.setVideoFlag(true);
                } else if (epgEventTimerInfo.enTimerType == EPG_EVENT_REMIDER) {
                    selectItem.setVideoFlag(false);
                    selectItem.setReminderFlag(true);
                } else {
                    selectItem.setReminderFlag(false);
                    selectItem.setVideoFlag(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing
            }
        });
        /***** End by gerard.jiang 2013/04/17 *****/
        updateScheduleListPage();
    }

    public void updateScheduleListPage() {
        // get schedule list event info
        EpgEventTimerInfo epgEventTimerInfo = new EpgEventTimerInfo();

        // get cur system time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String dateStr = formatter.format(curDate);
        currentTimeTextView.setText(dateStr);

        schvhs.clear();

        int epgTimerEventCount = mTvTimerManager.getEpgTimerEventCount();
        EpgEventInfo eventInfo = new EpgEventInfo();
        String serviceName = null;
        String str = null;
        Time time = new Time();
        for (int i = 0; i < epgTimerEventCount; i++) {
            epgEventTimerInfo = mTvTimerManager.getEpgTimerEventByIndex(i);

            Time startTime = new Time();
            Time endTime = new Time();
            Time curTime = new Time();
            startTime.setToNow();
            endTime.setToNow();
            curTime.setToNow();

            // System.out.println("\n--->cur Time       "+startTime.toMillis(true));

            int eventCount = mTvEpgManager.getDvbEventCount(epgEventTimerInfo.serviceType,
                    epgEventTimerInfo.serviceNumber, curTime.toMillis(true));

            if (eventCount > 0) {
                if (eventInfoList != null) {
                    eventInfoList.clear();
                }
                eventInfoList = mTvEpgManager.getEventInfo((short) epgEventTimerInfo.serviceType,
                        epgEventTimerInfo.serviceNumber, curTime, eventCount);
                if (eventInfoList == null) {
                    continue;
                }
                endTime.set((long) eventInfoList.get(eventInfoList.size() - 1).endTime * 1000 + 1);

                startTime.set((long) epgEventTimerInfo.startTime * 1000 + 1);

                // System.out.println("\n--->eventCount     "+eventCount);
                // System.out.println("\n--->startTime now  "+startTime.toMillis(true));
                // System.out.println("\n--->endTime now    "+endTime.toMillis(true));
                // System.out.println("\n--->enRepeatMode   "+epgEventTimerInfo.enRepeatMode);

                if (startTime.toMillis(true) > endTime.toMillis(true)) {

                    if (epgEventTimerInfo.enRepeatMode == EPG_REPEAT_DAILY) {
                        long diffTime = startTime.toMillis(true) - curTime.toMillis(true);
                        long tmp = diffTime / MILLISECOND_PER_DAY;
                        startTime.set((long) epgEventTimerInfo.startTime * 1000 + 1 - tmp
                                * MILLISECOND_PER_DAY);
                    } else if (epgEventTimerInfo.enRepeatMode == EPG_REPEAT_WEEKLY) {
                        long diffTime = startTime.toMillis(true) - curTime.toMillis(true);
                        long tmp = diffTime / MILLISECOND_PER_WEEK;
                        startTime.set((long) epgEventTimerInfo.startTime * 1000 + 1 - tmp
                                * MILLISECOND_PER_WEEK);
                    } else {
                        startTime
                                .set((long) eventInfoList.get(eventInfoList.size() - 1).startTime * 1000 + 1);
                    }
                }
                // System.out.println("\n--->startTime now C "+startTime.toMillis(true));

                eventInfoList.clear();
                eventCount = mTvEpgManager.getDvbEventCount(epgEventTimerInfo.serviceType,
                        epgEventTimerInfo.serviceNumber, startTime.toMillis(true));
                // System.out.println("\n--->eventCount2299     "+eventCount);
                if (eventCount > 0) {
                    // System.out.println("\n--->eventCount22     "+eventCount);
                    eventInfoList = mTvEpgManager.getEventInfo(
                            (short) epgEventTimerInfo.serviceType, epgEventTimerInfo.serviceNumber,
                            startTime, 1);
                    if(eventInfoList != null) {
                        eventInfo = eventInfoList.get(0);
                    }
                } else {
                    eventInfo.name = "NONE";
                }
            } else {
                eventInfo.name = "NONE";
            }

            serviceName = mTvChannelManager.getProgramName(epgEventTimerInfo.serviceNumber,
                    TvChannelManager.SERVICE_TYPE_DTV, (short) 0);

            // System.out.println("\n        ###addEpgEvent schedule list-- "+epgEventTimerInfo.startTime);
            time.set((long) epgEventTimerInfo.startTime * 1000); // s->ms

            ScheduleListViewHolder vh = new ScheduleListViewHolder();
            if (epgEventTimerInfo.enTimerType == EPG_EVENT_RECORDER) {
                vh.setVideoFlag(true);
            } else if (epgEventTimerInfo.enTimerType == EPG_EVENT_REMIDER) {
                vh.setReminderFlag(true);
            } else {
                vh.setReminderFlag(false);
                vh.setVideoFlag(false);
            }
            Date date = new Date(time.toMillis(true));
            SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
            str = formatterTime.format(date);
            vh.setTime(str);

            SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy/MM/dd");
            str = formatterDate.format(date);
            vh.setData(str);

            str = "CH" + epgEventTimerInfo.serviceNumber + "  " + serviceName;
            vh.setChannelName(str);

            vh.setProgrammeTitle(eventInfo.name);

            schvhs.add(vh);
        }

        adapter.notifyDataSetChanged();
        listView.invalidate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                intent = new Intent(this, EPGActivity.class);
                intent.setClass(ScheduleListActivity.this, EPGActivity.class);
                startActivity(intent);
                finish();
                return true;
            case KeyEvent.KEYCODE_PROG_RED:
                // del event focus on
                mTvTimerManager.delEpgEvent(listView.getSelectedItemPosition());
                updateScheduleListPage();
                return true;
                /*
                 * Add by gerard.jiang for "0380505" 2013/04/17 Dispatch yellow
                 * key to edit the item.
                 */
            case KeyEvent.KEYCODE_PROG_YELLOW:
                if (selectItem == null) {
                    intent = new Intent(this, EPGActivity.class);
                    startActivity(intent);
                } else {
                    if (selectItem.isVideoFlag()) {
                        intent = new Intent(this, RecordActivity.class);
                        intent.setClass(ScheduleListActivity.this, RecordActivity.class);
                        startActivity(intent);
                    } else if (selectItem.isReminderFlag()) {
                        intent = new Intent(this, EPGActivity.class);
                        intent.setClass(ScheduleListActivity.this, ReminderActivity.class);
                        EpgEventTimerInfo epgEventTimerInfo = new EpgEventTimerInfo();
                        epgEventTimerInfo = mTvTimerManager.getEpgTimerEventByIndex(listView.getSelectedItemPosition());
                        Time baseTime = new Time();
                        baseTime.setToNow();
                        int startTime = epgEventTimerInfo.startTime + mTvEpgManager.getEpgEventOffsetTime(baseTime, true);
                        intent.putExtra("EventBaseTime", startTime);
                        startActivity(intent);
                    }
                }
                finish();
                return true;
                /***** End by gerard.jiang 2013/04/17 *****/
        }
        return super.onKeyDown(keyCode, event);
    }
}
