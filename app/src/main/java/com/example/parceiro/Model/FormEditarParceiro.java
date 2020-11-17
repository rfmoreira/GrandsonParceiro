package com.example.parceiro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormEditarParceiro {

    @SerializedName("cep")
    @Expose
    private Integer cep;
    @SerializedName("complemento")
    @Expose
    private String complemento;
    @SerializedName("endereco")
    @Expose
    private String endereco;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("numero")
    @Expose
    private Integer numero;
    @SerializedName("telefone")
    @Expose
    private String telefone;

    public FormEditarParceiro(Integer cep, String complemento, String endereco, String nome, Integer numero, String telefone) {
        this.cep = cep;
        this.complemento = complemento;
        this.endereco = endereco;
        this.nome = nome;
        this.numero = numero;
        this.telefone = telefone;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
