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
import brainwiz.gobrainwiz.api.model.OnlineTestSetModel;
import brainwiz.gobrainwiz.databinding.InflateInstructionTestTypeBinding;

public class InstructionTestTypeAdapter extends RecyclerView.Adapter<InstructionTestTypeAdapter.TestTypeHolder> {

    private List<OnlineTestSetModel.TestSet> options = new ArrayList<>();

    private Context context;
    private int selectedOption = -1;
    private TypeListener listener;


    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public InstructionTestTypeAdapter(Context context, List<OnlineTestSetModel.TestSet> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public TestTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestTypeHolder(LayoutInflater.from(context).inflate(R.layout.inflate_instruction_test_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestTypeHolder holder, int position) {
        OnlineTestSetModel.TestSet testSet = options.get(position);
        holder.bind.testType.setText(testSet.getCatName());
        holder.bind.testType.setChecked(testSet.isSelected());
        holder.bind.testType.setEnabled(!testSet.isCompleted());
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void setListener(TypeListener listener) {
        this.listener = listener;
    }

    public TypeListener getListener() {
        return listener;
    }


    public List<OnlineTestSetModel.TestSet> getOptions() {
        return options;
    }

    public void setOptions(List<OnlineTestSetModel.TestSet> options) {
        this.options = options;
    }

    class TestTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateInstructionTestTypeBinding bind;

        public TestTypeHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            bind.testType.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {

                if (options.get(getAdapterPosition()).isCompleted()) {
                    return;
                }
                if (selectedOption != -1) {
                    options.get(selectedOption).setSelected(false);
                }

                selectedOption = getAdapterPosition();
                if (listener != null) {
                    listener.onOptionSelected(selectedOption);
                }
                OnlineTestSetModel.TestSet testSet = options.get(selectedOption);
                testSet.setSelected(!testSet.isSelected());
                notifyDataSetChanged();
            }
        }
    }

    interface TypeListener {
        void onOptionSelected(int position);
    }
}
