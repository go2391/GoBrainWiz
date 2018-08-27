package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class BaseModel {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
