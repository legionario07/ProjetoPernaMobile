package br.com.omniatechnology.pernavendas.pernavendas.api.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.api.IService;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class GenericDAO extends AsyncTask<Serializable, IService, Serializable> {

    private Context context;
    private ProgressDialog progressDialog;
    private ITaskProcess listener;

    public GenericDAO(){

    }
    public GenericDAO(Context context){
        this();
        this.context = context;
        

    }

    public GenericDAO(Context context, ITaskProcess listener){
        this(context);
        this.listener = listener;


    }

    private void showProgressDialog() throws Exception{
        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getString(R.string.processando));
        progressDialog.setTitle(context.getString(R.string.aguarde));
        progressDialog.show();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        try {
            showProgressDialog();
        }catch(Exception e){
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }

    }

    @Override
    protected Serializable doInBackground(Serializable... serializables) {

        String OPCAO = (String) serializables[1];
        IService service = (IService) serializables[2];


        if(ConstraintUtils.SALVAR.equals(OPCAO) || ConstraintUtils.EDITAR.equals(OPCAO)){
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

        }else if(ConstraintUtils.FIND_ALL.equals(OPCAO)){

            try {
                return (Serializable) service.findAll();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }else if(ConstraintUtils.FIND_BY_ID.equals(OPCAO)){
            try {
                return (Serializable) service.findById((Long) serializables[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
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
        progressDialog.dismiss();

        listener.onPostProcess(s);


    }


}
