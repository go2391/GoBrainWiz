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
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.api.model.RegistrationModel;
import brainwiz.gobrainwiz.databinding.FragmentChangePasswordBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.KeyBoardUtils;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

import static brainwiz.gobrainwiz.utils.SharedPrefUtils.IS_LOGIN;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_EMAIL;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_ID;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_MOBILE;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_NAME;
import static brainwiz.gobrainwiz.utils.SharedPrefUtils.USER_TOKEN;

public class ChangePasswordFragment extends BaseFragment {


    private Context context;
    private FragmentActivity activity;
    private FragmentChangePasswordBinding bind;
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
        View inflate = inflater.inflate(R.layout.fragment_change_password, container, false);
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
                        changePassword();
                    }
                    break;
            }
        }
    };


    private boolean verifyFileds() {


        if (isEmpty(bind.currentPassword)) {
            DDAlerts.showToast(getActivity(), "enter current password.");
            return false;
        }


        if (isEmpty(bind.password)) {
            DDAlerts.showToast(getActivity(), "enter new password.");
            return false;
        }

        if (isEmpty(bind.confirmPassword)) {
            DDAlerts.showToast(getActivity(), "enter confirm password.");
            return false;
        }


        if (bind.currentPassword.getText().toString().equals(bind.password.getText().toString())) {
            DDAlerts.showToast(getActivity(), "current password and new password are matcheh. Please use different password.");
            return false;
        }


        if (!bind.password.getText().toString().equals(bind.confirmPassword.getText().toString())) {
            DDAlerts.showToast(getActivity(), "new password and confirm password are not matched ");
            return false;
        }


        return true;
    }


    private void changePassword() {

        if (!NetWorkUtil.isConnected(context)) {
            DDAlerts.showNetworkAlert(activity);
            return;
        }

        KeyBoardUtils.hideKeyboard(getActivity());

        showProgress();
        HashMap<String, String> hashMap = getBaseBodyMap();
        hashMap.put("old_password", bind.currentPassword.getText().toString().trim());
        hashMap.put("new_password", bind.password.getText().toString().trim());


        RetrofitManager.getRestApiMethods().changePassword(hashMap).enqueue(new ApiCallback<BaseModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
//                    if (response.body().getData().getMessage().equalsIgnoreCase("Password Updated")) {
                    DDAlerts.showToast(getActivity(), "Password changed successfully.");
                    getActivity().getSupportFragmentManager().popBackStack();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));
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
