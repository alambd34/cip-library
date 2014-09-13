package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by kevin on 11/09/14.
 */
public abstract class TimePickerPreference extends DialogPreference {

    private int hour = 0;
    private int minute = 0;
    private TimePicker timePicker = null;

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super( context, attrs );
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        Context context = getContext();

        timePicker = new TimePicker(context);
        timePicker.setIs24HourView(DateFormat.is24HourFormat(context));

        return timePicker;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if( positiveResult ){
            onSetTime( timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
        }
    }

    @Override
    protected Object onGetDefaultValue( TypedArray a, int index ){
        return a.getInt(index, 0);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object _defaultValue) {
        Integer defaultInMillies = (Integer) _defaultValue;
        hour = 0;
        minute = 0;

        if( restoreValue && defaultInMillies != null && defaultInMillies != 0 ){
            hour = (int) defaultInMillies / 3600000;
            minute = defaultInMillies - (hour * 3600000);
        }
    }


    public abstract void onSetTime( int hour, int minute );
}