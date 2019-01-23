package br.com.omniatechnology.pernavendas.pernavendas.utils;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;

public class SessionUtil {

    private static SessionUtil instance = null;

    private Usuario usuario;
    private List<Produto> produtos;
    private List<Venda> vendas;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    private SessionUtil(){

    }

    public static SessionUtil getInstance(){

        if(instance==null){
            instance = new SessionUtil();
        }

        return instance;
    }
}
