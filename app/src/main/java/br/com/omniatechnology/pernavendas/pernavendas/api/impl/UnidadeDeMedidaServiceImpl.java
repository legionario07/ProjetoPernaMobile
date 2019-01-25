package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IUnidadeDeMedidaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class UnidadeDeMedidaServiceImpl implements IService<UnidadeDeMedida> {


    private IUnidadeDeMedidaService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private UnidadeDeMedida p;

    public UnidadeDeMedidaServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUnidadeDeMedidaService.class);
        
        return service.findById(id).execute().body();

    }

    @Override
    public List<UnidadeDeMedida> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUnidadeDeMedidaService.class);
        
        List<UnidadeDeMedida> unidadesDeMedidas =  service.findAll().execute().body();

        return unidadesDeMedidas;
    }


    public boolean save(UnidadeDeMedida unidadeDeMedida) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUnidadeDeMedidaService.class);

        try {
            p = service.save(unidadeDeMedida).execute().body();
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
        service = retrofit.create(IUnidadeDeMedidaService.class);
        
        return service.delete(id).execute().body();

    }



}
