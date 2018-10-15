package it.lucichkevin.cip.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import it.lucichkevin.cip.R;


public abstract class ObjectAdapter<T> extends ArrayAdapter<T> {

	public final static int ITEM_LAYOUT_SIMPLE = android.R.layout.simple_list_item_1;
	public final static int ITEM_LAYOUT_WITH_IMAGE = R.layout.adapter_item_with_image;
	public final static int DEFAULT_RESOURCE_LAYOUT = ITEM_LAYOUT_SIMPLE;

	protected int layout_id;
	protected ArrayList<T> items_list;
	protected LayoutInflater inflater;

	protected Context context = null;


	public ObjectAdapter( Activity context, ArrayList<T> items_list ){
		this(context, DEFAULT_RESOURCE_LAYOUT, items_list);
	}

	public ObjectAdapter( Activity context, int layout_id, ArrayList<T> items_list ){
		super(context, layout_id, items_list);

		this.items_list = items_list;
		this.context = context;
		this.layout_id = layout_id;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@NonNull
	public View getView( int position, View convertView, @NonNull ViewGroup parent ){
		View item_view = this.inflater.inflate( this.layout_id, parent,false );
		attachItemToLayout( items_list.get(position), position, item_view );
		return item_view;
	}

	public View getDropDownView( int position, View convertView, @NonNull ViewGroup parent ){
		return getView(position, convertView, parent);
	}


	//////////////////////////////////////////
	//  Abstract

	protected abstract void attachItemToLayout(T T, int position, View view);

}


