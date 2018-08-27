package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
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
        startSlide();
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


        }
    }

    private void startSlide() {

        new Timer().scheduleAtFixedRate(timerTask, 500, DELAY);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mRegistered) {

            mRegistered = false;
        }
    }


    private void pageSlide() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                int currentItem = getCurrentItem();
                PagerAdapter adapter = getAdapter();

                if (adapter != null) {
                    int count = adapter.getCount();

                    setCurrentItem(currentItem == count -1 ? 0 : ++currentItem, true);
                }

            }
        });
    }


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            pageSlide();
        }
    };

}
