package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.VideoView;

import brainwiz.gobrainwiz.utils.SharedPrefUtils;

public class Splash extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        playVideo();
    }

    private void playVideo() {
        VideoView videoView = findViewById(R.id.splash_video_view);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer m) {
                try {
                    if (m.isPlaying()) {
                        m.stop();
                        m.release();
                        m = new MediaPlayer();
                    }
                    m.setLooping(false);
                    m.start();
                    m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (!SharedPrefUtils.getIsFirstLaunch(Splash.this)) {
                                if (SharedPrefUtils.getIsLogin(Splash.this)) {
                                    startActivity(new Intent(Splash.this, MainActivity.class));
                                } else {
                                    startActivity(new Intent(Splash.this, LoginActivity.class));
                                }
                                finish();
                            } else {
                                startActivity(new Intent(Splash.this, LandingActivity.class));
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
