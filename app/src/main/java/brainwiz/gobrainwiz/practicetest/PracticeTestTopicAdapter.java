package brainwiz.gobrainwiz.practicetest;

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
import brainwiz.gobrainwiz.databinding.InflateTestTopicItemBinding;
import brainwiz.gobrainwiz.databinding.InflateVideoItemBinding;

public class PracticeTestTopicAdapter extends RecyclerView.Adapter<PracticeTestTopicAdapter.VideoViewHolder> {

    Context context;
    List<VideoListModel.VideosList> videosLists = new ArrayList<>();


    public List<VideoListModel.VideosList> getVideosLists() {
        return videosLists;
    }

    public void setVideosLists(List<VideoListModel.VideosList> videosLists) {
        this.videosLists = videosLists;
    }

    public PracticeTestTopicAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_test_topic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
//        VideoListModel.VideosList video = videosLists.get(position);
//        holder.bind.testTopicItemTitle.set(video.getVImage());
//        holder.bind.videoItemTitle.setText(video.getVideoTitle());
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateTestTopicItemBinding bind;

        public VideoViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
