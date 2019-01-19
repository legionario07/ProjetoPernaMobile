package br.com.omniatechnology.pernavendas.pernavendas.model.Interfaces;

import android.content.Context;

import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public interface IProduto extends IModel {

    boolean create();
    boolean delete();
    boolean update();
    Produto findById();

    String isValid(Context context);

}
