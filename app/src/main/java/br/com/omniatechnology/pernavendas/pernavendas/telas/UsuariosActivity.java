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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IUsuarioPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.UsuarioPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UsuariosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class UsuariosActivity extends AppCompatActivity implements IModelView.IUsuarioView, View.OnClickListener {

    private ListView lstUsuario;
    IUsuarioPresenter usuarioPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_activity);

        lstUsuario = findViewById(R.id.lstUsuario);

        FloatingActionButton fabNewUsuario = findViewById(R.id.fabNovoUsuario);
        fabNewUsuario.setOnClickListener(this);

        usuarioPresenter = new UsuarioPresenter(this, this);
        usuarioPresenter.atualizarList(lstUsuario);

        lstUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewUsuarioActivity.class);
                intent.putExtra(ConstraintUtils.USUARIO_INTENT, (Serializable) lstUsuario.getAdapter().getItem(position));
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


            case R.id.fabNovoUsuario:

                startActivity(new Intent(this, NewUsuarioActivity.class));

                break;

            default:
        }

    }


}
