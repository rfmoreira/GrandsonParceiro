package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Comentario;
import com.example.parceiro.Model.Resposta;
import com.example.parceiro.Model.Servico;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.MetodosCadastro;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitacaoServico extends AppCompatActivity {

    private CircleImageView imgPerfil;
    private TextView nomeCliente,txtNotaPerf,
            txtData,txtHora,
            txtValor,txtHorasServico,
            txtCep,txtEndereco,
            txtNumero,txtComplemento;
    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;
    private Button bt_aceitar,bt_recusar,bt_ver_perf;
    private int idServico;
    private int idCliente;

    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacao_servico);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");
        idServico = getIntent().getExtras().getInt("idServico");
        idCliente = getIntent().getExtras().getInt("idCliente");


        imgPerfil = (CircleImageView) findViewById(R.id.imgPerfil);
        nomeCliente = (TextView) findViewById(R.id.nomeCliente);
        txtNotaPerf = (TextView) findViewById(R.id.txtNotaPerf);
        txtData = (TextView) findViewById(R.id.txtData);
        txtHora = (TextView) findViewById(R.id.txtHora);
        txtValor = (TextView) findViewById(R.id.txtValor);
        txtHorasServico = (TextView) findViewById(R.id.txtHorasServico);
        txtCep = (TextView) findViewById(R.id.txtCep);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        txtNumero = (TextView) findViewById(R.id.txtNumero);
        txtComplemento = (TextView) findViewById(R.id.txtComplemento);

        listViewComentarios =(ListView) findViewById(R.id.listViewComentarios);
        bt_aceitar =(Button) findViewById(R.id.bt_aceitar);
        bt_recusar =(Button) findViewById(R.id.bt_recusar);
        bt_ver_perf =(Button) findViewById(R.id.bt_ver_perf);
        //idParceiro = getIntent().getExtras().getInt("idParceiro");

        getServico();

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
                aceitarServico();
            }
        });

        bt_recusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bt_ver_perf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SolicitacaoServico.this, PerfilCliente.class);
                intent.putExtra("idCliente",idCliente);
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


    public void getServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Servico> call = restService.detalharSolicitacao("Bearer "+auth,idServico);

        call.enqueue(new Callback<Servico>() {
            @Override
            public void onResponse(Call<Servico> call, Response<Servico> response) {
                Servico servico = response.body();

                if(response.isSuccessful()){
                    nomeCliente.setText(servico.getNome());
                    txtNotaPerf.setText(servico.getNota());
                    String[] data = servico.getDia().split("-");
                    txtData.setText(data[2]+"/"+data[1]+"/"+data[0]);
                    String hora = servico.getHorario();
                    int i = hora.length();
                    txtHora.setText(hora.substring(0,i-3));
                    String valor = String.valueOf(servico.getValor());
                    txtValor.setText("R$ "+valor.replace(".",",")+"0");
                    txtHorasServico.setText(String.valueOf(servico.getQuantidadeHoras())+":00");
                    String cep = MetodosCadastro.addMask(String.valueOf(servico.getEndereco().getCep()),"##.###-###");
                    txtCep.setText(cep);
                    txtEndereco.setText(servico.getEndereco().getEndereco());
                    txtNumero.setText(String.valueOf(servico.getEndereco().getNumero()));
                    txtComplemento.setText(servico.getEndereco().getComplemento());



                    if(servico.getFoto().getData() == null){

                    }else {
                        String foto = servico.getFoto().getData();
                        //Decondificando imagem recebida do JSON
                        byte[]  stringDecodificada = Base64.decode(foto, Base64.DEFAULT);
                        Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

                        imgPerfil.setImageBitmap(imgbtmap);
                    }

                    Toast.makeText(SolicitacaoServico.this, "Sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SolicitacaoServico.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Servico> call, Throwable t) {
                Toast.makeText(SolicitacaoServico.this, "Falha", Toast.LENGTH_SHORT).show();
                Log.i("Falha",t.toString());
            }
        });

    }

    public void aceitarServico(){
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Resposta> call = restService.aceitarServico("Bearer "+auth,idServico);
        Log.i("ID SERVICO",String.valueOf(idServico));
        call.enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                Resposta resposta = response.body();
                if(response.isSuccessful()){
                    Toast.makeText(SolicitacaoServico.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SolicitacaoServico.this, DetalharServicoAceito.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(SolicitacaoServico.this, "Erro", Toast.LENGTH_SHORT).show();
                    ResponseBody responseBody = response.errorBody();
                    try {
                        Log.i("Erro",responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("Erro2",response.message());
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Toast.makeText(SolicitacaoServico.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }

}