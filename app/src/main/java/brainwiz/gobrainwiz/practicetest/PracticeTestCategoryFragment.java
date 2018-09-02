package brainwiz.gobrainwiz.practicetest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.databinding.FragmentTestCategoryBinding;
import brainwiz.gobrainwiz.databinding.FragmentVideoCategoryBinding;

public class PracticeTestCategoryFragment extends BaseFragment {

    private FragmentTestCategoryBinding bind;
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_test_category, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        bind.testCategory1.setOnClickListener(clickListener);
        bind.testCategory2.setOnClickListener(clickListener);
        bind.testCategory3.setOnClickListener(clickListener);

        bind.recycleArithmetic.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
        bind.recycleLogical.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
        bind.recycleVerbal.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_category1:
                    if (bind.recycleArithmetic.getAdapter() == null) {
                        bind.recycleArithmetic.setAdapter(new PracticeTestTopicAdapter(context));
                    } else {
                        bind.recycleArithmetic.setAdapter(null);
                    }

                    break;

                case R.id.test_category2:

                    if (bind.recycleLogical.getAdapter() == null) {
                        bind.recycleLogical.setAdapter(new PracticeTestTopicAdapter(context));
                    } else {
                        bind.recycleLogical.setAdapter(null);
                    }
                    break;

                case R.id.test_category3:
                    if (bind.recycleVerbal.getAdapter() == null) {
                        bind.recycleVerbal.setAdapter(new PracticeTestTopicAdapter(context));
                    } else {
                        bind.recycleVerbal.setAdapter(null);
                    }
                    break;
            }

        }
    };


}
