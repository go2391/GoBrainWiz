package brainwiz.gobrainwiz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.notifications.NotificationsFragment;
import brainwiz.gobrainwiz.profile.ProfileFragment;
import brainwiz.gobrainwiz.sidemenu.TestHistoryFragment;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

import static brainwiz.gobrainwiz.utils.SharedPrefUtils.IS_LOGIN;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 100;
    private static final int PICK_IMAGE_CAMERA = 100;
    private static final int PICK_IMAGE_GALLERY = 200;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int PIC_CROP = 2;

    private static final String PHONE_NUMBER = "8142123938";
    private DrawerLayout drawer;
    private AlertDialog alertDialog;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private RoundishImageView roundishImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        roundishImageView = (RoundishImageView) header.findViewById(R.id.imageView);
        roundishImageView.setUrl(SharedPrefUtils.getString(this, SharedPrefUtils.PROFILE_IMAGE, ""));
        ((TextView) header.findViewById(R.id.textView_user_name)).setText(SharedPrefUtils.getUserName(this));
        ((TextView) header.findViewById(R.id.textView_user_email)).setText(SharedPrefUtils.getUserEmail(this));
        ((TextView) header.findViewById(R.id.textView_user_phone)).setText(SharedPrefUtils.getUserPhone(this));
//        ((TextView) header.findViewById(R.id.textView_user_location)).setText(SharedPrefUtils.getUser(this));
        roundishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);


        findViewById(R.id.logo).setOnClickListener(onClickListener);
        fragmentTransaction(new HomeFragment(), R.id.content_frame, false);
    }

    private void dispatchTakePictureIntent() {

        Context context = MainActivity.this;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);

            return;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CAMERA);

            return;
        }


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PIC_CROP:
                    if (data != null) {
                        // get the returned data
                        Bundle extras = data.getExtras();
                        // get the cropped bitmap
                        Bitmap srcBmp = extras.getParcelable("data");
                        Bitmap dstBmp;

                        src(srcBmp);
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    roundishImageView.setImageBitmap(imageBitmap);
                    src(imageBitmap);
                    break;
            }

        }
    }

    private void src(Bitmap srcBmp) {
        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        } else {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }

        roundishImageView.setImageBitmap(dstBmp);
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
        baseBodyMap.put("image", getEncoded64ImageStringFromBitmap(dstBmp));
        RetrofitManager.getRestApiMethods().uploadImage(baseBodyMap).enqueue(new ApiCallback<BaseModel>(MainActivity.this) {
            @Override
            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    LogUtils.e(response.body().getMessage());
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                LogUtils.e(message);
            }
        });
    }

    public HashMap<String, String> getBaseBodyMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", SharedPrefUtils.getToken(this));
        hashMap.put("student_id", SharedPrefUtils.getStudentID(this));

        return hashMap;
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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
                drawer.openDrawer(Gravity.START);
                break;

            case R.id.action_call:
                call();
                break;
            case R.id.action_notifications:
                fragmentTransaction(new NotificationsFragment());
                break;
        }


        return true;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logo:
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    break;
            }
        }
    };

    public void call() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PHONE_NUMBER));
            this.startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.nav_camera:
                fragmentTransaction(new ProfileFragment(), R.id.content_frame, true);
                break;
            case R.id.nav_my_tests:
                fragmentTransaction(new TestHistoryFragment(), R.id.content_frame, true);
                break;
            case R.id.nav_notifications:
                fragmentTransaction(new NotificationsFragment(),R.id.content_frame, true);
                break;
            case R.id.nav_contact_us:
                fragmentTransaction(new ContactUsFragment(), R.id.content_frame, true);
                break;
            case R.id.nav_about_us:
                fragmentTransaction(new AboutUsFragment(), R.id.content_frame, true);
                break;

            case R.id.nav_share:
                share();
                break;
            case R.id.nav_send:
                fragmentTransaction(new JoinFragment(), R.id.content_frame, true);
                break;
            case R.id.nav_logout:
                SharedPrefUtils.putData(this,IS_LOGIN,false);
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void share() {

//        ShareCompat.IntentBuilder.from(this)
//                .setType("text/plain")
//                .setChooserTitle("Chooser title")
//                .setText("http://play.google.com/store/apps/details?id=" + getPackageName())
//                .startChooser();
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void showProgress() {
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


    /*...
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
mBuilder.setContentTitle("Picture Download")
        .setContentText("Download in progress")
        .setSmallIcon(R.drawable.ic_notification)
        .setPriority(NotificationCompat.PRIORITY_LOW);

    // Issue the initial notification with zero progress
    int PROGRESS_MAX = 100;
    int PROGRESS_CURRENT = 0;
mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
notificationManager.notify(notificationId, mBuilder.build());

// Do the job here that tracks the progress.
// Usually, this should be in a worker thread
// To show progress, update PROGRESS_CURRENT and update the notification with:
// mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
// notificationManager.notify(notificationId, mBuilder.build());

// When done, update the notification one more time to remove the progress bar
mBuilder.setContentText("Download complete")
        .setProgress(0,0,false);
notificationManager.notify(notificationId, mBuilder.build());*/
}
