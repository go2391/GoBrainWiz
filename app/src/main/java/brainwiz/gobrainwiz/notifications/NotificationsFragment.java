package brainwiz.gobrainwiz.notifications;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.HistoryPractiseTestModel;
import brainwiz.gobrainwiz.api.model.NotificationsModel;
import brainwiz.gobrainwiz.databinding.FragmentNotificationsBinding;
import brainwiz.gobrainwiz.sidemenu.PractiseTestHistoryAdapter;
import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class NotificationsFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private NotificationsAdapter notificationsAdapter;
    FragmentNotificationsBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_notifications, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();

        getNotifications();
        return inflate;
    }

    private void getNotifications() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

//        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
//        (getArguments().getString(TOPIC_ID))
        RetrofitManager.getRestApiMethods().getNotifications(baseBodyMap).enqueue(new ApiCallback<NotificationsModel>(activity) {
            @Override
            public void onApiResponse(Response<NotificationsModel> response, boolean isSuccess, String message) {
//                dismissProgress();
                if (isSuccess) {
                    notificationsAdapter.setData(response.body().getData());
                    notificationsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }


    private void initViews() {

        notificationsAdapter = new NotificationsAdapter(context);
        bind.recycleNotifications.setAdapter(notificationsAdapter);


        bind.recycleNotifications.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

//        notificationsAdapter.set(testListener);
//        bind.recycleTopics.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }


}
