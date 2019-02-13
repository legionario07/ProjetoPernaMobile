package br.com.omniatechnology.pernavendas.pernavendas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Mercadoria implements Serializable {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorVenda;
    private Integer qtde;
    private Integer qtdeMinima;
    private Long dataCadastro;
    private String ean;
    private boolean isAtivo;
    private Categoria categoria;
    private UnidadeDeMedida unidadeDeMedida;

    public Mercadoria(){
        this.setAtivo(true);
        setUnidadeDeMedida(new UnidadeDeMedida());
        this.setDataCadastro(Calendar.getInstance().getTimeInMillis());
        this.qtdeMinima = 0;
        this.valorVenda = BigDecimal.ZERO;
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

    public Long getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Long dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean.trim();
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public UnidadeDeMedida getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }
}
