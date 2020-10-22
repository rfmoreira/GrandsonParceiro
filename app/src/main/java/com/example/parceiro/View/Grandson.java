package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parceiro.R;

public class Grandson extends AppCompatActivity {

    private Button btLogin, btCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogin = (Button) findViewById(R.id.btLogin);
        btCadastro = (Button) findViewById(R.id.btCadastro);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginGrandson.class);
                startActivity(intent);
            }
        });
    }

    public void onClickCadastro(View V){
        Intent intent = new Intent(getApplicationContext(), CadastroParceiro.class);
        startActivity(intent);

    }
}