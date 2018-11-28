package brainwiz.gobrainwiz;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.payumoney.core.PayUmoneySdkInitializer;

import brainwiz.gobrainwiz.utils.SharedPrefUtils;
import brainwiz.gobrainwiz.utils.URLImageParserNew;

public class SampleActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String webData = "<!DOCTYPE html><head> <meta http-equiv=\"Content-Type\" " +
                "content=\"text/html; charset=utf-8\"> <html><head><link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/mhtml.css' /><link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/bootstrap.css' /><meta http-equiv=\"content-type\" content=\"text/html; charset=windows-1250\">" +
                "<meta name=\"spanish press\" content=\"spain, spanish newspaper, news,economy,politics,sports\"><title></title></head><body id=\"body\">" +
                "<script src=\"http://www.myscript.com/a\"></script>%s</body></html>";


        String newString = "<!DOCTYPE html><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <html><head><link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/mhtml.css' /><link rel = 'stylesheet' href='http://itsbiz.000webhostapp.com/bootstrap.css' /><meta http-equiv=\"content-type\" content=\"text/html; charset=windows-1250\"><meta name=\"spanish press\" content=\"spain, spanish newspaper, news,economy,politics,sports\"><title></title></head><body id=\"body\"><p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mn>2</mn><mfrac><mn>2</mn><mn>7</mn></mfrac></math>&nbsp;days</p></body></html>";

        ((WebView) findViewById(R.id.webview)).loadData(webData, "text/html", "UTF-8");

        String htmlString = "<span style=\\\"font-family: verdana, geneva; font-size: 12pt;\\\">In the question given below one dice has been shown in different positions, whose sides have 1 to 6 digits printed on them Study the following positions and answer the following.<\\/span><\\/p>\\n\n" +
                "                                                                            <p>In the dice given below which number will be on the opposite side of 3?<\\/p>\\n\n" +
                "                                                                                <p>&nbsp;<\\/p>\\n\n" +
                "                                                                                    <p>&nbsp; \n" +
                "                                                                                        <span style=\\\"font-family: verdana, geneva; font-size: 12pt;\\\">\n" +
                "                                                                                            <img src=\\\"http:\\/\\/gobrainwiz.in\\/upload\\/images\\/1533969401_D1.png\\\" alt=\\\"\\\" width=\\\"375\\\" height=\\\"123\\\" \\/><\\/span><\\/p>";


        TextView textView = (TextView) findViewById(R.id.sampletextview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY, new URLImageParserNew(textView, this), null));
        } else {
            textView.setText(Html.fromHtml(htmlString, new URLImageParserNew(textView, this), null));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                String txnId = SharedPrefUtils.getStudentID(SampleActivity.this) + SharedPrefUtils.getUserName(SampleActivity.this) + System.currentTimeMillis();

                PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                        PayUmoneySdkInitializer.PaymentParam.Builder();
//                builder.setAmount(10)                          // Payment amount
//                        .setTxnId(txnId)                                             // Transaction ID
//                        .setPhone(SharedPrefUtils.getUserPhone(SampleActivity.this))                                           // User Phone number
//                        .setProductName("exam fee")                   // Product Name or description
//                        .setFirstName(SharedPrefUtils.getUserName(SampleActivity.this))                              // User First name
//                        .setEmail(SharedPrefUtils.getUserEmail(SampleActivity.this))                                            // User Email ID
//                        .setsUrl(appEnvironment.surl())                    // Success URL (surl)
//                        .setfUrl(appEnvironment.furl())                     //Failure URL (furl)
//                        .setUdf1(udf1)
//                        .setUdf2(udf2)
//                        .setUdf3(udf3)
//                        .setUdf4(udf4)
//                        .setUdf5(udf5)
//                        .setUdf6(udf6)
//                        .setUdf7(udf7)
//                        .setUdf8(udf8)
//                        .setUdf9(udf9)
//                        .setUdf10(udf10)
//                        .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
//                        .setKey(“enter merchant key”)                        // Merchant key
//                        .setMerchantId(“enter merchant ID”);             // Merchant ID

            }
        });
    }

}
