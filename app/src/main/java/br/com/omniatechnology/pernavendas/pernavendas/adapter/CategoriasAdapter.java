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



/**
 * Created by PauLinHo on 10/08/2017.
 */

public class CategoriasAdapter extends ArrayAdapter<Categoria> {

    private Context context;
    private List<Categoria> lista;

    public CategoriasAdapter(Context context, List<Categoria> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Categoria categoria = new Categoria();
        categoria = (Categoria) this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_categoria, null);

        TextView txtCategoriaID = (TextView) convertView.findViewById(R.id.txtCategoriaID);
        TextView txtCategoriaNome = (TextView) convertView.findViewById(R.id.txtCategoriaNome);

        txtCategoriaID.setText(categoria.getId().toString());
        txtCategoriaNome.setText(categoria.getNome());

        return convertView;
    }

}
