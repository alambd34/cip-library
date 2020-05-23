package it.lucichkevin.cip.preferences;

public interface Preference {

	Object getDefaultValue();
	void setDefaultValue( Object default_value );

	String getKey();
	CharSequence getTitle();
	CharSequence getSummary();

	androidx.preference.Preference.OnPreferenceChangeListener getOnPreferenceChangeListener();
	androidx.preference.Preference.OnPreferenceClickListener getOnPreferenceClickListener();


	//	Simplify writing code :)
	interface OnPreferenceChangeListener extends androidx.preference.Preference.OnPreferenceChangeListener {
        boolean onPreferenceChange( androidx.preference.Preference preference, Object newValue);
	}
	interface OnPreferenceClickListener extends androidx.preference.Preference.OnPreferenceClickListener {
        boolean onPreferenceClick( androidx.preference.Preference preference);
	}

}
