package com.example.appdelivery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdelivery.Modal.Produto;
import com.example.appdelivery.R;


import java.util.ArrayList;
import java.util.List;



public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.ProdutoViewHolder> {

    private List <Produto> listaProdutos;

    public AdapterProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemProduto;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemProduto = layoutInflater.inflate(R.layout.produto_item, parent, false);
        return new ProdutoViewHolder(itemProduto);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        holder.nome.setText(listaProdutos.get(position).getNome());
        holder.preco.setText(listaProdutos.get(position).getPreco());
        holder.foto.setImageResource(listaProdutos.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder{

        private TextView nome, preco;
        private ImageView foto;
        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome_produto);
            preco = itemView.findViewById(R.id.preco_produto);
            foto = itemView.findViewById(R.id.foto_produto);

        }
    }
}
