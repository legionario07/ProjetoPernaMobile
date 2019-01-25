package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UnidadesDeMedidasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UnidadesDeMedidasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class UnidadeDeMedidaPresenter implements IUnidadeDeMedidaPresenter, ITaskProcess {

    IModelView.IUnidadeDeMedidaView unidadeDeMedidaView;
    private Context context;
    UnidadeDeMedida unidadeDeMedida;
    private Boolean isSave;
    private List<UnidadeDeMedida> unidadesDeMedidas;
    private UnidadesDeMedidasAdapter unidadesDeMedidasAdapter;

    private ListView view;
    private OperationType operationType;


    public UnidadeDeMedidaPresenter() {
        unidadeDeMedida = new UnidadeDeMedida();
    }

    public UnidadeDeMedidaPresenter(IModelView.IUnidadeDeMedidaView unidadeDeMedidaView) {
        this.unidadeDeMedidaView = unidadeDeMedidaView;
    }

    public UnidadeDeMedidaPresenter(IModelView.IUnidadeDeMedidaView unidadeDeMedidaView, Context context) {
        this();
        this.unidadeDeMedidaView = unidadeDeMedidaView;
        this.context = context;
    }


    public void atualizarList(ListView view) {

        this.view = view;

        if (unidadesDeMedidasAdapter == null) {
            findAll();

        } else {

            unidadesDeMedidasAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = unidadeDeMedida.isValid(context);

        if (retornoStr.length() > 1)
            unidadeDeMedidaView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(unidadeDeMedida, ConstraintUtils.SALVAR, new UnidadeDeMedidaServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new UnidadeDeMedidaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = unidadeDeMedida.isValid(context);

        if (retornoStr.length() > 1)
            unidadeDeMedidaView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(unidadeDeMedida, ConstraintUtils.EDITAR, new UnidadeDeMedidaServiceImpl());
            } catch (Exception e) {
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(unidadeDeMedida, ConstraintUtils.FIND_BY_ID, new UnidadeDeMedidaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(unidadeDeMedida, ConstraintUtils.FIND_ALL, new UnidadeDeMedidaServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
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
    public void onPostProcess(Serializable serializable) {

        switch (operationType) {
            case SAVE:
            case UPDATE:
            case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    unidadeDeMedidaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    unidadeDeMedidaView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if (operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if (unidadesDeMedidas != null) {
                    unidadesDeMedidas.clear();
                    unidadesDeMedidas.addAll((List<UnidadeDeMedida>) serializable);
                } else {
                    unidadesDeMedidas = (List<UnidadeDeMedida>) serializable;
                }

                if (unidadesDeMedidasAdapter == null) {
                    unidadesDeMedidasAdapter = new UnidadesDeMedidasAdapter(context, unidadesDeMedidas);

                    view.setAdapter(unidadesDeMedidasAdapter);
                } else {
                    unidadesDeMedidasAdapter.notifyDataSetChanged();
                }

                unidadesDeMedidasAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            default:
        }

    }


}
