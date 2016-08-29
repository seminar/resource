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

package com.mstar.tv.tvplayer.ui.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import com.mstar.android.tv.TvS3DManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tv.TvCiManager;
import com.mstar.android.tv.TvOadManager;
import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.android.tvapi.common.vo.StandardTime;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.ca.CaActivity;
import com.mstar.tv.tvplayer.ui.channel.ProgramListViewActivity;
import com.mstar.tv.tvplayer.ui.channel.LNBSettingActivity;
import com.mstar.tv.tvplayer.ui.dtv.CimmiActivity;
import com.mstar.tv.tvplayer.ui.component.PasswordCheckDialog;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.CheckParentalPwdActivity;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.MenuConstants;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.tuning.SignalInfoActivity;
import com.mstar.tv.tvplayer.ui.tuning.OadScan;
import com.mstar.util.Tools;

public class ChannelViewHolder {
    private static final String TAG = "ChannelViewHolder";

    private final int SWITCH_OFF = 0;

    private final int SWITCH_ON = 1;

    private final int OAD_UPDATE_TIME_NOW = 24;

    protected TextView text_cha_antennatype_val;

    protected TextView text_cha_software_update_oad_val;

    protected TextView text_cha_time_to_search_for_ssu_val;

    protected TextView text_cha_oad_view_prompt_val;

    protected LinearLayout linear_cha_antennatype;

    protected LinearLayout linear_cha_autotuning;

    protected LinearLayout linear_cha_dtvmanualtuning;

    protected LinearLayout linear_cha_atvmanualtuning;

    protected LinearLayout linear_cha_dvbs_lnbsetting;

    protected LinearLayout linear_cha_programedit;

    protected LinearLayout linear_cha_ciinformation;

    protected LinearLayout linear_cha_cainformation;

    protected LinearLayout linear_cha_signalinfo;

    protected LinearLayout linear_cha_softwareupdateoad;

    protected LinearLayout linear_cha_oadtime;

    protected LinearLayout linear_cha_oadscan;

    protected LinearLayout linear_cha_oadviewprompt;

    private TvS3DManager tvS3DManager = null;

    private final Activity mChannelActivity;

    private final Intent mIntent = new Intent();

    private int focusedid = 0x00000000;

    private boolean[] mRouteEnableTable;

    private String[] mRouteStringTable;

    private String[] mSoftwareUpdateOadTable;

    private String[] mOadViewPromptTable;

    private int mTvSystem = 0;

    private int mAtscAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_AIR;

    private TvAtscChannelManager mTvAtscChannelManager = null;

    private int mSoftwareUpdateOadIndex = SWITCH_OFF;

    private int mOadViewPromptIndex = SWITCH_OFF;

    private int oadTime = 0;

    private PasswordCheckDialog mVchiplLock = null;

