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

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CombosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ComboServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ComboPresenter implements IComboPresenter, ITaskProcess {

    IModelView.IComboView comboView;
    private Context context;
    Combo combo;
    private Boolean isSave;
    private List<Combo> combos;
    private CombosAdapter combosAdapter;
    private OperationType operationType;

    private ListView view;

    public ComboPresenter() {
        combo = new Combo();
    }

    public ComboPresenter(IModelView.IComboView comboView) {
        this.comboView = comboView;
    }

    public ComboPresenter(IModelView.IComboView comboView, Context context) {
        this();
        this.comboView = comboView;
        this.context = context;
    }


    public void atualizarList(ListView view){

        this.view = view;

        if(combosAdapter==null){
            findAll();

        }else{

            combosAdapter.notifyDataSetChanged();

        }

    }

    public void setItem(IModel model){
        this.combo = (Combo) model;
    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = combo.isValid(context);

        if (retornoStr.length() > 1)
            comboView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(combo, ConstraintUtils.SALVAR, new ComboServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new ComboServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = combo.isValid(context);

        if (retornoStr.length() > 1)
            comboView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(combo, ConstraintUtils.EDITAR, new ComboServiceImpl());
            }catch (Exception e){
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(combo, ConstraintUtils.FIND_BY_ID, new ComboServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(combo, ConstraintUtils.FIND_ALL, new ComboServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    public void addTextWatcherNomeCombo(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                combo.setNome(s.toString());
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
                    comboView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    comboView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if(operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if(combos!=null){
                    combos.clear();
                    combos.addAll((List<Combo>) serializable);
                }else {
                    combos = (List<Combo>) serializable;
                }

                if(combosAdapter == null) {
                    combosAdapter = new CombosAdapter(context, combos);

                    view.setAdapter(combosAdapter);
                }else{
                    combosAdapter.notifyDataSetChanged();
                }

                combosAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            default:
        }

    }

}
