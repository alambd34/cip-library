package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;

/**
 *  @author     Kevin Lucich (11/09/14)
 */
public abstract class MinutePickerPreference extends TimePickerPreference {

    public MinutePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView(){
        super.onCreateDialogView();

        try {
            Class<?> classForId = Class.forName("com.android.internal.R$id");
            Field minute = classForId.getField("hour");
            Field divider = classForId.getField("divider");
            (timePicker.findViewById(minute.getInt(null))).setVisibility(View.GONE);
            (timePicker.findViewById(divider.getInt(null))).setVisibility(View.GONE);
        }catch( Exception e ){
            e.printStackTrace();
        }

        return timePicker;
    }

    public abstract void onSetTime( int minute );

    @Override
    public void onSetTime( int hour, int minute ){
        onSetTime(minute);
    }

}