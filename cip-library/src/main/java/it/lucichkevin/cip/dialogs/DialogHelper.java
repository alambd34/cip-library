package it.lucichkevin.cip.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;

/**
 * @author  Kevin Lucich (04/09/14)
 */
public class DialogHelper {

    //  Default behavior - Log (if in debug) and close dialog
    private static final DialogHelper.Callbacks callbacks = new DialogHelper.EmptyCallbacks(){
        public void onClickButton( DialogInterface dialog, int which ){
            super.onClickButton( dialog, which );
            Utils.logger("CipLibrary.DialogHelper","Callback onClickButton() called!", Utils.LOG_INFO );
        }
    };

//    private static final int DEFAULT_TITLE = R.string.dialoghelper_title;
    private static final int DEFAULT_MESSAGE = R.string.dialoghelper_message;
    private static final int BTN_TEXT = R.string.dialoghelper_neutral_btn_text;

    public static void show( Context context, int title ){
        show( context, title, DEFAULT_MESSAGE, BTN_TEXT, callbacks );
    }

    public static void show( Context context, int title, int message ){
        show( context, title, message, BTN_TEXT, callbacks );
    }

    public static void show( Context context, int title, int message, int btn_text ){
        show( context, title, message, btn_text, callbacks );
    }

    public static void show( Context context, int title, int message, int btn_text, final Callbacks callbacks ){
        try {
            new AlertDialog.Builder(context)
                .setTitle(context.getString(title))
                .setMessage(context.getString(message))
                .setNeutralButton(context.getString(btn_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbacks.onClickButton(dialog, which);
                    }
                }).show();
        }catch( NotFoundException e ){
            Utils.logger("Cip.WelcomeDialog", e.getMessage(), Utils.LOG_ERROR );
        }
    }


    /////////////////////////////////////
    //  Callbacks

    public static interface Callbacks {
        public void onClickButton( DialogInterface dialog, int which );
    }

    public static class EmptyCallbacks implements Callbacks {
        public void onClickButton( DialogInterface dialog, int which ){
            dialog.dismiss();
        }
    }


}
