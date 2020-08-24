package com.resolveconsultoria.resolveprefeitura.Database;

import android.content.Context;

import com.google.gson.Gson;
import com.resolveconsultoria.resolveprefeitura.Model.Configuracoes;
import com.resolveconsultoria.resolveprefeitura.Model.Usuario;

public class SharedPreferences {

    public Context context;

    // Nome do Arquivo
    public static final String ARQUIVO = "PREFEITURA";

    // Nome das Chaves
    public static final String DADOSPESSOAIS = "DADOSPESSOAIS";

    public static final String CONFIGURACOES = "CONFIGURACOES";

    private android.content.SharedPreferences sharedPreferences;
    private android.content.SharedPreferences.Editor editor ;

    /**Metodos responsavel retorna configuração SharedPreferences*/
    public SharedPreferences (Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    /**Metodos responsavel atualizar dados pessoais no SharedPreferences*/
    public void atualizaDadosPessoais(Usuario entrevistador){

        Gson gson = new Gson();
        String json = gson.toJson(entrevistador);
        editor.putString(DADOSPESSOAIS, json);
        editor.commit();

    }

    /**Metodos responsavel por recupera os dados pessoais no SharedPreferences
     @return  Dados do Usuario*/
    public Usuario recupraDadosPessoais(){

        Usuario usuario = new Usuario();

        Gson gson = new Gson();
        String json = sharedPreferences.getString(DADOSPESSOAIS, "");
        usuario = gson.fromJson(json, Usuario.class);

        return usuario ;

    }

    /**Metodos responsavel atualizar dados pessoais no SharedPreferences*/
    public void atualizaConfiguracoes(Configuracoes configuracoes){

        Gson gson = new Gson();
        String json = gson.toJson(configuracoes);
        editor.putString(CONFIGURACOES, json);
        editor.commit();

    }

    /**Metodos responsavel por recupera os dados pessoais no SharedPreferences
     @return  Dados do Usuario*/
    public Configuracoes recupraConfiguracoes(){

        Configuracoes configuracoes = new Configuracoes();

        Gson gson = new Gson();
        String json = sharedPreferences.getString(CONFIGURACOES, "");
        configuracoes = gson.fromJson(json, Configuracoes.class);

        return configuracoes ;

    }


}
