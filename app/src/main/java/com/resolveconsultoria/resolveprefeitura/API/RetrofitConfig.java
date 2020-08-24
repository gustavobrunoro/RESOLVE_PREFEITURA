package com.resolveconsultoria.resolveprefeitura.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitConfig {

    /**Metodos Responsavel por Retorna Paramentro de Conexão com a API
     @return  Paramento Retrofit*/
    public static Retrofit getRetrofit(){

        return new Retrofit.Builder()
                .baseUrl( API.URL_PRINCIPAL)
                .addConverterFactory( GsonConverterFactory.create() )
                .addConverterFactory( ScalarsConverterFactory.create() )
                .build() ;
    }

    public static Retrofit getViaCEP(){

        return new Retrofit.Builder()
                .baseUrl( API.URL_VIACEP)
                .addConverterFactory( JacksonConverterFactory.create() )
                .addConverterFactory( ScalarsConverterFactory.create() )
                .build();
    }

}
