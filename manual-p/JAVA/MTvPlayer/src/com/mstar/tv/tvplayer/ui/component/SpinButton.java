
package com.mstar.tv.tvplayer.ui.component;

import android.app.Activity;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SpinButton implements IUpdateSysData {
    private static final int PROGRESS_BAR_IDX = 2;

    private static final int TEXT_VIEW_PROGRESS_IDX = 1;

    private LinearLayout container;

    private ProgressBar progressBar;

    private TextView textViewProgress;

    private int step;

    private int leftKeyCode = KeyEvent.KEYCODE_DPAD_LEFT;

    private int rightKeyCode = KeyEvent.KEYCODE_DPAD_RIGHT;

    private boolean isSelectedDifferent = false;

    public SpinButton(Activity context, int resId, int step, boolean isSelectedDiff) {
        this.isSelectedDifferent = isSelectedDiff;
        this.step = step;
        container = (LinearLayout) context.findViewById(resId);
        progressBar = (ProgressBar) container.getChildAt(PROGRESS_BAR_IDX);
        textViewProgress = (TextView) container.getChildAt(TEXT_VIEW_PROGRESS_IDX);
        textViewProgress.setText(String.valueOf(progressBar.getProgress()));
        setLRListener();
    }

    public SpinButton(Dialog dialog, int resId, int step) {
        this.step = step;
        container = (LinearLayout) dialog.findViewById(resId);
        progressBar = (ProgressBar) container.getChildAt(PROGRESS_BAR_IDX);
        textViewProgress = (TextView) container.getChildAt(TEXT_VIEW_PROGRESS_IDX);
        textViewProgress.setText(String.valueOf(progressBar.getProgress()));
        setLRListener();
    }

    public void setSeletedDifferent(boolean b) {
        isSelectedDifferent = b;
    }

    private void setLRListener() {
        container.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (container.isSelected()) {
                        container.setSelected(false);
                    } else {
                        container.setSelected(true);
                    }
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    container.setSelected(false);
                }
                if (keyCode == leftKeyCode && event.getAction() == KeyEvent.ACTION_DOWN
                        && (container.isSelected() || !isSelectedDifferent)) {
                    decreaseProgress();
                    doUpdate();
                    return true;
                } else if (keyCode == rightKeyCode && event.getAction() == KeyEvent.ACTION_DOWN
                        && (container.isSelected() || !isSelectedDifferent)) {
                    increaseProgress();
                    doUpdate();
                    return true;
                } else
                    return false;
            }
        });
    }

    public void setLRKeyCode(int ltKeyCode, int rtKeyCode) {
        this.leftKeyCode = ltKeyCode;
        this.rightKeyCode = rtKeyCode;
    }

    protected void increaseProgress() {
        progressBar.incrementProgressBy(this.step);
        textViewProgress.setText(String.valueOf(progressBar.getProgress()));
    }

    protected void decreaseProgress() {
        // TODO Auto-generated method stub
        progressBar.incrementProgressBy(-this.step);
        textViewProgress.setText(String.valueOf(progressBar.getProgress()));
    }

    public short getProgress() {
        return (short) progressBar.getProgress();
    }

    public void setProgress(short progress) {
        progressBar.setProgress(progress);
        textViewProgress.setText(String.valueOf(progressBar.getProgress()));
    }

    @Override
    public void doUpdate() {
        // TODO Auto-generated method stub
    }

    public void setFocused() {
        container.setFocusable(true);
        container.requestFocus();
        container.setFocusableInTouchMode(true);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        container.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        container.setOnClickListener(onClickListener);
    }

    public void setFocusable(boolean b) {
        container.setFocusable(b);
    }

    public void setEnable(boolean b) {
        container.setEnabled(b);
    }
}
