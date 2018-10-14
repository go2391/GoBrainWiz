package brainwiz.gobrainwiz.sidemenu;

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
import brainwiz.gobrainwiz.api.model.HistoryOnlineTestModel;
import brainwiz.gobrainwiz.databinding.InflateTestItemBinding;

public class OnlineTestHistoryAdapter extends RecyclerView.Adapter<OnlineTestHistoryAdapter.TestViewHolder> {

    Context context;

    private TestListener testListener;
    private List<HistoryOnlineTestModel.TestHistory> data = new ArrayList<>();


    public OnlineTestHistoryAdapter(Context context) {

        this.context = context;
    }

    public void setTestListener(TestListener testListener) {
        this.testListener = testListener;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {

        HistoryOnlineTestModel.TestHistory testList = data.get(position);
        holder.bind.testItemTitle.setText(testList.getTestName());
        holder.bind.testQuestions.setText(String.format(context.getString(R.string.sets), testList.getTotalSets()));
        holder.bind.testMins.setText(String.format(context.getString(R.string.mins), ""));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HistoryOnlineTestModel.TestHistory> data) {

        this.data = data;
    }

    public List<HistoryOnlineTestModel.TestHistory> getData() {
        return data;
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateTestItemBinding bind;

        public TestViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            bind.testTopicItemStart.setText(context.getString(R.string.review));
            bind.testTopicItemStart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (testListener != null) {
                testListener.onReviewTest(getAdapterPosition());
            }
        }
    }

    public interface TestListener {

        void onReviewTest(int position);
    }

}
