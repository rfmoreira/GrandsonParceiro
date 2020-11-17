package com.example.parceiro.View;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Resposta;
import com.example.parceiro.Model.Servico;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.MetodosCadastro;

import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalharServicoAceito extends FragmentActivity {

    private CircleImageView imgPerfil;
    private TextView nomeCliente,txtNotaPerf,
            txtData,txtHora,
            txtValor,txtHorasServico,
            txtCep,txtEndereco,
            txtNumero,txtComplemento;
    private Button bt_cancelar;

    private String auth;
    private int idServico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_servico_aceito);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");
        idServico = getIntent().getExtras().getInt("idServico");

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
        bt_cancelar = (Button) findViewById(R.id.bt_cancelar);

        getServico();

        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarServico();
            }
        });

    }

    private void getServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Servico> call = restService.detalharSolicitacao("Bearer "+auth,idServico);

        call.enqueue(new Callback<Servico>() {
            @Override
            public void onResponse(Call<Servico> call, Response<Servico> response) {
                if(response.isSuccessful()){
                    Servico servico = response.body();

                    Log.i("Servico", servico.toString());

                    nomeCliente.setText(servico.getNome());
                    String v = servico.getNota();
                    if (v.length() == 1){
                        txtNotaPerf.setText(servico.getNota()+",0");
                    }else {
                        txtNotaPerf.setText(servico.getNota());
                    }

                    String[] data = servico.getDia().split("-");
                    txtData.setText(data[2]+"/"+data[1]+"/"+data[0]);
                    String hora = servico.getHorario();
                    int i = hora.length();
                    txtHora.setText(hora.substring(0,i-3));

                    //String valor = String.valueOf(servico.getValor());
                    //txtValor.setText("R$ "+valor.replace(".",","));

                    DecimalFormat df = new DecimalFormat("0.00");
                    txtValor.setText("R$ "+String.valueOf(df.format(Double.valueOf(servico.getValor()))));

                    String qtdHoras = String.valueOf(servico.getQuantidadeHoras()).replace(".",":");
                    txtHorasServico.setText(qtdHoras+"0");
                    //txtHorasServico.setText(String.valueOf(servico.getQuantidadeHoras())+":00");
                    String cep = MetodosCadastro.addMask(String.valueOf(servico.getEndereco().getCep()),"##.###-###");
                    txtCep.setText(cep);
                    txtEndereco.setText(servico.getEndereco().getEndereco());
                    txtNumero.setText(String.valueOf(servico.getEndereco().getNumero()));
                    txtComplemento.setText(servico.getEndereco().getComplemento());

                    if(servico.getFoto().getData() != null){
                        String foto = servico.getFoto().getData();
                        //Decondificando imagem recebida do JSON
                        byte[]  stringDecodificada = Base64.decode(foto, Base64.DEFAULT);
                        Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

                        imgPerfil.setImageBitmap(imgbtmap);
                    }

                    //Toast.makeText(DetalharServicoAceito.this, "Sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetalharServicoAceito.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Servico> call, Throwable t) {
                Toast.makeText(DetalharServicoAceito.this, "Falha" , Toast.LENGTH_SHORT).show();
                Log.i("Falha", t.toString());
            }
        });

    }

    private void cancelarServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Resposta> call = restService.cancelarServico("Bearer "+auth,idServico);

        call.enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DetalharServicoAceito.this, response.body().getMensagem(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(DetalharServicoAceito.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Toast.makeText(DetalharServicoAceito.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }


}