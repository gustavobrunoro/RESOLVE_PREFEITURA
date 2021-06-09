package com.resolveconsultoria.resolveprefeitura.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.resolveconsultoria.resolveprefeitura.API.Resolve;
import com.resolveconsultoria.resolveprefeitura.API.RetrofitConfig;
import com.resolveconsultoria.resolveprefeitura.Activity.ServicosActivty;
import com.resolveconsultoria.resolveprefeitura.Helper.CustomGridViewActivity;
import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.Model.Cliente;
import com.resolveconsultoria.resolveprefeitura.R;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoriasFragment extends Fragment {

    private Context context;
    private View view;
    private GridView androidGridView;
    private Cliente cliente;
    private List<Categoria> categoriaList = new ArrayList<>();

    private Retrofit retrofit;
    private Resolve resolve;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_categorias, container, false);
        context = getActivity();

        inicializaComponentes ();

        FontDrawable drawable = new FontDrawable( context, R.string.fa_heart_solid, true, false);
        drawable.setTextColor(ContextCompat.getColor( context, android.R.color.holo_blue_dark));

        int IDCliente = Integer.decode(getString(R.string.id_Cliente));
        downloadCategoria( IDCliente );

        return view;

    }

    private void inicializaComponentes (){

        androidGridView = (GridView) view.findViewById(R.id.grid_view_image_text);

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
                    Toast.makeText(context, R.string.Main_erro_Catalogo_Servico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<Cliente>> call, Throwable t) {
                Toast.makeText(context, R.string.Erro_Conexao_Internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void carregaCatalogo(Cliente cliente){

        categoriaList = cliente.getCategoias();

        List<Drawable> imamgem = new ArrayList<>();
        ImageView imageView = view.findViewById( R.id.imageView );

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(context, categoriaList );

        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int i, long id) {
                startActivity( new Intent( context , ServicosActivty.class ).putExtra("Categoria",categoriaList.get(i)));
            }
        });
    }

}