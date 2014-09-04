package it.lucichkevin.cip.navigationdrawermenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;


public class DrawerLayoutHelper {

    private Activity activity;

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
//    private int defaultActionBarDisplay;
    private int defaultNavigationMode;
    private CharSequence defaultTitle;

//    private ItemDrawerMenu[] ARRAY_ITEMS = new ItemDrawerMenu[]{
//
//        //  Duration in hours and minutes
//        new ItemDrawerMenu( R.string.prefs_choose_workday_length, new ItemDrawerMenu.OnClickListener() {
//            @Override
//            public void onClick() {
//                final CustomTimeDialog dialog = new CustomTimeDialog(DrawerLayoutHelper.this.getActivity());
//                dialog.setContentView(R.layout.time_preference);
//                dialog.show();
//            }
//        }),
//
//        //
//        new ItemDrawerMenu( R.string.prefs_delete_timings, new ItemDrawerMenu.OnClickListener() {
//            @Override
//            public void onClick() {
//                ((WorkItOutMain) DrawerLayoutHelper.this.getActivity()).clearAllInput();
//            }
//        }),
//
//        //
//        new ItemDrawerMenu( R.string.send_email, new ItemDrawerMenu.OnClickListener() {
//            @Override
//            public void onClick() {
//                ((WorkItOutMain) DrawerLayoutHelper.this.getActivity()).sendMail();
//            }
//        }),
//
//        //  About WorkItOut
//        new ItemDrawerMenu( R.string.prefs_about, AboutActivity.class ),
//
//    };
/*
    public DrawerLayoutHelper( final Activity activity, final ItemDrawerMenu[] ARRAY_ITEMS_LIST ){
        this( activity, activity.getActionBar(), R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS_LIST, null );
    }
    public DrawerLayoutHelper( final Activity activity, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final DrawerCallbacks drawerCallbacks ){
        this( activity, activity.getActionBar(), R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS_LIST, drawerCallbacks );
    }
    public DrawerLayoutHelper( final Activity activity, ActionBar actionBar, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final DrawerCallbacks drawerCallbacks ){
        this( activity, actionBar, R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS_LIST, drawerCallbacks );
    }
    public DrawerLayoutHelper( final Activity activity, ActionBar actionBar, final ItemDrawerMenu[] ARRAY_ITEMS_LIST ){
        this( activity, actionBar, R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS_LIST, null );
    }
*/
//    public DrawerLayoutHelper( final Activity activity, final ItemDrawerMenu[] ARRAY_ITEMS_LIST ){
//        this( activity, R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS_LIST, null );
//    }
//    public DrawerLayoutHelper( final Activity activity, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final DrawerCallbacks drawerCallbacks ){
//        this( activity, R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS_LIST, drawerCallbacks );
//    }
    public DrawerLayoutHelper( final Activity activity, int DRAWER_LAYOUT_ID, int DRAWER_LIST_VIEW_ID, final ItemDrawerMenu[] ARRAY_ITEMS_LIST ){
        this( activity, DRAWER_LAYOUT_ID, DRAWER_LIST_VIEW_ID, ARRAY_ITEMS_LIST, null );
    }

    //  Delete the support.v7 (permette di chiamare .setHomeButtonEnabled() )
//    public DrawerLayoutHelper( final Activity activity, ActionBar actionBar, int DRAWER_LAYOUT_ID, int DRAWER_LIST_VIEW_ID, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final DrawerCallbacks drawerCallbacks ){
    public DrawerLayoutHelper( final Activity activity, int DRAWER_LAYOUT_ID, int DRAWER_LIST_VIEW_ID, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final DrawerCallbacks drawerCallbacks ){

        setActivity(activity);
        this.actionBar = activity.getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);     //  API min Level 14

        //  Dati di default dell'action bar
        defaultNavigationMode = actionBar.getNavigationMode();
        defaultTitle = actionBar.getTitle();

//        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.main_layout);
        drawerLayout = (DrawerLayout) getActivity().findViewById(DRAWER_LAYOUT_ID);
//        drawerListView = (ListView) activity.findViewById(R.id.left_drawer);
        drawerListView = (ListView) getActivity().findViewById(DRAWER_LIST_VIEW_ID);

//        drawerListView.setAdapter(adapter);
        MenuItemDrawerAdapter mAdapter = new MenuItemDrawerAdapter( getActivity(), R.layout.navigationdrawermenu, ARRAY_ITEMS_LIST );
        drawerListView.setAdapter( mAdapter );

        // Set the list's click listener
//        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Closing the drawer
                close();
                drawerListView.setItemChecked(position, false);

                ItemDrawerMenu itemSelected = ARRAY_ITEMS_LIST[ position ];

                if( itemSelected.getClassOfActivity() != null ){
                    getActivity().startActivity(new Intent(getActivity(), itemSelected.getClassOfActivity()));
                }else if( itemSelected.getOnClickListener() != null ){
                    itemSelected.getOnClickListener().onClick();
                }else{
                    Utils.Toaster( getActivity(), "position = "+ position +"\n TITLE: "+ itemSelected.getTitle() );
                }

            }
        });

        drawerToggle = new ActionBarDrawerToggle( getActivity(), drawerLayout, R.drawable.ic_navigation_drawer, R.string.open, R.string.close ){

            public void onDrawerClosed( View drawerView ){
                restoreActionBar();
                if( drawerCallbacks != null ){
                    drawerCallbacks.onDrawerClose(drawerView);
                }
            }

            public void onDrawerOpened(View drawerView) {
                if( drawerCallbacks != null ) {
                    drawerCallbacks.onDrawerOpen(drawerView);
                }
//                actionBar.setTitle(activity.getString(R.string.title_activity_settings));
//                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }

    public void open(){
        drawerLayout.openDrawer(getDrawerListView());
    }
    public void close(){
        drawerLayout.closeDrawer(getDrawerListView());
    }
    public void toggle() {
        if( drawerLayout.isDrawerOpen(drawerListView) ){
            close();
        }else{
            open();
        }
    }

    private void restoreActionBar() {
        actionBar.setCustomView(null);
//        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(defaultNavigationMode);
        actionBar.setTitle(defaultTitle);
    }


    //  Getter and setters

    public Activity getActivity() {
        return activity;
    }
    public void setActivity( Activity activity ){
        this.activity = activity;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public ListView getDrawerListView() {
        return drawerListView;
    }
    public void setDrawerListView(ListView drawerListView) {
        this.drawerListView = drawerListView;
    }



    /////////////////////////////////////////
    //  Callbacks

    public interface DrawerCallbacks{
        public void onDrawerOpen(View drawerView);
        public void onDrawerClose(View drawerView);
    }
}

