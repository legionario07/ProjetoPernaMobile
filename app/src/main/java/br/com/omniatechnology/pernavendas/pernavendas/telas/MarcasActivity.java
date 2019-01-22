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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IMarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

import static android.widget.Toast.LENGTH_LONG;

public class MarcasActivity extends AppCompatActivity implements IModelView.IMarcaView, View.OnClickListener {

    private ListView lstMarca;
    private MarcasAdapter marcasAdapter;
    IMarcaPresenter marcaPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marca_activity);

        lstMarca = findViewById(R.id.lstMarca);

        FloatingActionButton fabNewMarca = findViewById(R.id.fabNovaMarca);
        fabNewMarca.setOnClickListener(this);

        //marcaPresenter = new MarcaPresenter(this, this, marcasAdapter);
       // ((MarcaPresenter) marcaPresenter).initialize(rcViewMarcas);


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
    public List<IModel> findAllSuccess() {
        return null;
    }

    @Override
    public List<IModel> findAllError(String message) {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_save:



                break;

            case R.id.fabNovaMarca:

                startActivity(new Intent(this, NewMarcaActivity.class));

                break;

                default:
        }

    }



}
