
package com.mstar.tv.tvplayer.ui.tuning;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tvframework.MstarBaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseCityForAutoTuneActivity extends MstarBaseActivity {
    /** Called when the activity is first created. */
    private Button button0;

    private Button button1;

    private Button button2;

    private Button button3;

    private Button button4;

    private Button button5;

    private Button button6;

    private Button button7;

    private Button button8;

    private static Button[] buttonArray = new Button[9];

    private int currentPage = 0;

    private final int ONEPINDEXS = 9;

    private int MAXINDEXS;

    private static int pagesCount = 0;

    private String[] OptionCities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecityforautotuning);
        findViews();
        goToPage(0);
        registerListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void findViews() {
        button0 = (Button) findViewById(R.id.button_cha_choose_city_1);
        button1 = (Button) findViewById(R.id.button_cha_choose_city_2);
        button2 = (Button) findViewById(R.id.button_cha_choose_city_3);
        button3 = (Button) findViewById(R.id.button_cha_choose_city_4);
        button4 = (Button) findViewById(R.id.button_cha_choose_city_5);
        button5 = (Button) findViewById(R.id.button_cha_choose_city_6);
        button6 = (Button) findViewById(R.id.button_cha_choose_city_7);
        button7 = (Button) findViewById(R.id.button_cha_choose_city_8);
        button8 = (Button) findViewById(R.id.button_cha_choose_city_9);
        buttonArray[0] = button0;
        buttonArray[1] = button1;
        buttonArray[2] = button2;
        buttonArray[3] = button3;
        buttonArray[4] = button4;
        buttonArray[5] = button5;
        buttonArray[6] = button6;
        buttonArray[7] = button7;
        buttonArray[8] = button8;
        buttonArray[1] = button0;
        OptionCities = getResources().getStringArray(R.array.cities_option_array);
        pagesCount = OptionCities.length / ONEPINDEXS;

    }

    protected void goToPage(int pageNo) {
        if (pageNo < pagesCount && pageNo >= 0) {
            button0.setText(OptionCities[pageNo * 9 + 0]);
            button1.setText(OptionCities[pageNo * 9 + 1]);
            button2.setText(OptionCities[pageNo * 9 + 2]);
            button3.setText(OptionCities[pageNo * 9 + 3]);
            button4.setText(OptionCities[pageNo * 9 + 4]);
            button5.setText(OptionCities[pageNo * 9 + 5]);
            button6.setText(OptionCities[pageNo * 9 + 6]);
            button7.setText(OptionCities[pageNo * 9 + 7]);
            button8.setText(OptionCities[pageNo * 9 + 8]);
            button0.setVisibility(View.VISIBLE);
            button0.setFocusable(true);
            button1.setVisibility(View.VISIBLE);
            button1.setFocusable(true);
            button2.setVisibility(View.VISIBLE);
            button2.setFocusable(true);
            button3.setVisibility(View.VISIBLE);
            button3.setFocusable(true);
            button4.setVisibility(View.VISIBLE);
            button4.setFocusable(true);
            button5.setVisibility(View.VISIBLE);
            button5.setFocusable(true);
            button6.setVisibility(View.VISIBLE);
            button6.setFocusable(true);
            button7.setVisibility(View.VISIBLE);
            button7.setFocusable(true);
            button8.setVisibility(View.VISIBLE);
            button8.setFocusable(true);

        } else if (pageNo == pagesCount) {
            for (int index = 0; index < OptionCities.length % 9; index++) {
                Log.i("ChooseCity", "@@@@@@@@~~~~~index is " + index);
                buttonArray[index].setText(OptionCities[pageNo * 9 + index]);
            }
            for (int indx = OptionCities.length % 9; indx < 9; indx++) {
                Log.i("ChooseCity", "@@@@@@@@~~~~~indx is " + indx);
                buttonArray[indx].setVisibility(View.INVISIBLE);
                buttonArray[indx].setFocusable(false);
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentid = getCurrentFocus().getId();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.button_cha_choose_city_7:
                    case R.id.button_cha_choose_city_8:
                    case R.id.button_cha_choose_city_9: {
                        if (currentPage < pagesCount) {
                            currentPage++;
                            Log.i("ChooseCity", "@@@@@@@____currentPage is " + currentPage);
                            goToPage(currentPage);
                        }

                    }
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.button_cha_choose_city_1:
                    case R.id.button_cha_choose_city_2:
                    case R.id.button_cha_choose_city_3: {
                        if (currentPage >= 0) {
                            currentPage--;
                            goToPage(currentPage);
                        }
                    }
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                Intent intent = new Intent();
                intent.setClass(ChooseCityForAutoTuneActivity.this, AutoTuneOptionActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerListeners() {
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button0.setOnClickListener(listener);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            switch (view.getId()) {
                case R.id.button_cha_choose_city_1:

                    // break;
                case R.id.button_cha_choose_city_2:

                    // break;
                case R.id.button_cha_choose_city_3:

                    // break;
                case R.id.button_cha_choose_city_4:

                    // break;
                case R.id.button_cha_choose_city_5:

                    // break;
                case R.id.button_cha_choose_city_6:

                    // break;
                case R.id.button_cha_choose_city_7:

                    // break;
                case R.id.button_cha_choose_city_8:

                    // break;
                case R.id.button_cha_choose_city_9:
                    Intent intent = new Intent();
                    intent.setClass(ChooseCityForAutoTuneActivity.this,
                            DTVAutoTuneOptionActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

}
