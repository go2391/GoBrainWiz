package brainwiz.gobrainwiz;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import java.util.HashMap;

import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.utils.SharedPrefUtils;

@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {

    public static final String ID = "id";
    public static final String IS_COMPANY_TEST = "isCompanyTest";
    public static final String CAT_ID = "catId";
    public static final String IS_REVIEW = "isReview";
    public static final String DURATION = "Duration";
    public static final String COMPANY_NAME = "CompanyName";


    public void showProgress() {

        FragmentActivity activity = getActivity();

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).showProgress();
        } else if (activity instanceof RegistrationActivity) {
            ((RegistrationActivity) activity).showProgress();
        } else if (activity instanceof TestActivity) {
            ((TestActivity) activity).showProgress();
        } else if (activity instanceof LoginActivity) {
            ((LoginActivity) activity).showProgress();
        }


    }

    public void dismissProgress() {

        FragmentActivity activity = getActivity();

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).dismissProgress();
        } else if (activity instanceof RegistrationActivity) {
            ((RegistrationActivity) activity).dismissProgress();
        } else if (activity instanceof TestActivity) {
            ((TestActivity) activity).dismissProgress();
        } else if (activity instanceof LoginActivity) {
            ((LoginActivity) activity).dismissProgress();
        }
//        ((MainActivity) getActivity()).dismissProgress();
    }


    public HashMap<String, String> getBaseBodyMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", SharedPrefUtils.getToken(getActivity()));
        hashMap.put("student_id", SharedPrefUtils.getStudentID(getActivity()));

        return hashMap;
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
}
