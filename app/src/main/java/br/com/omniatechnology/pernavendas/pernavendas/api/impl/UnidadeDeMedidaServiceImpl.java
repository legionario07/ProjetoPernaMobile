package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IUnidadeDeMedidaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UnidadeDeMedidaServiceImpl implements IUnidadeDeMedidaService {



    private IUnidadeDeMedidaService service;
    private Retrofit retrofit;

    public UnidadeDeMedidaServiceImpl() {
    }



    public Observable<UnidadeDeMedida> save(final UnidadeDeMedida unidadeDeMedida){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUnidadeDeMedidaService.class);


        return service.save(unidadeDeMedida).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<List<UnidadeDeMedida>> findAll(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUnidadeDeMedidaService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<UnidadeDeMedida> findById(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUnidadeDeMedidaService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Boolean> delete(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUnidadeDeMedidaService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }



}
