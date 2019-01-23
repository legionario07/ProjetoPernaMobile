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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IMarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.MarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class MarcasActivity extends AppCompatActivity implements IModelView.IMarcaView, View.OnClickListener {

    private ListView lstMarca;
    IMarcaPresenter marcaPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marca_activity);

        lstMarca = findViewById(R.id.lstMarca);

        FloatingActionButton fabNewMarca = findViewById(R.id.fabNovaMarca);
        fabNewMarca.setOnClickListener(this);

        marcaPresenter = new MarcaPresenter(this, this);

        marcaPresenter.atualizarList(lstMarca);

        lstMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewMarcaActivity.class);
                intent.putExtra(ConstraintUtils.MARCA_INTENT, (Serializable) lstMarca.getAdapter().getItem(position));
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
    public List<IModel> findAllSuccess() {
        return null;
    }

    @Override
    public List<IModel> findAllError(String message) {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fabNovaMarca:

                startActivity(new Intent(this, NewMarcaActivity.class));

                break;

            default:
        }

    }


}
