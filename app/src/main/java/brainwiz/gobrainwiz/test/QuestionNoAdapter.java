package brainwiz.gobrainwiz.test;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.databinding.InflateQuestionNoBinding;

public class QuestionNoAdapter extends RecyclerView.Adapter<QuestionNoAdapter.NumberHolder> {

    private int selectedPosition = 0;

    public List<QuestionNumber> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionNumber> options) {
        this.options = options;
    }

    private List<QuestionNumber> options = new ArrayList<>();

    private Context context;
    private QuestionListener listener;
    private boolean reviewMode;


    public QuestionNoAdapter(Context context) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public NumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberHolder(LayoutInflater.from(context).inflate(R.layout.inflate_question_no, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NumberHolder holder, int position) {
        QuestionNumber questionNumber = options.get(position);
        holder.bind.optionNo.setText(questionNumber.getNo());

        holder.bind.optionNo.setBackgroundResource(questionNumber.isDone() ? R.drawable.stroke_greeen_bg_gray : R.drawable.stroke_gray_bg_question);
        if (questionNumber.isReview()) {
            holder.bind.optionNo.setBackgroundResource(R.drawable.stroke_greeen_bg_orange);
        }
        if (questionNumber.isSelected()) {
            holder.bind.optionNo.setBackgroundResource(R.drawable.stroke_greeen_bg_question);
        }


    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void setListener(QuestionListener listener) {
        this.listener = listener;
    }

    public QuestionListener getListener() {
        return listener;
    }

    public void setReviewMode(boolean reviewMode) {
        this.reviewMode = reviewMode;
    }

    public boolean isReviewMode() {
        return reviewMode;
    }

    public void setSelected(int position) {
        setSelection(position);
//        notifyDataSetChanged();
    }


    class NumberHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateQuestionNoBinding bind;

        public NumberHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {

                if (listener != null) {
                    listener.onOptionSelected(adapterPosition);
                }

            }
        }
    }

    private void setSelection(int adapterPosition) {
        if (selectedPosition != adapterPosition) {
            options.get(selectedPosition).setSelected(false);
            options.get(adapterPosition).setSelected(true);
            selectedPosition = adapterPosition;
            notifyDataSetChanged();
        }
    }

    interface QuestionListener {
        void onOptionSelected(int position);
    }
}
