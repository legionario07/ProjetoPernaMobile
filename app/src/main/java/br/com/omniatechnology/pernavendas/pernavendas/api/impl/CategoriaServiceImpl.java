package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.ICategoriaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class CategoriaServiceImpl implements IService<Categoria> {


    private ICategoriaService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Categoria p;

    public CategoriaServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(ICategoriaService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Categoria> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(ICategoriaService.class);

        List<Categoria> categorias =  service.findAll().execute().body();

        return categorias;
    }


    public boolean save(Categoria categoria) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(ICategoriaService.class);

        try {
            p = service.save(categoria).execute().body();
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
        service = retrofit.create(ICategoriaService.class);

        return service.delete(id).execute().body();

    }



}
