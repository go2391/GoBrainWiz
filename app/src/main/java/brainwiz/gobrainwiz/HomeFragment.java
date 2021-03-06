package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.databinding.FragmentHomeBinding;
import brainwiz.gobrainwiz.onlinetest.OnlineTestFragment;
import brainwiz.gobrainwiz.practicetest.PracticeTestCategoryFragment;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import brainwiz.gobrainwiz.videos.VideoCategoryFragment;
import brainwiz.gobrainwiz.videos.VideosFragment;
import retrofit2.Response;

import static brainwiz.gobrainwiz.utils.SharedPrefUtils.NOTIFICATION_COUNT;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.PROFILE_IMAGE;

public class HomeFragment extends BaseFragment {

    private Context context;
    private FragmentHomeBinding bind;
    private FragmentActivity activity;
    private List<DashBoardModel.Banner> bannerList;
    private DashBoardModel.Data data;

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

    @Override
    public void onResume() {
        super.onResume();
//        AppInstructionFragment appInstructionFragment = new AppInstructionFragment();
//        appInstructionFragment.show(getChildFragmentManager(), "new");
    }

    private void initViews(View inflate) {

        inflate.findViewById(R.id.tv_online_tests_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.tv_practice_tests_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.tv_weekly_schedule_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.tv_video_gallery_layout).setOnClickListener(clickListener);
        inflate.findViewById(R.id.share_arrow).setOnClickListener(clickListener);


//        bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, bannerList));

//Location of Media File

        bind.videoPlayIcon.setOnClickListener(clickListener);
        playVideo();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.youtubeView_frame, new VideoPlayFragment(), VideoPlayFragment.class.getSimpleName()).commit();

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
                    AudioManager mAm = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                    mAm.setStreamMute(AudioManager.STREAM_MUSIC, false);
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
        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
        RetrofitManager.getRestApiMethods().getDashBoard(baseBodyMap).enqueue(new ApiCallback<DashBoardModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<DashBoardModel> response, boolean isSuccess, String message) {
                if (isSuccess) {


                    Log.e("", response.toString());
                    data = response.body().getData();
                    bannerList = data.getBanners();
                    bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, bannerList));

                    bind.homeTestimonialsViewpager.setAdapter(new TestimonalAdapter(context, data.getTestinomials()));


                    SharedPrefUtils.putData(getActivity(), PROFILE_IMAGE, data.getProfile_link());
                    SharedPrefUtils.putData(getActivity(), NOTIFICATION_COUNT, data.getNotification_count());
                }
                dismissProgress();

            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                Log.e("", message);
                dismissProgress();

            }


        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_video_gallery_layout:
                    ((MainActivity) activity).fragmentTransaction(new VideoCategoryFragment());
                    break;

                case R.id.tv_online_tests_layout:
                    ((MainActivity) activity).fragmentTransaction(new OnlineTestFragment());
                    break;

                case R.id.tv_practice_tests_layout:
                    ((MainActivity) activity).fragmentTransaction(new PracticeTestCategoryFragment());
                    break;

                case R.id.tv_weekly_schedule_layout:
                    openWeeklySchedule(data.getWeekSchedule().getImage());
                    break;
                case R.id.video_play_icon:
                    Intent intent1 = new Intent(getActivity(), YoutubeVideoActivity.class);
                    intent1.putExtra(YoutubeVideoActivity.VIDEO_ID, "DqROxv0pDDA");
                    startActivity(intent1);
                    break;
                case R.id.review_arrow:
                    break;
                case R.id.share_arrow:
                    ((MainActivity) getActivity()).share();
                    break;
            }
        }
    };

    public void openWeeklySchedule(String imageUrl) {
        Intent intent = new Intent(activity, FullscreenActivity.class);
        intent.putExtra("URL", imageUrl);
        startActivity(intent);
    }
}
