package com.example.appdelivery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Alterar_Dados extends AppCompatActivity {

    private CircleImageView fotoUsuario;
    private Button btnSelecionarFoto, btnAtualizarDados;
    private EditText editNome;
    private Uri uriImagemSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);

        inicializarComponentes();

        btnSelecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarFoto();
            }
        });

        btnAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editNome.getText().toString();

                if (nome.isEmpty()) {

                    Snackbar snackbar = Snackbar
                            .make(v, "Preencha todos os campos!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else {
                    verificarImagemNoFirestore(v);
                }
            }
        });

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        uriImagemSelecionada = data.getData();

                        try {
                            fotoUsuario.setImageURI(uriImagemSelecionada);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
    );

    private void selecionarFoto() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);

    }


    private void verificarImagemNoFirestore(View view) {
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Usuarios").document(usuarioID)
                .get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot != null) {
                        String imagemAntiga = documentSnapshot.getString("foto");
                        Log.d("ImagemAntiga", "Caminho da imagem armazenado no Firestore: " + imagemAntiga);
                        if (imagemAntiga != null) {

                            apagarImagemAntiga(imagemAntiga);
                        }
                    }
                    fazerUploadImagemNova(view, usuarioID);

                }).addOnFailureListener(e -> {
                    Log.i("erro", "Não encontramos o local do arquivo!");
                });

    }

    private void apagarImagemAntiga(String imagemAntiga) {

            try {
                // Converte a URL em URI
                Uri uri = Uri.parse(imagemAntiga);

                // Extrai o path do Storage
                String fullPath = uri.getPath();
                if (fullPath != null) {
                    // O path começa com "/v0/b/seu-projeto.appspot.com/o/"
                    // Removendo o prefixo desnecessário
                    String path = fullPath.split("/o/")[1];
                    path = path.split("\\?")[0]; // Remove parâmetros da URL
                    path = path.replace("%2F", "/"); // Decodifica o caminho

                    // Cria a referência ao arquivo no Storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(path);

                    // Exclui o arquivo
                    storageReference.delete().addOnSuccessListener(aVoid -> {
                        // Sucesso
                        System.out.println("Imagem excluída com sucesso!");
                    }).addOnFailureListener(e -> {
                        // Falha
                        e.printStackTrace();
                    });
                } else {
                    System.out.println("URL inválida");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void fazerUploadImagemNova (View view, String usuarioID){

            String nomeArquivo = UUID.randomUUID().toString();
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference("/imagens/" + nomeArquivo);
            storageReference.putFile(uriImagemSelecionada)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("UploadSucesso", "Arquivo enviado para o Storage com sucesso.");

                            storageReference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String fotoNova = uri.toString();
                                    System.out.println("Nova imagem enviada!");
                                    Log.d("CaminhoImagem", "Caminho salvo no Firestore: " + fotoNova);
                                    atualizarDadosDoPerfil(view, usuarioID, fotoNova); // Atualiza o Firestore
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("Erro URL", "ERRO NA URL:", e);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(e -> {
                        System.out.println("Erro ao enviar nova imagem: " + e.getMessage());
                    });

        }

        private void atualizarDadosDoPerfil (View v, String usuarioID, String imagemNova){

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            String nome = editNome.getText().toString();

            Map<String, Object> usuarios = new HashMap<>();
            usuarios.put("nome", nome);
            usuarios.put("foto", imagemNova);

            db.collection("Usuarios").document(usuarioID)
                    .update(usuarios)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar snackbar = Snackbar
                                    .make(v, "Atualizado com sucesso!", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                        }
                                    });
                            snackbar.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar snackbar = Snackbar
                                    .make(v, "Erro ao atualizar!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    });
        }

        private void inicializarComponentes () {

            fotoUsuario = findViewById(R.id.foto_usuario);
            btnSelecionarFoto = findViewById(R.id.btn_selecionar_foto);
            editNome = findViewById(R.id.edit_nome);
            btnAtualizarDados = findViewById(R.id.btn_atualizar_dados);
        }

    }