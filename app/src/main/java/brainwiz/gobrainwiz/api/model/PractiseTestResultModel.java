package brainwiz.gobrainwiz.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PractiseTestResultModel extends BaseModel {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Parcelable {

        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("test_id")
        @Expose
        private int testId;
        @SerializedName("total_questions")
        @Expose
        private int totalQuestions;
        @SerializedName("attempted_questions")
        @Expose
        private int attemptedQuestions;
        @SerializedName("nonattempted_questions")
        @Expose
        private int nonattemptedQuestions;
        @SerializedName("correct")
        @Expose
        private int correct;
        @SerializedName("incorrect")
        @Expose
        private int incorrect;
        @SerializedName("total_marks")
        @Expose
        private int totalMarks;
        @SerializedName("total_time")
        @Expose
        private int totalTime;
        @SerializedName("rank")
        @Expose
        private int rank;

        @SerializedName("total_rank")
        @Expose
        private int totalRank;

        protected Data(Parcel in) {
            userId = in.readInt();
            testId = in.readInt();
            totalQuestions = in.readInt();
            attemptedQuestions = in.readInt();
            nonattemptedQuestions = in.readInt();
            correct = in.readInt();
            incorrect = in.readInt();
            totalMarks = in.readInt();
            totalTime = in.readInt();
            rank = in.readInt();
            totalRank = in.readInt();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTestId() {
            return testId;
        }

        public void setTestId(int testId) {
            this.testId = testId;
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

        public int getNonattemptedQuestions() {
            return nonattemptedQuestions;
        }

        public void setNonattemptedQuestions(int nonattemptedQuestions) {
            this.nonattemptedQuestions = nonattemptedQuestions;
        }

        public int getCorrect() {
            return correct;
        }

        public void setCorrect(int correct) {
            this.correct = correct;
        }

        public int getIncorrect() {
            return incorrect;
        }

        public void setIncorrect(int incorrect) {
            this.incorrect = incorrect;
        }

        public int getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(int totalMarks) {
            this.totalMarks = totalMarks;
        }

        public int getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(int totalTime) {
            this.totalTime = totalTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(userId);
            dest.writeInt(testId);
            dest.writeInt(totalQuestions);
            dest.writeInt(attemptedQuestions);
            dest.writeInt(nonattemptedQuestions);
            dest.writeInt(correct);
            dest.writeInt(incorrect);
            dest.writeInt(totalMarks);
            dest.writeInt(totalTime);
            dest.writeInt(rank);
            dest.writeInt(totalRank);
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(int totalRank) {
            this.totalRank = totalRank;
        }
    }
}
