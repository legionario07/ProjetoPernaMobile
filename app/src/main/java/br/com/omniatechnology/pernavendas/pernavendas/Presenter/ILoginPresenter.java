package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.widget.EditText;

public interface ILoginPresenter {

    void onLogin();
    void addSenhaTextWatcher(EditText editText);
    void addUsuarioTextWatcher(EditText editText);

}
