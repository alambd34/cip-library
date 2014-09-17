package it.lucichkevin.cip.preferencesmanager.activity;

import android.preference.Preference;

import it.lucichkevin.cip.Utils;

/**
 *  Describe a item of prefereces list
 *
 *  @author     Kevin Lucich 11/09/2014
 *  @since      CipLibrary v0.5.0
 */
public class ItemPreference {

    public static final int TYPE_SWITCH = 1;         //  Boolean
    public static final int TYPE_TIMEPICKER = 10;    //  TimePicker
    public static final int TYPE_DATEPICKER = 11;    //  DatePicker
    public static final int TYPE_MINUTEPICKER = 12;    //  MinutePicker

    private String key;
    private String title;
    private String summary;
    private int type;
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener;
    private Preference.OnPreferenceClickListener onPreferenceClickListener;

    //  null OnPreferenceChangeListener
    public ItemPreference( String key, int title, int summary, int type ){
        this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), type, null, null );
    }

    //  OnPreferenceChangeListener set
    public ItemPreference( String key, int title, int summary, int type, Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
        this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), type, changeListener, clickListener );
    }

    //  null OnPreferenceChangeListener
    public ItemPreference( String key, String title, String summary, int type ){
        this( key, title, summary, type, null, null );
    }

    public ItemPreference( String key, String title, String summary, int type, Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
        this.key = key;
        this.title = title;
        this.summary = summary;
        this.type = type;
        this.onPreferenceChangeListener = changeListener;
        this.onPreferenceClickListener = clickListener;
    }


    /////////////////////////////////////////
    //  Getters and setters

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

    public Preference.OnPreferenceChangeListener getOnPreferenceChangeListener() {
        return onPreferenceChangeListener;
    }
    public void setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener onPreferenceChangeListener) {
        this.onPreferenceChangeListener = onPreferenceChangeListener;
    }

    public Preference.OnPreferenceClickListener getOnPreferenceClickListener() {
        return onPreferenceClickListener;
    }
    public void setOnPreferenceClickListener(Preference.OnPreferenceClickListener onPreferenceClickListener) {
        this.onPreferenceClickListener = onPreferenceClickListener;
    }

}
