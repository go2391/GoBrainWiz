package brainwiz.gobrainwiz.api;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import brainwiz.gobrainwiz.R;

public class NetworkImageView extends android.support.v7.widget.AppCompatImageView {

    private int placeHolder;

    public NetworkImageView(Context context) {
        super(context);
    }

    public NetworkImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NetworkImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }


    public void setUrl(String url) {

        if (url == null) {
            return;
        }

        Glide.with(getContext()).load(url).placeholder(getPlaceHolder()).into(this);
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }
}
