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
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CombosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ComboServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ComboPresenter implements IComboPresenter, ITaskProcess {

    IModelView.IComboView comboView;
    private Context context;
    Combo combo;
    private Boolean isSave;
    private List<Combo> combos;
    private CombosAdapter combosAdapter;
    private OperationType operationType;

    private Spinner spnCategoria;
    private Spinner spnUnidadeDeMedida;

    private AutoCompleteTextView actProdutos;

    private ArrayAdapter<Produto> arrayAdapter;

    private ListView view;
    private TextView txtEmpty;
    private List<Produto> produtos;

    public ComboPresenter() {
        combo = new Combo();
    }

    public ComboPresenter(IModelView.IComboView comboView) {
        this.comboView = comboView;
    }

    public ComboPresenter(IModelView.IComboView comboView, Context context) {
        this();
        this.comboView = comboView;
        this.context = context;
    }

    public void initializeSpinner(Spinner spnCategoria, Spinner spnUnidadeDeMedida) {
        this.spnUnidadeDeMedida = spnUnidadeDeMedida;
        this.spnCategoria = spnCategoria;
    }

    public void atualizarProdutos(AutoCompleteTextView autoCompleteTextView){

        this.actProdutos = autoCompleteTextView;

        findAllProdutos();

        actProdutos.setThreshold(1);
        actProdutos.setTextColor(Color.RED);

    }

    public void setSpinnerCategoria() {

        operationType = OperationType.FIND_ALL_CATEGORIA;
        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new CategoriaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    public void setSpinnerUnidadeDeMedida() {

        operationType = OperationType.FIND_ALL_UNIDADE_DE_MEDIDA;
        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new UnidadeDeMedidaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }


    }

    @Override
    public void setProdutosEmCombo(List<Produto> produtos) {
        this.combo.setProdutos(produtos);
    }

    public void atualizarList(ListView view, TextView txtEmpty) {

        this.view = view;
        this.txtEmpty = txtEmpty;

        if (combosAdapter == null) {
            findAll();

        } else {

            combosAdapter.notifyDataSetChanged();

        }

    }

    public void setItem(IModel model) {
        this.combo = (Combo) model;
    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        getDadoSpinnerCategoria(spnCategoria);
        getDadoSpinnerUnidadeDeMedida(spnUnidadeDeMedida);
        String retornoStr = combo.isValid(context);

        if (retornoStr.length() > 1)
            comboView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(combo, ConstraintUtils.SALVAR, new ComboServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new ComboServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;
        String retornoStr = combo.isValid(context);

        if (retornoStr.length() > 1)
            comboView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(combo, ConstraintUtils.EDITAR, new ComboServiceImpl());
            } catch (Exception e) {
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(combo, ConstraintUtils.FIND_BY_ID, new ComboServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(combo, ConstraintUtils.FIND_ALL, new ComboServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    public void addTextWatcherNomeCombo(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                combo.setNome(s.toString());
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

    public void addTextWatcherQtdeCombo(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                combo.setQtde(Integer.valueOf(s.toString()));
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

    public void addTextWatcherPrecoVenda(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                combo.setValorVenda(new BigDecimal(s.toString()));
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

    public void addTextWatcherEanCombo(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                combo.setEan(s.toString());
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



    public void addTextWatcherDescricaoCombo(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                combo.setDescricao(s.toString());
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

        switch (operationType) {
            case SAVE:
            case UPDATE:
            case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    comboView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    comboView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if (operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if (combos != null) {
                    combos.clear();
                    List<Combo> comboTemp = (List<Combo>) serializable;
                    if (comboTemp != null) {
                        combos.addAll(comboTemp);
                    }
                } else {
                    combos = (List<Combo>) serializable;
                    if (combos == null) {
                        combos = new ArrayList<>();
                    }
                }

                if (combosAdapter == null) {
                    combosAdapter = new CombosAdapter(context, combos);

                    view.setAdapter(combosAdapter);
                } else {
                    combosAdapter.notifyDataSetChanged();
                }

                if (combos.isEmpty()) {
                    view.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.GONE);
                }

                combosAdapter.notifyDataSetChanged();

                break;

            case FIND_ALL_PRODUTO:

                produtos = (List<Produto>) serializable;

                if (produtos == null) {
                    produtos = new ArrayList<>();
                }

                arrayAdapter = new ArrayAdapter<Produto>
                        (context, android.R.layout.select_dialog_item, produtos);

                actProdutos.setAdapter(arrayAdapter);

                setSpinnerCategoria();

                break;

            case FIND_ALL_UNIDADE_DE_MEDIDA:

                List<UnidadeDeMedida> unidadeDeMedidas = (List<UnidadeDeMedida>) serializable;

                if(unidadeDeMedidas == null){
                    unidadeDeMedidas = new ArrayList<>();
                }

                ArrayAdapter arrayUnidadeDeMedida = new ArrayAdapter(context, android.R.layout.simple_spinner_item, unidadeDeMedidas);
                arrayUnidadeDeMedida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnUnidadeDeMedida.setAdapter(arrayUnidadeDeMedida);



                break;

            case FIND_ALL_CATEGORIA:

                List<Categoria> categorias = (List<Categoria>) serializable;
                if(categorias==null){
                    categorias = new ArrayList<>();
                }

                ArrayAdapter arrayCategorias = new ArrayAdapter(context, android.R.layout.simple_spinner_item, categorias);
                arrayCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategoria.setAdapter(arrayCategorias);

                setSpinnerUnidadeDeMedida();

                break;

            case FIND_BY_ID:

                break;

            default:
        }

    }

    public Produto verificarProdutoPorEAN(String ean) {

        for (Produto p : produtos) {

            if (p.getEan().equals(ean)) {
                return p;

            }
        }

        return null;

    }



    public void findAllProdutos() {

        operationType = OperationType.FIND_ALL_PRODUTO;

        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new ProdutoServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    public void getDadoSpinnerCategoria(Spinner spinner) {
        combo.setCategoria((Categoria) getDadosDeSpinner(spinner));
    }

    public void getDadoSpinnerUnidadeDeMedida(Spinner spinner) {
        combo.setUnidadeDeMedida((UnidadeDeMedida) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner) {
        return (IModel) spinner.getSelectedItem();
    }



}
