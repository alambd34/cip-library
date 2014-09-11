package it.lucichkevin.cip.preferencesmanager.activity.pickers;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevin on 11/09/14.
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
        Context context = getContext();
        datePicker = new DatePicker(context);
        return datePicker;
    }

    @Override
    protected void onBindDialogView( View v ){
        super.onBindDialogView(v);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if( positiveResult ){
            Date dateSelected;
            try {
                dateSelected = (new SimpleDateFormat("yyyy-mm-dd")).parse(datePicker.getYear() +"-"+ datePicker.getMonth() +"-"+ datePicker.getDayOfMonth());
            } catch (ParseException e) {
                e.printStackTrace();
                dateSelected = new Date();
            }
            onSetDate(dateSelected);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return(a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue ){
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
    }


    public abstract void onSetDate( Date date );
}