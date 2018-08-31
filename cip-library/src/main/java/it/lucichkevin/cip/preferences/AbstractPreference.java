package it.lucichkevin.cip.preferences;

import it.lucichkevin.cip.Utils;


public abstract class AbstractPreference implements Preference {

	protected String key;
	protected String title;
	protected String summary;
	protected Object default_value;
	protected android.preference.Preference.OnPreferenceChangeListener onPreferenceChangeListener;
	protected android.preference.Preference.OnPreferenceClickListener onPreferenceClickListener;


	//  null OnPreferenceChangeListener
	public AbstractPreference( String key, int title, int summary ){
		this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), null, null, null );
	}

	public AbstractPreference( String key, int title, int summary, Object default_value ){
		this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), null, null, default_value );
	}

	//  OnPreferenceChangeListener set
	public AbstractPreference(String key, int title, int summary, android.preference.Preference.OnPreferenceChangeListener changeListener, android.preference.Preference.OnPreferenceClickListener clickListener ){
		this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), changeListener, clickListener, null );
	}

	protected AbstractPreference(String key, String title, String summary, android.preference.Preference.OnPreferenceChangeListener changeListener, android.preference.Preference.OnPreferenceClickListener clickListener, Object default_value ){
		setKey(key);
		setTitle(title);
		setSummary(summary);
		setDefaultValue(default_value);
		setOnPreferenceChangeListener(changeListener);
		setOnPreferenceClickListener(clickListener);
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

	public Object getDefaultValue(){
		return this.default_value;
	}
	public void setDefaultValue( Object default_value ){
		this.default_value = default_value;
	}

	public android.preference.Preference.OnPreferenceChangeListener getOnPreferenceChangeListener() {
		return onPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener(android.preference.Preference.OnPreferenceChangeListener onPreferenceChangeListener) {
		this.onPreferenceChangeListener = onPreferenceChangeListener;
	}

	public android.preference.Preference.OnPreferenceClickListener getOnPreferenceClickListener() {
		return onPreferenceClickListener;
	}
	public void setOnPreferenceClickListener(android.preference.Preference.OnPreferenceClickListener onPreferenceClickListener) {
		this.onPreferenceClickListener = onPreferenceClickListener;
	}
}
