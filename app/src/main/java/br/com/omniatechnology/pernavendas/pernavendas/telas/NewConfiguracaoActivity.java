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
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewConfiguracaoActivity extends AppCompatActivity implements IModelView.IConfiguracaoView, View.OnClickListener {

    TextInputLayout inpPropriedadeConfiguracao;
    TextInputLayout inpValorConfiguracao;
    ImageButton btnSave;

    private Configuracao configuracao;

    IConfiguracaoPresenter configuracaoPresenter;

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

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.CONFIGURACAO_INTENT)){
            configuracao = (Configuracao) getIntent().getExtras().get(ConstraintUtils.CONFIGURACAO_INTENT);
            preencherDadosNaView();
        }

    }

    public void preencherDadosNaView(){

        inpPropriedadeConfiguracao.getEditText().setText(configuracao.getPropriedade());
        inpValorConfiguracao.getEditText().setText(configuracao.getValor());
        configuracaoPresenter.setItem(configuracao);

    }


    @Override
    public void onMessageSuccess(String message) {
        Toast.makeText(this, message ,Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_save:

                configuracaoPresenter.onCreate();

                break;

                default:
        }

    }

}
