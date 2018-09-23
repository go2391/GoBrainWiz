package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DashBoardModel extends BaseModel {

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

        @SerializedName("banners")
        @Expose
        private List<Banner> banners = new ArrayList<Banner>();
        @SerializedName("testinomials")
        @Expose
        private List<Testinomial> testinomials = new ArrayList<Testinomial>();

        @SerializedName("weekSchedule")
        @Expose
        private WeekSchedule weekSchedule = new WeekSchedule();

        public List<Banner> getBanners() {
            return banners;
        }

        public void setBanners(List<Banner> banners) {
            this.banners = banners;
        }

        public List<Testinomial> getTestinomials() {
            return testinomials;
        }

        public void setTestinomials(List<Testinomial> testinomials) {
            this.testinomials = testinomials;
        }

        public WeekSchedule getWeekSchedule() {
            return weekSchedule;
        }

        public void setWeekSchedule(WeekSchedule weekSchedule) {
            this.weekSchedule = weekSchedule;
        }
    }

    public static class Banner {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("banner_name")
        @Expose
        private String bannerName;
        @SerializedName("banner_location")
        @Expose
        private String bannerLocation;
        @SerializedName("banner_status")
        @Expose
        private String bannerStatus;
        @SerializedName("banner_position")
        @Expose
        private String bannerPosition;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBannerName() {
            return bannerName;
        }

        public void setBannerName(String bannerName) {
            this.bannerName = bannerName;
        }

        public String getBannerLocation() {
            return bannerLocation;
        }

        public void setBannerLocation(String bannerLocation) {
            this.bannerLocation = bannerLocation;
        }

        public String getBannerStatus() {
            return bannerStatus;
        }

        public void setBannerStatus(String bannerStatus) {
            this.bannerStatus = bannerStatus;
        }

        public String getBannerPosition() {
            return bannerPosition;
        }

        public void setBannerPosition(String bannerPosition) {
            this.bannerPosition = bannerPosition;
        }

    }


    public static class Testinomial {

        @SerializedName("voice_id")
        @Expose
        private String voiceId;
        @SerializedName("student_name")
        @Expose
        private String studentName;
        @SerializedName("voice_description")
        @Expose
        private String voiceDescription;
        @SerializedName("voice_image")
        @Expose
        private String voiceImage;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("voice_status")
        @Expose
        private String voiceStatus;

        public String getVoiceId() {
            return voiceId;
        }

        public void setVoiceId(String voiceId) {
            this.voiceId = voiceId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getVoiceDescription() {
            return voiceDescription;
        }

        public void setVoiceDescription(String voiceDescription) {
            this.voiceDescription = voiceDescription;
        }

        public String getVoiceImage() {
            return voiceImage;
        }

        public void setVoiceImage(String voiceImage) {
            this.voiceImage = voiceImage;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getVoiceStatus() {
            return voiceStatus;
        }

        public void setVoiceStatus(String voiceStatus) {
            this.voiceStatus = voiceStatus;
        }

    }

    public static class WeekSchedule {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
