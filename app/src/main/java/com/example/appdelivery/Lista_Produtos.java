package com.example.appdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdelivery.Adapter.AdapterProdutos;
import com.example.appdelivery.Modal.Produto;
import com.example.appdelivery.RecyclerViewItemClickListener.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Lista_Produtos extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private List<Produto> listaProdutos;

    private AdapterProdutos adapterProdutos;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        recyclerViewProdutos = findViewById(R.id.recycleView_produtos);
        listaProdutos = new ArrayList<>();
        adapterProdutos = new AdapterProdutos(listaProdutos, getApplicationContext());

        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.setAdapter(adapterProdutos);

        //Evento de click na recycleView

        recyclerViewProdutos.addOnItemTouchListener(

                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewProdutos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Produto produto = listaProdutos.get(position);
                                Intent intent = new Intent(Lista_Produtos.this, Detalhes_Produto.class);
                                intent.putExtra("nome", produto.getNome());
                                intent.putExtra("foto", produto.getFoto());
                                intent.putExtra("preco", produto.getPreco());
                                intent.putExtra("descricao", produto.getDescricao());
                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        })
        );



        db = FirebaseFirestore.getInstance();
        db.collection("Produtos")
                .orderBy("nome")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                           for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                               Produto produto = queryDocumentSnapshot.toObject(Produto.class);
                               listaProdutos.add(produto);
                           }
                            adapterProdutos.notifyDataSetChanged();
                        }
                    }
                });


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
}