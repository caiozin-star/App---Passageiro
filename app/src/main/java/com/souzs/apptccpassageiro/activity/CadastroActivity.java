package com.souzs.apptccpassageiro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.souzs.apptccpassageiro.R;
import com.souzs.apptccpassageiro.helper.ConfiguracaoFireBase;
import com.souzs.apptccpassageiro.helper.UsuarioFireBase;
import com.souzs.apptccpassageiro.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoEmail, campoNome, campoSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();

        campoEmail = findViewById(R.id.editEmailCad);
        campoNome = findViewById(R.id.editNomeCad);
        campoSenha = findViewById(R.id.editSenhaCad);
    }
    public void validarCampos(View view){

        String email = campoEmail.getText().toString();
        String nome = campoNome.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!email.isEmpty()){
            if (!nome.isEmpty()){
                if (!senha.isEmpty()){
                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    cadastrarUsu( usuario );
                }else {
                    Toast.makeText(this, "Ops, Preencha o campo Senha para finalizar seu cadastro!", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Ops, Preencha o campo nome para prosseguir com " +
                        "o cadastro!", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Ops, Preencha os campos para realizar seu cadastro!", Toast.LENGTH_SHORT).show();
        }
    }
    public void cadastrarUsu(final Usuario u){
        auth = ConfiguracaoFireBase.getAutenticacao();

        auth.createUserWithEmailAndPassword(
                u.getEmail(), u.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    try {
                        String idMotorista = task.getResult().getUser().getUid();
                        u.setId(idMotorista);
                        u.salvarUsu();

                        UsuarioFireBase.atualizarNome(u.getNome());

                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    String erro = "";
                    try {
                        throw  task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Por favor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Está conta já está cadastrada";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário! " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
