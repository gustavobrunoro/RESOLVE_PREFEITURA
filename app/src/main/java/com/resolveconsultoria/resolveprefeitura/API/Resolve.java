package com.resolveconsultoria.resolveprefeitura.API;

import com.resolveconsultoria.resolveprefeitura.Model.Cliente;
import com.resolveconsultoria.resolveprefeitura.Model.Solicitacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Resolve {

    @GET("catalogo/{ClienteID}")
    Call<List<Cliente>> getCatalogo (@Path("ClienteID") int ClienteID);

    @POST("novaSolicitacao")
    Call<Solicitacao> postSolicitacao (@Body Solicitacao solicitacao) ;
}
