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

import java.io.UnsupportedEncodingException;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvCaManager;
import com.mstar.android.tvapi.dtv.vo.CaStartIPPVBuyDlgInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.NoSignalActivity;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.ca.AutoScrollTextView;
import com.mstar.tv.tvplayer.ui.ca.CaErrorType.CA_NOTIFY;
import com.mstar.tv.tvplayer.ui.ca.ippv.StartIppvBuyActivity;

import com.mstar.util.Constant;
import com.mstar.util.Constant.ScreenSaverMode;
import com.mstar.util.Tools;

import com.mstar.android.tvapi.dtv.common.CaManager;
import com.mstar.android.tvapi.dtv.common.CaManager.OnCaEventListener;
import com.mstar.android.tvapi.dtv.vo.CaLockService;

public class CaViewHolder {
    private static final String TAG = "CaViewHolder";

    private ProgressBar update_progressBar;

    private TextView update_textview;

    private TvCaManager tvCaManager = null;

    private View updateView;

    private View installView;

    private int screenWidth;

    private int screenHeight;

    private TextView disturbTextView;

    private TextView noSignalTextView;

    private AutoScrollTextView osdTextViewUp;

    private AutoScrollTextView osdTextViewDown;

    private ImageView emailNotifyIcon;

    private ImageView detitleNotifyIcon;

    private static final int UPDATE_PROGRESS = 1000000010;

    private static final int UPDATE_START = 1000000011;

    private Handler myCaHandler = null;

    private OnCaEventListener mCaEventListener = null;

    private RootActivity mRootActivity;

    private RelativeLayout relativeLayout;

    private AlertDialog.Builder builder;

    private Dialog choiceDialog;

    private Dialog cadialog;

