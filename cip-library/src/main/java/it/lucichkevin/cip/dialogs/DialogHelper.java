package it.lucichkevin.cip.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;

/**
 * Created by kevin on 04/09/14.
 */
public class DialogHelper {

    //  Default behavior - Log (if in debug) and close dialog
    private DialogHelper.Callbacks callbacks = new DialogHelper.EmptyCallbacks(){
        public void onClickButton( DialogInterface dialog, int which ){
            super.onClickButton( dialog, which );
            dialog.dismiss();
        }
    };

    private static final int DEFAULT_TITLE = R.string.welcomedialog_title;
    private static final int DEFAULT_MESSAGE = R.string.welcomedialog_message;
    private static final int BTN_VALUE = R.string.welcomedialog_neutral_btn_value;

    public DialogHelper(){
    }

    public void show(){
        show( Utils.getContext(), DEFAULT_TITLE, DEFAULT_MESSAGE, BTN_VALUE );
    }

    public void show( Context context ){
        show( context, DEFAULT_TITLE, DEFAULT_MESSAGE, BTN_VALUE );
    }

    public void show( Context context, int title, int message ){
        show( context, title, message, BTN_VALUE );
    }

    public void show( Context context, int title, int message, int btn_value ){
        try {
            show( context, context.getString(title), context.getString(message), context.getString(btn_value) );
        }catch( NotFoundException e ){
            Utils.logger("Cip.WelcomeDialog", e.getMessage(), Utils.LOG_ERROR );
        }
    }

    public void show( Context context, String title, String message, String btn_value ){
        new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(btn_value, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callbacks.onClickButton(dialog, which);
                }
            }).show();
    }


    /////////////////////////////////////
    //  Callbacks

    public static interface Callbacks {
        public void onClickButton( DialogInterface dialog, int which );
    }

    public static class EmptyCallbacks implements Callbacks {
        public void onClickButton( DialogInterface dialog, int which ){
            Utils.logger("CipLibrary.WelcomeDialog","Callback onClickButton() called!", Utils.LOG_INFO );
        }
    }


}
