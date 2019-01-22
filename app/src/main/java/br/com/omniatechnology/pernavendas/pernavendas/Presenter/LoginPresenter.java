package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.omniatechnology.pernavendas.pernavendas.View.ILoginView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;

public class LoginPresenter implements ILoginPresenter {

    ILoginView loginView;
    Usuario usuario;
    Context context;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        usuario = new Usuario();
    }


    public LoginPresenter(ILoginView loginView,Context context) {
        this(loginView);
        this.context = context;
    }

    public void addUsuarioTextWatcher(EditText view){
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
    }

    public void addSenhaTextWatcher(EditText view){
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
    }


    @Override
    public void onLogin() {


        String retornoStr = usuario.isValid(context);

        if(retornoStr.length()==0)
            loginView.OnLoginResultSuccess();
        else
            loginView.OnLoginResultError();

    }
}
