package brainwiz.gobrainwiz;

import android.content.Context;
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
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.api.model.RegistrationModel;
import brainwiz.gobrainwiz.databinding.FragmentRegistrationBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import retrofit2.Response;

import static brainwiz.gobrainwiz.R.color.colorAccent;

public class RegistrationFragment extends BaseFragment {

    private Context context;
    private FragmentActivity activity;
    private FragmentRegistrationBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_registration, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        SpannableString spannableString = new SpannableString(getString(R.string.already_have_an_account_login));
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorAccent)), spannableString.length() - 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bind.loginHere.setText(spannableString);

        bind.registrationArrow.setOnClickListener(onClickListener);
        bind.loginHere.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_here:
                    getActivity().finish();
                    break;
                case R.id.registration_arrow:
                    if (isValidDetails()) {
                        registerUser();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));

                    }

                    break;
            }
        }
    };

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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", bind.name.getText().toString());
        hashMap.put("mobile", bind.mobile.getText().toString());
        hashMap.put("mail_id", bind.email.getText().toString());
        hashMap.put("college_name", bind.location.getText().toString());


        RetrofitManager.getRestApiMethods().register(hashMap).enqueue(new ApiCallback<RegistrationModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<RegistrationModel> response, boolean isSuccess, String message) {
                dismissProgress();
                if (isSuccess) {
                    if (response.body().getData().getData().getMessage().contains("Successfully")) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(LoginFragment.KEY_ISREGISTRATION, true);
                        bundle.putString("name", bind.name.getText().toString());
                        bundle.putString("mobile", bind.mobile.getText().toString());
                        bundle.putString("mail_id", bind.email.getText().toString());
                        bundle.putString("college_name", bind.location.getText().toString());

                        OtpPasswordFragment newFragment = new OtpPasswordFragment();
                        newFragment.setArguments(bundle);
                        ((RegistrationActivity) getActivity()).fragmentTransaction(newFragment, R.id.login_frame);
                    } else {

                    }
                }
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {

            }
        });

    }

    private boolean isValidDetails() {
        if (isEmpty(bind.name)) {
            DDAlerts.showToast(getActivity(), "enter name.");
            return false;
        }

        if (isEmpty(bind.email)) {
            DDAlerts.showToast(getActivity(), "enter email.");
            return false;
        }

        if (isEmpty(bind.mobile)) {
            DDAlerts.showToast(getActivity(), "enter mobile number.");
            return false;
        }


        if (bind.mobile.getText().toString().length() < 10) {
            DDAlerts.showToast(getActivity(), "enter valid mobile number.");
            return false;
        }

        if (isEmpty(bind.location)) {
            DDAlerts.showToast(getActivity(), "enter college name.");
            return false;
        }


        return true;
    }

}
