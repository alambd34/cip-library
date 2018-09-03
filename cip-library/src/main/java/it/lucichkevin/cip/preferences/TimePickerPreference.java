package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;


/**
 *  @author	 Kevin Lucich (11/09/14)
 */
public class TimePickerPreference extends AbstractDialogPreference {

	protected TimePicker timePicker = null;
	private OnTimePickerPreferenceChangeListener onTimePickerPreferenceChangeListener = null;

	public TimePickerPreference(Context context, String key, int title, int summary) {
		super(context, key, title, summary);
	}
	public TimePickerPreference(Context context, String key, int title, int summary, Object default_value) {
		super(context, key, title, summary, default_value);
	}
	public TimePickerPreference(Context context, String key, int title, int summary, OnTimePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener) {
		super(context, key, title, summary, null, clickListener);
		setOnPreferenceChangeListener(changeListener);
	}
	protected TimePickerPreference(Context context, String key, String title, String summary, OnTimePickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Object default_value) {
		super(context, key, title, summary, null, clickListener, default_value);
		setOnPreferenceChangeListener(changeListener);
	}


	@Override
	protected View onCreateDialogView() {

		timePicker = new TimePicker(getContext());
		timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

		int minute = 0;
		int hour = 0;

		int minutes_to_set = PreferencesManager.getPreferences().getInt( getKey(), 0 );
		if( minutes_to_set != 0 ){
			hour = minutes_to_set / 60;
			minute = minutes_to_set - (hour * 60);
		}

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

			if( getOnTimePickerPreferenceChangeListener() != null ){
				if( getOnTimePickerPreferenceChangeListener().onTimePickerPreferenceChange(this, hour, minute) ){
					savePreferenceValue(value);
				}
			}else{
				savePreferenceValue(value);
			}
		}
	}

	protected void savePreferenceValue( int minutes_to_save ){
		SharedPreferences.Editor editor = getEditor();
		editor.putInt( getKey(), minutes_to_save );
		editor.commit();
	}

	public OnTimePickerPreferenceChangeListener getOnTimePickerPreferenceChangeListener(){
		return onTimePickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnTimePickerPreferenceChangeListener onTimePickerPreferenceChangeListener ){
		this.onTimePickerPreferenceChangeListener = onTimePickerPreferenceChangeListener;
	}

	public static abstract class OnTimePickerPreferenceChangeListener {
		public abstract boolean onTimePickerPreferenceChange( android.preference.Preference preference, int hours, int minutes );
	}

}