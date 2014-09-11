package it.lucichkevin.cip.preferencesmanager.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import java.util.Date;
import java.util.Set;

import it.lucichkevin.cip.preferencesmanager.PreferencesManager;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.DatePickerPreference;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.TimePickerPreference;


/**
 * Created by kevin on 02/09/2014.
 */
public class PreferencesListFragment extends PreferenceFragment {

    private Activity activity;

    public PreferencesListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen( createPreferenceHierarchy() );
    }

    private PreferenceScreen createPreferenceHierarchy() {

        ItemPreferece[] items = ((PreferencesListActivity) activity).getItems();

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());

//        PreferenceCategory category = newCategory("Test category");
//        root.addPreference(category);

        for( int i=0; i<items.length; i++ ){

            Preference input = null;
            final ItemPreferece item = items[i];

            switch( item.getType() ){

                case ItemPreferece.TYPE_SWITCH:
                    if( Build.VERSION.SDK_INT < 14 ){
                        input = new CheckBoxPreference(getActivity());
                    }else{
                        input = new SwitchPreference(getActivity());
                    }
                    break;

                case ItemPreferece.TYPE_TIMEPICKER:
                    input = new TimePickerPreference(getActivity(), null) {
                        @Override
                        public void onSetTime( int hour, int minute ){
                            long timeInMillies = (hour*60 + minute)*60000;
                            PreferencesInterface.setPreferences( item.getKey(), timeInMillies );
                        }
                    };
                    break;

                case ItemPreferece.TYPE_DATEPICKER:
                    input = new DatePickerPreference(getActivity(), null) {
                        @Override
                        public void onSetDate( Date date ){
                            PreferencesInterface.setPreferences( item.getKey(), date.getTime() );
                        }
                    };
                    break;
            }

            input.setKey( item.getKey() );
            input.setTitle( item.getTitle() );
            input.setSummary( item.getSummary() );
            root.addPreference(input);
        }

        return root;
    }

    private PreferenceCategory newCategory( String title ){
        PreferenceCategory inlinePrefCat = new PreferenceCategory(getActivity());
        inlinePrefCat.setTitle( title );
        return inlinePrefCat;
    }


    ////////////////////////////////////////////////

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}


//  Creata per rendere pubblici i metodi "protetti" nel PreferecesManager
class PreferencesInterface extends PreferencesManager{
    public static void setPreferences( String key, Boolean value ){
        setPreferences(key, value);
    }
    public static void setPreferences( String key, int value ){
        setPreferences(key, value);
    }
    public static void setPreferences( String key, long value ){
        setPreferences(key, value);
    }
    public static void setPreferences( String key, float value ){
        setPreferences(key, value);
    }
    public static void setPreferences( String key, String value ){
        setPreferences(key, value);
    }
    public static void setPreferences( String key, Set<String> value ){
        setPreferences(key, value);
    }
}