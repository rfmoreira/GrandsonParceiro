package com.example.parceiro.Services;

import com.example.parceiro.Model.Cep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RetrofitServiceCEP {

        //consultar CEP no webservice do ViaCEP
        @GET("{cep}/json/")
        Call<Cep> consultarCEP(@Path("cep") String cep);

}

