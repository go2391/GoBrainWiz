package brainwiz.gobrainwiz.api;


import com.apshutters.salesperson.api.RestApiMethods;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
final public class RetrofitInterface {

    static Retrofit retrofit = null;

    /**
     * This is the method which return object of RestApiMethods with full log.
     * Log is usefull for storing the record offLine.
     *
     * @return
     */
    public static RestApiMethods getRestApiMethods() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
            httpClient.connectTimeout(60000, TimeUnit.MILLISECONDS);
            // Retrofit setup
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            // Service setup
        }
        return retrofit.create(RestApiMethods.class);
    }
}
