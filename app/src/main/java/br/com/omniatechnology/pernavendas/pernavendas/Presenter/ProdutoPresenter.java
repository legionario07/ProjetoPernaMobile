package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ProdutoPresenter implements IProdutoPresenter {

    IModelView.IProdutoView produtoView;
    private Context context;
    Produto produto;
    private GenericDAO genericDAO;
    private Boolean isSave;

    public ProdutoPresenter() {
        produto = new Produto();
        genericDAO = new GenericDAO();
    }

    public ProdutoPresenter(IModelView.IProdutoView produtoView) {
        this.produtoView = produtoView;
    }

    public ProdutoPresenter(IModelView.IProdutoView produtoView, Context context) {
        this();
        this.produtoView = produtoView;
        this.context = context;
    }


    @Override
    public void onCreate() {

        String retornoStr = produto.isValid(context);



        if (retornoStr.length() > 1)
            produtoView.onMessageError(retornoStr);
        else {



            try {
                isSave = (Boolean) genericDAO.execute(produto, ConstraintUtils.SALVAR, new ProdutoServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                produtoView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete() {
        try {
            isSave = (Boolean) genericDAO.execute(produto, ConstraintUtils.DELETAR, new ProdutoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            produtoView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        try {
            isSave = (Boolean) genericDAO.execute(produto, ConstraintUtils.EDITAR, new ProdutoServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            produtoView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            produtoView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void findById() {

    }

    @Override
    public void findAll() {

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
                produto.setQtde(Integer.valueOf(s.toString()));
                ViewUtils.hideKeyboard(context, editText);
            }
        });
    }

}
