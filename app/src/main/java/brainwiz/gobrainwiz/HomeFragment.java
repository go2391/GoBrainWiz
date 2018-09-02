package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.databinding.FragmentHomeBinding;
import brainwiz.gobrainwiz.practicetest.PracticeTestCategoryFragment;
import brainwiz.gobrainwiz.videos.VideoCategoryFragment;
import brainwiz.gobrainwiz.videos.VideosFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private Context context;
    private FragmentHomeBinding bind;
    private FragmentActivity activity;
    private List<DashBoardModel.Banner> bannerList;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews(inflate);
        getDashboardData();
        return inflate;
    }


    private void initViews(View inflate) {

        inflate.findViewById(R.id.tv_online_tests_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.tv_practice_tests_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.tv_test_series_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.tv_video_gallery_layout).setOnClickListener(clickListener);


//        bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, bannerList));

//Location of Media File

        playVideo();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.youtubeView_frame, new VideoPlayFragment(), VideoPlayFragment.class.getSimpleName()).commit();
        bind.youtubeViewImage.setUrl("http://i3.ytimg.com/vi/HL32sd9J9X0/maxresdefault.jpg");

        bind.youtubeViewImage.setOnClickListener(clickListener);
    }

    private void playVideo() {
        bind.videoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.demo));
        bind.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer m) {
                try {
                    if (m.isPlaying()) {
                        m.stop();
                        m.release();
                        m = new MediaPlayer();
                    }
                    m.setVolume(0f, 0f);
                    m.setLooping(true);
                    m.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDashboardData() {
        RetrofitManager.getRestApiMethods().getDashBoard().enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {
                Log.e("", response.toString());
                DashBoardModel.Data data = response.body().getData();
                bannerList = data.getBanners();
                bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, bannerList));

                bind.homeTestimonialsViewpager.setAdapter(new TestimonalAdapter(context, data.getTestinomials()));


            }

            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {
                Log.e("", call.toString());
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_video_gallery_layout:
                    ((MainActivity) activity).fragmentTransaction(new VideosFragment());
                    break;

                case R.id.tv_online_tests_layout:
                    break;

                case R.id.tv_practice_tests_layout:
                    ((MainActivity) activity).fragmentTransaction(new PracticeTestCategoryFragment());
                    break;

                case R.id.tv_test_series_layout:
                    break;
            }
//                startActivity(new Intent(getActivity(), YoutubeVideoActivity.class));
        }
    };

}
