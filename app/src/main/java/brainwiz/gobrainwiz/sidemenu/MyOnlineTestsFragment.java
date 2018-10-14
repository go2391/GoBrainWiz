package brainwiz.gobrainwiz.sidemenu;

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
import brainwiz.gobrainwiz.api.model.HistoryOnlineTestModel;
import brainwiz.gobrainwiz.api.model.TestsModel;
import brainwiz.gobrainwiz.databinding.FragmentMyTestsBinding;
import brainwiz.gobrainwiz.databinding.FragmentTestTopicBinding;
import brainwiz.gobrainwiz.practicetest.PracticeTestCategoryFragment;
import brainwiz.gobrainwiz.practicetest.PracticeTestTestsAdapter;
import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

import static brainwiz.gobrainwiz.practicetest.PracticeTestCategoryFragment.TOPIC_ID;

public class MyTestsFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentMyTestsBinding bind;
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

        View inflate = inflater.inflate(R.layout.fragment_my_tests, container, false);
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

//        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
//        (getArguments().getString(TOPIC_ID))
        RetrofitManager.getRestApiMethods().getPastTests(baseBodyMap).enqueue(new ApiCallback<HistoryOnlineTestModel>(activity) {
            @Override
            public void onApiResponse(Response<HistoryOnlineTestModel> response, boolean isSuccess, String message) {
//                dismissProgress();
                if (isSuccess) {
                    testTestsAdapter.setData(response.body().get().getTestList());
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

        testTestsAdapter = new PracticeTestTestsAdapter(context);
        bind.recycleMyTests.setAdapter(testTestsAdapter);
        testTestsAdapter.setTestListener(testListener);
//        bind.recycleTopics.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }

    private final PracticeTestTestsAdapter.TestListener testListener = new PracticeTestTestsAdapter.TestListener() {
        @Override
        public void onTestStart(int position) {


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
