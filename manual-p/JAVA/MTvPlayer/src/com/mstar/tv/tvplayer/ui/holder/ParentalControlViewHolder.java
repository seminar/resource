
package com.mstar.tv.tvplayer.ui.holder;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.android.tv.TvCiManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.BlockProgramActivity;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.CheckParentalPwdActivity;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.ParentalGuidanceActivity;
import com.mstar.tv.tvplayer.ui.dtv.parental.dvb.SetParentalPwdActivity;

public class ParentalControlViewHolder {
    private static final String TAG = "ParentalControlViewHolder";

    protected LinearLayout linear_lock_system;

    protected LinearLayout linear_set_pwd;

    protected LinearLayout linear_block_program;

    protected LinearLayout linear_parental_guidance;

    protected LinearLayout linear_parental_cipincode;

    protected TextView text_lock_system;

    protected TextView textt_set_pwd;

    protected TextView text_block_program;

    protected TextView text_parental_guidance;

    protected TextView lock_status;

    private MainMenuActivity mainMenuActivity;

    private int focusedid = 0x00000000;

    private EditText mEditPin1;

    private EditText mEditPin2;

    private EditText mEditPin3;

    private EditText mEditPin4;

    public ParentalControlViewHolder(MainMenuActivity activity) {
        this.mainMenuActivity = activity;
    }

    public void findViews() {
        linear_lock_system = (LinearLayout) mainMenuActivity
                .findViewById(R.id.linearlayout_lock_system);
        linear_set_pwd = (LinearLayout) mainMenuActivity.findViewById(R.id.linearlayout_set_pwd);
        linear_block_program = (LinearLayout) mainMenuActivity
                .findViewById(R.id.linearlayout_block_program);
        linear_parental_guidance = (LinearLayout) mainMenuActivity
                .findViewById(R.id.linearlayout_parental_guidance);
        linear_parental_cipincode = (LinearLayout) mainMenuActivity
                .findViewById(R.id.linearlayout_parental_cipincode);

        text_lock_system = (TextView) mainMenuActivity.findViewById(R.id.textview_lock_system);
        textt_set_pwd = (TextView) mainMenuActivity.findViewById(R.id.textview_set_pwd);
        text_block_program = (TextView) mainMenuActivity.findViewById(R.id.textview_block_program);
        text_parental_guidance = (TextView) mainMenuActivity
                .findViewById(R.id.textview_parental_guidance);
        lock_status = (TextView) mainMenuActivity.findViewById(R.id.textview_lock_system_statue);

        updateButtonColor();
        setOnClickLisenters();
        setOnFocusChangeListeners();
        setOnTouchListeners();
        findViewCiPinCode();
    }

    public void updateButtonColor() {

        final int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        if (currInputSource != TvCommonManager.INPUT_SOURCE_DTV) {
            text_lock_system.setTextColor(Color.GRAY);
            textt_set_pwd.setTextColor(Color.GRAY);
            text_block_program.setTextColor(Color.GRAY);
            text_parental_guidance.setTextColor(Color.GRAY);
            lock_status.setTextColor(Color.GRAY);
            linear_lock_system.setFocusable(false);
            linear_set_pwd.setFocusable(false);
            linear_block_program.setFocusable(false);
            linear_parental_guidance.setFocusable(false);
        } else if (MainMenuActivity.getInstance().isPwdCorrect() == false) {
            lock_status.setText("");
            linear_set_pwd.setFocusable(false);
            linear_block_program.setFocusable(false);
            linear_parental_guidance.setFocusable(false);
            textt_set_pwd.setTextColor(Color.GRAY);
            text_block_program.setTextColor(Color.GRAY);
            text_parental_guidance.setTextColor(Color.GRAY);
        } else {
            if (TvParentalControlManager.getInstance().isSystemLock()) {
                lock_status.setText(mainMenuActivity.getResources().getString(R.string.str_set_on));
                linear_set_pwd.setFocusable(true);
                linear_block_program.setFocusable(true);
                linear_parental_guidance.setFocusable(true);
                textt_set_pwd.setTextColor(Color.WHITE);
                text_block_program.setTextColor(Color.WHITE);
                text_parental_guidance.setTextColor(Color.WHITE);
            } else {
                lock_status.setText(mainMenuActivity.getResources().getString(R.string.str_set_off));
                linear_set_pwd.setFocusable(false);
                linear_block_program.setFocusable(false);
                linear_parental_guidance.setFocusable(false);
                textt_set_pwd.setTextColor(Color.GRAY);
                text_block_program.setTextColor(Color.GRAY);
                text_parental_guidance.setTextColor(Color.GRAY);
            }
        }
    }

