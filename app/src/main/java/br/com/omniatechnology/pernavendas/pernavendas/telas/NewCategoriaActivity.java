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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ICategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.CategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewCategoriaActivity extends AppCompatActivity implements IModelView.ICategoriaView, View.OnClickListener {

    TextInputLayout inpNomeCategoria;
    ImageButton btnSave;

    ICategoriaPresenter categoriaPresenter;

    private Categoria categoria;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_categoria_activity);

        inpNomeCategoria = findViewById(R.id.inp_layout_tipo_categoria);
        btnSave = findViewById(R.id.btn_save);

        categoriaPresenter = new CategoriaPresenter(this, this);
        ((CategoriaPresenter) categoriaPresenter).addTextWatcherNomeCategoria(inpNomeCategoria.getEditText());

        btnSave.setOnClickListener(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.CATEGORIA_INTENT)){
            categoria = (Categoria) getIntent().getExtras().get(ConstraintUtils.CATEGORIA_INTENT);
            preencherDadosNaView();
        }

    }

    public void preencherDadosNaView(){

        inpNomeCategoria.getEditText().setText(categoria.getNome());

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

                categoriaPresenter.onCreate();
                progressDialog.dismiss();

                onBackPressed();

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
