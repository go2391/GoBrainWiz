package brainwiz.gobrainwiz.test;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.FragmentOverviewBinding;
import brainwiz.gobrainwiz.databinding.FragmentTestContentBinding;

import static brainwiz.gobrainwiz.BaseFragment.IS_REVIEW;

public class OverViewFragment extends android.support.v4.app.DialogFragment {


    FragmentOverviewBinding contentBinding;
    private List<QuestionNumber> data;

    public QuestionNoAdapter.QuestionListener getQuestionListener() {
        return questionListener;
    }

    public void setQuestionListener(QuestionNoAdapter.QuestionListener questionListener) {
        this.questionListener = questionListener;
    }

    private QuestionNoAdapter.QuestionListener questionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_overview, container, false);
        contentBinding = DataBindingUtil.bind(inflate);
        init();
        return inflate;
    }

    private void init() {

        contentBinding.overViewRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        QuestionNoAdapter questionNoAdapter = new QuestionNoAdapter(getActivity());
        questionNoAdapter.setOptions(data);
        contentBinding.overViewRecycler.setAdapter(questionNoAdapter);
        questionNoAdapter.setListener(questionListenerNew);

    }

    QuestionNoAdapter.QuestionListener questionListenerNew = new QuestionNoAdapter.QuestionListener() {
        @Override
        public void onOptionSelected(int position) {
            questionListener.onOptionSelected(position);
            dismiss();
        }
    };


    public void setData(List<QuestionNumber> data) {

        this.data = data;
    }

}
