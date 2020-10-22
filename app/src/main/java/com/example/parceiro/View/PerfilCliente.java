package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Comentario;
import com.example.parceiro.Model.Parceiro;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;

import java.util.ArrayList;

import retrofit2.Call;

public class PerfilCliente extends AppCompatActivity {

    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;
    private Button bt_aceitar,bt_recusar;
    private int idParceiro;

    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        listViewComentarios =(ListView) findViewById(R.id.listViewComentarios);
        bt_aceitar =(Button) findViewById(R.id.bt_aceitar);
        bt_recusar =(Button) findViewById(R.id.bt_recusar);
        //idParceiro = getIntent().getExtras().getInt("idParceiro");

        //Preenchendo Lista de comentarios
        /*listaCometarios = preencherList();

        // Verificando se lista esta vazia
        if (listaCometarios.isEmpty()){
            AdapterListViewComentario adapter = new AdapterListViewComentario(this,null);
        }else {
            // Chamando Adaptador para preenchimento do list View
            AdapterListViewComentario adapter = new AdapterListViewComentario(this,listaCometarios);
            // Setenado adptador no list view
            listViewComentarios.setAdapter(adapter);
        }*/

        bt_aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetalharServicoAceito.class);
                startActivity(intent);
            }
        });

    }

    // Metodo para Preencher ListView
    private ArrayList<Comentario> preencherList() {
        ArrayList<Comentario> list = new ArrayList<Comentario>();
        /*Comentario c = new Comentario(1,"Lucas Francelino","Ótima pessoa, gosteis muito da comanhia","0");

        for(int i = 0; i < 4 ;i++){
           list.add(c);
           c = new Comentario(2
                   ,"Rafael Moreira"
                   ,"Ótima pessoa, gosteis muito da comanhia"
                   ,"0");
           list.add(c);
           c = new Comentario(3
                   ,"Luan Amor"
                   ,"Ótimo profissional muito atencioso e dedicado, confiavel e tem um otimo papo pena que não é muito bom e jogos peder todos, kkkk"
                   ,"0");
           list.add(c);
           c = new Comentario(4
                   ,"Ferdinando Garcia"
                   ,"Ótima pessoa, gosteis muito da comanhia"
                   ,"0");
       }*/
        return list;
    }


    public void getPerfilParceiro(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Parceiro> call = restService.detalharParceiro("Bearer "+auth,idParceiro);



    }



}