package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.databinding.JoinContactBrainwizBinding;
import brainwiz.gobrainwiz.utils.DDAlerts;
import brainwiz.gobrainwiz.utils.NetWorkUtil;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import retrofit2.Response;

public class JoinFragment extends BaseFragment {

    private Context context;
    JoinContactBrainwizBinding bind;
//    JoinContactBrainwizBinding

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.join_contact_brainwiz, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        bind.name.setText(SharedPrefUtils.getUserName(context));
//        bind.email.setText(SharedPrefUtils.getUserEmail(context));
//        bind.mobile.setText(SharedPrefUtils.getUserPhone(context));

        bind.mapImageView.setOnClickListener(onClickListener);
        bind.contactUsEmail.setOnClickListener(onClickListener);
        bind.contactUsCall.setOnClickListener(onClickListener);

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
                        baseBodyMap.put("email", SharedPrefUtils.getUserEmail(context));
                        baseBodyMap.put("mobile", SharedPrefUtils.getUserPhone(context));
                        baseBodyMap.put("message", bind.message.getText().toString());
                        RetrofitManager.getRestApiMethods().postJoinRequest(baseBodyMap).enqueue(new ApiCallback<BaseModel>(getActivity()) {
                            @Override
                            public void onApiResponse(Response<BaseModel> response, boolean isSuccess, String message) {
                                dismissProgress();
                                if (isSuccess) {
                                    bind.name.setText("");
                                    bind.message.setText("");
                                    DDAlerts.showAlert(getActivity(), "Your request has been sent, We will contact you soon. Thank you for your interest.", getString(R.string.ok));
                                }
                            }

                            @Override
                            public void onApiFailure(boolean isSuccess, String message) {
                                dismissProgress();
                            }
                        });
                    }
                    break;


                case R.id.map_image_view:
                    openGmap();
                    break;
                case R.id.contact_us_call:
                    ((MainActivity) getActivity()).call();
                    break;

                case R.id.contact_us_email:


                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "gobrainwiz@gmail.com", null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    final PackageManager pm = context.getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    String className = null;
                    for (final ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                            className = info.activityInfo.name;

                            if(className != null && !className.isEmpty()){
                                break;
                            }
                        }
                    }
                    intent.setClassName("com.google.android.gm", className);
                    getActivity().startActivity(intent);
                    break;

            }
        }
    };

    private boolean isValidDetails() {
        if (isEmpty(bind.name)) {
            DDAlerts.showToast(getActivity(), "enter name.");
            return false;
        }


        if (isEmpty(bind.message)) {
            DDAlerts.showToast(getActivity(), "enter query.");
            return false;
        }


        return true;
    }


    private void openGmap() {
        Uri gmmIntentUri = Uri.parse("geo:17.438687,78.448138");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

}
