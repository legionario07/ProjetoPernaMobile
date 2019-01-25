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
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class VendasAdapter extends ArrayAdapter<Venda> {

    private Context context;
    private List<Venda> lista;

    public VendasAdapter(Context context, List<Venda> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Venda venda = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_venda, null);

//        TextView txtItemNomeProduto = (TextView) convertView.findViewById(R.id.txtItemNomeProduto);
//        TextView txtItemQtdeProduto = (TextView) convertView.findViewById(R.id.txtItemQtdeProduto);
//        TextView txtItemDesconto = (TextView) convertView.findViewById(R.id.txtItemDesconto);
//        TextView txtItemValor = (TextView) convertView.findViewById(R.id.txtItemValor);
//
//        txtItemNomeProduto.setText(venda.getProduto().getNome());
//        txtItemQtdeProduto.setText(venda.getTotal().toString());
//        txtItemDesconto.setText(venda.getDesconto().toString());
//        txtItemValor.setText(venda.getValorTotal().toString());

        return convertView;
    }

}
