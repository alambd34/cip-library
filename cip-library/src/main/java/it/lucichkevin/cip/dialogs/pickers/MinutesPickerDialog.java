package it.lucichkevin.cip.dialogs.pickers;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

import it.lucichkevin.cip.Utils;

/**
    Create a dialog with a NumberPicker to choose the minutes

    @author     Kevin Lucich    2014-09-03
    @version	0.1.0
    @since      0.3.0

*/
public class MinutesPickerDialog extends TimePickerDialog {

    protected MinutesPickerDialog.Callbacks callbacks = new MinutesPickerDialog.EmptyCallbacks(){
        public void onButtonPositiveClicked( Button view, int minute ){
            super.onButtonPositiveClicked( view, minute );
            timePickerDialog.dismiss();
        }
        public void onButtonCancelClicked( Button view, int minute ){
            super.onButtonCancelClicked( view, minute );
            timePickerDialog.dismiss();
        }
    };


    public MinutesPickerDialog(Context context) {
        super(context);
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ){
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field minute = classForid.getField("hour");
            ((NumberPicker) timePicker.findViewById(minute.getInt(null))).setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dialog;
    }

    protected void setCallbacksOfButtons() {
        ((Button) timePickerDialog.findViewById(BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonPositiveClicked( (Button) view, timePicker.getCurrentMinute() );
            }
        });
        ((Button) timePickerDialog.findViewById(BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ){
                callbacks.onButtonCancelClicked( (Button) view, timePicker.getCurrentMinute() );
            }
        });
    }


    ///////////////////////////////
    //  Getters and Setters

    public void setCallbacks( Callbacks callbacks ){
        this.callbacks = callbacks;
    }



    /////////////////////////////////////
    //  Callbacks

    public static interface Callbacks {
        public void onButtonPositiveClicked( Button view, int minute );
        public void onButtonCancelClicked( Button view, int minute );
    }

    public static class EmptyCallbacks implements MinutesPickerDialog.Callbacks{
        public void onButtonPositiveClicked( Button view, int minute ){
            Utils.logger("Cip.MinutesPickerDialog", "Callback onButtonCancelClicked( Button, " + minute + " ) called!", Utils.LOG_INFO);
        }
        public void onButtonCancelClicked( Button view, int minute ){
            Utils.logger("Cip.MinutesPickerDialog", "Callback onButtonCancelClicked( Button, " + minute + " ) called!", Utils.LOG_INFO);
        }
    }

}