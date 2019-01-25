package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IConfiguracaoService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class ConfiguracaoServiceImpl implements IService<Configuracao> {


    private IConfiguracaoService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Configuracao p;

    public ConfiguracaoServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IConfiguracaoService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Configuracao> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IConfiguracaoService.class);

        List<Configuracao> configuracoes =  service.findAll().execute().body();

        return configuracoes;
    }


    public boolean save(Configuracao configuracao) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IConfiguracaoService.class);

        try {
            p = service.save(configuracao).execute().body();
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
        service = retrofit.create(IConfiguracaoService.class);

        return service.delete(id).execute().body();

    }



}
