package org.ajcm.bibliarv;

import android.app.Application;

/**
 *
 * @author Jhon_Li
 */
public class Singleton extends Application {

    private int cap;
    private String libro;

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }
}
