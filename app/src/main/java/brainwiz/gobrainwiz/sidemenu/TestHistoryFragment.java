package brainwiz.gobrainwiz.sidemenu;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import brainwiz.gobrainwiz.databinding.FragmentTestHistoryBinding;
import brainwiz.gobrainwiz.utils.SlidingTabLayout;

public class TestHistoryFragment extends BaseFragment {

    private ViewPagerAdapter viewPagerAdapter;
    FragmentTestHistoryBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_test_history, container, false);
        bind = DataBindingUtil.bind(inflate);
        init(inflate);

        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void init(View inflate) {

        bind.viewPagerHistory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bind.rbPractice.setChecked(true);
                        break;
                    default:
                        bind.rbOnline.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bind.rbPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.viewPagerHistory.setCurrentItem(0);
            }
        });
        bind.rbOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.viewPagerHistory.setCurrentItem(1);
            }
        });


        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        bind.viewPagerHistory.setAdapter(viewPagerAdapter);


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
