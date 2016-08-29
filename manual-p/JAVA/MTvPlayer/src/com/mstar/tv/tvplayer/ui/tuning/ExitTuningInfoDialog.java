
package com.mstar.tv.tvplayer.ui.tuning;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;

public class ExitTuningInfoDialog extends Dialog {
    /** Called when the activity is first created. */
    @SuppressWarnings("unused")
    private ViewHolder viewholder_exittune;

    private int mTvSystem = 0;

    private static int ATV_MIN_FREQ = 45200;

    private static int ATV_MAX_FREQ = 876250;

    private static int ATV_EVENTINTERVAL = 500 * 1000;// every 500ms to show

    // FIXME: add delay time for executing stopDtvScan()
    private static final int STOP_DELAY = 3 * 1000;

    protected TextView textview_cha_exittune_yes;

    protected TextView textview_cha_exittune_no;
    
    protected TextView textview_cha_exittuning_info;

    TvChannelManager mTvChannelManager = null;
    
    //add by wxy begin
    private int inputsource;
    
    private TvCommonManager mTvCommonManager;
      
    //add end

    // FIXME: add delay time for executing stopDtvScan()
    private Handler mDtvStopHandler = new Handler();

    // FIXME: add delay time for executing stopDtvScan()
    private Runnable mDtvStopTask = new Runnable() {
        @Override
        public void run() {
            ExitTuningActivityExit(true);
        }
    };

    @SuppressWarnings("unused")
    private ViewHolder viewholder_channeltune;

    public ExitTuningInfoDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        setContentView(R.layout.exittuninginfo_dialog);
        textview_cha_exittune_yes = (TextView) findViewById(R.id.textview_cha_exittune_yes);
        textview_cha_exittune_no = (TextView) findViewById(R.id.textview_cha_exittune_no);
        
        //add by wxy begin 
        textview_cha_exittuning_info = (TextView)findViewById(R.id.textview_cha_exittuning_info);
        
        inputsource = TvCommonManager.getInstance().getCurrentTvInputSource();
        if(inputsource==mTvCommonManager.INPUT_SOURCE_DTV )
        {
        	textview_cha_exittuning_info.setText(R.string.str_cha_exittuning_info_dtv);
        }
        else if(inputsource==mTvCommonManager.INPUT_SOURCE_ATV)
        {
        	textview_cha_exittuning_info.setText(R.string.str_cha_exittuning_info_atv);
        }
        else 
        {
        	textview_cha_exittuning_info.setText(R.string.str_cha_exittuning_info);
        }
        //add end
        
        viewholder_exittune = new ViewHolder(ExitTuningInfoDialog.this);
        textview_cha_exittune_yes.requestFocus();
        registerListeners();
        mTvChannelManager = TvChannelManager.getInstance();
    }

    private void registerListeners() {
        textview_cha_exittune_yes.setOnClickListener(listener);
        textview_cha_exittune_no.setOnClickListener(listener);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ExitTuningActivityExit(false);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            switch (view.getId()) {
                case R.id.textview_cha_exittune_yes:
                	AutoTuneOptionActivity.isautotuning = false;   //add by wxy
                    if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                        /*
                         *  FIXME: add delay time for executing stopDtvScan()
                         *  pauseDtvScan() is processed before creating ExitTuningInfoDialog.
                         *  It needs to postpone stopDtvScan() for 3 seconnds to wait command complete.
                         *  (ANR occured if no any delay time for executing stopDtvScan())
                         *  (Mantis issue: 0572214)
                         */
                        mDtvStopHandler.removeCallbacks(mDtvStopTask);
                        mDtvStopHandler.postDelayed(mDtvStopTask, STOP_DELAY);
                        setCancelable(false);
                        textview_cha_exittune_no.setFocusable(false);
                        textview_cha_exittune_no.setFocusableInTouchMode(false);
                    } else {
                        ExitTuningActivityExit(true);
                    }
                    break;
                case R.id.textview_cha_exittune_no:
                    ExitTuningActivityExit(false);
                    break;
                default:
                    ExitTuningActivityExit(false);
                    break;
            }
        }
    };

    private void ExitTuningActivityExit(boolean flag) {
        Intent intent = new Intent();

        // FIXME: add delay time for executing stopDtvScan()
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mDtvStopHandler.removeCallbacks(mDtvStopTask);
        }

        if (flag == true)// stop tuning
        {
            switch (mTvChannelManager.getTuningStatus()) {
                case TvChannelManager.TUNING_STATUS_ATV_SCAN_PAUSING:
                    mTvChannelManager.stopAtvAutoTuning();
                    mTvChannelManager.changeToFirstService(
                            TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                            TvChannelManager.FIRST_SERVICE_DEFAULT);
                    intent.setAction(TvIntent.MAINMENU);
                    intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
                    getContext().startActivity(intent);
                    this.dismiss();
                    break;
                case TvChannelManager.TUNING_STATUS_DTV_SCAN_PAUSING:
                    mTvChannelManager.stopDtvScan();
                    if (mTvChannelManager.getUserScanType() == mTvChannelManager.TV_SCAN_ALL) {
                        boolean res = mTvChannelManager.startAtvAutoTuning(ATV_EVENTINTERVAL,
                                ATV_MIN_FREQ, ATV_MAX_FREQ);
                        if (res == false) {
                            Log.e("TuningService", "atvSetAutoTuningStart Error!!!");
                        }
                    } else {
                        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ISDB) {
                            TvIsdbChannelManager.getInstance().genMixProgList(false);
                        }
                        mTvChannelManager.changeToFirstService(
                                TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV,
                                TvChannelManager.FIRST_SERVICE_DEFAULT);
                        intent.setAction(TvIntent.MAINMENU);
                        intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
                        getContext().startActivity(intent);
                    }
                    this.dismiss();
                    break;
                default:
                    break;
            }
        } else
        // resume tuning
        {
            switch (mTvChannelManager.getTuningStatus()) {
                case TvChannelManager.TUNING_STATUS_ATV_SCAN_PAUSING:
                    mTvChannelManager.resumeAtvAutoTuning();
                    this.dismiss();
                    break;
                case TvChannelManager.TUNING_STATUS_DTV_SCAN_PAUSING:
                    mTvChannelManager.resumeDtvScan();
                    this.dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
