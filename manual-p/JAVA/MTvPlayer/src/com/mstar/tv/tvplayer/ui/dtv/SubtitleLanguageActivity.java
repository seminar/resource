
package com.mstar.tv.tvplayer.ui.dtv;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;
import com.mstar.android.tvapi.dtv.vo.MenuSubtitleService;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;

public class SubtitleLanguageActivity extends MstarBaseActivity {
    private SubtitleAdapter adapter = null;

    private ListView subtitleLanguageListView = null;

    private static int subtitlePos = 0;

    private TimeOutHelper timeOutHelper;

    private TvChannelManager mTvChannelManager;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TimeOutHelper.getTimeOutMsg()) {
                finish();
            }
        }
    };

    private static class SubtitleAdapter extends BaseAdapter {

        private DataHolder list[];

        private LayoutInflater inflater;

        public SubtitleAdapter(Activity context, DataHolder list[]) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (list != null) {
                return list.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int arg0) {

            return arg0;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.subtitle_item, null);
                holder = new ViewHolder();
                holder.language = (TextView) convertView.findViewById(R.id.language);
                holder.hoh = (ImageView) convertView.findViewById(R.id.hoh);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.language.setText(list[position].language);
            if (list[position].isHOH) {
                holder.hoh.setVisibility(View.VISIBLE);
            } else {
                holder.hoh.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        private static class ViewHolder {
            public TextView language;

            public ImageView hoh;
        }

    }

    private static boolean isHOHSubtitle(int type) {
        /**
         * // dvb subtitle hard of hearing E_SUBTITLING_TYPE_HH_NO(0x20), 32 //
         * dvb subtitle hard of hearing 4:3 E_SUBTITLING_TYPE_HH_4X3(0x21), 33
         * // dvb subtitle hard of hearing 16:9 E_SUBTITLING_TYPE_HH_16X9(0x22),
         * 34 // dvb subtitle hard of hearing 221:100
         * E_SUBTITLING_TYPE_HH_221X100(0x23), 35 // dvb HD subtitle hard of
         * hearing E_SUBTITLING_TYPE_HH_HD(0x24); 36
         */
        if (type == 32 || type == 33 || type == 34 || type == 35 || type == 36) {
            return true;
        }
        return false;
    }

    private static class DataHolder {
        public String language;

        public boolean isHOH;
    }

    private int currentSubtitleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subtitle_language);
        subtitleLanguageListView = (ListView) findViewById(R.id.subtitle_language_list_view);

        TextView title = (TextView) findViewById(R.id.subtitle_language_title);
        String str = "Subtitle Language";
        title.setText(str);

        DtvSubtitleInfo subtitleInfo = new DtvSubtitleInfo();
        mTvChannelManager = TvChannelManager.getInstance();

        subtitleInfo = mTvChannelManager.getSubtitleInfo();

        Log.e("qhc", "subtitleInfo=" + subtitleInfo.currentSubtitleIndex);
        currentSubtitleIndex = subtitleInfo.currentSubtitleIndex;
        if (currentSubtitleIndex == 255) {
            currentSubtitleIndex = 0;
        } else {
            currentSubtitleIndex++;
        }

        if (subtitleInfo.subtitleServiceNumber > 0) {
            // String[] subtitleLang = new
            // String[subtitleInfo.subtitleServiceNumber + 1];
            //
            // subtitleLang[0] = "CLOSE";
            // for (short i = 0; i < subtitleInfo.subtitleServiceNumber; i++)
            // {
            // subtitleLang[i+1] = subtitleInfo.subtitleServices[i]
            // .getLanguage().name();
            // }
            // adapter = new ArrayAdapter<String>(this,
            // R.layout.pvr_menu_info_list_view_item, subtitleLang);

            MenuSubtitleService list[] = subtitleInfo.subtitleServices;

            DataHolder[] data = new DataHolder[subtitleInfo.subtitleServiceNumber + 1];
            DataHolder tmp = null;
            for (int i = 0; i < data.length; i++) {
                tmp = new DataHolder();
                if (i == 0) {
                    tmp.language = "CLOSE";
                    tmp.isHOH = false;
                } else {
                    tmp.language = list[i - 1].getLanguage().name();
                    tmp.isHOH = isHOHSubtitle(list[i - 1].enSubtitleType);
                }
                data[i] = tmp;

            }
            adapter = new SubtitleAdapter(SubtitleLanguageActivity.this, data);
            subtitleLanguageListView.setAdapter(adapter);
            subtitleLanguageListView.setDividerHeight(0);

            subtitleLanguageListView.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    subtitlePos = arg0.getSelectedItemPosition();
                    System.out.println("\n=====>Set subtitle language index:" + subtitlePos);
                    TvChannelManager.getInstance().closeSubtitle();
                    if (subtitlePos > 0) {
                        mTvChannelManager.openSubtitle((subtitlePos - 1));
                    }
                    SharedPreferences settings = getSharedPreferences(Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
                    Editor editor = settings.edit();
                    editor.putInt("subtitlePos", subtitlePos);
                    editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
        }

        timeOutHelper = new TimeOutHelper(handler, this);
    }

    @Override
    protected void onResume() {
        // SharedPreferences settings = getSharedPreferences("TvSetting", 0);
        // subtitlePos = settings.getInt("subtitlePos", 0);
        // subtitleLanguageListView.setSelection(subtitlePos);
        subtitleLanguageListView.setSelection(currentSubtitleIndex);
        timeOutHelper.start();
        timeOutHelper.init();
        super.onResume();
    }

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
}
