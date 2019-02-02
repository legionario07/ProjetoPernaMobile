package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiFunction;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IVendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.VendaPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.QrCodeUtil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.VerificaConexaoStrategy;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewVendaActivity extends AppCompatActivity implements IModelView.IVendaView, View.OnClickListener {

    private ListView lstPedidos;
    private List<Pedido> pedidos;
    private AutoCompleteTextView inpProduto;
    IVendaPresenter vendaPresenter;
    private ImageButton imgAdicionarProduto;
    private ImageButton imgSalvarVenda;
    private Mercadoria produto;
    private TextView txtTotal;
    private Venda venda;

    private PedidosAdapter pedidosAdapter;

    private LayoutInflater inflater;
    private AlertDialog alert;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;

    private TextView txtNomeProduto;
    private TextView txtDescricaoProduto;
    private TextInputLayout inpLayoutQuantidade;
    private TextInputLayout inpLayoutDesconto;

    private ImageButton imgQrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venda);

        if (!VerificaConexaoStrategy.verificarConexao(this)) {
            Toast.makeText(this, "Verifique sua conexão com a Internet", Toast.LENGTH_LONG).show();
            finishAffinity();
        }

        lstPedidos = findViewById(R.id.lstPedidos);
        inpProduto = findViewById(R.id.inp_produto);
        txtTotal  =findViewById(R.id.txtTotal);
        imgAdicionarProduto = findViewById(R.id.imgAdicionarProduto);
        imgSalvarVenda = findViewById(R.id.imgSalvarVenda);
        imgQrCode  =findViewById(R.id.imgLerQrCode);

        imgAdicionarProduto.setOnClickListener(this);
        imgSalvarVenda.setOnClickListener(this);
        imgQrCode.setOnClickListener(this);

        vendaPresenter = new VendaPresenter(this, this);

        ((VendaPresenter) vendaPresenter).addDataForAdapter(inpProduto);


        inpProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Mercadoria) inpProduto.getAdapter().getItem(position);

                //ViewUtils.hideKeyboard(getApplicationContext(), inpProduto, true);

            }
        });


        atualizarListDePedidos();

        registerForContextMenu(lstPedidos);



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual_excluir, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();


        switch (item.getItemId()) {

            case R.id.menu_excluir:

                pedidos.remove(info.position);
                atualizarListDePedidos();

                break;
        }
        return super.onContextItemSelected(item);
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
        onBackPressed();
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        if (!VerificaConexaoStrategy.verificarConexao(this)) {
            Toast.makeText(this, "Verifique sua conexão com a Internet", Toast.LENGTH_LONG).show();
            finishAffinity();
        }

        switch (v.getId()){
            case R.id.imgSalvarVenda:

                venda = new Venda();
                venda.setPedidos(pedidos);
                venda.setUsuario(SessionUtil.getInstance().getUsuario());
                venda.setValorTotal(new BigDecimal(txtTotal.getText().toString()));

                vendaPresenter.save(venda);


                break;


            case R.id.imgAdicionarProduto:

               if(produto==null || inpProduto.getText().toString().isEmpty()){
                   Toast.makeText(this, getString(R.string.selecione_produto_primeiro), Toast.LENGTH_LONG).show();
               }else{
                   //Regra para abrir dialog com o produto selecionado
                   criarDialogCRUD();
                   inpProduto.setText("");
               }

                break;

            case R.id.imgLerQrCode:

                QrCodeUtil.lerQRCode(this);

                break;

                default:
        }

    }

    private void criarDialogCRUD() {

        dialogBuilder = new AlertDialog.Builder(this);

        inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_pedido, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.adicionar_produto);

        txtNomeProduto = dialogView.findViewById(R.id.txtNomeProduto);
        txtDescricaoProduto = dialogView.findViewById(R.id.txtDescricaoProduto);
        inpLayoutQuantidade = dialogView.findViewById(R.id.inp_layout_quantidade);
        inpLayoutDesconto = dialogView.findViewById(R.id.inp_layout_desconto);

        if(produto instanceof Produto) {
            txtNomeProduto.setText(((Produto) produto ).getNome());
            txtDescricaoProduto.setText(((Produto) produto ).getDescricao());
        }else{
            txtNomeProduto.setText(((Combo) produto ).getNome());
            txtDescricaoProduto.setText(((Combo) produto ).getDescricao());
        }
        inpLayoutQuantidade.getEditText().setText("1");
        inpLayoutDesconto.getEditText().setText("0");

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                BigDecimal valor = BigDecimal.ZERO;

                Pedido pedido = new Pedido();
                pedido.setTotal(Integer.valueOf(inpLayoutQuantidade.getEditText().getText().toString()));
                if(produto instanceof Produto) {
                    pedido.setProduto((Produto) produto);
                    valor = pedido.getProduto().getValorVenda().multiply(new BigDecimal(pedido.getTotal()));
                }else{
                    pedido.setCombo((Combo) produto);
                    valor = pedido.getCombo().getValorVenda().multiply(new BigDecimal(pedido.getTotal()));
                }
                pedido.setDesconto(new BigDecimal(inpLayoutDesconto.getEditText().getText().toString()));



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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){

            if(result.getContents()!=null){
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();

                produto = ((VendaPresenter) vendaPresenter).verificarProdutoPorEAN(result.getContents());

                if(produto==null){
                    Toast.makeText(this, getString(R.string.nao_possivel_ler_codigo),Toast.LENGTH_LONG).show();
                }else{
                    criarDialogCRUD();
                }

            }else{
                Toast.makeText(this, getString(R.string.nao_possivel_ler_codigo), Toast.LENGTH_LONG).show();
            }

        }else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
