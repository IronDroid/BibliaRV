package org.ajcm.bibliarv.libros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.LinkedList;
import org.ajcm.bibliarv.R;

/**
 *
 * @author Jhon_Li
 */
public class CapituloAdapter extends ArrayAdapter<Versiculo> {

    private LinkedList<Versiculo> ll;
    private LayoutInflater li;

    public CapituloAdapter(Context context, int textViewResourceId, LinkedList<Versiculo> objects) {
        super(context, textViewResourceId, objects);
        ll = objects;
        li = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h;
        if (convertView == null) {
            h = new Holder();
            convertView = li.inflate(R.layout.item_capitulo, null);
            h.estrella = (ImageView) convertView.findViewById(R.id.star);
            h.texto = (TextView) convertView.findViewById(R.id.versiculo);
            convertView.setTag(h);
        } else {
            h = (Holder) convertView.getTag();
        }
        h.texto.setText(ll.get(position).getTexto().toString());
        if (ll.get(position).getFavorito() == 1) {
            h.estrella.setImageResource(R.drawable.btn_star_big_on);
        } else {
            h.estrella.setImageResource(R.drawable.btn_star_big_off_disable);
        }
        return convertView;
    }

    class Holder {

        TextView texto;
        ImageView estrella;
    }
}
