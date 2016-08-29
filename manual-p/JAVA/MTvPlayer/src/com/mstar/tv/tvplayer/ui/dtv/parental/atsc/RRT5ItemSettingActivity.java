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

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tvapi.dtv.atsc.vo.RR5RatingPair;
import com.mstar.tv.tvplayer.ui.FocusScrollListView;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MenuConstants;

public class RRT5ItemSettingActivity extends BaseActivity {
    private FocusScrollListView RRT5ItemLst;

    private List<SettingItem> items;

    private int selectItemIndex = 0;

    private SettingAdapter adapter;

    private TvAtscChannelManager manager;

    private List<RR5RatingPair> list;

    private int graduated_Scale = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publiclist);
        ((TextView) findViewById(R.id.setting_title_txt)).setText(getIntent().getStringExtra(
                "title"));
        graduated_Scale = getIntent().getIntExtra("graduated_Scale", 0);
        items = new ArrayList<SettingItem>();
        RRT5ItemLst = (FocusScrollListView) findViewById(R.id.context_lst);
        RRT5ItemLst.setFocusBitmap(R.drawable.menu_setting_focus);
        initData();
        adapter = new SettingAdapter(this, items);
        adapter.setHasShowValue(true);
        RRT5ItemLst.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        manager = TvAtscChannelManager.getInstance();
        list = manager.getRR5RatingPair(getIntent().getIntExtra("index", 0), getIntent()
                .getIntExtra("count", 0));
        for (int i = 0; i < list.size(); i++) {
            SettingItem item = new SettingItem(this, list.get(i).abbRatingText, getResources()
                    .getStringArray(R.array.rrt5_value), list.get(i).rR5RatingPair_id,
                    MenuConstants.ITEMTYPE_ENUM, true);
            items.add(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            selectItemIndex = RRT5ItemLst.getSelectedItemPosition();
            return items.get(selectItemIndex).onKeyDown(keyCode, event);
        } else if ((keyCode == KeyEvent.KEYCODE_BACK) || (keyCode == KeyEvent.KEYCODE_MENU)) {
            // findViewById(R.id.setting_layout).startAnimation(menu_AnimOut_three);
            handler.sendEmptyMessageDelayed(MenuConstants.DELAYFINIFH, 200);
            return true;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (RRT5ItemLst.getSelectedItemPosition() == 0) {
                    RRT5ItemLst.setSelection(adapter.getCount() - 1);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (RRT5ItemLst.getSelectedItemPosition() == adapter.getCount() - 1) {
                    RRT5ItemLst.setSelection(0);
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void callBack(int resultVaule) {
        ((TextView) RRT5ItemLst.getSelectedView().getTag()).setText(items.get(selectItemIndex)
                .getValues()[resultVaule]);
        manager.setRR5RatingPair(selectItemIndex, getIntent().getIntExtra("index", 0), resultVaule);
        // if the graduated is 1,it's mean that all the item is associations
        // between each other.
        if (graduated_Scale == 1) {
            if (selectItemIndex < adapter.getCount() - 1 && resultVaule == 1) {
                for (int i = selectItemIndex + 1; i < adapter.getCount(); i++) {
                    if (items.get(i).getCurValue() == 0) {
                        items.get(i).setCurValue(resultVaule);
                        manager.setRR5RatingPair(i, getIntent().getIntExtra("index", 0),
                                resultVaule);
                    }
                }
            } else if (selectItemIndex > 0 && resultVaule == 0) {
                for (int i = 0; i < selectItemIndex; i++) {
                    if (items.get(i).getCurValue() == 1) {
                        items.get(i).setCurValue(resultVaule);
                        manager.setRR5RatingPair(i, getIntent().getIntExtra("index", 0),
                                resultVaule);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void callBack(Boolean resultVaule) {

    }

    @Override
    public void callBack() {

    }
}
