package brainwiz.gobrainwiz.videos;

import android.content.Context;
import android.content.Intent;
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

import com.payu.magicretry.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import brainwiz.gobrainwiz.BaseActivity;
import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.VideoActivity;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.VideoListModel;
import brainwiz.gobrainwiz.databinding.FragmentVideoCategoryBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class VideoCategoryFragment extends BaseFragment {
    public static final String CATEGORY = "Category";
    private FragmentVideoCategoryBinding bind;
    private Context context;
    private FragmentActivity activity;
    private VideosTopicAdapter videosTopicAdapter;


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_video_category, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        bind.recycleVideosTopicList.setLayoutManager(new LinearLayoutManager(activity));
        videosTopicAdapter = new VideosTopicAdapter(context);
        videosTopicAdapter.setListener(videoTopicSelectedListener);
        bind.recycleVideosTopicList.setAdapter(videosTopicAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getVideos();
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

                    videosTopicAdapter.setVideosLists(videos);


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


    private HashMap<String, List<VideoListModel.VideosList>> getCategories(List<VideoListModel.Video> videos) {
        HashMap<String, List<VideoListModel.VideosList>> listHashMap = new LinkedHashMap<>();
        for (VideoListModel.Video video : videos) {
            listHashMap.put(video.getTopicName(), video.getVideosList());
        }
        return listHashMap;


    }


    VideosTopicAdapter.VideoTopicSelectedListener videoTopicSelectedListener = new VideosTopicAdapter.VideoTopicSelectedListener() {
        @Override
        public void onTopicSelected(int position) {

            Intent intent = new Intent(getActivity(), VideoActivity.class);
            intent.putExtra(CAT_ID, videosTopicAdapter.getVideosLists().get(position).getTopicName());

            startActivity(intent);


//            ((BaseActivity) getActivity()).fragmentTransaction(videosFragment, R.id.content_frame, true);

        }

    };


}
