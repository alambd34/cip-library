package it.lucichkevin.cip.preferences.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import org.threeten.bp.LocalTime;

import androidx.preference.PreferenceDialogFragmentCompat;
import it.lucichkevin.cip.preferences.PreferencesManager;
import it.lucichkevin.cip.preferences.TimePickerPreference;



public class TimePickerDialogPreferenceFragmentCompat extends PreferenceDialogFragmentCompat  {

	private TimePicker time_picker;
	private String preference_key = null;

	public static TimePickerDialogPreferenceFragmentCompat newInstance( String key ){
		final TimePickerDialogPreferenceFragmentCompat fragment = new TimePickerDialogPreferenceFragmentCompat();
		final Bundle bundle = new Bundle(1);
		bundle.putString(ARG_KEY, key);
		fragment.setArguments(bundle);
		return fragment;
	}


	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setPreferenceKey(getArguments().getString(ARG_KEY));
	}

	@Override
	protected View onCreateDialogView( Context context ) {
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
		));
		linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		time_picker = new TimePicker(getContext());
		time_picker.setIs24HourView(true);

		linearLayout.addView(time_picker);

		return linearLayout;
	}

	@Override
	protected void onBindDialogView( View view ) {
		super.onBindDialogView(view);

		final TimePickerPreference preference = (TimePickerPreference) this.getPreference();

		LocalTime time_selected = preference.getDefaultValue();
		long minutes_to_set = PreferencesManager.getPreferences().getLong( getPreferenceKey(), -1 );
		if( minutes_to_set > -1 ){
			time_selected = LocalTime.ofSecondOfDay(minutes_to_set*60);
		}

		time_picker.setHour(time_selected.getHour());
		time_picker.setMinute(time_selected.getMinute());
	}

	@Override
	public void onDialogClosed( boolean positiveResult ) {

		int minutes_to_save = (time_picker.getHour() * 60 + time_picker.getMinute());
		LocalTime time_selected = LocalTime.of(time_picker.getHour(), time_picker.getMinute());

		final TimePickerPreference preference = (TimePickerPreference) this.getPreference();
		if( preference.getOnTimePickerPreferenceChangeListener() != null ){
			if( preference.getOnTimePickerPreferenceChangeListener().onTimePickerPreferenceChange(preference,time_selected) ){
				savePreferenceValue(minutes_to_save);
			}
		}else{
			savePreferenceValue(minutes_to_save);
		}
	}

	protected void savePreferenceValue( long minutes_to_save ){
		SharedPreferences.Editor editor = PreferencesManager.getEditor();
		editor.putLong( getPreferenceKey(), minutes_to_save );
		editor.commit();
	}


	////////////////////////////////////////
	//	Getter and Setter

	public String getPreferenceKey(){
		return this.preference_key;
	}
	public void setPreferenceKey( String key ){
		this.preference_key = key;
	}

}