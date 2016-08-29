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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.PvrManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.vo.EnumPvrStatus;
import com.mstar.android.tvapi.common.vo.PvrFileInfo;
import com.mstar.android.tvapi.common.vo.VideoWindowType;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.util.Constant;
import java.io.File;
import java.util.ArrayList;

public class PVRFullPageBrowserActivity extends MstarBaseActivity {

    private static final String TAG = "PVRFullPageBrowserActivity";

    private ArrayList<listViewHolder> mPvrList = new ArrayList<listViewHolder>();

    private Bitmap[] mBitmapList = null;

    private Bitmap mBitmap = null;

    private boolean mIsAscend = true;

    private boolean mIsItemClicked = false;

    private boolean mIsPreviewSetted = false;

    private boolean mIsRecordingItem = false;

    private boolean mIsTransparency = false;

    private Handler mHandler = new Handler();

    private int mCurrentPosition = 0;

    private int mCurrentRecording = -1;

    private int mCurSortKey = PvrManager.PVR_FILE_INFO_SORT_FILENAME;

    private LinearLayout mGallery;

    private ListView mListview;

    private ProgressBar mPVRPlaybackProgress = null;

    private PVRListViewAdapter mPvrAdapter;

    private PVRThumbnail mThumbnail = null;

    private SurfaceView mPVRPlaybackView = null;

    private TextView mTextViewBlueKey;

    private TextView mTextViewGreenKey;

    private TextView mTotalTime;

    private TvPvrManager mPvrManager = null;

    private USBDiskSelecter mUsbSelecter = null;

    private UsbReceiver mUsbReceiver = null;

    // private ImageAdapter imageAdapter;
    /*
     * private int[] resIds = new int[] { R.drawable.tv_record_list_pic_bg,
     * R.drawable.tv_record_list_pic_s_bg, R.drawable.tv_record_list_pic_bg,
     * R.drawable.tv_record_list_pic_s_bg, R.drawable.tv_record_list_pic_bg,
     * R.drawable.tv_record_list_pic_s_bg,}; private String[] data1 = new
     * String[] { "ATV", "DTV", "SV", "VGA", "SCART", "AV1", "AV2", "AV3",
     * "HDMI1" }; private String[] data2 = new String[] { "ATV", "DTV", "SV",
     * "VGA", "SCART", "AV1", "AV2", "AV3", "HDMI1" }; private String[] data3 =
     * new String[] { "ATV", "DTV", "SV", "VGA", "SCART", "AV1", "AV2", "AV3",
     * "HDMI1" };
     */

    /**
     * Override Adapter
     */
    public class ImageAdapter extends BaseAdapter {

        int mGalleryItemBackground;

        private Context mContext;

        private Bitmap[] mImageArray;

        public ImageAdapter(Context context, Bitmap[] mBitmapList) {
            mContext = context;
            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
            mGalleryItemBackground = typedArray.getResourceId(
                    R.styleable.Gallery_android_galleryItemBackground, 0);
            mImageArray = mBitmapList;
        }

        public int getCount() {
            // return resIds.length;
            return mImageArray.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            // imageView.setImageResource(resIds[position]);
            imageView.setImageBitmap(mImageArray[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(222, 231));
            imageView.setBackgroundResource(mGalleryItemBackground);
            return imageView;
        }
    }

    public class PVRListViewAdapter extends BaseAdapter {

        ArrayList<listViewHolder> mData = null;

        private Context mContext;

        public PVRListViewAdapter(Context context, ArrayList<listViewHolder> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.pvr_listview_item, null);
            ImageView tmpImage = (ImageView) convertView.findViewById(R.id.player_recording_file);
            if (mCurrentRecording == position) {
                tmpImage.setVisibility(View.VISIBLE);
                mIsRecordingItem = true;
            } else {
                tmpImage.setVisibility(View.INVISIBLE);
                mIsRecordingItem = false;
            }
            TextView tmpText = (TextView) convertView.findViewById(R.id.pvr_listview_item_lcn);
            tmpText.setText(mData.get(position).getPvr_text_view_lcn());
            tmpText = (TextView) convertView.findViewById(R.id.pvr_listview_item_channel);
            tmpText.setText(mData.get(position).getPvr_text_view_channel());
            tmpText = (TextView) convertView.findViewById(R.id.pvr_listview_item_program);
            tmpText.setText(mData.get(position).getPvr_text_view_program_service());
            return convertView;
        }
    }

