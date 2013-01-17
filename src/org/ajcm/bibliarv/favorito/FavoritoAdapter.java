package org.ajcm.bibliarv.favorito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;
import org.ajcm.bibliarv.R;

/**
 *
 * @author Jhon_Li
 */
public class FavoritoAdapter extends ArrayAdapter<Favorito> {

    private LinkedList<Favorito> llf;
    private LayoutInflater li;

    public FavoritoAdapter(Context context, int textViewResourceId, List<Favorito> objects) {
        super(context, textViewResourceId, objects);
        llf = (LinkedList<Favorito>) objects;
        li = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = li.inflate(R.layout.item_favorito, null);
            holder = new Holder();
            holder.referencia = (TextView) convertView.findViewById(R.id.referencia);
            holder.favorito = (TextView) convertView.findViewById(R.id.favorito);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.referencia.setText(llf.get(position).getReferencia());
        holder.favorito.setText(llf.get(position).getTexto());
        return convertView;
    }

    class Holder {

        TextView referencia;
        TextView favorito;
    }
}
