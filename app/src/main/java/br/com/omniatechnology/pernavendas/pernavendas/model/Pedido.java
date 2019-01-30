package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.content.Context;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IPedido;


public class Pedido implements Serializable, IPedido {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private BigDecimal valorTotal;
	private Produto produto;
	private Combo combo;
	private Integer total;
	private BigDecimal desconto;

	
	
	public Pedido(BigDecimal valorTotal, Produto produto, Integer total) {
		super();
		this.valorTotal = valorTotal;
		this.produto = produto;
		this.total = total;
	}

	public Mercadoria getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Pedido(Long id) {
		this();
		this.id = id;
	}
	
	public Pedido() {
		this.produto = new Produto();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public String isValid(Context context) {
		StringBuilder retorno = new StringBuilder();
		if(getTotal()<1){

			retorno.append(context.getResources().getString(R.string.total_vazio));
		}

		return retorno.toString();
	}

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}
}
