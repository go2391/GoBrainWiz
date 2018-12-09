package brainwiz.gobrainwiz.api.rest.service;


import java.util.HashMap;

import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.api.model.HistoryOnlineTestModel;
import brainwiz.gobrainwiz.api.model.HistoryPractiseTestModel;
import brainwiz.gobrainwiz.api.model.ImageUploadModel;
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.api.model.NotificationsModel;
import brainwiz.gobrainwiz.api.model.OnlineTestModle;
import brainwiz.gobrainwiz.api.model.OnlineTestSetModel;
import brainwiz.gobrainwiz.api.model.PracticeTestModel;
import brainwiz.gobrainwiz.api.model.PractiseTestResultModel;
import brainwiz.gobrainwiz.api.model.RegistrationModel;
import brainwiz.gobrainwiz.api.model.ScoreCardModel;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.api.model.TestsModel;
import brainwiz.gobrainwiz.api.model.VideoListModel;
import brainwiz.gobrainwiz.onlinetest.OnlineTestPostModel;
import brainwiz.gobrainwiz.practicetest.PractiseTestPostModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 */
public interface APIService {
    String URL = ".php";

    @POST("login" + URL)
    Call<LoginModel> login(@Body HashMap<String, String> hashMap);


    @POST("register" + URL)
    Call<RegistrationModel> register(@Body HashMap<String, String> hashMap);

    @POST("dashboard" + URL)
    Call<DashBoardModel> getDashBoard(@Body HashMap<String, String> hashMap);

    @GET("videos_list" + URL)
    Call<VideoListModel> getVideos();


//    @GET("videos_list"+URL+"?id=?")
//    Call<VideoListModel> getVideos();

    @GET("practice_test_content" + URL)
    Call<PracticeTestModel> getPractiveTestCategories();

    @POST("practise-list" + URL)
    Call<TestsModel> getTests(@Body HashMap<String, String> hashMap);

    @POST("companies_testList" + URL)
    Call<OnlineTestModle> getCompanies(@Body HashMap<String, String> hashMap);

    @POST("companies_testList" + URL)
    Call<OnlineTestSetModel> getCompanySets(@Body HashMap<String, String> hashMap);


    @POST("company_test" + URL)
    Call<TestModel> getCompanyTests(@Body HashMap<String, String> hashMap);

    @POST("practise_test" + URL)
    Call<TestModel> getPracticeTests(@Body HashMap<String, String> hashMap);

    @POST("test_history" + URL)
    Call<HistoryOnlineTestModel> getPastTests(@Body HashMap<String, String> baseBodyMap);


    @POST("practise_test_history" + URL)
    Call<HistoryPractiseTestModel> getPastPractiseTests(@Body HashMap<String, String> baseBodyMap);


    @POST("practise-list" + URL)
    Call<PractiseTestResultModel> postPracticeTest(@Body PractiseTestPostModel model);

    @POST("company_test" + URL)
    Call<ScoreCardModel> postOnlineTest(@Body OnlineTestPostModel model);

    @POST("send_enquiry" + URL)
    Call<BaseModel> postJoinRequest(@Body HashMap<String, String> baseBodyMap);

    //    upload_image
    @POST("upload_image" + URL)
    Call<ImageUploadModel> uploadImage(@Body HashMap<String, String> baseBodyMap);


    @POST("forgot_password" + URL)
    Call<BaseModel> resetPassword(@Body HashMap<String, String> baseBodyMap);


//    @POST("verifyOTP" + URL)
//    Call<RegistrationModel> verifyOTP(@Body HashMap<String, String> baseBodyMap);

    @POST("get_score_card" + URL)
    Call<ScoreCardModel> getScoreCard(@Body HashMap<String, String> baseBodyMap);

    @POST("get_score_card" + URL)
    Call<PractiseTestResultModel> getPractiseScoreCard(@Body HashMap<String, String> baseBodyMap);


    @POST("get_notifications_list" + URL)
    Call<NotificationsModel> getNotifications(@Body HashMap<String, String> baseBodyMap);


    @POST("get_notifications_list" + URL)
    Call<BaseModel> updateNotifications(@Body HashMap<String, String> baseBodyMap);
}
