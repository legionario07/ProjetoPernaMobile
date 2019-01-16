package br.com.omniatechnology.pernavendas.pernavendas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Produto implements Serializable {

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
	private String EAN;
	private boolean isAtivo;
	private boolean isSubProduto;
	private UnidadeDeMedida unidadeDeMedida;
	private Calendar dataCadastro;
	private Produto produtoPai;
	private Integer qteDivisao;
	
	public Produto(Long id) {
		this();
		this.id = id;
	}
	
	public Produto() {
		//Por Default o produto inicia-se como Ativo no cadastro
		this.isAtivo = true;
		unidadeDeMedida = new UnidadeDeMedida();
		this.dataCadastro = Calendar.getInstance();
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

	public String getEAN() {
		return EAN;
	}

	public void setEAN(String eAN) {
		EAN = eAN;
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
		return "Produto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", valorCompra=" + valorCompra
				+ ", valorVenda=" + valorVenda + ", qtde=" + qtde + ", qtdeMinima=" + qtdeMinima + ", EAN=" + EAN
				+ ", isAtivo=" + isAtivo + ", unidadeDeMedida=" + unidadeDeMedida + "]";
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isSubProduto() {
		return isSubProduto;
	}

	public void setSubProduto(boolean isSubProduto) {
		this.isSubProduto = isSubProduto;
	}

	public Produto getProdutoPai() {
		return produtoPai;
	}

	public void setProdutoPai(Produto produtoPai) {
		this.produtoPai = produtoPai;
	}

	public Integer getQteDivisao() {
		return qteDivisao;
	}

	public void setQteDivisao(Integer qteDivisao) {
		this.qteDivisao = qteDivisao;
	}
	
	public void decrementarProduto(Integer valorADecrementar) {
		if(qtde<=valorADecrementar) {
			qtde = 0;
		}else {
			qtde = qtde - valorADecrementar;
		}
	}

}
