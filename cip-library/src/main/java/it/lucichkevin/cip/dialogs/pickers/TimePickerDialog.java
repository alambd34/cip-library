package it.lucichkevin.cip.dialogs.pickers;

import android.app.Dialog;
import android.content.Context;
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
 */
public class TimePickerDialog extends DialogFragment {

    protected final static int BUTTON_POSITIVE = R.id.btn_positive;
    protected final static int BUTTON_NEGATIVE = R.id.btn_negative;

    protected Context context = null;
    protected Dialog timePickerDialog = null;
    protected TimePicker timePicker = null;
    protected boolean is24HourFormat = false;
    protected int hour = 0;
    protected int minute = 0;

    //  Default behavior - Log (if in debug) and close dialog
    protected Callbacks callbacks = new EmptyCallbacks(){
        public void onButtonPositiveClicked( Button view, int hour, int minute ){
            super.onButtonPositiveClicked( view, hour, minute );
            timePickerDialog.dismiss();
        }
        public void onButtonCancelClicked( Button view, int hour, int minute ){
            super.onButtonCancelClicked( view, hour, minute );
            timePickerDialog.dismiss();
        }
    };



    public TimePickerDialog( Context context ){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        this.context = context;
        is24HourFormat = DateFormat.is24HourFormat(this.context);

        timePickerDialog = new Dialog(this.context);
        timePickerDialog.setContentView(R.layout.timepicker_layout);
        timePickerDialog.setTitle("TEST");
        timePickerDialog.setCancelable(false);

        timePicker = ((TimePicker) timePickerDialog.findViewById(R.id.timepicker));
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ){

        timePicker.setIs24HourView(is24HourFormat);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

        setCallbacksOfButtons();

        return timePickerDialog;
    }

    protected void setCallbacksOfButtons() {
        ((Button) timePickerDialog.findViewById(BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonPositiveClicked((Button) view, timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
            }
        });
        ((Button) timePickerDialog.findViewById(BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonCancelClicked( (Button) view, timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
            }
        });
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

    public static interface Callbacks {
        public void onButtonPositiveClicked(Button view, int hour, int minute);
        public void onButtonCancelClicked(Button view, int hour, int minute);
    }

    public static class EmptyCallbacks implements Callbacks {
        public void onButtonPositiveClicked( Button view, int hour, int minute ){
            Utils.logger("Cip.TimePickerDialog", "Callback onButtonPositiveClicked( Button, \"+ hour +\", \"+ minute +\" )\" called!", Utils.LOG_INFO);
        }
        public void onButtonCancelClicked( Button view, int hour, int minute ){
            Utils.logger("Cip.TimePickerDialog", "Callback onButtonCancelClicked( Button, \"+ hour +\", \"+ minute +\" )\" called!", Utils.LOG_INFO);
        }
    }

}