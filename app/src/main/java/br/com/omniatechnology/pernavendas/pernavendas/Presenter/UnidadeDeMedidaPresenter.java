package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UnidadeDeMedidaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class UnidadeDeMedidaPresenter implements IUnidadeDeMedidaPresenter {

    IModelView.IUnidadeDeMedidaView unidadeDeMedidaView;
    private Context context;
    UnidadeDeMedida unidadeDeMedida;

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

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

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


}
