package brainwiz.gobrainwiz.test;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.databinding.InflateAnswerOptionBinding;
import brainwiz.gobrainwiz.utils.LogUtils;
import brainwiz.gobrainwiz.utils.StringUtills;
import brainwiz.gobrainwiz.utils.URLImageParserNew;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionHolder> {

    private List<String> options = new ArrayList<>();
    private String[] optionsIndicator = new String[]{"A", "B", "C", "D", "E", "F"};

    private Context context;
    private int selectedOption = -1;
    private OptionListener listener;
    private boolean reviewMode;
    private TestModel.Data.Datum selectedData;


    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public OptionsAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionHolder(LayoutInflater.from(context).inflate(R.layout.inflate_answer_option, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.bind.optionNo.setText(optionsIndicator[position]);
        String source = options.get(position);
        source = StringUtills.getHtmlContent(source);
//        source = source.replaceAll("<\\/span><\\/p>", "");
        holder.bind.optionText.loadData(source, "text/html", "UTF-8");
//        LogUtils.e(source);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.bind.optionText.setText(Html.fromHtml(source, Html.FROM_HTML_OPTION_USE_CSS_COLORS, new URLImageParserNew(holder.bind.optionText, context), null));
//        } else {
//            holder.bind.optionText.setText(Html.fromHtml(source, new URLImageParserNew(holder.bind.optionText, context), null));
//        }
        String selectedOption = selectedData.getSelectedOption();

        boolean selected;
        if (isReviewMode()) {
            position = position + 1;
        } /*else {

        }*/
        selected = selectedOption != null && selectedOption.equals(String.valueOf(position));
        holder.bind.optionNo.setSelected(selected);
        holder.bind.optionLayout.setSelected(selected);

        if (isReviewMode()) {
            String selectedAnswer = selectedData.getSelectedAnswer();
            boolean selectedCorrect = selectedOption != null && selectedOption.equalsIgnoreCase(String.valueOf(position));
            holder.bind.optionNo.setSelected(selectedCorrect);
            holder.bind.optionIcon.setVisibility(View.VISIBLE);

//            if () {
            holder.bind.optionIcon.setImageResource(selected && !selectedAnswer.equalsIgnoreCase(String.valueOf(position)) ? R.drawable.ic_wrong : 0);
//            }
            if (selectedAnswer != null && selectedAnswer.equalsIgnoreCase(String.valueOf(position))) {
                holder.bind.optionIcon.setImageResource(R.drawable.ic_tick);
            }
        }

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void setListener(OptionListener listener) {
        this.listener = listener;
    }

    public OptionListener getListener() {
        return listener;
    }

    public void setReviewMode(boolean reviewMode) {
        this.reviewMode = reviewMode;
    }

    public boolean isReviewMode() {
        return reviewMode;
    }

    public void setSelectedData(TestModel.Data.Datum selectedData) {
        this.selectedData = selectedData;
    }

    public TestModel.Data.Datum getSelectedData() {
        return selectedData;
    }

    class OptionHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener, Handler.Callback {
        private static final int CLICK_ON_WEBVIEW = 10;
        InflateAnswerOptionBinding bind;
        private final Handler handler = new Handler(this);

        public OptionHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
            if (!isReviewMode()) {
                bind.selectionArea.setOnClickListener(this);
            }
//            bind.optionText.setWebViewClient(new WebViewClient() {
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    // do your handling codes here, which url is the requested url
//                    // probably you need to open that url rather than redirect:
////                    view.loadUrl(url);
//                    return false; // then it is not handled by default action
//                }
//            });

            bind.optionText.setEnabled(false);

//            bind.optionText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            bind.optionText.getSettings().setJavaScriptEnabled(true);
            bind.optionText.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            bind.optionText.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            bind.optionText.getSettings().setAppCacheEnabled(true);
            bind.optionText.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            bind.optionText.getSettings().setDomStorageEnabled(true);
            bind.optionText.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            bind.optionText.getSettings().setUseWideViewPort(true);
            bind.optionText.getSettings().setSavePassword(true);
            bind.optionText.getSettings().setSaveFormData(true);
//            bind.optionText.setOnTouchListener(this);
            bind.optionText.setScrollbarFadingEnabled(true);
            bind.optionText.getSettings().setEnableSmoothTransition(true);
            bind.optionText.setWebChromeClient(new WebChromeClient());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 200);
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {

                selectedOption = getAdapterPosition();
                selectedData.setSelectedOption(String.valueOf(selectedOption));
                if (listener != null) {
                    listener.onOptionSelected(selectedOption);
                }
                notifyDataSetChanged();
            }
        }

        @Override
        public boolean handleMessage(Message msg) {
//            Toast.makeText(this, "WebView clicked", Toast.LENGTH_SHORT).show();
            if (msg.what == CLICK_ON_WEBVIEW) {
                bind.optionLayout.performClick();
                return true;
            }
            return true;
        }
    }

    interface OptionListener {
        void onOptionSelected(int position);
    }
}
