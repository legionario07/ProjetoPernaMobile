package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

public interface IModelPresenter {

    void onCreate();
    void onDelete(Long id);
    void findById();
    void findAll();
    void setItem(IModel model);

}
