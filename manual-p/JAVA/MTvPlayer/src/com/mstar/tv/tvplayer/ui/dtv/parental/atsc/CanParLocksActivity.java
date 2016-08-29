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
import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MenuConstants;
import com.mstar.tv.tvplayer.ui.FocusScrollListView;

public class CanParLocksActivity extends BaseActivity {
    private static final String TAG = "CanParLocksActivity";

    private FocusScrollListView canparLocks_englishLst;

    private FocusScrollListView canparLocks_frenchLst;

    private TextView englishTitleTxt;

    private TextView frenchTitleTxt;

    private List<SettingItem> english_items;

    private List<SettingItem> french_items;

    private SettingAdapter english_adapter;

    private SettingAdapter french_adapter;

    private String[] titles = null;

    private TvAtscChannelManager manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate\n");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_canparlocks);
        ((TextView) findViewById(R.id.canadian_parental_title_txt)).setText(getIntent()
                .getStringExtra(MenuConstants.TITLE_KEY));

        titles = getResources().getStringArray(R.array.setting_canadian_parental_locks_titles);
        english_items = new ArrayList<SettingItem>();
        french_items = new ArrayList<SettingItem>();

        canparLocks_englishLst = (FocusScrollListView) findViewById(R.id.canadian_english_list);
        canparLocks_englishLst.setFocusBitmap(R.drawable.menu_setting_focus);
        englishTitleTxt = (TextView) findViewById(R.id.canadian_english_title_txt);
        englishTitleTxt.setText(titles[0]);

        canparLocks_frenchLst = (FocusScrollListView) findViewById(R.id.canadian_french_list);
        canparLocks_frenchLst.setFocusBitmap(R.drawable.one_px);
        frenchTitleTxt = (TextView) findViewById(R.id.canadian_french_title_txt);
        frenchTitleTxt.setText(titles[1]);

        initItems();
        setListener();

        english_adapter = new SettingAdapter(this, english_items);
        english_adapter.setHasShowValue(true);
        canparLocks_englishLst.setAdapter(english_adapter);

