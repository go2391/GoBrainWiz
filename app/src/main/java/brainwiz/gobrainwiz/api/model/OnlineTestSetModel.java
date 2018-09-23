package brainwiz.gobrainwiz.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class OnlineTestSetModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<TestSet> data = new ArrayList<>();


    public List<TestSet> getData() {
        return data;
    }

    public void setData(List<TestSet> data) {
        this.data = data;
    }

    public static class TestSet {

        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("cat_id")
        @Expose
        private String catId;
        private boolean completed;
        private boolean selected;

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }


}
