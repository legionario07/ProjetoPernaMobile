package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IVendaService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class VendaServiceImpl implements IService<Venda> {


    private IVendaService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Venda p;

    public VendaServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IVendaService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Venda> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IVendaService.class);

        List<Venda> vendas =  service.findAll().execute().body();

        return vendas;
    }


    public boolean save(Venda venda) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IVendaService.class);

        try {
            p = service.save(venda).execute().body();
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
        service = retrofit.create(IVendaService.class);

        return service.delete(id).execute().body();

    }



}
