package brainwiz.gobrainwiz;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppInstructionFragment extends android.support.v4.app.DialogFragment {


    @Override
    public void onStart() {
        super.onStart();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 1.2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(width, height);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_app_instruction, container, false);
        return inflate;
    }


}
