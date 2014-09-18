package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import it.lucichkevin.cip.preferencesmanager.PreferencesManager;

/**
 *  @author     Kevin Lucich (11/09/14)
 */
public abstract class TimePickerPreference extends DialogPreference {

    protected Integer default_minute = null;
    protected TimePicker timePicker = null;

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super( context, attrs );

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
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

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

        return timePicker;
    }

    @Override
    protected void onDialogClosed( boolean positiveResult ){
        super.onDialogClosed(positiveResult);

        if( positiveResult ){
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            SharedPreferences.Editor editor = getEditor();
            editor.putInt( getKey(), (hour * 60 + minute) );
            editor.commit();

            onSetTime( hour, minute );
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if( defaultValue != null ){
            default_minute = (Integer) defaultValue;
        }else{
            default_minute = PreferencesManager.getPreferences().getInt(getKey(), 0);
        }
    }

    /**
     *  Called when
     */
    public abstract void onSetTime( int hour, int minute );
}