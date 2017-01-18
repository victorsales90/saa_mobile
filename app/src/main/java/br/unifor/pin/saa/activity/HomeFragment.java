package br.unifor.pin.saa.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.unifor.pin.saa.R;

/**
 * Classe responsavel por instaciar o fragment Home
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
