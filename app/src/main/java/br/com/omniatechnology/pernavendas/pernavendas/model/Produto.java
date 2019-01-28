package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IProduto;

public class Produto extends Mercadoria implements Serializable, IProduto {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorCompra;
    private BigDecimal valorVenda;
    private Integer qtde;
    private Integer qtdeMinima;
    private String ean;
    private boolean isAtivo;
    private UnidadeDeMedida unidadeDeMedida;
    private Long dataCadastro;
    private Marca marca;
    private Categoria categoria;
    private String eanPai;
    private Boolean isSubProduto;
    private Integer qtdeSubProduto;

    public Produto(Long id) {
        this();
        this.id = id;
    }

    public Produto() {
        //Por Default o produto inicia-se como Ativo no cadastro
        this.isAtivo = true;
        unidadeDeMedida = new UnidadeDeMedida();
        this.dataCadastro = Calendar.getInstance().getTimeInMillis();
        this.marca = new Marca();
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
        this.nome = nome.toUpperCase().trim();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toUpperCase().trim();
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Integer getQtdeMinima() {
        return qtdeMinima;
    }

    public void setQtdeMinima(Integer qtdeMinima) {
        this.qtdeMinima = qtdeMinima;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String eAN) {
        ean = eAN;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public UnidadeDeMedida getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    @Override
    public String toString() {
        return nome + " - " + descricao;
    }


    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void decrementarProduto(Integer valorADecrementar) {
        if (qtde <= valorADecrementar) {
            qtde = 0;
        } else {
            qtde = qtde - valorADecrementar;
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


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Long dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEanPai() {
        return eanPai;
    }

    public void setEanPai(String eanPai) {
        this.eanPai = eanPai;
    }

    public Boolean getSubProduto() {
        return isSubProduto;
    }

    public void setSubProduto(Boolean subProduto) {
        isSubProduto = subProduto;
    }

    public Integer getQtdeSubProduto() {
        return qtdeSubProduto;
    }

    public void setQtdeSubProduto(Integer qtdeSubProduto) {
        this.qtdeSubProduto = qtdeSubProduto;
    }
}
