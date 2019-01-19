package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
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

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IProdutoService.class);

        produto.setNome("TESTE");
        produto.setDescricao("Teste");
        produto.setUnidadeDeMedida(new UnidadeDeMedida(1l));

        try {
            p = service.save(produto).execute().body();
        } catch (IOException e) {
            Log.i(ConstraintUtils.TAG, "Erro ao salvar objeto");
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

    @Override
    public boolean update(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IProdutoService.class);

        return service.delete(id).execute().body();

    }



}
