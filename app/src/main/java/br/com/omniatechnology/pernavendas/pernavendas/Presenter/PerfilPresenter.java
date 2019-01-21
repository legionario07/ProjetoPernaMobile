package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PerfilServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class PerfilPresenter implements IPerfilPresenter {

    IModelView.IPerfilView perfilView;
    private Context context;
    Perfil perfil;
    private GenericDAO genericDAO;
    private Boolean isSave;

    public PerfilPresenter() {
        perfil = new Perfil();
        genericDAO = new GenericDAO();
    }

    public PerfilPresenter(IModelView.IPerfilView perfilView) {
        this.perfilView = perfilView;
    }

    public PerfilPresenter(IModelView.IPerfilView perfilView, Context context) {
        this();
        this.perfilView = perfilView;
        this.context = context;
    }


    @Override
    public void onCreate() {

        String retornoStr = perfil.isValid(context);



        if (retornoStr.length() > 1)
            perfilView.onMessageError(retornoStr);
        else {



            try {
                isSave = (Boolean) genericDAO.execute(perfil, ConstraintUtils.SALVAR, new PerfilServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                perfilView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                perfilView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete() {
        try {
            isSave = (Boolean) genericDAO.execute(perfil, ConstraintUtils.DELETAR, new PerfilServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            perfilView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            perfilView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        try {
            isSave = (Boolean) genericDAO.execute(perfil, ConstraintUtils.EDITAR, new PerfilServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            perfilView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            perfilView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

    }

    public void addTextWatcherNomePerfil(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                perfil.setNome(s.toString());
            }
        });
    }


}
