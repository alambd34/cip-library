package it.lucichkevin.cip.navigationdrawermenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import it.lucichkevin.cip.ObjectAdapter;
import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;

/**
 *  Create a drawer menu using a drawer layout (DRAWER_LAYOUT_ID) and populating the DRAWER_LIST_VIEW_ID with list of items (array of ItemDrawerMenu object) and attaching a callback to menu
 *
 *  @author  Kevin Lucich
 *  @author  Marco Zanetti (fixes and new methods)
 *  @version 1.1.0
*/
public class DrawerLayoutHelper {

    public static final int RESOURCE_ID_MAIN_CONTAINER = R.id.drawer_layout;
    public static final int RESOURCE_ID_DRAWER_LIST_VIEW = R.id.drawer_list_view;

    private Activity activity;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
//    private int defaultNavigationMode;
    private CharSequence defaultTitle;


//    private ItemDrawerMenu[] ARRAY_ITEMS = new ItemDrawerMenu[]{
//
//        //  Duration in hours and minutes
//        new ItemDrawerMenu( R.string.prefs_choose_workday_length, new ItemDrawerMenu.OnClickListener() {
//            @Override
//            public void onClick() {
//                //    Do something....
//            }
//        }),
//
//        //  About WorkItOut
//        new ItemDrawerMenu( R.string.prefs_about, AboutActivity.class ),
//
//    };

    /**
     * Create a empty drawer menu, will be attachd a empty callbacks
     *
     * @param   activity    The activity where including the drawer menu
     * @since   CipLibrary v0.4.0
     * @see     EmptyCallbacks
     */
    public DrawerLayoutHelper( final Activity activity ){
        this( activity, RESOURCE_ID_MAIN_CONTAINER, RESOURCE_ID_DRAWER_LIST_VIEW, new ItemDrawerMenu[]{}, new DrawerLogCallbacks() );
    }

    /**
     * Create drawer menu with a list of items of menu
     *
     * @param   activity            The activity where including the drawer menu
     * @param   ARRAY_ITEMS_LIST    Array of items of menu
     * @since   CipLibrary v0.4.0
     * @see     ItemDrawerMenu
     * @see     EmptyCallbacks
     */
    public DrawerLayoutHelper( final Activity activity, final ItemDrawerMenu[] ARRAY_ITEMS_LIST ){
        this( activity, RESOURCE_ID_MAIN_CONTAINER, RESOURCE_ID_DRAWER_LIST_VIEW, ARRAY_ITEMS_LIST, new DrawerLogCallbacks() );
    }

    /**
     * Create a drawer menu with list of items (ARRAY_ITEMS_LIST) and attaching a callback to menu
     *
     * @param   activity            The activity where including the drawer menu
     * @param   ARRAY_ITEMS_LIST    Array of items of menu
     * @param   callbacks           Implementation of callbacks when an action performed to drawer menu
     * @since   CipLibrary v0.4.0
     * @see     ItemDrawerMenu
     * @see     Callbacks
     */
    public DrawerLayoutHelper( final Activity activity, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final Callbacks callbacks ){
        this( activity, RESOURCE_ID_MAIN_CONTAINER, RESOURCE_ID_DRAWER_LIST_VIEW, ARRAY_ITEMS_LIST, callbacks );
    }

    /**
     * Create a drawer menu, using a drawer layout (DRAWER_LAYOUT_ID) and populating the DRAWER_LIST_VIEW_ID with list of items (ARRAY_ITEMS_LIST)
     *
     * @param   activity    The activity where including the drawer menu
     * @since   CipLibrary v0.4.0
     * @see     EmptyCallbacks
     * @see     #DrawerLayoutHelper(android.app.Activity, int, int, ItemDrawerMenu[], it.lucichkevin.cip.navigationdrawermenu.DrawerLayoutHelper.Callbacks)
     */
    public DrawerLayoutHelper( final Activity activity, int DRAWER_LAYOUT_ID, int DRAWER_LIST_VIEW_ID, final ItemDrawerMenu[] ARRAY_ITEMS_LIST ){
        this( activity, DRAWER_LAYOUT_ID, DRAWER_LIST_VIEW_ID, ARRAY_ITEMS_LIST, new DrawerLogCallbacks() );
    }

