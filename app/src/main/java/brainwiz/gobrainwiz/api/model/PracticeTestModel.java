package brainwiz.gobrainwiz.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PracticeTestModel extends BaseModel {

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

        @SerializedName("1")
        @Expose
        private TopicModel arithmetic;

        @SerializedName("2")
        @Expose
        private TopicModel topicModel;

        public TopicModel getArithmetic() {
            return arithmetic;
        }

        public void setArithmetic(TopicModel arithmetic) {
            this.arithmetic = arithmetic;
        }

        public TopicModel getOgicalReasoning() {
            return topicModel;
        }

        public void setOgicalReasoning(TopicModel ogicalReasoning) {
            this.topicModel = ogicalReasoning;
        }


    }


    public static class TopicModel {

        @SerializedName("topic_id")
        @Expose
        private String topicId;
        @SerializedName("topic_name")
        @Expose
        private String topicName;
        @SerializedName("topic_count")
        @Expose
        private int topicCount;
        @SerializedName("tests_count")
        @Expose
        private int testsCount;
        @SerializedName("subLists")
        @Expose
        private List<SubList> subLists = new ArrayList<SubList>();

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public int getTopicCount() {
            return topicCount;
        }

        public void setTopicCount(int topicCount) {
            this.topicCount = topicCount;
        }

        public int getTestsCount() {
            return testsCount;
        }

        public void setTestsCount(int testsCount) {
            this.testsCount = testsCount;
        }

        public List<SubList> getSubLists() {
            return subLists;
        }

        public void setSubLists(List<SubList> subLists) {
            this.subLists = subLists;
        }

    }


    public class SubList {

        @SerializedName("topic_name")
        @Expose
        private String topicName;
        @SerializedName("topic_id")
        @Expose
        private String topicId;
        @SerializedName("tests_count")
        @Expose
        private String testsCount;

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getTestsCount() {
            return testsCount;
        }

        public void setTestsCount(String testsCount) {
            this.testsCount = testsCount;
        }

    }
}