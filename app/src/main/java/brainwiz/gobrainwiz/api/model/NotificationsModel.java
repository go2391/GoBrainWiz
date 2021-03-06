package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotificationsModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("log_id")
        @Expose
        private String logId;
        @SerializedName("student_id")
        @Expose
        private String studentId;
        @SerializedName("notification_id")
        @Expose
        private String notificationId;
        @SerializedName("checked")
        @Expose
        private String checked;
        @SerializedName("nid")
        @Expose
        private String nid;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("time_update")
        @Expose
        private String timeUpdate;
        @SerializedName("notify_type")
        @Expose
        private int type;
        @SerializedName("catid")
        @Expose
        private String catid;
        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("catname")
        @Expose
        private String catname;

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTimeUpdate() {
            return timeUpdate;
        }

        public void setTimeUpdate(String timeUpdate) {
            this.timeUpdate = timeUpdate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCatname() {
            return catname;
        }

        public void setCatname(String catname) {
            this.catname = catname;
        }
    }
}
