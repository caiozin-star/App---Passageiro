package com.souzs.apptccpassageiro.slider_personalizado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.souzs.apptccpassageiro.R;

import io.github.dreierf.materialintroscreen.SlideFragment;

public class SliderIntro extends SlideFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.intro_inicial, container, false);
        return v;
    }
    @Override
    public boolean canMoveFurther() {
        return true;
    }
    @Override
    public int backgroundColor() {
        return R.color.color_background__slider;
    }

    @Override
    public int buttonsColor() {
        return R.color.color_button_slider;
    }
}
