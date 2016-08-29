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

package com.mstar.tv.tvplayer.ui.channel;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;


import com.mstar.android.MKeyEvent;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tv.TvCiManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;
import com.mstar.tv.tvplayer.ui.pvr.PVRActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.util.Constant;
import com.mstar.tvframework.MstarBaseActivity;

public class ChannelListActivity extends MstarBaseActivity {
    private enum EnumChannelListState {
        E_NORMAL_CHANNEL_LIST_MENU, E_OPERATOR_PROFILE_MENU, E_OP_CHANNEL_LIST_MENU,
    };

    private static final String TAG = "ChannelListActivity";

    private int mTvSystem = 0;

    private int proglistId = 0;// 1 favorite list // 2 progam list

    private int m_nServiceNum = 0;

    TvChannelManager mTvChannelManager = null;

    private ListView proListView;

    private ArrayList<ProgramFavoriteObject> pfos = new ArrayList<ProgramFavoriteObject>();

    private ArrayList<ProgramInfo> progInfoList = new ArrayList<ProgramInfo>();

    private ChannelListAdapter adapter = null;

    private TimeOutHelper timeOutHelper;

    private EnumChannelListState mstate = EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU;

    private OperatorProfileListAdapter mOPListAdapter = null;

    private TextView mTextTitle = null;

    private TextView mHintOP = null;

    private TextView mHintDelOP = null;

    private short mOPCount = 0;

    private AlertDialog mDeleteOpconfirmation = null;

    private TvAtscChannelManager mTvAtscChannelManager = null;

    private TvIsdbChannelManager mTvIsdbChannelManager = null;

    private OnDtvPlayerEventListener mDtvEventListener = null;
    
    private ImageView favo_skip = null;

    private class DtvEventListener implements OnDtvPlayerEventListener {

        @Override
        public boolean onDtvChannelNameReady(int what) {
            return false;
        }

        @Override
        public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
            return false;
        }

        @Override
        public boolean onDtvProgramInfoReady(int what) {
            return false;
        }

        @Override
        public boolean onCiLoadCredentialFail(int what) {
            return false;
        }

