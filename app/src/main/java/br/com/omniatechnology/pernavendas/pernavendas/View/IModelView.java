package br.com.omniatechnology.pernavendas.pernavendas.View;

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

    interface IUsuarioView extends IModel{

    }

    interface IPedidoView extends IModel{

    }

    interface IVendaView extends IModel{

    }

    interface IUnidadeDeMedidaView extends IModelView{

    }

}
