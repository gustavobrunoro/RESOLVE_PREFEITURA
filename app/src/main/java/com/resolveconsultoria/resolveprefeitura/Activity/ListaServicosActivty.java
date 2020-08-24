package com.resolveconsultoria.resolveprefeitura.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.resolveconsultoria.resolveprefeitura.Adapter.AdapterServico;
import com.resolveconsultoria.resolveprefeitura.Helper.RecyclerItemClickListener;
import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.Model.Servico;
import com.resolveconsultoria.resolveprefeitura.R;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;

public class ListaServicosActivty extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdapterServico adapter;
    private List<Servico> listaServicosList = new ArrayList<>();
    private Servico servico = new Servico();
    private Categoria categoria = new Categoria();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicos_activty);

        categoria = (Categoria) getIntent().getSerializableExtra( "Categoria" );

        listaServicosList = categoria.getServicos();

        inicializaComponentes ();

        toolbar.setTitle(categoria.getDescricao());
        setSupportActionBar(toolbar);

        adapter = new AdapterServico(listaServicosList,this);

        FontDrawable drawable = new FontDrawable(this, R.string.fa_heart_solid, true, false);
        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

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

    private void inicializaComponentes (){

        toolbar        = findViewById( R.id.toolbar );
        recyclerView   = findViewById( R.id.recycle_Listagem_ItemID);

    }

}