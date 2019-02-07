package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.ILoginView;
import br.com.omniatechnology.pernavendas.pernavendas.api.RetrofitConfig;
import br.com.omniatechnology.pernavendas.pernavendas.api.UsuarioService;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginPresenter implements ILoginPresenter, ITaskProcess {

    ILoginView loginView;
    Usuario usuario;
    Context context;
    private ProgressDialog progressDialog;


    public LoginPresenter() {

    }

    public LoginPresenter(ILoginView loginView) {
        this();
        this.loginView = loginView;
        usuario = new Usuario();
    }


    public LoginPresenter(ILoginView loginView, Context context) {
        this(loginView);
        this.context = context;
    }

    public void addUsuarioTextWatcher(final EditText view) {
        view.addTextChangedListener(new TextWatcher() {
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

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, view);
                }
            }
        });
    }

    public void addSenhaTextWatcher(final EditText view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(ConstraintUtils.TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(ConstraintUtils.TAG, "beforeTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                usuario.setSenha(s.toString());
            }
        });

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, view);
                }
            }
        });
    }


    public void onLogin(){

        String retornoStr = usuario.isValid(context);

        if (retornoStr.length() == 0) {

            new UsuarioServiceImpl().login(usuario)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Usuario>() {
                @Override
                public void onCompleted() {
                    loginView.OnLoginResultSuccess(context.getString(R.string.login_sucess));
                }

                @Override
                public void onError(Throwable e) {
                    loginView.OnLoginResultError(context.getString(R.string.login_error )+ " - "+e.getMessage());
                }

                @Override
                public void onNext(Usuario usuario) {
                    SessionUtil.getInstance().setUsuario(usuario);
                }
            });


        }else{
            loginView.OnLoginResultError(retornoStr);
        }



    }



    @Override
    public void onPostProcess(Serializable serializable) {

    }

//    @Override
//    public void onLogin() {
//
//
//        String retornoStr = usuario.isValid(context);
//
//        if (retornoStr.length() == 0) {
//            try {
//                new GenericDAO(context, this).execute(usuario, ConstraintUtils.LOGIN, new UsuarioServiceImpl());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            loginView.OnLoginResultError();
//
//            usuario = new Usuario();
//        }
//    }



}
