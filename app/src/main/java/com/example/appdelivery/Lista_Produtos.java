package com.example.appdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdelivery.Adapter.AdapterProdutos;
import com.example.appdelivery.Modal.Produto;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Lista_Produtos extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private List<Produto> listaProdutos;

    private AdapterProdutos adapterProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        recyclerViewProdutos = findViewById(R.id.recycleView_produtos);
        listaProdutos = new ArrayList<>();
        adapterProdutos = new AdapterProdutos(listaProdutos);

        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.setAdapter(adapterProdutos);
        Produtos();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.perfil){
            Intent intent = new Intent(Lista_Produtos.this, Perfil_Usuario.class);
            startActivity(intent);
        }
        else if (itemID == R.id.pedidos) {
            Toast.makeText(getApplicationContext(), "pedidos clicado", Toast.LENGTH_SHORT).show();
        }
        else if (itemID == R.id.deslogar) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Usuario deslogado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Lista_Produtos.this, Form_Login.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Produtos(){

        Produto produto1 = new Produto("Pudim", "R$ 15,00", R.drawable.logo);
        listaProdutos.add(produto1);
        Produto produto2 = new Produto("Bolo de chocolate", "R$ 15,00", R.drawable.logo);
        listaProdutos.add(produto2);
        Produto produto3 = new Produto("macarrao", "R$ 15,00", R.drawable.logo);
        listaProdutos.add(produto3);

    }
}