        french_adapter = new SettingAdapter(this, french_items);
        french_adapter.setHasShowValue(true);
        canparLocks_frenchLst.setAdapter(french_adapter);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume\n");
        super.onResume();
    }

    private void initItems() {
        // FIXME: refactor this function
        Log.d(TAG, "================= initItems =================\n");
        manager = TvAtscChannelManager.getInstance();
        int ordinal = manager.getCanadaEngRatingLockLevel();
        String[] englishTitles = getResources().getStringArray(R.array.canadian_english_titles);
        String[] values = getResources().getStringArray(
                R.array.setting_canadian_parental_locks_values);
        SettingItem english_e_item = new SettingItem(this, englishTitles[0],
                MenuConstants.ITEMTYPE_BUTTON, true);
        english_items.add(english_e_item);
        SettingItem english_c_item = new SettingItem(this, englishTitles[1], values,
                ordinal == 1 ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        english_items.add(english_c_item);
        SettingItem english_c8_item = new SettingItem(this, englishTitles[2], values,
                (ordinal > 0 && ordinal <= 2) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        english_items.add(english_c8_item);
        SettingItem english_g_item = new SettingItem(this, englishTitles[3], values,
                (ordinal > 0 && ordinal <= 3) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        english_items.add(english_g_item);
        SettingItem english_pg_item = new SettingItem(this, englishTitles[4], values,
                (ordinal > 0 && ordinal <= 4) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        english_items.add(english_pg_item);
        SettingItem english_14_item = new SettingItem(this, englishTitles[5], values,
                (ordinal > 0 && ordinal <= 5) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        english_items.add(english_14_item);
        SettingItem english_18_item = new SettingItem(this, englishTitles[6], values,
                (ordinal > 0 && ordinal <= 6) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        english_items.add(english_18_item);

        ordinal = manager.getCanadaFreRatingLockLevel();
        String[] frenchTitles = getResources().getStringArray(R.array.canadian_french_titles);
        SettingItem french_e_item = new SettingItem(this, frenchTitles[0],
                MenuConstants.ITEMTYPE_BUTTON, true);
        french_items.add(french_e_item);
        SettingItem french_g_item = new SettingItem(this, frenchTitles[1], values, ordinal == 1 ? 1
                : 0, MenuConstants.ITEMTYPE_ENUM, true);
        french_items.add(french_g_item);
        SettingItem french_8ans_item = new SettingItem(this, frenchTitles[2], values,
                (ordinal > 0 && ordinal <= 2) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        french_items.add(french_8ans_item);
        SettingItem french_13ans_item = new SettingItem(this, frenchTitles[3], values,
                (ordinal > 0 && ordinal <= 3) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        french_items.add(french_13ans_item);
        SettingItem french_16ans_item = new SettingItem(this, frenchTitles[4], values,
                (ordinal > 0 && ordinal <= 4) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        french_items.add(french_16ans_item);
        SettingItem french_18ans_item = new SettingItem(this, frenchTitles[5], values,
                (ordinal > 0 && ordinal <= 5) ? 1 : 0, MenuConstants.ITEMTYPE_ENUM, true);
        french_items.add(french_18ans_item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "================ onKeyDown ==================\n");

        if (KeyEvent.KEYCODE_DPAD_LEFT == keyCode || KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
            if (canparLocks_englishLst.hasFocus()) {
                int selectItemIndex = canparLocks_englishLst.getSelectedItemPosition();
                return english_items.get(selectItemIndex).onKeyDown(keyCode, event);
            } else if (canparLocks_frenchLst.hasFocus()) {
                int selectItemIndex = canparLocks_frenchLst.getSelectedItemPosition();
                return french_items.get(selectItemIndex).onKeyDown(keyCode, event);
            }
        } else if (KeyEvent.KEYCODE_MENU == keyCode) {
            onBackPressed();
            return true;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (canparLocks_frenchLst.getSelectedItemPosition() == 0
                        && canparLocks_frenchLst.hasFocus()) {
                    canparLocks_frenchLst.setFocusable(false);
                    canparLocks_frenchLst.setFocusBitmap(R.drawable.one_px);
                    canparLocks_englishLst.setVisibility(View.VISIBLE);
                    canparLocks_englishLst.setFocusBitmap(R.drawable.menu_setting_focus);
                    canparLocks_englishLst.requestFocus();
                    canparLocks_englishLst.setSelection(canparLocks_englishLst.getCount() - 1);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (canparLocks_englishLst.getSelectedItemPosition() == canparLocks_englishLst
                        .getCount() - 1 && canparLocks_englishLst.hasFocus()) {
                    canparLocks_englishLst.setFocusBitmap(R.drawable.one_px);
                    canparLocks_englishLst.setVisibility(View.GONE);
                    canparLocks_frenchLst.setFocusable(true);
                    canparLocks_frenchLst.setFocusBitmap(R.drawable.menu_setting_focus);
                    canparLocks_frenchLst.requestFocus();
                    canparLocks_frenchLst.setSelection(0);
                    french_adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void callBack(int resultVaule) {
        Log.d(TAG, "callBack(int)\n");

        if (canparLocks_englishLst.hasFocus()) {
            int selectItemIndex = canparLocks_englishLst.getSelectedItemPosition();
            ((TextView) canparLocks_englishLst.getSelectedView().getTag()).setText(english_items
                    .get(selectItemIndex).getValues()[resultVaule]);
            switch (selectItemIndex) {
                case 1:
                    if (resultVaule == 1) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_C);
                        for (int i = 2; i < 7; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    } else if (resultVaule == 0) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_C8PLUS);
                    }
                    break;
                case 2:
                    if (resultVaule == 1) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_C8PLUS);
                        for (int i = 3; i < 7; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_G);
                        english_items.get(1).setCurValue(resultVaule);
                    }
                    break;
                case 3:
                    if (resultVaule == 1) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_G);
                        for (int i = 4; i < 7; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_PG);
                        for (int i = 1; i < 3; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    }
                    break;
                case 4:
                    if (resultVaule == 1) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_PG);
                        for (int i = 5; i < 7; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_14PLUS);
                        for (int i = 1; i < 4; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    }
                    break;
                case 5:
                    if (resultVaule == 1) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_14PLUS);
                        english_items.get(6).setCurValue(resultVaule);
                    } else {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_18PLUS);
                        for (int i = 1; i < 5; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    }
                    break;
                case 6:
                    if (resultVaule == 0) {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_EXEMPT);
                        for (int i = 1; i < 6; i++) {
                            english_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_18PLUS);
                    }
                    break;
                default:
                    break;
            }
            english_adapter.notifyDataSetChanged();
        } else if (canparLocks_frenchLst.hasFocus()) {
            int selectItemIndex = canparLocks_frenchLst.getSelectedItemPosition();
            french_items.get(selectItemIndex).setCurValue(resultVaule);
            switch (selectItemIndex) {
                case 1:
                    if (resultVaule == 1) {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_G);
                        for (int i = 2; i < 6; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_8ANSPLUS);
                    }
                    break;
                case 2:
                    if (resultVaule == 1) {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_8ANSPLUS);
                        for (int i = 3; i < 6; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_13ANSPLUS);
                        french_items.get(1).setCurValue(resultVaule);
                    }
                    break;
                case 3:
                    if (resultVaule == 1) {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_13ANSPLUS);
                        for (int i = 4; i < 6; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_16ANSPLUS);
                        for (int i = 1; i < 3; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    }
                    break;
                case 4:
                    if (resultVaule == 1) {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_16ANSPLUS);
                        for (int i = 5; i < 6; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    } else {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_18ANSPLUS);
                        for (int i = 1; i < 4; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    }
                    break;
                case 5:
                    if (resultVaule == 1) {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_18ANSPLUS);
                    } else {
                        manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_EXEMPT);
                        for (int i = 1; i < 5; i++) {
                            french_items.get(i).setCurValue(resultVaule);
                        }
                    }
                    break;
                default:
                    break;
            }
            french_adapter.notifyDataSetChanged();
        }
    }

    private void setListener() {
        Log.d(TAG, "setListener\n");

        canparLocks_englishLst.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                english_items.get(position).itemClicked();
            }

        });

        canparLocks_frenchLst.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                french_items.get(position).itemClicked();
            }
        });
    }

    @Override
    public void callBack(Boolean resultVaule) {

    }

    @Override
    public void callBack() {
        if (canparLocks_englishLst.hasFocus()) {
            int selectItemIndex = canparLocks_englishLst.getSelectedItemPosition();
            if (selectItemIndex == 0) {
                manager.setCanadaEngRatingLock(TvAtscChannelManager.RATING_CANADA_ENG_EXEMPT);
                for (int i = 1; i < 7; i++) {
                    english_items.get(i).setCurValue(0);
                }
                english_adapter.notifyDataSetChanged();
            }
        } else if (canparLocks_frenchLst.hasFocus()) {
            int selectItemIndex = canparLocks_frenchLst.getSelectedItemPosition();
            if (selectItemIndex == 0) {
                manager.setCanadaFreRatingLock(TvAtscChannelManager.RATING_CANADA_FRE_EXEMPT);
                for (int i = 1; i < 6; i++) {
                    french_items.get(i).setCurValue(0);
                }
                french_adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, VChipActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
