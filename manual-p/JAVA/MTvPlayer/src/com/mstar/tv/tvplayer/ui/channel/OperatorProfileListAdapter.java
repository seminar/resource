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

package com.mstar.tv.tvplayer.ui.channel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.tv.tvplayer.ui.R;

public class OperatorProfileListAdapter extends BaseAdapter {
    private Context mContext;

    // The items to be displayed
    final public int maxOpNum = 7;

    private List<OperatorProfileInfo> mOProfiles;

    private final Object mLock = new Object();

    private boolean shoudRefreshDataWhenSetItems = true;

    private int mDtvRoute = TvChannelManager.TV_ROUTE_NONE;

    public void setRefreshWhenSetItems(boolean refresh) {
        shoudRefreshDataWhenSetItems = refresh;
    }

    public OperatorProfileListAdapter(Context context) {
        mContext = context;
        mOProfiles = new ArrayList<OperatorProfileInfo>();
        int dtvRouteIndex = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
        TvTypeInfo tvinfo = TvCommonManager.getInstance().getTvInfo();
        mDtvRoute = tvinfo.routePath[dtvRouteIndex];
    }

    public void setItems(List<OperatorProfileInfo> items) {
        mOProfiles = items;
        if (shoudRefreshDataWhenSetItems)
            notifyDataSetChanged();
    }

    public List<OperatorProfileInfo> getOProfiles() {
        return mOProfiles;
    }

    /**
     * Add one channel, Should be called in Main thread
     *
     * @param object the channel to be added
     */
    public void add(OperatorProfileInfo object) {
        synchronized (mLock) {
            if (mOProfiles != null) {
                mOProfiles.add(object);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(OperatorProfileInfo object) {
        synchronized (mLock) {
            if (mOProfiles != null) {
                mOProfiles.remove(object);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (mLock) {
            if (mOProfiles != null) {
                mOProfiles.clear();
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mOProfiles != null ? mOProfiles.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (mOProfiles == null || mOProfiles.size() == 0)
            return null;
        return mOProfiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mOProfiles == null || mOProfiles.size() == 0)
            return 0;
        return mOProfiles.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.oprofile_item, null);
        }
        TextView opName = (TextView) convertView.findViewById(R.id.oprofile_name);
        opName.setText(mOProfiles.get(position).getOperatorProfileName());
        ImageView tvSystem = (ImageView) convertView.findViewById(R.id.tv_system);

        int opSysType = mOProfiles.get(position).getOPSysType();
        if (opSysType == OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_TDSD.ordinal()) {
            if ((mDtvRoute == TvChannelManager.TV_ROUTE_DVBT)
                    || (mDtvRoute == TvChannelManager.TV_ROUTE_DVBT2)) {
                opName.setTextColor(Color.WHITE);
            } else {
                opName.setTextColor(Color.GRAY);
            }
            tvSystem.setImageResource(R.drawable.dvbt_focus);
        } else if (opSysType == OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_CDSD
                .ordinal()) {
            if (mDtvRoute == TvChannelManager.TV_ROUTE_DVBC) {
                opName.setTextColor(Color.WHITE);
            } else {
                opName.setTextColor(Color.GRAY);
            }
            tvSystem.setImageResource(R.drawable.dvbc_focus);
        } else if (opSysType == OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_SDSD
                .ordinal()) {
            opName.setTextColor(Color.GRAY);
            tvSystem.setImageResource(R.drawable.dvbs_focus);
        } else {
            opName.setTextColor(Color.GRAY);
        }
        return convertView;
    }
}
