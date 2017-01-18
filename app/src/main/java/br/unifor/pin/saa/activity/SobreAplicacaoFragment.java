package br.unifor.pin.saa.activity;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.unifor.pin.saa.R;

/**
 * Classe de instancia do fragment Sobre a Aplicacao
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class SobreAplicacaoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sobre_aplicacao, container, false);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) v.findViewById(R.id.main_collapsing);
        collapsingToolbar.setTitle("            SAA");

        return v;
    }
}
