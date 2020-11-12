package com.example.parceiro.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.ListaCliente;
import com.example.parceiro.Model.ServicosAceitos;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.AdapterListVewHomeCliente;
import com.example.parceiro.Utils.AdapterListVewServicosAgendados;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicoConcluidoFragment extends Fragment {

    private List<ListaCliente> listaClientes = new ArrayList<ListaCliente>();
    private ListView listServConcluido;
    private String auth;
    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        listServConcluido = (ListView) view.findViewById(R.id.listServConcluido);

        //listaClientes = preencherList();


        /*listServConcluido.setVisibility(View.VISIBLE);
        // Chamando Adaptador para preenchimento do list View
        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(this.getContext(), listaClientes);
        // Setenado adptador no list view
        listServConcluido.setAdapter(adapter);*/
        getListaServicosAceitos();

    // Metodo de onClick do list view
        listServConcluido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Toast.makeText(view.getContext(), "Posição: "+ listaClientes.get(position).getNome(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), AvaliacaoCliente.class);
            intent.putExtra("idServico", listaClientes.get(position).getIdServico());
            startActivity(intent);
        }
    });

    }


    // Metodo para Preencher ListView
    private ArrayList<ListaCliente> preencherList() {
        ArrayList<ListaCliente> list = new ArrayList<ListaCliente>();
       /* ListaCliente p = new ListaCliente();
        ListaCliente p1 = new ListaCliente();
        ListaCliente p2 = new ListaCliente();
        ListaCliente p3 = new ListaCliente();
        ListaCliente p4 = new ListaCliente();
        p.setNome("Rafael");
        list.add(p);
        p1.setNome("Luan");
        list.add(p1);
        p2.setNome("Lucas");
        list.add(p2);
        p3.setNome("Alessandro");
        list.add(p3);
        p4.setNome("Alex");
        list.add(p4);*/
        return list;
    }

    private void getListaServicosAceitos(){
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<List<ListaCliente>> call = restService.getServicosConcluidos("Bearer "+auth);

        call.enqueue(new Callback<List<ListaCliente>>() {
            @Override
            public void onResponse(Call<List<ListaCliente>> call, Response<List<ListaCliente>> response) {
                if (response.isSuccessful()){

                    listaClientes = response.body();

                    // Verificando se lista esta vazia
                    if (listaClientes.isEmpty()){
                        Toast.makeText(getContext(), "Lista Vazia", Toast.LENGTH_SHORT).show();
                    }else {
                        listServConcluido.setVisibility(View.VISIBLE);
                        // Chamando Adaptador para preenchimento do list View
                        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(getContext(), listaClientes);
                        // Setenado adptador no list view
                        listServConcluido.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListaCliente>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha"
                        , Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servico_concluido, container, false);
    }
}