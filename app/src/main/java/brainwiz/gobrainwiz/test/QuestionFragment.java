package brainwiz.gobrainwiz.test;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.YoutubeVideoActivity;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.FragmentTestContentBinding;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.StringUtills;
import brainwiz.gobrainwiz.utils.URLImageParserNew;

public class QuestionFragment extends BaseFragment implements TestQuestionFragment.FragmentStateListener {
    private TestModel.Datum data;
    private TestQuestionFragment.QuestionStatusListener listener;
    private long startTime;
    private long endTime;
    private long totalTime;
    private boolean isReview;


    public QuestionFragment() {
        super();
    }


    public static QuestionFragment getInstance(TestModel.Datum datum, Bundle bundle) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();

        args.putString(ID, bundle.getString(ID));
        args.putBoolean(IS_COMPANY_TEST, bundle.getBoolean(IS_COMPANY_TEST));
        args.putBoolean(IS_REVIEW, bundle.getBoolean(IS_REVIEW));
//        args.putBundle(bundle);
        args.putParcelable("object", datum);
        questionFragment.setArguments(args);
        return questionFragment;
    }


    @Override
    public void onResumeFragment() {
        startTime = ((TestActivity) getActivity()).getCurrentRunningTime();
    }

    @Override
    public void onPauseFragment() {
        endTime = ((TestActivity) getActivity()).getCurrentRunningTime();
//        totalTime = data.getSpentTime();
        totalTime += endTime - startTime;
//        timerTextView.setText(String.format("%02d:%02d", currentRunningTime / 60, currentRunningTime % 60));
        LogUtils.e("Total Time Spent sec:" + String.format("%02d:%02d", totalTime / 60, totalTime % 60));

//        data.setSpentTime(totalTime);

    }


    FragmentTestContentBinding contentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_test_content, container, false);
        contentBinding = DataBindingUtil.bind(inflate);
        init();
//        setHasOptionsMenu(true);
        return inflate;
    }

    private void init() {

        contentBinding.testVideoExplanationLayout.setOnClickListener(onClickListener);

        data = ((TestModel.Datum) getArguments().getParcelable("object"));
        contentBinding.answerOptionsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        OptionsAdapter adapter = new OptionsAdapter(getActivity(), data.getOptions());
        adapter.setHasStableIds(true);
        adapter.setListener(optionListener);
        boolean isReviewMode = getArguments().getBoolean(IS_REVIEW);
        adapter.setReviewMode(isReviewMode);
        adapter.setSelectedData(data);
        contentBinding.answerOptionsRecycler.setAdapter(adapter);
        contentBinding.bookmark.setVisibility(isReviewMode ? View.INVISIBLE : View.VISIBLE);
//        contentBinding.testQuestion.setText(data.getQuestion());
        if (!isReviewMode) {
            contentBinding.bookmark.setOnClickListener(onClickListener);
        }


        contentBinding.testExplanationLayout.setVisibility(isReviewMode && !data.getExplanation().isEmpty() ? View.VISIBLE : View.GONE);
        contentBinding.testVideoExplanationLayout.setVisibility(isReviewMode && !data.getVideoLink().isEmpty() ? View.VISIBLE : View.GONE);
        contentBinding.testQuestion.loadData(StringUtills.getHtmlContent(data.getQuestion()), "text/html", "UTF-8");
        contentBinding.testQuestion.getSettings().setUseWideViewPort(false);
        contentBinding.testQuestion.getSettings().setJavaScriptEnabled(true);
        contentBinding.testQuestion.getSettings().setLoadWithOverviewMode(true);
        contentBinding.testQuestion.setScrollbarFadingEnabled(true);
        contentBinding.testExplanation.loadData(StringUtills.getHtmlContent(data.getExplanation()), "text/html", "UTF-8");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            contentBinding.testExplanation.setText(Html.fromHtml(data.getExplanation(), Html.FROM_HTML_MODE_LEGACY, new URLImageParserNew(contentBinding.testExplanation, getActivity()), null));
//            contentBinding.testQuestion.setText(Html.fromHtml(data.getQuestion(), Html.FROM_HTML_MODE_LEGACY, new URLImageParserNew(contentBinding.testQuestion, getActivity()), null));
//        } else {
//            contentBinding.testQuestion.setText(Html.fromHtml(data.getQuestion(), new URLImageParserNew(contentBinding.testQuestion, getActivity()), null));
//            contentBinding.testExplanation.setText(Html.fromHtml(data.getExplanation(), new URLImageParserNew(contentBinding.testExplanation, getActivity()), null));
//        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bookmark:
                    contentBinding.bookmark.setChecked(!contentBinding.bookmark.isChecked());
                    data.setBookMark(contentBinding.bookmark.isChecked());
                    listener.onMarkReview(contentBinding.bookmark.isChecked());
                    break;
                case R.id.test_video_explanation_layout:
                    Intent intent1 = new Intent(getActivity(), YoutubeVideoActivity.class);
                    String youtubeLink = data.getVideoLink();
                    youtubeLink = youtubeLink.substring(youtubeLink.lastIndexOf("/") + 1, youtubeLink.length());

                    intent1.putExtra(YoutubeVideoActivity.VIDEO_ID, youtubeLink);
                    startActivity(intent1);
                    break;
            }
        }
    };


    private final OptionsAdapter.OptionListener optionListener = new OptionsAdapter.OptionListener() {
        @Override
        public void onOptionSelected(int position) {
            data.setSelectedOption(String.valueOf(position));
            listener.onAnswer(true);
        }
    };

    public void setListener(TestQuestionFragment.QuestionStatusListener listener) {
        this.listener = listener;
    }

    public TestQuestionFragment.QuestionStatusListener getListener() {
        return listener;
    }


}
