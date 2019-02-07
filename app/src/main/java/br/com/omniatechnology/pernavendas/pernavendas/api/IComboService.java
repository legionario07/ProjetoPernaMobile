package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IComboService {

    String BASE_URL_COMBOS = "combos/";
    String FIND_BY_ID = BASE_URL_COMBOS + "findById";
    String SAVE = BASE_URL_COMBOS + "save";
    String DELETE = BASE_URL_COMBOS + "delete";


    @GET(BASE_URL_COMBOS)
    Observable<List<Combo>> findAll();

    @GET(FIND_BY_ID)
    Observable<Combo> findById(@Query("id") Long id);

    @POST(SAVE)
    Observable<Combo> save(@Body Combo combo);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);


}
