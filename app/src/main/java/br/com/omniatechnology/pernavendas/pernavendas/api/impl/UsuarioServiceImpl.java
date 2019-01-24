package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.api.IUsuarioService;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IUsuario;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class UsuarioServiceImpl implements IService<Usuario> {


    private IUsuarioService service;
    private Retrofit retrofit;
    private Boolean isSave;
    private Usuario p;

    public UsuarioServiceImpl() {
    }


    @Override
    public IModel findById(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUsuarioService.class);

        return service.findById(id).execute().body();

    }

    @Override
    public List<Usuario> findAll() throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUsuarioService.class);

        List<Usuario> usuarios =  service.findAll().execute().body();

        return usuarios;
    }


    public boolean save(Usuario usuario) {

        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUsuarioService.class);

        try {
            p = service.save(usuario).execute().body();
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
        service = retrofit.create(IUsuarioService.class);

        return service.delete(id).execute().body();

    }

    @Override
    public boolean update(Long id) throws IOException {
        retrofit = RetrofitConfig.getBuilder();
        service = retrofit.create(IUsuarioService.class);

        return service.delete(id).execute().body();

    }

    public Usuario login(Usuario usuario) throws IOException{
        retrofit = RetrofitConfig.getBuilder();

        service = retrofit.create(IUsuarioService.class);

        return service.login(usuario).execute().body();
    }


}
