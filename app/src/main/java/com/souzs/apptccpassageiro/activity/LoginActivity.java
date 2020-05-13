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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.souzs.apptccpassageiro.R;
import com.souzs.apptccpassageiro.helper.ConfiguracaoFireBase;
import com.souzs.apptccpassageiro.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        campoEmail = findViewById(R.id.editEmailLogin);
        campoSenha = findViewById(R.id.editSenhaLogin);
    }
    public void validarCampos(View view){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                Usuario u = new Usuario();
                u.setEmail(email);
                u.setSenha(senha);

                logarUsuario( u );

            }else {
                Toast.makeText(this, "Ops, Preencha o campo Senha para validar seu Login!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Ops, Preencha os dados para validar seu Login!", Toast.LENGTH_SHORT).show();
        }
    }
    public void logarUsuario(Usuario usuario){
        auth = ConfiguracaoFireBase.getAutenticacao();

        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                }else {
                    String erro = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        erro = "Digite um e-mail cadastrado!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail e senha não correspondem a um usuário cadastrado!";
                    }
                    catch (Exception e){
                        erro = "Erro ao cadastrar uauário! " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
