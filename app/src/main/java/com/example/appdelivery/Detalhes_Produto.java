package com.example.appdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Detalhes_Produto extends AppCompatActivity {

    private ImageView fotoDetalheProduto;
    private TextView nomeDetalheProduto, descricaoDetalheProduto, precoDetalheProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        inicializarComponentes();

        Intent intent = getIntent();
        Glide.with(getApplicationContext()).load(intent.getStringExtra("foto")).into(fotoDetalheProduto);
        nomeDetalheProduto.setText(intent.getStringExtra("nome"));
        descricaoDetalheProduto.setText(intent.getStringExtra("descricao"));
        precoDetalheProduto.setText(intent.getStringExtra("preco"));

    }

    private void inicializarComponentes() {

        fotoDetalheProduto = findViewById(R.id.foto_detalhe_produto);
        nomeDetalheProduto = findViewById(R.id.nome_detalhe_produto);
        descricaoDetalheProduto = findViewById(R.id.descricao_detalhe_produto);
        precoDetalheProduto = findViewById(R.id.preco_detalhe_produto);

    }
}