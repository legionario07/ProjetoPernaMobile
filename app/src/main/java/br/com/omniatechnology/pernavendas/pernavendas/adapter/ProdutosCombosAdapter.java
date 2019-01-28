package br.com.omniatechnology.pernavendas.pernavendas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class ProdutosCombosAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private List<Produto> lista;

    public ProdutosCombosAdapter(Context context, List<Produto> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Produto produto = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_new_combo, null);

        TextView txtComboProduto = (TextView) convertView.findViewById(R.id.txtComboProduto);
        TextView txtComboMarca = (TextView) convertView.findViewById(R.id.txtComboMarca);
        TextView txtComboValor = (TextView) convertView.findViewById(R.id.txtComboValor);
        TextView txtComboEan = (TextView) convertView.findViewById(R.id.txtComboEan);

        txtComboProduto.setText(produto.getNome());
        txtComboMarca.setText(produto.getMarca().getNome());
        txtComboValor.setText(produto.getValorVenda().toString());
        txtComboEan.setText(produto.getEan());

        return convertView;
    }

}
