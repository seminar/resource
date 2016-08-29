
package com.mstar.tv.tvplayer.ui.holder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;

import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tvapi.common.vo.EnumSleepTimeState;
import com.mstar.android.tvapi.common.vo.StandardTime;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.settings.SetTimeOffDialogActivity;
import com.mstar.tv.tvplayer.ui.settings.SetTimeOnDialogActivity;

public class TimeViewHolder {
    protected boolean isClockExisted = false;

    // protected ST_Time curDateTime = new ST_Time();
    protected Timer timer = new Timer();

    protected ComboButton comboBtnDate;

    protected ComboButton comboBtnCurrentTime;

    protected MyButton myBtnOffTime;

    protected MyButton myBtnScheduledTime;

    protected ComboButton comboBtnSleepTime;

    // protected ComboButton comboBtnAutoTime;
    private TvTimerManager tvTimerManager = null;

    protected Activity activity;

    protected Handler handler;

    //zb20141009 add
    private int menutimeindex = 0;
    private TvCommonManager mTvCommonManager;
    protected ComboButton comboBtnMenuTime;
    //end
    
    public TimeViewHolder(Activity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
        tvTimerManager = TvTimerManager.getInstance();
        //zb20141009 add
        mTvCommonManager=TvCommonManager.getInstance();
        //end
    }

    public void toHandleMsg(Message msg) {
        switch (msg.what) {
            case 10086: {
                // comboBtnCurrentTime.setTextInChild(2, curDateTime.hour + ":"
                // + curDateTime.minute + ":" + curDateTime.second);
                StandardTime curTime = tvTimerManager.getCurTimer();
                String dispDayTime = curTime.hour + ":" + curTime.minute + ":" + curTime.second;
                comboBtnCurrentTime.setTextInChild(2, dispDayTime);
            }
                break;
        }
    }

