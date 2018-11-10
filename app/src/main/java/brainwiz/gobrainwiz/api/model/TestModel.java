package brainwiz.gobrainwiz.api.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TestModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();


    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum implements Parcelable {

        @SerializedName("question_id")
        @Expose
        private String questionId;
        @SerializedName("selected_answer")
        @Expose
        private String selectedAnswer;
        @SerializedName("explanation")
        @Expose
        private String explanation;
        @SerializedName("videoLink")
        @Expose
        private String videoLink;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("options")
        @Expose
        private List<String> options = new ArrayList<String>();
        @SerializedName("selected_option")
        @Expose
        private String selectedOption = "";
        private boolean bookMark;
        private long spentTime;
        private long startTime;
        private long endTime;

        protected Datum(Parcel in) {
            questionId = in.readString();
            selectedAnswer = in.readString();
            explanation = in.readString();
            videoLink = in.readString();
            question = in.readString();
            options = in.createStringArrayList();
            selectedOption = in.readString();
            spentTime = in.readLong();
        }

        public final Creator<Datum> CREATOR = new Creator<Datum>() {
            @Override
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            @Override
            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getSelectedAnswer() {
            return selectedAnswer;
        }

        public void setSelectedAnswer(String selectedAnswer) {
            this.selectedAnswer = selectedAnswer;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public String getVideoLink() {
            return videoLink;
        }

        public void setVideoLink(String videoLink) {
            this.videoLink = videoLink;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(questionId);
            dest.writeString(selectedAnswer);
            dest.writeString(explanation);
            dest.writeString(videoLink);
            dest.writeString(question);
            dest.writeStringList(options);
            dest.writeString(selectedOption);
            dest.writeLong(spentTime);
        }

        public void setSelectedOption(String selectedOption) {
            this.selectedOption = selectedOption;
        }

        public String getSelectedOption() {
            return selectedOption;
        }

        public void setBookMark(boolean bookMark) {
            this.bookMark = bookMark;
        }

        public boolean isBookMark() {
            return bookMark;
        }

        public long getSpentTime() {
            return spentTime;
        }

        public void setSpentTime(long spentTime) {
            this.spentTime = spentTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
            calculateTotalTime();
        }

        private void calculateTotalTime() {
            spentTime = spentTime + startTime - endTime;
        }

        public long getEndTime() {
            return endTime;
        }
    }


}
