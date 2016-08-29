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

package com.mstar.tv.tvplayer.ui.dtv.lnb.dvb;

import java.util.ArrayList;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.util.Constant;
import com.mstar.util.Tools;

import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.LocationInfo;
import com.mstar.android.tvapi.common.vo.UserLocationSetting;

public class LnbMotorEditorActivity extends MstarBaseActivity {

    private static final String TAG = "LnbMotorEditorActivity";

    private static final int POSITION_CMD_MOVE_STOP = 0;

    private static final int POSITION_CMD_MOVE_AUTO_WEST = 1;

    private static final int POSITION_CMD_MOVE_AUTO_EAST = 2;

    private static final int POSITION_CMD_MOVE_CONTI_WEST = 3;

    private static final int POSITION_CMD_MOVE_CONTI_EAST = 4;

    private static final int ARROW_RIGHT = 0;

    private static final int ARROW_LEFT = 1;

    private static final int LOCATIONINFO_MANUAL_SLOT = 0;

    private String mDiSEqCVersion = null;

    private String mStrOk = null;

    private String mStrStop = null;

    private String mStrEast = null;

    private String mStrWest = null;

    private String[] mStrDirection = null;

    private LinearLayout mLayoutMoveAuto = null;

    private LinearLayout mLayoutMoveContinue = null;

    private LinearLayout mLayoutMoveStep = null;

    private LinearLayout mLayoutStorePos = null;

    private LinearLayout mLayoutGotoPos = null;

    private LinearLayout mLayoutWestLimit = null;

    private LinearLayout mLayoutEastLimit = null;

    private LinearLayout mLayoutGotoRef = null;

    private LinearLayout mLayoutGotoX = null;

    private LinearLayout mLayoutDisableLimit = null;

    private LinearLayout mLayoutLocation = null;

    private LinearLayout mLayoutLongitudeDirection = null;

    private LinearLayout mLayoutLongitudeAngle = null;

    private LinearLayout mLayoutLatitudeDirection = null;

    private LinearLayout mLayoutLatitudeAngle = null;

    private TextView mTvMoveAuto = null;

    private TextView mTvMoveContinue = null;

    private TextView mTvMoveStep = null;

    private TextView mTvStorePos = null;

    private TextView mTvGotoPos = null;

    private TextView mTvWestLimit = null;

    private TextView mTvEastLimit = null;

    private TextView mTvGotoRef = null;

    private TextView mTvGotoX = null;

    private TextView mTvDisableLimit = null;

    private ComboButton mCbLocation = null;

    private ComboButton mCbLongitudeDirection = null;

    private ComboButton mCbLatitudeDirection = null;

    private SeekBarButton mSbLongitudeAngle = null;

    private SeekBarButton mSbLatitudeAngle = null;


    private String[] mLocationName = null;

    private int[] mLongitudeAngle = null;

    private int[] mLatitudeAngle = null;

    private int mOptionType = Constant.LNBOPTION_MOTOR_ACTION_INVALID;

    private int mCurrentLocationNumber = 0;

    private int mCurrentLongitudeAngle = 0;

    private int mCurrentLatitudeAngle = 0;

    private int mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;

    private AlphaAnimation alphaAnimation;

    private MotorFocusChangeListener motorFocusChangeListener = null;

