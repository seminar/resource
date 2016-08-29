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

package com.mstar.tv.tvplayer.ui.dtv.parental.dvb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvS3DManager;
import com.mstar.android.tv.TvFactoryManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.tuning.atsc.AtscATVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.ATVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.AutoTuneOptionActivity;
import com.mstar.tv.tvplayer.ui.tuning.DTVManualTuning;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;

@SuppressLint("CommitPrefEdits")
public class CheckParentalPwdActivity extends MstarBaseActivity {

    private static final String TAG = "CheckParentalPwdActivity";

    private EditText check1;

    private EditText check2;

    private EditText check3;

    private EditText check4;

    private int list = -1;

    private Intent intent;

    private TvChannelManager mTvChannelManager = null;

    public static CheckParentalPwdActivity instance = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreat CheckParentalPwdActivity");
        setContentView(R.layout.check_parental_pwd);
        mTvChannelManager = TvChannelManager.getInstance();
        list = getIntent().getIntExtra("list", -1);
        findView();
        instance = this;
    }

    private void findView() {
        check1 = (EditText) findViewById(R.id.check_pwd1);
        check2 = (EditText) findViewById(R.id.check_pwd2);
        check3 = (EditText) findViewById(R.id.check_pwd3);
        check4 = (EditText) findViewById(R.id.check_pwd4);
        setMyEditListener(check1, check2);
        setMyEditListener(check2, check3);
        setMyEditListener(check3, check4);
        setMyEditListener(check4, check1);
        // BugFix modify: Mantis:0387066 Fix
        // last input displays as normal
        // input type instead of password
        // input type
        // check4,null
    }

    private void setMyEditListener(EditText edittext, final View view) {
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (null != view) {
                    view.requestFocus();
                    if ((EditText) findViewById(R.id.check_pwd1) == view) {
                        checkpsw();
                    }
                }
            }
        });
    }

    private void checkpsw() {
        int c1, c2, c3, c4, checkpwd;
        c1 = c2 = c3 = c4 = 0;
        boolean isValid = true;
        if((check1.getText().toString().equals("")) && (check2.getText().toString().equals(""))
            && (check3.getText().toString().equals("")) && (check4.getText().toString().equals(""))) {
            return;
        }
        try {
            c1 = Integer.parseInt(check1.getText().toString());
            c2 = Integer.parseInt(check2.getText().toString());
            c3 = Integer.parseInt(check3.getText().toString());
            c4 = Integer.parseInt(check4.getText().toString());
        } catch (Exception e) {
            isValid = false;
        }

        if (false == isValid) {
            Toast.makeText(this, getResources().getString(R.string.str_check_password_input_number), Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(TvIntent.MAINMENU);
            intent.putExtra("currentPage", MainMenuActivity.OPTION_PAGE);
            startActivity(intent);
            finish();
            return;
        }

        checkpwd = getpassword(c1, c2, c3, c4);
        intent = new Intent();
        boolean result = (checkpwd == TvParentalControlManager.getInstance().getParentalPassword());
        if (result) {
            Log.d(TAG, "CheckParentalPwdActivity List= " + list);
            switch (list) {
                case 0:
                    intent.setAction(TvIntent.ACTION_AUTOTUNING_OPTION);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    finish();
                    break;
                case 1:
                    intent.setClass(this, DTVManualTuning.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
                        intent.setClass(this, AtscATVManualTuning.class);
                    } else {
                        intent.setClass(this, ATVManualTuning.class);
                    }
                    startActivity(intent);
                    finish();
                    break;
                case 3:
                    TvFactoryManager tvFactoryManager = TvFactoryManager.getInstance();
                    tvFactoryManager.restoreToDefault();
                    tvFactoryManager.setEnvironmentPowerOnMusicVolume((int) 0xFF);

                    int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
                    if (currInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
                        TvS3DManager.getInstance().setDisplayFormatForUI(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                        int dtmbRouteIndex = mTvChannelManager.getSpecificDtvRouteIndex(TvChannelManager.TV_ROUTE_DTMB);
                        TvDvbChannelManager.getInstance().setDtvAntennaType(dtmbRouteIndex);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TvCommonManager.getInstance().rebootSystem("reboot");
                    /*
                     * Added by gerard.jiang for "0386249" in 2013/04/28. Add
                     * reboot flag
                     */
                    RootActivity.setRebootFlag(true);
                    /***** Ended by gerard.jiang 2013/04/28 *****/
                case 4:
                    TvParentalControlManager.getInstance().unlockChannel();
                    Intent intent = new Intent(this, RootActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("result", true);
                    intent.putExtras(bundle);
                    setResult(Constant.CHANNEL_LOCK_RESULT_CODE, intent);
                    break;
                case 5:
                    SharedPreferences share = getSharedPreferences("temporary_parentalcontrol",
                            Activity.MODE_PRIVATE);
                    int channelnum1 = share.getInt("rating_lock_num", -1);
                    short channeltype = (short) share.getInt("rating_lock_type", -1);
                    if ((channelnum1 != -1) && (channeltype != (short) -1)) {
                        mTvChannelManager.setProgramAttribute(TvChannelManager.PROGRAM_ATTRIBUTE_LOCK, channelnum1,
                                channeltype, 0x00, false);
                        Editor editor = share.edit();
                        editor.putInt("rating_lock_num", -1);
                        editor.putInt("rating_lock_type", -1);
                        editor.commit();
                    }
                    break;
                case 6:
                    SharedPreferences share2 = getSharedPreferences("menu_check_pwd",
                            Activity.MODE_PRIVATE);
                    Editor editor = share2.edit();
                    editor.putBoolean("pwd_ok", true);
                    editor.commit();
                    Intent intent2 = new Intent(TvIntent.MAINMENU);
                    intent2.putExtra("currentPage", MainMenuActivity.LOCK_PAGE);
                    startActivity(intent2);
                    finish();
                default:
                    break;
            }
            finish();
        } else {
            // check fail
            Toast.makeText(this, getResources().getString(R.string.str_check_password_incorrect), Toast.LENGTH_LONG).show();
            check1.setText("");
            check2.setText("");
            check3.setText("");
            check4.setText("");
        }
    }

    private int getpassword(int i, int j, int k, int x) {
        return i * 1000 + j * 100 + k * 10 + x;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*
         * Added by gerard.jiang for "0387023" & "0387027" in 2013/04/26.
         */
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                startActivity(new Intent(TvIntent.MAINMENU));
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_CHANNEL_UP:
                if (mTvChannelManager.programUp()) {
                    finish();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_CHANNEL_DOWN:
                if (mTvChannelManager.programDown()) {
                    finish();
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            default:
                // NULL
                /***** Ended by gerard.jiang 2013/04/26 *****/
        }
        return super.onKeyDown(keyCode, event);
    }

    /* add by avery.yan for fix pr:0396132 */

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.d(TAG, "onDestroy CheckParentaPwd");
        super.onDestroy();
        instance = null;
    }
    /* end add by avery.yan for fix pr:0396132 */
}
