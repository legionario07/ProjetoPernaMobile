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
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class UsuariosAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> lista;

    public UsuariosAdapter(Context context, List<Usuario> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Usuario usuario = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_usuarios, null);

        TextView txtUsuarioID = (TextView) convertView.findViewById(R.id.txtUsuarioID);
        TextView txtUsuarioNome = (TextView) convertView.findViewById(R.id.txtUsuarioNome);
        TextView txtUsuarioPerfil = (TextView) convertView.findViewById(R.id.txtUsuarioPerfil);

        txtUsuarioID.setText(usuario.getId().toString());
        txtUsuarioNome.setText(usuario.getUsuario());
        txtUsuarioPerfil.setText(usuario.getPerfil().getNome());

        return convertView;
    }

}
