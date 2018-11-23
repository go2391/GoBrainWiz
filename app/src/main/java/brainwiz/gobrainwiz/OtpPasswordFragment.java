package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.api.model.RegistrationModel;
import brainwiz.gobrainwiz.databinding.FragmentOtpPasswordBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

import static brainwiz.gobrainwiz.utils.SharedPrefUtils.IS_LOGIN;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_EMAIL;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_ID;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_MOBILE;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_NAME;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_TOKEN;

public class OtpPasswordFragment extends BaseFragment {


    private Context context;
    private FragmentActivity activity;
    private FragmentOtpPasswordBinding bind;
    private boolean isRegistration;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_otp_password, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {

        isRegistration = getArguments().getBoolean(LoginFragment.KEY_ISREGISTRATION, true);
        bind.confirm.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:

                    if (verifyFileds()) {
                        if (isRegistration) {
                            registerUser();
                        } else {
                            resetPassword();
                        }
//                        userLogin();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));

                    }
                    break;
            }
        }
    };

    private void userLogin() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
//            "user_name" : "6785435787",
//             "password" : "alex1234"
        HashMap<String, String> hashMap = getBaseBodyMap();
        hashMap.put("user_name", getArguments().getString("mobile"));
        hashMap.put("password", bind.password.getText().toString());
        RetrofitManager.getRestApiMethods().login(hashMap).enqueue(new ApiCallback<LoginModel>(activity) {
            @Override
            public void onApiResponse(Response<LoginModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
                    saveUserDetails(response.body().getData());
                    activity.finish();
                    startActivity(new Intent(activity, MainActivity.class));
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }

    private void saveUserDetails(LoginModel.Data body) {
        SharedPrefUtils.putData(context, IS_LOGIN, true);
        SharedPrefUtils.putData(context, USER_EMAIL, body.getExamuserEmail());
        SharedPrefUtils.putData(context, USER_ID, body.getExamuserId());
        SharedPrefUtils.putData(context, USER_MOBILE, body.getExamuserMobile());
        SharedPrefUtils.putData(context, USER_NAME, body.getExamuserName());
        SharedPrefUtils.putData(context, USER_TOKEN, body.getExamUserToken());
    }

    private boolean verifyFileds() {
        if (isEmpty(bind.otp)) {
            DDAlerts.showToast(getActivity(), "enter OTP.");
            return false;
        }

        if (bind.otp.getText().toString().length() < 6) {
            DDAlerts.showToast(getActivity(), "enter valid OTP.");
            return false;
        }


        if (isEmpty(bind.password)) {
            DDAlerts.showToast(getActivity(), "enter password.");
            return false;
        }

        if (isEmpty(bind.confirmPassword)) {
            DDAlerts.showToast(getActivity(), "enter confirm password.");
            return false;
        }

        if (!bind.password.getText().toString().equals(bind.confirmPassword.getText().toString())) {
            DDAlerts.showToast(getActivity(), "password and confirm password are not matched ");
            return false;
        }


        return true;
    }


    private void resetPassword() {
//        {
//"name":"werty",
//"mobile":8769054576,
//"mail_id":"alet@gmail.com",
//"college_name":"sampleNAme"

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        Bundle arguments = getArguments();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", arguments.getString("mobile"));
        hashMap.put("verify_otp", bind.otp.getText().toString());
        hashMap.put("password", bind.password.getText().toString());


        RetrofitManager.getRestApiMethods().resetPassword(hashMap).enqueue(new ApiCallback<BaseModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
//                    if (response.body().getData().getMessage().equalsIgnoreCase("Password Updated")) {
                    userLogin();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));
                } else {

                }
//                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });

    }

    private void registerUser() {
//        {
//"name":"werty",
//"mobile":8769054576,
//"mail_id":"alet@gmail.com",
//"college_name":"sampleNAme"

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        showProgress();
        Bundle arguments = getArguments();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", arguments.getString("name"));
        hashMap.put("mobile", arguments.getString("mobile"));
        hashMap.put("mail_id", arguments.getString("mail_id"));
        hashMap.put("college_name", arguments.getString("college_name"));
        hashMap.put("verify_otp", bind.otp.getText().toString());
        hashMap.put("password", bind.password.getText().toString());


        RetrofitManager.getRestApiMethods().register(hashMap).enqueue(new ApiCallback<RegistrationModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<RegistrationModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    if (response.body().getData().getData().getMessage().equalsIgnoreCase("Password Updated")) {
                        userLogin();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));
                    } else {

                    }
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {

            }
        });

    }


}
