package com.example.appdelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdelivery.Modal.Produto;
import com.example.appdelivery.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.ProdutoViewHolder> {

    private List <Produto> listaProdutos;
    private Context context;

    public AdapterProdutos(List<Produto> listaProdutos, Context context) {
        this.listaProdutos = listaProdutos;
        this.context = context;
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
        Glide.with(context).load(listaProdutos.get(position).getFoto()).into(holder.foto);
        holder.nome.setText(listaProdutos.get(position).getNome());
        holder.preco.setText(listaProdutos.get(position).getPreco());

    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder{

        private TextView nome, preco;
        private CircleImageView foto;
        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome_produto);
            preco = itemView.findViewById(R.id.preco_produto);
            foto = itemView.findViewById(R.id.foto_produto);

        }
    }
}
