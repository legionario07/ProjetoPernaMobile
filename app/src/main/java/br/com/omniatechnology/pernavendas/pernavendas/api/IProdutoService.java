package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IProdutoService {

    String BASE_URL_PRODUTOS = "produtos/";
    String FIND_BY_ID = BASE_URL_PRODUTOS + "findById";
    String SAVE = BASE_URL_PRODUTOS + "save";
    String DELETE = BASE_URL_PRODUTOS + "delete";
    String LOGIN = BASE_URL_PRODUTOS + "login";


    @GET(BASE_URL_PRODUTOS)
    Call<List<Produto>> findAll();

    @GET(FIND_BY_ID)
    Call<Produto> findById(@Query("id") Integer id);

    @POST(SAVE)
    Call<Produto> save(@Body Produto Produto);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Integer id);

    @POST(LOGIN)
    Call<Produto> login(@Body Produto Produto);

}
