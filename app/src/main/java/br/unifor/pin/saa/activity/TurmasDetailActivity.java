package br.unifor.pin.saa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.entity.Turmas;

/**
 * Classe de iniciacao da Activity de detalhe da turma
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class TurmasDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turmas_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Detalhes da Turma");
        /**
         * Recupera o objeto passado de outra activity/fragment para setar nas labels que compoem a activity
         */
        Turmas turmas = (Turmas) getIntent().getSerializableExtra("Turmas");

        TextView txtUniversidade = (TextView) findViewById(R.id.nome_universidade);
        TextView txtDisciplina = (TextView) findViewById(R.id.nome_disciplina);
        TextView txtSemestre = (TextView) findViewById(R.id.nome_semestre);
        TextView txtProfessor = (TextView) findViewById(R.id.nome_professor);

        if (txtUniversidade != null) {
            txtUniversidade.setText("Instituição: " + turmas.getInstituicao().toString());
        }
        if (txtDisciplina != null) {
            txtDisciplina.setText("Disciplina: " + turmas.getDisciplina().toString());
        }
        if (txtSemestre != null) {
            txtSemestre.setText("Período: " + turmas.getSemestre().toString());
        }

        if (txtProfessor != null) {
            txtProfessor.setText("Professor: " + turmas.getProfessor().getNome().toString());
        }
    }


    public void clickBtnAula(View view){
        Intent it = new Intent(this, AulasActivity.class);
        startActivity(it);
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
