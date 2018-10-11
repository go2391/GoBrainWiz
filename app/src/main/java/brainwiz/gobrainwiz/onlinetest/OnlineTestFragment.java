package brainwiz.gobrainwiz.onlinetest;

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
import brainwiz.gobrainwiz.MainActivity;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.OnlineTestModle;
import brainwiz.gobrainwiz.databinding.FragmentOnlineTestBinding;
import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class OnlineTestFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private CompaniesAdapter companiesAdapter;
    private FragmentOnlineTestBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_online_test, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();

        getOnlineTests();
        return inflate;
    }

    private void getOnlineTests() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
//        baseBodyMap.put("test_id", testID);
        RetrofitManager.getRestApiMethods().getCompanies(baseBodyMap).enqueue(new ApiCallback<OnlineTestModle>(activity) {
            @Override
            public void onApiResponse(Response<OnlineTestModle> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
                    companiesAdapter.setData(response.body().getData());
                    companiesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }


    private void initViews() {

        companiesAdapter = new CompaniesAdapter(context);
        bind.recycleTopics.setAdapter(companiesAdapter);
        companiesAdapter.setTestListener(testListener);
//        bind.recycleTopics.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }

    private final OnlineTestTestsAdapter.TestListener testListener = new OnlineTestTestsAdapter.TestListener() {
        @Override
        public void onTestStart(OnlineTestModle.TestList test) {

            Intent intent = new Intent(getActivity(), TestActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString(DURATION, test.getDuration());
            bundle.putString(ID, test.getBrainTestId());
            bundle.putString(CAT_ID, "");
            bundle.putBoolean(IS_COMPANY_TEST, true);
            bundle.putBoolean(IS_REVIEW, true);
            intent.putExtras(bundle);
            startActivity(intent);


        }

        @Override
        public void onReviewTest(int position) {

        }
    };

}
