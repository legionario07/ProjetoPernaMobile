package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.io.Serializable;

import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

public interface IService extends Serializable {

    boolean save(IModel model);
}
