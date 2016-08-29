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

package com.mstar.tv.tvplayer.ui.dtv.parental.atsc;

import com.mstar.tv.tvplayer.ui.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType.EnumUsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaTvRatingInformation;
import android.content.Intent;

public class UsaParLocksActivity extends BaseActivity {

    private List<ImageButtonItem> movieRatingList;

    private List<ImageButtonItem> tvRatingALLList;

    private List<ImageButtonItem> tvRatingLList;

    private List<ImageButtonItem> tvRatingSList;

    private List<ImageButtonItem> tvRatingVList;

    private List<ImageButtonItem> tvRatingDList;

    private List<ImageButtonItem> tvRatingPGList;

    private List<ImageButtonItem> tvRating14List;

    private List<ImageButtonItem> tvRatingMAList;

    private ImageButton islock_movie_noneImg;

    private ImageButton islock_tv_noneImg;

    private ImageButtonItem islock_tv_y7_fv_item;

    private ImageButtonItem item_nr;

    private TvAtscChannelManager manager;

    private UsaMpaaRatingType mUsaMpaaRatingType;

    private UsaTvRatingInformation mUsaTvRatingInfo;

    private static int LOCK = 1;

    private static int UNLOCK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_usaparlocks);

        movieRatingList = new ArrayList<ImageButtonItem>();
        tvRatingALLList = new ArrayList<ImageButtonItem>();
        tvRatingLList = new ArrayList<ImageButtonItem>();
        tvRatingSList = new ArrayList<ImageButtonItem>();
        tvRatingVList = new ArrayList<ImageButtonItem>();
        tvRatingDList = new ArrayList<ImageButtonItem>();
        tvRatingPGList = new ArrayList<ImageButtonItem>();
        tvRating14List = new ArrayList<ImageButtonItem>();
        tvRatingMAList = new ArrayList<ImageButtonItem>();

        initView();
        setListener();
    }

    private void initView() {
        // FIXME: clean magic#, replace enum with final variable
        manager = TvAtscChannelManager.getInstance();
        mUsaMpaaRatingType = manager.getUsaMpaaRatingLock();
        int ordinal = mUsaMpaaRatingType.enUaMpaaRatingType.ordinal();
        ImageButtonItem item = null;
        islock_movie_noneImg = (ImageButton) findViewById(R.id.islock_movie_none_img);
        ImageButton islock_movie_gImg = (ImageButton) findViewById(R.id.islock_movie_g_img);
        item = new ImageButtonItem(islock_movie_gImg, ordinal == 1 ? LOCK : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                if (mCurrValue == LOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_G;
                    for (int i = 1; i < movieRatingList.size(); i++) {
                        movieRatingList.get(i).updateUI(LOCK);
                    }
                } else {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_PG;
                }
            }
        };
        movieRatingList.add(item);
        ImageButton islock_movie_pgImg = (ImageButton) findViewById(R.id.islock_movie_pg_img);
        item = new ImageButtonItem(islock_movie_pgImg, (ordinal > 0 && ordinal <= 2) ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                if (mCurrValue == LOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_PG;
                    for (int i = 2; i < movieRatingList.size(); i++) {
                        movieRatingList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_PG_13;
                    movieRatingList.get(0).updateUI(UNLOCK);
                }
            }
        };
        movieRatingList.add(item);
        ImageButton islock_movie_pg13Img = (ImageButton) findViewById(R.id.islock_movie_pg_13_img);
        item = new ImageButtonItem(islock_movie_pg13Img, (ordinal > 0 && ordinal <= 3) ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                if (mCurrValue == LOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_PG_13;
                    for (int i = 3; i < movieRatingList.size(); i++) {
                        movieRatingList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_R;
                    for (int i = 0; i < movieRatingList.size() - 4; i++) {
                        movieRatingList.get(i).updateUI(UNLOCK);
                    }
                }
            }
        };
        movieRatingList.add(item);
        ImageButton islock_movie_rImg = (ImageButton) findViewById(R.id.islock_movie_r_img);
        item = new ImageButtonItem(islock_movie_rImg, (ordinal > 0 && ordinal <= 4) ? LOCK : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                if (mCurrValue == LOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_R;
                    for (int i = 4; i < movieRatingList.size(); i++) {
                        movieRatingList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_NC_17;
                    for (int i = 0; i < movieRatingList.size() - 3; i++) {
                        movieRatingList.get(i).updateUI(UNLOCK);
                    }
                }
            }
        };
        movieRatingList.add(item);
        ImageButton islock_movie_nc17Img = (ImageButton) findViewById(R.id.islock_movie_nc_17_img);
        item = new ImageButtonItem(islock_movie_nc17Img, (ordinal > 0 && ordinal <= 5) ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                if (mCurrValue == LOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_NC_17;
                    movieRatingList.get(5).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_X;
                    for (int i = 0; i < movieRatingList.size() - 2; i++) {
                        movieRatingList.get(i).updateUI(UNLOCK);
                    }
                }
            }
        };
        movieRatingList.add(item);
        ImageButton islock_movie_xImg = (ImageButton) findViewById(R.id.islock_movie_x_img);
        item = new ImageButtonItem(islock_movie_xImg, (ordinal > 0 && ordinal <= 6) ? LOCK : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                if (mCurrValue == UNLOCK) {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_NA;
                    for (int i = 0; i < movieRatingList.size() - 1; i++) {
                        movieRatingList.get(i).updateUI(UNLOCK);
                    }
                } else {
                    mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_X;
                }
            }
        };
        movieRatingList.add(item);
        ImageButton islock_movie_nrImg = (ImageButton) findViewById(R.id.islock_movie_nr_img);
        item_nr = new ImageButtonItem(islock_movie_nrImg, mUsaMpaaRatingType.isNr ? 1 : 0) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaMpaaRatingType.isNr = mCurrValue == 1 ? true : false;
            }
        };

        mUsaTvRatingInfo = manager.getUsaTvRatingLock();
        islock_tv_noneImg = (ImageButton) findViewById(R.id.islock_tv_none_img);
        item = new ImageButtonItem(islock_tv_noneImg, mUsaTvRatingInfo.bTV_NONE_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_NONE_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    islock_tv_y7_fv_item.updateUI(LOCK);
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 1; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                }
            }
        };
        tvRatingALLList.add(item);

        ImageButton islock_tv_y_allImg = (ImageButton) findViewById(R.id.islock_tv_y_all_img);
        item = new ImageButtonItem(islock_tv_y_allImg, mUsaTvRatingInfo.bTV_Y_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_Y_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (LOCK == mCurrValue) {
                    islock_tv_y7_fv_item.updateUI(LOCK);
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 2; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                } else if (UNLOCK == mCurrValue) {
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                }
            }
        };
        tvRatingALLList.add(item);
        ImageButton islock_tv_y7_allImg = (ImageButton) findViewById(R.id.islock_tv_y7_all_img);
        item = new ImageButtonItem(islock_tv_y7_allImg, mUsaTvRatingInfo.bTV_Y7_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_Y7_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    islock_tv_y7_fv_item.updateUI(LOCK);
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 3; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                    tvRatingALLList.get(1).updateUI(UNLOCK);
                }
            }
        };
        tvRatingALLList.add(item);
        ImageButton islock_tv_g_allImg = (ImageButton) findViewById(R.id.islock_tv_g_all_img);
        item = new ImageButtonItem(islock_tv_g_allImg, mUsaTvRatingInfo.bTV_G_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_G_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 4; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                    tvRatingALLList.get(1).updateUI(UNLOCK);
                    tvRatingALLList.get(2).updateUI(UNLOCK);
                }
            }
        };
        tvRatingALLList.add(item);
        ImageButton islock_tv_pg_allImg = (ImageButton) findViewById(R.id.islock_tv_pg_all_img);
        item = new ImageButtonItem(islock_tv_pg_allImg, mUsaTvRatingInfo.bTV_PG_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_PG_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 5; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(UNLOCK);
                    }
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                    tvRatingALLList.get(1).updateUI(UNLOCK);
                    tvRatingALLList.get(2).updateUI(UNLOCK);
                    tvRatingALLList.get(3).updateUI(UNLOCK);
                }
            }
        };
        tvRatingALLList.add(item);
        ImageButton islock_tv_14_allImg = (ImageButton) findViewById(R.id.islock_tv_14_all_img);
        item = new ImageButtonItem(islock_tv_14_allImg, mUsaTvRatingInfo.bTV_14_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_14_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 6; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(UNLOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(UNLOCK);
                    }
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                    tvRatingALLList.get(1).updateUI(UNLOCK);
                    tvRatingALLList.get(2).updateUI(UNLOCK);
                    tvRatingALLList.get(3).updateUI(UNLOCK);
                    tvRatingALLList.get(4).updateUI(UNLOCK);
                }
            }
        };
        tvRatingALLList.add(item);
        ImageButton islock_tv_ma_allImg = (ImageButton) findViewById(R.id.islock_tv_ma_all_img);
        item = new ImageButtonItem(islock_tv_ma_allImg, mUsaTvRatingInfo.bTV_MA_ALL_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_MA_ALL_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(UNLOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(UNLOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(UNLOCK);
                    }
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                    tvRatingALLList.get(1).updateUI(UNLOCK);
                    tvRatingALLList.get(2).updateUI(UNLOCK);
                    tvRatingALLList.get(3).updateUI(UNLOCK);
                    tvRatingALLList.get(4).updateUI(UNLOCK);
                    tvRatingALLList.get(5).updateUI(UNLOCK);
                }
            }
        };
        tvRatingALLList.add(item);

        ImageButton islock_tv_y7_fvImg = (ImageButton) findViewById(R.id.islock_tv_y7_fv_img);
        islock_tv_y7_fv_item = new ImageButtonItem(islock_tv_y7_fvImg,
                mUsaTvRatingInfo.bTV_Y7_FV_Lock ? LOCK : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_Y7_FV_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    for (int i = 0; i < tvRatingPGList.size(); i++) {
                        tvRatingPGList.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRating14List.size(); i++) {
                        tvRating14List.get(i).updateUI(LOCK);
                    }
                    for (int i = 0; i < tvRatingMAList.size(); i++) {
                        tvRatingMAList.get(i).updateUI(LOCK);
                    }
                    for (int i = 2; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(LOCK);
                    }
                } else if (mCurrValue == UNLOCK) {
                    tvRatingALLList.get(0).updateUI(UNLOCK);
                    tvRatingALLList.get(1).updateUI(UNLOCK);
                    tvRatingALLList.get(2).updateUI(UNLOCK);
                }
            }
        };

        ImageButton islock_tv_pg_lImg = (ImageButton) findViewById(R.id.islock_tv_pg_l_img);
        item = new ImageButtonItem(islock_tv_pg_lImg, mUsaTvRatingInfo.bTV_PG_L_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_PG_L_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingLList.get(1).updateUI(LOCK);
                    tvRatingLList.get(2).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 2; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingPGList.add(item);
        tvRatingLList.add(item);
        ImageButton islock_tv_14_lImg = (ImageButton) findViewById(R.id.islock_tv_14_l_img);
        item = new ImageButtonItem(islock_tv_14_lImg, mUsaTvRatingInfo.bTV_14_L_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_14_L_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingLList.get(2).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingLList.get(0).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 1; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRating14List.add(item);
        tvRatingLList.add(item);
        ImageButton islock_tv_ma_lImg = (ImageButton) findViewById(R.id.islock_tv_ma_l_img);
        item = new ImageButtonItem(islock_tv_ma_lImg, mUsaTvRatingInfo.bTV_MA_L_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_MA_L_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingLList.get(0).updateUI(UNLOCK);
                    tvRatingLList.get(1).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingMAList.add(item);
        tvRatingLList.add(item);

        ImageButton islock_tv_pg_sImg = (ImageButton) findViewById(R.id.islock_tv_pg_s_img);
        item = new ImageButtonItem(islock_tv_pg_sImg, mUsaTvRatingInfo.bTV_PG_S_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_PG_S_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingSList.get(1).updateUI(LOCK);
                    tvRatingSList.get(2).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 2; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingPGList.add(item);
        tvRatingSList.add(item);
        ImageButton islock_tv_14_sImg = (ImageButton) findViewById(R.id.islock_tv_14_s_img);
        item = new ImageButtonItem(islock_tv_14_sImg, mUsaTvRatingInfo.bTV_14_S_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_14_S_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingSList.get(2).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingSList.get(0).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 1; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRating14List.add(item);
        tvRatingSList.add(item);
        ImageButton islock_tv_ma_sImg = (ImageButton) findViewById(R.id.islock_tv_ma_s_img);
        item = new ImageButtonItem(islock_tv_ma_sImg, mUsaTvRatingInfo.bTV_MA_S_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_MA_S_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingSList.get(0).updateUI(UNLOCK);
                    tvRatingSList.get(1).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingMAList.add(item);
        tvRatingSList.add(item);

        ImageButton islock_tv_pg_vImg = (ImageButton) findViewById(R.id.islock_tv_pg_v_img);
        item = new ImageButtonItem(islock_tv_pg_vImg, mUsaTvRatingInfo.bTV_PG_V_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_PG_V_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingVList.get(1).updateUI(LOCK);
                    tvRatingVList.get(2).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 2; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingPGList.add(item);
        tvRatingVList.add(item);
        ImageButton islock_tv_14_vImg = (ImageButton) findViewById(R.id.islock_tv_14_v_img);
        item = new ImageButtonItem(islock_tv_14_vImg, mUsaTvRatingInfo.bTV_14_V_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_14_V_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingVList.get(2).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingVList.get(0).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 1; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRating14List.add(item);
        tvRatingVList.add(item);
        ImageButton islock_tv_ma_vImg = (ImageButton) findViewById(R.id.islock_tv_ma_v_img);
        item = new ImageButtonItem(islock_tv_ma_vImg, mUsaTvRatingInfo.bTV_MA_V_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_MA_V_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingVList.get(0).updateUI(UNLOCK);
                    tvRatingVList.get(1).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size(); i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingMAList.add(item);
        tvRatingVList.add(item);

        ImageButton islock_tv_pg_dImg = (ImageButton) findViewById(R.id.islock_tv_pg_d_img);
        item = new ImageButtonItem(islock_tv_pg_dImg, mUsaTvRatingInfo.bTV_PG_D_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_PG_D_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == LOCK) {
                    tvRatingDList.get(1).updateUI(LOCK);
                } else if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 2; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRatingPGList.add(item);
        tvRatingDList.add(item);
        ImageButton islock_tv_14_dImg = (ImageButton) findViewById(R.id.islock_tv_14_d_img);
        item = new ImageButtonItem(islock_tv_14_dImg, mUsaTvRatingInfo.bTV_14_D_Lock ? LOCK
                : UNLOCK) {
            @Override
            public void updateUI() {
                super.updateUI();
                mUsaTvRatingInfo.bTV_14_D_Lock = (mCurrValue == LOCK ? true : false);
                if (mCurrValue == UNLOCK) {
                    islock_tv_y7_fv_item.updateUI(UNLOCK);
                    tvRatingDList.get(0).updateUI(UNLOCK);
                    for (int i = 0; i < tvRatingALLList.size() - 1; i++) {
                        tvRatingALLList.get(i).updateUI(UNLOCK);
                    }
                }
                setALLLock();
            }
        };
        tvRating14List.add(item);
        tvRatingDList.add(item);
    }

    private void setALLLock() {
        int lockPGCount = 0;
        for (int i = 0; i < tvRatingPGList.size(); i++) {
            if (tvRatingPGList.get(i).getCurrValue() == LOCK) {
                lockPGCount++;
            }
        }
        if (lockPGCount == tvRatingPGList.size()) {
            tvRatingALLList.get(4).updateUI(LOCK);
        }
        int lock14Count = 0;
        for (int i = 0; i < tvRating14List.size(); i++) {
            if (tvRating14List.get(i).getCurrValue() == LOCK) {
                lock14Count++;
            }
        }
        if (lock14Count == tvRating14List.size()) {
            tvRatingALLList.get(5).updateUI(LOCK);
        }
        int lockMACount = 0;
        for (int i = 0; i < tvRatingMAList.size(); i++) {
            if (tvRatingMAList.get(i).getCurrValue() == LOCK) {
                lockMACount++;
            }
        }
        if (lockMACount == tvRatingMAList.size()) {
            tvRatingALLList.get(6).updateUI(LOCK);
        }
    }

    private void setListener() {
        islock_movie_noneImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsaMpaaRatingType.enUaMpaaRatingType = EnumUsaMpaaRatingType.E_MPAA_RATING_NA;
                for (int i = 0; i < movieRatingList.size(); i++) {
                    movieRatingList.get(i).updateUI(UNLOCK);
                }
                manager.setUsaMpaaRatingLock(mUsaMpaaRatingType);
            }
        });
        item_nr.getmImgbtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item_nr.updateUI();
                manager.setUsaMpaaRatingLock(mUsaMpaaRatingType);
            }
        });
        for (int i = 0; i < movieRatingList.size(); i++) {
            final ImageButtonItem imgbtnItem = movieRatingList.get(i);
            imgbtnItem.getmImgbtn().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgbtnItem.updateUI();
                    manager.setUsaMpaaRatingLock(mUsaMpaaRatingType);
                }
            });
        }
        for (int i = 0; i < tvRatingALLList.size(); i++) {
            final ImageButtonItem imgbtnItem = tvRatingALLList.get(i);
            imgbtnItem.getmImgbtn().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgbtnItem.updateUI();
                    updateData();
                }
            });
        }
        for (int i = 0; i < tvRatingLList.size(); i++) {
            final ImageButtonItem imgbtnItem = tvRatingLList.get(i);
            imgbtnItem.getmImgbtn().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgbtnItem.updateUI();
                    updateData();
                }
            });
        }
        for (int i = 0; i < tvRatingSList.size(); i++) {
            final ImageButtonItem imgbtnItem = tvRatingSList.get(i);
            imgbtnItem.getmImgbtn().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgbtnItem.updateUI();
                    updateData();
                }
            });
        }
        for (int i = 0; i < tvRatingVList.size(); i++) {
            final ImageButtonItem imgbtnItem = tvRatingVList.get(i);
            imgbtnItem.getmImgbtn().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgbtnItem.updateUI();
                    updateData();
                }
            });
        }
        for (int i = 0; i < tvRatingDList.size(); i++) {
            final ImageButtonItem imgbtnItem = tvRatingDList.get(i);
            imgbtnItem.getmImgbtn().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgbtnItem.updateUI();
                    updateData();
                }
            });
        }

        islock_tv_y7_fv_item.getmImgbtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                islock_tv_y7_fv_item.updateUI();
                updateData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    protected void updateData() {
        mUsaTvRatingInfo.bTV_NONE_ALL_Lock = tvRatingALLList.get(0).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_Y_ALL_Lock = tvRatingALLList.get(1).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_Y7_ALL_Lock = tvRatingALLList.get(2).getCurrValue() == 1 ? true
                : false;
        mUsaTvRatingInfo.bTV_Y7_FV_Lock = islock_tv_y7_fv_item.getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_G_ALL_Lock = tvRatingALLList.get(3).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_PG_ALL_Lock = tvRatingALLList.get(4).getCurrValue() == 1 ? true
                : false;
        mUsaTvRatingInfo.bTV_PG_V_Lock = tvRatingVList.get(0).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_PG_S_Lock = tvRatingSList.get(0).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_PG_L_Lock = tvRatingLList.get(0).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_PG_D_Lock = tvRatingDList.get(0).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_14_ALL_Lock = tvRatingALLList.get(5).getCurrValue() == 1 ? true
                : false;
        mUsaTvRatingInfo.bTV_14_V_Lock = tvRatingVList.get(1).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_14_S_Lock = tvRatingSList.get(1).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_14_L_Lock = tvRatingLList.get(1).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_14_D_Lock = tvRatingDList.get(1).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_MA_ALL_Lock = tvRatingALLList.get(6).getCurrValue() == 1 ? true
                : false;
        mUsaTvRatingInfo.bTV_MA_V_Lock = tvRatingVList.get(2).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_MA_S_Lock = tvRatingSList.get(2).getCurrValue() == 1 ? true : false;
        mUsaTvRatingInfo.bTV_MA_L_Lock = tvRatingLList.get(2).getCurrValue() == 1 ? true : false;
        manager.setUsaTvRatingLock(mUsaTvRatingInfo);
    }

    @Override
    void callBack(int resultVaule) {

    }

    @Override
    void callBack(Boolean resultVaule) {

    }

    @Override
    void callBack() {

    }

    class ImageButtonItem {
        public ImageButton mImgbtn;

        public int mCurrValue;

        ImageButtonItem(ImageButton imgbtn, int currValue) {
            this.mImgbtn = imgbtn;
            this.mCurrValue = currValue;
            updateUI(mCurrValue);
        }

        public ImageButton getmImgbtn() {
            return mImgbtn;
        }

        public void setmImgbtn(ImageButton mImgbtn) {
            this.mImgbtn = mImgbtn;
        }

        public int getCurrValue() {
            return mCurrValue;
        }

        public void setmCurrValue(int mCurrValue) {
            this.mCurrValue = mCurrValue;
        }

        public void updateUI() {
            if (mCurrValue == UNLOCK) {
                mCurrValue = LOCK;
                mImgbtn.setImageResource(R.drawable.lock);
            } else if (mCurrValue == LOCK) {
                mCurrValue = UNLOCK;
                mImgbtn.setImageResource(R.drawable.unlock);
            }
            updateUI(mCurrValue);
        }

        public void updateUI(int curValue) {
            mCurrValue = curValue;
            if (curValue == UNLOCK) {
                mImgbtn.setImageResource(R.drawable.unlock);
            } else if (curValue == LOCK) {
                mImgbtn.setImageResource(R.drawable.lock);
            }
            mImgbtn.invalidate();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, VChipActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
