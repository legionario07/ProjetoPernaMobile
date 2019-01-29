package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ConfiguracoesAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ConfiguracaoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ConfiguracaoPresenter implements IConfiguracaoPresenter, ITaskProcess {

    IModelView.IConfiguracaoView configuracaoView;
    private Context context;
    Configuracao configuracao;
    private Boolean isSave;
    private List<Configuracao> configuracoes;
    private ConfiguracoesAdapter configuracoesAdapter;
    
    private ListView view;
    private OperationType operationType;

    public ConfiguracaoPresenter() {
        configuracao = new Configuracao();
    }

    public ConfiguracaoPresenter(IModelView.IConfiguracaoView configuracaoView) {
        this.configuracaoView = configuracaoView;
    }

    public ConfiguracaoPresenter(IModelView.IConfiguracaoView configuracaoView, Context context) {
        this();
        this.configuracaoView = configuracaoView;
        this.context = context;
    }

    public void atualizarList(ListView view){

        this.view = view;

        if(configuracoesAdapter==null){
            findAll();

        }else{

            configuracoesAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = configuracao.isValid(context);

        if (retornoStr.length() > 1)
            configuracaoView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(configuracao, ConstraintUtils.SALVAR, new ConfiguracaoServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new ConfiguracaoServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = configuracao.isValid(context);

        if (retornoStr.length() > 1)
            configuracaoView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(configuracao, ConstraintUtils.EDITAR, new ConfiguracaoServiceImpl());
            }catch (Exception e){
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(configuracao, ConstraintUtils.FIND_BY_ID, new ConfiguracaoServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(configuracao, ConstraintUtils.FIND_ALL, new ConfiguracaoServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }


    }

    @Override
    public void setItem(IModel model) {
        this.configuracao = (Configuracao) model;
    }

    public void addTextWatcherPropriedadeConfiguracao(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                configuracao.setPropriedade(s.toString());
            }
        });
    }

    public void addTextWatcherValorConfiguracao(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                configuracao.setValor(s.toString());
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
                    configuracaoView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    configuracaoView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if(operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if(configuracoes!=null){
                    configuracoes.clear();
                    List<Configuracao> configuracoesTemp = (List<Configuracao>) serializable;
                    if(configuracoesTemp!=null) {
                        configuracoes.addAll(configuracoesTemp);
                    }
                }else {
                    configuracoes = (List<Configuracao>) serializable;
                    if(configuracoes==null){
                        configuracoes = new ArrayList<>();
                    }
                }

                if(configuracoesAdapter == null) {
                    configuracoesAdapter = new ConfiguracoesAdapter(context, configuracoes);

                    view.setAdapter(configuracoesAdapter);
                }else{
                    configuracoesAdapter.notifyDataSetChanged();
                }

                configuracoesAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            default:
        }

    }

}
