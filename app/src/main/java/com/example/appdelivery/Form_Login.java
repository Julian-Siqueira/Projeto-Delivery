package com.example.appdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form_Login extends AppCompatActivity {

    private TextView txtCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
        getSupportActionBar().hide();

        inicializarComponentes();
        txtCriarConta.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Form_Cadastro.class);
            startActivity(intent);
        });
    }
    public void inicializarComponentes(){
        txtCriarConta = findViewById(R.id.txt_criar_conta);
    }

}