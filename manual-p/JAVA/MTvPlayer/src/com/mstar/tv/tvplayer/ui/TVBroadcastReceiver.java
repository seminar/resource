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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;
import com.mstar.util.Constant;

public class TVBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TVBroadcastReceiver", "onReceive" + intent.getAction());
        String action = intent.getAction();
        if (action.equals("com.mstar.tv.fromhotkey")) {
            TVRootApp.setSourceDirty(true);
        } else if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
            new TimeZoneSetter(context).updateTimeZone();
        } else if (action.equals("com.android.settings")) {
            int value = intent.getIntExtra("lang", 0);
            TvCommonManager.getInstance().setOsdLanguage(value);
        } else if (action.equals("android.intent.action.LOCALE_CHANGED")) {
            if(TvCecManager.getInstance().getCecConfiguration().cecStatus == Constant.CEC_STATUS_ON) {
                TvCecManager.getInstance().setMenuLanguage(getCurrentLangToEnum(context));
            }
        }
    }
    private EnumLanguage getCurrentLangToEnum(Context context) {
        Configuration conf = context.getResources().getConfiguration();
        String language = conf.locale.getLanguage();

        if (language.equals("en_CA")) {
            return EnumLanguage.E_ENGLISH;
        } else if (language.equals("fr_CA")) {
            return EnumLanguage.E_ENGLISH;
        } else if (language.equals("zh_CN")) {
            return EnumLanguage.E_MANDARIN;
        } else if (language.equals("zh")) {
            return EnumLanguage.E_CHINESE;
        } else if (language.equals("en")) {
            return EnumLanguage.E_ENGLISH;
        } else if (language.equals("fr_FR")) {
            return EnumLanguage.E_FRENCH;
        } else if (language.equals("fr")) {
            return EnumLanguage.E_FRENCH;
        } else if (language.equals("de")) {
            return EnumLanguage.E_GERMAN;
        } else if (language.equals("de_DE")) {
            return EnumLanguage.E_GERMAN;
        } else if (language.equals("it")) {
            return EnumLanguage.E_ITALIAN;
        } else if (language.equals("it_IT")) {
            return EnumLanguage.E_ITALIAN;
        } else if (language.equals("ja_JP")) {
            return EnumLanguage.E_JAPANESE;
        } else if (language.equals("ja")) {
            return EnumLanguage.E_JAPANESE;
        } else if (language.equals("ko_KR")) {
            return EnumLanguage.E_KOREAN;
        } else if (language.equals("ko")) {
            return EnumLanguage.E_KOREAN;
        } else if (language.equals("zh_CN")) {
            return EnumLanguage.E_ENGLISH;
        } else if (language.equals("ROOT")) {
            return EnumLanguage.E_ENGLISH;
        } else if (language.equals("zh_CN")) {
            return EnumLanguage.E_MANDARIN;
        } else if (language.equals("zh_TW")) {
            return EnumLanguage.E_CHINESE;
        } else if (language.equals("en_GB")) {
            return EnumLanguage.E_MANDARIN;
        } else if (language.equals("en_US")) {
            return EnumLanguage.E_ENGLISH;
        }
        return EnumLanguage.E_UNKNOWN;
    }
}
