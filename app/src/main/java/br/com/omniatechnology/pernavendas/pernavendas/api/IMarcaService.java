package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IMarcaService {

    String BASE_URL_MARCAS = "marcas/";
    String FIND_BY_ID = BASE_URL_MARCAS + "findById";
    String SAVE = BASE_URL_MARCAS + "save";
    String DELETE = BASE_URL_MARCAS + "delete";
    String LOGIN = BASE_URL_MARCAS + "login";


    @POST(SAVE)
    Observable<Marca> save(@Body Marca marca);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_MARCAS)
    Observable<List<Marca>> findAll();

    @GET(FIND_BY_ID)
    Observable<Marca> findById(@Query("id") Long id);


}
