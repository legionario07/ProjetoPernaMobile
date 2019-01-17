package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.ILoginView;
import br.com.omniatechnology.pernavendas.pernavendas.api.Presenter.ILoginPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.api.Presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    EditText inp_usuario;
    EditText inp_senha;
    Button btn_login;

    ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        inp_usuario = findViewById(R.id.inp_usuario);
        inp_senha = findViewById(R.id.inp_senha);
        btn_login = findViewById(R.id.btn_login);

        loginPresenter = new LoginPresenter(this);
        ((LoginPresenter) loginPresenter).addSenhaTextWatcher(inp_senha);
        ((LoginPresenter) loginPresenter).addUsuarioTextWatcher(inp_usuario);

        btn_login.setOnClickListener(this);

    }

    @Override
    public void OnLoginResultSucess() {
        Toast.makeText(this, getResources().getString(R.string.login_sucess),Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnLoginResultError() {
        Toast.makeText(this, getResources().getString(R.string.login_error),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                loginPresenter.onLogin();
                break;

                default:
        }
    }
}
