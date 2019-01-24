package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.CategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ICategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.VendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CategoriasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class CategoriasActivity extends AppCompatActivity implements IModelView.ICategoriaView, View.OnClickListener {

    private ListView lstCategoria;
    ICategoriaPresenter categoriaPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_activity);

        lstCategoria = findViewById(R.id.lstCategoria);

        FloatingActionButton fabNewCategoria = findViewById(R.id.fabNovaCategoria);
        fabNewCategoria.setOnClickListener(this);

        categoriaPresenter = new CategoriaPresenter(this, this);

        categoriaPresenter.atualizarList(lstCategoria);

        registerForContextMenu(lstCategoria);

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

                showProgressDialog(getResources().getString(R.string.processando));

                categoriaPresenter.onUpdate();

                progressDialog.dismiss();

                break;

            case R.id.menu_excluir:

                showProgressDialog(getResources().getString(R.string.processando));

                categoriaPresenter.onDelete(((Categoria) lstCategoria.getItemAtPosition(info.position)).getId());

                progressDialog.dismiss();

                break;
        }
        return super.onContextItemSelected(item);
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

            case R.id.fabNovaCategoria:

                startActivity(new Intent(this, NewCategoriaActivity.class));

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
