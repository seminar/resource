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

package com.mstar.tv.tvplayer.ui.pvr;

import com.mstar.tv.tvplayer.ui.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.android.tv.TvPvrManager;

public class PVRMenuInfoActivity extends MstarBaseActivity {
    private String[] data = new String[] {};
    private ArrayAdapter<String> adapter = null;
    private ListView listView = null;
    private static boolean isDiskSelectPage = true;
    private TvPvrManager mPvrManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // TODO Auto-generated method stub
        mPvrManager = TvPvrManager.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pvr_menu_info);
        listView = (ListView) findViewById(R.id.pvr_menu_info_list_view);
        TextView title = (TextView) findViewById(R.id.pvr_menu_info_subtitle);
        adapter = new ArrayAdapter<String>(this,
                R.layout.pvr_menu_info_list_view_item, data);
        listView.setAdapter(adapter);
        listView.setDividerHeight(0);
        String titleStr = savedInstanceState
                .getString("mstar.tvsetting.ui.PVRMenuInfoActivity");
        title.setText(titleStr);
        if (titleStr.equals("Select Disk")) {
            updateUsbDeviceAndPartitionInfoList();
            isDiskSelectPage = true;
        } else if (titleStr.equals("Time Shift Size")) {
            // TODO no list
            adapter.clear();
            isDiskSelectPage = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode) {
            case KeyEvent.KEYCODE_L:
                if (isDiskSelectPage) {
                    selectUsbDisk(true);
                } else {
                    // TODO change time shift size for recorder
                    selectTimeShiftSize(true);
                }
                return true;
            case KeyEvent.KEYCODE_R:
                if (isDiskSelectPage) {
                    selectUsbDisk(false);
                } else {
                    // TODO change time shift size for recorder
                    selectTimeShiftSize(false);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void updateUsbDeviceAndPartitionInfoList(){
        // clear all
        adapter.clear();
        int idx = 0;
        int partitionCount = 0;
        String tmpStr = null;
        // usb device name, store at index 0
        tmpStr = "USB " + mPvrManager.getUsbDeviceIndex();
        adapter.insert(tmpStr, 0);
        // usb partition name, store from index 1
        partitionCount = mPvrManager.getUsbPartitionNumber();
        for (idx = 0; idx < partitionCount; idx++) {
            tmpStr = "DISK " + idx;
            adapter.insert(tmpStr, (idx + 1));
        }
        // invalidate page
        adapter.notifyDataSetChanged();
        listView.invalidate();
    }

    public void selectUsbDisk(boolean isLeftKey){
        int curDeviceIndex = 0;
        int totalDeviceCount = 0;
        curDeviceIndex = mPvrManager.getUsbDeviceIndex();
        totalDeviceCount = mPvrManager.getUsbDeviceNumber();
        mPvrManager = TvPvrManager.getInstance();

        if (isLeftKey == true) {
            if (curDeviceIndex > 0) {
                curDeviceIndex--;
            } else {
                curDeviceIndex = mPvrManager.getUsbDeviceNumber() - 1;
            }
        } else {
            if (curDeviceIndex < (totalDeviceCount - 1)) {
                curDeviceIndex++;
            } else {
                curDeviceIndex = 0;
            }
        }
        mPvrManager.changeDevice((short) curDeviceIndex);
        updateUsbDeviceAndPartitionInfoList();
    }

    public void selectTimeShiftSize(boolean isLeftKey){
        // clear all
        adapter.clear();
        // TODO select time shift size!!
        // invalidate page
        adapter.notifyDataSetChanged();
        listView.invalidate();
    }
}
