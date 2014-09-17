package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

import it.lucichkevin.cip.preferencesmanager.PreferencesManager;

/**
 *  @author     Kevin Lucich (11/09/14)
 */
public abstract class MinutePickerPreference extends TimePickerPreference {

    public MinutePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView() {
        return super.onCreateDialogView();
    }

    public abstract void onSetTime( int minute );

    @Override
    public void onSetTime(int hour, int minute) {
        onSetTime(minute);
    }

}