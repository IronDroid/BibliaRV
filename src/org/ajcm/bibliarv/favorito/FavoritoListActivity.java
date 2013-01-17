package org.ajcm.bibliarv.favorito;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import java.util.LinkedList;
import org.ajcm.bibliarv.R;
import org.ajcm.bibliarv.db.DBAdapter;

/**
 *
 * @author Jhon_Li
 */
public class FavoritoListActivity extends ListActivity {

    private DBAdapter db;
    private LinkedList<Favorito> llf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favorito);
        db = new DBAdapter(this);
        llf = new LinkedList<Favorito>();
        try {
            db.open();
            Cursor favoritos = db.getFavoritos();
            Favorito f;
            do {
                f = new Favorito();
                Cursor libro = db.getLibro(favoritos.getInt(0));
                f.setReferencia(libro.getString(1) + " " + favoritos.getString(1) + ":" + favoritos.getString(2));
                f.setTexto(favoritos.getString(3));
                llf.add(f);
            } while (favoritos.moveToNext());
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ningun texto favorito", Toast.LENGTH_SHORT).show();
        }
        setListAdapter(new FavoritoAdapter(this, R.layout.list_favorito, llf));
    }
}
