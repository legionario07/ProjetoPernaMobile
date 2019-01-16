package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
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
    Call<List<Usuario>> findAll();

    @GET(FIND_BY_ID)
    Call<Usuario> findById(@Query("id") Integer id);

    @POST(SAVE)
    Call<Usuario> save(@Body Usuario usuario);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Integer id);

    @POST(LOGIN)
    Call<Usuario> login(@Body Usuario usuario);

}
