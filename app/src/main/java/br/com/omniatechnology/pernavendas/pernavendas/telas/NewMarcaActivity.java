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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.MarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IMarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewMarcaActivity extends AppCompatActivity implements IModelView.IMarcaView, View.OnClickListener {

    TextInputLayout inpNomeMarca;
    ImageButton btnSave;

    IMarcaPresenter marcaPresenter;

    private ProgressDialog progressDialog;
    private Marca marca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_marca_activity);

        inpNomeMarca = findViewById(R.id.inp_layout_tipo_marca);
        btnSave = findViewById(R.id.btn_save);

        marcaPresenter = new MarcaPresenter(this, this);
        ((MarcaPresenter) marcaPresenter).addTextWatcherNomeMarca(inpNomeMarca.getEditText());

        btnSave.setOnClickListener(this);

        if(getIntent().getExtras().containsKey(ConstraintUtils.MARCA_INTENT)){
            marca = (Marca) getIntent().getExtras().get(ConstraintUtils.MARCA_INTENT);
            preencherDadosNaView();
        }

    }

    public void preencherDadosNaView(){

        inpNomeMarca.getEditText().setText(marca.getNome());

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

                marcaPresenter.onCreate();
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
