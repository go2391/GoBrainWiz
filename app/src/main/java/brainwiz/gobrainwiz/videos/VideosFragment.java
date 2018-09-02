package brainwiz.gobrainwiz.videos;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.VideoPlayFragment;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.api.model.VideoListModel;
import brainwiz.gobrainwiz.databinding.FragmentVideoCategoryBinding;
import brainwiz.gobrainwiz.databinding.FragmentVideosBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class VideosFragment extends BaseFragment {
    private FragmentVideosBinding bind;
    private FragmentActivity activity;
    private Context context;
    private VideosAdapter videosAdapter;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_videos, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getVideos();
    }

    private void initViews() {
        bind.recycleVideosList.setLayoutManager(new LinearLayoutManager(activity));
        videosAdapter = new VideosAdapter(context);
        bind.recycleVideosList.setAdapter(videosAdapter);
        playVideo(null);
    }

    private void getVideos() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }
        RetrofitManager.getRestApiMethods().getVideos().enqueue(new ApiCallback<VideoListModel>(activity) {
            @Override
            public void onApiResponse(Response<VideoListModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    List<VideoListModel.Video> videos = response.body().getData().getVideos();
                    videosAdapter.setVideosLists(videos.get(0).getVideosList());
                    videosAdapter.notifyDataSetChanged();
                    playVideo(videos.get(0).getVideosList().get(0));
                } else {

                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {

            }
        });
    }

    private void playVideo(VideoListModel.VideosList videosList) {

        getChildFragmentManager().beginTransaction().replace(R.id.video_frame, VideoPlayFragment.newInstance("Nju4GPd1xw8"), "").commit();
//        getChildFragmentManager().beginTransaction().replace(R.id.video_frame, VideoPlayFragment.newInstance(videosList.getYoutubeLink()), "");
    }
}
