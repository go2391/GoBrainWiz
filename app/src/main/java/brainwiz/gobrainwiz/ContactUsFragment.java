package brainwiz.gobrainwiz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brainwiz.gobrainwiz.databinding.FragmentContactUsBinding;

public class ContactUsFragment extends BaseFragment {

    private Context context;
    FragmentContactUsBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_contact_us, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {

        bind.contactUsAddress.setOnClickListener(onClickListener);
        bind.contactUsEmail.setOnClickListener(onClickListener);
        bind.contactUsCall.setOnClickListener(onClickListener);


    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.contact_us_address:
                    openGmap();
                    break;
                case R.id.contact_us_call:
                    ((MainActivity) getActivity()).call();
                    break;

                case R.id.contact_us_email:


                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.gobrainwiz_gmail_com), null));
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

    private void openGmap() {
        Uri gmmIntentUri = Uri.parse("geo:17.438687,78.448138");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
