package it.lucichkevin.cip.preferences.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.StringRes;
import androidx.preference.PreferenceDialogFragmentCompat;
import it.lucichkevin.cip.preferences.AbstractDialogPreference;
import it.lucichkevin.cip.preferences.NumberPickerPreference;
import it.lucichkevin.cip.preferences.PreferencesManager;

import static it.lucichkevin.cip.preferences.PreferencesManager.getEditor;


public class NumberPickerDialogPreferenceFragmentCompat extends PreferenceDialogFragmentCompat  {

	private NumberPicker number_picker;
	private int min_value = 0;
	private int max_value = 10;
	private String preference_key = null;

	public static NumberPickerDialogPreferenceFragmentCompat newInstance( String key ){
		final NumberPickerDialogPreferenceFragmentCompat fragment = new NumberPickerDialogPreferenceFragmentCompat();
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

		number_picker = new NumberPicker(getContext());
		number_picker.setGravity(Gravity.CENTER);
		number_picker.setMinValue( min_value );
		number_picker.setMaxValue( max_value );

		int default_number = PreferencesManager.getPreferences().getInt( getPreferenceKey(), min_value );
		if( default_number != min_value ){
			number_picker.setValue(default_number);
		}

		linearLayout.addView(number_picker);

		return linearLayout;
	}

	@Override
	public void onDialogClosed(boolean positiveResult) {

		if( !positiveResult ){
			return;
		}

		number_picker.clearFocus();

		NumberPickerPreference preference = (NumberPickerPreference) this.getPreference();
		if( preference.getOnNumberPickerPreferenceChangeListener() != null ){
			if( preference.getOnNumberPickerPreferenceChangeListener().onNumberPickerPreferenceChange(preference, number_picker.getValue()) ){
				savePreferenceValue(number_picker.getValue());
			}
		}else{
			savePreferenceValue(number_picker.getValue());
		}
	}

	protected void savePreferenceValue( int number_to_save ){
		SharedPreferences.Editor editor = getEditor();
		editor.putInt( getPreferenceKey(), number_to_save );
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

	public int getMaxValue(){
		return this.max_value;
	}
	public void setMaxValue( int max_value ){
		this.max_value = max_value;
	}

	public int getMinValue(){
		return this.min_value;
	}
	public void setMinValue( int min_value ){
		this.min_value = min_value;
	}

	public void setIntervalValues( int min_value, @StringRes int max_value ){
		setMinValue(min_value);
		setMaxValue(max_value);
	}


}