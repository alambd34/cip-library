package it.lucichkevin.cip.preferences;


import android.preference.Preference;

import it.lucichkevin.cip.Utils;

public class SwitchPreference extends AbstractPreference {

	protected OnSwitchPreferenceChangeListener onSwitchPreferenceChangeListener;


	public SwitchPreference( String key, int title, int summary ) {
		super( key, title, summary );
	}
	public SwitchPreference( String key, int title, int summary, Object default_value ){
		super( key, title, summary, default_value );
	}
	public SwitchPreference( String key, int title, int summary, OnSwitchPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( key, Utils.getContext().getString(title), Utils.getContext().getString(summary), changeListener, clickListener, false );
	}
	public SwitchPreference(String key, String title, String summary, final OnSwitchPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Boolean default_value) {
		super( key, title, summary, null, clickListener, default_value );

		onPreferenceChangeListener = changeListener;
	}


	public Boolean getDefaultValue(){
		return (Boolean) super.getDefaultValue();
	}

	public void setDefaultValue( Boolean default_value ){
		super.setDefaultValue(default_value);
	}




	public static abstract class OnSwitchPreferenceChangeListener implements Preference.OnPreferenceChangeListener {

		public abstract boolean onPreferenceChange( Preference preference, Boolean newValue );

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			return onPreferenceChange(preference, (Boolean) newValue );
		}
	}

}
