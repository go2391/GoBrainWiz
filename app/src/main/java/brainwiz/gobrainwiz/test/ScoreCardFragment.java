package brainwiz.gobrainwiz.test;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.MainActivity;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.PractiseTestResultModel;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.databinding.FragmentScoreCardBinding;
import brainwiz.gobrainwiz.ui.CircularProgressBar;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.StringUtills;
import retrofit2.Response;

public class ScoreCardFragment extends BaseFragment {

    FragmentScoreCardBinding bind;

    private boolean isReview;

    private Context context;

    public static ScoreCardFragment getInstance(PractiseTestResultModel.Data datum, boolean isReview, String id) {
        ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
        Bundle args = new Bundle();
        args.putParcelable("object", datum);
        args.putBoolean(IS_REVIEW, isReview);
        args.putString(ID, id);
        scoreCardFragment.setArguments(args);
        return scoreCardFragment;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
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

    public boolean isReview() {
        return isReview;
    }

    public void setReview(boolean review) {
        isReview = review;
    }

    private void initViews() {

        isReview = getArguments().getBoolean(IS_REVIEW);
        if (isReview) {
            getScoreCard();

        } else {
            PractiseTestResultModel.Data object = getArguments().getParcelable("object");
            showScorecard(object);

        }

        bind.reviewQuestions.setText(isReview ? getString(R.string.review_questions) : getString(R.string.done));

        bind.reviewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReview) {
                    Bundle extras = getArguments();
                    ((TestActivity) getActivity()).fragmentTransaction(TestQuestionFragment.getInstance(extras.getString(BaseFragment.ID), extras.getBoolean(BaseFragment.IS_REVIEW)), R.id.content_frame, true);
                } else {
                    getActivity().onBackPressed();
                }
            }
        });

    }

    private void showScorecard(PractiseTestResultModel.Data object) {

        bind.scoreCardCorrect.setText(StringUtills.getSpanMarks(String.format(getString(R.string.correct), object.getCorrect())));
        bind.scoreCardWrong.setText(StringUtills.getSpanMarks(String.format(getString(R.string.wrong), object.getIncorrect())));
        bind.scoreCardQuestionsAttempted.setText(StringUtills.getSpanMarks(String.format(getString(R.string.questions_attempted), object.getAttemptedQuestions())));
        bind.scoreCardTotalQuestions.setText(StringUtills.getSpanMarks(String.format(getString(R.string.total_questions), object.getTotalQuestions())));
        bind.scoreCardQuestionsUnAttempted.setText(String.format(context.getString(R.string.questions_unattempted), object.getNonattemptedQuestions()));
        float progress = (float) object.getCorrect() / object.getTotalQuestions();
        bind.scoreCardProgress.setProgress(Math.round(progress * 100f));


        bind.scoreCardProgress.animateProgressTo(0, Math.round(progress * 100f), new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                bind.scoreCardProgress.setTitle(progress + "");
            }

            @Override
            public void onAnimationFinish() {
                bind.scoreCardProgress.setSubTitle("percentage");
            }
        });

        bind.rank.setText(StringUtills.getRankText(getActivity(), object.getRank(), object.getTotalRank()));

    }


    private void getScoreCard() {

        if (!NetWorkUtil.isConnected(getActivity())) {
            DDAlerts.showNetworkAlert(getActivity());
            return;
        }

        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
        baseBodyMap.put("practice_id", getArguments().getString(ID, ""));
        RetrofitManager.getRestApiMethods().getPractiseScoreCard(baseBodyMap).enqueue(new ApiCallback<PractiseTestResultModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<PractiseTestResultModel> response, boolean isSuccess, String message) {
                if (isSuccess) {

                    showScorecard(response.body().getData());

                }
                dismissProgress();
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }
}
