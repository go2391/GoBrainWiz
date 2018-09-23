package brainwiz.gobrainwiz;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {

    public static final String ID = "id";
    public static final String IS_COMPANY_TEST = "isCompanyTest";
    public static final String CAT_ID = "catId";
    public static final String IS_REVIEW = "isReview";

    public void showProgress() {

        ((BaseActivity) getActivity()).showProgress();
    }

    public void dismissProgress() {
        ((BaseActivity) getActivity()).dismissProgress();
    }
}
