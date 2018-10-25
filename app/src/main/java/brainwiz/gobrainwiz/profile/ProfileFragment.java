package brainwiz.gobrainwiz.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.databinding.FragmentProfileBinding;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;

public class ProfileFragment extends BaseFragment {

    FragmentProfileBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        bind.profileImageView.setUrl(SharedPrefUtils.getString(getActivity(), SharedPrefUtils.PROFILE_IMAGE, ""));
    }
}
