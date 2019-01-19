package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class MarcaPresenter implements IMarcaPresenter {

    IModelView.IMarcaView marcaView;
    private Context context;
    Marca marca;
    private GenericDAO genericDAO;
    private Boolean isSave;

    public MarcaPresenter() {
        marca = new Marca();
        genericDAO = new GenericDAO();
    }

    public MarcaPresenter(IModelView.IMarcaView marcaView) {
        this.marcaView = marcaView;
    }

    public MarcaPresenter(IModelView.IMarcaView marcaView, Context context) {
        this();
        this.marcaView = marcaView;
        this.context = context;
    }


    @Override
    public void onCreate() {

        String retornoStr = marca.isValid(context);



        if (retornoStr.length() > 1)
            marcaView.onMessageError(retornoStr);
        else {



            try {
                isSave = (Boolean) genericDAO.execute(marca, ConstraintUtils.SALVAR, new MarcaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                marcaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete() {
        try {
            isSave = (Boolean) genericDAO.execute(marca, ConstraintUtils.DELETAR, new MarcaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            marcaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        try {
            isSave = (Boolean) genericDAO.execute(marca, ConstraintUtils.EDITAR, new MarcaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            marcaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

    }

    public void addTextWatcherNomeMarca(final EditText editText) {

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
    }


}
