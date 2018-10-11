package brainwiz.gobrainwiz;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

import java.util.HashMap;

import brainwiz.gobrainwiz.utils.SharedPrefUtils;

@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {

    public static final String ID = "id";
    public static final String IS_COMPANY_TEST = "isCompanyTest";
    public static final String CAT_ID = "catId";
    public static final String IS_REVIEW = "isReview";
    public static final String DURATION = "Duration";

    public void showProgress() {

        ((BaseActivity) getActivity()).showProgress();
    }

    public void dismissProgress() {
        ((BaseActivity) getActivity()).dismissProgress();
    }


    public HashMap<String, String> getBaseBodyMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", SharedPrefUtils.getToken(getActivity()));
        hashMap.put("student_id", SharedPrefUtils.getStudentID(getActivity()));

        return hashMap;
    }
}
