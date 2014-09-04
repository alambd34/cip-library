package it.lucichkevin.cip.pickerdialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;

/**
    Create a dialog with a two NumberPickers to choose hours and minutes

    @author     Kevin Lucich    2014-09-03
    @version	0.1.0
    @since      0.3.0

    Usage:

*/
public class TimePickerDialog extends DialogFragment {

    protected final static int BUTTON_POSITIVE = R.id.btn_positive;
    protected final static int BUTTON_NEGATIVE = R.id.btn_negative;

    protected Dialog timePickerDialog = null;
    protected TimePicker timePicker = null;
    protected Callbacks callbacks = null;
    protected boolean is24HourFormat = false;

    protected int hour = 0;
    protected int minute = 0;

    //  Used for save sthe status
    protected static final String HOUR = "hour";
    protected static final String MINUTE = "minute";
    protected static final String IS_24_HOUR = "is24hour";

    public TimePickerDialog(){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        is24HourFormat = DateFormat.is24HourFormat(getActivity());
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ){

        timePickerDialog = new Dialog(getActivity());
        timePickerDialog.setContentView(R.layout.timepicker_layout);
        timePickerDialog.setTitle("TEST");
        timePickerDialog.setCancelable(false);

        try{
            timePicker = ((TimePicker) timePickerDialog.findViewById(R.id.timepicker));
            timePicker.setIs24HourView(is24HourFormat);
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }catch( Exception e ){
            e.printStackTrace();
        }

        ((Button) timePickerDialog.findViewById(BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonPositiveClicked((Button) view, timePicker.getCurrentHour(), timePicker.getCurrentMinute(), timePickerDialog);
            }
        });
        ((Button) timePickerDialog.findViewById(BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonCancelClicked( (Button) view, timePicker.getCurrentHour(), timePicker.getCurrentMinute(), timePickerDialog );
            }
        });

        return timePickerDialog;
    }


    ///////////////////////////////
    //  Getters and Setters

    public void setHour( int hour ){
        timePicker.setCurrentHour(hour);
    }
    public void setMinute( int minute ){
        timePicker.setCurrentMinute(minute);
    }

    public void setCallbacks( Callbacks callbacks ){
        this.callbacks = callbacks;
    }

    public void setIs24HourFormat( boolean is24HourFormat ){
        this.is24HourFormat = is24HourFormat;
    }


    /////////////////////////////////////
    //  Callbacks

    public static abstract class Callbacks {
        public void onButtonPositiveClicked( Button view, int hourOfDay, int minute, Dialog dialog ){
            Utils.logger("Mi orario settato, chiamo la callback ;) ", Utils.LOG_DEBUG);
            dialog.dismiss();
        }
        public void onButtonCancelClicked( Button view, int hourOfDay, int minute, Dialog dialog ){
            Utils.logger("Mi sto chiudendo... :) ", Utils.LOG_DEBUG);
            dialog.dismiss();
        }
    }

}