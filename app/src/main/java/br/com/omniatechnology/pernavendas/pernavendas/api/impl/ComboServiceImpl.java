package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IComboService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class ComboServiceImpl implements IService<Combo> {


    private IComboService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Combo p;

    public ComboServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IComboService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Combo> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IComboService.class);

        List<Combo> combos =  service.findAll().execute().body();

        return combos;
    }


    public boolean save(Combo combo) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IComboService.class);

        try {
            p = service.save(combo).execute().body();
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
        service = retrofit.create(IComboService.class);

        return service.delete(id).execute().body();

    }



}
