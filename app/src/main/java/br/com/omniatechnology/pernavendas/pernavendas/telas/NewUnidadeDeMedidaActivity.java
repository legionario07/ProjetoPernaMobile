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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IUnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.UnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewUnidadeDeMedidaActivity extends AppCompatActivity implements IModelView.IUnidadeDeMedidaView, View.OnClickListener {

    TextInputLayout inpTipoUnidadeDeMedida;
    ImageButton btnSave;

    IUnidadeDeMedidaPresenter unidadeDeMedidaPresenter;
    private UnidadeDeMedida unidadeDeMedida;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_unidade_de_medida_activity);

        inpTipoUnidadeDeMedida = findViewById(R.id.inp_layout_tipo_unidade_de_medida);
        btnSave = findViewById(R.id.btn_save);

        unidadeDeMedidaPresenter = new UnidadeDeMedidaPresenter(this, this);
        ((UnidadeDeMedidaPresenter) unidadeDeMedidaPresenter).addTextWatcherTipoUnidadeDeMedida(inpTipoUnidadeDeMedida.getEditText());

        btnSave.setOnClickListener(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.UNIDADE_DE_MEDIDA_INTENT)){
            unidadeDeMedida = (UnidadeDeMedida) getIntent().getExtras().get(ConstraintUtils.UNIDADE_DE_MEDIDA_INTENT);
            preencherDadosNaView();
        }

    }

    public void preencherDadosNaView(){

        inpTipoUnidadeDeMedida.getEditText().setText(unidadeDeMedida.getTipo());

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

                unidadeDeMedidaPresenter.onCreate();

                break;

                default:
        }

    }

}
