package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;


/**
 *  @author	 Kevin Lucich (11/09/14)
 */
public class TimePickerPreference extends AbstractDialogPreference {

	protected Integer default_minute = null;
	protected TimePicker timePicker = null;
	private OnTimePickerPreferenceChangeListener onTimePickerPreferenceChangeListener = null;

	public TimePickerPreference(Context context, String key, int title, int summary) {
		super(context, key, title, summary);
	}
	public TimePickerPreference(Context context, String key, int title, int summary, Object default_value) {
		super(context, key, title, summary, default_value);
	}
	public TimePickerPreference(Context context, String key, int title, int summary, OnPreferenceChangeListener changeListener, OnPreferenceClickListener clickListener) {
		super(context, key, title, summary, changeListener, clickListener);
	}
	protected TimePickerPreference(Context context, String key, String title, String summary, OnPreferenceChangeListener changeListener, OnPreferenceClickListener clickListener, Object default_value) {
		super(context, key, title, summary, changeListener, clickListener, default_value);
	}


	@Override
	protected View onCreateDialogView() {

		timePicker = new TimePicker(getContext());
		timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

		int minute = 0;
		if( default_minute != null ){
			minute = default_minute;
		}

		int hour = minute / 60;
		minute = minute - (hour * 60);

		timePicker.setHour(hour);
		timePicker.setMinute(minute);

		return timePicker;
	}

	@Override
	protected void onDialogClosed( boolean positiveResult ){
		super.onDialogClosed(positiveResult);

		if( positiveResult ){
			int hour = timePicker.getHour();
			int minute = timePicker.getMinute();
			int value = (hour * 60 + minute);

			SharedPreferences.Editor editor = getEditor();
			editor.putInt( getKey(), value );
			editor.commit();

			if( getOnDatePickerPreferenceChangeListener() != null ){
				getOnDatePickerPreferenceChangeListener().onPreferenceChange( TimePickerPreference.this, value );
			}
		}
	}

	@Override
	protected void onSetInitialValue( boolean restorePersistedValue, Object defaultValue ){
		if( defaultValue != null ){
			default_minute = (Integer) defaultValue;
		}else{
			default_minute = PreferencesManager.getPreferences().getInt(getKey(), 0);
		}
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}


	public OnTimePickerPreferenceChangeListener getOnDatePickerPreferenceChangeListener(){
		return onTimePickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnTimePickerPreferenceChangeListener onTimePickerPreferenceChangeListener ){
		this.onTimePickerPreferenceChangeListener = onTimePickerPreferenceChangeListener;
	}

	public static abstract class OnTimePickerPreferenceChangeListener implements Preference.OnPreferenceChangeListener {

		public abstract boolean onPreferenceChange( Preference preference, Integer minutes );

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			return onPreferenceChange(preference, (Integer) newValue );
		}
	}

}