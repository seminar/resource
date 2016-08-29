
package com.mstar.tv.tvplayer.ui.dtv.parental.dvb;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;

public class BlockProgramActivity extends MstarBaseActivity {

    private int m_nServiceNum = 0;

    private ListView proListView;

    private ArrayList<ProgramFavoriteObject> pfos = new ArrayList<ProgramFavoriteObject>();

    private ArrayList<ProgramInfo> progInfoList = new ArrayList<ProgramInfo>();

    private ChannelListAdapter adapter = null;

    private boolean[] ischannellocked = null;

    private TvChannelManager mTvChannelManager = null;

    public class ChannelListAdapter extends BaseAdapter {
        ArrayList<ProgramFavoriteObject> mData = null;

        private Context mContext;

        private boolean[] ischannellocked = null;

        public ChannelListAdapter(Context context, ArrayList<ProgramFavoriteObject> data,
                boolean[] ischannellock) {
            mContext = context;
            mData = data;
            ischannellocked = ischannellock;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.block_program_item, null);
            TextView tmpText = (TextView) convertView
                    .findViewById(R.id.program_parentalcontrol_number);
            tmpText.setText(mData.get(position).getChannelId());
            tmpText = (TextView) convertView.findViewById(R.id.program_parentalcontrol_data);
            tmpText.setText(mData.get(position).getChannelName());
            ImageView lock = (ImageView) convertView.findViewById(R.id.is_pclocked);
            if (ischannellocked[position]) {
                lock.setVisibility(View.VISIBLE);
            } else {
                lock.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    private class ProgramFavoriteObject {
        private String channelId = null;

        private String channelName = null;

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
    }

    private void getListInfo() {
        ProgramInfo pgi = null;
        int indexBase = 0;

        int currInputSource = TvCommonManager.getInstance().getCurrentTvInputSource();
        int curTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        if (curTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
            TvIsdbChannelManager.getInstance().genMixProgList(false);
            m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV_DTV);
        } else {
            if (currInputSource != TvCommonManager.INPUT_SOURCE_DTV) {
                m_nServiceNum = 0;
            } else {
                m_nServiceNum = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_DTV);
            }
        }
        ischannellocked = new boolean[m_nServiceNum];
        for (int k = indexBase; k < m_nServiceNum; k++) {
            if (curTvSystem == TvCommonManager.TV_SYSTEM_ATSC) {
                pgi = TvAtscChannelManager.getInstance().getProgramInfo(k);
            } else if (curTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                pgi = TvIsdbChannelManager.getInstance().getProgramInfo(k);
            } else {
                pgi = mTvChannelManager.getProgramInfoByIndex(k);
            }
            if (pgi != null) {

                ProgramFavoriteObject pfo = new ProgramFavoriteObject();
                if (curTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                    if (pgi.serviceType == TvChannelManager.SERVICE_TYPE_ATV) {
                        pfo.setChannelId(String.valueOf(pgi.number + 1));
                    } else {
                        String channum = pgi.majorNum + "." + pgi.minorNum;
                        pfo.setChannelId(channum);
                    }
                } else {
                    pfo.setChannelId(String.valueOf(pgi.number));
                }
                pfo.setChannelName(pgi.serviceName);
                ischannellocked[k] = pgi.isLock;
                pfos.add(pfo);
                progInfoList.add(pgi);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_program);
        proListView = (ListView) findViewById(R.id.block_program_list);
        TextView title = (TextView) findViewById(R.id.block_program_title);
        title.setText(R.string.block_program_title);
        mTvChannelManager = TvChannelManager.getInstance();
        getListInfo();
        adapter = new ChannelListAdapter(this, pfos, ischannellocked);
        proListView.setAdapter(adapter);
        proListView.setDividerHeight(0);
        proListView.setSelection(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SharedPreferences share2 = getSharedPreferences("menu_check_pwd", Activity.MODE_PRIVATE);
            Editor editor = share2.edit();
            editor.putBoolean("pwd_ok", true);
            editor.commit();
            Intent intent = new Intent(TvIntent.MAINMENU);
            intent.putExtra("currentPage", MainMenuActivity.LOCK_PAGE);
            startActivity(intent);
            finish();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_PROG_GREEN) {
            int select = proListView.getSelectedItemPosition();
            ProgramInfo progInf = progInfoList.get(select);
            boolean islock = progInf.isLock;
            islock = !islock;
            progInf.isLock = islock;

            mTvChannelManager.setProgramAttribute(TvChannelManager.PROGRAM_ATTRIBUTE_LOCK, progInf.number,
                    progInf.serviceType, 0x00, islock);
            ischannellocked[select] = islock;
            adapter.notifyDataSetChanged();
        }
        return super.onKeyDown(keyCode, event);
    }

}
