package it.lucichkevin.cip.preferences.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.preferences.AbstractPreference;
import it.lucichkevin.cip.preferences.CategoryPreference;


/**
 *  Create a base Activity with inside a Fragment (instance of AbstractPreferencesListFragment) with the list of Preferences
 *
 *  <code>
 *		public class MyPreferencesListActivity extends PreferencesListActivity {
 *			//@Override
 *			public void populatePreferencesList() {
 *				//  Add your keys here... :)
 *				//  addItem(new ItemPreference("KEY", R.string.KEY_TITLE, R.string.KEY_SUMMARY, ItemPreference.TYPE_X ));
 *			}
 *		}
 *  </code>
 *
 *  @author		Kevin Lucich (2014-09-11)
 *  @version 	2.0.0 (2018-08-13)
 *  @update
 *  	2.0.0 [2018-08-13]
 *  		Refactoring AbstractPreferencesListActivity and PreferencesListFragment
 *  		[REMOVE] Removed  AbstractPreferencesListFragment
 *
 */
public abstract class AbstractPreferencesListActivity extends AppCompatActivity {

	protected PreferencesListFragment fragment = new PreferencesListFragment();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_abstract_preferences_list);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		populatePreferencesList();

		getFragmentManager().beginTransaction()
			.replace( R.id.preferences_fragment_list, fragment )
			.commit();
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){
		switch( item.getItemId() ){
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	/**
	 *  Populate in this method the ArrayList with ItemPreference
	 */
	public abstract void populatePreferencesList();

	/**
	 *  Alias to call the method populatePreferencesListWithDefault() to AbstractPreferencesListFragment
	 */
	protected void populatePreferencesListWithDefault(){
		fragment.populatePreferencesListWithDefault();
	}

	public void addItem( AbstractPreference item ){
		fragment.addItem(item);
	}

	public void addCategory( CategoryPreference category ){
		fragment.addCategory(category);
	}

	/////////////////////////////////////////
	//  Getters and setters



}