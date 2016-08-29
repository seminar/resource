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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.tv.tvplayer.ui.R;

public class PasswordChangeDialog {

    private final static String TAG = "PasswordChangeDialog";

    private Activity mActivity = null;

    private Dialog mDialog = null;

    private EditText mEditOldPassword = null;
    private EditText mEditNewPassword = null;
    private EditText mEditConfirmPassword = null;

    private ArrayList<TextView> mArrTextOldPassword = null;
    private ArrayList<TextView> mArrTextNewPassword = null;
    private ArrayList<TextView> mArrTextConfirmPassword = null;

    private LinearLayout mlinearOldPasswordGroup = null;
    private LinearLayout mlinearNewPasswordGroup = null;
    private LinearLayout mlinearConfirmPasswordGroup = null;

    private LinearLayout mlinearPasswordOldRoot = null;
    private LinearLayout mlinearPasswordNewRoot = null;
    private LinearLayout mlinearPasswordConfirmRoot = null;

    public Toast mToast = null;

    private Button btnChangePasswordApply = null;

    private Button btnChangePasswordCancel = null;

    private Button btnChangePasswordReset = null;

    private final static String STR_EMPTY = "";

    private final static String STR_ASTERISK = "*";

    public PasswordChangeDialog(Activity activity) {
        mActivity = activity;

        mToast = Toast.makeText(activity, STR_EMPTY , Toast.LENGTH_SHORT);
        mDialog = new Dialog(mActivity, R.style.dialog);
        mDialog.setContentView(R.layout.password_change_dialog);

        initDialog();
        setListeners();
    }

    public String onCheckPassword() {
        return "";
    }

    public boolean setPassword(String newPassword) {
        return false;
    }

