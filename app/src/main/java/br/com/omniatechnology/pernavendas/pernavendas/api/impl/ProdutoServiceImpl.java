package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IProdutoService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ProdutoServiceImpl extends AsyncTask<Serializable, Void, String> implements IService {

    protected ProgressDialog progressDialog;

    private IProdutoService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Produto p;
    private Context context;
    ;

    public ProdutoServiceImpl() {
    }

    public ProdutoServiceImpl(Context context) {
        this.context = context;
    }

    public List<Produto> findAll() {

        return null;

    }


    public boolean save(IModel model) {

        Produto produto = (Produto) model;

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


    public boolean create(final Produto produto) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                retrofit = RetrofitConfig.getBuilder();
                service = retrofit.create(IProdutoService.class);

                produto.setNome("TESTE");
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


            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isSave;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.setMessage(s);
        progressDialog.dismiss();
    }

    @Override
    protected String doInBackground(Serializable... objects) {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IProdutoService.class);

        Produto produto = (Produto) objects[0];

        produto.setNome("TESTE");
        produto.setUnidadeDeMedida(new UnidadeDeMedida(1l));

        try {
            produto = service.save(produto).execute().body();
        } catch (IOException e) {
            Log.i(ConstraintUtils.TAG, "Erro ao salvar objeto");
            isSave = false;
        }

        if (produto != null) {
            return "Salvo com Sucesso";
        } else {
            return "Não foi possivel salvar";
        }
    }

}
