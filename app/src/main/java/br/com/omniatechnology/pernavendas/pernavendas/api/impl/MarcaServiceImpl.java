package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IMarcaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class MarcaServiceImpl implements IService<Marca> {


    private IMarcaService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Marca p;

    public MarcaServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IMarcaService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Marca> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IMarcaService.class);

        List<Marca> marcas =  service.findAll().execute().body();

        return marcas;
    }


    public boolean save(Marca marca) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IMarcaService.class);

        try {
            p = service.save(marca).execute().body();
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
        service = retrofit.create(IMarcaService.class);

        return service.delete(id).execute().body();

    }



}
