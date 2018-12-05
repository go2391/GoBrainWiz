package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayFragment extends com.google.android.youtube.player.YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private static final String DEVELOPER_KEY = "AIzaSyBIdEzp4gNeDAPpLzPraaBs4fDtzmOwDbY";
    public static final String VIDEO_ID = "video_id";

    private String mVideoId = "HL32sd9J9X0";
    private YouTubePlayerView youTubePlayerView;
    private FragmentActivity activity;
    private YouTubePlayer activePlayer;
    //    private ViewDataBinding dataBinding;


    public static VideoPlayFragment newInstance(String videoID) {

        Bundle args = new Bundle();
        args.putString(VIDEO_ID, videoID);
        VideoPlayFragment fragment = new VideoPlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        init();
    }

    private void init() {
        mVideoId = getArguments().getString(VIDEO_ID);
        initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
            }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!wasRestored) {
                    activePlayer.loadVideo(mVideoId, 0);

                }
            }
        });
    }

    /* @Override
     public void onYouTubeVideoPaused() {
         activePlayer.pause();
     }
 */
   /* @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_youtube_video, viewGroup, false);
        initViews(inflate);
        return inflate;
    }*/

    private void initViews(View view) {

//        YouTubePlayerView activePlayer = (YouTubePlayerView) view.findViewById(R.id.youtubeView);
        mVideoId = "HL32sd9J9X0";
//        init();

        // Initializing video player with developer key


    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(activity, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = "Initialization failed...!";
            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(mVideoId);

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
//            youTubePlayerView.initialize(DEVELOPER_KEY, this);
        }
    }

}