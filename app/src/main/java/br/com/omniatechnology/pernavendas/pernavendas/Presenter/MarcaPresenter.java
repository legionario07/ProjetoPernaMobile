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
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.helpers.ViewHelper;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;
import rx.Observer;
import rx.functions.Action0;

public class MarcaPresenter implements IMarcaPresenter {

    IModelView.IMarcaView marcaView;
    private Context context;
    Marca marca;
    private List<Marca> marcas = new ArrayList<>();
    private MarcasAdapter marcasAdapter;
    private TextView txtEmpty;
    
    private ListView view;

    public MarcaPresenter() {
        marca = new Marca();
    }


    public MarcaPresenter(IModelView.IMarcaView marcaView, Context context) {
        this();
        this.marcaView = marcaView;
        this.context = context;
    }



    public void atualizarList(ListView view, TextView txtEmpty){

        this.view = view;
        this.txtEmpty = txtEmpty;

        if(marcasAdapter==null){
            findAll();

        }else{

            marcasAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        String retornoStr = marca.isValid(context);


        if (retornoStr.length() == 0) {

            new MarcaServiceImpl().save(marca)
                    .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                    .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                    .subscribe(new Observer<Marca>() {
                        @Override
                        public void onCompleted() {
                            marcaView.onMessageSuccess(context.getString(R.string.save_success));
                        }

                        @Override
                        public void onError(Throwable e) {
                            marcaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
                        }

                        @Override
                        public void onNext(Marca marca) {

                        }
                    });


        } else {
            marcaView.onMessageError(retornoStr);
        }

    }

    @Override
    public void onDelete(Long id) {

        new MarcaServiceImpl().delete(id)
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
                        marcaView.onMessageSuccess(context.getString(R.string.save_success));

                    }

                    @Override
                    public void onError(Throwable e) {
                        marcaView.onMessageError(context.getString(R.string.error_operacao) + " - " + e.getMessage());
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

        new MarcaServiceImpl().findAll()
                .doOnSubscribe(ViewHelper.showProgressDialogAction(context))
                .doAfterTerminate(ViewHelper.closeProgressDialogAction(context))
                .subscribe(new Observer<List<Marca>>() {
                    @Override
                    public void onCompleted() {

                        if (marcasAdapter == null) {
                            marcasAdapter = new MarcasAdapter(context, marcas);

                            view.setAdapter(marcasAdapter);
                        } else {
                            marcasAdapter.notifyDataSetChanged();
                        }

                        if (marcas.isEmpty()) {
                            view.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);
                        }

                        marcasAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Marca> marcasTemp) {
                        if (marcas != null) {
                            marcas.clear();
                            marcas.addAll(marcasTemp);
                        }
                    }
                });


    }
    
    
    @Override
    public void setItem(IModel model) {
        this.marca = (Marca) model;
    }

    public void addTextWatcherNomeMarca(final EditText editText){

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    marca.setNome(s.toString());
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