    public void findViews() {
        myBtnOffTime = new MyButton(activity, R.id.linearlayout_time_offtime);
        myBtnScheduledTime = new MyButton(activity, R.id.linearlayout_time_schedule);
        myBtnOffTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, SetTimeOffDialogActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        myBtnScheduledTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, SetTimeOnDialogActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        comboBtnDate = new ComboButton(activity, null, R.id.linearlayout_time_date, 1, 2, false);
        comboBtnCurrentTime = new ComboButton(activity, null, R.id.linearlayout_time_currenttime,
                1, 2, false);
        comboBtnSleepTime = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_sleep_mode_vals), R.id.linearlayout_time_sleep, 1, 2, true) {
            @Override
            public void doUpdate() {
                tvTimerManager
                        .setSleepMode(EnumSleepTimeState.values()[comboBtnSleepTime.getIdx()]);
            }
        };
        // comboBtnAutoTime = new ComboButton(this,
        // getResources().getStringArray(R.array.str_arr_time_autotimetype),
        // R.id.linearlayout_time_autotime, 1, 2, true){
        // @Override
        // public void doUpdate() {
        // // TODO Auto-generated method stub
        // super.doUpdate();
        // }
        // };
        
      //zb20141009 add
        comboBtnMenuTime = new ComboButton(activity, activity.getResources().getStringArray(
                R.array.str_arr_set_menutimetype), R.id.linearlayout_menutime, 1, 2, true) {
            @Override
            public void doUpdate() {
            	mTvCommonManager.setOsdDuration(comboBtnMenuTime.getIdx());
            	int second=mTvCommonManager.getOsdTimeoutInSecond();
            	menutimeindex = mTvCommonManager.getOsdDuration();
                if (menutimeindex == 5) {
                    LittleDownTimer.stopMenu();
                } else {
                    LittleDownTimer.setMenuStatus(second);
                }
            }
        };
        //end
        
        setOnClickListeners();
        setOnFocusChangeListeners();
        //myBtnOffTime.setFocused();
        //zb20141009 add
        comboBtnMenuTime.setFocused();
        //end
        comboBtnDate.setFocusable(false);
        comboBtnCurrentTime.setFocusable(false);
    }

    public void loadDataToUI() {
        loadDataToMyBtnOffTime();
        loadDataToMyBtnScheduledTime();
        if (!isClockExisted) {
            isClockExisted = true;
            StandardTime curTime = tvTimerManager.getCurTimer();
            // curDateTime.set(timerSkin.getCurTimer());
            // comboBtnDate.setTextInChild(2, curDateTime.year + "."
            // + curDateTime.month + "." + curDateTime.monthDay);
            String dispDateTime, dispDayTime;
            dispDateTime = curTime.year + "/" + curTime.month + "/" + curTime.monthDay;
            dispDayTime = curTime.hour + ":" + curTime.minute + ":" + curTime.second;
            comboBtnDate.setTextInChild(2, dispDateTime);
            // comboBtnCurrentTime.setTextInChild(2, curDateTime.hour + ":"
            // + curDateTime.minute + ":" + curDateTime.second);
            comboBtnCurrentTime.setTextInChild(2, dispDayTime);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // curDateTime.second++;
                    // if (curDateTime.second >= 60)
                    // {
                    // curDateTime.second = 0;
                    // curDateTime.minute++;
                    // if (curDateTime.minute >= 60)
                    // {
                    // curDateTime.minute = 0;
                    // curDateTime.hour++;
                    // if (curDateTime.hour >= 24)
                    // {
                    // curDateTime.hour = 0;
                    // }
                    // }
                    // }
                    handler.sendEmptyMessage(10086);
                }
            }, 1000, 1000);
        }
        comboBtnSleepTime.setIdx(tvTimerManager.getSleepMode().ordinal());
        //zb20141009 add
        comboBtnMenuTime.setIdx(mTvCommonManager.getOsdDuration());
        //end
        // comboBtnAutoTime.setIdx(0);
    }

    public void endUIClock() {
        timer.cancel();
    }

    public void loadDataToMyBtnOffTime() {
        StandardTime dateTime;
        if (tvTimerManager.isOffTimerEnable()) {
            dateTime = tvTimerManager.getOffTimer();
            myBtnOffTime.setTextInChild(2, "" + dateTime.hour + ":" + dateTime.minute);
        } else {
            myBtnOffTime.setTextInChild(2,
                    activity.getResources().getStringArray(R.array.str_arr_time_switch)[0]);
        }
    }

    public void loadDataToMyBtnScheduledTime() {
        StandardTime dateTime;
        if (tvTimerManager.isOnTimerEnable()) {
            dateTime = tvTimerManager.getOnTimer();
            myBtnScheduledTime.setTextInChild(2, "" + dateTime.hour + ":" + dateTime.minute);
        } else {
            myBtnScheduledTime.setTextInChild(2,
                    activity.getResources().getStringArray(R.array.str_arr_time_switch)[0]);
        }
    }

    private void setOnClickListeners() {
        OnClickListener comoBtnOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.VISIBLE);
                    container.getChildAt(3).setVisibility(View.VISIBLE);
                } else {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.GONE);
                    container.getChildAt(3).setVisibility(View.GONE);
                }
            }
        };
        comboBtnSleepTime.setOnClickListener(comoBtnOnClickListener);
        //zb20141009 add
        comboBtnMenuTime.setOnClickListener(comoBtnOnClickListener);
        //end
        
        // comboBtnAutoTime.setOnClickListener(comoBtnOnClickListener);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener comboBtnFocusListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // if(hasFocus){
                // LinearLayout container = (LinearLayout)v;
                // container.getChildAt(0).setVisibility(View.VISIBLE);
                // container.getChildAt(3).setVisibility(View.VISIBLE);
                // }
                // else
                {
                    LinearLayout container = (LinearLayout) v;
                    container.getChildAt(0).setVisibility(View.GONE);
                    container.getChildAt(3).setVisibility(View.GONE);
                }
            }
        };
        comboBtnSleepTime.setOnFocusChangeListener(comboBtnFocusListener);
        //zb20141009 add
        comboBtnMenuTime.setOnFocusChangeListener(comboBtnFocusListener);
        //end
        // comboBtnAutoTime.setOnFocusChangeListener(comboBtnFocusListener);
    }
}
