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

package com.mstar.tv.tvplayer.ui.channel;

import java.util.ArrayList;

import com.mstar.tv.tvplayer.ui.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mstar.android.tv.TvChannelManager;

public class ProgramEditAdapter extends BaseAdapter {
    private Context activity;

    private ArrayList<ProgramListViewItemObject> mData;

    public ProgramEditAdapter(Context context, ArrayList<ProgramListViewItemObject> data) {
        mData = data;
        activity = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        view = LayoutInflater.from(activity).inflate(R.layout.program_list_view_item, null);
        TextView tvnumber = (TextView) view.findViewById(R.id.program_edit_number);
        tvnumber.setText(mData.get(index).getTvNumber());
        TextView tvname = (TextView) view.findViewById(R.id.program_edit_data);
        tvname.setText(mData.get(index).getTvName());
        
        Log.d("zhongbin","index==="+index);
        if(index==foucusItem)
        {
        	tvnumber.setTextColor(0xFF0000FF);
        	tvname.setTextColor(0xFF0000FF);
        }
        ImageView favimage = (ImageView) view.findViewById(R.id.program_edit_favorite_img);
        favimage.setImageResource(R.drawable.list_menu_img_favorite_focus);
        if (mData.get(index).isFavoriteImg()) {
            favimage.setVisibility(View.VISIBLE);
        } else {
            favimage.setVisibility(View.GONE);
        }
        ImageView sslimage = (ImageView) view.findViewById(R.id.program_edit_ssl_img);
        sslimage.setImageResource(R.drawable.list_menu_img_ssl_focus);
        if (mData.get(index).isSslImg()) {
            sslimage.setVisibility(View.VISIBLE);
        } else {
            sslimage.setVisibility(View.GONE);
        }
        ImageView skipimage = (ImageView) view.findViewById(R.id.program_edit_skip_img);
        // lockimage.setImageResource(R.drawable.list_menu_img_lock_focus);
        skipimage.setImageResource(R.drawable.list_menu_img_skip_focus);
        if (mData.get(index).isSkipImg()) {
            skipimage.setVisibility(View.VISIBLE);
        } else {
            skipimage.setVisibility(View.GONE);
        }
        ImageView lockimage = (ImageView) view.findViewById(R.id.program_edit_lock_img);
        lockimage.setImageResource(R.drawable.list_menu_img_lock_focus);
        if (mData.get(index).isLockImg()) {
            lockimage.setVisibility(View.VISIBLE);
        } else {
            lockimage.setVisibility(View.GONE);
        }
        ImageView sourceimage = (ImageView) view.findViewById(R.id.program_edit_source_img);
        short serviceType = mData.get(index).getServiceType();
        int resId;
        switch(serviceType) {
            case TvChannelManager.SERVICE_TYPE_ATV:
                resId = R.drawable.list_menu_img_atv_foucus;
                break;
            case TvChannelManager.SERVICE_TYPE_RADIO:
                resId = R.drawable.list_menu_img_radio_foucus;
                break;
            case TvChannelManager.SERVICE_TYPE_DTV:
            default:
                resId = R.drawable.list_menu_img_dtv_foucus;
        }
        sourceimage.setImageResource(resId);
        sourceimage.setVisibility(View.VISIBLE);
        return view;
    }
    
    //zb20141029 add
    private int foucusItem=-1;
    public void setFocusItem(int focus)
    {
    	foucusItem=focus;
    }
    //end
}
