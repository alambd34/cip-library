package it.lucichkevin.cip.dialogs.pickers;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
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
    @since      0.3.0
 */
public class TimePickerDialog extends DialogFragment {

    protected final static int BUTTON_POSITIVE = R.id.btn_positive;
    protected final static int BUTTON_NEGATIVE = R.id.btn_negative;

    protected Dialog timePickerDialog = null;
    protected TimePicker timePicker = null;

    //  Default behavior - Log (if in debug) and close dialog
    protected Callbacks callbacks = new EmptyCallbacks(){
        public void onButtonPositiveClicked( Dialog dialog, int hour, int minute ){
            super.onButtonPositiveClicked( dialog, hour, minute );
            Utils.logger("Cip.TimePickerDialog", "Callback onButtonPositiveClicked( Button, "+ String.valueOf(hour) +", "+ String.valueOf(minute) +" ) called!", Utils.LOG_INFO);
        }
        public void onButtonCancelClicked( Dialog dialog, int hour, int minute ){
            super.onButtonCancelClicked( dialog, hour, minute );
            Utils.logger("Cip.TimePickerDialog", "Callback onButtonCancelClicked( Button, "+ String.valueOf(hour) +", "+ String.valueOf(minute) +" ) called!", Utils.LOG_INFO);
        }
    };

    public TimePickerDialog(){
        this(Utils.getContext());
    }

    public TimePickerDialog( Context context ){

        timePickerDialog = new Dialog(context);
        timePickerDialog.setContentView(R.layout.timepicker_layout);
        timePickerDialog.setTitle("TEST");
        timePickerDialog.setCancelable(false);

        timePicker = ((TimePicker) timePickerDialog.findViewById(R.id.timepicker));

        final Calendar c = Calendar.getInstance();
        timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
        timePicker.setIs24HourView(DateFormat.is24HourFormat(context));
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ){
        setCallbacksOfButtons();
        return timePickerDialog;
    }

    protected void setCallbacksOfButtons() {
        ((Button) timePickerDialog.findViewById(BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonPositiveClicked( timePickerDialog, timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
            }
        });
        ((Button) timePickerDialog.findViewById(BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonCancelClicked( timePickerDialog, timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
            }
        });
    }

    public void show( FragmentManager fragmentManager ){
        super.show( fragmentManager, "PickerDialog-" + Math.random()*100 );
    }

    ///////////////////////////////
    //  Getters and Setters

    public int getHour(){
        return timePicker.getCurrentHour();
    }
    public void setHour( int hour ){
        timePicker.setCurrentHour(hour);
    }

    public int getMinute(){
        return timePicker.getCurrentMinute();
    }
    public void setMinute( int minute ){
        timePicker.setCurrentMinute(minute);
    }

    public void setCallbacks( Callbacks callbacks ){
        this.callbacks = callbacks;
    }

    public void setIs24HourFormat( boolean is24HourFormat ){
        timePicker.setIs24HourView(is24HourFormat);
    }

    public Dialog getDialog(){
        return timePickerDialog;
    }




    /////////////////////////////////////
    //  Callbacks

    public static interface Callbacks {
        public void onButtonPositiveClicked( Dialog dialog, int hour, int minute);
        public void onButtonCancelClicked( Dialog dialog, int hour, int minute);
    }

    public static class EmptyCallbacks implements Callbacks {
        @Override
        public void onButtonPositiveClicked( Dialog dialog, int hour, int minute ){
            dialog.dismiss();
        }
        @Override
        public void onButtonCancelClicked( Dialog dialog, int hour, int minute ){
            dialog.dismiss();
        }
    }

}