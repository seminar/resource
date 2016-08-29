
package com.mstar.tv.tvplayer.ui.ca;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.ca.ippv.IppvProgramInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class CaInformationActivity extends Activity {
    private String OperatorId;

    private String CURRNT_OPERATOR_ID = "OperatorId";

    private String TAG = "CaInformationActivity";

    protected LinearLayout linearlayout_ca_info_entitle;

    protected LinearLayout linearlayout_ca_info_detitle;

    protected LinearLayout linearlayout_ca_info_purse;

    protected LinearLayout linearlayout_ca_card_info;

    protected LinearLayout linearlayout_ca_ippv_program;

    private Intent intent = new Intent();

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaInformationActivity.this.finish();
                Intent intent = new Intent(CaInformationActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_card_information);
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            OperatorId = getIntent().getStringExtra(CURRNT_OPERATOR_ID);
            Log.d(TAG, "get String extra,OperatorId=" + OperatorId);
        }
        LittleDownTimer.setHandler(handler);
        findViews();
        setOnClickLisenters();
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaInformationActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaInformationActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();
    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaInformationActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void findViews() {
        linearlayout_ca_info_entitle = (LinearLayout) findViewById(R.id.linearlayout_ca_info_entitle);
        linearlayout_ca_info_detitle = (LinearLayout) findViewById(R.id.linearlayout_ca_info_detitle);
        linearlayout_ca_info_purse = (LinearLayout) findViewById(R.id.linearlayout_ca_info_purse);
        linearlayout_ca_card_info = (LinearLayout) findViewById(R.id.linearlayout_ca_card_info);
        linearlayout_ca_ippv_program = (LinearLayout) findViewById(R.id.linearlayout_ca_ippv_program);
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.linearlayout_ca_info_entitle:
                        intent.setClass(CaInformationActivity.this, CaEntitleInfoActivity.class);
                        intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_info_detitle:
                        intent.setClass(CaInformationActivity.this, CaDetitleInfoActivity.class);
                        intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_info_purse:
                        intent.setClass(CaInformationActivity.this, CaPurseInfoActivity.class);
                        intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_ippv_program:
                        intent.setClass(CaInformationActivity.this, IppvProgramInfo.class);
                        intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.linearlayout_ca_card_info:
                        intent.setClass(CaInformationActivity.this, CaCardInfoActivity.class);
                        intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        linearlayout_ca_info_entitle.setOnClickListener(listener);
        linearlayout_ca_info_detitle.setOnClickListener(listener);
        linearlayout_ca_info_purse.setOnClickListener(listener);
        linearlayout_ca_ippv_program.setOnClickListener(listener);
        linearlayout_ca_card_info.setOnClickListener(listener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaInformationActivity.this, CaOperatorListActivity.class);
                if ((getIntent() != null) && (getIntent().getExtras() != null)) {
                    OperatorId = getIntent().getStringExtra(CURRNT_OPERATOR_ID);
                    Log.d(TAG, "get String extra,OperatorId=" + OperatorId);
                }
                intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
