package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.FormAvaliacao;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvaliacaoCliente extends AppCompatActivity {

    private RatingBar avalicao;
    private TextInputLayout editTextComentario;
    private Button bt_avaliar;

    private String auth;
    private int idServico;
    private FormAvaliacao formAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_cliente);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        idServico = getIntent().getExtras().getInt("idServico");

        avalicao = (RatingBar) findViewById(R.id.ratingAvaliacao);
        editTextComentario = (TextInputLayout) findViewById(R.id.editTextComentario);
        bt_avaliar = (Button) findViewById(R.id.bt_avaliar);



        avalicao.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v < 1){
                    ratingBar.setRating(1);
                }
                Toast.makeText(AvaliacaoCliente.this, String.valueOf(v), Toast.LENGTH_SHORT).show();

            }
        });

        bt_avaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coment = editTextComentario.getEditText().getText().toString();
                int nota = (int)(Math.round(avalicao.getRating()));
                formAvaliacao = new FormAvaliacao(coment,nota);
                avaliarServico();
            }
        });


    }


    private void avaliarServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<ResponseBody> call = restService.avaliarCliente("Bearer "+auth,idServico,formAvaliacao);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AvaliacaoCliente.this, "Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AvaliacaoCliente.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AvaliacaoCliente.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });

    }
    
}