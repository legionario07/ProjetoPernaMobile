package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IPerfilService {

    String BASE_URL_PERFIS = "perfis/";
    String FIND_BY_ID = BASE_URL_PERFIS + "findById";
    String SAVE = BASE_URL_PERFIS + "save";
    String DELETE = BASE_URL_PERFIS + "delete";
    String LOGIN = BASE_URL_PERFIS + "login";


    @GET(BASE_URL_PERFIS)
    Call<List<Perfil>> findAll();

    @GET(FIND_BY_ID)
    Call<Perfil> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Perfil> save(@Body Perfil perfil);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Perfil> login(@Body Perfil perfil);

}
