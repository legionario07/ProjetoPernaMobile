package br.com.omniatechnology.pernavendas.pernavendas.api;


import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IProdutoService {

    String BASE_URL_PRODUTOS = "produtos/";
    String FIND_BY_ID = BASE_URL_PRODUTOS + "findById";
    String FIND_BY_CATEGORIA = BASE_URL_PRODUTOS + "findByCategoriaId";
    String SAVE = BASE_URL_PRODUTOS + "save";
    String DELETE = BASE_URL_PRODUTOS + "delete";


    @GET(BASE_URL_PRODUTOS)
    Observable<List<Produto>> findAll();

    @GET(FIND_BY_ID)
    Observable<Produto> findById(@Query("id") Long id);

    @POST(SAVE)
    Observable<Produto> save(@Body Produto Produto);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(FIND_BY_CATEGORIA)
    Observable<List<Produto>> findByCategoriaId(@Query("categoriaId") Long categoriaId);


}
