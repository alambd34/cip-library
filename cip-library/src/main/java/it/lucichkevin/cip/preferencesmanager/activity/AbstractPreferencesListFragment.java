package it.lucichkevin.cip.preferencesmanager.activity;

import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.preferencesmanager.PreferencesManager;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.DatePickerPreference;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.MinutePickerPreference;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.TimePickerPreference;


/**
 *  PreferenceFragment with the list of preferences listed in ArrayList items
 *  @author     Kevin Lucich    11/09/2014
 *  @since      CipLibrary v0.5.0
 */
public abstract class AbstractPreferencesListFragment extends PreferenceFragment {

    protected ArrayList<ItemPreference> items = new ArrayList<ItemPreference>();

    public AbstractPreferencesListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populatePreferencesList();
        setPreferenceScreen( createPreferenceHierarchy() );
    }

    protected PreferenceScreen createPreferenceHierarchy() {

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());

//        PreferenceCategory category = newCategory("Test category");
//        root.addPreference(category);

        int size = items.size();

        if( size == 0 ){
            populatePreferencesListWithDefault();
        }

        for( final ItemPreference item : items ){

            Preference preference;

            switch (item.getType()) {

                case ItemPreference.TYPE_SWITCH:
                    if (Build.VERSION.SDK_INT < 14) {
                        preference = new CheckBoxPreference(getActivity());
                    } else {
                        preference = new SwitchPreference(getActivity());
                    }
                    break;

                case ItemPreference.TYPE_TIMEPICKER:
                    preference = new TimePickerPreference(getActivity(), null) {
                        @Override
                        public void onSetTime(int hour, int minute) {
                            //  Do nothing...
                        }
                    };
                    break;

                case ItemPreference.TYPE_DATEPICKER:
                    preference = new DatePickerPreference(getActivity(), null) {
                        @Override
                        public void onSetDate(Date date) {
                            //  Do nothing...
                        }
                    };
                    break;

                case ItemPreference.TYPE_MINUTEPICKER:
                    preference = new MinutePickerPreference(getActivity(), null) {
                        @Override
                        public void onSetTime( int minute ) {
                            //  Do nothing...
                        }
                    };
                    break;

                default:
                    //  Type not valid, no will be added the preference :)
                    Utils.logger("[CipLibrary -> createPreferenceHierarchy()] Type preference ("+ item.getType() +") unknown", Utils.LOG_ERROR );
                    continue;
            }

            preference.setKey(item.getKey());
            preference.setTitle(item.getTitle());
            preference.setSummary(item.getSummary());

            preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange( Preference preference, Object newValue ){
                    if( item.getOnPreferenceChangeListener() == null ){
                        //  Communicate to Preferences method that new value must be saved
                        return true;
                    }
                    return item.getOnPreferenceChangeListener().onPreferenceChange( preference, newValue );
                }
            });
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick( Preference preference ){
                    if( item.getOnPreferenceClickListener() == null ){
                        //  Communicate to Preferences method that click event is not handled
                        return false;
                    }
                    return item.getOnPreferenceClickListener().onPreferenceClick( preference );
                }
            });

            root.addPreference(preference);
        }

        return root;
    }

    protected PreferenceCategory newCategory( String title ){
        PreferenceCategory inlinePrefCat = new PreferenceCategory(getActivity());
        inlinePrefCat.setTitle( title );
        return inlinePrefCat;
    }

    /**
     *  Populate in this method the ArratList with ItemPreferece
     */
    protected abstract void populatePreferencesList();

    protected void populatePreferencesListWithDefault(){
        items.add(new ItemPreference("DEBUG_LOG", "Debug Log", "Check this if you want debug logging enabled on logcat", ItemPreference.TYPE_SWITCH ));
        items.add(new ItemPreference("TOASTER_TO_LOGCAT", "Toast vs. Log", "Check this and all toasts will be converted in Info Log", ItemPreference.TYPE_SWITCH ));
    }

    protected void setItems( ArrayList<ItemPreference> items ){
        this.items = items;
    }
    protected ArrayList<ItemPreference> getItems(){
        return items;
    }

}


//  Creata per rendere pubblici i metodi "protetti" nel PreferecesManager
class PreferencesInterface extends PreferencesManager{
    public static void setPreferences( String key, Boolean value ){
        PreferencesManager.setPreferences(key, value);
    }
    public static void setPreferences( String key, int value ){
        PreferencesManager.setPreferences(key, value);
    }
    public static void setPreferences( String key, long value ){
        PreferencesManager.setPreferences(key, value);
    }
    public static void setPreferences( String key, float value ){
        PreferencesManager.setPreferences(key, value);
    }
    public static void setPreferences( String key, String value ){
        PreferencesManager.setPreferences(key, value);
    }
    public static void setPreferences( String key, Set<String> value ){
        PreferencesManager.setPreferences(key, value);
    }
}