package org.ajcm.bibliarv.libros;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.viewpagerindicator.TabPageIndicator;
import java.util.LinkedList;
import org.ajcm.bibliarv.R;
import org.ajcm.bibliarv.Singleton;
import org.ajcm.bibliarv.db.DBAdapter;

/**
 *
 * @author Jhon_Li
 */
public class TextoActivity extends Activity {

    private DBAdapter db;
    private LinkedList<Versiculo> ll;
    private Singleton s;
    private int idLibro;
    private int numCap;
    private ViewPager pager;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.texto_lista);
        setTitle(getIntent().getStringExtra("libro_nombre"));
        s = (Singleton) getApplication();
        ll = new LinkedList<Versiculo>();
        db = new DBAdapter(this);
        db.open();
        Cursor libro = db.getLibro(getIntent().getIntExtra("libro", 0));
        idLibro = libro.getInt(0);
        numCap = libro.getInt(2);

        MyPagerAdapter adapter = new MyPagerAdapter();
        pager = (ViewPager) findViewById(R.id.my_pager);
        pager.setAdapter(adapter);
        TabPageIndicator tabIndicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        tabIndicator.setViewPager(pager);
        pager.setCurrentItem(0);
        tabIndicator.setCurrentItem(0);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return numCap;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ListView list = (ListView) inflater.inflate(R.layout.capitulo, null);
            Cursor versiculo = db.getCapitulo(idLibro, position + 1);
            versiculo.moveToFirst();
            ll = new LinkedList<Versiculo>();
            Versiculo v;
            do {
                v = new Versiculo();
                v.setFavorito(versiculo.getInt(4));
                v.setTexto(versiculo.getInt(2) + " - " + versiculo.getString(3));
                ll.add(v);
            } while (versiculo.moveToNext());
            list.setAdapter(new CapituloAdapter(getApplicationContext(), R.layout.texto_lista, ll));
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                    ImageView iv = (ImageView) view.findViewById(R.id.star);
                    if (db.update(getIntent().getIntExtra("libro", 0), pager.getCurrentItem() + 1, position + 1)) {
                        iv.setImageResource(R.drawable.btn_star_big_on);
                        return true;
                    } else {
                        iv.setImageResource(R.drawable.btn_star_big_off_disable);
                        return false;
                    }
                }
            });
            ((ViewPager) container).addView(list, 0);
            return list;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == ((View) o);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "" + (position + 1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }
    }
}
