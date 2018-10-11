package brainwiz.gobrainwiz.api.rest.service;


import com.apshutters.salesperson.model.BaseResponse;

import java.util.HashMap;

import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.api.model.OnlineTestModle;
import brainwiz.gobrainwiz.api.model.OnlineTestSetModel;
import brainwiz.gobrainwiz.api.model.PracticeTestModel;
import brainwiz.gobrainwiz.api.model.TestModel;
import brainwiz.gobrainwiz.api.model.TestsModel;
import brainwiz.gobrainwiz.api.model.VideoListModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 */
public interface APIService {
    @GET("/dashboard")
    Call<DashBoardModel> getDashBoard();

    @POST("/login")
    Call<LoginModel> login(@Body HashMap<String, String> hashMap);


    @POST("/register")
    Call<DashBoardModel> register(@Body HashMap<String, String> hashMap);

    @GET("/videos")
    Call<VideoListModel> getVideos();

    @GET("/practice_tests/2")
    Call<PracticeTestModel> getPractiveTestCategories();

    @GET("/practice/{id}")
    Call<TestsModel> getTests(@Path("id") String id);

    @POST("/company")
    Call<OnlineTestModle> getCompanies(@Body HashMap<String, String> hashMap);

    @POST("/company_sets")
    Call<OnlineTestSetModel> getCompanySets(@Body HashMap<String, String> hashMap);


    @GET("/company-test")
///21/16
    Call<TestModel> getCompanyTests(@Path(value = "idCatID", encoded = true) String id);

    @GET("/practise-test/{id}")
    Call<TestModel> getPracticeTests(@Path("id") String id);

    @POST("/past_tests")
    Call<BaseResponse> getPastTests();


}
