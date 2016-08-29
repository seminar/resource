//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2013 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tvframework;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceView;

public abstract class BaseUIActivity extends Activity {
    private static final String TAG = "BaseUIActivity";
    protected String blendFile = new String();
    protected String configFile = new String();
    protected Handler handler = null;
    protected EGL10 egl = null;
    protected GL11 gl = null;
    protected boolean ranInit = false;
    protected boolean paused = false;
    protected EGLSurface eglSurface = null;
    protected EGLDisplay eglDisplay = null;
    protected EGLContext eglContext = null;
    protected EGLConfig eglConfig = null;
    protected Runnable initRunnable = null;
    protected Runnable painter = null;
    protected int surfaceWidth = 0;
    protected int surfaceHeight = 0;
    protected SurfaceView surfaceView = null;
    protected GLsurfaceViewActivity glView = null;
    protected long abstime = 0;

    protected boolean Isrender = false;
    protected boolean bOnSurfaceCreated = false;
    protected boolean _3Dflag;
    //protected static boolean isFirstCreated = true;
    protected static boolean mIsInMenuPage = false;

    // //////////////////////////////////////////////////////////////////////////
    // interface declare begin
    /**
     * Function called when the application wants to initialize the application.
     * 
     * @return True if initialization was successful.
     */
    public abstract boolean init(String blendFile, String configFile, String EtcPath);

    /**
     * Called when the application is exiting
     */
    public abstract boolean cleanup();

    // public abstract void setOffsets(int x, int y);

    /**
     * Function called on key events
     * 
     * @param action
     *            The action of this event.
     * @param unicodeChar
     *            The unicode character represented by the entered key char. If this is 0, further info will have to be
     *            extracted from the event object.
     * @param event
     *            The event object if further details are needed.
     * @return True if the event was handled.
     */
    public abstract boolean keyEvent(int action, int unicodeChar, int keyCode, KeyEvent event);

    public abstract boolean render(int drawWidth, int drawHeight, boolean forceRedraw);

    public abstract boolean axisEvent(int coordType, int btnType, int action, float x, float y, MotionEvent event);

    public abstract boolean runLuaScript(String Script);

    public abstract boolean setImage(Bitmap image, String appName, String sceneName, String objName, int subEntityID,
            int techID, int passID, int stateID);

    public abstract boolean setImage(Bitmap image, String appName, String sceneName, String objName, int subEntityID,
            int techID, int passID, int stateID, boolean formatFlag);

    public abstract String queryCurrentScene();

    public abstract String queryCurrentFocusObj(String SceneName);

    public abstract String queryCurrentFocusApp(String SceneName, String ObjName);

    public abstract int queryAnimationStatus(String SceneName);

    public abstract void registerCallBackString(String JavaClassName);

    public abstract void callBackError(int errorCode, String message);

    public abstract void callBackSceneChange(String OldSceneName, String NewSceneName);

    public abstract void callBackBlendFileChange(String OldFileName, String NewFileName);

    public abstract void callBackAxisEvent(int coordType, int btnType, int action, float x, float y);

    public abstract void callBackKeyEvent(String sceneName, String objName, int action, int unicodeChar, int keyCode);

    public abstract void callBackFocusChange(String SceneName, String OldObjName, String NewObjName);

    public abstract void callBackAnimDone(String SceneName, String OldObjName, String NewObjName);

    public abstract boolean registerAnimToListen(String SceneName, String OldObjName, String NewObjName);

    public abstract boolean unRegisterListenAnim(String SceneName, String OldObjName, String NewObjName);

    public abstract void createDynamicTexture(int width, int height, int num_mips, String pixelFormat, String name,
            String group, int texType, int usage, boolean hwGammaCorrection, int fsaa, int fsaaHint);

    public abstract void delDynamicTexture();

    public abstract void enableUpdateDynamicTex(boolean bFlag);

    public abstract boolean replacewithDynamicTex(String appName, String sceneName, String objName, int subEntityID,
            int techID, int passID, int stateID);

