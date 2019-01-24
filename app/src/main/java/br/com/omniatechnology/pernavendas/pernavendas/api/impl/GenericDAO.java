package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class GenericDAO extends AsyncTask<Serializable, IService, Serializable> {


    public GenericDAO(){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected Serializable doInBackground(Serializable... serializables) {

        String OPCAO = (String) serializables[1];
        IService service = (IService) serializables[2];


        if(ConstraintUtils.SALVAR.equals(OPCAO)){
            if(service.save((IModel) serializables[0]))
                return true;
            else
                return false;
        }else if(ConstraintUtils.DELETAR.equals(OPCAO)){

            try {
                return service.delete((Long) serializables[0]);
            } catch (IOException e) {
                return false;
            }

        }else if(ConstraintUtils.EDITAR.equals(OPCAO)){

            try {
                return service.update((Long) serializables[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(ConstraintUtils.FIND_ALL.equals(OPCAO)){

            try {
                return (Serializable) service.findAll();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(ConstraintUtils.FIND_BY_ID.equals(OPCAO)){
            try {
                return (Serializable) service.findById((Long) serializables[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(ConstraintUtils.LOGIN.equals(OPCAO)){
            try {
                return new UsuarioServiceImpl().login((Usuario) serializables[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Serializable s) {
        super.onPostExecute(s);
    }
}
