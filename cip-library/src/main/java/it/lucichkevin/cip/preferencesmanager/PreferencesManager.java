package it.lucichkevin.cip.preferencesmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import it.lucichkevin.cip.Utils;

/**
    Base utility to manager shared preferences without call the specify native android methods.
    it is recommended to create your own object (which extends PreferencesManager) where you can specify shortcuts for setting keys

    <code>

    class MyPreferences extends PreferencesManager{

        //  Costant to use memory the key of preference ( the use is optional :) )
        private static final String KEY_NAME = "test_name";

        //  Your method to save a name of user (it's an it.lucichkevin.cip.examples.examples, you can save all you want!)
        public static void setName( String name ){
            setPreferences( KEY_NAME, name );
        }

        //  Your method to get the name of the user. NOTE: you must define a "default" value, that it's returned if the key passed don't exists.
        public static String getName( String default_name ){
            return getPreferences().getString(KEY_NAME, default_name);
        }
        //  Alias of the method "getName()" to not define anywhere the default value to return (it.lucichkevin.cip.examples.examples of use)
        public static String getName(){
            return getName("default_name");
        }
    }

    //  Somewhere else...
    MyView.setText("Is my first run? "+ String.valueOf(MyPreferences.isFirstRun()) );
    MyView.setText("My name is: "+ MyPreferences.getName() );

    </code>

    @author     Kevin Lucich    29/08/2014
    @version	1.1.0
    @since      0.3.0

 */
public class PreferencesManager {

    //  is_first_run is a placeholder for all session of App, started is NULL for read the real value from Prefereces :)
    private static Boolean is_first_run = null;
    private static boolean initialized = false;
    private static Context context = null;

    public static void init( Context context ){
        if( PreferencesManager.isInitialized() ){
//            Utils.logger("CIP.PreferencesManager", "PreferencesManager is already initialized, don't worry, i will change only the context :)", Utils.LOG_INFO );
            Utils.logger("CIP.PreferencesManager", "PreferencesManager is already initialized :)", Utils.LOG_INFO );
            return;
        }
        PreferencesManager.initialized = true;
        setContext(context);

        if( PreferencesManager.isFirstRun() ){
            PreferencesManager.setFirstRun(false,false);
        }
    }

    public static Context getContext() throws Exception {
        if( !isInitialized() ){
            throw new Exception("You must call Utils.init() in the first Activity available");
        }
        return context;
    }
    public static void setContext(Context context) {
        PreferencesManager.context = context;
    }
    public static boolean isInitialized(){
        return PreferencesManager.initialized;
    }

    ///////////////////////////////////////
    //  Defaults

    protected static final String DEFAULT_LANGUAGE = "en";

    ///////////////////////////////////////
    //  Costants

    protected static final String STRING_LOG = "PREFERENCESMANAGER";
    protected static final String KEY_FIRST_RUN_OF_APP = "FIRST_RUN_OF_APP";
    protected static final String KEY_DEBUG_LOG = "DEBUG_LOG";
    protected static final String KEY_TOASTER_TO_LOGCAT = "TOASTER_TO_LOGCAT";

    ///////////////////////////////////////
    //  Helper

    /**
        Set the default of shared preferences
    */
    public static void setDefaultPreferences(){
        PreferencesManager.setDebugLog(true);
        PreferencesManager.setToasterToLogcat(false);
    }

    /**
        Return a boolean associated to the key of setting param, if the key don't exist return false
        @param  key     The key of setting to check
        @see    #is( String, Boolean )
    */
    public static boolean is( String key ){
        return PreferencesManager.is( key, false );
    }

    /**
        Return a boolean associated to the key of setting param
        @param  key             The key of setting to check
        @param  default_value   The value to return if the key don't exist
        @see    #is( String )
    */
    public static boolean is( String key, Boolean default_value ){
        try{
            return PreferencesManager.getPreferences().getBoolean(key, default_value);
        }catch( NullPointerException e ){
            Log.d( STRING_LOG, "Error when getting SharedPreferences [key = \"" + key + "\"] \n" + e.getMessage());
            return false;
        }
    }


