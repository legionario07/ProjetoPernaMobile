package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IPedidoService {

    String BASE_URL_PEDIDOS = "pedidos/";
    String FIND_BY_ID = BASE_URL_PEDIDOS + "findById";
    String SAVE = BASE_URL_PEDIDOS + "save";
    String DELETE = BASE_URL_PEDIDOS + "delete";

    @POST(SAVE)
    Observable<Pedido> save(@Body Pedido pedido);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_PEDIDOS)
    Observable<List<Pedido>> findAll();

    @GET(FIND_BY_ID)
    Observable<Pedido> findById(@Query("id") Long id);
}
