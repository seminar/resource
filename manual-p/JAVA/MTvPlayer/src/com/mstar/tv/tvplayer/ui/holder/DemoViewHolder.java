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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tv.TvS3DManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;

public class DemoViewHolder {
    private final static String TAG = "DemoViewHolder";

    public Activity demoActivity;

    public int focusedid = 0x00000000;

    protected TextView text_demo_mwe_val;

    protected TextView text_demo_dbc_val;

    protected TextView text_demo_dlc_val;

    protected TextView text_demo_dcc_val;

    protected TextView text_demo_nr_val;

    protected TextView text_demo_uclear_val;

    protected LinearLayout linear_demo;

    protected LinearLayout linear_demo_mwe;

    protected LinearLayout linear_demo_dbc;

    protected LinearLayout linear_demo_dlc;

    protected LinearLayout linear_demo_dcc;

    protected LinearLayout linear_demo_nr;

    protected LinearLayout linear_demo_uclear;

    public final static int MAX_CHILD_IDX = 5;

    /* this mapping is reference to str_arr_mwe_text in string.xml */
    private final static int IDX_DEMO_MODE_SQUARE = 6;

    private int mwe_index = 0;

    private int dbc_index = 0;

    private int dlc_index = 0;

    private int dcc_index = 0;

    private int nr_index = 0;

    private int uclear_index = 0;

    private String[] stronoff;

    private String[] strMEWtypes;

    private String[] str3dNRtypes;

    public DemoViewHolder(Activity activity) {
        this.demoActivity = activity;
    }

