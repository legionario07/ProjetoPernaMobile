package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IPerfilService {

    String BASE_URL_PERFIS = "perfis/";
    String FIND_BY_ID = BASE_URL_PERFIS + "findById";
    String SAVE = BASE_URL_PERFIS + "save";
    String DELETE = BASE_URL_PERFIS + "delete";
    String LOGIN = BASE_URL_PERFIS + "login";


    @POST(SAVE)
    Observable<Perfil> save(@Body Perfil perfil);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_PERFIS)
    Observable<List<Perfil>> findAll();

    @GET(FIND_BY_ID)
    Observable<Perfil> findById(@Query("id") Long id);

}
