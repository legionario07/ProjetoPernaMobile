package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICategoriaService {

    String BASE_URL_CATEGORIAS = "categorias/";
    String FIND_BY_ID = BASE_URL_CATEGORIAS + "findById";
    String SAVE = BASE_URL_CATEGORIAS + "save";
    String DELETE = BASE_URL_CATEGORIAS + "delete";
    String LOGIN = BASE_URL_CATEGORIAS + "login";


    @GET(BASE_URL_CATEGORIAS)
    Call<List<Categoria>> findAll();

    @GET(FIND_BY_ID)
    Call<Categoria> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Categoria> save(@Body Categoria categoria);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Categoria> login(@Body Categoria categoria);

}
