package com.example.parceiro.View;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.parceiro.R;

public class DetalharServicoAceito extends FragmentActivity {



    private String auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_servico_aceito);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");


    }


}