        @Override
        public boolean onEpgTimerSimulcast(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onHbbtvStatusMode(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onMheg5StatusMode(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onMheg5ReturnKey(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onOadHandler(int what, int arg1, int arg2) {
            return false;
        }

        @Override
        public boolean onOadDownload(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onDtvAutoUpdateScan(int what) {
            return false;
        }

        @Override
        public boolean onTsChange(int what) {
            boolean ret = false;
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                Log.i(TAG, "onTsChange what:" + what);
                getListInfo();
                if (TvCiManager.getInstance().isOpMode()) {
                    SwitchChannelListState(EnumChannelListState.E_OP_CHANNEL_LIST_MENU);
                } else {
                    SwitchChannelListState(EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU);
                }
                ret = true;
            }
            return ret;
        }

        @Override
        public boolean onPopupScanDialogLossSignal(int what) {
            return false;
        }

        @Override
        public boolean onPopupScanDialogNewMultiplex(int what) {
            return false;
        }

        @Override
        public boolean onPopupScanDialogFrequencyChange(int what) {
            return false;
        }

        @Override
        public boolean onRctPresence(int what) {
            return false;
        }

        @Override
        public boolean onChangeTtxStatus(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onDtvPriComponentMissing(int what) {
            return false;
        }

        @Override
        public boolean onAudioModeChange(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onMheg5EventHandler(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onOadTimeout(int what, int arg1) {
            return false;
        }

        @Override
        public boolean onGingaStatusMode(int what, boolean arg1) {
            return false;
        }

        @Override
        public boolean onSignalLock(int what) {
            return false;
        }

        @Override
        public boolean onSignalUnLock(int what) {
            return false;
        }

        @Override
        public boolean onUiOPRefreshQuery(int what) {
            return false;
        }

        @Override
        public boolean onUiOPServiceList(int what) {
            Log.i("MyTvHandler", "get CI+ OP event EV_CI_OP_SERVICE_LIST");
            getListInfo();
            SwitchChannelListState(EnumChannelListState.E_OP_CHANNEL_LIST_MENU);
            return true;
        }

        @Override
        public boolean onUiOPExitServiceList(int what) {
            Log.i("MyTvHandler", "get CI+ OP event EV_CI_OP_EXIT_SERVICE_LIST");
            getListInfo();
            SwitchChannelListState(EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU);
            return true;
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TimeOutHelper.getTimeOutMsg()) {
                // finish();
            }
        }
    };

    public class ChannelListAdapter extends BaseAdapter {
        ArrayList<ProgramFavoriteObject> mData = null;

        private Context mContext;

        public ChannelListAdapter(Context context, ArrayList<ProgramFavoriteObject> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.program_favorite_list_item, null);
            TextView tvnumber = (TextView) convertView
                    .findViewById(R.id.program_favorite_edit_number);
            tvnumber.setText(mData.get(position).getChannelId());
            TextView tvname = (TextView) convertView.findViewById(R.id.program_favorite_edit_data);
            tvname.setText(mData.get(position).getChannelName());
            //zb20141029 add
            if(position==foucusItem)
            {
            	tvnumber.setTextColor(0xFF0000FF);
            	tvname.setTextColor(0xFF0000FF);
            }
            //end
            ImageView sourceimage = (ImageView) convertView.findViewById(R.id.program_favorite_source_img);
            ImageView iv_skip_btn = (ImageView)convertView.findViewById(R.id.fav_skip_button);
            ProgramFavoriteObject obj = mData.get(position);
            if(obj.isskip)
            {
            	Resources res = getResources();
        		Drawable favo_img = res.getDrawable(R.drawable.list_menu_img_skip_focus);
            	iv_skip_btn.setImageDrawable(favo_img);
            }
            if((obj.isskip==false)&&(obj.isfavorrite==false)){
            	iv_skip_btn.setVisibility(View.INVISIBLE);
			}
            
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                short serviceType = mData.get(position).getServiceType();
                int resId;
                switch(serviceType) {
                    case TvChannelManager.SERVICE_TYPE_ATV:
                        resId = R.drawable.list_menu_img_atv_foucus;
                        break;
                    case TvChannelManager.SERVICE_TYPE_RADIO:
                        resId = R.drawable.list_menu_img_radio_foucus;
                        break;
                    case TvChannelManager.SERVICE_TYPE_DTV:
                    default:
                        resId = R.drawable.list_menu_img_dtv_foucus;
                }
                sourceimage.setImageResource(resId);
                sourceimage.setVisibility(View.VISIBLE);
            } else {
                sourceimage.setVisibility(View.GONE);
            }
            return convertView;
        }
        
      //zb20141029 add
        private int foucusItem=-1;
        public void setFocusItem(int focus)
        {
        	foucusItem=focus;
        }
        //end
    }

    private class ProgramFavoriteObject {
        private String channelId = null;

        private String channelName = null;

        public short serviceType;
        
        public boolean isfavorrite = false;
        
        public boolean isskip = false;

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public short getServiceType() {
            return serviceType;
        }

        public void setServiceType(short type) {
            this.serviceType = type;
        }
    }

    private void getListInfo() {
        pfos.clear();
        progInfoList.clear();
        ProgramInfo pgi = null;
        int indexBase = 0;

        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
            mTvIsdbChannelManager.genMixProgList(false);
            m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);
        } else {
	      ///lxk modify for showing ATV&DTV channels in programme list 20141017 start
            /*if (currInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    indexBase = 0;
                } else {
                    indexBase = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
                    if (0xFFFFFFFF == indexBase) {
                        indexBase = 0;
                    }
                }
                m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);

            } else {
                indexBase = 0;
                m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            }*/
	     {
                indexBase = 0;
                m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);
            }
	     ///lxk modify for showing ATV&DTV channels in programme list 20141017 start
        }
        Log.d(TAG, "indexBase:" + indexBase);
        Log.d(TAG, "m_nServiceNum:" + m_nServiceNum);
        for (int k = indexBase; k < m_nServiceNum; k++) {
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                pgi = mTvAtscChannelManager.getProgramInfo(k);
            } else if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
                pgi = mTvIsdbChannelManager.getProgramInfo(k);
            } else {
                pgi = mTvChannelManager.getProgramInfoByIndex(k);
            }

