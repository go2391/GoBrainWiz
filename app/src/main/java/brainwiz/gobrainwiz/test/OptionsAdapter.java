package brainwiz.gobrainwiz.test;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.InflateAnswerOptionBinding;
import brainwiz.gobrainwiz.utils.UrlImageParser;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionHolder> {

    private List<String> options = new ArrayList<>();
    private String[] optionsIndicator = new String[]{"A", "B", "C", "D", "E", "F"};

    private Context context;
    private int selectedOption = -1;
    private OptionListener listener;
    private boolean reviewMode;
    private TestModel.Datum selectedData;


    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public OptionsAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionHolder(LayoutInflater.from(context).inflate(R.layout.inflate_answer_option, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.bind.optionNo.setText(optionsIndicator[position]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.bind.optionText.setText(Html.fromHtml(options.get(position), Html.FROM_HTML_MODE_LEGACY, new UrlImageParser(holder.bind.optionText, context), null));
        } else {
            holder.bind.optionText.setText(Html.fromHtml(options.get(position), new UrlImageParser(holder.bind.optionText, context), null));
        }
        String selectedOption = selectedData.getSelectedOption();
        boolean selected = selectedOption != null && selectedOption.equals(String.valueOf(position));
        holder.bind.optionNo.setSelected(selected);
        holder.bind.optionLayout.setSelected(selected);
    }

    @Override
    public int getItemCount() {
        return options.size();
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

    public void setSelectedData(TestModel.Datum selectedData) {
        this.selectedData = selectedData;
    }

    public TestModel.Datum getSelectedData() {
        return selectedData;
    }

    class OptionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateAnswerOptionBinding bind;

        public OptionHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {

                selectedOption = getAdapterPosition();
                selectedData.setSelectedOption(String.valueOf(selectedOption));
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