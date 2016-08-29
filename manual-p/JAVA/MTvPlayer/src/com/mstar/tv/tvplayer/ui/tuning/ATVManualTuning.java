
package com.mstar.tv.tvplayer.ui.tuning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;

import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvIsdbChannelManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.listener.OnMhlEventListener;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.atv.vo.AtvEventScan;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tvframework.MstarBaseActivity;

public class ATVManualTuning extends MstarBaseActivity {
    /** Called when the activity is first created. */
    private static final String TAG = "ATVManualTuning";

    private static final int USER_CHANGE_CHANNEL_TIMEOUT = 1000;

    private ViewHolder viewholder_atvmanualtuning;

    private int mColorSystemIndex = 0;

    //lxk add for manul tuning input opteration 20141014
	private static int iIdleDigitCount = 0;
    private static int iIdleInputValue = 0;
	//end

    private int mSoundSystemIndex = 0;

    private String mSoundSystem[] = { "BG", "DK", "I", "L", "M" };

    //zb20141013 modify
    private String mColorSystem[] = { "PAL", "NTSC", "SECAM", "AUTO" };
//    private String mColorSystem[] = { "PAL", "NTSC_M", "SECAM", "NTSC_44", "PAL_M", "PAL_N", "PAL_60",
//            "NO_STAND", "AUTO" };
    //end
    
    public static TvChannelManager mTvChannelManager = null;

    private OnAtvPlayerEventListener mAtvPlayerEventListener = null;

    private Handler mAtvUiEventHandler = null;

    private int mTvSystem = 0;

    private int mChannelMax = 0;

    private int mChannelMin = 0;

    private int mCurChannelNumber = 0;

    private BroadcastReceiver mReceiver = null;

    private Handler mDirectChangeChTimeout = new Handler();
    
    public static boolean isSearchByUser=false;//add by wxy
    
    private Runnable mDirectChangeChTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            setIsdbAtvChannel(mCurChannelNumber - 1);
            updateAtvManualtuningComponents();
            mCurChannelNumber = -1;
        }
    };

    private class AtvPlayerEventListener implements OnAtvPlayerEventListener {

        @Override
        public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
            Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
            mAtvUiEventHandler.sendMessage(msg);
            return true;
        }

        @Override
        public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
            Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
            mAtvUiEventHandler.sendMessage(msg);
            return true;
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
        public boolean onAtvProgramInfoReady(int what) {
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atvmanualtuning);
        mTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        mTvChannelManager = TvChannelManager.getInstance();

        viewholder_atvmanualtuning = new ViewHolder(ATVManualTuning.this);
        viewholder_atvmanualtuning.findViewForAtvManualTuning();
        // registerListeners();
	  iIdleDigitCount = 0;

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE)) {
                    Log.i(TAG, "Receive ACTION_CIPLUS_TUNER_UNAVAIABLE...");
                    finish();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
        registerReceiver(mReceiver, filter);

        if (TvCommonManager.getInstance().getCurrentTvInputSource() != TvCommonManager.INPUT_SOURCE_ATV) {
            if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
                mTvChannelManager.changeToFirstService(TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                        TvChannelManager.FIRST_SERVICE_MENU_SCAN);
            } else {
                TvCommonManager.getInstance().setInputSource(TvCommonManager.INPUT_SOURCE_ATV);
                mTvChannelManager.changeToFirstService(TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV,
                        TvChannelManager.FIRST_SERVICE_DEFAULT);
            }
        }

        if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
            TvIsdbChannelManager.getInstance().genMixProgList(false);
            // FIXME: use constant instead of enum
            mChannelMax = TvChannelManager.getInstance().getProgramCtrl(TvChannelManager.ATV_PROG_CTRL_GET_MAX_CHANNEL, 0, 0);
            mChannelMin = TvChannelManager.getInstance().getProgramCtrl(TvChannelManager.ATV_PROG_CTRL_GET_MIN_CHANNEL, 0, 0);
            Log.d(TAG, "mChannelMax: " + mChannelMax);
            Log.d(TAG, "mChannelMin: " + mChannelMin);
        }

        updateAtvManualtuningComponents();

        TvManager.getInstance().getMhlManager().setOnMhlEventListener(new OnMhlEventListener() {

            @Override
            public boolean onKeyInfo(int arg0, int arg1, int arg2) {
                Log.d("ATVManualTuning", "onKeyInfo");
                return false;
            }

            @Override
            public boolean onAutoSwitch(int arg0, int arg1, int arg2) {
                Log.d("ATVManualTuning", "onAutoSwitch");
                if (mTvChannelManager.getTuningStatus() != TvChannelManager.TUNING_STATUS_NONE) {
                    mTvChannelManager.stopAtvManualTuning();
                    isSearchByUser = false;
                }
                finish();

                return false;
            }
        });

        mAtvUiEventHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateAtvTuningScanInfo((AtvEventScan)msg.obj);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
	 iIdleDigitCount = 0;
        mAtvPlayerEventListener = new AtvPlayerEventListener();
        TvChannelManager.getInstance().registerOnAtvPlayerEventListener(mAtvPlayerEventListener);
        LittleDownTimer.resumeMenu();
    	LittleDownTimer.resumeItem();
    }
