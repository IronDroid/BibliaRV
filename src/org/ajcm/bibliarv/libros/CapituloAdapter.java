package org.ajcm.bibliarv.libros;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = li.inflate(R.layout.item_capitulo, null);
//        }
//        LinearLayout label = (LinearLayout) convertView.findViewById(R.id.label);
//        TextView texto = (TextView) convertView.findViewById(R.id.versiculo);
//        Toast.makeText(getContext(), position + " - " + ll.get(position).getFavorito(), Toast.LENGTH_SHORT).show();
//        texto.setText(ll.get(position).getTexto().toString());
//        if (ll.get(position).getFavorito() == 1) {
//            label.setBackgroundColor(Color.rgb(51, 181, 229));
//        } else {
//            label.setBackgroundColor(Color.TRANSPARENT);
//        }
//        return convertView;
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h;
        if (convertView == null) {
            h = new Holder();
            convertView = li.inflate(R.layout.item_capitulo, null);
            h.label = (LinearLayout) convertView.findViewById(R.id.label);
            h.texto = (TextView) convertView.findViewById(R.id.versiculo);
            convertView.setTag(h);
        } else {
            h = (Holder) convertView.getTag();
        }
        h.texto.setText(ll.get(position).getTexto().toString());
        if (ll.get(position).getFavorito() == 1) {
//            h.label.setBackgroundColor(Color.rgb(51, 181, 229));rgb(244,211,108)
            h.label.setBackgroundColor(Color.rgb(244, 211, 108));
        } else {
            h.label.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    class Holder {

        TextView texto;
        LinearLayout label;
    }

    public void setLl(LinkedList<Versiculo> ll) {
        this.ll = ll;
    }
}
