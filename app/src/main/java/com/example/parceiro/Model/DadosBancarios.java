package com.example.parceiro.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DadosBancarios {

    @SerializedName("agencia")
    private Integer agencia;
    @SerializedName("banco")
    private String banco;
    @SerializedName("conta")
    private Integer conta;
    @SerializedName("id")
    private Integer id;
    @SerializedName("tipo")
    private String tipo;

    public DadosBancarios() {
    }

    public DadosBancarios(Integer agencia, String banco, Integer conta, Integer id, String tipo) {
        this.agencia = agencia;
        this.banco = banco;
        this.conta = conta;
        this.id = id;
        this.tipo = tipo;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
