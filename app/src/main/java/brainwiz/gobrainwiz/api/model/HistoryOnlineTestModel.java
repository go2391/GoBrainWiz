package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoryOnlineTestModel extends BaseModel {
    @SerializedName("data")
    @Expose
    private List<TestHistory> data = new ArrayList<TestHistory>();

    public List<TestHistory> getData() {
        return data;
    }

    public void setData(List<TestHistory> data) {
        this.data = data;
    }


    public static class TestHistory {

        @SerializedName("test_id")
        @Expose
        private String testId;
        @SerializedName("totalSets")
        @Expose
        private String totalSets;
        @SerializedName("test_name")
        @Expose
        private String testName;

        @SerializedName("duration")
        @Expose
        private String duration = "";


        public String getTestId() {
            return testId;
        }

        public void setTestId(String testId) {
            this.testId = testId;
        }

        public String getTotalSets() {
            return totalSets;
        }

        public void setTotalSets(String totalSets) {
            this.totalSets = totalSets;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
