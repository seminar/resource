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

import com.mstar.tv.tvplayer.ui.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComboButton implements IUpdateSysData {

    private LinearLayout mLayout;

    private TextView mItemNameView;

    private TextView mItemValueView;

    private String[] mItems;
    
    private boolean[] mItemEnableFlags;

    private int mIndex = 0;

    private Activity mActivity;

    private int mLeftKeyCode = KeyEvent.KEYCODE_DPAD_LEFT;

    private int mRightKeyCode = KeyEvent.KEYCODE_DPAD_RIGHT;

    private boolean mIsSwitchEnable = false;
  //edit rollin 20130711
  	private boolean isAcyclic = false;

    // activity is current context,items are the items of choices,
    // resourceId is the linearlayout of combobutton(contain 2 textview)
    public ComboButton(Activity activity, String[] items, int resourceId, boolean isSwitchEnable) {
        mActivity = activity;
        mIsSwitchEnable = isSwitchEnable;
        mLayout = (LinearLayout) mActivity.findViewById(resourceId);
        mItemNameView = (TextView) mLayout.getChildAt(0);
        mItemValueView = (TextView) mLayout.getChildAt(1);
        initItems(items);
        initItemEnableFlags();
        setEnable(true);
    }

    public ComboButton(Dialog dialogContext, String[] items, int resourceId) {
        mLayout = (LinearLayout) dialogContext.findViewById(resourceId);
        mItemNameView = (TextView) mLayout.getChildAt(0);
        mItemValueView = (TextView) mLayout.getChildAt(1);
        setLRListener();
        initItems(items);
        initItemEnableFlags();
        setEnable(true);
    }

    public ComboButton(Activity activity, String[] items, int resourceId, int idxNameView,
            int idxIndicator, boolean isSwitchEnable) {
        mActivity = activity;
        mIsSwitchEnable = isSwitchEnable;
        mLayout = (LinearLayout) mActivity.findViewById(resourceId);
        mItemNameView = (TextView) mLayout.getChildAt(idxNameView);
        mItemValueView = (TextView) mLayout.getChildAt(idxIndicator);
        setLRListener();
        initItems(items);
        if((R.id.linearlayout_pic_zoommode)==(resourceId))
        	initItemEnableFlags_Zoom();
        else
        	initItemEnableFlags();	
        setEnable(true);
        setDefaultUiOnFocusChangeListener();
        setDefaultUiOnClickListener();
    }

    public ComboButton(Dialog dialogContext, String[] items, int resourceId, int idxNameView,
            int idxIndicator) {
        mLayout = (LinearLayout) dialogContext.findViewById(resourceId);
        mItemNameView = (TextView) mLayout.getChildAt(idxNameView);
        mItemValueView = (TextView) mLayout.getChildAt(idxIndicator);
        setLRListener();
        initItems(items);
        initItemEnableFlags();
        setEnable(true);
        setDefaultUiOnFocusChangeListener();
        setDefaultUiOnClickListener();
    }
  //edit rollin 20130711
  	public ComboButton(Activity con, String[] items, int resId, int first,
  	        int second, boolean isSelectedDiff, boolean acyclic)
  	{
  		isAcyclic = acyclic;
  		this.mIsSwitchEnable = isSelectedDiff;
  		mActivity = con;
  		mLayout = (LinearLayout) mActivity.findViewById(resId);
  		mItemNameView = (TextView) mLayout.getChildAt(first);
  		mItemValueView = (TextView) mLayout.getChildAt(second);
  		setLRListener();
  		mIndex = 0;
  		this.mItems = items;
  		if (items != null)
  			mItemValueView.setText(items[0]);
  		else
  			mItemValueView.setText("" + mIndex);
  		initItemEnableFlags();
    }

    private void initItems(String[] items) {
        setLRListener();
        mItems = items;
        // Set index 0 if the given index is Out of Boundary
        if (null != mItems && mItems.length > 0) {
            mItemValueView.setText(mItems[0]);
        } else {
            mItemValueView.setText("" + mIndex);
        }
    }
    //add by wxy
    private void initItemEnableFlags_Zoom() {
        if (null != mItems) {
            int len = mItems.length;
            mItemEnableFlags = new boolean[len];
            for (int i = 0; i < len; i++) {
            	switch(i)
            	{
            	case 1:
            	case 2:
            	case 3:
            	case 6:
            	case 7:
            		mItemEnableFlags[i] = true;
            	break;
            	default:
            		mItemEnableFlags[i] = false;
            	break;
            	}
            }
        }
    }
    //add end
    
    private void initItemEnableFlags() {
        if (null != mItems) {
            int len = mItems.length;
            mItemEnableFlags = new boolean[len];
            for (int i = 0; i < len; i++) {
                mItemEnableFlags[i] = true;
            }
        }
    }
   
  

    /**
     * Set if the combobutton can switch or not
     * @param bEnable Enable Switch Ability
     * @deprecated Use {@link setSwitchEnable(boolean)}
     */
    @Deprecated
    public void setSeletedDifferent(boolean bEnable) {
        setSwitchEnable(bEnable);
    }

    /**
     * Set if the combobutton can switch or not
     * @param bEnable Enable Switch Ability
     */
    public void setSwitchEnable(boolean bEnable) {
        mIsSwitchEnable = bEnable;
    }

    public void setLRKeyCode(int leftKeyCode, int rightKeyCode) {
        mLeftKeyCode = leftKeyCode;
        mRightKeyCode = rightKeyCode;
    }

    private void setLRListener() {
        mLayout.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (true == mLayout.isSelected()) {
                        mLayout.setSelected(false);
                    } else {
                        mLayout.setSelected(true);
                    }
                    return false;
                }

                // FIXME: avoid hardcoding keyevent behavior in components,
                // using OnFocusChangeListener is better
                if (KeyEvent.KEYCODE_DPAD_DOWN == keyCode || KeyEvent.KEYCODE_DPAD_UP == keyCode) {
                    mLayout.setSelected(false);
                }

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mLayout.isSelected() || !mIsSwitchEnable) {
                        if (keyCode == mLeftKeyCode) {
                            decreaseIdx();
                            doUpdate();
                            return true;
                        } else if (keyCode == mRightKeyCode) {
                            increaseIdx();
                            doUpdate();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        mLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (true == mLayout.isFocusable()) {
                        ComboButton.this.setFocused();
                        increaseIdx();
                        doUpdate();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void setIdx(int idx) {
        mIndex = idx;
        if (null != mItems) {
            // Set index 0 if the given index is Out of Boundary
            if (mItems.length <= mIndex || 0 > mIndex) {
                mIndex = 0;
            }
            mItemValueView.setText(mItems[mIndex]);
        } else {
            mItemValueView.setText("" + mIndex);
        }
    }

    public short getIdx() {
        return (short) mIndex;
    }

    public String getItemNameByIdx(int itemIndex) {
        if (null != mItems) {
            if (mItems.length > itemIndex && 0 <= itemIndex) {
                return mItems[itemIndex];
            }
        }
        return "";
    }

    public void setItemEnable(int itemIndex, boolean bEnable) {
    	Log.i("wxy","--setItemEnable="+bEnable+"---itemIndex="+itemIndex);
        if (null != mItemEnableFlags) {
            if (mItemEnableFlags.length > itemIndex && 0 <= itemIndex) {
                mItemEnableFlags[itemIndex] = bEnable;
            }
        }
    }

    public boolean getIsItemEnable(int itemIndex) {
        if (null != mItemEnableFlags) {
            if (mItemEnableFlags.length > itemIndex && 0 <= itemIndex) {
                return mItemEnableFlags[itemIndex];
            }
        }
        return false;
    }

    protected void increaseIdx() {
	        if (null != mItems && mItems.length > 0) {
	            for (int idx = mIndex + 1; idx < mIndex + mItems.length; idx++) {
	                if (true == mItemEnableFlags[idx % mItems.length]) {
	                    mIndex = idx % mItems.length;
	                    mItemValueView.setText(mItems[mIndex]);
	                    break;
	                }
	            }
	        } else {
	            mIndex++;
	            mItemValueView.setText("" + mIndex);
	        }
    }

    protected void decreaseIdx() {
        if (null != mItems && mItems.length > 0) {
            for (int idx = mIndex + mItems.length - 1; idx > mIndex; idx--) {
                if (true == mItemEnableFlags[idx % mItems.length]) {
                    mIndex = idx % mItems.length;
                    mItemValueView.setText(mItems[mIndex]);
                    break;
                }
            }
        } else {
            mIndex--;
            mItemValueView.setText("" + mIndex);
        }

    }

    public void doUpdate() {
    }

    public void setFocused() {
        mLayout.setFocusable(true);
        mLayout.setFocusableInTouchMode(true);
        mLayout.requestFocus();
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mLayout.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mLayout.setOnClickListener(onClickListener);
    }

    public void setTextInChild(int idx, String str) {
        TextView textView = (TextView) mLayout.getChildAt(idx);
        textView.setText(str);
    }

    /**
     * set Combo Button Focusable or not
     * @param bFocusable Focusable or not
     * @depricated Use {@link setEnable(boolean)}.
     */
    @Deprecated
    public void setFocusable(boolean bFocusable) {
        if (bFocusable) {
            mItemNameView.setTextColor(Color.WHITE);
            mItemValueView.setTextColor(Color.WHITE);
        } else {
            mItemNameView.setTextColor(Color.GRAY);
            mItemValueView.setTextColor(Color.GRAY);
        }
        mLayout.setFocusable(bFocusable);
        mLayout.setFocusableInTouchMode(bFocusable);
    }

    /**
     * set Combo Button Enable or Disable
     * @param bEnable Enable or Disable
     */
    public void setEnable(boolean bEnable) {
        if (bEnable) {
            mItemNameView.setTextColor(Color.WHITE);
            mItemValueView.setTextColor(Color.WHITE);
        } else {
            mItemNameView.setTextColor(Color.GRAY);
            mItemValueView.setTextColor(Color.GRAY);
        }
        mLayout.setEnabled(bEnable);
        mLayout.setFocusable(bEnable);
        mLayout.setFocusableInTouchMode(bEnable);
    }

    public void setVisibility(boolean bVisible) {
        if (bVisible) {
            mLayout.setVisibility(View.VISIBLE);
        } else {
            mLayout.setVisibility(View.GONE);
        }
    }

    public void setmItemValueViewColor(int color) {
        mItemValueView.setTextColor(color);
    }

    public LinearLayout getLayout() {
        return mLayout;
    }

    private void setDefaultUiOnFocusChangeListener() {
        mLayout.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout container = (LinearLayout) v;
                if (container.getChildAt(3) != null) {
                    container.getChildAt(0).setVisibility(View.GONE);
                    container.getChildAt(3).setVisibility(View.GONE);
                }
                mLayout.setSelected(false);
            }
        });
    }

    private void setDefaultUiOnClickListener() {
        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout container = (LinearLayout) v;
                if (container.isSelected()) {
                    if (container.getChildAt(3) != null) {
                        container.getChildAt(0).setVisibility(View.VISIBLE);
                        container.getChildAt(3).setVisibility(View.VISIBLE);
                    }
                } else {
                    if (container.getChildAt(3) != null) {
                        container.getChildAt(0).setVisibility(View.GONE);
                        container.getChildAt(3).setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
