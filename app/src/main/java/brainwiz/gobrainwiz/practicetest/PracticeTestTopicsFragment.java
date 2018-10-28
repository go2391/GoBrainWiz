package brainwiz.gobrainwiz.practicetest;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.TestsModel;
import brainwiz.gobrainwiz.databinding.FragmentTestTopicBinding;
import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

import static brainwiz.gobrainwiz.practicetest.PracticeTestCategoryFragment.TOPIC_ID;

public class PracticeTestTopicsFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentTestTopicBinding bind;
    private PracticeTestTestsAdapter testTestsAdapter;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_test_topic, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();

        getTests();
        return inflate;
    }

    private void getTests() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
        baseBodyMap.put("topic_id", getArguments().getString(TOPIC_ID));
        RetrofitManager.getRestApiMethods().getTests(baseBodyMap).enqueue(new ApiCallback<TestsModel>(activity) {
            @Override
            public void onApiResponse(Response<TestsModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
                    testTestsAdapter.setData(response.body().getData().getTestList());
                    testTestsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }


    private void initViews() {

        bind.topicTitle.setText(getArguments().getString(PracticeTestCategoryFragment.TOPIC_NAME));
        testTestsAdapter = new PracticeTestTestsAdapter(context);
        bind.recycleTopics.setAdapter(testTestsAdapter);
        testTestsAdapter.setTestListener(testListener);
//        bind.recycleTopics.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }

    private final PracticeTestTestsAdapter.TestListener testListener = new PracticeTestTestsAdapter.TestListener() {
        @Override
        public void onTestStart(int position) {

            Intent intent = new Intent(getActivity(), TestActivity.class);
            Bundle bundle = new Bundle();
            TestsModel.TestList testList = testTestsAdapter.getData().get(position);
            bundle.putString(ID, testList.getTestId());
            bundle.putString(CAT_ID, "");
            bundle.putBoolean(IS_COMPANY_TEST, false);
            bundle.putBoolean(IS_REVIEW, false);

            bundle.putString(DURATION, String.valueOf(parseTimeToMinutes(testList.getTestTime())));
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onReviewTest(int position) {
            Intent intent = new Intent(getActivity(), TestActivity.class);
            Bundle bundle = new Bundle();
            TestsModel.TestList testList = testTestsAdapter.getData().get(position);
            bundle.putString(ID, testList.getTestId());
            bundle.putString(CAT_ID, "");
            bundle.putBoolean(IS_COMPANY_TEST, false);
            bundle.putBoolean(IS_REVIEW, true);

            bundle.putString(DURATION, String.valueOf(parseTimeToMinutes(testList.getTestTime())));
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };

    public static int parseTimeToMinutes(String hourFormat) {

        int minutes = 0;
        String[] split = hourFormat.split(":");

        try {

            minutes += Double.parseDouble(split[0]) * 60;
            minutes += Double.parseDouble(split[1]);
            minutes += Double.parseDouble(split[2]) / 60;
            return minutes;

        } catch (Exception e) {
            return -1;
        }

    }


}
