package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UsuariosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PerfilServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class UsuarioPresenter implements IUsuarioPresenter {

    IModelView.IUsuarioView usuarioView;
    private Context context;
    Usuario usuario;
    private Boolean isSave;
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Perfil> perfis = new ArrayList<>();
    private String confirmarSenhaStr;
    private UsuariosAdapter usuariosAdapter;

    private ListView view;
    private Spinner spnPerfil;

    private TextView txtEmpty;

    public UsuarioPresenter() {
        usuario = new Usuario();
    }

    public UsuarioPresenter(IModelView.IUsuarioView usuarioView, Context context) {
        this();
        this.usuarioView = usuarioView;
        this.context = context;
    }

    public void setSpinnerPerfil(Spinner spinner) {

        this.spnPerfil = spinner;

        new PerfilServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        usuarioView.onLoadedEntitys();
                    }
                })
                .doOnUnsubscribe(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Perfil>>() {
                    @Override
                    public void onCompleted() {

                        ArrayAdapter arrayPerfis = new ArrayAdapter(context, android.R.layout.simple_spinner_item, perfis);
                        arrayPerfis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnPerfil.setAdapter(arrayPerfis);


                    }

                    @Override
                    public void onError(Throwable e) {
                        usuarioView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Perfil> perfisTemp) {
                        perfis.addAll(perfisTemp);
                    }
                });

    }

    public void getDadosSpinnerPerfil(Spinner spinner) {
        usuario.setPerfil((Perfil) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner) {
        return (IModel) spinner.getSelectedItem();
    }

    public void atualizarList(ListView view, TextView txtEmpty) {

        this.view = view;
        this.txtEmpty = txtEmpty;

        if (usuariosAdapter == null) {
            findAll();

        } else {

            usuariosAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        String retornoStr = usuario.isValid(context);

        if (!usuario.getSenha().equals(confirmarSenhaStr)) {
            retornoStr = context.getString(R.string.confirmacao_senha);
        }

        if (retornoStr.length() == 0) {

            new UsuarioServiceImpl().save(usuario)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Usuario>() {
                        @Override
                        public void onCompleted() {
                            usuarioView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            usuarioView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Usuario usuario) {

                        }
                    });


        } else {
            usuarioView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new UsuarioServiceImpl().delete(id)
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
                        usuarioView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        usuarioView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
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

        new UsuarioServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Usuario>>() {
                    @Override
                    public void onCompleted() {

                       if (usuariosAdapter == null) {
                            usuariosAdapter = new UsuariosAdapter(context, usuarios);

                            view.setAdapter(usuariosAdapter);
                        } else {
                            usuariosAdapter.notifyDataSetChanged();
                        }

                        if (usuarios.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        usuariosAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Usuario> usuariosTemp) {
                        if (usuarios != null) {
                            usuarios.clear();
                            usuarios.addAll(usuariosTemp);
                        }
                    }
                });


    }

    @Override
    public void setItem(IModel model) {
        this.usuario = (Usuario) model;
    }

    public void addTextWatcherUsuario(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usuario.setUsuario(s.toString());
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

    public void addTextWatcherSenha(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usuario.setSenha(s.toString());
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

    public void addTextWatcherConfirmarSenha(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmarSenhaStr = s.toString();
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



