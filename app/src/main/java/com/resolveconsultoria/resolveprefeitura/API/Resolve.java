package com.resolveconsultoria.resolveprefeitura.API;

import com.resolveconsultoria.resolveprefeitura.Model.Cliente;
import com.resolveconsultoria.resolveprefeitura.Model.Solicitacao;
import com.resolveconsultoria.resolveprefeitura.Model.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Resolve {

    @GET("catalogo/{ClienteID}")
    Call<List<Cliente>> getCatalogo (@Path("ClienteID") int ClienteID);

    @Multipart
    @POST("upload")
    Call<ResponseBody> postSolicitacao (@Body Solicitacao solicitacao,@Part MultipartBody.Part file) ;

    @POST("usuario")
    Call<Usuario> postUsuario (@Body Usuario usuario) ;

}
