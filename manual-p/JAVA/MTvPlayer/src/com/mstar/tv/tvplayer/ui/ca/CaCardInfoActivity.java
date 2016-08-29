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
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaOperatorChildStatus;
import com.mstar.util.Constant;
import com.mstar.util.Tools;

public class CaCardInfoActivity extends Activity {
    private final String CURRNT_OPERATOR_ID = "OperatorId";

    private String OperatorId;

    private TvCaManager tvCaManager = null;

    private CaOperatorChildStatus caOperatorChildStatus;

    private TextView ca_card_feed_child_or_parent_textview;

    private TextView ca_card_num_textview2;

    private TextView ca_card_delaytime_textview;

    private TextView ca_card_delaytime_lastfeedtime_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_card_info);
        findView();
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            OperatorId = getIntent().getStringExtra(CURRNT_OPERATOR_ID);
        }
        tvCaManager = TvCaManager.getInstance();
        try {
            caOperatorChildStatus = tvCaManager.CaGetOperatorChildStatus(Short
                    .parseShort(OperatorId));
            if (caOperatorChildStatus.sOperatorChildState == Constant.CDCA_RC_POINTER_INVALID) {
                Tools.toastShow(R.string.cdca_rc_pointer_invalid, this);
            } else if (caOperatorChildStatus.sOperatorChildState == Constant.CDCA_RC_CARD_INVALID) {
                Tools.toastShow(R.string.cdca_rc_invalid, this);
            } else if (caOperatorChildStatus.sOperatorChildState == Constant.CDCA_RC_DATA_NOT_FIND) {
                Tools.toastShow(R.string.ippv_cdca_rc_data_not_find, this);
            }

        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException");
            e.printStackTrace();
        }
        if (caOperatorChildStatus.sIsChild == 0) {
            ca_card_feed_child_or_parent_textview.setText(getResources().getString(
                    R.string.str_ca_mg_parent_card));
        } else if (caOperatorChildStatus.sIsChild == 1) {
            ca_card_feed_child_or_parent_textview.setText(getResources().getString(
                    R.string.str_ca_mg_child_card));
            ca_card_num_textview2.setText(caOperatorChildStatus.pParentCardSN);
            ca_card_delaytime_textview.setText(caOperatorChildStatus.sDelayTime + "");
            ca_card_delaytime_lastfeedtime_textview
                    .setText(dateConvert(caOperatorChildStatus.wLastFeedTime));
        }

    }

    private void findView() {
        ca_card_feed_child_or_parent_textview = (TextView) findViewById(R.id.ca_card_feed_child_or_parent_textview);
        ca_card_num_textview2 = (TextView) findViewById(R.id.ca_card_num_textview2);
        ca_card_delaytime_textview = (TextView) findViewById(R.id.ca_card_delaytime_textview);
        ca_card_delaytime_lastfeedtime_textview = (TextView) findViewById(R.id.ca_card_delaytime_lastfeedtime_textview);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(CaCardInfoActivity.this, CaInformationActivity.class);
                intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
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
