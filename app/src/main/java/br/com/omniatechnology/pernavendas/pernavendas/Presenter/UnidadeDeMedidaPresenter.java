package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UnidadesDeMedidasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class UnidadeDeMedidaPresenter implements IUnidadeDeMedidaPresenter {

    IModelView.IUnidadeDeMedidaView unidadeDeMedidaView;
    private Context context;
    UnidadeDeMedida unidadeDeMedida;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<UnidadeDeMedida> unidadesDeMedidas;
    private UnidadesDeMedidasAdapter unidadesDeMedidasAdapter;


    public UnidadeDeMedidaPresenter() {
        unidadeDeMedida = new UnidadeDeMedida();
        genericDAO = new GenericDAO();
    }

    public UnidadeDeMedidaPresenter(IModelView.IUnidadeDeMedidaView unidadeDeMedidaView) {
        this.unidadeDeMedidaView = unidadeDeMedidaView;
    }

    public UnidadeDeMedidaPresenter(IModelView.IUnidadeDeMedidaView unidadeDeMedidaView, Context context) {
        this();
        this.unidadeDeMedidaView = unidadeDeMedidaView;
        this.context = context;
    }

    
    @Override
    public void onCreate() {

        String retornoStr = unidadeDeMedida.isValid(context);

        Boolean isSave = false;

        if (retornoStr.length() > 1)
            unidadeDeMedidaView.onMessageError(retornoStr);
        else {

            GenericDAO genericDAO = new GenericDAO();

            try {
                isSave = (Boolean) genericDAO.execute(unidadeDeMedida, ConstraintUtils.SALVAR, new UnidadeDeMedidaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                unidadeDeMedidaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                unidadeDeMedidaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete() {

        try {
            isSave = (Boolean) genericDAO.execute(unidadeDeMedida, ConstraintUtils.DELETAR, new UnidadeDeMedidaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            unidadeDeMedidaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            unidadeDeMedidaView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        String retornoStr = unidadeDeMedida.isValid(context);

        Boolean isSave = false;

        if (retornoStr.length() > 1)
            unidadeDeMedidaView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(unidadeDeMedida, ConstraintUtils.EDITAR, new UnidadeDeMedidaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                unidadeDeMedidaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
            else
                unidadeDeMedidaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void findById() {

        try {
            unidadeDeMedida = (UnidadeDeMedida) genericDAO.execute(unidadeDeMedida, ConstraintUtils.FIND_BY_ID, new UnidadeDeMedidaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findAll() {

        try {
            unidadesDeMedidas = (List<UnidadeDeMedida>) genericDAO.execute(unidadeDeMedida, ConstraintUtils.FIND_ALL, new UnidadeDeMedidaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    }


    public void atualizarList(ListView view) {


        if (unidadesDeMedidasAdapter == null) {
            unidadesDeMedidas = (List<UnidadeDeMedida>) genericDAO.execute(new UnidadeDeMedida(), ConstraintUtils.FIND_ALL, new UnidadeDeMedidaServiceImpl());

            unidadesDeMedidasAdapter = new UnidadesDeMedidasAdapter(context, unidadesDeMedidas);

            view.setAdapter(unidadesDeMedidasAdapter);

        } else {

            unidadesDeMedidasAdapter.notifyDataSetChanged();

        }
    }

}
