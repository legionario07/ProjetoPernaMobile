package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.content.Context;

import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.ICategoria;

public class Categoria implements Serializable, ICategoria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	
	private Categoria(Long id, String nome) {
		this();
		this.id = id;
		this.nome = nome;
	}
	
	private Categoria(Long id) {
		this();
		this.id = id;
	}
	
	private Categoria() {
		
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

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Categoria other = (Categoria) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String isValid(Context context) {
		return null;
	}
}
