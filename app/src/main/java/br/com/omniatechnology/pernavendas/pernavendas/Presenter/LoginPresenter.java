package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.View.ILoginView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class LoginPresenter implements ILoginPresenter {

    ILoginView loginView;
    Usuario usuario;
    Context context;

    private GenericDAO genericDAO;


    public LoginPresenter(){
        genericDAO  = new GenericDAO();
    }

    public LoginPresenter(ILoginView loginView) {
        this();
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

        if(retornoStr.length()==0) {
            try {
                usuario = (Usuario) genericDAO.execute(usuario, ConstraintUtils.LOGIN, new UsuarioServiceImpl()).get();

                if(usuario==null){
                    loginView.OnLoginResultError();
                }else{
                    loginView.OnLoginResultSuccess();
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
            loginView.OnLoginResultError();

    }
}
