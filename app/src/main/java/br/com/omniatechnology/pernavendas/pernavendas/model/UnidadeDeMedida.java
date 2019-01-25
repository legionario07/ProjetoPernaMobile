package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.content.Context;
import android.text.TextUtils;

import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IUnidadeDeMedida;

public class UnidadeDeMedida implements IUnidadeDeMedida, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String tipo;
	
	public UnidadeDeMedida(Long id, String tipo) {
		this(id);
		this.tipo = tipo;
	}
	
	public UnidadeDeMedida(Long id) {
		this();
		this.id = id;
	}
	
	public UnidadeDeMedida() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo.toUpperCase().trim();
	}

	@Override
	public String toString() {
		return tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		UnidadeDeMedida other = (UnidadeDeMedida) obj;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String isValid(Context context) {
		StringBuilder retorno = new StringBuilder();
		if(TextUtils.isEmpty(getTipo())){

			retorno.append(context.getResources().getString(R.string.tipo_vazio));
		}

		return retorno.toString();
	}
}
