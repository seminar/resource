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

package com.mstar.tv.tvplayer.ui.component;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View.OnFocusChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.tv.tvplayer.ui.R;

public class PasswordCheckDialog {

    private final static String TAG = "PasswordCheckDialog";

    private Activity mActivity = null;

    private Dialog mDialog = null;

    private EditText mEditPassword = null;

    private ArrayList<TextView> mArrEditPassword = null;

    private LinearLayout mlinearPasswordGroup = null;

    public Toast mToast = null;

    private final static String STR_EMPTY = "";

    private final static String STR_ASTERISK = "*";

    public PasswordCheckDialog(Activity activity) {
        mActivity = activity;

        mToast = Toast.makeText(activity, STR_EMPTY , Toast.LENGTH_SHORT);
        mDialog = new Dialog(mActivity, R.style.dialog);
        mDialog.setContentView(R.layout.password_check_dialog);
        mEditPassword = (EditText)mDialog.findViewById(R.id.edittext_password);
        mArrEditPassword = new ArrayList<TextView>();
        mlinearPasswordGroup = (LinearLayout)mDialog.findViewById(R.id.linear_password_group);
        initDialog();
    }

    public String onCheckPassword() {
        return "";
    }

    public void onPassWordCorrect() {
        if (null != mToast) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.str_check_password_pass) , Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void onPassWordWrong() {
        if (null != mToast) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.str_check_password_wrong) , Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void show() {
        if (false == mDialog.isShowing()) {
            mEditPassword.setText("");
            mDialog.show();
            mEditPassword.requestFocus();
        }
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void cancel() {
        mDialog.cancel();
    }

    public void onCancel() {
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void onDismiss() {
    }

    public void onKeyDown(int keyCode, KeyEvent keyEvent) {
    }

    public void onShow() {
    }

    private void initDialog() {
        View.OnClickListener onClickPassThroughListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mEditPassword) {
                    mEditPassword.requestFocus();
                    mEditPassword.onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                }
            }
        };

        TextView textView = null;
        for(int index = 0; index < ((ViewGroup)mlinearPasswordGroup).getChildCount(); ++index) {
            textView = (TextView)((ViewGroup)mlinearPasswordGroup).getChildAt(index);
            if (null != textView) {
                mArrEditPassword.add(textView);
                textView.setOnClickListener(onClickPassThroughListener);
            }
        }

        mlinearPasswordGroup.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
            }
        });

        mDialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                 PasswordCheckDialog.this.onShow();
            }
        });

        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                 PasswordCheckDialog.this.onCancel();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                 PasswordCheckDialog.this.onDismiss();
            }
        });

        mDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
                    return true;
                }

                switch (keyCode) {
                    case KeyEvent.KEYCODE_0:
                    case KeyEvent.KEYCODE_1:
                    case KeyEvent.KEYCODE_2:
                    case KeyEvent.KEYCODE_3:
                    case KeyEvent.KEYCODE_4:
                    case KeyEvent.KEYCODE_5:
                    case KeyEvent.KEYCODE_6:
                    case KeyEvent.KEYCODE_7:
                    case KeyEvent.KEYCODE_8:
                    case KeyEvent.KEYCODE_9:
                        Log.d(TAG, "User Input: " + keyEvent.getDisplayLabel());
                        break;
                    case KeyEvent.KEYCODE_BACK:
                        PasswordCheckDialog.this.cancel();
                        return true;
                    default:
                        onKeyDown(keyCode, keyEvent);
                        break;
                }
                return false;
            }
        });

        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                int count = arg0.length();
                if (0 > count || mArrEditPassword.size() < count) {
                    return;
                }

                for (int i = 0; i < count; i++) {
                    mArrEditPassword.get(i).setText(STR_ASTERISK);
                }
                for (int i = count; i < mArrEditPassword.size(); i++) {
                    mArrEditPassword.get(i).setText(STR_EMPTY);
                }

                if (mArrEditPassword.size() == count) {
                    if (arg0.toString().equals(onCheckPassword())) {
                        onPassWordCorrect();
                    } else {
                        onPassWordWrong();
                    }
                    mEditPassword.setText(STR_EMPTY);
                    for (TextView textview : mArrEditPassword) {
                        textview.setText(STR_EMPTY);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }
}
