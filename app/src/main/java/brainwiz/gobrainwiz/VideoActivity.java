package brainwiz.gobrainwiz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import brainwiz.gobrainwiz.videos.VideoCategoryFragment;
import brainwiz.gobrainwiz.videos.VideosFragment;

import static brainwiz.gobrainwiz.BaseFragment.CAT_ID;
import static brainwiz.gobrainwiz.BaseFragment.YOUTUBE_LINK;

public class VideoActivity extends BaseActivity {
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Video Player");
        setSupportActionBar(toolbar);


        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setTitle(getIntent().getStringExtra(VideoCategoryFragment.CAT_ID));

        VideosFragment videosFragment = new VideosFragment();


        Bundle args = getIntent().getExtras();

        args.putString(CAT_ID, getIntent().getStringExtra(CAT_ID));
        args.putString(YOUTUBE_LINK, getIntent().getStringExtra(YOUTUBE_LINK));
        videosFragment.setArguments(args);
        fragmentTransaction(videosFragment, R.id.video_frame);
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }


    protected void showProgress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        View inflate = this.getLayoutInflater().inflate(R.layout.progress_dialog_layout, null);
        builder.setView(inflate);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        configureProgress(alertDialog);
        alertDialog.show();

    }

    public void dismissProgress() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


}
