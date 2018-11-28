package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import brainwiz.gobrainwiz.R;

public class StringUtills {
    public static String getUrlString(String url, String[] values) {
        String urlResult = url;
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (value != null) {
                urlResult = urlResult.replaceFirst("~", value);
            }
            Log.i("string", url.indexOf("~") + "" + urlResult);
        }
        return urlResult;
    }

    public static String formatDecimal(float f) {
        return String.format("%.2f", f);
    }

    public static String formatDecimal(double f) {
        return String.format("%.2f", f);
    }

    @NonNull
    public static String formatMobileNo(String s) {
        String str = s.replaceAll("\\D+", "");

        if (str.length() > 3) {
            s = "(" + str.substring(0, 3) + ") " + str.substring(3, str.length());
        }
        if (str.length() > 6) {
            s = "(" + str.substring(0, 3) + ") " + str.substring(3, 6) + "-" + str.substring(6, str.length());
        }
        return s;
    }

    @NotNull
    public static String addSlashForCardExpiry(@NotNull String s) {
        String str = s.replaceAll("\\D+", "");
        if (str.length() > 2) {
            s = str.substring(0, 2) + "/" + str.substring(2, str.length());
        }
        return s;
    }

    public static SpannableString getSpanMarks(String s) {
        final SpannableString text = new SpannableString(s);
        text.setSpan(new RelativeSizeSpan(1.2f), 0, 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        text.setSpan(new ForegroundColorS+pan(Color.RED), 3, text.length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return text;
    }


    public static SpannableString getRankText(Context context, int rank, int totalRank) {
        String s = String.format(context.getString(R.string.your_rank), rank, totalRank);

        final SpannableString text = new SpannableString(s);
        text.setSpan(new RelativeSizeSpan(2f), 10, 10 + String.valueOf(rank).length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.blueSecondary)), 10, 10 + String.valueOf(rank).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        text.setSpan(new RelativeSizeSpan(1.2f), s.length() - String.valueOf(totalRank).length(), s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.blueSecondary)), s.length() - String.valueOf(totalRank).length(), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return text;
    }

    //<link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/bootstrap.css' />
    public static String getHtmlContent(String source) {
        String header = String.format("<!DOCTYPE html><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/mhtml.css' /><link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/bootstrap.css' /></head><body id=\"body\">");
        StringBuilder stringBuilder = new StringBuilder(header);
        stringBuilder.append(source);
        stringBuilder.append("</body></html>");

        return stringBuilder.toString();
    }


}

