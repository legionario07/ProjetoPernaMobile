package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.content.Context;
import android.text.TextUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IVenda;

public class Venda implements Serializable, IVenda {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Calendar dataVenda;
	private BigDecimal valorTotal;
	private List<Pedido> pedidos;
	private Usuario usuario;
	
	public Venda(Long id) {
		this();
		this.id = id;
	}
	
	public Venda() {
		this.pedidos = new ArrayList<Pedido>();
		this.usuario = new Usuario();
		this.dataVenda = Calendar.getInstance();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Calendar getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(Calendar dataVenda) {
		this.dataVenda = dataVenda;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	@Override
	public String isValid(Context context) {

		StringBuilder retorno = new StringBuilder();
		if(getPedidos()==null || getPedidos().isEmpty()){
			retorno.append(context.getResources().getString(R.string.sem_pedidos));
		}

		return retorno.toString();
	}
}
