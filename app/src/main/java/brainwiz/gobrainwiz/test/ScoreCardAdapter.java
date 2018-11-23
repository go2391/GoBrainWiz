package brainwiz.gobrainwiz.test;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.databinding.InflateOnlineScoreCardBinding;

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

    @Override
    public void onBindViewHolder(@NonNull ScoreCardHolder holder, int position) {

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
        holder.bind.scoreCardCorrect.setText(String.format(context.getString(R.string.correct), Integer.parseInt(source.getCorrectAnswers())));
        holder.bind.scoreCardWrong.setText(String.format(context.getString(R.string.wrong), Integer.parseInt(source.getIncorrectAnswers())));
        holder.bind.scoreCardQuestionsAttempted.setText(String.format(context.getString(R.string.questions_attempted), Integer.parseInt(source.getAttemptedQues())));
        holder.bind.scoreCardTotalQuestions.setText(String.format(context.getString(R.string.total_questions), Integer.parseInt(source.getAllQuestions())));
        float progress = (Integer.parseInt(source.getCorrectAnswers()) / Float.parseFloat(source.getAllQuestions())) * 100f;
        holder.bind.scoreCardProgress.setProgress(Math.round(progress));

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
