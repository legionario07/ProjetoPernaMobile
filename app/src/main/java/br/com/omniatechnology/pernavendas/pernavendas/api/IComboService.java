package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IComboService {

    String BASE_URL_COMBOS = "combos/";
    String FIND_BY_ID = BASE_URL_COMBOS + "findById";
    String SAVE = BASE_URL_COMBOS + "save";
    String DELETE = BASE_URL_COMBOS + "delete";
    String LOGIN = BASE_URL_COMBOS + "login";


    @GET(BASE_URL_COMBOS)
    Call<List<Combo>> findAll();

    @GET(FIND_BY_ID)
    Call<Combo> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Combo> save(@Body Combo combo);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Combo> login(@Body Combo combo);

}
