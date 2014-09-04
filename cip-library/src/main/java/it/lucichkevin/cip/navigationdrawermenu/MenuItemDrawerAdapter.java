package it.lucichkevin.cip.navigationdrawermenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.lucichkevin.cip.R;


/**
 * Created by kevin on 30/08/2014.
 */
public class MenuItemDrawerAdapter extends ArrayAdapter<ItemDrawerMenu> {

    public MenuItemDrawerAdapter( Context context, int textViewResourceId, ItemDrawerMenu[] items ) {
        super( context, textViewResourceId, items );
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ){

        ViewHolder viewHolder = null;

        if( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate( R.layout.drawer_menu, null );

            viewHolder = new ViewHolder();
            viewHolder.item_title = (TextView) convertView.findViewById(R.id.item_menu_name);
            viewHolder.item_image = (ImageView) convertView.findViewById(R.id.item_menu_image);

            convertView.setTag( viewHolder );

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemDrawerMenu item = getItem(position);
        viewHolder.item_title.setText(item.getTitle());

        Integer image_resource = item.getImage();
        if( image_resource != null ) {
            viewHolder.item_image.setImageResource(image_resource);
            viewHolder.item_image.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class ViewHolder {
        public TextView item_title;
        public ImageView item_image;
    }

}
