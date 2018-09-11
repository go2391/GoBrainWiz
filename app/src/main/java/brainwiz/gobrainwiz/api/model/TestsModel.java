package brainwiz.gobrainwiz.api.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestsModel {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<TestItem> data = new ArrayList<TestItem>();

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

    public List<TestItem> getData() {
        return data;
    }

    public void setData(List<TestItem> data) {
        this.data = data;
    }


    public class TestItem {

        @SerializedName("test_name")
        @Expose
        private String testName;
        @SerializedName("test_time")
        @Expose
        private String testTime;
        @SerializedName("test_id")
        @Expose
        private String testId;
        @SerializedName("countque")
        @Expose
        private String countque;

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getTestTime() {
            return testTime;
        }

        public void setTestTime(String testTime) {
            this.testTime = testTime;
        }

        public String getTestId() {
            return testId;
        }

        public void setTestId(String testId) {
            this.testId = testId;
        }

        public String getCountque() {
            return countque;
        }

        public void setCountque(String countque) {
            this.countque = countque;
        }

    }
}
