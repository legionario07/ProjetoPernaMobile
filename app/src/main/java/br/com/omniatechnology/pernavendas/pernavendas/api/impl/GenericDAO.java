package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.os.AsyncTask;

import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import retrofit2.Retrofit;

public class GenericDAO extends AsyncTask<Serializable, IService, Boolean> {

    private Retrofit retrofit;


    public GenericDAO(){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected Boolean doInBackground(Serializable... serializables) {

        IModel model = (IModel) serializables[0];
        String OPCAO = (String) serializables[1];
        IService service = (IService) serializables[2];


        if(ConstraintUtils.SALVAR.equals(OPCAO)){
            if(service.save(model))
                return true;
            else
                return false;
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
    }
}
