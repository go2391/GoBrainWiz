package brainwiz.gobrainwiz.test;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.HashMap;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.databinding.FragmentScoreCardOnlineBinding;
import brainwiz.gobrainwiz.ui.CircularProgressBar;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.StringUtills;
import retrofit2.Response;

public class ScoreCardOnlineFragment extends BaseFragment {

    private static final int TEST_REQUESTCODE = 1;
    FragmentScoreCardOnlineBinding bind;
    private boolean isReview;


    public static ScoreCardOnlineFragment getInstance(ScoreCardModel.Datum datum, boolean isReview, String testID) {
        ScoreCardOnlineFragment scoreCardFragment = new ScoreCardOnlineFragment();
        Bundle args = new Bundle();
        args.putParcelable("object", datum);
        args.putBoolean(IS_REVIEW, isReview);
        args.putString(ID, testID);
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

        isReview = getArguments().getBoolean(IS_REVIEW);


        if (isReview) {
            getScoreCard();
        } else {
            ScoreCardModel.Datum object = getArguments().getParcelable("object");

            showScoreCard(object);
        }

        bind.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEST_REQUESTCODE) {
//            if (data != null) {
//                List<OnlineTestSetModel.TestSet> options = adapter.getOptions();
//                for (OnlineTestSetModel.TestSet option : options) {
//                    if (option.getCatId().equalsIgnoreCase(data.getStringExtra(CAT_ID))) {
//                        option.setCompleted(true);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//
////                breakTime.setVisibility(View.VISIBLE);
//
////                breakTime.setText();
//
//
//                try {
//                    final ScoreCardModel.Datum parcelableExtra = (ScoreCardModel.Datum) data.getParcelableExtra("data");
//                    final ArrayList<ScoreCardModel.Sets> resultList = new ArrayList<>(parcelableExtra.getSets());
//
//                    if (!resultList.isEmpty()) {
//                        ((TestActivity) activity).stopTest();
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                ScoreCardOnlineFragment instance = ScoreCardOnlineFragment.getInstance(parcelableExtra,isReview,testID);
//                                ((TestActivity) getActivity()).fragmentTransaction(instance);
//
//                            }
//                        }, 2000);
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void showScoreCard(final ScoreCardModel.Datum object) {
        bind.scoreCardRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ScoreCardAdapter adapter = new ScoreCardAdapter(getActivity(), new ArrayList<>(object.getSets()));
        adapter.setReviewMode(true);
        adapter.setListener(new ScoreCardAdapter.OptionListener() {
            @Override
            public void onOptionSelected(int position) {
                ScoreCardModel.Sets data = object.getSets().get(position);

                TestQuestionFragment instance = TestQuestionFragment.getInstance(data.getTestId(), data.getCatId(), true, isReview);
                instance.setTargetFragment(ScoreCardOnlineFragment.this, TEST_REQUESTCODE);
                ((TestActivity) getActivity()).fragmentTransaction(instance);
            }
        });
        bind.scoreCardRecycler.setAdapter(adapter);
        bind.scoreCardRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        bind.scoreCardCorrect.setText(StringUtills.getSpanMarks(String.format(getString(R.string.correct), object.getCorrectAnswers())));
        bind.scoreCardWrong.setText(StringUtills.getSpanMarks(String.format(getString(R.string.wrong), object.getIncorrectAnswers())));
        bind.scoreCardQuestionsAttempted.setText(StringUtills.getSpanMarks(String.format(getString(R.string.questions_attempted), object.getAttemptedQuestions())));
        bind.scoreCardTotalQuestions.setText(StringUtills.getSpanMarks(String.format(getString(R.string.total_questions), object.getTotalQuestions())));
        float i = (float) object.getCorrectAnswers() / object.getTotalQuestions();
        int round = Math.round(i * 100f);

        bind.scoreCardProgress.animateProgressTo(0, (int) round, new CircularProgressBar.ProgressAnimationListener() {

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
        baseBodyMap.put("online_id", getArguments().getString(ID, ""));
        RetrofitManager.getRestApiMethods().getScoreCard(baseBodyMap).enqueue(new ApiCallback<ScoreCardModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<ScoreCardModel> response, boolean isSuccess, String message) {
                if (isSuccess) {

                    showScoreCard(response.body().getData());

                }
                dismissProgress();
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }

    public boolean isReview() {
        return isReview;
    }

    public void setReview(boolean review) {
        this.isReview = review;
    }
}
