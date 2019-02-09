package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class ProdutoPresenter implements IProdutoPresenter {

    IModelView.IProdutoView produtoView;
    private Context context;
    private Produto produto;
    private List<Produto> produtos = new ArrayList<>();
    private List<Marca> marcas = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<UnidadeDeMedida> unidadesDeMedidas = new ArrayList<>();
    private ProdutosAdapter produtosAdapter;

    private RecyclerView view;
    private Spinner spnMarca;
    private Spinner spnCategoria;
    private Spinner spnUnidadeDeMedida;

    private TextView txtEmpty;

    public ProdutoPresenter() {
        produto = new Produto();
    }

    public ProdutoPresenter(IModelView.IProdutoView produtoView, Context context) {
        this();
        this.produtoView = produtoView;
        this.context = context;
    }


    public void initializeSpinner(Spinner spnMarca, Spinner spnCategoria, Spinner spnUnidadeDeMedida) {
        this.spnUnidadeDeMedida = spnUnidadeDeMedida;
        this.spnCategoria = spnCategoria;
        this.spnMarca = spnMarca;
    }

    /**
     * Ira chamar o setSpinnerMarca -> setSpinnerUnidadeDeMedida -> setSpinnerCategorias
     */
    public void initializeSpinnersWithData(){
        ViewHelper.showProgressDialog(context);
        setSpinnerMarca();
        setSpinnerCategoria();
        setSpinnerUnidadeDeMedida();
    }

    public void setSpinnerMarca() {

        new MarcaServiceImpl().findAll()
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                       produtoView.fillDataInSpinnerMarca();
                    }
                })
                .subscribe(new Observer<List<Marca>>() {
                    @Override
                    public void onCompleted() {

                        ArrayAdapter arrayMarcas = new ArrayAdapter(context, android.R.layout.simple_spinner_item, marcas);
                        arrayMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnMarca.setAdapter(arrayMarcas);

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Marca> marcasTemp) {
                        marcas.addAll(marcasTemp);
                    }
                });


    }

    public void setSpinnerUnidadeDeMedida() {

        new UnidadeDeMedidaServiceImpl().findAll()
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                       produtoView.fillDataInSpinnerUnidadeDeMedida();
                       produtoView.onLoadeadEntitys();
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
                        produtoView.fillDataInSpinnerCategoria();
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

    public void getDadoSpinnerMarca(Spinner spinner) {
        produto.setMarca((Marca) getDadosDeSpinner(spinner));
    }

    public void getDadoSpinnerCategoria(Spinner spinner) {
        produto.setCategoria((Categoria) getDadosDeSpinner(spinner));
    }

    public void getDadoSpinnerUnidadeDeMedida(Spinner spinner) {
        produto.setUnidadeDeMedida((UnidadeDeMedida) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner) {
        return (IModel) spinner.getSelectedItem();
    }

    public void atualizarList(RecyclerView view, TextView txtEmpty) {

        this.view = view;
        this.txtEmpty = txtEmpty;

        if (produtosAdapter == null) {
            findAll();

        } else {

            produtosAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        String retornoStr = produto.isValid(context);


        if (retornoStr.length() == 0) {

            new ProdutoServiceImpl().save(produto)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Produto>() {
                        @Override
                        public void onCompleted() {
                            produtoView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            produtoView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Produto produto) {

                        }
                    });


        } else {
            produtoView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new ProdutoServiceImpl().delete(id)
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
                        produtoView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        produtoView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
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

        new ProdutoServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doOnUnsubscribe(
                        ViewHelper.closeProgressDialogAction(context)
                )
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        produtoView.onLoadeadEntitys();
                    }
                })
                .subscribe(new Observer<List<Produto>>() {
                    @Override
                    public void onCompleted() {

                        if (produtosAdapter == null) {
                            produtosAdapter = new ProdutosAdapter(context, produtos);

                            view.setAdapter(produtosAdapter);
                        } else {
                            produtosAdapter.notifyDataSetChanged();
                        }

                        if (produtos.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        produtosAdapter.notifyDataSetChanged();

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


    @Override
    public void setItem(IModel model) {
        this.produto = (Produto) model;
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
                if (!s.toString().isEmpty()) {
                    produto.setValorVenda(new BigDecimal(s.toString()));
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
                if (!s.toString().isEmpty()) {
                    produto.setQtdeMinima(new Integer(s.toString()));
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
                if (!s.toString().isEmpty()) {
                    produto.setQtde(Integer.valueOf(s.toString()));
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
                produto.setEan(s.toString());
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

    public void addTextWatcherEanPaiProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                produto.setEanPai(s.toString());
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

    public void addTextWatcherQtdeSubProduto(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    produto.setQtdeSubProduto(Integer.valueOf(s.toString()));
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

    public void getDadosForCheckboxAtivo(CheckBox checkBox) {
        produto.setAtivo(checkBox.isChecked());
    }

    public void getDadosForCheckboxSubProduto(CheckBox checkBox) {
        produto.setSubProduto(checkBox.isChecked());
    }
}
