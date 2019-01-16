package br.com.omniatechnology.pernavendas.pernavendas.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioService {

    String BASE_URL_USUARIOS = "usuarios/";
    String FIND_BY_ID = BASE_URL_USUARIOS + "findById";
    String SAVE = BASE_URL_USUARIOS + "save";
    String DELETE = BASE_URL_USUARIOS + "delete";
    String LOGIN = BASE_URL_USUARIOS + "login";


    @GET(BASE_URL_USUARIOS)
    Call<List<Bounty>> findAll();

    @GET(FIND_BY_ID)
    Call<Bounty> findById(@Query("id") Integer id);

    @POST(SAVE)
    Call<Bounty> save(@Body Bounty local);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Integer id);

}
