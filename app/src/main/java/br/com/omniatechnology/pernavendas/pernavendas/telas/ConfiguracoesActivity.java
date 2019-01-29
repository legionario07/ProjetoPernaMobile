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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ConfiguracaoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IConfiguracaoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ConfiguracoesAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class ConfiguracoesActivity extends AppCompatActivity implements IModelView.IConfiguracaoView, View.OnClickListener {

    private ListView lstConfiguracao;
    IConfiguracaoPresenter configuracaoPresenter;
    private TextView txtEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao_activity);

        lstConfiguracao = findViewById(R.id.lstConfiguracao);
        txtEmpty = findViewById(R.id.txtEmpty);

        FloatingActionButton fabNewConfiguracao = findViewById(R.id.fabNovaConfiguracao);
        fabNewConfiguracao.setOnClickListener(this);

        configuracaoPresenter = new ConfiguracaoPresenter(this, this);

        configuracaoPresenter.atualizarList(lstConfiguracao, txtEmpty);

        registerForContextMenu(lstConfiguracao);

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

                Intent intent = new Intent(this,NewConfiguracaoActivity.class);
                intent.putExtra(ConstraintUtils.CONFIGURACAO_INTENT, (Serializable) lstConfiguracao.getItemAtPosition(info.position));
                startActivityForResult(intent, ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            case R.id.menu_excluir:

                configuracaoPresenter.onDelete(((Configuracao) lstConfiguracao.getItemAtPosition(info.position)).getId());

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

            case R.id.fabNovaConfiguracao:

                startActivityForResult(new Intent(this, NewConfiguracaoActivity.class), ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            default:
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==ConstraintUtils.IDENTIFICATION_ACTIVITY){
            configuracaoPresenter.findAll();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
