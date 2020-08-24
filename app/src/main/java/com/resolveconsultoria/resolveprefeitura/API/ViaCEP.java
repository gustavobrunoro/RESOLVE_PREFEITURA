package com.resolveconsultoria.resolveprefeitura.API;

import com.resolveconsultoria.resolveprefeitura.Model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCEP {

    @GET("{id}/json")
    Call<CEP> select (@Path("id") int id);

}


