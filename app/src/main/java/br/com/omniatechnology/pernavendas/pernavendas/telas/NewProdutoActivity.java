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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

import static android.widget.Toast.LENGTH_LONG;

public class NewProdutoActivity extends AppCompatActivity implements IModelView.IProdutoView, View.OnClickListener {

    TextInputLayout inpNomeProduto;
    TextInputLayout inpDescricaoProduto;
    TextInputLayout inpValorVendaProduto;
    TextInputLayout inpQtdeProduto;
   protected TextInputLayout inpQtdeMinProduto;
    Spinner spnMarca;
    Spinner spnUnidadeDeMedida;
    Spinner spnCategoria;
    CheckBox chkIsInativo;
    TextInputLayout inpEanProduto;
    ImageButton btnSave;

    IProdutoPresenter produtoPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_produto_activity);

        inpNomeProduto = findViewById(R.id.inp_layout_nome_produto);
        inpDescricaoProduto = findViewById(R.id.inp_layout_descricao_produto);
        inpValorVendaProduto = findViewById(R.id.inp_layout_valor_venda_produto);
        inpQtdeProduto = findViewById(R.id.inp_layout_qtde_produto);
        inpQtdeMinProduto = findViewById(R.id.inp_layout_qtde_min_produto);
        spnMarca = findViewById(R.id.spnMarca);
        spnCategoria = findViewById(R.id.spnCategoria);
        spnUnidadeDeMedida = findViewById(R.id.spnUnidadeDeMedida);
        chkIsInativo = findViewById(R.id.chkInativo);
        btnSave = findViewById(R.id.btn_save);

        produtoPresenter = new ProdutoPresenter(this, this);
        ((ProdutoPresenter) produtoPresenter).addTextWatcherNomeProduto(inpNomeProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherQtdeProduto(inpQtdeProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherQtdeMinProduto(inpQtdeMinProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherDescricaoProduto(inpDescricaoProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherValorVendaProduto(inpValorVendaProduto.getEditText());

        btnSave.setOnClickListener(this);

        produtoPresenter.setSpinnerCategoria(spnCategoria);
        produtoPresenter.setSpinnerMarca(spnMarca);
        produtoPresenter.setSpinnerUnidadeDeMedida(spnUnidadeDeMedida);

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

                produtoPresenter.onCreate();
                progressDialog.dismiss();

                break;

                default:
        }

    }

    private void getDadosDeSpinner(){

        produtoPresenter.getDadoSpinnerCategoria(spnCategoria);
        produtoPresenter.getDadoSpinnerMarca(spnMarca);
        produtoPresenter.getDadoSpinnerUnidadeDeMedida(spnUnidadeDeMedida);

    }

    private void showProgressDialog(String message){
        progressDialog  =new ProgressDialog(this);

        progressDialog.setMessage(message);
        progressDialog.setTitle(getString(R.string.aguarde));
        progressDialog.show();

    }

}
