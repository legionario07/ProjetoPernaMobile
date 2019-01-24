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
    TextInputLayout inpEanProduto;
    private ImageView imgQrCode;

    private Produto produto;
    ImageButton btnSave;

    IProdutoPresenter produtoPresenter;

    private ProgressDialog progressDialog;
    private static final int SOLICITAR_PERMISSAO = 1;


    private List<Marca> marcas;
    private List<UnidadeDeMedida> unidadesDeMedidas;
    private List<Categoria> categorias;

    private ArrayAdapter<Marca> adapterMarcas;
    private ArrayAdapter<UnidadeDeMedida> adapterUnidades;
    private ArrayAdapter<Categoria> adapterCategorias;

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
        imgQrCode = findViewById(R.id.imgQrCode);

        spnMarca = findViewById(R.id.spnMarca);
        spnCategoria = findViewById(R.id.spnCategoria);
        spnUnidadeDeMedida = findViewById(R.id.spnUnidadeDeMedida);
        chkIsInativo = findViewById(R.id.chkInativo);
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

        btnSave.setOnClickListener(this);

//        produtoPresenter.setSpinnerCategoria(spnCategoria);
//        produtoPresenter.setSpinnerMarca(spnMarca);
//        produtoPresenter.setSpinnerUnidadeDeMedida(spnUnidadeDeMedida);

        categorias = SessionUtil.getInstance().getCategorias();
        marcas = SessionUtil.getInstance().getMarcas();
        unidadesDeMedidas = SessionUtil.getInstance().getUnidadeDeMedidas();

        adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapterCategorias);

        adapterMarcas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, marcas);
        adapterMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMarca.setAdapter(adapterMarcas);

        adapterUnidades = new ArrayAdapter(this, android.R.layout.simple_spinner_item, unidadesDeMedidas);
        adapterUnidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUnidadeDeMedida.setAdapter(adapterUnidades);


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.PRODUTO_INTENT)) {

            produto = (Produto) getIntent().getExtras().get(ConstraintUtils.PRODUTO_INTENT);

            for(int i = 0;i<marcas.size();i++){

                if (produto.getMarca().getId() == marcas.get(i).getId()) {
                    spnMarca.setSelection(i);
                }

            }

            for(int i = 0;i<categorias.size();i++){

                if (produto.getCategoria().getId() == categorias.get(i).getId()) {
                    spnCategoria.setSelection(i);
                }

            }

            for(int i = 0;i<unidadesDeMedidas.size();i++){

                if (produto.getUnidadeDeMedida().getId() == unidadesDeMedidas.get(i).getId()) {
                    spnUnidadeDeMedida.setSelection(i);
                }

            }

            preencherViewsComDados();


        }


    }

    private void preencherViewsComDados() {

        inpNomeProduto.getEditText().setText(produto.getNome());
        inpDescricaoProduto.getEditText().setText(produto.getDescricao());
        inpValorVendaProduto.getEditText().setText(produto.getValorVenda().toString());
        inpQtdeProduto.getEditText().setText(produto.getQtde().toString());
        inpQtdeMinProduto.getEditText().setText(produto.getQtdeMinima().toString());
        chkIsInativo.setChecked(!produto.isAtivo());

    }

    @Override
    public void onMessageSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_save:

                showProgressDialog(getResources().getString(R.string.processando));

                getDadosDeSpinner();

                produtoPresenter.onCreate();
                progressDialog.dismiss();

                break;

            case R.id.fabGerarBarcode:

                if(inpEanProduto.getEditText().getText().length()==0){
                    Toast.makeText(this, getString(R.string.preencher_codigo_produto),Toast.LENGTH_LONG).show();
                }else{
                  checarPermissao();
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

    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage(message);
        progressDialog.setTitle(getString(R.string.aguarde));
        progressDialog.show();

    }

}
