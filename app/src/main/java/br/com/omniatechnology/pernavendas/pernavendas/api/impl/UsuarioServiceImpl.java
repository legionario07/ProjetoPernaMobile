package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IUsuarioService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UsuarioServiceImpl implements IUsuarioService{


    private IUsuarioService service;
    private Retrofit retrofit;

    public UsuarioServiceImpl() {
    }


    public Observable<Usuario> login(final Usuario usuario){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUsuarioService.class);


        return service.login(usuario).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Usuario> save(final Usuario usuario){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUsuarioService.class);


        return service.save(usuario).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<List<Usuario>> findAll(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUsuarioService.class);


        return service.findAll().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Usuario> findById(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUsuarioService.class);

        return service.findById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable<Boolean> delete(final Long id){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IUsuarioService.class);

        return service.delete(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }


}
