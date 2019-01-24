package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.PerfilPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IPerfilPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewPerfilActivity extends AppCompatActivity implements IModelView.IPerfilView, View.OnClickListener {

    TextInputLayout inpNomePerfil;
    ImageButton btnSave;

    IPerfilPresenter perfilPresenter;
    private Perfil perfil;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_perfil_activity);

        inpNomePerfil = findViewById(R.id.inp_layout_tipo_perfil);
        btnSave = findViewById(R.id.btn_save);

        perfilPresenter = new PerfilPresenter(this, this);
        ((PerfilPresenter) perfilPresenter).addTextWatcherNomePerfil(inpNomePerfil.getEditText());

        btnSave.setOnClickListener(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.PERFIL_INTENT)){
            perfil = (Perfil) getIntent().getExtras().get(ConstraintUtils.PERFIL_INTENT);
            preencherDadosNaView();
        }

    }

    public void preencherDadosNaView(){

        inpNomePerfil.getEditText().setText(perfil.getNome());

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
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_save:

                showProgressDialog(getResources().getString(R.string.processando));

                perfilPresenter.onCreate();
                progressDialog.dismiss();

                break;

                default:
        }

    }

    private void showProgressDialog(String message){
        progressDialog  =new ProgressDialog(this);

        progressDialog.setMessage(message);
        progressDialog.setTitle(getString(R.string.aguarde));
        progressDialog.show();

    }

}
