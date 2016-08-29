//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2012 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tv.tvplayer.ui.tuning;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.util.Constant;

public class AutoTuneOptionActivity extends MstarBaseActivity {
    /** Called when the activity is first created. */

    private static final String TAG = "AutoTuneOptionActivity";

    private ViewHolder viewholder_autotune;

    private int mTvSystem = 0;

    private int mTuningType = TvChannelManager.TV_SCAN_ATV;

    private int mAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_NONE;

    private int page_index = 0;

    public static boolean isautotuning = false;   //add by wxy
    
    private String[] mOptionScanTypes = {
            "ATV", "DTV", "ATV/DTV"
    };

    private String[] mOptionScanAntennaTypes = {
           "None", "Air", "Cable"
    };

    private final int ONEPINDEXS = 9;

    private int MAXINDEXS;

    private String[] OptionCountrys;

    private TvChannelManager mTvChannelManager = null;

    private SharedPreferences settings;

    private int MSG_ONCREAT = 1001;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_ONCREAT) {
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    mTuningType = TvChannelManager.TV_SCAN_ALL;
                    viewholder_autotune.text_cha_autotuning_antenna_val
                        .setText(mOptionScanAntennaTypes[mAntennaType]);
                    viewholder_autotune.relative_country_choose.setVisibility(View.GONE);
                    viewholder_autotune.linear_4dots.setVisibility(View.INVISIBLE);
                    viewholder_autotune.linear_main.setBackgroundResource(R.drawable.list_menu_img_bg);
                } else {
                    viewholder_autotune.linear_cha_autotuning_antenna_type.setVisibility(View.GONE);
                    viewholder_autotune.relative_search.setVisibility(View.GONE);
                    updateUiCoutrySelect();
                    focusLastTuneCountry(msg.arg1);
                }
                viewholder_autotune.linear_cha_autotuning_tuningtype.requestFocus();
                viewholder_autotune.text_cha_autotuning_tuningtype_val
                        .setText(mOptionScanTypes[mTuningType]);
            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autotuning);

        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvChannelManager = TvChannelManager.getInstance();
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            if(TvAtscChannelManager.getInstance()
                .getDtvAntennaType() == TvChannelManager.DTV_ANTENNA_TYPE_AIR) {
                mAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_AIR;
            } else {
                mAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_CABLE;
            }
        }
        
        //add by wxy
        LittleDownTimer.resumeMenu();
        LittleDownTimer.resumeItem();
        
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                int curCountryIdex = settings.getInt("tuningCountry", 0);
                page_index = curCountryIdex / ONEPINDEXS;
                viewholder_autotune = new ViewHolder(AutoTuneOptionActivity.this);
                viewholder_autotune.findViewsForAutoTuning();
                mTuningType = mTvChannelManager.getUserScanType();
                OptionCountrys = getResources().getStringArray(R.array.str_arr_autotuning_country);
                MAXINDEXS = OptionCountrys.length;
                registerListeners();
                Message msg = Message.obtain();
                msg.what = MSG_ONCREAT;
                msg.arg1 = curCountryIdex;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
        if (false == settings.getBoolean(Constant.PREFERENCES_IS_AUTOSCAN_LAUNCHED, false)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constant.PREFERENCES_IS_AUTOSCAN_LAUNCHED, true).commit();
        }
        LittleDownTimer.resumeItem();
        LittleDownTimer.resumeMenu();
    }
    
    //add by wxy
    @Override
    public void onUserInteraction() {
        LittleDownTimer.resetMenu();
        LittleDownTimer.resetItem();
        super.onUserInteraction();
    }
    //add end
    
    private void focusLastTuneCountry(int index) {

        index++;
        switch (index % ONEPINDEXS) {

            case 1:
                viewholder_autotune.button_cha_country_1.requestFocus();
                break;
            case 2:
                viewholder_autotune.button_cha_country_2.requestFocus();
                break;
            case 3:
                viewholder_autotune.button_cha_country_3.requestFocus();
                break;
            case 4:
                viewholder_autotune.button_cha_country_4.requestFocus();
                break;
            case 5:
                viewholder_autotune.button_cha_country_5.requestFocus();
                break;
            case 6:
                viewholder_autotune.button_cha_country_6.requestFocus();
                break;
            case 7:
                viewholder_autotune.button_cha_country_7.requestFocus();
                break;
            case 8:
                viewholder_autotune.button_cha_country_8.requestFocus();
                break;
            case 0:
                viewholder_autotune.button_cha_country_9.requestFocus();
                break;
            default:
                // do nothing
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = getCurrentFocus().getId();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_cha_autotuning_tuningtype:
                        if (mTuningType == TvChannelManager.TV_SCAN_ALL)
                            mTuningType = TvChannelManager.TV_SCAN_ATV;
                        else
                            mTuningType++;
                        viewholder_autotune.text_cha_autotuning_tuningtype_val
                                .setText(mOptionScanTypes[mTuningType]);
                        break;
                    case R.id.linearlayout_cha_autotuning_antenna_type:
                        if (mAntennaType == TvChannelManager.DTV_ANTENNA_TYPE_CABLE)
                            mAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_AIR;
                        else
                            mAntennaType++;
                        viewholder_autotune.text_cha_autotuning_antenna_val
                            .setText(mOptionScanAntennaTypes[mAntennaType]);
                        break;
                    case R.id.button_cha_autotuning_choosecountry_denmark: {
                        if ((page_index + 1) * ONEPINDEXS < MAXINDEXS) {
                            page_index++;
                            updateUiCoutrySelect();
                            viewholder_autotune.button_cha_country_1.requestFocus();
                        }
                    }
                        break;
                    case R.id.button_cha_autotuning_choosecountry_finland: {
                        if ((page_index + 1) * ONEPINDEXS < MAXINDEXS) {
                            page_index++;
                            updateUiCoutrySelect();
                            viewholder_autotune.button_cha_country_2.requestFocus();
                        }
                    }
                        break;
                    case R.id.button_cha_autotuning_choosecountry_france: {
                        if ((page_index + 1) * ONEPINDEXS < MAXINDEXS) {
                            page_index++;
                            updateUiCoutrySelect();
                            viewholder_autotune.button_cha_country_3.requestFocus();
                        }
                    }
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_cha_autotuning_tuningtype:
                        if (mTuningType == TvChannelManager.TV_SCAN_ATV)
                            mTuningType = TvChannelManager.TV_SCAN_ALL;
                        else
                            mTuningType--;
                        viewholder_autotune.text_cha_autotuning_tuningtype_val
                                .setText(mOptionScanTypes[mTuningType]);
                        break;
                        case R.id.linearlayout_cha_autotuning_antenna_type:
                        if (mAntennaType == TvChannelManager.DTV_ANTENNA_TYPE_AIR)
                            mAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_CABLE;
                        else
                            mAntennaType--;
                        viewholder_autotune.text_cha_autotuning_antenna_val
                            .setText(mOptionScanAntennaTypes[mAntennaType]);
                    break;
                    case R.id.button_cha_autotuning_choosecountry_austria: {
                        if (page_index > 0) {
                            page_index--;
                            updateUiCoutrySelect();
                            viewholder_autotune.button_cha_country_8.requestFocus();
                        }
                    }
                        break;
                    case R.id.button_cha_autotuning_choosecountry_australia: {
                        if (page_index > 0) {
                            page_index--;
                            updateUiCoutrySelect();
                            viewholder_autotune.button_cha_country_7.requestFocus();
                        }
                    }
                        break;
                    case R.id.button_cha_autotuning_choosecountry_beligum: {
                        if (page_index > 0) {
                            page_index--;
                            updateUiCoutrySelect();
                            viewholder_autotune.button_cha_country_9.requestFocus();
                        }
                    }
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                Intent intent = new Intent(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
                startActivity(intent);
                isautotuning = false;
                finish();
                break;
            default:
                break;
        }
        //FIXME, remove ATSC/ISDB setup antenna type and move to ChannelViewHolder
        mTvChannelManager.setUserScanType(mTuningType);
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            TvAtscChannelManager.getInstance().setDtvAntennaType(mAntennaType);
        } else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            TvIsdbChannelManager.getInstance().setDtvAntennaType(mAntennaType);
        }

        return super.onKeyDown(keyCode, event);
    }

    private void registerListeners() {
        viewholder_autotune.button_cha_country_1.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_2.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_3.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_4.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_5.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_6.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_7.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_8.setOnClickListener(listener);
        viewholder_autotune.button_cha_country_9.setOnClickListener(listener);

        viewholder_autotune.button_search.setOnClickListener(listenerSearchBtn);
    }

    private OnClickListener listenerSearchBtn = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("isAustria", false);
            intent.setClass(AutoTuneOptionActivity.this, ChannelTuning.class);
            startActivity(intent);
            finish();
        }
    };

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
        	isautotuning = true;
        	
            switch (view.getId()) {
                case R.id.button_cha_autotuning_choosecountry_australia:
                    CoutrySelected(0);
                    break;
                case R.id.button_cha_autotuning_choosecountry_austria:
                    CoutrySelected(1);
                    break;
                case R.id.button_cha_autotuning_choosecountry_beligum:
                    CoutrySelected(2);
                    break;
                case R.id.button_cha_autotuning_choosecountry_bulgaral:
                    CoutrySelected(3);
                    break;
                case R.id.button_cha_autotuning_choosecountry_croatia:
                    CoutrySelected(4);
                    break;
                case R.id.button_cha_autotuning_choosecountry_czech:
                    CoutrySelected(5);
                    break;
                case R.id.button_cha_autotuning_choosecountry_denmark:
                    CoutrySelected(6);
                    break;
                case R.id.button_cha_autotuning_choosecountry_finland:
                    CoutrySelected(7);
                    break;
                case R.id.button_cha_autotuning_choosecountry_france:
                    CoutrySelected(8);
                    break;
                default:
                    break;
            }
        }
    };

    private void setSystemTimeZone(int timeZone) {
        // set time zone according country selection
        String timeZoneString = "Asia/Shanghai";

        if (timeZone == TvCountry.AUSTRALIA) {
            timeZoneString = "Australia/Sydney";
        } else if (timeZone == TvCountry.AUSTRIA) {
            timeZoneString = "Europe/Vienna";
        } else if (timeZone == TvCountry.BELGIUM) {
            timeZoneString = "Europe/Brussels";
        } else if (timeZone == TvCountry.BULGARIA) {
            timeZoneString = "Europe/Sofia";
        } else if (timeZone == TvCountry.CROATIA) {
            timeZoneString = "Europe/Zagreb";
        } else if (timeZone == TvCountry.CZECH) {
            timeZoneString = "Europe/Prague";
        } else if (timeZone == TvCountry.DENMARK) {
            timeZoneString = "Europe/Copenhagen";
        } else if (timeZone == TvCountry.FINLAND) {
            timeZoneString = "Europe/Helsinki";
        } else if (timeZone == TvCountry.FRANCE) {
            timeZoneString = "Europe/Paris";
        } else if (timeZone == TvCountry.GERMANY) {
            timeZoneString = "Europe/Berlin";
        } else if (timeZone == TvCountry.GREECE) {
            timeZoneString = "Europe/Athens";
        } else if (timeZone == TvCountry.HUNGARY) {
            timeZoneString = "Europe/Budapest";
        } else if (timeZone == TvCountry.ITALY) {
            timeZoneString = "Europe/Rome";
        } else if (timeZone == TvCountry.LUXEMBOURG) {
            timeZoneString = "Europe/Luxembourg";
        } else if (timeZone == TvCountry.NETHERLANDS) {
            timeZoneString = "Europe/Amsterdam";
        } else if (timeZone == TvCountry.NORWAY) {
            timeZoneString = "Europe/Oslo";
        } else if (timeZone == TvCountry.POLAND) {
            timeZoneString = "Europe/Warsaw";
        } else if (timeZone == TvCountry.PORTUGAL) {
            timeZoneString = "Europe/Lisbon";
        } else if (timeZone == TvCountry.RUMANIA) {
            // use default
            timeZoneString = "Europe/London";
        } else if (timeZone == TvCountry.RUSSIA) {
            timeZoneString = "Asia/Kamchatka";
        } else if (timeZone == TvCountry.SERBIA) {
            timeZoneString = "Europe/Belgrade";
        } else if (timeZone == TvCountry.SLOVENIA) {
            timeZoneString = "Europe/Ljubljana";
        } else if (timeZone == TvCountry.SPAIN) {
            timeZoneString = "Europe/Madrid";
        } else if (timeZone == TvCountry.SWEDEN) {
            timeZoneString = "Europe/Stockholm";
        } else if (timeZone == TvCountry.SWITZERLAND) {
            timeZoneString = "Europe/Zurich";
        } else if (timeZone == TvCountry.UK) {
            timeZoneString = "Europe/London";
        } else if (timeZone == TvCountry.NEWZEALAND) {
            timeZoneString = "Europe/London";
        } else if (timeZone == TvCountry.ARAB) {
            timeZoneString = "Asia/Dubai";
        } else if (timeZone == TvCountry.ESTONIA) {
            timeZoneString = "Europe/Tallinn";
        } else if (timeZone == TvCountry.HEBREW) {
            timeZoneString = "Europe/Tallinn";
        } else if (timeZone == TvCountry.LATVIA) {
            timeZoneString = "Europe/Riga";
        } else if (timeZone == TvCountry.SLOVAKIA) {
            timeZoneString = "Europe/Bratislava";
        } else if (timeZone == TvCountry.TURKEY) {
            timeZoneString = "Europe/Istanbul";
        } else if (timeZone == TvCountry.IRELAND) {
            timeZoneString = "Europe/Dublin";
        } else if (timeZone == TvCountry.JAPAN) {
            timeZoneString = "Asia/Tokyo";
        } else if (timeZone == TvCountry.PHILIPPINES) {
            timeZoneString = "Asia/Manila";
        } else if (timeZone == TvCountry.THAILAND) {
            timeZoneString = "Asia/Bangkok";
        } else if (timeZone == TvCountry.MALDIVES) {
            timeZoneString = "Indian/Maldives";
        } else if (timeZone == TvCountry.URUGUAY) {
            timeZoneString = "America/Montevideo";
        } else if (timeZone == TvCountry.PERU) {
            timeZoneString = "America/Lima";
        } else if (timeZone == TvCountry.ARGENTINA) {
            timeZoneString = "America/Argentina/Buenos_Aires";
        } else if (timeZone == TvCountry.CHILE) {
            timeZoneString = "America/Santiago";
        } else if (timeZone == TvCountry.VENEZUELA) {
            timeZoneString = "America/Caracas";
        } else if (timeZone == TvCountry.ECUADOR) {
            timeZoneString = "America/Guayaquil";
        } else if (timeZone == TvCountry.COSTARICA) {
            timeZoneString = "America/Guayaquil";
        } else if (timeZone == TvCountry.PARAGUAY) {
            timeZoneString = "America/Asuncion";
        } else if (timeZone == TvCountry.BOLIVIA) {
            timeZoneString = "America/La_Paz";
        } else if (timeZone == TvCountry.BELIZE) {
            timeZoneString = "America/Belize";
        } else if (timeZone == TvCountry.NICARAGUA) {
            timeZoneString = "America/Managua";
        } else if (timeZone == TvCountry.GUATEMALA) {
            timeZoneString = "America/Guatemala";
        } else if (timeZone == TvCountry.CHINA) {
            timeZoneString = "Asia/Shanghai";
        } else if (timeZone == TvCountry.TAIWAN) {
            timeZoneString = "Asia/Taipei";
        } else if (timeZone == TvCountry.BRAZIL) {
            timeZoneString = "America/Noronha";
        } else if (timeZone == TvCountry.CANADA) {
            timeZoneString = "America/St_Johns";
        } else if (timeZone == TvCountry.MEXICO) {
            timeZoneString = "America/Mexico_City";
        } else if (timeZone == TvCountry.US) {
            timeZoneString = "America/New_York";
        } else if (timeZone == TvCountry.SOUTHKOREA) {
            timeZoneString = "Asia/Shanghai";
        }
        Time time = new Time();
        time.setToNow();
        if (timeZoneString.compareTo(time.timezone) == 0) {
            return;
        }
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setTimeZone(timeZoneString);
    }

    private void CoutrySelected(int index) {
        mTuningType = mTvChannelManager.getUserScanType();
        int coutry_index = page_index * ONEPINDEXS + index;
        if (coutry_index >= TvCountry.COUNTRY_NUM) {
            return;
        }
        Time time = new Time();
        time.setToNow();
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("tuningCountry", coutry_index);
        editor.commit();
        Log.e(TAG, "coutry" + coutry_index);
        mTvChannelManager.setSystemCountryId(coutry_index);
        if (mTuningType == TvChannelManager.TV_SCAN_DTV) {
            int currentRouteIndex = mTvChannelManager.getCurrentDtvRouteIndex();
            int dvbcRouteIndex = mTvChannelManager
                    .getSpecificDtvRouteIndex(TvChannelManager.TV_ROUTE_DVBC);
            if (currentRouteIndex == dvbcRouteIndex) {
                Intent intent = new Intent();
                if (coutry_index == TvCountry.CHINA) {
                    intent.setClass(AutoTuneOptionActivity.this,
                            ChooseCityForAutoTuneActivity.class);
                } else {
                    intent.setClass(AutoTuneOptionActivity.this, DTVAutoTuneOptionActivity.class);
                }
                startActivity(intent);
                finish();
                return;
            }
        }
        Intent intent = new Intent();
        intent.putExtra("isAustria", coutry_index == TvCountry.AUSTRALIA ? true : false);
        intent.putExtra("CoutrySelected_Index", coutry_index);
        intent.setClass(AutoTuneOptionActivity.this, ChannelTuning.class);
        
        
        
        startActivity(intent);
        setSystemTimeZone(coutry_index);
        finish();
    }

    private void updateUiCoutrySelect() {
        int index = page_index * ONEPINDEXS;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_1.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_1.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_1.setText("");
            viewholder_autotune.button_cha_country_1.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_2.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_2.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_2.setText("");
            viewholder_autotune.button_cha_country_2.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_3.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_3.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_3.setText("");
            viewholder_autotune.button_cha_country_3.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_4.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_4.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_4.setText("");
            viewholder_autotune.button_cha_country_4.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_5.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_5.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_5.setText("");
            viewholder_autotune.button_cha_country_5.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_6.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_6.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_6.setText("");
            viewholder_autotune.button_cha_country_6.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_7.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_7.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_7.setText("");
            viewholder_autotune.button_cha_country_7.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_8.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_8.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_8.setText("");
            viewholder_autotune.button_cha_country_8.setFocusable(false);
        }
        index++;
        if (index < MAXINDEXS) {
            viewholder_autotune.button_cha_country_9.setText(OptionCountrys[index]);
            viewholder_autotune.button_cha_country_9.setFocusable(true);
        } else {
            viewholder_autotune.button_cha_country_9.setText("");
            viewholder_autotune.button_cha_country_9.setFocusable(false);
        }
    }
}
