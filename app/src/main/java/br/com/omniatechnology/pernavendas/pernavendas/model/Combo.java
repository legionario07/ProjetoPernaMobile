package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.content.Context;
import android.text.TextUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.ICombo;

public class Combo extends Mercadoria implements Serializable, ICombo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private List<Produto> produtos;

    public Combo(Long id) {
        this();
        setId(id);
    }

    public Combo() {
        this.produtos = new ArrayList<Produto>();
        this.setQtde(0);
    }


    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }


    @Override
    public String toString() {
        return getNome() + " - " + getDescricao();
    }


    @Override
    public String isValid(Context context) {
        StringBuilder retorno = new StringBuilder();
        if (TextUtils.isEmpty(getNome())) {

            retorno.append(context.getResources().getString(R.string.nome_vazio_combo));
        } else if (getValorVenda() == null) {
            retorno.append(context.getResources().getString(R.string.preco_vazio_combo));
        } else if (getQtde() == null) {
            retorno.append(context.getResources().getString(R.string.deve_haver_qtde));
        }

        return retorno.toString();
    }


}
