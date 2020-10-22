package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parceiro.R;

public class Cadastro extends AppCompatActivity {

    private Button btCliente, btColaborador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btColaborador = (Button) findViewById(R.id.btColaborador);
        btCliente = (Button) findViewById(R.id.btCliente);

        btCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroParceiro.class);
                startActivity(intent);
            }
        });

        btColaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CadastroColaborador.class);
                startActivity(intent);
            }
        });
    }




}