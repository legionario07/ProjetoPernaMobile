package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.CategoriasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class CategoriaPresenter implements ICategoriaPresenter {

    IModelView.ICategoriaView categoriaView;
    private Context context;
    Categoria categoria;
    private List<Categoria> categorias = new ArrayList<>();
    private CategoriasAdapter categoriasAdapter;

    private ListView view;
    private TextView txtEmpty;

    public CategoriaPresenter() {
        categoria = new Categoria();
    }


    public CategoriaPresenter(IModelView.ICategoriaView categoriaView, Context context) {
        this();
        this.categoriaView = categoriaView;
        this.context = context;
    }


    public void atualizarList(ListView view, TextView txtEmpty){

        this.view = view;
        this.txtEmpty = txtEmpty;

        if(categoriasAdapter==null){
            findAll();

        }else{

            categoriasAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onCreate() {

        String retornoStr = categoria.isValid(context);


        if (retornoStr.length() == 0) {

            new CategoriaServiceImpl().save(categoria)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Categoria>() {
                        @Override
                        public void onCompleted() {
                            categoriaView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            categoriaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Categoria categoria) {

                        }
                    });


        } else {
            categoriaView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new CategoriaServiceImpl().delete(id)
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
                        categoriaView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        categoriaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
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

        new CategoriaServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Categoria>>() {
                    @Override
                    public void onCompleted() {

                        if (categoriasAdapter == null) {
                            categoriasAdapter = new CategoriasAdapter(context, categorias);

                            view.setAdapter(categoriasAdapter);
                        } else {
                            categoriasAdapter.notifyDataSetChanged();
                        }

                        if (categorias.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        categoriasAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Categoria> categoriasTemp) {
                        if (categorias != null) {
                            categorias.clear();
                            categorias.addAll(categoriasTemp);
                        }
                    }
                });


    }

    public void setItem(IModel model){
        this.categoria = (Categoria) model;
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
