package brainwiz.gobrainwiz.api.rest.service;


import brainwiz.gobrainwiz.api.model.DashBoardModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 */
public interface APIService {
    @GET("/dashboard")
    Call<DashBoardModel> getDashBoard();
}
