package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.VendasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ComboServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PedidoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.VendaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
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
    private List<Mercadoria> produtos;
    private List<Pedido> pedidos;
    ArrayAdapter<Mercadoria> adapter;
    private PedidosAdapter pedidosAdapter;
    private VendasAdapter vendasAdapter;

    private OperationType operationType;
    private ListView lstVenda;
    private ListView lstPedidos;
    private ListView lstProdutos;
    private AutoCompleteTextView autoCompleteProdutos;

    private TextView txtEmpty;

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

        findAllProdutos();

        autoCompleteProdutos.setThreshold(1);
        autoCompleteProdutos.setTextColor(Color.RED);

    }

    public void save(Venda venda) {
        this.venda = venda;
        onCreate();
    }

    public void atualizarListaVenda(ListView view, TextView txtEmpty) {

        this.lstVenda = view;
        this.txtEmpty = txtEmpty;

        if (vendasAdapter == null) {
            findAll();

        } else {

            vendasAdapter.notifyDataSetChanged();

        }

    }

    public void atualizarListaPedidos(ListView view) {

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

        pedidos.remove(id);

        pedidosAdapter.notifyDataSetChanged();

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

    @Override
    public void setItem(IModel model) {
        this.venda = (Venda) model;
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

    public void findAllCombos() {

        operationType = OperationType.FIND_ALL_COMBOS;

        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new ComboServiceImpl());
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

    public Mercadoria verificarProdutoPorEAN(String ean) {

        for (Mercadoria p : produtos) {

            if(p instanceof Produto) {
                if (((Produto) p ).getEan().equals(ean)) {
                    return p;
                }
            }else{
                if(((Combo) p ).getEan().equals(ean)){
                    return p;
                }
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
                    if(vendas==null){
                        vendas = new ArrayList<>();
                    }
                }

                if (vendasAdapter == null) {
                    vendasAdapter = new VendasAdapter(context, vendas);

                    lstVenda.setAdapter(vendasAdapter);
                } else {
                    vendasAdapter.notifyDataSetChanged();
                }

                if(vendas.isEmpty()){
                    lstVenda.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);
                }else{
                    lstVenda.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.GONE);
                }

                vendasAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:


                break;
            case FIND_ALL_PEDIDO:

                pedidos = (List<Pedido>) serializable;
                if(pedidos==null){
                    pedidos = new ArrayList<>();
                }

                break;

            case FIND_ALL_PRODUTO:

                produtos = (List<Mercadoria>) serializable;

                if(produtos==null){
                    produtos = new ArrayList<>();
                }


                findAllCombos();

                break;

            case FIND_ALL_COMBOS:

                List<Mercadoria> mercadoriasTemp = (List<Mercadoria>) serializable;

                if(mercadoriasTemp!=null && produtos!=null) {
                    produtos.addAll(mercadoriasTemp);
                }

                if (produtos == null) {
                    produtos = new ArrayList<>();
                }

                adapter = new ArrayAdapter<Mercadoria>
                        (context, android.R.layout.select_dialog_item, produtos);

                autoCompleteProdutos.setAdapter(adapter);

                break;


            default:
        }

    }

}
