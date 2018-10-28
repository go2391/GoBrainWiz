package brainwiz.gobrainwiz.onlinetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.OnlineTestSetModel;
import brainwiz.gobrainwiz.test.TestActivity;
import brainwiz.gobrainwiz.test.TestQuestionFragment;
import brainwiz.gobrainwiz.utils.DDAlerts;
import retrofit2.Response;

public class InstructionsFragment extends BaseFragment {

    static String KEY_TEST_ID = "testID";
    private Context context;
    private FragmentActivity activity;
    private List<OnlineTestSetModel.TestSet> testSets = new ArrayList<>();
    private String testID;
    private InstructionTestTypeAdapter adapter;
    private String selectedCatId = "";
    private boolean isReview;
    int TEST_REQUESTCODE = 100;

    public static InstructionsFragment getInstance(Bundle bundle) {
        InstructionsFragment instructionsFragment = new InstructionsFragment();
        instructionsFragment.setArguments(bundle);
        return instructionsFragment;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCompanySets();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_instructions, container, false);
        setHasOptionsMenu(true);
        initViews(inflate);
        return inflate;
    }

    private void initViews(View inflate) {

        isReview = getArguments().getBoolean(BaseFragment.IS_REVIEW);


        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.instruction_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new InstructionTestTypeAdapter(context, testSets);
        adapter.setReview(isReview);
        adapter.setListener(listener);
        recyclerView.setAdapter(adapter);
        TextView start_Review_btn = inflate.findViewById(R.id.start);
        start_Review_btn.setText(isReview ? getString(R.string.review) : getString(R.string.start));
        start_Review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedCatId.isEmpty()) {
                    DDAlerts.showAlert(getActivity(), getString(R.string.select_section), getString(R.string.ok));
                    return;
                }
                if (!isReview)
                    ((TestActivity) activity).startTest();

                TestQuestionFragment instance = TestQuestionFragment.getInstance(testID, selectedCatId, true, isReview);

                instance.setTargetFragment(InstructionsFragment.this, TEST_REQUESTCODE);
                ((TestActivity) activity).fragmentTransaction(instance);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEST_REQUESTCODE) {
            if (data != null) {
                List<OnlineTestSetModel.TestSet> options = adapter.getOptions();
                for (OnlineTestSetModel.TestSet option : options) {
                    if (option.getCatId().equalsIgnoreCase(data.getStringExtra(ID))) {
                        option.setCompleted(true);
                    }
                }
                adapter.notifyDataSetChanged();

            }
        }
    }

    private void getCompanySets() {
        if (!testSets.isEmpty()) {
            return;
        }
        showProgress();
        testID = getArguments().getString(ID, "");
        HashMap<String, String> baseBodyMap = getBaseBodyMap();
        baseBodyMap.put("exam_id", testID);
        RetrofitManager.getRestApiMethods().getCompanySets(baseBodyMap).enqueue(new ApiCallback<OnlineTestSetModel>(getActivity()) {
            @Override
            public void onApiResponse(Response<OnlineTestSetModel> response, boolean isSuccess, String message) {
                if (isSuccess) {
                    testSets = response.body().getData();
                    adapter.setOptions(testSets);
                    adapter.notifyDataSetChanged();
                }
                dismissProgress();
            }

            @Override
            public void onApiFailure(boolean isSuccess, String message) {
                dismissProgress();
            }
        });
    }


    InstructionTestTypeAdapter.TypeListener listener = new InstructionTestTypeAdapter.TypeListener() {
        @Override
        public void onOptionSelected(int position) {

            selectedCatId = testSets.get(position).getCatId();

        }
    };

}
