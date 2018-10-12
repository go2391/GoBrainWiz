package brainwiz.gobrainwiz.test;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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
    private QuestionNoAdapter questionNoAdapter;
    private RecyclerView indicatorRecycler;
    private List<TestModel.Datum> data;
    private int fragmentCurrentPosition = -1;


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
                    data = body.getData();
                    setViews(data);
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

    private void setViews(List<TestModel.Datum> data) {

        ((TestActivity) getActivity()).startTest();
        questionNoAdapter.setOptions(getQuestionsObjects(data.size()));
        questionNoAdapter.notifyDataSetChanged();
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.setData(data);
        viewPager.setAdapter(viewPagerAdapter);
        onPageChangeListener.onPageSelected(0);
    }

    private List<QuestionNumber> getQuestionsObjects(int size) {
        List<QuestionNumber> questionNumbers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            questionNumbers.add(new QuestionNumber(String.valueOf(i)));
        }
        if (!questionNumbers.isEmpty()) {
            questionNumbers.get(0).setSelected(true);
        }
        return questionNumbers;
    }

    private void init(View inflate) {

        indicatorRecycler = inflate.findViewById(R.id.question_no_recycler);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        indicatorRecycler.setLayoutManager(layout);
        questionNoAdapter = new QuestionNoAdapter(getActivity());
        indicatorRecycler.setAdapter(questionNoAdapter);
        questionNoAdapter.setListener(questionListener);
        viewPager = (ViewPager) inflate.findViewById(R.id.view_pager_questions);


        inflate.findViewById(R.id.test_next).setOnClickListener(onClickListener);

        inflate.findViewById(R.id.test_previous).setOnClickListener(onClickListener);

        viewPager.addOnPageChangeListener(onPageChangeListener);


        inflate.findViewById(R.id.questions_overview).setOnClickListener(onClickListener);


    }


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

    QuestionNoAdapter.QuestionListener questionListener = new QuestionNoAdapter.QuestionListener() {
        @Override
        public void onOptionSelected(int position) {
            viewPager.setCurrentItem(position);
        }
    };


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
                case R.id.questions_overview:
                    OverViewFragment overViewFragment = new OverViewFragment();
                    overViewFragment.setData(questionNoAdapter.getOptions());
                    overViewFragment.setQuestionListener(questionListener);
                    overViewFragment.show(getChildFragmentManager(), overViewFragment.getClass().getSimpleName());
                    break;
            }
        }
    };

    public void submitTest() {
//        TODO Submit Test
        for (TestModel.Datum datum : viewPagerAdapter.getData()) {

        }

        ScoreCardFragment instance = ScoreCardFragment.getInstance(new ArrayList<TestModel.Datum>(viewPagerAdapter.getData()));

        ((TestActivity) getActivity()).fragmentTransaction(instance);
    }

    public void bookMark(boolean checked) {
        viewPagerAdapter.getData().get(viewPager.getCurrentItem()).setBookMark(checked);
    }


    public boolean isBookMark() {
        List<TestModel.Datum> data = viewPagerAdapter.getData();
        return data != null && data.get(viewPager.getCurrentItem()).isBookMark();
    }

    private final class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<TestModel.Datum> data;
        QuestionFragment[] questionFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            QuestionFragment instance = questionFragments[position];
            instance.setListener(questionStatusListener);
            return instance;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        /*@Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position + 1);
        }
*/
        public void setData(List<TestModel.Datum> data) {
            this.data = data;
            questionFragments = new QuestionFragment[data.size()];
            for (int i = 0; i < data.size(); i++) {
                questionFragments[i] = QuestionFragment.getInstance(data.get(i), getArguments());
            }
        }

        public List<TestModel.Datum> getData() {
            return data;
        }
    }


    QuestionStatusListener questionStatusListener = new QuestionStatusListener() {
        @Override
        public void onSelected(boolean status) {

        }

        @Override
        public void onMarkReview(boolean status) {
            questionNoAdapter.getOptions().get(viewPager.getCurrentItem()).setReview(status);
        }

        @Override
        public void onAnswer(boolean status) {
            questionNoAdapter.getOptions().get(viewPager.getCurrentItem()).setDone(status);
        }

    };

    interface QuestionStatusListener {
        void onSelected(boolean status);

        void onMarkReview(boolean status);

        void onAnswer(boolean status);
    }

    interface FragmentStateListener {
        void onResumeFragment();

        void onPauseFragment();
    }

}
