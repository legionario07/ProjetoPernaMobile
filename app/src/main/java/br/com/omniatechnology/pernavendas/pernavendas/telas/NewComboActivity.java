package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.CategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosCombosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewComboActivity extends AppCompatActivity implements IModelView.IComboView, View.OnClickListener {

    ImageButton btnSave;
    private AutoCompleteTextView inpProduto;
    private ListView lstProdutos;
    private EditText inpPrecoVenda;
    private EditText inpNomeCombo;
    private EditText inpEanCombo;

    private ImageButton imgAdicionarProduto;
    private ImageButton imgLerQrCode;
    private ProdutosCombosAdapter combosAdapter;

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
        imgAdicionarProduto = findViewById(R.id.imgAdicionarProduto);
        imgLerQrCode = findViewById(R.id.imgLerQrCode);
        inpPrecoVenda = findViewById(R.id.inpPrecoVenda);
        inpNomeCombo = findViewById(R.id.inpNomeCombo);
        inpEanCombo = findViewById(R.id.inpNomeCombo);

        comboPresenter = new ComboPresenter(this, this);
        ((ComboPresenter) comboPresenter).addTextWatcherNomeCombo(inpNomeCombo);
        ((ComboPresenter) comboPresenter).addTextWatcherPrecoVenda(inpPrecoVenda);
        ((ComboPresenter) comboPresenter).addTextWatcherEanCombo(inpEanCombo);

        btnSave.setOnClickListener(this);

        inpProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Produto) inpProduto.getAdapter().getItem(position);
                produtos.add(produto);
                atualizarListDeProdutos();

            }
        });

        atualizarListDeProdutos();

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.COMBO_INTENT)){
            combo = (Combo) getIntent().getExtras().get(ConstraintUtils.COMBO_INTENT);
            preencherDadosNaView();
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
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();


        switch (item.getItemId()) {

            case R.id.menu_excluir:

                produtos.remove(info.position);
                atualizarListDeProdutos();

                break;
        }
        return super.onContextItemSelected(item);
    }



    public void atualizarListDeProdutos(){

        if(combosAdapter==null){
            produtos = new ArrayList<>();
            combosAdapter = new ProdutosCombosAdapter(this, produtos);
            lstProdutos.setAdapter(combosAdapter);

        }else{

            combosAdapter.notifyDataSetChanged();


        }



    }

    public void preencherDadosNaView(){

       produtos = combo.getProdutos();
       inpPrecoVenda.setText(combo.getPreco().toString());
       atualizarListDeProdutos();


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

                comboPresenter.onCreate();

                break;

            case R.id.imgAdicionarProduto:


                if(produto==null){
                    Toast.makeText(this, getString(R.string.selecione_produto_primeiro), Toast.LENGTH_LONG).show();
                }else{
                    produtos.add(produto);
                    atualizarListDeProdutos();
                }

                break;

                default:
        }

    }
}
