package it.lucichkevin.cip.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import it.lucichkevin.cip.R;


public class ProgressAlertDialog {

	protected AlertDialog dialog = null;


	public ProgressAlertDialog( @NonNull Context context ){
		this(context,false);
	}

	public ProgressAlertDialog( @NonNull Context context, boolean is_cancelable ){
		dialog = AlertDialogHelper.Builder(context,R.layout.layout_loading_dialog)
					.setCancelable(is_cancelable)
					.create();
	}

	public void setCancelable( boolean is_cancelable ){
		dialog.setCancelable(is_cancelable);
	}

	public void setText( int resource_text ){
		getTextView().setText(resource_text);
	}

	public void setText( String text ){
		getTextView().setText(text);
	}

	protected TextView getTextView(){
		return ((TextView) dialog.findViewById(R.id.progress_dialog_text));
	}

	public ProgressAlertDialog show(){
		dialog.show();
		return this;
	}

	public ProgressAlertDialog dismiss(){
		dialog.dismiss();
		return this;
	}

	public boolean isShowing(){
		return dialog.isShowing();
	}

}
