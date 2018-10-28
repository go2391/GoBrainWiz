package brainwiz.gobrainwiz.test;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import brainwiz.gobrainwiz.BaseActivity;
import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.MainActivity;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.TimerService;
import brainwiz.gobrainwiz.onlinetest.InstructionsFragment;
import brainwiz.gobrainwiz.utils.DDAlerts;

public class TestActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = TestActivity.class.getSimpleName();
    private boolean isCompanyTest;
    private TimerService timerService;
    private boolean serviceBound;

    private TextView timerTextView;

    // Handler to update the UI every second when the timer is running
    private final Handler mUpdateTimeHandler = new UIUpdateHandler(this);

    // Message type for the handler
    private final static int MSG_UPDATE_TIME = 0;
    private long targetTime;
    private View submitView;
    private AlertDialog alertDialog;
    private boolean isReview;

    private long currentRunningTime;

    public long getTargetTime() {
        return targetTime;
    }

    public long getCurrentRunningTime() {
        return currentRunningTime;
    }

    public void setTargetTime(long targetTime) {
        this.targetTime = targetTime;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting and binding service");
        }
        Intent i = new Intent(this, TimerService.class);
        startService(i);
        bindService(i, mConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateUIStopRun();
        if (serviceBound) {
            // If a timer is active, foreground the service, otherwise kill the service
            if (timerService.isTimerRunning()) {
                timerService.foreground();
            } else {
                stopService(new Intent(this, TimerService.class));
            }
            // Unbind the service
            unbindService(mConnection);
            serviceBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        submitView = findViewById(R.id.submit);
        submitView.setOnClickListener(clickListener);

        timerTextView = findViewById(R.id.timer);
        Bundle extras = getIntent().getExtras();
        isCompanyTest = extras.getBoolean(BaseFragment.IS_COMPANY_TEST);
        isReview = extras.getBoolean(BaseFragment.IS_REVIEW);
        String string = extras.getString(BaseFragment.DURATION);
        targetTime = Integer.parseInt(string) * 60;

        if (isCompanyTest) {
            fragmentTransaction(InstructionsFragment.getInstance(extras), R.id.content_frame, false);
        } else {
            fragmentTransaction(TestQuestionFragment.getInstance(extras.getString(BaseFragment.ID), extras.getBoolean(BaseFragment.IS_REVIEW)), R.id.content_frame, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void startTest() {
        submitView.setVisibility(View.VISIBLE);
        if (serviceBound && !timerService.isTimerRunning()) {
            startTimer();
        }

    }

    public void runTimer() {
        if (serviceBound && !timerService.isTimerRunning()) {
            startTimer();
        } else if (serviceBound && timerService.isTimerRunning()) {
            stopTimer();
        }
    }

    private void stopTimer() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Stopping timer");
        }
        timerService.stopTimer();
        updateUIStopRun();
    }

    private void startTimer() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting timer");
        }
        timerService.startTimer();
        updateUIStartRun();
    }

    /**
     * Updates the UI when a run starts
     */
    private void updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
//        timerButton.setText(R.string.timer_stop_button);
    }

    /**
     * Updates the UI when a run stops
     */
    private void updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
//        timerButton.setText(R.string.timer_start_button);
    }


    /**
     * Updates the timer readout in the UI; the service must be bound
     */
    private void updateUITimer() {
        if (serviceBound) {
            currentRunningTime = targetTime - timerService.elapsedTime();
            timerTextView.setText(String.format("%02d:%02d", currentRunningTime / 60, currentRunningTime % 60));
            final Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (timerTextView.getText().toString().equalsIgnoreCase("00:00")) {
                if (fragmentById != null && fragmentById instanceof TestQuestionFragment) {
                    ((TestQuestionFragment) fragmentById).submitTest();
                    stopTimer();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (fragmentById != null) {
            if (fragmentById instanceof ScoreCardFragment && backStackEntryCount > 0) {
                finish();
                return;
            }
        }
        if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragmentById != null) {
            if (fragmentById instanceof InstructionsFragment) {
                submitView.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                timerTextView.setText(getIntent().getExtras().getString(BaseFragment.COMPANY_NAME, ""));
//                            runTimer();
            } else if (fragmentById instanceof TestQuestionFragment) {

                submitView.setVisibility(((TestQuestionFragment) fragmentById).isReview() ? View.INVISIBLE : View.VISIBLE);
                timerTextView.setVisibility(((TestQuestionFragment) fragmentById).isReview() ? View.INVISIBLE : View.VISIBLE);
            } else if (fragmentById instanceof ScoreCardFragment) {
                submitView.setVisibility(View.INVISIBLE);
                timerTextView.setText(R.string.your_result);
                timerTextView.setVisibility(View.VISIBLE);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                break;

        }


        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_my_tests) {

        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            switch (v.getId()) {
                case R.id.submit:
                    DDAlerts.showAlert(TestActivity.this, "Are you sure you want to submit this test.", getString(R.string.submit), getString(R.string.cancel), new DDAlerts.AlertListener() {
                        @Override
                        public void onClick(int buttonType) {
                            if (buttonType == DDAlerts.BUTTON_POSITIVE) {
                                if (fragmentById != null && fragmentById instanceof TestQuestionFragment) {
                                    ((TestQuestionFragment) fragmentById).submitTest();
                                    stopTimer();
                                }
                            }

                        }
                    });


                    break;

            }
        }
    };

    /**
     * Callback for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service bound");
            }
            TimerService.RunServiceBinder binder = (TimerService.RunServiceBinder) service;
            timerService = binder.getService();
            serviceBound = true;
            // Ensure the service is not in the foreground when bound
            timerService.background();
            // Update the UI if the service is already running the timer
            if (timerService.isTimerRunning()) {
                updateUIStartRun();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect");
            }
            serviceBound = false;
        }
    };

    /**
     * When the timer is running, use this handler to update
     * the UI every second to show timer progress
     */
    static class UIUpdateHandler extends Handler {

        private final static int UPDATE_RATE_MS = 1000;
        private final WeakReference<TestActivity> activity;

        UIUpdateHandler(TestActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            if (MSG_UPDATE_TIME == message.what) {
                if (Log.isLoggable(TAG, Log.VERBOSE)) {
                    Log.v(TAG, "updating time");
                }
                activity.get().updateUITimer();
                sendEmptyMessageDelayed(MSG_UPDATE_TIME, UPDATE_RATE_MS);
            }
        }
    }


    public void showProgress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        View inflate = this.getLayoutInflater().inflate(R.layout.progress_dialog_layout, null);
        builder.setView(inflate);

        alertDialog = builder.create();
        configureProgress(alertDialog);
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public void dismissProgress() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

}
