package it.lucichkevin.cip;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
    Adapter for T type object

    <code>
    ObjectAdapter<MyObject> adapter = new ObjectAdapter<MyObject>(){
        #@Override
        protected void attachItemToLayout( MyObject item, int position ){
            //  Do something...
        }
    };
    </code>

    @author     Kevin Lucich    14/05/2014
    @version	0.0.2
    @since      Cip version 0.0.1
*/
public abstract class ObjectAdapter<T> extends ArrayAdapter<T> {

    protected View convertView = null;
    protected Context context = null;

	protected T[] items = null;
    protected int layout;
    protected SparseArray<View> viewHolder = null;

    public ObjectAdapter( Context context, T[] items ){
        this(context, android.R.layout.simple_list_item_1, items );
    }

//    public ObjectAdapter( Context context, int layout, List<T> objectsList ){
//        //  Cast to Array
//        this( context, layout, objectsList.toArray( T[objectsList.size()] ));
//    }

    public ObjectAdapter( Context context, int layout, T[] items ){
        super( context, layout, items );
        this.layout = layout;
        this.items = items;
        this.context = context;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ){

        setConvertView(convertView);

        //  The developer has the task of implement the logic of this method
        attachItemToLayout( getItem(position), position );

        return getConvertView();
    }


    public T[] getItems(){
        return this.items;
    }

    /**
     *	Attach the info of item to the layout, inside use getViewById method to get the view where attach the info.	<br /><br />
     *  
     *	<i>CheckedTextView checkedTextView = (CheckedTextView) getViewById((R.id.choice_item_name);</i>    <br /><br />
     *	
     *	Called for each item into array items
     *	@param  item    	The item of type <T>
     *	@param  position   The position of item into array
    */
    protected abstract void attachItemToLayout( T item, int position );


	/**
	 *	Return the View searched
	 *	@param  resource_id	Resource_id to search in view
	 *
	 */
	protected View findViewById( int resource_id ){

        viewHolder = (SparseArray<View>) getConvertView().getTag();

        View view = viewHolder.get(resource_id);
        if( view == null ){
        	view = getConvertView().findViewById(resource_id);
        	viewHolder.put( resource_id, view );
        	getConvertView().setTag( viewHolder );
        }

        return view;
	}



    //////////////////////////////////////////
    //  Getter and Setters

    protected View getConvertView(){

        if( this.convertView == null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            setConvertView( inflater.inflate( layout, null ) );
            (this.convertView).setTag( new SparseArray<View>() );
        }

        return this.convertView;
    }

    protected void setConvertView( View convertView ){
        this.convertView = convertView;
    }

}