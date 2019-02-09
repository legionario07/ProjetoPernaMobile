package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public interface IComboPresenter extends IModelPresenter{


    void atualizarList(ListView view, TextView txtEmpty);

    void initializeSpinnersWithData();
    void initializeSpinner(Spinner spnCategoria, Spinner spnUnidadeDeMedida);

    void atualizarProdutos(AutoCompleteTextView autoCompleteTextView);

    void setProdutosEmCombo(List<Produto> produtos);

    void getDadosSpinnerCategoria(Spinner spnCategoria);

    void getDadosSpinnerUnidadeDeMedida(Spinner spnUnidadeDeMedida);

    void addTextWatcherPrecoVenda(EditText editText);
    void addTextWatcherEanCombo(EditText editText);
    void addTextWatcherQtdeCombo(EditText editText);
    void addTextWatcherDescricaoCombo(EditText editText);
    void addTextWatcherNomeCombo(EditText editText);
}
