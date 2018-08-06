package it.lucichkevin.cip.activity_drawer_menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;


public abstract class AbstractActivityWithActionBarMenu extends AppCompatActivity {

	protected DrawerLayout drawer = null;
	protected ActionBarDrawerToggle toggle = null;

	protected NavigationView navigation_view = null;
	protected View navigation_view_header = null;
	protected DrawerItemMenu[] items_menu_list = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abstract_activity_with_action_bar_menu);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawer = findViewById(R.id.drawer_layout);

		toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		navigation_view = findViewById(R.id.navigation_view);

		if( this.getDrawerMenuHeaderView() != null ){

			navigation_view_header = navigation_view.inflateHeaderView(this.getDrawerMenuHeaderView());

//			navigation_view.findViewById(R.id.nav_header_image_view);

//			((ImageView) findViewById(R.id.nav_header_image_view)).setImageResource(R.drawable.ic_launcher);
//			((TextView) findViewById(R.id.nav_header_title)).setText(R.string.app_name);
//			((TextView) findViewById(R.id.nav_header_subtitle)).setText(R.string.app_name);
		}

		navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item_menu ){

				Utils.logger("item_menu.getItemId() = "+ String.valueOf(item_menu.getItemId()), Utils.LOG_DEBUG );

				DrawerItemMenu item = items_menu_list[ item_menu.getItemId() ];

				drawer.closeDrawer(GravityCompat.START);

				if( item.getClassOfActivity() != null ){
					(AbstractActivityWithActionBarMenu.this).startActivity( new Intent( AbstractActivityWithActionBarMenu.this, item.getClassOfActivity()) );
				}

				item.onItemClicked();

				return false;
			}
		});

		populateDrawerMenu();

		if( this.getContentActivity() == null ){
			throw new NullPointerException("Value returned by getContentActivity() method is Null. Fix it! :-)");
		}

		//	This is real main content of activity
		ViewStub stub = findViewById(R.id.activity_content);
		stub.setLayoutResource(this.getContentActivity());
		stub.inflate();
	}

	/**
	 * Populate the drawer menu with items menu
	 */
	protected void populateDrawerMenu(){

		Menu menu = navigation_view.getMenu();

		int menu_list_size = items_menu_list.length;
		for(int item_id = 0; item_id < menu_list_size; item_id++) {
			DrawerItemMenu item_menu = items_menu_list[item_id];

			menu.add( Menu.NONE, item_id, Menu.NONE, item_menu.getTitle() );

			if( item_menu.getImage() != null ){
				menu.findItem(item_id).setIcon( item_menu.getImage() );
			}
		}
	}

	/**
	 * Return the resource of layout to use for main content of your activity :)
	 * @return	int
	 * Example: R.layout.your_activity_content
	 */
	protected abstract Integer getContentActivity();

	/**
	 * Return the resource of layout to use for main content in your activity :)
	 * @return	int
	 * Default is R.layout.drawer_menu_default_header
	 */
	protected Integer getDrawerMenuHeaderView(){
		return R.layout.drawer_menu_default_header;
	}

	/**
	 * Return the resource of layout to use for action bar menu in your activity :)
	 * Remember to define the "onOptionsItemSelected" callback to handle action to bar item
	 * @return	int
	 * Example: R.menu.abstract_activity_with_action_bar_menu
	 */
	protected Integer getActionBarMenuResource(){
		return null;
	}


	/**
	 * Return the navigation view in the drawer
	 * @return NavigationView
	 */
	public NavigationView getNavigationView(){
		return this.navigation_view;
	}

	/**
	 * Return the view inflated into the header, null otherwise
	 * @return View
	 */
	public View getNavigationHeaderView(){
		return this.navigation_view_header;
	}





	@Override
	public void onBackPressed(){
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if( drawer.isDrawerOpen(GravityCompat.START) ){
			drawer.closeDrawer(GravityCompat.START);
		}else{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ){

		Integer menu_resource = getActionBarMenuResource();

		if( menu_resource == null ){
			return super.onCreateOptionsMenu(menu);
		}

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(menu_resource, menu);

		return true;
	}

/*
	Must be defined
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will automatically handle clicks
		// on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
		int item_id = item.getItemId();

		if( item_id == R.id.action_settings ){
			//	Do something...
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
*/

}
