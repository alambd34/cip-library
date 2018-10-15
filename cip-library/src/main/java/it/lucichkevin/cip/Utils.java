package it.lucichkevin.cip;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;
import java.util.Locale;

import it.lucichkevin.cip.preferences.PreferencesManager;

/**
 * @author	Kevin Lucich 2014-02-14
 * @version	1.1.0 (2018-08-24)
 *
 * @update
 * 	v1.1.0 (2018-08-24)
 * 		Fix Utils.getDeviceId();
 *
 * Singleton class of utility methods. Instantiate with Utils.init()
 */
public class Utils {

	/**
	 * Public vars for logging
	 */
	public static final int LOG_DEBUG = 0;
	public static final int LOG_INFO = 1;
	public static final int LOG_ERROR = 2;

	protected static Context context = null;
	protected static String device_id = null;
	protected static String APP_TAG = "";

	public static void init( Context context ){
		Utils.init( context, context.getPackageName() );
	}

	public static void init( Context context, String app_tag ){
		setContext(context);
		setAppTag(app_tag);
		PreferencesManager.init(context);
		AndroidThreeTen.init(context);
	}

	/**
	 * Return a string with a human readable bytes count
	 * @param	bytes	The number bytes that user will read
	 * @param	si		True to use the International System of Units (SI)
	 */
	public static String humanReadableByteCount( long bytes, boolean si ){
		int unit = si ? 1000 : 1024;
		if( bytes < unit ){
			return bytes + " B";
		}
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format( Locale.ITALIAN, "%.2f %sB", bytes / Math.pow(unit, exp), pre );
	}

	/***
	 * Return a string with a human readable bytes count - using the International System of Units (SI)
	 * @see #humanReadableByteCount(long, boolean)
	 */
	public static String humanReadableByteCount( long bytes ){
		return humanReadableByteCount(bytes,true);
	}

	//////////////////////////////////////////////
	//  Getters and Setters

	public static Context getContext() {
		return context;
	}
	protected static void setContext( Context _context ){
		context = _context;
	}

	public static String getAppTag(){
		return APP_TAG;
	}
	protected static void setAppTag(){
		setAppTag(context.getPackageName());
	}

	protected static void setAppTag(String app_tag){
		APP_TAG = app_tag;
	}

	public static String getDeviceId(){
		if( device_id == null ){
			device_id = PreferencesManager.getDeviceId();
		}
		return device_id;
	}

	public static String getDeviceName(){
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;

		if( model.startsWith(manufacturer) ){
			return capitalize(model);
		}

		return capitalize(manufacturer) + " " + model;
	}

	//////////////////////////////////////////////
	//  Methods

	public String pad( int c ){
		return pad(c,1);
	}
	public String pad( int c, int n ){
		if( c >= 10 ) {
			return String.valueOf(c);
		}else{
			return (new String(new char[n]).replace("\0","0")) + String.valueOf(c);
		}
	}

