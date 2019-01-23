package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.CategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ICategoriaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.VendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CategoriasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

import static android.widget.Toast.LENGTH_LONG;

public class CategoriasActivity extends AppCompatActivity implements IModelView.ICategoriaView, View.OnClickListener {

    private ListView lstCategoria;
    ICategoriaPresenter categoriaPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_activity);

        lstCategoria = findViewById(R.id.lstCategoria);

        FloatingActionButton fabNewCategoria = findViewById(R.id.fabNovaCategoria);
        fabNewCategoria.setOnClickListener(this);

        categoriaPresenter = new CategoriaPresenter(this, this);

        categoriaPresenter.atualizarList(lstCategoria);

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


}
