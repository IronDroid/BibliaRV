package org.ajcm.bibliarv.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Jhon_Li
 */
public class DBAdapter {

    private static final String DB_NAME = "biblia";
    private static final String TABLE_LIBRO = "libro";
    private static final String LIBRO_ID = "id_libro";
    private static final String LIBRO_NOMBRE = "nombre";
    private static final String LIBRO_CAP = "num_cap";
    private static final String TABLE_VERSICULO = "versiculo";
    private static final String CAPITULO = "capitulo";
    private static final String FAVORITO = "favorito";
    private static final String VERSICULO = "versiculo";
    private static final String TEXTO = "texto";
    private static final int DB_VERSION = 1;
    private Context context;
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        public void copydatabase(Context context) throws IOException {
            this.getReadableDatabase();
            InputStream input = context.getAssets().open("biblia");
            OutputStream output = new FileOutputStream("/data/data/" + context.getPackageName() + "/databases/biblia");

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        }

        public boolean checkDataBase(Context context) {
            SQLiteDatabase checkDB = null;
            String destPath = "/data/data/" + context.getPackageName() + "/databases/biblia";
            try {
                checkDB = SQLiteDatabase.openDatabase(destPath, null, SQLiteDatabase.OPEN_READONLY);
            } catch (Exception e) {
                return false;
            }
            if (checkDB != null) {
                checkDB.close();
                return true;
            }
            return false;
        }
    }

    public void loadDB() throws Exception {
        if (!helper.checkDataBase(context)) {
            helper.copydatabase(context);
        }
    }

    public boolean update(int libro, int capitulo, int versiculo) {
        Cursor rawQuery = db.rawQuery("select * from versiculo where id_libro = " + libro + " and capitulo = " + capitulo + " and versiculo = " + versiculo, null);
        rawQuery.moveToFirst();
        if (rawQuery.getInt(0) == 0) {
            db.execSQL("update " + TABLE_VERSICULO + " set favorito = " + 1 + " where id_libro = " + libro + " and capitulo = " + capitulo + " and versiculo = " + versiculo);
            return true;
        } else {
            db.execSQL("update " + TABLE_VERSICULO + " set favorito = " + 0 + " where id_libro = " + libro + " and capitulo = " + capitulo + " and versiculo = " + versiculo);
            return false;
        }
    }

    public Cursor getLibro(String nombre) {
        Cursor res = db.query(true, TABLE_LIBRO, new String[]{LIBRO_ID, LIBRO_NOMBRE, LIBRO_CAP}, LIBRO_NOMBRE + " = '" + nombre + "'", null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getLibro(int idLibro) {
        Cursor res = db.query(true, TABLE_LIBRO, new String[]{LIBRO_ID, LIBRO_NOMBRE, LIBRO_CAP}, LIBRO_ID + " = " + idLibro, null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getLibros() {
        Cursor res = db.query(true, TABLE_LIBRO, new String[]{LIBRO_ID, LIBRO_NOMBRE}, null, null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getLibrosAntiguo() {
        Cursor res = db.query(true, TABLE_LIBRO, new String[]{LIBRO_ID, LIBRO_NOMBRE}, LIBRO_ID + " < 40", null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getLibrosNuevo() {
        Cursor res = db.query(true, TABLE_LIBRO, new String[]{LIBRO_ID, LIBRO_NOMBRE}, LIBRO_ID + " > 39", null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getCapitulo(int id, int cap) {
        Cursor res = db.query(true, TABLE_VERSICULO, new String[]{LIBRO_ID, CAPITULO, VERSICULO, TEXTO, FAVORITO}, LIBRO_ID + " = " + id + " AND " + CAPITULO + " = " + cap, null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getVersiculo(int id) {
        Cursor res = db.query(true, TABLE_VERSICULO, new String[]{LIBRO_ID, CAPITULO, VERSICULO, TEXTO}, LIBRO_ID + " = " + id, null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getFavoritos() {
        Cursor res = db.query(true, TABLE_VERSICULO, new String[]{LIBRO_ID, CAPITULO, VERSICULO, TEXTO}, FAVORITO + " = " + 1, null, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public DBAdapter open() {
        db = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }
}