    public ChannelViewHolder(Activity activity) {
        this.mChannelActivity = activity;
        tvS3DManager = TvS3DManager.getInstance();
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            mTvAtscChannelManager = TvAtscChannelManager.getInstance();
        }
        if ((TvCommonManager.TV_SYSTEM_ATSC != mTvSystem)
                && (TvCommonManager.TV_SYSTEM_ISDB != mTvSystem)) {
            fillRouteTable();
        }
    }

    public void findViews() {
        text_cha_antennatype_val = (TextView) mChannelActivity
                .findViewById(R.id.textview_cha_antennatype_val);
        text_cha_software_update_oad_val = (TextView) mChannelActivity
                .findViewById(R.id.textview_cha_software_update_oad_val);
        text_cha_time_to_search_for_ssu_val = (TextView) mChannelActivity
                .findViewById(R.id.textview_cha_time_to_search_for_ssu_val);
        text_cha_oad_view_prompt_val = (TextView) mChannelActivity
                .findViewById(R.id.textview_cha_oad_view_prompt_val);
        linear_cha_antennatype = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_antennatype);
        linear_cha_autotuning = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_autotuning);
        linear_cha_dtvmanualtuning = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_dtvmanualtuning);
        linear_cha_atvmanualtuning = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_atvmanualtuning);
        linear_cha_dvbs_lnbsetting = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_dvbs_lnbsetting);
        if (Tools.isBox()) {
            // make sure ATV is unusable for box
            linear_cha_atvmanualtuning.setVisibility(View.GONE);
        }
        linear_cha_programedit = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_programedit);
        linear_cha_ciinformation = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_ciinformation);
        linear_cha_cainformation = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_cainformation);
        linear_cha_signalinfo = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_signalinfo);
        linear_cha_softwareupdateoad = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_software_update_oad);
        linear_cha_oadtime = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_oadtime);
        linear_cha_oadscan = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_oadscan);
        linear_cha_oadviewprompt = (LinearLayout) mChannelActivity
                .findViewById(R.id.linearlayout_cha_oad_view_prompt);

        if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
            linear_cha_antennatype.setVisibility(View.VISIBLE);
            mRouteStringTable = mChannelActivity.getResources().getStringArray(
                    R.array.str_arr_cha_atsc_antannatype);
            linear_cha_ciinformation.setVisibility(View.GONE);
            linear_cha_cainformation.setVisibility(View.GONE);
            linear_cha_softwareupdateoad.setVisibility(View.GONE);
            linear_cha_oadtime.setVisibility(View.GONE);
            linear_cha_oadscan.setVisibility(View.GONE);
            linear_cha_oadviewprompt.setVisibility(View.GONE);
            mAtscAntennaType = mTvAtscChannelManager.getDtvAntennaType();

            /**
             * Only create instance here, TV system other than ATSC should NOT use this
            */
            mVchiplLock = new PasswordCheckDialog(mChannelActivity) {
                @Override
                public String onCheckPassword() {
                    return MenuConstants.getSharedPreferencesValue(mChannelActivity, MenuConstants.VCHIPPASSWORD, MenuConstants.VCHIPPASSWORD_DEFAULTVALUE);
                }

                @Override
                public void onPassWordCorrect() {
                    mToast.cancel();
                    mToast = Toast.makeText(mChannelActivity, mChannelActivity.getResources().getString(R.string.str_check_password_pass) , Toast.LENGTH_SHORT);
                    mToast.show();
                    dismiss();

                    if (mIntent.getAction()!=null && mIntent.getAction().length() > 0) {
                        if (mIntent.resolveActivity(mChannelActivity.getPackageManager()) != null) {
                            mChannelActivity.startActivity(mIntent);
                            mChannelActivity.finish();
                        }
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
                    View view = mChannelActivity.findViewById(android.R.id.content);
                    if (null != view) {
                        view.animate().alpha(0f)
                                .setDuration(mChannelActivity.getResources().getInteger(android.R.integer.config_shortAnimTime));
                    }
                }

                @Override
                public void onDismiss() {
                    LittleDownTimer.resumeMenu();
                    View view = mChannelActivity.findViewById(android.R.id.content);
                    if (null != view) {
                        view.animate().alpha(1f)
                                .setDuration(mChannelActivity.getResources().getInteger(android.R.integer.config_shortAnimTime));
                    }
                }
            };


        } else if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
            linear_cha_antennatype.setVisibility(View.GONE);
            linear_cha_ciinformation.setVisibility(View.GONE);
            linear_cha_cainformation.setVisibility(View.GONE);
            linear_cha_softwareupdateoad.setVisibility(View.VISIBLE);
            linear_cha_oadtime.setVisibility(View.GONE);
            linear_cha_oadscan.setVisibility(View.VISIBLE);
            linear_cha_oadviewprompt.setVisibility(View.GONE);
        } else {
        	//zb20141008 modify
            linear_cha_antennatype.setVisibility(View.GONE);
            linear_cha_ciinformation.setVisibility(View.GONE);
            linear_cha_cainformation.setVisibility(View.GONE);
            linear_cha_softwareupdateoad.setVisibility(View.GONE);
            linear_cha_oadtime.setVisibility(View.GONE);
            linear_cha_oadscan.setVisibility(View.GONE);
            linear_cha_oadviewprompt.setVisibility(View.GONE);
            //end
        }

        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (currInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            enableCompositeItemOrNot(linear_cha_antennatype, true, (short) 2);
            enableSingleItemOrNot(linear_cha_dtvmanualtuning, true);
            enableSingleItemOrNot(linear_cha_ciinformation, true);
            enableSingleItemOrNot(linear_cha_cainformation, true);

            if (0 >= TvChannelManager.getInstance().getProgramCount(
                    TvChannelManager.PROGRAM_COUNT_DTV)) {
                enableSingleItemOrNot(linear_cha_signalinfo, false);
            }
            int curRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
            TvTypeInfo info = TvCommonManager.getInstance().getTvInfo();
            if ((TvChannelManager.TV_ROUTE_DVBS == info.routePath[curRouteIndex])
                    || TvChannelManager.TV_ROUTE_DVBS2 == info.routePath[curRouteIndex]) {
                linear_cha_dvbs_lnbsetting.setVisibility(View.VISIBLE);
                enableSingleItemOrNot(linear_cha_dvbs_lnbsetting, true);
            }
        } else {
            if (mTvSystem != TvCommonManager.TV_SYSTEM_ATSC) {
                enableCompositeItemOrNot(linear_cha_antennatype, false, (short) 2);
                enableSingleItemOrNot(linear_cha_dtvmanualtuning, false);
            }
            enableSingleItemOrNot(linear_cha_ciinformation, false);
            enableSingleItemOrNot(linear_cha_cainformation, false);
            enableSingleItemOrNot(linear_cha_signalinfo, false);
            enableCompositeItemOrNot(linear_cha_softwareupdateoad, false, (short) 2);
            enableCompositeItemOrNot(linear_cha_oadtime, false, (short) 2);
            enableSingleItemOrNot(linear_cha_oadscan, false);
            enableCompositeItemOrNot(linear_cha_oadviewprompt, false, (short) 2);
        }
        setOnClickLisenters();
        setOnFocusChangeListeners();
        setOnTouchListeners();
        if (mTvSystem != TvCommonManager.TV_SYSTEM_ISDB) {
            text_cha_antennatype_val.setText(getAntentText());
        }
        mSoftwareUpdateOadTable = mChannelActivity.getResources().getStringArray(
                R.array.str_arr_software_update_oad_value);
        mOadViewPromptTable = mChannelActivity.getResources().getStringArray(
                R.array.str_arr_oad_view_prompt_value);
        mSoftwareUpdateOadIndex = TvOadManager.getInstance().getSoftwareUpdateState() == 0 ? SWITCH_OFF
                : SWITCH_ON;
        text_cha_software_update_oad_val.setText(mSoftwareUpdateOadTable[mSoftwareUpdateOadIndex]);
        oadTime = TvOadManager.getInstance().getOadTime();
        if (oadTime < OAD_UPDATE_TIME_NOW) {
            text_cha_time_to_search_for_ssu_val.setText(oadTime + ":00");
        } else {
            oadTime = OAD_UPDATE_TIME_NOW;
            text_cha_time_to_search_for_ssu_val.setText("Now");
        }
        mOadViewPromptIndex = TvOadManager.getInstance().getOadViewerPrompt() == false ? SWITCH_OFF
                : SWITCH_ON;
        text_cha_oad_view_prompt_val.setText(mOadViewPromptTable[mOadViewPromptIndex]);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = -1;
        if (mChannelActivity.getCurrentFocus() != null) {
            currentid = mChannelActivity.getCurrentFocus().getId();
        }
        if (focusedid != currentid) {
            MainMenuActivity.selectedstatusforChannel = 0x00000000;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_cha_antennatype:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00000001) {
                            if (Tools.isBox()) { // for box only
                                focusedid = currentid;
                                return true;
                            }
                            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                                if (mAtscAntennaType == TvChannelManager.DTV_ANTENNA_TYPE_AIR) {
                                    mAtscAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_CABLE;
                                    text_cha_antennatype_val.setText(getAntentText());
                                    mTvAtscChannelManager.setDtvAntennaType(mAtscAntennaType);
                                    TvChannelManager.getInstance().changeToFirstService(
                                            TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ALL,
                                            TvChannelManager.FIRST_SERVICE_DEFAULT);
                                }
                            } else {
                                int dtvRoute = getNextRouteIndex();
                                tvS3DManager
                                        .setDisplayFormatForUI(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                                TvDvbChannelManager.getInstance().setDtvAntennaType(dtvRoute);
                                text_cha_antennatype_val.setText(getAntentText());

                                TvTypeInfo info = TvCommonManager.getInstance().getTvInfo();
                                if ((TvChannelManager.TV_ROUTE_DVBS == info.routePath[dtvRoute])
                                        || TvChannelManager.TV_ROUTE_DVBS2 == info.routePath[dtvRoute]) {
                                    linear_cha_dvbs_lnbsetting.setVisibility(View.VISIBLE);
                                    enableSingleItemOrNot(linear_cha_dvbs_lnbsetting, true);
                                } else {
                                    linear_cha_dvbs_lnbsetting.setVisibility(View.GONE);
                                    enableSingleItemOrNot(linear_cha_dvbs_lnbsetting, false);
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_software_update_oad:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00000010) {
                            if (mSoftwareUpdateOadIndex == SWITCH_OFF) {
                                mSoftwareUpdateOadIndex = SWITCH_ON;
                                TvOadManager.getInstance().setOadOn();
                            } else {
                                mSoftwareUpdateOadIndex = SWITCH_OFF;
                                TvOadManager.getInstance().setOadOff();
                            }
                            TvOadManager.getInstance().setSoftwareUpdateState(
                                    mSoftwareUpdateOadIndex);
                            text_cha_software_update_oad_val
                                    .setText(mSoftwareUpdateOadTable[mSoftwareUpdateOadIndex]);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_oadtime:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00000100) {
                            if (oadTime < OAD_UPDATE_TIME_NOW) {
                                oadTime++;
                            } else {
                                oadTime = 0;
                            }
                            TvOadManager.getInstance().setOadTime(oadTime);
                            if (oadTime == OAD_UPDATE_TIME_NOW) {
                                text_cha_time_to_search_for_ssu_val.setText("Now");
                            } else {
                                text_cha_time_to_search_for_ssu_val.setText(oadTime + ":00");
                            }
                            if (mSoftwareUpdateOadIndex == SWITCH_ON) {
                                if (oadTime != OAD_UPDATE_TIME_NOW) {
                                    StandardTime stdTime = new StandardTime();
                                    stdTime = TvTimerManager.getInstance().getCurTimer();
                                    int curMinutes = stdTime.hour * 60 + stdTime.minute;
                                    int targetMinutes = oadTime * 60;
                                    if (targetMinutes > curMinutes) {
                                        TvOadManager.getInstance().setOadScanTime(
                                                60 * (targetMinutes - curMinutes));
                                    } else if (targetMinutes < curMinutes) {
                                        TvOadManager.getInstance().setOadScanTime(
                                                60 * (1440 + targetMinutes - curMinutes));
                                    } else {
                                        TvOadManager.getInstance().setOadScanTime(10);
                                    }
                                } else {
                                    AlertDialog.Builder build = new AlertDialog.Builder(
                                            mChannelActivity);
                                    build.setMessage(R.string.str_oad_msg_scan_confirm);
                                    build.setPositiveButton(R.string.str_oad_yes,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                        int which) {
                                                    TvOadManager.getInstance().resetOad();
                                                    mIntent.setClass(mChannelActivity, OadScan.class);
                                                    mChannelActivity.startActivity(mIntent);
                                                    mChannelActivity.finish();
                                                }
                                            });
                                    build.setNegativeButton(R.string.str_oad_no,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                        int which) {
                                                    mChannelActivity.finish();
                                                }
                                            });
                                    build.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            // TODO Auto-generated method stub
                                            mChannelActivity.finish();
                                        }
                                    });
                                    build.create().show();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_oad_view_prompt:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00001000) {
                            if (mOadViewPromptIndex == SWITCH_OFF) {
                                mOadViewPromptIndex = SWITCH_ON;
                            } else {
                                mOadViewPromptIndex = SWITCH_OFF;
                            }
                            TvOadManager.getInstance().setOadViewerPrompt(
                                    mOadViewPromptIndex == SWITCH_OFF ? false : true);
                            text_cha_oad_view_prompt_val
                                    .setText(mOadViewPromptTable[mOadViewPromptIndex]);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_autotuning:
                    case R.id.linearlayout_cha_dtvmanualtuning:
                    case R.id.linearlayout_cha_atvmanualtuning:
                    case R.id.linearlayout_cha_programedit:
                    case R.id.linearlayout_cha_ciinformation:
                    case R.id.linearlayout_cha_cainformation:
                    case R.id.linearlayout_cha_signalinfo:
                    case R.id.linearlayout_cha_oadscan:
                        focusedid = currentid;
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_cha_antennatype:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00000001) {
                            if (Tools.isBox()) { // for box only
                                focusedid = currentid;
                                return true;
                            }
                            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                                if (mAtscAntennaType == TvChannelManager.DTV_ANTENNA_TYPE_CABLE) {
                                    mAtscAntennaType = TvChannelManager.DTV_ANTENNA_TYPE_AIR;
                                    text_cha_antennatype_val.setText(getAntentText());
                                    mTvAtscChannelManager.setDtvAntennaType(mAtscAntennaType);
                                    TvChannelManager.getInstance().changeToFirstService(
                                            TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ALL,
                                            TvChannelManager.FIRST_SERVICE_DEFAULT);
                                }
                            } else {
                                int dtvRoute = getPreviousRouteIndex();
                                tvS3DManager
                                        .setDisplayFormatForUI(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                                TvDvbChannelManager.getInstance().setDtvAntennaType(dtvRoute);
                                text_cha_antennatype_val.setText(getAntentText());

                                TvTypeInfo info = TvCommonManager.getInstance().getTvInfo();
                                if ((TvChannelManager.TV_ROUTE_DVBS == info.routePath[dtvRoute])
                                        || TvChannelManager.TV_ROUTE_DVBS2 == info.routePath[dtvRoute]) {
                                    linear_cha_dvbs_lnbsetting.setVisibility(View.VISIBLE);
                                    enableSingleItemOrNot(linear_cha_dvbs_lnbsetting, true);
                                } else {
                                    linear_cha_dvbs_lnbsetting.setVisibility(View.GONE);
                                    enableSingleItemOrNot(linear_cha_dvbs_lnbsetting, false);
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_software_update_oad:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00000010) {
                            if (mSoftwareUpdateOadIndex == SWITCH_OFF) {
                                mSoftwareUpdateOadIndex = SWITCH_ON;
                                TvOadManager.getInstance().setOadOn();
                            } else {
                                mSoftwareUpdateOadIndex = SWITCH_OFF;
                                TvOadManager.getInstance().setOadOff();
                            }
                            TvOadManager.getInstance().setSoftwareUpdateState(
                                    mSoftwareUpdateOadIndex);
                            text_cha_software_update_oad_val
                                    .setText(mSoftwareUpdateOadTable[mSoftwareUpdateOadIndex]);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_oadtime:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00000100) {
                            if (oadTime > 0) {
                                oadTime--;
                            } else {
                                oadTime = OAD_UPDATE_TIME_NOW;
                            }
                            TvOadManager.getInstance().setOadTime(oadTime);
                            if (oadTime == OAD_UPDATE_TIME_NOW) {
                                text_cha_time_to_search_for_ssu_val.setText("Now");
                            } else {
                                text_cha_time_to_search_for_ssu_val.setText(oadTime + ":00");
                            }
                            if (mSoftwareUpdateOadIndex == SWITCH_ON) {
                                if (oadTime != OAD_UPDATE_TIME_NOW) {
                                    StandardTime stdTime = new StandardTime();
                                    stdTime = TvTimerManager.getInstance().getCurTimer();
                                    int curMinutes = stdTime.hour * 60 + stdTime.minute;
                                    int targetMinutes = oadTime * 60;
                                    if (targetMinutes > curMinutes) {
                                        TvOadManager.getInstance().setOadScanTime(
                                                60 * (targetMinutes - curMinutes));
                                    } else if (targetMinutes < curMinutes) {
                                        TvOadManager.getInstance().setOadScanTime(
                                                60 * (1440 + targetMinutes - curMinutes));
                                    } else {
                                        TvOadManager.getInstance().setOadScanTime(10);
                                    }
                                } else {
                                    AlertDialog.Builder build = new AlertDialog.Builder(
                                            mChannelActivity);
                                    build.setMessage(R.string.str_oad_msg_scan_confirm);
                                    build.setPositiveButton(R.string.str_oad_yes,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                        int which) {
                                                    TvOadManager.getInstance().resetOad();
                                                    mIntent.setClass(mChannelActivity, OadScan.class);
                                                    mChannelActivity.startActivity(mIntent);
                                                    mChannelActivity.finish();
                                                }
                                            });
                                    build.setNegativeButton(R.string.str_oad_no,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                        int which) {
                                                    mChannelActivity.finish();
                                                }
                                            });
                                    build.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            mChannelActivity.finish();
                                        }
                                    });
                                    build.create().show();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_oad_view_prompt:
                        if (MainMenuActivity.selectedstatusforChannel == 0x00001000) {
                            if (mOadViewPromptIndex == SWITCH_OFF) {
                                mOadViewPromptIndex = SWITCH_ON;
                            } else {
                                mOadViewPromptIndex = SWITCH_OFF;
                            }
                            TvOadManager.getInstance().setOadViewerPrompt(
                                    mOadViewPromptIndex == SWITCH_OFF ? false : true);
                            text_cha_oad_view_prompt_val
                                    .setText(mOadViewPromptTable[mOadViewPromptIndex]);
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_cha_autotuning:
                    case R.id.linearlayout_cha_dtvmanualtuning:
                    case R.id.linearlayout_cha_atvmanualtuning:
                    case R.id.linearlayout_cha_programedit:
                    case R.id.linearlayout_cha_ciinformation:
                    case R.id.linearlayout_cha_cainformation:
                    case R.id.linearlayout_cha_signalinfo:
                    case R.id.linearlayout_cha_oadscan:
                        focusedid = currentid;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentid = mChannelActivity.getCurrentFocus().getId();
                if (focusedid != currentid)
                    MainMenuActivity.selectedstatusforChannel = 0x00000000;
                switch (currentid) {
                    case R.id.linearlayout_cha_antennatype:
                        linear_cha_antennatype.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_cha_antennatype.getChildAt(3).setVisibility(View.VISIBLE);
                        MainMenuActivity.selectedstatusforChannel = 0x00000001;
                        focusedid = R.id.linearlayout_cha_antennatype;
                        break;
                    case R.id.linearlayout_cha_autotuning:
                        if ((mTvSystem != TvCommonManager.TV_SYSTEM_ATSC)
                                && (mTvSystem != TvCommonManager.TV_SYSTEM_ISDB)) {
                            Log.d(TAG, "isOpMode: " + TvCiManager.getInstance().isOpMode());
                            if (TvCiManager.getInstance().isOpMode()) {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    public void run(){
                                        Toast.makeText(mChannelActivity, R.string.str_op_forbid_channel_tuning_content, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            }
                        }

                        // create dvbs autotuning page when current route is
                        // dvbs/dvbs2
                        int currentRouteIndex = TvChannelManager.getInstance()
                                .getCurrentDtvRouteIndex();
                        TvTypeInfo tvInfo = TvCommonManager.getInstance().getTvInfo();
                        int currentRoute = tvInfo.routePath[currentRouteIndex];
                        Log.d(TAG, "currRoute:" + currentRouteIndex);
                        if ((TvChannelManager.TV_ROUTE_DVBS == currentRoute)
                                || (TvChannelManager.TV_ROUTE_DVBS2 == currentRoute)) {
                            Log.d(TAG, "create dvbs activity");
                            mIntent.setAction(TvIntent.ACTION_DVBSDTV_AUTOTUNING_OPTION);
                            if (mIntent.resolveActivity(mChannelActivity.getPackageManager()) != null) {
                                mChannelActivity.startActivity(mIntent);
                            mChannelActivity.finish();
                            }
                        } else {
                            Log.d(TAG, "create normal tuning activity");
                            if (getSystemLock()) {
                                if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                                    mIntent.setAction(TvIntent.ACTION_AUTOTUNING_OPTION);
                                    mVchiplLock.show();
                                } else {
                                    mIntent.setClass(mChannelActivity, CheckParentalPwdActivity.class);
                                    mIntent.putExtra("list", 0);
                                    mChannelActivity.startActivity(mIntent);
                                }
                            } else {
                                mIntent.setAction(TvIntent.ACTION_AUTOTUNING_OPTION);
                                if (mIntent.resolveActivity(mChannelActivity.getPackageManager()) != null) {
                                    mChannelActivity.startActivity(mIntent);
                                    mChannelActivity.finish();
                                }
                            }
                        }
                        break;
                    case R.id.linearlayout_cha_dvbs_lnbsetting:
                        mIntent.setAction(TvIntent.ACTION_LNBSETTING);
                        if (mIntent.resolveActivity(mChannelActivity.getPackageManager()) != null) {
                            mChannelActivity.startActivity(mIntent);
                        }
                        mChannelActivity.finish();
                        break;
                    case R.id.linearlayout_cha_dtvmanualtuning:
                        if ((mTvSystem != TvCommonManager.TV_SYSTEM_ATSC)
                                && (mTvSystem != TvCommonManager.TV_SYSTEM_ISDB)) {
                            Log.d(TAG, "isOpMode: " + TvCiManager.getInstance().isOpMode());
                            if (TvCiManager.getInstance().isOpMode()) {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    public void run(){
                                        Toast.makeText(mChannelActivity, R.string.str_op_forbid_channel_tuning_content, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            }
                        }

                        if (true == getSystemLock()) {
                            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                                mIntent.setAction(TvIntent.ACTION_DTV_MANUAL_TUNING);
                                mVchiplLock.show();
                            } else {
                                mIntent.setClass(mChannelActivity, CheckParentalPwdActivity.class);
                                mIntent.putExtra("list", 1);
                                mChannelActivity.startActivity(mIntent);
                            }
                        } else {
                            mIntent.setAction(TvIntent.ACTION_DTV_MANUAL_TUNING);
                            if (mIntent.resolveActivity(mChannelActivity.getPackageManager()) != null) {
                                mChannelActivity.startActivity(mIntent);
                                mChannelActivity.finish();
                            }
                        }
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning:
                        if ((mTvSystem != TvCommonManager.TV_SYSTEM_ATSC)
                                && (mTvSystem != TvCommonManager.TV_SYSTEM_ISDB)) {
                            Log.d(TAG, "isOpMode: " + TvCiManager.getInstance().isOpMode());
                            if (TvCiManager.getInstance().isOpMode()) {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    public void run(){
                                        Toast.makeText(mChannelActivity, R.string.str_op_forbid_channel_tuning_content, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            }
                        }

                        if (getSystemLock()) {
                            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                                mIntent.setAction(TvIntent.ACTION_ATSC_ATV_MANUAL_TUNING);
                                mVchiplLock.show();
                            } else {
                                mIntent.setClass(mChannelActivity, CheckParentalPwdActivity.class);
                                mIntent.putExtra("list", 2);
                                mChannelActivity.startActivity(mIntent);
                            }
                        } else {
                            if (TvCommonManager.TV_SYSTEM_ATSC == mTvSystem) {
                                mIntent.setAction(TvIntent.ACTION_ATSC_ATV_MANUAL_TUNING);
                            } else {
                                mIntent.setAction(TvIntent.ACTION_ATV_MANUAL_TUNING);
                            }
                            if (mIntent.resolveActivity(mChannelActivity.getPackageManager()) != null) {
                                mChannelActivity.startActivity(mIntent);
                                mChannelActivity.finish();
                            }
                        }
                        break;
                    case R.id.linearlayout_cha_programedit:
                        mIntent.setClass(mChannelActivity, ProgramListViewActivity.class);
                        mChannelActivity.startActivity(mIntent);
                        mChannelActivity.finish();
                        break;
                    case R.id.linearlayout_cha_signalinfo:
                        mIntent.setClass(mChannelActivity, SignalInfoActivity.class);
                        mChannelActivity.startActivity(mIntent);
                        mChannelActivity.finish();
                        break;
                    case R.id.linearlayout_cha_ciinformation: {
                        if (TvCiManager.getInstance() != null
                                && TvCommonManager.getInstance() != null) {
                            int currInputSource = TvCommonManager.getInstance()
                                    .getCurrentTvInputSource();
                            if (currInputSource != TvCommonManager.INPUT_SOURCE_DTV) {
                                break;
                            }

                            int status = TvCiManager.CARD_STATE_NO;
                            status = TvCiManager.getInstance().getCiCardState();

                            if (status == TvCiManager.CARD_STATE_READY) {
                                TvCiManager.getInstance().enterMenu();
                                mIntent.setClass(mChannelActivity, CimmiActivity.class);
                                mChannelActivity.startActivity(mIntent);
                                mChannelActivity.finish();
                                break;
                            }
                        }

                    }
                        break;
                    case R.id.linearlayout_cha_cainformation:
                        if (TvCommonManager.getInstance() != null) {
                            int currInputSource = TvCommonManager.getInstance()
                                    .getCurrentTvInputSource();
                            if (currInputSource != TvCommonManager.INPUT_SOURCE_DTV) {
                                break;
                            }
                            if (TvCaManager.getInstance().CaGetVer() == 0) {
                                break;
                            }
                            mIntent.setClass(mChannelActivity, CaActivity.class);
                            mChannelActivity.startActivity(mIntent);
                            mChannelActivity.finish();
                        }
                        break;
                    case R.id.linearlayout_cha_software_update_oad:
                        linear_cha_softwareupdateoad.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_cha_softwareupdateoad.getChildAt(3).setVisibility(View.VISIBLE);
                        MainMenuActivity.selectedstatusforChannel = 0x00000010;
                        focusedid = R.id.linearlayout_cha_software_update_oad;
                        break;
                    case R.id.linearlayout_cha_oadtime:
                        linear_cha_oadtime.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_cha_oadtime.getChildAt(3).setVisibility(View.VISIBLE);
                        MainMenuActivity.selectedstatusforChannel = 0x00000100;
                        focusedid = R.id.linearlayout_cha_oadtime;
                        break;
                    case R.id.linearlayout_cha_oadscan:
                        if (mSoftwareUpdateOadIndex == SWITCH_ON) {
                            mIntent.setClass(mChannelActivity, OadScan.class);
                            mChannelActivity.startActivity(mIntent);
                            mChannelActivity.finish();
                        }
                        break;
                    case R.id.linearlayout_cha_oad_view_prompt:
                        linear_cha_oadviewprompt.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_cha_oadviewprompt.getChildAt(3).setVisibility(View.VISIBLE);
                        MainMenuActivity.selectedstatusforChannel = 0x00001000;
                        focusedid = R.id.linearlayout_cha_oad_view_prompt;
                        break;
                    default:
                        break;
                }
            }
        };
        linear_cha_antennatype.setOnClickListener(listener);
        linear_cha_autotuning.setOnClickListener(listener);
        linear_cha_dtvmanualtuning.setOnClickListener(listener);
        linear_cha_atvmanualtuning.setOnClickListener(listener);
        linear_cha_dvbs_lnbsetting.setOnClickListener(listener);
        linear_cha_programedit.setOnClickListener(listener);
        linear_cha_ciinformation.setOnClickListener(listener);
        linear_cha_cainformation.setOnClickListener(listener);
        linear_cha_signalinfo.setOnClickListener(listener);
        linear_cha_softwareupdateoad.setOnClickListener(listener);
        linear_cha_oadtime.setOnClickListener(listener);
        linear_cha_oadscan.setOnClickListener(listener);
        linear_cha_oadviewprompt.setOnClickListener(listener);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener FocuschangesListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout container = (LinearLayout) v;
                container.getChildAt(0).setVisibility(View.GONE);
                container.getChildAt(3).setVisibility(View.GONE);
                MainMenuActivity.selectedstatusforChannel = 0x00000000;
            }
        };
        linear_cha_antennatype.setOnFocusChangeListener(FocuschangesListener);
        linear_cha_softwareupdateoad.setOnFocusChangeListener(FocuschangesListener);
        linear_cha_oadtime.setOnFocusChangeListener(FocuschangesListener);
        linear_cha_oadviewprompt.setOnFocusChangeListener(FocuschangesListener);
    }

    private void setOnTouchListeners() {
        setMyOntouchListener(R.id.linearlayout_cha_antennatype, 0x00000001, linear_cha_antennatype);
        setMyOntouchListener(R.id.linearlayout_cha_software_update_oad, 0x00000010,
                linear_cha_softwareupdateoad);
        setMyOntouchListener(R.id.linearlayout_cha_oadtime, 0x00000100, linear_cha_oadtime);
        setMyOntouchListener(R.id.linearlayout_cha_oad_view_prompt, 0x00001000,
                linear_cha_oadviewprompt);
    }

    private void setMyOntouchListener(final int resID, final int status, LinearLayout lay) {

        lay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP) {
                    v.requestFocus();
                    MainMenuActivity.selectedstatusforChannel = status;
                    focusedid = resID;
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                }
                return true;
            }
        });
    }

    void enableSingleItemOrNot(LinearLayout linearLayout, boolean isEnable) {
        if (!isEnable) {
            ((TextView) (linearLayout.getChildAt(0))).setTextColor(Color.GRAY);
            linearLayout.setEnabled(false);
            linearLayout.setFocusable(false);
        } else {
            ((TextView) (linearLayout.getChildAt(0))).setTextColor(Color.WHITE);
            linearLayout.setEnabled(true);
            linearLayout.setFocusable(true);
        }
    }

    void enableCompositeItemOrNot(LinearLayout linearLayout, boolean isEnable, short Count) {
        if (!isEnable) {
            for (short i = 1; i <= Count; i++) {
                ((TextView) (linearLayout.getChildAt(i))).setTextColor(Color.GRAY);
            }
            linearLayout.setEnabled(false);
            linearLayout.setFocusable(false);
        } else {
            for (short i = 1; i <= Count; i++) {
                ((TextView) (linearLayout.getChildAt(i))).setTextColor(Color.WHITE);
            }
            linearLayout.setEnabled(true);
            linearLayout.setFocusable(true);
        }
    }

    private boolean getSystemLock() {
        return TvParentalControlManager.getInstance().isSystemLock();
    }

    private String getAntentText() {
        String str = null;
        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            str = mRouteStringTable[mAtscAntennaType];
        } else {
            int dtvRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
            if ((dtvRouteIndex >= 0)
                    && (dtvRouteIndex < TvChannelManager.DTV_ROUTE_INDEX_MAX_COUNT)) {
                str = mRouteStringTable[dtvRouteIndex];
            } else {
                /*
                 * This case should not happened. current dtv route should not
                 * out of bound if get a out of bound route, get next valid
                 * route index to show.
                 */
                dtvRouteIndex = getNextRouteIndex();
                str = mRouteStringTable[dtvRouteIndex];
            }
        }
        return str;
    }

    private void fillRouteTable() {
        TvTypeInfo tvinfo = TvCommonManager.getInstance().getTvInfo();
        mRouteEnableTable = new boolean[tvinfo.routePath.length];
        mRouteStringTable = new String[tvinfo.routePath.length];
        for (int i = 0; i < tvinfo.routePath.length; i++) {
            if (TvChannelManager.TV_ROUTE_NONE == tvinfo.routePath[i]) {
                mRouteEnableTable[i] = false;
                mRouteStringTable[i] = "";
            } else {
                mRouteEnableTable[i] = true;
                String tmpStr = "";
                switch (tvinfo.routePath[i]) {
                    case TvChannelManager.TV_ROUTE_DVBT:
                        tmpStr = "DVB-T";
                        break;
                    case TvChannelManager.TV_ROUTE_DVBC:
                        tmpStr = "DVB-C";
                        break;
                    case TvChannelManager.TV_ROUTE_DVBS:
                        tmpStr = "DVB-S";
                        break;
                    case TvChannelManager.TV_ROUTE_DTMB:
                        tmpStr = "DTMB";
                        break;
                    case TvChannelManager.TV_ROUTE_DVBT2:
                        tmpStr = "DVB-T2";
                        break;
                    case TvChannelManager.TV_ROUTE_DVBS2:
                        tmpStr = "DVB-S2";
                        break;
                    default:
                        tmpStr = "UnKnown";
                        Log.e(TAG, "Can not mapping route type");
                }
                mRouteStringTable[i] = tmpStr;
            }
        }
    }

    private int getNextRouteIndex() {
        // find next enabled dtv route
        int currentRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
        do {
            currentRouteIndex += 1;
            currentRouteIndex %= TvChannelManager.DTV_ROUTE_INDEX_MAX_COUNT;
        } while (mRouteEnableTable[currentRouteIndex] == false);
        return currentRouteIndex;
    }

    private int getPreviousRouteIndex() {
        // find previous enabled dtv route
        int currentRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
        do {
            currentRouteIndex -= 1;
            currentRouteIndex += TvChannelManager.DTV_ROUTE_INDEX_MAX_COUNT;
            currentRouteIndex %= TvChannelManager.DTV_ROUTE_INDEX_MAX_COUNT;
        } while (mRouteEnableTable[currentRouteIndex] == false);
        return currentRouteIndex;
    }
}
