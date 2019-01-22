package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IUsuarioPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.UsuarioPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

import static android.widget.Toast.LENGTH_LONG;

public class NewUsuarioActivity extends AppCompatActivity implements IModelView.IUsuarioView, View.OnClickListener {

    TextInputLayout inpUsuario;
    TextInputLayout inpSenha;
    TextInputLayout inpConfirmarSenha;
    Spinner spnPerfil;
    ImageButton btnSave;

    IUsuarioPresenter usuarioPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_usuario_activity);

        inpUsuario = findViewById(R.id.inp_layout_usuario_usuario);
        inpSenha = findViewById(R.id.inp_layout_senha_usuario);
        inpConfirmarSenha = findViewById(R.id.inp_layout__confirmar_senha_usuario);
        spnPerfil = findViewById(R.id.spnPerfil);
        btnSave = findViewById(R.id.btn_save);

        usuarioPresenter = new UsuarioPresenter(this, this);
        ((UsuarioPresenter) usuarioPresenter).addTextWatcherSenha(inpSenha.getEditText());
        ((UsuarioPresenter) usuarioPresenter).addTextWatcherUsuario(inpUsuario.getEditText());
        ((UsuarioPresenter) usuarioPresenter).addTextWatcherConfirmarSenha(inpConfirmarSenha.getEditText());

        btnSave.setOnClickListener(this);

        usuarioPresenter.setSpinnerPerfil(spnPerfil);

    }

    @Override
    public void onMessageSuccess(String message) {
        Toast.makeText(this, message ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }

    @Override
    public List<IModel> findAllSuccess() {
        return null;
    }

    @Override
    public List<IModel> findAllError(String message) {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_save:

                showProgressDialog(getResources().getString(R.string.processando));

                getDadosDeSpinner();

                usuarioPresenter.onCreate();
                progressDialog.dismiss();

                break;

                default:
        }

    }

    private void getDadosDeSpinner(){

        usuarioPresenter.getDadosSpinnerPerfil(spnPerfil);

    }

    private void showProgressDialog(String message){
        progressDialog  =new ProgressDialog(this);

        progressDialog.setMessage(message);
        progressDialog.setTitle(getString(R.string.aguarde));
        progressDialog.show();

    }

}
