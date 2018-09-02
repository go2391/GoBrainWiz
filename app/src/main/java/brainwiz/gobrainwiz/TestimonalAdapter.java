package brainwiz.gobrainwiz;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brainwiz.gobrainwiz.api.NetworkImageView;
import brainwiz.gobrainwiz.api.model.DashBoardModel;


public class TestimonalAdapter extends PagerAdapter {


    private List<DashBoardModel.Testinomial> testimoniels;
    private LayoutInflater inflater;


    public TestimonalAdapter(Context context, List<DashBoardModel.Testinomial> testimoniels) {
        this.testimoniels = testimoniels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return testimoniels != null ? testimoniels.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.inflate_home_test, view, false);

        assert imageLayout != null;
        final NetworkImageView imageView = (NetworkImageView) imageLayout
                .findViewById(R.id.testimonials_image);
        DashBoardModel.Testinomial testinomial = testimoniels.get(position);
        imageView.setUrl(testinomial.getVoiceImage());

        ((TextView) imageLayout.findViewById(R.id.test_title)).setText(testinomial.getStudentName());

        ((TextView) imageLayout.findViewById(R.id.test_sub_title)).setText(testinomial.getCompany());

        SpannableString spannableStringBuilder = new SpannableString("\"" + testinomial.getVoiceDescription() + ",,");

        spannableStringBuilder.setSpan(new RelativeSizeSpan(2f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(2f), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((TextView) imageLayout.findViewById(R.id.test_content)).setText(testinomial.getVoiceDescription());


        view.addView(imageLayout, 0);

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
