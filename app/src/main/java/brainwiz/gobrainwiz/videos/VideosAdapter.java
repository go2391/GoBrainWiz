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

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    Context context;
    List<VideoListModel.VideosList> videosLists = new ArrayList<>();


    public List<VideoListModel.VideosList> getVideosLists() {
        return videosLists;
    }

    public void setVideosLists(List<VideoListModel.VideosList> videosLists) {
        this.videosLists = videosLists;
    }

    public VideosAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoListModel.VideosList video = videosLists.get(position);
        holder.bind.videoItemThumb.setUrl(video.getVImage());
        holder.bind.videoItemTitle.setText(video.getVideoTitle());
        holder.bind.videoItemDesc.setText(video.getVideoDesc());
    }

    @Override
    public int getItemCount() {
        return videosLists.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        InflateVideoItemBinding bind;

        public VideoViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
