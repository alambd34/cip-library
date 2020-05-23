package it.lucichkevin.cip.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;


public class PickerDialogBuilder {

	public static class DatePickerDialog {

		public static android.app.DatePickerDialog get( Context context, LocalDate date_selected, android.app.DatePickerDialog.OnDateSetListener onDateSetListener ){
			if( date_selected == null ){
				date_selected = LocalDate.now();
			}
			return new android.app.DatePickerDialog( context, onDateSetListener, date_selected.getYear(), date_selected.getMonth().getValue(), date_selected.getDayOfMonth() );
		}

		public static void show( Context context, LocalDate date_selected, android.app.DatePickerDialog.OnDateSetListener onDateSetListener ){
			PickerDialogBuilder.DatePickerDialog.get( context, date_selected, onDateSetListener ).show();
		}
	}


	public static class TimePickerDialog {

		protected static int style_time_picker = android.R.style.Theme_Material_Light_Dialog;

		public static android.app.TimePickerDialog get( Context context, LocalTime time_selected, android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener ){
			if( time_selected == null ){
				time_selected = LocalTime.now();
			}
			return new android.app.TimePickerDialog( context, style_time_picker, onTimeSetListener, time_selected.getHour(), time_selected.getMinute(), true );
		}

		public static void show( Context context, LocalTime time_selected, android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener ){
			PickerDialogBuilder.TimePickerDialog.get( context,time_selected,onTimeSetListener).show();
		}

		public static void setTimePickerStyle( @IdRes int style_time_picker ){
			PickerDialogBuilder.TimePickerDialog.style_time_picker = style_time_picker;
		}

	}



	public static class NumberPickerDialog {

		public static AlertDialog get(Context context, int min_value, int max_value, Integer value_selected, final OnNumberPickerChangeListener onNumberPickerChangeListener ){

			LinearLayout linearLayout = new LinearLayout(context);
			linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
			));
			linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

			final NumberPicker number_picker = new NumberPicker(context);
			number_picker.setGravity(Gravity.CENTER);
			number_picker.setMinValue( min_value );
			number_picker.setMaxValue( max_value );

			if( value_selected != null ){
				number_picker.setValue(value_selected);
			}

			linearLayout.addView(number_picker);

			return new AlertDialog.Builder(context)
				.setView(linearLayout)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if( onNumberPickerChangeListener != null ){
							onNumberPickerChangeListener.onNumberPickerChange( number_picker, number_picker.getValue() );
						}
					}
				})
				.setNegativeButton("Chiudi", new DialogInterface.OnClickListener() {
					@Override
					public void onClick( DialogInterface dialog, int which ){
						dialog.dismiss();
					}
				})
				.create();
		}

		public static void show( Context context, int min_value, int max_value, Integer value_selected, final OnNumberPickerChangeListener onNumberPickerChangeListener ){
			PickerDialogBuilder.NumberPickerDialog.get( context, min_value, max_value, value_selected, onNumberPickerChangeListener ).show();
		}

		public static abstract class OnNumberPickerChangeListener {
			public abstract void onNumberPickerChange( NumberPicker number_picker, int number );
		}
	}


}
