package brainwiz.gobrainwiz.onlinetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.OnlineTestSetModel;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.test.ScoreCardOnlineFragment;
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
    TextView breakTime;
    int TEST_REQUESTCODE = 100;
    private String nextAutoSelectionID;
    private Button start_Review_btn;
    private boolean breakTimeDone;
    private CountDownTimer countDownTimer;
    private boolean isShowing;

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

        breakTime = inflate.findViewById(R.id.break_time);


        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.instruction_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new InstructionTestTypeAdapter(context, testSets);
        adapter.setReview(isReview);
        adapter.setListener(listener);
        recyclerView.setAdapter(adapter);
        start_Review_btn = inflate.findViewById(R.id.start);
        start_Review_btn.setText(isReview ? getString(R.string.review) : getString(R.string.start));
        start_Review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakTimeDone = false;
                if (selectedCatId.isEmpty()) {
                    DDAlerts.showAlert(getActivity(), getString(R.string.select_section), getString(R.string.ok));
                    return;
                }
                if (!isReview)
                    ((TestActivity) activity).startTest();
                TestQuestionFragment instance = TestQuestionFragment.getInstance(testID, selectedCatId, true, isReview);
                instance.setTargetFragment(InstructionsFragment.this, TEST_REQUESTCODE);
                ((TestActivity) activity).fragmentTransactionStateLoss(instance);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isShowing = true;
        if (breakTimeDone) {
            selectedCatId = nextAutoSelectionID;
            start_Review_btn.performClick();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEST_REQUESTCODE) {
            if (data != null) {
                nextAutoSelectionID = "";
                List<OnlineTestSetModel.TestSet> options = adapter.getOptions();
                for (OnlineTestSetModel.TestSet option : options) {
                    if (option.getCatId().equalsIgnoreCase(data.getStringExtra(CAT_ID))) {
                        option.setCompleted(true);
                    }

                    if (!option.isCompleted() && nextAutoSelectionID.isEmpty()) {
                        nextAutoSelectionID = option.getCatId();
//                        adapter.setSelectedOption();
                    }
                }

                adapter.notifyDataSetChanged();

//                breakTime.setVisibility(View.VISIBLE);

//                breakTime.setText();


                try {
                    final ScoreCardModel.Datum parcelableExtra = (ScoreCardModel.Datum) data.getParcelableExtra("data");
                    final ArrayList<ScoreCardModel.Sets> resultList = new ArrayList<>(parcelableExtra.getSets());

                    if (!resultList.isEmpty()) {
                        ((TestActivity) activity).stopTest();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ScoreCardOnlineFragment instance = ScoreCardOnlineFragment.getInstance(parcelableExtra, isReview, testID);
                                ((TestActivity) getActivity()).fragmentTransaction(instance);

                            }
                        }, 1000);

                    } else
                        startBreakTime();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getNextAutoSelectionID() {
        return nextAutoSelectionID;
    }

    public void setNextAutoSelectionID(String nextAutoSelectionID) {
        this.nextAutoSelectionID = nextAutoSelectionID;
    }


    private void startBreakTime() {
        if (nextAutoSelectionID.isEmpty()) {
            return;
        }
        breakTime.setVisibility(View.VISIBLE);
        final int breakTimeMinutes = Integer.parseInt(getArguments().getString(BREAK_TIME));
        breakTimeDone = false;
        breakTime.setText(String.format(getString(R.string.break_time), breakTimeMinutes));

        countDownTimer = new CountDownTimer((breakTimeMinutes * 60) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                InstructionsFragment.this.breakTime.setText(String.format(getString(R.string.break_time), millisUntilFinished / 1000));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                breakTimeDone = true;
                if (getActivity() != null && isShowing) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InstructionsFragment.this.breakTime.setText("Done!");
                            selectedCatId = nextAutoSelectionID;
                            start_Review_btn.performClick();
                        }
                    });

                }
            }
        };
        countDownTimer.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        isShowing = false;
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
