package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.VendasAbertasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.VendasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ComboServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PedidoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.VendaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class VendaPresenter implements IVendaPresenter{

    IModelView.IVendaView vendaView;
    private Context context;
    Venda venda;
    private Boolean isSave;
    private List<Venda> vendas =  new ArrayList<>();
    private List<Mercadoria> produtos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    ArrayAdapter<Mercadoria> adapter;
    private VendasAbertasAdapter vendasAbertasAdapter;
    private PedidosAdapter pedidosAdapter;
    private VendasAdapter vendasAdapter;

    private ListView lstVenda;
    private ListView lstPedidos;
    private ListView lstProdutos;
    private AutoCompleteTextView autoCompleteProdutos;

    private TextView txtEmpty;

    public VendaPresenter() {
        venda = new Venda();
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

    @Override
    public void atualizarListaVendasAbertas(ListView view, TextView txtEmpty) {

        this.lstVenda = view;
        this.txtEmpty = txtEmpty;

        if (vendasAbertasAdapter == null) {
            findAllVendasAbertas();

        } else {

            vendasAbertasAdapter.notifyDataSetChanged();

        }


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

        String retornoStr = venda.isValid(context);


        if (retornoStr.length() == 0) {

            new VendaServiceImpl().save(venda)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Venda>() {
                        @Override
                        public void onCompleted() {
                            vendaView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            vendaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Venda venda) {

                        }
                    });


        } else {
            vendaView.onMessageError(retornoStr);
        }

    }

   

    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

        new VendaServiceImpl().findAllVendasFechadas()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Venda>>() {
                    @Override
                    public void onCompleted() {

                        if (vendasAdapter == null) {
                            vendasAdapter = new VendasAdapter(context, vendas);

                            lstVenda.setAdapter(vendasAdapter);
                        } else {
                            vendasAdapter.notifyDataSetChanged();
                        }

                        if (vendas.isEmpty()) {
                            lstVenda.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            lstVenda.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        vendasAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Venda> vendasTemp) {
                        if (vendas != null) {
                            vendas.clear();
                            vendas.addAll(vendasTemp);
                        }
                    }
                });


    }


    @Override
    public void onDelete(Long id) {

        pedidos.remove(id);

        pedidosAdapter.notifyDataSetChanged();

    }




    @Override
    public void setItem(IModel model) {
        this.venda = (Venda) model;
    }


    public void findAllVendasAbertas() {

        new VendaServiceImpl().findAllVendasAbertas()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Venda>>() {
                    @Override
                    public void onCompleted() {

                        if (pedidosAdapter == null) {
                            vendasAbertasAdapter = new VendasAbertasAdapter(context, vendas);

                            lstVenda.setAdapter(pedidosAdapter);
                        } else {
                            pedidosAdapter.notifyDataSetChanged();
                        }

                        if (vendas.isEmpty()) {
                            lstVenda.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            lstVenda.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        pedidosAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Venda> vendasTemp) {
                        if (vendas != null) {
                            vendas.clear();
                            vendas.addAll(vendasTemp);
                        }
                    }
                });


    }



    public void findAllPedidos() {

        new PedidoServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doOnUnsubscribe(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Pedido>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Pedido> pedidosTemp) {
                        if (pedidos != null) {
                            pedidos.clear();
                            pedidos.addAll(pedidosTemp);
                        }
                    }
                });


    }

    public void findAllProdutos() {
        new ProdutoServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        findAllCombos();
                    }
                })
                .doOnUnsubscribe(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Produto>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Produto> produtosTemp) {
                        if (produtos != null) {
                            produtos.clear();
                            produtos.addAll(produtosTemp);
                        }
                    }
                });



    }

    public void findAllCombos() {

        new ComboServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doOnUnsubscribe(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Combo>>() {
                    @Override
                    public void onCompleted() {
                        adapter = new ArrayAdapter<Mercadoria>
                                (context, android.R.layout.select_dialog_item, produtos);

                        autoCompleteProdutos.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Combo> combosTemp) {
                        if (produtos != null) {
                            produtos.addAll(combosTemp);
                        }
                    }
                });

    }


    public Mercadoria verificarProdutoPorEAN(String ean) {

        for (Mercadoria p : produtos) {

                if (p.getEan().equals(ean)) {
                    return p;
                }

        }

        return null;
    }

    public void addTextWatcherNomeCliente(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                venda.setNomeCliente(s.toString());
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
            }
        });
    }


}
