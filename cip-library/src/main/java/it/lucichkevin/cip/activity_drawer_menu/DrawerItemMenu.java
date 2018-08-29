package it.lucichkevin.cip.activity_drawer_menu;

import android.view.View;


/**
 * @author	Kevin Lucich
 * @version	1.1.0 (2018-08-06)
 * @since	CipLibrary v1.0.0
*/
public class DrawerItemMenu {

    private Integer title;      //  Resource
    private Integer image;      //  Resource
    private Class classOfActivity;
    private OnClickListener onClickListener = null;

    public DrawerItemMenu( Integer title, Class classOfActivity ){
        this( title, null, classOfActivity, null );
    }

    public DrawerItemMenu( Integer title, OnClickListener onClickListener ){
        this( title, null, null, onClickListener );
    }

    public DrawerItemMenu( Integer title, Class classOfActivity, OnClickListener onClickListener ){
        this( title, null, classOfActivity, onClickListener );
    }

    public DrawerItemMenu( Integer title, Integer image, Class classOfActivity ){
        this( title, image, classOfActivity, null );
    }

	public DrawerItemMenu( Integer title, Integer image, OnClickListener onClickListener ){
		this( title, image, null, onClickListener );
	}

    public DrawerItemMenu(Integer title, Integer image, Class classOfActivity, OnClickListener onClickListener ){
        this.title = title;
        this.image = image;
        this.classOfActivity = classOfActivity;
        this.onClickListener = onClickListener;
    }


    /////////////////////////////////////////
    //  Helper for the ItemDrawer

	public void onItemClicked(){
		if( this.getOnClickListener() != null ){
			this.getOnClickListener().onClick();
		}else{
//			Snackbar.make( this, "[Cip.DrawerMenu.onItemClicked] "+ getTitleString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
		}
	}

//	public boolean onItemLongClicked( View view ){
//
//		if( this.getOnClickListener() != null ){
//			return this.getOnClickListener().onLongClick(view);
//		}else{
////			Utils.logger( "[Cip.DrawerMenu.onItemClicked] "+ getTitleString(), Utils.LOG_DEBUG );
//		}
//
//		return false;
//	}

	//  Quando "qualcosa" succede e deve essere modificato lo stato dell'item
	public void onStatusChanged(){
		//	Do nothing...
	}

    /////////////////////////////////////////
    //  Getters and setters

    public Integer getTitle() {
        return title;
    }
    public void setTitle( Integer title ){
        this.title = title;
    }
	public String getTitleString() {
//		return Utils.getContext().getResources().getString(getTitle());
		return "";
	}

    public Integer getImage() {
        return image;
    }
    public void setImage( Integer image ){
        this.image = image;
    }

    public Class getClassOfActivity() {
        return classOfActivity;
    }
    public void setClassOfActivity( Class classOfActivity ){
        this.classOfActivity = classOfActivity;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }
    public void setOnClickListener( OnClickListener onClickListener ){
        this.onClickListener = onClickListener;
	}



    /////////////////////////////////////////
    //  Callback

    public abstract static class OnClickListener {
        public abstract void onClick();
		public boolean onLongClick( View view ){
			return false;
		}
	}

}