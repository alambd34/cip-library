package it.lucichkevin.cip.preferencesmanager.activity;

public class ItemPreferece{

    public static final int TYPE_SWITCH = 1;         //  Boolean
    public static final int TYPE_TIMEPICKER = 10;    //  TimePicker
    public static final int TYPE_DATEPICKER = 11;    //  DatePicker

    private String key;
    private String title;
    private String summary;
    private int type;
    private OnChangeListener onChangeListener;

    public ItemPreferece( String key, String title, String summary, int type ){
        this.key = key;
        this.title = title;
        this.summary = summary;
        this.type = type;
    }


    //////////////////


    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    //  Callback
    public static interface OnChangeListener{
        public void onChange();
    }
}
