package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginModel extends BaseModel {

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

        @SerializedName("examuser_id")
        @Expose
        private String examuserId;
        @SerializedName("examuser_name")
        @Expose
        private String examuserName;
        @SerializedName("examuser_mobile")
        @Expose
        private String examuserMobile;
        @SerializedName("examuser_email")
        @Expose
        private String examuserEmail;
        @SerializedName("examUser_token")
        @Expose
        private String examUserToken;
        @SerializedName("examuser_cname")
        @Expose
        private String examuser_cname;

        @SerializedName("status")
        @Expose
        private boolean status;
        @SerializedName("code")
        @Expose
        private int code;
        @SerializedName("message")
        @Expose
        private String message;

        public String getExamuserId() {
            return examuserId;
        }

        public void setExamuserId(String examuserId) {
            this.examuserId = examuserId;
        }

        public String getExamuserName() {
            return examuserName;
        }

        public void setExamuserName(String examuserName) {
            this.examuserName = examuserName;
        }

        public String getExamuserMobile() {
            return examuserMobile;
        }

        public void setExamuserMobile(String examuserMobile) {
            this.examuserMobile = examuserMobile;
        }

        public String getExamuserEmail() {
            return examuserEmail;
        }

        public void setExamuserEmail(String examuserEmail) {
            this.examuserEmail = examuserEmail;
        }

        public String getExamUserToken() {
            return examUserToken;
        }

        public void setExamUserToken(String examUserToken) {
            this.examUserToken = examUserToken;
        }

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

        public String getExamuser_cname() {
            return examuser_cname;
        }

        public void setExamuser_cname(String examuser_cname) {
            this.examuser_cname = examuser_cname;
        }
    }
}



