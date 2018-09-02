package brainwiz.gobrainwiz;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import brainwiz.gobrainwiz.api.NetworkImageView;
import brainwiz.gobrainwiz.api.model.DashBoardModel;


public class SlidingImageAdapter extends PagerAdapter {


    private List<DashBoardModel.Banner> images;
    private LayoutInflater inflater;


    public SlidingImageAdapter(Context context, List<DashBoardModel.Banner> images) {
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images != null ? images.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final NetworkImageView imageView = (NetworkImageView) imageLayout
                .findViewById(R.id.image);


        imageView.setUrl(images.get(position).getBannerLocation());

        view.addView(imageLayout, 0);

//        imageLayout.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in));
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
