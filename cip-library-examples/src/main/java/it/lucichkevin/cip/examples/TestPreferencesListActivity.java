package it.lucichkevin.cip.examples;

import it.lucichkevin.cip.preferencesmanager.activity.AbstractPreferencesListActivity;
import it.lucichkevin.cip.preferencesmanager.activity.ItemPreference;

/**
 * Created by kevin on 11/09/14.
 */
public class TestPreferencesListActivity extends AbstractPreferencesListActivity {

    @Override
    protected void populatePreferencesList() {
        super.populatePreferencesListWithDefault();

        items.add(new ItemPreference( "TEST_DATEPICKER", R.string.abc_action_mode_done, R.string.abc_action_mode_done, ItemPreference.TYPE_DATEPICKER ));
        items.add(new ItemPreference( "TEST_TIMEPICKER", R.string.abc_action_mode_done, R.string.abc_action_mode_done, ItemPreference.TYPE_TIMEPICKER ));
        //  Others items...
    }

}
