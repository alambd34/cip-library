package it.lucichkevin.cip.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;

import it.lucichkevin.cip.*;
import it.lucichkevin.cip.Utils;

/**
 * @author  Kevin Lucich (04/09/14)
 */
public class DialogHelper {

//    private static final int DEFAULT_TITLE = R.string.dialoghelper_title;
    protected static final int DEFAULT_MESSAGE = R.string.dialoghelper_message;
    protected static final int BTN_TEXT = R.string.dialoghelper_neutral_btn_text;

    //  Default behavior - Log (if in debug) and close dialog
    protected static DialogHelper.Callbacks callbacks = new DialogHelper.EmptyCallbacks(){
        public void onClickButton( DialogInterface dialog, int which ){
            super.onClickButton( dialog, which );
            Utils.logger("CipLibrary.DialogHelper","Callback onClickButton() called!", Utils.LOG_INFO );
        }
    };

    protected Context context = null;
    protected Integer title = null;
    protected Integer message = DEFAULT_MESSAGE;
    protected Integer btn_text = BTN_TEXT;
    protected AlertDialog dialog = null;

    public DialogHelper(){

    }
    public DialogHelper( Context context ){
        this.context = context;
    }

    public AlertDialog create( String title ){
        this.dialog = DialogHelper.create( this.context, title, context.getString(DEFAULT_MESSAGE), context.getString(BTN_TEXT), callbacks );
        return this.dialog;
    }

    public AlertDialog create( int title, int message, int btn_text ){
        this.dialog = DialogHelper.create(this.context, title, message, btn_text, callbacks );
        return this.dialog;
    }

    public AlertDialog show(){
        this.dialog = DialogHelper.create( this.context, "", "", context.getString(BTN_TEXT), callbacks );
        dialog.show();
        return dialog;
    }

    public AlertDialog show( String title ){
        this.create(title);
        dialog.show();
        return dialog;
    }

    public AlertDialog show( int title, int message, int btn_text ){
        this.dialog = this.create( title, DEFAULT_MESSAGE, BTN_TEXT );
        this.dialog.show();
        return this.dialog;
    }


    /////////////////////////////////////
    //  Static Helpers

    public static AlertDialog show( Context context, int title ){
        return show( context, title, DEFAULT_MESSAGE, BTN_TEXT, callbacks );
    }

    public static AlertDialog show( Context context, int title, int message ){
        return show( context, title, message, BTN_TEXT, callbacks );
    }

    public static AlertDialog show( Context context, int title, int message, int btn_text ){
        return show( context, title, message, btn_text, callbacks );
    }

    public static AlertDialog show( Context context, String title, String message, String btn_text, final Callbacks callbacks ){
        AlertDialog dialog = create(context, title, message, btn_text, callbacks );
        dialog.show();
        return dialog;
    }

    public static AlertDialog show( Context context, int title, int message, int btn_text, final Callbacks callbacks ){
        AlertDialog dialog = create(context, title, message, btn_text, callbacks );
        dialog.show();
        return dialog;
    }

    public static AlertDialog create( Context context, int title, int message, int btn_text, final Callbacks callbacks ){
        return create( context, context.getString(title), context.getString(message), context.getString(btn_text), callbacks );
    }

    public static AlertDialog create( Context context, String title, String message, String btn_text, final Callbacks callbacks ){
        try {
            return new AlertDialog.Builder(context)
                .setTitle( title )
                .setMessage( message )
                .setNeutralButton( btn_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbacks.onClickButton(dialog, which);
                    }
                }).create();
        }catch( NotFoundException e ){
            Utils.logger("Cip.WelcomeDialog", e.getMessage(), Utils.LOG_ERROR );
        }

        return new AlertDialog.Builder(context).create();
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


    /////////////////////////////////////
    //  Getters and Setters

    public Integer getTitle() {
        return title;
    }
    public void setTitle( Integer title ){
        this.title = title;
    }

    public Integer getMessage() {
        return message;
    }
    public void setMessage( Integer message ){
        this.message = message;
    }

    public Integer getBtnText() {
        return btn_text;
    }
    public void setBtnText( Integer btn_text ){
        this.btn_text = btn_text;
    }

    public static Callbacks getCallbacks() {
        return callbacks;
    }
    public static void setCallbacks(Callbacks callbacks) {
        DialogHelper.callbacks = callbacks;
    }


}
