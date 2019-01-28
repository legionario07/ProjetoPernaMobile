package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.omniatechnology.pernavendas.pernavendas.Presenter.ComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.Presenter.IComboPresenter;
import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.model.Combo;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.widget.Toast.LENGTH_LONG;

public class NewComboActivity extends AppCompatActivity implements IModelView.IComboView, View.OnClickListener {

    TextInputLayout inpNomeCombo;
    ImageButton btnSave;

    IComboPresenter comboPresenter;

    private Combo combo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_combo_activity);

       // inpNomeCombo = findViewById(R.id.inp_layout_tipo_combo);
        btnSave = findViewById(R.id.btn_save);

        comboPresenter = new ComboPresenter(this, this);
        ((ComboPresenter) comboPresenter).addTextWatcherNomeCombo(inpNomeCombo.getEditText());

        btnSave.setOnClickListener(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(ConstraintUtils.COMBO_INTENT)){
            combo = (Combo) getIntent().getExtras().get(ConstraintUtils.COMBO_INTENT);
            preencherDadosNaView();
        }

    }

    public void preencherDadosNaView(){

        inpNomeCombo.getEditText().setText(combo.getNome());
        comboPresenter.setItem(combo);

    }

    @Override
    public void onMessageSuccess(String message) {
        Toast.makeText(this, message ,Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onMessageError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_save:

                comboPresenter.onCreate();

                break;

                default:
        }

    }
}
