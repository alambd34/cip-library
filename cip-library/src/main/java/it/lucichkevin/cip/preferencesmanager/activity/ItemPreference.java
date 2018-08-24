package it.lucichkevin.cip.preferencesmanager.activity;

import android.content.Context;
import android.preference.Preference;

import java.util.ArrayList;


/**
 *  Describe a item of preferences list
 *
 *  @author	Kevin Lucich 11/09/2014
 *  @since	CipLibrary v0.5.0
 *  @update	2018-08-08
 */
public class ItemPreference {

	public static final int TYPE_SWITCH = 1;		//  Boolean
	public static final int TYPE_LIST = 2;			//  Boolean
	public static final int TYPE_TIMEPICKER = 10;	//  TimePicker
	public static final int TYPE_DATEPICKER = 11;	//  DatePicker
	public static final int TYPE_MINUTEPICKER = 12;	//  MinutePicker

	protected String key;
	protected ArrayList<PreferenceListEntry> entries_list = new ArrayList<>();
	protected String title;
	protected String summary;
	protected int type;
	protected Object default_value;
	protected Preference.OnPreferenceChangeListener onPreferenceChangeListener;
	protected Preference.OnPreferenceClickListener onPreferenceClickListener;
	protected static Context context = null;

	//  null OnPreferenceChangeListener
	public ItemPreference( String key, int title, int summary, int type ){
		this( key, context.getString(title), context.getString(summary), type, null, null, null );
	}

	public ItemPreference( String key, int title, int summary, int type, Object default_value ){
		this( key, context.getString(title), context.getString(summary), type, null, null, default_value );
	}

	//  OnPreferenceChangeListener set
	public ItemPreference( String key, int title, int summary, int type, Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( key, context.getString(title), context.getString(summary), type, changeListener, clickListener, null );
	}

	//  null OnPreferenceChangeListener
	public ItemPreference( String key, String title, String summary, int type ){
		this( key, title, summary, type, null, null, null );
	}

	//  null OnPreferenceChangeListener
	public ItemPreference( String key, String title, String summary, int type, Object default_value ){
		this( key, title, summary, type, null, null, default_value );
	}

	public ItemPreference( String key, String title, String summary, int type, Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Object default_value ){
		setKey(key);
		setTitle(title);
		setSummary(summary);
		setType(type);
		setOnPreferenceChangeListener(changeListener);
		setOnPreferenceClickListener(clickListener);
		setDefaultValue(default_value);
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

	public ArrayList<PreferenceListEntry> getEntriesList() {
		return entries_list;
	}
	// Add an entry into the list of ListPreference
	public void addEntry( PreferenceListEntry entry ){
		this.entries_list.add(entry);
	}
	public void setEntriesList( ArrayList<PreferenceListEntry> entries_list ){
		this.entries_list = entries_list;
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

	public Object getDefaultValue(){
		return this.default_value;
	}
	public void setDefaultValue( Object default_value ){
		this.default_value = default_value;
	}

	public static void setContext( Context _context ){
		context = _context;
	}





	public static class PreferenceListEntry {

		protected String label;
		protected String value;

		public PreferenceListEntry( String label, String value ){
			this.label = label;
			this.value = value;
		}

		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
