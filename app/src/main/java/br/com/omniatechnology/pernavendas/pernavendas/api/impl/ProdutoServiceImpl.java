package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;
import android.util.Patterns;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IProdutoService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class ProdutoServiceImpl implements IService<Produto> {


    private static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    private IProdutoService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Produto p;

    public ProdutoServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IProdutoService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Produto> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IProdutoService.class);

        List<Produto> produtos =  service.findAll().execute().body();

        return produtos;
    }


    public boolean save(Produto produto) {

        retrofit = RetrofitConfig.getBuilderData(PATTERN_YYYY_MM_DD);
        service = retrofit.create(IProdutoService.class);

        if(produto.getDataCadastro()==null){
            produto.setDataCadastro(Calendar.getInstance());
        }

        try {
            p = service.save(produto).execute().body();
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
        service = retrofit.create(IProdutoService.class);

        return service.delete(id).execute().body();

    }




}
