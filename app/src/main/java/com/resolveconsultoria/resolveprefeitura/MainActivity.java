package com.resolveconsultoria.resolveprefeitura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.resolveconsultoria.resolveprefeitura.Activity.DadosPessoaisActivity;
import com.resolveconsultoria.resolveprefeitura.Activity.Fragment.NoticiasFragment;
import com.resolveconsultoria.resolveprefeitura.Activity.Fragment.CategoriasFragment;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigation bottomNavigation;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaComponentes ();

        toolbar.setTitle( R.string.Main_title_Prefeitura );
        setSupportActionBar( toolbar );

//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
//                R.drawable.ic_baseline_more_vert_24 );
//        toolbar.setOverflowIcon(drawable);

        carregaOpcoes();

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
    }

    /**Metodo responsavel por Efetuar o Carregamento das opções na bara inferior
     */
    public void carregaOpcoes(){

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int itemId) {

                switch (itemId){
                    case R.id.tab_home:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fv_view,new NoticiasFragment());
                        break;
                    case R.id.tab_servicos:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fv_view,new CategoriasFragment());
                        break;
//                    case R.id.tab_camera:
//                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.fv_view,new RegistroPontoExportacaoFragment());
//                        break;
                }
                fragmentTransaction.commit();
            }
        });

    }

}

