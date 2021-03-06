package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ProdutoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.VerificaConexaoStrategy;

import static android.widget.Toast.LENGTH_LONG;

public class ProdutosActivity extends AppCompatActivity implements IModelView.IProdutoView, View.OnClickListener {

    private RecyclerView rcViewProdutos;
    IProdutoPresenter produtoPresenter;
    private TextView txtEmpty;
    private ProdutosAdapter produtosAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produtos_activity);

        if (!VerificaConexaoStrategy.verificarConexao(this)) {
            Toast.makeText(this, "Verifique sua conexão com a Internet", Toast.LENGTH_LONG).show();
            finishAffinity();
        }

        rcViewProdutos = findViewById(R.id.rcViewProdutos);
        txtEmpty = findViewById(R.id.txtEmpty);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcViewProdutos.setLayoutManager(layoutManager);

        FloatingActionButton fabNewProduto = findViewById(R.id.fabNovoProduto);
        fabNewProduto.setOnClickListener(this);

        produtoPresenter = new ProdutoPresenter(this, this);
        ((ProdutoPresenter) produtoPresenter).atualizarList(rcViewProdutos, txtEmpty);



    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getMenuInflater().inflate(R.menu.menu_contextual, menu);
//
//        ContextMenuRecyclerView.RecyclerContextMenuInfo info = (ContextMenuRecyclerView
//                .RecyclerContextMenuInfo) menuInfo;
//
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:

                Intent intent = new Intent(this,NewProdutoActivity.class);
                intent.putExtra(ConstraintUtils.PRODUTO_INTENT, produtosAdapter.getItem(item.getGroupId()));
                startActivityForResult(intent, ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            case 2:

                produtoPresenter.onDelete(produtosAdapter.getItem(item.getGroupId()).getId());

                break;
        }
        return super.onContextItemSelected(item);
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


    @Override
    public void onLoadeadEntitys() {
       produtosAdapter = (ProdutosAdapter) rcViewProdutos.getAdapter();
    }
}
