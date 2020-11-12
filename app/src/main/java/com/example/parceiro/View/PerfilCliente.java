package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Cliente;
import com.example.parceiro.Model.Comentario;
import com.example.parceiro.Model.ListaCliente;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.AdapterListViewComentario;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getCliente();
    }


    private void getCliente(){
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Cliente> call = restService.getPerfilCliente("Bearer "+auth, idCliente);

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful()){
                    Cliente parceiro = response.body();

                    nomeCliente.setText(parceiro.getNome());
                    txtNotaPerf.setText(parceiro.getNota());
                    txtViewQtdCompanhia.setText(String.valueOf(parceiro.getQuantidadeServico()));
                    String[] date1 = parceiro.getDataInicio().split("T");
                    String[] date2 = date1[0].split("-");
                    txtViewDataInicio.setText(date2[2]+"/"+date2[1]+"/"+date2[0]);
                    //Preenchendo Lista de comentarios
                    listaCometarios = new ArrayList<>(parceiro.getComentarios());
                    if(parceiro.getFoto().getData() != null){
                        byte[]  stringDecodificada = Base64.decode(parceiro.getFoto().getData(), Base64.DEFAULT);
                        Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);
                        imgPerfil.setImageBitmap(imgbtmap);
                    }

                    // Verificando se lista esta vazia
                    if (listaCometarios.isEmpty()){
                        AdapterListViewComentario adapter = new AdapterListViewComentario(PerfilCliente.this,null);
                    }else {
                        // Chamando Adaptador para preenchimento do list View
                        AdapterListViewComentario adapter = new AdapterListViewComentario(PerfilCliente.this,listaCometarios);
                        // Setenado adptador no list view
                        listViewComentarios.setAdapter(adapter);
                    }
                    Toast.makeText(PerfilCliente.this, "Sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PerfilCliente.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(PerfilCliente.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });

    }
}