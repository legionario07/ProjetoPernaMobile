package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.omniatechnology.pernavendas.pernavendas.View.IProdutoView;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public class ProdutoPresenter implements IProdutoPresenter {

    IProdutoView.INewProdutoView produtoView;
    private ProdutoServiceImpl produtoService;
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

        if(retornoStr.length()>1)
            produtoView.onCreateError(retornoStr);
        else {

            produtoView.onCreateSucess();

        }

    }

    public void addTextWatcherNomeProduto(final EditText editText){

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

    public void addTextWatcherQtdeProduto(final EditText editText){

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
            }
        });
    }

}
