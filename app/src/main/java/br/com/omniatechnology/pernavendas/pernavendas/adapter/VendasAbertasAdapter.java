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
import br.com.omniatechnology.pernavendas.pernavendas.model.Pedido;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class VendasAbertasAdapter extends ArrayAdapter<Venda> {

    private Context context;
    private List<Venda> lista;

    public VendasAbertasAdapter(Context context, List<Venda> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Venda venda = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_venda_aberta, null);

        TextView txtCliente = convertView.findViewById(R.id.txtCliente);
        TextView txtQtdePedidos = convertView.findViewById(R.id.txtQtdePedidos);
        TextView txtValorVenda = convertView.findViewById(R.id.txtValorVenda);
        TextView txtDesconto = convertView.findViewById(R.id.txtDesconto);
        TextView txtDataVenda = convertView.findViewById(R.id.txtDataVenda);

        String nomeCliente = venda.getNomeCliente();

        if (nomeCliente!=null){
            txtCliente.setText(venda.getNomeCliente());
        }else{
            txtCliente.setText("");
        }


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
