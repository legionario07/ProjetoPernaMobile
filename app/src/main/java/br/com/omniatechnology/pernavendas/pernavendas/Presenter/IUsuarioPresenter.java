package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public interface IUsuarioPresenter extends IModelPresenter{

    void getDadosSpinnerPerfil(Spinner spinner);

    void setSpinnerPerfil(Spinner spinner);

    void atualizarList(ListView view, TextView txtEmpty);

}
