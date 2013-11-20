package org.ajcm.bibliarv;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import java.util.LinkedList;
import org.ajcm.bibliarv.db.DBAdapter;
import org.ajcm.bibliarv.favorito.Favorito;
import org.ajcm.bibliarv.favorito.FavoritoAdapter;
import org.ajcm.bibliarv.libros.TextoActivity;

/**
 *
 * @author Jhon_Li
 */
public class InicioActivity extends ListActivity {

    private DBAdapter db;
    private LinkedList<String> ll;
    private LinkedList<Favorito> llf;
    private Singleton s;
    TabHost contenedorPestana;
    TabHost.TabSpec tabSpec;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.inicio);
        s = (Singleton) getApplication();
        ll = new LinkedList<String>();
        llf = new LinkedList<Favorito>();
        db = new DBAdapter(this);
        try {
            db.loadDB();
        } catch (Exception ex) {
        }
        listLibros();
        tabs();
    }

    private void listLibros() {
        db.open();
        Cursor libros = db.getLibros();
        db.close();
        do {
            ll.add(libros.getString(1));
        } while (libros.moveToNext());
        setListAdapter(new LibroAdapter(this, R.layout.list_libros, ll));
    }

    private void ListFavoritos() {
        ListView listF = (ListView) findViewById(R.id.list_favoritos);
        llf = new LinkedList<Favorito>();
        db.open();
        Cursor favoritos = db.getFavoritos();
        if (favoritos.getCount() > 0) {
            Favorito f;
            do {
                f = new Favorito();
                Cursor libro = db.getLibro(favoritos.getInt(0));
                f.setReferencia(libro.getString(1) + " " + favoritos.getString(1) + ":" + favoritos.getString(2));
                f.setTexto(favoritos.getString(3));
                llf.add(f);
            } while (favoritos.moveToNext());
            db.close();
        } else {
            Toast.makeText(getApplicationContext(), "Ningun texto favorito", Toast.LENGTH_SHORT).show();
        }
        if (listF.getAdapter() != null) {
            ((FavoritoAdapter) listF.getAdapter()).clear();
        }
        listF.setAdapter(new FavoritoAdapter(this, R.layout.list_favorito, llf));
    }

    private void tabs() {
        contenedorPestana = (TabHost) findViewById(android.R.id.tabhost);
        contenedorPestana.setup();
        tabSpec = contenedorPestana.newTabSpec("libros");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Libros");
        contenedorPestana.addTab(tabSpec);
        tabSpec = contenedorPestana.newTabSpec("favoritos");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Favoritos");
        contenedorPestana.addTab(tabSpec);
        contenedorPestana.setCurrentTab(0);
        contenedorPestana.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("favoritos")) {
                    ListFavoritos();
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        db.open();
        Cursor libro = db.getLibro(position + 1);
        db.close();
        libro.moveToFirst();
        s.setLibro(libro.getString(1));
        Intent i = new Intent(InicioActivity.this, TextoActivity.class);
        i.putExtra("libro", libro.getInt(0));
        i.putExtra("libro_nombre", libro.getString(1));
        i.putExtra("capitulo", libro.getInt(2));
        startActivity(i);
    }
}
