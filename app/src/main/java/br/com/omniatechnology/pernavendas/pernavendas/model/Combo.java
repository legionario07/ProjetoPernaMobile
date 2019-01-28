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

    private Long id;
    private String nome;
    private String descricao;
    private List<Produto> produtos;
    private BigDecimal preco;
    private String ean;

    public Combo(Long id) {
        this();
        this.id = id;
    }

    public Combo() {
        this.produtos = new ArrayList<Produto>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }



    @Override
    public String toString() {
        return nome;
    }

    @Override
    public String isValid(Context context) {
        StringBuilder retorno = new StringBuilder();
        if(TextUtils.isEmpty(getNome())){

            retorno.append(context.getResources().getString(R.string.nome_vazio_categoria));
        }

        return retorno.toString();
    }


    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }
}
