package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IConfiguracaoService {

    String BASE_URL_CONFIGURACOES = "configuracoes/";
    String FIND_BY_ID = BASE_URL_CONFIGURACOES + "findById";
    String SAVE = BASE_URL_CONFIGURACOES + "save";
    String DELETE = BASE_URL_CONFIGURACOES + "delete";



    @POST(SAVE)
    Observable<Configuracao> save(@Body Configuracao configuracao);

    @DELETE(DELETE)
    Observable<Boolean> delete(@Query("id") Long id);

    @GET(BASE_URL_CONFIGURACOES)
    Observable<List<Configuracao>> findAll();

    @GET(FIND_BY_ID)
    Observable<Configuracao> findById(@Query("id") Long id);

}