    private void setOnClickLisenters() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentid = mainMenuActivity.getCurrentFocus().getId();
                Intent intent = new Intent();
                if (focusedid != currentid) {
                    MainMenuActivity.getInstance().setParentalControlSelectStatus(0x00000000);
                }
                // TODO Auto-generated method stub
                switch (currentid) {
                    case R.id.linearlayout_lock_system:
                        if (MainMenuActivity.getInstance().isPwdCorrect() == true) {
                            MainMenuActivity.getInstance().setParentalControlSelectStatus(0x00000001);
                            focusedid = R.id.linearlayout_lock_system;
                            linear_lock_system.getChildAt(0).setVisibility(View.VISIBLE);
                            linear_lock_system.getChildAt(3).setVisibility(View.VISIBLE);
                        } else {
                            intent = new Intent(mainMenuActivity,
                                    CheckParentalPwdActivity.class);
                            intent.putExtra("list", 6);
                            mainMenuActivity.startActivity(intent);
                        }
                        break;
                    case R.id.linearlayout_set_pwd:
                        intent.setClass(mainMenuActivity, SetParentalPwdActivity.class);
                        mainMenuActivity.startActivity(intent);
                        mainMenuActivity.finish();
                        break;
                    case R.id.linearlayout_block_program:
                        intent.setClass(mainMenuActivity, BlockProgramActivity.class);
                        mainMenuActivity.startActivity(intent);
                        mainMenuActivity.finish();
                        break;
                    case R.id.linearlayout_parental_guidance:
                        intent.setClass(mainMenuActivity, ParentalGuidanceActivity.class);
                        mainMenuActivity.startActivity(intent);
                        mainMenuActivity.finish();
                        break;
                    case R.id.linearlayout_parental_cipincode:
                        ShowEditTextDialog(true);
                        break;
                    default:
                        break;
                }
            }
        };
        linear_lock_system.setOnClickListener(listener);
        linear_set_pwd.setOnClickListener(listener);
        linear_block_program.setOnClickListener(listener);
        linear_parental_guidance.setOnClickListener(listener);
        linear_parental_cipincode.setOnClickListener(listener);

    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener FocuschangesListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout container = (LinearLayout) v;
                container.getChildAt(0).setVisibility(View.GONE);
                container.getChildAt(3).setVisibility(View.GONE);
                MainMenuActivity.getInstance().setParentalControlSelectStatus(0x00000000);
            }
        };
        linear_lock_system.setOnFocusChangeListener(FocuschangesListener);
    }

    private void setOnTouchListeners() {
        setMyOntouchListener(R.id.linearlayout_lock_system, 0x00000001, linear_lock_system);
    }

    private void setMyOntouchListener(final int resID, final int status, LinearLayout lay) {
        lay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    v.requestFocus();
                    MainMenuActivity.getInstance().setParentalControlSelectStatus(status);
                    focusedid = resID;
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                }
                return true;
            }
        });
    }

    private void findViewCiPinCode() {
        mEditPin1 = (EditText) mainMenuActivity.findViewById(R.id.ci_pin1);
        mEditPin2 = (EditText) mainMenuActivity.findViewById(R.id.ci_pin2);
        mEditPin3 = (EditText) mainMenuActivity.findViewById(R.id.ci_pin3);
        mEditPin4 = (EditText) mainMenuActivity.findViewById(R.id.ci_pin4);
        setMyEditListener(mEditPin1, mEditPin2);
        setMyEditListener(mEditPin2, mEditPin3);
        setMyEditListener(mEditPin3, mEditPin4);
        setMyEditListener(mEditPin4, linear_parental_cipincode);
    }

    private void ShowEditTextDialog(boolean isShow) {
        mEditPin1.setText("");
        mEditPin2.setText("");
        mEditPin3.setText("");
        mEditPin4.setText("");
        int visible = isShow ? View.VISIBLE : View.GONE;
        mEditPin1.setVisibility(visible);
        mEditPin2.setVisibility(visible);
        mEditPin3.setVisibility(visible);
        mEditPin4.setVisibility(visible);
        if (isShow) {
            mEditPin1.requestFocus();
        } else {
            linear_parental_cipincode.requestFocus();
        }
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
                if (R.id.linearlayout_parental_cipincode == mainMenuActivity.getCurrentFocus().getId()) {
                    return;
                }
                if (null != view) {
                    view.requestFocus();
                    if (linear_parental_cipincode == view) {
                        setCiPinCode();
                    }
                }
            }
        });
    }

    private void setCiPinCode() {
        int pin1, pin2, pin3, pin4, ciCamPinCode;
        pin1 = pin2 = pin3 = pin4 = 0;
        boolean isValid = true;
        if((mEditPin1.getText().toString().equals("")) && (mEditPin2.getText().toString().equals(""))
            && (mEditPin3.getText().toString().equals("")) && (mEditPin4.getText().toString().equals(""))) {
            return;
        }
        try {
            pin1 = Integer.parseInt(mEditPin1.getText().toString());
            pin2 = Integer.parseInt(mEditPin2.getText().toString());
            pin3 = Integer.parseInt(mEditPin3.getText().toString());
            pin4 = Integer.parseInt(mEditPin4.getText().toString());
        } catch (Exception e) {
            isValid = false;
        }
        if (false == isValid)
            return;
        ciCamPinCode = pin1 * 1000 + pin2 * 100 + pin3 * 10 + pin4;
        Log.d(TAG, "ciCamPinCode:"+ciCamPinCode);
        TvCiManager.getInstance().setCiCamPinCode(ciCamPinCode);
        Toast.makeText(mainMenuActivity, R.string.str_ci_set_cam_pincode_done, Toast.LENGTH_SHORT).show();
        ShowEditTextDialog(false);
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        int currentid = -1;
        if (MainMenuActivity.getInstance().isPwdCorrect() == false) {
            Log.e(TAG, "onKeyDown: isPwdCorrect == false!!!");
            return;
        }
        if (mainMenuActivity.getCurrentFocus() != null) {
            currentid = mainMenuActivity.getCurrentFocus().getId();
        }
        if (focusedid != currentid) {
            MainMenuActivity.getInstance().setParentalControlSelectStatus(0x00000000);
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (currentid == R.id.linearlayout_lock_system) {
                    if (MainMenuActivity.getInstance().getParentalControlSelectStatus() == 0x00000001) {
                        boolean lockstatus = TvParentalControlManager.getInstance().isSystemLock();
                        lockstatus = !lockstatus;
                        TvParentalControlManager.getInstance().setSystemLock(lockstatus);
                        updateButtonColor();
                    }
                }
                focusedid = currentid;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (currentid == R.id.linearlayout_lock_system) {
                    if (MainMenuActivity.getInstance().getParentalControlSelectStatus() == 0x00000001) {
                        boolean lockstatus = TvParentalControlManager.getInstance().isSystemLock();
                        lockstatus = !lockstatus;
                        TvParentalControlManager.getInstance().setSystemLock(lockstatus);
                        updateButtonColor();
                    }
                }
                focusedid = currentid;
                break;
            default:
                break;
        }
    }
}
