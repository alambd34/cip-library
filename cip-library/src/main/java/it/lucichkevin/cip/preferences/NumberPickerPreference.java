package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;


public class NumberPickerPreference extends DialogPreference {

	private NumberPicker mPicker;

	private int mNumber = 0;
	private int min_value = 0;
	private int max_value = 10;

	public NumberPickerPreference(Context context, AttributeSet attrs ){
		this(context, attrs, 0);
	}

	public NumberPickerPreference(Context context, AttributeSet attrs, int defStyle ){
		super(context, attrs, defStyle);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
	}

	@Override
	protected View onCreateDialogView() {
		mPicker = new NumberPicker(getContext());
		mPicker.setMinValue( min_value );
		mPicker.setMaxValue( max_value );
		mPicker.setValue(mNumber);
		return mPicker;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			mPicker.clearFocus();
			setValue(mPicker.getValue());
		}
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		setValue(restoreValue ? getPersistedInt(mNumber) : (Integer) defaultValue);
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getInt(index, 0);
	}


	public void setValue(int value) {
		if (shouldPersist()) {
			persistInt(value);
		}

		if (value != mNumber) {
			mNumber = value;
			notifyChanged();
		}
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

}