package brainwiz.gobrainwiz;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {
    public void showProgress() {

        ((BaseActivity) getActivity()).showProgress();
    }

    public void dismissProgress() {
        ((BaseActivity) getActivity()).dismissProgress();
    }
}
