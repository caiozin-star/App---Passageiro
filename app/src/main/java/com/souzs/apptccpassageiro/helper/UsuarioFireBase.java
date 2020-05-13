package com.souzs.apptccpassageiro.helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFireBase {
    public static FirebaseUser getUsuAtual(){
        FirebaseAuth usu = ConfiguracaoFireBase.getAutenticacao();

        return  usu.getCurrentUser();
    }

    public static Boolean atualizarNome(String nome){
        try {
            FirebaseUser m = getUsuAtual();
            UserProfileChangeRequest perfil = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            m.updateProfile(perfil).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
