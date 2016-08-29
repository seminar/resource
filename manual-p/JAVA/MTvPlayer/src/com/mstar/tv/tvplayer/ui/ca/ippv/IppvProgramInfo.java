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

package com.mstar.tv.tvplayer.ui.ca.ippv;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaIPPVProgramInfo;
import com.mstar.android.tvapi.dtv.vo.CaIPPVProgramInfos;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.ca.CaInformationActivity;
import com.mstar.util.Constant;
import com.mstar.util.Tools;

public class IppvProgramInfo extends Activity {
    private final String CURRNT_OPERATOR_ID = "OperatorId";

    private String OperatorId;

    private TvCaManager tvCaManager = null;

    private CaIPPVProgramInfos caIPPVProgramInfos;

    private final ArrayList<CaIPPVProgramInfo> list = new ArrayList<CaIPPVProgramInfo>();

    private LinearLayout ippv_program_info_linearlayout;

    private TextView ippv_program_info_purse_id;

    private TextView ippv_program_info_productid;

    private TextView ippv_program_info_product_state;

    private TextView ippv_program_info_tape;

    private TextView ippv_program_info_price;

    private TextView ippv_program_info_expireddate;

    private TextView ippv_program_info_page;

    private int currentposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ippv_program_info);
        ippv_program_info_linearlayout = (LinearLayout) findViewById(R.id.ippv_program_info_linearlayout);
        ippv_program_info_purse_id = (TextView) findViewById(R.id.ippv_program_info_purse_id);
        ippv_program_info_productid = (TextView) findViewById(R.id.ippv_program_info_productid);
        ippv_program_info_product_state = (TextView) findViewById(R.id.ippv_program_info_product_state);
        ippv_program_info_tape = (TextView) findViewById(R.id.ippv_program_info_tape);
        ippv_program_info_price = (TextView) findViewById(R.id.ippv_program_info_price);
        ippv_program_info_expireddate = (TextView) findViewById(R.id.ippv_program_info_expireddate);
        ippv_program_info_page = (TextView) findViewById(R.id.ippv_program_info_page);
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            OperatorId = getIntent().getStringExtra(CURRNT_OPERATOR_ID);
        }
        tvCaManager = TvCaManager.getInstance();
        caIPPVProgramInfos = tvCaManager.CaGetIPPVProgram(Short.parseShort(OperatorId));
        if (caIPPVProgramInfos.sIPPVInfoState == Constant.CDCA_RC_OK) {
            Tools.toastShow(R.string.ippv_cdca_rc_ok, IppvProgramInfo.this);
        } else if (caIPPVProgramInfos.sIPPVInfoState == Constant.CDCA_RC_CARD_INVALID) {
            Tools.toastShow(R.string.cdca_rc_invalid, IppvProgramInfo.this);
        } else if (caIPPVProgramInfos.sIPPVInfoState == Constant.CDCA_RC_DATA_NOT_FIND) {
            Tools.toastShow(R.string.ippv_cdca_rc_data_not_find, IppvProgramInfo.this);
        }
        if (caIPPVProgramInfos.sNumber == 0) {
            ippv_program_info_linearlayout.setVisibility(View.INVISIBLE);
            Toast.makeText(IppvProgramInfo.this,
                    getResources().getString(R.string.ippv_data_not_find), 0);
        } else {
            for (int i = 0; i < caIPPVProgramInfos.sNumber; i++) {
                list.add(caIPPVProgramInfos.IPPVProgramInfo[i]);
            }
            showData(0);
        }

    }

    private void showData(int position) {
        CaIPPVProgramInfo caIPPVProgramInfo = list.get(position);
        ippv_program_info_purse_id.setText(caIPPVProgramInfo.sSlotID + "");
        ippv_program_info_productid.setText(caIPPVProgramInfo.wdwProductID + "");

        if (caIPPVProgramInfo.sBookEdFlag == 0x01) {
            ippv_program_info_product_state.setText(getResources().getString(
                    R.string.ippv_product_state_booking));
        } else if (caIPPVProgramInfo.sBookEdFlag == 0x03) {
            ippv_program_info_product_state.setText(getResources().getString(
                    R.string.ippv_product_state_viewed));
        }

        if (caIPPVProgramInfo.sCanTape == 0) {
            ippv_program_info_tape.setText(getResources().getString(R.string.ippv_can_not_tape));
        } else if (caIPPVProgramInfo.sCanTape == 1) {
            ippv_program_info_tape.setText(getResources().getString(R.string.ippv_can_tape));

        }

        ippv_program_info_price.setText(caIPPVProgramInfo.sPrice + "");
        ippv_program_info_expireddate.setText(dateConvert(caIPPVProgramInfo.sExpiredDate));
        ippv_program_info_page.setText(position + 1 + "/" + caIPPVProgramInfos.sNumber);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                intent.setClass(IppvProgramInfo.this, CaInformationActivity.class);
                intent.putExtra(CURRNT_OPERATOR_ID, OperatorId);
                startActivity(intent);
                finish();
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (currentposition > 0) {
                    currentposition--;
                    showData(currentposition);
                }

                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (currentposition < caIPPVProgramInfos.sNumber - 1) {
                    currentposition++;
                    showData(currentposition);
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
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
