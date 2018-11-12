package brainwiz.gobrainwiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import brainwiz.gobrainwiz.utils.SharedPrefUtils;

public class LandingActivity extends BaseActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_landing_pages);
        initViews();

    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.landing_viewpager);
        int[] images = new int[]{R.drawable.landingpage01, R.drawable.landingpage02, R.drawable.landingpage03,R.drawable.landingpage04};
        viewPager.setAdapter(new SlidingPagerAdapter(this, images));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.image_indicator);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.skip).setOnClickListener(onClickListener);
        findViewById(R.id.next).setOnClickListener(onClickListener);

        if (!SharedPrefUtils.getIsFirstLaunch(this)) {
            if (SharedPrefUtils.getIsLogin(this)) {
                startActivity(new Intent(LandingActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
            }
            finish();
        }
    }


    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.skip:
                    startActivity(new Intent(LandingActivity.this, RegistrationActivity.class));
                    SharedPrefUtils.putData(getApplicationContext(), SharedPrefUtils.IS_FIRST_LAUNCH, false);
                    finish();
                    break;
                case R.id.next:
                    int count = viewPager.getAdapter().getCount();

                    if (viewPager.getCurrentItem() == count - 1) {
                        startActivity(new Intent(LandingActivity.this, RegistrationActivity.class));
                        SharedPrefUtils.putData(getApplicationContext(), SharedPrefUtils.IS_FIRST_LAUNCH, false);
                        finish();
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    }
                    break;
            }
        }
    };
}
