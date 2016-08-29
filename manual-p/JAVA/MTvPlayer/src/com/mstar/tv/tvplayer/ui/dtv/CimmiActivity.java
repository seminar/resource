//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2013 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tv.tvplayer.ui.dtv;

import java.util.ArrayList;

import android.util.Log;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.tvapi.dtv.vo.EnumMmiType;
import com.mstar.android.tv.TvCiManager;
import com.mstar.android.tv.TvCiManager.OnUiEventListener;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tvframework.MstarBaseActivity;

public class CimmiActivity extends MstarBaseActivity {
    private static final String TAG = "CimmiActivity";

    private static int CIMMI_DELAY_TIME_COUNTER_MAX = 120; // 90s

    private ArrayAdapter<String> adapter = null;

    private ListView cimmiListView = null;

    private TextView title = null;

    private TextView subTitle = null;

    private TextView bottom = null;

    private TvCiManager tvCiManager = null;

    private Thread closeCimmiDetectThread = null;

    private int cimmiTimeCounter = 0;

    private boolean cimMiHasDestory = false;

    private ArrayList<String> data = new ArrayList<String>();

    private OnUiEventListener mUiEventListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cimmi_list_view);
        cimmiListView = (ListView) findViewById(R.id.cimmi_list_view);
        title = (TextView) findViewById(R.id.cimmi_subtitle_one);
        subTitle = (TextView) findViewById(R.id.cimmi_subtitle_two);
        bottom = (TextView) findViewById(R.id.cimmi_text_end);
        adapter = new ArrayAdapter<String>(this, R.layout.pvr_menu_info_list_view_item, data);

        cimmiListView.setAdapter(adapter);
        cimmiListView.setDividerHeight(0);

        tvCiManager = TvCiManager.getInstance();

        cimmiListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                resetCimmiTimeCounter();
                tvCiManager.answerMenu((short) (cimmiListView.getSelectedItemPosition() + 1));
            }
        });

        cimmiUiDataReady();
        cimmiListView.requestFocus();

        closeCimmiDetectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!cimMiHasDestory) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    cimmiTimeCounter++;
                    if (cimmiTimeCounter >= CIMMI_DELAY_TIME_COUNTER_MAX) {
                        Log.i(TAG,"timeout, send EV_CIMMI_UI_CLOSEMMI to close cimmi");
                        finish();
                    }
                }
            }
        });

        if (closeCimmiDetectThread != null) {
            closeCimmiDetectThread.start();
        }
        resetCimmiTimeCounter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUiEventListener = new UiEventListener();
        TvCiManager.getInstance().registerOnUiEventListener(mUiEventListener);
    }

    @Override
    public void onPause() {
        TvCiManager.getInstance().unregisterOnUiEventListener(mUiEventListener);
        mUiEventListener = null;
        super.onPause();
    }

    public void resetCimmiTimeCounter() {
        cimmiTimeCounter = 0;
    }

    public void cimmiUiDataReady() {
        String strTitle = null;
        String strSubTitle = null;
        String strBottom = null;
        String strSelection = null;
        boolean hasData = false;
        int mmiType = TvCiManager.CIMMI_NONE;
        hasData = tvCiManager.isDataExisted();

        if (hasData == false) {
            return;
        }
        mmiType = tvCiManager.getCiMmiType();
        if (mmiType == TvCiManager.CIMMI_MENU) {
            // title text
            if (tvCiManager.getMenuTitleLength() > 0) {
                strTitle = tvCiManager.getMenuTitleString();
            } else {
                strTitle = " ";
            }
            title.setText(strTitle);

            // subtitle text
            if (tvCiManager.getMenuSubtitleLength() > 0) {
                strSubTitle = tvCiManager.getMenuSubtitleString();
            } else {
                strSubTitle = " ";
            }
            subTitle.setText(strSubTitle);

            // bottom text
            if (tvCiManager.getMenuBottomLength() > 0) {
                strBottom = tvCiManager.getMenuBottomString();
            } else {
                strBottom = " ";
            }
            bottom.setText(strBottom);
            // selection list
            data.clear();
            for (int i = 0; i < tvCiManager.getMenuChoiceNumber(); i++) {
                strSelection = tvCiManager.getMenuSelectionString(i);
                data.add(strSelection);

            }
            adapter.notifyDataSetChanged();
            cimmiListView.invalidate();

        } else if (mmiType == TvCiManager.CIMMI_LIST) {
            // title text
            if (tvCiManager.getListTitleLength() > 0) {
                strTitle = tvCiManager.getListTitleString();
            } else {
                strTitle = " ";
            }
            title.setText(strTitle);

            // subtitle text
            if (tvCiManager.getListSubtitleLength() > 0) {
                strSubTitle = tvCiManager.getListSubtitleString();
            } else {
                strSubTitle = " ";
            }
            subTitle.setText(strSubTitle);

            // bottom text
            if (tvCiManager.getListBottomLength() > 0) {
                strBottom = tvCiManager.getListBottomString();
            } else {
                strBottom = " ";
            }
            bottom.setText(strSubTitle);

            // selection list
            adapter.clear();
            for (int i = 0; i < tvCiManager.getListChoiceNumber(); i++) {
                strSelection = tvCiManager.getListSelectionString(i);
                adapter.add(strSelection);
            }
            adapter.notifyDataSetChanged();
            cimmiListView.invalidate();
        } else if (mmiType == TvCiManager.CIMMI_ENQ) {
            // password
            PWDDialog pwdDialog = new PWDDialog(this, android.R.style.Theme_Panel);
            pwdDialog.show();

            // title text
            if (tvCiManager.getEnqLength() > 0) {
                strTitle = tvCiManager.getEnqString();
            } else {
                strTitle = " ";
            }
            title.setText(strTitle);

            // subtitle text
            strSubTitle = " ";
            subTitle.setText(strSubTitle);

            // bottom text
            strBottom = " ";
            bottom.setText(strSubTitle);
        }

    }

    private class UiEventListener implements OnUiEventListener {
        @Override
        public boolean onUiEvent(int what) {
            switch (what) {
                case TvCiManager.TVCI_UI_DATA_READY: {
                    cimmiUiDataReady();
                    resetCimmiTimeCounter();
                } break;
                case TvCiManager.TVCI_UI_CLOSEMMI: {
                    Log.i(TAG,"----------EV_CIMMI_UI_CLOSEMMI");
                    finish();
                } break;
                case TvCiManager.TVCI_UI_CARD_INSERTED: {
                    Toast toast = Toast.makeText(CimmiActivity.this,
                            R.string.str_cimmi_hint_ci_inserted, 3);
                    toast.show();
                } break;
                case TvCiManager.TVCI_UI_CARD_REMOVED: {
                    Toast toast = Toast.makeText(CimmiActivity.this,
                            R.string.str_cimmi_hint_ci_removed, 3);
                    toast.show();
                } break;
                case TvCiManager.TVCI_UI_AUTOTEST_MESSAGE_SHOWN: {
                    Toast toast = Toast.makeText(CimmiActivity.this,
                            R.string.str_cimmi_hint_ci_auto_test, 3);
                    toast.show();
                } break;
                default: {
                } break;
            }
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_M:
            case KeyEvent.KEYCODE_MENU:
                Log.i(TAG,"----------KEYCODE_MENU");
                tvCiManager.close();
                cimmiTimeCounter = CIMMI_DELAY_TIME_COUNTER_MAX - 3;// close
                                                                    // after 3s
                return true;

            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_CLEAR:
                resetCimmiTimeCounter();
                Log.i(TAG,"----------KEYCODE_BACK");
                tvCiManager.backMenu();
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    class PWDDialog extends Dialog {
        private EditText editText = null;

        public PWDDialog(Context context, int theme) {
            super(context, theme);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.cimmi_pwd_dialog);
            super.onCreate(savedInstanceState);

            editText = (EditText) findViewById(R.id.editText);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    short length = 0;
                    resetCimmiTimeCounter();
                    length = tvCiManager.getEnqAnsLength();
                    // Log.i(TAG,"##length>>:" +
                    // length+"=="+editText.length()+">>"+editText.getEditableText().toString());
                    if (editText.length() >= length) {
                        tvCiManager.answerEnq(editText.getEditableText().toString());
                        editText.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    protected void onDestroy() {
        cimMiHasDestory = true;
        super.onDestroy();
    }
}
