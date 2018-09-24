package brainwiz.gobrainwiz.onlinetest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.MainActivity;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.PracticeTestModel;
import brainwiz.gobrainwiz.databinding.FragmentTestCategoryBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class OnlineTestCompaniesFragment extends BaseFragment {

    private FragmentTestCategoryBinding bind;
    private Context context;
    private PracticeTestTopicAdapter adapterArithmetic;
    private PracticeTestTopicAdapter adapterLogical;
    private PracticeTestTopicAdapter adapterVerbal;
    private FragmentActivity activity;

    public static final String TOPIC_ID = "TopicID";
    public static final String TOPIC_NAME = "TopicName";

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_test_category, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();

        getCategories();
        return inflate;
    }

    private void getCategories() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        RetrofitManager.getRestApiMethods().getPractiveTestCategories().enqueue(new ApiCallback<PracticeTestModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<PracticeTestModel> response, boolean isSuccess, String message) {
                if (isSuccess) {

                    PracticeTestModel.Data data = response.body().getData();
                    setDataToArithmetic(data.getArithmetic());

                    setDataToLogical(data.getOgicalReasoning());

                    setDataToVerbal(data.getOgicalReasoning());


//                    adapterVerbal = new PracticeTestTopicAdapter(context, data.getArithmetic().getSubLists());

                }
                dismissProgress();
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }

    private void setDataToArithmetic(PracticeTestModel.TopicModel arithmetic) {
        adapterArithmetic = new PracticeTestTopicAdapter(context, arithmetic.getSubLists());
        bind.testCategoryTests.setText(String.format(getString(R.string.tests), String.valueOf(arithmetic.getTestsCount())));
        bind.testCategoryTopics.setText(String.format(getString(R.string.topics), String.valueOf(arithmetic.getTopicCount())));
        adapterArithmetic.setTopicSelectionListener(topicSelectionListener);
    }

    private void setDataToLogical(PracticeTestModel.TopicModel logical) {
        adapterLogical = new PracticeTestTopicAdapter(context, logical.getSubLists());
        bind.testCategoryTests1.setText(String.format(getString(R.string.tests), String.valueOf(logical.getTestsCount())));
        bind.testCategoryTopics1.setText(String.format(getString(R.string.topics), String.valueOf(logical.getTopicCount())));
        adapterLogical.setTopicSelectionListener(topicSelectionListener);
    }

    private void setDataToVerbal(PracticeTestModel.TopicModel logical) {
        adapterVerbal = new PracticeTestTopicAdapter(context, logical.getSubLists());
        bind.testCategoryTests2.setText(String.format(getString(R.string.tests), String.valueOf(logical.getTestsCount())));
        bind.testCategoryTopics2.setText(String.format(getString(R.string.topics), String.valueOf(logical.getTopicCount())));
        adapterVerbal.setTopicSelectionListener(topicSelectionListener);
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
                        bind.recycleArithmetic.setAdapter(adapterArithmetic);
                        bind.arrowArithmetic.setSelected(true);
                    } else {
                        bind.recycleArithmetic.setAdapter(null);
                        bind.arrowArithmetic.setSelected(false);
                    }

                    break;

                case R.id.test_category2:

                    if (bind.recycleLogical.getAdapter() == null) {
                        bind.recycleLogical.setAdapter(adapterLogical);
                        bind.arrowLogical.setSelected(true);
                    } else {
                        bind.recycleLogical.setAdapter(null);
                        bind.arrowLogical.setSelected(false);
                    }
                    break;

                case R.id.test_category3:
                    if (bind.recycleVerbal.getAdapter() == null) {
                        bind.recycleVerbal.setAdapter(adapterVerbal);
                        bind.arrowVerbal.setSelected(true);
                    } else {
                        bind.recycleVerbal.setAdapter(null);
                        bind.arrowVerbal.setSelected(false);
                    }
                    break;
            }

        }
    };

    private final PracticeTestTopicAdapter.TopicSelectionListener topicSelectionListener = new PracticeTestTopicAdapter.TopicSelectionListener() {
        @Override
        public void onTopicSelect(PracticeTestModel.SubList subList) {
            Bundle bundle = new Bundle();
            bundle.putString(TOPIC_ID, subList.getTopicId());
            bundle.putString(TOPIC_NAME, subList.getTopicName());

            OnlineTestFragment topicsFragment = new OnlineTestFragment();
            topicsFragment.setArguments(bundle);
            ((MainActivity) activity).fragmentTransaction(topicsFragment);
        }
    };


}
