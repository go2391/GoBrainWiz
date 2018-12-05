package brainwiz.gobrainwiz;

import android.content.Context;
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
import brainwiz.gobrainwiz.api.model.RegistrationModel;
import brainwiz.gobrainwiz.databinding.FragmentForgotPasswordBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import retrofit2.Response;

public class ForgotPasswordFragment extends BaseFragment {

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
            DDAlerts.showToast(getActivity(), "enter valid mobile no or email id.");
            return false;
        }


        return true;
    }

    private void resetPassword() {
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

                }
//                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {

            }
        });

    }

}
