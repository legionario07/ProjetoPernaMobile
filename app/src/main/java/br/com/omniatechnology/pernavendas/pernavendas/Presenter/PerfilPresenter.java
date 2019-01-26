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

import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PerfisAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PerfilServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class PerfilPresenter implements IPerfilPresenter, ITaskProcess {

    IModelView.IPerfilView perfilView;
    private Context context;
    Perfil perfil;
    private Boolean isSave;
    private List<Perfil> perfis;
    private PerfisAdapter perfilsAdapter;
    private ListView view;

    private OperationType operationType;

    public PerfilPresenter() {
        perfil = new Perfil();

    }

    public PerfilPresenter(IModelView.IPerfilView perfilView) {
        this.perfilView = perfilView;
    }

    public PerfilPresenter(IModelView.IPerfilView perfilView, Context context) {
        this();
        this.perfilView = perfilView;
        this.context = context;
    }

    public void atualizarList(ListView view) {

        this.view = view;

        if (perfilsAdapter == null) {
            findAll();
        } else {

            perfilsAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = perfil.isValid(context);

        if (retornoStr.length() > 1)
            perfilView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(perfil, ConstraintUtils.SALVAR, new PerfilServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new PerfilServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = perfil.isValid(context);

        if (retornoStr.length() > 1)
            perfilView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(perfil, ConstraintUtils.EDITAR, new PerfilServiceImpl());
            }catch (Exception e){
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(perfil, ConstraintUtils.FIND_BY_ID, new CategoriaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(perfil, ConstraintUtils.FIND_ALL, new PerfilServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }


    }

    @Override
    public void setItem(IModel model) {
        this.perfil = (Perfil) model;
    }

    public void addTextWatcherNomePerfil(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                perfil.setNome(s.toString());
            }
        });

//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (false == hasFocus) {
//                    ViewUtils.hideKeyboard(context, editText);
//                }
//            }
//        });
    }




    @Override
    public void onPostProcess(Serializable serializable) {

        switch (operationType){
            case SAVE: case UPDATE: case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    perfilView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    perfilView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if(operationType == OperationType.DELETE)
                    findAll();
            break;
            case FIND_ALL:

                    if(perfis!=null){
                        perfis.clear();
                        perfis.addAll((List<Perfil>) serializable);
                    }else {
                        perfis = (List<Perfil>) serializable;
                    }

                    if(perfilsAdapter == null) {
                        perfilsAdapter = new PerfisAdapter(context, perfis);

                        view.setAdapter(perfilsAdapter);
                    }else{
                        perfilsAdapter.notifyDataSetChanged();
                    }

                    perfilsAdapter.notifyDataSetChanged();

                    break;

            case FIND_BY_ID:

                break;

                default:
        }

    }
}
