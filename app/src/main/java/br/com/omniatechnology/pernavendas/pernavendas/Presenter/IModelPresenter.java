package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

public interface IModelPresenter {

    void onCreate();
    void onDelete(Long id);
    void onUpdate();
    void findById();
    void findAll();

}
