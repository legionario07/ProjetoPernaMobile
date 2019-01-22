package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IMarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IVendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.VendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewVendaActivity extends AppCompatActivity implements IModelView.IVendaView, View.OnClickListener {

    private ListView lstPedidos;
    private MarcasAdapter marcasAdapter;
    private AutoCompleteTextView inpProduto;
    IVendaPresenter vendaPresenter;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venda);

        lstPedidos = findViewById(R.id.lstPedidos);
        inpProduto = findViewById(R.id.inp_produto);


        List<Produto> produtos = new ArrayList<>();
        Produto p = new Produto();
        p.setNome("CASA COM PISCINA");
        p.setQtde(2);

        Produto p3 = new Produto();
        p3.setNome("CASA SEM PISCINA");
        p3.setQtde(2);

        Produto p2 = new Produto();
        p2.setNome("CArro COM PISCINA");
        p2.setQtde(2);

        produtos.add(p);
        produtos.add(p2);
        produtos.add(p3);


        vendaPresenter = new VendaPresenter(this, this);
       // ((MarcaPresenter) marcaPresenter).initialize(rcViewMarcas);

        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>
                (this, android.R.layout.select_dialog_item, produtos);

        inpProduto.setAdapter(adapter);
        inpProduto.setThreshold(1);
        inpProduto.setTextColor(Color.RED);




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
