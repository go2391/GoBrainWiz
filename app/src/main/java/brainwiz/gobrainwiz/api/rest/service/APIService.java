package brainwiz.gobrainwiz.api.rest.service;


import java.util.HashMap;

import brainwiz.gobrainwiz.api.model.DashBoardModel;
import brainwiz.gobrainwiz.api.model.LoginModel;
import brainwiz.gobrainwiz.api.model.PracticeTestModel;
import brainwiz.gobrainwiz.api.model.TestsModel;
import brainwiz.gobrainwiz.api.model.VideoListModel;
import retrofit2.Call;
import retrofit2.Callback;
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


}
