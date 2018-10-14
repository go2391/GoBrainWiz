package brainwiz.gobrainwiz.api.model;

public class HistoryOnlineTestModle extends BaseModel {
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

}
