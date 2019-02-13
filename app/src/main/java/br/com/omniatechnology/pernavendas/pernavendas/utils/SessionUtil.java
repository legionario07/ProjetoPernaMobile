package br.com.omniatechnology.pernavendas.pernavendas.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.model.UnidadeDeMedida;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.model.Venda;

public class SessionUtil {

    private static SessionUtil instance = null;

    private Usuario usuario;
    private List<Produto> produtos;
    private List<Venda> vendas;
    private List<Categoria> categorias;
    private List<Marca> marcas;
    private List<UnidadeDeMedida> unidadeDeMedidas;
    private Integer qtdeMinEstoqueDefault;
    private Integer notificacaoLigada;

    private SessionUtil(){

        produtos = new ArrayList<>();
        vendas = new ArrayList<>();
        marcas = new ArrayList<>();
        categorias = new ArrayList<>();
        unidadeDeMedidas = new ArrayList<>();

    }


    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public List<UnidadeDeMedida> getUnidadeDeMedidas() {
        return unidadeDeMedidas;
    }

    public void setUnidadeDeMedidas(List<UnidadeDeMedida> unidadeDeMedidas) {
        this.unidadeDeMedidas = unidadeDeMedidas;
    }


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



    public static SessionUtil getInstance(){

        if(instance==null){
            instance = new SessionUtil();
        }

        return instance;
    }

    public Integer getQtdeMinEstoqueDefault() {
        return qtdeMinEstoqueDefault;
    }

    public void setQtdeMinEstoqueDefault(Integer qtdeMinEstoqueDefault) {
        this.qtdeMinEstoqueDefault = qtdeMinEstoqueDefault;
    }

    public Integer getNotificacaoLigada() {
        return notificacaoLigada;
    }

    public void setNotificacaoLigada(Integer notificacaoLigada) {
        this.notificacaoLigada = notificacaoLigada;
    }
}
