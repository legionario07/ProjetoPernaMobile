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
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ComboServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class ComboPresenter implements IComboPresenter {

    IModelView.IComboView comboView;
    private Context context;
    Combo combo;
    private List<Combo> combos = new ArrayList<>();
    private CombosAdapter combosAdapter;

    private Spinner spnCategoria;
    private Spinner spnUnidadeDeMedida;

    private List<UnidadeDeMedida> unidadesDeMedidas = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();

    private AutoCompleteTextView actProdutos;

    private ArrayAdapter<Produto> arrayAdapter;

    private ListView view;
    private TextView txtEmpty;
    private List<Produto> produtos = new ArrayList<>();

    public ComboPresenter() {
        combo = new Combo();
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


    /**
     * Ira chamar o setSpinnerMarca -> setSpinnerUnidadeDeMedida -> setSpinnerCategorias
     */
    public void initializeSpinnersWithData(){
        ViewHelper.showProgressDialog(context);
        setSpinnerCategoria();
        setSpinnerUnidadeDeMedida();
    }

    public void setSpinnerUnidadeDeMedida() {

        new UnidadeDeMedidaServiceImpl().findAll()
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        comboView.fillDataInSpinnerUnidadeDeMedida();
                        comboView.onLoadeadEntitys();
                    }
                })
                .subscribe(new Observer<List<UnidadeDeMedida>>() {
                    @Override
                    public void onCompleted() {

                        ArrayAdapter arrayUnidadeDeMedidas = new ArrayAdapter(context, android.R.layout.simple_spinner_item, unidadesDeMedidas);
                        arrayUnidadeDeMedidas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnUnidadeDeMedida.setAdapter(arrayUnidadeDeMedidas);

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<UnidadeDeMedida> unidadeDeMedidasTemp) {
                        unidadesDeMedidas.addAll(unidadeDeMedidasTemp);
                    }
                });


    }

    public void setSpinnerCategoria() {

        new CategoriaServiceImpl().findAll()
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        comboView.fillDataInSpinnerCategoria();
                    }
                })
                .subscribe(new Observer<List<Categoria>>() {
                    @Override
                    public void onCompleted() {

                        ArrayAdapter arrayCategorias = new ArrayAdapter(context, android.R.layout.simple_spinner_item, categorias);
                        arrayCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnCategoria.setAdapter(arrayCategorias);

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Categoria> categoriasTemp) {
                        categorias.addAll(categoriasTemp);
                    }
                });


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

        String retornoStr = combo.isValid(context);

        getDadosSpinnerCategoria(spnCategoria);
        getDadosSpinnerUnidadeDeMedida(spnUnidadeDeMedida);


        if (retornoStr.length() == 0) {

            new ComboServiceImpl().save(combo)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Combo>() {
                        @Override
                        public void onCompleted() {
                            comboView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            comboView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Combo combo) {

                        }
                    });


        } else {
            comboView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new ComboServiceImpl().delete(id)
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        findAll();
                    }
                })
                .doOnUnsubscribe(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        comboView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        comboView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean isSave) {

                    }
                });
    }


    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

        new ComboServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doOnUnsubscribe(
                        ViewHelper.closeProgressDialogAction(context)
                )
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        comboView.onLoadeadEntitys();
                    }
                })
                .subscribe(new Observer<List<Combo>>() {
                    @Override
                    public void onCompleted() {

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

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Combo> combosTemp) {
                        if (combos != null) {
                            combos.clear();
                            combos.addAll(combosTemp);
                        }
                    }
                });

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
                if(!s.toString().isEmpty()) {
                    combo.setQtde(Integer.valueOf(s.toString()));
                }
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
                if(!s.toString().isEmpty()) {
                    combo.setValorVenda(new BigDecimal(s.toString()));
                }
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


    public Produto verificarProdutoPorEAN(String ean) {

        for (Produto p : produtos) {

            if (p.getEan().equals(ean)) {
                return p;

            }
        }

        return null;

    }



    public void findAllProdutos() {

        new ProdutoServiceImpl().findAll()
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        comboView.onLoadeadEntitys();
                    }
                })
                .subscribe(new Observer<List<Produto>>() {
                    @Override
                    public void onCompleted() {

                        arrayAdapter = new ArrayAdapter<Produto>
                                (context, android.R.layout.select_dialog_item, produtos);

                        actProdutos.setAdapter(arrayAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Produto> produtosTemp) {
                        produtos.addAll(produtosTemp);
                    }
                });


    }

    public void getDadosSpinnerCategoria(Spinner spinner) {
        combo.setCategoria((Categoria) getDadosDeSpinner(spinner));
    }

    public void getDadosSpinnerUnidadeDeMedida(Spinner spinner) {
        combo.setUnidadeDeMedida((UnidadeDeMedida) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner) {
        return (IModel) spinner.getSelectedItem();
    }



}
