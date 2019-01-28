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


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class CombosAdapter extends ArrayAdapter<Combo> {

    private Context context;
    private List<Combo> lista;

    public CombosAdapter(Context context, List<Combo> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Combo combo = this.lista.get(position);

          convertView = LayoutInflater.from(this.context).inflate(R.layout.item_combo, null);

        TextView txtComboID = (TextView) convertView.findViewById(R.id.txtComboID);
        TextView txtComboNome = (TextView) convertView.findViewById(R.id.txtComboNome);
        TextView txtComboTotalItens = (TextView) convertView.findViewById(R.id.txtComboTotalItens);
        TextView txtComboValor = (TextView) convertView.findViewById(R.id.txtComboValor);

        txtComboID.setText(combo.getId().toString());
        txtComboNome.setText(combo.getNome());
        txtComboTotalItens.setText(String.valueOf(combo.getProdutos().size()));
        txtComboValor.setText(combo.getPreco().toString());

        return convertView;
    }

}
