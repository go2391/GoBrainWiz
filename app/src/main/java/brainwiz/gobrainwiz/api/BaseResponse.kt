package com.apshutters.salesperson.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Purushotam on 2/1/2017.
 */

class BaseResponse<T> {

    @SerializedName("responsecode")
    @Expose
    var responsecode: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("data")
    @Expose
    var data: T? = null

}