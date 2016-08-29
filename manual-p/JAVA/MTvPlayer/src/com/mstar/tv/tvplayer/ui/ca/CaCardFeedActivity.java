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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaFeedDataInfo;
import com.mstar.android.tvapi.dtv.vo.CaOperatorChildStatus;
import com.mstar.android.tvapi.dtv.vo.CaOperatorIds;
import com.mstar.util.Tools;

import com.mstar.tv.tvplayer.ui.ca.CaErrorType.RETURN_CODE;

public class CaCardFeedActivity extends Activity {
    private TvCaManager tvCaManager = null;

    private ListView ca_card_feed_listview;

    private final List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

    private static int CDCAS_OK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_card_feed);

        ca_card_feed_listview = (ListView) findViewById(R.id.ca_card_feed_listview);
        tvCaManager = TvCaManager.getInstance();

        getInitData();
        SimpleAdapter adapter = new SimpleAdapter(this, contents,
                R.layout.ca_card_feed_listview_item, new String[] {
                        "operator", "isparentcard", "delaytime", "lastfeedtime"
                }, new int[] {
                        R.id.ca_card_feed_listviewitem_operator_id,
                        R.id.ca_card_feed_listviewitem_child_or_parent,
                        R.id.ca_card_feed_listviewitem_delaytime,
                        R.id.ca_card_feed_listviewitem_lastfeedtime
                });
        ca_card_feed_listview.setAdapter(adapter);
        ca_card_feed_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final short wTVSID = (Short) contents.get(position).get("operator");
                if (!getResources().getString(R.string.str_ca_mg_parent_card).equals(
                        contents.get(position).get("isparentcard"))) {
                    new AlertDialog.Builder(CaCardFeedActivity.this)
                            .setTitle(R.string.please_insert_parent_card)
                            .setNegativeButton(android.R.string.cancel, null)
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            CaFeedDataInfo caFeedDataInfo = tvCaManager
                                                    .CaReadFeedDataFromParent(wTVSID);
                                            if (caFeedDataInfo.sFeedDataState == CDCAS_OK) {
                                                showInsertChildDialog(wTVSID, caFeedDataInfo);
                                            } else {
                                                showpRomptDialog(
                                                        R.string.read_feeddata_from_parent_failed,
                                                        null);
                                            }

                                        }
                                    }).create().show();

                }
            }
        });

    }

    private void showInsertChildDialog(final short wTVSID, final CaFeedDataInfo caFeedDataInfo) {
        showpRomptDialog(R.string.please_insert_child_card, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                short result;
                result = tvCaManager.CaWriteFeedDataToChild(wTVSID, caFeedDataInfo);
                if (result == CDCAS_OK) {
                    showpRomptDialog(R.string.feeddata_successed, null);

                } else {
                    showpRomptDialog(R.string.feeddata_failed, null);
                }
            }
        });
    }

    private void getInitData() {
        CaOperatorIds OperatorIds = tvCaManager.CaGetOperatorIds();
        if (OperatorIds == null) {
            return;
        }
        contents.clear();
        if (OperatorIds.sOperatorIdState == RETURN_CODE.ST_CA_RC_OK.getRetCode()) {
            if (OperatorIds.OperatorId.length > 0) {
                for (int i = 0; i < OperatorIds.OperatorId.length; i++) {
                    if (OperatorIds.OperatorId[i] == 0)// CA Spec:if
                                                       // OperatorId=0,need
                                                       // continue
                    {
                        continue;
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("operator", OperatorIds.OperatorId[i]);
                    CaOperatorChildStatus caOperatorChildStatus = tvCaManager
                            .CaGetOperatorChildStatus(OperatorIds.OperatorId[i]);
                    if (caOperatorChildStatus.sIsChild == 0) {
                        map.put("isparentcard",
                                getResources().getString(R.string.str_ca_mg_parent_card));
                        map.put("delaytime", "");
                        map.put("lastfeedtime", "");
                    } else if (caOperatorChildStatus.sIsChild == 1) {
                        map.put("isparentcard",
                                getResources().getString(R.string.str_ca_mg_child_card));
                        map.put("delaytime", caOperatorChildStatus.sDelayTime);
                        map.put("lastfeedtime", dateConvert(caOperatorChildStatus.wLastFeedTime));
                    }
                    contents.add(map);
                }
            }
        } else if (OperatorIds.sOperatorIdState == RETURN_CODE.ST_CA_RC_CARD_INVALID.getRetCode()) {
            Tools.toastShow(R.string.st_ca_rc_card_invalid, this);
        } else if (OperatorIds.sOperatorIdState == RETURN_CODE.ST_CA_RC_POINTER_INVALID
                .getRetCode()) {
            Tools.toastShow(R.string.st_ca_rc_pointer_invalid, this);
        }
    }

    private void showpRomptDialog(int resId, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(CaCardFeedActivity.this).setTitle(resId)
                .setPositiveButton(android.R.string.ok, listener).create().show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent();
        Log.d("TvApp", "CaInfoModifyPinActivity keyCode" + keyCode);
        Log.d("TvApp", "CaInfoModifyPinActivity KeyEvent.KEYCODE_FORWARD_DEL"
                + KeyEvent.KEYCODE_FORWARD_DEL);
        Log.d("TvApp", "CaInfoModifyPinActivity KeyEvent.KEYCODE_DEL" + KeyEvent.KEYCODE_DEL);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaCardFeedActivity.this, CaManagementActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String dateConvert(int dayNum) {
        String endDate = "";
        Calendar ca = Calendar.getInstance();
        ca.set(2000, Calendar.JANUARY, 1);
        ca.add(Calendar.DATE, (short) (dayNum >> 16));
        Format s = new SimpleDateFormat("yyyy-MM-dd");
        endDate = s.format(ca.getTime());

        return endDate;
    }

}
