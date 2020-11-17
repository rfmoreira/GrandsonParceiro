package com.example.parceiro.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.ServicosAceitos;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.AdapterListVewServicosAgendados;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicosAceitosFragment extends Fragment {


    private ListView listView;
    private List<ServicosAceitos> lServicosAgendados = new ArrayList<>();
    private TextView textInfo;

    private String auth;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //lListaParceiro = preencherList();

        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        //Assosinado entradas da tela
        listView =(ListView) view.findViewById(R.id.listViewParceiro);
        textInfo = (TextView) view.findViewById(R.id.textInfo);

        listarServicosAgendados();

        // Metodo de onClick do list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(view.getContext(), "Posição: "+ lServicosAgendados.get(position).getNome(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),DetalharServicoAceito.class);
                intent.putExtra("idServico", lServicosAgendados.get(position).getIdServico());
                startActivity(intent);

            }
        });
    }


        private void listarServicosAgendados(){

            //Instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
            //Passando os dados para consulta
            Call<List<ServicosAceitos>> call = restService.getServicosAceitos("Bearer "+auth);

            call.enqueue(new Callback<List<ServicosAceitos>>() {
                @Override
                public void onResponse(Call<List<ServicosAceitos>> call, Response<List<ServicosAceitos>> response) {

                    Log.i("Response Code", String.valueOf(response.code()));

                    if(response.isSuccessful()){
                        lServicosAgendados.addAll(response.body());
                        //Log.i("Response", lListaParceiro.get(0).getNota());

                        // Verificando se lista esta vazia
                        if (lServicosAgendados.isEmpty()){
                            textInfo.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);
                           // AdapterListVewServicosAgendados adapter = new AdapterListVewServicosAgendados(getActivity(),null);
                        }else {
                            listView.setVisibility(View.VISIBLE);
                            try {
                                // Chamando Adaptador para preenchimento do list View
                                AdapterListVewServicosAgendados adapter = new AdapterListVewServicosAgendados(getContext(), lServicosAgendados);
                                // Setenado adptador no list view
                                listView.setAdapter(adapter);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }else {
                        Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                        Log.i("Erro:  ",response.message());

                    }
                }

                @Override
                public void onFailure(Call<List<ServicosAceitos>> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                    Log.i("Falha:  ",t.getMessage());


                }
            });


        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servicos_aceitos, container, false);
    }
}