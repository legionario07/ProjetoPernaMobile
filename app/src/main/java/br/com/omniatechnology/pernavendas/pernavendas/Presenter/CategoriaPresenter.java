package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CategoriasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class CategoriaPresenter implements ICategoriaPresenter, ITaskProcess {

    IModelView.ICategoriaView categoriaView;
    private Context context;
    Categoria categoria;
    private Boolean isSave;
    private List<Categoria> categorias;
    private CategoriasAdapter categoriasAdapter;
    private OperationType operationType;
    
    private ListView view;
    private TextView txtEmpty;

    public CategoriaPresenter() {
        categoria = new Categoria();
    }

    public CategoriaPresenter(IModelView.ICategoriaView categoriaView) {
        this.categoriaView = categoriaView;
    }

    public CategoriaPresenter(IModelView.ICategoriaView categoriaView, Context context) {
        this();
        this.categoriaView = categoriaView;
        this.context = context;
    }


    public void atualizarList(ListView view, TextView txtEmpty){

        this.view = view;
        this.txtEmpty = txtEmpty;

        if(categoriasAdapter==null){
            findAll();

        }else{

            categoriasAdapter.notifyDataSetChanged();

        }

    }

    public void setItem(IModel model){
        this.categoria = (Categoria) model;
    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = categoria.isValid(context);

        if (retornoStr.length() > 1)
            categoriaView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(categoria, ConstraintUtils.SALVAR, new CategoriaServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new CategoriaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = categoria.isValid(context);

        if (retornoStr.length() > 1)
            categoriaView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(categoria, ConstraintUtils.EDITAR, new CategoriaServiceImpl());
            }catch (Exception e){
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(categoria, ConstraintUtils.FIND_BY_ID, new CategoriaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(categoria, ConstraintUtils.FIND_ALL, new CategoriaServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    public void addTextWatcherNomeCategoria(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                categoria.setNome(s.toString());
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
                    categoriaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    categoriaView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if(operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if(categorias!=null){
                    categorias.clear();
                    List<Categoria> categoriasTemp = (List<Categoria>) serializable;
                    if(categoriasTemp!=null){

                        categorias.addAll(categoriasTemp);
                    }
                }else {
                    categorias = (List<Categoria>) serializable;

                    if(categorias==null){
                        categorias = new ArrayList<>();
                    }
                }

                if(categoriasAdapter == null) {
                    categoriasAdapter = new CategoriasAdapter(context, categorias);

                    view.setAdapter(categoriasAdapter);
                }else{
                    categoriasAdapter.notifyDataSetChanged();
                }

                if(categorias.isEmpty()){
                    view.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.GONE);
                }

                categoriasAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            default:
        }

    }

}
