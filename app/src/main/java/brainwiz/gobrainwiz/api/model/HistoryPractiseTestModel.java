package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoryPractiseTestModel extends BaseModel {


    @SerializedName("data")
    @Expose
    private List<PractiseTestHistory> data = new ArrayList<PractiseTestHistory>();

    public List<PractiseTestHistory> getData() {
        return data;
    }

    public void setData(List<PractiseTestHistory> data) {
        this.data = data;
    }


    public static class PractiseTestHistory {

        @SerializedName("test_id")
        @Expose
        private String testId;
        @SerializedName("test_name")
        @Expose
        private String testName;
        @SerializedName("total_questions")
        @Expose
        private String totalQuestions;
        @SerializedName("attempted")
        @Expose
        private String attempted;
        @SerializedName("not_attempted")
        @Expose
        private String notAttempted;
        @SerializedName("correct")
        @Expose
        private String correct;
        @SerializedName("wrong")
        @Expose
        private String wrong;
        @SerializedName("marks")
        @Expose
        private String marks;

        public String getTestId() {
            return testId;
        }

        public void setTestId(String testId) {
            this.testId = testId;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getTotalQuestions() {
            return totalQuestions;
        }

        public void setTotalQuestions(String totalQuestions) {
            this.totalQuestions = totalQuestions;
        }

        public String getAttempted() {
            return attempted;
        }

        public void setAttempted(String attempted) {
            this.attempted = attempted;
        }

        public String getNotAttempted() {
            return notAttempted;
        }

        public void setNotAttempted(String notAttempted) {
            this.notAttempted = notAttempted;
        }

        public String getCorrect() {
            return correct;
        }

        public void setCorrect(String correct) {
            this.correct = correct;
        }

        public String getWrong() {
            return wrong;
        }

        public void setWrong(String wrong) {
            this.wrong = wrong;
        }

        public String getMarks() {
            return marks;
        }

        public void setMarks(String marks) {
            this.marks = marks;
        }

    }
}