    private TvDvbChannelManager mTvDvbChannelManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.lnb_motor_editor);
        mOptionType = getIntent().getIntExtra(Constant.LNBOPTION_MOTOR_ACTIONTYPE,
                Constant.LNBOPTION_MOTOR_ACTION_INVALID);
        mDiSEqCVersion = getIntent().getStringExtra(Constant.LNBMOTOR_EDITOR_DISEQC_VERSION);

        mStrOk = getResources().getString(R.string.str_lnb_motor_editor_ok);
        mStrStop = getResources().getString(R.string.str_lnb_motor_editor_stop);
        mStrEast = getResources().getString(R.string.str_lnb_motor_editor_east);
        mStrWest = getResources().getString(R.string.str_lnb_motor_editor_west);
        mStrDirection = getResources().getStringArray(R.array.str_arr_longitude_direction_option);

        mLayoutMoveAuto = (LinearLayout) findViewById(R.id.lnb_motor_editor_move_auto);
        mLayoutMoveContinue = (LinearLayout) findViewById(R.id.lnb_motor_editor_move_continue);
        mLayoutMoveStep = (LinearLayout) findViewById(R.id.lnb_motor_editor_move_step);
        mLayoutStorePos = (LinearLayout) findViewById(R.id.lnb_motor_editor_store_position);
        mLayoutGotoPos = (LinearLayout) findViewById(R.id.lnb_motor_editor_goto_position);
        mLayoutWestLimit = (LinearLayout) findViewById(R.id.lnb_motor_editor_set_west_limit);
        mLayoutEastLimit = (LinearLayout) findViewById(R.id.lnb_motor_editor_set_east_limit);
        mLayoutGotoRef = (LinearLayout) findViewById(R.id.lnb_motor_editor_goto_reference);
        mLayoutGotoX = (LinearLayout) findViewById(R.id.lnb_motor_editor_goto_x);
        mLayoutDisableLimit = (LinearLayout) findViewById(R.id.lnb_motor_editor_disable_limit);
        mLayoutLocation = (LinearLayout) findViewById(R.id.lnb_motor_editor_location);
        mLayoutLongitudeDirection = (LinearLayout) findViewById(R.id.lnb_motor_editor_longitude_direction);
        mLayoutLongitudeAngle = (LinearLayout) findViewById(R.id.lnb_motor_editor_longitude_angle);
        mLayoutLatitudeDirection = (LinearLayout) findViewById(R.id.lnb_motor_editor_latitude_direction);
        mLayoutLatitudeAngle = (LinearLayout) findViewById(R.id.lnb_motor_editor_latitude_angle);

        mTvMoveAuto = (TextView) findViewById(R.id.lnb_motor_move_auto_option);
        mTvMoveContinue = (TextView) findViewById(R.id.lnb_motor_move_continue_option);
        mTvMoveStep = (TextView) findViewById(R.id.lnb_motor_move_step_option);
        mTvStorePos = (TextView) findViewById(R.id.lnb_motor_store_position_option);
        mTvGotoPos = (TextView) findViewById(R.id.lnb_motor_goto_position_option);
        mTvWestLimit = (TextView) findViewById(R.id.lnb_motor_west_limit_option);
        mTvEastLimit = (TextView) findViewById(R.id.lnb_motor_east_limit_option);
        mTvGotoRef = (TextView) findViewById(R.id.lnb_motor_goto_reference_option);
        mTvGotoX = (TextView) findViewById(R.id.lnb_motor_goto_x_option);
        mTvDisableLimit = (TextView) findViewById(R.id.lnb_motor_disable_limit_option);

        alphaAnimation = new AlphaAnimation(1, 0);
        motorFocusChangeListener = new MotorFocusChangeListener();

        mTvDvbChannelManager = TvDvbChannelManager.getInstance();

        setupPage();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = getCurrentFocus().getId();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                backToPreviousPage();
                break;
            case KeyEvent.KEYCODE_ENTER:
                handleEnterKeyEvent(currentid, event);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                handleRightKeyEvent(currentid, event);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                handleLeftKeyEvent(currentid, event);
                break;
            default:
                Log.d(TAG, "default key code:" + keyCode);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int currentid = getCurrentFocus().getId();
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                handleEnterKeyEvent(currentid, event);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                handleRightKeyEvent(currentid, event);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                handleLeftKeyEvent(currentid, event);
                break;
            default:
                Log.d(TAG, "default key code:" + keyCode);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupPage() {
        mTvMoveAuto.setText(mStrStop);
        mTvMoveContinue.setText(mStrStop);
        mTvMoveStep.setText(mStrStop);
        mTvStorePos.setText(mStrOk);
        mTvGotoPos.setText(mStrOk);
        mTvWestLimit.setText(mStrOk);
        mTvEastLimit.setText(mStrOk);
        mTvGotoRef.setText(mStrOk);
        mTvGotoX.setText(mStrOk);
        mTvDisableLimit.setText(mStrOk);

        switch (mOptionType) {
            case Constant.LNBOPTION_MOTOR_ACTION_POSITION:
                mLayoutWestLimit.setVisibility(View.GONE);
                mLayoutEastLimit.setVisibility(View.GONE);
                if (mDiSEqCVersion.equals(Constant.LNBMOTOR_EDITOR_DISEQC_1_2)) {
                    mLayoutGotoX.setVisibility(View.GONE);
                }
                mLayoutDisableLimit.setVisibility(View.GONE);
                mLayoutLocation.setVisibility(View.GONE);
                mLayoutLongitudeDirection.setVisibility(View.GONE);
                mLayoutLongitudeAngle.setVisibility(View.GONE);
                mLayoutLatitudeDirection.setVisibility(View.GONE);
                mLayoutLatitudeAngle.setVisibility(View.GONE);
                mLayoutMoveAuto.setOnFocusChangeListener(motorFocusChangeListener);
                mLayoutMoveContinue.setOnFocusChangeListener(motorFocusChangeListener);
                break;
            case Constant.LNBOPTION_MOTOR_ACTION_LIMIT:
                mLayoutMoveAuto.setVisibility(View.GONE);
                mLayoutStorePos.setVisibility(View.GONE);
                mLayoutGotoPos.setVisibility(View.GONE);
                mLayoutGotoX.setVisibility(View.GONE);
                mLayoutLocation.setVisibility(View.GONE);
                mLayoutLongitudeDirection.setVisibility(View.GONE);
                mLayoutLongitudeAngle.setVisibility(View.GONE);
                mLayoutLatitudeDirection.setVisibility(View.GONE);
                mLayoutLatitudeAngle.setVisibility(View.GONE);
                mLayoutMoveContinue.setOnFocusChangeListener(motorFocusChangeListener);
                break;
            case Constant.LNBOPTION_MOTOR_ACTION_LOCATION:
                mLayoutMoveAuto.setVisibility(View.GONE);
                mLayoutMoveContinue.setVisibility(View.GONE);
                mLayoutMoveStep.setVisibility(View.GONE);
                mLayoutStorePos.setVisibility(View.GONE);
                mLayoutGotoPos.setVisibility(View.GONE);
                mLayoutWestLimit.setVisibility(View.GONE);
                mLayoutEastLimit.setVisibility(View.GONE);
                mLayoutGotoRef.setVisibility(View.GONE);
                mLayoutGotoX.setVisibility(View.GONE);
                mLayoutDisableLimit.setVisibility(View.GONE);

                // get current location number
                UserLocationSetting locSetting = mTvDvbChannelManager.getUserLocationSetting();
                mCurrentLocationNumber = locSetting.locationNo;
                mCurrentLongitudeAngle = locSetting.manualLongitude;
                mCurrentLatitudeAngle = locSetting.manualLatitude;

                int count = mTvDvbChannelManager.getUserLocationCount();
                mLocationName = new String[count];
                mLongitudeAngle = new int[count];
                mLatitudeAngle = new int[count];
                for (int i = 0; i < count; i++) {
                    LocationInfo locInfo = mTvDvbChannelManager.getUserLocationInfo(i);
                    if (LOCATIONINFO_MANUAL_SLOT == i) {
                        // manual case
                        mLocationName[i] = locInfo.locationName;
                        mLongitudeAngle[i] = mCurrentLongitudeAngle;
                        mLatitudeAngle[i] = mCurrentLatitudeAngle;
                    } else {
                        // location info from tvservice
                        mLocationName[i] = locInfo.locationName;
                        mLongitudeAngle[i] = locInfo.longitude;
                        mLatitudeAngle[i] = locInfo.latitude;
                    }
                }

                mCbLocation = new ComboButton(this, mLocationName, R.id.lnb_motor_editor_location,
                        0, 1, false) {
                    @Override
                    public void doUpdate() {
                        setLocationOptionFocusable();
                        freshUiWhenLocationChange();
                    }
                };
                mCbLongitudeDirection = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_longitude_direction_option),
                        R.id.lnb_motor_editor_longitude_direction, 0, 1, false) {
                };
                mCbLatitudeDirection = new ComboButton(this, getResources().getStringArray(
                        R.array.str_arr_latitude_direction_option),
                        R.id.lnb_motor_editor_latitude_direction, 0, 1, false) {
                };
                mSbLongitudeAngle = new SeekBarButton(this, R.id.lnb_motor_editor_longitude_angle,
                        1, false, 1) {
                };
                mSbLatitudeAngle = new SeekBarButton(this, R.id.lnb_motor_editor_latitude_angle, 1,
                        false, 1) {
                };
                freshUiWhenLocationChange();
                break;
            default:
                break;
        }
    }

    private void handleRightKeyEvent(int viewId, KeyEvent event) {
        switch (viewId) {
            case R.id.lnb_motor_editor_move_auto:
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (POSITION_CMD_MOVE_STOP == mCurrentPositionCommand) {
                        // stop case , start to flicker right arrow and send move command to TvService
                        mTvMoveAuto.setText(mStrEast);
                        flickerArrowImage(mLayoutMoveAuto, ARROW_RIGHT, true);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_AUTO_EAST;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_CONTINUE_EAST);
                    } else if (POSITION_CMD_MOVE_AUTO_WEST == mCurrentPositionCommand) {
                        // west case , stop to flicker left arrow and send stop command to TvService
                        mTvMoveAuto.setText(mStrStop);
                        flickerArrowImage(mLayoutMoveAuto, ARROW_LEFT, false);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                    }
                }
                break;
            case R.id.lnb_motor_editor_move_continue:
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (POSITION_CMD_MOVE_STOP == mCurrentPositionCommand) {
                        // stop case , start to flicker right arrow and send move command to TvService
                        mTvMoveContinue.setText(mStrEast);
                        flickerArrowImage(mLayoutMoveContinue, ARROW_RIGHT, true);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_CONTI_EAST;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_CONTINUE_EAST);
                    } else if (POSITION_CMD_MOVE_CONTI_WEST == mCurrentPositionCommand) {
                        // west case , stop to flicker left arrow and send stop command to TvService
                        mTvMoveContinue.setText(mStrStop);
                        flickerArrowImage(mLayoutMoveContinue, ARROW_LEFT, false);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                    }
                }
                break;
            case R.id.lnb_motor_editor_move_step:
                // move east step 1
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvMoveStep.setText(mStrEast);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_STEP_EAST);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvMoveStep.setText(mStrStop);
                }
                break;
            default:
                Log.w(TAG, "fall into a wrong case");
                break;
        }
    }

    private void handleLeftKeyEvent(int viewId, KeyEvent event) {
        switch(viewId) {
            case R.id.lnb_motor_editor_move_auto:
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (POSITION_CMD_MOVE_STOP == mCurrentPositionCommand) {
                        // stop case , start to flicker right arrow and send move command to TvService
                        mTvMoveAuto.setText(mStrWest);
                        flickerArrowImage(mLayoutMoveAuto, ARROW_LEFT, true);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_AUTO_WEST;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_CONTINUE_WEST);
                    } else if (POSITION_CMD_MOVE_AUTO_EAST == mCurrentPositionCommand) {
                        // west case , stop to flicker left arrow and send stop command to TvService
                        mTvMoveAuto.setText(mStrStop);
                        flickerArrowImage(mLayoutMoveAuto, ARROW_RIGHT, false);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                    }
                }
                break;
            case R.id.lnb_motor_editor_move_continue:
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (POSITION_CMD_MOVE_STOP == mCurrentPositionCommand) {
                        // stop case , start to flicker right arrow and send move command to TvService
                        mTvMoveContinue.setText(mStrWest);
                        flickerArrowImage(mLayoutMoveContinue, ARROW_LEFT, true);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_CONTI_WEST;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_CONTINUE_WEST);
                    } else if (POSITION_CMD_MOVE_CONTI_EAST == mCurrentPositionCommand) {
                        // west case , stop to flicker left arrow and send stop command to TvService
                        mTvMoveContinue.setText(mStrStop);
                        flickerArrowImage(mLayoutMoveContinue, ARROW_RIGHT, false);
                        mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                        mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                    }
                }
                break;
            case R.id.lnb_motor_editor_move_step:
                // move west step 1
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvMoveStep.setText(mStrWest);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_STEP_WEST);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvMoveStep.setText(mStrStop);
                }
                break;
            default:
                Log.w(TAG, "fall into a wrong case");
                break;
        }
    }

    private void handleEnterKeyEvent(int viewId, KeyEvent event) {
        switch (viewId) {
            case R.id.lnb_motor_editor_store_position:
                // store position
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvStorePos.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_SAVE_SAT_POSITION);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvStorePos.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_goto_position:
                // go to position
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvGotoPos.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_GOTO_SAT_POSITION);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvGotoPos.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_set_west_limit:
                // set west limit
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvWestLimit.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_SET_WEST_LIMIT);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvWestLimit.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_set_east_limit:
                // set east limit
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvEastLimit.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_SET_EAST_LIMIT);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvEastLimit.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_goto_reference:
                // go to reference position
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvGotoRef.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_GOTO_REF_POINT);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvGotoRef.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_goto_x:
                // go to x
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvGotoX.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_GOTO_X);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvGotoX.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_disable_limit:
                // disable limit
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    mTvDisableLimit.setVisibility(View.VISIBLE);
                    mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_DISABLE_LIMIT);
                } else if (KeyEvent.ACTION_UP == event.getAction()) {
                    mTvDisableLimit.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lnb_motor_editor_location:
            case R.id.lnb_motor_editor_longitude_direction:
            case R.id.lnb_motor_editor_latitude_direction:
            case R.id.lnb_motor_editor_longitude_angle:
            case R.id.lnb_motor_editor_latitude_angle:
                // update current location information
                mCurrentLocationNumber = mCbLocation.getIdx();
                if (TvDvbChannelManager.LONGITUDE_DIRECTION_EAST == mCbLongitudeDirection.getIdx()) {
                    mCurrentLongitudeAngle = mSbLongitudeAngle.getProgressInt();
                } else if (TvDvbChannelManager.LONGITUDE_DIRECTION_WEST == mCbLongitudeDirection.getIdx()) {
                    mCurrentLongitudeAngle = -mSbLongitudeAngle.getProgressInt();
                }
                if (TvDvbChannelManager.LATITUDE_DIRECTION_NORTH == mCbLatitudeDirection.getIdx()) {
                    mCurrentLatitudeAngle = mSbLatitudeAngle.getProgressInt();
                } else if (TvDvbChannelManager.LATITUDE_DIRECTION_SOUTH == mCbLatitudeDirection.getIdx()) {
                    mCurrentLatitudeAngle = -mSbLatitudeAngle.getProgressInt();
                }
                Log.d(TAG, "longitude:"+mCurrentLongitudeAngle + " latitude:"+mCurrentLatitudeAngle);
                // set user location setting
                UserLocationSetting locSetting = new UserLocationSetting();
                locSetting.locationNo = mCurrentLocationNumber;
                locSetting.manualLongitude = mCurrentLongitudeAngle;
                locSetting.manualLatitude = mCurrentLatitudeAngle;
                mTvDvbChannelManager.setUserLocationSetting(locSetting);
                // update manual angle information
                mLongitudeAngle[LOCATIONINFO_MANUAL_SLOT] = mCurrentLongitudeAngle;
                mLatitudeAngle[LOCATIONINFO_MANUAL_SLOT] = mCurrentLatitudeAngle;
                break;
        }
    }

    private void flickerArrowImage(LinearLayout layout, int arrowDirection, boolean enable) {
        int arrowId = 0;
        if (ARROW_RIGHT == arrowDirection) {
            arrowId = 3;
        } else if (ARROW_LEFT == arrowDirection) {
            arrowId = 1;
        } else {
            Log.w(TAG, "use default arrow id : 0 !!!");
        }
        ImageView iv = (ImageView) layout.getChildAt(arrowId);
        if (enable) {
            alphaAnimation.setDuration(300);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            iv.setAnimation(alphaAnimation);
            alphaAnimation.startNow();
            mLayoutMoveAuto.invalidate();
        } else {
            iv.clearAnimation();
        }
    }

    private class MotorFocusChangeListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // clear motor position command while lose focus
            if (!hasFocus) {
                ImageView iv = null;
                switch (v.getId()) {
                    case R.id.lnb_motor_editor_move_auto:
                        if(POSITION_CMD_MOVE_AUTO_EAST == mCurrentPositionCommand) {
                            flickerArrowImage(mLayoutMoveAuto, ARROW_RIGHT, false);
                            mTvMoveAuto.setText(mStrStop);
                            mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                            mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                        } else if (POSITION_CMD_MOVE_AUTO_WEST == mCurrentPositionCommand) {
                            flickerArrowImage(mLayoutMoveAuto, ARROW_LEFT, false);
                            mTvMoveAuto.setText(mStrStop);
                            mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                            mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                        }
                        break;
                    case R.id.lnb_motor_editor_move_continue:
                        if(POSITION_CMD_MOVE_CONTI_EAST == mCurrentPositionCommand) {
                            flickerArrowImage(mLayoutMoveContinue, ARROW_RIGHT, false);
                            mTvMoveContinue.setText(mStrStop);
                            mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                            mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                        } else if (POSITION_CMD_MOVE_CONTI_WEST == mCurrentPositionCommand) {
                            flickerArrowImage(mLayoutMoveContinue, ARROW_LEFT, false);
                            mTvMoveContinue.setText(mStrStop);
                            mCurrentPositionCommand = POSITION_CMD_MOVE_STOP;
                            mTvDvbChannelManager.sendDiSEqCMotorCommand(TvDvbChannelManager.DISEQC_MOTOR_CMD_HALTMOTOR);
                        }
                        break;
                    default:
                        Log.e(TAG, "this is a error id in page motor position !!");
                        break;
                }
            }
        }
    };

    private void backToPreviousPage() {
        Intent clickIntent = new Intent(TvIntent.ACTION_LNBOPTION);
        clickIntent
                .putExtra(Constant.LNBOPTION_PAGETYPE, Constant.LNBOPTION_PAGETYPE_MOTOR);
        // setup focus after back to LnbOption
        if (null == mDiSEqCVersion) {
            clickIntent.putExtra(Constant.LNBOPTION_MOTOR_FOCUS,
                    Constant.LNBOPTION_MOTOR_NONE);
        } else if (mDiSEqCVersion.equals(Constant.LNBMOTOR_EDITOR_DISEQC_1_2)) {
            clickIntent.putExtra(Constant.LNBOPTION_MOTOR_FOCUS,
                    Constant.LNBOPTION_MOTOR_1_2);
        } else if (mDiSEqCVersion.equals(Constant.LNBMOTOR_EDITOR_DISEQC_1_3)) {
            clickIntent.putExtra(Constant.LNBOPTION_MOTOR_FOCUS,
                    Constant.LNBOPTION_MOTOR_1_3);
        }
        startActivity(clickIntent);
    }

    private void setLocationOptionFocusable() {
        // index = 0 means it is maual mode
        if (LOCATIONINFO_MANUAL_SLOT == mCbLocation.getIdx()) {
            mCbLongitudeDirection.setEnable(true);
            mSbLongitudeAngle.setEnable(true);
            mCbLatitudeDirection.setEnable(true);
            mSbLatitudeAngle.setEnable(true);
            mCbLongitudeDirection.setFocusable(true);
            mSbLongitudeAngle.setFocusable(true);
            mCbLatitudeDirection.setFocusable(true);
            mSbLatitudeAngle.setFocusable(true);
        } else {
            mCbLongitudeDirection.setEnable(false);
            mSbLongitudeAngle.setEnable(false);
            mCbLatitudeDirection.setEnable(false);
            mSbLatitudeAngle.setEnable(false);
            mCbLongitudeDirection.setFocusable(false);
            mSbLongitudeAngle.setFocusable(false);
            mCbLatitudeDirection.setFocusable(false);
            mSbLatitudeAngle.setFocusable(false);
        }
    }

    private void freshUiWhenLocationChange() {
        int idx = mCbLocation.getIdx();
        // longitude > 0 : east ; longitude < 0 : west
        if (mLongitudeAngle[idx] > 0) {
            mCbLongitudeDirection.setIdx(TvDvbChannelManager.LONGITUDE_DIRECTION_EAST);
            mSbLongitudeAngle.setProgressInt(mLongitudeAngle[idx]);
        } else {
            int longitude = -mLongitudeAngle[idx];
            mCbLongitudeDirection.setIdx(TvDvbChannelManager.LONGITUDE_DIRECTION_WEST);
            mSbLongitudeAngle.setProgressInt(longitude);
        }
        // latitude > 0 : north ; latitude < 0 : south
        if (mLatitudeAngle[idx] > 0) {
            mCbLatitudeDirection.setIdx(TvDvbChannelManager.LATITUDE_DIRECTION_NORTH);
            mSbLatitudeAngle.setProgressInt(mLatitudeAngle[idx]);
        } else {
            int latitude = -mLatitudeAngle[idx];
            mCbLatitudeDirection.setIdx(TvDvbChannelManager.LATITUDE_DIRECTION_SOUTH);
            mSbLatitudeAngle.setProgressInt(latitude);
        }
    }
}
