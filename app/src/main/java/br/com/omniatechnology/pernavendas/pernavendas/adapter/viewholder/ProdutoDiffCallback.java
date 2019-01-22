package br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder;

import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public class ProdutoDiffCallback extends DiffUtil.Callback{

    List<Produto> oldProdutos;
    List<Produto> newProdutos;

    public ProdutoDiffCallback(List<Produto> newProdutos, List<Produto> oldProdutos) {
        this.newProdutos = newProdutos;
        this.oldProdutos = oldProdutos;
    }

    @Override
    public int getOldListSize() {
        return oldProdutos.size();
    }

    @Override
    public int getNewListSize() {
        return newProdutos.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldProdutos.get(oldItemPosition).getId() == newProdutos.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldProdutos.get(oldItemPosition).equals(newProdutos.get(newItemPosition));
    }

}