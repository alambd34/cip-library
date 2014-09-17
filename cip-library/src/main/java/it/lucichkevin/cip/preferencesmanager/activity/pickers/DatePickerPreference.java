package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.lucichkevin.cip.preferencesmanager.PreferencesManager;

/**
 *  @author     Kevin Lucich (11/09/14)
 */
public abstract class DatePickerPreference extends DialogPreference {

    private DatePicker datePicker = null;

    public DatePickerPreference( Context context, AttributeSet attrs ){
        super( context, attrs );
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        datePicker = new DatePicker(getContext());

        long millis = PreferencesManager.getPreferences().getLong(getKey(), 0L);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        if( millis != 0 ){
            c.setTimeInMillis(millis);
        }
        datePicker.updateDate( c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) );

        return datePicker;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if( positiveResult ){
            SharedPreferences.Editor editor = getEditor();
            editor.putLong( getKey(), calendar.getTimeInMillis() );
            editor.commit();
            onSetDate( calendar.getTime() );
        }
    }

    public abstract void onSetDate( Date date );
}