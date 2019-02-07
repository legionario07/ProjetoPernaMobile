package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IUnidadeDeMedidaService {

    String BASE_URL_UNIDADES_DE_MEDIDAS = "unidadesDeMedidas/";
    String FIND_BY_ID = BASE_URL_UNIDADES_DE_MEDIDAS + "findById";
    String SAVE = BASE_URL_UNIDADES_DE_MEDIDAS + "save";
    String DELETE = BASE_URL_UNIDADES_DE_MEDIDAS + "delete";
    String LOGIN = BASE_URL_UNIDADES_DE_MEDIDAS + "login";


    @POST(SAVE)
    Observable<UnidadeDeMedida> save(@Body UnidadeDeMedida unidadeDeMedida);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_UNIDADES_DE_MEDIDAS)
    Observable<List<UnidadeDeMedida>> findAll();

    @GET(FIND_BY_ID)
    Observable<UnidadeDeMedida> findById(@Query("id") Long id);

}
