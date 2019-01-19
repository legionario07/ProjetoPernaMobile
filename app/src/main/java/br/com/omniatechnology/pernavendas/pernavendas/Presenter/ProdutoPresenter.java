package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IProdutoView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class ProdutoPresenter implements IProdutoPresenter {

    IProdutoView.INewProdutoView produtoView;
    private Context context;
    Produto produto;

    public ProdutoPresenter() {
        produto = new Produto();
    }

    public ProdutoPresenter(IProdutoView.INewProdutoView produtoView) {
        this.produtoView = produtoView;
    }

    public ProdutoPresenter(IProdutoView.INewProdutoView produtoView, Context context) {
        this();
        this.produtoView = produtoView;
        this.context = context;
    }

    @Override
    public void onCreate() {

        String retornoStr = produto.isValid(context);

        Boolean isSave = false;

        if (retornoStr.length() > 1)
            produtoView.onCreateError(retornoStr);
        else {

            GenericDAO genericDAO = new GenericDAO();

            try {
                isSave = genericDAO.execute(produto, ConstraintUtils.SALVAR, new ProdutoServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                produtoView.onCreateSucess();
            else
                produtoView.onCreateError(context.getResources().getString(R.string.error_operacao));

        }

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
