package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IUnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.UnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;

import static android.widget.Toast.LENGTH_LONG;

public class NewUnidadeDeMedidaActivity extends AppCompatActivity implements IModelView.IUnidadeDeMedidaView, View.OnClickListener {

    TextInputLayout inpTipoUnidadeDeMedida;
    ImageButton btnSave;

    IUnidadeDeMedidaPresenter unidadeDeMedidaPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_unidade_de_medida_activity);

        inpTipoUnidadeDeMedida = findViewById(R.id.inp_layout_tipo_unidade_de_medida);
        btnSave = findViewById(R.id.btn_save);

        unidadeDeMedidaPresenter = new UnidadeDeMedidaPresenter(this, this);
        ((UnidadeDeMedidaPresenter) unidadeDeMedidaPresenter).addTextWatcherTipoUnidadeDeMedida(inpTipoUnidadeDeMedida.getEditText());

        btnSave.setOnClickListener(this);

    }

    @Override
    public void onCreateSuccess() {
        Toast.makeText(this, getResources().getString(R.string.save_success) ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_save:

                showProgressDialog(getResources().getString(R.string.processando));

                unidadeDeMedidaPresenter.onCreate();

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
