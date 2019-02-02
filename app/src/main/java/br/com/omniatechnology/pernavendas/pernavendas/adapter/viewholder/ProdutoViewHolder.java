package br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;
import br.com.omniatechnology.pernavendas.pernavendas.interfaces.OnItemClickListener;


public class ProdutoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView txtProduto;
    public TextView txtQtde;
    public TextView txtDescricao;
    public TextView txtValorVenda;
    private CardView cardViewProdutos;


    public ProdutoViewHolder(@NonNull final View itemView, final ProdutosAdapter.OnItemClickListener listener) {
        super(itemView);


        txtProduto = itemView.findViewById(R.id.txtProduto);
        txtQtde = itemView.findViewById(R.id.txtQtde);
        txtDescricao = itemView.findViewById(R.id.txtDescricao);
        txtValorVenda = itemView.findViewById(R.id.txtValorVenda);
        cardViewProdutos = itemView.findViewById(R.id.cardViewProdutos);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });

        cardViewProdutos.setOnCreateContextMenuListener(this);

    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(this.getAdapterPosition(), 1, 1, "Editar");
        menu.add(this.getAdapterPosition(), 2, 2, "Excluir");

    }
}

