package br.unifor.pin.saa.database;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Classe responsavel por instanciar o framework EasyPreferences dentro da aplicacao
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class PrefsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Instancia do framework
         */
        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

    }
}
