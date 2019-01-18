package br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces;

import android.content.Context;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public interface IProduto {

    boolean create();
    boolean delete();
    boolean update();
    Produto findById();

    String isValid(Context context);

}
