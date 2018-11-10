package brainwiz.gobrainwiz.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ScoreCardModel extends BaseModel {
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public static class Datum implements Parcelable {
        @SerializedName("log_id")
        @Expose
        private String logId;
        @SerializedName("test_id")
        @Expose
        private String testId;
        @SerializedName("cat_id")
        @Expose
        private String catId;
        @SerializedName("student_id")
        @Expose
        private String studentId;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("all_questions")
        @Expose
        private String allQuestions;
        @SerializedName("attempted_ques")
        @Expose
        private String attemptedQues;
        @SerializedName("unattempted_ques")
        @Expose
        private String unattemptedQues;
        @SerializedName("correct_answers")
        @Expose
        private int correctAnswers;
        @SerializedName("incorrect_answers")
        @Expose
        private int incorrectAnswers;
        @SerializedName("marks")
        @Expose
        private String marks;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("total_questions")
        @Expose
        private int totalQuestions;
        @SerializedName("attempted_questions")
        @Expose
        private int attemptedQuestions;
        @SerializedName("unattempted_questions")
        @Expose
        private int unattemptedQuestions;
        @SerializedName("total_marks")
        @Expose
        private int totalMarks;
        @SerializedName("time_attempt")
        @Expose
        private int timeAttempt;
        @SerializedName("success")
        @Expose
        private String success;
        public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            public Datum[] newArray(int size) {
                return (new Datum[size]);
            }

        }
                ;

        protected Datum(Parcel in) {
            this.logId = ((String) in.readValue((String.class.getClassLoader())));
            this.testId = ((String) in.readValue((String.class.getClassLoader())));
            this.catId = ((String) in.readValue((String.class.getClassLoader())));
            this.studentId = ((String) in.readValue((String.class.getClassLoader())));
            this.startTime = ((String) in.readValue((String.class.getClassLoader())));
            this.endTime = ((String) in.readValue((String.class.getClassLoader())));
            this.allQuestions = ((String) in.readValue((String.class.getClassLoader())));
            this.attemptedQues = ((String) in.readValue((String.class.getClassLoader())));
            this.unattemptedQues = ((String) in.readValue((String.class.getClassLoader())));
            this.correctAnswers = ((int) in.readValue((int.class.getClassLoader())));
            this.incorrectAnswers = ((int) in.readValue((int.class.getClassLoader())));
            this.marks = ((String) in.readValue((String.class.getClassLoader())));
            this.time = ((String) in.readValue((String.class.getClassLoader())));
            this.totalQuestions = ((int) in.readValue((int.class.getClassLoader())));
            this.attemptedQuestions = ((int) in.readValue((int.class.getClassLoader())));
            this.unattemptedQuestions = ((int) in.readValue((int.class.getClassLoader())));
            this.totalMarks = ((int) in.readValue((int.class.getClassLoader())));
            this.timeAttempt = ((int) in.readValue((int.class.getClassLoader())));
            this.success = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Datum() {
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getTestId() {
            return testId;
        }

        public void setTestId(String testId) {
            this.testId = testId;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAllQuestions() {
            return allQuestions;
        }

        public void setAllQuestions(String allQuestions) {
            this.allQuestions = allQuestions;
        }

        public String getAttemptedQues() {
            return attemptedQues;
        }

        public void setAttemptedQues(String attemptedQues) {
            this.attemptedQues = attemptedQues;
        }

        public String getUnattemptedQues() {
            return unattemptedQues;
        }

        public void setUnattemptedQues(String unattemptedQues) {
            this.unattemptedQues = unattemptedQues;
        }

        public int getCorrectAnswers() {
            return correctAnswers;
        }

        public void setCorrectAnswers(int correctAnswers) {
            this.correctAnswers = correctAnswers;
        }

        public int getIncorrectAnswers() {
            return incorrectAnswers;
        }

        public void setIncorrectAnswers(int incorrectAnswers) {
            this.incorrectAnswers = incorrectAnswers;
        }

        public String getMarks() {
            return marks;
        }

        public void setMarks(String marks) {
            this.marks = marks;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTotalQuestions() {
            return totalQuestions;
        }

        public void setTotalQuestions(int totalQuestions) {
            this.totalQuestions = totalQuestions;
        }

        public int getAttemptedQuestions() {
            return attemptedQuestions;
        }

        public void setAttemptedQuestions(int attemptedQuestions) {
            this.attemptedQuestions = attemptedQuestions;
        }

        public int getUnattemptedQuestions() {
            return unattemptedQuestions;
        }

        public void setUnattemptedQuestions(int unattemptedQuestions) {
            this.unattemptedQuestions = unattemptedQuestions;
        }

        public int getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(int totalMarks) {
            this.totalMarks = totalMarks;
        }

        public int getTimeAttempt() {
            return timeAttempt;
        }

        public void setTimeAttempt(int timeAttempt) {
            this.timeAttempt = timeAttempt;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(logId);
            dest.writeValue(testId);
            dest.writeValue(catId);
            dest.writeValue(studentId);
            dest.writeValue(startTime);
            dest.writeValue(endTime);
            dest.writeValue(allQuestions);
            dest.writeValue(attemptedQues);
            dest.writeValue(unattemptedQues);
            dest.writeValue(correctAnswers);
            dest.writeValue(incorrectAnswers);
            dest.writeValue(marks);
            dest.writeValue(time);
            dest.writeValue(totalQuestions);
            dest.writeValue(attemptedQuestions);
            dest.writeValue(unattemptedQuestions);
            dest.writeValue(totalMarks);
            dest.writeValue(timeAttempt);
            dest.writeValue(success);
        }

        public int describeContents() {
            return 0;
        }

    }


}
