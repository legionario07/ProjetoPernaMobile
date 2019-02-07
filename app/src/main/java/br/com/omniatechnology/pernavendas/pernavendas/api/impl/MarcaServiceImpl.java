package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IMarcaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MarcaServiceImpl implements IMarcaService {


    private IMarcaService service;
    private Retrofit retrofit;

    public MarcaServiceImpl() {
    }



    public Observable<Marca> save(final Marca marca){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IMarcaService.class);


        return service.save(marca).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<List<Marca>> findAll(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IMarcaService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Marca> findById(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IMarcaService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Boolean> delete(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IMarcaService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }


}
