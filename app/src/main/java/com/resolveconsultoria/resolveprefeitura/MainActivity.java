package com.resolveconsultoria.resolveprefeitura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.resolveconsultoria.resolveprefeitura.API.Resolve;
import com.resolveconsultoria.resolveprefeitura.API.RetrofitConfig;
import com.resolveconsultoria.resolveprefeitura.Activity.DadosPessoaisActivity;
import com.resolveconsultoria.resolveprefeitura.Activity.ServicosActivty;
import com.resolveconsultoria.resolveprefeitura.Helper.CustomGridViewActivity;
import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.Model.Cliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView androidGridView;
    private Cliente cliente;
    private List<Categoria> categoriaList = new ArrayList<>();

    private Retrofit retrofit;
    private Resolve resolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int IDCliente = Integer.decode(getString(R.string.id_Cliente));

        inicializaComponentes ();

        toolbar.setTitle( R.string.Main_title_Prefeitura );
        setSupportActionBar( toolbar );

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_baseline_more_vert_24 );
        toolbar.setOverflowIcon(drawable);

        downloadCategoria( IDCliente );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        final MenuItem item = menu.findItem(R.id.menu_search);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.DadosPessoais:
                startActivity( new Intent( getApplicationContext() , DadosPessoaisActivity.class ));
                break;
        }
        return true;
    }

    private void inicializaComponentes (){

        toolbar         = findViewById( R.id.toolbar );
        androidGridView = (GridView)findViewById(R.id.grid_view_image_text);

        retrofit = RetrofitConfig.getRetrofit(  );
        resolve  = retrofit.create( Resolve.class);
    }

    public void downloadCategoria(int IDCliente){
        resolve.getCatalogo(IDCliente).enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse (Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful()){
                    cliente = response.body().get(0);
                    carregaCatalogo(cliente);
                }
                else{
                    Toast.makeText(MainActivity.this, R.string.Main_erro_Catalogo_Servico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<Cliente>> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.Erro_Conexao_Internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void carregaCatalogo(Cliente cliente){

        categoriaList = cliente.getCategoias();

        List<Drawable> imamgem = new ArrayList<>();
        ImageView imageView = findViewById(R.id.imageView);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(this, categoriaList );

        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int i, long id) {
                startActivity( new Intent( getApplicationContext() , ServicosActivty.class ).putExtra("Categoria",categoriaList.get(i)));
            }
        });
    }

}

