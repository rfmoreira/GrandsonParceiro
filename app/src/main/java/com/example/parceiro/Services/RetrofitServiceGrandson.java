package com.example.parceiro.Services;

import com.example.parceiro.Model.FormEditarSenha;
import com.example.parceiro.Model.Auth;
import com.example.parceiro.Model.FormCadastrarServico;
import com.example.parceiro.Model.CartaoCredito;
import com.example.parceiro.Model.Cliente;
import com.example.parceiro.Model.FormCadastroParceiro;
import com.example.parceiro.Model.FormEditarCartao;
import com.example.parceiro.Model.Foto;
import com.example.parceiro.Model.ListaParceiro;
import com.example.parceiro.Model.FormLogin;
import com.example.parceiro.Model.Parceiro;
import com.example.parceiro.Model.Resposta;
import com.example.parceiro.Model.ServicosAgendados;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitServiceGrandson {

    //************** METODOS GETs *******************//


    @GET("cliente/home")
    Call<List<ListaParceiro>> listarParceiros(@Header("Authorization") String auth);

    @GET("cliente/perfil/parceiro/{id}")
    Call<Parceiro> detalharParceiro(@Header("Authorization") String auth, @Path("id") int id);

    @GET("cliente/perfil/carteira")
    Call<CartaoCredito> getCarteira(@Header("Authorization") String auth);

    @GET("foto/cliente/{id}")
    Call<Foto> getFoto(@Header("Authorization") String auth);

    @GET("cliente/perfil")
    Call<Cliente> getPerfilCliente(@Header("Authorization") String auth);

   @GET("cliente/servico/agendados")
   Call<List<ServicosAgendados>> getServicosAgendados(@Header("Authorization") String auth);



    //************* METODOS POSTs ******************//


    @POST("parceiro/cadastrar/")
    Call<Parceiro> cadastrarParceiro(@Body FormCadastroParceiro parceiro);

    @POST("auth/parceiro")
    Call<Auth> loginCliente(@Body FormLogin formLogin);

    @POST("cliente/servico/cadastrar")
    Call<ServicosAgendados> cadastrarServico(@Header("Authorization") String auth, @Body FormCadastrarServico formCadastrarServico);

    @Multipart
    @POST("foto/parceiro/{id}")
    Call<Foto> uploadImagem(@Part MultipartBody.Part file, @Path("id") int id);


    //************* METODOS PUTs ******************//


    @PUT("cliente/alterar/senha")
    Call<Resposta> alterarSenha(@Header("Authorization") String auth, @Body FormEditarSenha formEditarSenha);

    @PUT("cliente/carteira")
    Call<CartaoCredito> alterarCartao(@Header("Authorization") String auth, @Body FormEditarCartao formEditarCartao);

    @Multipart
    @PUT("foto/cliente/{id}")
    Call<Foto> alterarFotoCliente(@Header("Authorization") String auth, @Part MultipartBody.Part file);
}
