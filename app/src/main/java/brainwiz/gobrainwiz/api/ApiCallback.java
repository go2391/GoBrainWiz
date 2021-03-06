package brainwiz.gobrainwiz.api;

import android.app.Activity;
import android.util.Log;

import brainwiz.gobrainwiz.BaseActivity;
import brainwiz.gobrainwiz.api.model.BaseModel;
import brainwiz.gobrainwiz.utils.DDAlerts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */

public abstract class ApiCallback<T> implements Callback<T> {

    Activity context;

    public ApiCallback(final Activity context) {
        this.context = context;
    }

    public abstract void onApiResponse(Response<T> response, boolean isSuccess, String message);

    public abstract void onApiFailure(boolean isSuccess, String message);

    @Override
    public void onResponse(Call<T> call, final Response<T> response) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.code() == 400) {
                    onApiFailure(false, "Server not responding");
                    DDAlerts.showToast(context, "Server not responding");
                }
                if (((BaseModel) response.body()).getCode() == 601) {
                    ((BaseActivity) context).onUserError(context,((BaseModel) response.body()).getMessage());
                } else {
                    Log.i("API Response", response.toString());
                    onApiResponse(response, ((BaseModel) response.body()).getCode() == 200, "");
                }
            }
        });
    }

    @Override
    public void onFailure(Call<T> call, final Throwable t) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onApiFailure(false, "");
            }
        });

    }


}
