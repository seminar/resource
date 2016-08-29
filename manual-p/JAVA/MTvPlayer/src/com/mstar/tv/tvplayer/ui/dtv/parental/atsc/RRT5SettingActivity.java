//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2014 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tv.tvplayer.ui.dtv.parental.atsc;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tvapi.dtv.atsc.vo.Regin5DimensionInformation;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.FocusScrollListView;
import com.mstar.tv.tvplayer.ui.MenuConstants;

import com.mstar.android.tv.TvAtscChannelManager;
import android.util.Log;

public class RRT5SettingActivity extends BaseActivity {
    private static final String TAG = "RRT5SettingActivity";

    private FocusScrollListView RRT5Lst;

    private List<SettingItem> items;

    private int selectItemIndex = 0;

    private SettingAdapter adapter;

    private TvAtscChannelManager manager;

    private List<Regin5DimensionInformation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publiclist);
        ((TextView) findViewById(R.id.setting_title_txt)).setText(getIntent().getStringExtra(
                MenuConstants.TITLE_KEY));

        items = new ArrayList<SettingItem>();
        RRT5Lst = (FocusScrollListView) findViewById(R.id.context_lst);
        RRT5Lst.setFocusBitmap(R.drawable.menu_setting_focus);
        initData();
        setListener();
        adapter = new SettingAdapter(this, items);
        RRT5Lst.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        setVisible(true);
        super.onResume();
    }

    private void initData() {
        manager = TvAtscChannelManager.getInstance();
        list = manager.getRRT5Dimension();
        for (int i = 0; i < list.size(); i++) {
            SettingItem item = new SettingItem(this, list.get(i).dimensionName,
                    MenuConstants.ITEMTYPE_BUTTON, true);
            items.add(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (RRT5Lst.getSelectedItemPosition() == 0) {
                    RRT5Lst.setSelection(adapter.getCount() - 1);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (RRT5Lst.getSelectedItemPosition() == adapter.getCount() - 1) {
                    RRT5Lst.setSelection(0);
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setListener() {
        RRT5Lst.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectItemIndex = position;
                items.get(selectItemIndex).itemClicked();
            }

        });
    }

    @Override
    public void callBack(int resultVaule) {
    }

    @Override
    public void callBack(Boolean resultVaule) {

    }

    @Override
    public void callBack() {
        if (list.get(selectItemIndex).values_Defined < 0) {
            return;
        }
        Intent intent = new Intent(this, RRT5ItemSettingActivity.class);
        intent.putExtra("title", list.get(selectItemIndex).dimensionName);
        intent.putExtra("index", selectItemIndex);
        intent.putExtra("count", list.get(selectItemIndex).values_Defined);
        intent.putExtra("graduated_Scale", list.get(selectItemIndex).graduated_Scale);
        startActivity(intent);
        setVisible(false);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Class RRT5SettingActivity.onBackPressed\n");
        Intent intent = new Intent(this, VChipActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();

    }
}
