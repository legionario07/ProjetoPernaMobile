package br.com.omniatechnology.pernavendas.pernavendas.View;

public interface IModelView {

    void onMessageSuccess(String message);
    void onMessageError(String message);


    interface IProdutoView extends IModelView{

    }

    interface IUnidadeDeMedidaView extends IModelView{

    }

}
