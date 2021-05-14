package com.vbuecker.chucknorrisio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashcreenActivity extends AppCompatActivity {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    private View mContentView;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            delayedShow(1500);
        }
    };
        private final Runnable mShowPart2Runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashcreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        private final Runnable mShowRunnable = new Runnable() {
            @Override
            public void run() {
                show();
            }
        };

        private final Runnable mHideRunnable = new Runnable() {
            @Override
            public void run() {
                hide();
            }
        };

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splashcreen);
            mContentView = findViewById(R.id.fullscreen_content);
        }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void hide(){
            mHideHandler.removeCallbacks(mShowPart2Runnable);
            mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }
    @SuppressLint("InlineApi")
    private void show(){
            //Show system bar
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

            mHideHandler.removeCallbacks(mHidePart2Runnable);
            mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }
    private void delayedHide (int delayMillis){
            mHideHandler.removeCallbacks(mHideRunnable);
            mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    private void delayedShow (int delayMillis){
            mHideHandler.removeCallbacks(mShowRunnable);
            mHideHandler.postDelayed(mShowRunnable, delayMillis);
    }
}