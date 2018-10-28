package brainwiz.gobrainwiz.test;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.PractiseTestResultModel;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.FragmentScoreCardBinding;

public class ScoreCardFragment extends BaseFragment {

    FragmentScoreCardBinding bind;


    public static ScoreCardFragment getInstance(PractiseTestResultModel.Data datum) {
        ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
        Bundle args = new Bundle();
        args.putParcelable("object", datum);
        scoreCardFragment.setArguments(args);
        return scoreCardFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_score_card, container, false);
        bind = DataBindingUtil.bind(inflate);
        ((TestActivity) getActivity()).invalidateOptionsMenu();
        initViews();
        return inflate;
    }

    private void initViews() {

        PractiseTestResultModel.Data object = getArguments().getParcelable("object");

        bind.scoreCardCorrect.setText(String.format(getString(R.string.correct), object.getCorrect()));
        bind.scoreCardWrong.setText(String.format(getString(R.string.wrong), object.getIncorrect()));
        bind.scoreCardQuestionsAttempted.setText(String.format(getString(R.string.questions_attempted), object.getAttemptedQuestions()));
        bind.scoreCardTotalQuestions.setText(String.format(getString(R.string.total_questions), object.getTotalQuestions()));
        bind.scoreCardProgress.setProgress((object.getCorrect() / object.getTotalQuestions()) * 100);
    }
}
