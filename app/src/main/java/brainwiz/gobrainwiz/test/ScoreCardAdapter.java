package brainwiz.gobrainwiz.test;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.databinding.InflateOnlineScoreCardBinding;
import brainwiz.gobrainwiz.ui.CircularProgressBar;
import brainwiz.gobrainwiz.utils.StringUtills;

public class ScoreCardAdapter extends RecyclerView.Adapter<ScoreCardAdapter.ScoreCardHolder> {

    private ArrayList<ScoreCardModel.Sets> scoreCards = new ArrayList<>();

    private Context context;
    private int selectedOption = -1;
    private OptionListener listener;
    private boolean reviewMode;


    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public ScoreCardAdapter(Context context, ArrayList<ScoreCardModel.Sets> scoreCards) {
        this.context = context;
        this.scoreCards = scoreCards;
    }

    @NonNull
    @Override
    public ScoreCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreCardHolder(LayoutInflater.from(context).inflate(R.layout.inflate_online_score_card, parent, false));
    }

    @SuppressLint("ObjectAnimatorBinding")
    @Override
    public void onBindViewHolder(@NonNull final ScoreCardHolder holder, int position) {

        ScoreCardModel.Sets source = scoreCards.get(position);
        /*int bgResource = 0;
        switch (position) {
            case 0:
                bgResource = R.drawable.gradient_orange;
                break;
            case 1:
                bgResource = R.drawable.gradient_blue;
                break;
            default:
                bgResource = R.drawable.gradient_red;
                break;
        }
        holder.bind.scoreCardLayout.setBackgroundResource(bgResource);
        */
        int totalTime = Integer.parseInt(source.getTime());
        holder.bind.timeTaken.setText(String.format("%02d:%02d", totalTime / 60, totalTime % 60));
        holder.bind.yourScore.setText(source.getCategory_name());
        holder.bind.scoreCardCorrect.setText(StringUtills.getSpanMarks(String.format(context.getString(R.string.correct), Integer.parseInt(source.getCorrectAnswers()))));
        holder.bind.scoreCardWrong.setText(StringUtills.getSpanMarks(String.format(context.getString(R.string.wrong), Integer.parseInt(source.getIncorrectAnswers()))));
        holder.bind.scoreCardQuestionsAttempted.setText(StringUtills.getSpanMarks(String.format(context.getString(R.string.questions_attempted), Integer.parseInt(source.getAttemptedQues()))));
        holder.bind.scoreCardQuestionsUnAttempted.setText(StringUtills.getSpanMarks(String.format(context.getString(R.string.questions_unattempted), Integer.parseInt(source.getUnattemptedQues()))));
        holder.bind.scoreCardTotalQuestions.setText(StringUtills.getSpanMarks(String.format(context.getString(R.string.total_questions), Integer.parseInt(source.getAllQuestions()))));
        final float progress = (Integer.parseInt(source.getCorrectAnswers()) / Float.parseFloat(source.getAllQuestions())) * 100f;


        holder.bind.scoreCardProgress.animateProgressTo(0, (int) progress, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                holder.bind.scoreCardProgress.setTitle(progress + "");
            }

            @Override
            public void onAnimationFinish() {
                holder.bind.scoreCardProgress.setSubTitle("percentage");
            }
        });


    }


    @Override
    public int getItemCount() {
        return scoreCards.size();
    }

    public void setListener(OptionListener listener) {
        this.listener = listener;
    }

    public OptionListener getListener() {
        return listener;
    }

    public void setReviewMode(boolean reviewMode) {
        this.reviewMode = reviewMode;
    }

    public boolean isReviewMode() {
        return reviewMode;
    }


    class ScoreCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateOnlineScoreCardBinding bind;

        public ScoreCardHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            if (isReviewMode()) {
                bind.reviewQuestions.setVisibility(View.VISIBLE);
                bind.reviewQuestions.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {

                selectedOption = getAdapterPosition();

                if (listener != null) {
                    listener.onOptionSelected(selectedOption);
                }
                notifyDataSetChanged();
            }
        }
    }

    interface OptionListener {
        void onOptionSelected(int position);
    }
}
