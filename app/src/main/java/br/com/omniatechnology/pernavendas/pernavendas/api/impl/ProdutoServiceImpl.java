package br.com.omniatechnology.pernavendas.pernavendas.api.impl;


import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IProdutoService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProdutoServiceImpl implements IProdutoService {


    private IProdutoService service;
    private Retrofit retrofit;

    public ProdutoServiceImpl() {
    }



    public Observable<Produto> save(final Produto produto){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IProdutoService.class);


        return service.save(produto).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<List<Produto>> findAll(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IProdutoService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Produto> findById(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IProdutoService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Boolean> delete(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IProdutoService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public Observable<List<Produto>> findByCategoriaId(Long categoriaId) {
        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IProdutoService.class);


        return service.findByCategoriaId(categoriaId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
