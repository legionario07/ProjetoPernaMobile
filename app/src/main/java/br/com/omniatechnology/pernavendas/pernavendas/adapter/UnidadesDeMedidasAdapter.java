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
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class UnidadesDeMedidasAdapter extends ArrayAdapter<UnidadeDeMedida> {

    private Context context;
    private List<UnidadeDeMedida> lista;

    public UnidadesDeMedidasAdapter(Context context, List<UnidadeDeMedida> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UnidadeDeMedida unidadeDeMedida = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_unidade_de_medida, null);

        TextView txtUnidadeDeMedidaID = (TextView) convertView.findViewById(R.id.txtUnidadeDeMedidaID);
        TextView txtUnidadeDeMedidaNome = (TextView) convertView.findViewById(R.id.txtUnidadeDeMedidaNome);

        txtUnidadeDeMedidaID.setText(unidadeDeMedida.getId().toString());
        txtUnidadeDeMedidaNome.setText(unidadeDeMedida.getTipo());

        return convertView;
    }

}
