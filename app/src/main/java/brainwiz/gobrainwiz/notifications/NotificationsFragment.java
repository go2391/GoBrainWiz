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
import brainwiz.gobrainwiz.FullscreenActivity;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.VideoActivity;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.NotificationsModel;
import brainwiz.gobrainwiz.databinding.FragmentNotificationsBinding;
import brainwiz.gobrainwiz.utils.AppUtils;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
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

        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
//        (getArguments().getString(TOPIC_ID))
        RetrofitManager.getRestApiMethods().getNotifications(baseBodyMap).enqueue(new ApiCallback<NotificationsModel>(activity) {
            @Override
            public void onApiResponse(Response<NotificationsModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
                    notificationsAdapter.setData(response.body().getData());
                    notificationsAdapter.notifyDataSetChanged();
                    SharedPrefUtils.putData(context, SharedPrefUtils.NOTIFICATION_COUNT, "0");
                    getActivity().invalidateOptionsMenu();
                    bind.emptyView.setVisibility(notificationsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }

    private void updateNotifications(final NotificationsModel.Datum datum) {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("android_id", AppUtils.getDeviceID(getActivity()));
        hashMap.put("token", SharedPrefUtils.getToken(getActivity()));
        hashMap.put("used_id", SharedPrefUtils.getStudentID(getActivity()));
        hashMap.put("notification_id", datum.getNotificationId());


        HashMap<String, String> baseBodyMap = getBaseBodyMap();
//        (getArguments().getString(TOPIC_ID))
        RetrofitManager.getRestApiMethods().updateNotifications(hashMap).enqueue(new ApiCallback<BaseModel>(activity) {
            @Override
            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {

                    switch (datum.getType()) {
                        case 1:
                            openWeeklySchedule(datum.getImage());
                            break;
                        case 2:
                            Intent intent = new Intent(getActivity(), VideoActivity.class);
                            intent.putExtra(CAT_ID, datum.getCatname());
                            intent.putExtra(YOUTUBE_LINK, datum.getUrl());

                            startActivity(intent);
                            break;

                        case 4:
//                            imgresourse = R.drawable.online_tests;
                            break;
                        case 3:
//                            imgresourse = R.drawable.practice_tests;
                            break;
                        default:
//                            imgresourse = R.drawable.logo;
                            break;
                    }


                    notificationsAdapter.getData().remove(datum);
                    notificationsAdapter.notifyDataSetChanged();

//                    notificationsAdapter.setData(response.body().getData());
//                    notificationsAdapter.notifyDataSetChanged();
//                    SharedPrefUtils.putData(context, SharedPrefUtils.NOTIFICATION_COUNT, "0");
//                    getActivity().invalidateOptionsMenu();
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }


    public void openWeeklySchedule(String imageUrl) {
        Intent intent = new Intent(activity, FullscreenActivity.class);
        intent.putExtra("URL", imageUrl);
        startActivity(intent);
    }

    private void initViews() {

        notificationsAdapter = new NotificationsAdapter(context);
        bind.recycleNotifications.setAdapter(notificationsAdapter);


        bind.recycleNotifications.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        notificationsAdapter.setNotificationListener(new NotificationsAdapter.NotificationListener() {
            @Override
            public void onViewNotification(int position) {
                updateNotifications(notificationsAdapter.getData().get(position));
            }
        });

//        notificationsAdapter.set(testListener);
//        bind.recycleTopics.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
    }


}
