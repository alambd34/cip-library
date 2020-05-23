package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;


public class NumberPickerPreference extends AbstractDialogPreference implements Preference {

	private NumberPicker number_picker;
	private OnNumberPickerPreferenceChangeListener onNumberPickerPreferenceChangeListener = null;

	private int min_value = 0;
	private int max_value = 10;


	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary ){
		this( context, key, title, summary, null, null, null );
	}
	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, Object default_value ){
		this( context, key, title, summary, null, null, default_value );
	}
	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnNumberPickerPreferenceChangeListener changeListener, Object default_value ){
		this( context, key, title, summary, changeListener, null, default_value );
	}
	public NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnNumberPickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener ){
		this( context, key, title, summary, changeListener, clickListener, null );
	}
	protected NumberPickerPreference( Context context, String key, @StringRes int title, @StringRes int summary, OnNumberPickerPreferenceChangeListener changeListener, Preference.OnPreferenceClickListener clickListener, Object default_value ){
		super( context, key, title, summary, null, clickListener, default_value );
		setOnPreferenceChangeListener(changeListener);
	}

//	@Override
	protected View onCreateDialogView() {

		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
		));
		linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		number_picker = new NumberPicker(getContext());
		number_picker.setGravity(Gravity.CENTER);
		number_picker.setMinValue( min_value );
		number_picker.setMaxValue( max_value );

		int default_number = PreferencesManager.getPreferences().getInt( getKey(), min_value );
		if( default_number != min_value ){
			number_picker.setValue(default_number);
		}

		linearLayout.addView(number_picker);

		return linearLayout;
	}

//	@Override
	protected void onDialogClosed( boolean positiveResult ){

		if( !positiveResult ){
			return;
		}

		number_picker.clearFocus();

		if( getOnNumberPickerPreferenceChangeListener() != null ){
			if( getOnNumberPickerPreferenceChangeListener().onNumberPickerPreferenceChange(this, number_picker.getValue()) ){
				savePreferenceValue(number_picker.getValue());
			}
		}else{
			savePreferenceValue(number_picker.getValue());
		}
	}

	protected void savePreferenceValue( int number_to_save ){
		SharedPreferences.Editor editor = PreferencesManager.getEditor();
		editor.putInt( getKey(), number_to_save );
		editor.commit();
	}


	////////////////////////////////////////
	//	Getter and Setter

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




	public OnNumberPickerPreferenceChangeListener getOnNumberPickerPreferenceChangeListener() {
		return onNumberPickerPreferenceChangeListener;
	}
	public void setOnPreferenceChangeListener( OnNumberPickerPreferenceChangeListener onNumberPickerPreferenceChangeListener ){
		this.onNumberPickerPreferenceChangeListener = onNumberPickerPreferenceChangeListener;
	}


	public static abstract class OnNumberPickerPreferenceChangeListener {
		public abstract boolean onNumberPickerPreferenceChange( androidx.preference.Preference preference, @StringRes int number );
	}
}