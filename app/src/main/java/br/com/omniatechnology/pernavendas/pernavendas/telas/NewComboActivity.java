package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosCombosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.QrCodeUtil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewComboActivity extends AppCompatActivity implements IModelView.IComboView, View.OnClickListener {

    ImageButton btnSave;
    private AutoCompleteTextView inpProduto;
    private ListView lstProdutos;
    private EditText inpPrecoVenda;
    private EditText inpNomeCombo;
    private EditText inpEanCombo;
    private EditText inpDescricaoCombo;
    private EditText inpQtdeCombo;

    private ImageButton imgLerQrCode;
    private ProdutosCombosAdapter combosAdapter;

    Spinner spnCategoria;
    Spinner spnUnidadeDeMedida;

    private List<Produto> produtos;

    private Produto produto;


    IComboPresenter comboPresenter;

    private Combo combo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_combo_activity);

        btnSave = findViewById(R.id.btn_save);

        lstProdutos = findViewById(R.id.lstProdutos);
        inpProduto = findViewById(R.id.inp_produto);
        imgLerQrCode = findViewById(R.id.imgLerQrCode);
        inpPrecoVenda = findViewById(R.id.inpPrecoVenda);
        inpNomeCombo = findViewById(R.id.inpNomeCombo);
        inpEanCombo = findViewById(R.id.inpEanCombo);
        inpDescricaoCombo = findViewById(R.id.inpDescricaoCombo);
        inpQtdeCombo = findViewById(R.id.inpQtdeCombo);

        spnCategoria = findViewById(R.id.spnCategoriaCombo);
        spnUnidadeDeMedida = findViewById(R.id.spnUnidadeDeMedidaCombo);

        comboPresenter = new ComboPresenter(this, this);
        comboPresenter.initializeSpinner(spnCategoria, spnUnidadeDeMedida);
        comboPresenter.initializeSpinnersWithData();

        comboPresenter.addTextWatcherNomeCombo(inpNomeCombo);
        comboPresenter.addTextWatcherPrecoVenda(inpPrecoVenda);
        comboPresenter.addTextWatcherEanCombo(inpEanCombo);
        comboPresenter.addTextWatcherDescricaoCombo(inpDescricaoCombo);
        comboPresenter.addTextWatcherQtdeCombo(inpQtdeCombo);
        comboPresenter.atualizarProdutos(inpProduto);

        btnSave.setOnClickListener(this);
        imgLerQrCode.setOnClickListener(this);

        inpProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Produto) inpProduto.getAdapter().getItem(position);
                produtos.add(produto);
                atualizarListDeProdutos();
                inpProduto.setText("");
                ViewUtils.hideKeyboard(getApplicationContext(), inpProduto, true);

            }
        });

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.COMBO_INTENT)) {
            combo = (Combo) getIntent().getExtras().get(ConstraintUtils.COMBO_INTENT);
            preencherDadosNaView();
        }else{
            atualizarListDeProdutos();
        }


        registerForContextMenu(lstProdutos);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual_excluir, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {

            case R.id.menu_excluir:

                produtos.remove(info.position);
                atualizarListDeProdutos();

                break;
        }
        return super.onContextItemSelected(item);
    }


    public void atualizarListDeProdutos() {

        if (combosAdapter == null) {
            produtos = new ArrayList<>();
            combosAdapter = new ProdutosCombosAdapter(this, produtos);
            lstProdutos.setAdapter(combosAdapter);

        } else {

            combosAdapter.notifyDataSetChanged();


        }


    }

    public void preencherDadosNaView() {

        produtos = combo.getProdutos();

        combosAdapter = new ProdutosCombosAdapter(this, produtos);
        lstProdutos.setAdapter(combosAdapter);

        inpPrecoVenda.setText(combo.getValorVenda().toString());
        inpNomeCombo.setText(combo.getNome());
        inpEanCombo.setText(combo.getEan());
        inpDescricaoCombo.setText(combo.getDescricao());
        inpQtdeCombo.setText(combo.getQtde().toString());

        comboPresenter.setItem(combo);
        atualizarListDeProdutos();


    }

    @Override
    public void onMessageSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onLoadeadEntitys() {


        ViewHelper.closeProgressDialog();

    }

    public void fillDataInSpinnerCategoria() {

        if(combo == null)
            return;

        for(int i = 0;i<spnCategoria.getAdapter().getCount();i++){

            Categoria categoria = (Categoria) spnCategoria.getAdapter().getItem(i);

            if (combo.getCategoria().getId().compareTo(categoria.getId())==0) {
                spnCategoria.setSelection(i);
                return;
            }

        }
    }

    public void fillDataInSpinnerUnidadeDeMedida() {

        if(combo == null)
            return;

        for(int i = 0;i<spnUnidadeDeMedida.getAdapter().getCount();i++){

            UnidadeDeMedida unidadeDeMedida = (UnidadeDeMedida) spnUnidadeDeMedida.getAdapter().getItem(i);

            if (combo.getUnidadeDeMedida().getId().compareTo(unidadeDeMedida.getId())==0) {
                spnUnidadeDeMedida.setSelection(i);
                return;
            }

        }
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_save:

                comboPresenter.setProdutosEmCombo(produtos);

                comboPresenter.onCreate();

                break;

            case R.id.imgLerQrCode:

                QrCodeUtil.lerQRCode(this);

                break;


            default:
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() != null) {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                produto = ((ComboPresenter) comboPresenter).verificarProdutoPorEAN(result.getContents());

                if (produto == null) {
                    Toast.makeText(this, getString(R.string.nao_possivel_ler_codigo), Toast.LENGTH_LONG).show();
                } else {
                    produtos.add(produto);
                    atualizarListDeProdutos();
                }

            } else {
                Toast.makeText(this, getString(R.string.nao_possivel_ler_codigo), Toast.LENGTH_LONG).show();
            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getDadosDeSpinner() {

        comboPresenter.getDadosSpinnerCategoria(spnCategoria);
        comboPresenter.getDadosSpinnerUnidadeDeMedida(spnUnidadeDeMedida);

    }

}
