package br.com.omniatechnology.pernavendas.pernavendas.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder.ProdutoDiffCallback;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder.ProdutoViewHolder;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.OnItemClickListener;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutoViewHolder> {

    private List<Produto> produtos;
    private Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProdutosAdapter(Context context, List<Produto> produtos){
        this.produtos = produtos;
        this.context = context;
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_produtos, viewGroup, false);

        ProdutoViewHolder produtoViewHolder = new ProdutoViewHolder(view, mListener);

        return produtoViewHolder;
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder produtoViewHolder, int i) {

        Produto produto = produtos.get(i);

        produtoViewHolder.txtProduto.setText(produto.getNome());
        produtoViewHolder.txtQtde.setText(String.valueOf(produto.getQtde().intValue()));
        produtoViewHolder.txtDescricao.setText(produto.getDescricao());
        produtoViewHolder.txtValorVenda.setText(produto.getValorVenda().toString());

    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(int position) {
        produtos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, produtos.size());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void updateList(Produto produto) {
        insertItem(produto);
    }

    // Método responsável por inserir um novo usuário na lista
    //e notificar que há novos itens.
    public void insertItem(Produto produto) {
        produtos.add(produto);
        notifyItemInserted(getItemCount());
    }

    public void updateList(List<Produto> produtos) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProdutoDiffCallback(this.produtos, produtos));
        this.produtos = produtos;
        diffResult.dispatchUpdatesTo(this);
    }

    public Produto getItem(int position)
    {
        return produtos.get(position);
    }


}
