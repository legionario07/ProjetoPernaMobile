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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ConfiguracaoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IConfiguracaoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

import static android.widget.Toast.LENGTH_LONG;

public class NewConfiguracaoActivity extends AppCompatActivity implements IModelView.IConfiguracaoView, View.OnClickListener {

    TextInputLayout inpPropriedadeConfiguracao;
    TextInputLayout inpValorConfiguracao;
    ImageButton btnSave;

    IConfiguracaoPresenter configuracaoPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_configuracao_activity);

        inpPropriedadeConfiguracao = findViewById(R.id.inp_layout_propriedade);
        inpValorConfiguracao = findViewById(R.id.inp_layout_valor);
        btnSave = findViewById(R.id.btn_save);

        configuracaoPresenter = new ConfiguracaoPresenter(this, this);
        ((ConfiguracaoPresenter) configuracaoPresenter).addTextWatcherPropriedadeConfiguracao(inpPropriedadeConfiguracao.getEditText());
        ((ConfiguracaoPresenter) configuracaoPresenter).addTextWatcherValorConfiguracao(inpValorConfiguracao.getEditText());

        btnSave.setOnClickListener(this);

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
                configuracaoPresenter.onCreate();
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
