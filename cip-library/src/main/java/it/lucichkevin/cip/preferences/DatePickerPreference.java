package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 *  @author	 Kevin Lucich (11/09/14)
 */
public class DatePickerPreference extends AbstractDialogPreference {

	private Long default_millis = null;
	private DatePicker datePicker = null;
	private OnDatePickerPreferenceChangeListener onDatePickerPreferenceChangeListener = null;

	public DatePickerPreference(Context context, String key, int title, int summary) {
		super(context, key, title, summary);
	}
	public DatePickerPreference(Context context, String key, int title, int summary, Object default_value) {
		super(context, key, title, summary, default_value);
	}
	public DatePickerPreference(Context context, String key, int title, int summary, OnPreferenceChangeListener changeListener, OnPreferenceClickListener clickListener) {
		super(context, key, title, summary, changeListener, clickListener);
	}
	protected DatePickerPreference(Context context, String key, String title, String summary, OnPreferenceChangeListener changeListener, OnPreferenceClickListener clickListener, Object default_value) {
		super(context, key, title, summary, changeListener, clickListener, default_value);
	}


	@Override
	protected View onCreateDialogView() {
		datePicker = new DatePicker(getContext());

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		if( default_millis != null ){
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
			SharedPreferences.Editor editor = getEditor();
			editor.putLong( getKey(), calendar.getTimeInMillis() );
			editor.commit();

			if( getOnDatePickerPreferenceChangeListener() != null ){
				getOnDatePickerPreferenceChangeListener().onPreferenceChange(DatePickerPreference.this, calendar.getTime() );
			}
		}
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		if( defaultValue != null ){
			default_millis = (Long) defaultValue;
		}else{
			default_millis = PreferencesManager.getPreferences().getLong(getKey(), 0 );
		}
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}


	public OnDatePickerPreferenceChangeListener getOnDatePickerPreferenceChangeListener() {
		return onDatePickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener(OnDatePickerPreferenceChangeListener onDatePickerPreferenceChangeListener ){
		this.onDatePickerPreferenceChangeListener = onDatePickerPreferenceChangeListener;
	}

	public static abstract class OnDatePickerPreferenceChangeListener implements Preference.OnPreferenceChangeListener {

		public abstract boolean onPreferenceChange( Preference preference, Date date );

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			return onPreferenceChange(preference, (Date) newValue );
		}
	}

}