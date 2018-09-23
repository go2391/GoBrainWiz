package brainwiz.gobrainwiz.onlinetest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.MainActivity;
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
        initViews(inflate);
        return inflate;
    }

    private void initViews(View inflate) {

        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.instruction_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new InstructionTestTypeAdapter(context, testSets);
        adapter.setListener(listener);
        recyclerView.setAdapter(adapter);
        inflate.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedCatId.isEmpty()) {
                    DDAlerts.showAlert(getActivity(), "Select any one test", getString(R.string.ok));
                    return;
                }
                ((TestActivity) activity).fragmentTransaction(TestQuestionFragment.getInstance(testID, selectedCatId, true, false));

            }
        });
    }


    private void getCompanySets() {
        showProgress();
        testID = getArguments().getString(ID, "");
        RetrofitManager.getRestApiMethods().getCompanySets(testID).enqueue(new ApiCallback<OnlineTestSetModel>(getActivity()) {
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
