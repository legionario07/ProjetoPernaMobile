package br.com.omniatechnology.pernavendas.pernavendas.model;

import android.text.TextUtils;

import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces.IUsuario;

public class Usuario implements Serializable, IUsuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String usuario;
	private String senha;
	private Perfil perfil;
	
	public Usuario(String usuario, String senha) {
		this();
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public Usuario(Long id) {
		this();
		this.id = id;
	}
	
	
	public Usuario() {
		perfil = new Perfil();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", senha=" + senha + ", perfil=" + perfil + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}


	@Override
	public boolean isValidLogin() {
		return !TextUtils.isEmpty(getUsuario());
	}
}
