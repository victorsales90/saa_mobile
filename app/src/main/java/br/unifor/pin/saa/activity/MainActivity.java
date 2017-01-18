package br.unifor.pin.saa.activity;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.adapter.ListViewSideMenuAdapter;

/**
 * Classe responsavel por alocar todos os fragments,
 * criar o navigationDrawer,
 * setar os itens do NavigationDrawer
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class MainActivity extends NavigationDrawer {

    private ArrayList<br.unifor.pin.saa.entity.MenuItem> listMenuItens = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce = false;
    private boolean novaTurmas = false;
    private FloatingActionsMenu multipleActions;
    private Integer clickMenu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        super.onCreateDrawer();

        TypedArray navMenuIcon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        String[] navMenuName = getResources().getStringArray(R.array.nav_drawer_items);

        listMenuItens.add(new br.unifor.pin.saa.entity.MenuItem(navMenuIcon.getResourceId(0, -1), navMenuName[0]));
        listMenuItens.add(new br.unifor.pin.saa.entity.MenuItem(navMenuIcon.getResourceId(1, -1), navMenuName[1]));
        listMenuItens.add(new br.unifor.pin.saa.entity.MenuItem(navMenuIcon.getResourceId(2, -1), navMenuName[2]));
        listMenuItens.add(new br.unifor.pin.saa.entity.MenuItem(navMenuIcon.getResourceId(3, -1), navMenuName[3]));

        navMenuIcon.recycle();

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.changeable, homeFragment).commit();

        //Intancia o Adapter criado para a lista de ítens do NavigationDrawer
        adapter = new ListViewSideMenuAdapter(this, listMenuItens);
        //Adapta os ítens da lista de itens no NavigationDrawer
        listView.setAdapter(adapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Instancia o Floating Action Button
        multipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        RelativeLayout fabMenuLayout = (RelativeLayout) findViewById(R.id.layout_fab_menu);
        //Se o Floating Action Button estiver expandido, o mesmo e fechado
        if (fabMenuLayout != null) {
            fabMenuLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (multipleActions.isExpanded()) {
                        multipleActions.collapse();
                        return true;
                    }
                    return false;
                }
            });
        }

        //Verifica qual o item do menu foi clicado na lista de Itens do menu NavigationDrawer
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    //Ser for o primeiro, realoca a home
                    case 0:
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.changeable, homeFragment).commit();
                        break;

                    //Se for o segundo, realoca turmas
                    case 1:
                        TurmasFragment turmas = new TurmasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.changeable, turmas).commit();
                        novaTurmas = true;
                        break;


                    //Se for o terceiro, realoca sobre a aplicacao
                    case 2:
                        SobreAplicacaoFragment sobreAplicacao = new SobreAplicacaoFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.changeable, sobreAplicacao).commit();
                        break;

                    //Se for o quarto, inicia dialog perguntando se deseja realmente sair da aplicacao
                    case 3:
                        finish(view);
                        break;

                }
                updateMenuItens(position);
                toogleMenu();
            }
        });


        setTitle("");

        frameLayout = (FrameLayout) findViewById(R.id.changeable);

        actionA = (FloatingActionButton) findViewById(R.id.action_a);
        if (actionA != null) {
            actionA.setIcon(R.drawable.ic_power_settings_new_black_24dp);
        }

        //Adiciona acao para um dos botoes do Floating Action Button redirecionando para o site da unifor no browser.
        actionB = (FloatingActionButton) findViewById(R.id.action_b);
        if (actionB != null) {
            actionB.setIcon(R.drawable.ic_trending_flat_black_24dp);
        }
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.unifor.br");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * Metodo generico responsavel por iniciar o dialog de saida da aplicacao
     * @param view
     */
    public void finish(View view){
        dialogExitApplication(view);
    }

    /**
     * Metodo responsavel por indicar qual item do menu NavigationDrawer esta selecionado.
     * @param position
     */
    public void updateMenuItens(int position){
        for (br.unifor.pin.saa.entity.MenuItem m : listMenuItens) {
            m.setSelected(false);
        }
        listMenuItens.get(position).setSelected(true);

        adapter.notifyDataSetChanged();

        if(position == 1) {
            clickMenu++;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toogleMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Metodo que inicia dialog de saida da aplicacao caso o botao de voltar seja tocado.
     */
    @Override
    public void onBackPressed() {
        hideSoftKeyboard();
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        View view = findViewById(R.id.changeable);
        finish(view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10);
    }

    /**
     * Metodo que cria o dialog para a finalização da aplicacao e redirecionamento para a tela de login
     * @param view
     */
    protected void dialogExitApplication(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Tem certeza que deseja sair?")
                .setCancelable(true)
                .setNegativeButton("NÃO", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("SIM", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Prefs.remove("email");
                        Prefs.remove("senha");
                        Prefs.remove("nome");
                        Prefs.remove("id");
                        finish();
                        //error here. Intend to close the activtiy that created this dialog and has the error
                    }
                });

        AlertDialog alert = builder.create();
        // The dialog utils is outside an activity. Need to set owner
        alert.show();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
