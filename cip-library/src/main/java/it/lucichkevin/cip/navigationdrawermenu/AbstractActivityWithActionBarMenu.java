package it.lucichkevin.cip.navigationdrawermenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;

abstract public class AbstractActivityWithActionBarMenu extends AppCompatActivity {

	public _DrawerLayoutHandling DrawerLayoutHandling = null;
	public _NavigationViewHandling NavigationViewHandling = null;

	@Override
	protected void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_with_action_bar_menu);

		this.DrawerLayoutHandling = new _DrawerLayoutHandling(this);
		this.NavigationViewHandling = new _NavigationViewHandling(this);

		//	This is real main content of activity
		ViewStub stub = (ViewStub) findViewById(R.id.activity_content);
		stub.setLayoutResource(getContentActivity());
		stub.inflate();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

//		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//		fab.setOnClickListener(new View.OnClickListener(){
//			@Override
//			public void onClick(View view ){
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//			}
//		});

		DrawerLayout drawer = DrawerLayoutHandling.getDrawerLayout();
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

	}


	//////////////////////////////////////////////////////////////
	//

	protected class _DrawerLayoutHandling {

		DrawerLayout drawer = null;

		public _DrawerLayoutHandling(Activity activity ){
			this.drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
		}

		public DrawerLayout getDrawerLayout(){
			return drawer;
		}

		/**
		 * Check if the given drawer view is currently in an open state.
		 *	@return true if the given drawer view is in an open state
		 *	@see android.support.v4.widget.DrawerLayout#isDrawerOpen
		 */
		public boolean isDrawerOpen(){
			return getDrawerLayout().isDrawerOpen(GravityCompat.START);
		}

		/**
		 *	Open the drawer by animating it out of view.
		 *	@see android.support.v4.widget.DrawerLayout#openDrawer
		 */
		public _DrawerLayoutHandling openDrawer(){
			getDrawerLayout().openDrawer(GravityCompat.START);
			return this;
		}

		/**
		 *	Close the specified drawer by animating it out of view.
		 *	@see android.support.v4.widget.DrawerLayout#closeDrawer
		 */
		public _DrawerLayoutHandling closeDrawer(){
			getDrawerLayout().closeDrawer(GravityCompat.START);
			return this;
		}
	}


	//////////////////////
	//

	protected class _NavigationViewHandling {

		NavigationView navigationView = null;

		public _NavigationViewHandling(Activity activity ){
			this.navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
		}

		public NavigationView getNavigationView() {
			return this.navigationView;
		}

		public _NavigationViewHandling inflateMenu( int menu_to_inflate ){
			getNavigationView().inflateMenu(menu_to_inflate);
			return this;
		}

		public _NavigationViewHandling inflateHeaderView( int header_to_inflate ){
			getNavigationView().inflateHeaderView(header_to_inflate);
			return this;
		}

		public _NavigationViewHandling setNavigationItemSelectedListener( NavigationView.OnNavigationItemSelectedListener listener ){
			getNavigationView().setNavigationItemSelectedListener(listener);
			return this;
		}

	}

	@Override
	public void onBackPressed(){
		if( DrawerLayoutHandling.isDrawerOpen() ){
			DrawerLayoutHandling.closeDrawer();
		}else{
			super.onBackPressed();
		}
	}


	/**
	 *	Return the resource of layout to use for main content of your activity :)
	 *	@return	int	R.layout.home_with_hello_world
	 */
	protected abstract int getContentActivity();

}