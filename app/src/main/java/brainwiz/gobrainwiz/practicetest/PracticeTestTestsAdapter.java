package brainwiz.gobrainwiz.practicetest;

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
import brainwiz.gobrainwiz.api.model.TestsModel;
import brainwiz.gobrainwiz.databinding.InflateTestItemBinding;

public class PracticeTestTestsAdapter extends RecyclerView.Adapter<PracticeTestTestsAdapter.TestViewHolder> {

    Context context;

    private TestListener testListener;
    private List<TestsModel.TestList> data = new ArrayList<>();


    public PracticeTestTestsAdapter(Context context) {

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

        TestsModel.TestList testList = data.get(position);
        holder.bind.testItemTitle.setText(testList.getTestName());
        holder.bind.testQuestions.setText(String.format(context.getString(R.string.questions), testList.getCountque()));
        holder.bind.testMins.setText(String.format(context.getString(R.string.mins), testList.getTestTime()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<TestsModel.TestList> data) {

        this.data = data;
    }

    public List<TestsModel.TestList> getData() {
        return data;
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateTestItemBinding bind;

        public TestViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            bind.testTopicItemStart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (testListener != null) {
                testListener.onTestStart(getAdapterPosition());
            }
        }
    }

    interface TestListener {
        void onTestStart(int position);

        void onReviewTest(int position);
    }

}
