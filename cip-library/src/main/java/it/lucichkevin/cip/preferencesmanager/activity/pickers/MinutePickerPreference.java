package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

/**
 * Created by kevin on 11/09/14.
 */
public abstract class MinutePickerPreference extends TimePickerPreference {

    private int minute = 0;
    private TimePicker timePicker = null;

    public MinutePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView() {
        return super.onCreateDialogView();
    }

    @Override
    protected void onBindDialogView( View view ){
        super.onBindDialogView(view);

        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field minute = classForid.getField("hour");
            Field divider = classForid.getField("divider");
            ((NumberPicker) timePicker.findViewById(minute.getInt(null))).setVisibility(View.GONE);
            ((NumberPicker) timePicker.findViewById(divider.getInt(null))).setVisibility(View.GONE);
        }catch( Exception e ){
            e.printStackTrace();
        }

        timePicker.setCurrentMinute(minute);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }

    @Override
    protected void onSetInitialValue( boolean restoreValue, Object defaultValue ){
        minute = (Integer) defaultValue;
        if( restoreValue && defaultValue == null ){
            minute = 0;
        }
    }

    public abstract void onSetTime( int minute );

    @Override
    public void onSetTime( int hour, int minute ){
        onSetTime(minute);
    }

}