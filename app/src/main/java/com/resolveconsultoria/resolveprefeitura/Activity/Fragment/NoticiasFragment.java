package com.resolveconsultoria.resolveprefeitura.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
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
import com.resolveconsultoria.resolveprefeitura.RSSFeed.RssFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticiasFragment extends Fragment {

    private Context context;
    private View view;
    private GridView androidGridView;
    private Cliente cliente;
    private List<Categoria> categoriaList = new ArrayList<>();

    private Retrofit retrofit;
    private Resolve resolve;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_noticias, container, false);
        context = getActivity();

        inicializaComponentes();

        LoadRSSFeedItems( "https://rss.app/feeds/s75nqu61nq5JtInf.xml") ;

        return view;

    }

    private void inicializaComponentes (){
        retrofit = RetrofitConfig.getRetrofit(  );
        resolve  = retrofit.create( Resolve.class);
    }

    /**Metodo Responsavel por Busca os RSS na WEB
     * */
    private void LoadRSSFeedItems (String url){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rlContainer, RssFragment.newInstance(url))
                .commit();
    }

}