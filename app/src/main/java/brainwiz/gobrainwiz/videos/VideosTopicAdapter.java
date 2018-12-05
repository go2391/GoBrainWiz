package brainwiz.gobrainwiz.videos;

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
import brainwiz.gobrainwiz.api.model.VideoListModel;
import brainwiz.gobrainwiz.databinding.InflateVideoItemBinding;
import brainwiz.gobrainwiz.databinding.InflateVideoTopicItemBinding;

public class VideosTopicAdapter extends RecyclerView.Adapter<VideosTopicAdapter.VideoViewHolder> {

    Context context;
    List<VideoListModel.Video> videosLists = new ArrayList<>();
    private VideoTopicSelectedListener listener;


    public List<VideoListModel.Video> getVideosLists() {
        return videosLists;
    }

    public void setVideosLists(List<VideoListModel.Video> videosLists) {
        this.videosLists = videosLists;
        notifyDataSetChanged();
    }

    public VideosTopicAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_video_topic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoListModel.Video video = videosLists.get(position);
        holder.bind.videoItemTitle.setText(video.getTopicName());
        holder.bind.videoItemDesc.setText(video.getVideosList().size() + " Videos");
    }

    @Override
    public int getItemCount() {
        return videosLists.size();
    }

    public void setListener(VideoTopicSelectedListener listener) {
        this.listener = listener;
    }

    public VideoTopicSelectedListener getListener() {
        return listener;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateVideoTopicItemBinding bind;

        public VideoViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onTopicSelected(getAdapterPosition());
            }
        }
    }

    interface VideoTopicSelectedListener {
        void onTopicSelected(int position);
    }
}