    private class listViewHolder {

        private String pvr_text_view_lcn = null;

        private String pvr_text_view_channel = null;

        private String pvr_text_view_program_service = null;

        public String getPvr_text_view_lcn() {
            return pvr_text_view_lcn;
        }

        public void setPvr_text_view_lcn(String pvrTextViewLcn) {
            pvr_text_view_lcn = pvrTextViewLcn;
        }

        public String getPvr_text_view_channel() {
            return pvr_text_view_channel;
        }

        public void setPvr_text_view_channel(String pvrTextViewChannel) {
            pvr_text_view_channel = pvrTextViewChannel;
        }

        public String getPvr_text_view_program_service() {
            return pvr_text_view_program_service;
        }

        public void setPvr_text_view_program_service(String pvrTextViewProgramService) {
            pvr_text_view_program_service = pvrTextViewProgramService;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pvr_full_page_browser);
        mListview = (ListView) findViewById(R.id.pvr_listview);
        mPVRPlaybackView = (SurfaceView) findViewById(R.id.pvr_lcn_img);
        mPVRPlaybackProgress = (ProgressBar) findViewById(R.id.pvr_progressBar);
        mTextViewGreenKey = (TextView) findViewById(R.id.pvr_tips_green);
        mTextViewBlueKey = (TextView) findViewById(R.id.pvr_tips_blue);

        createPVRPlaybackView();
        mPvrManager = TvPvrManager.getInstance();

        mUsbSelecter = new USBDiskSelecter(this) {
            @Override
            public void onItemChosen(int position, String diskLabel, String diskPath) {
                String strMountPath = diskPath + "/_MSTPVR";
                if (diskPath.isEmpty()) {
                    Log.e(TAG, "=============>>>>> USB Disk Path is NULL !!!");
                    return;
                }
                Log.d(TAG, "=============>>>>> USB Disk Path = " + diskPath);
                mPvrManager.clearMetadata();
                mPvrManager.setPvrParams(diskPath, (short) 2);
                mPvrManager.createMetadata(strMountPath);
                init();
            }
        };

        int usbDriverCount = mUsbSelecter.getDriverCount();
        /* modify by owen.qin begin */
        String bestPath = mUsbSelecter.getBestDiskPath();

