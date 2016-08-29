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

import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaEmailHeadInfo;
import com.mstar.android.tvapi.dtv.vo.CaEmailHeadsInfo;

public class EmailListActivity extends Activity {
    private ListView email_listview;

    private TextView bottomTextView;

    private TvCaManager tvCaManager = null;

    private CaEmailHeadsInfo mCaEmailHeadsInfo;

    private CaEmailHeadInfo[] mCaEmailHeadInfos;

    private final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

    private SimpleAdapter htSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emaillist);

        tvCaManager = TvCaManager.getInstance();
        getmCaEmailHeadInfos();

        htSchedule = new SimpleAdapter(this, mylist, R.layout.emaillist_item, new String[] {
                "theme", "time", "level", "state"
        }, new int[] {
                R.id.emaillist_item_theme, R.id.emaillist_item_time, R.id.emaillist_item_level,
                R.id.emaillist_item_state
        });
        email_listview = (ListView) findViewById(R.id.email_listview);
        email_listview.setAdapter(htSchedule);
        email_listview.setOnItemSelectedListener(onEmailItemSelectedListener);
        email_listview.setOnItemClickListener(onEmailItemClickListener);
        bottomTextView = (TextView) findViewById(R.id.emaillist_bottom);
        showEmailNum();
    }

    private void showEmailNum() {
        short emptynum = 0;
        emptynum = tvCaManager.CaGetEmailSpaceInfo().getsEmptyNum();

        if (mCaEmailHeadInfos.length == 0) {
            bottomTextView.setText("0/" + mCaEmailHeadInfos.length + " "
                    + getResources().getString(R.string.email_free_space) + " " + emptynum
                    + getResources().getString(R.string.email_free_letter));
        } else {
            bottomTextView.setText("1/" + mCaEmailHeadInfos.length + " "
                    + getResources().getString(R.string.email_free_space) + " " + emptynum
                    + getResources().getString(R.string.email_free_letter));
        }

    }

    private void getmCaEmailHeadInfos() {

        mCaEmailHeadsInfo = tvCaManager.CaGetEmailHeads((short) 100, (short) 0);
        mCaEmailHeadInfos = mCaEmailHeadsInfo.getEmailHeads();
        mylist.clear();
        for (int i = 0; i < mCaEmailHeadInfos.length; i++) {
            CaEmailHeadInfo caEmailHeadInfo = mCaEmailHeadsInfo.getEmailHeads()[i];
            HashMap<String, String> map = new HashMap<String, String>();
            try {
                map.put("theme", new String(caEmailHeadInfo.getPcEmailHead().getBytes(), "GB2312"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map.put("time", dateConvert(caEmailHeadInfo.getwCreateTime()));
            map.put("level", formatEmailImportance(caEmailHeadInfo.getwImportance()));
            Log.i("liux", "state===" + caEmailHeadInfo.getsEmailHeadState());
            map.put("state", formatEmailHeadState(caEmailHeadInfo.getM_bNewEmail()));
            mylist.add(map);
        }
    }

    AdapterView.OnItemClickListener onEmailItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CaEmailHeadInfo caEmailHeadInfo = mCaEmailHeadInfos[position];
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            try {
                bundle.putString("theme", new String(caEmailHeadInfo.getPcEmailHead().getBytes(),
                        "GB2312"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            bundle.putInt("time", caEmailHeadInfo.getwCreateTime());
            bundle.putInt("level", caEmailHeadInfo.getwImportance());
            bundle.putInt("emailid", caEmailHeadInfo.getwActionID());
            try {
                bundle.putString("content",
                        new String(tvCaManager.CaGetEmailContent(caEmailHeadInfo.getwActionID())
                                .getPcEmailContent().getBytes(), "GB2312"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            intent.putExtras(bundle);
            intent.setClass(EmailListActivity.this, EmailDetailActivity.class);
            EmailListActivity.this.startActivity(intent);
            EmailListActivity.this.finish();
        }
    };

    AdapterView.OnItemSelectedListener onEmailItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                bottomTextView.setText((position + 1) + "/" + mCaEmailHeadInfos.length + " "
                        + getResources().getString(R.string.email_free_space) + " "
                        + tvCaManager.CaGetEmailSpaceInfo().getsEmptyNum()
                        + getResources().getString(R.string.email_free_letter));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private String formatEmailImportance(int arg0) {
        return arg0 == 0 ? getResources().getString(R.string.email_ordinary) : getResources()
                .getString(R.string.email_important);
    }

    private String formatEmailHeadState(int arg0) {
        return arg0 == 0 ? getResources().getString(R.string.email_read) : getResources()
                .getString(R.string.email_unread);
    }

    private String dateConvert(int dayNum)

    {

        String endDate = "";

        Calendar ca = Calendar.getInstance();

        ca.set(1970, Calendar.JANUARY, 1, 0, 0, 0);

        dayNum = dayNum + 8 * 3600;

        ca.add(Calendar.SECOND, dayNum);

        Format s = new SimpleDateFormat("yyyy-MM-dd");

        endDate = s.format(ca.getTime());
        return endDate;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_PROG_RED) {
            if (mCaEmailHeadInfos.length != 0 && email_listview.hasFocus()) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.really_remove_this_email)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tvCaManager.CaDelEmail(mCaEmailHeadInfos[email_listview
                                                .getSelectedItemPosition()].getwActionID());
                                        getmCaEmailHeadInfos();
                                        htSchedule.notifyDataSetChanged();
                                        showEmailNum();
                                    }
                                }).create().show();
            }

        } else if (keyCode == KeyEvent.KEYCODE_PROG_GREEN) {
            if (mCaEmailHeadInfos.length != 0 && email_listview.hasFocus()) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.really_remove_all_email)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tvCaManager.CaDelEmail(0);
                                        getmCaEmailHeadInfos();
                                        htSchedule.notifyDataSetChanged();
                                        showEmailNum();
                                    }
                                }).create().show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
