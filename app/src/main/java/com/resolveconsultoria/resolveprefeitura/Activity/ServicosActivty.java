package com.resolveconsultoria.resolveprefeitura.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.resolveconsultoria.resolveprefeitura.Adapter.AdapterServico;
import com.resolveconsultoria.resolveprefeitura.Helper.RecyclerItemClickListener;
import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.Model.Servico;
import com.resolveconsultoria.resolveprefeitura.R;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;

public class ServicosActivty extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterServico adapter;
    private List<Servico> listaServicosList = new ArrayList<>();
    private Servico servico = new Servico();
    private Categoria categoria = new Categoria();
    private MaterialSearchView searchView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicos_activty);

        categoria = (Categoria) getIntent().getSerializableExtra( "Categoria" );

        listaServicosList = categoria.getServicos();

        inicializaComponentes ();

        toolbar.setTitle( categoria.getDescricao() );
        setSupportActionBar( toolbar );

        FontDrawable drawable = new FontDrawable(this, R.string.fa_heart_solid, true, false);
        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));

        atualizaRecycleView( listaServicosList );

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener( getApplicationContext(),  recyclerView,  new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                servico = listaServicosList.get(position);
                if (servico.getPaginaWEB() == null) {
                    startActivity(new Intent(getApplicationContext(), NovaSolicitacaoActivity.class).putExtra("Servico", servico));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("Servico", servico));
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }

        } ));

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);

        final MenuItem item = menu.findItem(R.id.menu_search);
        final MenuItem itemDados = menu.findItem(R.id.DadosPessoais);
        final MenuItem itemConfig = menu.findItem(R.id.action_settings);
        final MenuItem itemSair = menu.findItem(R.id.sair);

        itemDados.setVisible(false);
        itemConfig.setVisible(false);
        itemSair.setVisible(false);

        searchView.setMenuItem(item);
        return  true;
    }

    private void inicializaComponentes (){
        toolbar        = findViewById( R.id.toolbar );
        searchView     = findViewById(R.id.search_view);
        recyclerView   = findViewById( R.id.recycle_Listagem_ItemID);
    }

    public void atualizaRecycleView (List<Servico> listaServicosList){
        adapter = new AdapterServico(listaServicosList,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}