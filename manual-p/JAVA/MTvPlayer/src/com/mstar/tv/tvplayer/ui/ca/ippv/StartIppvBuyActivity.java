
package com.mstar.tv.tvplayer.ui.ca.ippv;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mstar.android.tvapi.dtv.vo.CaStartIPPVBuyDlgInfo;
import com.mstar.tv.tvplayer.ui.R;
import android.util.Log;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.common.CaManager;
import com.mstar.android.tvapi.dtv.common.CaManager.OnCaEventListener;
import com.mstar.android.tvapi.dtv.vo.CaLockService;
import com.mstar.android.tvapi.dtv.vo.CaStartIPPVBuyDlgInfo;

public class StartIppvBuyActivity extends Activity {
    private static final String TAG = "StartIppvBuyActivity";
    private CaStartIPPVBuyDlgInfo StIppvInfo;

    private int pricestate;

    private OnCaEventListener mCaEventListener = null;

    class CaEventListener implements OnCaEventListener {
        @Override
        public boolean onStartIppvBuyDlg(CaManager mgr, int what, int arg1, int arg2,CaStartIPPVBuyDlgInfo arg3) {
            return true;
        }

        @Override
        public boolean onHideIPPVDlg(CaManager mgr, int what, int arg1, int arg2) {
            StartIppvBuyActivity.this.finish();
            return true;
        }

        @Override
        public boolean onEmailNotifyIcon(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onShowOSDMessage(CaManager mgr, int what, int arg1, int arg2, String arg3) {
            return true;
        }

        @Override
        public boolean onHideOSDMessage(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onRequestFeeding(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }


        @Override
        public boolean onShowBuyMessage(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onShowFingerMessage(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onShowProgressStrip(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onActionRequest(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onEntitleChanged(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onDetitleReceived(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onLockService(CaManager mgr, int what, int arg1, int arg2, CaLockService arg3) {
            return true;
        }

        @Override
        public boolean onUNLockService(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }

        @Override
        public boolean onOtaState(CaManager mgr, int what, int arg1, int arg2) {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ippv_buy_dialog);

        StIppvInfo = (CaStartIPPVBuyDlgInfo) getIntent().getSerializableExtra("StIppvInfo");
        TextView ippv_buy_dialog_segment = (TextView) findViewById(R.id.ippv_buy_dialog_segment);
        TextView ippv_buy_dialog_tvsid = (TextView) findViewById(R.id.ippv_buy_dialog_tvsid);
        TextView ippv_buy_dialog_productid = (TextView) findViewById(R.id.ippv_buy_dialog_productid);
        TextView ippv_buy_dialog_slotid = (TextView) findViewById(R.id.ippv_buy_dialog_slotid);
        TextView ippv_buy_dialog_expireddate = (TextView) findViewById(R.id.ippv_buy_dialog_expireddate);
        final TextView ippv_buy_dialog_price = (TextView) findViewById(R.id.ippv_buy_dialog_price);
        Button confirm_button = (Button) findViewById(R.id.ippv_buy_dialog_confirm);
        Button cancel_button = (Button) findViewById(R.id.ippv_buy_dialog_cancel);
        confirm_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StartIppvBuyActivity.this, IppvPinActivity.class);
                Bundle bundle = new Bundle();
                if (pricestate == 1) {
                    bundle.putShort("price", StIppvInfo.m_Price[1].m_wPrice);
                    bundle.putShort("pricecode", StIppvInfo.m_Price[1].m_byPriceCode);
                } else if (pricestate == 0) {
                    bundle.putShort("price", StIppvInfo.m_Price[0].m_wPrice);
                    bundle.putShort("pricecode", StIppvInfo.m_Price[0].m_byPriceCode);
                } else {
                    return;
                }
                bundle.putShort("ecmpid", StIppvInfo.getwEcmPid());
                bundle.putShort("messagetype", StIppvInfo.getWyMessageType());
                intent.putExtras(bundle);
                StartIppvBuyActivity.this.startActivity(intent);
                finish();

            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        switch (StIppvInfo.wyMessageType) {
            case 0:
                ippv_buy_dialog_segment.setText(getResources().getString(
                        R.string.ippv_freeviewed_segment));
                ippv_buy_dialog_expireddate.setText(getResources().getString(
                        R.string.ippv_payviewed_expireddate)
                        + dateConvert(StIppvInfo.wExpiredDate));
                break;
            case 1:
                ippv_buy_dialog_segment.setText(getResources().getString(
                        R.string.ippv_payviewed_segment));
                ippv_buy_dialog_expireddate.setText(getResources().getString(
                        R.string.ippv_payviewed_expireddate)
                        + dateConvert(StIppvInfo.wExpiredDate));
                break;
            case 2:
                ippv_buy_dialog_segment.setText(getResources().getString(
                        R.string.ippt_payviewed_segment));
                ippv_buy_dialog_expireddate.setText(getResources().getString(
                        R.string.ippt_interval_min)
                        + StIppvInfo.wExpiredDate
                        + getResources().getString(R.string.str_work_time_minute_text));
                break;

        }

        ippv_buy_dialog_tvsid.setText(getResources().getString(R.string.ippv_tvsid)
                + StIppvInfo.wTvsID);
        ippv_buy_dialog_productid.setText(getResources().getString(
                R.string.ippv_payviewed_productid)
                + StIppvInfo.dwProductID);
        ippv_buy_dialog_slotid.setText(getResources().getString(R.string.ippv_payviewed_slotid)
                + StIppvInfo.wySlotID);

        int pricecode = StIppvInfo.m_Price[1].m_byPriceCode;
        if (pricecode == 1) {
            pricestate = 1;
            ippv_buy_dialog_price.setText(getResources().getString(R.string.ippv_payviewed_cantape)
                    + StIppvInfo.m_Price[1].m_wPrice);
        } else if (pricecode == 0) {
            pricestate = 0;
            ippv_buy_dialog_price.setText(getResources().getString(
                    R.string.ippv_payviewed_cannottape)
                    + StIppvInfo.m_Price[0].m_wPrice);
        }

        ippv_buy_dialog_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ippv_buy_dialog_price.setBackgroundResource(R.drawable.programme_epg_img_focus);
                } else {
                    ippv_buy_dialog_price.setBackgroundResource(Color.TRANSPARENT);
                }
            }
        });

        ippv_buy_dialog_price.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    if (pricestate == 1) {
                        ippv_buy_dialog_price.setText(getResources().getString(
                                R.string.ippv_payviewed_cannottape)
                                + StIppvInfo.m_Price[0].getM_wPrice());
                        pricestate = 0;
                    } else if (pricestate == 0) {
                        ippv_buy_dialog_price.setText(getResources().getString(
                                R.string.ippv_payviewed_cantape)
                                + StIppvInfo.m_Price[1].getM_wPrice());
                        pricestate = 1;
                    }
                }
                return false;
            }
        });
        ippv_buy_dialog_price.requestFocus();

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mCaEventListener = new CaEventListener();
        TvCaManager.getInstance().registerOnCaEventListener(mCaEventListener);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        TvCaManager.getInstance().unregisterOnCaEventListener(mCaEventListener);
        mCaEventListener = null;
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent arg1) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, arg1);
    }

    private String dateConvert(short dayNum) {
        String endDate = "";
        Calendar ca = Calendar.getInstance();
        ca.set(2000, Calendar.JANUARY, 1);
        ca.add(Calendar.DATE, dayNum);
        Format s = new SimpleDateFormat("yyyy-MM-dd");
        endDate = s.format(ca.getTime());

        return endDate;
    }
}
