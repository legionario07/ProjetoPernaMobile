package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IPedidoService {

    String BASE_URL_PEDIDOS = "pedidos/";
    String FIND_BY_ID = BASE_URL_PEDIDOS + "findById";
    String SAVE = BASE_URL_PEDIDOS + "save";
    String DELETE = BASE_URL_PEDIDOS + "delete";
    String LOGIN = BASE_URL_PEDIDOS + "login";


    @GET(BASE_URL_PEDIDOS)
    Call<List<Pedido>> findAll();

    @GET(FIND_BY_ID)
    Call<Pedido> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Pedido> save(@Body Pedido pedido);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Pedido> login(@Body Pedido pedido);

}
