package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface IMercadoriaService<T extends Mercadoria> {

    Call<List<T>> findAll();

    Call<T> findById(@Query("id") Long id);

    Call<T> save(@Body T Produto);

    Call<Boolean> delete(@Query("id") Long id);


}
