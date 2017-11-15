package it.lucichkevin.cip.navigationdrawermenu;

/**
	@author		Kevin Lucich
	@version	1.0.0
 	@updated	2017-02-12
	@since		CipLibrary v0.8.0
*/
public class NavigationItemMenu {

	private Integer title;	  //  Resource
	private Integer icon;	  //  Resource
	private Class classOfActivity;
	private OnClickListener onClickListener = null;

	public NavigationItemMenu(Integer title, Class classOfActivity ){
		this( title, null, classOfActivity, null );
	}

	public NavigationItemMenu(Integer title, OnClickListener onClickListener ){
		this( title, null, null, onClickListener );
	}

	public NavigationItemMenu(Integer title, Class classOfActivity, OnClickListener onClickListener ){
		this( title, null, classOfActivity, onClickListener );
	}

	public NavigationItemMenu(Integer title, Integer icon, Class classOfActivity ){
		this( title, icon, classOfActivity, null );
	}

	public NavigationItemMenu(Integer title, Integer icon, OnClickListener onClickListener ){
		this( title, icon, null, onClickListener );
	}

	public NavigationItemMenu(Integer title, Integer icon, Class classOfActivity, OnClickListener onClickListener ){
		this.title = title;
		this.icon = icon;
		this.classOfActivity = classOfActivity;
		this.onClickListener = onClickListener;
	}


	/////////////////////////////////////////
	//  Helper for the ItemDrawer

	public void onItemClicked(){
		if( this.getOnClickListener() != null ){
			this.getOnClickListener().onClick();
		}else{
//			Utils.logger( "[Cip.NavigationMenu.onItemClicked] "+ getTitleString(), Utils.LOG_DEBUG );
		}
	}

	//  Quando "qualcosa" succede e deve essere modificato lo stato dell'item
	public void onStatusChanged(){
		//	Do nothing...
	}

	protected String getTitleString() {
		return "";
//		return Utils.getContext().getResources().getString(getTitle());
	}

	/////////////////////////////////////////
	//  Getters and setters

	public Integer getTitle() {
		return title;
	}
	public void setTitle( Integer title ){
		this.title = title;
	}

	public Integer getIcon() {
		return icon;
	}
	public void setIcon( Integer icon ){
		this.icon = icon;
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

	public static interface OnClickListener {
		public void onClick();
	}

}