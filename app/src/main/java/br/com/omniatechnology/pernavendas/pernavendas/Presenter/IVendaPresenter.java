package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.ListView;

import br.com.omniatechnology.pernavendas.pernavendas.model.Mercadoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;

public interface IVendaPresenter extends IModelPresenter{

    void save(Venda venda);
    Mercadoria verificarProdutoPorEAN(String EAN);
    void atualizarListaPedidos(ListView listView);


}
