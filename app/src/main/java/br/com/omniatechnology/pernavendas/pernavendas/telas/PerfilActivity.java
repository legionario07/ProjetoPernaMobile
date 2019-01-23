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
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class PerfilActivity extends AppCompatActivity implements IModelView.IPerfilView, View.OnClickListener {

    private ListView lstPerfil;
    IPerfilPresenter perfilPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_activity);

        lstPerfil = findViewById(R.id.lstPerfil);

        FloatingActionButton fabNewPerfil = findViewById(R.id.fabNovoPerfil);
        fabNewPerfil.setOnClickListener(this);

        perfilPresenter = new PerfilPresenter(this, this);

        perfilPresenter.atualizarList(lstPerfil);

//        lstPerfil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), NewPerfilActivity.class);
//                intent.putExtra(ConstraintUtils.PERFIL_INTENT, (Serializable) lstPerfil.getAdapter().getItem(position));
//                startActivity(intent);
//            }
//        });


        registerForContextMenu(lstPerfil);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
                break;

            case R.id.menu_excluir:

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


}
