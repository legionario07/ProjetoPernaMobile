package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

public interface IComboPresenter extends IModelPresenter{


    void atualizarList(ListView view, TextView txtEmpty);


    void atualizarProdutos(AutoCompleteTextView autoCompleteTextView);
}
