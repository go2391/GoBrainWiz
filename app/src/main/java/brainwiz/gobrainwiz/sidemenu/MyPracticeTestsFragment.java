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
import brainwiz.gobrainwiz.databinding.FragmentMyTestsBinding;
import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class MyOnlineTestsFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentMyTestsBinding bind;
    private OnlineTestHistoryAdapter testTestsAdapter;

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
                    testTestsAdapter.setData(response.body().getData());
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

        testTestsAdapter = new OnlineTestHistoryAdapter(context);
        bind.recycleMyTests.setAdapter(testTestsAdapter);
        testTestsAdapter.setTestListener(testListener);
//        bind.recycleTopics.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }

    private final OnlineTestHistoryAdapter.TestListener testListener = new OnlineTestHistoryAdapter.TestListener() {

        @Override
        public void onReviewTest(int position) {
            Intent intent = new Intent(getActivity(), TestActivity.class);
            Bundle bundle = new Bundle();
            HistoryOnlineTestModel.TestHistory testList = testTestsAdapter.getData().get(position);
            bundle.putString(ID, testList.getTestId());
            bundle.putString(CAT_ID, "");
            bundle.putBoolean(IS_COMPANY_TEST, true);
            bundle.putBoolean(IS_REVIEW, true);

            bundle.putString(DURATION, String.valueOf(0));
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };


}
