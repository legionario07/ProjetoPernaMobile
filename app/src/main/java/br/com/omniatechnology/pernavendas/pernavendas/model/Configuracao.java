package br.com.omniatechnology.pernavendas.pernavendas.model;

import java.io.Serializable;

public class Configuracao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String propriedade;
	private String valor;
	
	public Configuracao(Long id, String propriedade, String valor) {
		this(id);
		this.propriedade = propriedade;
		this.valor = valor;
	}
	
	public Configuracao(Long id) {
		this();
		this.id = id;
	}
	
	public Configuracao() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropriedade() {
		return propriedade.toUpperCase().trim();
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((propriedade == null) ? 0 : propriedade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuracao other = (Configuracao) obj;
		if (propriedade == null) {
			if (other.propriedade != null)
				return false;
		} else if (!propriedade.equals(other.propriedade))
			return false;
		return true;
	}
	
	

}
