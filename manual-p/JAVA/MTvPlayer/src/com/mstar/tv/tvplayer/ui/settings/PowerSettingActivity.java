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

package com.mstar.tv.tvplayer.ui.settings;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.android.tv.TvFactoryManager;

public class PowerSettingActivity extends Activity {
    private String[] powermusictype = {
            "Off", "Default", "Music1"
    };

    private String[] powerlogotype = {
            "Off", "Default", "Cap1", "Cap2"
    };

    private TvFactoryManager tvFactoryManager;

    private boolean defmusicflag = false;

    private boolean music1flag = false;

    private boolean deflogoflag = false;

    private boolean cap1flag = false;

    private boolean cap2flag = false;

    private ComboButton comboBtnMusic;

    private ComboButton comboBtnLogo;

    private SeekBarButton seekBarBtnVolume;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powersetting);
        tvFactoryManager = TvFactoryManager.getInstance();
        setOnTouchListeners();
        defmusicflag = findFileFromCustomer("boot0.mp3");
        music1flag = findFileFromCustomer("boot1.mp3");
        deflogoflag = findFileFromCustomer("boot0.jpg");
        cap1flag = findFileFromCustomer("boot1.jpg");
        cap2flag = findFileFromCustomer("boot2.jpg");
        Log.i("wxy","--deflogoflag="+deflogoflag+"----cap1flag="+cap1flag+"----cap2flag="+cap2flag);
        comboBtnMusic = new ComboButton(PowerSettingActivity.this, powermusictype,
                R.id.linearlayout_set_powersetting_powermusic, false) {
            @Override
            public void doUpdate() {
                if (comboBtnMusic.getIdx() == 0) {
                    seekBarBtnVolume.setFocusable(false);
                } else {
                    seekBarBtnVolume.setFocusable(true);
                }
                tvFactoryManager.setPowerOnMusicMode(comboBtnMusic.getIdx());
            }
        };

        comboBtnLogo = new ComboButton(PowerSettingActivity.this, powerlogotype,
                R.id.linearlayout_set_powersetting_powerlogo, false) {
            @Override
            public void doUpdate() {
                if (comboBtnLogo.getIdx() == 0) {
                    comboBtnMusic.setFocusable(false); // if logo is set off,
                                                       // music can be set on!
                    seekBarBtnVolume.setFocusable(false);
                } else {
                    comboBtnMusic.setFocusable(true);
                    seekBarBtnVolume.setFocusable(true);
                }
                Log.i("wxy","----LogoIndex="+comboBtnLogo.getIdx()+"------");
                tvFactoryManager.setPowerOnLogoMode(comboBtnLogo.getIdx());
            }

        };

        seekBarBtnVolume = new SeekBarButton(PowerSettingActivity.this,
                R.id.linearlayout_set_powersetting_powervolume, 1, false) {
            @Override
            public void doUpdate() {
                tvFactoryManager.setEnvironmentPowerOnMusicVolume((int) seekBarBtnVolume
                        .getProgress());
            }
        };

        LoadDataToUI();
        setOnFocusChangeListeners();
    }

    private boolean findFileFromCustomer(String filename) {
        // TODO Find File From Customer function
        boolean ret = false;
        File file = new File("/tvconfig/" + filename);
        if (file != null && file.exists()) {
            ret = true;
        }
        return ret;
    }

    private void LoadDataToUI() {
        LoadMusicData();
        LoadLogoData();
        LoadVolumeData();
    }

    private void LoadMusicData() {
        int musicmode = 0;
        musicmode = tvFactoryManager.getPowerOnMusicMode();

        if ((musicmode == 1 && !defmusicflag) || (musicmode == 2 && !music1flag)) {
            comboBtnMusic.setIdx(0);
        } else {
            comboBtnMusic.setIdx(musicmode);
        }
        comboBtnMusic.setItemEnable(1, defmusicflag);
        comboBtnMusic.setItemEnable(2, music1flag);
    }

    private void LoadLogoData() {
        int logomode = 0;
        logomode = tvFactoryManager.getPowerOnLogoMode();
        comboBtnLogo.setIdx(logomode);
        comboBtnLogo.setItemEnable(1, deflogoflag);
        comboBtnLogo.setItemEnable(2, cap1flag);
        comboBtnLogo.setItemEnable(3, cap2flag);
    }

    private void LoadVolumeData() {
        short volume = 0;
        volume = (short) tvFactoryManager.getEnvironmentPowerOnMusicVolume();
        seekBarBtnVolume.setProgress(volume);
    }

    private void setOnFocusChangeListeners() {
        OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                LinearLayout container = (LinearLayout) v;
                if (hasFocus) {
                    container.getChildAt(2).setVisibility(View.VISIBLE);
                } else {
                    container.getChildAt(2).setVisibility(View.GONE);
                }
            }
        };
        seekBarBtnVolume.setOnFocusChangeListener(seekBarBtnFocusListenser);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                Intent intent = new Intent(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.SETTING_PAGE);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setOnTouchListeners() {
        setMyOntouchListener(findViewById(R.id.linearlayout_set_powersetting_powermusic));
        setMyOntouchListener(findViewById(R.id.linearlayout_set_powersetting_powerlogo));
        setMyOntouchListener(findViewById(R.id.linearlayout_set_powersetting_powervolume));
    }

    private void setMyOntouchListener(View view) {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    v.requestFocus();
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                }
                return true;
            }
        });
    }
}
