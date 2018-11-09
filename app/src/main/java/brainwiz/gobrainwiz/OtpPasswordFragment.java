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
import brainwiz.gobrainwiz.databinding.FragmentOtpPasswordBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import retrofit2.Response;

public class OtpPasswordFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentOtpPasswordBinding bind;

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

        bind.confirm.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:

                    if (verifyFileds()) {
                        confirmUser();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));

                    }
                    break;
            }
        }
    };

    private boolean verifyFileds() {
        if (isEmpty(bind.otp)) {
            DDAlerts.showToast(getActivity(), "enter OTP.");
            return false;
        }

        if (bind.otp.getText().toString().length() < 4) {
            DDAlerts.showToast(getActivity(), "enter valid OTP.");
            return false;
        }


        if (isEmpty(bind.password)) {
            DDAlerts.showToast(getActivity(), "enter password.");
            return false;
        }

        if (isEmpty(bind.confirmPassword)) {
            DDAlerts.showToast(getActivity(), "enter mobile number.");
            return false;
        }

        if (bind.password.getText().toString().equals(bind.confirmPassword.getText().toString())) {
            DDAlerts.showToast(getActivity(), "password and confirm password are not matched ");
            return false;
        }


        return true;
    }


    private void confirmUser() {
        HashMap<String, String> hashMap = getBaseBodyMap();
        hashMap.put("otp", bind.otp.getText().toString());
        hashMap.put("password", bind.password.getText().toString());
        hashMap.put("confirmPassword", bind.confirmPassword.getText().toString());

        RetrofitManager.getRestApiMethods().verifyOTP(hashMap).enqueue(new ApiCallback<BaseModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {

            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {

            }
        });

    }

}