//add by wxy
    @Override
    public void onUserInteraction() {
        LittleDownTimer.resetMenu();
        LittleDownTimer.resetItem();
        super.onUserInteraction();
    }
//add end
    public boolean MapKeyPadToIR(int keyCode, KeyEvent event) {
        String deviceName = InputDevice.getDevice(event.getDeviceId()).getName();
        Log.d(TAG, "deviceName" + deviceName);
        if (!deviceName.equals("MStar Smart TV Keypad"))
            return false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_CHANNEL_UP:
                keyInjection(KeyEvent.KEYCODE_DPAD_UP);
                return true;
            case KeyEvent.KEYCODE_CHANNEL_DOWN:
                keyInjection(KeyEvent.KEYCODE_DPAD_DOWN);
                return true;
            default:
                return false;
        }

    }

    private void keyInjection(final int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP
                || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            new Thread() {
                public void run() {
                    try {
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(keyCode);
                    } catch (Exception e) {
                        Log.e("Exception when sendPointerSync", e.toString());
                    }
                }
            }.start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // If MapKeyPadToIR returns true,the previous keycode has been changed
        // to responding
        // android d_pad keys,just return.
        if (MapKeyPadToIR(keyCode, event))
            return true;
        Intent intent = new Intent();
        int currentid = getCurrentFocus().getId();
        int maxv = mColorSystem.length;
        int maxs = mSoundSystem.length;
        int nCurrentFrequency = mTvChannelManager.getAtvCurrentFrequency();
        int curChannelNumber = mTvChannelManager.getCurrentChannelNumber();
        ProgramInfo mProInfo = mTvChannelManager.getCurrentProgramInfo();
	  Log.d(TAG,"----Current channel:" + curChannelNumber + " with frequency:" + nCurrentFrequency);

	  ///lxk add for manul input number to verify channel number and frequency directly 20141014 start
	  if(!((keyCode >= KeyEvent.KEYCODE_0)&&(keyCode <= KeyEvent.KEYCODE_9)))
	  {
		Log.d(TAG,"---------not number reset iIdleDigitCount ---");
		iIdleDigitCount = 0;
	  }
	  ///lxk add for manul input number to verify channel number and frequency directly 20141014 end
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (currentid) {
                    case R.id.linearlayout_cha_atvmanualtuning_frequency:
                        mTvChannelManager.startAtvManualTuning(3 * 1000, nCurrentFrequency,
                                TvChannelManager.ATV_MANUAL_TUNE_MODE_FINE_TUNE_UP);
                        updateAtvManualtuningfreq();
                        //mTvChannelManager.saveAtvProgram(curChannelNumber);
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_starttuning:
                        if (mTvChannelManager.getTuningStatus() == TvChannelManager.TUNING_STATUS_ATV_MANUAL_TUNING_LEFT) {
                            mTvChannelManager.stopAtvManualTuning();
                        }
 	                  mTvChannelManager.saveAtvProgram(mProInfo.number);
                        mTvChannelManager.startAtvManualTuning(5 * 1000, nCurrentFrequency,
                                TvChannelManager.ATV_MANUAL_TUNE_MODE_SEARCH_UP);
                        isSearchByUser = true;
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_channelnum:
				 ///lxk-20141012 verifying for ATV manualtuning OSD operation start
                            /*if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                                setIsdbAtvChannel(curChannelNumber + 1);
                            } else {
                                int pc = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV);
                                if (pc > 0) {
                                    mTvChannelManager.programUp();
                                }
                            }*/
                            mTvChannelManager.programUp();///lxk-20141012 verifying for ATV manualtuning OSD operation end
                            updateAtvManualtuningComponents();
                            break;
                    case R.id.linearlayout_cha_atvmanualtuning_colorsystem:
                        mColorSystemIndex = (mColorSystemIndex+1 ) % (maxv);
                        //zb20141013 modify
                        if(mColorSystemIndex==3)
                        	mTvChannelManager.setAtvVideoStandard(8);
                        else {
                        	mTvChannelManager.setAtvVideoStandard(mColorSystemIndex);
						}
                      //end
 	                  //mTvChannelManager.saveAtvProgram(mProInfo.number);
                        viewholder_atvmanualtuning.text_cha_atvmanualtuning_colorsystem_val
                                .setText(mColorSystem[mColorSystemIndex]);
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_soundsystem:
                        mSoundSystemIndex = (mSoundSystemIndex + 1) % (maxs);
                        mTvChannelManager.setAtvAudioStandard(mSoundSystemIndex);
                        viewholder_atvmanualtuning.text_cha_atvmanualtuning_soundsystem_val
                                .setText(mSoundSystem[mSoundSystemIndex]);
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (currentid) {
                    case R.id.linearlayout_cha_atvmanualtuning_frequency:
                        mTvChannelManager.startAtvManualTuning(3 * 1000, nCurrentFrequency,
                                TvChannelManager.ATV_MANUAL_TUNE_MODE_FINE_TUNE_DOWN);
                        updateAtvManualtuningfreq();
                        //mTvChannelManager.saveAtvProgram(curChannelNumber);
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_starttuning:
                        if (mTvChannelManager.getTuningStatus() == TvChannelManager.TUNING_STATUS_ATV_MANUAL_TUNING_RIGHT) {
                            mTvChannelManager.stopAtvManualTuning();
                        }
 	                  mTvChannelManager.saveAtvProgram(mProInfo.number);
                        mTvChannelManager.startAtvManualTuning(5 * 1000, nCurrentFrequency,
                                TvChannelManager.ATV_MANUAL_TUNE_MODE_SEARCH_DOWN);
                        isSearchByUser = true;
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_channelnum:
			    ///lxk-20141012 verifying for ATV manualtuning OSD operation start
			    /*if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
                            setIsdbAtvChannel(curChannelNumber - 1);
                        } else {
                            int pc = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV);
                            if (pc > 0) {
                                mTvChannelManager.programDown();
                            }
                        }*/
                        mTvChannelManager.programDown();///lxk-20141012 verifying for ATV manualtuning OSD operation end
                        updateAtvManualtuningComponents();
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_colorsystem:
                        mColorSystemIndex = (mColorSystemIndex + maxv - 1) % (maxv);
                      //zb20141013 modify
                        if(mColorSystemIndex==3)
                        	mTvChannelManager.setAtvVideoStandard(8);
                        else {
                        	mTvChannelManager.setAtvVideoStandard(mColorSystemIndex);
						}
                      //end
                        viewholder_atvmanualtuning.text_cha_atvmanualtuning_colorsystem_val
                                .setText(mColorSystem[mColorSystemIndex]);
 	                  //mTvChannelManager.saveAtvProgram(mProInfo.number);
                        break;
                    case R.id.linearlayout_cha_atvmanualtuning_soundsystem:
                        mSoundSystemIndex = (mSoundSystemIndex + maxs - 1) % (maxs);
                        mTvChannelManager.setAtvAudioStandard(mSoundSystemIndex);
                        viewholder_atvmanualtuning.text_cha_atvmanualtuning_soundsystem_val
                                .setText(mSoundSystem[mSoundSystemIndex]);
                        break;
                    default:
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
		   Log.d(TAG,"---------DPAD DOWN---------");
		   if(currentid == R.id.linearlayout_cha_atvmanualtuning_channelnum)
		   {
	  		Log.d(TAG,"----curChannelNumber:" + curChannelNumber + " mProInfo.number:" + mProInfo.number);
			 if(mProInfo != null)
 		            viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val.setText(String.valueOf(mProInfo.number + 1));
		       else
 		            viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val.setText(String.valueOf(curChannelNumber+1));
 		   }
		   if(currentid == R.id.linearlayout_cha_atvmanualtuning_frequency)
			viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val
				.setText(String.format("%.2f", nCurrentFrequency/1000.0));
                if (mTvChannelManager.getTuningStatus() != TvChannelManager.TUNING_STATUS_NONE) {
                    mTvChannelManager.stopAtvManualTuning();
                    isSearchByUser = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
		   Log.d(TAG,"---------DPAD UP---------");
		   if(currentid == R.id.linearlayout_cha_atvmanualtuning_channelnum)
		   {
	  		Log.d(TAG,"----curChannelNumber:" + curChannelNumber + " mProInfo.number:" + mProInfo.number);
			 if(mProInfo != null)
 		            viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val.setText(String.valueOf(mProInfo.number + 1));
		       else
 		            viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val.setText(String.valueOf(curChannelNumber+1));
 		   }
		   if(currentid == R.id.linearlayout_cha_atvmanualtuning_frequency)
			viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val
				.setText(String.format("%.2f", nCurrentFrequency/1000.0));
                if (mTvChannelManager.getTuningStatus() != TvChannelManager.TUNING_STATUS_NONE) {
                    mTvChannelManager.stopAtvManualTuning();
                    isSearchByUser = false;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
                if (mTvChannelManager.getTuningStatus() != TvChannelManager.TUNING_STATUS_NONE) {
                    mTvChannelManager.stopAtvManualTuning();
                }
                isSearchByUser = false;
                intent.setAction(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
                startActivity(intent);
                finish();
                break;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                if (currentid == R.id.linearlayout_cha_atvmanualtuning_channelnum) {
	              ///lxk add for manul input number to verify channel number and frequency directly 20141014 start
			 //Log.d(TAG,"---------input number:" + (keyCode - KeyEvent.KEYCODE_0));
                     //int pc = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV);
                     /*if (mTvSystem == TvCommonManager.TV_SYSTEM_ISDB) {
			if (mCurChannelNumber == -1) {
				// it's the first input number and it equals to 0, do nothing
				if (KeyEvent.KEYCODE_0 == keyCode) {
					break;
				}
				mCurChannelNumber = keyCode - KeyEvent.KEYCODE_0;
			} else if (mCurChannelNumber >= 10) {
				mCurChannelNumber = keyCode - KeyEvent.KEYCODE_0;
			} else {
				mCurChannelNumber = mCurChannelNumber * 10 + (keyCode - KeyEvent.KEYCODE_0);
			}*/
			if (iIdleDigitCount == 0)
				iIdleInputValue = 0;
			iIdleInputValue = iIdleInputValue * 10 + (keyCode - KeyEvent.KEYCODE_0);
			iIdleDigitCount++;
			if(iIdleDigitCount > 3 || (iIdleInputValue > 200))
			{
				iIdleInputValue = iIdleInputValue%10;
				iIdleDigitCount = 1;
			}
			if(iIdleInputValue <= 200 && iIdleInputValue >= 1)
			{
				mCurChannelNumber = iIdleInputValue-1;
			}
			Log.d(TAG,"---------mCurChannelNumber:" + mCurChannelNumber);
	     	///lxk add for manul input number to verify channel number and frequency directly 20141014 start
			viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val
				.setText(String.valueOf(iIdleInputValue));
                }
	          ///lxk add for manul input number to verify channel number and frequency directly 20141014 start
		   if (currentid == R.id.linearlayout_cha_atvmanualtuning_frequency){
			if (iIdleDigitCount == 0)
			       iIdleInputValue = 0;
	    	iIdleDigitCount++;
	    	if(iIdleDigitCount == 1)
				iIdleInputValue=(keyCode - KeyEvent.KEYCODE_0)*10000;
			else if(iIdleDigitCount == 2)
				iIdleInputValue = iIdleInputValue + (keyCode - KeyEvent.KEYCODE_0)*1000;
		   	else if(iIdleDigitCount == 3)
				iIdleInputValue = iIdleInputValue + (keyCode - KeyEvent.KEYCODE_0)*100;
			else if(iIdleDigitCount == 4)
				iIdleInputValue = iIdleInputValue + (keyCode - KeyEvent.KEYCODE_0)*10;
			else
				iIdleInputValue = iIdleInputValue + (keyCode - KeyEvent.KEYCODE_0);
			Log.d(TAG,"---------iIdleDigitCount:" + iIdleDigitCount + "  Input:" + iIdleInputValue);
		   	if(iIdleDigitCount >= 5){
		   	    iIdleDigitCount = 0;
			    if((iIdleInputValue >= 4400)&&(iIdleInputValue < 89900)){
				    nCurrentFrequency = iIdleInputValue*10;
	                        mTvChannelManager.startAtvManualTuning(5*1000, nCurrentFrequency,
	                                TvChannelManager.ATV_MANUAL_TUNE_MODE_FINE_TUNE_ONE_FREQ);
	                        //updateAtvManualtuningfreq();
			    }
		  	}
			viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val
				.setText(String.format("%.2f", iIdleInputValue/100.0));
			Log.d(TAG,"---channel:" + curChannelNumber + "-frequency:" + nCurrentFrequency + " Vs:" + mTvChannelManager.getAtvCurrentFrequency());
	     	///lxk add for manul input number to verify channel number and frequency directly 20141014 start
		   }
                break;
	     ///lxk add for manul input number to verify channel number and frequency directly 20141014 start
	     case KeyEvent.KEYCODE_ENTER:
                if (currentid == R.id.linearlayout_cha_atvmanualtuning_channelnum) {
			int iCurrentChannelNum = mTvChannelManager.getCurrentChannelNumber();
			//Log.d(TAG,"---------mCurChannelNumber:" + mCurChannelNumber + " vs " + iCurrentChannelNum);
			mTvChannelManager.selectProgram(mCurChannelNumber,TvChannelManager.SERVICE_TYPE_ATV);
			updateAtvManualtuningfreq();
                    //TvChannelManager.getInstance().setAtvChannel(mCurChannelNumber);///lxk-20141012 verifying for ATV manualtuning OSD operation end
                    //mTvChannelManager.saveAtvProgram(mCurChannelNumber);
			//Log.d(TAG,"---------Current Channel Number:" + mTvChannelManager.getCurrentChannelNumber());
                }
		   if((currentid == R.id.linearlayout_cha_atvmanualtuning_frequency)||
		   	(currentid == R.id.linearlayout_cha_atvmanualtuning_colorsystem)||
		   	(currentid == R.id.linearlayout_cha_atvmanualtuning_soundsystem)){
			CharSequence ChannelNum = viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val.getText();
			int iChNumber = Integer.parseInt(ChannelNum.toString());
			//Log.d(TAG,"----save-----Channel Number:" + mTvChannelManager.getCurrentChannelNumber());
			//Log.d(TAG,"----text value:" + ChannelNum);
			//Log.d(TAG,"----Display Channel Number:" + ChannelNum);
                    mTvChannelManager.saveAtvProgram(iChNumber-1);
		   }
		   ///lxk add for manul input number to verify channel number and frequency directly 20141015 end
		   break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * private void registerListeners() { } private OnClickListener listener =
     * new OnClickListener() { Intent intent = new Intent();
     * @Override public void onClick(View view) { // TODO Auto-generated method
     * stub switch (view.getId()) { } } };
     */
    private void updateAtvManualtuningfreq() {
        String str_val;
        int freqKhz = mTvChannelManager.getAtvCurrentFrequency();
        str_val = String.format("%.2f", freqKhz/1000.0);
        viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val.setText(str_val);
    }

    private void updateAtvManualtuningComponents() {
        String str_val;
        int curChannelNum = mTvChannelManager.getCurrentChannelNumber() + 1;
        ProgramInfo mProInfo = mTvChannelManager.getCurrentProgramInfo();

        if (TvCommonManager.TV_SYSTEM_ISDB == mTvSystem) {
             ((LinearLayout)findViewById(R.id.linearlayout_cha_atvmanualtuning_soundsystem)).setVisibility(LinearLayout.GONE);
        }

        // 0.250M
        Log.i(TAG,"--------mProInfo.number:" + mProInfo.number + " curChannelNum:" + curChannelNum);
	 if(mProInfo != null)
	 	str_val =  Integer.toString(mProInfo.number + 1);
	else
        str_val = Integer.toString(curChannelNum);
        //zb20141013 modify
        if(mTvChannelManager.getAvdVideoStandard()<3)
	        mColorSystemIndex = mTvChannelManager.getAvdVideoStandard()% (mColorSystem.length);
        else {
        	mColorSystemIndex =3;
		}
        mSoundSystemIndex = mTvChannelManager.getAtvAudioStandard()
                % mSoundSystem.length;
        viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val.setText(str_val);
        viewholder_atvmanualtuning.text_cha_atvmanualtuning_colorsystem_val
                .setText(mColorSystem[mColorSystemIndex]);
        viewholder_atvmanualtuning.text_cha_atvmanualtuning_soundsystem_val
                .setText(mSoundSystem[mSoundSystemIndex]);
        updateAtvManualtuningfreq();
    }

    @Override
    protected void onPause() {
        TvChannelManager.getInstance().unregisterOnAtvPlayerEventListener(mAtvPlayerEventListener);
        mAtvPlayerEventListener = null;

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mDirectChangeChTimeout.removeCallbacks(mDirectChangeChTimeoutRunnable);
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void updateAtvTuningScanInfo(AtvEventScan extra) {
        String str_val;
        int frequency = extra.frequencyKHz;
        int percent = extra.percent;
        int minteger = frequency / 1000;
        int mfraction = (frequency % 1000) / 10;
        str_val = Integer.toString(minteger) + "." + Integer.toString(mfraction);
        viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val.setText(str_val);

        if (percent >= 100) {
            mTvChannelManager.stopAtvManualTuning();

            int atvProgCount = mTvChannelManager.getProgramCount(TvChannelManager.PROGRAM_COUNT_ATV);
            int curProgNumber = mTvChannelManager.getCurrentChannelNumber();
            if ((atvProgCount == 0) || (curProgNumber == 0xFF)) {
                curProgNumber = 0;
            }

            Log.i("ATVManualTuning", "count:" + atvProgCount + "---current:"
                    + curProgNumber);

            mTvChannelManager.saveAtvProgram(curProgNumber);
            int curChannelNumber = mTvChannelManager.getCurrentChannelNumber();
            mTvChannelManager.selectProgram(curChannelNumber, TvChannelManager.SERVICE_TYPE_ATV);
            updateAtvManualtuningComponents();

            // finish atv manual tuning activity when search is
            // finished.
           
            new Handler().postDelayed(new Runnable() {
            	

                @Override
                public void run() {
                	isSearchByUser = false;   //add by wxy
                    ATVManualTuning.this.finish();
                }
            }, 3000);
        }
    }

    private void setIsdbAtvChannel(int channelNumber) {
        if (channelNumber > mChannelMax) {
            channelNumber = mChannelMin;
        } else if (channelNumber < mChannelMin) {
            channelNumber = mChannelMax;
        }
        TvIsdbChannelManager.getInstance().setChannel(channelNumber, true);
    }
}
