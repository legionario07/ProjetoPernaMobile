package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IPerfilService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.api.UsuarioService;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PerfilServiceImpl implements IService<Perfil> {


    private IPerfilService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Perfil p;

    public PerfilServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPerfilService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Perfil> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPerfilService.class);

        List<Perfil> perfis =  service.findAll().execute().body();

        return perfis;
    }

    public Observable<List<Perfil>> findAll2(){

        retrofit = RetrofitConfig.getBuilderAdapter();

        service = retrofit.create(IPerfilService.class);


        return service.findAll2().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public boolean save(Perfil perfil) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPerfilService.class);

        try {
            p = service.save(perfil).execute().body();
        } catch (IOException e) {
            Log.i(ConstraintUtils.TAG, e.getMessage());
            isSave = false;
        }

        if (p == null) {
            isSave = false;
        } else {
            isSave = true;
        }


        return isSave;
    }

    @Override
    public boolean delete(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPerfilService.class);

        return service.delete(id).execute().body();

    }



}
