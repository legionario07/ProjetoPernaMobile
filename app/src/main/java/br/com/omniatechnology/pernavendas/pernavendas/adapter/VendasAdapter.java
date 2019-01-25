package br.com.omniatechnology.pernavendas.pernavendas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
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

        TextView txtUsuario = (TextView) convertView.findViewById(R.id.txtUsuario);
        TextView txtQtdePedidos = (TextView) convertView.findViewById(R.id.txtQtdePedidos);
        TextView txtValorVenda = (TextView) convertView.findViewById(R.id.txtValorVenda);
        TextView txtDesconto = (TextView) convertView.findViewById(R.id.txtDesconto);
        TextView txtDataVenda = (TextView) convertView.findViewById(R.id.txtDataVenda);

        txtUsuario.setText(venda.getUsuario().getUsuario());
        txtQtdePedidos.setText(String.valueOf(venda.getPedidos().size()));
        txtValorVenda.setText(venda.getValorTotal().toString());

        BigDecimal desconto = BigDecimal.ZERO;
        for(Pedido p : venda.getPedidos()){
            desconto = desconto.add(p.getDesconto());
        }

        txtDesconto.setText(desconto.toString());

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(venda.getDataVenda());

        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");

        txtDataVenda.setText(sdt.format(c.getTime()));

        return convertView;
    }

}
