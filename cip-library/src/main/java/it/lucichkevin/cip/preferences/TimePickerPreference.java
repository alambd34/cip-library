package it.lucichkevin.cip.preferences;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.StringRes;
import android.widget.TimePicker;

import org.threeten.bp.LocalTime;

import it.lucichkevin.cip.dialogs.PickerDialogBuilder;


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

	protected void showDialog( Bundle state ){

		LocalTime time_selected = getDefaultValue();
		long minutes_to_set = PreferencesManager.getPreferences().getLong( getKey(), -1 );
		if( minutes_to_set > -1 ){
			time_selected = LocalTime.ofSecondOfDay(minutes_to_set*60);
		}
		PickerDialogBuilder.TimePickerDialog.show(getContext(), time_selected, new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet( TimePicker view, int hour, int minute ){

				int minutes_to_save = (hour * 60 + minute);
				LocalTime time_selected = LocalTime.of(hour, minute);

				if( getOnTimePickerPreferenceChangeListener() != null ){
					if( getOnTimePickerPreferenceChangeListener().onTimePickerPreferenceChange(TimePickerPreference.this,time_selected) ){
						savePreferenceValue(minutes_to_save);
					}
				}else{
					savePreferenceValue(minutes_to_save);
				}
			}
		});
	}

	protected void savePreferenceValue( int minutes_to_save ){
		SharedPreferences.Editor editor = PreferencesManager.getEditor();
		editor.putInt( getKey(), minutes_to_save );
		editor.commit();
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