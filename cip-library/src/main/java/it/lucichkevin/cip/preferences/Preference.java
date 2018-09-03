package it.lucichkevin.cip.preferences;

public interface Preference {

	Object getDefaultValue();
	void setDefaultValue( Object default_value );

	String getKey();
	CharSequence getTitle();
	CharSequence getSummary();

	android.preference.Preference.OnPreferenceChangeListener getOnPreferenceChangeListener();
	android.preference.Preference.OnPreferenceClickListener getOnPreferenceClickListener();


	//	Simplify writing code :)
	interface OnPreferenceChangeListener extends android.preference.Preference.OnPreferenceChangeListener {
        boolean onPreferenceChange( android.preference.Preference preference, Object newValue);
	}
	interface OnPreferenceClickListener extends android.preference.Preference.OnPreferenceClickListener {
        boolean onPreferenceClick( android.preference.Preference preference);
	}

}
