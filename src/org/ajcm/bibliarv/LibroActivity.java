package org.ajcm.bibliarv;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.LinkedList;
import org.ajcm.bibliarv.db.DBAdapter;
import org.ajcm.bibliarv.libros.TextoActivity;

public class LibroActivity extends ListActivity {

    private DBAdapter db;
    private LinkedList<String> ll;
    private Singleton s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_libros);
        s = (Singleton) getApplication();
        ll = new LinkedList<String>();
        db = new DBAdapter(this);
        cargarLibros();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        db.open();
        Cursor libro = db.getLibro(position + 1);
        db.close();
        libro.moveToFirst();
        s.setLibro(libro.getString(1));
        Intent i;
        i = new Intent(LibroActivity.this, TextoActivity.class);
        i.putExtra("libro", libro.getInt(0));
        i.putExtra("libro_nombre", libro.getString(1));
        i.putExtra("capitulo", libro.getInt(2));
        startActivity(i);
    }

    private void cargarLibros() {
        db.open();
        Cursor libros = db.getLibros();
        db.close();
        do {
            ll.add(libros.getString(1));
        } while (libros.moveToNext());
        setListAdapter(new LibroAdapter(this, R.layout.list_libros, ll));
    }
}
