package br.com.omniatechnology.pernavendas.pernavendas.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

public interface IService<T extends IModel> extends Serializable {

    IModel findById(Long id) throws Exception;
    List<T> findAll() throws IOException;
    boolean save(T model);
    boolean delete(Long id) throws IOException;
}
