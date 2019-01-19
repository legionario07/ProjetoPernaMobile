package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMarcaService {

    String BASE_URL_MARCAS = "marcas/";
    String FIND_BY_ID = BASE_URL_MARCAS + "findById";
    String SAVE = BASE_URL_MARCAS + "save";
    String DELETE = BASE_URL_MARCAS + "delete";
    String LOGIN = BASE_URL_MARCAS + "login";


    @GET(BASE_URL_MARCAS)
    Call<List<Marca>> findAll();

    @GET(FIND_BY_ID)
    Call<Marca> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Marca> save(@Body Marca marca);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Marca> login(@Body Marca marca);

}
