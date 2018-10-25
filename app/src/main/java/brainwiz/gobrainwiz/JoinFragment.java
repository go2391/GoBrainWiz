package brainwiz.gobrainwiz;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.databinding.FragmentJoinBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

public class JoinFragment extends BaseFragment {

    private Context context;
    FragmentJoinBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_join, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        bind.name.setText(SharedPrefUtils.getUserName(context));
        bind.email.setText(SharedPrefUtils.getUserEmail(context));
        bind.mobile.setText(SharedPrefUtils.getUserPhone(context));

        bind.submit.setOnClickListener(onClickListener);
    }


    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit:
                    if (isValidDetails()) {

                        if (!NetWorkUtil.isConnected(context)) {
                            DDAlerts.showNetworkAlert(getActivity());
                            return;
                        }
                        showProgress();
                        HashMap<String, String> baseBodyMap = getBaseBodyMap();
                        baseBodyMap.put("name", bind.name.getText().toString());
                        baseBodyMap.put("email", bind.email.getText().toString());
                        baseBodyMap.put("mobile", bind.mobile.getText().toString());
                        baseBodyMap.put("message", bind.message.getText().toString());
                        RetrofitManager.getRestApiMethods().postJoinRequest(baseBodyMap).enqueue(new ApiCallback<String>(getActivity()) {
                            @Override
                            public void onApiResponse(Response<String> response, boolean isSuccess, String message) {
                                dismissProgress();
                                if (isSuccess) {
                                    bind.name.setText("");
                                    bind.email.setText("");
                                    bind.mobile.setText("");
                                    bind.message.setText("");
                                    DDAlerts.showAlert(getActivity(), "Your request has been sent, We will contact you soon. Thank you for your interest.", getString(R.string.ok));
                                }
                            }

                            @Override
                            public void onApiFailure(boolean isSuccess, String message) {

                            }
                        });
                    }
                    break;

            }
        }
    };

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


        return true;
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }


}
