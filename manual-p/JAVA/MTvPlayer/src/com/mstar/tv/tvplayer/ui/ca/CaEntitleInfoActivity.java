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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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

import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaServiceEntitles;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.ca.CaErrorType.RETURN_CODE;

public class CaEntitleInfoActivity extends Activity {
    private final String TAG = "CaEntitleInfoActivity";

    private String OperatorId;

    private ListView listentitle;

    private final String CURRNT_OPERATOR_ID = "OperatorId";

    private TvCaManager tvCaManager = null;

    private final List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
                CaEntitleInfoActivity.this.finish();
                Intent intent = new Intent(CaEntitleInfoActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_entitle_info);
        LittleDownTimer.setHandler(handler);
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            OperatorId = getIntent().getStringExtra(CURRNT_OPERATOR_ID);
            Log.d(TAG, "get String extra,OperatorId=" + OperatorId);
        }
        findViews();
    }

    @Override
    protected void onResume() {
        Log.d("TvApp", "CaEntitleInfoActivity onResume");
        LittleDownTimer.resumeMenu();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TvApp", "CaEntitleInfoActivity onPause");
        LittleDownTimer.pauseMenu();
        super.onPause();

    }

    @Override
    public void onUserInteraction() {
        Log.d("TvApp", "CaEntitleInfoActivity onUserInteraction");
        LittleDownTimer.resetMenu();
        super.onUserInteraction();
    }

    private void findViews() {
        listentitle = (ListView) findViewById(R.id.listentitle);

        contents.clear();
        SimpleAdapter adapter = new SimpleAdapter(this, contents, R.layout.ca_entitle_item,
                new String[] {
                        "id", "video", "date"
                }, new int[] {
                        R.id.textview_ca_entitle_item_id, R.id.textview_ca_entitle_item_video,
                        R.id.textview_ca_entitle_item_date
                });

        getInitData();
        listentitle.setAdapter(adapter);
    }

    private void getInitData() {
        TVRootApp app = (TVRootApp) getApplication();
        tvCaManager = TvCaManager.getInstance();
        CaServiceEntitles Entitles = tvCaManager.CaGetServiceEntitles(Short.parseShort(OperatorId));
        if (Entitles == null) {
            return;
        }
        if (Entitles.sEntitlesState == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            for (int i = 0; i < Entitles.sProductCount; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", Entitles.cEntitles[i].wwProductID);
                String video_flag_yes = this.getResources().getString(
                        R.string.str_ca_entitle_video_flag_yes);
                String video_flag_no = this.getResources().getString(
                        R.string.str_ca_entitle_video_flag_no);
                if (Entitles.cEntitles[i].m_bCanTape == 0) {
                    map.put("video", video_flag_no);
                } else {
                    map.put("video", video_flag_yes);
                }
                dateConvert(Entitles.cEntitles[i].sExpireDate);
                map.put("date", dateConvert(Entitles.cEntitles[i].sExpireDate));
                contents.add(map);
            }
        } else if (Entitles.sEntitlesState == RETURN_CODE.ST_CA_RC_CARD_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_card_invalid);
        } else if (Entitles.sEntitlesState == RETURN_CODE.ST_CA_RC_POINTER_INVALID.getRetCode()) {
            toastShow(R.string.st_ca_rc_pointer_invalid);
        } else if (Entitles.sEntitlesState == RETURN_CODE.ST_CA_RC_DATA_NOT_FIND.getRetCode()) {
            toastShow(R.string.st_ca_rc_data_not_find);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaEntitleInfoActivity.this, CaInformationActivity.class);
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
