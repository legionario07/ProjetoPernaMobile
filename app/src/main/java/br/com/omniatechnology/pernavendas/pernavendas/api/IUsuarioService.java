package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IUsuarioService {

    String BASE_URL_USUARIOS = "usuarios/";
    String FIND_BY_ID = BASE_URL_USUARIOS + "findById";
    String SAVE = BASE_URL_USUARIOS + "save";
    String DELETE = BASE_URL_USUARIOS + "delete";
    String LOGIN = BASE_URL_USUARIOS + "login";


    @POST(LOGIN)
    Observable<Usuario> login(@Body Usuario usuario);

    @POST(SAVE)
    Observable<Usuario> save(@Body Usuario usuario);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_USUARIOS)
    Observable<List<Usuario>> findAll();

    @GET(FIND_BY_ID)
    Observable<Usuario> findById(@Query("id") Long id);

}
