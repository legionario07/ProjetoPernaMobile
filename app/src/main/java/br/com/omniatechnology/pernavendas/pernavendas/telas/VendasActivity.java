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
import android.widget.Toast;

import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IVendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.VendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class VendasActivity extends AppCompatActivity implements IModelView.IVendaView, View.OnClickListener{
    private ListView lstVenda;
    IVendaPresenter vendaPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venda_activity);

        lstVenda = findViewById(R.id.lstVenda);

        FloatingActionButton fabNewVenda = findViewById(R.id.fabNovaVenda);
        fabNewVenda.setOnClickListener(this);

        vendaPresenter = new VendaPresenter(this, this);
        ((VendaPresenter) vendaPresenter).atualizarListaVenda(lstVenda);

        registerForContextMenu(lstVenda);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();


        switch (item.getItemId()) {
            case R.id.menu_editar:

                Intent intent = new Intent(this,NewVendaActivity.class);
                intent.putExtra(ConstraintUtils.USUARIO_INTENT, (Serializable) lstVenda.getItemAtPosition(info.position));
                startActivityForResult(intent, ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            case R.id.menu_excluir:

                vendaPresenter.onDelete(((Venda) lstVenda.getItemAtPosition(info.position)).getId());

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


            case R.id.fabNovaVenda:

                startActivityForResult(new Intent(this, NewVendaActivity.class), ConstraintUtils.IDENTIFICATION_ACTIVITY);

                break;

            default:
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==ConstraintUtils.IDENTIFICATION_ACTIVITY){
            vendaPresenter.findAll();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }





}
