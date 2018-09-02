package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoListModel extends BaseModel {

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

        @SerializedName("videos")
        @Expose
        private List<Video> videos = new ArrayList<Video>();

        public List<Video> getVideos() {
            return videos;
        }

        public void setVideos(List<Video> videos) {
            this.videos = videos;
        }
    }

    public static class Video {

        @SerializedName("topicName")
        @Expose
        private String topicName;
        @SerializedName("videosList")
        @Expose
        private List<VideosList> videosList = new ArrayList<VideosList>();

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public List<VideosList> getVideosList() {
            return videosList;
        }

        public void setVideosList(List<VideosList> videosList) {
            this.videosList = videosList;
        }

    }


    public static class VideosList {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("cid")
        @Expose
        private String cid;
        @SerializedName("video_format")
        @Expose
        private String videoFormat;
        @SerializedName("video_title")
        @Expose
        private String videoTitle;
        @SerializedName("video_desc")
        @Expose
        private String videoDesc;
        @SerializedName("like")
        @Expose
        private String like;
        @SerializedName("dislike")
        @Expose
        private String dislike;
        @SerializedName("views")
        @Expose
        private String views;
        @SerializedName("v_image")
        @Expose
        private String vImage;
        @SerializedName("youtube_link")
        @Expose
        private String youtubeLink;
        @SerializedName("cat_name")
        @Expose
        private String catName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getVideoFormat() {
            return videoFormat;
        }

        public void setVideoFormat(String videoFormat) {
            this.videoFormat = videoFormat;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public String getVideoDesc() {
            return videoDesc;
        }

        public void setVideoDesc(String videoDesc) {
            this.videoDesc = videoDesc;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getDislike() {
            return dislike;
        }

        public void setDislike(String dislike) {
            this.dislike = dislike;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getVImage() {
            return vImage;
        }

        public void setVImage(String vImage) {
            this.vImage = vImage;
        }

        public String getYoutubeLink() {
            return youtubeLink;
        }

        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

    }


}
