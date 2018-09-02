package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import retrofit2.Response;

import static brainwiz.gobrainwiz.R.color.colorAccent;

public class LoginFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentLoginBinding bind;

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
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorAccent)), spannableString.length() - 8, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bind.registrationHere.setText(spannableString);
        bind.registrationHere.setOnClickListener(onClickListener);
        bind.loginArrow.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.registration_here:
                    ((LoginActivity) activity).fragmentTransaction(new RegistrationFragment(), R.id.login_frame, true);
                    break;
                case R.id.login_arrow:

                    userLogin();
//                    activity.finish();
//                    startActivity(new Intent(activity, MainActivity.class));
                    break;

            }
        }
    };

    private void userLogin() {
        if (NetWorkUtil.isConnected(context)) {
//            "user_name" : "6785435787",
//             "password" : "alex1234"
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_name", bind.mobile.getText().toString().trim());
            hashMap.put("password", bind.password.getText().toString().trim());
            RetrofitManager.getRestApiMethods().login(hashMap).enqueue(new ApiCallback<LoginModel>(activity) {
                @Override
                public void onApiResponse(Response<LoginModel> response, boolean isSuccess, String message) {

                    if (isSuccess) {
                        activity.finish();
                        startActivity(new Intent(activity, MainActivity.class));
                    }
                }

                @Override
                public void onApiFailure(boolean isSuccess, String message) {

                }
            });
        } else {
            DDAlerts.showNetworkAlert(activity);
        }
    }


}
