package it.lucichkevin.cip.preferencesmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


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
 *  @author		Kevin Lucich
 *  @creation	2014-09-11
 *  @version 	2.0.0 (2018-08-13)
 *  @update
 *  	2.0.0 [2018-08-13]
 *  		Refactoring AbstractPreferencesListActivity and PreferencesListFragment
 *  		[REMOVE] Removed  AbstractPreferencesListFragment
 *
 */
public abstract class AbstractPreferencesListActivity extends AppCompatActivity {

	protected PreferencesListFragment fragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ItemPreference.setContext( AbstractPreferencesListActivity.this );

		fragment = new PreferencesListFragment();

		populatePreferencesList();

		getFragmentManager().beginTransaction()
			.replace( android.R.id.content,fragment)
			.commit();
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

	public void addItem( ItemPreference item ){
		fragment.addItem(item);
	}

	public void addCategory( CategoryPreference category ){
		fragment.addCategory(category);
	}

	/////////////////////////////////////////
	//  Getters and setters



}