    public void show() {
        if (false == mDialog.isShowing()) {
            mEditOldPassword.setText(STR_EMPTY);
            mEditNewPassword.setText(STR_EMPTY);
            mEditConfirmPassword.setText(STR_EMPTY);
            mDialog.show();
            mEditOldPassword.requestFocus();
        }
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void cancel() {
        mDialog.cancel();
    }

    public void onCancel() {
        dismiss();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void onDismiss() {
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        return false;
    }

    public void onShow() {
    }

    private void initDialog() {
        /* Find View for Compoments */
        mEditOldPassword = (EditText)mDialog.findViewById(R.id.edittext_old_password);
        mEditNewPassword = (EditText)mDialog.findViewById(R.id.edittext_new_password);
        mEditConfirmPassword = (EditText)mDialog.findViewById(R.id.edittext_confirm_password);

        mlinearPasswordOldRoot = (LinearLayout)mDialog.findViewById(R.id.linear_old_password_root);
        mlinearPasswordNewRoot = (LinearLayout)mDialog.findViewById(R.id.linear_new_password_root);
        mlinearPasswordConfirmRoot = (LinearLayout)mDialog.findViewById(R.id.linear_confirm_password_root);

        mlinearOldPasswordGroup = (LinearLayout)mDialog.findViewById(R.id.linear_old_password_group);
        mlinearNewPasswordGroup = (LinearLayout)mDialog.findViewById(R.id.linear_new_password_group);
        mlinearConfirmPasswordGroup = (LinearLayout)mDialog.findViewById(R.id.linear_confirm_password_group);

        btnChangePasswordApply = (Button)mDialog.findViewById(R.id.button_change_password_apply);
        btnChangePasswordCancel = (Button)mDialog.findViewById(R.id.button_change_password_cancel);
        btnChangePasswordReset = (Button)mDialog.findViewById(R.id.button_change_password_reset);

        /* Set up textview array for showing asterisks */
        mArrTextOldPassword = new ArrayList<TextView>();
        mArrTextNewPassword = new ArrayList<TextView>();
        mArrTextConfirmPassword = new ArrayList<TextView>();
    }

    private void setListeners() {

        mDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    return onKeyDown(keyCode, keyEvent);
                } else {
                    return onKeyUp(keyCode, keyEvent);
                }
            }
        });

        /* Opened interface for the caller to do necessary behaviors */
        mDialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                 PasswordChangeDialog.this.onShow();
            }
        });

        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                 PasswordChangeDialog.this.onCancel();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                 PasswordChangeDialog.this.onDismiss();
            }
        });

        /* Pass a Enter Key to EditText to show the Android Default Input Window */
        if (null != mlinearPasswordOldRoot) {
            mlinearPasswordOldRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditOldPassword.requestFocus();
                    mEditOldPassword.onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                }
            });
        }
        if (null != mlinearPasswordNewRoot) {
            mlinearPasswordNewRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditNewPassword.requestFocus();
                    mEditNewPassword.onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                }
            });
        }
        if (null != mlinearPasswordConfirmRoot) {
            mlinearPasswordConfirmRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditConfirmPassword.requestFocus();
                    mEditConfirmPassword.onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                }
            });
        }

        /* Set background of LinearLayout Since the Focus is Actually on the EditText */
        OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (true == bHasFocus) {
                    ((LinearLayout)view.getParent()).setBackgroundResource(R.drawable.round_corner_hollow_focused);
                } else {
                    ((LinearLayout)view.getParent()).setBackgroundResource(R.drawable.round_corner_hollow_unfocused);
                }
            }
        };

        mEditOldPassword.setOnFocusChangeListener(mOnFocusChangeListener);
        mEditNewPassword.setOnFocusChangeListener(mOnFocusChangeListener);
        mEditConfirmPassword.setOnFocusChangeListener(mOnFocusChangeListener);

        /* Set TextWatcher for Showing Asterisks after the content of the Edittext changed */
        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int count = editable.toString().length();
                ArrayList<TextView> arrayTextview = null;

                if (mEditOldPassword.getText() == editable) {
                    arrayTextview = mArrTextOldPassword;
                } else if (mEditNewPassword.getText() == editable) {
                    arrayTextview = mArrTextNewPassword;
                } else if (mEditConfirmPassword.getText() == editable) {
                    arrayTextview = mArrTextConfirmPassword;
                }

                if (null != arrayTextview) {
                    for (int i = 0; i < count; i++) {
                        if (null != arrayTextview.get(i)) {
                            arrayTextview.get(i).setText(STR_ASTERISK);
                        }
                    }
                    for (int i = count; i < arrayTextview.size(); i++) {
                        if (null != arrayTextview.get(i)) {
                            arrayTextview.get(i).setText(STR_EMPTY);
                        }
                    }
                }
            }
        };

        mEditOldPassword.addTextChangedListener(mTextWatcher);
        mEditNewPassword.addTextChangedListener(mTextWatcher);
        mEditConfirmPassword.addTextChangedListener(mTextWatcher);

        btnChangePasswordApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        btnChangePasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        btnChangePasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditOldPassword.setText(STR_EMPTY);
                mEditNewPassword.setText(STR_EMPTY);
                mEditConfirmPassword.setText(STR_EMPTY);
            }
        });

        /* Set onClickListener to pass click event to EditText */
        View.OnClickListener onClickPassThroughListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayoutRoot = (LinearLayout)view.getParent();
                EditText editText = null;
                if (linearLayoutRoot == mlinearOldPasswordGroup) {
                    editText = mEditOldPassword;
                } else if (linearLayoutRoot == mlinearNewPasswordGroup) {
                    editText = mEditNewPassword;
                } else if (linearLayoutRoot == mlinearConfirmPasswordGroup) {
                    editText = mEditConfirmPassword;
                }

                if (null != editText) {
                    editText.requestFocus();
                    editText.onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                }
            }
        };

        TextView textView = null;

        for(int index = 0; index < ((ViewGroup)mlinearOldPasswordGroup).getChildCount(); ++index) {
            textView = (TextView)((ViewGroup)mlinearOldPasswordGroup).getChildAt(index);
            if (null != textView) {
                mArrTextOldPassword.add(textView);
                textView.setOnClickListener(onClickPassThroughListener);
            }
        }
        for(int index = 0; index < ((ViewGroup)mlinearNewPasswordGroup).getChildCount(); ++index) {
            textView = (TextView)((ViewGroup)mlinearNewPasswordGroup).getChildAt(index);
            if (null != textView) {
                mArrTextNewPassword.add(textView);
                textView.setOnClickListener(onClickPassThroughListener);
            }
        }
        for(int index = 0; index < ((ViewGroup)mlinearConfirmPasswordGroup).getChildCount(); ++index) {
            textView = (TextView)((ViewGroup)mlinearConfirmPasswordGroup).getChildAt(index);
            if (null != textView) {
                mArrTextConfirmPassword.add(textView);
                textView.setOnClickListener(onClickPassThroughListener);
            }
        }
    }

    private void changePassword() {
        String currentPassword = onCheckPassword();
        String oldPassword = mEditOldPassword.getText().toString();
        String newPassword = mEditNewPassword.getText().toString();
        String confirmPassword = mEditConfirmPassword.getText().toString();

       if (null != mToast) {
            mToast.cancel();
        }

        if (oldPassword == null || oldPassword.length() < 6) {
            mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.oldpasswordnullorlessthansix) , Toast.LENGTH_LONG);
            mToast.show();
        } else if (newPassword == null || newPassword.length() < 6) {
            mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.newpasswordnullorlessthansix) , Toast.LENGTH_LONG);
            mToast.show();
        } else if (confirmPassword == null || confirmPassword.length() < 6) {
            mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.confirmpasswordnullorlessthansix) , Toast.LENGTH_LONG);
            mToast.show();
        } else if (!(oldPassword.equals(currentPassword))) {
            mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.originalpassworderr) , Toast.LENGTH_LONG);
            mToast.show();
        } else if (!(newPassword.equals(confirmPassword))) {
            mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.newconfirmpassworderr) , Toast.LENGTH_LONG);
            mToast.show();
        } else if (oldPassword.equals(currentPassword) && newPassword.equals(confirmPassword)) {
            setPassword(newPassword);
            mToast = Toast.makeText(mActivity, mActivity.getResources().getString(R.string.resetsuccess) , Toast.LENGTH_LONG);
            mToast.show();
            dismiss();
        }
    }
}
