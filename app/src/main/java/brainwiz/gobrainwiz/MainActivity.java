package brainwiz.gobrainwiz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.ImageUploadModel;
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.notifications.NotificationsFragment;
import brainwiz.gobrainwiz.profile.ProfileFragment;
import brainwiz.gobrainwiz.sidemenu.TestHistoryFragment;
import brainwiz.gobrainwiz.ui.CountDrawable;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

import static brainwiz.gobrainwiz.utils.SharedPrefUtils.IS_LOGIN;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_COLLEGE;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_EMAIL;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_ID;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_MOBILE;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_NAME;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_TOKEN;

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
    boolean doubleBackToExitPressedOnce = false;
    private TextView notifications;

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
        ((TextView) header.findViewById(R.id.textView_user_location)).setText(SharedPrefUtils.getUserCollege(this));
        roundishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        notifications = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_notifications));
        initializeCountDrawer();


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

    private void initializeCountDrawer() {
        //Gravity property aligns the text
        notifications.setGravity(Gravity.CENTER_VERTICAL);
        notifications.setTypeface(null, Typeface.BOLD);
        notifications.setTextColor(getResources().getColor(R.color.colorAccent));
        String notificationCount = SharedPrefUtils.getNotificationCount(this);
        notifications.setText(notificationCount);
        notifications.setVisibility(Integer.parseInt(notificationCount) > 0 ? View.VISIBLE : View.INVISIBLE);

    }

    private void selectImage() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    dispatchTakePictureIntent();
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(/*Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI*/);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, PICK_IMAGE_GALLERY);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setCount(menu, SharedPrefUtils.getNotificationCount(this));
        return super.onPrepareOptionsMenu(menu);
    }

    public void setCount(Menu defaultMenu, String count) {
        MenuItem menuItem = defaultMenu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();


        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        reuse.setVisible(Integer.parseInt(count) > 0, true);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(this);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
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
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE_GALLERY);
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
                case PICK_IMAGE_GALLERY:

//                    Bitmap photo = (Bitmap) data.getData().getPath();
//                    Uri imagename = data.getData();
//                    Uri selectedImageUri = data.getData();
//                    imagepath = getPath(selectedImageUri);
//                    Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
//                    imageview.setImageBitmap(bitmap);
//                    messageText.setText("Uploading file path:" + imagepath);


                    Uri uri = data.getData();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        // Log.d(TAG, String.valueOf(bitmap));

                        src(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    if (data != null) {
//                        // get the returned data
//                        Bundle extras = data.getExtras();
//                        // get the cropped bitmap
//                        Bitmap srcBmp = extras.getParcelable("data");
//                        Bitmap dstBmp;
//
//                        src(srcBmp);
//                    }
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
        RetrofitManager.getRestApiMethods().uploadImage(baseBodyMap).enqueue(new ApiCallback<ImageUploadModel>(MainActivity.this) {
            @Override
            public void onApiResponse(Response<ImageUploadModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    LogUtils.e(response.body().getMessage());
                    roundishImageView.setUrl(response.body().getData().getUrl());
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
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
//            super.onBackPressed();
        }
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
            case PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {

                }

                break;

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
                fragmentTransaction(new NotificationsFragment(), R.id.content_frame, true);
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
                clearUserData(MainActivity.this);
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
