package br.com.omniatechnology.pernavendas.pernavendas.Presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.View.IModelView;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UsuariosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.UsuariosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.GenericDAO;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.PerfilServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.UsuarioServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.enums.OperationType;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.ITaskProcess;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;
import br.com.omniatechnology.pernavendas.pernavendas.model.Perfil;
import br.com.omniatechnology.pernavendas.pernavendas.model.Usuario;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ViewUtils;

public class UsuarioPresenter implements IUsuarioPresenter, ITaskProcess {

    IModelView.IUsuarioView usuarioView;
    private Context context;
    Usuario usuario;
    private Boolean isSave;
    private List<Usuario> usuarios;
    private String confirmarSenhaStr;
    private UsuariosAdapter usuariosAdapter;

    private ListView view;
    private OperationType operationType;
    private Spinner spnPerfil;

    public UsuarioPresenter() {
        usuario = new Usuario();
    }

    public UsuarioPresenter(IModelView.IUsuarioView usuarioView) {
        this.usuarioView = usuarioView;
    }

    public UsuarioPresenter(IModelView.IUsuarioView usuarioView, Context context) {
        this();
        this.usuarioView = usuarioView;
        this.context = context;
    }

    public void setSpinnerPerfil(Spinner spinner) {

        this.spnPerfil = spinner;

        operationType = OperationType.FIND_ALL_PERFIL;
        try {
            new GenericDAO(context, this).execute(false, ConstraintUtils.FIND_ALL, new PerfilServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }


    }

    public void getDadosSpinnerPerfil(Spinner spinner) {
        usuario.setPerfil((Perfil) getDadosDeSpinner(spinner));
    }

    private IModel getDadosDeSpinner(Spinner spinner) {
        return (IModel) spinner.getSelectedItem();
    }

    public void atualizarList(ListView view) {

        this.view = view;

        if (usuariosAdapter == null) {
            findAll();

        } else {

            usuariosAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreate() {

        operationType = OperationType.SAVE;
        String retornoStr = usuario.isValid(context);

        if(!usuario.getSenha().equals(confirmarSenhaStr)){
            retornoStr = context.getString(R.string.confirmacao_senha);
        }

        if (retornoStr.length() > 1 )
            usuarioView.onMessageError(retornoStr);
        else {

            try {
                new GenericDAO(context, this).execute(usuario, ConstraintUtils.SALVAR, new UsuarioServiceImpl());
            } catch (Exception e) {
                Log.i(ConstraintUtils.TAG, e.getMessage());
            }

        }

    }

    @Override
    public void onDelete(Long id) {

        operationType = OperationType.DELETE;

        try {
            new GenericDAO(context, this).execute(id, ConstraintUtils.DELETAR, new UsuarioServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }

    }

    @Override
    public void onUpdate() {

        operationType = OperationType.UPDATE;

        String retornoStr = usuario.isValid(context);

        if(!usuario.getSenha().equals(confirmarSenhaStr)){
            retornoStr = context.getString(R.string.confirmacao_senha);
        }

        if (retornoStr.length() > 1)
            usuarioView.onMessageError(retornoStr);
        else {
            try {
                new GenericDAO(context, this).execute(usuario, ConstraintUtils.EDITAR, new UsuarioServiceImpl());
            } catch (Exception e) {
                Log.e(ConstraintUtils.TAG, e.getMessage());
            }


        }

    }

    @Override
    public void findById() {

        operationType = OperationType.FIND_BY_ID;

        try {
            new GenericDAO(context, this).execute(usuario, ConstraintUtils.FIND_BY_ID, new UsuarioServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void findAll() {

        operationType = OperationType.FIND_ALL;

        try {
            new GenericDAO(context, this).execute(usuario, ConstraintUtils.FIND_ALL, new UsuarioServiceImpl());
        } catch (Exception e) {
            Log.e(ConstraintUtils.TAG, e.getMessage());
        }
    }

    @Override
    public void setItem(IModel model) {
        this.usuario = (Usuario) model;
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
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

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false == hasFocus) {
                    ViewUtils.hideKeyboard(context, editText);
                }
            }
        });
    }


    @Override
    public void onPostProcess(Serializable serializable) {

        switch (operationType) {
            case SAVE:
            case UPDATE:
            case DELETE:

                isSave = (Boolean) serializable;

                if (isSave)
                    usuarioView.onMessageSuccess(context.getResources().getString(R.string.save_success));
                else
                    usuarioView.onMessageError(context.getResources().getString(R.string.error_operacao));

                if (operationType == OperationType.DELETE)
                    findAll();
                break;
            case FIND_ALL:

                if (usuarios != null) {
                    usuarios.clear();
                    List<Usuario> usuariosTemp = (List<Usuario>) serializable;
                    if(usuariosTemp!=null) {
                        usuarios.addAll(usuariosTemp);
                    }
                } else {
                    usuarios = (List<Usuario>) serializable;
                    if(usuarios==null){
                        usuarios = new ArrayList<>();
                    }
                }

                if (usuariosAdapter == null) {
                    usuariosAdapter = new UsuariosAdapter(context, usuarios);

                    view.setAdapter(usuariosAdapter);
                } else {
                    usuariosAdapter.notifyDataSetChanged();
                }

                usuariosAdapter.notifyDataSetChanged();

                break;

            case FIND_BY_ID:

                break;

            case FIND_ALL_PERFIL:

                List<Perfil> perfis = (List<Perfil>) serializable;

                ArrayAdapter arrayPerfis = new ArrayAdapter(context, android.R.layout.simple_spinner_item, perfis);
                arrayPerfis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnPerfil.setAdapter(arrayPerfis);

                break;
            default:
        }

    }


}
