package com.example.parceiro.Services;

import com.example.parceiro.Model.Bancos;
import com.example.parceiro.Model.Cliente;
import com.example.parceiro.Model.FormAvaliacao;
import com.example.parceiro.Model.FormEditarParceiro;
import com.example.parceiro.Model.FormEditarSenha;
import com.example.parceiro.Model.Auth;
import com.example.parceiro.Model.DadosBancarios;
import com.example.parceiro.Model.FormCadastroParceiro;
import com.example.parceiro.Model.FormEditarDadosBancarios;
import com.example.parceiro.Model.Foto;
import com.example.parceiro.Model.ListaCliente;
import com.example.parceiro.Model.FormLogin;
import com.example.parceiro.Model.Parceiro;
import com.example.parceiro.Model.Resposta;
import com.example.parceiro.Model.Servico;
import com.example.parceiro.Model.ServicosAceitos;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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

    //OK
    @GET("parceiro/home")
    Call<List<ListaCliente>> listarCliente(@Header("Authorization") String auth);

    //OK
    @GET("parceiro/servico/detalhar/{id}")
    Call<Servico> detalharSolicitacao(@Header("Authorization") String auth, @Path("id") int idServico);


    @GET("parceiro/perfil/carteira")
    Call<DadosBancarios> getCarteira(@Header("Authorization") String auth);

    //OK
    @GET("foto/parceiro")
    Call<Foto> getFoto(@Header("Authorization") String auth);

    //OK
    @GET("parceiro/perfil")
    Call<Parceiro> getPerfilParceiro(@Header("Authorization") String auth);

    @GET("parceiro/servico/concluidos")
    Call<List<ListaCliente>> getServicosConcluidos(@Header("Authorization") String auth);

    @GET("parceiro/perfil/cliente/{id}")
    Call<Cliente> getPerfilCliente(@Header("Authorization") String auth,@Path("id") int id);

   @GET("parceiro/servico/agendados")
   Call<List<ServicosAceitos>> getServicosAceitos(@Header("Authorization") String auth);

   @GET("parceiro/bancos")
   Call<List<Bancos>> getBancos();



    //************* METODOS POSTs ******************//

    //OK
    @POST("parceiro/cadastrar")
    Call<Resposta> cadastrarParceiro(@Body FormCadastroParceiro parceiro);
    //OK
    @POST("auth/parceiro")
    Call<Auth> loginCliente(@Body FormLogin formLogin);

    /*@POST("cliente/servico/cadastrar")
    Call<ServicosAceitos> cadastrarServico(@Header("Authorization") String auth, @Body FormCadastrarServico formCadastrarServico);
    */

    @Multipart
    @POST("foto/parceiro/{id}")
    Call<Foto> uploadImagem(@Part MultipartBody.Part file, @Path("id") int id);


    //************* METODOS PUTs ******************//


    @PUT("parceiro/alterar/senha")
    Call<Resposta> alterarSenha(@Header("Authorization") String auth, @Body FormEditarSenha formEditarSenha);

    @PUT("parceiro/perfil/carteira")
    Call<DadosBancarios> alterarCartao(@Header("Authorization") String auth, @Body FormEditarDadosBancarios formEditarDadosBancarios);

    @Multipart
    @PUT("foto/parceiro")
    Call<Foto> alterarFotoParceiro(@Header("Authorization") String auth, @Part MultipartBody.Part file);

    @PUT("parceiro/servico/avaliar/{id}")
    Call<ResponseBody> avaliarCliente(@Header("Authorization") String auth, @Path("id") int id , @Body FormAvaliacao formAvaliacao);

    @PUT("parceiro")
    Call<Parceiro> alterarParceiro(@Header("Authorization") String auth, @Body FormEditarParceiro formEditarCliente);

    @PUT("parceiro/servico/aceitar/{idservico}")
    Call<Resposta> aceitarServico(@Header("Authorization") String auth,@Path("idservico") int id);

    @PUT("parceiro/servico/cancelar/{id}")
    Call<Resposta> cancelarServico(@Header("Authorization") String auth, @Path("id") int id);


}
