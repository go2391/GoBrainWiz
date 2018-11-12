package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.databinding.FragmentLoginBinding;
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

public class LoginFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentLoginBinding bind;

    public static final String KEY_ISREGISTRATION = "isRegistration";

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {

        SpannableString spannableString = new SpannableString(getString(R.string.don_t_you_have_an_account_please_register));
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.blueSecondary)), spannableString.length() - 14, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bind.registrationHere.setText(spannableString);
        bind.registrationHere.setOnClickListener(onClickListener);
        bind.loginArrow.setOnClickListener(onClickListener);
        bind.forgotPassword.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
            switch (v.getId()) {
                case R.id.registration_here:
                    intent.putExtra(KEY_ISREGISTRATION,true);
                    getActivity().startActivity(intent);
//                    ((RegistrationActivity) activity).fragmentTransaction(new RegistrationFragment(), R.id.login_frame, true);
                    break;
                case R.id.login_arrow:

                    userLogin();
//                    activity.finish();
//                    startActivity(new Intent(activity, MainActivity.class));
                    break;
                case R.id.forgot_password:
                    intent.putExtra(KEY_ISREGISTRATION,false);
                    getActivity().startActivity(intent);
//                    ((RegistrationActivity) activity).fragmentTransaction(new ForgotPasswordFragment(), R.id.login_frame, true);
                    break;

            }
        }
    };

    private void userLogin() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }
        if (isValidDetails()) {
            showProgress();
//            "user_name" : "6785435787",
//             "password" : "alex1234"
            HashMap<String, String> hashMap = getBaseBodyMap();
            hashMap.put("user_name", bind.mobile.getText().toString().trim());
            hashMap.put("password", bind.password.getText().toString().trim());
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


    }

    private boolean isValidDetails() {

        if (isEmpty(bind.mobile)) {
            DDAlerts.showToast(getActivity(), "enter mobile number or email.");
            return false;
        }


        if (isEmpty(bind.password)) {
            DDAlerts.showToast(getActivity(), "enter password.");
            return false;
        }


        return true;
    }


    private void saveUserDetails(LoginModel.Data body) {
        SharedPrefUtils.putData(context, IS_LOGIN, true);
        SharedPrefUtils.putData(context, USER_EMAIL, body.getExamuserEmail());
        SharedPrefUtils.putData(context, USER_ID, body.getExamuserId());
        SharedPrefUtils.putData(context, USER_MOBILE, body.getExamuserMobile());
        SharedPrefUtils.putData(context, USER_NAME, body.getExamuserName());
        SharedPrefUtils.putData(context, USER_TOKEN, body.getExamUserToken());
    }


}
