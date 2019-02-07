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
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UnidadesDeMedidasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class UnidadeDeMedidaPresenter implements IUnidadeDeMedidaPresenter {

    IModelView.IUnidadeDeMedidaView unidadeDeMedidaView;
    private Context context;
    UnidadeDeMedida unidadeDeMedida;
    private List<UnidadeDeMedida> unidadesDeMedidas = new ArrayList<>();
    private UnidadesDeMedidasAdapter unidadesDeMedidasAdapter;

    private ListView view;
    private TextView txtEmpty;



    public UnidadeDeMedidaPresenter() {
        unidadeDeMedida = new UnidadeDeMedida();
    }


    public UnidadeDeMedidaPresenter(IModelView.IUnidadeDeMedidaView unidadeDeMedidaView, Context context) {
        this();
        this.unidadeDeMedidaView = unidadeDeMedidaView;
        this.context = context;
    }


    public void atualizarList(ListView view, TextView txtEmpty) {

        this.view = view;
        this.txtEmpty = txtEmpty;

        if (unidadesDeMedidasAdapter == null) {
            findAll();

        } else {

            unidadesDeMedidasAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void setItem(IModel model) {
        this.unidadeDeMedida = (UnidadeDeMedida) model;
    }



    public void addTextWatcherTipoUnidadeDeMedida(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                unidadeDeMedida.setTipo(s.toString());
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

    @Override
    public void onCreate() {

        String retornoStr = unidadeDeMedida.isValid(context);


        if (retornoStr.length() == 0) {

            new UnidadeDeMedidaServiceImpl().save(unidadeDeMedida)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<UnidadeDeMedida>() {
                        @Override
                        public void onCompleted() {
                            unidadeDeMedidaView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            unidadeDeMedidaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(UnidadeDeMedida unidadeDeMedida) {

                        }
                    });


        } else {
            unidadeDeMedidaView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new UnidadeDeMedidaServiceImpl().delete(id)
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
                        unidadeDeMedidaView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        unidadeDeMedidaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
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

        new UnidadeDeMedidaServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<UnidadeDeMedida>>() {
                    @Override
                    public void onCompleted() {

                        if (unidadesDeMedidasAdapter == null) {
                            unidadesDeMedidasAdapter = new UnidadesDeMedidasAdapter(context, unidadesDeMedidas);

                            view.setAdapter(unidadesDeMedidasAdapter);
                        } else {
                            unidadesDeMedidasAdapter.notifyDataSetChanged();
                        }

                        if (unidadesDeMedidas.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        unidadesDeMedidasAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<UnidadeDeMedida> unidadesDeMedidasTemp) {
                        if (unidadesDeMedidas != null) {
                            unidadesDeMedidas.clear();
                            unidadesDeMedidas.addAll(unidadesDeMedidasTemp);
                        }
                    }
                });


    }

}
