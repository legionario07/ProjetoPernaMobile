package br.com.omniatechnology.pernavendas.pernavendas.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.adapter.ProdutosAdapter;


public class ProdutoViewHolder extends RecyclerView.ViewHolder {

    public TextView txtProduto;
    public TextView txtQtde;
    public TextView txtDescricao;
    public TextView txtValorVenda;


    public ProdutoViewHolder(@NonNull View itemView,
                             final ProdutosAdapter.OnItemClickListener listener) {
        super(itemView);


        txtProduto = itemView.findViewById(R.id.txtProduto);
        txtQtde = itemView.findViewById(R.id.txtQtde);
        txtDescricao = itemView.findViewById(R.id.txtDescricao);
        txtValorVenda = itemView.findViewById(R.id.txtValorVenda);

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


    }

}
