package it.lucichkevin.cip.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.annotation.NonNull;

import it.lucichkevin.cip.R;


public class AlertDialogHelper {

	protected static boolean is_cancelable = false;

	public static AlertDialog.Builder Builder( Context context ){
		return Builder( context, android.R.layout.select_dialog_item );
	}

	public static AlertDialog.Builder Builder( Context context, int layout_resource_id ){
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(layout_resource_id);

		builder.setCancelable(is_cancelable);

		builder.setPositiveButton( R.string.dialog_close_button, new DialogInterface.OnClickListener(){
			@Override
			public void onClick( DialogInterface dialog, int which ){
				onClickButton(dialog,which);
			}
		});

		return builder;
	}

	/**
	 * Create and return a dialog using the builder param
	 * @param builder The builder to use to create the dialog
	 * @return The AlertDialog created and shown
	 */
	public static AlertDialog create( @NonNull AlertDialog.Builder builder ){

		AlertDialog dialog = builder.create();
		dialog.show();

		return dialog;
	}

	/**
	 * Create and return a default dialog
	 * @param context The context where will be shown the dialog
	 * @return The AlertDialog created and shown
	 */
	public static AlertDialog create( final Context context ){
		return create( AlertDialogHelper.Builder(context) );
	}

	/**
	 * Create and return a default dialog
	 * @param dialog	The dialog that received the click
	 * @param which 	The button that was clicked (ex. {@link DialogInterface#BUTTON_POSITIVE})
	 */
	public static void onClickButton( @NonNull DialogInterface dialog, int which ){
		dialog.dismiss();
	}
}
