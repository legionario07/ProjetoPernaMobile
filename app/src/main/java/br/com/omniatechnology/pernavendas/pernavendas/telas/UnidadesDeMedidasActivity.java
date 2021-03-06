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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IUnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.UnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UnidadesDeMedidasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class UnidadesDeMedidasActivity extends AppCompatActivity implements IModelView.IUnidadeDeMedidaView, View.OnClickListener {

    private ListView lstUnidadeDeMedida;
    IUnidadeDeMedidaPresenter unidadeDeMedidaPresenter;
    private TextView txtEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unidade_de_medida_activity);

        lstUnidadeDeMedida = findViewById(R.id.lstUnidadeDeMedida);
        txtEmpty = findViewById(R.id.txtEmpty);

        FloatingActionButton fabNewUnidadeDeMedida = findViewById(R.id.fabNovaUnidadeDeMedida);
        fabNewUnidadeDeMedida.setOnClickListener(this);

        unidadeDeMedidaPresenter = new UnidadeDeMedidaPresenter(this, this);

        unidadeDeMedidaPresenter.atualizarList(lstUnidadeDeMedida, txtEmpty);

        registerForContextMenu(lstUnidadeDeMedida);

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

                Intent intent = new Intent(this,NewUnidadeDeMedidaActivity.class);
                UnidadeDeMedida unidadeDeMedida = (UnidadeDeMedida) lstUnidadeDeMedida.getItemAtPosition(info.position);
                intent.putExtra(ConstraintUtils.UNIDADE_DE_MEDIDA_INTENT, unidadeDeMedida);
                startActivityForResult(intent, ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            case R.id.menu_excluir:

                unidadeDeMedidaPresenter.onDelete(((UnidadeDeMedida) lstUnidadeDeMedida.getItemAtPosition(info.position)).getId());

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

            case R.id.fabNovaUnidadeDeMedida:

                startActivityForResult(new Intent(this, NewUnidadeDeMedidaActivity.class), ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            default:
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==ConstraintUtils.IDENTIFICATION_ACTIVITY){
            unidadeDeMedidaPresenter.findAll();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
