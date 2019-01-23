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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CategoriasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder.PedidosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class CategoriaPresenter implements ICategoriaPresenter {

    IModelView.ICategoriaView categoriaView;
    private Context context;
    Categoria categoria;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<Categoria> categorias;
    private CategoriasAdapter categoriasAdapter;

    public CategoriaPresenter() {
        categoria = new Categoria();
        genericDAO = new GenericDAO();
    }

    public CategoriaPresenter(IModelView.ICategoriaView categoriaView) {
        this.categoriaView = categoriaView;
    }

    public CategoriaPresenter(IModelView.ICategoriaView categoriaView, Context context) {
        this();
        this.categoriaView = categoriaView;
        this.context = context;
    }


    public void atualizarList(ListView view){

        BigDecimal valorTotal = new BigDecimal("0.0");

        if(categoriasAdapter==null){
            categorias = (List<Categoria>) genericDAO.execute(new Categoria(), ConstraintUtils.FIND_ALL, new CategoriaServiceImpl());

            categoriasAdapter = new CategoriasAdapter(context, categorias);

            view.setAdapter(categoriasAdapter);

        }else{

            categoriasAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onCreate() {

        String retornoStr = categoria.isValid(context);

        if (retornoStr.length() > 1)
            categoriaView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(categoria, ConstraintUtils.SALVAR, new CategoriaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                categoriaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                categoriaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete() {

        try {
            isSave = (Boolean) genericDAO.execute(categoria, ConstraintUtils.DELETAR, new CategoriaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            categoriaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            categoriaView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {


        String retornoStr = categoria.isValid(context);

        if (retornoStr.length() > 1)
            categoriaView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(categoria, ConstraintUtils.EDITAR, new CategoriaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (isSave)
                categoriaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
            else
                categoriaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void findById() {

        try {
            categoria = (Categoria) genericDAO.execute(categoria, ConstraintUtils.FIND_BY_ID, new CategoriaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void findAll() {
        try {
            categorias = (List<Categoria>) genericDAO.execute(categoria, ConstraintUtils.FIND_ALL, new CategoriaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(categorias==null){
            categoriaView.findAllError(context.getString(R.string.nao_possivel_dados_solicitados));
        }else{
            categoriaView.findAllSuccess();
        }

    }

    public void addTextWatcherNomeCategoria(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                categoria.setNome(s.toString());
            }
        });
    }


}
