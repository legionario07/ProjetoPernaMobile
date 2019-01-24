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
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IPerfilPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.PerfilPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PerfisAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class PerfilActivity extends AppCompatActivity implements IModelView.IPerfilView, View.OnClickListener {

    private ListView lstPerfil;
    IPerfilPresenter perfilPresenter;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_activity);

        lstPerfil = findViewById(R.id.lstPerfil);

        FloatingActionButton fabNewPerfil = findViewById(R.id.fabNovoPerfil);
        fabNewPerfil.setOnClickListener(this);

        perfilPresenter = new PerfilPresenter(this, this);

        perfilPresenter.atualizarList(lstPerfil);

        registerForContextMenu(lstPerfil);

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

                perfilPresenter.onUpdate();

                progressDialog.dismiss();

                break;

            case R.id.menu_excluir:

                showProgressDialog(getResources().getString(R.string.processando));

                perfilPresenter.onDelete(((Perfil) lstPerfil.getItemAtPosition(info.position)).getId());

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

            case R.id.fabNovoPerfil:

                startActivity(new Intent(this, NewPerfilActivity.class));

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
