package brainwiz.gobrainwiz.test;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.FragmentTestContentBinding;

public class QuestionFragment extends BaseFragment {
    private TestModel.Datum data;

    public static QuestionFragment getInstance(TestModel.Datum datum, Bundle bundle) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = bundle;
        args.putParcelable("object", datum);
        questionFragment.setArguments(args);
        return questionFragment;
    }

    FragmentTestContentBinding contentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_test_content, container, false);
        contentBinding = DataBindingUtil.bind(inflate);
        init();
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

        contentBinding.testExplanationLayout.setVisibility(isReviewMode ? View.VISIBLE : View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentBinding.testExplanation.setText(Html.fromHtml(data.getExplanation(), Html.FROM_HTML_MODE_LEGACY));
            contentBinding.testQuestion.setText(Html.fromHtml(data.getQuestion(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            contentBinding.testQuestion.setText(Html.fromHtml(data.getQuestion()));
            contentBinding.testExplanation.setText(Html.fromHtml(data.getExplanation()));
        }
    }

    private final OptionsAdapter.OptionListener optionListener = new OptionsAdapter.OptionListener() {
        @Override
        public void onOptionSelected(int position) {
            data.setSelectedOption(String.valueOf(position));
        }
    };
}
