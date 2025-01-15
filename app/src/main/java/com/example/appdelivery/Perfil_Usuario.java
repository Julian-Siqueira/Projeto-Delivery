package com.example.appdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil_Usuario extends AppCompatActivity {

    private CircleImageView fotoUsuario;
    private TextView nomeUsuario, emailUsuario;
    private Button btnEditarPerfil;

    private String usuarioID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_perfil);

        inicializarComponentes();

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil_Usuario.this, Alterar_Dados.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Iniciar o banco de dados
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String usuarioEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot != null){
                    Glide.with(getApplicationContext()).load(documentSnapshot.getString("foto")).into(fotoUsuario);
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    emailUsuario.setText(usuarioEmail);
                }else {
                    Toast.makeText(getApplicationContext(), "Documento não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void inicializarComponentes() {

        fotoUsuario = findViewById(R.id.foto_usuario);
        nomeUsuario = findViewById(R.id.nome_usuario);
        emailUsuario = findViewById(R.id.email_usuario);
        btnEditarPerfil = findViewById(R.id.btn_editar_Perfil);

    }


}