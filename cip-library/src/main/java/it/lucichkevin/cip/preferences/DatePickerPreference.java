package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 *  @author	 Kevin Lucich (11/09/14)
 */
public class DatePickerPreference extends AbstractDialogPreference {

	private DatePicker datePicker = null;
	private OnDatePickerPreferenceChangeListener onDatePickerPreferenceChangeListener = null;

	public DatePickerPreference(Context context, String key, int title, int summary) {
		super(context, key, title, summary);
	}
	public DatePickerPreference(Context context, String key, int title, int summary, Object default_value) {
		super(context, key, title, summary, default_value);
	}
	public DatePickerPreference(Context context, String key, int title, int summary, Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener) {
		super(context, key, title, summary, null);
		setOnPreferenceChangeListener(changeListener);
	}
	protected DatePickerPreference(Context context, String key, String title, String summary, OnDatePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Object default_value) {
		super(context, key, title, summary, null, clickListener, default_value);
		setOnPreferenceChangeListener(changeListener);
	}


	@Override
	protected View onCreateDialogView() {
		datePicker = new DatePicker(getContext());

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		long default_millis = PreferencesManager.getPreferences().getLong( getKey(), 0 );
		if( default_millis != 0 ){
			c.setTimeInMillis(default_millis);
		}
		datePicker.updateDate( c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) );

		return datePicker;
	}

	@Override
	protected void onDialogClosed( boolean positiveResult ){

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, datePicker.getYear());
		calendar.set(Calendar.MONTH, datePicker.getMonth());
		calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		if( positiveResult ){
			if( getOnDatePickerPreferenceChangeListener() != null ){
				if( getOnDatePickerPreferenceChangeListener().onDatePickerPreferenceChange(this, calendar.getTime()) ){
					savePreferenceValue(calendar.getTimeInMillis());
				}
			}else{
				savePreferenceValue(calendar.getTimeInMillis());
			}
		}
	}

	protected void savePreferenceValue( long millis_to_save ){
		SharedPreferences.Editor editor = getEditor();
		editor.putLong( getKey(), millis_to_save );
		editor.commit();
	}


	public OnDatePickerPreferenceChangeListener getOnDatePickerPreferenceChangeListener() {
		return onDatePickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnDatePickerPreferenceChangeListener onDatePickerPreferenceChangeListener ){
		this.onDatePickerPreferenceChangeListener = onDatePickerPreferenceChangeListener;
	}

	public static abstract class OnDatePickerPreferenceChangeListener {
		public abstract boolean onDatePickerPreferenceChange( android.preference.Preference preference, Date date );
	}

}