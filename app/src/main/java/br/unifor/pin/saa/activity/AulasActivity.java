package br.unifor.pin.saa.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.adapter.ListViewAulaAdapter;
import br.unifor.pin.saa.entity.Aula;
import br.unifor.pin.saa.entity.Turmas;
import br.unifor.pin.saa.restImpl.RestService;
import br.unifor.pin.saa.restInteface.RestInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Classe de iniciacao da Activity de detalhe da turma
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class AulasActivity extends AppCompatActivity {


    List<Aula> listAula = new ArrayList<>();
    ListView listViewAula;
    ListViewAulaAdapter listViewAulaAdapter;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


      listViewAula = new ListView(this);

      // setContentView(R.layout.activity_lista_aulas);
        setContentView(listViewAula);



        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Lista de Aulas");


        //aqui
        listViewAulaAdapter = new ListViewAulaAdapter(this, listAula);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("SSA Mobile");
        progressDialog.setMessage("Aguarde");
        progressDialog.setCancelable(false);
        progressDialog.show();






        /**
         * Recupera o objeto passado de outra activity/fragment para setar nas labels que compoem a activity
         */

        //  Turmas turmas = (Turmas) getIntent().getSerializableExtra("Turmas");

        RestInterface restInterface;
        Turmas turma = new Turmas();
        turma.setId(30);//Prefs.getInt("id",30)





        //Recupera a instancia da interface no fragment
        restInterface = RestService.getRestInterface();
        //Chama o servico e retorna uma lista com as aulas quando o fragment e criado.
        restInterface.getAulasJSON(turma, new Callback<List<Aula>>() {


            @Override
            public void success(List<Aula> aulas, Response response) {
//                TextView txtAula = (TextView) findViewById(R.id.aula1);
//                txtAula.setText(aulas.get(0).getDescricao().toString());

                if(aulas == null){
                    progressDialog.dismiss();
                    Toast.makeText(null, "Você não tem agendamentos", Toast.LENGTH_LONG).show();
                    return;
                }

                //Percorre a lista do servico e adiciona o objeto a lista do fragment para adaptacao
                for (Aula a : aulas) {
                    if (a.getDescricao() != null) {

//                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
//                      String dataInicio = dateFormat.format(a.getDataInicio());
//                        try {
//                            a.setDataInicio(dateFormat.parse(dataInicio));
//                            a.setDataFim(dateFormat.parse(dataFim));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        listAula.add(a);
                    }
                }
                progressDialog.dismiss();


                listViewAula.setAdapter(listViewAulaAdapter);


            }

            @Override
            public void failure(RetrofitError error) {
                // progressDialog.dismiss();
                //Toast.makeText(getView().getContext(), "FAILURE: " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });


    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideSoftKeyboard();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

}
