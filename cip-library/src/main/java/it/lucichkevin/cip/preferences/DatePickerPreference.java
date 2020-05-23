package it.lucichkevin.cip.preferences;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.StringRes;
import android.widget.DatePicker;

import org.threeten.bp.LocalDate;

import it.lucichkevin.cip.dialogs.PickerDialogBuilder;


/**
 * @author	 Kevin Lucich 2014-09-11
 *
 * @version 2.0.0 (2018-10-11)
 */
public class DatePickerPreference extends AbstractDialogPreference implements Preference {

	private OnDatePickerPreferenceChangeListener onDatePickerPreferenceChangeListener = null;


	public DatePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary ){
		this( context, key, title, summary, null, null, null );
	}
	public DatePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, LocalDate default_value ){
		this( context, key, title, summary, null, null, default_value );
	}
	public DatePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnDatePickerPreferenceChangeListener changeListener, LocalDate default_value ){
		this( context, key, title, summary, changeListener, null, default_value );
	}
	public DatePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnDatePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( context, key, title, summary, changeListener, clickListener, null );
	}
	public DatePickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnDatePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, LocalDate default_value ){
		super( context, key, title, summary, null, clickListener, default_value );

		if( default_value == null ){
			default_value = LocalDate.now();
		}
		setDefaultValue(default_value);

		setOnPreferenceChangeListener(changeListener);
	}


	protected void showDialog( Bundle state ){

		LocalDate date_selected = getDefaultValue();
		long default_millis = PreferencesManager.getPreferences().getLong( getKey(), 0 );
		if( default_millis > -1 ){
			date_selected = LocalDate.ofEpochDay(default_millis);
		}
	}


	public LocalDate getDefaultValue() {
		return (LocalDate) super.getDefaultValue();
	}

	public OnDatePickerPreferenceChangeListener getOnDatePickerPreferenceChangeListener() {
		return onDatePickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnDatePickerPreferenceChangeListener onDatePickerPreferenceChangeListener ){
		this.onDatePickerPreferenceChangeListener = onDatePickerPreferenceChangeListener;
	}

	public static abstract class OnDatePickerPreferenceChangeListener {
		public abstract boolean onDatePickerPreferenceChange( androidx.preference.Preference preference, LocalDate date );
	}

}