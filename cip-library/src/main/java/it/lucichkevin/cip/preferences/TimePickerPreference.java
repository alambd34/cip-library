package it.lucichkevin.cip.preferences;

import android.content.Context;
import androidx.annotation.StringRes;

import org.threeten.bp.LocalTime;


/**
 * @author	 Kevin Lucich 2014-09-11
 *
 * @version 2.0.0 (2018-10-11)
 */
public class TimePickerPreference extends AbstractDialogPreference implements Preference {

	private OnTimePickerPreferenceChangeListener onTimePickerPreferenceChangeListener = null;


	public TimePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary ){
		this( context, key, title, summary, null, null, null );
	}
	public TimePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, LocalTime default_value ){
		this( context, key, title, summary, null, null, default_value);
	}
	public TimePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnTimePickerPreferenceChangeListener changeListener, LocalTime default_value ){
		this( context, key, title, summary, changeListener, null, default_value );
	}
	public TimePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnTimePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( context, key, title, summary, changeListener, clickListener, null );
	}
	public TimePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnTimePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, LocalTime default_value ){
		super( context, key, title, summary, null, clickListener, default_value );

		if( default_value == null ){
			default_value = LocalTime.now();
		}
		setDefaultValue(default_value);

		setOnPreferenceChangeListener(changeListener);
		setDialogLayoutResource(android.R.style.Theme_Material_Light_Dialog);
	}


	public LocalTime getDefaultValue() {
		return (LocalTime) super.getDefaultValue();
	}

	public OnTimePickerPreferenceChangeListener getOnTimePickerPreferenceChangeListener(){
		return onTimePickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnTimePickerPreferenceChangeListener onTimePickerPreferenceChangeListener ){
		this.onTimePickerPreferenceChangeListener = onTimePickerPreferenceChangeListener;
	}

	public static abstract class OnTimePickerPreferenceChangeListener {
		public abstract boolean onTimePickerPreferenceChange( androidx.preference.Preference preference, LocalTime time );
	}

}