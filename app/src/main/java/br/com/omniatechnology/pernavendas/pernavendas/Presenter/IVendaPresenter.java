package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;

public interface IVendaPresenter extends IModelPresenter{

    void save(Venda venda);
    void saveSemDecrementar(Venda venda);
    Mercadoria verificarProdutoPorEAN(String EAN);
    void atualizarListaPedidos(ListView listView);
    void atualizarListaVenda(ListView listView, TextView textView);
    void addDataForAdapter(AutoCompleteTextView autoCompleteTextView);
    void atualizarListaVendasAbertas(ListView listView, TextView textView);
    void onCreateSemDecrementar();
    void findAllVendasAbertas();


}
