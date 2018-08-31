package it.lucichkevin.cip.preferences;

import java.util.ArrayList;



public class CategoryPreference {

	protected String name;
	protected ArrayList<Preference> items = new ArrayList<Preference>();


	public CategoryPreference( String name ){
		this.name = name;
	}
	public CategoryPreference( String name, ArrayList<Preference> items ){
		this.name = name;
		this.items = items;
	}



	public void addItem( Preference item ){
		items.add(item);
	}


	/////////////////////////////////////////
	//  Getters and setters


	public String getName(){
		return name;
	}
	public void setName( String name ){
		this.name = name;
	}

	public ArrayList<Preference> getItems(){
		return items;
	}
	public void setItems( ArrayList<Preference> items ){
		this.items = items;
	}

}
