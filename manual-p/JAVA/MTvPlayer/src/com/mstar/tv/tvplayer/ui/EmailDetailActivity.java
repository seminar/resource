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

package com.mstar.tv.tvplayer.ui;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mstar.android.tv.TvCaManager;

public class EmailDetailActivity extends Activity {
    private TextView emaildetail_theme;

    private TextView emaildetail_time;

    private TextView emaildetail_level;

    private TextView emaildetail_content;

    private TvCaManager tvCaManager = null;

    private int emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emaildetail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String theme = bundle.getString("theme");
        String content = bundle.getString("content");
        int time = bundle.getInt("time");
        int level = bundle.getInt("level");
        emailid = bundle.getInt("emailid");
        tvCaManager = TvCaManager.getInstance();

        emaildetail_theme = (TextView) findViewById(R.id.emaildetail_theme);
        emaildetail_time = (TextView) findViewById(R.id.emaildetail_time);
        emaildetail_level = (TextView) findViewById(R.id.emaildetail_level);
        emaildetail_content = (TextView) findViewById(R.id.emaildetail_content);

        emaildetail_theme.setText(theme);
        emaildetail_time.setText(dateConvert(time));
        emaildetail_level.setText(formatEmailImportance(level));
        emaildetail_content.setText(content);

    }

    private String formatEmailImportance(int arg0) {
        return arg0 == 0 ? getResources().getString(R.string.email_ordinary) : getResources()
                .getString(R.string.email_important);
    }

    private String dateConvert(int dayNum)

    {

        String endDate = "";

        Calendar ca = Calendar.getInstance();

        ca.set(1970, Calendar.JANUARY, 1, 0, 0, 0);

        dayNum = dayNum + 8 * 3600;

        ca.add(Calendar.SECOND, dayNum);

        Format s = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        endDate = s.format(ca.getTime());

        return endDate;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToEmailListActivity();
        } else if (keyCode == KeyEvent.KEYCODE_PROG_RED) {
            new AlertDialog.Builder(this).setTitle(R.string.really_remove_this_email)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvCaManager.CaDelEmail(emailid);
                            goToEmailListActivity();
                        }
                    }).create().show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goToEmailListActivity() {
        Intent intent = new Intent();
        intent.setClass(EmailDetailActivity.this, EmailListActivity.class);
        EmailDetailActivity.this.startActivity(intent);
        EmailDetailActivity.this.finish();
    }

}
