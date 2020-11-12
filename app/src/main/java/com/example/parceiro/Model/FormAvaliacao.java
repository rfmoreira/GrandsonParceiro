package com.example.parceiro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormAvaliacao{

    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("notaCliente")
    @Expose
    private Integer notaCliente;

    public FormAvaliacao(String comentario, Integer notaParceiro) {
        this.comentario = comentario;
        this.notaCliente = notaParceiro;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getNotaParceiro() {
        return notaCliente;
    }

    public void setNotaParceiro(Integer notaParceiro) {
        this.notaCliente = notaParceiro;
    }
}
