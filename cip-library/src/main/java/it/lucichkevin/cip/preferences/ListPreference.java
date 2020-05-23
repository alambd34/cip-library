package it.lucichkevin.cip.preferences;


import androidx.preference.Preference;

import java.util.ArrayList;

import it.lucichkevin.cip.Utils;


public class ListPreference extends AbstractPreference {


	protected ArrayList<Entry> entries_list = new ArrayList<>();


	public ListPreference(String key, int title, int summary ) {
		super( key, title, summary );
	}
	public ListPreference(String key, int title, int summary, String default_value ){
		super( key, title, summary, default_value );
	}
	public ListPreference(String key, int title, int summary, OnListPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), changeListener, clickListener, false );
	}
	public ListPreference(String key, String title, String summary, final OnListPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Boolean default_value) {
		super( key, title, summary, null, clickListener, default_value );

		onPreferenceChangeListener = new Preference.OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange( Preference preference, Object newValue ){
				return changeListener.onPreferenceChange(preference, String.valueOf(newValue) );
			}
		};
	}


	public String getDefaultValue(){
		return (String) super.getDefaultValue();
	}
	public void setDefaultValue( String default_value ){
		super.setDefaultValue(default_value);
	}

	public ArrayList<Entry> getEntriesList() {
		return entries_list;
	}
	// Add an entry into the list of ListPreference
	public void addEntry( Entry entry ){
		this.entries_list.add(entry);
	}
	public void setEntriesList( ArrayList<Entry> entries_list ){
		this.entries_list = entries_list;
	}




	public static class Entry {

		//	The value will be saved into preference
		private String value;

		//	The label will be showed to the user
		private String label;

		public Entry( String value, String label ){
			this.value = value;
			this.label = label;
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


	public static abstract class OnListPreferenceChangeListener implements Preference.OnPreferenceChangeListener {

		public abstract boolean onListPreferenceChange( androidx.preference.Preference preference, String newValue );

		@Override
		public boolean onPreferenceChange( androidx.preference.Preference preference, Object newValue ){
			return onListPreferenceChange( preference, (String) newValue );
		}
	}

}
