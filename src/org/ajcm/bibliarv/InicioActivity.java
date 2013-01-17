package org.ajcm.bibliarv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import org.ajcm.bibliarv.db.DBAdapter;
import org.ajcm.bibliarv.favorito.Favorito;
import org.ajcm.bibliarv.favorito.FavoritoListActivity;

/**
 *
 * @author Jhon_Li
 */
public class InicioActivity extends Activity {

    private DBAdapter db;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.inicio);
        db = new DBAdapter(this);
        try {
            db.loadDB();
        } catch (Exception ex) {
        }
    }

    public void onClickLibros(View button) {
        startActivity(new Intent(InicioActivity.this, LibroActivity.class));
    }

    public void onClickFavorito(View button) {
        startActivity(new Intent(InicioActivity.this, FavoritoListActivity.class));
    }
}
