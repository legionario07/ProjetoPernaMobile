package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public interface IPerfilPresenter extends IModelPresenter{


    void atualizarList(ListView view, TextView txtEmpty);
    void addTextWatcherNomePerfil(EditText editText);

}
