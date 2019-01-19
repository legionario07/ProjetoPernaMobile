package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IVendaService {

    String BASE_URL_VENDAS = "vendas/";
    String FIND_BY_ID = BASE_URL_VENDAS + "findById";
    String SAVE = BASE_URL_VENDAS + "save";
    String DELETE = BASE_URL_VENDAS + "delete";
    String LOGIN = BASE_URL_VENDAS + "login";


    @GET(BASE_URL_VENDAS)
    Call<List<Venda>> findAll();

    @GET(FIND_BY_ID)
    Call<Venda> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Venda> save(@Body Venda venda);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Venda> login(@Body Venda venda);

}
