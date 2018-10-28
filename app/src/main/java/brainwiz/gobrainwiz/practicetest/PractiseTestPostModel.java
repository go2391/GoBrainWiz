package brainwiz.gobrainwiz.practicetest;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PractiseTestPostModel {

    private int student_id;
    private String device_id;
    private String token;
    private int examTest_id;
    private List<Question> questions = new ArrayList<Question>();

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExamTest_id() {
        return examTest_id;
    }

    public void setExamTest_id(int examTest_id) {
        this.examTest_id = examTest_id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public static class Question {

        private String q_id;
        private int selected_option;
        private int time_taken;

        public String getQId() {
            return q_id;
        }

        public void setQId(String qId) {
            this.q_id = qId;
        }

        public int getSelected_option() {
            return selected_option;
        }

        public void setSelected_option(int selected_option) {
            this.selected_option = selected_option;
        }

        public int getTime_taken() {
            return time_taken;
        }

        public void setTime_taken(int time_taken) {
            this.time_taken = time_taken;
        }

    }
}