    public CaViewHolder(RootActivity mRootActivity) {
        Point size = new Point();
        mRootActivity.getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        this.mRootActivity = mRootActivity;

        cadialog = new Dialog(mRootActivity, R.style.cadialog);
        cadialog.setContentView(R.layout.cadialog);
        Window dialogWindow = cadialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = screenWidth;
        lp.height = screenHeight;
        dialogWindow.setAttributes(lp);
        relativeLayout = (RelativeLayout) cadialog.findViewById(R.id.dialog_relativielayout);
        cadialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    return CaViewHolder.this.mRootActivity.onKeyDown(keyCode, event);
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    return CaViewHolder.this.mRootActivity.onKeyUp(keyCode, event);
                } else {
                    return true;
                }
            }
        });
        myCaHandler = new Handler();
        mCaEventListener = new CaEventListener();
        tvCaManager = TvCaManager.getInstance();
        mRootActivity.caCurEvent = tvCaManager.getCurrentEvent();
        mRootActivity.caCurNotifyIdx = tvCaManager.getCurrentMsgType();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isDialogShowing()) {
                cadialog.show();
            }
            if (relativeLayout == null) {
                return;
            }
            switch (msg.what) {
                case UPDATE_START:
                    if (installView == null) {
                        installView = mRootActivity.getLayoutInflater().inflate(
                                R.layout.show_install_progressbar, null);
                        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);

                        relativeLayout.addView(installView, param);
                        installView.setX(screenWidth / 2 - 100);
                        installView.setY(screenHeight / 2 - 200);
                    }
                    break;
                case UPDATE_PROGRESS:
                    if (installView != null) {
                        installView.setVisibility(View.GONE);
                    }
                    installView = null;
                    break;
            }
        };
    };

    /**
     * @param num
     */
    protected void showDisturbTextView(String num) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (disturbTextView == null) {
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            disturbTextView = new TextView(mRootActivity);
            relativeLayout.addView(disturbTextView, param);
        }
        if ("0".equals(num)) {
            disturbTextView.setVisibility(View.GONE);
        } else {
            disturbTextView.setText(num);
            disturbTextView.setX((float) (Math.random() * (screenWidth - 100)));
            disturbTextView.setY((float) (Math.random() * (screenHeight - 100)));
            disturbTextView.setTextSize(25);
            disturbTextView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * @param context
     * @param position the position of the view ,1 is up ,2 is down
     */
    protected void showScrollBar(String context, int position) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (position == 1) {
            if (osdTextViewUp == null) {
                osdTextViewUp = new AutoScrollTextView(mRootActivity);
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                relativeLayout.addView(osdTextViewUp, param);
            }
            osdTextViewUp.setText(context);
            osdTextViewUp.init(mRootActivity.getWindowManager());
            osdTextViewUp.setX(0);
            osdTextViewUp.setY(200);
            osdTextViewUp.setVisibility(View.VISIBLE);
            osdTextViewUp.startScroll();

        } else if (position == 2) {
            if (osdTextViewDown == null) {
                osdTextViewDown = new AutoScrollTextView(mRootActivity);
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                relativeLayout.addView(osdTextViewDown, param);
            }
            osdTextViewDown.setText(context);
            osdTextViewDown.init(mRootActivity.getWindowManager());
            osdTextViewDown.setX(0);
            osdTextViewDown.setY(screenHeight - 200);
            osdTextViewDown.setVisibility(View.VISIBLE);
            osdTextViewDown.startScroll();

        }

    }

    protected void hideScrollBar(int position) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (position == 1 && osdTextViewUp != null) {
            osdTextViewUp.stopScroll();
            osdTextViewUp.setVisibility(View.GONE);
        } else if (position == 2 && osdTextViewDown != null) {
            osdTextViewDown.stopScroll();
            osdTextViewDown.setVisibility(View.GONE);
        }
    }

    /**
     * @param byshow 0:hide icon 1:show icon 2:Disk full, Icon flash
     */
    public void emailNotify(int byshow) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (emailNotifyIcon == null) {
            emailNotifyIcon = new ImageView(mRootActivity);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.addView(emailNotifyIcon, param);
            emailNotifyIcon.setImageResource(R.drawable.ic_dialog_email);
            emailNotifyIcon.setX(screenWidth - 150);
            emailNotifyIcon.setY(100);
        }
        switch (byshow) {
            case 0:
                emailNotifyIcon.setVisibility(View.GONE);
                break;
            case 1:
                emailNotifyIcon.setVisibility(View.VISIBLE);
                break;
            case 2:
                emailNotifyIcon.setVisibility(View.VISIBLE);
                setFlickerAnimation(emailNotifyIcon);
                break;

        }

    }

    /**
     * @param byshow 0:hide icon 1:show icon 2:Disk full, Icon flash
     */
    public void detitleNotify(int byshow) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (detitleNotifyIcon == null) {
            detitleNotifyIcon = new ImageView(mRootActivity);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.addView(detitleNotifyIcon, param);
            detitleNotifyIcon.setImageResource(R.drawable.ca_detitle);
            detitleNotifyIcon.setX(screenWidth - 75);
            detitleNotifyIcon.setY(100);
        }
        switch (byshow) {
            case Constant.CDCA_Detitle_All_Read:
                detitleNotifyIcon.setVisibility(View.GONE);
                break;
            case Constant.CDCA_Detitle_Received:
                detitleNotifyIcon.setVisibility(View.VISIBLE);
                break;
            case Constant.CDCA_Detitle_Space_Small:
                detitleNotifyIcon.setVisibility(View.VISIBLE);
                setFlickerAnimation(detitleNotifyIcon);
                break;

        }

    }

    protected void setFlickerAnimation(ImageView iv_chat_head) {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        iv_chat_head.setAnimation(animation);
    }

    protected void showProgressBar(int progress) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (updateView == null) {
            updateView = mRootActivity.getLayoutInflater().inflate(
                    R.layout.show_update_progressbar, null);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            relativeLayout.addView(updateView, param);
            updateView.setX(screenWidth / 2 - 100);
            updateView.setY(screenHeight / 2 - 200);
        }
        Constant.lockKey = false;
        update_progressBar = (ProgressBar) updateView.findViewById(R.id.update_progressBar);
        update_textview = (TextView) updateView.findViewById(R.id.update_textview);
        update_textview.setText(progress + "%");
        update_progressBar.setProgress(progress);
        if (progress == 100) {
            updateView.setVisibility(View.GONE);
            Constant.lockKey = true;
        }
    }

    /**
     * /data/user/update.zip
     *
     * @param updateFile
     */
    protected void doSytemUpgrade() {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }

        TvCommonManager.getInstance().recoverySystem("/data/user/update.zip");
        mHandler.sendEmptyMessage(UPDATE_START);
        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 20000);

    }

    public void showNoSignalTextView(int resid) {
        if (!isDialogShowing()) {
            cadialog.show();
        }
        if (relativeLayout == null) {
            return;
        }
        if (noSignalTextView == null) {
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            noSignalTextView = new TextView(mRootActivity);
            relativeLayout.addView(noSignalTextView, param);
            noSignalTextView.setX(screenWidth / 2 - 100);
            noSignalTextView.setY(screenHeight / 2 + 100);
            noSignalTextView.setTextSize(30);
            noSignalTextView.setTextColor(Color.WHITE);
        }
        if (resid == 0) {
            noSignalTextView.setVisibility(View.GONE);
        } else {
            noSignalTextView.setVisibility(View.VISIBLE);
            noSignalTextView.setText(resid);
        }
    }

    public boolean isCaEnable() {
        int curInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (tvCaManager.CaGetVer() != 0 && curInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            return true;
        } else {
            return false;
        }

    }

    public void cancelAllCANotify() {
        if (relativeLayout == null) {
            return;
        }
        detitleNotify(0);
        emailNotify(0);
        hideScrollBar(1);
        hideScrollBar(2);
        showDisturbTextView("0");
        if (isDialogShowing()) {
            cadialog.dismiss();
        }
    }

    class CaEventListener implements OnCaEventListener {
        @Override
        public boolean onStartIppvBuyDlg(CaManager mgr, int what, int arg1, int arg2,CaStartIPPVBuyDlgInfo arg3) {
            Log.i(TAG, "onStartIppvBuyDlg ");
            final CaStartIPPVBuyDlgInfo StIppvInfo = arg3;
            Intent intent = new Intent();
            intent.setClass(mRootActivity, StartIppvBuyActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("StIppvInfo", StIppvInfo);
            intent.putExtras(bundle);
            mRootActivity.startActivity(intent);
            return true;
        }

        @Override
        public boolean onHideIPPVDlg(CaManager mgr, int what, int arg1, int arg2) {

            return true;
        }

        @Override
        public boolean onEmailNotifyIcon(CaManager mgr, int what, int byShow, int dwEmailID) {
            emailNotify(byShow);
            Log.d("camanage", "email notify, byShow :" + byShow + ",dwEmailID: " + dwEmailID);

            return true;
        }

        @Override
        public boolean onShowOSDMessage(CaManager mgr, int what, int byStyle, int szMessage, String arg3) {
            String sOsdData = null;
            try {
                sOsdData = new String(arg3.getBytes(), "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("camanage", "osd show notify, byStyle :" + byStyle + ",szMessage: "
                    + szMessage);
            Log.d("camanage", "osd show notify, String is :" + sOsdData);

            showScrollBar(sOsdData, byStyle);
            return true;
        }

        @Override
        public boolean onHideOSDMessage(CaManager mgr, int what, int byStyle, int arg2) {
            hideScrollBar(byStyle);
            Log.d("camanage", "osd hide notify ");
            return true;
        }

        @Override
        public boolean onRequestFeeding(CaManager mgr, int what, int arg1, int arg2) {
                return true;
        }


        @Override
        public boolean onShowBuyMessage(CaManager mgr, int what, int arg1, int MessageType) {

            int MsgFrom = 0;// 0 for SN Post;1 for // Activity Send

            CA_NOTIFY notify = null;

            for (CA_NOTIFY c : CA_NOTIFY.values()) {
                if (MessageType == c.ordinal()) {
                    notify = c;
                    break;
                }
            }
            if (notify == null)
                return false;

            Log.d("MessageType", notify.toString());
            switch (notify) {
                case ST_CA_MESSAGE_CANCEL_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_CANCEL_TYPE.ordinal(),
                            0, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_BADCARD_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_BADCARD_TYPE.ordinal(),
                            R.string.st_ca_message_badcard_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_EXPICARD_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_EXPICARD_TYPE.ordinal(),
                            R.string.st_ca_message_expicard_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_INSERTCARD_TYPE: {
                     showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_INSERTCARD_TYPE.ordinal(),
                            R.string.st_ca_message_insertcard_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_NOOPER_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_NOOPER_TYPE.ordinal(),
                            R.string.st_ca_message_nooper_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_BLACKOUT_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_BLACKOUT_TYPE.ordinal(),
                            R.string.st_ca_message_blackout_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_OUTWORKTIME_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_OUTWORKTIME_TYPE.ordinal(),
                            R.string.st_ca_message_outworktime_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_WATCHLEVEL_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_WATCHLEVEL_TYPE.ordinal(),
                            R.string.st_ca_message_watchlevel_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_PAIRING_TYPE: {
                     showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_PAIRING_TYPE.ordinal(),
                            R.string.st_ca_message_pairing_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_NOENTITLE_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_NOENTITLE_TYPE.ordinal(),
                            R.string.st_ca_message_noentitle_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_DECRYPTFAIL_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_DECRYPTFAIL_TYPE.ordinal(),
                            R.string.st_ca_message_decryptfail_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_NOMONEY_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_NOMONEY_TYPE.ordinal(),
                            R.string.st_ca_message_nomoney_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_ERRREGION_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_ERRREGION_TYPE.ordinal(),
                            R.string.st_ca_message_errregion_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_NEEDFEED_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_NEEDFEED_TYPE.ordinal(),
                            R.string.st_ca_message_needfeed_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_ERRCARD_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_ERRCARD_TYPE.ordinal(),
                            R.string.st_ca_message_errcard_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_UPDATE_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_UPDATE_TYPE.ordinal(),
                            R.string.st_ca_message_update_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_LOWCARDVER_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_LOWCARDVER_TYPE.ordinal(),
                            R.string.st_ca_message_lowcardver_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_VIEWLOCK_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_VIEWLOCK_TYPE.ordinal(),
                            R.string.st_ca_message_viewlock_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_MAXRESTART_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_MAXRESTART_TYPE.ordinal(),
                            R.string.st_ca_message_maxrestart_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_FREEZE_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_FREEZE_TYPE.ordinal(),
                            R.string.st_ca_message_freeze_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_CALLBACK_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_CALLBACK_TYPE.ordinal(),
                            R.string.st_ca_message_callback_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_CURTAIN_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_CURTAIN_TYPE.ordinal(),
                            R.string.st_ca_message_curtain_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_CARDTESTSTART_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_CARDTESTSTART_TYPE.ordinal(),
                            R.string.st_ca_message_cardteststart_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_CARDTESTFAILD_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_CARDTESTFAILD_TYPE.ordinal(),
                            R.string.st_ca_message_cardtestfaild_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_CARDTESTSUCC_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_CARDTESTSUCC_TYPE.ordinal(),
                            R.string.st_ca_message_cardtestsucc_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_NOCALIBOPER_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_NOCALIBOPER_TYPE.ordinal(),
                            R.string.st_ca_message_nocaliboper_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_STBLOCKED_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_STBLOCKED_TYPE.ordinal(),
                            R.string.st_ca_message_stblocked_type, MsgFrom);
                    break;
                }
                case ST_CA_MESSAGE_STBFREEZE_TYPE: {
                    showBuyMessage(what, CA_NOTIFY.ST_CA_MESSAGE_STBFREEZE_TYPE.ordinal(),
                            R.string.st_ca_message_stbfreeze_type, MsgFrom);
                    break;
                }
                default: {
                    Log.d("MessageType", "not exist");
                    break;
                }
            }
            return true;
        }

        @Override
        public boolean onShowFingerMessage(CaManager mgr, int what, int num, int num2) {
            showDisturbTextView(num2 + "");
            Log.d("camanage", "EV_CA_LOCKSERVICE ");
            return true;
        }

        @Override
        public boolean onShowProgressStrip(CaManager mgr, int what, int arg1, int arg2) {
            Log.d("camanage", "EV_CA_SHOW_PROGRESS_STRIP ");
            return true;
        }

        @Override
        public boolean onActionRequest(CaManager mgr, int what, int arg1, int arg2) {
            Log.d("camanage", "EV_CA_ACTION_REQUEST ");
            return true;
        }

        @Override
        public boolean onEntitleChanged(CaManager mgr, int what, int arg1, int arg2) {
            Log.d("camanage", "EV_CA_ENTITLE_CHANGED ");
            return true;
        }

        @Override
        public boolean onDetitleReceived(CaManager mgr, int what, int byShow, int arg2) {
            Log.d("camanage", "EV_CA_ENTITLE_CHANGED ");
            Log.d("camanage", "byShow===" + byShow);
            detitleNotify(byShow);
            return true;
        }

        @Override
        public boolean onLockService(CaManager mgr, int what, int arg1, int arg2, CaLockService arg3) {
            Log.d("camanage", "EV_CA_LOCKSERVICE ");
            Constant.lockKey = false;
            return true;
        }

        @Override
        public boolean onUNLockService(CaManager mgr, int what, int arg1, int arg2) {
            Log.d("camanage", "EV_CA_UNLOCKSERVICE ");
            Constant.lockKey = true;
            return true;
        }

        @Override
        public boolean onOtaState(CaManager mgr, int what, int messageType, int messageType2) {
            switch (messageType) {
                case 1: {
                    if (builder == null) {
                        builder = new AlertDialog.Builder(mRootActivity);

                    }
                    choiceDialog = builder
                            .setTitle(R.string.is_update)
                            .setNegativeButton(android.R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            tvCaManager.CaOTAStateConfirm(1, 0);
                                        }
                                    })
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            tvCaManager.CaOTAStateConfirm(1, 1);
                                        }
                                    }).create();
                    if (!choiceDialog.isShowing()) {
                        choiceDialog.show();
                    }
                    break;
                }
                case 2: {
                    tvCaManager.CaOTAStateConfirm(2, 1);
                    break;
                }
                case 3: {
                    showProgressBar(messageType2);
                    break;
                }
                case 4: {
                    showProgressBar(messageType2);
                    break;
                }
                case 5: {
                    doSytemUpgrade();
                    break;
                }
                case 6: {
                    doSytemUpgrade();
                    break;
                }
                case 7: {
                    Tools.toastShow(R.string.update_failed, mRootActivity);
                    Constant.lockKey = true;
                    if (updateView != null) {
                        updateView.setVisibility(View.GONE);
                    }
                    break;
                }
                default: {
                    break;
                }
            }

            return true;
        }
    }


        /* For Handle CA Notify Message */
        private void showBuyMessage(int caEventType, int caMsgType, int msgResId, int msgFrom) {
            int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

            Log.d("camanage", "caMsgType :" + caMsgType);

            {
                ActivityManager activitymanager = (ActivityManager) mRootActivity
                        .getSystemService(mRootActivity.ACTIVITY_SERVICE);
                ComponentName cn = activitymanager.getRunningTasks(1).get(0).topActivity;

                if (msgFrom == 0 && caEventType == mRootActivity.caCurEvent
                        && caMsgType == mRootActivity.caCurNotifyIdx
                        && cn.getClassName().equals("com.mstar.tv.tvplayer.ui.NoSignalActivity")) {
                    return;
                }

                Log.d("camanage", "caMsgType 2 :" + caMsgType);

                mRootActivity.caCurEvent = caEventType;
                mRootActivity.caCurNotifyIdx = caMsgType;
                Log.i("RootActivity", "caEventType=======" + caEventType + "caMsgType==="
                        + caMsgType + "resId========" + msgResId);

                if (mRootActivity.getActiveStatus() == false
                        && cn.getClassName().equals("com.mstar.tv.tvplayer.ui.NoSignalActivity")) {
                    Intent intent = new Intent(mRootActivity, NoSignalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("caEventType", caEventType);
                    bundle.putInt("caMsgType", caMsgType);
                    bundle.putInt("resId", msgResId);
                    bundle.putInt("IntendId", ScreenSaverMode.DTV_SS_CA_NOTIFY);
                    intent.putExtras(bundle);
                    mRootActivity.startActivity(intent);
                    return;
                }
                Log.d("camanage", "caMsgType 3 :" + caMsgType + "mRootActivity.bCmd_TvApkExit=="
                        + mRootActivity.bCmd_TvApkExit + "mRootActivity.mIsActive===="
                        + mRootActivity.getActiveStatus());
                if ((mRootActivity.getActiveStatus() == false && mRootActivity.caCurNotifyIdx != 0)
                        || mRootActivity.bCmd_TvApkExit == true
                        || currInputSource != TvCommonManager.INPUT_SOURCE_DTV) {
                    return;
                }
                Log.d("camanage", "caMsgType 4 :" + caMsgType);
                Intent intent = new Intent(mRootActivity, NoSignalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("caEventType", caEventType);
                bundle.putInt("caMsgType", caMsgType);
                bundle.putInt("resId", msgResId);
                bundle.putInt("IntendId", ScreenSaverMode.DTV_SS_CA_NOTIFY);
                intent.putExtras(bundle);
                mRootActivity.startActivity(intent);
            }
        }


    /* For Activity Send CA Notify Message */
    public void sendCaNotifyMsg(int caEventType, int caMsgType, int msgFrom) {
        if (!isCaEnable()) {
            return;
        }
        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (caEventType != 0 && caMsgType != 0
                && currInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            Bundle b = new Bundle();
            Message msg = myCaHandler.obtainMessage();
            msg.what = caEventType;
            b.putInt("MessageType", caMsgType);
            b.putInt("MessageFrom", msgFrom);
            msg.setData(b);
            myCaHandler.sendMessage(msg);
        }
    }
    public OnCaEventListener getCaEventListener() {
        return mCaEventListener;
    }

    public boolean isDialogShowing() {
        return cadialog.isShowing();
    }

}
