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

    private int lastHour=0;
    private int lastMinute=0;
    private TimePicker timePicker = null;

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super( context, attrs );
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    public static int getHour(String time) {
        String[] pieces=time.split(":");
        return(Integer.parseInt(pieces[0]));
    }

    public static int getMinute(String time) {
        String[] pieces=time.split(":");
        return(Integer.parseInt(pieces[1]));
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

        timePicker.setCurrentHour(lastHour);
        timePicker.setCurrentMinute(lastMinute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if( positiveResult ){
            onSetTime( timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return(a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time = null;
        if( restoreValue ){
            if( defaultValue == null ){
                time = getPersistedString("00:00");
            }else{
                time = getPersistedString( defaultValue.toString() );
            }
        }else{
            time = defaultValue.toString();
        }
        lastHour=getHour(time);
        lastMinute=getMinute(time);
    }


    public abstract void onSetTime( int hour, int minute );
}