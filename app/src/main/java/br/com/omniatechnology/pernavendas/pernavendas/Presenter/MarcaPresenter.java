package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class MarcaPresenter implements IMarcaPresenter, ITaskProcess {

    IModelView.IMarcaView marcaView;
    private Context context;
    Marca marca;
    private Boolean isSave;
    private List<Marca> marcas;
    private MarcasAdapter marcasAdapter;
    
    private ListView view;
    private OperationType operationType;

    public MarcaPresenter() {
        marca = new Marca();
    }

    public MarcaPresenter(IModelView.IMarcaView marcaView) {
        this.marcaView = marcaView;
    }

    public MarcaPresenter(IModelView.IMarcaView marcaView, Context context) {
        this();
        this.marcaView = marcaView;
        this.context = context;
    }



    public void atualizarList(ListView view){

        this.view = view;

        if(marcasAdapter==null){
            findAll();

        }else{

            marcasAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = marca.isValid(context);

        if (retornoStr.length() > 1)
            marcaView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(marca, ConstraintUtils.SALVAR, new MarcaServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new MarcaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = marca.isValid(context);

        if (retornoStr.length() > 1)
            marcaView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(marca, ConstraintUtils.EDITAR, new MarcaServiceImpl());
            }catch (Exception e){
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(marca, ConstraintUtils.FIND_BY_ID, new MarcaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(marca, ConstraintUtils.FIND_ALL, new MarcaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void setItem(IModel model) {
        this.marca = (Marca) model;
    }

    public void addTextWatcherNomeMarca(final EditText editText){

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    marca.setNome(s.toString());
                }
            });

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (false == hasFocus) {
                        ViewUtils.hideKeyboard(context, editText);
                    }
                }
            });
        }

    @Override
    public void onPostProcess(Serializable serializable) {

        switch (operationType){
            case SAVE: case UPDATE: case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    marcaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if(operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if(marcas!=null){
                    marcas.clear();
                    marcas.addAll((List<Marca>) serializable);
                }else {
                    marcas = (List<Marca>) serializable;
                }

                if(marcasAdapter == null) {
                    marcasAdapter = new MarcasAdapter(context, marcas);

                    view.setAdapter(marcasAdapter);
                }else{
                    marcasAdapter.notifyDataSetChanged();
                }

                marcasAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            default:
        }

    }

}
