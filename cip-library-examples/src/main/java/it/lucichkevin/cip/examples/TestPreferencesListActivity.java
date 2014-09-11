package it.lucichkevin.cip.examples;

import it.lucichkevin.cip.preferencesmanager.activity.ItemPreferece;
import it.lucichkevin.cip.preferencesmanager.activity.PreferencesListActivity;

/**
 * Created by kevin on 11/09/14.
 */
public class TestPreferencesListActivity extends PreferencesListActivity {

    protected void addItems() {
        items = new ItemPreferece[4];
        items[0] = new ItemPreferece("DEBUG_LOG", "Debug Log", "Check this if you want debug logging enabled on logcat", ItemPreferece.TYPE_SWITCH );
        items[1] = new ItemPreferece("TOASTER_TO_LOGCAT", "Toast vs. Log", "Check this and all toasts will be converted in Info Log", ItemPreferece.TYPE_SWITCH );
        items[2] = new ItemPreferece("KEY_TEST1", "Test Timepicker", "hihihih 1", ItemPreferece.TYPE_TIMEPICKER );
        items[3] = new ItemPreferece("KEY_TEST2", "Test Datepicker", "hihihih 2", ItemPreferece.TYPE_DATEPICKER );
    }

}
