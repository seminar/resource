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

package com.mstar.tv.tvplayer.ui.holder;

import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.tuning.atsc.AtscATVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.ATVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.AutoTuneOptionActivity;
import com.mstar.tv.tvplayer.ui.tuning.ChannelTuning;
import com.mstar.tv.tvplayer.ui.tuning.DTVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.ExitTuningInfoDialog;
import com.mstar.tv.tvplayer.ui.tuning.OadScan;
import com.mstar.tv.tvplayer.ui.R;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewHolder {
    private AutoTuneOptionActivity autotune;

    private ChannelTuning channeltune;

    @SuppressWarnings("unused")
    private ExitTuningInfoDialog exittune;

    private DTVManualTuning dtvmanualtune;

    private ATVManualTuning atvmanualtune;

    private AtscATVManualTuning mAtscAtvManualTune;

    private OadScan mOadScan;

    // private DemoActivity demoactivity;
    // private scheduletime schedule;
    // private SettingActivity settingactivity;
    // private HdmiCecActivity hdmi;
    /*----------------for channelActivity------------------*/
    protected TextView text_cha_antennatype_val;

    protected LinearLayout linearlayout_cha_antennatype;

    protected LinearLayout linearlayout_cha_autotuning;

    protected LinearLayout linearlayout_cha_dtvmanualtuning;

    protected LinearLayout linearlayout_cha_atvmanualtuning;

    protected LinearLayout linearlayout_cha_programedit;

    protected LinearLayout linearlayout_cha_ciinformation;

    /*------------- for DVBS manaultuning ----------------*/

    public TextView mTextViewDtvmanualtuningGalaxy = null;

    public ComboButton mComboBtnDtvmanualtuningPolarization = null;

    public ComboButton mComboBtnDtvmanualtuningNetwork = null;

    public ComboButton mComboBtnDtvmanualtuningScanMode = null;

    public ComboButton mComboBtnDtvmanualtuningServiceType = null;

    /*---------------for autotuning-----------------------*/
    public LinearLayout linear_main;

    public LinearLayout linear_4dots;

    public TextView text_cha_autotuning_tuningtype_val;

    public TextView text_cha_autotuning_antenna_val;

    public TextView text_cha_airdtv_scanned_channels_val;

    public TextView text_cha_airtv_scanned_channels_val;

    public TextView text_cha_cabletv_scanned_channels_val;

    public LinearLayout linear_cha_autotuning_antenna_type;

    public RelativeLayout relative_search;

    public Button button_search;

    public RelativeLayout relative_country_choose;

    public LinearLayout linear_cha_autotuning_tuningtype;

    public Button button_cha_country_1;

    public Button button_cha_country_2;

    public Button button_cha_country_3;

    public Button button_cha_country_4;

    public Button button_cha_country_5;

    public Button button_cha_country_6;

    public Button button_cha_country_7;

    public Button button_cha_country_8;

    public Button button_cha_country_9;

    /*---------------for channeltuning-----------------------*/
    public LinearLayout linear_cha_mainlinear;

    public TextView text_cha_tvprogram_val;

    public TextView text_cha_dtvprogram_val;

    public LinearLayout linear_cha_radioprogram;

    public TextView text_cha_radioprogram_val;

    public TextView text_cha_tuningprogress_num;

    public LinearLayout linear_cha_dataprogram;

    public TextView text_cha_dataprogram_val;

    public TextView text_cha_tuningprogress_rf;

    public TextView text_cha_tuningprogress_ch;

    public TextView text_cha_tuningprogress_val;

    public TextView text_cha_tuningprogress_type;

    public LinearLayout linear_cha_airdtv;

    public LinearLayout linear_cha_airtv;

    public LinearLayout linear_cha_cabletv;

    public LinearLayout lineaR_cha_tvprogram;

    public LinearLayout lineaR_cha_dtvprogram;

    public ProgressBar progressbar_cha_tuneprogress;

    // /*--------------for exittuninginfo----------------------*/
    // protected TextView textview_cha_exittune_yes;
    // protected TextView textview_cha_exittune_no;
    /*-------------for dtvmanualtuning----------------------*/
    public LinearLayout linear_cha_dtvmanualtuning_channelnum;

    public TextView text_cha_dtvmanualtuning_channelnum_val;

    public TextView text_cha_dtvmanualtuning_modulation_val;

    public LinearLayout linear_cha_dtvmanualtuning_signalstrength_val;

    public LinearLayout linear_cha_dtvmanualtuning_signalquality_val;

    public TextView text_cha_dtvmanualtuning_tuningresult_dtv_val;

    public TextView text_cha_dtvmanualtuning_tuningresult_data_val;

    public TextView text_cha_dtvmanualtuning_tuningresult_radio_val;

    public TextView text_cha_dtvmanualtuning_symbol_val;

    public TextView text_cha_dtvmanualtuning_frequency_val;

    /*-------------for atscatvmanualtuning----------------------*/
    public LinearLayout linear_cha_atscatvmanualtuning_channelnum;

    public TextView text_cha_atscatvmanualtuning_channelnum_val;

    public LinearLayout linear_cha_atscatvmanualtuning_frequency;

    public TextView text_cha_atscatvmanualtuning_freqency_val;

    /*-------------for atvmanualtuning----------------------*/
    public TextView text_cha_atvmanualtuning_channelnum_val;

    public LinearLayout linear_cha_atvmanualtuning_colorsystem;

    public TextView text_cha_atvmanualtuning_colorsystem_val;

    public LinearLayout linear_cha_atvmanualtuning_soundsystem;

    public TextView text_cha_atvmanualtuning_soundsystem_val;

    public TextView text_cha_atvmanualtuning_freqency_val;

    // /*----------------for DemoActivity----------------------*/
    // protected TextView text_demo_mwe_val;
    // protected TextView text_demo_dbc_val;
    // protected TextView text_demo_dlc_val;
    // protected TextView text_demo_dcc_val;
    // protected TextView text_demo_nr_val;
    /*----------------for TimeActivity----------------------*/
    protected TextView text_time_sleep_val;

    protected TextView text_time_autotime_val;

    protected LinearLayout linear_time_schedule;

    /*----------------for scheduletime----------------------*/
    protected TextView text_time_schedule_ontime_val;

    protected TextView text_time_schedule_hour_val;

    protected TextView text_time_schedule_minute_val;

    protected TextView text_time_schedule_source_val;

    protected TextView text_time_schedule_channel_val;

    protected TextView text_time_schedule_volume_val;

    // /*----------------for SettingActivity----------------------*/
    // protected TextView text_set_language_val;
    // protected TextView text_set_menutime_val;
    // protected TextView text_set_switchmode_val;
    // protected TextView text_set_autosourceident_val;
    // protected TextView text_set_colorrange_val;
    // protected TextView text_set_moviemode_val;
    // protected TextView text_set_bluescreenswitch_val;
    // protected LinearLayout linear_set_hdmicec;
    // protected LinearLayout linear_set_powersetting;
    // protected LinearLayout linear_set_restoretodefault;
    /*------------------------for hdmicec---------------------*/
    protected TextView text_hdmicec_devicelist_val;

    protected TextView text_set_hdmicec_onoff_val;

    protected TextView text_set_hdmicec_autostandby_val;

    protected TextView text_set_hdmicec_arc_val;

    /*---------------for oad scan-----------------------*/
    public TextView text_oadscan;

    public LinearLayout linear_oadscan_progress;

    public LinearLayout linear_oadscan_progress_text;

    public TextView text_oadscan_progress_percent;

    public TextView text_oadscan_ch;

    public TextView text_oadscan_num;

    public ProgressBar bar_oadscan_progress;

    // public ViewHolder(ChannelActivity activity) {
    // this.channelactivity = activity;
    // }
    public ViewHolder(AutoTuneOptionActivity activity) {
        this.autotune = activity;
    }

    public ViewHolder(ChannelTuning activity) {
        this.channeltune = activity;
    }

    public ViewHolder(ExitTuningInfoDialog activity) {
        this.exittune = activity;
    }

    public ViewHolder(DTVManualTuning activity) {
        this.dtvmanualtune = activity;
    }

    public ViewHolder(AtscATVManualTuning activity) {
        this.mAtscAtvManualTune = activity;
    }

    public ViewHolder(ATVManualTuning activity) {
        this.atvmanualtune = activity;
    }

    public ViewHolder(OadScan activity) {
        this.mOadScan = activity;
    }

    // public ViewHolder(DemoActivity activity) {
    // this.demoactivity = activity;
    // }
    // public ViewHolder(scheduletime activity) {
    // this.schedule = activity;
    // }
    // public ViewHolder(SettingActivity activity) {
    // this.settingactivity = activity;
    // }
    // public ViewHolder(HdmiCecActivity activity)
    // {
    // this.hdmi = activity;
    // }

    /*
     * //----------------for channelActivity------------------ void findViews()
     * { text_cha_antennatype_val =
     * (TextView)channelactivity.findViewById(R.id.textview_cha_antennatype_val
     * ); linearlayout_cha_antennatype =
     * (LinearLayout)channelactivity.findViewById(
     * R.id.linearlayout_cha_antennatype); linearlayout_cha_autotuning =
     * (LinearLayout)channelactivity.findViewById(R
     * .id.linearlayout_cha_autotuning); linearlayout_cha_dtvmanualtuning =
     * (LinearLayout)channelactivity.findViewById
     * (R.id.linearlayout_cha_dtvmanualtuning); linearlayout_cha_atvmanualtuning
     * = (LinearLayout)channelactivity.findViewById
     * (R.id.linearlayout_cha_atvmanualtuning); linearlayout_cha_programedit =
     * (LinearLayout)channelactivity.findViewById(
     * R.id.linearlayout_cha_programedit); linearlayout_cha_ciinformation =
     * (LinearLayout)channelactivity.findViewById
     * (R.id.linearlayout_cha_ciinformation); }
     */
    /*---------------for autotuning-----------------------*/
    public void findViewsForAutoTuning() {
        linear_main = (LinearLayout) autotune.findViewById(R.id.linearlayout_main);
        linear_4dots = (LinearLayout) autotune.findViewById(R.id.linearlayout_4dots);
        linear_cha_autotuning_tuningtype = (LinearLayout) autotune
                .findViewById(R.id.linearlayout_cha_autotuning_tuningtype);
        text_cha_autotuning_tuningtype_val = (TextView) autotune
                .findViewById(R.id.textview_cha_autotuning_tuningtype_val);
        linear_cha_autotuning_antenna_type = (LinearLayout) autotune
                .findViewById(R.id.linearlayout_cha_autotuning_antenna_type);
        text_cha_autotuning_antenna_val = (TextView) autotune
                .findViewById(R.id.textview_cha_autotuning_antenna_val);
        relative_search = (RelativeLayout) autotune.findViewById(R.id.relativelayout_search);
        button_search = (Button) autotune.findViewById(R.id.button_search);
        relative_country_choose = (RelativeLayout) autotune
                .findViewById(R.id.relativelayout_country_choose);
        button_cha_country_1 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_australia);
        button_cha_country_2 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_austria);
        button_cha_country_3 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_beligum);
        button_cha_country_4 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_bulgaral);
        button_cha_country_5 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_croatia);
        button_cha_country_6 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_czech);
        button_cha_country_7 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_denmark);
        button_cha_country_8 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_finland);
        button_cha_country_9 = (Button) autotune
                .findViewById(R.id.button_cha_autotuning_choosecountry_france);
        lineaR_cha_tvprogram = (LinearLayout) autotune
                .findViewById(R.id.linearlayout_cha_tvprogram);
        lineaR_cha_dtvprogram = (LinearLayout) autotune
                .findViewById(R.id.linearlayout_cha_dtvprogram);
        linear_cha_airdtv = (LinearLayout) autotune.findViewById(R.id.linearlayout_cha_airdtv);
        linear_cha_airtv = (LinearLayout) autotune.findViewById(R.id.linearlayout_cha_airtv);
        linear_cha_cabletv = (LinearLayout) autotune.findViewById(R.id.linearlayout_cha_cabletv);
        text_cha_airdtv_scanned_channels_val = (TextView) autotune
                .findViewById(R.id.textview_cha_airdtv_scanned_channels_val);
        text_cha_airtv_scanned_channels_val = (TextView) autotune
                .findViewById(R.id.textview_cha_airtv_scanned_channels_val);
        text_cha_cabletv_scanned_channels_val = (TextView) autotune
                .findViewById(R.id.textview_cha_cabletv_scanned_channels_val);
    }

    /*---------------for channeltuning-----------------------*/
    public void findViewForChannelTuning() {
        linear_cha_mainlinear = (LinearLayout) channeltune
                .findViewById(R.id.linearlayout_cha_mainlinear);
        text_cha_tvprogram_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_tvprogram_val);
        text_cha_dtvprogram_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_dtvprogram_val);
        linear_cha_radioprogram = (LinearLayout) channeltune
                .findViewById(R.id.linearlayout_cha_radioprogram);
        text_cha_radioprogram_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_radioprogram_val);
        linear_cha_dataprogram = (LinearLayout) channeltune
                .findViewById(R.id.linearlayout_cha_dataprogram);
        text_cha_dataprogram_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_dataprogram_val);
        text_cha_tuningprogress_rf = (TextView) channeltune
                .findViewById(R.id.textview_cha_tuningprogress_rf);
        text_cha_tuningprogress_ch = (TextView) channeltune
                .findViewById(R.id.textview_cha_tuningprogress_ch);
        text_cha_tuningprogress_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_tuningprogress_percent);
        text_cha_tuningprogress_num = (TextView) channeltune
                .findViewById(R.id.textview_cha_tuningprogress_num);
        text_cha_tuningprogress_type = (TextView) channeltune
                .findViewById(R.id.textview_cha_tuningprogress_type);
        progressbar_cha_tuneprogress = (ProgressBar) channeltune
                .findViewById(R.id.progressbar_cha_tuningprogress);
        lineaR_cha_tvprogram = (LinearLayout) channeltune
                .findViewById(R.id.linearlayout_cha_tvprogram);
        lineaR_cha_dtvprogram = (LinearLayout) channeltune
                .findViewById(R.id.linearlayout_cha_dtvprogram);
        linear_cha_airdtv = (LinearLayout) channeltune.findViewById(R.id.linearlayout_cha_airdtv);
        linear_cha_airtv = (LinearLayout) channeltune.findViewById(R.id.linearlayout_cha_airtv);
        linear_cha_cabletv = (LinearLayout) channeltune.findViewById(R.id.linearlayout_cha_cabletv);
        text_cha_airdtv_scanned_channels_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_airdtv_scanned_channels_val);
        text_cha_airtv_scanned_channels_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_airtv_scanned_channels_val);
        text_cha_cabletv_scanned_channels_val = (TextView) channeltune
                .findViewById(R.id.textview_cha_cabletv_scanned_channels_val);

    }

    // /*--------------for exittuninginfo-----------------------*/
    // void findViewForExitTuningInfo() {
    // textview_cha_exittune_yes =
    // (TextView)exittune.findViewById(R.id.textview_cha_exittune_yes);
    // textview_cha_exittune_no =
    // (TextView)exittune.findViewById(R.id.textview_cha_exittune_no);
    // }
    /*--------------for dtvmanualtuning-----------------------*/
    public void findViewForDtvManualTuning() {
        linear_cha_dtvmanualtuning_channelnum = (LinearLayout) dtvmanualtune
                .findViewById(R.id.linearlayout_cha_dtvmanualtuning_channelnum);
        text_cha_dtvmanualtuning_channelnum_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_channelnum_val);
        text_cha_dtvmanualtuning_modulation_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_modulation_val);
        linear_cha_dtvmanualtuning_signalstrength_val = (LinearLayout) dtvmanualtune
                .findViewById(R.id.linearlayout_cha_dtvmanualtuning_signalstrength_val);
        linear_cha_dtvmanualtuning_signalquality_val = (LinearLayout) dtvmanualtune
                .findViewById(R.id.linearlayout_cha_dtvmanualtuning_signalquality_val);
        text_cha_dtvmanualtuning_tuningresult_dtv_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_tuningresult_dtv_val);
        text_cha_dtvmanualtuning_tuningresult_data_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_tuningresult_data_val);
        text_cha_dtvmanualtuning_tuningresult_radio_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_tuningresult_radio_val);
        text_cha_dtvmanualtuning_symbol_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_symbol_val);
        text_cha_dtvmanualtuning_frequency_val = (TextView) dtvmanualtune
                .findViewById(R.id.textview_cha_dtvmanualtuning_frequency_val);
    }

    /*--------------for atscatvmanualtuning-----------------------*/
    public void findViewForAtscATVManualTuning() {
        linear_cha_atscatvmanualtuning_channelnum = (LinearLayout) mAtscAtvManualTune
                .findViewById(R.id.linearlayout_cha_atscatvmanualtuning_channelnum);
        text_cha_atscatvmanualtuning_channelnum_val = (TextView) mAtscAtvManualTune
                .findViewById(R.id.textview_cha_atscatvmanualtuning_channelnum_val);
        linear_cha_atscatvmanualtuning_frequency = (LinearLayout) mAtscAtvManualTune
                .findViewById(R.id.linearlayout_cha_atscatvmanualtuning_frequency);
        text_cha_atscatvmanualtuning_freqency_val = (TextView) mAtscAtvManualTune
                .findViewById(R.id.textview_cha_atscatvmanualtuning_frequency_val);
    }

    /*--------------for atvmanualtuning-----------------------*/
    public void findViewForAtvManualTuning() {
        text_cha_atvmanualtuning_channelnum_val = (TextView) atvmanualtune
                .findViewById(R.id.textview_cha_atvmanualtuning_channelnum_val);
        linear_cha_atvmanualtuning_colorsystem = (LinearLayout) atvmanualtune
                .findViewById(R.id.linearlayout_cha_atvmanualtuning_colorsystem);
        text_cha_atvmanualtuning_colorsystem_val = (TextView) atvmanualtune
                .findViewById(R.id.textview_cha_atvmanualtuning_colorsystem_val);
        linear_cha_atvmanualtuning_soundsystem = (LinearLayout) atvmanualtune
                .findViewById(R.id.linearlayout_cha_atvmanualtuning_soundsystem);
        text_cha_atvmanualtuning_soundsystem_val = (TextView) atvmanualtune
                .findViewById(R.id.textview_cha_atvmanualtuning_soundsystem_val);
        text_cha_atvmanualtuning_freqency_val = (TextView) atvmanualtune
                .findViewById(R.id.textview_cha_atvmanualtuning_frequency_val);
    }

    /*--------------for oad scan-----------------------*/
    public void findViewForOadScan() {
        text_oadscan = (TextView) mOadScan.findViewById(R.id.textview_oadscan);
        linear_oadscan_progress = (LinearLayout) mOadScan
                .findViewById(R.id.linearlayout_oadscan_progress);
        linear_oadscan_progress_text = (LinearLayout) mOadScan
                .findViewById(R.id.linearlayout_oadscan_progress_text);
        text_oadscan_progress_percent = (TextView) mOadScan
                .findViewById(R.id.textview_oadscan_progress_percent);
        text_oadscan_ch = (TextView) mOadScan.findViewById(R.id.textview_oadscan_ch);
        text_oadscan_num = (TextView) mOadScan.findViewById(R.id.textview_oadscan_num);
        bar_oadscan_progress = (ProgressBar) mOadScan
                .findViewById(R.id.progressbar_oadscan_progress);
    }

    // /*--------------for DemoActivity-----------------------*/
    // void findViewForDemoActivity() {
    // text_demo_mwe_val =
    // (TextView)demoactivity.findViewById(R.id.textview_demo_mwe_val);
    // text_demo_dbc_val =
    // (TextView)demoactivity.findViewById(R.id.textview_demo_dbc_val);
    // text_demo_dlc_val =
    // (TextView)demoactivity.findViewById(R.id.textview_demo_dlc_val);
    // text_demo_dcc_val =
    // (TextView)demoactivity.findViewById(R.id.textview_demo_dcc_val);
    // text_demo_nr_val =
    // (TextView)demoactivity.findViewById(R.id.textview_demo_nr_val);
    // }
    /*--------------for TimeActivity-----------------------*/
    // void findViewForTimeActivity() {
    // text_time_sleep_val =
    // (TextView)timeactivity.findViewById(R.id.textview_time_sleep_val);
    // text_time_autotime_val =
    // (TextView)timeactivity.findViewById(R.id.textview_time_autotime_val);
    // linear_time_schedule =
    // (LinearLayout)timeactivity.findViewById(R.id.linearlayout_time_schedule);
    // }
    /*--------------for scheduletime-----------------------*/
    // void findViewForScheduleTime() {
    // text_time_schedule_ontime_val =
    // (TextView)schedule.findViewById(R.id.textview_time_schedule_ontime_val);
    // text_time_schedule_hour_val =
    // (TextView)schedule.findViewById(R.id.textview_time_schedule_hour_val);
    // text_time_schedule_minute_val =
    // (TextView)schedule.findViewById(R.id.textview_time_schedule_minute_val);
    // text_time_schedule_source_val =
    // (TextView)schedule.findViewById(R.id.textview_time_schedule_source_val);
    // text_time_schedule_channel_val =
    // (TextView)schedule.findViewById(R.id.textview_time_schedule_channel_val);
    // text_time_schedule_volume_val =
    // (TextView)schedule.findViewById(R.id.textview_time_schedule_volume_val);
    // }
    // /*--------------for SettingActivity-----------------------*/
    // void findViewForSettingActivity() {
    // text_set_language_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_language_val);
    // text_set_menutime_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_menutime_val);
    // text_set_switchmode_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_switchmode_val);
    // text_set_autosourceident_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_autosourceident_val);
    // text_set_colorrange_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_colorrange_val);
    // text_set_moviemode_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_moviemode_val);
    // text_set_bluescreenswitch_val =
    // (TextView)settingactivity.findViewById(R.id.textview_set_bluescreenswitch_val);
    // linear_set_hdmicec =
    // (LinearLayout)settingactivity.findViewById(R.id.linearlayout_set_hdmicec);
    // linear_set_powersetting =
    // (LinearLayout)settingactivity.findViewById(R.id.linearlayout_set_powersetting);
    // linear_set_restoretodefault =
    // (LinearLayout)settingactivity.findViewById(R.id.linearlayout_set_restoretodefault);
    // }
    /*--------------for SettingActivity-----------------------*/
    // void findViewForHdmiCec()
    // {
    // text_hdmicec_devicelist_val = (TextView) hdmi
    // .findViewById(R.id.textview_hdmicec_devicelist_val);
    // text_set_hdmicec_onoff_val = (TextView) hdmi
    // .findViewById(R.id.textview_set_hdmicec_onoff_val);
    // text_set_hdmicec_autostandby_val = (TextView) hdmi
    // .findViewById(R.id.textview_set_hdmicec_autostandby_val);
    // text_set_hdmicec_arc_val = (TextView)
    // hdmi.findViewById(R.id.textview_set_hdmicec_arc_val);
    // }

}