    /*--------------for DemoActivity-----------------------*/
    public void findViews() {
        text_demo_mwe_val = (TextView) demoActivity.findViewById(R.id.textview_demo_mwe_val);
        text_demo_dbc_val = (TextView) demoActivity.findViewById(R.id.textview_demo_dbc_val);
        text_demo_dlc_val = (TextView) demoActivity.findViewById(R.id.textview_demo_dlc_val);
        text_demo_dcc_val = (TextView) demoActivity.findViewById(R.id.textview_demo_dcc_val);
        text_demo_nr_val = (TextView) demoActivity.findViewById(R.id.textview_demo_nr_val);
        text_demo_uclear_val = (TextView) demoActivity.findViewById(R.id.textview_demo_uclear_val);
        linear_demo_mwe = (LinearLayout) demoActivity.findViewById(R.id.linearlayout_demo_mwe);
        linear_demo_dbc = (LinearLayout) demoActivity.findViewById(R.id.linearlayout_demo_dbc);
        linear_demo_dlc = (LinearLayout) demoActivity.findViewById(R.id.linearlayout_demo_dlc);
        linear_demo_dcc = (LinearLayout) demoActivity.findViewById(R.id.linearlayout_demo_dcc);
        linear_demo_nr = (LinearLayout) demoActivity.findViewById(R.id.linearlayout_demo_nr);
        linear_demo_uclear = (LinearLayout) demoActivity
                .findViewById(R.id.linearlayout_demo_uclear);
        linear_demo = (LinearLayout) demoActivity.findViewById(R.id.linearlayout_demo);
        stronoff = demoActivity.getResources().getStringArray(R.array.str_arr_demo_onoff);
        strMEWtypes = demoActivity.getResources().getStringArray(R.array.str_arr_mwe_text);
        str3dNRtypes = demoActivity.getResources().getStringArray(R.array.str_arr_pic_imgnoisereduction_vals);
        text_demo_mwe_val.setText(stronoff[mwe_index]);
        text_demo_dbc_val.setText(stronoff[dbc_index]);
        text_demo_dcc_val.setText(stronoff[dcc_index]);
        text_demo_nr_val.setText(str3dNRtypes[nr_index]);
        text_demo_dlc_val.setText(stronoff[dlc_index]);
        text_demo_uclear_val.setText(stronoff[uclear_index]);
        setOnClickLisenters();
        setOnFocusChangeListeners();
        setOnTouchListeners();
        preinit();
        repaintComponents();

    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        int currentid = -1;
        if (demoActivity.getCurrentFocus() != null) {
            currentid = demoActivity.getCurrentFocus().getId();
        }
        if (focusedid != currentid) {
            MainMenuActivity.selectedsStatusForDemo = 0x00000000;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_demo_mwe:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00000001) {
                            if (mwe_index == (strMEWtypes.length - 1))
                                mwe_index = TvPictureManager.MWE_DEMO_MODE_OFF;
                            else
                                mwe_index++;

                            if (TvS3DManager.getInstance() != null) {
                                TvS3DManager tvS3DManager = TvS3DManager.getInstance();
                                tvS3DManager.get3dDisplayFormat();
                                if ((tvS3DManager.get3dDisplayFormat() != TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE)
                                        && (mwe_index != TvPictureManager.MWE_DEMO_MODE_OFF)) {
                                    tvS3DManager
                                            .set3dDisplayFormat(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                                    Toast toast = Toast.makeText(demoActivity,
                                            R.string.str_demo_toast, 5);
                                    toast.show();
                                }
                            }
                            text_demo_mwe_val.setText(strMEWtypes[mwe_index]);
                            if (TvPictureManager.getInstance() != null) {
                                TvPictureManager.getInstance().setMWEDemoMode(mappingIdxToMweType(mwe_index));
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_dbc:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00000010) {
                            if (dbc_index == 1)
                                dbc_index = 0;
                            else
                                dbc_index++;
                            text_demo_dbc_val.setText(stronoff[dbc_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (dbc_index == 1) {
                                    TvPictureManager.getInstance().enableDbc();
                                } else {
                                    TvPictureManager.getInstance().disableDbc();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_dlc:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00000100) {
                            if (dlc_index == 1)
                                dlc_index = 0;
                            else
                                dlc_index++;
                            text_demo_dlc_val.setText(stronoff[dlc_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (dlc_index == 1) {
                                    TvPictureManager.getInstance().enableDlc();
                                } else {
                                    TvPictureManager.getInstance().disableDlc();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_dcc:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00001000) {
                            if (dcc_index == 1)
                                dcc_index = 0;
                            else
                                dcc_index++;
                            text_demo_dcc_val.setText(stronoff[dcc_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (dcc_index == 1) {
                                    TvPictureManager.getInstance().enableDcc();
                                } else {
                                    TvPictureManager.getInstance().disableDcc();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_nr:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00010000) {
                            if (nr_index == TvPictureManager.NR_MODE_AUTO)
                                nr_index = TvPictureManager.NR_MODE_OFF;
                            else
                                nr_index++;
                            text_demo_nr_val.setText(str3dNRtypes[nr_index]);

                            if (TvPictureManager.getInstance() != null) {
                                TvPictureManager.getInstance().setNoiseReduction(nr_index);
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_uclear:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00100000) {
                            if (uclear_index == 1)
                                uclear_index = 0;
                            else
                                uclear_index++;
                            text_demo_uclear_val.setText(stronoff[uclear_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (uclear_index == 1) {
                                    TvPictureManager.getInstance().setUClearStatus(true);
                                } else {
                                    TvPictureManager.getInstance().setUClearStatus(false);
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_demo_mwe:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00000001) {
                            if (mwe_index == 0)
                                mwe_index = (strMEWtypes.length - 1);
                            else
                                mwe_index--;
                            if (TvS3DManager.getInstance() != null) {
                                TvS3DManager tvS3DManager = TvS3DManager.getInstance();
                                tvS3DManager.get3dDisplayFormat();
                                if ((tvS3DManager.get3dDisplayFormat() != TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE)
                                        && (mwe_index != TvPictureManager.MWE_DEMO_MODE_OFF)) {
                                    tvS3DManager.set3dDisplayFormat(TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
                                    Toast toast = Toast.makeText(demoActivity,
                                            R.string.str_demo_toast, 5);
                                    toast.show();
                                }
                            }
                            text_demo_mwe_val.setText(strMEWtypes[mwe_index]);
                            if (TvPictureManager.getInstance() != null) {
                                TvPictureManager.getInstance().setMWEDemoMode(mappingIdxToMweType(mwe_index));
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_dbc:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00000010) {
                            if (dbc_index == 0)
                                dbc_index = 1;
                            else
                                dbc_index--;
                            text_demo_dbc_val.setText(stronoff[dbc_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (dbc_index == 1) {
                                    TvPictureManager.getInstance().enableDbc();
                                } else {
                                    TvPictureManager.getInstance().disableDbc();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_dlc:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00000100) {
                            if (dlc_index == 0)
                                dlc_index = 1;
                            else
                                dlc_index--;
                            text_demo_dlc_val.setText(stronoff[dlc_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (dlc_index == 1) {
                                    TvPictureManager.getInstance().enableDlc();
                                } else {
                                    TvPictureManager.getInstance().disableDlc();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_dcc:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00001000) {
                            if (dcc_index == 0)
                                dcc_index = 1;
                            else
                                dcc_index--;
                            text_demo_dcc_val.setText(stronoff[dcc_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (dcc_index == 1) {
                                    TvPictureManager.getInstance().enableDcc();
                                } else {
                                    TvPictureManager.getInstance().disableDcc();
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_nr:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00010000) {
                            if (nr_index == TvPictureManager.NR_MODE_OFF)
                                nr_index = TvPictureManager.NR_MODE_AUTO;
                            else
                                nr_index--;
                            text_demo_nr_val.setText(str3dNRtypes[nr_index]);

                            if (TvPictureManager.getInstance() != null) {
                                TvPictureManager.getInstance().setNoiseReduction(nr_index);
                            }
                        }
                        focusedid = currentid;
                        break;
                    case R.id.linearlayout_demo_uclear:
                        if (MainMenuActivity.selectedsStatusForDemo == 0x00100000) {
                            if (uclear_index == 0)
                                uclear_index = 1;
                            else
                                uclear_index--;
                            text_demo_uclear_val.setText(stronoff[uclear_index]);

                            if (TvPictureManager.getInstance() != null) {
                                if (uclear_index == 1) {
                                    TvPictureManager.getInstance().setUClearStatus(true);
                                } else {
                                    TvPictureManager.getInstance().setUClearStatus(false);
                                }
                            }
                        }
                        focusedid = currentid;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentid = demoActivity.getCurrentFocus().getId();
                if (focusedid != currentid)
                    MainMenuActivity.selectedsStatusForDemo = 0x00000000;
                // TODO Auto-generated method stub
                switch (currentid) {
                    case R.id.linearlayout_demo_mwe:
                        MainMenuActivity.selectedsStatusForDemo = 0x00000001;
                        focusedid = R.id.linearlayout_demo_mwe;
                        linear_demo_mwe.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_demo_mwe.getChildAt(3).setVisibility(View.VISIBLE);
                        System.out.println("---2--linear_demo_mwe==-" + linear_demo_mwe);
                        break;
                    case R.id.linearlayout_demo_dbc:
                        MainMenuActivity.selectedsStatusForDemo = 0x00000010;
                        focusedid = R.id.linearlayout_demo_dbc;
                        linear_demo_dbc.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_demo_dbc.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_demo_dlc:
                        MainMenuActivity.selectedsStatusForDemo = 0x00000100;
                        focusedid = R.id.linearlayout_demo_dlc;
                        linear_demo_dlc.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_demo_dlc.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_demo_dcc:
                        MainMenuActivity.selectedsStatusForDemo = 0x00001000;
                        focusedid = R.id.linearlayout_demo_dcc;
                        linear_demo_dcc.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_demo_dcc.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_demo_nr:
                        MainMenuActivity.selectedsStatusForDemo = 0x00010000;
                        focusedid = R.id.linearlayout_demo_nr;
                        linear_demo_nr.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_demo_nr.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    case R.id.linearlayout_demo_uclear:
                        MainMenuActivity.selectedsStatusForDemo = 0x00100000;
                        focusedid = R.id.linearlayout_demo_uclear;
                        linear_demo_uclear.getChildAt(0).setVisibility(View.VISIBLE);
                        linear_demo_uclear.getChildAt(3).setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        };
        linear_demo_mwe.setOnClickListener(listener);
        linear_demo_dbc.setOnClickListener(listener);
        linear_demo_dlc.setOnClickListener(listener);
        linear_demo_dcc.setOnClickListener(listener);
        linear_demo_nr.setOnClickListener(listener);
        linear_demo_uclear.setOnClickListener(listener);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener FocuschangesListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout container = (LinearLayout) v;
                container.getChildAt(0).setVisibility(View.GONE);
                container.getChildAt(3).setVisibility(View.GONE);
                MainMenuActivity.selectedsStatusForDemo = 0x00000000;
            }
        };
        linear_demo_mwe.setOnFocusChangeListener(FocuschangesListener);
        linear_demo_dbc.setOnFocusChangeListener(FocuschangesListener);
        linear_demo_dlc.setOnFocusChangeListener(FocuschangesListener);
        linear_demo_dcc.setOnFocusChangeListener(FocuschangesListener);
        linear_demo_nr.setOnFocusChangeListener(FocuschangesListener);
        linear_demo_uclear.setOnFocusChangeListener(FocuschangesListener);
    }

    private void preinit() {
        if (TvPictureManager.getInstance() != null) {
            mwe_index = mappingMweTypeToIdx(TvPictureManager.getInstance().getMWEDemoMode());
            Log.i(TAG, "getMWEDemoMode() = " + TvPictureManager.getInstance().getMWEDemoMode() + "mwe_index = " + mwe_index);
            if (mwe_index > strMEWtypes.length) {
                Log.e(TAG, "mwe_index out of bound : " + mwe_index);
                mwe_index = 0;
            }
            nr_index = TvPictureManager.getInstance().getNoiseReduction();
            if (TvPictureManager.getInstance().isDlcEnabled()) {
                dlc_index = 1;
            } else {
                dlc_index = 0;
            }
            if (TvPictureManager.getInstance().isDbcEnabled()) {
                dbc_index = 1;
            } else {
                dbc_index = 0;
            }
            if (TvPictureManager.getInstance().isDccEnabled()) {
                dcc_index = 1;
            } else {
                dcc_index = 0;
            }
            if (TvPictureManager.getInstance().isUClearOn()) {
                uclear_index = 1;
            } else {
                uclear_index = 0;
            }
        }
    }

    private void repaintComponents() {
        text_demo_mwe_val.setText(strMEWtypes[mwe_index]);
        text_demo_dbc_val.setText(stronoff[dbc_index]);
        text_demo_dlc_val.setText(stronoff[dlc_index]);
        text_demo_nr_val.setText(str3dNRtypes[nr_index]);
        text_demo_uclear_val.setText(stronoff[uclear_index]);
    }

    private void setOnTouchListeners() {
        setMyOntouchListener(R.id.linearlayout_demo_mwe, 0x00000001, linear_demo_mwe);
        setMyOntouchListener(R.id.linearlayout_demo_dbc, 0x00000010, linear_demo_dbc);
        setMyOntouchListener(R.id.linearlayout_demo_dlc, 0x00000100, linear_demo_dlc);
        setMyOntouchListener(R.id.linearlayout_demo_dcc, 0x00001000, linear_demo_dcc);
        setMyOntouchListener(R.id.linearlayout_demo_nr, 0x00010000, linear_demo_nr);
        setMyOntouchListener(R.id.linearlayout_demo_uclear, 0x00100000, linear_demo_uclear);
    }

    private void setMyOntouchListener(final int resID, final int status, LinearLayout lay) {

        lay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP) {
                    v.requestFocus();
                    MainMenuActivity.selectedsStatusForDemo = status;
                    focusedid = resID;
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                }
                return true;
            }
        });
    }

    private int mappingIdxToMweType(int mweIdx) {
        /* this mapping is reference to str_arr_mwe_text in string.xml */
        switch (mweIdx) {
            case IDX_DEMO_MODE_SQUARE :
                return TvPictureManager.MWE_DEMO_MODE_SQUAREMOVE;
            default :
                return mweIdx;
        }
    }

    private int mappingMweTypeToIdx(int mweType) {
        /* this mapping is reference to str_arr_mwe_text in string.xml */
        switch (mweType) {
            case TvPictureManager.MWE_DEMO_MODE_SQUAREMOVE :
                return IDX_DEMO_MODE_SQUARE;
            default :
                return mweType;
        }
    }
}