	public static String capitalize(String s) {
		if( s == null || s.length() == 0 ){
			return "";
		}
		char first = s.charAt(0);
		if( Character.isUpperCase(first) ){
			return s;
		}
		return Character.toUpperCase(first) + s.substring(1);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById( View container, int id ){
		View view = container.findViewById(id);
		return (T) view;
	}

	public static <T extends View> T findViewById( Activity activity, int id ){
		return Utils.findViewById( activity.getWindow().getDecorView(), id );
	}

	/**
	 *  Get a List of Object of T type, and return an array
	 *  @param      list    List<T>	The list to convert in array
	 *  @return     array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] listToArray( List<T> list ){
		return (T[]) list.toArray();
	}


	/**
	 *	Log a message using a default app tag
	 *	@param  message     String	Message to log
	 *	@param  logLevel    int		Level of the log
	 *	@since 0.0.1
	 *	@see #logger(String, String, int)
	 */
	public static void logger( String message, int logLevel ){
		Utils.logger( getAppTag(), message, logLevel );
	}

	/**
	 *	Log a debug message using a default app tag
	 *	@param	message		Message to log
	 */
	public static void loggerDebug( String message ){
		Utils.logger( getAppTag(), message, Utils.LOG_DEBUG );
	}

	/**
	 *	Log a debug message using a default app tag
	 *	@param	message		Message to log
	 *	@param  exception	An exception to log
	 */
	public static void loggerDebug( String message, Throwable exception ){
		Utils.loggerDebug( message +"\n\n"+ Log.getStackTraceString(exception) );
	}

	/**
	 *	Prints log ONLY if PreferencesManager.isDebugLog() it's true.
	 *	@since  v0.4.0
	 *	@param  app_tag     App tag to search the log
	 *	@param  message     Message to log
	 *	@param  logLevel    Level of the log
	 */
	public static void logger( String app_tag, String message, int logLevel ){
		if( PreferencesManager.shouldBeLogToLogcat() ){
			switch( logLevel ) {
				case LOG_DEBUG:
					Log.d( app_tag, message );
					break;
				case LOG_ERROR:
					Log.e( app_tag, message );
					break;
				case LOG_INFO:
				default:
					Log.i( app_tag, message );
					break;
			}
		}
	}


	///////////////////////////////////////////////////////
	//  Toaster

	/**
	 @since      Cip v0.0.1
	 Utils.Toaster( [context], "test", Utils.Toaster.LENGTH_LONG );
	 */
	public static class Toaster{

		public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
		public static final int LENGTH_LONG = Toast.LENGTH_SHORT;

		private static void showToast( Context context, String msg ){
			Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
		}

		private static void showToast( final Context context, final String msg, final int time ){

			if( context instanceof Activity ){
				((Activity) context).runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText( (Activity) context, msg, time ).show();
					}
				});
				return;
			}

			Toast.makeText( context, msg, time ).show();
		}

		private static void showLongToast(Context context, final String msg, final int seconds){

			final Toast toast = Toast.makeText( context, msg, Toast.LENGTH_SHORT );

			toast.show();

			new CountDownTimer( seconds, 1000 ){

				public void onTick( long millisUntilFinished ){
					toast.show();
				}
				public void onFinish(){
					toast.show();
				}

			}.start();

		}
	}

	/**
	 *  Create a Toast on the device
	 *  @param  context     The context where display the toast
	 *  @param  msg         The message to display in the toast
	 */
	public static void Toaster( Context context, String msg ){
		Toaster.showToast(context, msg);
	}

	/**
	 *  Create a Toast on the device
	 *  @param  context     The context where display the toast
	 *  @param  msg         The message to display in the toast
	 *  @param  seconds     The time to view the toast
	 */
	public static void Toaster( Context context, String msg, int seconds ){
		if( seconds > 1 ){
			Toaster.showLongToast(context, msg, seconds);
		}else{
			Toaster.showToast(context, msg, seconds);
		}
	}



	///////////////////////////////////////////////////////
	//  App

	public static class App extends AsyncTask<Boolean,String,Boolean> {

		private final Callbacks callbacks;

		protected App( Callbacks callbacks ){
			this.callbacks = callbacks;
		}

		//  This method is called before the SplashScreen, if version is updated execute all method inside writted ;)
		public static void onUpdateVersion( Callbacks callbacks ) {
			(new Utils.App(callbacks)).execute();
		}

		public static PackageInfo getPackageInfo(){
			try {
				return Utils.getContext().getPackageManager().getPackageInfo( Utils.getContext().getPackageName(), 0);
			}catch( PackageManager.NameNotFoundException | NullPointerException e ){
				e.printStackTrace();
				PackageInfo PI = new PackageInfo();
				PI.versionCode = 0;
				PI.versionName = "N/A";
				return PI;
			}
		}
		public static String getVersionName(){
			return getPackageInfo().versionName;
		}
		public static int getVersionCode(){
			return getPackageInfo().versionCode;
		}


		@Override
		protected Boolean doInBackground( Boolean... objects ) {

			if( PreferencesManager.getAppVersionAtLastAccess() >= App.getVersionCode() ) {
				return false;
			}

			PreferencesManager.setAppVersionAtLastAccess();
			PreferencesManager.setDefaultPreferences();

			callbacks.onUpdate();

			return true;
		}

		@Override
		protected void onPostExecute( final Boolean version_has_been_updated ) {
			super.onPostExecute( version_has_been_updated );
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					callbacks.onEnd(version_has_been_updated);
				}
			}, 50);
		}

		//	Callbacks object
		public static abstract class Callbacks{
			public abstract void onUpdate();
			public abstract void onEnd( boolean is_version_updated );
		}
	}

}