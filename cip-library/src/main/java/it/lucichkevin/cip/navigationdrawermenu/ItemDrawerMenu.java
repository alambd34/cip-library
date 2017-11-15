package it.lucichkevin.cip.navigationdrawermenu;

import android.view.View;
import it.lucichkevin.cip.Utils;

/**
    @author     Kevin Lucich
    @version	1.1.0
 	@updated	2015-03-24
    @since      CipLibrary v0.2.0
*/
public class ItemDrawerMenu {

    private Integer title;      //  Resource
    private Integer image;      //  Resource
    private Class classOfActivity;
    private OnClickListener onClickListener = null;

    public ItemDrawerMenu( Integer title, Class classOfActivity ){
        this( title, null, classOfActivity, null );
    }

    public ItemDrawerMenu( Integer title, OnClickListener onClickListener ){
        this( title, null, null, onClickListener );
    }

    public ItemDrawerMenu( Integer title, Class classOfActivity, OnClickListener onClickListener ){
        this( title, null, classOfActivity, onClickListener );
    }

    public ItemDrawerMenu( Integer title, Integer image, Class classOfActivity ){
        this( title, image, classOfActivity, null );
    }

    public ItemDrawerMenu( Integer title, Integer image, Class classOfActivity, OnClickListener onClickListener ){
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
			Utils.logger( "[Cip.DrawerMenu.onItemClicked] "+ getTitleString(), Utils.LOG_DEBUG );
		}
	}

	public boolean onItemLongClicked( View view ){

		if( this.getOnClickListener() != null ){
			return this.getOnClickListener().onLongClick(view);
		}else{
			Utils.logger( "[Cip.DrawerMenu.onItemClicked] "+ getTitleString(), Utils.LOG_DEBUG );
		}

		return false;
	}

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
		return Utils.getContext().getResources().getString(getTitle());
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

	public static interface OnClickListenerInterface {
		public void onClick();
		public boolean onLongClick(View view);
	}

    public abstract static class OnClickListener implements OnClickListenerInterface {
        public void onClick(){}
		public boolean onLongClick( View view ){
			return false;
		}
	}

}