package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CategoriasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ConfiguracoesAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ConfiguracaoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class ConfiguracaoPresenter implements IConfiguracaoPresenter {

    IModelView.IConfiguracaoView configuracaoView;
    private Context context;
    Configuracao configuracao;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<Configuracao> configuracoes;
    private ConfiguracoesAdapter configuracoesAdapter;

    public ConfiguracaoPresenter() {
        configuracao = new Configuracao();
        genericDAO = new GenericDAO();
    }

    public ConfiguracaoPresenter(IModelView.IConfiguracaoView configuracaoView) {
        this.configuracaoView = configuracaoView;
    }

    public ConfiguracaoPresenter(IModelView.IConfiguracaoView configuracaoView, Context context) {
        this();
        this.configuracaoView = configuracaoView;
        this.context = context;
    }


    @Override
    public void onCreate() {

        String retornoStr = configuracao.isValid(context);

        if (retornoStr.length() > 1)
            configuracaoView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(configuracao, ConstraintUtils.SALVAR, new ConfiguracaoServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                configuracaoView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                configuracaoView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete(Long id) {

        try {
            isSave = (Boolean) genericDAO.execute(id, ConstraintUtils.DELETAR, new ConfiguracaoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            configuracaoView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            configuracaoView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        String retornoStr = configuracao.isValid(context);

        if (retornoStr.length() > 1)
            configuracaoView.onMessageError(retornoStr);
        else {


            try {
                isSave = (Boolean) genericDAO.execute(configuracao, ConstraintUtils.EDITAR, new ConfiguracaoServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (isSave)
                configuracaoView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
            else
                configuracaoView.onMessageError(context.getResources().getString(R.string.error_operacao));
        }

    }

    @Override
    public void findById() {

        try {
            configuracao = (Configuracao) genericDAO.execute(configuracao, ConstraintUtils.FIND_BY_ID, new ConfiguracaoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findAll() {

        try {
            configuracoes = (List<Configuracao>) genericDAO.execute(configuracao, ConstraintUtils.FIND_ALL, new ConfiguracaoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void atualizarList(ListView view){


        if(configuracoesAdapter==null){
            configuracoes = (List<Configuracao>) genericDAO.execute(new Configuracao(), ConstraintUtils.FIND_ALL, new ConfiguracaoServiceImpl());

            configuracoesAdapter = new ConfiguracoesAdapter(context, configuracoes);

            view.setAdapter(configuracoesAdapter);

        }else{

            configuracoesAdapter.notifyDataSetChanged();

        }

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
    }


}
