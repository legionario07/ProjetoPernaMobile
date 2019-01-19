package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IConfiguracaoService {

    String BASE_URL_CONFIGURACOES = "configuracoes/";
    String FIND_BY_ID = BASE_URL_CONFIGURACOES + "findById";
    String SAVE = BASE_URL_CONFIGURACOES + "save";
    String DELETE = BASE_URL_CONFIGURACOES + "delete";
    String LOGIN = BASE_URL_CONFIGURACOES + "login";


    @GET(BASE_URL_CONFIGURACOES)
    Call<List<Configuracao>> findAll();

    @GET(FIND_BY_ID)
    Call<Configuracao> findById(@Query("id") Long id);

    @POST(SAVE)
    Call<Configuracao> save(@Body Configuracao configuracao);

    @DELETE(DELETE)
    Call<Boolean> delete(@Query("id") Long id);

    @POST(LOGIN)
    Call<Configuracao> login(@Body Configuracao configuracao);

}
