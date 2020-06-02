package com.rt.apps.rrassociates;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
public class SplashActivity extends Activity {


    //Private declarations
    private InternalHandler mHandler;
    private long mStartTime;


    //Constants
    private static final int GO_AHEAD = 1;
    private static final long MAX_TIME = 1500L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mStartTime = SystemClock.uptimeMillis();
        mHandler = new InternalHandler(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        final Message goAHeadMessage = mHandler.obtainMessage(GO_AHEAD);
        mHandler.sendMessageAtTime(goAHeadMessage, mStartTime + MAX_TIME);

    }

    private void endSplash() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();

    }


    private static class InternalHandler extends Handler {

        private WeakReference<SplashActivity> mSAWeakRef;

        public InternalHandler(SplashActivity mSARef) {
            this.mSAWeakRef = new WeakReference<SplashActivity>(mSARef);
        }

        @Override
        public void handleMessage(Message msg) {
            final SplashActivity mActivity = mSAWeakRef.get();
            if (mActivity == null)
                return;
            switch (msg.what) {
                case GO_AHEAD:
                    long elapsedTime = SystemClock.uptimeMillis() - mActivity.mStartTime;
                    if (elapsedTime >= MAX_TIME) {
                        mActivity.endSplash();
                    }
                    break;
            }
        }
    }
}
