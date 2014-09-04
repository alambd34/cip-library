package it.lucichkevin.cip.pickerdialogs;

import android.app.Dialog;
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

    Usage:

*/
public class MinutesPickerDialog extends TimePickerDialog {

    protected Callbacks callbacks = null;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ){
        Dialog dialog = super.onCreateDialog(savedInstanceState);

//        timePicker.findViewById(R.id.ho)
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field minute = classForid.getField("hour");
            ((NumberPicker) timePicker.findViewById(minute.getInt(null))).setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dialog;
    }

    //  MinutesPickerDialog.Callbacks
    public static abstract class Callbacks extends TimePickerDialog.Callbacks{
        public void onButtonPositiveClicked( Button view, int minute, Dialog dialog ){
            super.onButtonPositiveClicked( view, 0, minute, dialog );
        }
        public void onButtonCancelClicked( Button view, int minute, Dialog dialog ){
            super.onButtonCancelClicked( view, 0, minute, dialog );
        }
    }

}
