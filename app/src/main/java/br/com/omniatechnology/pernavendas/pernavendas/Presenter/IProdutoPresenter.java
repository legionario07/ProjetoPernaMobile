package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public interface IProdutoPresenter extends IModelPresenter{

    void getDadoSpinnerMarca(Spinner spinner);
    void getDadoSpinnerCategoria(Spinner spinner);
    void getDadoSpinnerUnidadeDeMedida(Spinner spinner);

    void initializeSpinner(Spinner spnMarca, Spinner spnCategoria, Spinner spnUnidadeDeMedida);
    void initializeSpinnersWithData();
    void setSpinnerCategoria();
    void setSpinnerMarca();
    void setSpinnerUnidadeDeMedida();

    void addTextWatcherQtdeSubProduto(EditText editText);
    void addTextWatcherNomeProduto(EditText editText);
    void addTextWatcherValorVendaProduto(EditText editText);
    void addTextWatcherQtdeMinProduto(EditText editText);
    void addTextWatcherQtdeProduto(EditText editText);
    void addTextWatcherEanProduto(EditText editText);
    void addTextWatcherEanPaiProduto(EditText editText);
    void addTextWatcherDescricaoProduto(EditText editText);

    void atualizarList(RecyclerView recyclerView, TextView textView);

    void getDadosForCheckboxSubProduto(CheckBox isSubProduto);
    void getDadosForCheckboxAtivo(CheckBox isSubProduto);


}
