package br.unifor.pin.saa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.entity.Usuarios;
import br.unifor.pin.saa.restImpl.RestService;
import br.unifor.pin.saa.restInteface.RestInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Classe responsavel por instaciar o fragment Home
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private RestInterface restInterface;
    private EditText txtEmail;
    private EditText txtPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        Button btnLoggar = (Button) findViewById(R.id.btn_loggar);

        //Verifica se existe email e senha cadastrada no smartphone
        //Se existir, inicia a main activity
        //Se não, inicia a tela de login do usuario.
        if(Prefs.getString("senha", null) != null){
            if(Prefs.getString("email", null) != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return;
            }
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("SAA Mobile");
        progressDialog.setMessage("Aguarde...");

        restInterface = RestService.getRestInterface();
        btnLoggar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final Usuarios usuario = new Usuarios();
                usuario.setEmail(txtEmail.getText().toString());
                usuario.setSenha(txtPassword.getText().toString());

                progressDialog.show();
                progressDialog.setCancelable(false);
                restInterface.getUsuariosJson(usuario, new Callback<Usuarios>() {
                    @Override
                    public void success(Usuarios usuario, Response response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                        salvarUsuario(usuario);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        Toast.makeText(v.getContext(), "Failure: " +error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Método responsavel por salvar usuario na base de dados para utilização posterior dentro da aplicacao
     * e para a reinicializacao do mesmo
     * @param u
     */
    private void salvarUsuario(Usuarios u) {
        String[] nomeCompleto = u.getNome().split(" ");
        String primeiroNome = nomeCompleto[0];
        String sobrenome = nomeCompleto[1];
        String nomeSalvar = primeiroNome + " " + sobrenome;

        Prefs.putString("email", u.getEmail());
        Prefs.putString("senha", u.getSenha());
        Prefs.putString("nome", nomeSalvar);
        Prefs.putInt("id", u.getId());
    }
}
