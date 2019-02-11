package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IVendaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VendaServiceImpl implements IVendaService {

    private IVendaService service;
    private Retrofit retrofit;

    public VendaServiceImpl() {
    }



    public Observable<Venda> save(final Venda venda){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);


        return service.save(venda).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public Observable<Venda> saveSemDrecrementar(Venda venda) {
        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);


        return service.saveSemDrecrementar(venda).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Venda>> findAll(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Venda> findById(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public Observable<List<Venda>> findAllVendasFechadas() {
        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);


        return service.findAllVendasFechadas().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<List<Venda>> findAllVendasAbertas() {
        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);


        return service.findAllVendasAbertas().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Boolean> delete(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IVendaService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

}
