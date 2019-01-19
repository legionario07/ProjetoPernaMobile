package br.com.omniatechnology.pernavendas.pernavendas.View;

public interface IModelView {

    void onCreateSuccess();
    void onCreateError(String message);


    interface IProdutoView extends IModelView{

    }

    interface IUnidadeDeMedidaView extends IModelView{

    }

}
