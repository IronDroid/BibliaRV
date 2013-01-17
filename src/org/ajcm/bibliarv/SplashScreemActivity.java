package org.ajcm.bibliarv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Jhon_Li
 */
public class SplashScreemActivity extends Activity {

    private Timer t;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screem);
        t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreemActivity.this, InicioActivity.class));
            }
        }, 2500);
    }

    @Override
    public void onBackPressed() {
    }
    
    public void saltar(View image){
        t.cancel();
        finish();
        startActivity(new Intent(SplashScreemActivity.this, InicioActivity.class));
    }
}
