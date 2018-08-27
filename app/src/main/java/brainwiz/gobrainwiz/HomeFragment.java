package brainwiz.gobrainwiz;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brainwiz.gobrainwiz.api.ApiCallback;
import brainwiz.gobrainwiz.api.RetrofitManager;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private Context context;
    private FragmentHomeBinding bind;
    private FragmentActivity activity;
    private List<DashBoardModel.Banner> bannerList;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        getDashboardData();
        return inflate;
    }


    private void initViews() {

//        bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, bannerList));

        bind.homeImageIndicator.setupWithViewPager(bind.homeAutoSlideViewpager, true);

        bind.homeTestimonialsIndicator.setupWithViewPager(bind.homeTestimonialsViewpager, true);

        bind.youtubeViewImage.setUrl("http://i3.ytimg.com/vi/HL32sd9J9X0/hqdefault.jpg");
    }

    private void getDashboardData() {
        RetrofitManager.getRestApiMethods().getDashBoard().enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {
                Log.e("", response.toString());
                DashBoardModel.Data data = response.body().getData();
                bannerList = data.getBanners();
                bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, bannerList));

                bind.homeTestimonialsViewpager.setAdapter(new TestimonalAdapter(context, data.getTestinomials()));


            }

            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {
                Log.e("", call.toString());
            }
        });
    }
}
