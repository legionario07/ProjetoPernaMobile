package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ConfiguracaoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IConfiguracaoPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ConfiguracoesAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class ConfiguracoesActivity extends AppCompatActivity implements IModelView.IConfiguracaoView, View.OnClickListener {

    private ListView lstConfiguracao;
    IConfiguracaoPresenter configuracaoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao_activity);

        lstConfiguracao = findViewById(R.id.lstConfiguracao);

        FloatingActionButton fabNewConfiguracao = findViewById(R.id.fabNovaConfiguracao);
        fabNewConfiguracao.setOnClickListener(this);

        configuracaoPresenter = new ConfiguracaoPresenter(this, this);

        configuracaoPresenter.atualizarList(lstConfiguracao);

        lstConfiguracao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewConfiguracaoActivity.class);
                intent.putExtra(ConstraintUtils.CONFIGURACAO_INTENT, (Serializable) lstConfiguracao.getAdapter().getItem(position));
                startActivity(intent);
            }
        });


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

                startActivity(new Intent(this, NewConfiguracaoActivity.class));

                break;

            default:
        }

    }


}
