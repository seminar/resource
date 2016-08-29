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

package com.mstar.tv.tvplayer.ui;

import java.util.Calendar;
import java.util.TimeZone;
import android.content.Context;
import android.util.Log;

import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTimeZone;

public class TimeZoneSetter {

    private final String TAG = "TimeZoneSetter";

    private final int HOURSECOND = 60 * 60000;

    private TvTimerManager timerManager = null;

    public TimeZoneSetter(Context context) {
        timerManager = TvTimerManager.getInstance();
    }

    public void updateTimeZone() {
        int minoffset = getMinuteOffset();
        int houroffset = getHourOffset();

        Log.e(TAG, "getMinuteOffset() = " + minoffset);
        Log.e(TAG, "getHourOffset() = " + houroffset);

        if (minoffset == 0) {
            switch (houroffset) {
                case -11:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS11_START, true);
                    break;
                case -10:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS10_START, true);
                    break;
                case -9:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS9_START, true);
                    break;
                case -8:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS8_START, true);
                    break;
                case -7:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS7_START, true);
                    break;
                case -6:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS6_START, true);
                    break;
                case -5:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS5_START, true);
                    break;
                case -4:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS4_START, true);
                    break;
                case -3:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS3_START, true);
                    break;
                case -2:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS2_START, true);
                    break;
                case -1:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS1_START, true);
                    break;
                case 0:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_0_START, true);
                    break;
                case 1:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_1_START, true);
                    break;
                case 2:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_2_START, true);
                    break;
                case 3:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_3_START, true);
                    break;
                case 4:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_4_START, true);
                    break;
                case 5:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_5_START, true);
                    break;
                case 6:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_6_START, true);
                    break;
                case 7:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_7_START, true);
                    break;
                case 8:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_8_START, true);
                    break;
                case 9:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_9_START, true);
                    break;
                case 10:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_10_START, true);
                    break;
                case 11:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_11_START, true);
                    break;
                case 12:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_12_START, true);
                    break;
                case 13:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_13_START, true);
                    break;
                default:
                    Log.e(TAG, "this case is minutes offset 0. default case: set time zone fail!");
                    break;
            }
        } else if (minoffset == 30 || minoffset == -30) {
            switch (houroffset) {
                case -4:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS4_5_START, true);
                    break;
                case -3:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS3_5_START, true);
                    break;
                case -2:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_MINUS2_5_START, true);
                    break;
                case 3:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_3POINT5_START, true);
                    break;
                case 4:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_4POINT5_START, true);
                    break;
                case 5:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_5POINT5_START, true);
                    break;
                case 6:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_6POINT5_START, true);
                    break;
                case 9:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_9POINT5_START, true);
                    break;
                case 10:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_10POINT5_START, true);
                    break;
                default:
                    Log.e(TAG, "this case is minutes offset 30. default case: set timezone fail!");
                    break;
            }
        } else if (minoffset == 45 || minoffset == -45) {
            switch (houroffset) {
                case 5:
                    timerManager.setTimeZone(EnumTimeZone.E_TIMEZONE_GMT_5POINT45_START, true);
                    break;
                default:
                    Log.e(TAG, "this case is minutes offset 45. default case: set timezone fail!");
                    break;
            }
        } else {
            Log.e(TAG, "this case is minutes offset unknown. set timezone fail!");
        }
    }

    private int getHourOffset() {
        String id = TimeZone.getDefault().getID();
        long date = Calendar.getInstance().getTimeInMillis();
        TimeZone tz = TimeZone.getTimeZone(id);
        int offset = tz.getOffset(date);
        int houroffset = offset / HOURSECOND;
        return houroffset;
    }

    private int getMinuteOffset() {
        String id = TimeZone.getDefault().getID();
        long date = Calendar.getInstance().getTimeInMillis();
        TimeZone tz = TimeZone.getTimeZone(id);
        int offset = tz.getOffset(date);
        int minoffset = offset / 60000;
        minoffset %= 60;
        return minoffset;
    }
}
