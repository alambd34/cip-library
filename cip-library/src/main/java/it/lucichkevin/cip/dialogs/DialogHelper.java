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

    private Context context;
    private int title;
    private int message;
    private int btn_text;

    //  Default behavior - Log (if in debug) and close dialog
    private DialogHelper.Callbacks callbacks = new DialogHelper.EmptyCallbacks(){
        public void onClickButton( DialogInterface dialog, int which ){
            super.onClickButton( dialog, which );
            Utils.logger("CipLibrary.WelcomeDialog","Callback onClickButton() called!", Utils.LOG_INFO );
        }
    };

    private static final int DEFAULT_TITLE = R.string.welcomedialog_title;
    private static final int DEFAULT_MESSAGE = R.string.welcomedialog_message;
    private static final int BTN_TEXT = R.string.welcomedialog_neutral_btn_text;

    public DialogHelper(){
        this(Utils.getContext());
    }

    public DialogHelper( Context context ){
        this( Utils.getContext(), DEFAULT_TITLE, DEFAULT_MESSAGE, BTN_TEXT );
    }

    public DialogHelper( Context context, int title, int message, int btn_text, Callbacks callbacks ){
        this(context, title, message, btn_text);
        setCallbacks(callbacks);
    }

    public DialogHelper( Context context, int title, int message, int btn_text ){
        this.context = context;
        this.title = title;
        this.message = message;
        this.btn_text = btn_text;
    }

    public void setCallbacks( Callbacks callbacks ){
        this.callbacks = callbacks;
    }

    public void show(){
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
