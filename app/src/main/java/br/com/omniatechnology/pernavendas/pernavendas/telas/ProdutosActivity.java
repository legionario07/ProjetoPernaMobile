package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;

import static android.widget.Toast.LENGTH_LONG;

public class ProdutosActivity extends AppCompatActivity implements IModelView.IProdutoView, View.OnClickListener {

    private RecyclerView rcViewProdutos;
    private ProdutosAdapter produtosAdapter;
    IProdutoPresenter produtoPresenter;
    private List<Produto> produtos;
    private Produto produto;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produtos_activity);

        rcViewProdutos = findViewById(R.id.rcViewProdutos);

        FloatingActionButton fabNewProduto = findViewById(R.id.fabNovoProduto);
        fabNewProduto.setOnClickListener(this);

        produtoPresenter = new ProdutoPresenter(this, this, produtosAdapter);
        ((ProdutoPresenter) produtoPresenter).initialize(rcViewProdutos);

        produtos = SessionUtil.getInstance().getProdutos();

        atualizarDados();

        produtosAdapter.setOnItemClickListener(new ProdutosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                produto = produtos.get(position);

                Intent intent = new Intent(getApplicationContext(), NewProdutoActivity.class);
                intent.putExtra(ConstraintUtils.PRODUTO_INTENT, produto);
                startActivity(intent);

            }
        });

    }

    public void atualizarDados(){

        if(produtosAdapter==null){
            produtosAdapter = new ProdutosAdapter(this, produtos);
            rcViewProdutos.setAdapter(produtosAdapter);
        }else{
            produtosAdapter.updateList(produtos);
        }

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

            case R.id.fabNovoProduto:

                startActivity(new Intent(this, NewProdutoActivity.class));

                break;

                default:
        }

    }



}
