package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.QrCodeUtil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;

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
    CheckBox chbIsSubProduto;
    TextInputLayout inpEanProduto;
    TextInputLayout inpEanPaiProduto;
    TextInputLayout inpQtdeSubProduto;
    private ImageView imgQrCode;

    private LinearLayout ll;

    private Produto produto;
    ImageButton btnSave;

    IProdutoPresenter produtoPresenter;

    private static final int SOLICITAR_PERMISSAO = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_produto_activity);

        inpNomeProduto = findViewById(R.id.inp_layout_nome_produto);
        inpDescricaoProduto = findViewById(R.id.inp_layout_descricao_produto);
        inpValorVendaProduto = findViewById(R.id.inp_layout_valor_venda_produto);
        inpQtdeProduto = findViewById(R.id.inp_layout_qtde_produto);
        inpQtdeMinProduto = findViewById(R.id.inp_layout_qtde_min_produto);
        inpEanProduto = findViewById(R.id.inp_layout_ean_produto);
        inpEanPaiProduto = findViewById(R.id.inp_layout_ean_pai_produto);
        inpQtdeSubProduto = findViewById(R.id.inp_layout_qtde_sub_produto);
        imgQrCode = findViewById(R.id.imgQrCode);

        ll = findViewById(R.id.llSubProduto);

        spnMarca = findViewById(R.id.spnMarca);
        spnCategoria = findViewById(R.id.spnCategoria);
        spnUnidadeDeMedida = findViewById(R.id.spnUnidadeDeMedida);
        chkIsInativo = findViewById(R.id.chkInativo);
        chbIsSubProduto= findViewById(R.id.chbIsSubProduto);
        btnSave = findViewById(R.id.btn_save);

        FloatingActionButton floatingActionButton = findViewById(R.id.fabGerarBarcode);
        floatingActionButton.setOnClickListener(this);

        produtoPresenter = new ProdutoPresenter(this, this);
        ((ProdutoPresenter) produtoPresenter).addTextWatcherNomeProduto(inpNomeProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherQtdeProduto(inpQtdeProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherQtdeMinProduto(inpQtdeMinProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherDescricaoProduto(inpDescricaoProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherValorVendaProduto(inpValorVendaProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherEanProduto(inpEanProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherEanPaiProduto(inpEanPaiProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).addTextWatcherQtdeSubProduto(inpQtdeSubProduto.getEditText());
        ((ProdutoPresenter) produtoPresenter).initializeSpinner(spnMarca,spnCategoria,spnUnidadeDeMedida);

        btnSave.setOnClickListener(this);
        chbIsSubProduto.setOnClickListener(this);

        //Todos os spnner serao inicializados dentro desse metodo
        produtoPresenter.setSpinnerMarca();



        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.PRODUTO_INTENT)) {

            produto = (Produto) getIntent().getExtras().get(ConstraintUtils.PRODUTO_INTENT);


            preencherViewsComDados();


        }


    }

    private void preencherViewsComDados() {

        inpNomeProduto.getEditText().setText(produto.getNome());
        inpDescricaoProduto.getEditText().setText(produto.getDescricao());
        inpValorVendaProduto.getEditText().setText(produto.getValorVenda().toString());
        inpQtdeProduto.getEditText().setText(produto.getQtde().toString());
        inpQtdeMinProduto.getEditText().setText(produto.getQtdeMinima().toString());
        inpEanProduto.getEditText().setText(produto.getEan());


        chkIsInativo.setChecked(!produto.isAtivo());

        chbIsSubProduto.setChecked(produto.getSubProduto());
        if(produto.getSubProduto()) {
            ll.setVisibility(View.VISIBLE);
            inpEanPaiProduto.getEditText().setText(produto.getEanPai());
            inpQtdeSubProduto.getEditText().setText(produto.getQtdeSubProduto().toString());

        }

        produtoPresenter.setItem(produto);

    }

    @Override
    public void onMessageSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_save:

                getDadosDeSpinner();
                ((ProdutoPresenter) produtoPresenter).getDadosForCheckboxAtivo(chkIsInativo);
                ((ProdutoPresenter) produtoPresenter).getDadosForCheckboxSubProduto(chbIsSubProduto);

                produtoPresenter.onCreate();

                break;

            case R.id.fabGerarBarcode:

                if(inpEanProduto.getEditText().getText().length()==0){
                    Toast.makeText(this, getString(R.string.preencher_codigo_produto),Toast.LENGTH_LONG).show();
                }else{
                  checarPermissao();
                }

                break;

            case R.id.chbIsSubProduto:

                if(chbIsSubProduto.isChecked()){
                    ll.setVisibility(View.VISIBLE);
                }else{
                    ll.setVisibility(View.GONE);
                }

                break;

            default:
        }

    }

    private void checarPermissao(){

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, SOLICITAR_PERMISSAO);
        } else {
            sharedImage();
        }
    }

    private void sharedImage(){
        Bitmap b = QrCodeUtil.gerarQRCode(inpEanProduto.getEditText().getText().toString());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.QrCode), null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, getString(R.string.Selecione)));
    }

    private void getDadosDeSpinner() {

        produtoPresenter.getDadoSpinnerCategoria(spnCategoria);
        produtoPresenter.getDadoSpinnerMarca(spnMarca);
        produtoPresenter.getDadoSpinnerUnidadeDeMedida(spnUnidadeDeMedida);

    }

    @Override
    public void onLoadeadEntitys() {

        for(int i = 0;i<spnMarca.getAdapter().getCount();i++){

            Marca marca = (Marca) spnMarca.getAdapter().getItem(i);

            if (produto.getMarca().getId().compareTo(marca.getId())==0) {
                spnMarca.setSelection(i);
                continue;
            }

        }

        for(int i = 0;i<spnCategoria.getAdapter().getCount();i++){

            Categoria categoria = (Categoria) spnCategoria.getAdapter().getItem(i);

            if (produto.getCategoria().getId().compareTo(categoria.getId())==0) {
                spnCategoria.setSelection(i);
                continue;
            }

        }

        for(int i = 0;i<spnUnidadeDeMedida.getAdapter().getCount();i++){

            UnidadeDeMedida unidadeDeMedida = (UnidadeDeMedida) spnUnidadeDeMedida.getAdapter().getItem(i);

            if (produto.getUnidadeDeMedida().getId().compareTo(unidadeDeMedida.getId())==0) {
                spnUnidadeDeMedida.setSelection(i);
                continue;
            }

        }


    }
}
