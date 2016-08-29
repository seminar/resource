
package com.mstar.tv.tvplayer.ui.ca;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class CaActivity extends Activity {
    private String TAG = "CaActivity";

    protected LinearLayout linearlayout_ca_card_version;

    protected LinearLayout linearlayout_ca_card_info;

    protected LinearLayout linearlayout_ca_card_management;

    private Intent intent = new Intent();

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaActivity.this.finish();
                Intent intent = new Intent(CaActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_main_menu);
        LittleDownTimer.setHandler(handler);
        fingViews();
        setOnClickLisenters();
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();
    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void fingViews() {
        linearlayout_ca_card_version = (LinearLayout) findViewById(R.id.linearlayout_ca_card_version);
        linearlayout_ca_card_info = (LinearLayout) findViewById(R.id.linearlayout_ca_card_info);
        linearlayout_ca_card_management = (LinearLayout) findViewById(R.id.linearlayout_ca_card_management);
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "view.getId()=" + view.getId() + "\n");
                Log.i(TAG, "R.id.linearlayout_ca_card_info=" + R.id.linearlayout_ca_card_info
                        + "\n");
                Log.i(TAG, "R.id.linearlayout_ca_card_management="
                        + R.id.linearlayout_ca_card_management + "\n");
                switch (view.getId()) {
                    case R.id.linearlayout_ca_card_version:
                        intent.setClass(CaActivity.this, CaVersionInfoActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_card_info:
                        intent.setClass(CaActivity.this, CaOperatorListActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_card_management:
                        intent.setClass(CaActivity.this, CaManagementActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        linearlayout_ca_card_version.setOnClickListener(listener);
        linearlayout_ca_card_info.setOnClickListener(listener);
        linearlayout_ca_card_management.setOnClickListener(listener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setAction(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
