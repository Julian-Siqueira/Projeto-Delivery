package com.example.appdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class
Form_Login extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private TextView txtMensagemErro, txtCriarConta;
    private Button btnEntrar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        inicializarComponentes();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                String erro = "";

                if (email.isEmpty() || senha.isEmpty()){
                 erro = "Digite seus dados corretamente!";
                }else{
                   autenticarUsuario();
                }
                txtMensagemErro.setText(erro);
            }
        });

        txtCriarConta.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getApplicationContext(), Form_Cadastro.class);
            startActivity(intent);
            finish();
        });
    }

    private void autenticarUsuario() {

        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    progressBar.setVisibility(View.VISIBLE);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iniciarTelaProdutos();
                        }
                    }, 3000);

                }else {
                    String erro;

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        if (e.getMessage() != null && e.getMessage().contains("email")) {
                            erro = "E-mail inválido!";
                        } else {
                            erro = "Senha incorreta!";
                        }
                    } catch (FirebaseNetworkException e){
                        erro = "Sem conexão com a internet!";
                    } catch (Exception e) {
                        erro = "Erro ao Logar!";
                    }

                    txtMensagemErro.setText(erro);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioAtual != null){
            iniciarTelaProdutos();
        }
    }

    private void iniciarTelaProdutos() {

        Intent intent = new Intent(getApplicationContext(), Lista_Produtos.class);
        startActivity(intent);
    }

    public void inicializarComponentes(){

        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        txtMensagemErro = findViewById(R.id.txt_mensagemErro);
        txtCriarConta = findViewById(R.id.txt_criar_conta);
        btnEntrar = findViewById(R.id.btn_entrar);
        progressBar = findViewById(R.id.progress_bar);

    }

}