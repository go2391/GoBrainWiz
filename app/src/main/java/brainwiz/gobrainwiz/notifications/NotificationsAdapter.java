package brainwiz.gobrainwiz.notifications;

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
import brainwiz.gobrainwiz.api.model.HistoryPractiseTestModel;
import brainwiz.gobrainwiz.api.model.NotificationsModel;
import brainwiz.gobrainwiz.databinding.InflateNotificationsItemBinding;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.TestViewHolder> {

    Context context;

    private NotificationListener notificationListener;
    private List<NotificationsModel.Datum> data = new ArrayList<>();


    public NotificationsAdapter(Context context) {

        this.context = context;
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_notifications_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {

        NotificationsModel.Datum datum = data.get(position);
        holder.bind.notificationItemTitle.setText(datum.getTitle());
        holder.bind.notificationDesc.setText(datum.getMessage());
        holder.bind.notificationItemTime.setText(datum.getTimeUpdate());

        int imgresourse;
        switch (datum.getType()) {
            case 1:
                imgresourse = R.drawable.ic_calendar;
                break;
            case 2:
                imgresourse = R.drawable.video_gallery;
                break;

            case 4:
                imgresourse = R.drawable.online_tests;
                break;
            case 3:
                imgresourse = R.drawable.practice_tests;
                break;
            default:
                imgresourse = R.drawable.logo;
                break;
        }
        holder.bind.notificationItemImage.setImageResource(imgresourse);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<NotificationsModel.Datum> data) {

        this.data = data;
    }

    public List<NotificationsModel.Datum> getData() {
        return data;
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final InflateNotificationsItemBinding bind;

        public TestViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            bind.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (notificationListener != null) {
                notificationListener.onViewNotification(getAdapterPosition());
            }
        }
    }

    public interface NotificationListener {

        void onViewNotification(int position);
    }

}
