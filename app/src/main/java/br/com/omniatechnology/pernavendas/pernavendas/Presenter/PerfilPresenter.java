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
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.adapter.UsuariosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PerfisAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PerfilServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class PerfilPresenter implements IPerfilPresenter{

    IModelView.IPerfilView perfilView;
    private Context context;
    Perfil perfil;
    private List<Perfil> perfis = new ArrayList<>();
    private PerfisAdapter perfilsAdapter;
    private ListView view;
    private TextView txtEmpty;


    public PerfilPresenter() {
        perfil = new Perfil();

    }


    public PerfilPresenter(IModelView.IPerfilView perfilView, Context context) {
        this();
        this.perfilView = perfilView;
        this.context = context;
    }

    public void atualizarList(ListView view, TextView txtEmpty) {

        this.view = view;
        this.txtEmpty = txtEmpty;

        if (perfilsAdapter == null) {
            findAll();
        } else {

            perfilsAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onCreate() {

        String retornoStr = perfil.isValid(context);


        if (retornoStr.length() == 0) {

            new PerfilServiceImpl().save(perfil)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Perfil>() {
                        @Override
                        public void onCompleted() {
                            perfilView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            perfilView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Perfil perfil) {

                        }
                    });


        } else {
            perfilView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new PerfilServiceImpl().delete(id)
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
                        perfilView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        perfilView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
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

        new PerfilServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Perfil>>() {
                    @Override
                    public void onCompleted() {

                        if (perfilsAdapter == null) {
                            perfilsAdapter = new PerfisAdapter(context, perfis);

                            view.setAdapter(perfilsAdapter);
                        } else {
                            perfilsAdapter.notifyDataSetChanged();
                        }

                        if (perfis.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        perfilsAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Perfil> perfilsTemp) {
                        if (perfis != null) {
                            perfis.clear();
                            perfis.addAll(perfilsTemp);
                        }
                    }
                });


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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
            }
        });
    }


}
