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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IUnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.UnidadeDeMedidaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UnidadesDeMedidasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class UnidadesDeMedidasActivity extends AppCompatActivity implements IModelView.IUnidadeDeMedidaView, View.OnClickListener {

    private ListView lstUnidadeDeMedida;
    IUnidadeDeMedidaPresenter unidadeDeMedidaPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unidade_de_medida_activity);

        lstUnidadeDeMedida = findViewById(R.id.lstUnidadeDeMedida);

        FloatingActionButton fabNewUnidadeDeMedida = findViewById(R.id.fabNovaUnidadeDeMedida);
        fabNewUnidadeDeMedida.setOnClickListener(this);

        unidadeDeMedidaPresenter = new UnidadeDeMedidaPresenter(this, this);

        unidadeDeMedidaPresenter.atualizarList(lstUnidadeDeMedida);

        lstUnidadeDeMedida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewUnidadeDeMedidaActivity.class);
                intent.putExtra(ConstraintUtils.UNIDADE_DE_MEDIDA_INTENT, (Serializable) lstUnidadeDeMedida.getAdapter().getItem(position));
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
            case R.id.btn_save:


                break;

            case R.id.fabNovaUnidadeDeMedida:

                startActivity(new Intent(this, NewUnidadeDeMedidaActivity.class));

                break;

            default:
        }

    }


}
