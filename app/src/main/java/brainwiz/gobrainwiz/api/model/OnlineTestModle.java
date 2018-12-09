package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OnlineTestModle extends BaseModel {
    @SerializedName("data")
    @Expose
    private List<Data> data = new ArrayList<Data>();


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }


    public static class Data {

        @SerializedName("companyName")
        @Expose
        private String companyName;

        @SerializedName("image_link")
        @Expose
        private String imageLink;
        @SerializedName("list")
        @Expose
        private java.util.List<TestList> list = new ArrayList<>();

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public java.util.List<TestList> getList() {
            return list;
        }

        public void setList(List<TestList> list) {
            this.list = list;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }
    }

    public static class TestList {

        @SerializedName("brain_test_id")
        @Expose
        private String brainTestId;
        @SerializedName("test_name")
        @Expose
        private String testName;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("test_image")
        @Expose
        private String testImage;
        @SerializedName("count_q")
        @Expose
        private String countQ;
        @SerializedName("TestStatus")
        @Expose
        private int status;


        @SerializedName("break_time")
        @Expose
        private String break_time;



        public String getBrainTestId() {
            return brainTestId;
        }

        public void setBrainTestId(String brainTestId) {
            this.brainTestId = brainTestId;
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

        public String getTestImage() {
            return testImage;
        }

        public void setTestImage(String testImage) {
            this.testImage = testImage;
        }

        public String getCountQ() {
            return countQ;
        }

        public void setCountQ(String countQ) {
            this.countQ = countQ;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBreak_time() {
            return break_time;
        }

        public void setBreak_time(String break_time) {
            this.break_time = break_time;
        }
    }
}
