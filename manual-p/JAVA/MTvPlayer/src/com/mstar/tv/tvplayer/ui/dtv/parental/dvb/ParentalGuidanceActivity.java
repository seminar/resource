
package com.mstar.tv.tvplayer.ui.dtv.parental.dvb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;

public class ParentalGuidanceActivity extends MstarBaseActivity {
    private TextView title;

    private ListView guidancelist;

    private String[] parental;

    private boolean[] rating;

    private GuidanceListAdapter adapter;

    private class GuidanceListAdapter extends BaseAdapter {
        String[] content = null;

        private Context mContext;

        private boolean[] rating;

        public GuidanceListAdapter(Context context, String[] data, boolean[] rate) {
            mContext = context;
            content = data;
            rating = rate;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return content.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return content[arg0];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.guidance_item, null);
            }
            convertView = LayoutInflater.from(mContext).inflate(R.layout.guidance_item, null);
            TextView tmpText = (TextView) convertView.findViewById(R.id.guidance_content);
            tmpText.setText(content[position]);
            ImageView islock = (ImageView) convertView.findViewById(R.id.ifchoice);
            if (rating[position]) {
                islock.setVisibility(View.GONE);
            }
            return convertView;
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parental_guidance);
        parental = getResources().getStringArray(R.array.guidance_list);
        title = (TextView) findViewById(R.id.parental_guidance_title);
        title.setText(getResources().getString(R.string.str_parental_guidance));
        guidancelist = (ListView) findViewById(R.id.parental_guidance_list);
        int rate = TvParentalControlManager.getInstance().getParentalControlRating();

        rating = new boolean[parental.length];
        for (int i = 0; i < parental.length; i++) {
            rating[i] = true;
        }
        if ((rate < 19) && (rate > 4)) {
            rating[rate - 4] = false;
        } else {
            rating[0] = false;
        }
        adapter = new GuidanceListAdapter(this, parental, rating);
        guidancelist.setAdapter(adapter);
        setListener();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (guidancelist.getSelectedItemPosition() == 0) {
                guidancelist.setSelection(parental.length - 1);
                return true;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (guidancelist.getSelectedItemPosition() == parental.length - 1) {
                guidancelist.setSelection(0);
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
        return super.onKeyDown(keyCode, event);
    }

    private void setListener() {
        guidancelist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                // TODO Auto-generated method stub
                int oldrate = TvParentalControlManager.getInstance().getParentalControlRating();
                Log.v("qhc", "oldrate===" + oldrate);
                if (arg2 == 0) {
                    if (oldrate == 0) {
                        return;
                    } else {
                        rating[oldrate - 4] = true;
                        rating[arg2] = false;
                    }
                    TvParentalControlManager.getInstance().setParentalControlRating(0);
                } else {
                    if (oldrate == 0) {
                        rating[0] = true;

                    } else {
                        rating[oldrate - 4] = true;
                    }
                    rating[arg2] = false;
                    TvParentalControlManager.getInstance().setParentalControlRating(arg2 + 4);
                }

                adapter.notifyDataSetChanged();

            }
        });
    }
}