    /**
     * Create a drawer menu, using a drawer layout (DRAWER_LAYOUT_ID) and populating the DRAWER_LIST_VIEW_ID with list of items (ARRAY_ITEMS_LIST) and attaching a callback to menu
     *
     * @param   activity    The activity where including the drawer menu
     * @since   CipLibrary v0.4.0
     */
    public DrawerLayoutHelper( final Activity activity, int DRAWER_LAYOUT_ID, int DRAWER_LIST_VIEW_ID, final ItemDrawerMenu[] ARRAY_ITEMS_LIST, final Callbacks callbacks ){

        setActivity(activity);
        final ActionBar actionBar = activity.getActionBar();

        if( actionBar == null ){
            Log.e("Cip-Library DrawerLayoutHelper", "ERROR: ActionBar is empty!");
            return;
        }

        defaultTitle = actionBar.getTitle();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if( Build.VERSION.SDK_INT >= 14 ){
            //  API min Level 14
            actionBar.setHomeButtonEnabled(true);
        }

        drawerLayout = (DrawerLayout) getActivity().findViewById(DRAWER_LAYOUT_ID);
        if( drawerLayout == null ){
            Log.e("Cip-Library DrawerLayoutHelper", "ERROR: Resource for drawer_layout not found!");
            return;
        }

        drawerListView = (ListView) getActivity().findViewById(DRAWER_LIST_VIEW_ID);
        if( drawerListView == null ){
            Log.e("Cip-Library DrawerLayoutHelper", "ERROR: Resource for drawer listView not found!");
            return;
        }

        ObjectAdapter<ItemDrawerMenu> adapter = new ObjectAdapter<ItemDrawerMenu>( getActivity(), R.layout.drawerlayouthelper_itemmenu, ARRAY_ITEMS_LIST ){
            @Override
            protected void attachItemToLayout( ItemDrawerMenu item, int position ){

                ((TextView) findViewById(R.id.item_menu_name)).setText(item.getTitle());

                if( item.getImage() != null ) {
                    ImageView imageView = (ImageView) findViewById(R.id.item_menu_image);
                    imageView.setImageResource(item.getImage());
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        };
        drawerListView.setAdapter(adapter);

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
                    getActivity().startActivity( new Intent(getActivity(), itemSelected.getClassOfActivity()) );
                }else if( itemSelected.getOnClickListener() != null ){
                    itemSelected.getOnClickListener().onClick();
                }else{
                    Utils.Toaster( getActivity(), "position = "+ position +"\n TITLE: "+ itemSelected.getTitle() );
                }

            }
        });

        drawerToggle = new ActionBarDrawerToggle( getActivity(), drawerLayout, R.drawable.ic_navigation_drawer, R.string.open, R.string.close ){

            public void onDrawerClosed( View drawerView ){
                actionBar.setCustomView(null);
                actionBar.setTitle(defaultTitle);
                callbacks.onDrawerClose(getActivity(), drawerView);
            }

            public void onDrawerOpened(View drawerView) {
                callbacks.onDrawerOpen(getActivity(), drawerView);
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


    /////////////////////////////////////////
    //  Getters and setters

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

    /**
     * @since   CipLibrary v0.2.0
     */
    public interface Callbacks{
        public void onDrawerOpen( Activity activity, View drawerView );
        public void onDrawerClose( Activity activity, View drawerView );
    }

    /**
     * @since   CipLibrary v0.4.2
     */
    private static class DrawerLogCallbacks implements Callbacks{
        @Override
        public void onDrawerOpen(Activity activity, View drawerView) {
            Utils.logger("CipLibrary.DrawerLayoutHelper", "Drawer opened!", Utils.LOG_INFO );
        }
        @Override
        public void onDrawerClose(Activity activity, View drawerView) {
            Utils.logger("CipLibrary.DrawerLayoutHelper", "Drawer closed!", Utils.LOG_INFO );
        }
    }

    public static class EmptyCallbacks implements Callbacks{
        public void onDrawerOpen( Activity activity, View drawerView ){}
        public void onDrawerClose( Activity activity, View drawerView ){}
    }

}

