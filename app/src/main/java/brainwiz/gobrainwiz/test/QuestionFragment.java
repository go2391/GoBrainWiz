package brainwiz.gobrainwiz.test;

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
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.FragmentTestContentBinding;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.UrlImageParser;

public class QuestionFragment extends BaseFragment implements TestQuestionFragment.FragmentStateListener {
    private TestModel.Datum data;
    private TestQuestionFragment.QuestionStatusListener listener;
    private long startTime;
    private long endTime;
    private long totalTime;
    private boolean isReview;


    public static QuestionFragment getInstance(TestModel.Datum datum, Bundle bundle) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = bundle;
        args.putParcelable("object", datum);
        questionFragment.setArguments(args);
        return questionFragment;
    }


    @Override
    public void onResumeFragment() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onPauseFragment() {
        endTime = System.currentTimeMillis();
//        totalTime = data.getSpentTime();
        totalTime += endTime - startTime;
        LogUtils.e("Total Time Spent sec:" + totalTime / 1000);

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


        data = ((TestModel.Datum) getArguments().getParcelable("object"));
        contentBinding.answerOptionsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        OptionsAdapter adapter = new OptionsAdapter(getActivity(), data.getOptions());
        adapter.setListener(optionListener);
        boolean isReviewMode = getArguments().getBoolean(IS_REVIEW);
        adapter.setReviewMode(isReviewMode);
        adapter.setSelectedData(data);
        contentBinding.answerOptionsRecycler.setAdapter(adapter);
//        contentBinding.testQuestion.setText(data.getQuestion());
        if (!isReviewMode) {
            contentBinding.bookmark.setOnClickListener(onClickListener);
        }


        contentBinding.testExplanationLayout.setVisibility(isReviewMode ? View.VISIBLE : View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentBinding.testExplanation.setText(Html.fromHtml(data.getExplanation(), Html.FROM_HTML_MODE_LEGACY, new UrlImageParser(contentBinding.testExplanation, getActivity()), null));
            contentBinding.testQuestion.setText(Html.fromHtml(data.getQuestion(), Html.FROM_HTML_MODE_LEGACY, new UrlImageParser(contentBinding.testExplanation, getActivity()), null));
        } else {
            contentBinding.testQuestion.setText(Html.fromHtml(data.getQuestion(), new UrlImageParser(contentBinding.testExplanation, getActivity()), null));
            contentBinding.testExplanation.setText(Html.fromHtml(data.getExplanation(), new UrlImageParser(contentBinding.testExplanation, getActivity()), null));
        }
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
