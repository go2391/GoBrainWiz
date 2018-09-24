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
import brainwiz.gobrainwiz.api.model.OnlineTestModle;
import brainwiz.gobrainwiz.databinding.InflateTestItemBinding;

public class OnlineTestTestsAdapter extends RecyclerView.Adapter<OnlineTestTestsAdapter.TestViewHolder> {

    Context context;

    private TestListener testListener;
    private List<OnlineTestModle.TestList> data = new ArrayList<>();


    public OnlineTestTestsAdapter(Context context, List<OnlineTestModle.TestList> list) {

        this.context = context;
        data = list;
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

        OnlineTestModle.TestList testList = data.get(position);
        holder.bind.testItemTitle.setText(testList.getTestName());
        holder.bind.testQuestions.setText(String.format(context.getString(R.string.questions), testList.getCountQ()));
        holder.bind.testMins.setText(String.format(context.getString(R.string.mins), testList.getDuration()));
    }

    @Override
    public int getItemCount() {
        return data.size();
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
                testListener.onTestStart(data.get(getAdapterPosition()));
            }
        }
    }

    interface TestListener {
        void onTestStart(OnlineTestModle.TestList test);

        void onReviewTest(int position);
    }

}
