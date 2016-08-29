
package com.mstar.tv.tvplayer.ui.tuning;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tvframework.MstarBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AutoUpdateScanDialogActivity extends MstarBaseActivity {

    private LinearLayout chooseLinear_yes;

    private LinearLayout chooseLinear_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_update_scan_dialog);
        findViews();

        setOnClickListenter();
    }

    private void findViews() {
        chooseLinear_yes = (LinearLayout) findViewById(R.id.linearlayout_auto_update_scan_choosen_yes);
        chooseLinear_no = (LinearLayout) findViewById(R.id.linearlayout_auto_update_scan_choosen_no);
        chooseLinear_no.requestFocus();
    }

    private void setOnClickListenter() {
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View view) {
                int currentid = getCurrentFocus().getId();
                // TODO Auto-generated method stub
                switch (currentid) {
                    case R.id.linearlayout_auto_update_scan_choosen_yes:
                        goToDtvAutoUpdateScan();
                        finish();
                        break;
                    case R.id.linearlayout_auto_update_scan_choosen_no:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        chooseLinear_yes.setOnClickListener(listener);
        chooseLinear_no.setOnClickListener(listener);
    }

    public void goToDtvAutoUpdateScan() {
        Intent intent = new Intent(this, ChannelTuning.class);
        intent.putExtra("DtvAutoUpdateScan", true);
        this.startActivity(intent);
    }
}
