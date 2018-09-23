package brainwiz.gobrainwiz.test;

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

import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.TestModel;
import retrofit2.Response;

public class TestQuestionFragment extends BaseFragment {

    private ViewPager viewPager;
    private boolean isCompanyTest;
    private ViewPagerAdapter viewPagerAdapter;


    public static TestQuestionFragment getInstance(String id, boolean isReview) {

        TestQuestionFragment testQuestionFragment = new TestQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        bundle.putBoolean(IS_COMPANY_TEST, false);
        bundle.putBoolean(IS_REVIEW, isReview);
        testQuestionFragment.setArguments(bundle);
        return testQuestionFragment;
    }

    public static TestQuestionFragment getInstance(String id, String catId, boolean isCompanyTest, boolean isReview) {
        TestQuestionFragment testQuestionFragment = new TestQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        bundle.putString(CAT_ID, catId);
        bundle.putBoolean(IS_COMPANY_TEST, isCompanyTest);
        bundle.putBoolean(IS_REVIEW, isReview);
        testQuestionFragment.setArguments(bundle);
        return testQuestionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_test_tab, container, false);
        ViewDataBinding bind = DataBindingUtil.bind(inflate);
        init(inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getQuestions();
    }

    private void getQuestions() {

        showProgress();
        ApiCallback<TestModel> callback = new ApiCallback<TestModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<TestModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    TestModel body = response.body();
                    List<TestModel.Datum> data = body.getData();
                    viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
                    viewPagerAdapter.setData(data);
                    viewPager.setAdapter(viewPagerAdapter);
                }
                dismissProgress();
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        };

        isCompanyTest = getArguments().getBoolean("isCompanyTest");
        String id = getArguments().getString("id", "");
//        String catId = getArguments().getString("catId", "");

        if (isCompanyTest) {

            String catId = new StringBuilder().append(id).append("/").append(getArguments().getString("catId")).toString();
            RetrofitManager.getRestApiMethods().getCompanyTests(catId).enqueue(callback);
        } else {
            RetrofitManager.getRestApiMethods().getPracticeTests(id).enqueue(callback);
        }


    }

    private void init(View inflate) {
        viewPager = (ViewPager) inflate.findViewById(R.id.view_pager_questions);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) inflate.findViewById(R.id.pager_tab_strip);

        inflate.findViewById(R.id.test_next).setOnClickListener(onClickListener);

        inflate.findViewById(R.id.test_previous).setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_next:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    break;
                case R.id.test_previous:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                    break;
            }
        }
    };

    public void submitTest() {
//        TODO Submit Test
        for (TestModel.Datum datum : viewPagerAdapter.getData()) {

        }
    }


    private final class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<TestModel.Datum> data;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.getInstance(data.get(position), getArguments());
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position + 1);
        }

        public void setData(List<TestModel.Datum> data) {
            this.data = data;
        }

        public List<TestModel.Datum> getData() {
            return data;
        }
    }

}
