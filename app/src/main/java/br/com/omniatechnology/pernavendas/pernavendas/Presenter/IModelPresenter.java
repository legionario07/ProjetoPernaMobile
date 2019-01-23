package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.ListView;

public interface IModelPresenter {

    void onCreate();
    void onDelete();
    void onUpdate();
    void findById();
    void findAll();

}
