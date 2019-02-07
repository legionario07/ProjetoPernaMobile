package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.ICategoriaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoriaServiceImpl implements ICategoriaService {


    private ICategoriaService service;
    private Retrofit retrofit;

    public CategoriaServiceImpl() {
    }



    public Observable<Categoria> save(final Categoria categoria){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(ICategoriaService.class);


        return service.save(categoria).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<List<Categoria>> findAll(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(ICategoriaService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Categoria> findById(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(ICategoriaService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Boolean> delete(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(ICategoriaService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

}
