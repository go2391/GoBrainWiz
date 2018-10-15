package brainwiz.gobrainwiz.sidemenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.utils.SlidingTabLayout;

public class TestHistoryFragment extends BaseFragment {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_test_history, container, false);
        init(inflate);

        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void init(View inflate) {

        /*fragmentNotificationBinding.vpNotifications.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fragmentNotificationBinding.rbPromotions.setChecked(true);
                        break;
                    default:
                        fragmentNotificationBinding.rbGeneral.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentNotificationBinding.rbPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentNotificationBinding.vpNotifications.setCurrentItem(0);
            }
        });
        fragmentNotificationBinding.rbGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentNotificationBinding.vpNotifications.setCurrentItem(1);
            }
        });*/
        viewPager = (ViewPager) inflate.findViewById(R.id.view_pager_history);

        PagerTabStrip pagerTabStrip = inflate.findViewById(R.id.pager_tab_strip);


        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        SlidingTabLayout tabLayout = inflate.findViewById(R.id.sliding_tab_layout);
        tabLayout.setDistributeEvenly(false);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return R.color.blueSecondary;
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });
        tabLayout.setCustomTabView(R.layout.custom_tab, R.id.custom_tab_textview);


        tabLayout.setViewPager(viewPager);
//        pagerTabStrip.setvis

//
//        viewPager.addOnPageChangeListener(onPageChangeListener);


    }


/*
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if (fragmentCurrentPosition != -1) {
                ((FragmentStateListener) viewPagerAdapter.getItem(fragmentCurrentPosition)).onPauseFragment();
            }
            ((FragmentStateListener) viewPagerAdapter.getItem(position)).onResumeFragment();
            fragmentCurrentPosition = position;
            questionNoAdapter.setSelected(position);
            indicatorRecycler.scrollToPosition(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
*/


    private final class ViewPagerAdapter extends FragmentStatePagerAdapter {

        BaseFragment[] testsFragments = new BaseFragment[]{new MyPracticeTestsFragment(), new MyOnlineTestsFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return testsFragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Practice Tests" : "Company Tests";
        }


    }


}
