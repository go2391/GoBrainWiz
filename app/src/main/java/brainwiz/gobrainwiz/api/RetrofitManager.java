package brainwiz.gobrainwiz.api;


import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import brainwiz.gobrainwiz.api.rest.service.APIService;
import brainwiz.gobrainwiz.api.rest.service.UserService;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
final public class RetrofitManager {

    static Retrofit retrofit = null;
    private UserService apiService;
    private APIService APIService;

    /**
     * This is the method which return object of RestApiMethods with full log.
     * Log is usefull for storing the record offLine.
     *
     * @return
     */
    public static APIService getRestApiMethods() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
            httpClient.connectTimeout(60000, TimeUnit.MILLISECONDS);

            httpClient.protocols(Arrays.asList(Protocol.HTTP_1_1));
            // Retrofit setup
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            // Service setup
        }
        return retrofit.create(APIService.class);
    }


    public APIService getAPIService() {
        return APIService;
    }
}
