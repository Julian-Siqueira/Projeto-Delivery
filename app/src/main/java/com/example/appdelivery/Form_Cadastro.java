package com.example.appdelivery;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Cadastro extends AppCompatActivity {

    private CircleImageView fotoUsuario;
    private Button btnSelecionarFoto, btnCadastrar;
    private EditText editNome,editEmail,editSenha;
    private TextView txtMensagemErro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        iniciarComponentes();
        editNome.addTextChangedListener(cadastroTextWatcher);
        editEmail.addTextChangedListener(cadastroTextWatcher);
        editSenha.addTextChangedListener(cadastroTextWatcher);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario(v);
            }
        });


    }

    private void cadastrarUsuario(View view) {

        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    txtMensagemErro.setText("");
                    Snackbar snackbar = Snackbar
                            .make(view, "Usuario cadastrado com sucesso!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                    snackbar.show();
                }else {
                    String erro;

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Coloque uma senha com no mínimo 6 caracteres!";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail inválido!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Email já esta sendo usado!";
                    } catch (FirebaseNetworkException e){
                        erro = "Sem conexão com a internet!";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }

                    txtMensagemErro.setText(erro);

                }
            }
        });
    }

    private void iniciarComponentes() {
        fotoUsuario = findViewById(R.id.foto_usuario);
        btnSelecionarFoto = findViewById(R.id.btn_selecionar_foto);
        editNome = findViewById(R.id.edit_nome);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        txtMensagemErro = findViewById(R.id.txt_mensagemErro);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
    }


    TextWatcher cadastroTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()){

                btnCadastrar.setEnabled(true);
                btnCadastrar.setBackgroundColor(getResources().getColor(R.color.dark_red));

            }else {
                btnCadastrar.setEnabled(false);
                btnCadastrar.setBackgroundColor(getResources().getColor(R.color.grey));
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}