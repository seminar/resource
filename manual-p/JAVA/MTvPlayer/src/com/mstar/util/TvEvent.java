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

package com.mstar.util;

public class TvEvent {

    public static final int DTV_CHANNELNAME_READY = 0;

    public static final int ATV_AUTO_TUNING_SCAN_INFO = 1;

    public static final int ATV_MANUAL_TUNING_SCAN_INFO = 2;

    public static final int DTV_AUTO_TUNING_SCAN_INFO = 3;

    public static final int DTV_PROGRAM_INFO_READY = 4;

    public static final int SIGNAL_LOCK = 5;

    public static final int SIGNAL_UNLOCK = 6;

    public static final int POPUP_DIALOG = 7;

    public static final int SCREEN_SAVER_MODE = 8;

    public static final int CI_LOAD_CREDENTIAL_FAIL = 9;

    public static final int EPGTIMER_SIMULCAST = 10;

    public static final int HBBTV_STATUS_MODE = 11;

    public static final int MHEG5_STATUS_MODE = 12;

    public static final int MHEG5_RETURN_KEY = 13;

    public static final int OAD_HANDLER = 14;

    public static final int OAD_DOWNLOAD = 15;

    public static final int PVR_NOTIFY_PLAYBACK_TIME = 16;

    public static final int PVR_NOTIFY_PLAYBACK_SPEED_CHANGE = 17;

    public static final int PVR_NOTIFY_RECORD_TIME = 18;

    public static final int PVR_NOTIFY_RECORD_SIZE = 19;

    public static final int PVR_NOTIFY_RECORD_STOP = 20;

    public static final int PVR_NOTIFY_PLAYBACK_STOP = 21;

    public static final int PVR_NOTIFY_PLAYBACK_BEGIN = 22;

    public static final int PVR_NOTIFY_TIMESHIFT_OVERWRITES_BEFORE = 23;

    public static final int PVR_NOTIFY_TIMESHIFT_OVERWRITES_AFTER = 24;

    public static final int PVR_NOTIFY_OVER_RUN = 25;

    public static final int PVR_NOTIFY_USB_REMOVED = 26;

    public static final int PVR_NOTIFY_CI_PLUS_PROTECTION = 27;

    public static final int PVR_NOTIFY_PARENTAL_CONTROL = 28;

    public static final int PVR_NOTIFY_ALWAYS_TIMESHIFT_PROGRAM_READY = 29;

    public static final int PVR_NOTIFY_ALWAYS_TIMESHIFT_PROGRAM_NOTREADY = 30;

    public static final int PVR_NOTIFY_CI_PLUS_RETENTION_LIMIT_UPDATE = 31;

    public static final int DTV_AUTO_UPDATE_SCAN = 32;

    public static final int TS_CHANGE = 33;

    public static final int POPUP_SCAN_DIALOGE_LOSS_SIGNAL = 34;

    public static final int POPUP_SCAN_DIALOGE_NEW_MULTIPLEX = 35;

    public static final int POPUP_SCAN_DIALOGE_FREQUENCY_CHANGE = 36;

    public static final int RCT_PRESENCE = 37;

    public static final int CHANGE_TTX_STATUS = 38;

    public static final int DTV_PRI_COMPONENT_MISSING = 39;

    public static final int AUDIO_MODE_CHANGE = 40;

    public static final int MHEG5_EVENT_HANDLER = 41;

    public static final int OAD_TIMEOUT = 42;

    public static final int GINGA_STATUS_MODE = 43;

    public static final int HBBTV_UI_EVENT = 44;

    public static final int ATV_PROGRAM_INFO_READY = 45;

    public static final int CI_OP_REFRESH_QUERY = 46;

    public static final int CI_OP_SERVICE_LIST = 47;

    public static final int CI_OP_EXIT_SERVICE_LIST = 48;
}
