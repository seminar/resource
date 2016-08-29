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

package com.mstar.tv.tvplayer.ui.dtv;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.android.tvapi.dtv.vo.DtvAudioInfo;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;

public class AudioLanguageActivity extends MstarBaseActivity {
    private ListView audioLanguageListView = null;

    private short audioLangCount = 0;

    private TimeOutHelper timeOutHelper;

    private TvChannelManager mTvChannelManager = null;

    private DtvAudioInfo audioInfo = null;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TimeOutHelper.getTimeOutMsg()) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_language_list_view);
        audioLanguageListView = (ListView) findViewById(R.id.audio_language_list_view);
        TextView title = (TextView) findViewById(R.id.audio_language_title);
        String str = getResources().getString(R.string.str_audio_language);
        title.setText(str);
        mTvChannelManager = TvChannelManager.getInstance();
        audioInfo = new DtvAudioInfo();
        audioInfo = mTvChannelManager.getAudioInfo();

        if (audioInfo.audioLangNum == 0) {
            // show unknown
            audioLangCount = 1;
        } else {
            audioLangCount = audioInfo.audioLangNum;
        }

        String[] audioLang = new String[audioLangCount];
        int[] audioType = new int[audioLangCount];
        int[] audioMode = new int[audioLangCount];
        int[] audioTypeIso = new int[audioLangCount];

        for (short i = 0; i < audioLangCount; i++) {
            audioType[i] = 0;
            audioMode[i] = 0;
            audioTypeIso[i] = 0;
        }

        if (audioInfo.audioLangNum == 0) {
            audioLang[0] = getResources().getString(R.string.str_sound_format_unknown);
        }

        for (short i = 0; i < audioInfo.audioLangNum; i++) {
            audioLang[i] = mTvChannelManager.getLanguageNameByCode(String
                    .valueOf(audioInfo.audioInfos[i].isoLangInfo.isoLangInfo));

            audioType[i] = audioInfo.audioInfos[i].audioType;
            audioMode[i] = audioInfo.audioInfos[i].isoLangInfo.audioMode;
            audioTypeIso[i] = audioInfo.audioInfos[i].isoLangInfo.audioType;
        }

        // adapter = new ArrayAdapter<String>(this,
        // R.layout.pvr_menu_info_list_view_item, audioLang);
        // audioLanguageListView.setAdapter(adapter);
        audioLanguageListView.setAdapter(new AudioAdapter(audioLang, audioType, audioMode,
                audioTypeIso));
        audioLanguageListView.setDividerHeight(0);
        int index = audioInfo.currentAudioIndex;
        if (index < 0 || index >= audioInfo.audioLangNum) {
            index = 0;
        }
        audioLanguageListView.setSelection(index);
        audioLanguageListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int postion, long arg3) {
                System.out.println("\n=====>Set audio language=\n");
                mTvChannelManager.switchAudioTrack(postion);
                mTvChannelManager.setAudioLanguageDefaultValue(mTvChannelManager
                        .getLanguageIdByCode(String
                                .valueOf(audioInfo.audioInfos[postion].isoLangInfo.isoLangInfo)));
            }
        });

        timeOutHelper = new TimeOutHelper(handler, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeOutHelper.start();
        timeOutHelper.init();
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        timeOutHelper.stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        timeOutHelper.reset();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_PROG_RED:
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class AudioAdapter extends BaseAdapter {

        private String[] audioLang = null;

        private int[] audioTypeText = null;

        private int[] audioModeText = null;

        private int[] audioTypeIsoText = null;

        public AudioAdapter(String[] audioName, int[] audioSort, int[] audioMode, int[] audioTypeIso) {
            audioLang = audioName;
            audioTypeText = audioSort;
            audioModeText = audioMode;
            audioTypeIsoText = audioTypeIso;
        }

        @Override
        public int getCount() {
            return audioLangCount;
        }

        @Override
        public Object getItem(int arg0) {
            return audioLang[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                LayoutInflater layout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layout.inflate(R.layout.mts_info_list_view_item, parent, false);
            }
            ImageView AudioTypeImage = (ImageView) view.findViewById(R.id.mts_audio_info_type);
            switch (audioTypeText[position]) {
                case 0:
                    // MPEG
                    AudioTypeImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_mpeg));
                    break;

                case 1:
                    // AC3
                    AudioTypeImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_dolby));
                    break;

                case 3:
                    // AAC
                    AudioTypeImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_aac));
                    break;

                case 4:
                    // AC3P
                    AudioTypeImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_dolbyp));
                    break;

                default:
                    break;
            }

            ImageView AudioTypeIsoImage = (ImageView) view
                    .findViewById(R.id.mts_audio_info_type_iso);
            switch (audioTypeIsoText[position]) {
                case 1:
                    // CLEAN_EFFECTS
                    AudioTypeIsoImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_ce));
                    AudioTypeIsoImage.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    // HEARING_IMPAIRED
                    AudioTypeIsoImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_hi));
                    AudioTypeIsoImage.setVisibility(View.VISIBLE);
                    break;

                case 3:
                    // VISUAL_IMPAIRED
                    AudioTypeIsoImage.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_vi));
                    AudioTypeIsoImage.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

            ImageView AudioModeImageL = (ImageView) view.findViewById(R.id.mts_audio_info_left);
            ImageView AudioModeImageR = (ImageView) view.findViewById(R.id.mts_audio_info_right);
            switch (audioModeText[position]) {
                case 0:
                    // STEREO
                    AudioModeImageL.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_left));
                    AudioModeImageR.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_right));
                    break;

                case 1:
                    // LL
                    AudioModeImageL.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_left));
                    AudioModeImageR.setImageDrawable(null);
                    break;

                case 2:
                    // RR
                    AudioModeImageL.setImageDrawable(null);
                    AudioModeImageR.setImageDrawable(getResources().getDrawable(
                            R.drawable.mts_audio_right));
                    break;

                default:
                    break;

            }

            TextView text = (TextView) view.findViewById(R.id.mts_audio_info_text);
            text.setText(audioLang[position]);
            return view;
        }

    }
}
