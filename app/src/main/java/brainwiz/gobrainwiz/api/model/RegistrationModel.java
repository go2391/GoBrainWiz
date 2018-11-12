package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationModel extends BaseModel {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("data")
        @Expose
        private Data_ data;

        public Data_ getData() {
            return data;
        }

        public void setData(Data_ data) {
            this.data = data;
        }

    }

    public class Data_ {

        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
