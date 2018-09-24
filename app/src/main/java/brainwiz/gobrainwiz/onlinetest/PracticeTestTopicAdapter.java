package brainwiz.gobrainwiz.onlinetest;

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
import brainwiz.gobrainwiz.api.model.PracticeTestModel;
import brainwiz.gobrainwiz.databinding.InflateTestTopicItemBinding;

public class PracticeTestTopicAdapter extends RecyclerView.Adapter<PracticeTestTopicAdapter.TopicViewHolder> {

    Context context;
    private List<PracticeTestModel.SubList> subLists = new ArrayList<>();

    private TopicSelectionListener topicSelectionListener;


    public PracticeTestTopicAdapter(Context context, List<PracticeTestModel.SubList> subLists) {

        this.context = context;
        this.subLists = subLists;
    }

    public void setTopicSelectionListener(TopicSelectionListener topicSelectionListener) {
        this.topicSelectionListener = topicSelectionListener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_test_topic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        PracticeTestModel.SubList subList = subLists.get(position);
        holder.bind.testTopicItemTitle.setText(subList.getTopicName());
        holder.bind.testTopicItemDesc.setText(String.format(context.getString(R.string.tests), subList.getTestsCount()));
    }

    @Override
    public int getItemCount() {
        return subLists.size();
    }

    class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateTestTopicItemBinding bind;

        public TopicViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (topicSelectionListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                topicSelectionListener.onTopicSelect(subLists.get(adapterPosition));
            }
        }
    }

    interface TopicSelectionListener {
        void onTopicSelect(PracticeTestModel.SubList subList);
    }
}
