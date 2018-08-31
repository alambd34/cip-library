package it.lucichkevin.cip.preferences;

public interface Preference {

	Object getDefaultValue();
	void setDefaultValue( Object default_value );

	String getKey();
	CharSequence getTitle();
	CharSequence getSummary();

	android.preference.Preference.OnPreferenceChangeListener getOnPreferenceChangeListener();
	android.preference.Preference.OnPreferenceClickListener getOnPreferenceClickListener();
}
