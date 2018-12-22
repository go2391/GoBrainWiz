package brainwiz.gobrainwiz.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import brainwiz.gobrainwiz.BaseActivity;
import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.ChangePasswordFragment;
import brainwiz.gobrainwiz.MainActivity;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.ImageUploadModel;
import brainwiz.gobrainwiz.databinding.FragmentProfileBinding;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment {

    private static final int PICK_IMAGE_CAMERA = 100;
    private static final int PICK_IMAGE_GALLERY = 200;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int PIC_CROP = 2;
    FragmentProfileBinding bind;
    private Context context;
    private File destination;
    private String imgPath;
    private Bitmap bitmap;
    private InputStream inputStreamImg;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        bind.profileImageView.setPlaceHolder(R.drawable.ic_profile_default);
        bind.profileImageView.setUrl(SharedPrefUtils.getString(getActivity(), SharedPrefUtils.PROFILE_IMAGE, ""));
        bind.profileUserName.setText(SharedPrefUtils.getUserName(context));
        bind.profileName.setText(String.format(getString(R.string.name_), SharedPrefUtils.getUserName(context)));
        bind.profileEmail.setText(String.format(getString(R.string.email_), SharedPrefUtils.getUserEmail(context)));
        bind.profileMobile.setText(String.format(getString(R.string.mobile_number_), SharedPrefUtils.getUserPhone(context)));
        bind.profileLocation.setText(String.format(getString(R.string.location_), SharedPrefUtils.getUserCollege(context)));

        bind.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        bind.profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).fragmentTransaction(new ChangePasswordFragment(), R.id.content_frame, true);
            }
        });

    }


    private void selectImage() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {

//        Context context = MainActivity.this;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);

            return;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CAMERA);

            return;
        }


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
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

                    bind.profileImageView.setImageBitmap(imageBitmap);
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

        bind.profileImageView.setImageBitmap(dstBmp);
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
        baseBodyMap.put("image", getEncoded64ImageStringFromBitmap(dstBmp));
        RetrofitManager.getRestApiMethods().uploadImage(baseBodyMap).enqueue(new ApiCallback<ImageUploadModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<ImageUploadModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    LogUtils.e(response.body().getMessage());
                    bind.profileImageView.setUrl(response.body().getData().getUrl());
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                LogUtils.e(message);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
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


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
