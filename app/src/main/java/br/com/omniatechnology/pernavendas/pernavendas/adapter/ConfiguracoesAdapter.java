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
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;


/**
 * Created by PauLinHo on 10/08/2017.
 */

public class ConfiguracoesAdapter extends ArrayAdapter<Configuracao> {

    private Context context;
    private List<Configuracao> lista;

    public ConfiguracoesAdapter(Context context, List<Configuracao> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Configuracao configuracao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_configuracao, null);

        TextView txtConfiguracaoID = (TextView) convertView.findViewById(R.id.txtConfiguracaoID);
        TextView txtConfiguracaoPropriedade = (TextView) convertView.findViewById(R.id.txtConfiguracaoPropriedade);
        TextView txtConfiguracaoValor = (TextView) convertView.findViewById(R.id.txtConfiguracaoValor);

        txtConfiguracaoID.setText(configuracao.getId().toString());
        txtConfiguracaoPropriedade.setText(configuracao.getPropriedade());
        txtConfiguracaoValor.setText(configuracao.getValor());

        return convertView;
    }

}
