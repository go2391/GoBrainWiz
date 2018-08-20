package brainwiz.gobrainwiz.utils;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.TimerTask;

public class AutoSlideViewPager extends ViewPager {
    private boolean enabled = true;
    private boolean mRegistered;
    private boolean mShouldRunTicker;
    private boolean mHasSeconds;
    private boolean mShowCurrentUserTime;
    private int DELAY = 3 * 1000;//3 milli seconds delay
    private int secCounter;

    public AutoSlideViewPager(Context context) {
        super(context);
    }

    public AutoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.enabled && super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.enabled && super.onInterceptTouchEvent(event);

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!mRegistered) {
            mRegistered = true;

            registerReceiver();

        }
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);

        if (!mShouldRunTicker && isVisible) {
            mShouldRunTicker = true;
            if (mHasSeconds) {
                mTicker.run();
            } else {
                onTimeChanged();
            }
        } else if (mShouldRunTicker && !isVisible) {
            mShouldRunTicker = false;
            getHandler().removeCallbacks(mTicker);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mRegistered) {
            unregisterReceiver();

            mRegistered = false;
        }
    }

    private void registerReceiver() {
        final IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
//        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

        // OK, this is gross but needed. This class is supported by the
        // remote views mechanism and as a part of that the remote views
        // can be inflated by a context for another user without the app
        // having interact users permission - just for loading resources.
        // For example, when adding widgets from a managed profile to the
        // home screen. Therefore, we register the receiver as the user
        // the app is running as not the one the context is for.
//        getContext().registerReceiverAsUser(mIntentReceiver, android.os.Process.myUserHandle(),
//                filter, null, getHandler());
    }


    private void unregisterReceiver() {
        getContext().unregisterReceiver(mIntentReceiver);
    }


    /**
     * Update the displayed time if this view and its ancestors and window is visible
     */
    private void onTimeChanged() {
        // mShouldRunTicker always equals the last value passed into onVisibilityAggregated
        secCounter += 1000;
        if (mShouldRunTicker && secCounter % DELAY == 0) {
            secCounter = 0;
            int count = getAdapter().getCount();

            pageSlide(getCurrentItem() == count ? 0 : getCurrentItem() + 1);
        }
    }

    private void pageSlide(final int item) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(item, true);
            }
        });
    }

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onTimeChanged();
        }
    };

    private final Runnable mTicker = new Runnable() {
        public void run() {
            onTimeChanged();

            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            getHandler().postAtTime(mTicker, next);
        }
    };

}
