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
    String SAVE_SEM_DECREMENTAR = BASE_URL_VENDAS + "saveSemDecrementar";
    String VENDAS_ABERTAS = BASE_URL_VENDAS + "findAllVendasAbertas";
    String VENDAS_FECHADAS = BASE_URL_VENDAS + "findAllVendasFechadas";
    String DELETE = BASE_URL_VENDAS + "delete";

    @POST(SAVE)
    Observable<Venda> save(@Body Venda venda);

    @POST(SAVE_SEM_DECREMENTAR)
    Observable<Venda> saveSemDrecrementar(@Body Venda venda);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_VENDAS)
    Observable<List<Venda>> findAll();

    @GET(FIND_BY_ID)
    Observable<Venda> findById(@Query("id") Long id);


    @GET(VENDAS_ABERTAS)
    Observable<List<Venda>> findAllVendasAbertas();


    @GET(VENDAS_FECHADAS)
    Observable<List<Venda>> findAllVendasFechadas();


}
