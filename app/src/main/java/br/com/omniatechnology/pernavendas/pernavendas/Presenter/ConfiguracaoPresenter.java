package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ConfiguracoesAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ConfiguracaoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class ConfiguracaoPresenter implements IConfiguracaoPresenter {

    IModelView.IConfiguracaoView configuracaoView;
    private Context context;
    Configuracao configuracao;
    private List<Configuracao> configuracoes = new ArrayList<>();
    private ConfiguracoesAdapter configuracoesAdapter;
    
    private ListView view;
    private TextView txtEmpty;

    public ConfiguracaoPresenter() {
        configuracao = new Configuracao();
    }


    public ConfiguracaoPresenter(IModelView.IConfiguracaoView configuracaoView, Context context) {
        this();
        this.configuracaoView = configuracaoView;
        this.context = context;
    }

    public void atualizarList(ListView view, TextView txtEmpty){

        this.view = view;
        this.txtEmpty = txtEmpty;

        if(configuracoesAdapter==null){
            findAll();

        }else{

            configuracoesAdapter.notifyDataSetChanged();

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
    public void onCreate() {

        String retornoStr = configuracao.isValid(context);


        if (retornoStr.length() == 0) {

            new ConfiguracaoServiceImpl().save(configuracao)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Configuracao>() {
                        @Override
                        public void onCompleted() {
                            configuracaoView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            configuracaoView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Configuracao configuracao) {

                        }
                    });


        } else {
            configuracaoView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new ConfiguracaoServiceImpl().delete(id)
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        findAll();
                    }
                })
                .doOnUnsubscribe(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        configuracaoView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        configuracaoView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean isSave) {

                    }
                });


    }


    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

        new ConfiguracaoServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Configuracao>>() {
                    @Override
                    public void onCompleted() {

                        if (configuracoes == null) {
                            configuracoesAdapter = new ConfiguracoesAdapter(context, configuracoes);

                            view.setAdapter(configuracoesAdapter);
                        } else {
                            configuracoesAdapter.notifyDataSetChanged();
                        }

                        if (configuracoes.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        configuracoesAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Configuracao> configuracoesTemp) {
                        if (configuracoes != null) {
                            configuracoes.clear();
                            configuracoes.addAll(configuracoesTemp);
                        }
                    }
                });


    }

}
