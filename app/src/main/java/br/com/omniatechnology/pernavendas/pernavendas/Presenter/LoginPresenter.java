package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.View.ILoginView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class LoginPresenter implements ILoginPresenter, ITaskProcess {

    ILoginView loginView;
    Usuario usuario;
    Context context;

    private GenericDAO genericDAO;


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
        genericDAO = new GenericDAO(context, this);
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

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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


    @Override
    public void onLogin() {


        String retornoStr = usuario.isValid(context);

        if (retornoStr.length() == 0) {
            try {
                genericDAO.execute(usuario, ConstraintUtils.LOGIN, new UsuarioServiceImpl());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            loginView.OnLoginResultError();

    }


    @Override
    public void onPostProcess(Serializable serializable) {
        Log.i("LOG", "postProcess: ");
        usuario = (Usuario) serializable;

        if (usuario != null) {
            loginView.OnLoginResultError();
        } else {
            loginView.OnLoginResultSuccess();
        }
    }
}
