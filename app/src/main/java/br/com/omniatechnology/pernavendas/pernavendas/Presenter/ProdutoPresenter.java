package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ProdutoPresenter implements IProdutoPresenter {

    IModelView.IProdutoView produtoView;
    private Context context;
    Produto produto;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<Produto> produtos;
    private ProdutosAdapter produtosAdapter;

    public ProdutoPresenter() {
        produto = new Produto();
        genericDAO = new GenericDAO();
    }

    public ProdutoPresenter(IModelView.IProdutoView produtoView) {
        this.produtoView = produtoView;
    }

    public ProdutoPresenter(IModelView.IProdutoView produtoView, Context context) {
        this();
        this.produtoView = produtoView;
        this.context = context;
    }

    public ProdutoPresenter(IModelView.IProdutoView produtoView, Context context, ProdutosAdapter adapter) {
        this(produtoView, context);
        produtos = (List<Produto>) genericDAO.execute(produto, ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());
        adapter = new ProdutosAdapter(context, produtos);

        this.produtosAdapter = adapter;


    }

    public void initialize(RecyclerView recyclerView){
        recyclerView.setAdapter(produtosAdapter);

    }


    public void setSpinnerMarca(Spinner spinner){
       
        List<Marca> marcas = null;
        try {
            marcas = (List<Marca>) genericDAO.execute(false, ConstraintUtils.FIND_ALL, new MarcaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter arrayMarcas = new ArrayAdapter(context, android.R.layout.simple_spinner_item, marcas);
        arrayMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayMarcas);
        
    }

    public void setSpinnerUnidadeDeMedida(Spinner spinner){

        List<UnidadeDeMedida> unidadesDeMedidas = null;
        try {
            unidadesDeMedidas = (List<UnidadeDeMedida>) genericDAO.execute(false, ConstraintUtils.FIND_ALL, new UnidadeDeMedidaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter arrayUnidadesDeMedidas = new ArrayAdapter(context, android.R.layout.simple_spinner_item, unidadesDeMedidas);
        arrayUnidadesDeMedidas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayUnidadesDeMedidas);


    }

    public void setSpinnerCategoria(Spinner spinner){

        List<Categoria> categorias = null;
        try {
            categorias = (List<Categoria>) genericDAO.execute(false, ConstraintUtils.FIND_ALL, new CategoriaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter arrayCategorias = new ArrayAdapter(context, android.R.layout.simple_spinner_item, categorias);
        arrayCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayCategorias);


    }

    public void getDadoSpinnerMarca(Spinner spinner){
        produto.setMarca((Marca) getDadosDeSpinner(spinner));
    }

    public void getDadoSpinnerCategoria(Spinner spinner){
        produto.setCategoria((Categoria) getDadosDeSpinner(spinner));
    }

    public void getDadoSpinnerUnidadeDeMedida(Spinner spinner){
        produto.setUnidadeDeMedida((UnidadeDeMedida) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner){
        return (IModel) spinner.getSelectedItem();
    }

    @Override
    public void onCreate() {

        String retornoStr = produto.isValid(context);

        if (retornoStr.length() > 1)
            produtoView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(produto, ConstraintUtils.SALVAR, new ProdutoServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                produtoView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete(Long id) {

        produto = new Produto(id);

        try {
            isSave = (Boolean) genericDAO.execute(produto, ConstraintUtils.DELETAR, new ProdutoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            produtoView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        String retornoStr = produto.isValid(context);

        if (retornoStr.length() > 1)
            produtoView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(produto, ConstraintUtils.EDITAR, new ProdutoServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                produtoView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
            else
                produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void findById() {

        try {
            produto = (Produto) genericDAO.execute(produto, ConstraintUtils.FIND_BY_ID, new ProdutoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findAll() {

        try {
            produtos = (List<Produto>) genericDAO.execute(produto, ConstraintUtils.FIND_ALL, new ProdutoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void addTextWatcherNomeProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setNome(s.toString());
            }
        });
    }

    public void addTextWatcherDescricaoProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setDescricao(s.toString());
            }
        });
    }

    public void addTextWatcherValorVendaProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setValorVenda(new BigDecimal(s.toString()));
            }
        });
    }

    public void addTextWatcherQtdeMinProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setQtdeMinima(new Integer(s.toString()));
            }
        });
    }

    public void addTextWatcherQtdeProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setQtde(Integer.valueOf(s.toString()));
                ViewUtils.hideKeyboard(context, editText);
            }
        });
    }

    public void addTextWatcherEanProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setEAN(s.toString());
            }
        });
    }

}
