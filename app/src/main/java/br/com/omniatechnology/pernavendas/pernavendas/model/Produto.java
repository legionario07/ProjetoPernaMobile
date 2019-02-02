package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.content.Context;
import android.text.TextUtils;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IProduto;

public class Produto extends Mercadoria implements Serializable, IProduto {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BigDecimal valorCompra;

    private Marca marca;

    private String eanPai;
    private Boolean subProduto;
    private Integer qtdeSubProduto;

    public Produto(Long id) {
        this();
        setId(id);
    }

    public Produto() {
        super();
        this.qtdeSubProduto = 0;
    }


    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    @Override
    public String toString() {
        return getNome() + " - " + getDescricao();
    }


    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void decrementarProduto(Integer valorADecrementar) {
        if (getQtde() <= valorADecrementar) {
            setQtde(0);
        } else {
            setQtde( getQtde() - valorADecrementar);
        }
    }


    @Override
    public String isValid(Context context) {
        StringBuilder retorno = new StringBuilder();
        if (TextUtils.isEmpty(getNome())) {
            retorno.append(context.getResources().getString(R.string.nome_vazio));
        } else if (TextUtils.isEmpty(getDescricao())) {
            retorno.append(context.getResources().getString(R.string.descricao_vazio));
        } else if (TextUtils.isEmpty(getValorVenda().toString())) {
            retorno.append(context.getResources().getString(R.string.valor_venda_vazio));
        }else if(getSubProduto() && TextUtils.isEmpty(getEanPai())){
            retorno.append(context.getResources().getString(R.string.se_sub_produto_deve_haver_ean_pai));
        }

        return retorno.toString();
    }



    public String getEanPai() {
        return eanPai;
    }

    public void setEanPai(String eanPai) {
        this.eanPai = eanPai;
    }

    public Boolean getSubProduto() {
        return subProduto;
    }

    public void setSubProduto(Boolean subProduto) {
        this.subProduto = subProduto;
    }

    public Integer getQtdeSubProduto() {
        return qtdeSubProduto;
    }

    public void setQtdeSubProduto(Integer qtdeSubProduto) {
        this.qtdeSubProduto = qtdeSubProduto;
    }
}
