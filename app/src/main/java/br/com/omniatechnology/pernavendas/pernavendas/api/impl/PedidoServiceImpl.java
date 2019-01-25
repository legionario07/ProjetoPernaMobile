package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IPedidoService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class PedidoServiceImpl implements IService<Pedido> {


    private IPedidoService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Pedido p;

    public PedidoServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPedidoService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Pedido> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPedidoService.class);

        List<Pedido> pedidos =  service.findAll().execute().body();

        return pedidos;
    }


    public boolean save(Pedido pedido) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IPedidoService.class);

        try {
            p = service.save(pedido).execute().body();
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
        service = retrofit.create(IPedidoService.class);

        return service.delete(id).execute().body();

    }


}
