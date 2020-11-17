package com.example.parceiro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormEditarDadosBancarios {

    @SerializedName("agencia")
    @Expose
    private Integer agencia;
    @SerializedName("banco")
    @Expose
    private String banco;
    @SerializedName("conta")
    @Expose
    private Integer conta;
    @SerializedName("nomeBeneficiario")
    @Expose
    private String nome;
    @SerializedName("tipo")
    @Expose
    private String tipo;

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Integer getConta() {
        return conta;
    }

    public void setConta(Integer conta) {
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
