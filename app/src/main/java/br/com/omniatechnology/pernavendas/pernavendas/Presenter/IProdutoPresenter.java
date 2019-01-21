package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.Spinner;

public interface IProdutoPresenter extends IModelPresenter{

    void getDadoSpinnerMarca(Spinner spinner);
    void getDadoSpinnerCategoria(Spinner spinner);
    void getDadoSpinnerUnidadeDeMedida(Spinner spinner);

    void setSpinnerCategoria(Spinner spinner);
    void setSpinnerMarca(Spinner spinner);
    void setSpinnerUnidadeDeMedida(Spinner spinner);


}
