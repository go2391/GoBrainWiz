package brainwiz.gobrainwiz.onlinetest;


import java.util.ArrayList;
import java.util.List;

public class OnlineTestPostModel {

    private int student_id;
    private String device_id;
    private String token;
    private int test_id;
    private int set_id;
    private List<Question> questions = new ArrayList<Question>();

    public int getStudentId() {
        return student_id;
    }

    public void setStudentId(int studentId) {
        this.student_id = studentId;
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

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
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
        private String time_taken_in;
        private String time_taken_out;
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

        public String getTime_taken_in() {
            return time_taken_in;
        }

        public void setTime_taken_in(String time_taken_in) {
            this.time_taken_in = time_taken_in;
        }

        public String getTime_taken_out() {
            return time_taken_out;
        }

        public void setTime_taken_out(String time_taken_out) {
            this.time_taken_out = time_taken_out;
        }

        public int getTime_taken() {
            return time_taken;
        }

        public void setTime_taken(int time_taken) {
            this.time_taken = time_taken;
        }

    }
}