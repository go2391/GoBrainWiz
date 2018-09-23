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
import brainwiz.gobrainwiz.databinding.InflateCompanyItemBinding;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompanyViewHolder> {

    Context context;

    public void setData(List<OnlineTestModle.Data> data) {
        this.data = data;
    }

    private List<OnlineTestModle.Data> data = new ArrayList<>();

    private brainwiz.gobrainwiz.onlinetest.OnlineTestTestsAdapter.TestListener testListener;


    public CompaniesAdapter(Context context) {

        this.context = context;
    }


    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_company_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        OnlineTestModle.Data subList = data.get(position);
        holder.bind.companyName.setText(subList.getCompanyName());
        holder.bind.companyImage.setUrl(subList.getImageLink());
        holder.bind.companyTests.setText(String.format(context.getString(R.string.tests), String.valueOf(subList.getList().size())));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setTestListener(OnlineTestTestsAdapter.TestListener testListener) {
        this.testListener = testListener;
    }

    public List<OnlineTestModle.Data> getData() {
        return data;
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final InflateCompanyItemBinding bind;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            RecyclerView.Adapter adapter = bind.recycleCompanies.getAdapter();

            if (adapter != null) {
                bind.recycleCompanies.setAdapter(null);
            } else {
                final OnlineTestModle.Data data = CompaniesAdapter.this.data.get(getAdapterPosition());
                OnlineTestTestsAdapter onlineTestTestsAdapter = new OnlineTestTestsAdapter(context, data.getList());
                bind.recycleCompanies.setAdapter(onlineTestTestsAdapter);
                onlineTestTestsAdapter.setTestListener(testListener);
            }

//            if (topicSelectionListener != null && adapterPosition != RecyclerView.NO_POSITION) {
//                topicSelectionListener.onTopicSelect(data.get(adapterPosition));
//            }
        }
    }

}
