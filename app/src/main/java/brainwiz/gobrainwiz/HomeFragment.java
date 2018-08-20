package brainwiz.gobrainwiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import brainwiz.gobrainwiz.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private Context context;
    private FragmentHomeBinding bind;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        bind = DataBindingUtil.bind(inflate);
        initViews();
        return inflate;
    }

    private void initViews() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            integers.add(R.drawable.slide);
        }
        bind.homeAutoSlideViewpager.setAdapter(new SlidingImageAdapter(context, integers));
    }
}
