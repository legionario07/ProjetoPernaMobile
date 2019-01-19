package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUnidadeDeMedidaService {

    String BASE_URL_UNIDADES_DE_MEDIDAS = "unidadesDeMedidas/";
    String FIND_BY_ID = BASE_URL_UNIDADES_DE_MEDIDAS + "findById";
    String SAVE = BASE_URL_UNIDADES_DE_MEDIDAS + "save";
    String DELETE = BASE_URL_UNIDADES_DE_MEDIDAS + "delete";
    String LOGIN = BASE_URL_UNIDADES_DE_MEDIDAS + "login";


    @GET(BASE_URL_UNIDADES_DE_MEDIDAS)
    Call<List<UnidadeDeMedida>> findAll();

    @GET(FIND_BY_ID)
    Call<UnidadeDeMedida> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<UnidadeDeMedida> save(@Body UnidadeDeMedida unidadeDeMedida);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<UnidadeDeMedida> login(@Body UnidadeDeMedida unidadeDeMedida);

}
