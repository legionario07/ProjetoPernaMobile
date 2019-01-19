package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IProdutoService;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ProdutoServiceImpl {

    private IProdutoService produtoService;
    private Boolean isSave;
    private Produto p;
    
    
    public List<Produto> findAll(){
        
        return null;
        
    }
    
    public boolean create(final Produto produto){
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
    
                try {
                    p = produtoService.save(produto).execute().body();
                } catch (IOException e) {
                    Log.i(ConstraintUtils.TAG, "Erro ao salvar objeto");
                    isSave = false;
                }

                if(p == null){
                    isSave = false;
                }else{
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
    

}
