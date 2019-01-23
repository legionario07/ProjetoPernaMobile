package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IMarcaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IVendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.VendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewVendaActivity extends AppCompatActivity implements IModelView.IVendaView, View.OnClickListener {

    private ListView lstPedidos;
    private List<Pedido> pedidos;
    private AutoCompleteTextView inpProduto;
    IVendaPresenter vendaPresenter;
    private ImageButton imgAdicionarProduto;
    private Produto produto;
    private TextView txtTotal;

    private PedidosAdapter pedidosAdapter;

    private LayoutInflater inflater;
    private AlertDialog alert;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;

    private ProgressDialog progressDialog;

    private TextView txtNomeProduto;
    private TextView txtDescricaoProduto;
    private TextInputLayout inpLayoutQuantidade;
    private TextInputLayout inpLayoutDesconto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venda);

        lstPedidos = findViewById(R.id.lstPedidos);
        inpProduto = findViewById(R.id.inp_produto);
        txtTotal  =findViewById(R.id.txtTotal);
        imgAdicionarProduto = findViewById(R.id.imgAdicionarProduto);


        imgAdicionarProduto.setOnClickListener(this);


        final List<Produto> produtos = new ArrayList<>();
        Produto p = new Produto();
        p.setNome("CASA COM PISCINA");
        p.setDescricao("Casa");
        p.setQtde(2);
        p.setValorVenda(new BigDecimal("10.00"));

        Produto p3 = new Produto();
        p3.setNome("CASA SEM PISCINA");
        p3.setDescricao("Casa");
        p3.setQtde(2);
        p3.setValorVenda(new BigDecimal("10.00"));

        Produto p2 = new Produto();
        p2.setNome("CArro COM PISCINA");
        p2.setQtde(2);
        p2.setDescricao("CARRO");
        p2.setValorVenda(new BigDecimal("10.00"));

        produtos.add(p);
        produtos.add(p2);
        produtos.add(p3);


        vendaPresenter = new VendaPresenter(this, this);
       // ((MarcaPresenter) marcaPresenter).initialize(rcViewMarcas);


        ((VendaPresenter) vendaPresenter).addDataForAdapter(inpProduto);

        inpProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Produto) inpProduto.getAdapter().getItem(position);

            }
        });

        atualizarListDePedidos();


    }


    public void atualizarListDePedidos(){

        BigDecimal valorTotal = new BigDecimal("0.0");

        if(pedidosAdapter==null){
            pedidos = new ArrayList<>();
            pedidosAdapter = new PedidosAdapter(this, pedidos);
            lstPedidos.setAdapter(pedidosAdapter);

        }else{

            pedidosAdapter.notifyDataSetChanged();

            for(Pedido p : pedidos){
                valorTotal = valorTotal.add(p.getValorTotal());
            }

        }

        txtTotal.setText(valorTotal.toString());


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


            case R.id.imgAdicionarProduto:

               if(produto==null){
                   Toast.makeText(this, getString(R.string.selecione_produto_primeiro), Toast.LENGTH_LONG).show();
               }else{
                   //Regra para abrir dialog com o produto selecionado
                   Toast.makeText(this, produto.getNome(), Toast.LENGTH_LONG).show();
                   criarDialogCRUD("SALVAR");
               }

                break;

                default:
        }

    }

    private void criarDialogCRUD(final String operacao) {

        dialogBuilder = new AlertDialog.Builder(this);

        inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_pedido, null);
        dialogBuilder.setView(dialogView);

        txtNomeProduto = dialogView.findViewById(R.id.txtNomeProduto);
        txtDescricaoProduto = dialogView.findViewById(R.id.txtDescricaoProduto);
        inpLayoutQuantidade = dialogView.findViewById(R.id.inp_layout_quantidade);
        inpLayoutDesconto = dialogView.findViewById(R.id.inp_layout_desconto);

        txtNomeProduto.setText(produto.getNome());
        txtDescricaoProduto.setText(produto.getDescricao());
        inpLayoutQuantidade.getEditText().setText("1");
        inpLayoutDesconto.getEditText().setText("0");

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Pedido pedido = new Pedido();
                pedido.setProduto(produto);
                pedido.setDesconto(new BigDecimal(inpLayoutDesconto.getEditText().getText().toString()));
                pedido.setTotal(Integer.valueOf(inpLayoutQuantidade.getEditText().getText().toString()));

                BigDecimal valor = pedido.getProduto().getValorVenda().multiply(new BigDecimal(pedido.getTotal()));
                valor = valor.subtract(pedido.getDesconto());
                pedido.setValorTotal(valor);

                pedidos.add(pedido);

                atualizarListDePedidos();



            }
        })


                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;

                    }
                });

        dialog = dialogBuilder.create();
        dialog.show();

    }




}
