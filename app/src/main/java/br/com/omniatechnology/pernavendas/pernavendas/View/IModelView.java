package br.com.omniatechnology.pernavendas.pernavendas.View;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.IModel;

public interface IModelView {

    void onMessageSuccess(String message);
    void onMessageError(String message);
    List<IModel> findAllSuccess();
    List<IModel> findAllError(String message);


    interface IProdutoView extends IModelView{

    }

    interface IMarcaView extends IModelView{

    }

    interface ICategoriaView extends IModelView{

    }

    interface IConfiguracaoView extends IModelView{

    }

    interface IPerfilView extends IModelView{

    }

    interface IUsuarioView extends IModelView{

    }

    interface IPedidoView extends IModelView{

    }

    interface IVendaView extends IModelView{



    }

    interface IUnidadeDeMedidaView extends IModelView{

    }

}
