package org.ajcm.bibliarv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.emilsjolander.components.StickyListHeaders.StickyListHeadersBaseAdapter;
import java.util.LinkedList;

/**
 *
 * @author Jhon_Li
 */
public class LibroAdapter extends StickyListHeadersBaseAdapter {

    private LinkedList<String> ll;
    private LayoutInflater li;

    public LibroAdapter(Context context, int textViewResourceId, LinkedList<String> objects) {
        super(context);
        ll = objects;
        li = LayoutInflater.from(context);
    }

    @Override
    public View getHeaderView(int position, View convertView) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = li.inflate(R.layout.header, null);
            holder.text = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        if(position < 39){
            holder.text.setText("Antiguo Testamento");
        } else{
            holder.text.setText("Nuevo Testamento");
        }
        return convertView;
    }

    class HeaderViewHolder {

        TextView text;
    }

    @Override
    public long getHeaderId(int position) {
        if(position < 39){
            return 0;
        } else{
            return 1;
        }
    }

    @Override
    protected View getView(int position, View convertView) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = li.inflate(R.layout.item_libro, null);
            vh.text = (TextView) convertView.findViewById(R.id.libro);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.text.setText(ll.get(position));
        return convertView;
    }

    class ViewHolder {

        TextView text;
    }

    public int getCount() {
        return ll.size();
    }

    public Object getItem(int position) {
        return ll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
}
