package brainwiz.gobrainwiz;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.RegistrationModel;
import brainwiz.gobrainwiz.databinding.FragmentForgotPasswordBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

public class ForgotPasswordFragment extends BaseFragment {

    private static final int REQUEST_CODE_READ_SMS = 100;
    private Context context;
    private FragmentActivity activity;
    private FragmentForgotPasswordBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {

        bind.confirm.setOnClickListener(onClickListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    resetPassword();
                } else {
                    resetPassword();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    if (isValidDetails()) {
                        resetPassword();
                        ((RegistrationActivity) activity).getSupportFragmentManager().popBackStack();
                    }
                    break;
            }
        }
    };

    private boolean isValidDetails() {


        if (isEmpty(bind.emailMobileNo)) {
            DDAlerts.showToast(getActivity(), "enter valid mobile no.");
            return false;
        }


        return true;
    }

    private void resetPassword() {


        if (!NetWorkUtil.isConnected(getActivity())) {
            DDAlerts.showNetworkAlert(getActivity());
            return;
        }


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    REQUEST_CODE_READ_SMS);
            return;
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        }

        HashMap<String, String> hashMap = getBaseBodyMap();
        hashMap.put("forgot_password", bind.emailMobileNo.getText().toString());

        RetrofitManager.getRestApiMethods().resetPassword(hashMap).enqueue(new ApiCallback<BaseModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
//                    if (response.body().getData().getData().getMessage().contains("Successfully")) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(LoginFragment.KEY_ISREGISTRATION, false);
                    bundle.putString("mobile", bind.emailMobileNo.getText().toString());

                    OtpPasswordFragment newFragment = new OtpPasswordFragment();
                    newFragment.setArguments(bundle);
                    ((RegistrationActivity) getActivity()).fragmentTransaction(newFragment, R.id.login_frame);
                } else {
                    DDAlerts.showResponseError(getActivity(), response.body());

                }
//                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });

    }

}