    public static SharedPreferences getPreferences(){
        try{
            return PreferenceManager.getDefaultSharedPreferences(getContext());
        }catch( NullPointerException e ){
            Log.d( STRING_LOG, "Error when getting a null SharedPreferences, return a Implementation of NullObject ");
            return new SharedPreferencesNull();
        }catch( Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    /**
        Get an editor instance
        @return Editor for SharedPreferences
    */
    protected static Editor getEditor(){
        try {
            return (PreferenceManager.getDefaultSharedPreferences(getContext())).edit();
        }catch( Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    /**
        Set the preference with the value
        @param  key     The key of setting to set
        @param  value   The value to set
    */
    protected static void setPreferences( String key, Boolean value ){
        Editor editor = getEditor();
        editor.putBoolean( key, value );
        editor.commit();
    }

    /**
        Set the preference with the value
        @param  key     The key of setting to set
        @param  value   The value to set
    */
    protected static void setPreferences( String key, int value ){
        Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     Set the preference with the value
     @param  key     The key of setting to set
     @param  value   The value to set
     */
    protected static void setPreferences( String key, long value ){
        Editor editor = getEditor();
        editor.putLong( key, value );
        editor.commit();
    }

    /**
        Set the preference with the value
        @param  key     The key of setting to set
        @param  value   The value to set
    */
    protected static void setPreferences( String key, float value ){
        Editor editor = getEditor();
        editor.putFloat( key, value );
        editor.commit();
    }

    /**
        Set the preference with the value
        @param  key     The key of setting to set
        @param  value   The value to set
    */
    protected static void setPreferences( String key, String value ){
        Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    /**
        Set the preference with the value
        @param  key     The key of setting to set
        @param  value   The value to set
    */
    protected static void setPreferences( String key, Set<String> value ){
        Editor editor = getEditor();
        editor.putStringSet(key, value);
        editor.commit();
    }

    ////////////////////////////////////////////////

    public static boolean isToasterToLogcat(){
        return PreferencesManager.is(KEY_TOASTER_TO_LOGCAT);
    }
    public static void setToasterToLogcat( boolean b ){
        PreferencesManager.setPreferences( KEY_TOASTER_TO_LOGCAT, b );
    }

    public static boolean isDebugLog(){
        return PreferencesManager.is(KEY_DEBUG_LOG);
    }
    public static void setDebugLog( boolean b ){
        PreferencesManager.setPreferences( KEY_DEBUG_LOG, b );
    }

    /**
     *  Return true if is the first run of application
     *  @return     boolean
     */
    public static boolean isFirstRun(){
        //  The variable is using as placeholder
        if( is_first_run == null ){
            is_first_run = PreferencesManager.is(KEY_FIRST_RUN_OF_APP, true);
        }
        return is_first_run;
    }
    /**
     *  Set the key first_run in SharedPreferences
     *  @param  value   The value will be set to key
     *  @see    #setFirstRun(boolean, boolean)
     */
    public static void setFirstRun( boolean value ){
        setFirstRun(value,false);
    }
    /**
     *  Set the key first_run in SharedPreferences
     *  @param  value       The value will be set to key
     *  @param  thisSession If passed TRUE, overwrite the check for the session in running (ATTENTION: usare con cautela)
     */
    public static void setFirstRun( boolean value, boolean thisSession ){
        if( thisSession ){
            PreferencesManager.is_first_run = value;
        }
        PreferencesManager.setPreferences( KEY_FIRST_RUN_OF_APP, value );
    }


    ///////////////////////////////////////
    //  Utils method

    /**
        Get the PackageInfo of package name or null if exception occurs
    */
    public static PackageInfo getPackageInfo(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getContext().getPackageManager().getPackageInfo( getContext().getPackageName(), 0);
        }catch( PackageManager.NameNotFoundException e ){
            e.printStackTrace();
            return null;
        }catch( Exception e ){
            e.printStackTrace();
            packageInfo = new PackageInfo();
//            packageInfo.versionCode = 1;
//            packageInfo.versionName = "not available";
        }

        return packageInfo;
    }

    /**
        Return the version code of package or 0 if exception occurs
    */
    public static int getVersionCode(){
        PackageInfo packageInfo = getPackageInfo();
        return (packageInfo != null) ? packageInfo.versionCode : 0;
    }

    /**
        Return the version name of package or 0 if exception occurs
    */
    public static String getVersionName(){
        PackageInfo packageInfo = getPackageInfo();
        return (packageInfo != null) ? packageInfo.versionName : "0";
    }

    /**
        Return the language or the default if exception occurs
    */
    public static String getLanguage(){

        String language = Locale.getDefault().getLanguage();
        if( language.equals("") ){
            language = PreferencesManager.DEFAULT_LANGUAGE;
        }

        return language;
    }








    protected static class SharedPreferencesNull implements SharedPreferences {
        @Override
        public Map<String, ?> getAll() { return null; }
        @Override
        public String getString(String s, String s2) { return null; }
        @Override
        public Set<String> getStringSet(String s, Set<String> strings) { return null; }
        public int getInt(String s, int i){ return 0; }
        public long getLong(String s, long l){ return 0; }
        public float getFloat(String s, float v){ return 0; }
        public boolean getBoolean(String s, boolean b){ return false; }
        @Override
        public boolean contains(String s) { return false; }
        @Override
        public Editor edit() { return null; }
        @Override
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {}
        @Override
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {}
    }

}
