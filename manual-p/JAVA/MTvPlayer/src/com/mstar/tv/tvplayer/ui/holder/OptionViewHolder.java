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

package com.mstar.tv.tvplayer.ui.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.storage.MStorageManager;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tv.TvGingaManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvHbbTVManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.android.tvapi.dtv.dvb.isdb.vo.GingaInfo;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.MenuConstants;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.dtv.ClosedCaptionActivity;
import com.mstar.tv.tvplayer.ui.component.PasswordCheckDialog;

import java.util.List;
import java.util.Locale;

public class OptionViewHolder {
    private final String TAG = "OptionViewHolder";

    private final int SWITCH_OFF = 0;

    private final int SWITCH_ON = 1;

    protected TextView text_option_caption;

    protected TextView text_option_caption_value;

    protected LinearLayout linear_option_caption;

    protected LinearLayout linear_option_summer_time;

    protected TextView text_option_summer;

    protected TextView text_option_summer_value;

    protected TextView text_option_ginga_switch;

    protected TextView text_option_ginga_switch_value;

    protected LinearLayout linear_option_ginga_switch;

    protected LinearLayout linear_option_ginga_list;

    protected TextView text_option_ginga_list;

    protected LinearLayout linear_set_cc_setting;

    protected LinearLayout linear_set_vchip_setting;

    protected LinearLayout linear_option_store_cookies;

    protected TextView text_option_store_cookies;

    protected TextView text_option_store_cookies_value;

    private Activity mOptionActivity;

    private int focusedid = 0x00000000;

    private int mClosedCaptionMode = TvCcManager.CLOSED_CAPTION_OFF;

    private String[] mClosedCaptionStrings = null;

    private String[] mStoreCookiesTable = null;

    private Handler mHandler = new Handler();

    private CharSequence[] gingaApps;

    private List<GingaInfo> apps;

    private int mTvSystem = 0;

    private static TvCcManager mTvCcManager = null;

    private int mCountry = TvCountry.OTHERS;

    private int mStoreCookiesIndex = SWITCH_OFF;

    private PasswordCheckDialog mVchipEntry = null;

    public OptionViewHolder(Activity activity) {
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvCcManager = TvCcManager.getInstance();
        mCountry = TvChannelManager.getInstance().getSystemCountryId();
        this.mOptionActivity = activity;
    }

    public void findViews() {
        text_option_caption = (TextView) mOptionActivity.findViewById(R.id.textview_option_caption);

        text_option_caption_value = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_caption_val);

