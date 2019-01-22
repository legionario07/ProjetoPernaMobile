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
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class PerfisAdapter extends ArrayAdapter<Perfil> {

    private Context context;
    private List<Perfil> lista;

    public PerfisAdapter(Context context, List<Perfil> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Perfil perfil = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_perfil, null);

        TextView txtPerfilID = (TextView) convertView.findViewById(R.id.txtPerfilID);
        TextView txtPerfilNome = (TextView) convertView.findViewById(R.id.txtPerfilNome);

        txtPerfilID.setText(perfil.getId().toString());
        txtPerfilNome.setText(perfil.getNome());

        return convertView;
    }

}
