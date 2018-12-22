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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.VideoPlayFragment;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.VideoListModel;
import brainwiz.gobrainwiz.databinding.FragmentVideosBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class VideosFragment extends BaseFragment {
    private FragmentVideosBinding bind;
    private FragmentActivity activity;
    private Context context;
    private VideosAdapter videosAdapter;
    private HashMap<String, List<VideoListModel.VideosList>> categories = new LinkedHashMap<>();
    private ArrayAdapter adapter;
    private List<String> categoryNames = new ArrayList<>();
    private String categoryName;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        videosAdapter.setListener(videoSelectedListener);
        bind.recycleVideosList.setAdapter(videosAdapter);

        categoryName = getArguments().getString(CAT_ID);
//        bind.videosTopic.setText(categoryName);
//        bind.videosCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                categoryName = categoryNames.get(position);
//                loadList(categories.get(categoryName));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }

    private void getVideos() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }
        showProgress();
        RetrofitManager.getRestApiMethods().getVideos().enqueue(new ApiCallback<VideoListModel>(activity) {
            @Override
            public void onApiResponse(Response<VideoListModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    List<VideoListModel.Video> videos = response.body().getData().getVideos();

                    categories = getCategories(videos);
//                    categoryNames = new ArrayList<>(categories.keySet());
//                    adapter = new ArrayAdapter<>(activity, R.layout.inflate_spinner_item, R.id.spinner_title, categoryNames);
//                    bind.videosCategories.setAdapter(adapter);

                    if (!videos.isEmpty()) {
                        loadList(categories.get(categoryName));
                    }

                } else {

                }
                dismissProgress();
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }

    private void loadList(List<VideoListModel.VideosList> videosList) {

        videosAdapter.setVideosLists(videosList);


        if (!videosList.isEmpty()) {

            if (getArguments().containsKey(YOUTUBE_LINK) && !getArguments().getString(YOUTUBE_LINK).isEmpty()) {
                playVideo(getArguments().getString(YOUTUBE_LINK));
            } else {
                videosAdapter.setSelectedPosition(0);
                playVideo(videosList.get(0).getYoutubeLink());
            }
        }
        videosAdapter.notifyDataSetChanged();


    }

    private HashMap<String, List<VideoListModel.VideosList>> getCategories(List<VideoListModel.Video> videos) {
        HashMap<String, List<VideoListModel.VideosList>> listHashMap = new LinkedHashMap<>();
        for (VideoListModel.Video video : videos) {
            listHashMap.put(video.getTopicName(), video.getVideosList());
        }
        return listHashMap;


    }

    private void playVideo(String youtubeLink) {


//        String youtubeLink = videosList.getYoutubeLink();
        youtubeLink = youtubeLink.substring(youtubeLink.lastIndexOf("/") + 1, youtubeLink.length());

        getChildFragmentManager().beginTransaction().replace(R.id.video_frame, VideoPlayFragment.newInstance(youtubeLink), "").commit();
//        getChildFragmentManager().beginTransaction().replace(R.id.video_frame, VideoPlayFragment.newInstance(videosList.getYoutubeLink()), "");
    }

    VideosAdapter.VideoSelectedListener videoSelectedListener = new VideosAdapter.VideoSelectedListener() {
        @Override
        public void onVideoSelected(int position) {
            playVideo(videosAdapter.getVideosLists().get(position).getYoutubeLink());
        }
    };
}
