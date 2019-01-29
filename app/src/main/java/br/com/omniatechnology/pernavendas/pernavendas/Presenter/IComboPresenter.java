package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public interface IComboPresenter extends IModelPresenter{


    void atualizarList(ListView view, TextView txtEmpty);


    void atualizarProdutos(AutoCompleteTextView autoCompleteTextView);

    void setProdutosEmCombo(List<Produto> produtos);
}
