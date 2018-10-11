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
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.FragmentScoreCardBinding;

public class ScoreCardFragment extends BaseFragment {

    FragmentScoreCardBinding bind;


    public static ScoreCardFragment getInstance(ArrayList<TestModel.Datum> datum) {
        ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("object", datum);
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

        ArrayList<TestModel.Datum> object = getArguments().getParcelableArrayList("object");
        int correctAnswers = 0;
        int attempted = 0;

        for (int i = 0; i < object.size(); i++) {
            TestModel.Datum datum = object.get(i);
            if (datum.getSelectedOption() != null && !datum.getSelectedOption().isEmpty()) {
                ++attempted;
                if (datum.getSelectedAnswer().equalsIgnoreCase(datum.getSelectedOption())) {
                    ++correctAnswers;
                }
            }
        }
        bind.scoreCardCorrect.setText(String.format(getString(R.string.correct), correctAnswers));
        bind.scoreCardQuestionsAttempted.setText(String.format(getString(R.string.questions_attempted), attempted));
        bind.scoreCardTotalQuestions.setText(String.format(getString(R.string.total_questions), object.size()));
        bind.scoreCardProgress.setProgress((correctAnswers / object.size()) * 100);
    }
}
