package brainwiz.gobrainwiz.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.PractiseTestResultModel;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.databinding.FragmentScoreCardBinding;
import brainwiz.gobrainwiz.databinding.FragmentScoreCardOnlineBinding;

public class ScoreCardOnlineFragment extends BaseFragment {

    FragmentScoreCardOnlineBinding bind;


    public static ScoreCardOnlineFragment getInstance(ArrayList<ScoreCardModel.Datum> datum) {
        ScoreCardOnlineFragment scoreCardFragment = new ScoreCardOnlineFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("object", datum);
        scoreCardFragment.setArguments(args);
        return scoreCardFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_score_card_online, container, false);
        bind = DataBindingUtil.bind(inflate);
        ((TestActivity) getActivity()).invalidateOptionsMenu();
        initViews();
        return inflate;
    }

    private void initViews() {

        ArrayList<ScoreCardModel.Datum> object = getArguments().getParcelableArrayList("object");

        bind.scoreCardRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        bind.scoreCardRecycler.setAdapter(new ScoreCardAdapter(getActivity(), new ArrayList<ScoreCardModel.Datum>(object.subList(0,object.size() - 2))));
//        bind.scoreCardCorrect.setText(String.format(getString(R.string.correct), object.getCorrect()));
//        bind.scoreCardWrong.setText(String.format(getString(R.string.wrong), object.getIncorrect()));
//        bind.scoreCardQuestionsAttempted.setText(String.format(getString(R.string.questions_attempted), object.getAttemptedQuestions()));
//        bind.scoreCardTotalQuestions.setText(String.format(getString(R.string.total_questions), object.getTotalQuestions()));
//        bind.scoreCardProgress.setProgress((object.getCorrect() / object.getTotalQuestions()) * 100);
    }
}
