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

package com.mstar.tv.tvplayer.ui.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaDetitleChkNums;
import com.mstar.util.Tools;

import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.ca.CaErrorType.RETURN_CODE;

public class CaDetitleInfoActivity extends Activity {
    private final String TAG = "CaDetitleInfoActivity";

    private String OperatorId;

    private ListView listdetitle;

    private TextView textview_ca_detitle_item_state_Head;

    private final String CURRNT_OPERATOR_ID = "OperatorId";

    private TvCaManager tvCaManager = null;

    private final List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

    private CaDetitleChkNums DetitleChkNums;

    private SimpleAdapter adapter;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaDetitleInfoActivity.this.finish();
                Intent intent = new Intent(CaDetitleInfoActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_detitle_info);
        LittleDownTimer.setHandler(handler);
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            OperatorId = getIntent().getStringExtra(CURRNT_OPERATOR_ID);
            Log.d(TAG, "get String extra,OperatorId=" + OperatorId);
        }
        findViews();

        System.out.println("listdetitle.hasFocus()===" + listdetitle.hasFocus());
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaDetitleInfoActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaDetitleInfoActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();

    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaDetitleInfoActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void findViews() {
        listdetitle = (ListView) findViewById(R.id.listdetitle);
        textview_ca_detitle_item_state_Head = (TextView) findViewById(R.id.textview_ca_detitle_item_state_Head);

        contents.clear();
        adapter = new SimpleAdapter(this, contents, R.layout.ca_detitle_item, new String[] {
            "detitle_id"
        }, new int[] {
            R.id.textview_ca_detitle_item_list
        });

        getInitData();
        listdetitle.setAdapter(adapter);
    }

    private void getInitData() {
        TVRootApp app = (TVRootApp) getApplication();
        tvCaManager = TvCaManager.getInstance();
        contents.clear();
        DetitleChkNums = tvCaManager.CaGetDetitleChkNums(Short.parseShort(OperatorId));
        if (DetitleChkNums == null) {
            return;
        }

        short aa = DetitleChkNums.sDetitleChkNumsState;
        int bb = RETURN_CODE.ST_CA_RC_DATA_NOT_FIND.getRetCode();
        if (DetitleChkNums.sDetitleChkNumsState == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            for (int i = 0; i < DetitleChkNums.pdwEntitleIds.length; i++) {
                if (DetitleChkNums.pdwEntitleIds[i] != 0) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("detitle_id", DetitleChkNums.pdwEntitleIds[i]);
                    contents.add(map);
                }
            }
            if (DetitleChkNums.bReadFlag) {
                textview_ca_detitle_item_state_Head.setText(R.string.str_ca_detitle_state_yes);
            } else {
                textview_ca_detitle_item_state_Head.setText(R.string.str_ca_detitle_state_no);
            }

        } else if (DetitleChkNums.sDetitleChkNumsState == RETURN_CODE.ST_CA_RC_CARD_INVALID
                .getRetCode()) {
            toastShow(R.string.st_ca_rc_card_invalid);
        } else if (DetitleChkNums.sDetitleChkNumsState == RETURN_CODE.ST_CA_RC_POINTER_INVALID
                .getRetCode()) {
            toastShow(R.string.st_ca_rc_pointer_invalid);
        } else if (DetitleChkNums.sDetitleChkNumsState == RETURN_CODE.ST_CA_RC_DATA_NOT_FIND
                .getRetCode()) {
            toastShow(R.string.st_ca_rc_data_not_find);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_PROG_RED) {
            System.out.println(DetitleChkNums.pdwEntitleIds[0] + "--------------"
                    + listdetitle.hasFocus());
            if (DetitleChkNums.pdwEntitleIds[0] != 0 && listdetitle.hasFocus()) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.really_remove_this_info)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean state = tvCaManager.CaDelDetitleChkNum(
                                                Short.parseShort(OperatorId),
                                                Integer.parseInt(contents.get(
                                                        listdetitle.getSelectedItemPosition()).get(
                                                        "detitle_id")
                                                        + ""));
                                        if (state) {
                                            Tools.toastShow(R.string.delete_successed,
                                                    CaDetitleInfoActivity.this);
                                        } else {
                                            Tools.toastShow(R.string.delete_failed,
                                                    CaDetitleInfoActivity.this);
                                        }
                                        getInitData();
                                        adapter.notifyDataSetChanged();
                                    }
                                }).create().show();
            }
        } else if (keyCode == KeyEvent.KEYCODE_PROG_GREEN) {
            if (DetitleChkNums.pdwEntitleIds[0] != 0) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.really_remove_all_info)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean state = tvCaManager.CaDelDetitleChkNum(
                                                Short.parseShort(OperatorId), 0);
                                        if (state) {
                                            Tools.toastShow(R.string.delete_successed,
                                                    CaDetitleInfoActivity.this);
                                        } else {
                                            Tools.toastShow(R.string.delete_failed,
                                                    CaDetitleInfoActivity.this);
                                        }
                                        getInitData();
                                        adapter.notifyDataSetChanged();
                                    }
                                }).create().show();
            }
        }
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaDetitleInfoActivity.this, CaInformationActivity.class);
                intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toastShow(int resId) {
        Toast toast = new Toast(this);
        TextView MsgShow = new TextView(this);
        toast.setDuration(Toast.LENGTH_LONG);
        MsgShow.setTextColor(Color.RED);
        MsgShow.setTextSize(25);
        MsgShow.setText(resId);
        toast.setView(MsgShow);
        toast.show();
    }

}
