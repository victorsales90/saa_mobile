package br.unifor.pin.saa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.adapter.ListViewSideMenuAdapter;
import br.unifor.pin.saa.entity.Anexos;

import br.unifor.pin.saa.restImpl.RestService;
import br.unifor.pin.saa.restInteface.RestInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Classe de instancia do componente NavigationDrawer
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */

public class NavigationDrawer extends AppCompatActivity {

    NavigationView navView;
    DrawerLayout drawerLayout;
    ListView listView;
    ListViewSideMenuAdapter adapter;
    protected FrameLayout frameLayout;
    FloatingActionButton actionB;
    FloatingActionButton actionA;
    FloatingActionButton actionNewAula;
    private ImageView profileImage;
    private RestInterface restInterface;
    private File file;

    private static final int CAMERA_REQUEST = 1888;


    protected void onCreateDrawer() {

        listView = (ListView) findViewById(R.id.list_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);

        profileImage = (ImageView) findViewById(R.id.profile_image);
        Picasso.with(this).load(RestService.URL_PERFIL + Prefs.getString("matricula", "") +".png").placeholder(R.drawable.camera).into(profileImage);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });

        TextView profileLogin = (TextView) findViewById(R.id.profile_login);
        profileLogin.setText(Prefs.getString("nome", "Victor Sales"));

        restInterface = RestService.getRestInterface();
    }

    /***
     * Metodo responsavel por chamar a camera quando clicado na imageView do navigationDrawer
     * @param v
     */
    public void takePicture(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    /***
     * Metodo responsavel por adicionar a foto retirada da camera apos o usuario
     * finalizar o processo.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (photo != null) {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);

                byte imageData[] = bos.toByteArray();

                String imageDataString = encodeImage(imageData);
                Log.i("String: ", imageDataString);

                Anexos anexos = new Anexos();
                anexos.setPhoto(imageDataString);

                restInterface.salvarImagemUsuario(Prefs.getString("matricula", ""), anexos , new Callback<Anexos>() {
                    @Override
                    public void success(Anexos anexos, Response response) {
                        Toast.makeText(NavigationDrawer.this, "Imagem salva com sucesso!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(NavigationDrawer.this, "Erro ao salvar: " +error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

                try {
                    Picasso.with(this).load(convertBitmapToFile(photo))
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .noFade()
                            .into(profileImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * Metodo responsavel por converter um bitmap em um file
     * @param photo
     * @return File
     * @throws IOException
     */
    private File convertBitmapToFile(Bitmap photo) throws IOException {
        File f = new File(this.getFilesDir(), "/saa_files/");
        f.createNewFile();

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        return f;
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    /**
     * Metodo responsavel por esconder o teclado do android caso seja clicado no navigationDrawer
     * quando se está no fragment de nova aula
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!= null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Metodo responsavel por esconder/mostrar o navigationDrawer
     */
    void toogleMenu() {
        hideSoftKeyboard();
        if(drawerLayout.isDrawerOpen(navView)){
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(navView);
        }
    }
}
