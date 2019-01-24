package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.ILoginView;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ILoginPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.LoginPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {


    Button btn_login;
    CheckBox chkSalvarSenha;
    private Boolean isChecked;

    TextInputLayout usuarioWrapper;
    TextInputLayout senhaWrapper;

    ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btn_login = findViewById(R.id.btn_login);
        chkSalvarSenha = findViewById(R.id.chkSalvarSenha);

        usuarioWrapper = findViewById(R.id.usuarioWrapper);
        senhaWrapper = findViewById(R.id.senhaWrapper);

        verificarDadosSalvo();

        loginPresenter = new LoginPresenter(this, this);
        ((LoginPresenter) loginPresenter).addSenhaTextWatcher(usuarioWrapper.getEditText());
        ((LoginPresenter) loginPresenter).addUsuarioTextWatcher(senhaWrapper.getEditText());

        btn_login.setOnClickListener(this);

    }

    private void verificarDadosSalvo(){

        SharedPreferences prefs = getSharedPreferences(ConstraintUtils.PREFERENCES, MODE_PRIVATE);
        String login = prefs.getString("login", null);
        String senha = prefs.getString("senha", null);
        isChecked = prefs.getBoolean("checked", false);

        if (login != null && isChecked) {
            usuarioWrapper.getEditText().setText(login);
            senhaWrapper.getEditText().setText(senha);
            chkSalvarSenha.setChecked(isChecked);
        }

    }


    private void atualizarDadosSalvo(){

        SharedPreferences.Editor editor = getSharedPreferences(ConstraintUtils.PREFERENCES, MODE_PRIVATE).edit();
        editor.putString("login", usuarioWrapper.getEditText().getText().toString());
        editor.putString("senha", senhaWrapper.getEditText().getText().toString());
        editor.putBoolean("checked", chkSalvarSenha.isChecked());
        editor.commit();

    }

    @Override
    public void OnLoginResultSuccess() {
        atualizarDadosSalvo();
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
