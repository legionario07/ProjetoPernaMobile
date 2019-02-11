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
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class PedidosAdapter extends ArrayAdapter<Pedido> {

    private Context context;
    private List<Pedido> lista;

    public PedidosAdapter(Context context, List<Pedido> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pedido pedido = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_pedido, null);

        TextView txtItemNomeProduto = (TextView) convertView.findViewById(R.id.txtItemNomeProduto);
        TextView txtItemQtdeProduto = (TextView) convertView.findViewById(R.id.txtItemQtdeProduto);
        TextView txtItemDesconto = (TextView) convertView.findViewById(R.id.txtItemDesconto);
        TextView txtItemValor = (TextView) convertView.findViewById(R.id.txtItemValor);


        if(pedido.getProduto().getId()!=null){
            txtItemNomeProduto.setText(((Produto) pedido.getProduto() ).getNome());
        }else{
            txtItemNomeProduto.setText(((Combo) pedido.getCombo() ).getNome());
        }

        txtItemQtdeProduto.setText(pedido.getTotal().toString());
        txtItemDesconto.setText(pedido.getDesconto().toString());
        txtItemValor.setText(pedido.getValorTotal().toString());

        return convertView;
    }

}