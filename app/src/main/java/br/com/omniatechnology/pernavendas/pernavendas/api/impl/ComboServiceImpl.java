package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IComboService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ComboServiceImpl implements IComboService {

    private IComboService service;
    private Retrofit retrofit;

    public ComboServiceImpl() {
    }


    public Observable<Combo> save(final Combo combo) {

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IComboService.class);


        return service.save(combo).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<List<Combo>> findAll() {

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IComboService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Combo> findById(final Long id) {

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IComboService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Boolean> delete(final Long id) {

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IComboService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

}
