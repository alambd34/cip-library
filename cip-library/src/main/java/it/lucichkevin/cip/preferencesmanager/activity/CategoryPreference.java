package it.lucichkevin.cip.preferencesmanager.activity;

import java.util.ArrayList;


public class CategoryPreference {

	protected String name;
	protected ArrayList<ItemPreference> items = new ArrayList<ItemPreference>();


	public CategoryPreference( String name ){
		this.name = name;
	}
	public CategoryPreference( String name, ArrayList<ItemPreference> items ){
		this.name = name;
		this.items = items;
	}

	public String getName(){
		return name;
	}

	public ArrayList<ItemPreference> getItems(){
		return items;
	}

	public void addItem( ItemPreference item ){
		items.add(item);
	}

}
