package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
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


    Button btn_login;

    TextInputLayout usuarioWrapper;
    TextInputLayout senhaWrapper;

    ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btn_login = findViewById(R.id.btn_login);

        usuarioWrapper = findViewById(R.id.usuarioWrapper);
        senhaWrapper = findViewById(R.id.senhaWrapper);

        loginPresenter = new LoginPresenter(this);
        ((LoginPresenter) loginPresenter).addSenhaTextWatcher(usuarioWrapper.getEditText());
        ((LoginPresenter) loginPresenter).addUsuarioTextWatcher(senhaWrapper.getEditText());

        btn_login.setOnClickListener(this);

    }

    @Override
    public void OnLoginResultSucess() {
        Toast.makeText(this, getResources().getString(R.string.login_sucess),Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, HomeActivity.class));

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
