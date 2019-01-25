package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;

import static android.widget.Toast.LENGTH_LONG;

public class ProdutosActivity extends AppCompatActivity implements IModelView.IProdutoView, View.OnClickListener {

    private RecyclerView rcViewProdutos;
    private ProdutosAdapter produtosAdapter;
    IProdutoPresenter produtoPresenter;
    private List<Produto> produtos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produtos_activity);

        rcViewProdutos = findViewById(R.id.rcViewProdutos);

        FloatingActionButton fabNewProduto = findViewById(R.id.fabNovoProduto);
        fabNewProduto.setOnClickListener(this);

        produtoPresenter = new ProdutoPresenter(this, this, produtosAdapter);
        ((ProdutoPresenter) produtoPresenter).atualizarList(rcViewProdutos);

        registerForContextMenu(rcViewProdutos);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menu_editar:

                Intent intent = new Intent(this,NewCategoriaActivity.class);
                intent.putExtra(ConstraintUtils.PRODUTO_INTENT, (Serializable) produtosAdapter.getItem(info.position));
                startActivityForResult(intent, ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            case R.id.menu_excluir:

                produtoPresenter.onDelete((produtosAdapter.getItem(info.position)).getId());

                break;
        }
        return super.onContextItemSelected(item);
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
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.fabNovoProduto:

                startActivityForResult(new Intent(this, NewProdutoActivity.class), ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

                default:
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==ConstraintUtils.IDENTIFICATION_ACTIVITY){
            produtoPresenter.findAll();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
