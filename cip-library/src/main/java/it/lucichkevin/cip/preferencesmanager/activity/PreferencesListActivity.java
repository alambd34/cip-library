package it.lucichkevin.cip.preferencesmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import it.lucichkevin.cip.R;


/**
 * Created by kevin on 11/09/14.
 */
public abstract class PreferencesListActivity extends Activity {

    protected ItemPreferece[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencesmanager_activity);

        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////

        addItems();

        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////


        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
            .replace(android.R.id.content, new PreferencesListFragment())
            .commit();

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        switch( item.getItemId() ){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public ItemPreferece[] getItems(){
        return items;
    }

//    protected void addItems() {
//        items = new ItemPreferece[4];
//        items[0] = new ItemPreferece("DEBUG_LOG", "Debug Log", "Check this if you want debug logging enabled on logcat", ItemPreferece.TYPE_SWITCH );
//        items[1] = new ItemPreferece("TOASTER_TO_LOGCAT", "Toast vs. Log", "Check this and all toasts will be converted in Info Log", ItemPreferece.TYPE_SWITCH );
//        items[2] = new ItemPreferece("KEY_TEST1", "Test Timepicker", "hihihih 1", ItemPreferece.TYPE_TIMEPICKER );
//        items[3] = new ItemPreferece("KEY_TEST2", "Test Datepicker", "hihihih 2", ItemPreferece.TYPE_DATEPICKER );
//    }

    protected abstract void addItems();
}
