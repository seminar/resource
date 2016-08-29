
package com.mstar.tv.tvplayer.ui.dtv.epg;

import java.util.ArrayList;

import com.mstar.tv.tvplayer.ui.holder.EPGViewHolder;
import com.mstar.tv.tvplayer.ui.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EPGAdapter extends BaseAdapter {
    ArrayList<EPGViewHolder> mData = null;

    Activity act = null;

    public EPGAdapter(Activity activity, ArrayList<EPGViewHolder> vh) {
        mData = vh;
        act = activity;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(act).inflate(R.layout.programme_epg_item, null);
        }
        TextView channelName = (TextView) convertView
                .findViewById(R.id.programme_epg_list_view_item_text_view_title);
        TextView channelInfo = (TextView) convertView
                .findViewById(R.id.programme_epg_list_view_item_text_view);
        channelName.setText(mData.get(position).getChannelName());
        channelInfo.setText(mData.get(position).getChannelInfo());
        return convertView;
    }
}
