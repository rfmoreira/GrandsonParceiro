package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parceiro.Model.Comentario;
import com.example.parceiro.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilCliente extends AppCompatActivity {

    private TextView nomeCliente, txtNotaPerf,txtViewQtdCompanhia,txtViewDataInicio;
    private CircleImageView imgPerfil;
    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;


    private int idCliente;
    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");
        idCliente = getIntent().getExtras().getInt("idCliente");


        nomeCliente =(TextView) findViewById(R.id.nomeCliente);
        txtNotaPerf =(TextView) findViewById(R.id.txtNotaPerf);
        txtViewQtdCompanhia =(TextView) findViewById(R.id.txtViewQtdContratacoes);
        txtViewDataInicio =(TextView) findViewById(R.id.txtViewDataInicio);
        imgPerfil =(CircleImageView) findViewById(R.id.imgPerfil);
        listViewComentarios =(ListView) findViewById(R.id.listViewComentarios);

    }
}