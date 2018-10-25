package brainwiz.gobrainwiz.test;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
import java.util.HashMap;
import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.onlinetest.OnlineTestPostModel;
import brainwiz.gobrainwiz.practicetest.PractiseTestPostModel;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

public class TestQuestionFragment extends BaseFragment {

    private ViewPager viewPager;
    private boolean isCompanyTest;
    private ViewPagerAdapter viewPagerAdapter;
    private QuestionNoAdapter questionNoAdapter;
    private RecyclerView indicatorRecycler;
    private List<TestModel.Datum> data;
    private int fragmentCurrentPosition = -1;
    private boolean isReview;


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
        setHasOptionsMenu(true);
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
        isReview = getArguments().getBoolean(BaseFragment.IS_REVIEW);
        String id = getArguments().getString("id", "");
        String catId = getArguments().getString(CAT_ID, "");

        if (isCompanyTest) {

            HashMap<String, String> baseBodyMap = getBaseBodyMap();
            baseBodyMap.put("sets_id", catId);
            baseBodyMap.put("exam_id", id);
            RetrofitManager.getRestApiMethods().getCompanyTests(baseBodyMap).enqueue(callback);
        } else {
            RetrofitManager.getRestApiMethods().getPracticeTests(id).enqueue(callback);
        }


    }

    private void setViews(List<TestModel.Datum> data) {
        if (!isReview)
            ((TestActivity) getActivity()).startTest();

        questionNoAdapter.setReviewMode(isReview);
        questionNoAdapter.setOptions(getQuestionsObjects(data.size()));
        questionNoAdapter.notifyDataSetChanged();
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.setData(data);
        viewPager.setAdapter(viewPagerAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onPageChangeListener.onPageSelected(0);
            }
        }, 1000);

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
        public void onPageSelected(final int position) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (fragmentCurrentPosition != -1) {
//                        ((FragmentStateListener) viewPagerAdapter.getItem(fragmentCurrentPosition)).onPauseFragment();
                        viewPagerAdapter.getData().get(fragmentCurrentPosition).setEndTime(((TestActivity) getActivity()).getCurrentRunningTime());

                        long spentTime = viewPagerAdapter.getData().get(fragmentCurrentPosition).getSpentTime();

                        LogUtils.e("Total Time Spent sec:" + String.format("%02d:%02d", spentTime / 60, spentTime % 60));

                    }
                    viewPagerAdapter.getData().get(position).setStartTime(((TestActivity) getActivity()).getCurrentRunningTime());
                    fragmentCurrentPosition = position;
                    questionNoAdapter.setSelected(position);
                    indicatorRecycler.scrollToPosition(position);


                }
            });

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
        if (viewPagerAdapter.getData() == null) {
            return;
        }

        if (isCompanyTest) {
            RetrofitManager.getRestApiMethods().postOnlineTest(getOnlineTestData(viewPagerAdapter.getData())).enqueue(new ApiCallback<String>(getActivity()) {
                @Override
                public void onApiResponse(Response<String> response, boolean isSuccess, String message) {
                    LogUtils.e(response.body());
                }

                @Override
                public void onApiFailure(boolean isSuccess, String message) {
                    LogUtils.e(message);
                }
            });

        } else {
            RetrofitManager.getRestApiMethods().postPracticeTest(getPraticeTestData(viewPagerAdapter.getData())).enqueue(new ApiCallback<String>(getActivity()) {
                @Override
                public void onApiResponse(Response<String> response, boolean isSuccess, String message) {
                    LogUtils.e(response.body());
                }

                @Override
                public void onApiFailure(boolean isSuccess, String message) {
                    LogUtils.e(message);
                }
            });
        }

//        for (TestModel.Datum datum : viewPagerAdapter.getData()) {
//
//        }

/*        if (isCompanyTest) {
            Intent data = new Intent();
            data.putExtra(ID, getArguments().getString(ID, ""));
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
            ((TestActivity) getActivity()).getSupportFragmentManager().popBackStack();

        } else {

            ScoreCardFragment instance = ScoreCardFragment.getInstance(new ArrayList<TestModel.Datum>(viewPagerAdapter.getData()));

            ((TestActivity) getActivity()).fragmentTransaction(instance);
        }*/
    }

    @SuppressLint("HardwareIds")
    private OnlineTestPostModel getOnlineTestData(List<TestModel.Datum> data) {
        OnlineTestPostModel onlineTestPostModel = new OnlineTestPostModel();
        onlineTestPostModel.setToken(SharedPrefUtils.getToken(getActivity()));
        onlineTestPostModel.setStudentId(Integer.parseInt(SharedPrefUtils.getStudentID(getActivity())));
        onlineTestPostModel.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        onlineTestPostModel.setTest_id(Integer.parseInt(getArguments().getString(ID, "")));
        onlineTestPostModel.setSet_id(Integer.parseInt(getArguments().getString(CAT_ID, "")));

        ArrayList<OnlineTestPostModel.Question> questions = new ArrayList<>();

        for (TestModel.Datum datum : data) {
            OnlineTestPostModel.Question question = new OnlineTestPostModel.Question();
            question.setQId(datum.getQuestionId());
            String selectedOption = datum.getSelectedOption();
            if (selectedOption != null && !selectedOption.isEmpty()) {
                question.setSelected_option(Integer.parseInt(selectedOption));
            }
            question.setTime_taken((int) datum.getSpentTime());
            questions.add(question);
        }
        onlineTestPostModel.setQuestions(questions);
        return onlineTestPostModel;

    }

    @SuppressLint("HardwareIds")
    private PractiseTestPostModel getPraticeTestData(List<TestModel.Datum> data) {
        PractiseTestPostModel practiseTestPostModel = new PractiseTestPostModel();
        practiseTestPostModel.setToken(SharedPrefUtils.getToken(getActivity()));
        practiseTestPostModel.setStudentId(Integer.parseInt(SharedPrefUtils.getStudentID(getActivity())));
        practiseTestPostModel.setDeviceId(Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        practiseTestPostModel.setExamTestId(Integer.parseInt(getArguments().getString(ID, "")));

        ArrayList<PractiseTestPostModel.Question> questions = new ArrayList<>();

        for (TestModel.Datum datum : data) {
            PractiseTestPostModel.Question question = new PractiseTestPostModel.Question();
            question.setQId(datum.getQuestionId());
            String selectedOption = datum.getSelectedOption();
            if (selectedOption != null && !selectedOption.isEmpty()) {
                question.setSelectedOption(Integer.parseInt(selectedOption));
            }

            question.setTimeTaken((int) datum.getSpentTime());
            questions.add(question);
        }
        practiseTestPostModel.setQuestions(questions);
        return practiseTestPostModel;

    }

    public void bookMark(boolean checked) {
        viewPagerAdapter.getData().get(viewPager.getCurrentItem()).setBookMark(checked);
    }


    public boolean isBookMark() {
        List<TestModel.Datum> data = viewPagerAdapter.getData();
        return data != null && data.get(viewPager.getCurrentItem()).isBookMark();
    }

    public boolean isReview() {
        return isReview;
    }

    public void setReview(boolean review) {
        isReview = review;
    }

    private final class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<TestModel.Datum> data;
        QuestionFragment[] questionFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

//            QuestionFragment instance = questionFragments[position];
            QuestionFragment instance = QuestionFragment.getInstance(data.get(position), getArguments());
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
