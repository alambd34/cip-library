package it.lucichkevin.cip.preferences.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import org.threeten.bp.LocalDate;

import androidx.preference.PreferenceDialogFragmentCompat;
import it.lucichkevin.cip.preferences.DatePickerPreference;
import it.lucichkevin.cip.preferences.PreferencesManager;


public class DatePickerDialogPreferenceFragmentCompat extends PreferenceDialogFragmentCompat  {

	private DatePicker date_picker;
	private String preference_key = null;

	public static DatePickerDialogPreferenceFragmentCompat newInstance( String key ){
		final DatePickerDialogPreferenceFragmentCompat fragment = new DatePickerDialogPreferenceFragmentCompat();
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

		date_picker = new DatePicker(getContext());

		linearLayout.addView(date_picker);

		return linearLayout;
	}

	@Override
	protected void onBindDialogView( View view ) {
		super.onBindDialogView(view);

		final DatePickerPreference preference = (DatePickerPreference) this.getPreference();

		LocalDate date_selected = preference.getDefaultValue();
		long millis_to_save = PreferencesManager.getPreferences().getLong( getPreferenceKey(), -1 );
		if( millis_to_save > -1 ){
			date_selected = LocalDate.ofEpochDay(millis_to_save);
		}

		date_picker.updateDate( date_selected.getYear(), date_selected.getMonthValue(), date_selected.getDayOfMonth() );
	}

	@Override
	public void onDialogClosed( boolean positiveResult ) {

		LocalDate date_selected = LocalDate.of(date_picker.getYear(), date_picker.getMonth(), date_picker.getDayOfMonth());
		long millis_to_date_selected = date_selected.toEpochDay();

		final DatePickerPreference preference = (DatePickerPreference) this.getPreference();

		if( preference.getOnDatePickerPreferenceChangeListener() != null ){
			if( preference.getOnDatePickerPreferenceChangeListener().onDatePickerPreferenceChange(preference,date_selected) ){
				savePreferenceValue(millis_to_date_selected);
			}
		}else{
			savePreferenceValue(millis_to_date_selected);
		}
	}

	protected void savePreferenceValue( long millis_to_save ){
		SharedPreferences.Editor editor = PreferencesManager.getEditor();
		editor.putLong( getPreferenceKey(), millis_to_save );
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