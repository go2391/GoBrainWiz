package brainwiz.gobrainwiz.api.rest.service;


import java.util.HashMap;

import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.api.model.HistoryOnlineTestModel;
import brainwiz.gobrainwiz.api.model.HistoryPractiseTestModel;
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
    @POST("/dashboard")
    Call<DashBoardModel> getDashBoard(@Body HashMap<String, String> hashMap);

    @POST("/login")
    Call<LoginModel> login(@Body HashMap<String, String> hashMap);


    @POST("/register")
    Call<RegistrationModel> register(@Body HashMap<String, String> hashMap);

    @GET("/videos")
    Call<VideoListModel> getVideos();

    @GET("/practice_tests/2")
    Call<PracticeTestModel> getPractiveTestCategories();

    @POST("/practice")
    Call<TestsModel> getTests(@Body HashMap<String, String> hashMap);

    @POST("/company")
    Call<OnlineTestModle> getCompanies(@Body HashMap<String, String> hashMap);

    @POST("/company_sets")
    Call<OnlineTestSetModel> getCompanySets(@Body HashMap<String, String> hashMap);


    @POST("/company-test")
    Call<TestModel> getCompanyTests(@Body HashMap<String, String> hashMap);

    @POST("/practise-test")
    Call<TestModel> getPracticeTests(@Body HashMap<String, String> hashMap);

    @POST("/past_tests_company")
    Call<HistoryOnlineTestModel> getPastTests(@Body HashMap<String, String> baseBodyMap);


    @POST("/past_tests_practise")
    Call<HistoryPractiseTestModel> getPastPractiseTests(@Body HashMap<String, String> baseBodyMap);


    @POST("/practice-save")
    Call<PractiseTestResultModel> postPracticeTest(@Body PractiseTestPostModel model);

    @POST("/company-test")
    Call<ScoreCardModel> postOnlineTest(@Body OnlineTestPostModel model);

    @POST("/send_enquiry")
    Call<BaseModel> postJoinRequest(@Body HashMap<String, String> baseBodyMap);

    //    upload_image
    @POST("/upload_image")
    Call<BaseModel> uploadImage(@Body HashMap<String, String> baseBodyMap);


    @POST("/forgot")
    Call<BaseModel> resetPassword(@Body HashMap<String, String> baseBodyMap);


    @POST("/verifyOTP")
    Call<RegistrationModel> verifyOTP(@Body HashMap<String, String> baseBodyMap);

    @POST("/score-card")
    Call<ScoreCardModel> getScoreCard(@Body HashMap<String, String> baseBodyMap);

    @POST("/score-card")
    Call<PractiseTestResultModel> getPractiseScoreCard(@Body HashMap<String, String> baseBodyMap);


    @POST("/notification-history")
    Call<NotificationsModel> getNotifications(@Body HashMap<String, String> baseBodyMap);
}