    public abstract void startVECapture();

    public abstract void endVECapture();

    public abstract int queryVECaptureAttr(String attribute);

    public abstract void setVECaptureAttr(String attribute, int value);

    // interface declare end
    // //////////////////////////////////////////////////////////////////////////

    // //////////////////////////////////////////////////////////////////////////
    // Activity begin
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        handler = new Handler();
        MstarUtil.getInstance().setActivity(this);
//                initMainView();
//                systemInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        paused = false;

//        handler.post(painter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!_3Dflag) {
            return;
        }

        // handle the rest of events in queue.
        Runnable eventR = null;
        while (true) {
            synchronized (BaseUIActivity.this.glView.gameEngine) {

                if (!BaseUIActivity.this.glView.mMsEventQueue.isEmpty()) {
                    eventR = BaseUIActivity.this.glView.mMsEventQueue.remove(0);
                }
            }
            if (eventR == null)
                break;

            eventR.run();
            eventR = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // handler.removeCallbacks(painter);
        // paused = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!_3Dflag || !mIsInMenuPage) {
            return super.onTouchEvent(event);
        }
        boolean ret = super.onTouchEvent(event);
        if (!ret) {
            int coordType = 1;
            // LEFT_BTN
            int btnType = 0;
            ret = axisEvent(coordType, btnType, event.getAction(), event.getX(), event.getY(), event);
        }
        return ret;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!_3Dflag || !mIsInMenuPage) {
            return super.onKeyDown(keyCode, event);
        }

        boolean ret = glView.gameEngine.keyEvent(event.getAction(), event.getUnicodeChar(), event.getKeyCode(), event);

        if (!ret) {
            ret = super.onKeyDown(keyCode, event);
        }
        return ret;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!_3Dflag || !mIsInMenuPage) {
            return super.onKeyUp(keyCode, event);
        }

        boolean ret = glView.gameEngine.keyEvent(event.getAction(), event.getUnicodeChar(), event.getKeyCode(), event);
        if (!ret)
            ret = super.onKeyUp(keyCode, event);
        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Not Showing System Default Menu
        return false;
    }

    // //////////////////////////////////////////////////////////////////////////
    public class GLsurfaceViewActivity extends BaseGLSurfaceView {
//    private static String TAG = "GL2JNIView";
        private final boolean DEBUG = false;
        public GameEngine gameEngine;
        public EGL10 mEgl = null;
        public ArrayList<Runnable> mMsEventQueue = new ArrayList<Runnable>();

        public GLsurfaceViewActivity(Context context) {
            super(context);
            init(false, 0, 0);
            gameEngine = new GameEngine();
        }

        public GLsurfaceViewActivity(Context context, AttributeSet attrs) {
            super(context,attrs);
            init(false, 0, 0);
            gameEngine = new GameEngine();
        }

        // New GameEngine JNI wrapper
        public class GameEngine {
            public boolean m_bRet = false;
            public String m_strRet;
            public int m_iRet=0;

            public GameEngine() {
                m_bRet = false;
            }

            public boolean keyEvent(int action, int unicodeChar, int keyCode, KeyEvent event) {
                final int actionL = action;
                final int unicodeCharL = unicodeChar;
                final int keyCodeL = keyCode;
                final KeyEvent eventL = event;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            // Log.v(TAG, "keyEvent unicodeChar = " + unicodeCharL + ", keyCode = " + keyCodeL + "!! ");
                            m_bRet = BaseUIActivity.this.keyEvent(actionL, unicodeCharL, keyCodeL, eventL);
                        }
                    });
                }
                return m_bRet;
            }

            public boolean render(int drawWidth, int drawHeight, boolean forceRedraw) {
                final int drawWidthL = drawWidth;
                final int drawHeightL = drawHeight;
                final boolean forceRedrawL = forceRedraw;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_bRet = BaseUIActivity.this.render(drawWidthL, drawHeightL, forceRedrawL);
                        }
                    });
                }
                return m_bRet;
            }

            public boolean axisEvent(int coordType, int btnType, int action, float x, float y, MotionEvent event) {
                final int coordTypeL = coordType;
                final int btnTypeL = btnType;
                final int actionL = action;
                final float xL = x;
                final float yL = y;
                final MotionEvent eventL = event;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_bRet = BaseUIActivity.this.axisEvent(coordTypeL, btnTypeL, actionL, xL, yL, eventL);
                        }
                    });
                }
                return m_bRet;
            }

            public boolean runLuaScript(String Script) {
                final String ScriptL = Script;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_bRet = BaseUIActivity.this.runLuaScript(ScriptL);
                        }
                    });
                }
                return m_bRet;
            }

            public boolean setImage(Bitmap image, String appName, String sceneName, String objName, int subEntityID,
                    int techID, int passID, int stateID) {
                final Bitmap imageL = image;
                final String appNameL = appName;
                final String sceneNameL = sceneName;
                final String objNameL = objName;
                final int subEntityIDL = subEntityID;
                final int techIDL = techID;
                final int passIDL = passID;
                final int stateIDL = stateID;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            // if( GLsurfaceViewActivity.this.mEgl.eglGetCurrentContext() != EGL10.EGL_NO_CONTEXT
                            // ) {
                            m_bRet = BaseUIActivity.this.setImage(imageL, appNameL, sceneNameL, objNameL, subEntityIDL,
                                    techIDL, passIDL, stateIDL);
                            // }
                            // else {
                            // GLsurfaceViewActivity.this.queueEvent(this);
                            // }
                        }
                    });
                }
                return m_bRet;
            }

            public boolean setImage(Bitmap image, String appName, String sceneName, String objName, int subEntityID,
                    int techID, int passID, int stateID, boolean formatFlag) {
                final Bitmap imageL = image;
                final String appNameL = appName;
                final String sceneNameL = sceneName;
                final String objNameL = objName;
                final int subEntityIDL = subEntityID;
                final int techIDL = techID;
                final int passIDL = passID;
                final int stateIDL = stateID;
                final boolean formatFlagL = formatFlag;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            // if( GLsurfaceViewActivity.this.mEgl.eglGetCurrentContext() != EGL10.EGL_NO_CONTEXT
                            // ) {
                            m_bRet = BaseUIActivity.this.setImage(imageL, appNameL, sceneNameL, objNameL, subEntityIDL,
                                    techIDL, passIDL, stateIDL, formatFlagL);
                            // }
                            // else {
                            // GLsurfaceViewActivity.this.queueEvent(this);
                            // }
                        }
                    });
                }
                return m_bRet;
            }

            public String queryCurrentScene() {
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_strRet = BaseUIActivity.this.queryCurrentScene();
                        }
                    });
                }
                return m_strRet;
            }

            public String queryCurrentFocusObj(String SceneName) {
                final String SceneNameL = SceneName;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_strRet = BaseUIActivity.this.queryCurrentFocusObj(SceneNameL);
                        }
                    });
                }
                return m_strRet;
            }

            public String queryCurrentFocusApp(String SceneName, String ObjName) {
                final String SceneNameL = SceneName;
                final String ObjNameL = ObjName;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_strRet = BaseUIActivity.this.queryCurrentFocusApp(SceneNameL, ObjNameL);
                        }
                    });
                }
                return m_strRet;
            }

            public int queryAnimationStatus(String SceneName) {
                final String SceneNameL = SceneName;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_iRet = BaseUIActivity.this.queryAnimationStatus(SceneNameL);
                        }
                    });
                }
                return m_iRet;
            }

            /*
             * public void registerCallBackString(String JavaClassName);
             * 
             * public void callBackError(int errorCode, String message);
             * 
             * public void callBackSceneChange(String OldSceneName, String NewSceneName);
             * 
             * public void callBackBlendFileChange(String OldFileName, String NewFileName);
             * 
             * public void callBackAxisEvent(int coordType, int btnType, int action, float x, float y);
             * 
             * public void callBackKeyEvent(String sceneName, String objName, int action, int unicodeChar, int keyCode);
             * 
             * public void callBackFocusChange(String SceneName, String OldObjName, String NewObjName);
             * 
             * public void callBackAnimDone(String SceneName, String OldObjName, String NewObjName);
             */
            public boolean registerAnimToListen(String SceneName, String OldObjName, String NewObjName) {
                final String SceneNameL = SceneName;
                final String OldObjNameL = OldObjName;
                final String NewObjNameL = NewObjName;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_bRet = BaseUIActivity.this.registerAnimToListen(SceneNameL, OldObjNameL, NewObjNameL);
                        }
                    });
                }
                return m_bRet;
            }

            public boolean unRegisterListenAnim(String SceneName, String OldObjName, String NewObjName) {
                final String SceneNameL = SceneName;
                final String OldObjNameL = OldObjName;
                final String NewObjNameL = NewObjName;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_bRet = BaseUIActivity.this.unRegisterListenAnim(SceneNameL, OldObjNameL, NewObjNameL);
                        }
                    });
                }
                return m_bRet;
            }

            public void createDynamicTexture(int width, int height, int num_mips, String pixelFormat, String name,
                    String group, int texType, int usage, boolean hwGammaCorrection, int fsaa, int fsaaHint) {
                final int widthL = width;
                final int heightL = height;
                final int num_mipsL = num_mips;
                final String pixelFormatL = pixelFormat;
                final String nameL = name;
                final String groupL = group;
                final int texTypeL = texType;
                final int usageL = usage;
                final boolean hwGammaCorrectionL = hwGammaCorrection;
                final int fsaaL = fsaa;
                final int fsaaHintL = fsaaHint;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            BaseUIActivity.this.createDynamicTexture(widthL, heightL, num_mipsL, pixelFormatL, nameL,
                                    groupL, texTypeL, usageL, hwGammaCorrectionL, fsaaL, fsaaHintL);
                        }
                    });
                }
            }

            public void delDynamicTexture() {
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            BaseUIActivity.this.delDynamicTexture();
                        }
                    });
                }
            }

            public void enableUpdateDynamicTex(boolean bFlag) {
                final boolean bFlagL = bFlag;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            BaseUIActivity.this.enableUpdateDynamicTex(bFlagL);
                        }
                    });
                }
            }

            public boolean replacewithDynamicTex(String appName, String sceneName, String objName, int subEntityID,
                    int techID, int passID, int stateID) {
                final String appNameL = appName;
                final String sceneNameL = sceneName;
                final String objNameL = objName;
                final int subEntityIDL = subEntityID;
                final int techIDL = techID;
                final int passIDL = passID;
                final int stateIDL = stateID;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_bRet = BaseUIActivity.this.replacewithDynamicTex(appNameL, sceneNameL, objNameL,
                                    subEntityIDL, techIDL, passIDL, stateIDL);
                        }
                    });
                }
                return m_bRet;
            }

            public void startVECapture() {
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            BaseUIActivity.this.startVECapture();
                        }
                    });
                }
            }

            public void endVECapture() {
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            BaseUIActivity.this.endVECapture();
                        }
                    });
                }
            }

            public int queryVECaptureAttr(String attribute) {
                final String attributeL = attribute;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            m_iRet = BaseUIActivity.this.queryVECaptureAttr(attributeL);
                        }
                    });
                }
                return m_iRet;
            }

            public void setVECaptureAttr(String attribute, int value) {
                final String attributeL = attribute;
                final int valueL = value;
                synchronized (this) {
                    mMsEventQueue.add(new Runnable() {
                        public void run() {
                            BaseUIActivity.this.setVECaptureAttr(attributeL, valueL);
                        }
                    });
                }
            }
        }

        // //////////////////////////////////////////////////////////////////////////
        // ////////////////////////////////////////////////////////////////////////////
        // interface declare begin

        public GLsurfaceViewActivity(Context context, boolean translucent, int depth, int stencil) {
            super(context);
            init(translucent, depth, stencil);
        }

        public void setOgImage(Bitmap image, String appName, String sceneName, String objName, int subEntityID,
                int techID, int passID, int stateID, boolean formatFlag) {
            // Log.v(TAG, String.format(" BASEUI setOgImage  ~~~~~~~~~~~\n"));
        }

        public void setContext(Context context) {
            init(false, 0, 0);
        }

        private void init(boolean translucent, int depth, int stencil) {

            /*
             * By default, GLSurfaceView() creates a RGB_565 opaque surface. If we want a translucent one, we should
             * change the surface's format here, using PixelFormat.TRANSLUCENT for GL Surfaces is interpreted as any
             * 32-bit surface with alpha by SurfaceFlinger.
             */
            Log.v(TAG, String.format(" GLSurfaceView init ~~~~~~~~~~~\n"));
            if (translucent) {
                this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            }

            setPreserveEGLContextOnPause(true);

            /*
             * Setup the context factory for 2.0 rendering. See ContextFactory class definition below
             */
            setEGLContextFactory(new ContextFactory());

            /*
             * We need to choose an EGLConfig that matches the format of our surface exactly. This is going to be done
             * in our custom config chooser. See ConfigChooser class definition below.
             */
            setEGLConfigChooser(new ConfigChooser(8, 8, 8, 8, 16, 0));

            /* Set the renderer responsible for frame rendering */
            setRenderer(new Renderer());
        }

        private class ContextFactory implements BaseGLSurfaceView.EGLContextFactory {
            private int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

            public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
                Log.w(TAG, ">>>>>>>>creating OpenGL ES 2.0 context<<<<<<<<<");
                checkEglError("Before eglCreateContext", egl);
                int[] attrib_list = { EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
                EGLContext context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
                checkEglError("After eglCreateContext", egl);
                mEgl = egl;
                return context;
            }

            public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
                Log.w(TAG, ">>>>>>>>>>>>>>destroyContext OpenGL ES 2.0 context<<<<<<<<<<<");
                BaseUIActivity.this.cleanup();
                egl.eglDestroyContext(display, context);

            }
        }

        private void checkEglError(String prompt, EGL10 egl) {
            int error;
            while ((error = egl.eglGetError()) != EGL10.EGL_SUCCESS) {
                Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, error));
            }
        }

        private class ConfigChooser implements BaseGLSurfaceView.EGLConfigChooser {

            public ConfigChooser(int r, int g, int b, int a, int depth, int stencil) {
                mRedSize = r;
                mGreenSize = g;
                mBlueSize = b;
                mAlphaSize = a;
                mDepthSize = depth;
                mStencilSize = stencil;
            }

            /*
             * This EGL config specification is used to specify 2.0 rendering. We use a minimum size of 4 bits for
             * red/green/blue, but will perform actual matching in chooseConfig() below.
             */
            private int EGL_OPENGL_ES2_BIT = 4;
            private int[] s_configAttribs2 = { EGL10.EGL_RED_SIZE, 4, EGL10.EGL_GREEN_SIZE, 4, EGL10.EGL_BLUE_SIZE, 4,
                    EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL10.EGL_SAMPLE_BUFFERS, 1, EGL10.EGL_SAMPLES, 4,
                    EGL10.EGL_NONE };

            public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {

                /*
                 * Get the number of minimally matching EGL configurations
                 */
                int[] num_config = new int[1];
                egl.eglChooseConfig(display, s_configAttribs2, null, 0, num_config);

                int numConfigs = num_config[0];

                if (numConfigs <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }

                /*
                 * Allocate then read the array of minimally matching EGL configs
                 */
                EGLConfig[] configs = new EGLConfig[numConfigs];
                egl.eglChooseConfig(display, s_configAttribs2, configs, numConfigs, num_config);

                if (DEBUG) {
                    printConfigs(egl, display, configs);
                }
                /*
                 * Now return the "best" one
                 */
                return chooseConfig(egl, display, configs);
            }

            public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
                for (EGLConfig config : configs) {
                    int d = findConfigAttrib(egl, display, config, EGL10.EGL_DEPTH_SIZE, 0);
                    int s = findConfigAttrib(egl, display, config, EGL10.EGL_STENCIL_SIZE, 0);

                    // We need at least mDepthSize and mStencilSize bits
                    if (d < mDepthSize || s < mStencilSize)
                        continue;

                    // We want an *exact* match for red/green/blue/alpha
                    int r = findConfigAttrib(egl, display, config, EGL10.EGL_RED_SIZE, 0);
                    int g = findConfigAttrib(egl, display, config, EGL10.EGL_GREEN_SIZE, 0);
                    int b = findConfigAttrib(egl, display, config, EGL10.EGL_BLUE_SIZE, 0);
                    int a = findConfigAttrib(egl, display, config, EGL10.EGL_ALPHA_SIZE, 0);

                    if (r == mRedSize && g == mGreenSize && b == mBlueSize && a == mAlphaSize)
                        return config;
                }
                return null;
            }

            private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute,
                    int defaultValue) {

                if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
                    return mValue[0];
                }
                return defaultValue;
            }

            private void printConfigs(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
                int numConfigs = configs.length;
                Log.w(TAG, String.format("%d configurations", numConfigs));
                for (int i = 0; i < numConfigs; i++) {
                    Log.w(TAG, String.format("Configuration %d:\n", i));
                    printConfig(egl, display, configs[i]);
                }
            }

            private void printConfig(EGL10 egl, EGLDisplay display, EGLConfig config) {
                int[] attributes = { EGL10.EGL_BUFFER_SIZE, EGL10.EGL_ALPHA_SIZE, EGL10.EGL_BLUE_SIZE,
                        EGL10.EGL_GREEN_SIZE, EGL10.EGL_RED_SIZE, EGL10.EGL_DEPTH_SIZE, EGL10.EGL_STENCIL_SIZE,
                        EGL10.EGL_CONFIG_CAVEAT, EGL10.EGL_CONFIG_ID, EGL10.EGL_LEVEL, EGL10.EGL_MAX_PBUFFER_HEIGHT,
                        EGL10.EGL_MAX_PBUFFER_PIXELS, EGL10.EGL_MAX_PBUFFER_WIDTH,
                        EGL10.EGL_NATIVE_RENDERABLE,
                        EGL10.EGL_NATIVE_VISUAL_ID,
                        EGL10.EGL_NATIVE_VISUAL_TYPE,
                        0x3030, // EGL10.EGL_PRESERVED_RESOURCES,
                        EGL10.EGL_SAMPLES, EGL10.EGL_SAMPLE_BUFFERS, EGL10.EGL_SURFACE_TYPE,
                        EGL10.EGL_TRANSPARENT_TYPE, EGL10.EGL_TRANSPARENT_RED_VALUE, EGL10.EGL_TRANSPARENT_GREEN_VALUE,
                        EGL10.EGL_TRANSPARENT_BLUE_VALUE,
                        0x3039, // EGL10.EGL_BIND_TO_TEXTURE_RGB,
                        0x303A, // EGL10.EGL_BIND_TO_TEXTURE_RGBA,
                        0x303B, // EGL10.EGL_MIN_SWAP_INTERVAL,
                        0x303C, // EGL10.EGL_MAX_SWAP_INTERVAL,
                        EGL10.EGL_LUMINANCE_SIZE, EGL10.EGL_ALPHA_MASK_SIZE, EGL10.EGL_COLOR_BUFFER_TYPE,
                        EGL10.EGL_RENDERABLE_TYPE, 0x3042 // EGL10.EGL_CONFORMANT
                };
                String[] names = { "EGL_BUFFER_SIZE", "EGL_ALPHA_SIZE", "EGL_BLUE_SIZE", "EGL_GREEN_SIZE",
                        "EGL_RED_SIZE", "EGL_DEPTH_SIZE", "EGL_STENCIL_SIZE", "EGL_CONFIG_CAVEAT", "EGL_CONFIG_ID",
                        "EGL_LEVEL", "EGL_MAX_PBUFFER_HEIGHT", "EGL_MAX_PBUFFER_PIXELS", "EGL_MAX_PBUFFER_WIDTH",
                        "EGL_NATIVE_RENDERABLE", "EGL_NATIVE_VISUAL_ID", "EGL_NATIVE_VISUAL_TYPE",
                        "EGL_PRESERVED_RESOURCES", "EGL_SAMPLES", "EGL_SAMPLE_BUFFERS", "EGL_SURFACE_TYPE",
                        "EGL_TRANSPARENT_TYPE", "EGL_TRANSPARENT_RED_VALUE", "EGL_TRANSPARENT_GREEN_VALUE",
                        "EGL_TRANSPARENT_BLUE_VALUE", "EGL_BIND_TO_TEXTURE_RGB", "EGL_BIND_TO_TEXTURE_RGBA",
                        "EGL_MIN_SWAP_INTERVAL", "EGL_MAX_SWAP_INTERVAL", "EGL_LUMINANCE_SIZE", "EGL_ALPHA_MASK_SIZE",
                        "EGL_COLOR_BUFFER_TYPE", "EGL_RENDERABLE_TYPE", "EGL_CONFORMANT" };
                int[] value = new int[1];
                for (int i = 0; i < attributes.length; i++) {
                    int attribute = attributes[i];
                    String name = names[i];
                    if (egl.eglGetConfigAttrib(display, config, attribute, value)) {
                        Log.w(TAG, String.format("  %s: %d\n", name, value[0]));
                    }
                    else {
                        // Log.w(TAG, String.format("  %s: failed\n", name));
                        while (egl.eglGetError() != EGL10.EGL_SUCCESS)
                            ;
                    }
                }
            }

            // Subclasses can adjust these values:
            protected int mRedSize;
            protected int mGreenSize;
            protected int mBlueSize;
            protected int mAlphaSize;
            protected int mDepthSize;
            protected int mStencilSize;
            private int[] mValue = new int[1];
        }

        private class Renderer implements BaseGLSurfaceView.Renderer {

            public void onDrawFrame(GL10 gl) {

                try {

                    Runnable eventR = null;

                    while (true) {
                        synchronized (BaseUIActivity.this.glView.gameEngine) {

                            if (!BaseUIActivity.this.glView.mMsEventQueue.isEmpty()) {
                                eventR = BaseUIActivity.this.glView.mMsEventQueue.remove(0);
                            }
                        }
                        if (eventR == null)
                            break;

                        eventR.run();
                        eventR = null;
                    }

                    BaseUIActivity.this.render(surfaceWidth, surfaceHeight, true);

                }
                finally {
                }
            }

            public void onSurfaceChanged(GL10 gl, int width, int height) {
                Log.v(TAG, String.format(" onSurfalceChanged2 %d: %d\n", width, height));
                if (BaseUIActivity.this.Isrender) {
                    surfaceWidth = width;
                    surfaceHeight = height;
                    BaseUIActivity.this.render(surfaceWidth, surfaceHeight, true);
                }
            }

            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                BaseUIActivity.this.init(blendFile, configFile, "/data/data/com.android.mlauncher/files");
                Log.w(TAG, ">>>>>>>>creating OpenGL ES 2.0 Surface<<<<<<<<<");
                BaseUIActivity.this.render(surfaceWidth, surfaceHeight, true);
                bOnSurfaceCreated = true;
                // Do nothing.
            }
        }
    }
}