        linear_option_caption = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_set_caption);

        text_option_ginga_switch = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_ginga_switch);

        text_option_ginga_switch_value = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_ginga_switch_val);

        linear_option_ginga_switch = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_option_ginga_switch);

        linear_option_ginga_list = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_option_ginga_list);

        text_option_ginga_list = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_ginga_list);

        text_option_summer = (TextView) mOptionActivity.findViewById(R.id.textview_option_summer);

        text_option_summer_value = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_summer_val);

        linear_option_summer_time = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_set_summer_time);

        linear_set_cc_setting = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_set_cc_setting);

        linear_set_vchip_setting = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_set_vchip_setting);

        linear_option_store_cookies = (LinearLayout) mOptionActivity
                .findViewById(R.id.linearlayout_option_store_cookies);

        text_option_store_cookies = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_store_cookies);

        text_option_store_cookies_value = (TextView) mOptionActivity
                .findViewById(R.id.textview_option_store_cookies_val);

        if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
            linear_option_summer_time.setVisibility(View.GONE);
            linear_set_vchip_setting.setVisibility(View.GONE);
            linear_option_store_cookies.setVisibility(View.GONE);
        } else if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            linear_option_ginga_switch.setVisibility(View.GONE);
            linear_option_ginga_list.setVisibility(View.GONE);
            linear_option_store_cookies.setVisibility(View.GONE);
        } else {
            linear_set_vchip_setting.setVisibility(View.GONE);
            linear_option_ginga_switch.setVisibility(View.GONE);
            linear_option_ginga_list.setVisibility(View.GONE);
            if (mCountry != TvCountry.SPAIN) {
                linear_option_store_cookies.setVisibility(View.GONE);
            }
        }

        if (TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_CC)) {
            linear_option_caption.setVisibility(View.GONE);
            linear_set_cc_setting.setVisibility(View.VISIBLE);
        } else if (TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_BRAZIL_CC)) {
            linear_option_caption.setVisibility(View.VISIBLE);
            linear_set_cc_setting.setVisibility(View.GONE);
        } else {
            linear_option_caption.setVisibility(View.GONE);
            linear_set_cc_setting.setVisibility(View.GONE);
        }

        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            mVchipEntry = new PasswordCheckDialog(mOptionActivity) {
                @Override
                public String onCheckPassword() {
                    return MenuConstants.getSharedPreferencesValue(mOptionActivity, MenuConstants.VCHIPPASSWORD, MenuConstants.VCHIPPASSWORD_DEFAULTVALUE);
                }

                @Override
                public void onPassWordCorrect() {
                    mToast.cancel();
                    mToast = Toast.makeText(mOptionActivity, mOptionActivity.getResources().getString(R.string.str_check_password_pass) , Toast.LENGTH_SHORT);
                    mToast.show();
                    dismiss();
                    Intent intent = new Intent();
                    intent.setAction(TvIntent.ACTION_VCHIP_ACTIVITY);
                    if (intent.resolveActivity(mOptionActivity.getPackageManager()) != null) {
                        mOptionActivity.startActivity(intent);
                        mOptionActivity.finish();
                    }
                }

                @Override
                public void onKeyDown(int keyCode, KeyEvent keyEvent) {
                    if (KeyEvent.KEYCODE_MENU == keyCode) {
                        dismiss();
                    }
                }

                @Override
                public void onShow() {
                    LittleDownTimer.pauseMenu();
                    View view = mOptionActivity.findViewById(android.R.id.content);
                    if (null != view) {
                        view.animate().alpha(0f)
                                .setDuration(mOptionActivity.getResources().getInteger(android.R.integer.config_shortAnimTime));
                    }
                }

                @Override
                public void onDismiss() {
                    LittleDownTimer.resumeMenu();
                    View view = mOptionActivity.findViewById(android.R.id.content);
                    if (null != view) {
                        view.animate().alpha(1f)
                                .setDuration(mOptionActivity.getResources().getInteger(android.R.integer.config_shortAnimTime));
                    }
                }
            };
        }

        Resources resources = mOptionActivity.getResources();
        mClosedCaptionStrings = resources.getStringArray(R.array.str_arr_option_closed_caption);
        mStoreCookiesTable = resources.getStringArray(R.array.str_arr_oad_view_prompt_value);

        setOnClickLisenters();
        setOnFocusChangeListeners();
        setOnTouchListeners();
    }

    public void loadDataToUi() {
        int inputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        mClosedCaptionMode = mTvCcManager.getClosedCaptionMode();
        if ((inputSource == TvCommonManager.INPUT_SOURCE_ATV)
                || (inputSource == TvCommonManager.INPUT_SOURCE_DTV)) {
            if (0 <= mClosedCaptionMode && mClosedCaptionStrings.length > mClosedCaptionMode) {
                text_option_caption_value.setText(mClosedCaptionStrings[mClosedCaptionMode]);
            }
        } else {
            text_option_caption_value
                    .setText(mClosedCaptionStrings[TvCcManager.CLOSED_CAPTION_OFF]);
            linear_option_caption.setFocusable(false);
            text_option_caption.setTextColor(Color.GRAY);
            text_option_caption_value.setTextColor(Color.GRAY);
        }

        if (inputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            boolean isGingaOn = isGingaOn();
            setGingaSwitchText(isGingaOn);
            setGingaListState(isGingaOn);
            boolean isSummerTimeOn = isSummerTimeOn();
            setSummerTimeText(isSummerTimeOn);
            if (mCountry == TvCountry.SPAIN) {
                mStoreCookiesIndex = TvHbbTVManager.getInstance().getStoreCookiesEnable() == false ? SWITCH_OFF
                        : SWITCH_ON;
                text_option_store_cookies_value.setText(mStoreCookiesTable[mStoreCookiesIndex]);
            }
        } else {
            setGingaSwitchText(false);
            linear_option_ginga_switch.setFocusable(false);
            text_option_ginga_switch.setTextColor(Color.GRAY);
            text_option_ginga_switch_value.setTextColor(Color.GRAY);
            setGingaListState(false);
            setSummerTimeText(false);
            linear_option_summer_time.setFocusable(false);
            text_option_summer.setTextColor(Color.GRAY);
            text_option_summer_value.setTextColor(Color.GRAY);
            if (mCountry == TvCountry.SPAIN) {
                linear_option_store_cookies.setFocusable(false);
                text_option_store_cookies.setTextColor(Color.GRAY);
                text_option_store_cookies_value.setTextColor(Color.GRAY);
            }
        }
    }

    private boolean isGingaOn() {
        return TvGingaManager.getInstance().isGingaEnabled();
    }

    private boolean isSummerTimeOn() {
        return TvTimerManager.getInstance().getDaylightSavingState();
    }

    private void setGingaSwitchText(boolean isGingaOn) {
        text_option_ginga_switch_value
                .setText(mOptionActivity.getString(isGingaOn ? R.string.str_option_ginga_on
                        : R.string.str_option_ginga_off));
    }

    private void setSummerTimeText(boolean isSummerTimeOn) {
        text_option_summer_value.setText(mOptionActivity
                .getString(isSummerTimeOn ? R.string.str_option_summer_time_on
                        : R.string.str_option_summer_time_off));
    }

    private void setGingaEnableState(boolean enable) {
        if (enable) {
            TvGingaManager.getInstance().enableGinga();
        } else {
            TvGingaManager.getInstance().disableGinga();
        }
        setGingaListState(enable);
    }

    private void setGingaListState(boolean enable) {
        linear_option_ginga_list.setEnabled(enable);
        linear_option_ginga_list.setFocusable(enable);
        text_option_ginga_list.setTextColor(enable ? Color.WHITE : Color.GRAY);
    }

    private String getUsbPath() {
        MStorageManager storageManager = MStorageManager.getInstance(mOptionActivity);
        String[] volumes = storageManager.getVolumePaths();

        if (volumes == null || volumes.length < 2) {
            return "";
        }

        String state = storageManager.getVolumeState(volumes[1]);
        if (state == null || !state.equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        String path = volumes[1];
        return path;
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentid = mOptionActivity.getCurrentFocus().getId();
                Intent intent = new Intent();
                if (focusedid != currentid) {
                    MainMenuActivity.getInstance().setOptionSelectStatus(0x00000000);
                }
                switch (view.getId()) {
                    case R.id.linearlayout_set_caption:
                        MainMenuActivity.getInstance().setOptionSelectStatus(0x00000001);
                        focusedid = R.id.linearlayout_set_caption;
                        linear_option_caption.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_option_caption.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_option_ginga_switch:
                        MainMenuActivity.getInstance().setOptionSelectStatus(0x00000010);
                        focusedid = R.id.linearlayout_option_ginga_switch;
                        linear_option_ginga_switch.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_option_ginga_switch.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_option_ginga_list:
                        String path = getUsbPath();
                        if (!TextUtils.isEmpty(path)) {
                            path += "/apps/";
                        }
                        apps = TvGingaManager.getInstance().getApps(path);
                        int size = apps.size();
                        gingaApps = new String[size];
                        for (int j = 0; j < size; j++) {
                            gingaApps[j] = apps.get(j).name;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(mOptionActivity);
                        builder.setTitle("Ginga App")
                                .setItems(gingaApps, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        final long aid = apps.get(which).aid;
                                        final long oid = apps.get(which).oid;
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                TvGingaManager.getInstance().startApplication(aid,
                                                        oid);
                                            }
                                        }, 1000);
                                        mOptionActivity.finish();
                                    }
                                }).show();
                        break;
                    case R.id.linearlayout_set_summer_time:
                        MainMenuActivity.getInstance().setOptionSelectStatus(0x00000100);
                        focusedid = R.id.linearlayout_set_summer_time;
                        linear_option_summer_time.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_option_summer_time.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_set_cc_setting:
                        // add onTouchListener if needed
                        // MainMenuActivity.selectedStatusInSetting =
                        // 0x11000011;
                        // focusedid = R.id.linearlayout_set_cc_setting;
                        intent.setClass(mOptionActivity, ClosedCaptionActivity.class);
                        mOptionActivity.startActivity(intent);
                        break;
                    case R.id.linearlayout_set_vchip_setting:
                        // add onTouchListener if needed
                        // MainMenuActivity.selectedStatusInSetting =
                        // 0x11000011;
                        // focusedid = R.id.linearlayout_set_vchip_setting;
                        mVchipEntry.show();
                        break;
                    case R.id.linearlayout_option_store_cookies:
                        MainMenuActivity.getInstance().setOptionSelectStatus(0x00001000);
                        focusedid = R.id.linearlayout_option_store_cookies;
                        linear_option_store_cookies.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_option_store_cookies.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        };
        linear_option_caption.setOnClickListener(listener);
        linear_option_ginga_switch.setOnClickListener(listener);
        linear_option_ginga_list.setOnClickListener(listener);
        linear_option_summer_time.setOnClickListener(listener);
        linear_set_cc_setting.setOnClickListener(listener);
        linear_option_store_cookies.setOnClickListener(listener);
        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            linear_set_vchip_setting.setOnClickListener(listener);
        }
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener FocuschangesListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout container = (LinearLayout) v;
                container.getChildAt(0).setVisibility(View.GONE);
                container.getChildAt(3).setVisibility(View.GONE);
                MainMenuActivity.getInstance().setOptionSelectStatus(0x00000000);
            }
        };
        linear_option_caption.setOnFocusChangeListener(FocuschangesListener);
        linear_option_ginga_switch.setOnFocusChangeListener(FocuschangesListener);
        linear_option_summer_time.setOnFocusChangeListener(FocuschangesListener);
        linear_option_store_cookies.setOnFocusChangeListener(FocuschangesListener);
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        int currentid = -1;
        if (mOptionActivity.getCurrentFocus() != null) {
            currentid = mOptionActivity.getCurrentFocus().getId();
        }
        if (focusedid != currentid) {
            MainMenuActivity.getInstance().setOptionSelectStatus(0x00000000);
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_set_caption:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00000001) {
                            mClosedCaptionMode = mTvCcManager.getNextClosedCaptionMode();
                            mTvCcManager.setClosedCaptionMode(mClosedCaptionMode);
                            mTvCcManager.stopCc();
                            if (TvCcManager.CLOSED_CAPTION_OFF != mClosedCaptionMode) {
                                mTvCcManager.startCc();
                            }
                            if (0 <= mClosedCaptionMode
                                    && mClosedCaptionStrings.length > mClosedCaptionMode) {
                                text_option_caption_value
                                        .setText(mClosedCaptionStrings[mClosedCaptionMode]);
                            }
                            setGingaListState(true);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_option_ginga_switch:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00000010) {
                            boolean gingaStatus = isGingaOn();
                            gingaStatus = !gingaStatus;
                            setGingaSwitchText(gingaStatus);
                            setGingaEnableState(gingaStatus);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_set_summer_time:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00000100) {
                            boolean summerTimeStatus = isSummerTimeOn();
                            summerTimeStatus = !summerTimeStatus;
                            setSummerTimeText(summerTimeStatus);
                            TvTimerManager.getInstance().setDaylightSavingState(summerTimeStatus);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_option_store_cookies:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00001000) {
                            if (mStoreCookiesIndex == SWITCH_OFF) {
                                mStoreCookiesIndex = SWITCH_ON;
                            } else {
                                mStoreCookiesIndex = SWITCH_OFF;
                            }
                            TvHbbTVManager.getInstance().setStoreCookiesEnable(
                                    mStoreCookiesIndex == SWITCH_OFF ? false : true);
                            text_option_store_cookies_value
                                    .setText(mStoreCookiesTable[mStoreCookiesIndex]);
                        }
                        focusedid = currentid;
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_set_caption:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00000001) {
                            mClosedCaptionMode = mTvCcManager.getPreviousClosedCaptionMode();
                            mTvCcManager.setClosedCaptionMode(mClosedCaptionMode);
                            mTvCcManager.stopCc();
                            if (TvCcManager.CLOSED_CAPTION_OFF != mClosedCaptionMode) {
                                mTvCcManager.startCc();
                            }
                            if (0 <= mClosedCaptionMode
                                    && mClosedCaptionStrings.length > mClosedCaptionMode) {
                                text_option_caption_value
                                        .setText(mClosedCaptionStrings[mClosedCaptionMode]);
                            }
                            setGingaListState(true);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_option_ginga_switch:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00000010) {
                            boolean gingaStatus = isGingaOn();
                            gingaStatus = !gingaStatus;
                            setGingaSwitchText(gingaStatus);
                            setGingaEnableState(gingaStatus);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_set_summer_time:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00000100) {
                            boolean summerTimeStatus = isSummerTimeOn();
                            summerTimeStatus = !summerTimeStatus;
                            setSummerTimeText(summerTimeStatus);
                            TvTimerManager.getInstance().setDaylightSavingState(summerTimeStatus);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_option_store_cookies:
                        if (MainMenuActivity.getInstance().getOptionSelectStatus() == 0x00001000) {
                            if (mStoreCookiesIndex == SWITCH_OFF) {
                                mStoreCookiesIndex = SWITCH_ON;
                            } else {
                                mStoreCookiesIndex = SWITCH_OFF;
                            }
                            TvHbbTVManager.getInstance().setStoreCookiesEnable(
                                    mStoreCookiesIndex == SWITCH_OFF ? false : true);
                            text_option_store_cookies_value
                                    .setText(mStoreCookiesTable[mStoreCookiesIndex]);
                        }
                        focusedid = currentid;
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void restartActivity() {
        mOptionActivity.finish();
        Intent intent = new Intent(TvIntent.MAINMENU);
        intent.putExtra("currentPage", MainMenuActivity.OPTION_PAGE);
        mOptionActivity.startActivity(intent);
    }

    private void setOnTouchListeners() {
        setMyOntouchListener(R.id.linearlayout_set_caption, 0x00000001, linear_option_caption);
        setMyOntouchListener(R.id.linearlayout_option_ginga_switch, 0x00000010,
                linear_option_ginga_switch);
        setMyOntouchListener(R.id.linearlayout_set_summer_time, 0x00000100,
                linear_option_summer_time);
        setMyOntouchListener(R.id.linearlayout_set_summer_time, 0x00001000,
                linear_option_store_cookies);
    }

    private void setMyOntouchListener(final int resID, final int status, LinearLayout lay) {
        lay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    v.requestFocus();
                    MainMenuActivity.getInstance().setOptionSelectStatus(status);
                    focusedid = resID;
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                }
                return true;
            }
        });
    }
}
