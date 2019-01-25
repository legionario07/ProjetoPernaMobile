package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.VendasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PedidoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.VendaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class VendaPresenter implements IVendaPresenter, ITaskProcess {

    IModelView.IVendaView vendaView;
    private Context context;
    Venda venda;
    private Boolean isSave;
    private List<Venda> vendas;
    private List<Produto> produtos;
    private List<Pedido> pedidos;
    ArrayAdapter<Produto> adapter;
    private PedidosAdapter pedidosAdapter;
    private VendasAdapter vendasAdapter;

    private OperationType operationType;
    private ListView lstVenda;
    private ListView lstPedidos;
    private ListView lstProdutos;
    private AutoCompleteTextView autoCompleteProdutos;

    public VendaPresenter() {
        venda = new Venda();
    }

    public VendaPresenter(IModelView.IVendaView vendaView) {
        this.vendaView = vendaView;
    }

    public VendaPresenter(IModelView.IVendaView vendaView, Context context) {
        this();
        this.vendaView = vendaView;
        this.context = context;
    }

    public void addDataForAdapter(AutoCompleteTextView autoCompleteProdutos) {

        this.autoCompleteProdutos = autoCompleteProdutos;

        new GenericDAO(context, this).execute(new Produto(), ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());

        autoCompleteProdutos.setThreshold(1);
        autoCompleteProdutos.setTextColor(Color.RED);

    }

    public void save(Venda venda) {
        this.venda = venda;
        onCreate();
    }

    public void atualizarListVenda(ListView view) {

        this.lstVenda = view;

        if (vendasAdapter == null) {
            findAll();

        } else {

            vendasAdapter.notifyDataSetChanged();

        }

    }

    public void atualizarListPedidos(ListView view) {

        this.lstPedidos = view;

        if (pedidosAdapter == null) {
            findAllPedidos();

        } else {

            pedidosAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = venda.isValid(context);

        if (retornoStr.length() > 1)
            vendaView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(venda, ConstraintUtils.SALVAR, new VendaServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {


    }

    @Override
    public void onUpdate() {


    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(venda, ConstraintUtils.FIND_BY_ID, new VendaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(venda, ConstraintUtils.FIND_ALL, new VendaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    public void findAllPedidos() {

        operationType = OperationType.FIND_ALL_PEDIDO;

        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new PedidoServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    public void findAllProdutos() {

        operationType = OperationType.FIND_ALL_PRODUTO;

        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }


    public void addTextWatcherVenda(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //venda.setVenda(s.toString());
            }
        });
    }

    public Produto verificarProdutoPorEAN(String ean) {

        for (Produto p : produtos) {

            if (p.getEan().equals(ean)) {
                return p;
            }

        }

        return null;
    }

    @Override
    public void onPostProcess(Serializable serializable) {

        switch (operationType) {
            case SAVE:
            case UPDATE:
            case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    vendaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    vendaView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if (operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if (vendas != null) {
                    vendas.clear();
                    vendas.addAll((List<Venda>) serializable);
                } else {
                    vendas = (List<Venda>) serializable;
                }

                if (vendasAdapter == null) {
                    vendasAdapter = new VendasAdapter(context, vendas);

                    lstVenda.setAdapter(vendasAdapter);
                } else {
                    vendasAdapter.notifyDataSetChanged();
                }

                vendasAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:


                break;
            case FIND_ALL_PEDIDO:

                pedidos = (List<Pedido>) serializable;

                break;

            case FIND_ALL_PRODUTO:

                produtos = (List<Produto>) serializable;

                if (produtos == null) {
                    produtos = new ArrayList<>();
                }

                adapter = new ArrayAdapter<Produto>
                        (context, android.R.layout.select_dialog_item, produtos);

                autoCompleteProdutos.setAdapter(adapter);

                break;


            default:
        }

    }

}
