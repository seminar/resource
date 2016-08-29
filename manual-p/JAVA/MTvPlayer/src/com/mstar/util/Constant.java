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

public class Constant {
    // short
    public static final short CDCA_RC_OK = 0x00;
    public static final short CDCA_RC_UNKNOWN = 0x01;
    public static final short CDCA_RC_POINTER_INVALID = 0x02;
    public static final short CDCA_RC_CARD_INVALID = 0x03;
    public static final short CDCA_RC_PIN_INVALID = 0x04;
    public static final short CDCA_RC_DATASPACE_SMALL = 0x06;
    public static final short CDCA_RC_CARD_PAIROTHER = 0x07;
    public static final short CDCA_RC_DATA_NOT_FIND = 0x08;
    public static final short CDCA_RC_PROG_STATUS_INVALID = 0x09;
    public static final short CDCA_RC_CARD_NO_ROOM = 0x0A;
    public static final short CDCA_RC_WORKTIME_INVALID = 0x0B;
    public static final short CDCA_RC_IPPV_CANNTDEL = 0x0C;
    public static final short CDCA_RC_CARD_NOPAIR = 0x0D;
    public static final short CDCA_RC_WATCHRATING_INVALID = 0x0E;
    public static final short CDCA_RC_CARD_NOTSUPPORT = 0x0F;
    public static final short CDCA_RC_DATA_ERROR = 0x10;
    public static final short CDCA_RC_FEEDTIME_NOT_ARRIVE = 0x11;
    public static final short CDCA_Detitle_All_Read = 0x00;
    public static final short CDCA_Detitle_Received = 0x01;
    public static final short CDCA_Detitle_Space_Small = 0x02;
    public static final short CDCA_Detitle_Ignore = 0x03;
    // boolean
    public static boolean lockKey = true;
    // String
    public static final String OPMODE = "OP_MODE";
    public static final String TUNER_AVAIABLE = "TUNER_AVAILABLE";
    public static final String LNBOPTION_PAGETYPE = "LNBOPTION_PAGETYPE";
    public static final String LNBOPTION_EDITOR_PAGETYPE = "LNBOPTION_EDITOR_PAGETYPE";
    public static final String LNBOPTION_EDITOR_ACTIONTYPE = "LNBOPTION_EDITOR_ACTIONTYPE";
    public static final String LNBOPTION_EDITOR_INDEX = "LNBOPTION_EDITOR_INDEX";
    public static final String LNBOPTION_MOTOR_ACTIONTYPE = "LNBOPTION_MOTOR_ACTIONTYPE";
    public static final String LNBMOTOR_EDITOR_DISEQC_VERSION = "DISEQC_VERSION";
    public static final String LNBMOTOR_EDITOR_DISEQC_1_2 = "1.2";
    public static final String LNBMOTOR_EDITOR_DISEQC_1_3 = "1.3";
    public static final String LNBOPTION_MOTOR_FOCUS = "LNBOPTION_MOTOR_FOCUS";
    public static final String PREFERENCES_TV_SETTING = "TvSetting";
    public static final String PREFERENCES_IS_AUTOSCAN_LAUNCHED = "autoTuningLaunchedBefore";
    public static final String TV_EVENT_LISTENER_READY = "TVEventListenerReady";
    // float
    public static final float CCKEY_TEXTSIZE = 40.0f;
    public static final float CCKEY_ALPHA = 0.6f;
    // int
    public static final int TV_SCREENSAVER_NOSIGNAL = 80;
    public static final int ROOTACTIVITY_RESUME_MESSAGE = 800;
    public static final int CEC_STATUS_ON = 1;
    public static final int CHANNEL_LOCK_RESULT_CODE = 100;
    public static final int LNBOPTION_PAGETYPE_INVALID = -1;
    public static final int LNBOPTION_PAGETYPE_SATELLITE = 0;
    public static final int LNBOPTION_PAGETYPE_TRANSPONDER = 1;
    public static final int LNBOPTION_PAGETYPE_FREQUENCIES = 2;
    public static final int LNBOPTION_PAGETYPE_MOTOR = 3;
    public static final int LNBOPTION_PAGETYPE_SINGLECABLE = 4;
    public static final int LNBOPTION_EDITOR_ACTION_INVALID = -1;
    public static final int LNBOPTION_EDITOR_ACTION_ADD = 0;
    public static final int LNBOPTION_EDITOR_ACTION_EDIT = 1;
    public static final int LNBOPTION_MOTOR_NONE = 0;
    public static final int LNBOPTION_MOTOR_1_2 = 1;
    public static final int LNBOPTION_MOTOR_1_3 = 2;
    public static final int LNBOPTION_MOTOR_ACTION_INVALID = -1;
    public static final int LNBOPTION_MOTOR_ACTION_POSITION = 0;
    public static final int LNBOPTION_MOTOR_ACTION_LIMIT = 1;
    public static final int LNBOPTION_MOTOR_ACTION_LOCATION = 2;
    // class
    public class SignalProgSyncStatus {

        public static final int NOSYNC = 0x00;

        public static final int STABLE_SUPPORT_MODE = 0x01;

        public static final int STABLE_UN_SUPPORT_MODE = 0x02;

        public static final int UNSTABLE = 0x03;

        public static final int AUTO_ADJUST =0x04;
    };

    public class ScreenSaverMode {

        public static final int DTV_SS_INVALID_SERVICE = 0x00;

        public static final int DTV_SS_NO_CI_MODULE = 0x01;

        public static final int DTV_SS_CI_PLUS_AUTHENTICATION = 0x02;

        public static final int DTV_SS_SCRAMBLED_PROGRAM = 0x03;

        public static final int DTV_SS_CH_BLOCK = 0x04;

        public static final int DTV_SS_PARENTAL_BLOCK = 0x05;

        public static final int DTV_SS_AUDIO_ONLY = 0x06;

        public static final int DTV_SS_DATA_ONLY = 0x07;

        public static final int DTV_SS_COMMON_VIDEO = 0x08;

        public static final int DTV_SS_UNSUPPORTED_FORMAT = 0x09;

        public static final int DTV_SS_INVALID_PMT = 0x0A;

        public static final int DTV_SS_MAX = 0x0B;

        public static final int DTV_SS_CA_NOTIFY = 0x0C;
    };
}
