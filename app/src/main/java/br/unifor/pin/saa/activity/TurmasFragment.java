package br.unifor.pin.saa.activity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.adapter.ListViewTurmasAdapter;
import br.unifor.pin.saa.entity.Turmas;
import br.unifor.pin.saa.entity.Usuarios;
import br.unifor.pin.saa.restImpl.RestService;
import br.unifor.pin.saa.restInteface.RestInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Classe responsavel por instanciar o fragment de Turmas
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class TurmasFragment extends Fragment {

    private ListView listViewTurmas;
    private ListViewTurmasAdapter listViewTurmasAdapter;
    private List<Turmas> listaTurmas = new ArrayList<>();
    private RestInterface restInterface;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_turmas, container, false);

        listViewTurmas = (ListView) v.findViewById(R.id.listViewTurmas);

        //Instancia do dialog para bloqueio e informe de buscar de informacao para o usuario
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setTitle("SAA Mobile");
        progressDialog.setMessage("Aguarde");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Usuarios usuario = new Usuarios();
        usuario.setId(Prefs.getInt("id",21));



        //Recupera a instancia da interface no fragment
        restInterface = RestService.getRestInterface();
        //Chama o servico e retorna uma lista com as turmas quando o fragment e criado.
        restInterface.getTurmasJSON(usuario, new Callback<List<Turmas>>() {
            @Override
            public void success(List<Turmas> turmases, Response response) {
                //Caso a lista esteja nula, apresenta um Toast informando que o usuario não possui aulas cadastrados
                //e retorna.
                if(turmases == null){
                    progressDialog.dismiss();
                    Toast.makeText(getView().getContext(), "Não tem turmas", Toast.LENGTH_LONG).show();
                    return;
                }

                for (Turmas s : turmases) {
                    if (s != null) {
                        listaTurmas.add(s);
                    }
                }
                progressDialog.dismiss();
                listViewTurmasAdapter = new ListViewTurmasAdapter(getView().getContext(), listaTurmas);
                listViewTurmas.setAdapter(listViewTurmasAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Toast.makeText(getView().getContext(), "FAILURE: " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        //Passa o objeto para outras activities
        listViewTurmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), TurmasDetailActivity.class);
                Turmas turmas = listaTurmas.get(position);
                intent.putExtra("Turmas", turmas);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
                      });

        return v;
    }
}
