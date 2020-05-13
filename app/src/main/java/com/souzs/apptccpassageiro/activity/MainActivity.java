package com.souzs.apptccpassageiro.activity;

import androidx.annotation.FloatRange;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.souzs.apptccpassageiro.R;
import com.souzs.apptccpassageiro.helper.ConfiguracaoFireBase;
import com.souzs.apptccpassageiro.slider_personalizado.SliderCad;
import com.souzs.apptccpassageiro.slider_personalizado.SliderIntro;

import io.github.dreierf.materialintroscreen.MaterialIntroActivity;
import io.github.dreierf.materialintroscreen.MessageButtonBehaviour;
import io.github.dreierf.materialintroscreen.SlideFragmentBuilder;
import io.github.dreierf.materialintroscreen.animations.IViewTranslation;

public class MainActivity extends MaterialIntroActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        exibirSlider();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConfiguracaoFireBase.getAutenticacao();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), PrincipalMainActivity.class));
        }
    }

    public void abrirTelaCad(View view){
        startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
    }
    public void abrirTelaLogin(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void exibirSlider(){
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SliderIntro());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.color_background__slider)
                        .buttonsColor(R.color.color_button_slider)
                        .image(R.drawable.slider_p)
                        .possiblePermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                        .neededPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION})
                        .title("Aceite a permissão para prosseguir! \n")
                        .description("Fique tranquilo, a permissão é de suma importancia para o app, iremos \n usa-lá com " +
                                "consciência ;)")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("Á permissão já foi concedida, prossiga para a proxima tela");
                    }
                }, "Permissão concedida ;)"));

        addSlide(new SliderCad());
    }

}
