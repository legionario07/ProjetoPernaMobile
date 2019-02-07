package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IVendaService {

    String BASE_URL_VENDAS = "vendas/";
    String FIND_BY_ID = BASE_URL_VENDAS + "findById";
    String SAVE = BASE_URL_VENDAS + "save";
    String DELETE = BASE_URL_VENDAS + "delete";

    @POST(SAVE)
    Observable<Venda> save(@Body Venda venda);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_VENDAS)
    Observable<List<Venda>> findAll();

    @GET(FIND_BY_ID)
    Observable<Venda> findById(@Query("id") Long id);
}
