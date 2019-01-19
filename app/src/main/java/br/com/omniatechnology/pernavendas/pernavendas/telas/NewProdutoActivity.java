package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IProdutoView.INewProdutoView;

import static android.widget.Toast.LENGTH_LONG;

public class NewProdutoActivity extends AppCompatActivity implements INewProdutoView, View.OnClickListener {

    TextInputLayout inpNomeProduto;
    TextInputLayout inpQtdeProduto;
    ImageButton btnSave;

    IProdutoPresenter produtoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_produto_activity);

        inpNomeProduto = findViewById(R.id.inp_layout_nome_produto);
        inpQtdeProduto = findViewById(R.id.inp_layout_qtde_produto);
        btnSave = findViewById(R.id.btn_save);

        produtoPresenter = new ProdutoPresenter(this, this);
        ((ProdutoPresenter) produtoPresenter).addTextWatcherNomeProduto(inpNomeProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherQtdeProduto(inpQtdeProduto.getEditText());

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

                ProgressDialog progressDialog  =new ProgressDialog(this);
                progressDialog.setMessage("Processando");
                progressDialog.show();

                produtoPresenter.onCreate();

                progressDialog.dismiss();

                break;

                default:
        }

    }
}
