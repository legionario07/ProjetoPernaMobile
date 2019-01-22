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
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class MarcasAdapter extends ArrayAdapter<Marca> {

    private Context context;
    private List<Marca> lista;

    public MarcasAdapter(Context context, List<Marca> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Marca marca = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_marca, null);

        TextView txtMarcaID = (TextView) convertView.findViewById(R.id.txtMarcaID);
        TextView txtMarcaNome = (TextView) convertView.findViewById(R.id.txtMarcaNome);

        txtMarcaID.setText(marca.getId().toString());
        txtMarcaNome.setText(marca.getNome());

        return convertView;
    }

}
