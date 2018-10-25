package brainwiz.gobrainwiz.practicetest;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PractiseTestPostModel {

    @SerializedName("student_id")
    @Expose
    private int studentId;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("examTest_id")
    @Expose
    private int examTestId;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = new ArrayList<Question>();

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExamTestId() {
        return examTestId;
    }

    public void setExamTestId(int examTestId) {
        this.examTestId = examTestId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public static class Question {

        @SerializedName("q_id")
        @Expose
        private String qId;
        @SerializedName("selected_option")
        @Expose
        private int selectedOption;
        @SerializedName("time_taken")
        @Expose
        private int timeTaken;

        public String getQId() {
            return qId;
        }

        public void setQId(String qId) {
            this.qId = qId;
        }

        public int getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(int selectedOption) {
            this.selectedOption = selectedOption;
        }

        public int getTimeTaken() {
            return timeTaken;
        }

        public void setTimeTaken(int timeTaken) {
            this.timeTaken = timeTaken;
        }

    }
}