        Log.e(TAG, "bestPath::" + bestPath);
        if (usbDriverCount <= 0) {
            Toast.makeText(this, R.string.str_pvr_insert_usb, Toast.LENGTH_SHORT).show();
            return;
        }
        if (USBDiskSelecter.NO_DISK.equals(bestPath)) {
            Toast.makeText(this, R.string.str_pvr_insert_usb, Toast.LENGTH_SHORT).show();
            return;
        }
        if (USBDiskSelecter.CHOOSE_DISK.equals(bestPath)) {
            mUsbSelecter.start();
            return;
        } else {

            String strMountPath = bestPath + "/_MSTPVR";
            if (bestPath.isEmpty()) {
                Log.e(TAG, "=============>>>>> USB Disk Path is NULL !!!");
                return;
            }
            Log.d(TAG, "=============>>>>> USB Disk Path = " + bestPath);
            mPvrManager.clearMetadata();
            mPvrManager.setPvrParams(bestPath, (short) 2);
            mPvrManager.createMetadata(strMountPath);
            init();
        }
        /* modify by owen.qin end */

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Handler focusHandler = new Handler();
        if (mListview != null && mListview.isInTouchMode()) {
            focusHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListview.setFocusableInTouchMode(true);
                    mListview.requestFocusFromTouch();
                    mListview.requestFocus();
                    mListview.setSelection(0);
                }
            }, 500);
        } else if (mListview != null) {

            mListview.setSelection(0);
        }
    }

    @Override
    protected void onPause() {
        SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING,
                Context.MODE_PRIVATE);
        int subtitlePos = settings.getInt("subtitlePos", 0);
        TvChannelManager.getInstance().closeSubtitle();
        if (subtitlePos > 0) {
            TvChannelManager.getInstance().openSubtitle((subtitlePos - 1));
        }
        super.onPause();
    }

    private void init() {
        if (!isPVRAvailable()) {
            return;
        }
        mGallery = (LinearLayout) findViewById(R.id.pvr_gallery);
        mThumbnail = new PVRThumbnail(this) {

            @Override
            void onItemClicked(int position) {
                mPvrManager.jumpToThumbnail(position);
            }
        };
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mGallery.addView(mThumbnail);
        mThumbnail.setLayoutParams(lp);

        /*
         * // for test only for (int i = 0; i < data1.length; i++) {
         * listViewHolder vh = new listViewHolder();
         * vh.setPvr_text_view_lcn(data1[i]);
         * vh.setPvr_text_view_channel(data2[i]);
         * vh.setPvr_text_view_program_service(data3[i]); mPvrList.add(vh); }
         */
        mPvrAdapter = new PVRListViewAdapter(this, mPvrList);
        /*
         * imageAdapter = new ImageAdapter(this);
         * mGallery.setAdapter(imageAdapter);
         * mGallery.setSelection(resIds.length / 2);
         */
        mListview.setAdapter(mPvrAdapter);
        mListview.setDividerHeight(0);
        mListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                /*
                 * Scale to full screen will show garbage when playing.
                 * if we using mPvrManager.jumpPlaybackTime(0) then scale;
                 * so to prevent garbage stop and re-start playback.
                 */
                mPvrManager.stopPlayback();
                scaleToFullScreen();
                playRecordedFile(pos);
                mIsItemClicked = true;
                Intent intent = new Intent();
                intent.setClass(PVRFullPageBrowserActivity.this, PVRActivity.class);
                intent.putExtra("FullPageBrowserCall", true);
                startActivity(intent);
                finish();
            }
        });
        mListview.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mCurrentPosition = pos;
                // construct mThumbnail list
                String fileName = getFileNameByIndex(pos);
                Log.d(TAG, "===========>>>> current Selected fileName = " + fileName);
                constructThumbnailList(fileName);

                if (mCurrentRecording == pos) {
                    mIsRecordingItem = true;
                } else {
                    mIsRecordingItem = false;
                }
                // TODO set small display window
                playRecordedFile(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // construct recorder list
        setSortAscending(mIsAscend);
        constructRecorderList();
        // add by owen.qin to show the total time at first time.
        mTotalTime = (TextView) findViewById(R.id.total_time);
        String fileName = getFileNameByIndex(0);
        int total = mPvrManager.getRecordedFileDurationTime(fileName);
        mTotalTime.setText(getTimeString(total));
        new PlayBackProgress().start();
    }

    private boolean isPVRAvailable() {
        Log.d(TAG,
                "===========>>>> mPvrManager.getPvrFileNumber() = "
                        + mPvrManager.getPvrFileNumber());
        if (mPvrManager.getPvrFileNumber() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerUSBDetector();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUsbSelecter.dismiss();
        unregisterReceiver(mUsbReceiver);
        mUsbReceiver = null;
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                setTransparency(false);
                if (!mIsItemClicked && mPvrManager.isPlaybacking()) {
                    mPvrManager.stopPlayback();
                    mPvrManager.stopPlaybackLoop();
                }
                if (mIsPreviewSetted) {
                    scaleToFullScreen();
                }
                if (mPvrManager.isRecording()) {
                    Intent intent = new Intent();
                    intent.setClass(PVRFullPageBrowserActivity.this, PVRActivity.class);
                    intent.putExtra("PVR_ONE_TOUCH_MODE", 1);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean bRet = false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_PROG_RED: { // for delete
                // cannot delete current recording file
                if (mCurrentRecording == mCurrentPosition) {
                    Toast.makeText(this, R.string.str_pvr_is_recording, Toast.LENGTH_SHORT).show();
                    break;
                }
                String fileName;
                fileName = getFileNameByIndex(mCurrentPosition);
                Log.i(TAG, "get fileName;" + fileName);
                if (null == fileName) {
                    return false;
                }
                mPvrManager.stopPlayback();
                mPvrManager.deletefile(0, fileName);
                constructRecorderList();
                bRet = true;
                fileName = getFileNameByIndex(mCurrentPosition);
                constructThumbnailList(fileName);
                Log.i(TAG, "jump filename:" + fileName);
                mPvrManager.jumpToThumbnail(mCurrentPosition);

                break;
            }
            case KeyEvent.KEYCODE_PROG_BLUE: {
                mPvrManager.stopPlayback();
                if (mCurSortKey < PvrManager.PVR_FILE_INFO_SORT_PROGRAM) {
                    mCurSortKey++;
                } else {
                    mCurSortKey = PvrManager.PVR_FILE_INFO_SORT_TIME;
                }
                setSortKey(mCurSortKey);
                constructRecorderList();
                int nIdx = mListview.getSelectedItemPosition();
                String fileName = getFileNameByIndex(nIdx);
                constructThumbnailList(fileName);
                // TODO set small display window
                playRecordedFile(nIdx);
                bRet = true;
                break;
            }
            case KeyEvent.KEYCODE_PROG_GREEN: {
                mPvrManager.stopPlayback();
                setSortAscending(!mIsAscend);
                constructRecorderList();
                int nIdx = mListview.getSelectedItemPosition();
                String fileName = getFileNameByIndex(nIdx);
                constructThumbnailList(fileName);
                playRecordedFile(nIdx);
                bRet = true;
                break;
            }
        }
        if (bRet == false) {
            bRet = super.onKeyDown(keyCode, event);
        }
        return bRet;
    }

    private void setSortKey(final int key) {
        boolean iskeyAvailable = true;
        mCurSortKey = key;
        if (key == PvrManager.PVR_FILE_INFO_SORT_TIME) {
            mTextViewBlueKey.setText(getResources().getString(R.string.str_time_time));
        } else if (key == PvrManager.PVR_FILE_INFO_SORT_LCN) {
            mTextViewBlueKey.setText(getResources().getString(R.string.str_pvr_lcn_textview));
        } else if (key == PvrManager.PVR_FILE_INFO_SORT_CHANNEL) {
            mTextViewBlueKey.setText(getResources().getString(
                    R.string.str_schedule_list_channel_name));
        } else if (key == PvrManager.PVR_FILE_INFO_SORT_PROGRAM) {
            mTextViewBlueKey.setText(getResources().getString(
                    R.string.str_schedule_list_programmer_title));
        } else {
            mTextViewBlueKey.setText(getResources().getString(
                    R.string.str_pvr_program_service_tips_blue));
            iskeyAvailable = false;
        }
        if (iskeyAvailable)
            mPvrManager.setMetadataSortKey(mCurSortKey);
    }

    private void setSortAscending(final boolean isAscend) {
        mPvrManager.setMetadataSortAscending(isAscend);
        mIsAscend = isAscend;
        if (isAscend) {
            mTextViewGreenKey.setText(getResources().getString(
                    R.string.str_pvr_program_service_tips_green));
        } else {
            mTextViewGreenKey.setText(getResources().getString(
                    R.string.str_pvr_program_service_tips_descend));
        }
    }

    private void registerUSBDetector() {
        mUsbReceiver = new UsbReceiver();
        IntentFilter iFilter;
        iFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
        iFilter.addDataScheme("file");
        registerReceiver(mUsbReceiver, iFilter);
        iFilter = new IntentFilter(Intent.ACTION_MEDIA_EJECT);
        iFilter.addDataScheme("file");
        registerReceiver(mUsbReceiver, iFilter);
    }

    private void createPVRPlaybackView() {
        Callback callback = new Callback() {

            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    Log.e(TAG, "**************************************surfaceCreated!!!!!!!!");
                    TvManager.getInstance().getPlayerManager().setDisplay(holder);
                } catch (TvCommonException e) {
                    e.printStackTrace();
                }
            }
        };
        mPVRPlaybackView.getHolder().addCallback(callback);
    }

    private boolean setPreviewWindow() {
        if (mIsPreviewSetted) {
            return true;
        }
        VideoWindowType videoWindowType = new VideoWindowType();
        int[] location = new int[2];
        mPVRPlaybackView.getLocationOnScreen(location);
        videoWindowType.x = location[0];
        videoWindowType.y = location[1];
        videoWindowType.height = mPVRPlaybackView.getHeight();
        videoWindowType.width = mPVRPlaybackView.getWidth();
        Log.e(TAG, "[[[[[[[[[[[[[[the [x,y][w,h]=====[" + videoWindowType.x + ","
                + videoWindowType.y + "][" + videoWindowType.width + "," + videoWindowType.height
                + "]");
        if (videoWindowType.width == 0 || videoWindowType.height == 0) {
            return false;
        }
        mPvrManager.setPlaybackWindow(videoWindowType, 1920, 1080);
        mIsPreviewSetted = true;
        return true;
    }

    private void playRecordedFile(int pos) {
        String fileName = getFileNameByIndex(pos);
        if (fileName == null || !setPreviewWindow())
            return;
        Log.d(TAG, "===========>>>> current playback fileName = " + fileName);
        if (mPvrManager.getCurPlaybackingFileName().equals(fileName))
            return;
        if (mPvrManager.isPlaybacking()) {
            mPvrManager.stopPlayback();
            mPvrManager.stopPlaybackLoop();
        }
        EnumPvrStatus playbackStatus = mPvrManager.startPlayback(fileName);
        setTransparency(true);
        int total = mPvrManager.getRecordedFileDurationTime(fileName);
        Log.d(TAG, "===========>>>> current playback file mTotalTime = " + total);
        mPVRPlaybackProgress.setMax(total);
        mTotalTime.setText(getTimeString(total));
        if (!playbackStatus.equals(EnumPvrStatus.E_SUCCESS)) {
            Log.e(TAG, "playRecordedFile Error");
            // Toast.makeText(this, "Can't PlayBack Properly, the Reason is " +
            // playbackStatus.toString(), 500).show();
        }
        TvChannelManager.getInstance().closeSubtitle();
    }

    private String getTimeString(int seconds) {
        String hour = "00";
        String minute = "00";
        String second = "00";
        if (seconds % 60 < 10)
            second = "0" + seconds % 60;
        else
            second = "" + seconds % 60;

        int offset = seconds / 60;
        if (offset % 60 < 10)
            minute = "0" + offset % 60;
        else
            minute = "" + offset % 60;

        offset = seconds / 3600;
        if (offset < 10)
            hour = "0" + offset;
        else
            hour = "" + offset;
        return hour + ":" + minute + ":" + second;
    }

    private void scaleToFullScreen() {
        setTransparency(false);
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                VideoWindowType videoWindowType = new VideoWindowType();
                videoWindowType.height = 0;
                videoWindowType.width = 0;
                videoWindowType.x = 0xFFFF;
                videoWindowType.y = 0xFFFF;
                mPvrManager.setPlaybackWindow(videoWindowType, 0, 0);
                mIsPreviewSetted = false;
            }
        }, 500);
    }

    public void constructRecorderList() {
        final int pvrFileNumber = mPvrManager.getPvrFileNumber();
        final String strFileName = mPvrManager.getCurRecordingFileName();
        final int nSortKey = mPvrManager.getMetadataSortKey();
        PvrFileInfo fileInfo = new PvrFileInfo();
        String pvrFileLcn = null;
        String pvrFileServiceName = null;
        String pvrFileEventName = null;
        mPvrList.clear();

        for (int i = 0; i < pvrFileNumber; i++) {
            listViewHolder vh = new listViewHolder();
            fileInfo = mPvrManager.getPvrFileInfo(i, nSortKey);
            pvrFileLcn = "CH " + mPvrManager.getFileLcn(i);
            pvrFileServiceName = mPvrManager.getFileServiceName(fileInfo.filename);
            pvrFileEventName = mPvrManager.getFileEventName(fileInfo.filename);
            if (strFileName.equals(fileInfo.filename)) {
                mCurrentRecording = i;
            }
            vh.setPvr_text_view_lcn(pvrFileLcn);
            vh.setPvr_text_view_channel(pvrFileServiceName);
            vh.setPvr_text_view_program_service(pvrFileEventName);
            mPvrList.add(vh);
        }

        mPvrAdapter.notifyDataSetChanged();
        mListview.invalidate();
    }

    public String getFileNameByIndex(int index) {
        if (mPvrList.isEmpty()) {
            return null;
        }
        PvrFileInfo fileInfo = new PvrFileInfo();
        fileInfo = mPvrManager.getPvrFileInfo(index, mPvrManager.getMetadataSortKey());
        return fileInfo.filename;
    }

    public String getSelectedFileName() {
        if (mPvrList.isEmpty()) {
            return null;
        }
        PvrFileInfo fileInfo = new PvrFileInfo();
        fileInfo = mPvrManager.getPvrFileInfo(mListview.getSelectedItemPosition(),
                mPvrManager.getMetadataSortKey());
        return fileInfo.filename;
    }

    public void constructThumbnailList(String fileName) {
        if (fileName != null) {
            mPvrManager.assignThumbnailFileInfoHandler(fileName);
        }
        mThumbnail.updateThumbnail();
    }

    public Bitmap decodeFile(String filePath) {
        System.out.println("filepath in decode file .. " + filePath);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        // scale size
        final int REQUIRED_SIZE = 100;
        final int H = 50;
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < H) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        System.out.println("decode file ........... " + filePath);
        mBitmap = BitmapFactory.decodeFile(filePath, o2);
        return mBitmap;
    }

    /*
     * setVisibility/setBackgroundColor will do in background it will not be
     * setted immediately.
     */
    private void setTransparency(boolean transparencyOnOff) {
        if (mIsTransparency == transparencyOnOff) {
            return;
        }
        mIsTransparency = transparencyOnOff;
        if (transparencyOnOff)
            mPVRPlaybackView.setVisibility(View.VISIBLE);
        else
            mPVRPlaybackView.setVisibility(View.INVISIBLE);
    }

    private class PlayBackProgress extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                while (!isFinishing()) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int currentTime = mPvrManager.getCurPlaybackTimeInSecond();
                            if (mIsRecordingItem) {
                                int total = (mPvrManager.isRecording()) ? TvPvrManager
                                        .getInstance().getCurRecordTimeInSecond() : TvPvrManager
                                        .getInstance().getCurPlaybackTimeInSecond();
                                ;
                                mTotalTime.setText(getTimeString(total));
                                mPVRPlaybackProgress.setMax(total);
                            }
                            Log.e(TAG, "==========>>> current time = " + currentTime);
                            mPVRPlaybackProgress.setProgress(currentTime);
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class UsbReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Uri uri = intent.getData();
            String path = uri.getPath();
            if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {

            } else if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
                String mountPath = null;
                mountPath = mPvrManager.getPvrMountPath();
                Log.e(TAG, "mountPath:" + mountPath);
                if (path.equals(mountPath)) {
                    mPvrManager.clearMetadata();
                    finish();
                }
            }
        }
    }
}
