package it.lucichkevin.cip.preferences;

import android.content.Context;
import androidx.annotation.StringRes;


public class NumberPickerPreference extends AbstractDialogPreference implements Preference {

	private NumberPickerPreference.OnNumberPickerPreferenceChangeListener onNumberPickerPreferenceChangeListener = null;


	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary ){
		this( context, key, title, summary, null, null, null );
	}
	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, Object default_value ){
		this( context, key, title, summary, null, null, default_value );
	}
	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnNumberPickerPreferenceChangeListener changeListener, Object default_value ){
		this( context, key, title, summary, changeListener, null, default_value );
	}
	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnNumberPickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( context, key, title, summary, changeListener, clickListener, null );
	}
	protected NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnNumberPickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Object default_value ){
		super( context, key, title, summary, null, clickListener, default_value );
		setOnPreferenceChangeListener(changeListener);
	}


	public OnNumberPickerPreferenceChangeListener getOnNumberPickerPreferenceChangeListener() {
		return onNumberPickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnNumberPickerPreferenceChangeListener onNumberPickerPreferenceChangeListener ){
		this.onNumberPickerPreferenceChangeListener = onNumberPickerPreferenceChangeListener;
	}


	public static abstract class OnNumberPickerPreferenceChangeListener {
		public abstract boolean onNumberPickerPreferenceChange( androidx.preference.Preference preference, @StringRes int number );
	}
}