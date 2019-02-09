package br.com.omniatechnology.pernavendas.pernavendas.telas;

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

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class CombosActivity extends AppCompatActivity implements IModelView.IComboView, View.OnClickListener {

    private ListView lstCombo;
    IComboPresenter comboPresenter;
    private TextView txtEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combo_activity);

        lstCombo = findViewById(R.id.lstCombo);
        txtEmpty =  findViewById(R.id.txtEmpty);

        FloatingActionButton fabNewCombo = findViewById(R.id.fabNovoCombo);
        fabNewCombo.setOnClickListener(this);

        comboPresenter = new ComboPresenter(this, this);

        comboPresenter.atualizarList(lstCombo, txtEmpty);

        registerForContextMenu(lstCombo);

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

                Intent intent = new Intent(this,NewComboActivity.class);
                intent.putExtra(ConstraintUtils.COMBO_INTENT, (Serializable) lstCombo.getItemAtPosition(info.position));
                startActivityForResult(intent, ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            case R.id.menu_excluir:

                comboPresenter.onDelete(((Combo) lstCombo.getItemAtPosition(info.position)).getId());

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

            case R.id.fabNovoCombo:

                startActivityForResult(new Intent(this, NewComboActivity.class), ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            default:
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==ConstraintUtils.IDENTIFICATION_ACTIVITY){
            comboPresenter.findAll();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onLoadeadEntitys() {

    }

    @Override
    public void fillDataInSpinnerCategoria() {

    }

    @Override
    public void fillDataInSpinnerUnidadeDeMedida() {

    }
}
