package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ProdutoPresenter implements IProdutoPresenter, ITaskProcess {

    IModelView.IProdutoView produtoView;
    private Context context;
    Produto produto;
    private Boolean isSave;
    private List<Produto> produtos;
    private ProdutosAdapter produtosAdapter;
    
    private RecyclerView view;
    private OperationType operationType;
    private Spinner spnMarca;
    private Spinner spnCategoria;
    private Spinner spnUnidadeDeMedida;
    

    public ProdutoPresenter() {
        produto = new Produto();
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
        //produtos = (List<Produto>) genericDAO.execute(produto, ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());
        adapter = new ProdutosAdapter(context, produtos);

        this.produtosAdapter = adapter;


    }
    
    public void setSpinnerMarca(Spinner spinner){

        this.spnMarca = spinner;

        operationType = OperationType.FIND_ALL_MARCA;
        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new MarcaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
        
    }

    public void setSpinnerUnidadeDeMedida(Spinner spinner){

        this.spnUnidadeDeMedida = spinner;

        operationType = OperationType.FIND_ALL_MARCA;
        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new UnidadeDeMedidaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }


    }

    public void setSpinnerCategoria(Spinner spinner){

        this.spnCategoria = spinner;

        operationType = OperationType.FIND_ALL_MARCA;
        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new CategoriaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

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

    public void atualizarList(RecyclerView view) {

        this.view = view;

        if (produtosAdapter == null) {
            findAll();

        } else {

            produtosAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = produto.isValid(context);

        if (retornoStr.length() > 1)
            produtoView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(produto, ConstraintUtils.SALVAR, new ProdutoServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new ProdutoServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = produto.isValid(context);

        if (retornoStr.length() > 1)
            produtoView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(produto, ConstraintUtils.EDITAR, new ProdutoServiceImpl());
            }catch (Exception e){
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(produto, ConstraintUtils.FIND_BY_ID, new ProdutoServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(produto, ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());
        }catch (Exception e){
            Log.e(ConstraintUtils.TAG, e.getMessage());
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
            }
        });
    }

    @Override
    public void onPostProcess(Serializable serializable) {

        switch (operationType){
            case SAVE: case UPDATE: case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    produtoView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if(operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if(produtos!=null){
                    produtos.clear();
                    produtos.addAll((List<Produto>) serializable);
                }else {
                    produtos = (List<Produto>) serializable;
                }

                if(produtosAdapter == null) {
                    produtosAdapter = new ProdutosAdapter(context, produtos);

                    view.setAdapter(produtosAdapter);
                }else{
                    produtosAdapter.notifyDataSetChanged();
                }

                produtosAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            case FIND_ALL_MARCA:

                List<Marca> marcas = (List<Marca>) serializable;

                ArrayAdapter arrayMarcas = new ArrayAdapter(context, android.R.layout.simple_spinner_item, marcas);
                arrayMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMarca.setAdapter(arrayMarcas);

                break;
            case FIND_ALL_UNIDADE_DE_MEDIDA:

                List<UnidadeDeMedida> unidadeDeMedidas = (List<UnidadeDeMedida>) serializable;

                ArrayAdapter arrayUnidadeDeMedida = new ArrayAdapter(context, android.R.layout.simple_spinner_item, unidadeDeMedidas);
                arrayUnidadeDeMedida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnUnidadeDeMedida.setAdapter(arrayUnidadeDeMedida);

                break;

            case FIND_ALL_CATEGORIA:

                List<Categoria> categorias = (List<Categoria>) serializable;

                ArrayAdapter arrayCategorias = new ArrayAdapter(context, android.R.layout.simple_spinner_item, categorias);
                arrayCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategoria.setAdapter(arrayCategorias);

                break;

            default:
        }

    }

}