            if (pgi != null) {
                if (proglistId == 1) // favorate list
                {
                    if ((pgi.isDelete == true) || (pgi.isVisible == false) || pgi.favorite == 0) {
                        continue;
                    } else {
                        ProgramFavoriteObject pfo = new ProgramFavoriteObject();
                        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                            if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
                                pfo.setChannelId(String.valueOf(pgi.number));
                            } else {
                                String channum = mTvAtscChannelManager.getDispChannelNum(pgi);
                                String name = mTvAtscChannelManager.getDispChannelName(pgi);
                                pfo.setChannelId(channum);
                                pfo.setChannelName(name);
                            }
                        } else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                            if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
                                pfo.setChannelId(String.valueOf(pgi.number + 1));
                            } else {
                                String channum = pgi.majorNum + "." + pgi.minorNum;
                                pfo.setChannelId(channum);
                            }
                            pfo.setChannelName(pgi.serviceName);
                        } else {
				 ///lxk modify for showing ATV&DTV channels in programme list 20141017 start
        		 //Log.d(TAG, "pgi.number:" + pgi.number);
				 if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
    				if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
	    				pfo.setChannelId(String.valueOf(pgi.number));
    				} else {
	    				pfo.setChannelId(String.valueOf(pgi.number + 1));
    				}
				} else {
    				if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
	    				String channum = mTvAtscChannelManager.getDispChannelNum(pgi);
	    				pfo.setChannelId(channum);
    				} else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
	    				String channum = pgi.majorNum + "." + pgi.minorNum;
	    				pfo.setChannelId(channum);
    				} else {
	    				pfo.setChannelId(String.valueOf(pgi.number));
					}
				 }
                            /*if (currInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                                pfo.setChannelId(String.valueOf(pgi.number + 1));
                            } else {
                                pfo.setChannelId(String.valueOf(pgi.number));
                            }*/
				 ///lxk modify for showing ATV&DTV channels in programme list 20141017 end
                            pfo.setChannelName(pgi.serviceName);
                        }
                        Log.i("wxy","---pgi.serviceName="+pgi.serviceName+"pgi.num"+pgi.serviceId);
                      //add by wxy
                        if(pgi.favorite==0)
                        {
                        	pfo.isfavorrite = false;
                        }
                        else
                        {
                        	pfo.isfavorrite = true;
                        }
                        if(pgi.isSkip)
                        {
                        	pfo.isskip = true;
                        }
                        else
                        {
                        	pfo.isskip = false;
                        }
                        //add end
                        pfo.setServiceType(pgi.serviceType);
                        pfos.add(pfo);
                        progInfoList.add(pgi);
                    }
                } else if (proglistId == 2)// programelist
                {
                    if ((pgi.isDelete == true) || (pgi.isVisible == false)) {
                        continue;
                    } else {
                        ProgramFavoriteObject pfo = new ProgramFavoriteObject();
                        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                            String channum = mTvAtscChannelManager.getDispChannelNum(pgi);
                            String name = mTvAtscChannelManager.getDispChannelName(pgi);
                            if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
                                pfo.setChannelId(String.valueOf(pgi.number));
                            } else {
                                pfo.setChannelId(channum);
                            }
                            pfo.setChannelName(name);
                        } else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                            if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
                                pfo.setChannelId(String.valueOf(pgi.number + 1));
                            } else {
                                String channum = pgi.majorNum + "." + pgi.minorNum;
                                pfo.setChannelId(channum);
                            }
                            pfo.setChannelName(pgi.serviceName);
                        } else {
				 ///lxk modify for showing ATV&DTV channels in programme list 20141017 start
        		 Log.d(TAG, "pgi.number:" + pgi.number);
				 if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
    				if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
	    				pfo.setChannelId(String.valueOf(pgi.number));
    				} else {
	    				pfo.setChannelId(String.valueOf(pgi.number + 1));
    				}
				} else {
    				if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
	    				String channum = mTvAtscChannelManager.getDispChannelNum(pgi);
	    				pfo.setChannelId(channum);
    				} else if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
	    				String channum = pgi.majorNum + "." + pgi.minorNum;
	    				pfo.setChannelId(channum);
    				} else {
	    				pfo.setChannelId(String.valueOf(pgi.number));
					}
				 }
                            /*if (currInputSource == TvCommonManager.INPUT_SOURCE_ATV) {
                                pfo.setChannelId(String.valueOf(pgi.number + 1));
                            } else {
                                pfo.setChannelId(String.valueOf(pgi.number));
                            }*/
				 ///lxk modify for showing ATV&DTV channels in programme list 20141017 end
                            pfo.setChannelName(pgi.serviceName);
                        }
                        pfo.setServiceType(pgi.serviceType);
                      //add by wxy
                        if(pgi.favorite==0)
                        {
                        	pfo.isfavorrite = false;
                        }
                        else
                        {
                        	pfo.isfavorrite = true;
                        }
                        if(pgi.isSkip)
                        {
                        	pfo.isskip = true;
                        }
                        else
                        {
                        	pfo.isskip = false;
                        }
                        //add end
                        pfos.add(pfo);
                        progInfoList.add(pgi);
                    }
                }
            }
        }
    }

    private boolean isNeedToCheckExitRecord(ProgramInfo pi) {
        if (pi == null) {
            return false;
        }
        /* Always time shift recording will auto stop by tvsystem. */
        final TvPvrManager pvr = TvPvrManager.getInstance();
        if (PVRActivity.currentRecordingProgrammFrency != pi.frequency
                && pvr.isAlwaysTimeShiftRecording() == false
                && pvr.isRecording()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        proglistId = getIntent().getIntExtra("ListId", 0);
        setContentView(R.layout.program_favorite_list);
        redrawComponents();
        proListView = (ListView) findViewById(R.id.program_favorite_list_view);
        mTextTitle = (TextView) findViewById(R.id.program_favorite_title);
        mHintOP = (TextView) findViewById(R.id.program_favorite_str_op);
        mHintDelOP = (TextView) findViewById(R.id.program_favorite_str_del_op);
        mOPListAdapter = new OperatorProfileListAdapter(this);
        if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
            mTvChannelManager = TvIsdbChannelManager.getInstance();
            mTvIsdbChannelManager = TvIsdbChannelManager.getInstance();
            Log.d(TAG, "oncreate===antennaType = " + mTvIsdbChannelManager.getAntennaType());
            mTvIsdbChannelManager.genMixProgList(false);
        } else {
            if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                mTvAtscChannelManager = TvAtscChannelManager.getInstance();
            }
            mTvChannelManager = TvChannelManager.getInstance();
            refreshOPProfileList();
        }
        getListInfo();
        adapter = new ChannelListAdapter(this, pfos);

        if (TvCiManager.getInstance().isOpMode()) {
            Log.i(TAG, "E_OP_CHANNEL_LIST_MENU");
            SwitchChannelListState(EnumChannelListState.E_OP_CHANNEL_LIST_MENU);
        } else {
            Log.i(TAG, "E_NORMAL_CHANNEL_LIST_MENU");
            SwitchChannelListState(EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU);
        }
        proListView.setDividerHeight(0);
        proListView.setSelection(getfocusIndex());
        proListView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                int selItemIndex = (int) proListView.getSelectedItemId();
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && (keyEvent.getAction() == KeyEvent.ACTION_UP)) {
                    Log.d(TAG, "selItemIndex" + selItemIndex);

                    if (selItemIndex >= progInfoList.size()) {
                        return false;
                    }
                  //zb20141029 add
                    adapter.setFocusItem(selItemIndex);
                    adapter.notifyDataSetChanged();
                    //end
                    
                    final ProgramInfo ProgInf = progInfoList.get(selItemIndex);
                    Log.d(TAG, "number" + ProgInf.number);
                    if(true == isSameWithCurrentProgram(ProgInf)) {
                        Log.d(TAG, "CH List :Select the same channel!!!");
                    } else {
                        if (ProgInf.serviceType < TvChannelManager.SERVICE_TYPE_INVALID) {
                            if (isNeedToCheckExitRecord(ProgInf)) {
                                // Toast.makeText
                                AlertDialog.Builder build = new AlertDialog.Builder(
                                        ChannelListActivity.this);
                                build.setMessage(R.string.str_pvr_tip2);
                                build.setPositiveButton(R.string.str_stop_record_dialog_stop,
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                    int which) {
                                                TvPvrManager.getInstance().stopRecord();
                                                PVRActivity.currentRecordingProgrammFrency = -1;
                                                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                                                    mTvAtscChannelManager.programSel(ProgInf.majorNum, ProgInf.minorNum);
                                                } else {
                                                    mTvChannelManager.selectProgram(ProgInf.number, ProgInf.serviceType);
                                                }
                                            }
                                        });
                                build.setNegativeButton(R.string.str_stop_record_dialog_cancel,
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                    int which) {
                                            }
                                        });
                                build.create().show();
                            } else {
                                    if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                                        mTvAtscChannelManager.programSel(ProgInf.majorNum, ProgInf.minorNum);
                                    } else {
                                        mTvChannelManager.selectProgram(ProgInf.number, ProgInf.serviceType);
                                    }
                                }
                        } else {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        timeOutHelper = new TimeOutHelper(handler, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDtvEventListener = new DtvEventListener();
        TvChannelManager.getInstance().registerOnDtvPlayerEventListener(mDtvEventListener);

        if (mstate == EnumChannelListState.E_OPERATOR_PROFILE_MENU)
            SwitchChannelListState(EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU);
        timeOutHelper.start();
        timeOutHelper.init();
        LittleDownTimer.resumeMenu();
        LittleDownTimer.resumeItem();
    };

    @Override
    protected void onPause() {
        TvChannelManager.getInstance().unregisterOnDtvPlayerEventListener(mDtvEventListener);
        mDtvEventListener = null;
        timeOutHelper.stop();
        super.onPause();
    }
//add by wxy
    @Override
    public void onUserInteraction() {
        LittleDownTimer.resetMenu();
        LittleDownTimer.resetItem();
        super.onUserInteraction();
    }
//add end
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        timeOutHelper.reset();
        String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
        if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP) {
            if (deviceName.equals("MStar Smart TV Keypad")) {
                proListView.setSelection(proListView.getSelectedItemPosition() == 0 ? 0
                        : proListView.getSelectedItemPosition() - 1);
                return true;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN) {
            if (deviceName.equals("MStar Smart TV Keypad")) {
                proListView.setSelection(proListView.getSelectedItemPosition() == (proListView
                        .getCount() - 1) ? (proListView.getCount() - 1) : (proListView
                        .getSelectedItemPosition() + 1));
                return true;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (proListView.getSelectedItemPosition() == 0) {
                proListView.setSelection(pfos.size() - 1);
                return true;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (proListView.getSelectedItemPosition() == pfos.size() - 1) {
                proListView.setSelection(0);
                return true;
            }
        }

        if (keyCode == KeyEvent.KEYCODE_PROG_BLUE) {
            // Focus is not on channel list currently, ignore key.
            if (!proListView.isFocused())
                return true;

            if (mstate == EnumChannelListState.E_OPERATOR_PROFILE_MENU) {
                displayDeleteOpconfirmation();
                mOPListAdapter.notifyDataSetChanged();
            }
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_PROG_RED) {
                boolean bOpMode = false;
                bOpMode = TvCiManager.getInstance().isOpMode();

                if (mstate == EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU
                        && (mOPCount != 0)
                        && (TvCommonManager.getInstance().getCurrentTvInputSource() == TvCommonManager.INPUT_SOURCE_DTV)
                        && (bOpMode == false)) {
                    Log.i(TAG, "MVK_RED: Enter E_OPERATOR_PROFILE_MENU");

                    SwitchChannelListState(EnumChannelListState.E_OPERATOR_PROFILE_MENU);
                    mOPListAdapter.notifyDataSetChanged();
                } else if (mstate == EnumChannelListState.E_OPERATOR_PROFILE_MENU) {
                    if (mOPCount != 0) {
                        OperatorProfileInfo selectItem = (OperatorProfileInfo) mOPListAdapter
                                .getItem(proListView.getSelectedItemPosition());
                        if (selectItem.getOPAcceisable() == true) {
                            Log.i(TAG, "MVK_RED: Enter E_OP_CHANNEL_LIST_MENU");
                            Log.i(TAG, "enterCiOperatorProfile!");
                            TvCiManager.getInstance().enterCiOperatorProfile(
                                    selectItem.getOPCacheResideIndex());
                        } else {
                            Log.i(TAG, "Forbid Entering OP Mode!");
                        }
                    }
                } else if (mstate == EnumChannelListState.E_OP_CHANNEL_LIST_MENU
                        && (bOpMode == true)) {
                    Log.i(TAG, "MVK_RED: Enter E_NORMAL_CHANNEL_LIST_MENU");

                    TvCiManager.getInstance().exitCiOperatorProfile();
                }
                return true;
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Intent intent = new Intent(TvIntent.MAINMENU);
            intent.putExtra("currentPage", MainMenuActivity.SOUND_PAGE);
            startActivity(intent);
            finish();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_TV_INPUT) {
            finish();
            return true;
        }
        if (keyCode == MKeyEvent.KEYCODE_LIST) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private int getfocusIndex() {
        int focusIndex = 0;
        if (proglistId == 2) // program list
        {
            ProgramInfo cpi = mTvChannelManager.getCurrentProgramInfo();
            for (ProgramInfo pi: progInfoList) {
                if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                    if ((cpi.majorNum == pi.majorNum)
                        && (cpi.minorNum == pi.minorNum)
                        && (cpi.serviceType == pi.serviceType)) {
                        focusIndex = progInfoList.indexOf(pi);
                        break;
                    }
                } else {
                    if (cpi.number == pi.number
                        && cpi.serviceType == pi.serviceType) {
                        focusIndex = progInfoList.indexOf(pi);
                        break;
                    }
                }
            }
        }
        //zb20141029 add
        adapter.setFocusItem(focusIndex);
        adapter.notifyDataSetChanged();
        //end
        return focusIndex;
    }

    private void redrawComponents() {
        TextView title = (TextView) findViewById(R.id.program_favorite_title);
        if (proglistId == 1) {
            title.setText(R.string.str_channelList_favorite);
        } else if (proglistId == 2) {
            title.setText(R.string.str_channelList_program);
        }
    }

    private void SwitchChannelListState(EnumChannelListState state) {
        if (state == EnumChannelListState.E_NORMAL_CHANNEL_LIST_MENU) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
                    if ((mOPCount != 0) && (currInputSource == TvCommonManager.INPUT_SOURCE_DTV))
                        mHintOP.setVisibility(TextView.VISIBLE);
                    else
                        mHintOP.setVisibility(TextView.GONE);
                    mHintDelOP.setVisibility(TextView.GONE);
                    if(proglistId==2)
                    mTextTitle.setText(R.string.str_channelList_program);
                    mHintOP.setText(R.string.str_op_menu);
                    proListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
            getfocusIndex();
            mstate = state;
        } else if (state == EnumChannelListState.E_OPERATOR_PROFILE_MENU) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    mHintOP.setVisibility(TextView.VISIBLE);
                    mHintDelOP.setVisibility(TextView.VISIBLE);
                    mTextTitle.setText(R.string.str_op_menu_title);
                    mHintOP.setText(R.string.str_enter_op);
                    refreshOPProfileList();
                    proListView.setAdapter(mOPListAdapter);
                    mOPListAdapter.notifyDataSetChanged();
                }
            });
            mstate = state;
        } else if (state == EnumChannelListState.E_OP_CHANNEL_LIST_MENU) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    mHintOP.setVisibility(TextView.VISIBLE);
                    mHintDelOP.setVisibility(TextView.GONE);

                    mTextTitle.setText(R.string.str_op_channel_list_title);
                    mHintOP.setText(R.string.str_exit_op);
                    proListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
            getfocusIndex();
            mstate = state;
        } else {
            Log.v(TAG, "Set unkonwn state mstate = " + state);
        }
    }

    private void displayDeleteOpconfirmation() {
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View layout = factory.inflate(R.layout.delete_op_confirmation_dialog, null);

        String dialogcontent = null;
        String opName = null;
        OperatorProfileInfo selectItem = (OperatorProfileInfo) mOPListAdapter
                .getItem(proListView.getSelectedItemPosition());

        opName = TvCiManager.getInstance().getOpProfileNameByIndex(
                selectItem.getOPCacheResideIndex());

        dialogcontent = getString(R.string.str_delete_op_msg) + opName + "] ?";

        mDeleteOpconfirmation = new AlertDialog.Builder(getApplicationContext())
                .setTitle(getString(R.string.str_delete_op_confirm))
                .setView(layout)
                .setMessage(dialogcontent)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setPositiveButton(getString(android.R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OperatorProfileInfo deleteItem = (OperatorProfileInfo) mOPListAdapter
                                        .getItem(proListView.getSelectedItemPosition());
                                TvCiManager.getInstance().deleteOpCacheByIndex(
                                        deleteItem.getOPCacheResideIndex());
                                mOPCount--;
                                mOPListAdapter.remove(deleteItem);
                            }
                        })
                .setNegativeButton(getString(android.R.string.no), null)
                .create();
        mDeleteOpconfirmation.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        mDeleteOpconfirmation.show();
    }

    private boolean isSameWithCurrentProgram(ProgramInfo ProgInf) {
        boolean ret = false;
        ProgramInfo curProgInfo = mTvChannelManager.getCurrentProgramInfo();

        if (mTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
            if ((curProgInfo.majorNum == ProgInf.majorNum)
                && (curProgInfo.minorNum == ProgInf.minorNum)
                && (curProgInfo.serviceType == ProgInf.serviceType)) {
                ret = true;
            }
        } else {
            if ((curProgInfo.number == ProgInf.number)
                && (curProgInfo.serviceType == ProgInf.serviceType)) {
                ret = true;
            }
        }
        return ret;
    }

    private void refreshOPProfileList() {
        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();

        if (mOPListAdapter != null) {
            mOPListAdapter.clear();
        } else {
            Log.e(TAG, "mOPListAdapter is null!!");
            return;
        }

        if (currInputSource == TvCommonManager.INPUT_SOURCE_DTV) {
            List<OperatorProfileInfo> mopdatalist = new ArrayList<OperatorProfileInfo>();

            short opCacheCount = TvCiManager.getInstance().getOpCacheCount();
            Log.i(TAG, "opCacheCount:" + opCacheCount);

            for (int i = 0; i < opCacheCount; i++) {
                short opsystype = TvCiManager.getInstance().getOpDtvSysTypeByIndex((short) i);
                Log.i(TAG, "opsystype:" + opsystype);
                if (opsystype != OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_NONE
                        .ordinal()) {
                    mOPCount++;
                    OperatorProfileInfo opInfo = new OperatorProfileInfo();
                    String opName = TvCiManager.getInstance().getOpProfileNameByIndex((short) i);
                    Log.i(TAG, "OpName:" + opName);
                    int dtvRouteIndex = TvChannelManager.DTV_ROUTE_INDEX_MAX_COUNT;
                    dtvRouteIndex = mTvChannelManager.getCurrentDtvRouteIndex();
                    TvTypeInfo tvinfo = TvCommonManager.getInstance().getTvInfo();

                    opInfo.setId(i);
                    opInfo.setOPCacheResideIndex((short) i);
                    opInfo.setOperatorProfileName(opName);
                    opInfo.setOPSysType(opsystype);

                    if (opsystype == OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_TDSD
                            .ordinal()) {
                        if ((TvChannelManager.TV_ROUTE_DVBT == tvinfo.routePath[dtvRouteIndex])
                                || (TvChannelManager.TV_ROUTE_DVBT2 == tvinfo.routePath[dtvRouteIndex])) {
                            opInfo.setOPAcceisable(true);
                        } else {
                            opInfo.setOPAcceisable(false);
                        }
                    } else if (opsystype == OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_CDSD
                            .ordinal()) {
                        if (TvChannelManager.TV_ROUTE_DVBC == tvinfo.routePath[dtvRouteIndex]) {
                            opInfo.setOPAcceisable(true);
                        } else {
                            opInfo.setOPAcceisable(false);
                        }
                    } else if (opsystype == OperatorProfileInfo.EnumDeliverySysType.E_DELIVERY_SYS_SDSD
                            .ordinal()) {
                        opInfo.setOPAcceisable(false);
                    } else {
                        opInfo.setOPAcceisable(false);
                    }

                    mopdatalist.add(opInfo);
                } else {
                    Log.w(TAG, "[Warning!] DTV Type is E_DELIVERY_SYS_NONE");
                }

            }
            mOPListAdapter.setItems(mopdatalist);
        }
    }
}
