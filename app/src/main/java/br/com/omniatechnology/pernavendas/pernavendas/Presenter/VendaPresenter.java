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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.VendaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;

public class VendaPresenter implements IVendaPresenter {

    IModelView.IVendaView vendaView;
    private Context context;
    Venda venda;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<Venda> vendas;
    private List<Produto> produtos;
    ArrayAdapter<Produto> adapter;

    public VendaPresenter() {
        venda = new Venda();
        genericDAO = new GenericDAO();
    }

    public VendaPresenter(IModelView.IVendaView vendaView) {
        this.vendaView = vendaView;
    }

    public VendaPresenter(IModelView.IVendaView vendaView, Context context) {
        this();
        this.vendaView = vendaView;
        this.context = context;
    }

    public void addDataForAdapter(AutoCompleteTextView view){

        produtos = (List<Produto>) genericDAO.execute(new Produto(), ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());

        adapter = new ArrayAdapter<Produto>
                (context, android.R.layout.select_dialog_item, produtos);

        view.setAdapter(adapter);
        view.setThreshold(1);
        view.setTextColor(Color.RED);

    }

    public void save(Venda venda){
        this.venda = venda;
        onCreate();
    }

    @Override
    public void onCreate() {

        String retornoStr = venda.isValid(context);
        

        if (retornoStr.length() > 1)
            vendaView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(venda, ConstraintUtils.SALVAR, new VendaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                vendaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                vendaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete(Long id) {
//        try {
//            isSave = (Boolean) genericDAO.execute(venda, ConstraintUtils.DELETAR, new VendaServiceImpl()).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        if(isSave)
//            vendaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
//        else
//            vendaView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

//        String retornoStr = venda.isValid(context);
//
//        if (retornoStr.length() > 1)
//            vendaView.onMessageError(retornoStr);
//        else {
//
//            try {
//                isSave = (Boolean) genericDAO.execute(venda, ConstraintUtils.EDITAR, new VendaServiceImpl()).get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (isSave)
//                vendaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
//            else
//                vendaView.onMessageError(context.getResources().getString(R.string.error_operacao));
//
//        }

    }

    @Override
    public void findById() {

        try {
            venda = (Venda) genericDAO.execute(venda, ConstraintUtils.FIND_BY_ID, new VendaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findAll() {


        try {
            vendas = (List<Venda>) genericDAO.execute(venda, ConstraintUtils.FIND_ALL, new VendaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public Produto verificarProdutoPorEAN(String ean){

        for(Produto p : produtos){

            if(p.getEAN().equals(ean)){
                return p;
            }

        }

        return null;
    }


}
