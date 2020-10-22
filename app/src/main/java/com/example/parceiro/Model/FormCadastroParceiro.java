package com.example.parceiro.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FormCadastroParceiro implements Parcelable {

    //Primeira Tela
    @SerializedName("nome")
    private String nome;
    @SerializedName("email")
    private String email;
    @SerializedName("telefone")
    private String telefone;
    @SerializedName("senha")
    private String senha;

    private String uriFoto;

    //Segunda Tela Cadastro
    @SerializedName("cep")
    private int cep;
    @SerializedName("endereco")
    private String endereco;
    @SerializedName("numero")
    private int numero;
    @SerializedName("complemento")
    private String complemento;

    //Terceira Tela cadastro
    @SerializedName("cpf")
    private  String cpf;
    @SerializedName("agencia")
    private String agencia;
    @SerializedName("banco")
    private String banco;
    @SerializedName("conta")
    private int conta;
    @SerializedName("tipo")
    private String tipo;


    public FormCadastroParceiro(String nome, String email, String telefone, String senha, String uriFoto) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.uriFoto = uriFoto;
    }


    protected FormCadastroParceiro(Parcel in) {
        nome = in.readString();
        email = in.readString();
        telefone = in.readString();
        senha = in.readString();
        cep = in.readInt();
        endereco = in.readString();
        numero = in.readInt();
        complemento = in.readString();
        cpf = in.readString();
        agencia = in.readString();
        banco = in.readString();
        conta = in.readInt();
        tipo = in.readString();
        uriFoto = in.readString();
    }

    public static final Creator<FormCadastroParceiro> CREATOR = new Creator<FormCadastroParceiro>() {
        @Override
        public FormCadastroParceiro createFromParcel(Parcel in) {
            return new FormCadastroParceiro(in);
        }

        @Override
        public FormCadastroParceiro[] newArray(int size) {
            return new FormCadastroParceiro[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    public String toString(){

        return "Nome: "+ this.nome
                +"\n Email: "+ this.email
                +"\n Telefone: "+ this.telefone
                +"\n Senha: " +this.senha
                +"\n Cep: "+ this.cep
                +"\n Enderco: "+ this.endereco
                +"\n Numero: "+ this.numero
                +"\n complemento: "+ this.complemento
                +"\n cpf: "+ this.cpf
                +"\n agencia: "+ this.agencia
                +"\n banco: "+ this.banco
                +"\n conta: "+ this.conta
                +"\n tipo: "+ this.tipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(email);
        parcel.writeString(telefone);
        parcel.writeString(senha);
        parcel.writeInt(cep);
        parcel.writeString(endereco);
        parcel.writeInt(numero);
        parcel.writeString(complemento);
        parcel.writeString(cpf);
        parcel.writeString(agencia);
        parcel.writeString(banco);
        parcel.writeInt(conta);
        parcel.writeString(tipo);
        parcel.writeString(uriFoto);
    }
}
