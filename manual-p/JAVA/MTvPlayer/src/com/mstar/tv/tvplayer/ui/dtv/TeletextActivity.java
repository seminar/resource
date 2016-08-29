
package com.mstar.tv.tvplayer.ui.dtv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.mstar.android.MKeyEvent;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.TvEvent;

/**
 * This class implements the activity to support teletext capability.
 *
 * @author jacky.lin
 */
public class TeletextActivity extends MstarBaseActivity {

    private final static String TAG = "TeletextActivity";

    private TvChannelManager mTvChannelManager;

    private Runnable killself = new Runnable() {

        @Override
        public void run() {
            mTvChannelManager.closeTeletext();
            // after close teletext, should direct activity to root activity
            Intent intent = new Intent(TeletextActivity.this, RootActivity.class);
            startActivity(intent);
        }
    };

    protected Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TvEvent.CHANGE_TTX_STATUS) {
                Bundle b = msg.getData();
                // @todo: clearify teletext ui flow to sync here.
                // here is just a sample to show the event handle
                Intent intent = new Intent(TeletextActivity.this, RootActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate");
        super.onCreate(savedInstanceState);
        mTvChannelManager = TvChannelManager.getInstance();
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getBooleanExtra("TTX_MODE_CLOCK", false)) {
                if (mTvChannelManager.openTeletext(TvChannelManager.TTX_MODE_CLOCK) == false) {
                    Log.e(TAG, "open teletext false");
                } else {
                    myHandler.postDelayed(killself, 5000);
                }
            } else {
                if (mTvChannelManager.openTeletext(TvChannelManager.TTX_MODE_NORMAL) == false) {
                    Log.e(TAG, "open teletext false");
                }
            }
        } else {
            if (mTvChannelManager.openTeletext(TvChannelManager.TTX_MODE_NORMAL) == false) {
                Log.e(TAG, "open teletext false");
            }
        }
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacks(killself);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mTvChannelManager.closeTeletext();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
        // finish();
        // return true;
        // }
        // else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
        // finish();
        // return true;
        // }
        // else if (SwitchPageHelper.goToPvrPage(this, keyCode) == true) {
        // finish();
        // return true;
        // }
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_0);
                return true;
            case KeyEvent.KEYCODE_1:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_1);
                return true;
            case KeyEvent.KEYCODE_2:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_2);
                return true;
            case KeyEvent.KEYCODE_3:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_3);
                return true;
            case KeyEvent.KEYCODE_4:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_4);
                return true;
            case KeyEvent.KEYCODE_5:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_5);
                return true;
            case KeyEvent.KEYCODE_6:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_6);
                return true;
            case KeyEvent.KEYCODE_7:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_7);
                return true;
            case KeyEvent.KEYCODE_8:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_8);
                return true;
            case KeyEvent.KEYCODE_9:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_DIGIT_9);
                return true;
            case KeyEvent.KEYCODE_PAGE_UP:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_PAGE_UP);
                return true;
            case KeyEvent.KEYCODE_PAGE_DOWN:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_PAGE_DOWN);
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_PAGE_LEFT);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_PAGE_RIGHT);
                return true;
            case MKeyEvent.KEYCODE_TTX:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_NORMAL_MODE_NEXT_PHASE);
                if (!mTvChannelManager.isTeletextDisplayed()) {
                    Intent intent = new Intent(TeletextActivity.this, RootActivity.class);
                    startActivity(intent);
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                mTvChannelManager.closeTeletext();
                // after close teletext, should direct activity to root activity
                Intent intent = new Intent(TeletextActivity.this, RootActivity.class);
                startActivity(intent);
                return true;
            case KeyEvent.KEYCODE_PROG_BLUE:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_CYAN);
                return true;
            case KeyEvent.KEYCODE_PROG_YELLOW:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_YELLOW);
                return true;
            case KeyEvent.KEYCODE_PROG_GREEN:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_GREEN);
                return true;
            case KeyEvent.KEYCODE_PROG_RED:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_RED);
                return true;
            case MKeyEvent.KEYCODE_TV_SETTING:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_MIX);
                return true;
            case MKeyEvent.KEYCODE_MSTAR_UPDATE:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_UPDATE);
                return true;
            case MKeyEvent.KEYCODE_MSTAR_SIZE:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_SIZE);
                return true;
            case MKeyEvent.KEYCODE_MSTAR_INDEX:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_INDEX);
                return true;
            case MKeyEvent.KEYCODE_MSTAR_HOLD:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_HOLD);
                return true;
            case MKeyEvent.KEYCODE_MSTAR_REVEAL:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_REVEAL);
                return true;
            case MKeyEvent.KEYCODE_LIST:
                mTvChannelManager.sendTeletextCommand(TvChannelManager.TTX_COMMAND_LIST);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
