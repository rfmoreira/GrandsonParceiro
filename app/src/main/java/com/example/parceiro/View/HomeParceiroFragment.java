package com.example.parceiro.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.ListaCliente;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.AdapterListVewHomeCliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeParceiroFragment extends Fragment {

    ListView listView;
    List<ListaCliente> lListaCliente = new ArrayList<>();
    List<ListaCliente> paceirosFiltrados = new ArrayList<>();
    //int imagens[] = {};

    TextView textView, nomeCabecalho;
    SearchView searchParceiro;

    private String auth;
    private String nome;



    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");
        nome = pref.getString("nome","");

        //getActivity().onBackPressed();

        Log.i("Auth :", auth);
        listarCliente();
        //Assosinado entradas da tela
        listView =(ListView) view.findViewById(R.id.listViewParceiro);
        textView = (TextView) view.findViewById(R.id.textInfo);
        searchParceiro = (SearchView) view.findViewById(R.id.searchParceiro);
        nomeCabecalho = (TextView) view.findViewById(R.id.nomeCabecalho);

        //String nome = getActivity().getIntent().getStringExtra("nome");
        nomeCabecalho.setText(nome);




        // Metodo de onClick do list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(view.getContext(), "Posição: "+ lListaCliente.get(position).getIdServico(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), SolicitacaoServico.class);
                intent.putExtra("idServico", lListaCliente.get(position).getIdServico());
                intent.putExtra("idCliente", lListaCliente.get(position).getIdCliente());
                startActivity(intent);
            }
        });

        //Search View
        searchParceiro.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchContato(newText);
                return false;
            }
        });

    }

    // Metodo para Preencher ListView
    private ArrayList<ListaCliente> preencherList() {
        ArrayList<ListaCliente> list = new ArrayList<ListaCliente>();
       /* ListaParceiro p = new ListaParceiro();
        ListaParceiro p1 = new ListaParceiro();
        ListaParceiro p2 = new ListaParceiro();
        ListaParceiro p3 = new ListaParceiro();
        ListaParceiro p4 = new ListaParceiro();
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

    public void searchContato(String name){
        paceirosFiltrados.clear();;
        for( ListaCliente c: lListaCliente){
            if(c.getNome().toLowerCase().contains(name.toLowerCase())){
                paceirosFiltrados.add(c);
            }
            listView.invalidateViews();
        }
    }

    private void listarCliente(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<List<ListaCliente>> call = restService.listarCliente("Bearer "+auth);

        call.enqueue(new Callback<List<ListaCliente>>() {
            @Override
            public void onResponse(Call<List<ListaCliente>> call, Response<List<ListaCliente>> response) {

                if(response.isSuccessful()){
                    lListaCliente.addAll(response.body());
                   // Log.i("Response", lListaCliente.get(0).toString());


                    // Verificando se lista esta vazia
                    if (lListaCliente.isEmpty()){
                        Log.i("Empty", "Lista vazia");
                        textView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(getContext(),null);
                    }else {
                        paceirosFiltrados.addAll(lListaCliente);
                        listView.setVisibility(View.VISIBLE);
                        // Chamando Adaptador para preenchimento do list View
                        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(getContext(),paceirosFiltrados);
                        // Setenado adptador no list view
                        listView.setAdapter(adapter);

                    }



                }else {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                    Log.i("Erro:  ",response.message());

                }
            }

            @Override
            public void onFailure(Call<List<ListaCliente>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha", Toast.LENGTH_SHORT).show();
                Log.i("Falha:  ",t.getMessage());


            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_parceiro,container,false);
        // Inflate the layout for this fragment
        return view;
    }





}