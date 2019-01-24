package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class MarcaPresenter implements IMarcaPresenter {

    IModelView.IMarcaView marcaView;
    private Context context;
    Marca marca;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<Marca> marcas;
    private MarcasAdapter marcasAdapter;

    public MarcaPresenter() {
        marca = new Marca();
        genericDAO = new GenericDAO();
    }

    public MarcaPresenter(IModelView.IMarcaView marcaView) {
        this.marcaView = marcaView;
    }

    public MarcaPresenter(IModelView.IMarcaView marcaView, Context context) {
        this();
        this.marcaView = marcaView;
        this.context = context;
    }


    @Override
    public void onCreate() {

        String retornoStr = marca.isValid(context);

        if (retornoStr.length() > 1)
            marcaView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(marca, ConstraintUtils.SALVAR, new MarcaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                marcaView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete(Long id) {

        try {
            isSave = (Boolean) genericDAO.execute(id, ConstraintUtils.DELETAR, new MarcaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            marcaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        String retornoStr = marca.isValid(context);

        if (retornoStr.length() > 1)
            marcaView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(marca, ConstraintUtils.EDITAR, new MarcaServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                marcaView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
            else
                marcaView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void findById() {

        try {
            marca = (Marca) genericDAO.execute(marca, ConstraintUtils.FIND_BY_ID, new MarcaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findAll() {


        try {
            marcas = (List<Marca>) genericDAO.execute(marca, ConstraintUtils.FIND_ALL, new MarcaServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void atualizarList(ListView view) {


        if (marcasAdapter == null) {
            marcas = (List<Marca>) genericDAO.execute(new Marca(), ConstraintUtils.FIND_ALL, new MarcaServiceImpl());

            marcasAdapter = new MarcasAdapter(context, marcas);

            view.setAdapter(marcasAdapter);

        } else {

            marcasAdapter.notifyDataSetChanged();

        }
    }
        public void addTextWatcherNomeMarca(final EditText editText){

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    marca.setNome(s.toString());
                }
            });

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (false == hasFocus) {
                        ViewUtils.hideKeyboard(context, editText);
                    }
                }
            });
        }

}
