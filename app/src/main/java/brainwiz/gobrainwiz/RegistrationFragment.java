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

import brainwiz.gobrainwiz.databinding.FragmentLoginBinding;
import brainwiz.gobrainwiz.databinding.FragmentRegistrationBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;

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
                    activity.getSupportFragmentManager().popBackStack();
                    break;
                case R.id.registration_arrow:
                    if (isValidDetails()) {
                        registerUser();
//                        activity.finish();
//                        startActivity(new Intent(activity, MainActivity.class));
                        ((LoginActivity) getActivity()).fragmentTransaction(new OtpPasswordFragment(), R.id.login_frame);
                    }

                    break;
            }
        }
    };

    private void registerUser() {
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("name",bind)
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
            DDAlerts.showToast(getActivity(), "enter location or college name.");
            return false;
        }


        return true;
    }

}
