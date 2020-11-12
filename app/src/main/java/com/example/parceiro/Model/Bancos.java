package com.example.parceiro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bancos {

    @SerializedName("banco")
    @Expose
    private String banco;
    @SerializedName("codigo")
    @Expose
    private String codigo;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
