package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.MarcasAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UsuariosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.CategoriaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.MarcaServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PerfilServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Categoria;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Marca;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

public class UsuarioPresenter implements IUsuarioPresenter {

    IModelView.IUsuarioView usuarioView;
    private Context context;
    Usuario usuario;
    private GenericDAO genericDAO;
    private Boolean isSave;
    private List<Usuario> usuarios;
    private String confirmarSenhaStr;
    private UsuariosAdapter usuariosAdapter;

    public UsuarioPresenter() {
        usuario = new Usuario();
        genericDAO = new GenericDAO();
    }

    public UsuarioPresenter(IModelView.IUsuarioView usuarioView) {
        this.usuarioView = usuarioView;
    }

    public UsuarioPresenter(IModelView.IUsuarioView usuarioView, Context context) {
        this();
        this.usuarioView = usuarioView;
        this.context = context;
    }

    public void setSpinnerPerfil(Spinner spinner){

        List<Perfil> perfis = null;
        try {
            perfis = (List<Perfil>) genericDAO.execute(false, ConstraintUtils.FIND_ALL, new PerfilServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter arrayPerfis = new ArrayAdapter(context, android.R.layout.simple_spinner_item, perfis);
        arrayPerfis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayPerfis);


    }

    public void getDadosSpinnerPerfil(Spinner spinner){
        usuario.setPerfil((Perfil) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner){
        return (IModel) spinner.getSelectedItem();
    }

    @Override
    public void onCreate() {

        String retornoStr = usuario.isValid(context);
        
        if(!usuario.getSenha().equals(confirmarSenhaStr)){
            retornoStr = context.getString(R.string.senhas_nao_podem_ser_diferentes);
        }

        if (retornoStr.length() > 1)
            usuarioView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(usuario, ConstraintUtils.SALVAR, new UsuarioServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                usuarioView.onMessageSuccess(context.getResources().getString(R.string.save_success));
            else
                usuarioView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void onDelete() {
        try {
            isSave = (Boolean) genericDAO.execute(usuario, ConstraintUtils.DELETAR, new UsuarioServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isSave)
            usuarioView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
        else
            usuarioView.onMessageError(context.getResources().getString(R.string.error_operacao));

    }

    @Override
    public void onUpdate() {

        String retornoStr = usuario.isValid(context);

        if (retornoStr.length() > 1)
            usuarioView.onMessageError(retornoStr);
        else {

            try {
                isSave = (Boolean) genericDAO.execute(usuario, ConstraintUtils.EDITAR, new UsuarioServiceImpl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSave)
                usuarioView.onMessageSuccess(context.getResources().getString(R.string.concluido_sucesso));
            else
                usuarioView.onMessageError(context.getResources().getString(R.string.error_operacao));

        }

    }

    @Override
    public void findById() {

        try {
            usuario = (Usuario) genericDAO.execute(usuario, ConstraintUtils.FIND_BY_ID, new UsuarioServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findAll() {


        try {
            usuarios = (List<Usuario>) genericDAO.execute(usuario, ConstraintUtils.FIND_ALL, new UsuarioServiceImpl()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void addTextWatcherUsuario(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usuario.setUsuario(s.toString());
            }
        });
    }

    public void addTextWatcherSenha(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usuario.setSenha(s.toString());
            }
        });
    }

    public void addTextWatcherConfirmarSenha(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmarSenhaStr = s.toString();
            }
        });
    }

    public void atualizarList(ListView view) {


        if (usuariosAdapter == null) {
            usuarios = (List<Usuario>) genericDAO.execute(new Usuario(), ConstraintUtils.FIND_ALL, new UsuarioServiceImpl());

            usuariosAdapter = new UsuariosAdapter(context, usuarios);

            view.setAdapter(usuariosAdapter);

        } else {

            usuariosAdapter.notifyDataSetChanged();

        }
    }

}
